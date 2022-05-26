/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.properties;

import eu.melodic.event.control.webconf.WebMvcConfig;
import eu.melodic.event.util.KeystoreAndCertificateProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import java.util.Arrays;
import java.util.List;

import static eu.melodic.event.util.EmsConstant.EMS_PROPERTIES_PREFIX;

@Slf4j
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = EMS_PROPERTIES_PREFIX + "control")
@PropertySource(value = {
        "file:${MELODIC_CONFIG_DIR}/ems-server.yml",
        "file:${MELODIC_CONFIG_DIR}/ems-server.properties",
        "file:${MELODIC_CONFIG_DIR}/ems.yml",
        "file:${MELODIC_CONFIG_DIR}/ems.properties"
}, ignoreResourceNotFound = true)
public class ControlServiceProperties {
    public enum IpSetting {
        DEFAULT_IP,
        PUBLIC_IP
    }

    public enum ExecutionWare {
        CLOUDIATOR, PROACTIVE
    }

    private boolean printBuildInfo;

    private IpSetting ipSetting = IpSetting.PUBLIC_IP;
    private ExecutionWare executionware = ExecutionWare.PROACTIVE;

    private String upperwareGrouping;
    private String metasolverConfigurationUrl;
    private String esbUrl;

    private Preload preload = new Preload();

    private boolean skipTranslation;
    private boolean skipMvvRetrieve;
    private boolean skipBrokerCep;
    private boolean skipBaguette;
    private boolean skipCollectors;
    private boolean skipMetasolver;
    private boolean skipEsbNotification;

    private String tcLoadFile;
    private String tcSaveFile;

    private boolean exitAllowed;
    @Min(1)
    private long exitGracePeriod = 10;
    private int exitCode = 0;

    // control.ssl.** settings
    private KeystoreAndCertificateProperties ssl;

    private TaskSchedulerProperties taskScheduler = new TaskSchedulerProperties();
    private AuthorizationProperties authorization = new AuthorizationProperties();

    @Data
    public static class Preload {
        private String camelModel;
        private String cpModel;
    }

    @Data
    public static class TaskSchedulerProperties {
        @Min(1)
        private int threadPoolSize = 2;
    }

    @Data
    public static class AuthorizationProperties {
        private boolean enabled = true;
        private List<String> pathsProtected = Arrays.asList(WebMvcConfig.DEFAULT_PATHS_PROTECTED);
        private List<String> pathsExcluded = Arrays.asList(WebMvcConfig.DEFAULT_PATHS_EXCLUDED);
    }
}
