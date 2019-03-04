package eu.melodic.upperware.solvertodeployment.utils;

import camel.deployment.*;
import eu.melodic.upperware.solvertodeployment.exception.S2DException;
import io.github.cloudiator.rest.model.NodeCandidate;

import java.util.List;

public interface CamelInstanceService {

    void resetGlobalCount();

    void setGlobalDMIdx(int idx);

    SoftwareComponentInstance createSCInstance(SoftwareComponent softwareComponent);

    List<SoftwareComponentInstance> createSoftwareComponentInstances(String componentName, DeploymentTypeModel deploymentTypeModel, int cardinality, NodeCandidate nodeCandidate) throws S2DException;

    List<CommunicationInstance> createCommunicationInstanceFromDemand(Communication com, DeploymentInstanceModel deploymentInstanceModel, List<SoftwareComponentInstance> softwareComponentInstances) throws S2DException;
}
