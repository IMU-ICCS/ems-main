package eu.paasage.upperware.solvertodeployment.utils;

import camel.core.Attribute;
import camel.core.CamelModel;
import camel.core.CoreFactory;
import camel.deployment.VMInstance;
import camel.location.GeographicalRegion;
import camel.type.StringValue;
import camel.type.TypeFactory;
import eu.paasage.upperware.solvertodeployment.properties.SolverToDeploymentProperties;
import io.github.cloudiator.rest.model.CloudType;
import io.github.cloudiator.rest.model.Image;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProviderEnricherServiceImpl implements ProviderEnricherService {

    private SolverToDeploymentProperties solverToDeploymentProperties;

    private static final String AWS_EC2 = "aws-ec2";
    private static final String CLOUD_API_NAME_SUFFIX = "Api";
    private static final String CLOUD_PROPERTY_NAME_SUFFIX = "Property";
    private static final String CLOUD_CREDENTIAL_NAME_SUFFIX = "Credential";


    @Autowired
    public ProviderEnricherServiceImpl(SolverToDeploymentProperties solverToDeploymentProperties) {
        this.solverToDeploymentProperties = solverToDeploymentProperties;
    }

    @Override
    public void enrichVMInstance(@NonNull VMInstance vmInstance, @NonNull NodeCandidate nodeCandidate, @NonNull String constraintProblemId, @NonNull CamelModel camelModel) {
        EList<Attribute> attributes = vmInstance.getAttributes();

        attributes.add(createAttribute("cloudName", extractCloudName(nodeCandidate, vmInstance.getName(), constraintProblemId)));
        attributes.add(createAttribute("providerName", createProviderName(nodeCandidate, vmInstance.getName(), constraintProblemId)));
        attributes.add(createAttribute("location", extractLocation(nodeCandidate)));
        attributes.add(createAttribute("image", extractImage(nodeCandidate)));
        attributes.add(createAttribute("machineType", extractMachineType(nodeCandidate)));
        attributes.add(createAttribute("driver", extractDriver(nodeCandidate)));
        attributes.add(createAttribute("name", extractName(nodeCandidate)));
        attributes.add(createAttribute("apiName", extractApiName(nodeCandidate)));
        attributes.add(createAttribute("credentialsName", extractCredentialsName(nodeCandidate)));
        attributes.add(createAttribute("propertyName", extractPropertyName(nodeCandidate)));
        attributes.add(createAttribute("endpoint", extractEndpoint(nodeCandidate)));

        //TODO change getting region
        GeographicalRegion region = camelModel.getLocationModels().get(0).getRegions().get(0);
        vmInstance.setLocation(region);
    }

    private Attribute createAttribute(String attributeName, String attributeValue) {
        Attribute strAttribute = CoreFactory.eINSTANCE.createAttribute();
        strAttribute.setName(attributeName);
        strAttribute.setValue(createStringValue(attributeValue));
        return strAttribute;
    }

    private StringValue createStringValue(String attributeValue) {
        StringValue stringValue = TypeFactory.eINSTANCE.createStringValue();
        stringValue.setValue(attributeValue);
        return stringValue;
    }

    private String extractCloudName(NodeCandidate nodeCandidate, String componentName, String constraintProblemId) {
        return createProviderName(nodeCandidate, componentName, constraintProblemId);
    }

    private String createProviderName(NodeCandidate nodeCandidate, String componentName, String constraintProblemId) {
        return String.format("PROVIDER_%s_%s_%s", constraintProblemId, componentName, nodeCandidate.getCloud().getApi().getProviderName());
    }

    private String extractLocation(NodeCandidate nodeCandidate) {
        return nodeCandidate.getLocation().getName();
    }

    private String extractImage(NodeCandidate nodeCandidate) {
        Image image = nodeCandidate.getImage();
        String value = image.getId();
        return value.substring(value.lastIndexOf("~") + 1);
    }

    private String extractMachineType(NodeCandidate nodeCandidate) {
        return nodeCandidate.getHardware().getName();
    }

    private String extractDriver(NodeCandidate nodeCandidate) {
        return nodeCandidate.getCloud().getApi().getProviderName();
    }

    private String extractName(NodeCandidate nodeCandidate) {
        return nodeCandidate.getCloud().getApi().getProviderName();
    }

    private String extractApiName(NodeCandidate nodeCandidate) {
        return extractName(nodeCandidate) + CLOUD_API_NAME_SUFFIX;
    }

    private String extractCredentialsName(NodeCandidate nodeCandidate) {
        return extractName(nodeCandidate) + CLOUD_CREDENTIAL_NAME_SUFFIX;
    }

    private String extractPropertyName(NodeCandidate nodeCandidate) {
        return extractName(nodeCandidate) + CLOUD_PROPERTY_NAME_SUFFIX;
    }

    private String extractEndpoint(NodeCandidate nodeCandidate) {
        CloudType cloudType = nodeCandidate.getCloud().getCloudType();
        String providerName = nodeCandidate.getCloud().getApi().getProviderName();
        if (CloudType.PUBLIC.equals(cloudType)) {
            if (AWS_EC2.equalsIgnoreCase(providerName)) {
                return solverToDeploymentProperties.getEndpoint().getAmazon();
            } else {
                throw new RuntimeException(String.format("Cloud %s is PUBLIC, but only Amazon EC2 is supported", providerName));
            }
        }
        return nodeCandidate.getCloud().getEndpoint();
    }

}