package eu.melodic.upperware.guibackend.service.deployment;

import eu.melodic.models.commons.Watermark;
import eu.melodic.models.commons.WatermarkImpl;
import eu.melodic.models.services.frontend.DeploymentProcessRequest;
import eu.melodic.upperware.guibackend.communication.mule.MuleClientApi;
import eu.melodic.upperware.guibackend.controller.deployment.request.DeploymentRequest;
import eu.melodic.upperware.guibackend.controller.deployment.response.DeploymentResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DeploymentService {

    private MuleClientApi muleClientApi;
    private DeploymentMapper deploymentMapper;

    public DeploymentResponse createDeploymentProcess(DeploymentRequest deploymentRequest) {
        DeploymentProcessRequest deploymentProcessRequest = deploymentMapper.mapDeploymentRequestToDeploymentProcessRequest(deploymentRequest, createWatermark(deploymentRequest.getUsername()));
        return muleClientApi.createDeploymentProcess(deploymentProcessRequest);
    }

    private Watermark createWatermark(String username) {
        Watermark watermark = new WatermarkImpl();
        watermark.setUser(username);
        watermark.setSystem("UI");
        watermark.setDate(new Date());
        watermark.setUuid(UUID.randomUUID().toString());
        return watermark;
    }

}
