package eu.paasage.upperware.solvertodeployment.db.lib;

import camel.core.CamelModel;
import camel.deployment.DeploymentFactory;
import camel.deployment.DeploymentInstanceModel;
import camel.deployment.DeploymentModel;
import camel.deployment.DeploymentTypeModel;
import eu.paasage.upperware.solvertodeployment.utils.DataHolderNew;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CDODatabaseProxy2New {
    
    public static Optional<DeploymentInstanceModel> getLastDeployedInstanceModel(String camelModelID, CDOTransaction transaction) {
        CamelModel camelModel = getLastCamelModel(transaction.getResource(camelModelID).getContents())
                .orElseThrow(() -> new IllegalStateException("Could not find camel model from camelModelID: " + camelModelID));

        DeploymentModel deploymentModel = getLastElement(camelModel.getDeploymentModels());
        if (deploymentModel instanceof DeploymentInstanceModel) {
            return Optional.of((DeploymentInstanceModel) deploymentModel);
        }
        return Optional.empty();
    }

    private static <T extends EObject> T getLastElement(List<T> collection) {
        return CollectionUtils.isNotEmpty(collection) ? collection.get(collection.size() - 1) : null;
    }

    public static int saveNewDeploymentInstanceModel(CDOTransaction transaction, String camelModelID)
            throws CommitException {

        DeploymentInstanceModel deploymentInstanceModel = DeploymentFactory.eINSTANCE.createDeploymentInstanceModel();

        CamelModel camelModel = getLastCamelModel(transaction.getResource(camelModelID).getContents())
                .orElseThrow(() -> new IllegalStateException("Could not find camel model from camelModelID: " + camelModelID));

        EList<DeploymentModel> deploymentModels = camelModel.getDeploymentModels();

        //set required fields in new DeploymentInstanceModel
        String name = deploymentModels.get(0).getName();
        deploymentInstanceModel.setName(name + deploymentModels.size());
        deploymentInstanceModel.setType((DeploymentTypeModel) deploymentModels.get(0));

        deploymentModels.add(deploymentInstanceModel);
        int dmId = deploymentModels.size() - 1;

        try {
            transaction.commit();
        } catch (CommitException e) {
            log.error("Error during commit transaction", e);
            throw e;
        }

        return dmId;
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // Registering stuff into CDO
    //////////////////////////////////////////////////////////////////////////////////////

    public static class DataUpdater {

        public void registerElements(DataHolderNew dataHolder, String camelModelID, CDOTransaction transaction) {
            CDODatabaseProxy2New.DataUpdater.CamelAndDeploymentModelTransactionManager transactionManager = new CDODatabaseProxy2New.DataUpdater.CamelAndDeploymentModelTransactionManager(camelModelID, dataHolder.getDmId(), transaction);
            transactionManager.deploymentInstanceModels.get(transactionManager.dmId - 1)
                    .getSoftwareComponentInstances().addAll(dataHolder.getComponentInstancesToRegister());
            transactionManager.deploymentInstanceModels.get(transactionManager.dmId - 1)
                    .getCommunicationInstances().addAll(dataHolder.getCommunicationInstances());
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
                this.deploymentTypeModel = (DeploymentTypeModel) camelModel.getDeploymentModels().get(0);
                this.dmId = dmId;

                this.deploymentInstanceModels = new ArrayList<>();
                for (int i = 1; i < camelModel.getDeploymentModels().size(); i++) {
                    deploymentInstanceModels.add((DeploymentInstanceModel) camelModel.getDeploymentModels().get(i));
                }
            }

            void commit() {
                camelModel.getDeploymentModels().set(dmId, deploymentInstanceModels.get(dmId - 1));
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
