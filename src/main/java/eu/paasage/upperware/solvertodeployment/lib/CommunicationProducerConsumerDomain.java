/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.lib;

import java.util.List;

import org.apache.log4j.Logger;
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


public class CommunicationProducerConsumerDomain {
	
	private static Logger log = Logger.getLogger(CommunicationProducerConsumerDomain.class);

	public Component producerComponent,consumerComponent;
	public InternalComponentInstance producerComponentInstance,consumerComponentInstance;
	public Communication communication;

	static String computeConsumerName(String input)
	{
		return input.split("To")[1].replaceAll("Instance", "").replaceAll("Communication","");
	}

	static String computeProducerName(String input)
	{
		return input.split("To")[0];
	}

	static CommunicationProducerConsumerDomain findComponentFromCommunicationName(String componentName, DeploymentModel deployementModel) throws S2DException
	{
		EList<Communication> communications = deployementModel.getCommunications();

		CommunicationProducerConsumerDomain communicationProducerConsumerDomain = new CommunicationProducerConsumerDomain();

		String consumer = computeConsumerName(componentName);
		String producer  =  computeProducerName(componentName);
		String logTxt = "";
		for(int i = 0 ; i < communications.size(); i++)
		{
			InternalComponent internalComponentConsumer = (InternalComponent)(communications.get(i).getProvidedCommunication().eContainer());
			InternalComponent internalComponentProducer = (InternalComponent)(communications.get(i).getRequiredCommunication().eContainer()); 

			logTxt += ("\n\nCompare cons : " + internalComponentConsumer.getName() + " W " + consumer );
			logTxt += ("\nCompare prod : " +internalComponentProducer.getName() + " W " +  producer );
			if(internalComponentConsumer.getName().equals(consumer) && internalComponentProducer.getName().equals(producer))
			{	
				communicationProducerConsumerDomain.communication = communications.get(i);
				communicationProducerConsumerDomain.consumerComponent = internalComponentConsumer;
				communicationProducerConsumerDomain.producerComponent = internalComponentProducer ;
				log.info ("OK Component find " + componentName);
				return communicationProducerConsumerDomain;
			}

		}
		throw new S2DException("ERROR Component not FIND !"+ componentName +  " " + logTxt);
	}


	static InternalComponentInstance findComponentInstanceFromComponent(Component component, DeploymentModel deployementModel)
	{
		EList<InternalComponentInstance> internalComponentInstances = deployementModel.getInternalComponentInstances();

		String logTxt = "";
		for(int i = 0 ; i < internalComponentInstances.size(); i++)
		{
			logTxt += "Compare " +  internalComponentInstances.get(i).getType()  + " AND " + component;
			if(internalComponentInstances.get(i).getType().toString().equals(component.toString()))
			{
				log.error("Ok Component Instance Find " + logTxt);
				return internalComponentInstances.get(i);
			}
		}
		log.error("ERROR. Component Instance not find for component : " + component.getName()  + logTxt);
		return null;


	}

	static InternalComponentInstance findComponentInstanceFromComponent(Component component, List<InternalComponentInstance> internalComponentInstances)
	{
		String logTxt = "";
		for (InternalComponentInstance internalComponentInstance : internalComponentInstances) {

			logTxt += "Compare " +  internalComponentInstance.getType()  + " AND " + component;
			if(internalComponentInstance.getType().toString().equals(component.toString()))
			{

				log.debug("Ok Component Instance Find " + logTxt);
				return internalComponentInstance;
			}

		}
		log.error("ERROR. Component Instance not find for component : " + component.getName()  + logTxt);
		return null;
	}
	
	public static CommunicationPortInstance findCommuniCationPortInstanceFor(
			CommunicationPort communication,
			EList<? extends CommunicationPortInstance> requiredCommunicationInstances)
	{
		if(communication == null)
		{
			log.error("Try to find Communication port instance with commmunication port equal to null !!");
			return null;
		}
		
		CommunicationPortInstance result = null;
		for (CommunicationPortInstance requiredCommunicationInstance : requiredCommunicationInstances) {
			if(requiredCommunicationInstance.getType() == communication){
				result = requiredCommunicationInstance; 
				break;
			}
		}

		if(result == null)
		{
			log.error("Unable to find CommunicationInstance for " +communication.getName() + "!!" );
		}
		return result;
	}

	public static CommunicationInstance createCommunicationInstanceFromDemand(String demand, DeploymentModel deployementModel ,List<InternalComponentInstance> internalComponentInstances) throws S2DException
	{
		CommunicationProducerConsumerDomain result = findComponentFromCommunicationName(demand,deployementModel);

		CommunicationInstance communicationInstance = DeploymentFactory.eINSTANCE.createCommunicationInstance();
		communicationInstance.setName(result.communication.getName() + "Instance");
		InternalComponentInstance consumerInstance  = null;
		InternalComponentInstance producerInstance = null;
		if (internalComponentInstances == null)
		{
			consumerInstance  = findComponentInstanceFromComponent(result.consumerComponent, deployementModel);
			producerInstance = findComponentInstanceFromComponent(result.producerComponent, deployementModel);
		}
		else
		{
			consumerInstance  = findComponentInstanceFromComponent(result.consumerComponent, internalComponentInstances);
			producerInstance = findComponentInstanceFromComponent(result.producerComponent, internalComponentInstances);
		}
		
		if (consumerInstance == null)
		{
			throw new S2DException("Unable to find component Instance (consumer) associated to component " + result.consumerComponent.getName());
		}
		
		if(producerInstance == null)
		{
			throw new S2DException("Unable to find component Instance (producer) associated to component " + result.producerComponent.getName());
		}

		CommunicationPortInstance providedCommunicationPortInstance = findCommuniCationPortInstanceFor(result.communication.getProvidedCommunication(),consumerInstance.getProvidedCommunicationInstances());
		CommunicationPortInstance requiredCommunicationPortInstance = findCommuniCationPortInstanceFor(result.communication.getRequiredCommunication(),producerInstance.getRequiredCommunicationInstances());

		if(providedCommunicationPortInstance ==  null || requiredCommunicationPortInstance == null)
		{
			log.error("Unable to find providedCommunicationPortInstance or requiredCommunicationPortInstance for " + demand);
		}
		communicationInstance.setProvidedCommunicationInstance((ProvidedCommunicationInstance) providedCommunicationPortInstance);
		communicationInstance.setRequiredCommunicationInstance((RequiredCommunicationInstance) requiredCommunicationPortInstance);
		communicationInstance.setType(result.communication);

		return communicationInstance;
	}
}
