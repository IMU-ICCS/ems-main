/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.metasolver;

import eu.melodic.upperware.metasolver.metricvalue.MetricValueMonitorBean;
import eu.melodic.upperware.metasolver.properties.MetaSolverProperties;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;
 
@Component
@Slf4j
public class MetaSolverApplicationListener implements ApplicationListener<ApplicationEvent>, ApplicationContextAware {
 
    private MetricValueMonitorBean metricValueMonitorBean;
    private MetaSolverProperties properties;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
		this.metricValueMonitorBean = (MetricValueMonitorBean) applicationContext.getBean(MetricValueMonitorBean.class);
		this.properties = (MetaSolverProperties) applicationContext.getBean(MetaSolverProperties.class);
    }
 
    /*@Autowired
    public void setMetricValueMonitorBean(MetricValueMonitorBean bean) {
        this.metricValueMonitorBean = bean;
    }*/
 
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
		
		log.trace("** Application Event Received : "+event.getClass().getName());
		
		if (event instanceof org.springframework.context.event.ContextRefreshedEvent) {
			log.debug("** Application Event Received : Context Refreshed");
			properties.loadCdoConfig();
			((eu.melodic.upperware.metasolver.util.CpModelHelper) applicationContext.getBean(eu.melodic.upperware.metasolver.util.CpModelHelper.class)).connect();
			metricValueMonitorBean.subscribe();
		} else
		/*if (event instanceof org.springframework.boot.context.event.ApplicationReadyEvent) {
			log.debug("** Application Event Received : Application Ready");
		} else*/
		if (event instanceof org.springframework.context.event.ContextClosedEvent) {
			log.debug("** Application Event Received : Context Closed");
			metricValueMonitorBean.unsubscribe();
		} else
		{
			log.trace("** Application Event Received : Other...");
		}
    }
}