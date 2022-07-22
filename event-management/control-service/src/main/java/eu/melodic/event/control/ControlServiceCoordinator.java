/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control;

import camel.core.NamedElement;
import camel.metric.CompositeMetric;
import camel.metric.MetricContext;
import camel.metric.RawMetric;
import camel.requirement.ServiceLevelObjective;
import com.google.gson.*;
import eu.melodic.event.baguette.server.BaguetteServer;
import eu.melodic.event.baguette.server.NodeRegistry;
import eu.melodic.event.baguette.server.ServerCoordinator;
import eu.melodic.event.brokercep.BrokerCepService;
import eu.melodic.event.brokercep.BrokerCepStatementSubscriber;
import eu.melodic.event.brokercep.event.EventMap;
import eu.melodic.event.control.collector.netdata.ServerNetdataCollector;
import eu.melodic.event.control.properties.ControlServiceProperties;
import eu.melodic.event.control.util.TranslationContextMonitorGsonDeserializer;
import eu.melodic.event.translate.CamelToEplTranslator;
import eu.melodic.event.translate.TranslationContext;
import eu.melodic.event.translate.analyze.DAGNode;
import eu.melodic.event.util.KeystoreUtil;
import eu.melodic.event.util.NetUtil;
import eu.melodic.event.util.PasswordUtil;
import eu.melodic.models.commons.NotificationResult;
import eu.melodic.models.commons.NotificationResultImpl;
import eu.melodic.models.commons.Watermark;
import eu.melodic.models.commons.WatermarkImpl;
import eu.melodic.models.interfaces.ems.KeyValuePair;
import eu.melodic.models.interfaces.ems.Monitor;
import eu.melodic.models.interfaces.ems.Sink;
import eu.melodic.models.services.ems.CamelModelNotificationRequest;
import eu.melodic.models.services.ems.CamelModelNotificationRequestImpl;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ControlServiceCoordinator implements InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ControlServiceProperties properties;
    @Autowired
    private BaguetteServer baguette;
    @Autowired
    @Getter
    private BrokerCepService brokerCep;
    @Autowired
    private NodeRegistry nodeRegistry;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PasswordUtil passwordUtil;

    private AtomicBoolean inUse = new AtomicBoolean();
    private Map<String, TranslationContext> camelToTcCache = new HashMap<>();

    @Getter
    private String currentCamelModelId;
    @Getter
    private String currentCpModelId;
    private TranslationContext currentTC;

    private ServerNetdataCollector netdataCollector;

    @Getter
    private String reference = UUID.randomUUID().toString();

    public enum EMS_STATE {
        IDLE, INITIALIZING, RECONFIGURING, READY, ERROR
    }

    @Getter
    private EMS_STATE currentEmsState = EMS_STATE.IDLE;
    @Getter
    private String currentEmsStateMessage;
    @Getter
    private long currentEmsStateChangeTimestamp;


    @Override
    public void afterPropertiesSet() throws Exception {
        // Run configuration checks and throw exceptions early (before actually using EMS)
        if (properties.isSkipTranslation()) {
            if (StringUtils.isBlank(properties.getTcLoadFile()))
                throw new IllegalArgumentException("Model translation will be skipped (see property control.skip-translation), but no Translation Context file has been set. Check property: control.tc-load-file");
            if (! Paths.get(properties.getTcLoadFile()).toFile().exists())
                throw new IllegalArgumentException("Model translation will be skipped (see property control.skip-translation), but specified Translation Context file does not exist. Check property: control.tc-load-file=" + properties.getTcLoadFile());
            log.warn("Model translation will be skipped, and Translation Context file will be used: {}", properties.getTcLoadFile());
        }
    }

    public String getServerIpAddress() {
        return (properties.getIpSetting() == ControlServiceProperties.IpSetting.DEFAULT_IP)
                ? NetUtil.getDefaultIpAddress()
                : NetUtil.getPublicIpAddress();
    }

    // ------------------------------------------------------------------------------------------------------------

    public ControlServiceProperties getControlServiceProperties() {
        return properties;
    }

    public void setCurrentEmsState(@NonNull EMS_STATE newState, String message) {
        this.currentEmsState = newState;
        this.currentEmsStateMessage = message;
        this.currentEmsStateChangeTimestamp = System.currentTimeMillis();
    }

    // ------------------------------------------------------------------------------------------------------------

    @EventListener(ApplicationReadyEvent.class)
    public void applicationReady() {
        log.debug("ControlServiceCoordinator.applicationReady(): invoked");
        log.info("ControlServiceCoordinator.applicationReady(): IP setting: {}", properties.getIpSetting());
        preloadModels();
    }

    @Async
    public void preloadModels() {
        String preloadCamelModel = properties.getPreload().getCamelModel();
        String preloadCpModel = properties.getPreload().getCpModel();
        if (StringUtils.isNotBlank(preloadCamelModel)) {
            log.info("===================================================================================================");
            log.info("ControlServiceCoordinator.preloadModels(): Preloading models: camel-model={}, cp-model={}",
                    preloadCamelModel, preloadCpModel);
            processNewModel(preloadCamelModel, preloadCpModel, null, null, null);
        } else {
            log.info("ControlServiceCoordinator.preloadModels(): No CAMEL model to preload");
        }
    }

    // ------------------------------------------------------------------------------------------------------------

    @Async
    public void processNewModel(String camelModelId, String cpModelId, String notificationUri, String requestUuid, String jwtToken) {
        // Acquire lock of this coordinator
        if (!inUse.compareAndSet(false, true)) {
            String mesg = "ControlServiceCoordinator.processNewModel(): ERROR: Coordinator is in use. Method exits immediately";
            log.warn(mesg);
            if (!properties.isSkipEsbNotification()) {
                sendErrorNotification(camelModelId, notificationUri, requestUuid, jwtToken, mesg, mesg);
            } else {
                log.warn("ControlServiceCoordinator.processNewModel(): Skipping ESB notification due to configuration");
            }
            return;
        }

        try {
            // Call '_processNewModels()' to do actual processing
            _processNewModels(camelModelId, cpModelId, notificationUri, requestUuid, jwtToken);
            this.currentCamelModelId = _normalizeModelId(camelModelId);
            this.currentCpModelId = _normalizeModelId(cpModelId);
        } catch (Exception ex) {
            setCurrentEmsState(EMS_STATE.ERROR, ex.getMessage());

            String mesg = "ControlServiceCoordinator.processNewModel(): EXCEPTION: " + ex;
            log.error(mesg, ex);
            if (!properties.isSkipEsbNotification()) {
                sendErrorNotification(camelModelId, notificationUri, requestUuid, jwtToken, mesg, mesg);
            } else {
                log.warn("ControlServiceCoordinator.processNewModel(): Skipping ESB notification due to configuration");
            }
        } finally {
            // Release lock of this coordinator
            inUse.compareAndSet(true, false);
        }
    }

    @Async
    public void processCpModel(String cpModelId, String notificationUri, String requestUuid, String jwtToken) {
        // Acquire lock of this coordinator
        if (!inUse.compareAndSet(false, true)) {
            String mesg = "ControlServiceCoordinator.processCpModel(): ERROR: Coordinator is in use. Method exits immediately";
            log.warn(mesg);
            if (!properties.isSkipEsbNotification()) {
                sendErrorNotification(null, notificationUri, requestUuid, jwtToken, mesg, mesg);
            } else {
                log.warn("ControlServiceCoordinator.processCpModel(): Skipping ESB notification due to configuration");
            }
            return;
        }

        try {
            // Call '_processCpModel()' to do actual processing
            _processCpModel(cpModelId, notificationUri, requestUuid, jwtToken);
            this.currentCpModelId = _normalizeModelId(cpModelId);
        } catch (Exception ex) {
            setCurrentEmsState(EMS_STATE.ERROR, ex.getMessage());

            String mesg = "ControlServiceCoordinator.processCpModel(): EXCEPTION: " + ex;
            log.error(mesg, ex);
            if (!properties.isSkipEsbNotification()) {
                sendErrorNotification(null, notificationUri, requestUuid, jwtToken, mesg, mesg);
            } else {
                log.warn("ControlServiceCoordinator.processCpModel(): Skipping ESB notification due to configuration");
            }
        } finally {
            // Release lock of this coordinator
            inUse.compareAndSet(true, false);
        }
    }

    // ------------------------------------------------------------------------------------------------------------

    protected void _processNewModels(String camelModelId, String cpModelId, String notificationUri,
                                     String requestUuid, String jwtToken)
    {
        log.info("ControlServiceCoordinator.processNewModel(): BEGIN: camel-model-id={}, cp-model-id={}, notification-uri={}, request-uuid={}", camelModelId, cpModelId, notificationUri, requestUuid);

        // Translate models into EPL rules etc
        TranslationContext _TC = null;
        if (!properties.isSkipTranslation()) {
            setCurrentEmsState(EMS_STATE.INITIALIZING, "Retrieving and translating CAMEL model");

            log.info("ControlServiceCoordinator.processNewModel(): CAMEL-to-EPL rule translation: camel-model-id={}", camelModelId);
            CamelToEplTranslator translator =
                    applicationContext.getBean(CamelToEplTranslator.class);
            _TC = translator.translate(camelModelId);
            log.debug("ControlServiceCoordinator.processNewModel(): CAMEL-to-EPL rule translation: RESULTS: {}", _TC);

            // serialize 'TranslationContext' to file
            String fileName = properties.getTcSaveFile();
            if (StringUtils.isNotBlank(fileName)) {
                try {
                    setCurrentEmsState(EMS_STATE.INITIALIZING, "Storing translation context to file");

                    log.info("ControlServiceCoordinator.processNewModel(): Start serializing _TC data in file: {}", fileName);
                    java.io.Writer writer = new java.io.FileWriter(fileName);
                    com.google.gson.Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
                    // clone _TC
                    TranslationContext _copyTC = new TranslationContext(false);
                    _copyTC.G2R.putAll(_TC.G2R);
                    _copyTC.G2T.putAll(_TC.G2T);
                    _copyTC.getTopicConnections().putAll(_TC.getTopicConnections());

                    _copyTC.E2A.putAll(_TC.E2A);
                    _copyTC.SLO.addAll(_TC.SLO);
                    _copyTC.MON.addAll(_TC.MON);
                    _copyTC.MONS.addAll(_TC.MONS);
                    _copyTC.CMVAR.addAll(_TC.CMVAR);
                    _copyTC.MVV.addAll(_TC.MVV);
                    _copyTC.MVV_CP.putAll(_TC.MVV_CP);
                    _copyTC.addLoadAnnotatedMetrics(_TC.getLoadAnnotatedMetricsSet());

                    gson.toJson(_copyTC, writer);
                    writer.close();
                    log.info("ControlServiceCoordinator.processNewModel(): Serialized _TC data in file: {}", fileName);

                    /*try (FileOutputStream out = new FileOutputStream("_TC.xml")) {
                        log.info(">>>>>>>>  _TC.XML:  WRITING...");
                        XMLEncoder xmlEncoder = new XMLEncoder(out);
                        xmlEncoder.writeObject(_TC.DAG);
                        xmlEncoder.writeObject(_TC.SLO);
                        xmlEncoder.writeObject(_TC.C2S);
                        xmlEncoder.writeObject(_TC.D2S);
                        xmlEncoder.writeObject(_TC.MONS);
                        xmlEncoder.writeObject(_TC.G2R);
                        xmlEncoder.writeObject(_TC.G2T);
                        xmlEncoder.writeObject(_TC.M2MC);
                        xmlEncoder.writeObject(_TC.CMVAR);
                        xmlEncoder.writeObject(_TC.MVV);
                        xmlEncoder.writeObject(_TC.MVV_CP);
                        xmlEncoder.writeObject(_TC.FUNC);
                        xmlEncoder.writeObject(_TC.getTopicConnections());
                        xmlEncoder.writeObject(_TC.getMetricConstraints());
                        xmlEncoder.close();
                    } catch (Exception e) {
                        log.error(">>>>>>>>  _TC.XML:  EXCEPTION: ", e);
                    }*/
                } catch (java.io.IOException ex) {
                    log.error("ControlServiceCoordinator.processNewModel(): FAILED to serialize _TC to file: {} : Exception: ", fileName, ex);
                }
            }

        } else {
            log.warn("ControlServiceCoordinator.processNewModel(): Skipping CAMEL-to-EPL rule translation due to configuration");
            _TC = new TranslationContext();

            // unserialize 'TranslationContext' from file
            String fileName = properties.getTcLoadFile();
            if (StringUtils.isNotBlank(fileName)) {
                setCurrentEmsState(EMS_STATE.INITIALIZING, "Loading translation context from file");

                try {
                    log.info("ControlServiceCoordinator.processNewModel(): Start deserializing _TC data from file: {}", fileName);
                    java.io.Reader reader = new java.io.FileReader(fileName);
                    com.google.gson.Gson gson = new GsonBuilder()
                            .registerTypeAdapter(Monitor.class, new TranslationContextMonitorGsonDeserializer())
                            .create();
                    _TC = gson.fromJson(reader, TranslationContext.class);
                    reader.close();
                    log.info("ControlServiceCoordinator.processNewModel(): Deserialized _TC data from file: {}", fileName);

                    CamelToEplTranslator translator =
                            applicationContext.getBean(CamelToEplTranslator.class);
                    translator.printResults(_TC, null);
                } catch (java.io.IOException ex) {
                    log.error("ControlServiceCoordinator.processNewModel(): FAILED to deserialize _TC from file: {} : Exception: ", fileName, ex);
                }
            } else {
                throw new IllegalArgumentException("No translation context file has been set");
            }
        }

        // Retrieve Metric Variable Values (MVV) from CP model
        Map<String, Double> constants = new HashMap<>();
        if (!properties.isSkipMvvRetrieve()) {
            if (StringUtils.isNotBlank(cpModelId)) {
                setCurrentEmsState(EMS_STATE.INITIALIZING, "Retrieving MVVs from CP model");

                try {
                    log.info("ControlServiceCoordinator.processNewModel(): Retrieving MVVs from CP model: cp-model-id={}", cpModelId);

                    // Retrieve constant names from '_TC.MVV_CP' and values from a given CP model
                    eu.melodic.event.control.util.CpModelHelper helper = new eu.melodic.event.control.util.CpModelHelper();
                    /*constants.putAll(helper.getMetricVariableValues(cpModelId, new java.util.HashSet<String>(_TC.MVV)));*/
                    constants = helper.getMatchingMetricVariableValues(cpModelId, _TC);
                    log.info("ControlServiceCoordinator.processNewModel(): MVVs retrieved from CP model: cp-model-id={}, MVVs={}", cpModelId, constants);

                } catch (Exception ex) {
                    log.error("ControlServiceCoordinator.processNewModel(): EXCEPTION while retrieving MVVs from CP model: cp-model-id={}", cpModelId, ex);
                }
            } else {
                log.error("ControlServiceCoordinator.processNewModel(): No CP model have been provided");
            }
        } else {
            log.warn("ControlServiceCoordinator.processNewModel(): Skipping MVV retrieval due to configuration");
        }

        // (Re-)Configure Broker and CEP
        String upperwareGrouping = properties.getUpperwareGrouping();
        if (!properties.isSkipBrokerCep()) {
            setCurrentEmsState(EMS_STATE.INITIALIZING, "initializing Broker-CEP");

            try {
                // Initializing Broker-CEP module if necessary
                if (brokerCep == null) {
                    log.info("ControlServiceCoordinator.processNewModel(): Broker-CEP: Initializing...");
                    brokerCep = applicationContext.getBean(BrokerCepService.class);
                    log.info("ControlServiceCoordinator.processNewModel(): Broker-CEP: Initializing...ok");
                }

                // Get event types for GLOBAL grouping (i.e. that of Upperware)
                log.info("ControlServiceCoordinator.processNewModel(): Broker-CEP: Upperware grouping: {}", upperwareGrouping);
                Set<String> eventTypeNames = _TC.getG2T().get(upperwareGrouping);
                log.info("ControlServiceCoordinator.processNewModel(): Broker-CEP: Configuration of Event Types: {}", eventTypeNames);
                if (eventTypeNames == null || eventTypeNames.size() == 0)
                    throw new RuntimeException("Broker-CEP: No event types for GLOBAL grouping");

                // Clear any previous event types, statements or function definitions and register the new ones
                brokerCep.clearState();
                brokerCep.addEventTypes(eventTypeNames, EventMap.getPropertyNames(), EventMap.getPropertyClasses());
                //brokerCep.addEventTypes( eventTypeNames, eu.melodic.event.brokercep.event.MetricEvent.class );

                log.info("ControlServiceCoordinator.processNewModel(): Broker-CEP: Constants: {}", constants);
                brokerCep.setConstants(constants);

                log.info("ControlServiceCoordinator.processNewModel(): Broker-CEP: Function definitions: {}", _TC.getFunctionDefinitions());
                brokerCep.addFunctionDefinitions(_TC.getFunctionDefinitions());

                Map<String, Set<String>> ruleStatements = _TC.getG2R().get(upperwareGrouping);
                log.info("ControlServiceCoordinator.processNewModel(): Broker-CEP: Configuration of EPL statements: {}", ruleStatements);
                if (ruleStatements != null) {
                    int cnt = 0;
                    for (Map.Entry<String, Set<String>> topicRules : ruleStatements.entrySet()) {
                        String topicName = topicRules.getKey();
                        for (String rule : topicRules.getValue()) {
                            brokerCep.getCepService().addStatementSubscriber(
                                    new BrokerCepStatementSubscriber("Subscriber_" + cnt++, topicName, rule, brokerCep, passwordUtil)
                            );
                        }
                    }
                } else {
                    log.warn("ControlServiceCoordinator.processNewModel(): Broker-CEP: No EPL statements found for GLOBAL grouping");
                }
            } catch (Exception ex) {
                log.error("ControlServiceCoordinator.processNewModel(): EXCEPTION while initializing Broker-CEP of Upperware: camel-model-id={}", camelModelId, ex);
            }
        } else {
            log.warn("ControlServiceCoordinator.processNewModel(): Skipping Broker-CEP setup due to configuration");
        }

        // Process placeholders in sink type configurations
        String brokerUrlForClients = brokerCep.getBrokerCepProperties().getBrokerUrlForClients();
        for (Monitor mon : _TC.MON) {
            for (Sink s : mon.getSinks()) {
                for (KeyValuePair pair : s.getConfiguration()) {
                    String val = pair.getValue();
                    val = val.replace("%{BROKER_URL}%", brokerUrlForClients);
                    pair.setValue(val);
                }
            }
        }

        // (Re-)Configure Baguette server
        if (!properties.isSkipBaguette()) {
            setCurrentEmsState(EMS_STATE.INITIALIZING, "Initializing Baguette Server");

            log.info("ControlServiceCoordinator.processNewModel(): Re-configuring Baguette Server: camel-model-id={}", camelModelId);
            try {
                baguette.setTopologyConfiguration(_TC, constants, upperwareGrouping, brokerCep);
            } catch (Exception ex) {
                log.error("ControlServiceCoordinator.processNewModel(): EXCEPTION while starting Baguette server: camel-model-id={}", camelModelId, ex);
            }
        } else {
            log.warn("ControlServiceCoordinator.processNewModel(): Skipping Baguette Server setup due to configuration");
        }

        // Start/Stop Top-Level collectors
        if (!properties.isSkipCollectors()) {
            if (netdataCollector!=null) {
                log.info("ControlServiceCoordinator.processNewModel(): Stopping NetdataCollector: camel-model-id={}", camelModelId);
                try {
                    netdataCollector.stop();
                } catch (Exception ex) {
                    log.error("ControlServiceCoordinator.processNewModel(): EXCEPTION while stopping NetdataCollector: camel-model-id={}", camelModelId, ex);
                }
            }
            ServerCoordinator serverCoordinator = nodeRegistry.getCoordinator();
            if (! serverCoordinator.supportsAggregators()) {
                if (netdataCollector==null) {
                    netdataCollector = applicationContext.getBean(ServerNetdataCollector.class);
                }
                log.info("ControlServiceCoordinator.processNewModel(): Starting NetdataCollector: camel-model-id={}", camelModelId);
                try {
                    netdataCollector.start();
                } catch (Exception ex) {
                    log.error("ControlServiceCoordinator.processNewModel(): EXCEPTION while starting NetdataCollector: camel-model-id={}", camelModelId, ex);
                }
            } else {
                log.info("ControlServiceCoordinator.processNewModel(): NetdataCollector is not needed (will not start it): camel-model-id={}", camelModelId);
            }
        } else {
            log.warn("ControlServiceCoordinator.processNewModel(): Skipping Collectors setup due to configuration");
        }

        // (Re-)Configure MetaSolver
        if (!properties.isSkipMetasolver()) {
            setCurrentEmsState(EMS_STATE.INITIALIZING, "Sending configuration to MetaSolver");

            // Get scaling event and SLO topics from _TC
            Set<String> scalingTopics = new HashSet<>();
            scalingTopics.addAll(_TC.E2A.keySet());
            scalingTopics.addAll(_TC.SLO);
            log.debug("ControlServiceCoordinator.processNewModel(): MetaSolver configuration: scaling-topics: {}", scalingTopics);

            // Get top-level metric topics from _TC
            Set<String> metricTopics = _TC.DAG.getTopLevelNodes().stream().filter(node -> !scalingTopics.contains(node.getElementName())).map(node -> node.getElementName()).collect(Collectors.toSet());
            log.debug("ControlServiceCoordinator.processNewModel(): MetaSolver configuration: metric-topics: {}", metricTopics);

            // Prepare subscription configurations
            //String upperwareBrokerUrl = brokerCep != null ? brokerCep.getBrokerCepProperties().getBrokerUrlForConsumer() : null;
            String upperwareBrokerUrl = brokerCep != null ? brokerCep.getBrokerCepProperties().getBrokerUrlForClients() : null;
            boolean usesAuthentication = brokerCep.getBrokerCepProperties().isAuthenticationEnabled();
            String username = usesAuthentication ? brokerCep.getBrokerUsername() : null;
            String password = usesAuthentication ? brokerCep.getBrokerPassword() : null;
            String certificate = brokerCep.getBrokerCertificate();
            log.debug("ControlServiceCoordinator.processNewModel(): Local Broker: uses-authentication={}, username={}, password={}, has-certificate={}",
                    usesAuthentication, username, passwordUtil.encodePassword(password), StringUtils.isNotBlank(certificate));
            log.trace("ControlServiceCoordinator.processNewModel(): Local Broker: broker-certificate={}", certificate);

            if (StringUtils.isBlank(upperwareBrokerUrl)) {
                log.warn("ControlServiceCoordinator.processNewModel(): No Broker URL has been specified or Broker-CEP module is deactivated");
            }
            List<Map> subscriptionConfigs = new ArrayList<>();
            for (String t : scalingTopics)
                subscriptionConfigs.add(_prepareSubscriptionConfig(upperwareBrokerUrl, username, password, certificate, t, "", "SCALE"));
            for (String t : metricTopics)
                subscriptionConfigs.add(_prepareSubscriptionConfig(upperwareBrokerUrl, username, password, certificate, t, "", "MVV"));
            log.debug("ControlServiceCoordinator.processNewModel(): MetaSolver subscriptions configuration: {}", subscriptionConfigs);

            // Retrieve MVV to Current-Config MVV map
            Map<String, String> mvvMap = _TC.MVV_CP;
            log.debug("ControlServiceCoordinator.processNewModel(): MetaSolver MVV configuration: {}", mvvMap);

            // Prepare MetaSolver configuration
            Map<String,Object> msConfig = new HashMap<>();
            msConfig.put("subscriptions", subscriptionConfigs);
            msConfig.put("mvv", mvvMap);

            // POST configuration to MetaSolver
            String metaSolverEndpoint = properties.getMetasolverConfigurationUrl();
            com.google.gson.Gson gson = new com.google.gson.Gson();
            String json = gson.toJson(msConfig);
            log.debug("ControlServiceCoordinator.processNewModel(): MetaSolver configuration in JSON: {}", json);
            if (StringUtils.isNotEmpty(metaSolverEndpoint)) {
                try {
                    log.info("ControlServiceCoordinator.processNewModel(): Calling MetaSolver: endpoint={}", metaSolverEndpoint);
                    //String metaSolverResponse = restTemplate.postForObject(metaSolverEndpoint, json, String.class);
                    HttpEntity<String> entity = createHttpEntity(String.class, json, jwtToken);
                    final ResponseEntity<String> response = restTemplate.postForEntity(metaSolverEndpoint, entity, String.class);
                    String metaSolverResponse = response.getBody();
                    log.info("ControlServiceCoordinator.processNewModel(): MetaSolver response: endpoint={}, response={}", metaSolverEndpoint, metaSolverResponse);
                } catch (Exception ex) {
                    log.error("ControlServiceCoordinator.processNewModel(): Failed to call MetaSolver: endpoint={}, EXCEPTION: ", metaSolverEndpoint, ex);
                }
            } else {
                log.warn("ControlServiceCoordinator.processNewModel(): MetaSolver endpoint is empty. Skipping Metasolver configuration");
            }

        } else {
            log.warn("ControlServiceCoordinator.processNewModel(): Skipping MetaSolver setup due to configuration");
        }

        // Cache _TC in order to reply to Adapter queries about component-to-sensor mappings and sensor-configuration
        log.info("ControlServiceCoordinator.processNewModel(): Cache translation results: camel-model-id={}", camelModelId);
        camelToTcCache.put(_normalizeModelId(camelModelId), _TC);

        // Notify ESB, if 'notificationUri' is provided
        if (!properties.isSkipEsbNotification()) {
            if (StringUtils.isNotBlank(notificationUri)) {
                setCurrentEmsState(EMS_STATE.INITIALIZING, "Notifying ESB");

                notificationUri = notificationUri.trim();
                log.info("ControlServiceCoordinator.processNewModel(): Notifying ESB: {}", notificationUri);
                sendSuccessNotification(camelModelId, notificationUri, requestUuid, jwtToken);
                log.info("ControlServiceCoordinator.processNewModel(): ESB notified: {}", notificationUri);
            }
        } else {
            log.warn("ControlServiceCoordinator.processNewModel(): Skipping ESB notification due to configuration");
        }

        this.currentTC = _TC;
        log.info("ControlServiceCoordinator.processNewModel(): END: camel-model-id={}", camelModelId);

        setCurrentEmsState(EMS_STATE.READY, null);
    }

    protected void _processCpModel(String cpModelId, String notificationUri, String requestUuid, String jwtToken) {
        log.info("ControlServiceCoordinator._processCpModel(): BEGIN: cp-model-id={}, notification-uri={}, request-uuid={}", cpModelId, notificationUri, requestUuid);
        log.info("ControlServiceCoordinator._processCpModel(): Current camel-model-id={}", currentCamelModelId);
        TranslationContext _TC = this.currentTC;

        // Retrieve Metric Variable Values (MVV) from CP model
        Map<String, Double> constants = new HashMap<>();
        if (!properties.isSkipMvvRetrieve()) {
            if (StringUtils.isNotBlank(cpModelId)) {
                setCurrentEmsState(EMS_STATE.RECONFIGURING, "Retrieving MVVs from CP model");

                try {
                    log.info("ControlServiceCoordinator._processCpModel(): Retrieving MVVs from CP model: cp-model-id={}", cpModelId);

                    // Retrieve constant names from '_TC.MVV_CP' and values from a given CP model
                    log.info("ControlServiceCoordinator._processCpModel(): Looking for MVV_CP's: {}", _TC.MVV_CP);
                    eu.melodic.event.control.util.CpModelHelper helper = new eu.melodic.event.control.util.CpModelHelper();
                    /*constants.putAll(helper.getMetricVariableValues(cpModelId, new java.util.HashSet<String>(_TC.MVV)));*/
                    constants = helper.getMatchingMetricVariableValues(cpModelId, _TC);
                    log.info("ControlServiceCoordinator._processCpModel(): MVVs retrieved from CP model: cp-model-id={}, MVVs={}", cpModelId, constants);

                } catch (Exception ex) {
                    log.error("ControlServiceCoordinator._processCpModel(): EXCEPTION while retrieving MVVs from CP model: cp-model-id={}", cpModelId, ex);
                }
            } else {
                log.error("ControlServiceCoordinator._processCpModel(): No CP model have been provided");
            }
        } else {
            log.warn("ControlServiceCoordinator._processCpModel(): Skipping MVV retrieval due to configuration");
        }

        // (Re-)Configure Broker and CEP
        if (!properties.isSkipBrokerCep()) {
            try {
                setCurrentEmsState(EMS_STATE.RECONFIGURING, "Reconfiguring Broker-CEP");

                // Initializing Broker-CEP module if necessary
                if (brokerCep == null) {
                    log.info("ControlServiceCoordinator._processCpModel(): Broker-CEP: Initializing...");
                    brokerCep = applicationContext.getBean(BrokerCepService.class);
                    log.info("ControlServiceCoordinator._processCpModel(): Broker-CEP: Initializing...ok");
                }

                log.info("ControlServiceCoordinator._processCpModel(): Passing constants to Broker-CEP: {}", constants);
                brokerCep.setConstants(constants);
            } catch (Exception ex) {
                log.error("ControlServiceCoordinator._processCpModel(): EXCEPTION while initializing Broker-CEP of Upperware: camel-model-id={}", cpModelId, ex);
            }
        } else {
            log.warn("ControlServiceCoordinator._processCpModel(): Skipping Broker-CEP setup due to configuration");
        }

        // (Re-)Configure Baguette server
        if (!properties.isSkipBaguette()) {
            setCurrentEmsState(EMS_STATE.RECONFIGURING, "Reconfiguring Baguette Server");

            log.info("ControlServiceCoordinator._processCpModel(): Re-configuring Baguette Server with constants: {}", constants);
            try {
                baguette.sendConstants(constants);
            } catch (Exception ex) {
                log.error("ControlServiceCoordinator._processCpModel(): EXCEPTION while configuring Baguette server: cp-model-id={}", cpModelId, ex);
            }
        } else {
            log.warn("ControlServiceCoordinator._processCpModel(): Skipping Baguette Server setup due to configuration");
        }

        // Notify ESB, if 'notificationUri' is provided
        if (!properties.isSkipEsbNotification()) {
            if (StringUtils.isNotBlank(notificationUri)) {
                setCurrentEmsState(EMS_STATE.RECONFIGURING, "Notifying ESB");

                notificationUri = notificationUri.trim();
                log.info("ControlServiceCoordinator._processCpModel(): Notifying ESB: {}", notificationUri);
                sendSuccessNotification(null, notificationUri, requestUuid, jwtToken);
                log.info("ControlServiceCoordinator._processCpModel(): ESB notified: {}", notificationUri);
            }
        } else {
            log.warn("ControlServiceCoordinator._processCpModel(): Skipping ESB notification due to configuration");
        }

        log.info("ControlServiceCoordinator._processCpModel(): END: cp-model-id={}", cpModelId);

        setCurrentEmsState(EMS_STATE.READY, null);
    }

    public void setConstants(@NonNull Map<String,Double> constants, String notificationUri, String requestUuid, String jwtToken) {
        log.info("ControlServiceCoordinator.setConstants(): BEGIN: constants={}, notification-uri={}, request-uuid={}", constants, notificationUri, requestUuid);
        log.info("ControlServiceCoordinator.setConstants(): constants={}", constants);
        TranslationContext _TC = this.currentTC;

        // Retrieve Metric Variable Values (MVV) from CP model
        if (properties.isSkipMvvRetrieve()) {
            log.info("ControlServiceCoordinator.setConstants(): isSkipMvvRetrieve is true, but constants processing will continue");
        }

        // (Re-)Configure Broker and CEP
        if (!properties.isSkipBrokerCep()) {
            try {
                setCurrentEmsState(EMS_STATE.RECONFIGURING, "Reconfiguring Broker-CEP");

                // Initializing Broker-CEP module if necessary
                if (brokerCep == null) {
                    log.info("ControlServiceCoordinator.setConstants(): Broker-CEP: Initializing...");
                    brokerCep = applicationContext.getBean(BrokerCepService.class);
                    log.info("ControlServiceCoordinator.setConstants(): Broker-CEP: Initializing...ok");
                }

                log.info("ControlServiceCoordinator.setConstants(): Passing constants to Broker-CEP: {}", constants);
                brokerCep.setConstants(constants);
            } catch (Exception ex) {
                log.error("ControlServiceCoordinator.setConstants(): EXCEPTION while initializing Broker-CEP with constants: constants={}", constants, ex);
            }
        } else {
            log.warn("ControlServiceCoordinator.setConstants(): Skipping Broker-CEP setup due to configuration");
        }

        // (Re-)Configure Baguette server
        if (!properties.isSkipBaguette()) {
            setCurrentEmsState(EMS_STATE.RECONFIGURING, "Reconfiguring Baguette Server");

            log.info("ControlServiceCoordinator.setConstants(): Re-configuring Baguette Server with constants: {}", constants);
            try {
                baguette.sendConstants(constants);
            } catch (Exception ex) {
                log.error("ControlServiceCoordinator.setConstants(): EXCEPTION while configuring Baguette server: constants={}", constants, ex);
            }
        } else {
            log.warn("ControlServiceCoordinator.setConstants(): Skipping Baguette Server setup due to configuration");
        }

        // Notify ESB, if 'notificationUri' is provided
        if (!properties.isSkipEsbNotification()) {
            if (StringUtils.isNotBlank(notificationUri)) {
                setCurrentEmsState(EMS_STATE.RECONFIGURING, "Notifying ESB");

                notificationUri = notificationUri.trim();
                log.info("ControlServiceCoordinator.setConstants(): Notifying ESB: {}", notificationUri);
                sendSuccessNotification(null, notificationUri, requestUuid, jwtToken);
                log.info("ControlServiceCoordinator.setConstants(): ESB notified: {}", notificationUri);
            }
        } else {
            log.warn("ControlServiceCoordinator.setConstants(): Skipping ESB notification due to configuration");
        }

        log.info("ControlServiceCoordinator.setConstants(): END: constants={}", constants);

        setCurrentEmsState(EMS_STATE.READY, null);
    }

    // ------------------------------------------------------------------------------------------------------------

    protected String _normalizeModelId(String modelId) {
        if (StringUtils.isBlank(modelId)) return modelId;
        modelId = modelId.trim();
        if (!modelId.startsWith("/")) modelId = "/"+modelId;
        return modelId;
    }

    protected Map<String, String> _prepareSubscriptionConfig(String url, String username, String password, String certificate, String topic, String clientId, String type) {
        Map<String, String> map = new HashMap<>();
        map.put("url", url);
        map.put("username", username);
        map.put("password", password);
        map.put("certificate", certificate);
        map.put("topic", topic);
        map.put("client-id", clientId);
        map.put("type", type);
        return map;
    }

    // ------------------------------------------------------------------------------------------------------------
    // Translation information query methods
    // ------------------------------------------------------------------------------------------------------------

    public TranslationContext getTranslationContextOfCamelModel(String camelModelId) {
        return camelToTcCache.get(_normalizeModelId(camelModelId));
    }

    public List<Monitor> getSensorsOfCamelModel(String camelModelId) {
        if (StringUtils.isBlank(camelModelId))
            camelModelId = currentCamelModelId;
        TranslationContext _tc = camelToTcCache.get(_normalizeModelId(camelModelId));
        if (_tc==null) return Collections.emptyList();
        List<Monitor> sensors = new ArrayList<>(_tc.MON);
        return sensors;
    }

    public Set getMetricConstraints(String camelModelId) {
        TranslationContext _tc = camelToTcCache.get(_normalizeModelId(camelModelId));
        if (_tc==null) return Collections.emptySet();
        return _tc.getMetricConstraints();
    }

    /*public Set<String> getGlobalGroupingMetrics(String camelModelId) {
        TranslationContext _tc = camelToTcCache.get(_normalizeModelId(camelModelId));
        if (_tc==null) return Collections.emptySet();

        // get all top-level nodes their component metrics
        final Set<DAGNode> nodes = new HashSet<>();
        final Deque<DAGNode> q = new ArrayDeque<>(_tc.DAG.getTopLevelNodes());
        while (!q.isEmpty()) {
            DAGNode node = q.pop();
            if (node.getGrouping()==Grouping.GLOBAL) {
                nodes.add(node);
                q.addAll(_tc.DAG.getNodeChildren(node));
            }
        }

        // return metric names
        return nodes.stream()
                .map(DAGNode::getElementName)
                .collect(Collectors.toSet());
    }*/

    public @NonNull Map<String,Object> getSLOMetricDecomposition(String camelModelId) {
        List<Object> slos = _getSLOMetricDecomposition(camelModelId);
        Map<String,Object> result = new HashMap<>();
        result.put("name", "_");
        result.put("operator", "OR");
        result.put("constraints", slos);
        return result;
    }

    public @NonNull List<Object> _getSLOMetricDecomposition(String camelModelId) {
        TranslationContext _tc = camelToTcCache.get(_normalizeModelId(camelModelId));
        if (_tc==null) return Collections.emptyList();

        // Get metric and logical constraints
        Map<String, TranslationContext.MetricConstraint> mcMap = _tc.getMetricConstraints().stream()
                .collect(Collectors.toMap(TranslationContext.MetricConstraint::getName, mc -> mc));
        Map<String, TranslationContext.LogicalConstraint> lcMap = _tc.getLogicalConstraints().stream()
                .collect(Collectors.toMap(TranslationContext.LogicalConstraint::getName, lc -> lc));
        /*Map<String, TranslationContext.IfThenConstraint> ifMap = _tc.getIfThenConstraints().stream()
                .collect(Collectors.toMap(TranslationContext.IfThenConstraint::getName, ic -> ic));*/

        // Create map of top-level element names and instances
        Set<DAGNode> topLevelNodes = _tc.DAG.getTopLevelNodes();
        Map<String, DAGNode> topLevelNodesMap = topLevelNodes.stream()
                .collect(Collectors.toMap(DAGNode::getElementName, x -> x));

        // process each SLO
        List<Object> sloMetricDecompositions = new ArrayList<>();
        for (String sloName : _tc.SLO) {
            DAGNode node = topLevelNodesMap.get(sloName);
            if (node!=null) {
                // get SLO constraint
                Set<DAGNode> sloConstraintSet = _tc.DAG.getNodeChildren(node);
                // SLO must contain exactly one constraint
                if (sloConstraintSet.size()==1) {
                    DAGNode sloConstraintNode = sloConstraintSet.iterator().next();
                    // decompose constraint
                    Object decomposition = _decomposeConstraint(_tc, sloConstraintNode, mcMap, lcMap);
                    // cache decomposition
                    sloMetricDecompositions.add(decomposition);
                }
            }
        }

        return sloMetricDecompositions;
    }

    private Object _decomposeConstraint(TranslationContext _tc, DAGNode constraintNode, Map<String, TranslationContext.MetricConstraint> mcMap, Map<String, TranslationContext.LogicalConstraint> lcMap) {
        NamedElement element = constraintNode.getElement();
        String elementName = constraintNode.getElementName();
        if (element instanceof camel.constraint.MetricConstraint) {
            return mcMap.get(elementName);
        } else
        if (element instanceof camel.constraint.LogicalConstraint) {
            TranslationContext.LogicalConstraint lc = lcMap.get(elementName);

            // decompose child constraints
            List<Object> list = new ArrayList<>();
            for (DAGNode node : lc.getConstraintNodes()) {
                Object o = _decomposeConstraint(_tc, node, mcMap, lcMap);
                if (o!=null) list.add(o);
            }

            // create decomposition result
            Map<String,Object> result = new HashMap<>();
            result.put("name", lc.getName());
            result.put("operator", lc.getOperator());
            result.put("constraints", list);
            return result;
        } else
            log.warn("_decomposeConstraint: Unsupported Constraint type: {} {}", constraintNode.getElementName(), element.getClass().getName());
        return null;
    }

    public @NonNull Set<TranslationContext.MetricContext> getMetricContextsForPrediction(String camelModelId) {
        log.debug("getMetricContextsForPrediction: BEGIN: {}", camelModelId);
        TranslationContext _tc = camelToTcCache.get(_normalizeModelId(camelModelId));
        if (_tc==null) {
            log.debug("getMetricContextsForPrediction: END: No Translation Context found for model: {}", camelModelId);
            return Collections.emptySet();
        }

        // Process DAG top-level nodes
        Set<DAGNode> topLevelNodes = _tc.DAG.getTopLevelNodes();
        HashSet<TranslationContext.MetricContext> tcMetricsOfTopLevelNodes = new HashSet<>();
        log.debug("getMetricContextsForPrediction: Translation Context found for model: {}", camelModelId);

        final Deque<DAGNode> q = topLevelNodes.stream()
                .filter(x ->
                        x.getElement() instanceof ServiceLevelObjective ||
                        x.getElement() instanceof CompositeMetric ||
                        x.getElement() instanceof RawMetric)
                .distinct()
                .collect(Collectors.toCollection(ArrayDeque::new));

        while (!q.isEmpty()) {
            DAGNode node = q.pop();
            if (node.getElement() instanceof MetricContext) {
                tcMetricsOfTopLevelNodes.add(node.getMetricContext());
            } else {
                Set<DAGNode> children = _tc.DAG.getNodeChildren(node);
                if (children!=null) q.addAll(children);
            }
        }

        log.debug("getMetricContextsForPrediction: END: Metrics of Top-Level nodes of model: model={}, metrics={}", camelModelId, tcMetricsOfTopLevelNodes);
        return tcMetricsOfTopLevelNodes;
    }


    // ------------------------------------------------------------------------------------------------------------
    // Baguette control methods
    // ------------------------------------------------------------------------------------------------------------

    public BaguetteServer getBaguetteServer() {
        return baguette;
    }

    @Async
    public void stopBaguette() {
        // Acquire lock of this coordinator
        if (!inUse.compareAndSet(false, true)) {
            log.warn("ControlServiceCoordinator.stopBaguette(): ERROR: Coordinator is in use. Method exits immediately");
            return;
        }

        try {
            // Stop Baguette server
            log.info("ControlServiceCoordinator.stopBaguette(): Stopping Baguette server...");
            baguette.stopServer();
            log.info("ControlServiceCoordinator.stopBaguette(): Stopping Baguette server... done");
        } catch (Exception ex) {
            log.error("ControlServiceCoordinator.stopBaguette(): EXCEPTION while stopping Baguette server: ", ex);
        } finally {
            // Release lock of this coordinator
            inUse.compareAndSet(true, false);
        }
    }


    // ------------------------------------------------------------------------------------------------------------
    // Life-Cycle control methods
    // ------------------------------------------------------------------------------------------------------------

    void emsShutdown() {
        /*log.info("ControlServiceCoordinator.emsShutdown(): Shutting down EMS...");
        log.info("ControlServiceCoordinator.emsShutdown(): Shutting down EMS... done");*/
        log.warn("ControlServiceCoordinator.emsShutdown(): Not implemented");
    }

    @Async
    synchronized void emsExit() {
        emsExit(properties.getExitCode());
    }

    @Async
    synchronized void emsExit(int exitCode) {
        if (properties.isExitAllowed()) {
            // Signal SpringBootApp to exit
            log.info("ControlServiceCoordinator.emsExit(): Signaling exit...");
            ControlServiceApplication.exitApp(exitCode, properties.getExitGracePeriod());
            log.info("ControlServiceCoordinator.emsExit(): Signaling exit... done");
        } else {
            log.warn("ControlServiceCoordinator.emsExit(): Exit is not allowed");
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    // ESB notification methods
    // ------------------------------------------------------------------------------------------------------------

    private void sendSuccessNotification(String applicationId, String notificationUri, String requestUuid, String jwtToken) {
        // Prepare success result notification
        NotificationResultImpl result = new NotificationResultImpl();
        result.setStatus(NotificationResult.StatusType.SUCCESS);

        // Prepare and send CamelModelNotification
        try {
            sendCamelModelNotification(applicationId, result, notificationUri, requestUuid, jwtToken);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void sendErrorNotification(String applicationId, String notificationUri, String requestUuid,
                                       String jwtToken, String errorCode, String errorDescription)
    {
        // Prepare error result notification
        NotificationResultImpl result = new NotificationResultImpl();
        result.setStatus(NotificationResult.StatusType.ERROR);
        result.setErrorCode(errorCode);
        result.setErrorDescription(errorDescription);

        // Prepare and send CamelModelNotification
        try {
            sendCamelModelNotification(applicationId, result, notificationUri, requestUuid, jwtToken);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void sendCamelModelNotification(String applicationId, NotificationResult result, String notificationUri,
                                            String requestUuid, String jwtToken) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException, CertificateException, UnrecoverableKeyException {
        // Create a new watermark
        Watermark watermark = new WatermarkImpl();
        watermark.setUser("EMS");
        watermark.setSystem("EMS");
        watermark.setDate(new java.util.Date());
        String uuid = requestUuid!=null ? requestUuid : java.util.UUID.randomUUID().toString().toLowerCase();
        watermark.setUuid(uuid);

        // Create a new CamelModelNotification
        CamelModelNotificationRequest request = new CamelModelNotificationRequestImpl();
        request.setApplicationId(applicationId);
        request.setResult(result);
        request.setWatermark(watermark);

        // Send CamelModelNotification to ESB (Control Process)
        sendCamelModelNotification(request, notificationUri, jwtToken);
    }

    private void sendCamelModelNotification(CamelModelNotificationRequest notification, String notificationUri, String jwtToken) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, IOException, CertificateException, UnrecoverableKeyException {
        // Check if 'notificationUri' is blank
        if (StringUtils.isBlank(notificationUri)) {
            log.warn("ControlServiceCoordinator.sendCamelModelNotification(): notificationUri not provided or is empty. No notification will be sent to ESB.");
            return;
        }
        notificationUri = notificationUri.trim();

        // Get ESB url from control-service configuration
        String esbUrl = properties.getEsbUrl();
        if (StringUtils.isBlank(esbUrl)) {
            log.warn("ControlServiceCoordinator.sendCamelModelNotification(): esb-url property is empty. No notification will be sent to ESB.");
            return;
        }
        esbUrl = esbUrl.trim();

        // Fixing ESB url parts
        if (esbUrl.endsWith("/")) {
            esbUrl = esbUrl.substring(0, esbUrl.length() - 1);
        }
        if (notificationUri.startsWith("/")) {
            notificationUri = notificationUri.substring(1);
        }

        // Call ESB endpoint
        String url = esbUrl + "/" + notificationUri;
        log.info("ControlServiceCoordinator.sendCamelModelNotification(): Invoking ESB endpoint: {}", url);
        log.trace("ControlServiceCoordinator.sendCamelModelNotification(): JWT token: {}", jwtToken);
        //String responseStatus = restTemplate.postForEntity(url, notification, String.class).getStatusCode().toString();
        HttpEntity<CamelModelNotificationRequest> entity = createHttpEntity(CamelModelNotificationRequest.class, notification, jwtToken);

        ResponseEntity<String> response;
        if (url.toLowerCase().startsWith("http:")) {
            response = restTemplate.postForEntity(url, entity, String.class);
        } else {

            /*TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                    NoopHostnameVerifier.INSTANCE);

            Registry<ConnectionSocketFactory> socketFactoryRegistry =
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("https", sslsf)
                            .register("http", new PlainConnectionSocketFactory())
                            .build();

            BasicHttpClientConnectionManager connectionManager =
                    new BasicHttpClientConnectionManager(socketFactoryRegistry);
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
                    .setConnectionManager(connectionManager).build();

            HttpComponentsClientHttpRequestFactory requestFactory =
                    new HttpComponentsClientHttpRequestFactory(httpClient);

            response = new RestTemplate(requestFactory)
                    .postForEntity(url, entity, String.class);*/

            // Load keystore and truststore
            KeyStore keyStore = KeystoreUtil.readKeystore(
                    properties.getSsl().getKeystoreFile(),
                    properties.getSsl().getKeystoreType(),
                    properties.getSsl().getKeystorePassword());
            KeyStore trustStore = KeystoreUtil.readKeystore(
                    properties.getSsl().getTruststoreFile(),
                    properties.getSsl().getTruststoreType(),
                    properties.getSsl().getTruststorePassword());

            // Create SSL connection factory
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                    new SSLContextBuilder()
                            //.loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                            .loadTrustMaterial(trustStore, null)
                            .loadKeyMaterial(keyStore, properties.getSsl().getKeystorePassword().toCharArray())
                            .build(),
                    new DefaultHostnameVerifier()
            );

            // Create HTTPS client
            HttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(socketFactory).build();
            ClientHttpRequestFactory requestFactory =
                    new HttpComponentsClientHttpRequestFactory(httpClient);

            // Perform HTTPS call
            RestTemplate restTemplate = new RestTemplate(requestFactory);
            response = restTemplate
                    .postForEntity(url, entity, String.class);
        }

        String responseStatus = response.getStatusCode().toString();
        log.info("ControlServiceCoordinator.sendCamelModelNotification(): ESB endpoint invoked: {}, response={}", url, responseStatus);
    }

    public <T> HttpEntity<T> createHttpEntity(Class<T> notifType, Object notification, String jwtToken) {
        HttpHeaders headers = createHttpHeaders(jwtToken);
        return new HttpEntity<T>((T)notification, headers);
    }

    private HttpHeaders createHttpHeaders(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        if (StringUtils.isNotBlank(jwtToken)) {
            headers.set(HttpHeaders.AUTHORIZATION, jwtToken);
        }
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }


    // ------------------------------------------------------------------------------------------------------------
    // Event Generation and Debugging methods
    // ------------------------------------------------------------------------------------------------------------

    private final static String EVENT_LOG_OK = "OK";
    private final static String EVENT_LOG_ERROR = "ERROR";
    private final static String BAGUETTE_DISABLED = "BAGUETTE SERVER IS DISABLED";
    private final static String BAGUETTE_NOT_RUNNING = "BAGUETTE SERVER IS NOT RUNNING";

    private String eventLogEnd(String method, String result) {
        log.debug("ControlServiceCoordinator.{}(): END: result={}", method, result);
        return result;
    }

    private String eventSendCommandToClient(String method, String clientId, String command) {
        // Check status
        if (properties.isSkipBaguette()) return eventLogEnd(method, BAGUETTE_DISABLED);
        if (!baguette.isServerRunning()) return eventLogEnd(method, BAGUETTE_NOT_RUNNING);

        // Send command
        if (clientId.equals("0")) {
            if (command.startsWith("SEND-")) {
                try {
                    String[] part = command.split(" ");
                    String topicName = part[1].trim();
                    String value = part[2].trim();
                    eu.melodic.event.brokercep.event.EventMap event = new eu.melodic.event.brokercep.event.EventMap(Double.parseDouble(value), 3, System.currentTimeMillis());
                    brokerCep.publishEvent(null, topicName, event);
                } catch (Exception ex) {
                    log.debug("ControlServiceCoordinator.{}(): EXCEPTION: command: {}, exception: ", method, command, ex);
                    // Log error
                    return eventLogEnd(method, EVENT_LOG_ERROR);
                }
            } else {
                log.debug("ControlServiceCoordinator.{}(): ERROR: Unsupported command for client-id=0 : {}", method, command);
                // Log error
                return eventLogEnd(method, EVENT_LOG_ERROR);
            }
        } else if ("*".equals(clientId))
            baguette.sendToActiveClients(command);
        else
            baguette.sendToClient("#"+clientId, command);

        // Log success
        return eventLogEnd(method, EVENT_LOG_OK);
    }


    // Public API for event debugging
    public String eventGenerationStart(String clientId, String topicName, long interval, double lowerValue, double upperValue) {
        log.debug("ControlServiceCoordinator.eventGenerationStart(): client={}, topic={}, interval={}, value-range=[{},{}]", clientId, topicName, interval, lowerValue, upperValue);
        String command = String.format(java.util.Locale.ROOT, "GENERATE-EVENTS-START %s %d %f %f", topicName, interval, lowerValue, upperValue);
        return eventSendCommandToClient("eventGenerationStart", clientId, command);
    }

    public String eventGenerationStop(String clientId, String topicName) {
        log.debug("ControlServiceCoordinator.eventGenerationStop(): client={}, topic={}", clientId, topicName);
        String command = String.format(java.util.Locale.ROOT, "GENERATE-EVENTS-STOP %s", topicName);
        return eventSendCommandToClient("eventGenerationStop", clientId, command);
    }

    public String eventLocalSend(String clientId, String topicName, double value) {
        log.debug("ControlServiceCoordinator.eventLocalSend(): BEGIN: client={}, topic={}, value={}", clientId, topicName, value);
        String command = String.format(java.util.Locale.ROOT, "SEND-LOCAL-EVENT %s %f", topicName, value);
        return eventSendCommandToClient("eventLocalSend", clientId, command);
    }

    public String eventRemoteSend(String clientId, String brokerUrl, String topicName, double value) {
        log.debug("ControlServiceCoordinator.eventRemoteSend(): BEGIN: client={}, broker-url={}, topic={}, value={}", clientId, brokerUrl, topicName, value);
        String command = String.format(java.util.Locale.ROOT, "SEND-EVENT %s %s %f", brokerUrl, topicName, value);
        return eventSendCommandToClient("eventRemoteSend", clientId, command);
    }

    // ------------------------------------------------------------------------------------------------------------

    public List<String> clientList() {
        log.debug("ControlServiceCoordinator.clientList(): BEGIN:");
        return baguette.isServerRunning() ? baguette.getActiveClients() : Collections.emptyList();
    }

    public Map<String, Map<String, String>> clientMap() {
        log.debug("ControlServiceCoordinator.clientMap(): BEGIN:");
        return baguette.isServerRunning() ? baguette.getActiveClientsMap() : Collections.emptyMap();
    }

    public List<String> passiveClientList() {
        log.debug("ControlServiceCoordinator.passiveClientList(): BEGIN:");
        return baguette.isServerRunning() ? baguette.getPassiveNodes() : Collections.emptyList();
    }

    public Map<String, Map<String, String>> passiveClientMap() {
        log.debug("ControlServiceCoordinator.passiveClientMap(): BEGIN:");
        return baguette.isServerRunning() ? baguette.getPassiveNodesMap() : Collections.emptyMap();
    }

    public String clientCommandSend(String clientId, String command) {
        log.debug("ControlServiceCoordinator.clientCommandSend(): BEGIN: client={}, command={}", clientId, command);
        return eventSendCommandToClient("clientCommandSend", clientId, command);
    }

    public String clusterCommandSend(String clusterId, String command) {
        log.debug("ControlServiceCoordinator.clusterCommandSend(): BEGIN: cluster={}, command={}", clusterId, command);
        return sendCommandToCluster("clusterCommandSend", clusterId, command);
    }

    private String sendCommandToCluster(String method, String clusterId, String command) {
        // Check status
        if (properties.isSkipBaguette()) return eventLogEnd(method, BAGUETTE_DISABLED);
        if (!baguette.isServerRunning()) return eventLogEnd(method, BAGUETTE_NOT_RUNNING);

        // Send command
        if ("*".equals(clusterId))
            baguette.sendToActiveClusters(command);
        else
            baguette.sendToCluster(clusterId, command);

        // Log success
        return eventLogEnd(method, EVENT_LOG_OK);
    }

    // ------------------------------------------------------------------------------------------------------------
}
