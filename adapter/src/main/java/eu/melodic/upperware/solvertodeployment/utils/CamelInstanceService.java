package eu.melodic.upperware.solvertodeployment.utils;

import camel.deployment.DeploymentInstanceModel;
import camel.deployment.DeploymentTypeModel;

import java.util.List;

public interface CamelInstanceService {

    DeploymentInstanceModel createDeploymentInstanceModel(DeploymentTypeModel deploymentTypeModel, List<SoftwareInstanceDetail> softwareInstanceDetails);
}
