/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.derivator.lib;

import eu.paasage.camel.deployment.*;
import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.Feature;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.upperware.solvertodeployment.lib.S2DException;
import org.eclipse.emf.common.util.EList;

public class CloudMLHelper {

	private static int _globalCount = 0;
	private static int _newDMIdx = -1;

	private static int getGlobalCount() {
		return _globalCount++;
	}

	public static void resetGlobalCount() {
		_globalCount=0;
	}

	public static void setGlobalDMIdx(int idx) {
		_newDMIdx = idx;
	}

	public static int getGlobalDMIdx() {
		return _newDMIdx;
	}

	private static String getGlobalSuffix() {
		return getGlobalDMIdx() + "_" + getGlobalCount();
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// Internal Component Comnunication
	//////////////////////////////////////////////////////////////////////////////////////

	public static InternalComponent findProvidedComponentFromCommunication(Communication com) {
		return (InternalComponent)(com.getProvidedCommunication().eContainer());
	}

	public static InternalComponent findRequiredComponentFromCommunication(Communication com) {
		return (InternalComponent)(com.getRequiredCommunication().eContainer());
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// Internal Component Instance
	//////////////////////////////////////////////////////////////////////////////////////
	
	public static InternalComponentInstance createICInstance(InternalComponent ic1) {
		// Create Instance + name + type
		InternalComponentInstance internalComponentInstance = DeploymentFactory.eINSTANCE.createInternalComponentInstance();
		internalComponentInstance.setName(ic1.getName() + "Instance_" + getGlobalSuffix());
		internalComponentInstance.setType(ic1);

		//Create ProvidedCommunicationInstance
		for (ProvidedCommunication providedCommunication : ic1.getProvidedCommunications()) {
			ProvidedCommunicationInstance providedCommunicationInstance = DeploymentFactory.eINSTANCE.createProvidedCommunicationInstance();
			providedCommunicationInstance.setType(providedCommunication);
			providedCommunicationInstance.setName(providedCommunication.getName() + "ProvidedCommunicationInstance_" + getGlobalCount());
			internalComponentInstance.getProvidedCommunicationInstances().add(providedCommunicationInstance);
		}

		//Create RequiredCommunicationInstance
		for (RequiredCommunication requiredCommunication : ic1.getRequiredCommunications()) {
			RequiredCommunicationInstance requiredCommunicationInstance = DeploymentFactory.eINSTANCE.createRequiredCommunicationInstance();
			requiredCommunicationInstance.setType(requiredCommunication);
			requiredCommunicationInstance.setName(requiredCommunication.getName() + "ReqCommunicationInstance_" + getGlobalCount());
			internalComponentInstance.getRequiredCommunicationInstances().add(requiredCommunicationInstance);
		}

		//Create RequiredHostInstance
		RequiredHostInstance requiredHostInstance = DeploymentFactory.eINSTANCE.createRequiredHostInstance();
		requiredHostInstance.setType(ic1.getRequiredHost());
		requiredHostInstance.setName(ic1.getName() + "RequiredHostInstance_" + getGlobalCount());
		internalComponentInstance.setRequiredHostInstance(requiredHostInstance);

		return internalComponentInstance;
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// VM Instance
	//////////////////////////////////////////////////////////////////////////////////////

	public static VMInstance createVMInstance(VM vm) {
		// Create VMi
		VMInstance vmInstance = DeploymentFactory.eINSTANCE.createVMInstance();
		vmInstance.setType(vm);
		vmInstance.setName(vm.getName() + "VMInstance_" + getGlobalSuffix());

		// Create ProviderHostInstance
		for (ProvidedHost providedHost : vm.getProvidedHosts()) {
			ProvidedHostInstance providedHostInstance = DeploymentFactory.eINSTANCE.createProvidedHostInstance();
			providedHostInstance.setName(providedHost.getName() +"ProvidedHostInstance_" + getGlobalCount());
			providedHostInstance.setType(providedHost);
			vmInstance.getProvidedHostInstances().add(providedHostInstance);
		}

		return vmInstance;
	}

	public static Attribute findVMType(ProviderModel providerModel) throws S2DException {
		Attribute result = null;
		if(providerModel == null ) {
			throw new S2DException("Bad calling . Provider musn't not be null");
		}
		EList<Feature> subFeatures = providerModel.getRootFeature().getSubFeatures();
		StringBuilder logTxt = new StringBuilder("\n * Start looking vmType for providerModel " + providerModel.getName());
		logTxt.append(" \n  ** Scan SubFeature of provider model. Scanning ").append(subFeatures.size()).append(" elements");
		for (Feature feature : subFeatures) {
			EList<Attribute> attributes = feature.getAttributes();
			logTxt.append(" ** Will scanning all attributes for feature ").append(feature.getName()).append(". Number of attributes : ").append(attributes.size());

			for (Attribute attribute : attributes) {	
				logTxt.append("\n    *** Is attribute name equals vmType ? : ").append(attribute.getName()).append(" bla ").append(attribute.getValue());

				if("VMType".equals(attribute.getName())) {
					result = attribute;
				}
			}
		}
		if(result == null) 
			throw new S2DException("Unable to find VMType . There is error in original model ! .Details :" + logTxt);
		return result;
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// Hosting Instance
	//////////////////////////////////////////////////////////////////////////////////////

	public static HostingInstance buildNewHostingInstance(String acName,VMInstance vmInstance, InternalComponentInstance internalComponentInstance, Hosting hosting) {
		// CreateHostingInstance
		HostingInstance hostingInstance = DeploymentFactory.eINSTANCE.createHostingInstance();
		hostingInstance.setName("VMto" + acName + "HostingInstance_" + getGlobalSuffix());
		hostingInstance.setProvidedHostInstance(vmInstance.getProvidedHostInstances().get(0));
		hostingInstance.setRequiredHostInstance(internalComponentInstance.getRequiredHostInstance());
		hostingInstance.setType(hosting);
		return hostingInstance;
	}
}
