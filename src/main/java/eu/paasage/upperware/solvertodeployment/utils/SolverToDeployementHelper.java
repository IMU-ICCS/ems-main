/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.utils;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import eu.paasage.camel.CamelModel;
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
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;
import eu.paasage.upperware.metamodel.types.LongValueUpperware;
import eu.paasage.upperware.metamodel.types.NumericValueUpperware;
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
		String logg = "Listing Variable:\n===================================================\n";
		logg += "\n"+paaSageVariable.getRelatedVirtualMachineProfile().getCloudMLId() ;
		logg += "\n"+paaSageVariable.getRelatedVirtualMachineProfile().getRelatedCloudVMId() ;
		logg += "\n		"+paaSageVariable.getRelatedComponent().getCloudMLId();
		logg += "\n		"+paaSageVariable.getRelatedProvider().getId();
		logg += "\n		"+paaSageVariable.getPaasageType().getName();
		logg += "\n		"+paaSageVariable.getCpVariableId();
		logg += "\n===================================================\n\n";

		log.info(logg);
	}

	public static void printVar(PaasageConfiguration pc)
	{
		EList<PaaSageVariable> paasageVar = pc.getVariables();
		for (PaaSageVariable paaSageVariable : paasageVar) {
			printVar(paaSageVariable);
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	// Internal Component Instance
	//////////////////////////////////////////////////////////////////////////////////////

	public static InternalComponent findInternalComponentFromPaasageConfigurationApplicationComponent(DeploymentModel deploymentModel,
			String paasageConfigurationApplicationId) throws S2DException
	{
		EList<InternalComponent> components = deploymentModel.getInternalComponents();
		for (InternalComponent internalComponent : components)
		{
			if(internalComponent.getName().toLowerCase().equals(paasageConfigurationApplicationId.toLowerCase()))
				return internalComponent;
		}
		throw new S2DException("Unable to find "+ paasageConfigurationApplicationId+ " component in camel model");
	}

	public static EList<InternalComponentInstance> createInternalComponentInstanceFromPaasageVariable(PaaSageVariable paaSageVariable,DeploymentModel deploymentModel, Long nb) throws S2DException
	{
		ApplicationComponent component = paaSageVariable.getRelatedComponent();

		InternalComponent internalComponent = null;
		internalComponent = findInternalComponentFromPaasageConfigurationApplicationComponent(deploymentModel,component.getCloudMLId());
		EList<InternalComponentInstance> internalCIs = new BasicEList<InternalComponentInstance>();
		for(int i=0; i<nb; i++)
		{
			internalCIs.add(CloudMLHelper.createICInstance(internalComponent));			
		}
		return internalCIs;
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// Hosting Instance
	//////////////////////////////////////////////////////////////////////////////////////

	static public Hosting findHosting(InternalComponent component,DeploymentModel _deploymentModel) throws S2DException
	{
//		log.info("=> Looking for an hosting for component: "+component.getName()+ " of name "+component.getRequiredHost().getName());
		for(Hosting h : _deploymentModel.getHostings())
		{
//			log.info("  ? "+h.getProvidedHost().getName());
			if (h.getRequiredHost().getName().equals(component.getRequiredHost().getName()))
			{
//				log.info("  returns  "+h.getProvidedHost().getName());
				return h;
			}
		}
		throw new S2DException("Unable to find hosting for application component name :" + component.getName() + " . Seems to have error in original model");

	}

	static public HostingInstance createHostingInstance(VMInstance vmInstance, InternalComponentInstance internalComponentInstance, DeploymentModel deploymentModel) throws S2DException 
	{
		InternalComponent internalComponent = (InternalComponent) internalComponentInstance.getType();
		Hosting hosting = findHosting(internalComponent, deploymentModel);

		HostingInstance hostingInstance = CloudMLHelper.buildNewHostingInstance(internalComponentInstance.getType().getName(),vmInstance,internalComponentInstance,hosting);

		if(hostingInstance == null)
		{
			throw new S2DException("Unable to find hosting for application component name" + internalComponentInstance.getName());
		}	
		return hostingInstance;
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// VM Instance
	//////////////////////////////////////////////////////////////////////////////////////

	static VM findVM(EList<VM> vms, String vmIdentifier)
	{
		VM result = null;
		@SuppressWarnings("unused")
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


	public static EList<VMInstance> searchAndCreateVMInstance(DeploymentModel deploymentModel, PaaSageVariable paaSageVariable, String passageConfigurationID, Long nb) throws S2DException
	{
		EList<VM> vms = deploymentModel.getVms();

		String vmIdentifier = paaSageVariable.getRelatedVirtualMachineProfile().getRelatedCloudVMId();

		VM result = findVM(vms, vmIdentifier);
		String providerModelId =  paaSageVariable.getRelatedProvider().getId()+"#"+vmIdentifier;

		ProviderModel providerModel = null;
		CamelModel cm = (CamelModel) deploymentModel.eContainer();
//		log.info("Looking for PM id:"+providerModelId);
		for(ProviderModel p : cm.getProviderModels())
		{
//			log.info("Testing for PM id:"+p.getName());
			if (p.getName().equals(providerModelId)) {
				providerModel = p;
				break;
			}
		}
		if (providerModel==null)
		{
			log.fatal("Oops: no Provider model found:");
			throw new S2DException("Provider not found: "+providerModelId);
		}
		log.debug("Creating VM instances providerModel = "+providerModel.getName());
		//Create now
		EList<VMInstance> vmInstances = new BasicEList<VMInstance>();
		for(int i=0; i<nb; i++)
		{
			VMInstance vmInstanceResult = CloudMLHelper.createVMInstance(result, providerModel);
			//Set VM Type/value 
			Attribute attribute = CloudMLHelper.findVMType(providerModel);
			vmInstanceResult.setVmType(attribute);
			vmInstanceResult.setVmTypeValue(attribute.getValue());
			vmInstances.add(vmInstanceResult);
		}
		return vmInstances;

	}

	//////////////////////////////////////////////////////////////////////////////////////
	// Helper
	//////////////////////////////////////////////////////////////////////////////////////

	public static String providerModelToProviderModelId(String providerModelName)
	{
		String[] results = providerModelName.split("-");
		String result = results[0];
		return result;
	}

	
	public static Long findCardinalityOf(PaaSageVariable paaSageVariable, ConstraintProblem _constraintProblem, int solutionId) throws S2DException
	{		
		Solution solution = _constraintProblem.getSolution().get(solutionId);

			EList<VariableValue> variables  = solution.getVariableValue();

			for (VariableValue variableValue : variables) {
				log.debug("Compare " + paaSageVariable.getCpVariableId() + "  with " +  variableValue.getVariable().getId());
				if(paaSageVariable.getCpVariableId().equals(variableValue.getVariable().getId()))
				{
					NumericValueUpperware value = variableValue.getValue();
					Long result;
					if (value instanceof LongValueUpperware) {
						LongValueUpperware longValueUpperware = (LongValueUpperware) value;
						result = longValueUpperware.getValue();
					} else if (value instanceof IntegerValueUpperware) {
						IntegerValueUpperware intValueUpperware = (IntegerValueUpperware) value;
						result = (long) intValueUpperware.getValue();
					}
					else {
						String msg ="Did not support type for: "+value;
						throw new S2DException(msg);
					}
					log.debug("Find !" + result);

					return result;
				}
			}			
		throw new S2DException("Input error. Solver seems to have done something wrong. Unable to find the cardinality value for Solver constraint " + paaSageVariable.getCpVariableId() );
	}
	
}
