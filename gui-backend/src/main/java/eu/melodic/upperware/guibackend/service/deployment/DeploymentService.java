package eu.melodic.upperware.guibackend.service.deployment;

import eu.melodic.models.commons.Watermark;
import eu.melodic.models.commons.WatermarkImpl;
import eu.melodic.models.services.frontend.DeploymentProcessRequest;
import eu.melodic.upperware.guibackend.communication.mule.MuleApi;
import eu.melodic.upperware.guibackend.controller.deployment.request.DeploymentRequest;
import eu.melodic.upperware.guibackend.controller.deployment.response.DeploymentResponse;
import eu.melodic.upperware.guibackend.controller.deployment.response.UploadXmiResponse;
import eu.melodic.upperware.guibackend.service.cdo.CdoService;
import eu.melodic.upperware.guibackend.service.cdo.ModelNameGenerator;
import eu.passage.upperware.commons.model.SecureVariable;
import eu.passage.upperware.commons.service.provider.ProviderService;
import eu.passage.upperware.commons.service.store.SecureStoreDBService;
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
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DeploymentService {

    private MuleApi muleClientApi;
    private DeploymentMapper deploymentMapper;
    private CdoService cdoService;
    private ProviderService providerService;
    private ModelNameGenerator modelNameGenerator;
    private final SecureStoreDBService secureStoreDBService;

    public DeploymentResponse createDeploymentProcess(DeploymentRequest deploymentRequest, String token, String refreshToken) {
        deploymentRequest.setCloudDefinitions(deploymentRequest.getCloudDefinitions()
                .stream()
                .map(cloudDefinition -> providerService.fillSecureVariableInCredentials(cloudDefinition))
                .collect(Collectors.toList()));
        DeploymentProcessRequest deploymentProcessRequest = deploymentMapper
                .mapDeploymentRequestToDeploymentProcessRequest(deploymentRequest, createWatermark(deploymentRequest.getUsername()));
        try {
            log.info("LSZ DEV[DeploymentService]: createDeploymentProcess-> \nDeploymentProcessRequest: {}", deploymentProcessRequest);
            return muleClientApi.createDeploymentProcess(deploymentProcessRequest, token, refreshToken);
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Problem with communication with Mule by creating deployment process: %s", e.getMessage()));
        }
    }

    private Watermark createWatermark(String username) {
        Watermark watermark = new WatermarkImpl();
        watermark.setUser(username);
        watermark.setSystem("UI");
        watermark.setDate(new Date());
        watermark.setUuid(UUID.randomUUID().toString());
        return watermark;
    }

    public String uploadXmi(MultipartFile uploadXmiRequest) {

        try {
            if (uploadXmiRequest.getOriginalFilename() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Problem by uploading your %s file. Please try again.", uploadXmiRequest.getResource().getFilename()));
            }

            File xmiFile = new File(uploadXmiRequest.getOriginalFilename());
            Files.copy(uploadXmiRequest.getInputStream(), Paths.get(xmiFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
            String cdoName = modelNameGenerator.getModelName(xmiFile);
            log.info("File {} will be stored under name: {}", uploadXmiRequest.getResource().getFilename(), cdoName);
            if (!cdoService.storeFileInCdo(cdoName, xmiFile)) {
                log.error("Error by storing xmi model into cdo");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Your xmi model %s is invalid or connection timeout occurred. Please try again.", uploadXmiRequest.getResource().getFilename()));
            }
            return cdoName;

        } catch (IOException e) {
            log.error("Error by uploading xmi file:", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Problem by uploading your %s file. Please try again.", uploadXmiRequest.getResource().getFilename()));
        } catch (ConnectorException e) {
            log.error("Error by uploading xmi file:", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Problem by uploading your %s file. CDO repository not working. Please try again.", uploadXmiRequest.getResource().getFilename()));
        } catch (IllegalStateException e) {
            log.error("Error by uploading xmi file:", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Problem by uploading your %s file. CDO repository is in pending state. Please try again.", uploadXmiRequest.getResource().getFilename()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void deleteXmiModel(String xmiName) {
        if (cdoService.deleteXmi(xmiName)) {
            log.info("Model {} successfully deleted from cdo", xmiName);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Problem by deleting your model %s from CDO repository", xmiName));
        }
    }

    public List<String> getAllXmiModels() {
        List<String> allXmiModels = cdoService.getAllXmi();
        allXmiModels.sort(String::compareTo);
        return allXmiModels;
    }

    public UploadXmiResponse createUploadSingleXmiResponse(MultipartFile xmiFile, String cdoName) {
        try {
            List<SecureVariable> secureVariables = findSecureVariables(xmiFile);
            return UploadXmiResponse.builder()
                    .modelName(cdoName)
                    .secureVariables(secureVariables)
                    .httpStatus(HttpStatus.CREATED)
                    .build();
        } catch (ResponseStatusException ex) {
            return UploadXmiResponse.builder()
                    .modelName(cdoName)
                    .secureVariables(Collections.emptyList())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(ex.getMessage())
                    .build();
        }
    }

    public List<UploadXmiResponse> uploadXmiList(List<MultipartFile> files) {
        List<UploadXmiResponse> response = new ArrayList<>(files.size());
        files.forEach(multipartFile -> {
            try {
                String cdoName = uploadXmi(multipartFile);
                log.info("File {} successfully uploaded. Finding secure variables in progress.", cdoName);
                response.add(createUploadSingleXmiResponse(multipartFile, cdoName));
            } catch (ResponseStatusException ex) {
                response.add(UploadXmiResponse.builder()
                        .modelName(multipartFile.getName())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(ex.getMessage())
                        .build());
            }
        });
        return response;
    }

    private List<SecureVariable> findSecureVariables(MultipartFile xmiFile) {
        //List<String> secureVariablesKeys;
        List<String> secureVariablesKeys2;
        try {
            String xmiContent = new String(xmiFile.getBytes());
            //secureVariablesKeys = secureStoreService.findSecureVariables(xmiContent);
            secureVariablesKeys2 = secureStoreDBService.findSecureVariables(xmiContent);
            log.info("LSZ [DeploymentService]: findSecureVariables - secureVariablesKeys2={}", secureVariablesKeys2);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Problem by parsing your uploaded file %s in order to find secure variables", xmiFile.getName()));
        }
        return secureStoreDBService.fillSecureVariablesValues(secureVariablesKeys2);
        //return secureStoreService.fillSecureVariablesValues(secureVariablesKeys);
    }
}
