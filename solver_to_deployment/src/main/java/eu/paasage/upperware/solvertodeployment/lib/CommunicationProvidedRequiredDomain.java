/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.lib;

import java.util.List;

import org.apache.log4j.Logger;
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


public class CommunicationProvidedRequiredDomain {
	
	private static Logger log = Logger.getLogger(CommunicationProvidedRequiredDomain.class);

	public Component provComponent, reqComponent;
	public InternalComponentInstance provComponentInstance, reqComponentInstance;
	public Communication communication;

	static CommunicationProvidedRequiredDomain findComponentFromCommunication(Communication com, DeploymentModel deployementModel) throws S2DException
	{
		CommunicationProvidedRequiredDomain communicationProducerConsumerDomain = new CommunicationProvidedRequiredDomain();

//		log.info("Prod:"+com.getProvidedCommunication().getName());
//		log.info("Req:"+com.getRequiredCommunication().getName());

		InternalComponent internalComponentProv = CloudMLHelper.findProvidedComponentFromCommunication(com);
		InternalComponent internalComponentReq = CloudMLHelper.findRequiredComponentFromCommunication(com);

		log.debug("--> "+internalComponentProv.getName()+" -- "+internalComponentReq.getName());

		communicationProducerConsumerDomain.communication = com;
		communicationProducerConsumerDomain.reqComponent = internalComponentReq;
		communicationProducerConsumerDomain.provComponent = internalComponentProv ;

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

	static EList<InternalComponentInstance> findComponentInstanceFromComponent(Component component, List<InternalComponentInstance> internalComponentInstances)
	{
		EList<InternalComponentInstance> internalCIs = new BasicEList<InternalComponentInstance>();

		String logTxt = "";
		log.debug("Looking for ComponentInstance (InternalCI list) for type: "+component.getName());
		for (InternalComponentInstance internalCI : internalComponentInstances)
		{
			log.debug("finComponentInstance: testing "+internalCI.getName()+" of type "+internalCI.getType().getName());
			logTxt += "Compare " +  internalCI.getType()  + " AND " + component;
			if(internalCI.getType().getName().equals(component.getName()))
			{
				log.debug("Ok Component Instance Find " + logTxt);
				internalCIs.add(internalCI);
			}
		}
		if (internalCIs.isEmpty())
			log.info("WARNING. Component Instance not found for component : " + component.getName());
		return internalCIs;
	}
	
	public static CommunicationPortInstance findCommuniCationPortInstanceFor(CommunicationPort communication,
			EList<? extends CommunicationPortInstance> requiredCommunicationInstances)
	{
		if(communication == null)
		{
			log.error("Try to find Communication port instance with commmunication port equal to null !!");
			return null;
		}
		
		CommunicationPortInstance result = null;
		for (CommunicationPortInstance requiredCommunicationInstance : requiredCommunicationInstances) {
			if(requiredCommunicationInstance.getType().getName().equals(communication.getName())) 
			{
				result = requiredCommunicationInstance; 
				break;
			}
		}

		if(result == null)
		{
			log.error("Unable to find CommunicationPortInstance for " +communication.getName() + "!!" );
		}
		return result;
	}

	public static EList<CommunicationInstance> createCommunicationInstanceFromDemand(Communication com, DeploymentModel deployementModel, List<InternalComponentInstance> internalComponentInstances) throws S2DException
	{
		// Gathering information
		CommunicationProvidedRequiredDomain result = findComponentFromCommunication(com, deployementModel);
		EList<CommunicationInstance> communicationInstances = new BasicEList<CommunicationInstance>();

		EList<InternalComponentInstance> reqInstances  = null;
		EList<InternalComponentInstance> provInstances = null;
		if (internalComponentInstances == null)
		{
			reqInstances  = findComponentInstanceFromComponent(result.reqComponent, deployementModel);
			provInstances = findComponentInstanceFromComponent(result.provComponent, deployementModel);
		}
		else
		{
			reqInstances  = findComponentInstanceFromComponent(result.reqComponent, internalComponentInstances);
			provInstances = findComponentInstanceFromComponent(result.provComponent, internalComponentInstances);
		}
		
		if ((reqInstances == null)||reqInstances.isEmpty())
		{
			log.info("WARNING: ignoring communication "+com.getName());
//			throw new S2DException("Unable to find component Instance (consumer) associated to component " + result.reqComponent.getName());
			return communicationInstances;
		}
		
		if ((provInstances == null)||provInstances.isEmpty())
		{
			log.info("WARNING: ignoring communication "+com.getName());
			return communicationInstances;
//			throw new S2DException("Unable to find component Instance (producer) associated to component " + result.provComponent.getName());
		}
		
		log.debug("Looking for ComPI...");
		EList<CommunicationPortInstance> providedCommunicationPortInstances = new BasicEList<CommunicationPortInstance>();
		EList<CommunicationPortInstance> requiredCommunicationPortInstances = new BasicEList<CommunicationPortInstance>();

		for(InternalComponentInstance iCI : provInstances)
		{
			CommunicationPortInstance providedCommunicationPortInstance = findCommuniCationPortInstanceFor(result.communication.getProvidedCommunication(), iCI.getProvidedCommunicationInstances());
			if (providedCommunicationPortInstance!=null)
				providedCommunicationPortInstances.add(providedCommunicationPortInstance);
			else
				log.error("Unable to find providedCommunicationPortInstance for " + iCI.getName()+" for communication "+com.getName());
		}
		for(InternalComponentInstance iCI : reqInstances) 
		{
			CommunicationPortInstance requiredCommunicationPortInstance = findCommuniCationPortInstanceFor(result.communication.getRequiredCommunication(), iCI.getRequiredCommunicationInstances());
			if (requiredCommunicationPortInstance!=null)
				requiredCommunicationPortInstances.add(requiredCommunicationPortInstance);
			else
				log.error("Unable to find requiredCommunicationPortInstance for " + iCI.getName()+" for communication "+com.getName());
		}

		// Creating Communication Instances
		int cnt=0;
		for(CommunicationPortInstance providedPI : providedCommunicationPortInstances)
		{
			for(CommunicationPortInstance requiredPI : requiredCommunicationPortInstances)
			{
				CommunicationInstance communicationInstance = DeploymentFactory.eINSTANCE.createCommunicationInstance();
				communicationInstance.setName(result.communication.getName() + "Instance_"+Integer.toString(cnt));
				log.debug("Creating CommunicationInstance "+communicationInstance.getName());
				communicationInstance.setProvidedCommunicationInstance((ProvidedCommunicationInstance) providedPI);
				communicationInstance.setRequiredCommunicationInstance((RequiredCommunicationInstance) requiredPI);
				communicationInstance.setType(result.communication);
				
				communicationInstances.add(communicationInstance);
				cnt++;
			}
		}

		return communicationInstances;
	}
}
