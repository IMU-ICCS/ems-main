package eu.melodic.upperware.guibackend.communication.mule;

import eu.melodic.models.services.frontend.DeploymentProcessRequest;
import eu.melodic.upperware.guibackend.controller.deployment.response.DeploymentResponse;

import java.net.MalformedURLException;

public interface MuleApi {

    DeploymentResponse createDeploymentProcess(DeploymentProcessRequest deploymentProcessRequest, String token) throws MalformedURLException;
}
