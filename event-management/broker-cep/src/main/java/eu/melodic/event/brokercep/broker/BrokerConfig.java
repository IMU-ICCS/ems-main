/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.brokercep.broker;

import eu.melodic.event.brokercep.broker.interceptor.AbstractMessageInterceptor;
import eu.melodic.event.brokercep.properties.BrokerCepProperties;
import eu.melodic.event.util.KeystoreUtil;
import eu.melodic.event.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSslConnectionFactory;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.SslBrokerService;
import org.apache.activemq.broker.inteceptor.MessageInterceptorRegistry;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.activemq.security.AuthenticationUser;
import org.apache.activemq.security.SimpleAuthenticationPlugin;
import org.apache.activemq.usage.MemoryUsage;
import org.apache.activemq.usage.SystemUsage;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.ConnectionFactory;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;
import java.util.*;
import java.util.stream.Collectors;

//import org.apache.activemq.security.JaasAuthenticationPlugin;


@Slf4j
@Service
@EnableJms
@Configuration
@RequiredArgsConstructor
public class BrokerConfig implements InitializingBean {

    private final static int LOCAL_ADMIN_INDEX = 0;
    private final static int LOCAL_USER_INDEX = 1;
    private final static String LOCAL_ADMIN_PREFIX = "admin-local-";
    private final static String LOCAL_USER_PREFIX = "user-local-";
    private final static int USERNAME_RANDOM_PART_LENGTH = 10;
    private final static int PASSWORD_LENGTH = 20;

    private final BrokerCepProperties properties;
    private final PasswordUtil passwordUtil;
    private final ApplicationContext applicationContext;

    private SimpleAuthenticationPlugin brokerAuthenticationPlugin;
    private SimpleBrokerAuthorizationPlugin brokerAuthorizationPlugin;
    private ArrayList<AuthenticationUser> userList;
    private String brokerLocalAdmin;
    private String brokerLocalAdminPassword;
    private String brokerUsername;
    private String brokerPassword;
    private String brokerCert;

    private KeyStore truststore;

    @Value("${brokercep.broker-url-2}")
    private String brokerUrl2;
    @Value("${brokercep.broker-url-3}")
    private String brokerUrl3;

    private HashMap<String, ConnectionFactory> connectionFactoryCache = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        _initializeSecurity();
    }

    protected synchronized void _initializeSecurity() throws Exception {
        log.debug("BrokerConfig._initializeSecurity(): Initializing broker security: initialize-authentication={}, initialize-authorization={}",
                properties.isAuthenticationEnabled(), properties.isAuthorizationEnabled());

        // initialize authentication
        if (properties.isAuthenticationEnabled()) {
            userList = new ArrayList<>();

            // initialize local admin credentials
            brokerLocalAdmin = LOCAL_ADMIN_PREFIX + RandomStringUtils.randomAlphanumeric(USERNAME_RANDOM_PART_LENGTH);
            brokerLocalAdminPassword = RandomStringUtils.randomAlphanumeric(PASSWORD_LENGTH);
            userList.add(new AuthenticationUser(brokerLocalAdmin, brokerLocalAdminPassword, SimpleBrokerAuthorizationPlugin.ADMIN_GROUP));
            log.debug("BrokerConfig._initializeSecurity(): Initialized local admin: {} / {}",
                    brokerLocalAdmin, passwordUtil.encodePassword(brokerLocalAdminPassword));

            // initialize broker user credentials
            brokerUsername = LOCAL_USER_PREFIX+ RandomStringUtils.randomAlphanumeric(USERNAME_RANDOM_PART_LENGTH);
            brokerPassword = RandomStringUtils.randomAlphanumeric(PASSWORD_LENGTH);
            userList.add(new AuthenticationUser(brokerUsername, brokerPassword, SimpleBrokerAuthorizationPlugin.RO_USER_GROUP));
            log.debug("BrokerConfig._initializeSecurity(): Initialized broker user: {} / {}",
                    brokerUsername, passwordUtil.encodePassword(brokerPassword));

            // initialize additional user credentials from configuration
            for (String extraUserCred : properties.getAdditionalBrokerCredentials().split(",")) {
                String[] cred = extraUserCred.split("/", 2);
                String username = cred[0].trim();
                String password = cred.length > 1 ? cred[1].trim() : "";
                userList.add(new AuthenticationUser(username, password, SimpleBrokerAuthorizationPlugin.RW_USER_GROUP));
                log.debug("BrokerConfig._initializeSecurity(): Initialized additional broker user from configuration: {} / {}",
                        username, passwordUtil.encodePassword(password));
            }

            // initialize Broker authentication plugin
            SimpleAuthenticationPlugin sap = new SimpleAuthenticationPlugin();        //new JaasAuthenticationPlugin()
            sap.setAnonymousAccessAllowed(false);
            sap.setUsers(userList);
            brokerAuthenticationPlugin = sap;

            if (log.isDebugEnabled()) {
                log.debug("BrokerConfig._initializeSecurity(): Initialized broker authentication plugin: anonymous-access={}, user-list={}",
                        sap.isAnonymousAccessAllowed(), sap.getUserPasswords().keySet()
                );
            }
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

        // Initialize Key pair and Certificate for SSL broker
        if (getBrokerUrl().startsWith("ssl")) {
            log.debug("BrokerConfig._initializeSecurity(): Initializing Broker key pair and certificate...");
            initializeKeyPairAndCert();
            log.debug("BrokerConfig._initializeSecurity(): Broker key pair and certificate initialization has been completed");
        } else {
            log.debug("BrokerConfig._initializeSecurity(): Broker key pair and certificate NOT initialized");
        }
    }

    private void initializeKeyPairAndCert() throws Exception {
        log.debug("BrokerConfig.initializeKeyAndCert(): BrokerCepProperties: {}", properties);
        log.info("BrokerConfig.initializeKeyAndCert(): Initializing keystore, truststore and certificate for Broker-SSL...");
        KeystoreUtil.initializeKeystoresAndCertificate(properties.getSsl(), passwordUtil);

        log.trace("BrokerConfig.initializeKeyAndCert(): Retrieving certificate for Broker-SSL...");
        this.brokerCert = KeystoreUtil
                .getKeystore(properties.getSsl().getKeystoreFile(), properties.getSsl().getKeystoreType(), properties.getSsl().getKeystorePassword())
                .passwordUtil(passwordUtil)
                .getEntryCertificateAsPEM(properties.getSsl().getKeyEntryName());
        log.trace("BrokerConfig.initializeKeyAndCert(): Retrieving certificate for Broker-SSL: file={}, type={}, password={}, alias={}, cert=\n{}",
                properties.getSsl().getKeystoreFile(), properties.getSsl().getKeystoreType(),
                passwordUtil.encodePassword(properties.getSsl().getKeystorePassword()),
                properties.getSsl().getKeyEntryName(), this.brokerCert);
        log.info("BrokerConfig.initializeKeyAndCert(): Initializing keystore, truststore and certificate for Broker-SSL... done");
    }

    public String getBrokerName() {
        log.trace("BrokerConfig.getBrokerName(): broker-name: {}", properties.getBrokerName());
        return properties.getBrokerName();
    }

    public String getBrokerUrl() {
        log.trace("BrokerConfig.getBrokerUrl(): broker-url: {}", properties.getBrokerUrl());
        return properties.getBrokerUrl();
    }

    public String getBrokerCertificate() {
        log.trace("BrokerConfig.getBrokerCertificate(): Broker certificate (PEM):\n{}", brokerCert);
        return brokerCert;
    }

    public KeyStore getBrokerTruststore() { return truststore; }

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
            log.debug("BrokerConfig.setBrokerUsername(): username={}", s);
        } else
            log.debug("BrokerConfig.setBrokerUsername(): Username not set");
    }

    public void setBrokerPassword(String password) {
        if (userList != null) {
            brokerPassword = password;
            userList.get(LOCAL_USER_INDEX).setPassword(password);
            brokerAuthenticationPlugin.setUsers(userList);
            log.debug("BrokerConfig.setBrokerPassword(): password={}", passwordUtil.encodePassword(password));
        } else
            log.debug("BrokerConfig.setBrokerPassword(): Password not set");
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

        // Start additional connectors (non-SSL)
        log.debug("BrokerConfig: 2nd connector: {}", brokerUrl2);
        log.debug("BrokerConfig: 3rd connector: {}", brokerUrl3);
        if (StringUtils.isNotEmpty(brokerUrl2)) brokerService.addConnector(brokerUrl2);
        if (StringUtils.isNotEmpty(brokerUrl3)) brokerService.addConnector(brokerUrl3);

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

        // Print Management Context information
        try {
            log.debug("BrokerConfig.createBrokerService(): Management Context (MC) settings:");
            ManagementContext mc = brokerService.getManagementContext();
            log.debug("    MC: BrokerName: {}", mc.getBrokerName());
            log.debug("    MC: ConnectorHost: {}", mc.getConnectorHost());
            log.debug("    MC: ConnectorPath: {}", mc.getConnectorPath());
            log.debug("    MC: Environment: {}", mc.getEnvironment());
            log.debug("    MC: JmxDomainName: {}", mc.getJmxDomainName());
            log.debug("    MC: RmiServerPort: {}", mc.getRmiServerPort());
            log.debug("    MC: SuppressMBean: {}", mc.getSuppressMBean());
            log.debug("    MC: AllowRemoteAddressInMBeanNames: {}", mc.isAllowRemoteAddressInMBeanNames());
            log.debug("    MC: ConnectorStarted: {}", mc.isConnectorStarted());
            log.debug("    MC: CreateConnector: {}", mc.isCreateConnector());
            log.debug("    MC: CreateMBeanServer: {}", mc.isCreateMBeanServer());
            log.debug("    MC: FindTigerMbeanServer: {}", mc.isFindTigerMbeanServer());
            log.debug("    MC: UseMBeanServer: {}", mc.isUseMBeanServer());

            log.debug("    MC->MBS: DefaultDomain: {}", mc.getMBeanServer().getDefaultDomain());
            log.debug("    MC->MBS: Domains: {}", (Object[])mc.getMBeanServer().getDomains());
            log.debug("    MC->MBS: MBeanCount: {}", mc.getMBeanServer().getMBeanCount());
        } catch (Exception ex) {
            log.error("    MC: EXCEPTION: ", ex);
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

        // register broker service interceptors
        registerMessageInterceptors(brokerService);

        return brokerService;
    }

    private void registerMessageInterceptors(BrokerService brokerService) {
        // get message interceptor registry
        final MessageInterceptorRegistry registry = MessageInterceptorRegistry.getInstance().get(brokerService);    // or ...get(BrokerRegistry.getInstance().findFirst());
        log.trace("BrokerConfig: Message interceptor registry: {}", registry);

        if (properties.getMessageInterceptors()==null) {
            log.warn("BrokerConfig: No message interceptors configured");
            return;
        }

        log.info("BrokerConfig: Message interceptors initializing...");
        List<BrokerCepProperties.MessageInterceptorSpec> interceptorSpecs = properties.getMessageInterceptors()
                .stream()
                .map(c -> (BrokerCepProperties.MessageInterceptorSpec)c)
                .collect(Collectors.toList());
        List<AbstractMessageInterceptor> interceptors = InterceptorHelper.newInstance()
                .initializeInterceptors(registry, applicationContext,
                        properties.getMessageInterceptorsSpecs(), interceptorSpecs);
        log.info("BrokerConfig: Message interceptors initialized");

        // register interceptors
        log.info("BrokerConfig: Registering message interceptors...");
        interceptors.forEach(i -> {
            String destinationPattern = ((BrokerCepProperties.MessageInterceptorConfig) i.getInterceptorSpec()).getDestination();
            registry.addMessageInterceptorForTopic(destinationPattern, i);
            log.debug("BrokerConfig: - Registered message interceptor with spec.: {}", i.getInterceptorSpec());
        });
        log.info("BrokerConfig: Registering message interceptors... done");
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
        final KeyStore keystore = KeyStore.getInstance(properties.getSsl().getKeystoreType());

        //final Resource keystoreResource = new ClassPathResource( properties.getKeystoreFile() );
        final FileSystemResource keystoreResource = new FileSystemResource(properties.getSsl().getKeystoreFile());
        keystore.load(keystoreResource.getInputStream(), properties.getSsl().getKeystorePassword().toCharArray());
        keyManagerFactory.init(keystore, properties.getSsl().getKeystorePassword().toCharArray());
        final KeyManager[] keystoreManagers = keyManagerFactory.getKeyManagers();
        return keystoreManagers;
    }

    private TrustManager[] readTruststore() throws Exception {
        this.truststore = KeyStore.getInstance(properties.getSsl().getTruststoreType());

        //final Resource truststoreResource = new ClassPathResource( properties.getTruststoreFile() );
        final FileSystemResource truststoreResource = new FileSystemResource(properties.getSsl().getTruststoreFile());
        this.truststore.load(truststoreResource.getInputStream(), properties.getSsl().getTruststorePassword().toCharArray());
        final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(this.truststore);
        final TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        return trustManagers;
    }

    public void writeTruststore() throws Exception {
        //final Resource truststoreResource = new ClassPathResource( properties.getTruststoreFile() );
        final FileSystemResource truststoreResource = new FileSystemResource(properties.getSsl().getTruststoreFile());
        this.truststore.store(truststoreResource.getOutputStream(), properties.getSsl().getTruststorePassword().toCharArray());
    }

    /**
     * Creates a new connection factory
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        return connectionFactory(null);
    }

    public ConnectionFactory connectionFactory(String brokerUrl) {
        if (brokerUrl==null) brokerUrl = properties.getBrokerUrlForClients();

        // Create connection factory based on Broker URL scheme
        final ActiveMQConnectionFactory connectionFactory;
        if (brokerUrl.startsWith("ssl")) {
            log.info("BrokerConfig: Creating new SSL connection factory instance: url={}", brokerUrl);
            final ActiveMQSslConnectionFactory sslConnectionFactory = new ActiveMQSslConnectionFactory(brokerUrl);
            try {
                sslConnectionFactory.setTrustStore(properties.getSsl().getTruststoreFile());
                sslConnectionFactory.setTrustStoreType(properties.getSsl().getTruststoreType());
                sslConnectionFactory.setTrustStorePassword(properties.getSsl().getTruststorePassword());
                sslConnectionFactory.setKeyStore(properties.getSsl().getKeystoreFile());
                sslConnectionFactory.setKeyStoreType(properties.getSsl().getKeystoreType());
                sslConnectionFactory.setKeyStorePassword(properties.getSsl().getKeystorePassword());
                //sslConnectionFactory.setKeyStoreKeyPassword( properties.getSsl()........ );

                connectionFactory = sslConnectionFactory;
            } catch (final Exception theException) {
                throw new Error(theException);
            }
        } else {
            log.info("BrokerConfig: Creating new non-SSL connection factory instance: url={}", brokerUrl);
            connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        }

        // Set credentials, if using local broker URL
		if (brokerUrl.equals(properties.getBrokerUrlForClients()) && getBrokerLocalUserUsername()!=null) {
			connectionFactory.setUserName(getBrokerLocalUserUsername());
			connectionFactory.setPassword(getBrokerLocalUserPassword());
		}

        // Other connection factory settings
        //connectionFactory.setSendTimeout(....5000L);
        //connectionFactory.setTrustedPackages(Arrays.asList("eu.melodic.event"));
        connectionFactory.setTrustAllPackages(true);
        connectionFactory.setWatchTopicAdvisories(true);

        // Make pooled connection factory
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(connectionFactory);
        pooledConnectionFactory.setMaxConnections(64);
        log.trace("BrokerConfig: New connection factory created: {}", pooledConnectionFactory);

        return pooledConnectionFactory;
    }

    public ConnectionFactory getConnectionFactoryFor(String connectionString) {
        return connectionFactoryCache
                .computeIfAbsent(connectionString, this::connectionFactory);
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