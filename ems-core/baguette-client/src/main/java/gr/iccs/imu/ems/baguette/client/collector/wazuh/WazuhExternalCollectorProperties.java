/*
 * Copyright (C) 2017-2026 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package gr.iccs.imu.ems.baguette.client.collector.wazuh;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Settings for {@link WazuhExternalCollector}. These are the values that are NOT part of
 * the per-sensor configuration (Wazuh Indexer connection, EMS broker connection, defaults).
 * <p>
 * Example (application.yml / ems properties):
 * <pre>
 * collector:
 *   wazuh:
 *     config-file: /opt/wazuh-client/config.yaml
 *     indexer-host: https://wazuh-indexer:9200/
 * </pre>
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "collector.wazuh")
public class WazuhExternalCollectorProperties {

    /** Where the external Wazuh client configuration file is written */
    private String configFile = "wazuh_client_config.yaml";

    // --- Wazuh Indexer client settings
    private String indexerHost = "https://wazuh-indexer:9200/";
    private String indexerUsername = "admin";
    private String indexerPassword = "admin";
    private String indexerIndex = "wazuh-alerts-*";
    private boolean indexerVerifySsl = false;

    // --- Alerts settings
    /** ISO-8601 timestamp. If blank, the time of the first config generation is used */
    private String alertsInitialSince;
    /** If null, the lowest 'min_level' among the metrics is used */
    private Integer alertsMinimumLevel;
    private int alertsBatchSize = 500;

    // --- EMS broker client settings (used by the external client to publish events)
    private String brokerUrl = "host.docker.internal";
    private int brokerPort = 61610;
    private String brokerUsername = "";
    private String brokerPassword = "";

    // --- Processor settings
    private int processorIntervalSeconds = 20;
    private String processorCheckpointFile = "wazuh_checkpoint.json";

    /** Default minimum/maximum alert levels for metrics that don't specify them */
    private int defaultMinLevel = 7;
    private int defaultMaxLevel = 999;

    /** Keys extracted from each alert, in addition to any sensor-specific 'keys-to-extract' */
    private List<String> keysToExtract = List.of(
            "rule.id", "rule.description", "rule.groups",
            "agent.id", "agent.name", "agent.ip",
            "event.location", "event.full_log",
            "additional_context.*",
            "mitre.tactic", "mitre.technique", "mitre.subtechnique",
            "source.io", "source.user",
            "destination.ip", "destination.user"
    );
}
