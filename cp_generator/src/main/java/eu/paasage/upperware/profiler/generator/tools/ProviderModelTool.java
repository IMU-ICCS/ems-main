package eu.paasage.upperware.profiler.generator.tools;

        import eu.paasage.camel.provider.Attribute;
        import eu.paasage.camel.provider.Feature;
        import eu.paasage.camel.provider.ProviderModel;
        import org.eclipse.emf.common.util.EList;


public class ProviderModelTool {


    public static Feature getFeatureByFirstName(ProviderModel fm, String... featureNames) {
        for (String featureName : featureNames) {
            Feature featureByName = getFeatureByName(fm, featureName);
            if (featureByName != null) {
                return featureByName;
            }
        }
        return null;
    }

    public static Feature getFeatureByName(ProviderModel fm, String name) {
        return getFeatureByName(fm.getRootFeature(), name);
    }

    public static Feature getFeatureByName(Feature f, String name) {
        if (f.getName().equals(name))
            return f;
        else {
            for (Feature temp : f.getSubFeatures()) {
                Feature found = getFeatureByName(temp, name);

                if (found != null)
                    return found;
            }
        }

        return null;
    }

    public static Attribute getAttributeByName(Feature f, String attributeName) {
        for (Attribute att : f.getAttributes()) {
            if (att.getName().equals(attributeName))
                return att;
        }
        return null;
    }

    public static Attribute getAttributeByFirstName(Feature f, String... attributeNames) {
        for (String attributeName : attributeNames) {
            for (Attribute att : f.getAttributes()) {
                if (att.getName().equals(attributeName))
                    return att;
            }
        }
        return null;
    }



    public static Feature getSelectedFeatureFromList(EList<Feature> features) {
        for (Feature f : features) {
            if (f.getFeatureCardinality().getValue() > 0)
                return f;
        }
        return null;
    }

}
