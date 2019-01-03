package eu.melodic.upperware.adapter.validation;

import camel.deployment.DeploymentInstanceModel;
import eu.melodic.security.authorization.client.AuthorizationServiceClient;
import eu.melodic.security.authorization.client.extractor.DataExtractorHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class DefaultDeploymentInstanceModelValidator implements DeploymentInstanceModelValidator {

    private AuthorizationServiceClient authorizationServiceClient;

    @Override
    public boolean validate(DeploymentInstanceModel deploymentInstanceModel) {
        return preAuthorizeTargetModel(deploymentInstanceModel);
    }

    private boolean preAuthorizeTargetModel(DeploymentInstanceModel targetModel) {

        // Collect plan information to submit to (Pre-)Authorization Server
        String resource = "deployment-model";	//XXX: ...or the actual resource-id of target model in CDO
        String action = "DEPLOY";
        String subject = "Adapter";

        // Call data extractors to collect information from target model
        DataExtractorHelper helper = new DataExtractorHelper(authorizationServiceClient);
        Map<String,Object> extra = helper.getModelData( targetModel );
        log.info("preAuthorizeTargetModel: Extracted target model data: {}", extra);

        // Use PDP client to pre-authorize execution plan against policies
        try {
            log.debug("Calling PDP: {}", authorizationServiceClient.getPdpEndpoint());
            boolean isPermited = authorizationServiceClient.requestAccess(resource, action, subject, extra);
            log.debug("PDP decision: {}", isPermited);
            return isPermited;
        } catch (Exception ex) {
            log.warn("An error occurred while evaluating web access request: ", ex);
            throw new RuntimeException(ex);
        }
    }
}
