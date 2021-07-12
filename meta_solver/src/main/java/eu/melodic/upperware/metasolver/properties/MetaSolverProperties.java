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
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.regex.Pattern;

@Getter
@Setter
@ToString
@Validated
@Configuration
@ConfigurationProperties
@Slf4j
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.upperware.metaSolver.properties")
public class MetaSolverProperties {

    @Valid
    @NotNull
    private Esb esb;
    @Valid
    @NotNull
    private Pubsub pubsub;
    @Valid
    @NotNull
    private double utilityThresholdFactor;
    @Valid
    private DesignatedSolverType defaultSolver = DesignatedSolverType.CPSOLVER;
    @Valid
    private String emsUrl;
    @Valid
    private long cpModelUpdateInterval = 30000L;

    private boolean predictionMonitoringEnabled = false;
    private String predictionTopicFormatter = "prediction.%s";
    private Pattern predictionTopicPattern = Pattern.compile("^prediction\\.(.+)$");
    private long predictionRegistryCleanupRate = -1L;
    private boolean predictionRegistryCleanupAfterScaleEvent = true;
    private double reconfigurationProbabilityThreshold = 0.5;

    // --------------------------------------------------------------

    private static boolean booleanValue(String str) {
        return booleanValue(str, false);
    }

    private static boolean booleanValue(String str, boolean defVal) {
        if (StringUtils.isBlank(str)) return defVal;
        return BooleanUtils.toBoolean(str);
    }

    // --------------------------------------------------------------

    @Getter
    @Setter
    public static class Esb {
        @NotBlank
        private String url;
        private boolean enabled = true;
    }

    @Getter
    @Setter
    @ToString
    public static class Pubsub {
        private String on;

        private List<Topic> topics;

        public boolean isOn() {
            return booleanValue(on);
        }

        @Getter
        @Setter
        @ToString
        public static class Topic {
            @NotBlank
            private String name;
            @NotBlank
            private String url;
            private String clientId;
            private TopicType type;
        }
    }
}
