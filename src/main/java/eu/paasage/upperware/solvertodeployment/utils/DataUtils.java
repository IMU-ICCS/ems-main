/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */



package eu.paasage.upperware.solvertodeployment.utils;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

import eu.paasage.camel.deployment.Communication;
import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.HostingInstance;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.upperware.metamodel.application.PaaSageVariable;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metamodel.cp.VariableValue;
import eu.paasage.upperware.metamodel.types.LongValueUpperware;
import eu.paasage.upperware.solvertodeployment.db.lib.CDODatabaseProxy2;
import eu.paasage.upperware.solvertodeployment.lib.CommunicationProducerConsumerDomain;
import eu.paasage.upperware.solvertodeployment.lib.S2DException;

public class DataUtils {

	private static Logger log = Logger.getLogger(DataUtils.class);


/*<pre>
 * The Solver-to-deployement component is implemented in Java. It re-
ceives the solution as a list of objects with the PaaSageVariable type. A
PaaSageVariable object is described as follows:
RelatedComponent It gives the ApplicationComponent of the UpperModel to
instanciate in the CAMEL model.
RelatedVirtualMachineProfile It enables determining the VM template to in-
stanciate in the CAMEL model.
RelatedProvider It gives the Provider from the UpperwareModel that allows
finding in which provider the VM must be instanciated.
The creation process comprises the following steps.


1. Creation of the InternalComponentInstances.

A PaaSageVariable’s relatedComponent is used to create one correspond-
ing InternalComponentInstance. To do so, we need to find in the CAMEL
model the associated InternalComponent. This InternalComponent is as-
sociated to a list of ProvidedCommunications and a list of requiredCom-
munications. For each item of these lists, we must create a corresponding
instance, either ProvidedCommunicationInstance or RequiredCommuni-
cationInstance (depending on the original type). The two resulting lists
can then be associated to the InternalComponentInstance.


2. Creation of the VmInstance.

D3.1.2 - Product Upperware Report
Page 74 of 98PaaSageVariable’s RelatedVirtualMachineProfile and RelatedProvider are
used to create a vmInstance. Those two values allow finding the VM and
the ProviderModel. From the VM, we create a ProvidedHostInstances that
gets associated to the VmInstance. The ProviderModel is used to find the
VMType and VMTypeValue of the VmInstance.


3. Creation of the HostingInstances.

The HostingInstances are created using the previously-created VmIn-
stance and InternalComponentInstances, as well as the InternalComponent
associated to the ComponentInstance.
A HostingInstance must be created for each ProvidedHostInstance associ-
ated to the VmInstance. Each HostingInstance must be associated to the
current ProvidedHostInstance and to the RequiredHostInstance matching
the InternalComponentInstance.
 * 4. Create CommunicationInstances
 *  
 *  * </pre>
 */
	public static DataHolder computeDatasToRegister(PaasageConfiguration paasageConfiguration,
			DeploymentModel deploymentModel,ConstraintProblem constraintProblem ) throws S2DException{
		PaaSageVariable paaSageVariableCurrent = null;
		try{
			DataHolder result = new DataHolder();
	
	
			EList<PaaSageVariable> variables = paasageConfiguration.getVariables();
	
	
			for (PaaSageVariable paaSageVariable : variables) {
				if(SolverToDeployementHelper.findCardinalityOf(paaSageVariable, constraintProblem) > 0)
					{
					paaSageVariableCurrent = paaSageVariable;
					try{
						SolverToDeployementHelper.printVar(paaSageVariable);
						InternalComponentInstance internalComponentInstanceToRegister = SolverToDeployementHelper.createInternalComponentInstanceFromPaasageVariable(paaSageVariable, deploymentModel);
						result.getComponentInstancesToRegister().add(internalComponentInstanceToRegister);
						VMInstance vmInstanceToRegister = SolverToDeployementHelper.searchAndCreateVMInstance(deploymentModel,paaSageVariable, paasageConfiguration.getId());
						result.getVmInstancesToRegister().add(vmInstanceToRegister);
						HostingInstance hostingInstance  = SolverToDeployementHelper.createHostingInstance(vmInstanceToRegister, internalComponentInstanceToRegister, deploymentModel);
						result.getHostingInstancesToRegisters().add(hostingInstance);
					}catch(S2DException e)
					{
						SolverToDeployementHelper.printVar(paaSageVariable);
						throw e;
					}
				}
			}
			EList<Communication> communications = deploymentModel.getCommunications();
			for (Communication communication : communications) {
				CommunicationInstance communicationInstance = CommunicationProducerConsumerDomain.createCommunicationInstanceFromDemand(communication.getName(),deploymentModel,result.getComponentInstancesToRegister());
				result.getCommunicationInstances().add(communicationInstance);
			}
			return result;
		}catch(Exception e)
		{
			log.error("Error details : ", e);
			log.error("Error when try to decode the input paramers : ");
			if(paaSageVariableCurrent != null)
			{
				SolverToDeployementHelper.printVar(paaSageVariableCurrent);	
			}else{
				log.error("No parameters. Must never happened");
			}
			

			
		}
		return null;
		
	}	
	
	
	public static  void registerDataHolderToCDO(String camelModelID, DataHolder dataholder) {
		{

			List<VMInstance> vmInstancesToRegister = dataholder.getVmInstancesToRegister();
			for (VMInstance vmInstance : vmInstancesToRegister) {
				CDODatabaseProxy2.registerVMInstance(vmInstance,camelModelID);
			}
			List<InternalComponentInstance> componentInstancesToRegister = dataholder.getComponentInstancesToRegister();
			for (InternalComponentInstance internalComponentInstance : componentInstancesToRegister) {
				CDODatabaseProxy2.registerInternalComponentInstance(internalComponentInstance ,camelModelID);

			}
			List<HostingInstance> hostingInstancesToRegister = dataholder.getHostingInstancesToRegisters();
			for (HostingInstance hostingInstance : hostingInstancesToRegister) {
				CDODatabaseProxy2.registerHostingInstance(hostingInstance ,camelModelID);
			}

			List<CommunicationInstance> communicationInstances = dataholder.getCommunicationInstances();
			for (CommunicationInstance communicationInstance : communicationInstances) {
				CDODatabaseProxy2.registerCommunicationInstance(communicationInstance,camelModelID);
			}
		}

	}


}

