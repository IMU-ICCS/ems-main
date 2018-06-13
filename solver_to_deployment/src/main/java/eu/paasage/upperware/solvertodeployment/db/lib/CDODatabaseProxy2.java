/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.db.lib;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.*;
import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.camel.execution.ExecutionModel;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.solvertodeployment.utils.DataHolder;
import eu.passage.upperware.commons.model.tools.CdoTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.List;
import java.util.Optional;

@Slf4j
public class CDODatabaseProxy2 {
	
	//////////////////////////////////////////////////////////////////////////////////////
	// Class member variables
	//////////////////////////////////////////////////////////////////////////////////////

//	private static CDODatabaseProxy2 cdoDatabaseProxy2 = new CDODatabaseProxy2();

	//////////////////////////////////////////////////////////////////////////////////////
	// Helper class for transactions
	//////////////////////////////////////////////////////////////////////////////////////



	//////////////////////////////////////////////////////////////////////////////////////
	// Helper function
	//////////////////////////////////////////////////////////////////////////////////////

	public static int copyFirstDeploymentModel(String camelModelID) throws CommitException {
		CDOTransaction transaction = CDODatabaseProxy.getInstance().getCdoClient().openTransaction();

		CamelModel camelModel = CdoTool.getLastCamelModel(transaction.getResource(camelModelID).getContents())
				.orElseThrow(() -> new IllegalStateException("Could not find camel model from camelModelID: " + camelModelID));

		EList<DeploymentModel> deploymentModels = camelModel.getDeploymentModels();
		DeploymentModel dm = deploymentModels.get(0);
		DeploymentModel dmCopy = EcoreUtil.copy(dm);

		deploymentModels.add(dmCopy);
		int dmId = deploymentModels.size() - 1;

		try {
			transaction.commit();
		} catch (CommitException e) {
			log.error("Error during commit transaction", e);
			throw e;
		} finally {
			if (transaction != null && !transaction.isClosed()) {
				transaction.close();
			}
		}
		return dmId;
	}

	public static Optional<DeploymentModel> getLastDeployedModel(String camelModelID, CDOTransaction transaction){
		CamelModel camelModel = CdoTool.getLastCamelModel(transaction.getResource(camelModelID).getContents())
				.orElseThrow(() -> new IllegalStateException("Could not find camel model from camelModelID: " + camelModelID));

		ExecutionModel lastExecutionModel = getLastElement(camelModel.getExecutionModels());
		if (lastExecutionModel != null) {
			ExecutionContext lastExecutionContext = getLastElement(lastExecutionModel.getExecutionContexts());
			if (lastExecutionContext != null) {
				return Optional.of(lastExecutionContext.getDeploymentModel());
			}
		}
		return Optional.empty();
	}

	private static <T extends EObject> T getLastElement(List<T> collection) {
		return CollectionUtils.isNotEmpty(collection) ? collection.get(collection.size()-1) : null;
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// Registering stuff into CDO
	//////////////////////////////////////////////////////////////////////////////////////

	public static class Xxxx {

		private final CDOClient cdoClient;

		public Xxxx() {
			cdoClient = CDODatabaseProxy.getInstance().getCdoClient();
		}

		public void registerElements(DataHolder dataHolder, String camelModelID) {
			CamelAndDeploymentModelTransactionManager transactionManager = new CamelAndDeploymentModelTransactionManager(cdoClient.openTransaction(), camelModelID, dataHolder.getDmId());

			((CamelModel)transactionManager.deploymentModel.eContainer()).getProviderModels().addAll(dataHolder.getProviderModel());
			transactionManager.deploymentModel.getInternalComponentInstances().addAll(dataHolder.getComponentInstancesToRegister());
			transactionManager.deploymentModel.getVmInstances().addAll(dataHolder.getVmInstancesToRegister());
			transactionManager.deploymentModel.getHostingInstances().addAll(dataHolder.getHostingInstancesToRegister());
			transactionManager.deploymentModel.getCommunicationInstances().addAll(dataHolder.getCommunicationInstances());

			transactionManager.commitAndClose();
		}

		public void registerProviderModel(List<ProviderModel> providerModels, String camelModelID, int dmId) {
			CamelAndDeploymentModelTransactionManager transactionManager = new CamelAndDeploymentModelTransactionManager(cdoClient.openTransaction(), camelModelID, dmId);
			((CamelModel)transactionManager.deploymentModel.eContainer()).getProviderModels().addAll(providerModels);
			transactionManager.commitAndClose();
		}

		public void registerInternalComponentInstance(List<InternalComponentInstance> internalComponentInstances, String camelModelID, int dmId) {
			CamelAndDeploymentModelTransactionManager transactionManager = new CamelAndDeploymentModelTransactionManager(cdoClient.openTransaction(), camelModelID, dmId);
			transactionManager.deploymentModel.getInternalComponentInstances().addAll(internalComponentInstances);
			transactionManager.commitAndClose();
		}

		public void registerVMInstance(List<VMInstance> vmInstances, String camelModelID, int dmId) {
			CamelAndDeploymentModelTransactionManager transactionManager = new CamelAndDeploymentModelTransactionManager(cdoClient.openTransaction(), camelModelID,dmId);
			transactionManager.deploymentModel.getVmInstances().addAll(vmInstances);
			transactionManager.commitAndClose();
		}

		public void registerHostingInstance(List<HostingInstance> hostingInstances, String camelModelID, int dmId) {
			CamelAndDeploymentModelTransactionManager transactionManager = new CamelAndDeploymentModelTransactionManager(cdoClient.openTransaction(), camelModelID, dmId);
			transactionManager.deploymentModel.getHostingInstances().addAll(hostingInstances);
			transactionManager.commitAndClose();
		}

		public void registerCommunicationInstance(List<CommunicationInstance> communicationInstances, String camelModelID, int dmId) {
			CamelAndDeploymentModelTransactionManager transactionManager = new CamelAndDeploymentModelTransactionManager(cdoClient.openTransaction(), camelModelID, dmId);
			transactionManager.deploymentModel.getCommunicationInstances().addAll(communicationInstances);
			transactionManager.commitAndClose();
		}

		class CamelAndDeploymentModelTransactionManager {

			DeploymentModel deploymentModel;
			CamelModel camelModel;
			CDOTransaction transaction;
			int dmId;

			public CamelAndDeploymentModelTransactionManager(CDOTransaction transaction, String camelModelID, int dmId) {
				this.transaction = cdoClient.openTransaction();
				this.camelModel = CdoTool.getLastCamelModel(transaction.getResource(camelModelID).getContents())
						.orElseThrow(() -> new IllegalStateException("Could not find camel model from camelModelID: " + camelModelID));
				this.deploymentModel = camelModel.getDeploymentModels().get(dmId);
				this.dmId = dmId;
			}

			public void commitAndClose() {
				camelModel.getDeploymentModels().set(dmId, deploymentModel);
				try {
					transaction.commit();
				} catch (CommitException e) {
					log.error("Problem with commit", e);
				} finally {
					if (transaction != null && !transaction.isClosed()) {
						transaction.close();
					}
				}
			}
		}

	}


}
