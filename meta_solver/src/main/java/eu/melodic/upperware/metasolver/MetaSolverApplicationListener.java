/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.upperware.metasolver;

import eu.melodic.upperware.metasolver.metricvalue.MetricValueMonitorBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MetaSolverApplicationListener implements ApplicationListener<ApplicationEvent>, ApplicationContextAware {

    private MetricValueMonitorBean metricValueMonitorBean;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.metricValueMonitorBean = applicationContext.getBean(MetricValueMonitorBean.class);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        log.trace("** Application Event Received : " + event.getClass().getName());
		
		/*if (event instanceof org.springframework.context.event.ContextRefreshedEvent) {
			log.debug("** Application Event Received : Context Refreshed");
			metricValueMonitorBean.subscribe();
		} else*/
        if (event instanceof org.springframework.context.event.ContextClosedEvent) {
            log.debug("** Application Event Received : Context Closed");
            metricValueMonitorBean.unsubscribe();
        } else {
            log.trace("** Application Event Received : Other...");
        }
    }
}