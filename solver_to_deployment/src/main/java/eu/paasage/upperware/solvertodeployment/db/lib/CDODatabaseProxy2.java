package eu.paasage.upperware.solvertodeployment.db.lib;

import camel.core.CamelModel;
import camel.deployment.DeploymentFactory;
import camel.deployment.DeploymentInstanceModel;
import camel.deployment.DeploymentModel;
import camel.deployment.DeploymentTypeModel;
import camel.location.LocationModel;
import camel.location.impl.LocationFactoryImpl;
import eu.paasage.upperware.solvertodeployment.utils.DataHolder;
import eu.passage.upperware.commons.model.tools.CdoTool;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;

@Slf4j
public class CDODatabaseProxy2 {

    public static int saveNewDeploymentInstanceModel(CDOTransaction transaction, String camelModelID)
            throws CommitException {

        CamelModel camelModel = CdoTool.getCamelModelById(transaction, camelModelID);

        EList<DeploymentModel> deploymentModels = camelModel.getDeploymentModels();

        String name = deploymentModels.get(0).getName() + deploymentModels.size();
        DeploymentTypeModel deploymentTypeModel = (DeploymentTypeModel) deploymentModels.get(0);
        DeploymentInstanceModel deploymentInstanceModel = createDeploymentInstanceModel(name, deploymentTypeModel);

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

    private static DeploymentInstanceModel createDeploymentInstanceModel(String name, DeploymentTypeModel deploymentTypeModel) {
        DeploymentInstanceModel deploymentInstanceModel = DeploymentFactory.eINSTANCE.createDeploymentInstanceModel();
        //set required fields in new DeploymentInstanceModel
        deploymentInstanceModel.setName(name);
        deploymentInstanceModel.setType(deploymentTypeModel);
        return deploymentInstanceModel;
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // Registering stuff into CDO
    //////////////////////////////////////////////////////////////////////////////////////

    public static class DataUpdater {

        public void registerElements(DataHolder dataHolder, String camelModelID, CDOTransaction transaction) {
            CDODatabaseProxy2.DataUpdater.CamelAndDeploymentModelTransactionManager transactionManager = new CDODatabaseProxy2.DataUpdater.CamelAndDeploymentModelTransactionManager(camelModelID, dataHolder.getDmId(), transaction);

            if (CollectionUtils.isEmpty(transactionManager.camelModel.getLocationModels())) {
                String locationModelName = transactionManager.camelModel.getName() + "LocationModel";
                LocationModel locationModel = createLocationModel(locationModelName);
                transactionManager.camelModel.getLocationModels().add(locationModel);
            }

            transactionManager.camelModel.getLocationModels().get(0)
                    .getRegions().addAll(dataHolder.getLocationsToRegister());

            transactionManager.deploymentInstanceModels.get(transactionManager.dmId - 1)
                    .getSoftwareComponentInstances().addAll(dataHolder.getComponentInstancesToRegister());

            transactionManager.deploymentInstanceModels.get(transactionManager.dmId - 1)
                    .getCommunicationInstances().addAll(dataHolder.getCommunicationInstances());

            transactionManager.commit();
        }

        private LocationModel createLocationModel(String name) {
            log.info("Creating new Location Model {}", name);
            LocationModel locationModel = LocationFactoryImpl.eINSTANCE.createLocationModel();
            locationModel.setName(name);
            return locationModel;
        }

        class CamelAndDeploymentModelTransactionManager {

            DeploymentTypeModel deploymentTypeModel;
            CamelModel camelModel;
            CDOTransaction transaction;
            int dmId;
            ArrayList<DeploymentInstanceModel> deploymentInstanceModels;

            CamelAndDeploymentModelTransactionManager(String camelModelID, int dmId, CDOTransaction transaction) {

                this.transaction = transaction;
                this.camelModel = CdoTool.getCamelModelById(transaction, camelModelID);
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
            }
        }

    }
}
