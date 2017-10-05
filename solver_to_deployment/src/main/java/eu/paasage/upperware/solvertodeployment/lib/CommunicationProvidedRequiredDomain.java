/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.lib;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import eu.paasage.camel.deployment.Communication;
import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.CommunicationPort;
import eu.paasage.camel.deployment.CommunicationPortInstance;
import eu.paasage.camel.deployment.Component;
import eu.paasage.camel.deployment.DeploymentFactory;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.InternalComponent;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.ProvidedCommunicationInstance;
import eu.paasage.camel.deployment.RequiredCommunicationInstance;
import eu.paasage.upperware.solvertodeployment.derivator.lib.CloudMLHelper;

@Slf4j
@Setter
public class CommunicationProvidedRequiredDomain {

	private Component provComponent;
	private Component reqComponent;
	private Communication communication;

	static CommunicationProvidedRequiredDomain findComponentFromCommunication(Communication com) throws S2DException {
		CommunicationProvidedRequiredDomain communicationProducerConsumerDomain = new CommunicationProvidedRequiredDomain();

		InternalComponent internalComponentProv = CloudMLHelper.findProvidedComponentFromCommunication(com);
		InternalComponent internalComponentReq = CloudMLHelper.findRequiredComponentFromCommunication(com);

		log.debug("--> "+internalComponentProv.getName()+" -- "+internalComponentReq.getName());

		communicationProducerConsumerDomain.setCommunication(com);
		communicationProducerConsumerDomain.setReqComponent(internalComponentReq);
		communicationProducerConsumerDomain.setProvComponent(internalComponentProv);

		return communicationProducerConsumerDomain;
	}

	static EList<InternalComponentInstance> findComponentInstanceFromComponent(Component component, DeploymentModel deployementModel)
	{
		EList<InternalComponentInstance> internalComponentInstances = deployementModel.getInternalComponentInstances();
		EList<InternalComponentInstance> internalCIs = new BasicEList<InternalComponentInstance>();
		
		log.debug("Looking for ComponentInstance (InternalCI from DM) for type: "+component.getName());
		String logTxt = "";
		for(InternalComponentInstance internalCI : internalComponentInstances)
		{
			log.debug("finComponentInstance: testing"+internalCI.getName()+" of type "+internalCI.getType().getName());
			logTxt += "Compare " +  internalCI.getType()  + " AND " + component;
			if(internalCI.getType().getName().equals(component.getName()))
			{
				log.error("Ok Component Instance Find " + logTxt);
				internalCIs.add(internalCI);
			}
		}
		if (internalCIs.isEmpty())
			log.info("**WARNING. Component Instance not found for component : " + component.getName());
		return internalCIs;
	}

	static EList<InternalComponentInstance> findComponentInstanceFromComponent(Component component, List<InternalComponentInstance> internalComponentInstances){
		EList<InternalComponentInstance> internalCIs = new BasicEList<>();

		String logTxt = "";
		log.debug("Looking for ComponentInstance (InternalCI list) for type: "+component.getName());
		for (InternalComponentInstance internalCI : internalComponentInstances) {
			log.debug("finComponentInstance: testing "+internalCI.getName()+" of type "+internalCI.getType().getName());
			logTxt += "Compare " +  internalCI.getType()  + " AND " + component;
			if(internalCI.getType().getName().equals(component.getName())) {
				log.debug("Ok Component Instance Find " + logTxt);
				internalCIs.add(internalCI);
			}
		}
		if (internalCIs.isEmpty())
			log.warn("WARNING. Component Instance not found for component : {}", component.getName());
		return internalCIs;
	}
	
	public static CommunicationPortInstance findCommuniCationPortInstanceFor(CommunicationPort communication,
			EList<? extends CommunicationPortInstance> requiredCommunicationInstances) {

		if(communication == null) {
			log.error("Try to find Communication port instance with commmunication port equal to null !!");
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
			log.error("Unable to find CommunicationPortInstance for " +communication.getName() + "!!" );
		}
		return result;
	}

	public static EList<CommunicationInstance> createCommunicationInstanceFromDemand(Communication com, DeploymentModel deployementModel, List<InternalComponentInstance> internalComponentInstances) throws S2DException {
		// Gathering information
		CommunicationProvidedRequiredDomain result = findComponentFromCommunication(com);
		EList<CommunicationInstance> communicationInstances = new BasicEList<>();

		EList<InternalComponentInstance> reqInstances  = null;
		EList<InternalComponentInstance> provInstances = null;
		if (internalComponentInstances == null) {
			reqInstances  = findComponentInstanceFromComponent(result.reqComponent, deployementModel);
			provInstances = findComponentInstanceFromComponent(result.provComponent, deployementModel);
		} else {
			reqInstances  = findComponentInstanceFromComponent(result.reqComponent, internalComponentInstances);
			provInstances = findComponentInstanceFromComponent(result.provComponent, internalComponentInstances);
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

		for(InternalComponentInstance iCI : provInstances) {
			CommunicationPortInstance providedCommunicationPortInstance = findCommuniCationPortInstanceFor(result.communication.getProvidedCommunication(), iCI.getProvidedCommunicationInstances());
			if (providedCommunicationPortInstance!=null)
				providedCommunicationPortInstances.add(providedCommunicationPortInstance);
			else
				log.error("Unable to find providedCommunicationPortInstance for " + iCI.getName()+" for communication "+com.getName());
		}

		for(InternalComponentInstance iCI : reqInstances) {
			CommunicationPortInstance requiredCommunicationPortInstance = findCommuniCationPortInstanceFor(result.communication.getRequiredCommunication(), iCI.getRequiredCommunicationInstances());
			if (requiredCommunicationPortInstance!=null)
				requiredCommunicationPortInstances.add(requiredCommunicationPortInstance);
			else
				log.error("Unable to find requiredCommunicationPortInstance for " + iCI.getName()+" for communication "+com.getName());
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
