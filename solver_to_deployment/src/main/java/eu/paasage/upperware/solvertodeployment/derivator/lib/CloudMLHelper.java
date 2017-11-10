/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.derivator.lib;

import eu.paasage.camel.type.EnumerateValue;
import eu.paasage.camel.type.Enumeration;
import eu.paasage.camel.type.TypeModel;
import eu.paasage.camel.type.ValueType;
import org.eclipse.emf.common.util.EList;

import eu.paasage.camel.deployment.Communication;
import eu.paasage.camel.deployment.DeploymentFactory;
import eu.paasage.camel.deployment.Hosting;
import eu.paasage.camel.deployment.HostingInstance;
import eu.paasage.camel.deployment.InternalComponent;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.ProvidedCommunication;
import eu.paasage.camel.deployment.ProvidedCommunicationInstance;
import eu.paasage.camel.deployment.ProvidedHost;
import eu.paasage.camel.deployment.ProvidedHostInstance;
import eu.paasage.camel.deployment.RequiredCommunication;
import eu.paasage.camel.deployment.RequiredCommunicationInstance;
import eu.paasage.camel.deployment.RequiredHostInstance;
import eu.paasage.camel.deployment.VM;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.Feature;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.upperware.solvertodeployment.lib.S2DException;

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
		for(int i=0 ;i <ic1.getProvidedCommunications().size();i++) {
			ProvidedCommunicationInstance providedCommunicationInstance = DeploymentFactory.eINSTANCE.createProvidedCommunicationInstance();
			ProvidedCommunication providedCommunication = ic1.getProvidedCommunications().get(i);
			providedCommunicationInstance.setType(providedCommunication);
			providedCommunicationInstance.setName(providedCommunication.getName() + "ProvidedCommunicationInstance_" + getGlobalCount());
			internalComponentInstance.getProvidedCommunicationInstances().add(providedCommunicationInstance);
		}

		//Create RequiredCommunicationInstance
		for(int i=0 ;i <ic1.getRequiredCommunications().size();i++) {
			RequiredCommunicationInstance requiredCommunicationInstance = DeploymentFactory.eINSTANCE.createRequiredCommunicationInstance(); 
			RequiredCommunication requiredCommunication = ic1.getRequiredCommunications().get(i);
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
		for(int i=0 ;i < vm.getProvidedHosts().size();i++) {
			ProvidedHostInstance providedHostInstance = DeploymentFactory.eINSTANCE.createProvidedHostInstance();
			ProvidedHost providedHost = vm.getProvidedHosts().get(i);
			providedHostInstance.setName(providedHost.getName() +"ProvidedHostInstance_" + getGlobalCount());
			providedHostInstance.setType(providedHost);
			vmInstance.getProvidedHostInstances().add(providedHostInstance);
		}
		return vmInstance;
	}

	public static Attribute findVMType(ProviderModel _providerModel) throws S2DException {
		Attribute result = null;
		if(_providerModel == null ) {
			throw new S2DException("Bad calling . Provider musn't not be null");
		}
		EList<Feature> subFeatures = _providerModel.getRootFeature().getSubFeatures(); 
		String logTxt = "\n * Start looking vmType for providerModel " + _providerModel.getName();
		logTxt+=(" \n  ** Scan SubFeature of provider model. Scanning "+ subFeatures.size() +" elements");
		for (Feature feature : subFeatures) {
			EList<Attribute> attributes = feature.getAttributes();
			logTxt+=(" ** Will scanning all attributes for feature " + feature.getName()+ ". Number of attributes : " + attributes.size());

			for (Attribute attribute : attributes) {	
				logTxt+=("\n    *** Is attribute name equals vmType ? : " + attribute.getName() + " bla " + attribute.getValue());

				if(attribute.getName().equals("VMType")) {
					result = attribute;
				}
			}
		}
		if(result == null) 
			throw new S2DException("Unable to find VMType . There is error in original model ! .Details :" + logTxt);
		return result;
	}

	//TODO - uwspolnic.
	public static EnumerateValue findValueForFlavour(String flavourName, EList<TypeModel> typeModels){
		for (TypeModel typeModel : typeModels) {
			EList<ValueType> dataTypes = typeModel.getDataTypes();
			for (ValueType dataType : dataTypes) {
				if ("VMTypeEnum".equals(dataType.getName()) && dataType instanceof Enumeration){
					Enumeration enumerationDataType = (Enumeration) dataType;
					for (EnumerateValue enumerateValue : enumerationDataType.getValues()) {
						if (flavourName.equals(enumerateValue.getName())){
							return enumerateValue;
						}
					}
				}
			}
		}
		return null;
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// Hosting Instance
	//////////////////////////////////////////////////////////////////////////////////////

	public static HostingInstance buildNewHostingInstance(String acName,VMInstance vmInstance, InternalComponentInstance internalComponentInstance, Hosting hosting) {
		// CreateHostingInstance
		HostingInstance hostingInstance = DeploymentFactory.eINSTANCE.createHostingInstance();
		hostingInstance.setName("VMto" + acName + "HostingInstance_" + getGlobalSuffix());
		
		EList<ProvidedHostInstance> pis = vmInstance.getProvidedHostInstances();
		hostingInstance.setProvidedHostInstance(pis.get(0));
		hostingInstance.setRequiredHostInstance(internalComponentInstance.getRequiredHostInstance());
		hostingInstance.setType(hosting);
		return hostingInstance;
	}
}
