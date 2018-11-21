/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokercep.broker;
 
import eu.melodic.event.brokercep.properties.BrokerCepProperties;

import java.net.URI;
import java.util.Arrays;
import javax.jms.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.command.ActiveMQQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

@Configuration
@EnableJms
@Slf4j
public class BrokerConfig {
	
	@Autowired
	private BrokerCepProperties properties;
	
	public String getBrokerName() {
		log.trace("BrokerConfig.getBrokerName(): broker-name: {}", properties.getBrokerName());
		return properties.getBrokerName();
	}
	
	public String getBrokerUrl() {
		log.trace("BrokerConfig.getBrokerUrl(): broker-url: {}", properties.getBrokerUrl());
		return properties.getBrokerUrl();
	}
	
	/**
	 * Creates an embedded JMS server
	 *
	 * @return
	 * @throws Exception
	 */
	@Bean//(initMethod = "start", destroyMethod = "stop")
	public BrokerService createBrokerService() throws Exception {
		// create new broker service instance
        log.info("BrokerConfig: Creating new Broker Service instance: url={}", getBrokerUrl());
		BrokerService brokerService = new BrokerService();
		
		// configure broker service instance
		brokerService.setPersistent(false);
		brokerService.setUseJmx(true);
		brokerService.addConnector( getBrokerUrl() );
		brokerService.setBrokerName( getBrokerName() );
		brokerService.setUseShutdownHook(false);
		brokerService.setAdvisorySupport(true);
		
		// change the JMX connector port
		if (properties!=null && properties.getConnectorPort()>0) {
			if (brokerService!=null) {
				if (brokerService.getManagementContext()!=null) {
					log.info("BrokerConfig.createBrokerService(): Setting connector port to: {}", properties.getConnectorPort());
					brokerService.getManagementContext().setConnectorPort( properties.getConnectorPort() );
				}
			}
		}
		
		// start broker service instance
		brokerService.start();
		
        return brokerService;
	}
	
	
	/**
	 * Creates an new JMS client connection factory
	 *
	 * @return
	 */
	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL( getBrokerUrl() );
		//connectionFactory.setUserName(username);
		//connectionFactory.setPassword(password);
		//connectionFactory.setTrustedPackages(Arrays.asList("eu.melodic.event"));
		connectionFactory.setTrustAllPackages(true);
		connectionFactory.setWatchTopicAdvisories(true);
		return connectionFactory;
	}
	
	/**
	 * Creates a new JMS template for client-side connections
	 */
	@Bean
	public JmsTemplate jmsTemplate(){
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connectionFactory());
		return template;
	}
}