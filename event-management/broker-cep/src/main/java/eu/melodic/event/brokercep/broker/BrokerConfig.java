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
import java.util.Optional;
import javax.jms.ConnectionFactory;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;
import lombok.extern.slf4j.Slf4j;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSslConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.broker.SslBrokerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.usage.MemoryUsage;
import org.apache.activemq.usage.SystemUsage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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
		// Create new broker service instance
		String brokerUrl = getBrokerUrl();
        log.info("BrokerConfig: Creating new Broker Service instance: url={}", brokerUrl);
		
		BrokerService brokerService;
		if (brokerUrl.startsWith("ssl")) {
			brokerService = _createSslBrokerService();
		} else {
			brokerService = new BrokerService();
			brokerService.addConnector( brokerUrl );
		}
		brokerService.setBrokerName( getBrokerName() );
		
		// Configure broker service instance
		brokerService.setPersistent(false);
		brokerService.setUseJmx(true);
		brokerService.setUseShutdownHook(false);
		brokerService.setAdvisorySupport(true);
		
		// Change the JMX connector port
		if (properties!=null && properties.getConnectorPort()>0) {
			if (brokerService!=null) {
				if (brokerService.getManagementContext()!=null) {
					log.info("BrokerConfig.createBrokerService(): Setting connector port to: {}", properties.getConnectorPort());
					brokerService.getManagementContext().setConnectorPort( properties.getConnectorPort() );
				}
			}
		}
		
		// Set memory limit in order not to use too much memory
		int memHeapPercent = properties.getMemoryJvmHeapPercentage();
		long memSize= properties.getMemorySize();
		if (memHeapPercent>0 || memSize>0) {
			final MemoryUsage memoryUsage = new MemoryUsage();
			if (memHeapPercent>0) {
				memoryUsage.setPercentOfJvmHeap( memHeapPercent );
				log.info("BrokerConfig: Limiting Broker Service instance memory usage to {}% of JVM heap size", memHeapPercent);
			} else {
				memoryUsage.setUsage( memSize );
				log.info("BrokerConfig: Limiting Broker Service instance memory usage to {} bytes", memSize);
			}
			final SystemUsage systemUsage = new SystemUsage();
			systemUsage.setMemoryUsage(memoryUsage);
			brokerService.setSystemUsage(systemUsage);
		}
		
		// start broker service instance
		brokerService.start();
		
        return brokerService;
	}
	
	private BrokerService _createSslBrokerService() throws Exception {
		// Create new SSL broker service instance
		SslBrokerService brokerService = new SslBrokerService();
		
		// Add ActiveMQ SSL connector using configured keystore and truststore
		final KeyManager[] keystore = readKeystore();
		final TrustManager[] truststore = readTruststore();
		String props = Optional.ofNullable(properties.getBrokerUrlProperties()).orElse("").trim();
		if (! props.isEmpty() && ! props.startsWith("?")) props = "?"+props;
		brokerService.addSslConnector( properties.getBrokerUrl() + props, keystore, truststore, null );
		
		return brokerService;
	}
	
    private KeyManager[] readKeystore() throws Exception {
		final KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		final KeyStore keystore = KeyStore.getInstance( properties.getKeystoreType() );

		//final Resource keystoreResource = new ClassPathResource( properties.getKeystoreFile() );
		final FileSystemResource keystoreResource = new FileSystemResource( properties.getKeystoreFile() );
		keystore.load( keystoreResource.getInputStream(), properties.getKeystorePassword().toCharArray() );
		keyManagerFactory.init( keystore, properties.getKeystorePassword().toCharArray() );
		final KeyManager[] keystoreManagers = keyManagerFactory.getKeyManagers();
		return keystoreManagers;
    }
 
    private TrustManager[] readTruststore() throws Exception {
		final KeyStore truststore = KeyStore.getInstance( properties.getTruststoreType() );

		//final Resource truststoreResource = new ClassPathResource( properties.getTruststoreFile() );
		final FileSystemResource truststoreResource = new FileSystemResource( properties.getTruststoreFile() );
		truststore.load( truststoreResource.getInputStream(), properties.getTruststorePassword().toCharArray() );
		final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance( TrustManagerFactory.getDefaultAlgorithm() );
		trustManagerFactory.init( truststore );
		final TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
		return trustManagers;
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
		//connectionFactory.setUserName(username);
		//connectionFactory.setPassword(password);
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