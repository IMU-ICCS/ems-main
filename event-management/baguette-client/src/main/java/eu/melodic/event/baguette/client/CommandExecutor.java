/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client;

import eu.melodic.event.brokercep.BrokerCepService;
import eu.melodic.event.brokercep.cep.FunctionDefinition;
import eu.melodic.event.brokercep.cep.StatementSubscriber;
import eu.melodic.event.brokercep.event.EventMap;
import eu.melodic.event.brokerclient.BrokerClient;
import eu.melodic.event.brokerclient.event.EventGenerator;
import eu.melodic.event.brokerclient.properties.BrokerClientProperties;
import eu.melodic.event.util.GROUPING;
import eu.melodic.event.util.KeystoreUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

//import eu.melodic.event.brokercep.event.MetricEvent;
//import java.util.concurrent.Executors;


/**
 * Command Executor
 */
@Service
@Slf4j
public class CommandExecutor {
    private final static String DEFAULT_ID_FILE = "cached-id.properties";

    @Autowired
    private BrokerCepService brokerCepService;
    @Autowired
    private BrokerClientProperties brokerClientProperties;

    private Properties config;
    private String idFile;

    private InputStream in;
    private PrintStream out;
    //private PrintStream err;
    private String clientId;

    private Map<String, Grouping> groupings = new HashMap<>();
    private Grouping activeGrouping;

    private Map<String, EventGenerator> eventGenerators = new HashMap<>();

    public void setConfigAndId(Properties config, String idFile) throws IOException {
        log.trace("CommandExecutor: brokerCepService: {}", brokerCepService);
        this.config = config;
        this.idFile = idFile;
        this.clientId = config.getProperty("client.id", "");
    }

    boolean executeCommand(String line, BufferedReader in, PrintStream out, PrintStream err) throws IOException, InterruptedException {
        return execCmd(line.split("[ \t]+"), in, out, err);
    }

    boolean execCmd(String args[], BufferedReader in, PrintStream out, PrintStream err) throws IOException, InterruptedException {
        if (args == null || args.length == 0) return false;
        String cmd = args[0].toUpperCase();
        args[0] = "";

        if ("EXIT".equals(cmd)) {
            boolean canExit = false;
            try { canExit = Boolean.parseBoolean(config.getProperty("exit-command.allowed", "false")); } catch (Exception e) {}
            if (canExit)
                return true;    // Signal 'Sshc' to quit
            else {
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

        } else if ("SET-ID".equals(cmd)) {
            if (args.length < 2) return false;
            String id = args[1].trim();
            log.info("SET ID: {}", id);
            // Execute command
            setClientId(id);
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
        } else {
            args[0] = cmd;
            log.warn("UNKNOWN COMMAND: " + String.join(" ", args));
        }
        return false;
    }

    /*protected Properties _base64ToProperties(String paramsStr) {
        paramsStr = new String(Base64.getDecoder().decode(paramsStr), StandardCharsets.UTF_8);
        //log.trace("params-str:  {}", paramsStr);
        Properties params = new Properties();
        try {
            params.load(new StringReader(paramsStr));
            return params;
        } catch (IOException e) {
            log.error("Could not unserialize parameters: ", e);
        }
        return null;
    }*/

    /**
     * Read the object from Base64 string.
     */
    protected Object unserializeFromString(String s) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    protected synchronized void setGroupingConfiguration(String configStr) {
        try {
            log.debug("Received serialization of Grouping configuration: {}", configStr);
            HashMap all = (HashMap) unserializeFromString(configStr);
            log.debug("Received Grouping configuration: {}", all);

            String groupingName = (String) all.get("grouping");
            Properties config = (Properties) all.get("config");
            HashSet<String> eventTypes = (HashSet<String>) all.get("eventTypes");
            Map<String, Set<String>> rules = (Map<String, Set<String>>) all.get("rules");
            Map<String, Set<String>> connections = (Map<String, Set<String>>) all.get("connections");
            Set<FunctionDefinition> functionDefs = (Set<FunctionDefinition>) all.get("function-definitions");
            Map<String, Double> constants = (Map<String, Double>) all.get("constants");
            String username = (String) all.get("common-broker-username");
            String password = (String) all.get("common-broker-password");

            log.info("SETTING GROUPING CONFIGURATION: grouping={}, configuration={}, event-types={}, rules={}, connections={}, constants={}, function-definitions={}",
                    groupingName, config, eventTypes, rules, connections, constants, functionDefs);
            Grouping grouping = groupings.get(groupingName);
            if (grouping == null) {
                grouping = new Grouping(groupingName);
                synchronized (groupings) {
                    groupings.put(groupingName, grouping);
                }
            } else {
                log.debug("Old grouping config.: {}", grouping);
            }
            grouping.setConfig(config);
            grouping.setEventTypeNames(eventTypes);
            grouping.setRules(rules);
            grouping.setConnections(connections);
            grouping.setConstants(constants);
            grouping.setFunctionDefinitions(functionDefs);
            grouping.setBrokerUsername(username);
            grouping.setBrokerPassword(password);
            log.debug("New grouping config.: {}", grouping);

        } catch (Exception ex) {
            log.error("Exception while unserializing received Grouping configuration: ", ex);
        }
    }

    protected synchronized void setConstants(String configStr) {
        try {
            log.debug("Received serialization of Constants: {}", configStr);
            HashMap all = (HashMap) unserializeFromString(configStr);
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
            log.error("Exception while unserializing received Constants: ", ex);
        }
    }

    protected synchronized void setActiveGrouping(String newGroupingName) {
        Grouping newGrouping = groupings.get(newGroupingName);
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

            log.info("Setting event types: {}", activeGrouping.getConstants());
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
                                String brokerCert = groupingBrokerCfg.substring(groupingBrokerCfg.indexOf("\n")).trim();
                                if (StringUtils.isNotEmpty(brokerCert)) {
                                    log.info("Updating broker certificate to truststore for Grouping: {}", g);
                                    brokerCepService.addOrReplaceCertificateInTruststore(g, brokerCert);
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

    protected synchronized void setClientId(String id) {
        // Check new id value
        if (StringUtils.isEmpty(id)) {
            log.error("SET-ID: ERROR: Invalid id: {}", id);
            out.println("ERROR Invalid id: " + id);
            return;
        }
        clientId = id;

        // Load contents of existing 'id file' (if any)
        if (idFile == null)
            idFile = DEFAULT_ID_FILE;
        Properties p = new Properties();
        try {
            try (InputStream in = new FileInputStream(idFile)) {
                p.load(in);
            }
        } catch (Exception ex) {
        }

        // Update 'id file' contents in-memory
        p.setProperty("client.id", id);

        // Store new contents into 'id file'
        try {
            try (OutputStream out = new FileOutputStream(idFile)) {
                p.store(out, "# Saved on " + new java.util.Date());
            }
            log.info("ID SET to: {}", id);
            out.println("ID SET");
        } catch (Exception ex) {
            log.error("SET-ID: EXCEPTION: ", ex);
            out.println("ERROR While storing id to file: " + ex);
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

    @Data
    private static class Grouping {
        private String name;
        private Properties config;
        private Set<String> eventTypeNames;
        private Map<String, Set<String>> rules;
        private Map<String, Set<String>> connections;
        private Set<FunctionDefinition> functionDefinitions;
        private Map<String, Double> constants;
        private String brokerUsername;
        private String brokerPassword;

        public Grouping(String name) {
            this.name = name;
        }
    }

    public static class ClientStatementSubscriber implements StatementSubscriber {
        private String name;
        private String topic;
        private String statement;
        private Set<String> forwardToGroupings;
        private BrokerCepService brokerCep;
        private Grouping activeGrouping;

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

        public StatementSubscriber setNameAndStatement(String n, String t, String s, Set<String> f, BrokerCepService bc, Grouping ag) {
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
                log.info("- Event published to local broker: subscriber={}, local-broker={}, topic={}, payload={}",
                        name, localBrokerUrl, topic, eventMap);

                // Send new event to the next grouping(s)
                String username = activeGrouping.getBrokerUsername();
                String password = activeGrouping.getBrokerPassword();
                log.info("- Forwarding event to groupings: subscriber={}, forward-to-groupings={}, payload={}",
                        name, forwardToGroupings, eventMap);
                for (String fwdToGrouping : forwardToGroupings) {
                    brokerCep.publishEvent(fwdToGrouping, username, password, topic, eventMap);
                    log.info("- Event forwarded to grouping: subscriber={}, forwarded-to-grouping={}, username={}, topic={}, payload={}",
                            name, fwdToGrouping, username, topic, eventMap);
                }
            } catch (Exception ex) {
                log.error("- Error while sending event: subscriber={}, forward-to-groupings={}, payload={}, exception: ",
                        name, forwardToGroupings, eventMap, ex);
            }
        }
    }
}
