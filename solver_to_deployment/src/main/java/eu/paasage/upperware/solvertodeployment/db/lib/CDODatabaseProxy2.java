/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.db.lib;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.*;
import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.camel.execution.ExecutionModel;
import eu.paasage.camel.provider.ProviderModel;
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

	private static CDODatabaseProxy2 cdoDatabaseProxy2 = new CDODatabaseProxy2();

	//////////////////////////////////////////////////////////////////////////////////////
	// Helper class for transactions
	//////////////////////////////////////////////////////////////////////////////////////

	class CamelAndDeploymentModelTransactionManager {

		DeploymentModel deploymentModel;
		CamelModel camelModel;
		CDOTransaction transaction;
		int dmId;

		public CamelAndDeploymentModelTransactionManager(String camelModelID, int dmId) {
			this.transaction = CDODatabaseProxy.getInstance().getCdoClient().openTransaction();
			this.camelModel = (CamelModel) transaction.getResource(camelModelID).getContents().get(0);
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

	//////////////////////////////////////////////////////////////////////////////////////
	// Helper function
	//////////////////////////////////////////////////////////////////////////////////////

	public static int copyFirstDeploymentModel(String camelModelID) throws CommitException {
		CDOTransaction transaction = CDODatabaseProxy.getInstance().getCdoClient().openTransaction();
		CamelModel camelModel = (CamelModel) transaction.getResource(camelModelID).getContents().get(0);
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
		CamelModel camelModel = (CamelModel) transaction.getResource(camelModelID).getContents().get(0);
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

	public static void registerProviderModel(ProviderModel providerModel, String camelModelID, int dmId) {
		CamelAndDeploymentModelTransactionManager transactionManager = cdoDatabaseProxy2.new CamelAndDeploymentModelTransactionManager(camelModelID, dmId);
		((CamelModel)transactionManager.deploymentModel.eContainer()).getProviderModels().add(providerModel);
		transactionManager.commitAndClose();
	}

	public static void registerInternalComponentInstance(InternalComponentInstance internalComponentInstance, String camelModelID, int dmId) {
		CamelAndDeploymentModelTransactionManager transactionManager = cdoDatabaseProxy2.new CamelAndDeploymentModelTransactionManager(camelModelID, dmId);
		transactionManager.deploymentModel.getInternalComponentInstances().add(internalComponentInstance);
		transactionManager.commitAndClose();
	}

	public static void registerVMInstance(VMInstance vmInstance, String camelModelID, int dmId) {
		CamelAndDeploymentModelTransactionManager transactionManager = cdoDatabaseProxy2.new CamelAndDeploymentModelTransactionManager(camelModelID,dmId);
		transactionManager.deploymentModel.getVmInstances().add(vmInstance);
		transactionManager.commitAndClose();
	}

	public static void registerHostingInstance(HostingInstance hostingInstance, String camelModelID, int dmId) {
		CamelAndDeploymentModelTransactionManager transactionManager = cdoDatabaseProxy2.new CamelAndDeploymentModelTransactionManager(camelModelID, dmId);
		transactionManager.deploymentModel.getHostingInstances().add(hostingInstance);
		transactionManager.commitAndClose();
	}

	public static void registerCommunicationInstance(CommunicationInstance communicationInstance, String camelModelID, int dmId) {
		CamelAndDeploymentModelTransactionManager transactionManager = cdoDatabaseProxy2.new CamelAndDeploymentModelTransactionManager(camelModelID, dmId);
		transactionManager.deploymentModel.getCommunicationInstances().add(communicationInstance);
		transactionManager.commitAndClose();
	}

}
