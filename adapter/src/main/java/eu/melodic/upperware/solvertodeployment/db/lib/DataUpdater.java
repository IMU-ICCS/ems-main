package eu.melodic.upperware.solvertodeployment.db.lib;

import camel.core.CamelModel;
import camel.deployment.DeploymentInstanceModel;
import camel.location.LocationModel;
import camel.location.impl.LocationFactoryImpl;
import eu.melodic.upperware.solvertodeployment.utils.DataHolder;
import eu.passage.upperware.commons.model.tools.CdoTool;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DataUpdater {

    public void registerElements(DataHolder dataHolder, String camelModelID, CDOTransaction transaction) {
        DataUpdater.CamelAndDeploymentModelTransactionManager transactionManager = new DataUpdater.CamelAndDeploymentModelTransactionManager(camelModelID, dataHolder.getDmId(), transaction);

        if (CollectionUtils.isEmpty(transactionManager.camelModel.getLocationModels())) {
            String locationModelName = transactionManager.camelModel.getName() + "LocationModel";
            LocationModel locationModel = createLocationModel(locationModelName);
            transactionManager.camelModel.getLocationModels().add(locationModel);
        }

        DeploymentInstanceModel deploymentInstanceModel = transactionManager.deploymentInstanceModels.get(transactionManager.dmId - 1);
        deploymentInstanceModel.getSoftwareComponentInstances().addAll(dataHolder.getComponentInstancesToRegister());
        deploymentInstanceModel.getCommunicationInstances().addAll(dataHolder.getCommunicationInstances());

        transactionManager.commit();
    }

    private LocationModel createLocationModel(String name) {
        log.info("Creating new Location Model {}", name);
        LocationModel locationModel = LocationFactoryImpl.eINSTANCE.createLocationModel();
        locationModel.setName(name);
        return locationModel;
    }

    class CamelAndDeploymentModelTransactionManager {

        CamelModel camelModel;
        CDOTransaction transaction;
        int dmId;
        List<DeploymentInstanceModel> deploymentInstanceModels;

        CamelAndDeploymentModelTransactionManager(String camelModelID, int dmId, CDOTransaction transaction) {

            this.transaction = transaction;
            this.camelModel = CdoTool.getCamelModelById(transaction, camelModelID);
            this.dmId = dmId;
            this.deploymentInstanceModels = camelModel.getDeploymentModels()
                    .stream()
                    .map(deploymentModel -> (DeploymentInstanceModel) deploymentModel)
                    .collect(Collectors.toList());
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
