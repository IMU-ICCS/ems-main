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
import static eu.melodic.event.brokercep.properties.BrokerCepProperties.KEY_ENTRY_GENERATE;
import eu.melodic.event.util.KeystoreUtil;
import eu.melodic.event.util.NetUtil;
import eu.melodic.event.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSslConnectionFactory;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.SslBrokerService;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.security.AuthenticationUser;
import org.apache.activemq.security.SimpleAuthenticationPlugin;
import org.apache.activemq.usage.MemoryUsage;
import org.apache.activemq.usage.SystemUsage;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    private PasswordUtil passwordUtil;

    private SimpleAuthenticationPlugin brokerAuthenticationPlugin;
    private SimpleBrokerAuthorizationPlugin brokerAuthorizationPlugin;
    private ArrayList<AuthenticationUser> userList;
    private String brokerLocalAdmin;
    private String brokerLocalAdminPassword;
    private String brokerUsername;
    private String brokerPassword;
    private String brokerCert;

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
                log.debug("BrokerConfig._initializeSecurity(): Initialized additional broker user from configuration: {} / {}",
                        username, passwordUtil.encodePassword(password));
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
        log.debug("BrokerConfig.initializeKeyAndCert(): Key pair and Certificate settings:");
        log.debug("    Keystore file: {}", properties.getKeystoreFile());
        log.debug("    Keystore type: {}", properties.getKeystoreType());
        log.debug("    Keystore password: {}", passwordUtil.encodePassword(properties.getKeystorePassword()));
        log.debug("    Trust store file: {}", properties.getTruststoreFile());
        log.debug("    Trust store type: {}", properties.getTruststoreType());
        log.debug("    Trust store password: {}", passwordUtil.encodePassword(properties.getTruststorePassword()));
        log.debug("    Certificate file: {}", properties.getCertificateFile());
        log.debug("    Entry name:  {}", properties.getKeyEntryNameValue());
        log.debug("    Entry DName: {}", properties.getKeyEntryDNameValue());
        log.debug("    Entry SAN:   {}", properties.getKeyEntryExtSANValue());
        log.debug("    Entry Gen.:  {}", properties.getKeyEntryGenerate());

        KEY_ENTRY_GENERATE keGen = properties.getKeyEntryGenerate();
        boolean gen = (keGen==KEY_ENTRY_GENERATE.YES || keGen==KEY_ENTRY_GENERATE.ALWAYS);

        // Check if key entry is missing
        if (keGen==KEY_ENTRY_GENERATE.IF_MISSING) {
            boolean containsEntry = KeystoreUtil
                    .getKeystore(properties.getKeystoreFile(), properties.getKeystoreType(), properties.getKeystorePassword())
                    .containsEntry(properties.getKeyEntryNameValue());
            if (containsEntry) {
                log.debug("    Keystore already contains entry: {}", properties.getKeyEntryNameValue());
            } else {
                log.debug("    Keystore does not contain entry: {}", properties.getKeyEntryNameValue());
                gen = true;
            }
        }

        // Check if IP address is in subject CN or SAN list
        if (keGen==KEY_ENTRY_GENERATE.IF_IP_CHANGED) {
            // get subject CN and SAN list (IP's only)
            List<String> addrList = KeystoreUtil
                    .getKeystore(properties.getKeystoreFile(), properties.getKeystoreType(), properties.getKeystorePassword())
                    .getEntryNames(properties.getKeyEntryNameValue(), true);
            log.debug("    Entry addresses: {}", addrList);

            // get current Default and Public IP addresses
            String defaultIp = NetUtil.getDefaultIpAddress();
            String publicIp = NetUtil.getPublicIpAddress();

            // check if Default and Public IP addresses are contained in 'addrList'
            boolean defaultFound = addrList.stream().anyMatch(s -> s.equals(defaultIp));
            boolean publicFound = addrList.stream().anyMatch(s -> s.equals(publicIp));
            gen = !defaultFound || !publicFound;
            log.debug("    Address has changed: {}  (default-ip-found={}, public-ip-found={})",
                    gen, defaultFound, publicFound);
        }

        // Generate new key pair and certificate, and update keystore and trust store
        if (gen) {
            log.debug("    Generating new Key pair and Certificate for: {}", properties.getKeyEntryNameValue());

            KeystoreUtil ksUtil = KeystoreUtil
                    .getKeystore(properties.getKeystoreFile(), properties.getKeystoreType(), properties.getKeystorePassword());
            if (StringUtils.isBlank(properties.getKeyEntryExtSANValue())) {
                log.debug("    Create/Replace entry (with SAN auto-generate): {}", properties.getKeyEntryNameValue());
                ksUtil.createOrReplaceKeyAndCertWithSAN(properties.getKeyEntryNameValue(), properties.getKeyEntryDNameValue());
            } else {
                log.debug("    Create/Replace entry and SAN: entry={}, san={}",
                        properties.getKeyEntryNameValue(), properties.getKeyEntryExtSANValue());
                String extSAN = "SAN=" + properties.getKeyEntryExtSANValue().trim();
                ksUtil.createOrReplaceKeyAndCert(properties.getKeyEntryNameValue(), properties.getKeyEntryDNameValue(), extSAN);
            }
            log.debug("    Exporting certificate to: {}", properties.getCertificateFile());
            ksUtil.exportCertToFile(properties.getKeyEntryNameValue(), properties.getCertificateFile());

            KeystoreUtil tsUtil = KeystoreUtil
                    .getKeystore(properties.getTruststoreFile(), properties.getTruststoreType(), properties.getTruststorePassword());
            log.debug("    Importing certificate to trust store: {}", properties.getTruststoreFile());
            tsUtil.importAndReplaceCertFromFile(properties.getKeyEntryNameValue(), properties.getCertificateFile());

            log.debug("    Key pair and Certificate generation completed");
        } else {
            log.debug("    Key pair and Certificate will not be re-generated");
        }

        // Load PEM certificate
        brokerCert = KeystoreUtil
                    .getKeystore(properties.getKeystoreFile(), properties.getKeystoreType(), properties.getKeystorePassword())
                    .getEntryCertificatePEM(properties.getKeyEntryNameValue());
        log.debug("    Broker Certificate (PEM):\n{}", brokerCert);
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

    public void setBrokerPassword(String password) {
        if (userList != null) {
            brokerPassword = password;
            userList.get(LOCAL_USER_INDEX).setPassword(password);
            brokerAuthenticationPlugin.setUsers(userList);
        }
        log.debug("BrokerConfig.setBrokerPassword(): password={}", passwordUtil.encodePassword(password));
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
            log.debug("    MC->MBS: Domains: {}", mc.getMBeanServer().getDomains());
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
     * Creates a new connection factory
     */
    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        // Create connection factory based on Broker URL scheme
        final ActiveMQConnectionFactory connectionFactory;
        String brokerUrl = properties.getBrokerUrl();
        //XXX:WHICH-ONE?: String brokerUrl = properties.getBrokerUrlForConsumer();
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