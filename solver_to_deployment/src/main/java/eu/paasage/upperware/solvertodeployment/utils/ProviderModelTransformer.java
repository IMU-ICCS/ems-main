package eu.paasage.upperware.solvertodeployment.utils;

import eu.melodic.cloudiator.client.model.NodeCandidate;
import eu.paasage.camel.provider.*;
import eu.paasage.camel.type.SingleValue;
import eu.paasage.camel.type.StringsValue;
import eu.paasage.camel.type.TypeFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by pszkup on 11.01.18.
 */
public class ProviderModelTransformer {

    private static final ProviderFactory PROVIDER_FACTORY = ProviderFactory.eINSTANCE;
    private static final TypeFactory TYPE_FACTORY = TypeFactory.eINSTANCE;

    public static ProviderModel createProviderModel(NodeCandidate nodeCandidate, String componentName, String constraintProblemId) {
        ProviderModel providerModel = PROVIDER_FACTORY.createProviderModel();
        providerModel.setName(createProviderName(nodeCandidate, componentName, constraintProblemId));
        providerModel.setRootFeature(createRootFeature(nodeCandidate));

        return providerModel;
    }

    private static Feature createRootFeature(NodeCandidate nodeCandidate) {
        Feature result = PROVIDER_FACTORY.createFeature();
        result.setName(createRootFeatureName(nodeCandidate));
        result.getAttributes().addAll(createRootAttributes(nodeCandidate));
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
//        result.getAttributes().add(createAttribute("VMType", createStringValue(nodeCandidate.getHardware().getName()), null, null));
        result.getAttributes().add(createAttribute("VMType", createStringValue("t2.medium")));
        //TODO - skad wziac VMImageId (ami)
//        attribute VMImageId {
//            value: string value 'eu-west-1/ami-b9b394ca'
//            value type:  MyAmazonPM.AmazonEC2Type.VMImageIdEnum
//        }
        result.getAttributes().add(createAttribute("VMImageId", createStringValue("eu-west-1/ami-b9b394ca")));
        result.setFeatureCardinality(createFeatureCardinality());
        return result;
    }

    private static FeatCardinality createFeatureCardinality() {
        FeatCardinality featCardinality = PROVIDER_FACTORY.createFeatCardinality();
        featCardinality.setValue(1);
        featCardinality.setCardinalityMin(1);
        featCardinality.setCardinalityMax(1);
        return featCardinality;
    }


    private static Collection<? extends Attribute> createRootAttributes(NodeCandidate nodeCandidate) {
        List<Attribute> result = new ArrayList<>();
        result.add(createDeploymentModelAttribute(nodeCandidate));
        result.add(createEndpointAttribute(nodeCandidate));
        result.add(createNameAttribute(nodeCandidate));
        result.add(createDriverAttribute(nodeCandidate));
        return result;
    }

    private static Attribute createDeploymentModelAttribute(NodeCandidate nodeCandidate){
        return createAttribute("DeploymentModel", createStringValue(nodeCandidate.getCloud().getCloudType().getValue()));
    }

    private static Attribute createEndpointAttribute(NodeCandidate nodeCandidate) {
//        return createAttribute("Endpoint", createStringValue(nodeCandidate.getCloud().getEndpoint()), null, null);
        return createAttribute("Endpoint", createStringValue("http://ec2.eu-west-1.amazonaws.com"));
    }

    private static Attribute createLocationIdAttribute(NodeCandidate nodeCandidate) {
//        return createAttribute("LocationId", createStringValue(nodeCandidate.getCloud().getEndpoint()), null, null);
        //TODO - skad wziac  locationId
        return createAttribute("LocationId", createStringValue("eu-west-1"));
    }

//        attribute  Name {
//            value:  string value  'EC2'
//            value type: MyAmazonPM.AmazonEC2Type.StringValueType
//        }
    private static Attribute createNameAttribute(NodeCandidate nodeCandidate) {
        //TODO - skad wziac name ??
        return createAttribute("Name", createStringValue("EC2"));
    }


    private static Attribute createDriverAttribute(NodeCandidate nodeCandidate) {

        return createAttribute("Driver", createStringValue("aws-ec2"));
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
