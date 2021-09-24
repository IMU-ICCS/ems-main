/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.upperware.metasolver.properties;

import eu.melodic.models.interfaces.metaSolver.ConstraintProblemEnhancementResponse.DesignatedSolverType;
import eu.melodic.upperware.metasolver.metricvalue.TopicType;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Data
@Validated
@Configuration
@ConfigurationProperties
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.upperware.metaSolver.properties")
public class MetaSolverProperties {

    @NotNull
    private Esb esb;
    @NotNull
    private Pubsub pubsub;
    @NotNull
    private double utilityThresholdFactor;
    private DesignatedSolverType defaultSolver = DesignatedSolverType.CPSOLVER;
    private String emsUrl;

    private boolean cpModelUpdateEnabled = true;
    private long cpModelUpdateInterval = 30000L;

    private boolean predictionMonitoringEnabled = false;
    private String predictionTopicFormatter = "prediction.%s";
    private Pattern predictionTopicPattern = Pattern.compile("^prediction\\.(.+)$");
    private long predictionRegistryCleanupRate = -1L;
    private boolean predictionRegistryCleanupAfterScaleEvent = true;

    @Range(min=0, max=1)
    private double reconfigurationProbabilityThreshold = 0.5;
    @Min(0)
    private long reconfigurationBlockingPeriod = 0;     // reconfiguration cool down period
    private boolean preventConcurrentReconfigurations = false;
    private long preventConcurrentReconfigurationsTimeout = -1L;

    private DebugEvent debugEvents = new DebugEvent();

    // --------------------------------------------------------------

    @Data
    public static class Esb {
        @NotBlank
        private String url;
        private boolean enabled = true;
    }

    @Data
    public static class Pubsub {
        private boolean on;
        private List<Topic> startupTopics;
        private List<Topic> commonTopics;

        @Data
        public static class Topic {
            @NotBlank
            private String name;
            @NotBlank
            private String url;
            private String clientId;
            private TopicType type;
        }
    }

    @Data
    public static class DebugEvent {
        private boolean enabled = false;
        private String topicName = "metasolver_debug";
        private String url;
        private String username;
        @ToString.Exclude
        private String password;
        @ToString.Exclude
        private String certificate;
        private String clientId;
    }
}
