/*
 * Copyright (C) 2017-2026 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package gr.iccs.imu.ems.baguette.client.collector.wazuh;

import gr.iccs.imu.ems.baguette.client.IClientCollector;
import gr.iccs.imu.ems.brokercep.BrokerCepService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.net.URI;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Wazuh collector plugin.
 * <p>
 * Unlike the Netdata collector, this plugin does not scrape anything itself. It translates the
 * EMS sensor configurations passed in {@link #setConfiguration(Object)} into the configuration
 * file of the external Wazuh client (the process that queries the Wazuh Indexer and publishes
 * alerts to the EMS broker), and writes that file to disk.
 * <p>
 * Each (pull) sensor configuration maps to one entry of the client's 'processor.metrics' list:
 * <pre>
 * - name: wazuh_sca_alerts                 # --> metrics[].topic = /topic/wazuh_sca_alerts
 *   configuration:
 *     min_level: 9                         # --> metrics[].min_level
 *     max_level: 999                       # --> metrics[].max_level
 *     keys-to-extract: rule.id,agent.name  # --> (optional) added to processor.keys_to_extract
 *     filters:                             # --> metrics[].filters
 *       agent.name: cipher-client
 *       event.location: sca
 *     rule.groups: ".*"                    # any other scalar key is also treated as a filter
 * </pre>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WazuhExternalCollector implements IClientCollector {

    public static final String TOPIC_PREFIX = "/topic/";

    /**
     * Sensor 'configuration' keys that are NOT alert filters (metric-level or sensor settings).
     * Compared in normalized form: lower-case, '_' replaced by '-' (see {@link #isNonFilterKey}).
     */
    protected static final Set<String> SENSOR_CONFIG_KEYS_EXCLUDED = Set.of(
            "min-level", "max-level",
            "filters", "keys-to-extract",
            "type", "endpoint", "port", "-containername",
            "results-aggregation", "aggregation", "interval",
            "components", "namespace");

    private final WazuhExternalCollectorProperties properties;
    private final BrokerCepService brokerCepService;

    private boolean started;
    private List<Map<String, Object>> configurations;
    private String initialSince;        // fixed once chosen, so that rewrites don't move the start point
    private String lastWrittenContent;

    private record IndexerConfig(
            String host,
            String username,
            String password,
            String index,
            boolean verifySsl,
            String initialSince,
            int minimumLevel,
            int batchSize
    ) {}

    private IndexerConfig indexerConfig;

    @Override
    public String getName() {
        return "wazuh";
    }

    @Override
    public synchronized void setConfiguration(Object config) {
        setConfiguration(config, null);
    }

    @Override
    public synchronized void setConfiguration(Object config, Object typeConfig) {
        // Process wazuh collector common configuration
        if (typeConfig instanceof Map map) {
            if (map.get("indexer") instanceof Map indexerMap && ! indexerMap.isEmpty()) {
                indexerConfig = new IndexerConfig(
                    indexerMap.getOrDefault("host", "").toString(),
                    indexerMap.getOrDefault("username", "").toString(),
                    indexerMap.getOrDefault("password", "").toString(),
                    indexerMap.getOrDefault("index", "wazuh-alerts-*").toString(),
                    "true".equalsIgnoreCase( indexerMap.getOrDefault("verify_ssl", "false").toString() ),
                    indexerMap.getOrDefault("initial_since", "").toString(),
                    Integer.parseInt( indexerMap.getOrDefault("minimum_level", "1").toString() ),
                    Integer.parseInt( indexerMap.getOrDefault("batch_size", "500").toString() )
                );

                if (StringUtils.isAnyBlank(indexerConfig.host(), indexerConfig.username(), indexerConfig.password())) {
                    log.warn("Missing essential Wazuh Indexer settings: host, username, or password is missing or blank");
                }
            }
        }

        if (config instanceof List<?> sensorConfigList) {
            List<Map<String, Object>> newConfigurations = new ArrayList<>();
            for (Object o : sensorConfigList) {
                if (o instanceof Map<?, ?> m && m.keySet().stream().allMatch(k -> k instanceof String)) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> typed = (Map<String, Object>) m;
                    newConfigurations.add(typed);
                }
            }
            log.debug("WazuhExternalCollector: setConfiguration: newConfigurations: {}", newConfigurations);

            configurations = newConfigurations;
            log.debug("WazuhExternalCollector: setConfiguration: Collector configuration updated");

            // The external client is configured through a file, so no restart is needed here:
            // just regenerate the file (the client picks it up on its own restart/reload)
            if (started) writeConfigFile();
        } else
            log.warn("WazuhExternalCollector: setConfiguration: Ignoring unsupported configuration object: {}", config);
    }

    @Override
    public synchronized void start() {
        if (started) return;
        started = true;
        if (configurations != null) writeConfigFile();
        log.debug("WazuhExternalCollector: Started");
    }

    @Override
    public synchronized void stop() {
        if (!started) return;
        started = false;
        log.debug("WazuhExternalCollector: Stopped");
    }

    public synchronized void activeGroupingChanged(String oldGrouping, String newGrouping) {
        log.debug("WazuhExternalCollector: activeGroupingChanged: {} --> {}", oldGrouping, newGrouping);
    }

    // ------------------------------------------------------------------------------------------
    //  Config generation
    // ------------------------------------------------------------------------------------------

    private void writeConfigFile() {
        try {
            Map<String, Object> root = buildClientConfig();
            String yamlText = toYaml(root);

            if (yamlText.equals(lastWrittenContent)) {
                log.debug("WazuhExternalCollector: writeConfigFile(): Configuration unchanged. Not rewriting file");
                return;
            }

            Path target = Path.of(properties.getConfigFile()).toAbsolutePath();
            writeAtomically(target, yamlText);
            lastWrittenContent = yamlText;
            log.info("WazuhExternalCollector: Wazuh client configuration written to: {}", target);
        } catch (Exception e) {
            log.error("WazuhExternalCollector: writeConfigFile(): EXCEPTION while writing Wazuh client configuration: ", e);
        }
    }

    /** Builds the complete client configuration as an ordered map (mirrors the client's config.yaml) */
    protected Map<String, Object> buildClientConfig() {
        String indexerHost = indexerConfig.host();
        String indexerUsername = indexerConfig.username();
        String indexerPassword = indexerConfig.password();
        String indexerIndex = indexerConfig.index();
        boolean indexerVerifySSL = indexerConfig.verifySsl();

        String queryInitialSince = indexerConfig.initialSince();
        int queryMinimumLevel = indexerConfig.minimumLevel();
        int queryBatchSize = indexerConfig.batchSize();

        List<Map<String, Object>> metrics = new ArrayList<>();
        Map<Map<String, Object>, Map<String, Object>> pendingFilters = new IdentityHashMap<>();
        Set<String> keysToExtract = new LinkedHashSet<>(properties.getKeysToExtract());

        int sensorNum = 0;
        for (Map<String, Object> sensor : configurations == null ? List.<Map<String, Object>>of() : configurations) {
            sensorNum++;
            log.debug("WazuhExternalCollector: Sensor-{}: sensor={}", sensorNum, sensor);

            // Push sensors are ignored (same as in the Netdata collector)
            if ("true".equalsIgnoreCase(get(sensor, "pushSensor", "false"))) {
                log.debug("WazuhExternalCollector: Sensor-{}: It is a Push sensor. Ignoring", sensorNum);
                continue;
            }

            String name = get(sensor, "name", null);
            if (StringUtils.isBlank(name)) {
                log.warn("WazuhExternalCollector: Sensor-{}: No destination found in sensor config: {}", sensorNum, sensor);
                continue;
            }

            if (!(sensor.get("configuration") instanceof Map<?, ?> cfgRaw)) {
                log.warn("WazuhExternalCollector: Sensor-{}: No sensor configuration found in spec: {}", sensorNum, sensor);
                continue;
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> cfg = (Map<String, Object>) cfgRaw;

            /*if (cfg.get("indexer") instanceof Map<?, ?> indexerCfg) {
                Object o;
                indexerHost = (o = indexerCfg.get("host"))!=null ? o.toString() : null;
                indexerUsername = (o = indexerCfg.get("username"))!=null ? o.toString() : null;
                indexerPassword = (o = indexerCfg.get("password"))!=null ? o.toString() : null;
                indexerIndex = (o = indexerCfg.get("index"))!=null ? o.toString() : null;
                indexerVerifySSL = (o = indexerCfg.get("verify_ssl")) == null || ! "false".equalsIgnoreCase(o.toString());

                queryInitialSince = (o = indexerCfg.get("initial_since"))!=null ? o.toString() : null;
                queryMinimumLevel = (o = indexerCfg.get("minimum_level"))!=null ? Integer.parseInt(o.toString()) : queryMinimumLevel;
                queryBatchSize = (o = indexerCfg.get("batch_size"))!=null ? Integer.parseInt(o.toString()) : queryBatchSize;
            }*/

            Map<String, Object> metric = new LinkedHashMap<>();
            metric.put("topic", name.startsWith(TOPIC_PREFIX) ? name : TOPIC_PREFIX + name);
            metric.put("min_level", getInt(cfg, properties.getDefaultMinLevel(), "min_level", "min-level"));
            metric.put("max_level", getInt(cfg, properties.getDefaultMaxLevel(), "max_level", "max-level"));
            metric.put("publish_empty", true);

            // Filters: explicit 'filters' map first, then any other entries of 'configuration'.
            // Metric-level settings (aggregation, interval, levels...) are never filters, even when
            // they were placed inside the 'filters' map.
            Map<String, Object> filtersIn = new LinkedHashMap<>();
            if (cfg.get("filters") instanceof Map<?, ?> f)
                f.forEach((k, v) -> {
                    if (k != null) {
                        filtersIn.put(k.toString(), flattenObject(v));
                    }
                });
            Map<String, Object> filters = new LinkedHashMap<>();
            filtersIn.forEach((k, v) -> addFilter(filters, k, v));
            cfg.forEach((k, v) -> { if (StringUtils.isNotBlank(k)) addFilter(filters, k, v); });

            // Interval and aggregation: metric-level settings, looked up in the sensor,
            // its 'configuration', and its 'filters' map
            Integer intervalSeconds = getIntervalSeconds(sensor, cfg, filtersIn);
            if (intervalSeconds != null) metric.put("interval_seconds", intervalSeconds);
            String aggregation = firstNonBlank(
                    getAny(cfg, "results-aggregation", "results_aggregation", "aggregation"),
                    getAny(filtersIn, "results-aggregation", "results_aggregation", "aggregation"),
                    getAny(sensor, "results-aggregation", "results_aggregation", "aggregation"));
            if (aggregation != null) metric.put("aggregation", aggregation.toUpperCase());

            // Optional extra keys to extract from alerts
            keysToExtract.addAll(getList(cfg, "keys-to-extract", "keys_to_extract"));

            pendingFilters.put(metric, filters);
            metrics.add(metric);
            log.debug("WazuhExternalCollector: Sensor-{}: metric={}", sensorNum, metric);
        }

        // Filters may only use keys listed in keys_to_extract, so any filter key that is not
        // already covered (exactly or by a wildcard such as 'additional_context.*') is added to it
        pendingFilters.forEach((metric, filters) -> {
            filters.keySet().forEach(k -> {
                if (!isExtractedKey(keysToExtract, k)) {
                    log.debug("WazuhExternalCollector: Adding filter key '{}' of topic {} to keys_to_extract",
                            k, metric.get("topic"));
                    keysToExtract.add(k);
                }
            });
            if (!filters.isEmpty()) metric.put("filters", filters);
        });

        // Prepare partial configurations
        // --- wazuh
        Map<String, Object> wazuh = createWazuhIndexerConfiguration(indexerHost, indexerUsername, indexerPassword, indexerIndex, indexerVerifySSL);

        // --- alerts
        Map<String, Object> alerts = createAlertsConfiguration(queryInitialSince, queryMinimumLevel, metrics, queryBatchSize);

        // --- broker
        Map<String, Object> brokerClient = createBrokerClientConfiguration();

        // --- processor
        Map<String, Object> processor = createProcessorConfiguration(keysToExtract, metrics);

        Map<String, Object> root = new LinkedHashMap<>();
        root.put("wazuh", wazuh);
        root.put("alerts", alerts);
        root.put("broker", brokerClient);
        root.put("processor", processor);
        return root;
    }

    private static @NonNull Map<String, Object> createWazuhIndexerConfiguration(String indexerHost, String indexerUsername, String indexerPassword, String indexerIndex, boolean indexerVerifySSL) {
        Map<String, Object> wazuh = new LinkedHashMap<>();
        if (StringUtils.isNotBlank(indexerHost)) {
            wazuh.put("host", indexerHost);
            wazuh.put("username", indexerUsername);
            wazuh.put("password", indexerPassword);
            wazuh.put("index", indexerIndex);
            wazuh.put("verify_ssl", indexerVerifySSL);
        }
        return wazuh;
    }

    private @NonNull Map<String, Object> createAlertsConfiguration(String queryInitialSince, int queryMinimumLevel, List<Map<String, Object>> metrics, int queryBatchSize) {
        if (StringUtils.isBlank(initialSince)) {
            initialSince = StringUtils.isNotBlank(queryInitialSince)
                    ? queryInitialSince.trim()
                    : Instant.now().truncatedTo(ChronoUnit.SECONDS).toString();
        }
        int minimumLevel = queryMinimumLevel > 0
                ? queryMinimumLevel
                : metrics.stream().mapToInt(m -> (Integer) m.get("min_level")).min()
                    .orElse(properties.getDefaultMinLevel());
        Map<String, Object> alerts = new LinkedHashMap<>();
        alerts.put("initial_since", initialSince);
        alerts.put("minimum_level", minimumLevel);
        alerts.put("batch_size", queryBatchSize);
        return alerts;
    }

    private String flattenObject(Object v) {
        if (v==null) return "";
        if (v instanceof List<?> l) {
            return l.stream().filter(Objects::nonNull).map(this::flattenObject).collect(Collectors.joining("|"));
        }
        if (v instanceof Map<?, ?> m) {
            return m.entrySet().stream().map(this::flattenObject).collect(Collectors.joining("|"));
        }
        return v.toString();
    }

    private @NonNull Map<String, Object> createBrokerClientConfiguration() {
        Map<String, Object> broker = new LinkedHashMap<>();
        if (brokerCepService!=null && brokerCepService.getBrokerCepProperties()!=null) {
            String brokerUrl = null;
            int brokerPort = -1;
            // Get server address
            String clientUrl = brokerCepService.getBrokerCepProperties().getBrokerUrlForClients();
            if (StringUtils.isNotBlank(clientUrl)) {
                URI uri = URI.create(clientUrl);
                brokerUrl = uri.getHost();
                log.trace("createBrokerClientConfiguration: brokerUrl: {}", brokerUrl);
            }
            // Get STOMP port
            List<String> urlList = brokerCepService.getBrokerCepProperties().getBrokerUrlList();
            if (urlList!=null && ! urlList.isEmpty()) {
                for (String url : urlList) {
                    URI uri = URI.create(url);
                    String protocol = uri.getScheme();
                    int port = uri.getPort();
                    if (Strings.CI.equals(protocol, "stomp")) {
                        brokerPort = port > 0 ? port : 61610;
                        log.trace("createBrokerClientConfiguration: STOMP port: {}", brokerPort);
                        break;
                    }
                }
            }
            // Prepare broker client configuration
            if (StringUtils.isNotBlank(brokerUrl) && brokerPort > 0) {
                broker.put("url", brokerUrl);
                broker.put("port", brokerPort);
                broker.put("username", StringUtils.defaultString(brokerCepService.getBrokerUsername()));
                broker.put("password", StringUtils.defaultString(brokerCepService.getBrokerPassword()));
            }
        }
        return broker;
    }

    private @NonNull Map<String, Object> createProcessorConfiguration(Set<String> keysToExtract, List<Map<String, Object>> metrics) {
        Map<String, Object> processor = new LinkedHashMap<>();
        processor.put("interval_seconds", properties.getProcessorIntervalSeconds());
        processor.put("checkpoint_file", properties.getProcessorCheckpointFile());
        processor.put("keys_to_extract", new ArrayList<>(keysToExtract));
        processor.put("metrics", metrics);
        processor.put("publish_empty", true);
        return processor;
    }

    private String toYaml(Map<String, Object> root) {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setIndent(4);
        options.setIndicatorIndent(2);   // indent list dashes under their parent key
        options.setPrettyFlow(true);
        options.setSplitLines(false);    // never wrap long values such as regexes
        String header = """
                #
                # Wazuh collector Configuration (generated by EMS WazuhExternalCollector -- do not edit manually)
                # Created on: %s
                #
                
                """.formatted( ZonedDateTime.now().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")) );
        return header + new Yaml(options).dump(root);
    }

    private void writeAtomically(Path target, String content) throws IOException {
        Path dir = target.getParent();
        if (dir != null) Files.createDirectories(dir);
        Path tmp = (dir != null ? dir : Path.of("."))
                .resolve(target.getFileName() + ".tmp");
        Files.writeString(tmp, content);
        try {
            Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException e) {
            Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    // ------------------------------------------------------------------------------------------
    //  Helpers
    // ------------------------------------------------------------------------------------------

    private String get(Map<String, Object> map, String key, String defaultValue) {
        Object valObj;
        if (!map.containsKey(key) || (valObj = map.get(key)) == null) return defaultValue;
        String value = valObj.toString();
        if (StringUtils.isBlank(value)) return defaultValue;
        return value.trim();
    }

    private int getInt(Map<String, Object> map, int defaultValue, String... keys) {
        for (String key : keys) {
            String s = get(map, key, null);
            if (s != null) {
                try {
                    return Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    log.warn("WazuhExternalCollector: Invalid integer for '{}': {}. Using default: {}", key, s, defaultValue);
                    return defaultValue;
                }
            }
        }
        return defaultValue;
    }

    /** A key is allowed if listed exactly, or covered by a wildcard entry such as 'additional_context.*' */
    private boolean isExtractedKey(Collection<String> keysToExtract, String key) {
        for (String entry : keysToExtract) {
            if (entry.equals(key)) return true;
            if (entry.endsWith(".*") && key.startsWith(entry.substring(0, entry.length() - 1))) return true;
        }
        return false;
    }

    /** True for sensor/metric-level keys that must never be emitted as filters */
    private boolean isNonFilterKey(String key) {
        return SENSOR_CONFIG_KEYS_EXCLUDED.contains(key.trim().toLowerCase().replace('_', '-'));
    }

    /** Adds a filter. Accepts scalars and collections (e.g. rule_ids: [5710, 5712]); anything else is ignored */
    private void addFilter(Map<String, Object> filters, String key, Object value) {
        if (value == null || isNonFilterKey(key) || filters.containsKey(key)) return;
        if (value instanceof String || value instanceof Number || value instanceof Boolean) {
            filters.put(key, value.toString());
        } else if (value instanceof Collection<?> c) {
            List<String> items = c.stream().filter(Objects::nonNull).map(Object::toString).toList();
            if (!items.isEmpty()) filters.put(key, items);
        } else {
            log.warn("WazuhExternalCollector: Ignoring filter '{}' with unsupported value type: {}", key, value.getClass().getName());
        }
    }

    private String getAny(Map<String, Object> map, String... keys) {
        for (String key : keys) {
            String v = get(map, key, null);
            if (v != null) return v;
        }
        return null;
    }

    private String firstNonBlank(String... values) {
        for (String v : values) if (StringUtils.isNotBlank(v)) return v;
        return null;
    }

    /** Reads sensor 'interval' ({period, unit}) from the sensor or its configuration; null if absent */
    private Integer getIntervalSeconds(Map<String, Object> sensor, Map<String, Object> cfg, Map<String, Object> filtersIn) {
        Object o = Stream.of(sensor, cfg, filtersIn).map(m -> m.get("interval"))
                .filter(Objects::nonNull).findFirst().orElse(null);
        try {
            if (o instanceof Map<?, ?> m) {
                @SuppressWarnings("unchecked")
                Map<String, Object> im = (Map<String, Object>) m;
                long period = Long.parseLong(get(im, "period", "0"));
                if (period <= 0) return null;
                java.util.concurrent.TimeUnit unit = java.util.concurrent.TimeUnit.valueOf(
                        get(im, "unit", "SECONDS").toUpperCase());
                return (int) unit.toSeconds(period);
            }
            if (o != null && StringUtils.isNotBlank(o.toString()))
                return Integer.parseInt(o.toString().trim());
        } catch (Exception e) {
            log.warn("WazuhExternalCollector: Invalid interval specified: {}", o);
        }
        return null;
    }

    /** Accepts either a collection or a comma/space separated string */
    private List<String> getList(Map<String, Object> map, String... keys) {
        for (String key : keys) {
            Object o = map.get(key);
            if (o instanceof Collection<?> c)
                return c.stream().filter(Objects::nonNull).map(Object::toString)
                        .map(String::trim).filter(StringUtils::isNotBlank).toList();
            if (o instanceof String s && StringUtils.isNotBlank(s))
                return Arrays.stream(s.trim().split("[,\\s]+")).filter(StringUtils::isNotBlank).toList();
        }
        return List.of();
    }
}