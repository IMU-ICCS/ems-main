/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control;

import eu.melodic.event.baguette.server.BaguetteServer;
import eu.melodic.event.brokercep.BrokerCepService;
import eu.melodic.event.brokercep.cep.StatementSubscriber;
import eu.melodic.event.brokercep.event.EventMap;
import eu.melodic.event.control.properties.ControlServiceProperties;
import eu.melodic.event.translate.CamelToEplTranslator;
import eu.melodic.event.translate.TranslationContext;
import eu.melodic.event.util.KeystoreUtil;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
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
import java.security.*;
import java.security.cert.CertificateException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ControlServiceCoordinator {

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


    // ------------------------------------------------------------------------------------------------------------

    public ControlServiceProperties getControlServiceProperties() {
        return properties;
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
        String preloadCamelModel = properties.getPreloadCamelModel();
        String preloadCpModel = properties.getPreloadCpModel();
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
            this.currentCamelModelId = camelModelId;
            this.currentCpModelId = cpModelId;
        } catch (Exception ex) {
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
            this.currentCpModelId = cpModelId;
        } catch (Exception ex) {
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
            log.info("ControlServiceCoordinator.processNewModel(): CAMEL-to-EPL rule translation: camel-model-id={}", camelModelId);
            CamelToEplTranslator translator =
                    applicationContext.getBean(CamelToEplTranslator.class);
            _TC = translator.translate(camelModelId);
            log.debug("ControlServiceCoordinator.processNewModel(): CAMEL-to-EPL rule translation: RESULTS: {}", _TC);

            // serialize 'TranslationContext' to file
            String fileName = properties.getTcSaveFile();
            if (StringUtils.isNotBlank(fileName)) {
                try {
                    log.info("ControlServiceCoordinator.processNewModel(): Start serializing _TC data in file: {}", fileName);
                    java.io.Writer writer = new java.io.FileWriter(fileName);
                    com.google.gson.Gson gson = new com.google.gson.GsonBuilder().create();
                    // clone _TC
                    TranslationContext _copyTC = new TranslationContext(false);
                    _copyTC.G2R.putAll(_TC.G2R);
                    _copyTC.G2T.putAll(_TC.G2T);
                    gson.toJson(_copyTC, writer);
                    writer.close();
                    log.info("ControlServiceCoordinator.processNewModel(): Serialized _TC data in file: {}", fileName);
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
                try {
                    log.info("ControlServiceCoordinator.processNewModel(): Start unserializing _TC data from file: {}", fileName);
                    java.io.Reader reader = new java.io.FileReader(fileName);
                    com.google.gson.Gson gson = new com.google.gson.GsonBuilder().create();
                    _TC = gson.fromJson(reader, TranslationContext.class);
                    reader.close();
                    log.info("ControlServiceCoordinator.processNewModel(): Unserialized _TC data from file: {}", fileName);

                    CamelToEplTranslator translator =
                            applicationContext.getBean(CamelToEplTranslator.class);
                    translator.printResults(_TC, null);
                } catch (java.io.IOException ex) {
                    log.error("ControlServiceCoordinator.processNewModel(): FAILED to unserialize _TC from file: {} : Exception: ", fileName, ex);
                }
            }
        }

        // Signal Event Processing Network and/or User Application to stop/pause
//XXX: TODO: +++++++++++++++++++++
// e.g.  cloudiatorHelper.stopApplication();
// -AND- baguette.signalEPN(STOP);

        // Retrieve Metric Variable Values (MVV) from CP model
        Map<String, Double> constants = new HashMap<>();
        if (!properties.isSkipMvvRetrieve()) {
            if (StringUtils.isNotBlank(cpModelId)) {
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
                                    new CscStatementSubscriber("Subscriber_" + cnt++, topicName, rule, brokerCep, passwordUtil)
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
            log.info("ControlServiceCoordinator.processNewModel(): Re-configuring Baguette Server: camel-model-id={}", camelModelId);
            try {
                baguette.setTopologyConfiguration(_TC, constants, upperwareGrouping, brokerCep);
            } catch (Exception ex) {
                log.error("ControlServiceCoordinator.processNewModel(): EXCEPTION while starting Baguette server: camel-model-id={}", camelModelId, ex);
            }
        } else {
            log.warn("ControlServiceCoordinator.processNewModel(): Skipping Baguette Server setup due to configuration");
        }

        // (Re-)Configure MetaSolver
        if (!properties.isSkipMetasolver()) {
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
            log.info("ControlServiceCoordinator.processNewModel(): MetaSolver configuration in JSON: {}", json);
            if (StringUtils.isNotEmpty(metaSolverEndpoint)) {
                try {
                    log.info("ControlServiceCoordinator.processNewModel(): Calling MetaSolver: endpoint={}, body={}", metaSolverEndpoint, json);
                    //String metaSolverResponse = restTemplate.postForObject(metaSolverEndpoint, json, String.class);
                    HttpEntity<String> entity = createHttpEntity(String.class, json, jwtToken);
                    final ResponseEntity<String> response = restTemplate.postForEntity(metaSolverEndpoint, entity, String.class);
                    String metaSolverResponse = response.getBody();
                    log.info("ControlServiceCoordinator.processNewModel(): MetaSolver response: endpoint={}, response={}", metaSolverEndpoint, metaSolverResponse);
                } catch (Exception ex) {
                    log.error("ControlServiceCoordinator.processNewModel(): Failed to call MetaSolver: endpoint={}, body={}\nEXCEPTION: ", metaSolverEndpoint, json, ex);
                }
            } else {
                log.warn("ControlServiceCoordinator.processNewModel(): MetaSolver endpoint is empty. Skipping Metasolver configuration");
            }

        } else {
            log.warn("ControlServiceCoordinator.processNewModel(): Skipping MetaSolver setup due to configuration");
        }

        // (Re-)Configure LA Solver
//XXX: TODO: +++++++++++++++++++++
// e.g.  metaSolver.unsubscribe(); metaSolver.setConfiguration(...); metaSolver.subscribe();
		/*if (! properties.isSkipLASolver()) {
			//log.info("ControlServiceCoordinator.processNewModel(): Re-configuring MetaSolver: ???????????????");
		} else {
			//log.warn("ControlServiceCoordinator.processNewModel(): Skipping MetaSolver setup due to configuration");
		}*/

        // Cache _TC in order to reply to Adapter queries about component-to-sensor mappings and sensor-configuration
        log.info("ControlServiceCoordinator.processNewModel(): Cache translation results: camel-model-id={}", camelModelId);
        camelToTcCache.put(camelModelId, _TC);

        // Signal Event Processing Network and/or User Application to start/resume
//XXX: TODO: +++++++++++++++++++++
// e.g.  baguette.signalEPN(START);
// -AND- cloudiatorHelper.startApplication();

        // Notify ESB, if 'notificationUri' is provided
        if (!properties.isSkipEsbNotification()) {
            if (StringUtils.isNotBlank(notificationUri)) {
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
    }

    protected void _processCpModel(String cpModelId, String notificationUri, String requestUuid, String jwtToken) {
        log.info("ControlServiceCoordinator._processCpModel(): BEGIN: cp-model-id={}, notification-uri={}, request-uuid={}", cpModelId, notificationUri, requestUuid);
        log.info("ControlServiceCoordinator._processCpModel(): Current camel-model-id={}", currentCamelModelId);
        TranslationContext _TC = this.currentTC;

        // Retrieve Metric Variable Values (MVV) from CP model
        Map<String, Double> constants = new HashMap<>();
        if (!properties.isSkipMvvRetrieve()) {
            if (StringUtils.isNotBlank(cpModelId)) {
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
                notificationUri = notificationUri.trim();
                log.info("ControlServiceCoordinator._processCpModel(): Notifying ESB: {}", notificationUri);
                sendSuccessNotification(null, notificationUri, requestUuid, jwtToken);
                log.info("ControlServiceCoordinator._processCpModel(): ESB notified: {}", notificationUri);
            }
        } else {
            log.warn("ControlServiceCoordinator._processCpModel(): Skipping ESB notification due to configuration");
        }

        log.info("ControlServiceCoordinator._processCpModel(): END: cp-model-id={}", cpModelId);
    }

    // ------------------------------------------------------------------------------------------------------------

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

    @RequiredArgsConstructor
    @Getter
    public static class CscStatementSubscriber implements StatementSubscriber {
        private final String name;
        private final String topic;
        private final String statement;
        private final BrokerCepService brokerCep;
        private final PasswordUtil passwordUtil;

        public void update(java.util.Map<String, Object> eventMap) {
            try {
                log.info("- New event: subscriber={}, topic={}, payload={}", name, topic, eventMap);

                // Publish new event to Local Broker topic
                String localBrokerUrl = brokerCep.getBrokerCepProperties().getBrokerUrlForConsumer();
                String username = brokerCep.getBrokerUsername();
                String password = brokerCep.getBrokerPassword();
                log.trace("- Publishing event to local broker: subscriber={}, local-broker={}, username={}, password={}, topic={}, payload={}",
                        name, localBrokerUrl, username, passwordUtil.getPasswordEncoder().encode(password), topic, eventMap);
                brokerCep.publishEvent(localBrokerUrl, username, password, topic, eventMap);
                log.debug("- Event published to local broker: subscriber={}, local-broker={}, username={}, password={}, topic={}, payload={}",
                        name, localBrokerUrl, username, passwordUtil.getPasswordEncoder().encode(password), topic, eventMap);

            } catch (Exception ex) {
                log.error("- New event: ERROR: subscriber={}, topic={}, exception=", name, topic, ex);
            }
        }
    }


    // ------------------------------------------------------------------------------------------------------------
    // Translation information query methods
    // ------------------------------------------------------------------------------------------------------------

    public TranslationContext getTranslationContextOfCamelModel(String camelModelId) {
        return camelToTcCache.get(camelModelId);
    }

    public List<Monitor> getSensorsOfCamelModel(String camelModelId) {
        TranslationContext _tc = camelToTcCache.get(camelModelId);
        if (_tc==null) return Collections.emptyList();
        List<Monitor> sensors = new ArrayList<>(_tc.MON);
        return sensors;
    }

    public Set getMetricConstraints(String camelModelId) {
        TranslationContext _tc = camelToTcCache.get(camelModelId);
        if (_tc==null) return Collections.emptySet();
        return _tc.getMetricConstraints();
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
        if (properties.isExitAllowed()) {
            // Signal SpringBootApp to exit
            log.info("ControlServiceCoordinator.emsExit(): Signaling exit...");
            ControlServiceApplication.exitApp(properties.getExitCode(), properties.getExitGracePeriod());
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

    private final static String EVENT_DEBUG_OK = "OK";
    private final static String EVENT_DEBUG_ERROR = "ERROR";
    private final static String EVENT_DEBUG_DISABLED = "EVENT DEBUGGING IS DISABLED";
    private final static String BAGUETTE_DISABLED = "BAGUETTE SERVER IS DISABLED";
    private final static String BAGUETTE_NOT_RUNNING = "BAGUETTE SERVER IS NOT RUNNING";

    private String eventLogEnd(String method, String result) {
        log.debug("ControlServiceCoordinator.{}(): END: result={}", method, result);
        return result;
    }

    private String eventSendCommandToClient(String method, String clientId, String command) {
        // Check status
        if (!properties.isEventDebugEnabled()) return eventLogEnd(method, EVENT_DEBUG_DISABLED);
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
                    return eventLogEnd(method, EVENT_DEBUG_ERROR);
                }
            } else {
                log.debug("ControlServiceCoordinator.{}(): ERROR: Unsupported command for client-id=0 : {}", method, command);
                // Log error
                return eventLogEnd(method, EVENT_DEBUG_ERROR);
            }
        } else if ("*".equals(clientId))
            baguette.sendToActiveClients(command);
        else
            baguette.sendToClient("#"+clientId, command);

        // Log success
        return eventLogEnd(method, EVENT_DEBUG_OK);
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
        return baguette.getActiveClients();
    }

    public Map<String, Map<String, String>> clientMap() {
        log.debug("ControlServiceCoordinator.clientMap(): BEGIN:");
        return baguette.getActiveClientsMap();
    }

    public String clientCommandSend(String clientId, String command) {
        log.debug("ControlServiceCoordinator.clientCommandSend(): BEGIN: client={}, command={}", clientId, command);
        return eventSendCommandToClient("clientCommandSend", clientId, command);
    }

    // ------------------------------------------------------------------------------------------------------------
}
