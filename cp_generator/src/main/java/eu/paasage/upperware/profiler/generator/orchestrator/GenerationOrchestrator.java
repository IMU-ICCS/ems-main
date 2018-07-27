/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 * <p>
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 * <p>
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.generator.orchestrator;

import camel.core.CamelModel;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.profiler.generator.communication.CdoService;
import eu.paasage.upperware.profiler.generator.notification.NotificationService;
import eu.paasage.upperware.profiler.generator.result.CpGenerationResult;
import eu.paasage.upperware.profiler.generator.service.camel.NewConstraintProblemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static eu.passage.upperware.commons.MelodicConstants.CDO_SERVER_PATH;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GenerationOrchestrator {

    private NotificationService notificationService;
    private RequestSynchronizer requestSynchronizer;

    private CdoService cdoService;
    private NewConstraintProblemService newConstraintProblemService;

    /**
     * Generates the CP model by using the provided model path
     *
     * @param resourceName The path of the model
     * @return The id or path of the generate CP Model. It is stored in CDO
     */

    @Async
    public void generateCPModelAndSendNotification(String resourceName, String notificationUri, String requestUuid){

        try {
            requestSynchronizer.acquireLock(resourceName);
        } catch (Exception e) {
            notificationService.notifyError(resourceName, notificationUri, requestUuid, e.getMessage());
        }

        CpGenerationResult cpGenerationResult;
        CDOSessionX cdoSessionX = null;
        CDOTransaction cdoTransaction = null;
        try {
            log.info("Opening transaction...");
            cdoSessionX = cdoService.openSession();
            cdoTransaction = cdoSessionX.openTransaction();
            log.info("Transaction successfully opened!");
            cpGenerationResult = generateCPModel(resourceName, cdoTransaction, cdoSessionX);
            log.info("Transaction has been commited!");
        } catch (Exception e) {
            log.error("Error during generating CpModel.", e);
            notificationService.notifyError(resourceName, notificationUri, requestUuid, e.getMessage());
            return;
        } finally {
            log.info("Going to close transaction");
            if (cdoSessionX != null) {
                cdoSessionX.closeTransaction(cdoTransaction);
                cdoSessionX.closeSession();
            }
            requestSynchronizer.releaseLock(resourceName);
        }
        sendNotification(cpGenerationResult, resourceName, notificationUri, requestUuid);
    }

    private void sendNotification(CpGenerationResult cpGenerationResult, String resourceName, String notificationUri, String requestUuid){

        switch (cpGenerationResult.getStatus()) {
            case SUCCESS:
                log.info("Generated cpModelPath: {}", cpGenerationResult.getCpModelPath());
                notificationService.notifySuccess(resourceName, notificationUri, requestUuid,  cpGenerationResult.getCpModelPath());
                break;
            case INFO:
                log.info(cpGenerationResult.getMsg());
                notificationService.notifyError(null, notificationUri, requestUuid, cpGenerationResult.getMsg());
                break;
            case ERROR:
                log.error(cpGenerationResult.getMsg());
                notificationService.notifyError(null, notificationUri, requestUuid, cpGenerationResult.getMsg());
                break;
        }
    }

    private CpGenerationResult generateCPModel(String resourceName, CDOTransaction cdoTransaction, CDOSessionX cdoSessionX) {
        log.info("************************************CP Generator Model To Solver************************************");
        log.info("Loading camel model {}", resourceName);

        CamelModel camelModel;
        try {
            camelModel = cdoService.getCamelModel(resourceName, cdoTransaction);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            return CpGenerationResult.error("There is not Processor for Camel Models. The input model can not be processed");
        }

        log.info("Camel model {} loaded", resourceName);

        String cpName = getCpName(camelModel);

        //TODO - ten wrapper moze nie byc potrzebny.
        PaasageConfiguration pc = null;

        log.info("** Calling CPModelDerivator");

        ConstraintProblem cp = newConstraintProblemService.createConstraintProblem(camelModel, cpName);

        String cpId = CDO_SERVER_PATH + cpName;
        log.debug("** Calling DatabseProxy ");

        cdoService.saveModels(pc, cp, cdoSessionX);

        log.info("** CP Model Id: {}", cpId);

        return CpGenerationResult.succes(cpId);
    }

    private String getCpName(CamelModel camelModel) {
        String appId = getAppId(camelModel);
        return appId + System.currentTimeMillis();
    }

    private String getAppId(CamelModel camelModel) {
        return camelModel.getApplication().getName();
    }
}
