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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.broker.SslBrokerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.security.AuthenticationUser;
//import org.apache.activemq.security.JaasAuthenticationPlugin;
import org.apache.activemq.security.SimpleAuthenticationPlugin;
import org.apache.activemq.usage.MemoryUsage;
import org.apache.activemq.usage.SystemUsage;

import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Service;

 
@Service
@Configuration
@EnableJms
@Slf4j
public class BrokerConfig implements InitializingBean {
	
	@Autowired
	private BrokerCepProperties properties;
	
	private SimpleAuthenticationPlugin brokerAuthenticationPlugin;
	private BrokerPlugin brokerAuthorizationPlugin;
	private ArrayList<AuthenticationUser> userList;
	private String brokerLocalAdmin;
	private String brokerLocalAdminPassword;
	
	@Override
	public void afterPropertiesSet() {
		_initializeSecurity();
	}
	
	protected synchronized void _initializeSecurity() {
		log.debug("BrokerConfig._initializeSecurity(): Initializing broker security: initialize-authentication={}, initialize-authorization={}",
				properties.isAuthenticationEnabled(), properties.isAuthorizationEnabled());
		
		// initialize authentication
		if (properties.isAuthenticationEnabled()) {
			userList = new ArrayList<>();
			
			// initialize local admin credentials
			brokerLocalAdmin = "local-admin-"+RandomStringUtils.randomAlphanumeric(10);
			brokerLocalAdminPassword = RandomStringUtils.randomAlphanumeric(20);
			userList.add( new AuthenticationUser(brokerLocalAdmin, brokerLocalAdminPassword, "") );
			log.debug("BrokerConfig._initializeSecurity(): Initialized local admin: {} / {}", brokerLocalAdmin, brokerLocalAdminPassword);
			
			// initialize broker user credentials
			String brokerUsername = "user-"+RandomStringUtils.randomAlphanumeric(10);
			String brokerPassword = RandomStringUtils.randomAlphanumeric(20);
			userList.add( new AuthenticationUser(brokerUsername, brokerPassword, "") );
			log.debug("BrokerConfig._initializeSecurity(): Initialized broker user: {} / {}", brokerUsername, brokerPassword);
			
			// initialize additional user credentials from configuration
			for (String extraUserCred : properties.getAdditionalBrokerCredentials().split(",")) {
				String[] cred = extraUserCred.split("/",2);
				String username = cred[0].trim();
				String password = cred.length>1 ? cred[1].trim() : "";
				userList.add( new AuthenticationUser(username, password, "") );
				log.debug("BrokerConfig._initializeSecurity(): Initialized additional broker user from configuration: {} / {}", username, password);
			}
			
			// initialize Broker authentication plugin
			SimpleAuthenticationPlugin sap = new SimpleAuthenticationPlugin();		//new JaasAuthenticationPlugin()
			sap.setAnonymousAccessAllowed(false);
			sap.setUsers( userList );
			brokerAuthenticationPlugin = sap;
			log.debug("BrokerConfig._initializeSecurity(): Initialized broker authentication plugin: anonymous-access={}, user-credentials={}", sap.isAnonymousAccessAllowed(), sap.getUserPasswords());
		}
		
		// initialize authorization
		if (properties.isAuthorizationEnabled()) {
			//XXX:TODO: ++++++++++++++++++++
			brokerAuthorizationPlugin = null;
			//log.debug("BrokerConfig._initializeSecurity(): Initialized broker authorization plugin");
		}
	}
	
	public String getBrokerName() {
		log.trace("BrokerConfig.getBrokerName(): broker-name: {}", properties.getBrokerName());
		return properties.getBrokerName();
	}
	
	public String getBrokerUrl() {
		log.trace("BrokerConfig.getBrokerUrl(): broker-url: {}", properties.getBrokerUrl());
		return properties.getBrokerUrl();
	}
	
	public String getBrokerLocalAdmin() { return brokerLocalAdmin; }
	
	public String getBrokerLocalAdminPassword() { return brokerLocalAdminPassword; }
	
	public String getBrokerUsername() { return userList!=null ? userList.get(1).getUsername() : null; }
	
	public String getBrokerPassword() { return userList!=null ? userList.get(1).getPassword() : null; }
	
	public void setBrokerUsername(String s) {
		if (userList!=null) {
			userList.get(1).setUsername(s);
			brokerAuthenticationPlugin.setUsers(userList);
		}
		log.debug("BrokerConfig.setBrokerUsername(): username={}", s);
	}
	
	public void setBrokerPassword(String s) {
		if (userList!=null) {
			userList.get(1).setPassword(s);
			brokerAuthenticationPlugin.setUsers(userList);
		}
		log.debug("BrokerConfig.setBrokerPassword(): password=****");
	}
	
	public BrokerPlugin getBrokerAuthenticationPlugin() {
		return brokerAuthenticationPlugin;
	}
	
	public BrokerPlugin getBrokerAuthorizationPlugin() {
		return brokerAuthenticationPlugin;
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
		
		// Set authentication and authorization plugins
		List<BrokerPlugin> plugins = new ArrayList<>();
		if (properties.isAuthenticationEnabled()) plugins.add( getBrokerAuthenticationPlugin() );
		if (properties.isAuthorizationEnabled()) plugins.add( getBrokerAuthorizationPlugin() );
		if (plugins.size()>0) {
			brokerService.setPlugins( plugins.stream().toArray(BrokerPlugin[]::new) );
		}
		
		// Configure broker service instance
		log.warn("BrokerConfig: Broker configuration: persistence={}, use-jmx={}, advisory-support={}, use-shutdown-hook={}",
				properties.isBrokerPersistenceEnabled(), properties.isBrokerUsingJmx(), properties.isBrokerAdvisorySupportEnabled(), properties.isBrokerUsingShutdownHook());
		brokerService.setPersistent( properties.isBrokerPersistenceEnabled() );
		brokerService.setUseJmx( properties.isBrokerUsingJmx() );
		brokerService.setUseShutdownHook( properties.isBrokerUsingShutdownHook() );
		brokerService.setAdvisorySupport( properties.isBrokerAdvisorySupportEnabled() );
		
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
		/*if (getBrokerUsername()!=null) {
			connectionFactory.setUserName(getBrokerUsername());
			connectionFactory.setPassword(getBrokerPassword());
		}*/
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