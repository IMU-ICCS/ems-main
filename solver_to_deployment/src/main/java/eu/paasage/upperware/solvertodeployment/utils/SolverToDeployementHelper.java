/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.utils;

import eu.paasage.camel.impl.CamelModelImpl;
import eu.paasage.camel.type.EnumerateValue;
import eu.paasage.camel.type.TypeModel;
import eu.paasage.upperware.metamodel.cp.Variable;
import lombok.extern.slf4j.Slf4j;
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
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metamodel.cp.VariableValue;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;
import eu.paasage.upperware.metamodel.types.LongValueUpperware;
import eu.paasage.upperware.metamodel.types.NumericValueUpperware;
import eu.paasage.upperware.solvertodeployment.derivator.lib.CloudMLHelper;
import eu.paasage.upperware.solvertodeployment.lib.S2DException;

@Slf4j
public class SolverToDeployementHelper {

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

	//////////////////////////////////////////////////////////////////////////////////////
	// Internal Component Instance
	//////////////////////////////////////////////////////////////////////////////////////

	public static EList<InternalComponentInstance> createInternalComponentInstanceFromPaasageVariable(PaaSageVariable paaSageVariable,
																									  DeploymentModel deploymentModel, Long nb) throws S2DException {
		ApplicationComponent component = paaSageVariable.getRelatedComponent();

		InternalComponent internalComponent = findInternalComponentFromPaasageConfigurationApplicationComponent(deploymentModel,component.getCloudMLId());
		EList<InternalComponentInstance> internalCIs = new BasicEList<>();
		for(int i=0; i<nb; i++) {
			internalCIs.add(CloudMLHelper.createICInstance(internalComponent));			
		}
		return internalCIs;
	}

	public static InternalComponent findInternalComponentFromPaasageConfigurationApplicationComponent(DeploymentModel deploymentModel,
																									  String paasageConfigurationApplicationId) throws S2DException {
		return deploymentModel.getInternalComponents().stream()
				.filter(internalComponent -> internalComponent.getName().equalsIgnoreCase(paasageConfigurationApplicationId))
				.findFirst()
				.orElseThrow(() -> new S2DException("Unable to find "+ paasageConfigurationApplicationId+ " component in camel model"));
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// Hosting Instance
	//////////////////////////////////////////////////////////////////////////////////////

	static public Hosting findHosting(InternalComponent component,DeploymentModel deploymentModel) throws S2DException {
		return deploymentModel.getHostings()
				.stream()
				.filter(h -> h.getRequiredHost().getName().equals(component.getRequiredHost().getName()))
				.findFirst()
				.orElseThrow(() -> new S2DException("Unable to find hosting for application component name :" + component.getName() + " . Seems to have error in original model"));
	}

	static public HostingInstance createHostingInstance(VMInstance vmInstance, InternalComponentInstance internalComponentInstance, DeploymentModel deploymentModel) throws S2DException {
		InternalComponent internalComponent = (InternalComponent) internalComponentInstance.getType();
		Hosting hosting = findHosting(internalComponent, deploymentModel);

		HostingInstance hostingInstance = CloudMLHelper.buildNewHostingInstance(internalComponentInstance.getType().getName(), vmInstance, internalComponentInstance, hosting);
		if(hostingInstance == null) {
			throw new S2DException("Unable to find hosting for application component name" + internalComponentInstance.getName());
		}	
		return hostingInstance;
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// VM Instance
	//////////////////////////////////////////////////////////////////////////////////////

	static VM findVMByName(EList<VM> vms, String vmIdentifier) {
		return vms.stream()
				.filter(vm -> vm.getName().equals(vmIdentifier))
				.findFirst().orElse(null);
	}


	public static EList<VMInstance> searchAndCreateVMInstance(DeploymentModel deploymentModel, PaaSageVariable paaSageVariable, Long nb, ConstraintProblem constraintProblem) throws S2DException {
		String vmIdentifier = paaSageVariable.getRelatedVirtualMachineProfile().getRelatedCloudVMId();

		VM result = findVMByName(deploymentModel.getVms(), vmIdentifier);
		// Why do we need '#'+vmIdentifier (cf CDODatabaseProxy2)
		String providerModelId =  paaSageVariable.getRelatedProvider().getId()+"#"+vmIdentifier;

		CamelModel cm = (CamelModel) deploymentModel.eContainer();
		ProviderModel providerModel = cm.getProviderModels().stream()
				.filter(p -> p.getName().equals(providerModelId))
				.findFirst()
				.orElseThrow(() -> new S2DException("Provider not found: " + providerModelId));

		log.debug("Creating VM instances providerModel = {}", providerModel.getName());

		Variable cpVariable = constraintProblem.getVariables().stream()
				.filter(variable -> variable.getId().equals(paaSageVariable.getCpVariableId()))
				.findFirst().orElseThrow(() -> new S2DException("Could not find variable with id: " + paaSageVariable.getCpVariableId()));

		String flavourName = cpVariable.getFlavourName();
		EList<TypeModel> typeModels = ((CamelModelImpl) deploymentModel.eContainer()).getTypeModels();
		EnumerateValue valueForFlavour = CloudMLHelper.findValueForFlavour(flavourName, typeModels);



		//Create now
		EList<VMInstance> vmInstances = new BasicEList<>();
		for(int i=0; i<nb; i++) {
			VMInstance vmInstanceResult = CloudMLHelper.createVMInstance(result);
			//Set VM Type/value 
			Attribute attribute = CloudMLHelper.findVMType(providerModel);
			vmInstanceResult.setVmType(attribute);
			vmInstanceResult.setVmTypeValue(valueForFlavour);
			vmInstances.add(vmInstanceResult);
		}
		return vmInstances;

	}

	//////////////////////////////////////////////////////////////////////////////////////
	// Helper
	//////////////////////////////////////////////////////////////////////////////////////

	protected static Long findCardinalityOf(PaaSageVariable paaSageVariable, ConstraintProblem constraintProblem, int solutionId) throws S2DException {
		Solution solution = constraintProblem.getSolution().get(solutionId);

		EList<VariableValue> variables = solution.getVariableValue();

		for (VariableValue variableValue : variables) {
			log.debug("Compare {} with {}", paaSageVariable.getCpVariableId(), variableValue.getVariable().getId());
			if (paaSageVariable.getCpVariableId().equals(variableValue.getVariable().getId())) {
				NumericValueUpperware value = variableValue.getValue();
				Long result;
				if (value instanceof LongValueUpperware) {
					result = ((LongValueUpperware) value).getValue();
				} else if (value instanceof IntegerValueUpperware) {
					result = (long) ((IntegerValueUpperware) value).getValue();
				} else {
					throw new S2DException("Did not support type for: " + value);
				}
				log.debug("Find ! {}", result);
				return result;
			}
		}
		throw new S2DException("Input error. Solver seems to have done something wrong. Unable to find the cardinality value for Solver constraint " + paaSageVariable.getCpVariableId());
	}
	
}
