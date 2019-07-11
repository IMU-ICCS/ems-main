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
import camel.execution.ExecutionFactory;
import camel.execution.ExecutionModel;
import camel.execution.HistoryInfo;
import camel.execution.HistoryRecord;
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
    private String mmsComponentPath;
    private String mmsSuccessPath;
    private String mmsFailurePath;

    public void initHelperMetadata(String basePath, String componentPath, String successActionPath, String failureActionPath) {
        mmsBasePath = Objects.toString(basePath, "");
        mmsComponentPath = mmsBasePath + (!StringUtils.startsWith(componentPath, ".") ? "." : "") + componentPath;
        mmsSuccessPath = mmsBasePath + (!StringUtils.startsWith(successActionPath, ".") ? "." : "") + successActionPath;
        mmsFailurePath = mmsBasePath + (!StringUtils.startsWith(failureActionPath, ".") ? "." : "") + failureActionPath;
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
//            CDOResource cpResource = transaction.getResource(cpModelPath);
//            cpModel = (ConstraintProblem) cpResource.getContents().get(0);

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

   /* public static void main(String args[]) {
        try {
            String appId = "zzz_history_test";
            String cpPath = "zzz_history_test_cp";
            HistoryHelper helper = new HistoryHelper();
            helper.initHelperMetadata("MetaDataModel_1.MELODICMetadataSchema.History", ".PlatformComponent.META_SOLVER", ".PlatformAction.CP_SOLUTION_PRODUCED", ".PlatformAction.CP_SOLUTION_FAILED");
            helper.addCpSolutionHistoryInfo(appId, cpPath, true, "Meta-Solver: New Solution produced successfully");
            log.info("OOOOOOK");
        } catch (Exception ex) {
            log.error("main(): ", ex);
        }
    }*/
}
