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
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSslConnectionFactory;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.SslBrokerService;
import org.apache.activemq.security.*;
import org.apache.activemq.usage.MemoryUsage;
import org.apache.activemq.usage.SystemUsage;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;
import java.util.*;

//import org.apache.activemq.security.JaasAuthenticationPlugin;


@Service
@Configuration
@EnableJms
@Slf4j
public class BrokerConfig implements InitializingBean {

    private final static int LOCAL_ADMIN_INDEX = 0;
    private final static int LOCAL_USER_INDEX = 1;
    private final static String LOCAL_ADMIN_PREFIX = "admin-local-";
    private final static String LOCAL_USER_PREFIX = "user-local-";
    private final static int USERNAME_RANDOM_PART_LENGTH = 10;
    private final static int PASSWORD_LENGTH = 20;

    @Autowired
    private BrokerCepProperties properties;

    private SimpleAuthenticationPlugin brokerAuthenticationPlugin;
    private SimpleBrokerAuthorizationPlugin brokerAuthorizationPlugin;
    private ArrayList<AuthenticationUser> userList;
    private String brokerLocalAdmin;
    private String brokerLocalAdminPassword;
    private String brokerUsername;
    private String brokerPassword;

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
            brokerLocalAdmin = LOCAL_ADMIN_PREFIX + RandomStringUtils.randomAlphanumeric(USERNAME_RANDOM_PART_LENGTH);
            brokerLocalAdminPassword = RandomStringUtils.randomAlphanumeric(PASSWORD_LENGTH);
            userList.add(new AuthenticationUser(brokerLocalAdmin, brokerLocalAdminPassword, SimpleBrokerAuthorizationPlugin.ADMIN_GROUP));
            log.debug("BrokerConfig._initializeSecurity(): Initialized local admin: {} / {}", brokerLocalAdmin, brokerLocalAdminPassword);

            // initialize broker user credentials
            brokerUsername = LOCAL_USER_PREFIX+ RandomStringUtils.randomAlphanumeric(USERNAME_RANDOM_PART_LENGTH);
            brokerPassword = RandomStringUtils.randomAlphanumeric(PASSWORD_LENGTH);
            userList.add(new AuthenticationUser(brokerUsername, brokerPassword, SimpleBrokerAuthorizationPlugin.RO_USER_GROUP));
            log.debug("BrokerConfig._initializeSecurity(): Initialized broker user: {} / {}", brokerUsername, brokerPassword);

            // initialize additional user credentials from configuration
            for (String extraUserCred : properties.getAdditionalBrokerCredentials().split(",")) {
                String[] cred = extraUserCred.split("/", 2);
                String username = cred[0].trim();
                String password = cred.length > 1 ? cred[1].trim() : "";
                userList.add(new AuthenticationUser(username, password, SimpleBrokerAuthorizationPlugin.RW_USER_GROUP));
                log.debug("BrokerConfig._initializeSecurity(): Initialized additional broker user from configuration: {} / {}", username, password);
            }

            // initialize Broker authentication plugin
            SimpleAuthenticationPlugin sap = new SimpleAuthenticationPlugin();        //new JaasAuthenticationPlugin()
            sap.setAnonymousAccessAllowed(false);
            sap.setUsers(userList);
            brokerAuthenticationPlugin = sap;
            log.debug("BrokerConfig._initializeSecurity(): Initialized broker authentication plugin: anonymous-access={}, user-credentials={}", sap.isAnonymousAccessAllowed(), sap.getUserPasswords());
        }

        // initialize authorization (requires authentication being enabled)
        if (properties.isAuthorizationEnabled()) {
            if (properties.isAuthenticationEnabled()) {
                // initialize Broker authorization plugin
                brokerAuthorizationPlugin = new SimpleBrokerAuthorizationPlugin();
                log.debug("BrokerConfig._initializeSecurity(): Initialized broker authorization plugin");
            } else {
                log.error("BrokerConfig._initializeSecurity(): Authorization will not be configured because authentication is not enabled");
            }
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

    public String getBrokerLocalAdminUsername() {
        return brokerLocalAdmin;
    }

    public String getBrokerLocalAdminPassword() {
        return brokerLocalAdminPassword;
    }

    public String getBrokerLocalUserUsername() {
        return brokerUsername;
    }

    public String getBrokerLocalUserPassword() {
        return brokerPassword;
    }

    public void setBrokerUsername(String s) {
        if (userList != null) {
            brokerUsername = s;
            userList.get(LOCAL_USER_INDEX).setUsername(s);     // 'userList' contains at least 2 items or is null (see '_initializeSecurity()' method)
            brokerAuthenticationPlugin.setUsers(userList);
        }
        log.debug("BrokerConfig.setBrokerUsername(): username={}", s);
    }

    public void setBrokerPassword(String s) {
        if (userList != null) {
            brokerPassword = s;
            userList.get(LOCAL_USER_INDEX).setPassword(s);
            brokerAuthenticationPlugin.setUsers(userList);
        }
        log.debug("BrokerConfig.setBrokerPassword(): password={}", s);
    }

    public BrokerPlugin getBrokerAuthenticationPlugin() {
        return brokerAuthenticationPlugin;
    }

    public BrokerPlugin getBrokerAuthorizationPlugin() {
        return brokerAuthorizationPlugin;
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
            brokerService.addConnector(brokerUrl);
        }
        brokerService.setBrokerName(getBrokerName());

        // Set authentication and authorization plugins
        List<BrokerPlugin> plugins = new ArrayList<>();
        if (getBrokerAuthenticationPlugin()!=null) plugins.add(getBrokerAuthenticationPlugin());
        if (getBrokerAuthorizationPlugin()!=null) plugins.add(getBrokerAuthorizationPlugin());
        if (plugins.size() > 0) {
            brokerService.setPlugins(plugins.toArray(new BrokerPlugin[0]));
        }

        // Configure broker service instance
        log.warn("BrokerConfig: Broker configuration: persistence={}, use-jmx={}, advisory-support={}, use-shutdown-hook={}",
                properties.isBrokerPersistenceEnabled(), properties.isBrokerUsingJmx(), properties.isBrokerAdvisorySupportEnabled(), properties.isBrokerUsingShutdownHook());
        brokerService.setPersistent(properties.isBrokerPersistenceEnabled());
        brokerService.setUseJmx(properties.isBrokerUsingJmx());
        brokerService.setUseShutdownHook(properties.isBrokerUsingShutdownHook());
        brokerService.setAdvisorySupport(properties.isBrokerAdvisorySupportEnabled());

        brokerService.setPopulateJMSXUserID(properties.isPopulateJmsxUserId());
        brokerService.setEnableStatistics(properties.isEnableStatistics());

        // Change the JMX connector port
        if (properties != null && properties.getConnectorPort() > 0) {
            if (brokerService.getManagementContext() != null) {
                log.info("BrokerConfig.createBrokerService(): Setting connector port to: {}", properties.getConnectorPort());
                brokerService.getManagementContext().setConnectorPort(properties.getConnectorPort());
            }
        }

        // Set memory limit in order not to use too much memory
        int memHeapPercent = properties.getMemoryJvmHeapPercentage();
        long memSize = properties.getMemorySize();
        if (memHeapPercent > 0 || memSize > 0) {
            final MemoryUsage memoryUsage = new MemoryUsage();
            if (memHeapPercent > 0) {
                memoryUsage.setPercentOfJvmHeap(memHeapPercent);
                log.info("BrokerConfig: Limiting Broker Service instance memory usage to {}% of JVM heap size", memHeapPercent);
            } else {
                memoryUsage.setUsage(memSize);
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
        if (!props.isEmpty() && !props.startsWith("?")) props = "?" + props;
        brokerService.addSslConnector(properties.getBrokerUrl() + props, keystore, truststore, null);

        return brokerService;
    }

    private KeyManager[] readKeystore() throws Exception {
        final KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        final KeyStore keystore = KeyStore.getInstance(properties.getKeystoreType());

        //final Resource keystoreResource = new ClassPathResource( properties.getKeystoreFile() );
        final FileSystemResource keystoreResource = new FileSystemResource(properties.getKeystoreFile());
        keystore.load(keystoreResource.getInputStream(), properties.getKeystorePassword().toCharArray());
        keyManagerFactory.init(keystore, properties.getKeystorePassword().toCharArray());
        final KeyManager[] keystoreManagers = keyManagerFactory.getKeyManagers();
        return keystoreManagers;
    }

    private TrustManager[] readTruststore() throws Exception {
        final KeyStore truststore = KeyStore.getInstance(properties.getTruststoreType());

        //final Resource truststoreResource = new ClassPathResource( properties.getTruststoreFile() );
        final FileSystemResource truststoreResource = new FileSystemResource(properties.getTruststoreFile());
        truststore.load(truststoreResource.getInputStream(), properties.getTruststorePassword().toCharArray());
        final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(truststore);
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
                sslConnectionFactory.setTrustStore(properties.getTruststoreFile());
                sslConnectionFactory.setTrustStoreType(properties.getTruststoreType());
                sslConnectionFactory.setTrustStorePassword(properties.getTruststorePassword());
                sslConnectionFactory.setKeyStore(properties.getKeystoreFile());
                sslConnectionFactory.setKeyStoreType(properties.getKeystoreType());
                sslConnectionFactory.setKeyStorePassword(properties.getKeystorePassword());
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
		/*if (getBrokerLocalUserUsername()!=null) {
			connectionFactory.setUserName(getBrokerLocalUserUsername());
			connectionFactory.setPassword(getBrokerLocalUserPassword());
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
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        return template;
    }
}