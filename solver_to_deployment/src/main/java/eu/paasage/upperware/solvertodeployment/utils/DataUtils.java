/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.utils;

import com.google.common.collect.Sets;
import eu.melodic.cache.NodeCandidatePredicates;
import eu.melodic.cache.NodeCandidates;
import eu.paasage.camel.deployment.*;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.type.StringsValue;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metamodel.cp.VariableValue;
import eu.paasage.upperware.solvertodeployment.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.solvertodeployment.db.lib.CDODatabaseProxy2;
import eu.paasage.upperware.solvertodeployment.derivator.lib.CloudMLHelper;
import eu.paasage.upperware.solvertodeployment.lib.CommunicationProvidedRequiredDomain;
import eu.paasage.upperware.solvertodeployment.lib.S2DException;
import eu.paasage.upperware.solvertodeployment.properties.SolverToDeploymentProperties;
import eu.passage.upperware.commons.model.tools.CPModelTool;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.common.util.EList;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class DataUtils {

	/*<pre>

The Solver-to-deployement component is implemented in Java. It receives the solution as a list of objects with the PaaSageVariable type.
APaaSageVariable object is described as follows:
- RelatedComponent:  It gives the ApplicationComponent of the UpperModel to instanciate in the CAMEL model.
- RelatedVirtualMachineProfile: It enables determining the VM template to instanciate in the CAMEL model.
- RelatedProvider: It gives the Provider from the UpperwareModel that allows finding in which provider the VM must be instanciated.

The creation process comprises the following steps.

1. Creation of the InternalComponentInstances.

A PaaSageVariable’s relatedComponent is used to create one corresponding InternalComponentInstance. To do so, we need to find in the CAMEL
model the associated InternalComponent. This InternalComponent is associated to a list of ProvidedCommunications and a list of requiredCom-
munications. For each item of these lists, we must create a corresponding instance, either ProvidedCommunicationInstance or RequiredCommuni-
cationInstance (depending on the original type). The two resulting lists can then be associated to the InternalComponentInstance.


2. Creation of the VmInstance.

D3.1.2 - Product Upperware Report -- Page 74 of 98
PaaSageVariable’s RelatedVirtualMachineProfile and RelatedProvider are used to create a vmInstance. Those two values allow finding the VM and
the ProviderModel. From the VM, we create a ProvidedHostInstances that gets associated to the VmInstance. The ProviderModel is used to find the
VMType and VMTypeValue of the VmInstance.


3. Creation of the HostingInstances.

The HostingInstances are created using the previously-created VmInstance and InternalComponentInstances, as well as the InternalComponent
associated to the ComponentInstance.
A HostingInstance must be created for each ProvidedHostInstance associated to the VmInstance. Each HostingInstance must be associated to theA
current ProvidedHostInstance and to the RequiredHostInstance matching the InternalComponentInstance.

4. Create CommunicationInstances

 </pre>
	 */
	public static DataHolder computeDatasToRegister(DeploymentModel deploymentModel,
													ConstraintProblem constraintProblem, Solution solution, String camelModelID,
													NodeCandidates nodeCandidates, SolverToDeploymentProperties solverToDeploymentProperties) throws S2DException {

		// Analyzing the model for LOCAL group, ie component connected by LOCAL communication
		// component i => i
		Map<String, Integer> localComponentGroups = new HashMap<>();
		// i => { components }
		Map<Integer, Set<String>> localGroups= new HashMap<>();
		// Init

		EList<InternalComponent> internalComponents = deploymentModel.getInternalComponents();
		for (int i = 0; i < internalComponents.size(); i++) {
			String componentName = internalComponents.get(i).getName();
			localComponentGroups.put(componentName, i);
			localGroups.put(i, Sets.newHashSet(componentName));
		}

		localComponentGroups.forEach((name, index) -> log.info("componentGroups: <{}, {}>", name, index));
		localGroups.forEach((index, set) ->log.info("groups: <{} {} >", index, set));

		// Merging sets
		for (Communication communication : deploymentModel.getCommunications()) {
			if (communication.getType() == CommunicationType.LOCAL) {
				String provName = CloudMLHelper.findProvidedComponentFromCommunication(communication).getName();
				int provId = localComponentGroups.get(provName);
				String reqName = CloudMLHelper.findRequiredComponentFromCommunication(communication).getName();
				int reqId = localComponentGroups.get(reqName);
				if (provId == reqId) continue; // already merge
				if (provId < reqId) {
					for(Entry<String, Integer> entry : localComponentGroups.entrySet()) {
						if (entry.getValue() == reqId)
							entry.setValue(provId);
					}
					// merge all elements of reqId into provId
					localGroups.get(provId).addAll(localGroups.get(reqId));
					// reqId & provId use the same set
					localGroups.put(reqId, localGroups.get(provId));
				} else {
					for(Entry<String, Integer> entry : localComponentGroups.entrySet()) {
						if (entry.getValue() == provId)
							entry.setValue(reqId);
					}
					// merge all elements of reqId into provId
					localGroups.get(provId).addAll(localGroups.get(reqId));
					// reqId & provId use the same set
					localGroups.put(reqId, localGroups.get(provId));
				}
			}
		}

		// Preparing VMInstance memory
		int key=0;
		for (InternalComponent ic : internalComponents) {
			if (localComponentGroups.get(ic.getName())==key) {
				String msg = localGroups.get(key).stream().collect(Collectors.joining(" "));
				log.info("Group {}: {}", key, msg);
			}
			key++;
		}
		
		// Memory of instances
		Map<Integer, EList<VMInstance>> localGroupVMInstances = new HashMap<>();
		Map<String, List<VariableValue>> vvByComponentName = CPModelTool.groupVariableValuesByAppName(solution.getVariableValue());

		try {
			DataHolder dataHolder = new DataHolder();
			for (Entry<String, List<VariableValue>> entry : vvByComponentName.entrySet()) {
				String componentName = entry.getKey();

				int cardinality = CPModelTool.getIntValue(CPModelTool.getCardinality(entry.getValue()).orElseThrow(() -> new S2DException(String.format("Could not find cardinality for component %s", entry.getKey()))));
				if (cardinality > 0) {
					int providerId = CPModelTool.getIntValue(CPModelTool.getProviderId(entry.getValue())
							.orElseThrow(() -> new S2DException(String.format("Could not find provider for component %s", entry.getKey()))));

					Predicate<NodeCandidate>[] nodeCandidatePredicates = getNodeCandidatePredicates(entry.getValue());

					log.info(Arrays.stream(nodeCandidatePredicates)
							.map(Object::toString)
							.collect(Collectors.joining(",", "Filtering node candidates by component " + componentName + ", provider with id: " + providerId +  " and " + nodeCandidatePredicates.length + " predicates [", "]")));

					NodeCandidate nodeCandidate = nodeCandidates.getCheapest(componentName, providerId, nodeCandidatePredicates)
							.orElseThrow(() -> new S2DException(String.format("Could not find cheapest nodeCandidate for component %s, provider with index %d and %d predicates", componentName, providerId, nodeCandidatePredicates.length)));

					log.info("Found Node Candidate: {}", nodeCandidate);

					try {
						EList<InternalComponentInstance> internalComponentInstanceToRegisters = SolverToDeployementHelper.createInternalComponentInstance(componentName, deploymentModel, cardinality);
						dataHolder.getComponentInstancesToRegister().addAll(internalComponentInstanceToRegisters);

						// Create VM Instance or not (if LOCAL communication)
						int myKey = localComponentGroups.get(componentName);
						EList<VMInstance> vmInstanceToRegisters = localGroupVMInstances.get(myKey);
						log.info("VMs for key {}: {}", myKey, vmInstanceToRegisters);

						if (vmInstanceToRegisters == null) {
							log.info("Creating new VM Instances...");

							VM vm = findVMByName(deploymentModel.getVms(), getVmId(entry));

							ProviderModel providerModel = ProviderModelTransformer.createProviderModel(nodeCandidate, componentName, constraintProblem.getId(), solverToDeploymentProperties.getEndpoint().getAmazon());
							dataHolder.getProviderModel().add(providerModel);

							vmInstanceToRegisters = SolverToDeployementHelper.searchAndCreateVMInstance(providerModel, vm, cardinality);
							dataHolder.getVmInstancesToRegister().addAll(vmInstanceToRegisters);
							// memorize
							localGroupVMInstances.put(myKey,  vmInstanceToRegisters);
							log.info("**NEW** VMs for key {}", myKey);
						}

						// Create Hosting
						for(int i=0; i<cardinality; i++) {
							InternalComponentInstance iCI = internalComponentInstanceToRegisters.get(i);
							VMInstance vmI = vmInstanceToRegisters.get(i);
							dataHolder.getHostingInstancesToRegister().add(SolverToDeployementHelper.createHostingInstance(vmI, iCI, deploymentModel));
						}

					} catch(S2DException e) {
						throw e;
					}
				}
			}

			log.debug("2. Dealing with Communication Instances");
			for (Communication communication : deploymentModel.getCommunications()) {
				log.info("2a Dealing with communication: {} type: {}", communication.getName(), communication.getType());
				EList<CommunicationInstance> communicationInstances = CommunicationProvidedRequiredDomain.createCommunicationInstanceFromDemand(communication, deploymentModel, dataHolder.getComponentInstancesToRegister());
				dataHolder.getCommunicationInstances().addAll(communicationInstances);
			}
			log.debug("3. Changing names.");
			changeNames(dataHolder, camelModelID);
			log.debug("4. Done.");
			return dataHolder;

		} catch (Exception e) {
			log.error("Problem with S2D: ", e);
		}
		return null;
	}

	private static String getVmId(Entry<String, List<VariableValue>> entry) {
		return entry.getValue().get(0).getVariable().getVmId();
	}

	private static VM findVMByName(EList<VM> vms, String vmName) {
		return vms.stream()
				.filter(vm -> vm.getName().equals(vmName))
				.findFirst().orElse(null);
	}

	private static Predicate<NodeCandidate>[] getNodeCandidatePredicates(List<VariableValue> variableValues) {
		List<Predicate<NodeCandidate>> result = new ArrayList<>();

		CPModelTool.getCores(variableValues)
				.ifPresent(variableValue -> result.add(NodeCandidatePredicates.getCoresPredicate(CPModelTool.getIntValue(variableValue))));

		CPModelTool.getRam(variableValues)
				.ifPresent(variableValue -> result.add(NodeCandidatePredicates.getRamPredicate(CPModelTool.getLongValue(variableValue))));

		CPModelTool.getStorage(variableValues)
				.ifPresent(variableValue -> result.add(NodeCandidatePredicates.getStoragePredicate(CPModelTool.getDoubleValue(variableValue))));

		CPModelTool.getOs(variableValues)
				.ifPresent(variableValue -> result.add(NodeCandidatePredicates.getOsPredicate(CPModelTool.getIntValue(variableValue))));

		return result.toArray(new Predicate[result.size()]);
	}

	private static void changeNames(DataHolder result, String camelModelID) {
		CDOTransaction transaction = CDODatabaseProxy.getInstance().getCdoClient().openTransaction();
		try {
			CDODatabaseProxy2.getLastDeployedModel(camelModelID, transaction).ifPresent(deployedModel -> {
				//1. VMInstances
				changeNames(result.getVmInstancesToRegister(), deployedModel.getVmInstances(), VMKey::getInstance);

				//2. Component
				changeNames(result.getComponentInstancesToRegister(), deployedModel.getInternalComponentInstances(), VMKey::getInstance);
					});
		} finally {
			if (transaction != null && !transaction.isClosed()) {
				CDODatabaseProxy.getInstance().getCdoClient().closeTransaction(transaction);
			}
		}
	}

	private static <T extends DeploymentElement> void changeNames(List<T> newInstances, List<T> oldInstances, Function<T, VMKey> function) {
		Map<VMKey, List<T>> newVmTemporaryMap = createInstanceMap(newInstances, function);
		Map<VMKey, List<T>> deployedInstances = createInstanceMap(oldInstances, function);
		for (VMKey vmKey : deployedInstances.keySet()) {
            List<T> oldVmInstances = deployedInstances.get(vmKey);
            List<T> newVmInstances = newVmTemporaryMap.getOrDefault(vmKey, Collections.emptyList());

            for (int i=0; i< oldVmInstances.size(); i++) {
                if (newVmInstances.size()>i) {
                    T newVmInstance = newVmInstances.get(i);
                    newVmInstance.setName(oldVmInstances.get(i).getName());
                }
            }
        }
	}

	private static <T extends DeploymentElement> Map<VMKey, List<T>> createInstanceMap(List<T> vmInstancesToRegister, Function<T, VMKey> function) {
		Map<VMKey, List<T>> result = new HashMap<>();

		for (T instance : vmInstancesToRegister) {
			VMKey vmKey = function.apply(instance);

			if (!result.containsKey(vmKey)){
			    result.put(vmKey, new ArrayList<>());
            }
            result.get(vmKey).add(instance);
		}
		return result;
	}

	public static void registerDataHolderToCDO(String camelModelID, DataHolder dataholder) {

		for (ProviderModel providerModel: dataholder.getProviderModel()) {
			CDODatabaseProxy2.registerProviderModel(providerModel,camelModelID, dataholder.getDmId());
		}

		for (VMInstance vmInstance : dataholder.getVmInstancesToRegister()) {
			CDODatabaseProxy2.registerVMInstance(vmInstance,camelModelID, dataholder.getDmId());
		}

		for (InternalComponentInstance internalComponentInstance : dataholder.getComponentInstancesToRegister()) {
			CDODatabaseProxy2.registerInternalComponentInstance(internalComponentInstance ,camelModelID, dataholder.getDmId());
		}

		for (HostingInstance hostingInstance : dataholder.getHostingInstancesToRegister()) {
			CDODatabaseProxy2.registerHostingInstance(hostingInstance ,camelModelID, dataholder.getDmId());
		}

		for (CommunicationInstance communicationInstance : dataholder.getCommunicationInstances()) {
			CDODatabaseProxy2.registerCommunicationInstance(communicationInstance,camelModelID, dataholder.getDmId());
		}
	}

	@Getter
	@AllArgsConstructor
	static class VMKey {

		private String name;
		private String type;

		private static VMKey getInstance(VMInstance vmInstance) {
			String vmName = removeSuffixFromInstance(vmInstance.getName());
			String vmType = ((StringsValue)vmInstance.getVmTypeValue()).getValue();
			return new VMKey(vmName, vmType);
		}

		private static VMKey getInstance(InternalComponentInstance internalComponentInstance) {
			String vmName = removeSuffixFromInstance(internalComponentInstance.getName());
			return new VMKey(vmName, "");
		}

		private static String removeSuffixFromInstance(String vmName){
			return removeSuffix(removeSuffix(vmName));
		}

		private static String removeSuffix(String vmName){
			if (vmName != null) {
				int i = vmName.lastIndexOf("_");
				return vmName.substring(0, i);
			}
			return vmName;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			VMKey vmKey = (VMKey) o;

			if (name != null ? !name.equals(vmKey.name) : vmKey.name != null) return false;
			return type != null ? type.equals(vmKey.type) : vmKey.type == null;
		}

		@Override
		public int hashCode() {
			int result = name != null ? name.hashCode() : 0;
			result = 31 * result + (type != null ? type.hashCode() : 0);
			return result;
		}
	}


}

