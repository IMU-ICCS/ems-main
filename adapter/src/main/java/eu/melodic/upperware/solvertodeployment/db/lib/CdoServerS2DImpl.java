package eu.melodic.upperware.solvertodeployment.db.lib;

import camel.deployment.DeploymentFactory;
import camel.deployment.DeploymentInstanceModel;
import camel.deployment.DeploymentModel;
import camel.deployment.DeploymentTypeModel;
import eu.passage.upperware.commons.model.tools.CdoTool;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CdoServerS2DImpl implements CdoServerS2D {

    @Override
    public int saveNewDeploymentInstanceModel(CDOTransaction transaction, String camelModelID) throws CommitException {
        EList<DeploymentModel> deploymentModels = CdoTool.getCamelModelById(transaction, camelModelID).getDeploymentModels();

        DeploymentTypeModel deploymentTypeModel = (DeploymentTypeModel) deploymentModels.get(0);
        String deploymentInstanceModelName = deploymentTypeModel.getName() + deploymentModels.size();

        deploymentModels.add(createDeploymentInstanceModel(deploymentInstanceModelName, deploymentTypeModel));
        int dmId = deploymentModels.size() - 1;

        transaction.commit();

        return dmId;
    }

    private DeploymentInstanceModel createDeploymentInstanceModel(String name, DeploymentTypeModel deploymentTypeModel) {
        DeploymentInstanceModel deploymentInstanceModel = DeploymentFactory.eINSTANCE.createDeploymentInstanceModel();
        deploymentInstanceModel.setName(name);
        deploymentInstanceModel.setType(deploymentTypeModel);
        return deploymentInstanceModel;
    }



}
