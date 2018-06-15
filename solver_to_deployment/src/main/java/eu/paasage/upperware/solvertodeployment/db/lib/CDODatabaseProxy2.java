/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.db.lib;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.camel.execution.ExecutionModel;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
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

	public static int copyFirstDeploymentModel(CDOTransaction transaction, String camelModelID) throws CommitException {

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

	public static class DataUpdater {

		public void registerElements(DataHolder dataHolder, String camelModelID, CDOTransaction transaction) {
			CamelAndDeploymentModelTransactionManager transactionManager = new CamelAndDeploymentModelTransactionManager(camelModelID, dataHolder.getDmId(), transaction);

			((CamelModel)transactionManager.deploymentModel.eContainer()).getProviderModels().addAll(dataHolder.getProviderModel());
			transactionManager.deploymentModel.getInternalComponentInstances().addAll(dataHolder.getComponentInstancesToRegister());
			transactionManager.deploymentModel.getVmInstances().addAll(dataHolder.getVmInstancesToRegister());
			transactionManager.deploymentModel.getHostingInstances().addAll(dataHolder.getHostingInstancesToRegister());
			transactionManager.deploymentModel.getCommunicationInstances().addAll(dataHolder.getCommunicationInstances());

			transactionManager.commitAndClose();
		}

		class CamelAndDeploymentModelTransactionManager {

			DeploymentModel deploymentModel;
			CamelModel camelModel;
//			CDOSessionX session;
			CDOTransaction transaction;
			int dmId;

			CamelAndDeploymentModelTransactionManager(String camelModelID, int dmId, CDOTransaction transaction) {

//				CDOClientX cdoClient = CDODatabaseProxy.getInstance().getCdoClient();
//				this.session = cdoClient.getSession();
//				this.transaction = session.openTransaction();

				this.camelModel = CdoTool.getLastCamelModel(transaction.getResource(camelModelID).getContents())
						.orElseThrow(() -> new IllegalStateException("Could not find camel model from camelModelID: " + camelModelID));
				this.deploymentModel = camelModel.getDeploymentModels().get(dmId);
				this.dmId = dmId;
			}

			void commitAndClose() {
//				camelModel.getDeploymentModels().set(dmId, deploymentModel);
//				try {
//					transaction.commit();
//				} catch (CommitException e) {
//					log.error("Problem with commit", e);
//				} finally {
//					if (transaction != null && !transaction.isClosed()) {
//						session.closeTransaction(transaction);
//					}
//					session.closeSession();
//				}
			}
		}

	}


}
