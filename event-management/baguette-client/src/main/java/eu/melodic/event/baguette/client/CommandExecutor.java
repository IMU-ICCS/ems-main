/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client;

import eu.melodic.event.baguette.client.cluster.*;
import eu.melodic.event.brokercep.BrokerCepService;
import eu.melodic.event.util.GroupingConfiguration;
import eu.melodic.event.brokercep.cep.StatementSubscriber;
import eu.melodic.event.brokercep.event.EventMap;
import eu.melodic.event.brokerclient.BrokerClient;
import eu.melodic.event.brokerclient.event.EventGenerator;
import eu.melodic.event.brokerclient.properties.BrokerClientProperties;
import eu.melodic.event.util.GROUPING;
import eu.melodic.event.util.KeystoreUtil;
import eu.melodic.event.util.PasswordUtil;
import io.atomix.cluster.Member;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;


/**
 * Command Executor
 */
@Slf4j
@Service
public class CommandExecutor {

    private static String getConfigDir() {
        String confDir = System.getenv("MELODIC_CONFIG_DIR");
        if (StringUtils.isBlank(confDir)) confDir = System.getProperty("MELODIC_CONFIG_DIR");
        if (StringUtils.isBlank(confDir)) confDir = "conf";
        return confDir;
    }

    private final static String DEFAULT_CONF_DIR = getConfigDir();
    private final static String DEFAULT_ID_FILE = DEFAULT_CONF_DIR + "/cached-id.properties";
    private static final int DEFAULT_ID_LENGTH = 32;
    private final static String DEFAULT_KEYSTORE_DIR = DEFAULT_CONF_DIR;

    @Autowired
    private BrokerCepService brokerCepService;
    @Autowired
    private BrokerClientProperties brokerClientProperties;
    @Autowired
    private PasswordUtil passwordUtil;

    private BaguetteClientProperties config;
    private String idFile;

    private InputStream in;
    private PrintStream out;
    private PrintStream err;
    private String clientId;

    private Map<String, GroupingConfiguration> groupings = new HashMap<>();
    private GroupingConfiguration activeGrouping;

    private Map<String, EventGenerator> eventGenerators = new HashMap<>();

    @Autowired
    private ClusterManagerProperties clusterManagerProperties;
    private ClusterManager clusterManager;
    private ClusterTest clusterTest;
    private boolean clusterKeystoreInitialized = false;
    private String clusterKeystoreFile;
    private String clusterKeystoreType;
    private String clusterKeystorePassword;


    public CommandExecutor() {
        initializeClientId();
    }

    public void setConfiguration(BaguetteClientProperties config) {
        log.trace("CommandExecutor: brokerCepService: {}", brokerCepService);
        this.config = config;
        this.idFile = DEFAULT_ID_FILE;
        initializeClientId();
    }

    private void initializeClientId() {
        if (config!=null && StringUtils.isNotBlank(config.getClientId())) {
            clientId = config.getClientId().trim();
            saveClientId(clientId);
        }
        if (StringUtils.isBlank(clientId))
            clientId = loadCachedClientId();
        if (StringUtils.isBlank(clientId)) {
            this.clientId = RandomStringUtils.randomAlphanumeric(DEFAULT_ID_LENGTH);
            saveClientId(clientId);
        }
    }

    boolean executeCommand(String line, InputStream in, PrintStream out, PrintStream err) throws IOException, InterruptedException {
        return execCmd(line.split("[ \t]+"), in, out, err);
    }

    boolean execCmd(String args[], InputStream in, PrintStream out, PrintStream err) throws IOException, InterruptedException {
        if (args == null || args.length == 0) return false;
        String cmd = args[0].toUpperCase();
        args[0] = "";

        if ("EXIT".equals(cmd)) {
            boolean canExit = config != null && config.isExitCommandAllowed();
            if (canExit) {
                if (clusterManager != null && clusterManager.isRunning())
                    clusterManager.leaveCluster();
                return true;    // Signal 'Sshc' to quit
            } else {
                final String mesg = "Exit is not allowed. Ignoring EXIT command";
                log.warn(mesg);
                out.println(mesg);
            }
        } else if ("CLIENT".equals(cmd)) {
            // Information from server. Don't do anything
        } else if ("ECHO".equals(cmd)) {
            // Server echoes back client command. Don't do anything
        } else if ("HEARTBEAT".equals(cmd)) {
            // Respond to server with OK
            out.println("OK");

        } else if ("GET-ID".equals(cmd)) {
            log.info("GET ID: {}", clientId);
            out.println(clientId);
        } else if ("SET-ID".equals(cmd)) {
            if (args.length < 2) return false;
            String id = args[1].trim();
            log.info("SET ID: {}", id);
            // Execute command
            saveClientId(id);
        } else if ("SET-GROUPING-CONFIG".equals(cmd)) {
            if (args.length < 2) return false;
            String configStr = String.join(" ", args).trim();
            log.trace("grouping-config-base64: {}", configStr);
            setGroupingConfiguration(configStr);
        } else if ("SET-CONSTANTS".equals(cmd)) {
            if (args.length < 2) return false;
            String configStr = String.join(" ", args).trim();
            log.trace("constants-base64: {}", configStr);
            setConstants(configStr);
        } else if ("SET-ACTIVE-GROUPING".equals(cmd)) {
            if (args.length < 2) return false;
            String newGrouping = String.join(" ", args).trim();
            log.trace("new-active-grouping: {}", newGrouping);
            setActiveGrouping(newGrouping);
        } else if ("SEND-LOCAL-EVENT".equals(cmd)) {
            if (args.length < 2) return false;
            String destination = args[1].trim();
            double value = args.length > 2 ? Double.parseDouble(args[2].trim()) : Math.random() * 1000;
            log.trace("Sending local event: destination={}, metricValue={}", destination, value);
            sendLocalEvent(destination, value);
        } else if ("SEND-EVENT".equals(cmd)) {
            if (args.length < 3) return false;
            String connection = args[1].trim();
            String destination = args[2].trim();
            double value = args.length > 3 ? Double.parseDouble(args[3].trim()) : Math.random() * 1000;
            log.trace("Sending event: connection={}, destination={}, metricValue={}", connection, destination, value);
            sendEvent(connection, destination, value);
        } else if ("GENERATE-EVENTS-START".equals(cmd)) {
            if (args.length < 5) return false;
            String destination = args[1].trim();
            long interval = Long.parseLong(args[2].trim());
            double lower = Double.parseDouble(args[3].trim());
            double upper = Double.parseDouble(args[4].trim());

            if (eventGenerators.get(destination) == null) {
                EventGenerator generator = new EventGenerator();
                generator.setClient(new BrokerClient(brokerClientProperties));
                generator.setBrokerUrl(brokerCepService.getBrokerCepProperties().getBrokerUrl());
                generator.setDestinationName(destination);
                generator.setLevel(1);
                generator.setInterval(interval);
                generator.setLowerValue(lower);
                generator.setUpperValue(upper);
                eventGenerators.put(destination, generator);
                generator.start();
            }
        } else if ("GENERATE-EVENTS-STOP".equals(cmd)) {
            if (args.length < 2) return false;
            String destination = args[1].trim();
            EventGenerator generator = eventGenerators.remove(destination);
            if (generator != null) {
                generator.stop();
            }
        } else if ("CLUSTER-KEY".equals(cmd)) {
            if (args.length<5) {
                log.error("Too few arguments");
                return false;
            }

            setClusterKeystore(args[1], args[2], args[3], args[4]);

        } else if ("CLUSTER-JOIN".equals(cmd)) {
            if (clusterManager!=null && clusterManager.isRunning()) {
                log.error("Cluster is running. Leave cluster first");
                return false;
            }

            // Check arguments
            if (args.length<3) {
                log.error("Too few arguments");
                return false;
            }

            // Initialize cluster properties
            if (clusterManagerProperties==null)
                clusterManagerProperties = new ClusterManagerProperties();
            clusterManagerProperties.setClusterId(args[1]);

            if (clusterManagerProperties.getTls().isEnabled()) {
                log.debug("Cluster TLS is enabled");
                if (clusterKeystoreInitialized) {
                    log.debug("Cluster TLS Keystore has been initialized");
                    clusterManagerProperties.getTls().setKeystore(clusterKeystoreFile);
                    clusterManagerProperties.getTls().setKeystorePassword(clusterKeystorePassword);
                    clusterManagerProperties.getTls().setTruststore(clusterKeystoreFile);
                    clusterManagerProperties.getTls().setTruststorePassword(clusterKeystorePassword);
                }
            }

            clusterManagerProperties.getLocalNode().setAddress(args[2]);
            clusterManagerProperties.setMemberAddresses(
                    (args.length>3) ? Arrays.asList(args).subList(3, args.length) : null);
            log.debug("Cluster properties:  {}", clusterManagerProperties);

            // Initialize cluster manager
            if (clusterManager==null) {
                clusterManager = new ClusterManager();
                clusterManager.setProperties(clusterManagerProperties);
            }

            // Join/start cluster
            clusterManager.initialize(clusterManagerProperties, new ClusterNodeCallback());
            //clusterManager.setCallback(new TestCallback(clusterManager.getLocalAddress()));
            clusterManager.joinCluster();
            clusterManager.waitToJoin();
            log.info("Joined to cluster");

        } else if ("CLUSTER-TEST".equals(cmd)) {

            if (args.length<2 || "START".equalsIgnoreCase(args[1])) {
                if (clusterManager==null) {
                    log.error("Cluster has not been initialized. Run CLUSTER-JOIN first");
                    return false;
                }
                clusterTest = new ClusterTest(clusterManager);
                clusterTest.startTest();
            } else if ("STOP".equalsIgnoreCase(args[1])) {
                if (clusterTest==null) {
                    log.error("Cluster test is not running");
                    return false;
                }
                clusterTest.stopTest();
                clusterTest = null;
            }

        } else if ("CLUSTER-LEAVE".equals(cmd)) {
            if (clusterManager==null) {
                log.error("Cluster has not been initialized. Run CLUSTER-JOIN first");
                return false;
            }
            if (! clusterManager.isRunning()) {
                log.error("Cluster is not running. Join cluster first");
                return false;
            }

            clusterManager.leaveCluster();

            if (clusterTest!=null) {
                clusterTest.stopTest();
                clusterTest = null;
            }
            log.info("Left cluster");

        } else if ("CLUSTER-SHELL".equals(cmd)) {
            if (clusterManager==null) {
                log.error("Cluster has not been initialized. Run CLUSTER-JOIN first");
                return false;
            }
            ClusterCLI cli = clusterManager.getCli();
            cli.setIn(in);
            cli.setOut(out);
            cli.setErr(err);
            cli.setPromptUpdate(true);
            log.info("Cluster CLI starts");
            cli.run();
            log.info("Cluster CLI ended");
        } else if ("CLUSTER-EXEC".equals(cmd)) {
            if (args.length < 2) {
                log.error("No cluster command specified");
                return false;
            }
            if (clusterManager==null) {
                log.error("Cluster has not been initialized. Run CLUSTER-JOIN first");
                return false;
            }
            ClusterCLI cli = clusterManager.getCli();
            cli.setIn(in);
            cli.setOut(out);
            cli.setErr(err);
            String[] args1 = Arrays.stream(args, 1, args.length).toArray(String[]::new);
            String cmd1 = String.join(" ", args1);
            try {
                log.info("Cluster executes command: {}", cmd1);
                cli.executeCommand(cmd1, args1);
			} catch (Exception ex) {
				log.error("Cluster: Exception caught while executing command: {}\nException ", cmd1, ex);
            }

        } else if ("GET-LOCAL-NODE-CERTIFICATE".equals(cmd)) {
            String localAddress = ClusterManager.getLocalHostAddress();
            String localHostname = ClusterManager.getLocalHostName();
            String nlChar = (args.length > 1) ? args[1].trim() : null;
            try {
                log.info("Retrieving this node certificate from keystore:");
                String cert = brokerCepService.getBrokerCertificate();
                if (cert!=null && StringUtils.isNotBlank(nlChar))
                    cert = cert.replace("\r\n", nlChar).replace("\n", nlChar);
                log.info("{} {} {}", localAddress, localHostname, cert);
                out.println(localAddress+" "+localHostname+" "+cert);
            } catch (Exception e) {
                log.error("Exception while retrieving local node certificate: ", e);
            }

        } else if ("ADD-TRUSTED-NODE".equals(cmd)) {
            if (args.length < 4) return false;
            String nodeAlias = args[1];
            String nlChar = args[2];
            String nodeCert = String.join(" ",
                    Arrays.asList(args).subList(3, args.length)).replace(nlChar, "\n");
            try {
                log.info("Adding/Updating trusted node certificate in truststore: {}\nCertificate: {}", nodeAlias, nodeCert);
                brokerCepService.addOrReplaceCertificateInTruststore(nodeAlias, nodeCert);
                log.info("Truststore updated: {}", nodeAlias);
            } catch (Exception e) {
                log.error("Exception while updating truststore: ", e);
            }

        } else if ("DEL-TRUSTED-NODE".equals(cmd)) {
            if (args.length < 2) return false;
            String nodeAlias = args[1];
            try {
                log.info("Deleting trusted node certificate from truststore: {}", nodeAlias);
                brokerCepService.deleteCertificateFromTruststore(nodeAlias);
                log.info("Truststore updated: {}", nodeAlias);
            } catch (Exception e) {
                log.error("Exception while updating truststore: ", e);
            }

        } else if ("SHOW-CONFIG".equals(cmd)) {
            log.info("BaguetteClient: configuration:\n{}", config);
            log.info("Cluster: configuration:\n{}", clusterManagerProperties);
        } else {
            args[0] = cmd;
            String line = String.join(" ", args);
            if (StringUtils.isNotBlank(line))
                log.warn("UNKNOWN COMMAND: {}", line);
        }
        return false;
    }

    private void setClusterKeystore(String ksFile, String ksType, String ksPassword, String ksBase64) {
        String ksDir = clusterManagerProperties.getTls().getKeystoreDir();
        if (StringUtils.isBlank(ksDir)) ksDir = DEFAULT_KEYSTORE_DIR;
        if (!ksDir.endsWith("/")) ksDir += "/";
        this.clusterKeystoreInitialized = true;
        this.clusterKeystoreFile = ksDir + ksFile;
        this.clusterKeystoreType = ksType;
        this.clusterKeystorePassword = ksPassword;
        String clusterKeystoreBase64 = ksBase64;
        log.info("Cluster Keystore: file: {}", clusterKeystoreFile);
        log.info("                  type: {}", clusterKeystoreType);
        log.info("              password: {}", passwordUtil.encodePassword(clusterKeystorePassword));
        log.debug("        Base64 content: {}", clusterKeystoreBase64);
        try {
            KeystoreUtil
                    .getKeystore(clusterKeystoreFile, clusterKeystoreType, clusterKeystorePassword)
                    .createIfNotExist()
                    .writeBase64ToFile(clusterKeystoreBase64);
        } catch (Exception e) {
            log.error("Exception while creting cluster keystore", e);
        }
    }

    /*protected Properties _base64ToProperties(String paramsStr) {
        paramsStr = new String(Base64.getDecoder().decode(paramsStr), StandardCharsets.UTF_8);
        //log.trace("params-str:  {}", paramsStr);
        Properties params = new Properties();
        try {
            params.load(new StringReader(paramsStr));
            return params;
        } catch (IOException e) {
            log.error("Could not deserialize parameters: ", e);
        }
        return null;
    }*/

    /**
     * Read the object from Base64 string.
     */
    protected Object deserializeFromString(String s) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    protected synchronized void setGroupingConfiguration(String configStr) {
        try {
            log.debug("Received serialization of Grouping configuration: {}", configStr);
            GroupingConfiguration grouping = (GroupingConfiguration) deserializeFromString(configStr);
            GroupingConfiguration oldGrouping = groupings.get(grouping.getName());
            if (oldGrouping!=null) {
                log.debug("Old grouping config.: {}", oldGrouping);
            }
            synchronized (groupings) {
                groupings.put(grouping.getName(), grouping);
            }
            log.debug("New grouping config.: {}", grouping);

        } catch (Exception ex) {
            log.error("Exception while deserializing received Grouping configuration: ", ex);
        }
    }

    protected synchronized void setConstants(String configStr) {
        try {
            log.debug("Received serialization of Constants: {}", configStr);
            HashMap all = (HashMap) deserializeFromString(configStr);
            Map<String, Double> constants = (Map<String, Double>) all.get("constants");
            log.debug("Received Constants: {}", constants);

            if (activeGrouping != null) {
                log.info("SETTING CONSTANTS: {}", constants);
                activeGrouping.setConstants(constants);
                brokerCepService.setConstants(constants);
                log.debug("New constants set: {}", constants);
            } else {
                log.warn("No active grouping. Constants will be ignored");
            }

        } catch (Exception ex) {
            log.error("Exception while deserializing received Constants: ", ex);
        }
    }

    protected synchronized void setActiveGrouping(String newGroupingName) {
        GroupingConfiguration newGrouping = groupings.get(newGroupingName);
        if (newGrouping == null) {
            log.error("Grouping specified does not exist: {}", newGroupingName);
        } else {
            // Clear actions of "old" active grouping
            String oldGroupingName = "()";
            if (activeGrouping != null) {
                oldGroupingName = activeGrouping.getName();
                log.info("Old active grouping: {}", oldGroupingName);
            } else {
                log.info("No previous active grouping");
            }

            // Apply "new" active group settings
            activeGrouping = newGrouping;
            log.info("New active grouping: {}", newGrouping.getName());

            String activeGroupingName = newGrouping.getName();
            Properties config = activeGrouping.getConfig();

            log.info("Setting broker credentials: username={}, password=****", activeGrouping.getBrokerUsername());
            brokerCepService.setBrokerCredentials(activeGrouping.getBrokerUsername(), activeGrouping.getBrokerPassword());

            //log.info("Setting event types: {}", activeGrouping.getEventTypeNames());
            brokerCepService.clearState();
            brokerCepService.addEventTypes(activeGrouping.getEventTypeNames(), EventMap.getPropertyNames(), EventMap.getPropertyClasses());
            //brokerCepService.addEventTypes( activeGrouping.getEventTypeNames(), eu.melodic.event.brokercep.event.MetricEvent.class );

            log.info("Setting constants: {}", activeGrouping.getConstants());
            brokerCepService.setConstants(activeGrouping.getConstants());

            log.info("Setting function definitions: {}", activeGrouping.getFunctionDefinitions());
            brokerCepService.addFunctionDefinitions(activeGrouping.getFunctionDefinitions());

            if (activeGrouping.getRules() != null) {
                log.info("Adding level EPL statements: {}", activeGrouping.getRules());
                int cnt = 0;
                for (Map.Entry<String, Set<String>> topicRules : activeGrouping.getRules().entrySet()) {
                    String topic = topicRules.getKey();
                    log.info(" + Adding EPL statements for topic: {}", topic);
                    for (String rule : topicRules.getValue()) {
                        // Build forward-to-groupings set for current EPL statement
                        Set<String> forwardToGroupings = new HashSet<>();
                        if (activeGrouping.getConnections() != null) {
                            log.info(" + Connections for topic: {} --> {}", topic, activeGrouping.getConnections().get(topic));
                            if (activeGrouping.getConnections() != null && activeGrouping.getConnections().get(topic) != null) {
                                for (String fwdToGrouping : activeGrouping.getConnections().get(topic)) {
                                    String brokerUrl = config.getProperty(fwdToGrouping).split("\n")[0];    // the remaining lines are Broker Certificate
                                    forwardToGroupings.add(brokerUrl);
                                }
                            }
                        }

                        // Add EPL statement subscriber
                        String subscriberName = "Subscriber_" + cnt++;
                        log.info(" + Adding subscriber for EPL statement: subscriber-name={}, topic={}, rule={}, forward-to-groupings={}", subscriberName, topic, rule, forwardToGroupings);
                        brokerCepService.getCepService().addStatementSubscriber(
                                new ClientStatementSubscriber().setNameAndStatement(subscriberName, topic, rule, forwardToGroupings, brokerCepService, activeGrouping)
                        );
                    }
                }
            } else {
                log.warn("No EPL statements found for active grouping: {}", activeGrouping);
            }

            // Update truststore with per-grouping broker certificates
            if (brokerCepService.getBrokerTruststore()==null) {
                log.debug("Broker-CEP trust store has not been initialized. Probably SSL is disabled.");
                log.debug("Broker URL: {}", brokerCepService.getBrokerCepProperties().getBrokerUrl());
            } else {
                try {
                    log.debug("Truststore certificates before update: {}",
                            KeystoreUtil.getCertificateAliases(brokerCepService.getBrokerTruststore()));
                    for (String g : GROUPING.getNames()) {
                        String groupingBrokerCfg = config.getProperty(g);
                        if (groupingBrokerCfg != null) {
                            if (groupingBrokerCfg.indexOf("\n") > 0) {
                                String brokerUrl = groupingBrokerCfg.substring(0, groupingBrokerCfg.indexOf("\n")).trim();
                                String brokerCert = groupingBrokerCfg.substring(groupingBrokerCfg.indexOf("\n")).trim();
                                String host = null;
                                if (StringUtils.isNotBlank(brokerUrl))
                                    host = StringUtils.substringBetween(brokerUrl.trim(), "://", ":");
                                log.debug("Grouping host: {}", host);
                                if (StringUtils.isNotEmpty(brokerCert)) {
                                    log.info("Updating broker certificate to truststore for Grouping: {}", g);
                                    brokerCepService.addOrReplaceCertificateInTruststore(g, brokerCert);
                                    log.info("Updating broker certificate to truststore for Grouping Host: {}", host);
                                    brokerCepService.addOrReplaceCertificateInTruststore(host, brokerCert);
                                } else {
                                    log.info("No broker PEM certificate provided for Grouping: {}", g);
                                }
                            }
                        } else {
                            log.info("Removing broker certificate from truststore for Grouping (no new certificate provided): {}", g);
                            brokerCepService.deleteCertificateFromTruststore(g);
                        }
                    }
                    log.debug("Truststore certificates after update: {}",
                            KeystoreUtil.getCertificateAliases(brokerCepService.getBrokerTruststore()));
                } catch (Exception ex) {
                    log.error("EXCEPTION while updating Trust store: ", ex);
                }
            }

            log.info("Active grouping switch completed: {} -> {}", oldGroupingName, newGroupingName);
        }
    }

    public void sendLocalEvent(String destination, double metricValue) {
        if (activeGrouping != null) {
            String brokerUrl = brokerCepService.getBrokerCepProperties().getBrokerUrlForConsumer();
            log.info("sendLocalEvent(): local-broker-url={}", brokerUrl);
            sendEvent(brokerUrl, destination, metricValue);
        } else {
            log.warn("sendLocalEvent(): No active grouping");
        }
    }

    public void sendEvent(String connectionStr, String destination, double metricValue) {
        Map<String, Object> event = new HashMap<>();
        event.put("metricValue", metricValue);
        event.put("level", 1);
        event.put("timestamp", System.currentTimeMillis());
        sendEvent(connectionStr, destination, event);
    }

    public boolean sendEvent(String connectionStr, String destination, Map event, boolean createDestination) {
        if (createDestination || brokerCepService.destinationExists(destination)) {
            sendEvent(connectionStr, destination, event);
            return true;
        }
        return false;
    }

    public void sendEvent(String connectionStr, String destination, Map event) {
        try {
            log.info("sendEvent(): Sending event: connection={}, destination={}, payload={}", connectionStr, destination, event);
            brokerCepService.publishEvent(connectionStr, destination, event);
            log.info("sendEvent(): Event sent: connection={}, destination={}, payload={}", connectionStr, destination, event);
        } catch (Exception ex) {
            log.error("sendEvent(): Error while sending event: connection={}, destination={}, payload={}, exception: ", connectionStr, destination, event, ex);
        }
    }

    protected synchronized String loadCachedClientId() {
        // Get the 'cached client id' file name
        if (idFile == null)
            idFile = DEFAULT_ID_FILE;

        // Check if the cached client id file exists
        File file = Paths.get(idFile).toFile();
        if (! file.exists()) { log.warn("loadCachedClientId: Cached client id file not exists: {}", idFile); return null; }
        if (! file.isFile()) { log.warn("loadCachedClientId: Cached client id file is not a regular file: {}", idFile); return null; }

        // Load contents of existing 'client id' file
        try (InputStream in = new FileInputStream(idFile)) {

            Properties p = new Properties();
            p.load(in);

            // Get cached client id (if any)
            String id = p.getProperty("client.id", null);
            if (StringUtils.isNotBlank(id)) {
                id = id.trim();
                log.info("loadCachedClientId: Used cached Client Id: {}", clientId);
                return id;
            } else {
                log.warn("loadCachedClientId: No cached Client id found in file: {}", idFile);
            }
        } catch (Exception e) {
            log.warn("loadCachedClientId: EXCEPTION while loading cached Client id from file: {}\n", idFile, e);
        }
        return null;
    }

    protected synchronized void saveClientId(String id) {
        // Check new id value
        if (StringUtils.isBlank(id)) {
            log.error("SET-ID: ERROR: Empty id: {}", id);
            err.println("ERROR Empty id: " + id);
            return;
        }
        clientId = id.trim();

        // Load contents of existing 'id file' (if any)
        if (StringUtils.isBlank(idFile))
            idFile = DEFAULT_ID_FILE;
        Properties p = new Properties();
        // Check if the cached client id file exists
        File file = Paths.get(idFile).toFile();
        if (file.exists() && file.isFile()) {
            try (InputStream in = new FileInputStream(idFile)) {
                p.load(in);
            } catch (Exception e) {
                log.warn("saveClientId: EXCEPTION while reading cached Client id from file: {}\n", idFile, e);
            }
        } else {
            log.warn("saveClientId: Cached client id file not exists or is not a regular file: {}", idFile);
        }

        // Update 'id' in file contents in-memory
        p.setProperty("client.id", id);

        // Store new contents into 'id file'
        try (OutputStream os = new FileOutputStream(idFile)) {
            p.store(os, null);
            log.info("ID SET to: {}", id);
            if (out!=null) out.println("ID SET");
        } catch (Exception ex) {
            log.error("SET-ID: EXCEPTION: ", ex);
            err.println("ERROR While storing id to file: " + ex);
        }
    }

    private static class StreamGobbler implements Runnable {
        private InputStream inputStream1;
        private InputStream inputStream2;
        private Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream1, InputStream inputStream2, Consumer<String> consumer) {
            this.inputStream1 = inputStream1;
            this.inputStream2 = inputStream2;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream1)).lines().forEach(consumer);
            new BufferedReader(new InputStreamReader(inputStream2)).lines().forEach(consumer);
        }
    }

    public static class ClientStatementSubscriber implements StatementSubscriber {
        private String name;
        private String topic;
        private String statement;
        private Set<String> forwardToGroupings;
        private BrokerCepService brokerCep;
        private GroupingConfiguration activeGrouping;

        public String getName() {
            return name;
        }

        public String getTopic() {
            return topic;
        }

        public String getStatement() {
            return statement;
        }

        public Set<String> getForwardToGroupings() {
            return forwardToGroupings;
        }

        public StatementSubscriber setNameAndStatement(String n, String t, String s, Set<String> f, BrokerCepService bc, GroupingConfiguration ag) {
            name = n;
            topic = t;
            statement = s;
            forwardToGroupings = f;
            brokerCep = bc;
            activeGrouping = ag;
            return this;
        }

        public void update(Map<String, Object> eventMap) {
            log.info("- New event received: subscriber={}, topic={}, payload={}", name, topic, eventMap);

            try {
                // Publish new event to Local Broker topic
                String localBrokerUrl = brokerCep.getBrokerCepProperties().getBrokerUrlForConsumer();
                log.info("- Publishing event to local broker: subscriber={}, local-broker={}, topic={}, payload={}",
                        name, localBrokerUrl, topic, eventMap);
                brokerCep.publishEvent(localBrokerUrl, topic, eventMap);
                log.trace("- Event published to local broker: subscriber={}, local-broker={}, topic={}, payload={}",
                        name, localBrokerUrl, topic, eventMap);

                // Send new event to the next grouping(s)
                String username = activeGrouping.getBrokerUsername();
                String password = activeGrouping.getBrokerPassword();
                log.trace("- Forwarding event to groupings: subscriber={}, forward-to-groupings={}, payload={}",
                        name, forwardToGroupings, eventMap);
                for (String fwdToGrouping : forwardToGroupings) {
                    brokerCep.publishEvent(fwdToGrouping, username, password, topic, eventMap);
                    log.debug("- Event forwarded to grouping: subscriber={}, forwarded-to-grouping={}, username={}, topic={}, payload={}",
                            name, fwdToGrouping, username, topic, eventMap);
                }
            } catch (Exception ex) {
                log.error("- Error while sending event: subscriber={}, forward-to-groupings={}, payload={}, exception: ",
                        name, forwardToGroupings, eventMap, ex);
            }
        }
    }

    protected static class ClusterNodeCallback implements BrokerUtil.NodeCallback {
        @Override
        public void initialize() {
            log.warn(">>>>>>>  TODO: BROKER: INITIALIZE");
        }

        @Override
        public void stepDown() {
            log.warn(">>>>>>>  TODO: BROKER: STEP DOWN");
        }

        @Override
        public void backOff() {
            log.warn(">>>>>>>  TODO: BROKER: BACK OFF");
        }

        @Override
        public String getConfiguration(Member local) {
            return null;
        }

        @Override
        public void setConfiguration(String newConfig) {
            log.warn(">>>>>>>  TODO: BROKER: NEW CONFIG: "+newConfig);
        }
    }
}
