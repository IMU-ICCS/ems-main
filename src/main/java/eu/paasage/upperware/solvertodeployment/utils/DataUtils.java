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
import eu.paasage.upperware.solvertodeployment.db.lib.CDODatabaseProxy2;
import eu.paasage.upperware.solvertodeployment.lib.CommunicationProducerConsumerDomain;
import eu.paasage.upperware.solvertodeployment.lib.S2DException;

public class DataUtils {

	private static Logger log = Logger.getLogger(DataUtils.class);



	public static DataHolder computeDatasToRegister(PaasageConfiguration paasageConfiguration,
			DeploymentModel deploymentModel ) throws S2DException{

		DataHolder result = new DataHolder();


		EList<PaaSageVariable> variables = paasageConfiguration.getVariables();


		for (PaaSageVariable paaSageVariable : variables) {
			try{
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
		EList<Communication> communications = deploymentModel.getCommunications();
		for (Communication communication : communications) {
			CommunicationInstance communicationInstance = CommunicationProducerConsumerDomain.createCommunicationInstanceFromDemand(communication.getName(),deploymentModel,result.getComponentInstancesToRegister());
			result.getCommunicationInstances().add(communicationInstance);
		}
		return result;
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

