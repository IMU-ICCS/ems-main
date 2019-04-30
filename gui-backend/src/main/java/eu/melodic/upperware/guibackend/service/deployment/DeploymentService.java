package eu.melodic.upperware.guibackend.service.deployment;

import eu.melodic.models.commons.Watermark;
import eu.melodic.models.commons.WatermarkImpl;
import eu.melodic.models.services.frontend.DeploymentProcessRequest;
import eu.melodic.upperware.guibackend.communication.camunda.CamundaClientApi;
import eu.melodic.upperware.guibackend.communication.mule.MuleClientApi;
import eu.melodic.upperware.guibackend.controller.deployment.request.DeploymentRequest;
import eu.melodic.upperware.guibackend.controller.deployment.response.DeploymentResponse;
import eu.melodic.upperware.guibackend.controller.deployment.response.ProcessVariables;
import eu.melodic.upperware.guibackend.controller.deployment.response.UploadXmiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.net4j.connector.ConnectorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DeploymentService {

    private MuleClientApi muleClientApi;
    private DeploymentMapper deploymentMapper;
    private CdoService cdoService;
    private CamundaClientApi camundaClientApi;

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

    public UploadXmiResponse uploadXmi(MultipartFile uploadXmiRequest) {

        String cdoName = cdoService.getCdoName(uploadXmiRequest.getResource().getFilename(), ".xmi");

        try {
            if (uploadXmiRequest.getOriginalFilename() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Problem by uploading your %s file. Please try again.", uploadXmiRequest.getResource().getFilename()));
            }

            File xmiFile = new File(uploadXmiRequest.getOriginalFilename());
            Files.copy(uploadXmiRequest.getInputStream(), Paths.get(xmiFile.getPath()), StandardCopyOption.REPLACE_EXISTING);

            if (!cdoService.storeFileInCdo(cdoName, xmiFile)) {
                log.error("Error by storing xmi model into cdo");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your xmi model is invalid. Please try again.");
            }
        } catch (IOException e) {
            log.error("Error by uploading xmi file:", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Problem by uploading your %s file. Please try again.", uploadXmiRequest.getResource().getFilename()));
        } catch (ConnectorException e) {
            log.error("Error by uploading xmi file:", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Problem by uploading your %s file. CDO repository not working. Please try again.", uploadXmiRequest.getResource().getFilename()));
        } catch (IllegalStateException e) {
            log.error("Error by uploading xmi file:", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Problem by uploading your %s file. CDO repository is in pending state. Please try again.", uploadXmiRequest.getResource().getFilename()));
        }
        return new UploadXmiResponse(cdoName);
    }

    public void deleteXmiModel(String xmiName) {
        if (cdoService.deleteXmi(xmiName)) {
            log.info("Model {} successfully deleted from cdo", xmiName);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Problem by deleting your model %s from CDO repository", xmiName));
        }
    }

    public void getAllXmiModels() {
        cdoService.getAllXmi();
    }

    public ProcessVariables getProcessVariables(String processId) {
        return camundaClientApi.getProcessVariables(processId);
    }
}
