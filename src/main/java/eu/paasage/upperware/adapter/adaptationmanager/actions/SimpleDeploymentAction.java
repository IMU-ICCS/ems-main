/*
 * Copyright (c) 2014 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.actions;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import eu.paasage.camel.CamelModel;
//import eu.paasage.camel.deployment.CloudMLElement;
import eu.paasage.camel.deployment.Communication;
import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.Component;
import eu.paasage.camel.deployment.ComponentInstance;
import eu.paasage.camel.deployment.Configuration;
//import eu.paasage.camel.deployment.ComputationalResource;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.DeploymentPackage;
import eu.paasage.camel.deployment.HostingInstance;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.ProvidedCommunicationInstance;
import eu.paasage.camel.deployment.RequiredCommunicationInstance;
import eu.paasage.camel.deployment.RequiredHostInstance;
import eu.paasage.camel.deployment.VM;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.organisation.CloudProvider;
//import eu.paasage.camel.scalability.HorizontalScalabilityPolicy;
import eu.paasage.camel.scalability.HorizontalScalingAction;
import eu.paasage.camel.scalability.ScalabilityModel;
//import eu.paasage.camel.scalability.ScalabilityPolicy;
import eu.paasage.camel.scalability.ScalabilityRule;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecutionwareError;
import eu.paasage.upperware.adapter.adaptationmanager.core.Coordinator;
import eu.paasage.upperware.plangenerator.model.task.ConfigurationTask;


public class SimpleDeploymentAction implements Action {

	private static final Logger LOGGER = Logger
			.getLogger(SimpleDeploymentAction.class.getName());

	static DeploymentModel targetModel;
	JsonObject jsonModel;
	ExecInterfacer execInterfacer;

	public SimpleDeploymentAction(DeploymentModel targetModel, ExecInterfacer execInterfacer) {
		LOGGER.log(Level.INFO, "Creating deployment action");
		this.targetModel = targetModel;
//		this.jsonModel = generateJSON(targetModel);//COMMENTED FOR DEBUG
		
		//Added by Arnab
		this.execInterfacer = execInterfacer;
	}

	public void execute(Map<String, Object> input, Map<String, Object> output)
			throws ActionError {
		LOGGER.log(Level.INFO, "Executing deployment action");
		ExecInterfacer execInterfacer = (ExecInterfacer) input
				.get("execInterfacer");
		try {
			execInterfacer.simpleDeploy(jsonModel);
		} catch (ExecutionwareError e) {
			throw new ActionError();
		}
		output.putAll(input);
	}
	
	public void run(){
		try{
			LOGGER.log(Level.INFO, "Simple deployment thread for " + targetModel.getName());
			
			System.out.print("***" + this.toString() + " *** Data/Objects available from its dependencies ");
			Collection<Object> depActions = Coordinator.getNeighbourDependencies(this);
			for(Object obj : depActions)
				System.out.print("-- " + obj.toString());
			System.out.print("\n");
			mapAndDeploy();
			
			LOGGER.log(Level.INFO, "End of Simple deployment thread for " + targetModel.getName());

		} catch(Exception e){
			try {
				throw new ActionError();
			} catch (ActionError ae) {
				// TODO Auto-generated catch block
				ae.printStackTrace();
			}
		}
	}
	
	private void mapAndDeploy(){
		LOGGER.log(Level.INFO, "Performing SimpleDeployment for " + targetModel.getName());
		System.out.println("Performing SimpleDeployment");

		LOGGER.log(Level.INFO, "Application name : " + targetModel.getName());
		dataShare.createApplication(targetModel.getName(), targetModel.getName());

		try {
			execInterfacer.login("john.doe@example.com", "admin", "admin");
			LOGGER.log(Level.INFO, "User Name " + execInterfacer.getUserName());
			execInterfacer.createApp(targetModel.getName());
			LOGGER.log(Level.INFO, "Getting Application Id " + dataShare.getApplicationId());
		} catch (ExecutionwareError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static JsonObject generateJSON(EObject targetModel) {
		
		LOGGER.log(Level.INFO, "Generating JSONObject for SimpleDeployment");
		System.out.println("Generating JSONObject for SimpleDeployment");

		JsonObject target = new JsonObject();

		target.add("applicationName", (String) targetModel.eGet(targetModel
				.eClass().getEStructuralFeature("name")));
		JsonArray componentInstances = new JsonArray();
		JsonArray communicationInstances = new JsonArray();

		TreeIterator<EObject> eAllContents = targetModel.eAllContents();
		while (eAllContents.hasNext()) {
			EObject next = eAllContents.next();
			if (next instanceof InternalComponentInstance) {
				JsonObject tmp = convertInternalComponentInstance((InternalComponentInstance) next);
				componentInstances.add(tmp);
			} else if (next instanceof CommunicationInstance) {
				JsonObject tmp = convertCommunicationInstance((CommunicationInstance) next);
				communicationInstances.add(tmp);
			}
		}
		target.add("components", componentInstances);
		LOGGER.log(Level.INFO, "Added " + componentInstances.size()
				+ " components");
		target.add("communications", communicationInstances);
		LOGGER.log(Level.INFO, "Added " + communicationInstances.size()
				+ " communications");
		return target;
	}

	// private JsonObject convertInternalComponent(EObject next) {
	// InternalComponent obj = (InternalComponent) next;
	// JsonObject result = convertAttributes(obj);
	// result.add("resources", convertResources(obj));
	// EList<RequiredCommunication> requiredCommunications = obj
	// .getRequiredCommunications();
	// JsonArray rc = new JsonArray();
	// for (RequiredCommunication requiredCommmunication :
	// requiredCommunications) {
	// JsonObject e = convertAttributes(requiredCommmunication);
	// Collection references = EcoreUtil.UsageCrossReferencer.find(
	// requiredCommmunication, requiredCommmunication.eResource()
	// .getResourceSet());
	// Communication com = null;
	// for (Iterator iter = references.iterator(); iter.hasNext();) {
	// EStructuralFeature.Setting setting = (EStructuralFeature.Setting) iter
	// .next();
	// if (setting.getEStructuralFeature() ==
	// DeploymentPackage.Literals.COMMUNICATION__REQUIRED_COMMUNICATION) {
	// com = (Communication) setting.getEObject();
	// break;
	// }
	// }
	// ComputationalResource r = com.getRequiredPortResource();
	// if (r != null) {
	// e.add("resource", convertAttributes(r));
	// }
	// rc.add(e);
	// }
	// if (!rc.isEmpty())
	// result.add("requiredCommunications", rc);
	// return result;
	// }

//	private static JsonObject convertResource(CloudMLElement obj) {
//		JsonObject result = new JsonObject();
//		EList<ComputationalResource> resources = obj.getResources();
//		if (resources.isEmpty())
//			return result;
//		ComputationalResource r = resources.get(0);
//		if (r == null)
//			return result;
//		return convertAttributes(r);
//	}

	private static JsonObject convertInternalComponentInstance(
			InternalComponentInstance obj) {

		Component comp = obj.getType();
//		EList<ComputationalResource> resources = comp.getResources();
//		ComputationalResource r = null;
//		if (!(resources.isEmpty()))
//		r = resources.get(0);
		EList<Configuration> configurations = comp.getConfigurations();
		Configuration r = null;
		if (!(configurations.isEmpty()))
			r = configurations.get(0);
		String download = null;
		String install = null;
		String start = null;
		String stop = null;
		if (r != null) {
			download = r.getDownloadCommand();
			install = r.getInstallCommand();
			start = r.getStartCommand();
			stop = r.getStopCommand();
		}
		VMInstance host = getHostVM(obj);
		VM vm = (VM) host.getType();
		String cloud = null;
//		CloudProvider provider = vm.getProvider();
//		if (provider != null) {
//			cloud = provider.getName();
//		}
//		ScalabilityInfo info = getScalabilityInfo(obj);
		int minInstances = 1;
		int maxInstances = 1;
		String ruleName = null;
//		if (info != null) {
//			minInstances = info.minInstances;
//			maxInstances = info.maxInstances;
//			ruleName = info.ruleName;
//		}
		JsonObject result = new JsonObject();
		result.add("name", obj.getName());
		result.add("cloud", cloud);
		result.add("initialInstances", 1);
		result.add("scalabilityRuleName", ruleName);
		result.add("minimumInstances", minInstances);
		result.add("maxInstances", maxInstances);
//		result.add("osName", vm.getOs());
		result.add("osVersion", "14.04");
		result.add("architecture", "amd64");
//		result.add("ram", vm.getMinRam());
//		result.add("cores", vm.getMinCores());
		result.add("download", download);
		result.add("install", install);
		result.add("start", start);
		result.add("stop", stop);
		result.add("displayName", obj.getName());
		result.add("cloudifyName", obj.getName());
		return result;
	}

//	private static ScalabilityInfo getScalabilityInfo(
//			InternalComponentInstance obj) {
//
//		Component comp = obj.getType();
//		// Finds the first rule whose horizontal scaling actions reference comp
//		ScalabilityRule arule = null;
//		ScalabilityInfo info = new ScalabilityInfo();
//		CamelModel camel = (CamelModel) targetModel.eContainer();
//		for (ScalabilityModel model : camel.getScalabilityModels()) {
//			for (ScalabilityRule rule : model.getRules()) {
//				for (eu.paasage.camel.Action action : rule.getActions()) {
//					if (action instanceof HorizontalScalingAction) {
//						HorizontalScalingAction hscaction = (HorizontalScalingAction) action;
//						if (comp.equals(hscaction.getInternalComponent())) {
//							arule = rule;
//							break;
//						}
//					}
//				}
//				if (arule != null)
//					break;
//			}
//			if (arule != null)
//				break;
//		}
//		if (arule == null)
//			return null;
//		info.ruleName = arule.getName();
//
//		// Finds the first horizontal scalability policy associated with the
//		// rule
//		for (ScalabilityPolicy policy : arule.getScalabilityPolicies()) {
//			if (policy instanceof HorizontalScalabilityPolicy) {
//				HorizontalScalabilityPolicy hp = (HorizontalScalabilityPolicy) policy;
//				info.maxInstances = hp.getMaxInstances();
//				info.minInstances = hp.getMinInstances();
//				return info;
//			}
//		}
//		return info;
//	}

	private static VMInstance getHostVM(InternalComponentInstance obj) {
		ComponentInstance c = getHost(obj);
		if (c instanceof VMInstance)
			return (VMInstance) c;
		else
			return getHostVM((InternalComponentInstance) c);
	}

	private static ComponentInstance getHost(InternalComponentInstance obj) {

		RequiredHostInstance rhi = obj.getRequiredHostInstance();

		Collection<Setting> references = EcoreUtil.UsageCrossReferencer.find(
				rhi, rhi.eResource().getResourceSet());
		HostingInstance hi = null;
		for (Iterator<Setting> iter = references.iterator(); iter.hasNext();) {
			EStructuralFeature.Setting setting = (EStructuralFeature.Setting) iter
					.next();
			if (setting.getEStructuralFeature() == DeploymentPackage.Literals.HOSTING_INSTANCE__REQUIRED_HOST_INSTANCE) {
				hi = (HostingInstance) setting.getEObject();
				break;
			}
		}
		//Changed this for Camel 2.0.0 - Needs a check
		//return hi.getProvidedHostInstance().getOwner();
		return (ComponentInstance) hi.getProvidedHostInstance().eContainer();//java.lang.NullPointerException -- DEBUG
	}

	private static JsonObject convertCommunicationInstance(
			CommunicationInstance obj) {

		RequiredCommunicationInstance rci = obj
				.getRequiredCommunicationInstance();
		int port = rci.getType().getPortNumber();
		ComponentInstance required = (ComponentInstance) rci.eContainer();
		ProvidedCommunicationInstance pci = obj
				.getProvidedCommunicationInstance();
		ComponentInstance provided = (ComponentInstance) pci.eContainer();
		Communication comm = obj.getType();
		String downloadCommand = null;
		String installCommand = null;
//		ComputationalResource r = comm.getRequiredPortResource();
		Configuration r = comm.getProvidedPortConfiguration();
		if (r != null) {
			downloadCommand = r.getDownloadCommand();
			installCommand = r.getInstallCommand();
		}
		JsonObject result = new JsonObject();
		result.add("provider", provided.getName());
		result.add("consumer", required.getName());
		result.add("downloadCommand", downloadCommand);
		result.add("installCommand", installCommand);
		result.add("port", port);
		return result;
	}

//	private static JsonObject convertAttributes(EObject obj) {
//
//		JsonObject result = new JsonObject();
//
//		Object value = null;
//		EList<EAttribute> eAllAttributes = obj.eClass().getEAllAttributes();
//		for (EAttribute eAttribute : eAllAttributes) {
//			if (obj.eIsSet(eAttribute)) {
//				value = obj.eGet(eAttribute);
//				if (value != null)
//					result.add(eAttribute.getName(), value.toString());
//			}
//		}
//		return result;
//	}

	static class ScalabilityInfo {
		String ruleName;
		int maxInstances;
		int minInstances;
	}

	public ConfigurationTask getTask() {
		// TODO Auto-generated method stub
		return null;
	}

}
