/*
 * Copyright (C) 2017-2026 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package gr.iccs.imu.ems.k8s;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.TaskScheduler;

import static gr.iccs.imu.ems.api.EmsConstant.EMS_PROPERTIES_PREFIX;

/**
 * Configure default Kubernetes beans if not provided
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class K8sPodWatcherConfig implements InitializingBean {
    private final Environment environment;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("K8sPodWatcherConfig: Cloud Platform: {}", CloudPlatform.getActive(environment));
    }

    @Bean
    @ConditionalOnMissingBean(K8sEmsClientDeploymentPostProcessingPlugin.class)
    @ConditionalOnProperty(prefix = EMS_PROPERTIES_PREFIX + "k8s", name = "enabled", havingValue = "true", matchIfMissing = true)
    public K8sEmsClientDeploymentPostProcessingPlugin k8sEmsClientDeploymentPostProcessingPlugin(
            K8sPodWatcherProperties properties, ApplicationContext applicationContext)
    {
        return new K8sEmsClientDeploymentPostProcessingPlugin(properties, applicationContext);
    }

    @Bean
    @ConditionalOnMissingBean(K8sPodWatcher.class)
    @ConditionalOnProperty(prefix = EMS_PROPERTIES_PREFIX + "k8s", name = "enabled", havingValue = "true", matchIfMissing = true)
    public K8sPodWatcher k8sPodWatcher(K8sPodWatcherProperties properties, TaskScheduler taskScheduler) {
        return new K8sPodWatcher(properties, taskScheduler);
    }
}
