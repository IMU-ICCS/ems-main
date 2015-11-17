/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.utils;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.Hosting;
import eu.paasage.camel.deployment.HostingInstance;
import eu.paasage.camel.deployment.InternalComponent;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.VM;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.PaaSageVariable;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metamodel.cp.VariableValue;
import eu.paasage.upperware.metamodel.types.LongValueUpperware;
import eu.paasage.upperware.solvertodeployment.db.lib.CDODatabaseProxy2;
import eu.paasage.upperware.solvertodeployment.derivator.lib.CloudMLHelper;
import eu.paasage.upperware.solvertodeployment.lib.S2DException;

public class SolverToDeployementHelper {

	public static Logger log= Logger.getLogger(SolverToDeployementHelper.class);

	/**
	 * <pre>
	 * 
DE_GWDG_CoreIntensive_UbuntuReq__CoreIntensiveUbuntuGermanyVM_PROFILE
		ExperimentManager
		GWDG-DE-1441637954624
		VirtualLocation
		U_app_component_ExperimentManager_vm_DE_GWDG_CoreIntensive_UbuntuReq__CoreIntensiveUbuntuGermanyVM_PROFILE_provider_GWDG-DE-1441637954624
DE_GWDG_CoreIntensive_UbuntuReq__CoreIntensiveUbuntuGermanyVM_PROFILE
		InformationService
		GWDG-DE-1441637954624
		VirtualLocation
		U_app_component_InformationService_vm_DE_GWDG_CoreIntensive_UbuntuReq__CoreIntensiveUbuntuGermanyVM_PROFILE_provider_GWDG-DE-1441637954624
DE_GWDG_CPUIntensive_UbuntuReq__CPUIntensiveUbuntuGermanyVM_PROFILE
		SimulationManager
		GWDG-DE-1441637954624
		VirtualLocation
		U_app_component_SimulationManager_vm_DE_GWDG_CPUIntensive_UbuntuReq__CPUIntensiveUbuntuGermanyVM_PROFILE_provider_GWDG-DE-1441637954624
DE_GWDG_StorageIntensive_UbuntuReq__StorageIntensiveUbuntuGermanyVM_PROFILE
		StorageManager
		GWDG-DE-1441637954624
		VirtualLocation
		U_app_component_StorageManager_vm_DE_GWDG_StorageIntensive_UbuntuReq__StorageIntensiveUbuntuGermanyVM_PROFILE_provider_GWDG-DE-1441637954624

	 * </pre>
	 * 
	 * 
	 */

	public static void printVar(PaaSageVariable paaSageVariable) {
		String logg = "\n===================================================\n";
		logg += "\n"+paaSageVariable.getRelatedVirtualMachineProfile().getCloudMLId() ;
		logg += "\n"+paaSageVariable.getRelatedVirtualMachineProfile().getRelatedCloudVMId() ;
		logg += "\n		"+paaSageVariable.getRelatedComponent().getCloudMLId();
		logg += "\n		"+paaSageVariable.getRelatedProvider().getId();
		logg += "\n		"+paaSageVariable.getPaasageType().getName();
		logg += "\n		"+paaSageVariable.getCpVariableId();
		logg += "\n===================================================\n\n";

		log.error(logg);
	}

	public static void printVar(PaasageConfiguration pc)
	{
		EList<PaaSageVariable> paasageVar = pc.getVariables();
		for (PaaSageVariable paaSageVariable : paasageVar) {
			printVar(paaSageVariable);
		}
	}

	public static InternalComponent findInternalComponentFromPaasageConfigurationApplicationComponent(DeploymentModel deploymentModel,String paasageConfigurationApplicationId) throws S2DException
	{
		EList<InternalComponent> components = deploymentModel.getInternalComponents();
		for (InternalComponent internalComponent : components) {
			if(internalComponent.getName().toLowerCase().equals(paasageConfigurationApplicationId.toLowerCase()))
				return internalComponent;
		}
		throw new S2DException("Unable to find "+ paasageConfigurationApplicationId+ " component in camel model");
	}

	public static String providerModelToProviderModelId(String providerModelName)
	{
		String[] results = providerModelName.split("-");
		String result = results[results.length -1 ];
		return result;
	}

	static VM findVM(EList<VM> vms, String vmIdentifier)
	{
		VM result = null;
		String log = "";
		for (VM vm : vms) {

			log += (" \nLooking vm " + vm.getName() + " in camel deployement model " + "compare to" +vm.getName()); 
			//
			//						if(vms.get(i).getName().equals(computedVMProfileName))
			//						{

			if(vm instanceof VM && vm.getName().equals(vmIdentifier))
			{
				log += "\n Find  vm for name" + vmIdentifier;
				result = vm;
				break;
			}
		}
		return result;
	}

	public static InternalComponentInstance createInternalComponentInstanceFromPaasageVariable(PaaSageVariable paaSageVariable,DeploymentModel deploymentModel) throws S2DException
	{
		ApplicationComponent component = paaSageVariable.getRelatedComponent();

		InternalComponent internalComponent = null;
		internalComponent = findInternalComponentFromPaasageConfigurationApplicationComponent(deploymentModel,component.getCloudMLId());
		InternalComponentInstance internalComponentInstance =  CloudMLHelper.createICInstance(internalComponent);
		return internalComponentInstance;

	}

	public static VMInstance searchAndCreateVMInstance(DeploymentModel deploymentModel, PaaSageVariable paaSageVariable, String passageConfigurationID) throws S2DException
	{
		EList<VM> vms = deploymentModel.getVms();

		String vmIdentifier = paaSageVariable.getRelatedVirtualMachineProfile().getRelatedCloudVMId();

		VM result = findVM(vms, vmIdentifier);
		String providerModelId =  paaSageVariable.getRelatedProvider().getId();
		ProviderModel providerModel = null;


		providerModel =  CDODatabaseProxy2.findProviderModel(passageConfigurationID, providerModelId);

		//Create now
		VMInstance vmInstanceResult = CloudMLHelper.createVMInstance(result,providerModel);

		//Set VM Type/value 
		Attribute attribute = CloudMLHelper.findVMType(providerModel);
		vmInstanceResult.setVmType(attribute);
		vmInstanceResult.setVmTypeValue(attribute.getValue());
		return vmInstanceResult;

	}
	static public Hosting findHosting(String applicationComponentName,DeploymentModel _deploymentModel) throws S2DException {
		Hosting hosting = CDODatabaseProxy2.getHostingContainString(_deploymentModel,applicationComponentName);
		if(hosting == null)
		{
			throw new S2DException("Unable to find hosting for application component name :" + applicationComponentName + " . Seems to have error in original model");
		}	
		return hosting;
	}

	static public HostingInstance createHostingInstance(VMInstance vmInstance, InternalComponentInstance internalComponentInstance, DeploymentModel deploymentModel) throws S2DException {
		Hosting hosting = findHosting(internalComponentInstance.getType().getName(),deploymentModel);

		HostingInstance hostingInstance = CloudMLHelper.buildNewHostingInstance(internalComponentInstance.getType().getName(),vmInstance,internalComponentInstance,hosting);

		if(hostingInstance == null)
		{
			throw new S2DException("Unable to find hosting for application component name" + internalComponentInstance.getName());
		}	
		return hostingInstance;
	}
	public static Long findCardinalityOf(PaaSageVariable paaSageVariable, ConstraintProblem _constraintProblem) throws S2DException{		
		for(Solution solution : _constraintProblem.getSolution()){

			EList<VariableValue> variables  = solution.getVariableValue();

			for (VariableValue variableValue : variables) {
				log.debug("Compare " + paaSageVariable.getCpVariableId() + "  with " +  variableValue.getVariable().getId());
				if(paaSageVariable.getCpVariableId().equals(variableValue.getVariable().getId()))
				{
					LongValueUpperware longValueUpperware = (LongValueUpperware) variableValue.getValue();
					log.debug("Find !" + longValueUpperware.getValue());

					return longValueUpperware.getValue();
				}
			}			
		}	
		throw new S2DException("Input error. Solver seems to have done something wrong. Unable to find the cardinality value for Solver constraint " + paaSageVariable.getCpVariableId() );
	}
}
