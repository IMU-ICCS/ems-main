/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.metasolver.util;

import camel.core.CamelModel;
import camel.core.Model;
import camel.data.DataInstanceModel;
import camel.deployment.DeploymentInstanceModel;
import camel.execution.*;
import camel.mms.MetaDataModel;
import camel.mms.MmsConcept;
import camel.mms.MmsConceptInstance;
import eu.paasage.upperware.metamodel.cp.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.eclipse.emf.ecore.EObject;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class HistoryHelper extends AbstractCdoHelper {

    private String mmsBasePath;
    private String mmsTypeBasePath;
    private String mmsComponentPath;
    private String mmsSuccessPath;
    private String mmsFailurePath;

    public void initHelperWithDefaultMetadata(String basePath) {
        initHelperMetadata(basePath, "StateTransition", ".PlatformComponent.META_SOLVER", ".PlatformAction.CP_SOLUTION_PRODUCED", ".PlatformAction.CP_SOLUTION_FAILED");
    }

    public void initHelperMetadata(String basePath, String typesBasePath, String componentPath, String successActionPath, String failureActionPath) {
        mmsBasePath = Objects.toString(basePath, "");
        mmsTypeBasePath = mmsBasePath + (!StringUtils.startsWith(typesBasePath, ".") ? "." : "") + typesBasePath;
        mmsComponentPath = mmsBasePath + (!StringUtils.startsWith(componentPath, ".") ? "." : "") + componentPath;
        mmsSuccessPath = mmsBasePath + (!StringUtils.startsWith(successActionPath, ".") ? "." : "") + successActionPath;
        mmsFailurePath = mmsBasePath + (!StringUtils.startsWith(failureActionPath, ".") ? "." : "") + failureActionPath;
    }

    public void addNewHistoryRecord(String applicationId, String typeName, String causeDescription, String description,
                                    String newDeploymentInstanceName, String newDataInstanceName)
            throws ConcurrentAccessException
    {
        addNewHistoryRecord(applicationId, typeName, causeDescription, description, null, newDeploymentInstanceName, null, newDataInstanceName);
    }

    public void addNewHistoryRecord(String applicationId, String typeName, String causeDescription, String description,
                                    String oldDeploymentInstanceName, String newDeploymentInstanceName,
                                    String oldDataInstanceName, String newDataInstanceName)
            throws ConcurrentAccessException
    {
        String methodName = "HistoryHelper.addNewHistoryRecord()";
        log.debug("{}: BEGIN: helper-id={}, app-id={}, old-deployment-instance={}, new-deployment-instance={}, old-data-instance={}, new-data-instance={}",
                methodName, id, applicationId, oldDeploymentInstanceName, newDeploymentInstanceName, oldDataInstanceName, newDataInstanceName);

        processInTransaction(methodName, transaction -> {
            // get CAMEL model
            String camelPath = StringUtils.startsWith(applicationId, "/") ? applicationId : "/" + applicationId;
            CDOResource resource = transaction.getResource(camelPath);
            CamelModel camelModel = (CamelModel) resource.getContents().get(0);

            // get old deployment instances from camel model
            DeploymentInstanceModel oldDeploymentInstance = StringUtils.isNotEmpty(oldDeploymentInstanceName)
                    ? getModelInCamel(camelModel.getDeploymentModels(), DeploymentInstanceModel.class, oldDeploymentInstanceName)
                    : null;

            // get old data instances from camel model
            DataInstanceModel oldDataInstance = StringUtils.isNotEmpty(oldDataInstanceName)
                    ? getModelInCamel(camelModel.getDataModels(), DataInstanceModel.class, oldDataInstanceName)
                    : null;

            // get new deployment instance model
            DeploymentInstanceModel newDeploymentInstance = StringUtils.isNotEmpty(newDeploymentInstanceName)
                    ? getModelInCamel(camelModel.getDeploymentModels(), DeploymentInstanceModel.class, newDeploymentInstanceName)
                    : null;

            // get new data instance model
            DataInstanceModel newDataInstance = StringUtils.isNotEmpty(newDataInstanceName)
                    ? getModelInCamel(camelModel.getDataModels(), DataInstanceModel.class, newDataInstanceName)
                    : null;

            // check if at least one of new deployment and new data instances are not null
            if (newDeploymentInstance==null && newDataInstance==null) {
                throw new IllegalArgumentException("At least one of new deployment instance or new data instance must be not null and exist in camel model: "+applicationId);
            }

            // get MMS type and create cause
            MmsConceptInstance mmsType = getMetadataConceptInstance(camelModel, mmsTypeBasePath+"."+typeName);
            Cause cause = createCause(causeDescription);

            // create a new history record
            HistoryRecord record = createHistoryRecord(mmsType, cause, description,
                    oldDeploymentInstance, newDeploymentInstance, oldDataInstance, newDataInstance);

            // get last execution model and append new history record
            List<ExecutionModel> execModels = camelModel.getExecutionModels();
            execModels.get(execModels.size() - 1).getHistoryRecords().add(record);

            return null;
        });

        log.debug("{}: END: helper-id={}", methodName, id);
    }

    public <T extends Model,U extends Model> U getModelInCamel(Collection<T> modelsList, Class<U> clazz, String modelName) {
        U result = null;
        if (StringUtils.isNotEmpty(modelName)) {
            Optional<U> opt = modelsList.stream()
                    .filter(model -> modelName.equals(model.getName()))
                    .filter(model -> clazz.isAssignableFrom(model.getClass()))
                    .map(model -> clazz.cast(model))
                    .findFirst();
            if (opt.isPresent()) {
                result = opt.get();
            }
        }
        return result;
    }

    public void closeLastHistoryRecord(String applicationId) throws ConcurrentAccessException {
        String methodName = "HistoryHelper.closeLastHistoryRecord()";
        log.debug("{}: BEGIN: helper-id={}, app-id={}", methodName, id, applicationId);

        processInTransaction(methodName, transaction -> {
            // get CAMEL model
            String camelPath = StringUtils.startsWith(applicationId, "/") ? applicationId : "/" + applicationId;
            CDOResource resource = transaction.getResource(camelPath);
            CamelModel camelModel = (CamelModel) resource.getContents().get(0);

            // get last execution model and last history record
            List<ExecutionModel> execModels = camelModel.getExecutionModels();
            List<HistoryRecord> records = execModels.get(execModels.size() - 1).getHistoryRecords();

            // set end time
            records.get(records.size()-1).setEndTime(new Date());

            return null;
        });

        log.debug("{}: END: helper-id={}", methodName, id);
    }

    public HistoryRecord createHistoryRecord(MmsConceptInstance mmsType, Cause cause, String description,
                                             DeploymentInstanceModel oldDeployment, DeploymentInstanceModel newDeployment,
                                             DataInstanceModel oldData, DataInstanceModel newData)
    {
        Date now = new Date();
        HistoryRecord record = ExecutionFactory.eINSTANCE.createHistoryRecord();
        record.setName("Record_"+now.getTime());
        record.setType(mmsType);
        record.setCause(cause);
        record.setDescription(description);
        record.setStartTime(now);
        record.setEndTime(now);
        record.setFromDeploymentInstanceModel(oldDeployment);
        record.setToDeploymentInstanceModel(newDeployment);
        record.setFromDataInstanceModel(oldData);
        record.setToDataInstanceModel(newData);
        return record;
    }

    public Cause createCause(String description) {
        Cause cause = ExecutionFactory.eINSTANCE.createCause();
        cause.setName("Cause_"+System.currentTimeMillis());
        if (description!=null) cause.setDescription(description);
        return cause;
    }

    public void addCpSolutionHistoryInfo(String applicationId, String cpModelPath, boolean success, String description) throws ConcurrentAccessException {
        String methodName = "HistoryHelper.addCpSolutionHistoryInfo()";
        log.debug("{}: BEGIN: helper-id={}, app-id={}, cp-path={}", methodName, id, applicationId, cpModelPath);

        processInTransaction(methodName, transaction -> {
            // get CAMEL model
            String camelPath = StringUtils.startsWith(applicationId, "/") ? applicationId : "/" + applicationId;
            CDOResource resource = transaction.getResource(camelPath);
            CamelModel camelModel = (CamelModel) resource.getContents().get(0);

            // get MMS concept instances
            MmsConceptInstance mmsMetaSolver = getMetadataConceptInstance(camelModel, mmsComponentPath);
            MmsConceptInstance mmsSuccess = getMetadataConceptInstance(camelModel, mmsSuccessPath);
            MmsConceptInstance mmsFailure = getMetadataConceptInstance(camelModel, mmsFailurePath);
            log.debug("{}: MMS concept instance: MetaSolver: {}", methodName, mmsMetaSolver.getName());
            log.debug("{}: MMS concept instance: Success:    {}", methodName, mmsSuccess.getName());
            log.debug("{}: MMS concept instance: Failure:    {}", methodName, mmsFailure.getName());

            // get last history record
            List<ExecutionModel> execModels = camelModel.getExecutionModels();
            List<HistoryRecord> records = execModels.get(execModels.size() - 1).getHistoryRecords();
            HistoryRecord lastRecord = records.get(records.size() - 1);

            // get referenced CP model
            ConstraintProblem cpModel = null;
            if (StringUtils.isNotEmpty(cpModelPath)) {
                try {
                    String prefix = StringUtils.startsWith(cpModelPath, "/") ? "" : "/" + cpModelPath;
                    CDOResource cpResource = transaction.getResource(prefix + cpModelPath);
                    if (cpResource != null && cpResource.getContents().size() > 0)
                        cpModel = (ConstraintProblem) cpResource.getContents().get(0);
                } catch (org.eclipse.emf.cdo.util.InvalidURIException ex) {
                    if (ex.getCause()!=null && ex.getCause() instanceof org.eclipse.emf.cdo.common.util.CDOResourceNodeNotFoundException)
                        log.warn("{}: CP model not found. Leaving object reference in history record empty: cp-model={}", methodName, cpModelPath);
                    else
                        throw ex;
                } catch (org.eclipse.emf.cdo.common.util.CDOResourceNodeNotFoundException ex) {
                    log.warn("{}: CP model not found. Leaving object reference in history record empty: cp-model={}", methodName, cpModelPath);
                }
            }

            // create a new history info
            MmsConceptInstance action = success ? mmsSuccess : mmsFailure;
            HistoryInfo info = createHistoryInfo(mmsMetaSolver, action, cpModelPath, cpModel, description);

            // update history record
            lastRecord.getInfos().add(info);

            return null;
        });

        log.debug("{}: END: helper-id={}", methodName, id);
    }

    public HistoryInfo createHistoryInfo(MmsConceptInstance subject, MmsConceptInstance action, String object, EObject objRef, String description) {
        Date now = new Date();
        HistoryInfo info = ExecutionFactory.eINSTANCE.createHistoryInfo();
        info.setName("Info_"+now.getTime());
        info.setSubject(subject);
        info.setAction(action);
        info.setObject(object);
        if (objRef!=null) info.setObjectRef(objRef);
        if (description!=null) info.setDescription(description);
        info.setStartTime(now);
        info.setEndTime(now);
        return info;
    }

    public MmsConceptInstance getMetadataConceptInstance(CamelModel camelModel, String path) {
        log.debug("HistoryHelper.getMetadataConceptInstance(): BEGIN: camel-model={}, instance-path={}", camelModel.getName(), path);
        String[] part = path.split("\\.");
        //log.debug("HistoryHelper.getMetadataConceptInstance(): camel-model={}, path-parts={}", camelModel.getName(), Arrays.asList(part));
        Optional<MetaDataModel> opt1 = camelModel.getMetadataModels()
                .stream()
                .filter(mmsModel -> part[0].equals(mmsModel.getName()))
                .findFirst();
        if (opt1.isPresent()) {
            Optional<MmsConcept> opt2 = opt1.get().getMetadataElements()
                    .stream()
                    .filter(mmsRoot -> part[1].equals(mmsRoot.getId()))
                    .filter(mmsRoot -> mmsRoot instanceof MmsConcept)
                    .map(mmsRoot -> (MmsConcept)mmsRoot)
                    .findFirst();
            if (opt2.isPresent()) {
                MmsConcept concept = opt2.get();
                for (int i=2; i<part.length-1; i++) {
                    MmsConcept found = null;
                    for (MmsConcept mmsConcept : concept.getConcept()) {
                        if (part[i].equals(mmsConcept.getId())) {
                            found = mmsConcept;
                        }
                    }
                    if (found!=null) {
                        concept = found;
                    } else {
                        concept = null;
                        break;
                    }
                }

                // get concept instance
                if (concept!=null) {
                    for (MmsConceptInstance instance : concept.getInstance()) {
                        if (part[part.length-1].equals(instance.getId())) {
                            log.debug("HistoryHelper.getMetadataConceptInstance(): END: Found MMS concept instance: camel-model={}, instance-path={}", camelModel.getName(), path);
                            return instance;
                        }
                    }
                }
            }
        }
        log.error("HistoryHelper.getMetadataConceptInstance(): ERROR: NOT FOUND: MMS Concept Instance: camel-model={}, instance-path={}", camelModel.getName(), path);
        throw new RuntimeException("MMS Concept Instance not found in CAMEL model: camel-model="+camelModel.getName()+", concept-instance="+path);
    }

    /*public static void main(String args[]) {
        try {
            String appId = "zzz_history_test";
            String cpPath = "zzz_history_test_cp";
            HistoryHelper helper = new HistoryHelper();
//            helper.initHelperMetadata("MetaDataModel_1.MELODICMetadataSchema.History", ".PlatformComponent.META_SOLVER", ".PlatformAction.CP_SOLUTION_PRODUCED", ".PlatformAction.CP_SOLUTION_FAILED");
            helper.initHelperWithDefaultMetadata("MetaDataModel_1.MELODICMetadataSchema.History");

            helper.addNewHistoryRecord(appId, "APPLICATION_DEPLOYMENT", "A cause", "New History Record",
                    "DeplInst_1", null);
            helper.addCpSolutionHistoryInfo(appId, cpPath, true, "Meta-Solver: New Solution produced successfully");
            helper.addCpSolutionHistoryInfo(appId, null, false, "Meta-Solver: New Solution produced successfully!!!");
            helper.closeLastHistoryRecord(appId);

            log.info("OOOOOOK");
        } catch (Exception ex) {
            log.error("main(): ", ex);
        }
    }*/
}
