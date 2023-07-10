/*
 * Copyright (C) 2017-2023 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control;

import com.google.gson.GsonBuilder;
import eu.melodic.event.baguette.server.BaguetteServer;
import eu.melodic.event.baguette.server.NodeRegistry;
import eu.melodic.event.baguette.server.ServerCoordinator;
import eu.melodic.event.brokercep.BrokerCepService;
import eu.melodic.event.brokercep.BrokerCepStatementSubscriber;
import eu.melodic.event.brokercep.event.EventMap;
import eu.melodic.event.control.collector.netdata.ServerNetdataCollector;
import eu.melodic.event.control.properties.ControlServiceProperties;
import eu.melodic.event.control.util.TranslationContextMonitorGsonDeserializer;
import eu.melodic.event.translate.NoopTranslator;
import eu.melodic.event.translate.Translator;
import eu.melodic.event.translate.model.*;
import eu.melodic.event.translate.mvv.MetricVariableValuesService;
import eu.melodic.event.control.util.mvv.NoopMetricVariableValuesServiceImpl;
import eu.melodic.event.translate.TranslationContext;
import eu.melodic.event.translate.dag.DAGNode;
import eu.melodic.event.util.NetUtil;
import eu.melodic.event.util.PasswordUtil;
import eu.melodic.event.models.commons.NotificationResult;
import eu.melodic.event.models.commons.NotificationResultImpl;
import eu.melodic.event.models.commons.Watermark;
import eu.melodic.event.models.commons.WatermarkImpl;
import eu.melodic.event.models.services.CamelModelNotificationRequest;
import eu.melodic.event.models.services.CamelModelNotificationRequestImpl;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ControlServiceCoordinator implements InitializingBean {

    private final ApplicationContext applicationContext;
    private final ControlServiceProperties properties;
    private final BaguetteServer baguette;
    private final NodeRegistry nodeRegistry;
    private final WebClient webClient;
    private final PasswordUtil passwordUtil;

    private final List<Translator> translatorImplementations;
    private Translator translator;                      // Will be populated in 'afterPropertiesSet()'

    private final List<MetricVariableValuesService> mvvServiceImplementations;
    private MetricVariableValuesService mvvService;     // Will be populated in 'afterPropertiesSet()'

    @Getter private BrokerCepService brokerCep;

    private final AtomicBoolean inUse = new AtomicBoolean();
    private final Map<String, TranslationContext> appModelToTcCache = new HashMap<>();

    @Getter private final String reference = UUID.randomUUID().toString();

    @Getter private String currentAppModelId;
    @Getter private String currentCpModelId;
    private TranslationContext currentTC;

    private ServerNetdataCollector netdataCollector;

    public enum EMS_STATE {
        IDLE, INITIALIZING, RECONFIGURING, READY, ERROR
    }

    @Getter private EMS_STATE currentEmsState = EMS_STATE.IDLE;
    @Getter private String currentEmsStateMessage;
    @Getter private long currentEmsStateChangeTimestamp;


    @Override
    public void afterPropertiesSet() throws Exception {
        initTranslator();
        initMvvService();

        // Run configuration checks and throw exceptions early (before actually using EMS)
        if (properties.isSkipTranslation()) {
            if (StringUtils.isBlank(properties.getTcLoadFile()))
                throw new IllegalArgumentException("Model translation will be skipped (see property control.skip-translation), but no Translation Context file or pattern has been set. Check property: control.tc-load-file");
            log.warn("Model translation will be skipped, and a Translation Context file will be used: tc-file-pattern={}", properties.getTcLoadFile());
        }
    }

    private void initMvvService() {
        // Initialize MVV service
        log.debug("ControlServiceCoordinator.afterPropertiesSet():  MVV service implementations: {}", mvvServiceImplementations);
        if (mvvServiceImplementations.size() == 1) {
            mvvService = mvvServiceImplementations.get(0);
        } else if (mvvServiceImplementations.isEmpty()) {
            throw new IllegalArgumentException("No MVV service implementation found");
        } else {
            mvvService = mvvServiceImplementations.stream()
                    .filter(s -> s!=null && !(s instanceof NoopMetricVariableValuesServiceImpl))
                    .findAny()
                    .orElse(new NoopMetricVariableValuesServiceImpl());
        }
        log.debug("ControlServiceCoordinator.afterPropertiesSet():  MVV service implementation selected: {}", mvvService);
        mvvService.init();
        log.debug("ControlServiceCoordinator.afterPropertiesSet():  MVV service initialized");
    }

    private void initTranslator() {
        log.debug("ControlServiceCoordinator.getTranslator():  Translator implementations: {}", translatorImplementations);
        if (translatorImplementations.size() == 1) {
            translator = translatorImplementations.get(0);
        } else if (translatorImplementations.isEmpty()) {
            throw new IllegalArgumentException("No Translator implementations found");
        } else {
            translator = translatorImplementations.stream()
                    .filter(s -> s!=null && !(s instanceof NoopTranslator))
                    .findAny()
                    .orElse(new NoopTranslator());
        }
        log.debug("ControlServiceCoordinator.getTranslator():  Translator implementation selected: {}", translator);

        log.info("ControlServiceCoordinator: Effective translator: {}", translator.getClass().getName());
    }

    public String getServerIpAddress() {
        return (properties.getIpSetting() == ControlServiceProperties.IpSetting.DEFAULT_IP)
                ? NetUtil.getDefaultIpAddress()
                : NetUtil.getPublicIpAddress();
    }

    public String getAppModelPath() {
        return currentAppModelId;
    }

    public String getCpModelPath() {
        return currentCpModelId;
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
        String preloadAppModel = properties.getPreload().getCamelModel();
        String preloadCpModel = properties.getPreload().getCpModel();
        if (StringUtils.isNotBlank(preloadAppModel)) {
            log.info("===================================================================================================");
            log.info("ControlServiceCoordinator.preloadModels(): Preloading models: app-model={}, cp-model={}",
                    preloadAppModel, preloadCpModel);
            processAppModel(preloadAppModel, preloadCpModel, ControlServiceRequestInfo.EMPTY);
        } else {
            log.info("ControlServiceCoordinator.preloadModels(): No model to preload");
        }
    }

    // ------------------------------------------------------------------------------------------------------------

    @Async
    public void processAppModel(String appModelId, String cpModelId, ControlServiceRequestInfo requestInfo) {
        _loackAndProcessModel(appModelId, cpModelId, requestInfo, "processAppModel()", () -> {
            // Call '_processNewModels()' to do actual processing
            _processAppModels(appModelId, cpModelId, requestInfo);
            this.currentAppModelId = _normalizeModelId(appModelId);
            this.currentCpModelId = _normalizeModelId(cpModelId);
        });
    }

    @Async
    public void processCpModel(String cpModelId, ControlServiceRequestInfo requestInfo) {
        _loackAndProcessModel(null, cpModelId, requestInfo, "processCpModel()", () -> {
            // Call '_processCpModel()' to do actual processing
            _processCpModel(cpModelId, requestInfo);
            this.currentCpModelId = _normalizeModelId(cpModelId);
        });
    }

    @Async
    public void setConstants(@NonNull Map<String,Double> constants, ControlServiceRequestInfo requestInfo) {
        _loackAndProcessModel(null, null, requestInfo, "processCpModel()", () -> {
            // Call '_processCpModel()' to do actual processing
            _setConstants(constants, requestInfo);
        });
    }

    protected void _loackAndProcessModel(String appModelId, String cpModelId, ControlServiceRequestInfo requestInfo, String caller, Runnable callback) {
        // Acquire lock of this coordinator
        if (!inUse.compareAndSet(false, true)) {
            String mesg = "ControlServiceCoordinator."+caller+": ERROR: Coordinator is in use. Exits immediately";
            log.warn(mesg);
            if (!properties.isSkipEsbNotification()) {
                sendErrorNotification(appModelId, requestInfo, mesg, mesg);
            } else {
                log.warn("ControlServiceCoordinator."+caller+": Skipping ESB notification due to configuration");
            }
            return;
        }

        try {
            callback.run();
        } catch (Exception ex) {
            setCurrentEmsState(EMS_STATE.ERROR, ex.getMessage());

            String mesg = "ControlServiceCoordinator."+caller+": EXCEPTION: " + ex;
            log.error(mesg, ex);
            if (!properties.isSkipEsbNotification()) {
                sendErrorNotification(appModelId, requestInfo, mesg, mesg);
            } else {
                log.warn("ControlServiceCoordinator"+caller+": Skipping ESB notification due to configuration");
            }
        } finally {
            // Release lock of this coordinator
            inUse.compareAndSet(true, false);
        }
    }

    // ------------------------------------------------------------------------------------------------------------

    protected void _processAppModels(String appModelId, String cpModelId, ControlServiceRequestInfo requestInfo)
    {
        log.info("ControlServiceCoordinator.processAppModel(): BEGIN: app-model-id={}, cp-model-id={}, request-info={}", appModelId, cpModelId, requestInfo);

        // Translate model into Translation Context (with EPL rules etc)
        TranslationContext _TC;
        if (!properties.isSkipTranslation()) {
            _TC = translateAppModelAndStore(appModelId);
        } else {
            log.warn("ControlServiceCoordinator.processAppModel(): Skipping translation due to configuration");
            _TC = loadStoredTranslationContext(appModelId);
        }

        // Retrieve Metric Variable Values (MVV) from CP model - i.e. constants
        Map<String, Double> constants = new HashMap<>();
        if (!properties.isSkipMvvRetrieve()) {
            constants = retrieveConstantsFromCpModel(cpModelId, _TC, EMS_STATE.INITIALIZING);
        } else {
            log.warn("ControlServiceCoordinator.processAppModel(): Skipping MVV retrieval due to configuration");
        }

        // (Re-)Configure Broker and CEP
        String upperwareGrouping = properties.getUpperwareGrouping();
        if (!properties.isSkipBrokerCep()) {
            configureBrokerCep(appModelId, _TC, constants, upperwareGrouping);
        } else {
            log.warn("ControlServiceCoordinator.processAppModel(): Skipping Broker-CEP setup due to configuration");
        }

        // Process placeholders in sink type configurations
        String brokerUrlForClients = brokerCep.getBrokerCepProperties().getBrokerUrlForClients();
        processPlaceholdersInMonitors(_TC, brokerUrlForClients);

        // (Re-)Configure Baguette server
        if (!properties.isSkipBaguette()) {
            configureBaguetteServer(appModelId, _TC, constants, upperwareGrouping);
        } else {
            log.warn("ControlServiceCoordinator.processAppModel(): Skipping Baguette Server setup due to configuration");
        }

        // Start/Stop Netdata collector
        if (!properties.isSkipCollectors()) {
            startNetdataCollector(appModelId);
        } else {
            log.warn("ControlServiceCoordinator.processAppModel(): Skipping Collectors setup due to configuration");
        }

        // (Re-)Configure MetaSolver
        if (!properties.isSkipMetasolver()) {
            configureMetaSolver(_TC, requestInfo.getJwtToken());
        } else {
            log.warn("ControlServiceCoordinator.processAppModel(): Skipping MetaSolver setup due to configuration");
        }

        // Cache _TC in order to reply to Adapter queries about component-to-sensor mappings and sensor-configuration
        log.info("ControlServiceCoordinator.processAppModel(): Cache translation results: app-model-id={}", appModelId);
        appModelToTcCache.put(_normalizeModelId(appModelId), _TC);

        // Notify ESB, if 'notificationUri' is provided
        if (!properties.isSkipEsbNotification()) {
            notifyESB(appModelId, requestInfo, EMS_STATE.INITIALIZING);
        } else {
            log.warn("ControlServiceCoordinator.processAppModel(): Skipping ESB notification due to configuration");
        }

        this.currentTC = _TC;
        log.info("ControlServiceCoordinator.processAppModel(): END: app-model-id={}", appModelId);

        setCurrentEmsState(EMS_STATE.READY, null);
    }

    protected void _processCpModel(String cpModelId, ControlServiceRequestInfo requestInfo) {
        log.info("ControlServiceCoordinator._processCpModel(): BEGIN: cp-model-id={}, request-info={}", cpModelId, requestInfo);
        log.info("ControlServiceCoordinator._processCpModel(): Current app-model-id={}", currentAppModelId);
        TranslationContext _TC = this.currentTC;

        // Retrieve Metric Variable Values (MVV) from CP model
        Map<String, Double> constants = new HashMap<>();
        if (!properties.isSkipMvvRetrieve()) {
            constants = retrieveConstantsFromCpModel(cpModelId, _TC, EMS_STATE.RECONFIGURING);
        } else {
            log.warn("ControlServiceCoordinator._processCpModel(): Skipping MVV retrieval due to configuration");
        }

        // Set MVV constants in Broker-CEP and Baguette Server, and then notify ESB
        _setConstants(constants, requestInfo);

        log.info("ControlServiceCoordinator._processCpModel(): END: cp-model-id={}", cpModelId);

        setCurrentEmsState(EMS_STATE.READY, null);
    }

    protected void _setConstants(@NonNull Map<String,Double> constants, ControlServiceRequestInfo requestInfo) {
        log.info("ControlServiceCoordinator.setConstants(): BEGIN: constants={}, request-info={}", constants, requestInfo);
        log.info("ControlServiceCoordinator.setConstants(): constants={}", constants);

        // Retrieve Metric Variable Values (MVV) from CP model
        if (properties.isSkipMvvRetrieve()) {
            log.info("ControlServiceCoordinator.setConstants(): isSkipMvvRetrieve is true, but constants processing will continue");
        }

        // (Re-)Configure Broker and CEP
        if (!properties.isSkipBrokerCep()) {
            reconfigureBrokerCep(constants);
        } else {
            log.warn("ControlServiceCoordinator.setConstants(): Skipping Broker-CEP setup due to configuration");
        }

        // (Re-)Configure Baguette server
        if (!properties.isSkipBaguette()) {
            reconfigureBaguetteServer(constants);
        } else {
            log.warn("ControlServiceCoordinator.setConstants(): Skipping Baguette Server setup due to configuration");
        }

        // Notify ESB, if 'notificationUri' is provided
        if (!properties.isSkipEsbNotification()) {
            notifyESB(null, requestInfo, EMS_STATE.RECONFIGURING);
        } else {
            log.warn("ControlServiceCoordinator.setConstants(): Skipping ESB notification due to configuration");
        }

        log.info("ControlServiceCoordinator.setConstants(): END: constants={}", constants);

        setCurrentEmsState(EMS_STATE.READY, null);
    }

    private TranslationContext translateAppModelAndStore(String appModelId) {
        TranslationContext _TC;
        setCurrentEmsState(EMS_STATE.INITIALIZING, "Retrieving and translating model");

        log.info("ControlServiceCoordinator.translateAppModelAndStore(): Model translation: model-id={}", appModelId);
        _TC = translator.translate(appModelId);
        log.debug("ControlServiceCoordinator.translateAppModelAndStore(): Model translation: RESULTS: {}", _TC);

        // serialize 'TranslationContext' to file
        String fileName = properties.getTcSaveFile();
        if (StringUtils.isNotBlank(fileName)) {
            try {
                setCurrentEmsState(EMS_STATE.INITIALIZING, "Storing translation context to file");

                fileName = getTcFileName(appModelId, fileName);
                if (Paths.get(fileName).toFile().exists()) {
                    log.warn("ControlServiceCoordinator.translateAppModelAndStore(): The specified Translation Context file already exists. Its contents will be overwritten: tc-file-pattern={}, tc-file={}", properties.getTcLoadFile(), fileName);
                }

                // Store _TC in a file
                log.debug("ControlServiceCoordinator.translateAppModelAndStore(): Start serializing _TC data in file: {}", fileName);
                com.google.gson.Gson gson = new GsonBuilder().setPrettyPrinting().create();
                java.io.Writer writer = new java.io.FileWriter(fileName);
                gson.toJson(_TC, writer);
                writer.close();
                log.debug("ControlServiceCoordinator.translateAppModelAndStore(): Serialized _TC data in file: {}", fileName);
                log.info("ControlServiceCoordinator.translateAppModelAndStore(): Saved translation data in file: {}", fileName);

            } catch (IOException ex) {
                log.error("ControlServiceCoordinator.translateAppModelAndStore(): FAILED to serialize _TC to file: {} : Exception: ", fileName, ex);
            }
        }
        return _TC;
    }

    private TranslationContext loadStoredTranslationContext(String appModelId) {
        TranslationContext _TC;

        // deserialize 'TranslationContext' from file
        String fileName = properties.getTcLoadFile();
        if (StringUtils.isNotBlank(fileName)) {
            setCurrentEmsState(EMS_STATE.INITIALIZING, "Loading translation context from file");

            try {
                fileName = getTcFileName(appModelId, fileName);
                if (! Paths.get(fileName).toFile().exists()) {
                    log.error("ControlServiceCoordinator.loadStoredTranslationContext(): The specified Translation Context file does not exist: tc-file-pattern={}, tc-file={}", properties.getTcLoadFile(), fileName);
                    throw new IllegalArgumentException("The specified Translation Context file does not exist. Check property: control.tc-load-file=" + properties.getTcLoadFile() + ", file-name=" + fileName);
                }
                log.info("ControlServiceCoordinator.loadStoredTranslationContext(): Loading translator data from file: {}", fileName);
                log.debug("ControlServiceCoordinator.loadStoredTranslationContext(): Start deserializing _TC data from file: {}", fileName);
                java.io.Reader reader = new java.io.FileReader(fileName);
                com.google.gson.Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Monitor.class, new TranslationContextMonitorGsonDeserializer())
                        .create();
                _TC = gson.fromJson(reader, TranslationContext.class);
                reader.close();
                log.debug("ControlServiceCoordinator.loadStoredTranslationContext(): Deserialized _TC data from file: {}", fileName);

                // Print resulting Translation Context
                translator.printResults(_TC, null);
            } catch (IOException ex) {
                log.error("ControlServiceCoordinator.loadStoredTranslationContext(): FAILED to deserialize _TC from file: {} : Exception: ", fileName, ex);
                throw new IllegalArgumentException("Failed to load translation data from file: " + fileName, ex);
            }
        } else {
            log.error("ControlServiceCoordinator.loadStoredTranslationContext(): No translation context file has been set");
            throw new IllegalArgumentException("No translation context file has been set");
        }
        return _TC;
    }

    private String getTcFileName(@NonNull String appModelId, @NonNull String fileName) {
        appModelId = StringUtils.removeStart(appModelId, "/");
        return String.format(fileName, appModelId.replaceAll("[^\\p{L}\\d]", "_"));
    }

    private Map<String, Double> retrieveConstantsFromCpModel(String cpModelId, TranslationContext _TC, EMS_STATE emsState) {
        Map<String, Double> constants = Collections.emptyMap();
        if (StringUtils.isNotBlank(cpModelId)) {
            setCurrentEmsState(emsState, "Retrieving MVVs from CP model");

            try {
                log.info("ControlServiceCoordinator.retrieveConstantsFromCpModel(): Retrieving MVVs from CP model: cp-model-id={}", cpModelId);

                // Retrieve constant names from '_TC.MVV_CP' and values from a given CP model
                log.info("ControlServiceCoordinator.retrieveConstantsFromCpModel(): Looking for MVV_CP's: {}", _TC.getCompositeMetricVariables());
                constants = mvvService.getMatchingMetricVariableValues(cpModelId, _TC);
                log.info("ControlServiceCoordinator.retrieveConstantsFromCpModel(): MVVs retrieved from CP model: cp-model-id={}, MVVs={}", cpModelId, constants);

            } catch (Exception ex) {
                log.error("ControlServiceCoordinator.retrieveConstantsFromCpModel(): EXCEPTION while retrieving MVVs from CP model: cp-model-id={}", cpModelId, ex);
            }
        } else {
            log.error("ControlServiceCoordinator.retrieveConstantsFromCpModel(): No CP model have been provided");
        }
        return constants;
    }

    private void configureBrokerCep(String appModelId, TranslationContext _TC, Map<String, Double> constants, String upperwareGrouping) {
        setCurrentEmsState(EMS_STATE.INITIALIZING, "initializing Broker-CEP");

        try {
            // Initializing Broker-CEP module if necessary
            if (brokerCep == null) {
                log.debug("ControlServiceCoordinator.configureBrokerCep(): Broker-CEP: Initializing...");
                brokerCep = applicationContext.getBean(BrokerCepService.class);
                log.debug("ControlServiceCoordinator.configureBrokerCep(): Broker-CEP: Initializing...ok");
            }

            // Get event types for GLOBAL grouping (i.e. that of Upperware)
            log.info("ControlServiceCoordinator.configureBrokerCep(): Broker-CEP: Upperware grouping: {}", upperwareGrouping);
            Set<String> eventTypeNames = _TC.getG2T().get(upperwareGrouping);
            log.info("ControlServiceCoordinator.configureBrokerCep(): Broker-CEP: Configuration of Event Types: {}", eventTypeNames);
            if (eventTypeNames == null || eventTypeNames.size() == 0)
                throw new RuntimeException("Broker-CEP: No event types for GLOBAL grouping");

            // Clear any previous event types, statements or function definitions and register the new ones
            brokerCep.clearState();
            brokerCep.addEventTypes(eventTypeNames, EventMap.getPropertyNames(), EventMap.getPropertyClasses());

            log.info("ControlServiceCoordinator.configureBrokerCep(): Broker-CEP: Constants: {}", constants);
            brokerCep.setConstants(constants);

            log.info("ControlServiceCoordinator.configureBrokerCep(): Broker-CEP: Function definitions: {}", _TC.getFunctionDefinitions());
            brokerCep.addFunctionDefinitions(_TC.getFunctionDefinitions());

            Map<String, Set<String>> ruleStatements = _TC.getG2R().get(upperwareGrouping);
            log.info("ControlServiceCoordinator.configureBrokerCep(): Broker-CEP: Configuration of EPL statements: {}", ruleStatements);
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
                log.warn("ControlServiceCoordinator.configureBrokerCep(): Broker-CEP: No EPL statements found for GLOBAL grouping");
            }
        } catch (Exception ex) {
            log.error("ControlServiceCoordinator.configureBrokerCep(): EXCEPTION while initializing Broker-CEP of Upperware: app-model-id={}", appModelId, ex);
        }
    }

    private void reconfigureBrokerCep(Map<String, Double> constants) {
        try {
            setCurrentEmsState(EMS_STATE.RECONFIGURING, "Reconfiguring Broker-CEP");

            // Initializing Broker-CEP module if necessary
            if (brokerCep == null) {
                log.debug("ControlServiceCoordinator.reconfigureBrokerCep(): Broker-CEP: Initializing...");
                brokerCep = applicationContext.getBean(BrokerCepService.class);
                log.debug("ControlServiceCoordinator.reconfigureBrokerCep(): Broker-CEP: Initializing...ok");
            }

            log.info("ControlServiceCoordinator.reconfigureBrokerCep(): Passing constants to Broker-CEP: {}", constants);
            brokerCep.setConstants(constants);
        } catch (Exception ex) {
            log.error("ControlServiceCoordinator.reconfigureBrokerCep(): EXCEPTION while initializing Broker-CEP with constants: constants={}", constants, ex);
        }
    }

    private static void processPlaceholdersInMonitors(TranslationContext _TC, String brokerUrlForClients) {
        for (Monitor mon : _TC.getMON()) {
            if (mon.getSinks()!=null) {
                for (Sink s : mon.getSinks()) {
                    s.getConfiguration().entrySet().forEach(entry -> {
                        if (entry.getValue() != null)
                            entry.setValue(entry.getValue().replace("%{BROKER_URL}%", brokerUrlForClients));
                    });
                }
            }
        }
    }

    private void configureBaguetteServer(String appModelId, TranslationContext _TC, Map<String, Double> constants, String upperwareGrouping) {
        setCurrentEmsState(EMS_STATE.INITIALIZING, "Initializing Baguette Server");

        log.info("ControlServiceCoordinator.configureBaguetteServer(): Re-configuring Baguette Server: app-model-id={}", appModelId);
        try {
            baguette.setTopologyConfiguration(_TC, constants, upperwareGrouping, brokerCep);
        } catch (Exception ex) {
            log.error("ControlServiceCoordinator.configureBaguetteServer(): EXCEPTION while starting Baguette server: app-model-id={}", appModelId, ex);
        }
    }

    private void reconfigureBaguetteServer(Map<String, Double> constants) {
        setCurrentEmsState(EMS_STATE.RECONFIGURING, "Reconfiguring Baguette Server");

        log.info("ControlServiceCoordinator.reconfigureBaguetteServer(): Re-configuring Baguette Server with constants: {}", constants);
        try {
            baguette.sendConstants(constants);
        } catch (Exception ex) {
            log.error("ControlServiceCoordinator.reconfigureBaguetteServer(): EXCEPTION while configuring Baguette server: constants={}", constants, ex);
        }
    }

    private void startNetdataCollector(String appModelId) {
        // Stop any running Netdata collector instance
        if (netdataCollector!=null) {
            log.info("ControlServiceCoordinator.startNetdataCollector(): Stopping NetdataCollector: app-model-id={}", appModelId);
            try {
                netdataCollector.stop();
            } catch (Exception ex) {
                log.error("ControlServiceCoordinator.startNetdataCollector(): EXCEPTION while stopping NetdataCollector: app-model-id={}", appModelId, ex);
            }
        }

        // Starting new Netdata collector instance, if needed
        ServerCoordinator serverCoordinator = nodeRegistry.getCoordinator();
        if (! serverCoordinator.supportsAggregators()) {
            if (netdataCollector==null) {
                netdataCollector = applicationContext.getBean(ServerNetdataCollector.class);
            }
            log.info("ControlServiceCoordinator.startNetdataCollector(): Starting NetdataCollector: app-model-id={}", appModelId);
            try {
                netdataCollector.start();
            } catch (Exception ex) {
                log.error("ControlServiceCoordinator.startNetdataCollector(): EXCEPTION while starting NetdataCollector: app-model-id={}", appModelId, ex);
            }
        } else {
            log.info("ControlServiceCoordinator.startNetdataCollector(): NetdataCollector is not needed (will not start it): app-model-id={}", appModelId);
        }
    }

    private void configureMetaSolver(TranslationContext _TC, String jwtToken) {
        setCurrentEmsState(EMS_STATE.INITIALIZING, "Sending configuration to MetaSolver");

        // Check that MetaSolver configuration URL has been set
        if (StringUtils.isEmpty(properties.getMetasolverConfigurationUrl())) {
            log.warn("ControlServiceCoordinator.configureMetaSolver(): MetaSolver endpoint is empty. Skipping Metasolver configuration");
            return;
        }

        // Get scaling event and SLO topics from _TC
        Set<String> scalingTopics = new HashSet<>();
        scalingTopics.addAll(_TC.getE2A().keySet());
        scalingTopics.addAll(_TC.getSLO());
        log.debug("ControlServiceCoordinator.configureMetaSolver(): MetaSolver configuration: scaling-topics: {}", scalingTopics);

        // Get top-level metric topics from _TC
        Set<String> metricTopics = _TC.getDAG().getTopLevelNodes().stream()
                .map(DAGNode::getElementName)
                .filter(elementName -> !scalingTopics.contains(elementName))
                .collect(Collectors.toSet());
        log.debug("ControlServiceCoordinator.configureMetaSolver(): MetaSolver configuration: metric-topics: {}", metricTopics);

        // Prepare subscription configurations
        String upperwareBrokerUrl = brokerCep != null ? brokerCep.getBrokerCepProperties().getBrokerUrlForClients() : null;
        boolean usesAuthentication = brokerCep.getBrokerCepProperties().isAuthenticationEnabled();
        String username = usesAuthentication ? brokerCep.getBrokerUsername() : null;
        String password = usesAuthentication ? brokerCep.getBrokerPassword() : null;
        String certificate = brokerCep.getBrokerCertificate();
        log.debug("ControlServiceCoordinator.configureMetaSolver(): Local Broker: uses-authentication={}, username={}, password={}, has-certificate={}",
                usesAuthentication, username, passwordUtil.encodePassword(password), StringUtils.isNotBlank(certificate));
        log.trace("ControlServiceCoordinator.configureMetaSolver(): Local Broker: broker-certificate={}", certificate);

        if (StringUtils.isBlank(upperwareBrokerUrl)) {
            log.warn("ControlServiceCoordinator.configureMetaSolver(): No Broker URL has been specified or Broker-CEP module is deactivated");
        }
        List<Map<String, String>> subscriptionConfigs = new ArrayList<>();
        for (String t : scalingTopics)
            subscriptionConfigs.add(_prepareSubscriptionConfig(upperwareBrokerUrl, username, password, certificate, t, "", "SCALE"));
        for (String t : metricTopics)
            subscriptionConfigs.add(_prepareSubscriptionConfig(upperwareBrokerUrl, username, password, certificate, t, "", "MVV"));
        log.debug("ControlServiceCoordinator.configureMetaSolver(): MetaSolver subscriptions configuration: {}", subscriptionConfigs);

        // Retrieve MVV to Current-Config MVV map
        Map<String, String> mvvMap = _TC.getCompositeMetricVariables();
        log.debug("ControlServiceCoordinator.configureMetaSolver(): MetaSolver MVV configuration: {}", mvvMap);

        // Prepare MetaSolver configuration
        Map<String,Object> msConfig = new HashMap<>();
        msConfig.put("subscriptions", subscriptionConfigs);
        msConfig.put("mvv", mvvMap);

        // POST configuration to MetaSolver
        String metaSolverEndpoint = properties.getMetasolverConfigurationUrl();
        com.google.gson.Gson gson = new com.google.gson.Gson();
        String json = gson.toJson(msConfig);
        log.debug("ControlServiceCoordinator.configureMetaSolver(): MetaSolver configuration in JSON: {}", json);

        try {
            log.info("ControlServiceCoordinator.configureMetaSolver(): Calling MetaSolver: endpoint={}", metaSolverEndpoint);
            ResponseEntity<String> response = webClient.post()
                    .uri(metaSolverEndpoint)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, jwtToken)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(json)
                    .retrieve()
                    .toEntity(String.class)
                    .block();
            String metaSolverResponse = (response!=null && response.getStatusCode().is2xxSuccessful()) ? response.getBody() : null;
            log.info("ControlServiceCoordinator.configureMetaSolver(): MetaSolver response: endpoint={}, status={},  message={}",
                    metaSolverEndpoint, response!=null ? response.getStatusCode() : null, metaSolverResponse);
        } catch (Exception ex) {
            log.error("ControlServiceCoordinator.configureMetaSolver(): Failed to call MetaSolver: endpoint={}, EXCEPTION: ", metaSolverEndpoint, ex);
        }
    }

    private void notifyESB(String appModelId, ControlServiceRequestInfo requestInfo, @NonNull EMS_STATE emsState) {
        if (StringUtils.isNotBlank(requestInfo.getNotificationUri())) {
            setCurrentEmsState(emsState, "Notifying ESB");

            String notificationUri = requestInfo.getNotificationUri().trim();
            log.info("ControlServiceCoordinator.notifyESB(): Notifying ESB: {}", notificationUri);
            sendSuccessNotification(appModelId, requestInfo);
            log.info("ControlServiceCoordinator.notifyESB(): ESB notified: {}", notificationUri);
        } else {
            log.warn("ControlServiceCoordinator.notifyESB(): Notification URI is blank");
        }
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

    public TranslationContext getTranslationContextOfAppModel(String appModelId) {
        return appModelToTcCache.get(_normalizeModelId(appModelId));
    }

    public List<Monitor> getMonitorssOfAppModel(String appModelId) {
        if (StringUtils.isBlank(appModelId))
            appModelId = currentAppModelId;
        TranslationContext _tc = appModelToTcCache.get(_normalizeModelId(appModelId));
        if (_tc==null) return Collections.emptyList();
        return new ArrayList<>(_tc.getMON());
    }

    public Set getMetricConstraints(String appModelId) {
        TranslationContext _tc = appModelToTcCache.get(_normalizeModelId(appModelId));
        if (_tc==null) return Collections.emptySet();
        return _tc.getMetricConstraints();
    }

    /*public Set<String> getGlobalGroupingMetrics(String appModelId) {
        TranslationContext _tc = appModelToTcCache.get(_normalizeModelId(appModelId));
        if (_tc==null) return Collections.emptySet();

        // get all top-level nodes their component metrics
        final Set<DAGNode> nodes = new HashSet<>();
        final Deque<DAGNode> q = new ArrayDeque<>(_tc.getDAG().getTopLevelNodes());
        while (!q.isEmpty()) {
            DAGNode node = q.pop();
            if (node.getGrouping()==Grouping.GLOBAL) {
                nodes.add(node);
                q.addAll(_tc.getDAG().getNodeChildren(node));
            }
        }

        // return metric names
        return nodes.stream()
                .map(DAGNode::getElementName)
                .collect(Collectors.toSet());
    }*/

    public @NonNull Map<String,Object> getSLOMetricDecomposition(String appModelId) {
        List<Object> slos = _getSLOMetricDecomposition(appModelId);
        Map<String,Object> result = new HashMap<>();
        result.put("name", "_");
        result.put("operator", "OR");
        result.put("constraints", slos);
        return result;
    }

    public @NonNull List<Object> _getSLOMetricDecomposition(String appModelId) {
        TranslationContext _tc = appModelToTcCache.get(_normalizeModelId(appModelId));
        if (_tc==null) return Collections.emptyList();

        // Get metric and logical constraints
        Map<String, MetricConstraint> mcMap = _tc.getMetricConstraints().stream()
                .collect(Collectors.toMap(MetricConstraint::getName, mc -> mc));
        Map<String, LogicalConstraint> lcMap = _tc.getLogicalConstraints().stream()
                .collect(Collectors.toMap(LogicalConstraint::getName, lc -> lc));
        /*Map<String, TranslationContext.IfThenConstraint> ifMap = _tc.getIfThenConstraints().stream()
                .collect(Collectors.toMap(TranslationContext.IfThenConstraint::getName, ic -> ic));*/

        // Create map of top-level element names and instances
        Set<DAGNode> topLevelNodes = _tc.getDAG().getTopLevelNodes();
        Map<String, DAGNode> topLevelNodesMap = topLevelNodes.stream()
                .collect(Collectors.toMap(DAGNode::getElementName, x -> x));

        // process each SLO
        List<Object> sloMetricDecompositions = new ArrayList<>();
        for (String sloName : _tc.getSLO()) {
            DAGNode node = topLevelNodesMap.get(sloName);
            if (node!=null) {
                // get SLO constraint
                Set<DAGNode> sloConstraintSet = _tc.getDAG().getNodeChildren(node);
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

    private Object _decomposeConstraint(TranslationContext _tc, DAGNode constraintNode, Map<String, MetricConstraint> mcMap, Map<String, LogicalConstraint> lcMap) {
        NamedElement element = constraintNode.getElement();
        String elementName = constraintNode.getElementName();
        String elementClassName = ((Object)element).getClass().getName();
        if (element instanceof MetricConstraint) {
            return mcMap.get(elementName);
        } else
        if (element instanceof LogicalConstraint) {
            LogicalConstraint lc = lcMap.get(elementName);

            // decompose child constraints
            List<Object> list = new ArrayList<>();
            for (DAGNode node : lc.getConstraintNodes()) {
                Object o = _decomposeConstraint(_tc, node, mcMap, lcMap);
                if (o!=null) list.add(o);
            }

            // create decomposition result
            Map<String,Object> result = new HashMap<>();
            result.put("name", lc.getName());
            result.put("operator", lc.getLogicalOperator());
            result.put("constraints", list);
            return result;
        } else
            log.warn("_decomposeConstraint: Unsupported Constraint type: {} {}", constraintNode.getElementName(), elementClassName);
        return null;
    }

    public @NonNull Set<MetricContext> getMetricContextsForPrediction(String appModelId) {
        log.debug("getMetricContextsForPrediction: BEGIN: {}", appModelId);
        TranslationContext _tc = appModelToTcCache.get(_normalizeModelId(appModelId));
        if (_tc==null) {
            log.debug("getMetricContextsForPrediction: END: No Translation Context found for model: {}", appModelId);
            return Collections.emptySet();
        }

        // Process DAG top-level nodes
        Set<DAGNode> topLevelNodes = _tc.getDAG().getTopLevelNodes();
        HashSet<MetricContext> tcMetricsOfTopLevelNodes = new HashSet<>();
        log.debug("getMetricContextsForPrediction: Translation Context found for model: {}", appModelId);

        final Deque<DAGNode> q = topLevelNodes.stream()
                .filter(x ->
                        x.getElement() instanceof ServiceLevelObjective ||
                        x.getElement() instanceof Metric)
                .distinct()
                .collect(Collectors.toCollection(ArrayDeque::new));

        while (!q.isEmpty()) {
            DAGNode node = q.pop();
            if (node.getElement() instanceof MetricContext) {
                tcMetricsOfTopLevelNodes.add(node.getMetricContext());
            } else {
                Set<DAGNode> children = _tc.getDAG().getNodeChildren(node);
                if (children!=null) q.addAll(children);
            }
        }

        log.debug("getMetricContextsForPrediction: END: Metrics of Top-Level nodes of model: model={}, metrics={}", appModelId, tcMetricsOfTopLevelNodes);
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

    //@Async
    void emsExit() {
        emsExit(properties.getExitCode());
    }

    //@Async
    void emsExit(int exitCode) {
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

    private void sendSuccessNotification(String applicationId, ControlServiceRequestInfo requestInfo) {
        // Prepare success result notification
        NotificationResultImpl result = new NotificationResultImpl();
        result.setStatus(NotificationResult.StatusType.SUCCESS);

        // Prepare and send CamelModelNotification
        try {
            sendAppModelNotification(applicationId, result, requestInfo);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void sendErrorNotification(String applicationId, ControlServiceRequestInfo requestInfo,
                                       String errorCode, String errorDescription)
    {
        // Prepare error result notification
        NotificationResultImpl result = new NotificationResultImpl();
        result.setStatus(NotificationResult.StatusType.ERROR);
        result.setErrorCode(errorCode);
        result.setErrorDescription(errorDescription);

        // Prepare and send CamelModelNotification
        try {
            sendAppModelNotification(applicationId, result, requestInfo);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void sendAppModelNotification(String applicationId, NotificationResult result, ControlServiceRequestInfo requestInfo) {
        // Create a new watermark
        Watermark watermark = new WatermarkImpl();
        watermark.setUser("EMS");
        watermark.setSystem("EMS");
        watermark.setDate(new java.util.Date());
        String uuid = Objects.requireNonNullElse( requestInfo.getRequestUuid(), UUID.randomUUID().toString().toLowerCase() );
        watermark.setUuid(uuid);

        // Create a new CamelModelNotification
        CamelModelNotificationRequest request = new CamelModelNotificationRequestImpl();
        request.setApplicationId(applicationId);
        request.setResult(result);
        request.setWatermark(watermark);

        // Send CamelModelNotification to ESB (Control Process)
        sendAppModelNotification(request, requestInfo);
    }

    private void sendAppModelNotification(CamelModelNotificationRequest notification, ControlServiceRequestInfo requestInfo) {
        log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   request-info={}", requestInfo);
        log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   request-JWTT={}", requestInfo.getJwtToken());
        String notificationUri = requestInfo.getNotificationUri();
        String requestUuid = requestInfo.getRequestUuid();
        String jwtToken = requestInfo.getJwtToken();

        // Check if 'notificationUri' is blank
        if (StringUtils.isBlank(notificationUri)) {
            log.warn("ControlServiceCoordinator.sendAppModelNotification(): notificationUri not provided or is empty. No notification will be sent to ESB.");
            return;
        }
        notificationUri = notificationUri.trim();

        // Get ESB url from control-service configuration
        String esbUrl = properties.getEsbUrl();
        if (StringUtils.isBlank(esbUrl)) {
            log.warn("ControlServiceCoordinator.sendAppModelNotification(): esb-url property is empty. No notification will be sent to ESB.");
            return;
        }
        esbUrl = esbUrl.trim();

        // Fixing ESB URL parts
        if (esbUrl.endsWith("/")) {
            esbUrl = esbUrl.substring(0, esbUrl.length() - 1);
        }
        if (notificationUri.startsWith("/")) {
            notificationUri = notificationUri.substring(1);
        }

        // Call ESB endpoint
        String url = esbUrl + "/" + notificationUri;
        log.info("ControlServiceCoordinator.sendAppModelNotification(): Invoking ESB endpoint: {}", url);
        log.trace("ControlServiceCoordinator.sendAppModelNotification(): JWT token: {}", jwtToken);

        ResponseEntity<String> response;
        response = webClient.post().
                uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header("X-Morphemic-Request-UUID", requestUuid)
                .bodyValue(notification)
                .retrieve()
                .toEntity(String.class)
                .block();

        if (response!=null) {
            String responseStatus = response.getStatusCode().toString();
            log.info("ControlServiceCoordinator.sendAppModelNotification(): ESB endpoint invoked: {}, status={}, message={}", url, responseStatus, response.getBody());
        } else {
            log.warn("ControlServiceCoordinator.sendAppModelNotification(): ESB endpoint invoked: {}, response is NULL", url);
        }
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

    public List<String> allClientList() {
        log.debug("ControlServiceCoordinator.allClientList(): BEGIN:");
        return baguette.isServerRunning() ? baguette.getAllNodes() : Collections.emptyList();
    }

    public Map<String, Map<String, String>> allClientMap() {
        log.debug("ControlServiceCoordinator.allClientMap(): BEGIN:");
        return baguette.isServerRunning() ? baguette.getAllNodesMap() : Collections.emptyMap();
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
