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
import gr.iccs.imu.ems.util.SerializationUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

    @Override
    public String getName() {
        return "wazuh";
    }

    @SneakyThrows
    public static void main(String[] args) {
        String graph = "rO0ABXNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAEdwQAAAAEc3IAF2phdmEudXRpbC5MaW5rZWRIYXNoTWFwNMBOXBBswPsCAAFaAAthY2Nlc3NPcmRlcnhyABFqYXZhLnV0aWwuSGFzaE1hcAUH2sHDFmDRAwACRgAKbG9hZEZhY3RvckkACXRocmVzaG9sZHhwP0AAAAAAAAx3CAAAABAAAAAIdAAMQG9iamVjdENsYXNzdAAqZ3IuaWNjcy5pbXUuZW1zLnRyYW5zbGF0ZS5tb2RlbC5QdWxsU2Vuc29ydAAJY2xhc3NOYW1ldAAAdAANY29uZmlndXJhdGlvbnNxAH4AAj9AAAAAAAAMdwgAAAAQAAAABnQADl9jb250YWluZXJOYW1ldAALY2lwaGVyX25vZGV0AAhydWxlX2lkc3NxAH4AAAAAAAF3BAAAAAF0AAM1MDZ4dAAJYWdncmVnYXRldAAFY291bnR0AAhpbnRlcnZhbHQABjMwIHNlY3QABHR5cGV0AAV3YXp1aHQACmNvbXBvbmVudHNzcQB+AAAAAAABdwQAAAABdAALY2lwaGVyX25vZGV4eABxAH4AEnNxAH4AAj9AAAAAAAAMdwgAAAAQAAAAA3EAfgAFdAAoZ3IuaWNjcy5pbXUuZW1zLnRyYW5zbGF0ZS5tb2RlbC5JbnRlcnZhbHQABnBlcmlvZHNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAA8dAAEdW5pdHQAB1NFQ09ORFN4AHQABG5hbWV0ACBjaXBoZXJfbm9kZV9hZ2VudF9zdG9wcGVkX1NFTlNPUnQACnB1bGxTZW5zb3JzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABdAAEcHVzaHNxAH4AJAB0AApwdXNoU2Vuc29ycQB+ACd4AHNxAH4AAj9AAAAAAAAMdwgAAAAQAAAACHEAfgAFdAAqZ3IuaWNjcy5pbXUuZW1zLnRyYW5zbGF0ZS5tb2RlbC5QdWxsU2Vuc29ycQB+AAdxAH4ACHEAfgAJc3EAfgACP0AAAAAAAAx3CAAAABAAAAAGcQB+AAt0AAtjaXBoZXJfbm9kZXEAfgANc3EAfgAAAAAAAncEAAAAAnQAAzU1MHQAAzU1M3hxAH4AEHQABWNvdW50cQB+ABJ0AAYzMCBzZWNxAH4AFHQABXdhenVocQB+ABZzcQB+AAAAAAABdwQAAAABdAALY2lwaGVyX25vZGV4eABxAH4AEnNxAH4AAj9AAAAAAAAMdwgAAAAQAAAAA3EAfgAFdAAoZ3IuaWNjcy5pbXUuZW1zLnRyYW5zbGF0ZS5tb2RlbC5JbnRlcnZhbHEAfgAbcQB+AB5xAH4AH3QAB1NFQ09ORFN4AHEAfgAhdAAhY2lwaGVyX25vZGVfZmlsZV9pbnRlZ3JpdHlfU0VOU09ScQB+ACNxAH4AJXEAfgAmcQB+ACdxAH4AKHEAfgAneABzcQB+AAI/QAAAAAAADHcIAAAAEAAAAAhxAH4ABXQAKmdyLmljY3MuaW11LmVtcy50cmFuc2xhdGUubW9kZWwuUHVsbFNlbnNvcnEAfgAHcQB+AAhxAH4ACXNxAH4AAj9AAAAAAAAMdwgAAAAQAAAABnEAfgALdAALY2lwaGVyX25vZGVxAH4ADXNxAH4AAAAAAAF3BAAAAAF0AAM1MTB4cQB+ABB0AAVjb3VudHEAfgASdAAGMzAgc2VjcQB+ABR0AAV3YXp1aHEAfgAWc3EAfgAAAAAAAXcEAAAAAXQAC2NpcGhlcl9ub2RleHgAcQB+ABJzcQB+AAI/QAAAAAAADHcIAAAAEAAAAANxAH4ABXQAKGdyLmljY3MuaW11LmVtcy50cmFuc2xhdGUubW9kZWwuSW50ZXJ2YWxxAH4AG3EAfgAecQB+AB90AAdTRUNPTkRTeABxAH4AIXQAJGNpcGhlcl9ub2RlX3Jvb3RjaGVja19hbm9tYWx5X1NFTlNPUnEAfgAjcQB+ACVxAH4AJnEAfgAncQB+AChxAH4AJ3gAc3EAfgACP0AAAAAAAAx3CAAAABAAAAAIcQB+AAV0ACpnci5pY2NzLmltdS5lbXMudHJhbnNsYXRlLm1vZGVsLlB1bGxTZW5zb3JxAH4AB3EAfgAIcQB+AAlzcQB+AAI/QAAAAAAADHcIAAAAEAAAAAZxAH4AC3QAC2NpcGhlcl9ub2RlcQB+AA1zcQB+AAAAAAACdwQAAAACdAAEMjkwMnQABDI5MDN4cQB+ABB0AAVjb3VudHEAfgASdAAGMzAgc2VjcQB+ABR0AAV3YXp1aHEAfgAWc3EAfgAAAAAAAXcEAAAAAXQAC2NpcGhlcl9ub2RleHgAcQB+ABJzcQB+AAI/QAAAAAAADHcIAAAAEAAAAANxAH4ABXQAKGdyLmljY3MuaW11LmVtcy50cmFuc2xhdGUubW9kZWwuSW50ZXJ2YWxxAH4AG3EAfgAecQB+AB90AAdTRUNPTkRTeABxAH4AIXQAImNpcGhlcl9ub2RlX3BhY2thZ2VfY2hhbmdlc19TRU5TT1JxAH4AI3EAfgAlcQB+ACZxAH4AJ3EAfgAocQB+ACd4AHg=";
        Object config = SerializationUtil.deserializeFromString(graph);

        WazuhExternalCollector collector = new WazuhExternalCollector(new WazuhExternalCollectorProperties(), null);
        collector.setConfiguration(config);
        collector.start();
    }

    @Override
    public synchronized void setConfiguration(Object config) {
        if (config instanceof List<?> sensorConfigList) {
            List<Map<String, Object>> newConfigurations = new ArrayList<>();
            for (Object o : sensorConfigList) {
                if (o instanceof Map<?, ?> m && m.keySet().stream().allMatch(k -> k instanceof String)) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> typed = (Map<String, Object>) m;
                    newConfigurations.add(typed);
                }
            }
            log.debug("WazuhCollector: setConfiguration: newConfigurations: {}", newConfigurations);

            configurations = newConfigurations;
            log.debug("WazuhCollector: setConfiguration: Collector configuration updated");

            // The external client is configured through a file, so no restart is needed here:
            // just regenerate the file (the client picks it up on its own restart/reload)
            if (started) writeConfigFile();
        } else
            log.warn("WazuhCollector: setConfiguration: Ignoring unsupported configuration object: {}", config);
    }

    @Override
    public synchronized void start() {
        if (started) return;
        started = true;
        if (configurations != null) writeConfigFile();
        log.debug("WazuhCollector: Started");
    }

    @Override
    public synchronized void stop() {
        if (!started) return;
        started = false;
        log.debug("WazuhCollector: Stopped");
    }

    public synchronized void activeGroupingChanged(String oldGrouping, String newGrouping) {
        log.debug("WazuhCollector: activeGroupingChanged: {} --> {}", oldGrouping, newGrouping);
    }

    // ------------------------------------------------------------------------------------------
    //  Config generation
    // ------------------------------------------------------------------------------------------

    private void writeConfigFile() {
        try {
            Map<String, Object> root = buildClientConfig();
            String yamlText = toYaml(root);

            if (yamlText.equals(lastWrittenContent)) {
                log.debug("WazuhCollector: writeConfigFile(): Configuration unchanged. Not rewriting file");
                return;
            }

            Path target = Path.of(properties.getConfigFile()).toAbsolutePath();
            writeAtomically(target, yamlText);
            lastWrittenContent = yamlText;
            log.info("WazuhCollector: Wazuh client configuration written to: {}", target);
        } catch (Exception e) {
            log.error("WazuhCollector: writeConfigFile(): EXCEPTION while writing Wazuh client configuration: ", e);
        }
    }

    /** Builds the complete client configuration as an ordered map (mirrors the client's config.yaml) */
    protected Map<String, Object> buildClientConfig() {
        String indexerHost = properties.getIndexerHost();
        String indexerUsername = properties.getIndexerUsername();
        String indexerPassword = properties.getIndexerPassword();
        String indexerIndex = properties.getIndexerIndex();
        boolean indexerVerifySSL = properties.isIndexerVerifySsl();

        String queryInitialSince = properties.getAlertsInitialSince();
        int queryMinimumLevel = Objects.requireNonNullElse(
                properties.getAlertsMinimumLevel(), properties.getDefaultMinLevel());
        int queryBatchSize = properties.getAlertsBatchSize();

        List<Map<String, Object>> metrics = new ArrayList<>();
        Map<Map<String, Object>, Map<String, Object>> pendingFilters = new IdentityHashMap<>();
        Set<String> keysToExtract = new LinkedHashSet<>(properties.getKeysToExtract());

        int sensorNum = 0;
        for (Map<String, Object> sensor : configurations == null ? List.<Map<String, Object>>of() : configurations) {
            sensorNum++;
            log.debug("WazuhCollector: Sensor-{}: sensor={}", sensorNum, sensor);

            // Push sensors are ignored (same as in the Netdata collector)
            if ("true".equalsIgnoreCase(get(sensor, "pushSensor", "false"))) {
                log.debug("WazuhCollector: Sensor-{}: It is a Push sensor. Ignoring", sensorNum);
                continue;
            }

            String name = get(sensor, "name", null);
            if (StringUtils.isBlank(name)) {
                log.warn("WazuhCollector: Sensor-{}: No destination found in sensor config: {}", sensorNum, sensor);
                continue;
            }

            if (!(sensor.get("configuration") instanceof Map<?, ?> cfgRaw)) {
                log.warn("WazuhCollector: Sensor-{}: No sensor configuration found in spec: {}", sensorNum, sensor);
                continue;
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> cfg = (Map<String, Object>) cfgRaw;

            if (cfg.get("indexer") instanceof Map<?, ?> indexerCfg) {
                Object o;
                indexerHost = (o = indexerCfg.get("host"))!=null ? o.toString() : null;
                indexerUsername = (o = indexerCfg.get("username"))!=null ? o.toString() : null;
                indexerPassword = (o = indexerCfg.get("password"))!=null ? o.toString() : null;
                indexerIndex = (o = indexerCfg.get("index"))!=null ? o.toString() : null;
                indexerVerifySSL = (o = indexerCfg.get("verify_ssl")) == null || ! "false".equalsIgnoreCase(o.toString());

                queryInitialSince = (o = indexerCfg.get("initial_since"))!=null ? o.toString() : null;
                queryMinimumLevel = (o = indexerCfg.get("minimum_level"))!=null ? Integer.parseInt(o.toString()) : queryMinimumLevel;
                queryBatchSize = (o = indexerCfg.get("batch_size"))!=null ? Integer.parseInt(o.toString()) : queryBatchSize;
            }

            Map<String, Object> metric = new LinkedHashMap<>();
            metric.put("topic", name.startsWith(TOPIC_PREFIX) ? name : TOPIC_PREFIX + name);
            metric.put("min_level", getInt(cfg, properties.getDefaultMinLevel(), "min_level", "min-level"));
            metric.put("max_level", getInt(cfg, properties.getDefaultMaxLevel(), "max_level", "max-level"));

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
            log.debug("WazuhCollector: Sensor-{}: metric={}", sensorNum, metric);
        }

        // Filters may only use keys listed in keys_to_extract, so any filter key that is not
        // already covered (exactly or by a wildcard such as 'additional_context.*') is added to it
        pendingFilters.forEach((metric, filters) -> {
            filters.keySet().forEach(k -> {
                if (!isExtractedKey(keysToExtract, k)) {
                    log.debug("WazuhCollector: Adding filter key '{}' of topic {} to keys_to_extract",
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
                    log.warn("WazuhCollector: Invalid integer for '{}': {}. Using default: {}", key, s, defaultValue);
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
            log.warn("WazuhCollector: Ignoring filter '{}' with unsupported value type: {}", key, value.getClass().getName());
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
            log.warn("WazuhCollector: Invalid interval specified: {}", o);
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