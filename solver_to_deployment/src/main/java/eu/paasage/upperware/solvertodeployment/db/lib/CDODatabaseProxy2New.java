package eu.paasage.upperware.solvertodeployment.db.lib;

import camel.core.CamelModel;
import camel.deployment.DeploymentInstanceModel;
import camel.deployment.DeploymentModel;
import camel.deployment.DeploymentTypeModel;
import camel.execution.ExecutionModel;
import eu.paasage.upperware.solvertodeployment.utils.DataHolderNew;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CDODatabaseProxy2New {
    public static int copyFirstDeploymentModel(CDOTransaction transaction, String camelModelID) throws CommitException {

        CamelModel camelModel = getLastCamelModel(transaction.getResource(camelModelID).getContents())
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

    public static Optional<DeploymentTypeModel> getLastDeployedTypeModel(String camelModelID, CDOTransaction transaction) {
        CamelModel camelModel = getLastCamelModel(transaction.getResource(camelModelID).getContents())
                .orElseThrow(() -> new IllegalStateException("Could not find camel model from camelModelID: " + camelModelID));

        ExecutionModel lastExecutionModel = getLastElement(camelModel.getExecutionModels());
        if (lastExecutionModel != null) {
            return Optional.of(lastExecutionModel.getDeploymentTypeModel());
        }
        return Optional.empty();
    }

    public static Optional<DeploymentInstanceModel> getLastDeployedInstanceModel(String camelModelID, CDOTransaction transaction) {
        CamelModel camelModel = getLastCamelModel(transaction.getResource(camelModelID).getContents())
                .orElseThrow(() -> new IllegalStateException("Could not find camel model from camelModelID: " + camelModelID));

        DeploymentInstanceModel deploymentInstanceModel = (DeploymentInstanceModel) getLastElement(camelModel.getDeploymentModels());
        if (deploymentInstanceModel != null) {
            return Optional.of(deploymentInstanceModel);
        }
        return Optional.empty();
    }

    private static <T extends EObject> T getLastElement(List<T> collection) {
        return CollectionUtils.isNotEmpty(collection) ? collection.get(collection.size() - 1) : null;
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // Registering stuff into CDO
    //////////////////////////////////////////////////////////////////////////////////////

    public static class DataUpdater {

        public void registerElements(DataHolderNew dataHolder, String camelModelID, CDOTransaction transaction) {
            CDODatabaseProxy2New.DataUpdater.CamelAndDeploymentModelTransactionManager transactionManager = new CDODatabaseProxy2New.DataUpdater.CamelAndDeploymentModelTransactionManager(camelModelID, dataHolder.getDmId(), transaction);
            transactionManager.deploymentInstanceModels.forEach(deploymentInstanceModel -> {
                deploymentInstanceModel.getSoftwareComponentInstances()
                        .addAll(dataHolder.getComponentInstancesToRegister());
                deploymentInstanceModel.getCommunicationInstances()
                        .addAll(dataHolder.getCommunicationInstances());
            });

            transactionManager.commit();
        }

        class CamelAndDeploymentModelTransactionManager {

            DeploymentTypeModel deploymentTypeModel;
            CamelModel camelModel;
            //			CDOSessionX session;
            CDOTransaction transaction;
            int dmId;
            ArrayList<DeploymentInstanceModel> deploymentInstanceModels;

            CamelAndDeploymentModelTransactionManager(String camelModelID, int dmId, CDOTransaction transaction) {

//				CDOClientX cdoClient = CDODatabaseProxy.getInstance().getCdoClient();
//				this.session = cdoClient.getSession();
//				this.transaction = session.openTransaction();
                this.transaction = transaction;
                this.camelModel = getLastCamelModel(transaction.getResource(camelModelID).getContents())
                        .orElseThrow(() -> new IllegalStateException("Could not find camel model from camelModelID: " + camelModelID));
                this.deploymentTypeModel = (DeploymentTypeModel) camelModel.getDeploymentModels().get(dmId);
                this.dmId = dmId;

                this.deploymentInstanceModels = new ArrayList<>();
                for (int i = 0; i < camelModel.getDeploymentModels().size(); i++) {
                    if (i != dmId) {
                        deploymentInstanceModels.add((DeploymentInstanceModel) camelModel.getDeploymentModels().get(i));
                    }
                }
            }

            void commit() {
                camelModel.getDeploymentModels().set(dmId, deploymentTypeModel);
                try {
                    transaction.commit();
                } catch (CommitException e) {
                    log.error("Problem with commit", e);
                }
//				finally {
//					if (transaction != null && !transaction.isClosed()) {
//						session.closeTransaction(transaction);
//					}
//					session.closeSession();
//				}
            }
        }

    }


    //TODO - move this to
    private static Optional<CamelModel> getLastCamelModel(List<EObject> contentsCM) {
        return getLastElement1(contentsCM)
                .filter(CamelModel.class::isInstance)
                .map(CamelModel.class::cast);
    }

    private static <T extends EObject> Optional<T> getLastElement1(List<T> collection) {
        return Optional.ofNullable(CollectionUtils.isNotEmpty(collection) ? collection.get(collection.size() - 1) : null);
    }
}
