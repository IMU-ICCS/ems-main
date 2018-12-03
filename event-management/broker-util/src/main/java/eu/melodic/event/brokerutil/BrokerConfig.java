/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokerutil;
 
import eu.melodic.event.brokerutil.properties.BrokerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSslConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;


@Configuration
@EnableJms
@Slf4j
public class BrokerConfig {
	
	@Autowired
	private BrokerProperties properties;
	
	public String getBrokerName() {
		log.trace("BrokerConfig.getBrokerName(): broker-name: {}", properties.getBrokerName());
		return properties.getBrokerName();
	}
	
	public String getBrokerUrl() {
		log.trace("BrokerConfig.getBrokerUrl(): broker-url: {}", properties.getBrokerUrl());
		return properties.getBrokerUrl();
	}
	
	/**
	 * Creates an new JMS client connection factory
	 *
	 * @return
	 */
	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		// Create connection factory based on Broker URL scheme
		final ActiveMQConnectionFactory connectionFactory;
		String brokerUrl = properties.getBrokerUrl();
		if (brokerUrl.startsWith("ssl")) {
			log.info("BrokerConfig: Creating new SSL connection factory instance: url={}", brokerUrl);
			final ActiveMQSslConnectionFactory sslConnectionFactory = new ActiveMQSslConnectionFactory(brokerUrl);
			try {
				sslConnectionFactory.setTrustStore( properties.getTruststoreFile() );
				sslConnectionFactory.setTrustStoreType( properties.getTruststoreType() );
				sslConnectionFactory.setTrustStorePassword( properties.getTruststorePassword() );
				sslConnectionFactory.setKeyStore( properties.getKeystoreFile() );
				sslConnectionFactory.setKeyStoreType( properties.getKeystoreType() );
				sslConnectionFactory.setKeyStorePassword( properties.getKeystorePassword() );
				//sslConnectionFactory.setKeyStoreKeyPassword( properties........ );

				connectionFactory = sslConnectionFactory;
			} catch (final Exception theException) {
				throw new Error(theException);
			}
		} else {
			log.info("BrokerConfig: Creating new non-SSL connection factory instance: url={}", brokerUrl);
			connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
		}
		
		// Other connection factory settings
		//connectionFactory.setSendTimeout(....5000L);
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