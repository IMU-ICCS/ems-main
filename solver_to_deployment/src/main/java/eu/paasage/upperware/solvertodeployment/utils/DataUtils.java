/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.Communication;
import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.CommunicationType;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.HostingInstance;
import eu.paasage.camel.deployment.InternalComponent;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.upperware.metamodel.application.PaaSageVariable;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.solvertodeployment.db.lib.CDODatabaseProxy2;
import eu.paasage.upperware.solvertodeployment.derivator.lib.CloudMLHelper;
import eu.paasage.upperware.solvertodeployment.lib.CommunicationProvidedRequiredDomain;
import eu.paasage.upperware.solvertodeployment.lib.S2DException;

public class DataUtils {

	private static Logger log = Logger.getLogger(DataUtils.class);

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
A HostingInstance must be created for each ProvidedHostInstance associated to the VmInstance. Each HostingInstance must be associated to the
current ProvidedHostInstance and to the RequiredHostInstance matching the InternalComponentInstance.

4. Create CommunicationInstances

 </pre>
	 */
	public static DataHolder computeDatasToRegister(PaasageConfiguration paasageConfiguration,
			DeploymentModel deploymentModel, ConstraintProblem constraintProblem, int solutionId ) throws S2DException
	{
		PaaSageVariable paaSageVariableCurrent = null;

		// Analyzing the model for LOCAL group, ie component connected by LOCAL communication
		// component i => i
		HashMap<String, Integer> localComponentGroups = new HashMap<String, Integer>();
		// i => { components }
		HashMap<Integer, HashSet<String>> localGroups= new HashMap<Integer, HashSet<String>>();
		// Init
		int key=0;
		for (InternalComponent ic : deploymentModel.getInternalComponents())
		{
			log.info("componentGroups: <"+ ic.getName()+ ", "+key+" >");
			localComponentGroups.put(ic.getName(), key);
			HashSet<String> set = new HashSet<String>();
			set.add(ic.getName());
			localGroups.put(key,set);
			log.info("groups: <"+ key + " "+ set + " >");
			key=key+1;
		}
		// Merging sets
		for (Communication communication : deploymentModel.getCommunications())
		{
			if (communication.getType() == CommunicationType.LOCAL)
			{
				String provName = CloudMLHelper.findProvidedComponentFromCommunication(communication).getName();
				int provId = localComponentGroups.get(provName);
				String reqName = CloudMLHelper.findRequiredComponentFromCommunication(communication).getName();
				int reqId = localComponentGroups.get(reqName);
				if (provId == reqId) continue; // already merge
				if (provId < reqId)
				{
					for(Entry<String, Integer> entry : localComponentGroups.entrySet())
					{
						if (entry.getValue() == reqId)
							entry.setValue(provId);
					}
					// merge all elements of reqId into provId
					localGroups.get(provId).addAll(localGroups.get(reqId));
					// reqId & provId use the same set
					localGroups.put(reqId, localGroups.get(provId));
				}
				else
				{
					for(Entry<String, Integer> entry : localComponentGroups.entrySet())
					{
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
		key=0;
		HashMap<Integer, EList<VMInstance>> localGroupVMInstances = new HashMap<Integer, EList<VMInstance>>();
		for (InternalComponent ic : deploymentModel.getInternalComponents())
		{
			if (localComponentGroups.get(ic.getName())==key)
			{
				String msg="";
				for(String str : localGroups.get(key))
				{
					msg = msg + str + " ";
				}
				log.info("Group "+key+": "+msg);
			}
			key=key+1;
		}
		
		// Memory of instances
//		HashMap<Communication, EList<VMInstance>> vmInstanceComs = new HashMap<Communication, EList<VMInstance>>();
		HashMap<InternalComponentInstance, VMInstance> c2vminstances = new HashMap<InternalComponentInstance, VMInstance>();
		HashMap<VMInstance, Set<InternalComponentInstance>> vm2cinstances = new HashMap<VMInstance, Set<InternalComponentInstance>>();

		try
		{
			DataHolder result = new DataHolder();

			EList<PaaSageVariable> variables = paasageConfiguration.getVariables();

			log.debug("1. Dealing with Variable for Component, VM, and Hosting Instances");
			for (PaaSageVariable paaSageVariable : variables)
			{
				Long nb = SolverToDeployementHelper.findCardinalityOf(paaSageVariable, constraintProblem, solutionId);
				if( nb > 0)
				{
					paaSageVariableCurrent = paaSageVariable;
					try
					{
						// Print the value of the variable
						//						SolverToDeployementHelper.printVar(paaSageVariable);
						//						Long val = SolverToDeployementHelper.findCardinalityOf(paaSageVariable, constraintProblem);
						//						log.info("Value="+val);
						// End Print the value of the variable

						String componentId = paaSageVariableCurrent.getRelatedComponent().getCloudMLId();
						
						// Create CI Instance
						EList<InternalComponentInstance> internalComponentInstanceToRegisters = SolverToDeployementHelper.createInternalComponentInstanceFromPaasageVariable(paaSageVariable, deploymentModel, nb);
						result.getComponentInstancesToRegister().addAll(internalComponentInstanceToRegisters);
						
						// Create VM Instance or not (if LOCAL communication)
						int mykey = localComponentGroups.get(componentId);
						EList<VMInstance> vmInstanceToRegisters = localGroupVMInstances.get(mykey);
						log.info("VMs for key "+mykey+": "+vmInstanceToRegisters);
						if (vmInstanceToRegisters == null)
						{
							log.info("Creating new VM Instances...");
							vmInstanceToRegisters = SolverToDeployementHelper.searchAndCreateVMInstance(deploymentModel,paaSageVariable, paasageConfiguration.getId(), nb);
							result.getVmInstancesToRegister().addAll(vmInstanceToRegisters);
							// memorize
							localGroupVMInstances.put(mykey,  vmInstanceToRegisters);
							log.info("**NEW** VMs for key "+mykey+": "+vmInstanceToRegisters);
						}

						// Create Hosting
						for(int i=0; i<nb; i++)
						{
							InternalComponentInstance iCI = internalComponentInstanceToRegisters.get(i);
							VMInstance vmI = vmInstanceToRegisters.get(i);
							HostingInstance hostingInstance  = SolverToDeployementHelper.createHostingInstance(vmI, iCI, deploymentModel);
							result.getHostingInstancesToRegisters().add(hostingInstance);
							// memorizing
							c2vminstances.put(iCI, vmI);
							Set<InternalComponentInstance> cis = vm2cinstances.get(vmI);
							if (cis==null)
							{
								cis = new HashSet<InternalComponentInstance>();
								vm2cinstances.put(vmI, cis);
							}
							cis.add(iCI);
						}
					}
					catch(S2DException e)
					{
						SolverToDeployementHelper.printVar(paaSageVariable);
						throw e;
					}
				}
			}
			
			log.debug("2. Dealing with Communication Instances");
			EList<Communication> communications = deploymentModel.getCommunications();
			for (Communication communication : communications)
			{
				log.info("2a Dealing with communication: "+communication.getName()+"\ttype: "+communication.getType());
				EList<CommunicationInstance> communicationInstances = CommunicationProvidedRequiredDomain.createCommunicationInstanceFromDemand(communication,deploymentModel,result.getComponentInstancesToRegister());
				result.getCommunicationInstances().addAll(communicationInstances);
			}
			log.debug("3. Done.");
			return result;
		}
		catch(Exception e)
		{
			log.error("Error details : ", e);
			log.error("Error when try to decode the input paramers : ");
			if (paaSageVariableCurrent != null)
			{
				SolverToDeployementHelper.printVar(paaSageVariableCurrent);	
			}
			else{
				log.error("No parameters. Must never happened");
			}
		}
		return null;
	}	

	public static void copyCloudProviders(CamelModel cm, String camelModelID, String appId, PaasageConfiguration paasageConfiguration,
			ConstraintProblem constraintProblem, int solutionId) throws S2DException, CommitException
	{

		// Set containing added PM
		Set<String> pmList = new HashSet<String>(); 

		// Register all known PM
		for(ProviderModel pm : cm.getProviderModels())
		{
			log.info("CloudProvider already known: "+pm.getName());
			String PMid = pm.getName();
			pmList.add(PMid);
		}

		// Look for new PM
		EList<PaaSageVariable> variables = paasageConfiguration.getVariables();
		for (PaaSageVariable paaSageVariable : variables)
		{
			if (SolverToDeployementHelper.findCardinalityOf(paaSageVariable, constraintProblem, solutionId) > 0)
			{
				Provider upm = paaSageVariable.getRelatedProvider();
				VirtualMachineProfile relVMprofile = paaSageVariable.getRelatedVirtualMachineProfile();
				String cloudVMId = relVMprofile.getRelatedCloudVMId();
				String pId = upm.getId();
				log.info("New Cloud Provider?: "+pId+" CloudVMid:"+cloudVMId);

				String fullVMid = pId+"#"+cloudVMId;
				if (!pmList.contains(fullVMid)) {
					pmList.add(fullVMid);
					log.info("Copying into CAMEL new ProviderId: "+fullVMid);

					CDODatabaseProxy2.copyAllCloudProviderModel(pId, cloudVMId, camelModelID, appId);
				}
			}
		}
	}	

	public static void registerDataHolderToCDO(String camelModelID, DataHolder dataholder)
	{
		List<VMInstance> vmInstancesToRegister = dataholder.getVmInstancesToRegister();
		for (VMInstance vmInstance : vmInstancesToRegister) {
			CDODatabaseProxy2.registerVMInstance(vmInstance,camelModelID, dataholder.getDmId());
		}
		List<InternalComponentInstance> componentInstancesToRegister = dataholder.getComponentInstancesToRegister();
		for (InternalComponentInstance internalComponentInstance : componentInstancesToRegister) {
			CDODatabaseProxy2.registerInternalComponentInstance(internalComponentInstance ,camelModelID, dataholder.getDmId());

		}
		List<HostingInstance> hostingInstancesToRegister = dataholder.getHostingInstancesToRegisters();
		for (HostingInstance hostingInstance : hostingInstancesToRegister) {
			CDODatabaseProxy2.registerHostingInstance(hostingInstance ,camelModelID, dataholder.getDmId());
		}

		List<CommunicationInstance> communicationInstances = dataholder.getCommunicationInstances();
		for (CommunicationInstance communicationInstance : communicationInstances) {
			CDODatabaseProxy2.registerCommunicationInstance(communicationInstance,camelModelID, dataholder.getDmId());
		}
	}

}

