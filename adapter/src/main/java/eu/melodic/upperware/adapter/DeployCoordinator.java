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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import eu.melodic.models.services.adapter.Monitor;
import eu.melodic.upperware.adapter.communication.cdoserver.CdoServerApi;
import eu.melodic.upperware.adapter.communication.ems.EmsClientApi;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.executioncontext.ContextOperations;
import eu.melodic.upperware.adapter.executioncontext.cdoserver.CamelToFileSaver;
import eu.melodic.upperware.adapter.executioncontext.cdoserver.CamelToFileSaverImpl;
import eu.melodic.upperware.adapter.executioncontext.cdoserver.CdoServerUpdater;
import eu.melodic.upperware.adapter.planexecutor.PlanExecutor;
import eu.melodic.upperware.adapter.plangenerator.Plan;
import eu.melodic.upperware.adapter.plangenerator.PlanGenerator;
import eu.melodic.upperware.adapter.plangenerator.converter.CamelModelConverter;
import eu.melodic.upperware.adapter.plangenerator.graph.DiffCalculator;
import eu.melodic.upperware.adapter.plangenerator.graph.model.DividedElement;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterRequirement;
import eu.melodic.upperware.adapter.plangenerator.model.ComparableModel;
import eu.melodic.upperware.adapter.properties.AdapterProperties;
import eu.melodic.upperware.adapter.validation.DeploymentInstanceModelValidator;
import eu.melodic.upperware.adapter.notification.DeploymentNotificationSenderImpl;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import io.github.cloudiator.rest.ApiException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class DeployCoordinator {

    private static final Map<String, Object> LOCKS = Maps.newConcurrentMap();

    private CdoServerApi cdoServerApi;

    private PlanGenerator planGenerator;
    private PlanExecutor planExecutor;
    private CdoServerUpdater cdoServerUpdater;

    private ContextOperations context;
    private AdapterProperties properties;

    private EmsClientApi emsClientApi;

    private DeploymentInstanceModelValidator deploymentInstanceModelValidator;

    private ObjectMapper objectMapper = new ObjectMapper();

    private DeploymentNotificationSenderImpl deploymentNotificationSenderImpl;

    private CamelModelConverter converter;

    private CamelToFileSaver camelToFileSaver;

    private DiffCalculator<AdapterRequirement, String> diffCalculator;

    @Async
    public void deployNewModel(String resourceName, String notificationUri, String uuid, String authorization) {
        try {
            acquireLock(resourceName);
        } catch (Exception e) {
            log.error("An exception occurred during acquiring lock for application {}", resourceName, e);
            deploymentNotificationSenderImpl.notifyErrorOccurred(resourceName, notificationUri, uuid, e);
            return;
        }
        log.info("Starting new model deployment process");
        try {
            run(resourceName, notificationUri, uuid, authorization);
        } catch (Exception e) {
            log.error("An exception occurred during deployment process", e);
            deploymentNotificationSenderImpl.notifyErrorOccurred(resourceName, notificationUri, uuid, e);
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

        boolean isValid;
        try {
            DeploymentInstanceModel targetModel = cdoServerApi.getModelToDeploy(resourceName, tr); //new
            if (properties.getEms().isEnabled()) {
                enrichMonitors(targetModel, uuid, authorization, resourceName);
            }
            DeploymentInstanceModel currentModel = cdoServerApi.getDeployedModel(resourceName, tr); //old
            if (currentModel == null) {
                plan = planGenerator.buildConfigurationPlan(targetModel);
            } else {
                plan = planGenerator.buildReconfigurationPlan(currentModel, targetModel);
            }

            try {
                tr.commit();
                isValid = deploymentInstanceModelValidator.validate(targetModel);
                camelToFileSaver.toFile((CamelModel) targetModel.eContainer());
            } catch (CommitException e) {
                throw new AdapterException("Exception during commiting transaction", e);
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

        // pre-authorize target model
        log.info("Authorizing deployment plan with Authorization-Service...");
        try {
            if (isValid) {
                log.info("Deployment plan authorized, executing...");

                planExecutor.executePlan(plan);
                cdoServerUpdater.updateCamelModel(resourceName);
                camelToFileSaver.toFile(resourceName, CamelToFileSaverImpl.DEFAULT_NAME_AFTER_DEPLOYMENT_FUNCTION);
                deploymentNotificationSenderImpl.notifyPlanApplied(resourceName, notificationUri, uuid);
            } else {
                log.info("Deployment plan authorized failed...");
                deploymentNotificationSenderImpl.notifyPlanRejected(resourceName, notificationUri, uuid);
            }

        } catch (Exception ex) {
            log.error("Error: ", ex);
            deploymentNotificationSenderImpl.notifyErrorOccurred(resourceName, notificationUri, uuid, ex);
        }
    }

    private void enrichMonitors(DeploymentInstanceModel targetModel, String uuid, String authorization, String resourceName) {
        String attributeName = "monitors";
        List<Monitor> monitors = emsClientApi.getMonitors(resourceName, deploymentNotificationSenderImpl.prepareWatermark(uuid), authorization);
        if (CollectionUtils.isEmpty(monitors)) {
            log.info("There is no monitors defined for CamelModel {}", resourceName);
            return;
        }

        EList<Attribute> attributes = targetModel.getAttributes();
        if (findAttribute(attributes, attributeName)) {
            log.info("Attribute with name {} for {} exists. DeploymentInstanceModel will not be updated", attributeName, targetModel.getName());
        } else {
            String attributeValue;
            try {
                attributeValue = objectMapper.writeValueAsString(monitors);
            } catch (JsonProcessingException e) {
                throw new AdapterException("Error during serialising Monitors", e);
            }
            attributes.add(createAttribute(attributeName, attributeValue));
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

    public Map<String, DividedElement<AdapterRequirement>> calculateDifference(String resourceName, String deploymentInstanceName) {
        CDOSessionX cdoSessionX = cdoServerApi.openSession();
        CDOTransaction tr = cdoSessionX.openTransaction();

        try {
            DeploymentInstanceModel targetModel = cdoServerApi.getModelToDeploy(resourceName, deploymentInstanceName, tr); //new
            DeploymentInstanceModel currentModel = cdoServerApi.getDeployedModel(resourceName, tr); //old

            ComparableModel oldModel = currentModel != null ? converter.toComparableModel(currentModel) : ComparableModel.builder().build();
            ComparableModel newModel = converter.toComparableModel(targetModel);

            return diffCalculator.calculateDiff(
                    new ArrayList<>(newModel.getAdapterRequirements()),
                    new ArrayList<>(oldModel.getAdapterRequirements()),
                    AdapterRequirement.NODE_BI_PREDICATE,
                    AdapterRequirement::getTaskName);

        } finally {
            cdoSessionX.closeTransaction(tr);
            cdoSessionX.closeSession();
        }
    }
}
