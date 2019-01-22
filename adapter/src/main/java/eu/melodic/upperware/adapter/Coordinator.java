/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter;

import camel.core.Attribute;
import camel.core.CamelModel;
import camel.core.CoreFactory;
import camel.deployment.DeploymentInstanceModel;
import camel.type.StringValue;
import camel.type.TypeFactory;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import eu.melodic.models.commons.NotificationResult;
import eu.melodic.models.commons.NotificationResultImpl;
import eu.melodic.models.commons.Watermark;
import eu.melodic.models.commons.WatermarkImpl;
import eu.melodic.models.services.adapter.DeploymentNotificationRequest;
import eu.melodic.models.services.adapter.DeploymentNotificationRequestImpl;
import eu.melodic.models.services.adapter.Monitor;
import eu.melodic.upperware.adapter.communication.cdoserver.CdoServerApi;
import eu.melodic.upperware.adapter.communication.ems.EmsClientApi;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.executioncontext.ContextOperations;
import eu.melodic.upperware.adapter.executioncontext.cdoserver.CdoServerUpdater;
import eu.melodic.upperware.adapter.graphlogger.ToLogGraphLogger;
import eu.melodic.upperware.adapter.planexecutor.PlanExecutor;
import eu.melodic.upperware.adapter.plangenerator.Plan;
import eu.melodic.upperware.adapter.plangenerator.PlanGenerator;
import eu.melodic.upperware.adapter.properties.AdapterProperties;
import eu.melodic.upperware.adapter.validation.DeploymentInstanceModelValidator;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import io.github.cloudiator.rest.ApiException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static eu.melodic.models.commons.NotificationResult.StatusType.ERROR;
import static eu.melodic.models.commons.NotificationResult.StatusType.SUCCESS;
import static eu.passage.upperware.commons.MelodicConstants.CDO_SERVER_PATH;
import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class Coordinator {

    private static final Map<String, Object> LOCKS = Maps.newConcurrentMap();

    private CdoServerApi cdoServerApi;

    private PlanGenerator planGenerator;
    private PlanExecutor planExecutor;
    private CdoServerUpdater cdoServerUpdater;
    private ToLogGraphLogger toLogGraphLogger;

    private ContextOperations context;
    private RestTemplate restTemplate;
    private AdapterProperties properties;
    private CDOClientX cdoClientX;

    private EmsClientApi emsClientApi;

    private DeploymentInstanceModelValidator deploymentInstanceModelValidator;

    private Gson gson = new Gson();

    @Async
    public void deployNewModel(String resourceName, String notificationUri, String uuid, String authorization) {
        try {
            acquireLock(resourceName);
        } catch (Exception e) {
            log.error("An exception occurred during acquiring lock for application {}", resourceName, e);
            notifyErrorOccurred(resourceName, notificationUri, uuid, e);
            return;
        }
        log.info("Starting new model deployment process");
        try {
            run(resourceName, notificationUri, uuid, authorization);
        } catch (Exception e) {
            log.error("An exception occurred during deployment process", e);
            notifyErrorOccurred(resourceName, notificationUri, uuid, e);
        } finally {
            releaseLock(resourceName);
        }
        log.info("New model deployment process has been finished");
    }

    public void refreshContext() {
        try {
            context.refreshContext();
        } catch (ApiException e) {
            throw new AdapterException("Problem during refreshing context", e);
        }
    }

    private void run(String resourceName, String notificationUri, String uuid, String authorization) {
        Plan plan;
        CDOSessionX cdoSessionX = cdoServerApi.openSession();
        CDOTransaction tr = cdoSessionX.openTransaction();

        DeploymentInstanceModel targetModel = null;
        DeploymentInstanceModel currentModel = null;
        try {
            targetModel = cdoServerApi.getModelToDeploy(resourceName, tr); //new
            enrichMonitors(targetModel, uuid, authorization);
            currentModel = cdoServerApi.getDeployedModel(resourceName, tr); //old
            if (currentModel == null) {
                saveCamelModelToFile(((CamelModel) targetModel.eContainer()));
                plan = planGenerator.buildConfigurationPlan(targetModel);
            } else {
                plan = planGenerator.buildReconfigurationPlan(currentModel, targetModel);
            }
        } finally {
            cdoSessionX.closeTransaction(tr);
            cdoSessionX.closeSession();
        }
        if (!context.isLoaded()) {
            try {
                context.refreshContext();
            } catch (ApiException e) {
                throw new AdapterException("Problem during refreshing context", e);
            }
        }

        toLogGraphLogger.logGraph(plan.getTaskGraph());

        // pre-authorize target model
        if (targetModel != null) {
            log.info("Authorizing deployment plan with Authorization-Service...");
            try {
                if (deploymentInstanceModelValidator.validate(targetModel)) {
                    log.info("Deployment plan authorized, executing...");

                    planExecutor.executePlan(plan);
                    cdoServerUpdater.updateCamelModel(resourceName);
                    notifyPlanApplied(resourceName, notificationUri, uuid);
                } else {
                    log.info("Deployment plan authorized failed...");
                    notifyPlanRejected(resourceName, notificationUri, uuid);
                }

            } catch (Exception ex) {
                log.error("Error: ", ex);
                notifyPlanRejected(resourceName, notificationUri, uuid);
            }
        } else {
            log.warn("Cannot pre-authorize target model. Target model is null");
        }
    }

    private void enrichMonitors(DeploymentInstanceModel targetModel, String uuid, String authorization) {
        String attributeName = "monitors";
        String applicationId = ((CamelModel) targetModel.eContainer()).getName();
        List<Monitor> monitors = emsClientApi.getMonitors(applicationId, prepareWatermark(uuid), authorization);
        if (CollectionUtils.isEmpty(monitors)) {
            log.info("There is no monitors defined for CamelModel {}", applicationId);
            return;
        }

        EList<Attribute> attributes = targetModel.getAttributes();
        if (findAttribute(attributes, attributeName)) {
            log.info("Attribute with name {} for {} exists. DeploymentInstanceModel will not be updated", attributeName, targetModel.getName());
        } else {
            attributes.add(createAttribute(attributeName, gson.toJson(monitors)));
            log.info("Attribute with name {} for {} does not exist. New attribute will be created for monitors with metric names: {}", attributeName, targetModel.getName(),
                    monitors.stream().map(Monitor::getMetric).collect(Collectors.joining(", ", "[", "]")));
        }
    }

    private boolean findAttribute(EList<Attribute> attributes, String attributeName){
        return attributes.stream().anyMatch(attribute -> attribute.getName().equals(attributeName));
    }

    private Attribute createAttribute(String attributeName, String attributeValue) {
        log.info("Adding attribute {} with value: {}", attributeName, attributeValue);
        Attribute strAttribute = CoreFactory.eINSTANCE.createAttribute();
        strAttribute.setName(attributeName);
        strAttribute.setValue(createStringValue(attributeValue));
        return strAttribute;
    }

    private StringValue createStringValue(String attributeValue) {
        StringValue stringValue = TypeFactory.eINSTANCE.createStringValue();
        stringValue.setValue(attributeValue);
        return stringValue;
    }

    private void notifyPlanApplied(String resourceName, String notificationUri, String uuid) {
        log.info("Sending plan applied notification");
        NotificationResult result = prepareSuccessNotificationResult();
        DeploymentNotificationRequest notification = prepareNotification(resourceName, result, uuid);
        sendNotification(notification, notificationUri);
    }

    private void notifyPlanRejected(String resourceName, String notificationUri, String uuid) {
        log.info("Sending plan rejected notification");
        NotificationResult result = prepareErrorNotificationResult("Built plan was rejected by Plan Validator");
        DeploymentNotificationRequest notification = prepareNotification(resourceName, result, uuid);
        sendNotification(notification, notificationUri);
    }

    private void notifyErrorOccurred(String resourceName, String notificationUri, String uuid, Exception e) {
        String errorMsg = e.getMessage();
        log.error("Sending error notification: {}", errorMsg);
        NotificationResult result = prepareErrorNotificationResult(errorMsg);
        DeploymentNotificationRequest notification = prepareNotification(resourceName, result, uuid);
        sendNotification(notification, notificationUri);
    }

    private NotificationResult prepareSuccessNotificationResult() {
        NotificationResult result = new NotificationResultImpl();
        result.setStatus(SUCCESS);
        return result;
    }

    private NotificationResult prepareErrorNotificationResult(String errorMsg) {
        NotificationResult result = new NotificationResultImpl();
        result.setErrorDescription(errorMsg);
        result.setStatus(ERROR);
        return result;
    }

    private DeploymentNotificationRequest prepareNotification(String resourceName, NotificationResult result, String uuid) {
        DeploymentNotificationRequest notification = new DeploymentNotificationRequestImpl();
        notification.setApplicationId(resourceName);
        notification.setResult(result);
        notification.setWatermark(prepareWatermark(uuid));
        return notification;
    }

    private Watermark prepareWatermark(String uuid) {
        Watermark watermark = new WatermarkImpl();
        watermark.setUser("adapter");
        watermark.setSystem("adapter");
        watermark.setDate(new Date());
        watermark.setUuid(uuid);
        return watermark;
    }

    private void sendNotification(DeploymentNotificationRequest notification, String notificationUri) {
        String esbUrl = properties.getEsb().getUrl();
        if (esbUrl.endsWith("/")) {
            esbUrl = esbUrl.substring(0, esbUrl.length() - 1);
        }
        if (notificationUri.startsWith("/")) {
            notificationUri = notificationUri.substring(1);
        }
        restTemplate.postForEntity(esbUrl + "/" + notificationUri, notification, String.class);
    }

    private static void acquireLock(String resourceName) {
        synchronized (LOCKS) {
            log.info("Acquiring lock for application {}", resourceName);
            Object obj = LOCKS.get(resourceName);
            if (obj != null) {
                throw new IllegalStateException(format("(Re)configuration process for %s is running. " +
                        "Wait until it is completed and repeat request.", resourceName));
            }
            LOCKS.put(resourceName, Boolean.TRUE);
        }
    }

    private static void releaseLock(String resourceName) {
        log.info("Releasing lock for application {}", resourceName);
        LOCKS.remove(resourceName);
    }

    private void saveCamelModelToFile(CamelModel camelModel) {
        String pcId = camelModel.getName();
        log.debug("CDODatabaseProxy - saveModels to file");
        String cpPath = CDO_SERVER_PATH + pcId;
        cdoClientX.exportModel(camelModel, "/logs/adapter_camel_models/" + cpPath + System.currentTimeMillis() + ".xmi");
        log.debug("CDODatabaseProxy - saveModels - Models saved to file! ");
    }
}
