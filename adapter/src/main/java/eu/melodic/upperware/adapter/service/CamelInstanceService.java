package eu.melodic.upperware.adapter.service;

import camel.deployment.DeploymentInstanceModel;
import camel.deployment.DeploymentTypeModel;

import java.util.List;

public interface CamelInstanceService {

    DeploymentInstanceModel createDeploymentInstanceModel(DeploymentTypeModel deploymentTypeModel, List<SoftwareInstanceDetail> softwareInstanceDetails);
}
