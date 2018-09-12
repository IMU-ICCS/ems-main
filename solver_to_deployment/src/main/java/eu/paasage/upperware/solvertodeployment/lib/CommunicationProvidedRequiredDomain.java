/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.lib;

import camel.deployment.*;
import eu.paasage.upperware.solvertodeployment.derivator.lib.CloudMLHelper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import java.util.List;

@Slf4j
@Setter
public class CommunicationProvidedRequiredDomain {

	private Component provComponent;
	private Component reqComponent;
	private Communication communication;

	static CommunicationProvidedRequiredDomain findComponentFromCommunication(Communication com) throws S2DException {
		CommunicationProvidedRequiredDomain communicationProducerConsumerDomain = new CommunicationProvidedRequiredDomain();

		SoftwareComponent internalComponentProv = CloudMLHelper.findProvidedComponentFromCommunication(com);
		SoftwareComponent internalComponentReq = CloudMLHelper.findRequiredComponentFromCommunication(com);

		log.debug("--> {} -- {}", internalComponentProv.getName(), internalComponentReq.getName());

		communicationProducerConsumerDomain.setCommunication(com);
		communicationProducerConsumerDomain.setReqComponent(internalComponentReq);
		communicationProducerConsumerDomain.setProvComponent(internalComponentProv);

		return communicationProducerConsumerDomain;
	}

    private static EList<SoftwareComponentInstance> findComponentInstanceFromDeploymentInstanceModels(Component component, DeploymentInstanceModel deploymentInstanceModel) {
        EList<SoftwareComponentInstance> softwareCIs = new BasicEList<>();

		log.debug("Looking for ComponentInstance (SoftwareCI from DM) for type: {}", component.getName());
		StringBuilder logTxt = new StringBuilder();
        for (SoftwareComponentInstance softwareComponentInstance : deploymentInstanceModel.getSoftwareComponentInstances()) {
			log.debug("finComponentInstance: testing {} of type {}", softwareComponentInstance.getName(), softwareComponentInstance.getType().getName());
            logTxt.append("Compare ").append(softwareComponentInstance.getType()).append(" AND ").append(component);
			if (softwareComponentInstance.getType().getName().equals(component.getName())) {
				log.error("Ok Component Instance Find {}", logTxt);
                softwareCIs.add(softwareComponentInstance);
			}
		}
		if (softwareCIs.isEmpty()) {
			log.info("**WARNING. Component Instance not found for component : {}", component.getName());
		}
        return softwareCIs;
    }

    private static EList<SoftwareComponentInstance> findComponentInstanceFromComponents(Component component, List<SoftwareComponentInstance> softwareComponentInstances) {
        EList<SoftwareComponentInstance> internalCIs = new BasicEList<>();

		StringBuilder logTxt = new StringBuilder();
		log.debug("Looking for ComponentInstance (InternalCI list) for type: {}", component.getName());
        for (SoftwareComponentInstance internalCI : softwareComponentInstances) {
			log.debug("finComponentInstance: testing {} of type {}", internalCI.getName(), internalCI.getType().getName());
			logTxt.append("Compare ").append(internalCI.getType()).append(" AND ").append(component);
			if(internalCI.getType().getName().equals(component.getName())) {
				log.debug("Ok Component Instance Find {}", logTxt);
				internalCIs.add(internalCI);
			}
		}
		if (internalCIs.isEmpty())
			log.warn("WARNING. Component Instance not found for component : {}", component.getName());
		return internalCIs;
	}

    private static CommunicationPortInstance findCommunicationPortInstanceFor(CommunicationPort communication,
                                                                              EList<? extends CommunicationPortInstance> requiredCommunicationInstances) {

		if(communication == null) {
			log.error("Try to find Communication port instance with communication port equal to null!!");
			return null;
		}

		CommunicationPortInstance result = null;
		for (CommunicationPortInstance requiredCommunicationInstance : requiredCommunicationInstances) {
			if(requiredCommunicationInstance.getType().getName().equals(communication.getName())) {
				result = requiredCommunicationInstance; 
				break;
			}
		}

		if(result == null) {
			log.error("Unable to find CommunicationPortInstance for {}!!", communication.getName());
		}
		return result;
	}

    public static EList<CommunicationInstance> createCommunicationInstanceFromDemand(Communication com, DeploymentInstanceModel deploymentInstanceModel, List<SoftwareComponentInstance> softwareComponentInstances) throws S2DException {
		// Gathering information
		CommunicationProvidedRequiredDomain result = findComponentFromCommunication(com);
		EList<CommunicationInstance> communicationInstances = new BasicEList<>();

        EList<SoftwareComponentInstance> reqInstances = null;
        EList<SoftwareComponentInstance> provInstances = null;
        if (softwareComponentInstances == null) {
            reqInstances = findComponentInstanceFromDeploymentInstanceModels(result.reqComponent, deploymentInstanceModel);
            provInstances = findComponentInstanceFromDeploymentInstanceModels(result.provComponent, deploymentInstanceModel);
		} else {
            reqInstances = findComponentInstanceFromComponents(result.reqComponent, softwareComponentInstances);
            provInstances = findComponentInstanceFromComponents(result.provComponent, softwareComponentInstances);
		}
		
		if (CollectionUtils.isEmpty(reqInstances)) {
			log.info("WARNING: ignoring communication {}", com.getName());
			return communicationInstances;
		}
		
		if (CollectionUtils.isEmpty(provInstances)) {
			log.info("WARNING: ignoring communication {}", com.getName());
			return communicationInstances;
		}
		
		log.debug("Looking for ComPI...");
		EList<CommunicationPortInstance> providedCommunicationPortInstances = new BasicEList<>();
		EList<CommunicationPortInstance> requiredCommunicationPortInstances = new BasicEList<>();

        for (SoftwareComponentInstance iCI : provInstances) {
            CommunicationPortInstance providedCommunicationPortInstance = findCommunicationPortInstanceFor(result.communication.getProvidedCommunication(), iCI.getProvidedCommunicationInstances());
			if (providedCommunicationPortInstance!=null)
				providedCommunicationPortInstances.add(providedCommunicationPortInstance);
			else
				log.error("Unable to find providedCommunicationPortInstance for {} for communication {}", iCI.getName(), com.getName());
		}

        for (SoftwareComponentInstance iCI : reqInstances) {
            CommunicationPortInstance requiredCommunicationPortInstance = findCommunicationPortInstanceFor(result.communication.getRequiredCommunication(), iCI.getRequiredCommunicationInstances());
			if (requiredCommunicationPortInstance!=null)
				requiredCommunicationPortInstances.add(requiredCommunicationPortInstance);
			else
				log.error("Unable to find requiredCommunicationPortInstance for {} for communication {}", iCI.getName(), com.getName());
		}

		// Creating Communication Instances
		int cnt=0;
		for(CommunicationPortInstance providedPI : providedCommunicationPortInstances) {
			for(CommunicationPortInstance requiredPI : requiredCommunicationPortInstances) {
				CommunicationInstance communicationInstance = DeploymentFactory.eINSTANCE.createCommunicationInstance();
				communicationInstance.setName(result.communication.getName() + "Instance_"+Integer.toString(cnt));
				communicationInstance.setProvidedCommunicationInstance((ProvidedCommunicationInstance) providedPI);
				communicationInstance.setRequiredCommunicationInstance((RequiredCommunicationInstance) requiredPI);
				communicationInstance.setType(result.communication);

				log.debug("Creating CommunicationInstance {}", communicationInstance.getName());

				communicationInstances.add(communicationInstance);
				cnt++;
			}
		}

		return communicationInstances;
	}
}
