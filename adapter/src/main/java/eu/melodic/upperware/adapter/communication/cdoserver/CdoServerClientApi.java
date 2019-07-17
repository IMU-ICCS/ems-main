/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.communication.cdoserver;

import camel.core.CamelModel;
import camel.deployment.DeploymentInstanceModel;
import camel.deployment.DeploymentModel;
import camel.deployment.DeploymentTypeModel;
import camel.execution.Cause;
import camel.execution.ExecutionFactory;
import camel.execution.ExecutionModel;
import camel.execution.HistoryRecord;
import camel.mms.MetaDataModel;
import camel.mms.MmsConcept;
import camel.mms.MmsConceptInstance;
import camel.mms.impl.MmsConceptImpl;
import camel.requirement.RequirementModel;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.passage.upperware.commons.model.tools.CdoTool;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CdoServerClientApi implements CdoServerApi {

    private CDOClientX cdoClient;

    @Override
    public DeploymentInstanceModel getModelToDeploy(@NonNull String resourceName, CDOTransaction tr) {
        EList<EObject> contents = tr.getOrCreateResource(resourceName).getContents();
        if (CollectionUtils.isNotEmpty(contents)) {
            CamelModel model = CdoTool.getLastCamelModel(contents)
                    .orElseThrow(() -> new IllegalStateException(format("Could not find Camel Model for resourceName=%s", resourceName)));

            EList<DeploymentModel> deploymentModels = model.getDeploymentModels();
            if (CollectionUtils.isNotEmpty(deploymentModels)) {
                DeploymentModel deploymentModel = deploymentModels.get(deploymentModels.size() - 1);
                if (deploymentModel instanceof DeploymentInstanceModel) {
                    return (DeploymentInstanceModel) deploymentModel;
                }
            }
        }
        throw new IllegalArgumentException(format("Cannot load Camel Deployment Instance Model for resourceName=%s. " +
                "Check the value is valid and the model is available in CDO Server.", resourceName));
    }

    @Override
    public DeploymentInstanceModel getModelToDeploy(String resourceName, String deploymentInstanceName, CDOTransaction tr) {
        EList<EObject> contents = tr.getOrCreateResource(resourceName).getContents();
        if (CollectionUtils.isEmpty(contents)) {
            throw new IllegalArgumentException(format("Cannot load Camel Deployment Instance Model for resourceName=%s. " +
                    "Check the value is valid and the model is available in CDO Server.", resourceName));
        }

        CamelModel model = CdoTool.getLastCamelModel(contents)
                .orElseThrow(() -> new IllegalStateException(format("Could not find Camel Model for resourceName=%s", resourceName)));

        return CollectionUtils.emptyIfNull(model.getDeploymentModels())
                .stream()
                .filter(deploymentModel -> deploymentModel instanceof DeploymentInstanceModel)
                .map(deploymentModel -> (DeploymentInstanceModel) deploymentModel)
                .filter(deploymentModel -> deploymentInstanceName.equals(deploymentModel.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(format("Cannot load Camel Deployment Instance Model for resourceName=%s. " +
                        "Check the value is valid and the model is available in CDO Server.", resourceName)));
    }

    @Override
    public DeploymentInstanceModel getDeployedModel(String resourceName, CDOTransaction tr) {
        EList<EObject> contents = tr.getOrCreateResource(resourceName).getContents();

        if (CollectionUtils.isNotEmpty(contents)) {
            int numberOfCamelModels = contents.size();

            for (int i = numberOfCamelModels - 1; i > -1; i--) {
                CamelModel model = (CamelModel) contents.get(i);

                Optional<DeploymentTypeModel> deploymentTypeModelOpt = getDeploymentTypeModel(model);
                if (deploymentTypeModelOpt.isPresent()) {
                    Optional<ExecutionModel> executionModelOpt = getExecutionModel(model, deploymentTypeModelOpt.get());
                    if (executionModelOpt.isPresent()) {
                        return CdoTool.getCurrentlyInstalledModel(executionModelOpt.get()).orElse(null);
                    }
                }
            }
            return null;
        }
        throw new IllegalArgumentException(format("Cannot load Camel Deployment Instance Model for resourceName=%s. " +
                "Check the value is valid and the model is available in CDO Server.", resourceName));
    }

    @Override
    public void setExecutionContext(DeploymentInstanceModel newDeploymentModel) {

        CamelModel camelModel = (CamelModel) newDeploymentModel.eContainer();
        Optional<DeploymentTypeModel> deploymentTypeModelOpt = getDeploymentTypeModel(camelModel);

        if (deploymentTypeModelOpt.isPresent()) {
            DeploymentTypeModel deploymentTypeModel = deploymentTypeModelOpt.get();
            ExecutionModel executionModel = getExecutionModel(camelModel, deploymentTypeModel).orElseGet(() -> {
                ExecutionModel executionModelNew = createExecutionModel(deploymentTypeModel);
                camelModel.getExecutionModels().add(executionModelNew);
                return executionModelNew;
            });

            DeploymentInstanceModel oldModel = null;
            Optional<HistoryRecord> lastHistoryRecordOpt = getLastHistoryRecord(executionModel);
            if (lastHistoryRecordOpt.isPresent()) {
                HistoryRecord lastHistoryRecord = lastHistoryRecordOpt.get();
                oldModel = lastHistoryRecord.getToDeploymentInstanceModel();
                lastHistoryRecord.setEndTime(new Date());
            }

            executionModel.getHistoryRecords().add(createHistoryRecord(camelModel, oldModel, newDeploymentModel));
            log.info("History record has been added to ExecutionModel");
        }
    }

    private ExecutionModel createExecutionModel(DeploymentTypeModel deploymentTypeModel) {
        ExecutionModel executionModel = ExecutionFactory.eINSTANCE.createExecutionModel();
        executionModel.setName(getUniqueExecutionName());
        executionModel.setStartTime(new Date());
        executionModel.setDeploymentTypeModel(deploymentTypeModel);
        executionModel.setRequirementModel(getRequirementModel(deploymentTypeModel).orElseThrow(() -> new IllegalStateException("Missing required RequirementModel")));
        return executionModel;
    }

    private Optional<RequirementModel> getRequirementModel(DeploymentTypeModel deploymentTypeModel) {
        EList<RequirementModel> requirementModels = ((CamelModel) deploymentTypeModel.eContainer()).getRequirementModels();
        if (CollectionUtils.isNotEmpty(requirementModels)) {
            return Optional.of(requirementModels.get(0));
        }
        return Optional.empty();
    }

    private HistoryRecord createHistoryRecord(CamelModel camelModel, DeploymentInstanceModel oldModel, DeploymentInstanceModel newModel) {

        MmsConceptInstance mmsConceptInstance = getDeploymentSolutionSuccessInstance(camelModel)
                .orElseThrow(() -> new AdapterException("Could not find DEPLOYMENT_SOLUTION_SUCCESS instance"));

        HistoryRecord historyRecord = ExecutionFactory.eINSTANCE.createHistoryRecord();
        historyRecord.setName(getUniqueHistoryName());
        historyRecord.setStartTime(new Date());
        historyRecord.setEndTime(new Date());
        historyRecord.setFromDeploymentInstanceModel(oldModel);
        historyRecord.setToDeploymentInstanceModel(newModel);
        historyRecord.setType(mmsConceptInstance);
        historyRecord.setCause(createCause());
        return historyRecord;
    }

    private Optional<MmsConceptInstance> getDeploymentSolutionSuccessInstance(CamelModel camelModel) {
        return camelModel.getMetadataModels().stream()
                .filter(metaDataModel -> "MMS_MetaDataModel".equals(metaDataModel.getName()))
                .map(MetaDataModel::getMetadataElements)
                .flatMap(Collection::stream)
                .filter(mmsObject -> "MELODICMetadataSchema".equals(mmsObject.getId()))
                .filter(mmsObject -> mmsObject instanceof MmsConceptImpl)
                .map(mmsObject -> (MmsConceptImpl) mmsObject)
                .map(MmsConceptImpl::getConcept)
                .flatMap(Collection::stream)
                .filter(mmsConcept -> "History".equals(mmsConcept.getId()))
                .map(MmsConcept::getConcept)
                .flatMap(Collection::stream)
                .filter(mmsConcept -> "PlatformAction".equals(mmsConcept.getId()))
                .map(MmsConcept::getInstance)
                .flatMap(Collection::stream)
                .filter(mmsConceptInstances -> "DEPLOYMENT_SOLUTION_SUCCESS".equals(mmsConceptInstances.getId()))
                .findFirst();
    }

    private Cause createCause() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
        Cause cause = ExecutionFactory.eINSTANCE.createCause();
        String name = "Cause_" + generator.generate(10);
        cause.setName(name);
        cause.setDescription(name);
        return cause;
    }

    private Optional<HistoryRecord> getLastHistoryRecord(ExecutionModel executionModel) {
        EList<HistoryRecord> historyRecords = executionModel.getHistoryRecords();
        if (CollectionUtils.isNotEmpty(historyRecords)) {
            return Optional.of(historyRecords.get(historyRecords.size() - 1));
        }
        return Optional.empty();
    }

    @Override
    public CDOSessionX openSession() {
        return cdoClient.getSession();
    }

    private Optional<ExecutionModel> getExecutionModel(CamelModel camelModel, DeploymentTypeModel deploymentTypeModel) {
        EList<ExecutionModel> executionModels = camelModel.getExecutionModels();
        for (int i = executionModels.size() - 1; i >= 0; i--) {
            ExecutionModel executionModel = executionModels.get(0);
            if (executionModel.getDeploymentTypeModel().equals(deploymentTypeModel)) {
                return Optional.of(executionModel);
            }
        }
        return Optional.empty();
    }

    private Optional<DeploymentTypeModel> getDeploymentTypeModel(CamelModel camelModel) {
        List<DeploymentModel> deploymentModels = ListUtils.emptyIfNull((camelModel.getDeploymentModels()));
        for (int i = deploymentModels.size() - 1; i >= 0; i--) {
            DeploymentModel deploymentModel = deploymentModels.get(0);
            if (deploymentModel instanceof DeploymentTypeModel) {
                return Optional.of((DeploymentTypeModel) deploymentModel);
            }
        }
        return Optional.empty();
    }

    private String getUniqueHistoryName() {
        return getUniqueName("HistoryRecord");
    }

    private String getUniqueExecutionName() {
        return getUniqueName("ExecutionModel");
    }

    private String getUniqueName(String name) {
        return name + System.currentTimeMillis();
    }
}
