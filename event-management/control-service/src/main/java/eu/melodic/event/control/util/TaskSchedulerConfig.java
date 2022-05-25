/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.util;

import eu.melodic.event.control.properties.ControlServiceProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class TaskSchedulerConfig {
    private final ControlServiceProperties properties;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        int threadPoolSize = properties.getTaskScheduler().getThreadPoolSize();
        log.info("TaskSchedulerConfig: TaskScheduler thread pool size: {}", threadPoolSize);
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(threadPoolSize);
        return threadPoolTaskScheduler;
    }
}
