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

    private boolean initialized = false;

    private String mmsBasePath;
    private String mmsActionsBasePath;
    private String mmsComponentsBasePath;
    private String mmsTransitionsBasePath;

    private String mmsMetasolverPath;
    private String mmsCpSolutionSuccessPath;
    private String mmsCpSolutionFailurePath;

    // ------------------------------------------------------------------------

    public boolean isInitialized() { return initialized; }

    public void invalidateMetadata() {
        initialized = false;
    }

    public void initHelperMetadataWithBase(String basePath) {
        log.debug("HistoryHelper: Initializing Helper #{} metadata with base path: {}", id, basePath);
        initHelperMetadata(basePath, null, null, null, null, null, null);
    }

    public synchronized void initHelperMetadata(String basePath, String actionsBasePath, String componentsBasePath, String transitionsBasePath, String metasolverPath, String CpSolutionSuccessActionPath, String CpSolutionFailureActionPath) {
        if (StringUtils.isEmpty(basePath)) throw new IllegalArgumentException("'basePath' argument cannot be empty");

        mmsBasePath = basePath.trim();
        mmsActionsBasePath = mmsBasePath + "." + trimOrDefaultIfEmpty(actionsBasePath, "PlatformAction");
        mmsComponentsBasePath = mmsBasePath + "." + trimOrDefaultIfEmpty(componentsBasePath, "PlatformComponent");
        mmsTransitionsBasePath = mmsBasePath + "." + trimOrDefaultIfEmpty(transitionsBasePath, "StateTransition");

        mmsMetasolverPath = mmsComponentsBasePath + "." + trimOrDefaultIfEmpty(metasolverPath,"META_SOLVER");
        mmsCpSolutionSuccessPath = mmsActionsBasePath + "." + trimOrDefaultIfEmpty(CpSolutionSuccessActionPath,"CP_SOLUTION_PRODUCED");
        mmsCpSolutionFailurePath = mmsActionsBasePath + "." + trimOrDefaultIfEmpty(CpSolutionFailureActionPath,"CP_SOLUTION_FAILED");

        initialized = true;
        log.debug("HistoryHelper: Helper #{} metadata initialized", id);
    }

    protected String trimOrDefaultIfEmpty(String str, String def) {
        return StringUtils.isNotEmpty(str) ? str.trim() : def.trim();
    }

    protected void _initHelperMetadataFromCamelModel(String applicationId) throws ConcurrentAccessException {
        if (initialized) return;
        initHelperMetadataFromCamelModel(applicationId);
    }

    public void initHelperMetadataFromCamelModel(String applicationId) throws ConcurrentAccessException {
        log.debug("HistoryHelper: Attempting to initialize Helper #{} metadata from CAMEL model: {}", id, applicationId);

        String methodName = "initHelperMetadataFromCamelModel";
        String historyBasePath = processInTransaction(methodName, transaction -> {
            // get CAMEL model
            String camelPath = StringUtils.startsWith(applicationId, "/") ? applicationId : "/" + applicationId;
            CDOResource resource = transaction.getResource(camelPath);
            CamelModel camelModel = (CamelModel) resource.getContents().get(0);

            // get metadata schema
            Optional<MetaDataModel> opt = camelModel.getMetadataModels()
                    .stream()
                    .filter(model -> {
                        if (model.getMetadataElements().size()==1) {
                            if (model.getMetadataElements().get(0) instanceof MmsConcept) {
                                MmsConcept root = (MmsConcept)model.getMetadataElements().get(0);
                                Optional<MmsConcept> opt1 = root.getConcept()
                                        .stream()
                                        .filter(child -> "History".equals(child.getId()))
                                        .findFirst();
                                return opt1.isPresent();
                            }
                        }
                        return false;
                    })
                    .findFirst();
            if (opt.isPresent()) {
                String modelName = opt.get().getName();
                String rootName = opt.get().getMetadataElements().get(0).getId();
                return String.join(".", modelName, rootName, "History");
            }

            return null;
        });

        if (StringUtils.isNotEmpty(historyBasePath)) {
            log.debug("HistoryHelper: History base path from CAMEL model: {}", historyBasePath);
            initHelperMetadataWithBase(historyBasePath);
        } else {
            log.warn("HistoryHelper: Helper #{} metadata failed to initialize from CAMEL model: {}", id, applicationId);
        }
    }

    // ------------------------------------------------------------------------

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
        _initHelperMetadataFromCamelModel(applicationId);

        String methodName = "HistoryHelper.addNewHistoryRecord()";
        log.debug("{}: BEGIN: helper-id={}, app-id={}, old-deployment-instance={}, new-deployment-instance={}, old-data-instance={}, new-data-instance={}",
                methodName, id, applicationId, oldDeploymentInstanceName, newDeploymentInstanceName, oldDataInstanceName, newDataInstanceName);

        processInTransaction(methodName, transaction -> {
            // get CAMEL model
            log.trace("HistoryHelper: Loading Camel model: {}", applicationId);
            String camelPath = StringUtils.startsWith(applicationId, "/") ? applicationId : "/" + applicationId;
            CDOResource resource = transaction.getResource(camelPath);
            CamelModel camelModel = (CamelModel) resource.getContents().get(0);

            // get execution history records
            List<ExecutionModel> execModels = camelModel.getExecutionModels();
            log.trace("HistoryHelper: Execution models: size={}", execModels.size());
            if (execModels.size()==0)
                throw new IllegalStateException("Camel model does not contain any execution model: "+applicationId);
            List<HistoryRecord> records = execModels.get(execModels.size() - 1).getHistoryRecords();
            log.trace("HistoryHelper: History Records: size={}", records.size());

            // find last deployment instance model and last data instance model
            // (one of them might not be in the last history record)
            DeploymentInstanceModel lastDeploymentInstanceModel = null;
            DataInstanceModel lastDataInstanceModel = null;
            for (int i=records.size()-1; i>=0; i--) {
                if (lastDeploymentInstanceModel==null)
                    lastDeploymentInstanceModel = records.get(i).getToDeploymentInstanceModel();
                if (lastDataInstanceModel==null)
                    lastDataInstanceModel = records.get(i).getToDataInstanceModel();
            }
            log.trace("HistoryHelper: Last deployment instance model: {}", lastDeploymentInstanceModel);
            log.trace("HistoryHelper: Last data instance model: {}", lastDataInstanceModel);

            // get old deployment instances from camel model
            DeploymentInstanceModel oldDeploymentInstance = StringUtils.isNotEmpty(oldDeploymentInstanceName)
                    ? getModelInCamel(camelModel.getDeploymentModels(), DeploymentInstanceModel.class, oldDeploymentInstanceName)
                    : lastDeploymentInstanceModel;
            log.trace("HistoryHelper: Old deployment instance model: {}", oldDeploymentInstance);

            // get old data instances from camel model
            DataInstanceModel oldDataInstance = StringUtils.isNotEmpty(oldDataInstanceName)
                    ? getModelInCamel(camelModel.getDataModels(), DataInstanceModel.class, oldDataInstanceName)
                    : lastDataInstanceModel;
            log.trace("HistoryHelper: Old data instance model: {}", oldDataInstance);

            // get new deployment instance model
            DeploymentInstanceModel newDeploymentInstance = StringUtils.isNotEmpty(newDeploymentInstanceName)
                    ? getModelInCamel(camelModel.getDeploymentModels(), DeploymentInstanceModel.class, newDeploymentInstanceName)
                    : null;
            log.trace("HistoryHelper: New deployment instance model: {}", newDeploymentInstance);

            // get new data instance model
            DataInstanceModel newDataInstance = StringUtils.isNotEmpty(newDataInstanceName)
                    ? getModelInCamel(camelModel.getDataModels(), DataInstanceModel.class, newDataInstanceName)
                    : null;
            log.trace("HistoryHelper: New data instance model: {}", newDataInstance);

            // check if at least one of new deployment and new data instances are not null
            /*if (newDeploymentInstance==null && newDataInstance==null) {
                throw new IllegalArgumentException("At least one of new deployment instance or new data instance must be not null and exist in camel model: "+applicationId);
            }*/

            // get MMS type and create cause
            MmsConceptInstance mmsType = getMetadataConceptInstance(camelModel, mmsTransitionsBasePath+"."+typeName);
            Cause cause = createCause(causeDescription);
            log.trace("HistoryHelper: MMS state transition: {}", mmsType);

            // create a new history record
            HistoryRecord record = createHistoryRecord(mmsType, cause, description,
                    oldDeploymentInstance, newDeploymentInstance, oldDataInstance, newDataInstance);
            log.trace("HistoryHelper: New history record: {}", record);

            // get last execution model and append new history record
            records.add(record);
            log.trace("HistoryHelper: New history record added");

            return null;
        });

        log.debug("{}: END: helper-id={}", methodName, id);
    }

    public void closeLastHistoryRecord(String applicationId) throws ConcurrentAccessException {
        _initHelperMetadataFromCamelModel(applicationId);

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

    public void addCpSolutionHistoryInfo(String applicationId, String cpModelPath, boolean success, String description) throws ConcurrentAccessException {
        _initHelperMetadataFromCamelModel(applicationId);

        String methodName = "HistoryHelper.addCpSolutionHistoryInfo()";
        log.debug("{}: BEGIN: helper-id={}, app-id={}, cp-path={}", methodName, id, applicationId, cpModelPath);

        processInTransaction(methodName, transaction -> {
            // get CAMEL model
            String camelPath = StringUtils.startsWith(applicationId, "/") ? applicationId : "/" + applicationId;
            CDOResource resource = transaction.getResource(camelPath);
            CamelModel camelModel = (CamelModel) resource.getContents().get(0);

            // get MMS concept instances
            MmsConceptInstance mmsMetaSolver = getMetadataConceptInstance(camelModel, mmsMetasolverPath);
            MmsConceptInstance mmsSuccess = getMetadataConceptInstance(camelModel, mmsCpSolutionSuccessPath);
            MmsConceptInstance mmsFailure = getMetadataConceptInstance(camelModel, mmsCpSolutionFailurePath);
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

    // ------------------------------------------------------------------------

    public static Cause createCause(String description) {
        Cause cause = ExecutionFactory.eINSTANCE.createCause();
        cause.setName("Cause_"+System.currentTimeMillis());
        if (description!=null) cause.setDescription(description);
        return cause;
    }

    public static HistoryRecord createHistoryRecord(MmsConceptInstance mmsType, Cause cause, String description,
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

    public static HistoryInfo createHistoryInfo(MmsConceptInstance subject, MmsConceptInstance action, String object, EObject objRef, String description) {
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

    // ------------------------------------------------------------------------

    public static <T extends Model,U extends Model> U getModelInCamel(Collection<T> modelsList, Class<U> clazz, String modelName) {
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

    public static MmsConceptInstance getMetadataConceptInstance(CamelModel camelModel, String path) {
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
}
