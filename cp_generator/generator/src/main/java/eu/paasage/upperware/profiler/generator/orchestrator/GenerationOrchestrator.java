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

import com.google.common.collect.Maps;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.type.TypePackage;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.CloudMLElementUpperware;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;
import eu.paasage.upperware.profiler.cp.generator.model.lib.PaaSageConfigurationWrapper;
import eu.paasage.upperware.profiler.generator.db.IDatabaseProxy;
import eu.paasage.upperware.profiler.generator.notification.NotificationService;
import eu.paasage.upperware.profiler.generator.result.CpGenerationResult;
import eu.paasage.upperware.profiler.generator.service.camel.ConstraintProblemService;
import eu.paasage.upperware.profiler.generator.service.camel.PaasageConfigurationService;
import eu.paasage.upperware.profiler.generator.service.camel.SloService;
import fr.inria.paasage.saloon.camel.mapping.MappingPackage;
import fr.inria.paasage.saloon.camel.ontology.OntologyPackage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GenerationOrchestrator {

    private static final Map<String, Object> LOCKS = Maps.newConcurrentMap();

    private IDatabaseProxy database;
    private PaasageConfigurationService paaSageConfigurationService;
    private ConstraintProblemService constraintProblemService;
    private NotificationService notificationService;
    private SloService sloService;


    /**
     * The default constructor
     */
    public GenerationOrchestrator() {
        ApplicationPackage.eINSTANCE.eClass();
        CpPackage.eINSTANCE.eClass();
        TypesPackage.eINSTANCE.eClass();
        TypesPaasagePackage.eINSTANCE.eClass();
        OntologyPackage.eINSTANCE.eClass();
        MappingPackage.eINSTANCE.eClass();
        TypePackage.eINSTANCE.eClass();

        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
    }

    /**
     * Generates the CP model by using the provided model path
     *
     * @param resourceName The path of the model
     * @return The id or path of the generate CP Model. It is stored in CDO
     */

    @Async
    public void generateCPModelAndSendNotification(String resourceName, String notificationUri, String requestUuid){

        try {
            acquireLock(resourceName);
        } catch (Exception e) {
            notificationService.notifyError(resourceName, notificationUri, requestUuid, e.getMessage());
        }

        CpGenerationResult cpGenerationResult;
        try {
            cpGenerationResult = generateCPModel(resourceName);
        } catch (Exception e) {
            log.error("Error during generating CpModel.", e);
            notificationService.notifyError(resourceName, notificationUri, requestUuid, e.getMessage());
            return;
        } finally {
            releaseLock(resourceName);
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

    private CpGenerationResult generateCPModel(String resourceName) {
        log.info("************************************CP Generator Model To Solver************************************");

        CpGenerationResult result = null;

        log.info("Loading camel model {}", resourceName);
        CamelModel camelModel = createCamelModel(resourceName);
        log.info("Camel model {} loaded", resourceName);
        if (camelModel != null) {

            PaaSageConfigurationWrapper pcw = paaSageConfigurationService.createPaasageConfigurationWrapper(camelModel);
            PaasageConfiguration pc = pcw.getPaasageConfiguration();

            if(!pcw.hasUserSolution() && CollectionUtils.isNotEmpty(pc.getProviders()) && CollectionUtils.isEmpty(pcw.getComponentsWithoutVM()) && pcw.isHasCorrectHostingRelationships()) {
                log.info("** Calling CPModelDerivator");

                ConstraintProblem cp = constraintProblemService.derivateConstraintProblem(camelModel, pc);
                sloService.update(camelModel, cp);

                String cpId = pc.getId();
                log.debug("** Calling DatabseProxy ");
                database.saveModels(pc, cp);
                log.info("** CP Model Id: "+ cpId);

                result = CpGenerationResult.succes(cpId);
            } else if(pcw.hasUserSolution() && pcw.isValidUserSolution()) {
                result = CpGenerationResult.info("The user already provided a solution for the deployment. The CP Model will be not generated!");
            } else if(CollectionUtils.isEmpty(pc.getProviders())) {
                result = CpGenerationResult.info("There is not a suitable provider. The CP Model will be not generated!");
            } else if(!pcw.isHasCorrectHostingRelationships()) {
                result = CpGenerationResult.info("There are missing hosting relationships in the deployment model. The CP Model will be not generated!");
            } else if(CollectionUtils.isNotEmpty(pcw.getComponentsWithoutVM())) {
                String msg = pcw.getComponentsWithoutVM()
                        .stream()
                        .map(CloudMLElementUpperware::getCloudMLId)
                        .collect(Collectors.joining(", ", "There are not suitable providers for the following components: ", " The CP Model will be not generated!"));

                result = CpGenerationResult.info(msg);
            } else {
                result = CpGenerationResult.info("The user already provided a solution for the deployment but it is not valid. The CP Model will be not generated!");
            }
        } else {
            result =  CpGenerationResult.error("There is not Processor for Camel Models. The input model can not be processed");
        }
        return result;
    }

//    private InternalComponent getInternalComponent(String vmProvidedHostName, EList<InternalComponent> internalComponents, EList<Hosting> hostings) {
//        InternalComponent result = null;
//
//        String requiredHostName = null;
//        for (Hosting hosting : hostings) {
//            ProvidedHost providedHost = hosting.getProvidedHost();
//            if (providedHost != null) {
//                String name = providedHost.getName();
//                if (vmProvidedHostName.equals(name)) {
//                    RequiredHost requiredHost = hosting.getRequiredHost();
//                    if (requiredHost != null) {
//                        requiredHostName = requiredHost.getName();
//                    }
//                }
//            }
//        }
//
//        if (requiredHostName != null) {
//            for (InternalComponent internalComponent : internalComponents) {
//                if (requiredHostName.equals(internalComponent.getRequiredHost().getName())) {
//                    result = internalComponent;
//                }
//            }
//        }
//
//        return result;
//    }

    private CamelModel createCamelModel(String resourceName) {
        return database.getCamelModel(resourceName);
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

}
