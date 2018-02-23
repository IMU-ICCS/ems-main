package eu.paasage.upperware.solvertodeployment.utils;

import eu.paasage.camel.provider.*;
import eu.paasage.camel.type.SingleValue;
import eu.paasage.camel.type.StringsValue;
import eu.paasage.camel.type.TypeFactory;
import io.github.cloudiator.rest.model.CloudType;
import io.github.cloudiator.rest.model.Image;
import io.github.cloudiator.rest.model.NodeCandidate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by pszkup on 11.01.18.
 */
public class ProviderModelTransformer {

    private static final ProviderFactory PROVIDER_FACTORY = ProviderFactory.eINSTANCE;
    private static final TypeFactory TYPE_FACTORY = TypeFactory.eINSTANCE;

    public static ProviderModel createProviderModel(NodeCandidate nodeCandidate, String componentName, String constraintProblemId, String amazonEndpoint) {
        ProviderModel providerModel = PROVIDER_FACTORY.createProviderModel();
        providerModel.setName(createProviderName(nodeCandidate, componentName, constraintProblemId));
        providerModel.setRootFeature(createRootFeature(nodeCandidate, amazonEndpoint));

        return providerModel;
    }

    private static Feature createRootFeature(NodeCandidate nodeCandidate, String amazonEndpoint) {
        Feature result = PROVIDER_FACTORY.createFeature();
        result.setName(createRootFeatureName(nodeCandidate));
        result.getAttributes().addAll(createRootAttributes(nodeCandidate, amazonEndpoint));
        result.getSubFeatures().add(createVMSubFeature(nodeCandidate));
        result.getSubFeatures().add(createLocation(nodeCandidate));
        result.setFeatureCardinality(createFeatureCardinality());

        return result;
    }

    private static Feature createLocation(NodeCandidate nodeCandidate) {
        Exclusive exclusive = PROVIDER_FACTORY.createExclusive();
        exclusive.setName("Location");
        exclusive.getAttributes().add(createLocationIdAttribute(nodeCandidate));
        exclusive.setFeatureCardinality(createFeatureCardinality());
        return exclusive;
    }

    private static Feature createVMSubFeature(NodeCandidate nodeCandidate) {
        Feature result = PROVIDER_FACTORY.createFeature();
        result.setName("VM");
        String vmName = nodeCandidate.getHardware().getName();
        result.getAttributes().add(createAttribute("VMType", createStringValue(vmName)));

        String ami = getAmi(nodeCandidate.getImage());
        result.getAttributes().add(createAttribute("VMImageId", createStringValue(ami)));
        result.setFeatureCardinality(createFeatureCardinality());
        return result;
    }

    private static String getAmi(Image image) {
        String value = image.getId();
        return value.substring(value.lastIndexOf("~") + 1);
    }

    private static FeatCardinality createFeatureCardinality() {
        FeatCardinality featCardinality = PROVIDER_FACTORY.createFeatCardinality();
        featCardinality.setValue(1);
        featCardinality.setCardinalityMin(1);
        featCardinality.setCardinalityMax(1);
        return featCardinality;
    }


    private static Collection<? extends Attribute> createRootAttributes(NodeCandidate nodeCandidate, String amazonEndpoint) {
        List<Attribute> result = new ArrayList<>();
        result.add(createDeploymentModelAttribute(nodeCandidate));
        result.add(createEndpointAttribute(nodeCandidate, amazonEndpoint));
        result.add(createNameAttribute(nodeCandidate));
        result.add(createDriverAttribute(nodeCandidate));
        return result;
    }

    private static Attribute createDeploymentModelAttribute(NodeCandidate nodeCandidate){
        return createAttribute("DeploymentModel", createStringValue(nodeCandidate.getCloud().getCloudType().getValue()));
    }

    private static Attribute createEndpointAttribute(NodeCandidate nodeCandidate, String amazonEndpoint) {

        CloudType cloudType = nodeCandidate.getCloud().getCloudType();
        String providerName = nodeCandidate.getCloud().getApi().getProviderName();
        if (CloudType.PUBLIC.equals(cloudType)){
            if ("aws-ec2".equalsIgnoreCase(providerName)){
                return createAttribute("Endpoint", createStringValue(amazonEndpoint));
            } else {
                throw new  RuntimeException( String.format("Cloud %s is PUBLIC, but only Amazon EC2 is supported", providerName));
            }
        }
        return createAttribute("Endpoint", createStringValue(nodeCandidate.getCloud().getEndpoint()));
    }

    private static Attribute createLocationIdAttribute(NodeCandidate nodeCandidate) {
        String locationName = nodeCandidate.getHardware().getLocation().getName();
        return createAttribute("LocationId", createStringValue(locationName));
    }

    private static Attribute createNameAttribute(NodeCandidate nodeCandidate) {
        return createAttribute("Name", createStringValue(nodeCandidate.getCloud().getId()));
    }


    private static Attribute createDriverAttribute(NodeCandidate nodeCandidate) {
        String providerName = nodeCandidate.getCloud().getApi().getProviderName();
        return createAttribute("Driver", createStringValue(providerName));
    }

    private static Attribute createAttribute(String attributeName, SingleValue value) {
        Attribute result = PROVIDER_FACTORY.createAttribute();
        result.setName(attributeName);
        result.setValue(value);
        return result;
    }

    private static SingleValue createStringValue(String value) {
        StringsValue result = TYPE_FACTORY.createStringsValue();
        result.setValue(value);
        return result;
    }

    private static String createRootFeatureName(NodeCandidate nodeCandidate) {
        return String.format("ROOT_FEATURE_%s", nodeCandidate.getCloud().getApi().getProviderName());
    }

    private static String createProviderName(NodeCandidate nodeCandidate, String componentName, String constraintProblemId) {
        return String.format("PROVIDER_%s_%s_%s", constraintProblemId, componentName, nodeCandidate.getCloud().getApi().getProviderName());
    }

}
