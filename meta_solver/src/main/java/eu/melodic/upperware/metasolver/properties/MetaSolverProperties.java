/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.metasolver.properties;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.util.List;

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
    private String emsUrl;
    @Valid
    private History history;

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

    // --------------------------------------------------------------

    @Getter
    @Setter
    public static class History {
        private boolean enabled = false;

        private String basePath;
        private String actionsBasePath;
        private String componentsBasePath;
        private String transitionsBasePath;

        private String metasolverPath;
        private String cpSolutionProducedPath;
        private String cpSolutionFailedPath;
    }
}
