package eu.paasage.upperware.profiler.generator.communication.impl;

import camel.core.Attribute;
import camel.core.Feature;
import camel.core.NamedElement;
import camel.deployment.RequirementSet;
import camel.location.GeographicalRegion;
import camel.location.LocationModel;
import camel.mms.MmsObject;
import camel.requirement.LocationRequirement;
import camel.requirement.ProviderRequirement;
import camel.requirement.ResourceRequirement;
import camel.type.DoubleValue;
import camel.type.FloatValue;
import camel.type.IntValue;
import camel.type.Value;
import eu.paasage.upperware.profiler.generator.communication.CloudiatorServiceX;
import eu.paasage.upperware.profiler.generator.error.GeneratorException;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.api.MatchmakingApi;
import io.github.cloudiator.rest.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CloudiatorServiceXImpl implements CloudiatorServiceX {

    private static final String HARDWARE_CLASS = "hardware";
    private static final String IMAGE_CLASS = "image";
    private static final String LOCATION_CLASS = "location";
    private static final String CLOUD_CLASS = "cloud";

    private MatchmakingApi matchmakingApi;

    @Override
    public List<NodeCandidate> findNodeCandidates(List<Requirement> requirements) throws ApiException {
        List<NodeCandidate> nodeCandidates = matchmakingApi.findNodeCandidates(requirements);
        removeCredentials(nodeCandidates);
        return nodeCandidates;
    }

    @Override
    public List<Requirement> createRequirements(RequirementSet globalRequirementSet, RequirementSet localRequirementSet, List<LocationModel> locationModels, String imageId) {
        List<Requirement> requirements = new ArrayList<>();
        requirements.addAll(createResourceRequirement(getResourceRequirement(globalRequirementSet, localRequirementSet)));
        requirements.addAll(createLocationRequirement(getLocationRequirement(globalRequirementSet, localRequirementSet), locationModels));
        requirements.addAll(createImageRequirement(imageId));
        requirements.addAll(createOSRequirement(getOSRequirement(globalRequirementSet, localRequirementSet)));
        requirements.addAll(createProviderRequirement(getProviderRequirement(globalRequirementSet, localRequirementSet)));
        return requirements;
    }

    private Collection<? extends Requirement> createResourceRequirement(ResourceRequirement resourceRequirement) {
        if (resourceRequirement == null) {
            return Collections.emptyList();
        }

        List<Requirement> result = new ArrayList<>();

        Map<MmsObject, List<Attribute>> requirementsMap = getRequirementsMap(resourceRequirement);

        getAttribute(requirementsMap, "totalMemoryHasMin").ifPresent(attribute -> result.add(createRequirement(HARDWARE_CLASS, "ram", RequirementOperator.GEQ, getValueAsString(attribute.getValue()))));
        getAttribute(requirementsMap, "totalMemoryHasMax").ifPresent(attribute -> result.add(createRequirement(HARDWARE_CLASS, "ram", RequirementOperator.LEQ, getValueAsString(attribute.getValue()))));

        getAttribute(requirementsMap, "hasMinNumberofCores").ifPresent(attribute -> result.add(createRequirement(HARDWARE_CLASS, "cores", RequirementOperator.GEQ, getValueAsString(attribute.getValue()))));
        getAttribute(requirementsMap, "hasMaxNumberofCores").ifPresent(attribute -> result.add(createRequirement(HARDWARE_CLASS, "cores", RequirementOperator.LEQ, getValueAsString(attribute.getValue()))));

        getAttribute(requirementsMap, "hasMin").ifPresent(attribute -> result.add(createRequirement(HARDWARE_CLASS, "disk", RequirementOperator.GEQ, getValueAsString(attribute.getValue()))));
        getAttribute(requirementsMap, "hasMax").ifPresent(attribute -> result.add(createRequirement(HARDWARE_CLASS, "disk", RequirementOperator.LEQ, getValueAsString(attribute.getValue()))));

        getAttribute(requirementsMap, "minCpu").ifPresent(attribute -> log.warn("MinCpu requirement is not supported"));
        getAttribute(requirementsMap, "maxCpu").ifPresent(attribute -> log.warn("MaxCpu requirement is not supported"));

        return result;
    }

    private Collection<? extends Requirement> createProviderRequirement(ProviderRequirement providerRequirement) {
        if (providerRequirement == null) {
            return Collections.emptyList();
        }

        List<Requirement> result = new ArrayList<>();
        camel.requirement.CloudType cloudType = providerRequirement.getCloudType();
        if (cloudType != null) {
            result.add(createRequirement(CLOUD_CLASS, "type", RequirementOperator.EQ, prepareCloudTypeValue(cloudType.getName())));
        }

        EList<String> providerNames = providerRequirement.getProviderNames();
        if (CollectionUtils.isNotEmpty(providerNames)) {
            result.add(createRequirement(CLOUD_CLASS, "api.providerName", RequirementOperator.IN, String.join(", ", providerNames)));
        }

        return result;
    }

    private Collection<? extends Requirement> createLocationRequirement(LocationRequirement locationRequirement, List<LocationModel> locationModels) {
        if (locationRequirement == null) {
            return Collections.emptyList();
        }

        List<GeographicalRegion> requiredGeographicalRegions = locationRequirement.getLocations().stream().filter(location -> location instanceof GeographicalRegion).map(location -> (GeographicalRegion) location).collect(Collectors.toList());
        List<GeographicalRegion> allGeographicalRegions = locationModels.stream().map(LocationModel::getRegions).flatMap(Collection::stream).collect(Collectors.toList());

        Set<GeographicalRegion> result = new HashSet<>();
        requiredGeographicalRegions.forEach(geographicalRegion -> addRegion(geographicalRegion, allGeographicalRegions, result));

        String requirementValue = result.stream().map(NamedElement::getName).collect(Collectors.joining(", "));

        return Collections.singletonList(createRequirement(LOCATION_CLASS, "name", RequirementOperator.IN, requirementValue));
    }

    private void addRegion(GeographicalRegion geographicalRegion, List<GeographicalRegion> locationModels, Set<GeographicalRegion> result) {
        if (geographicalRegion == null || result.contains(geographicalRegion)) {
            return;
        }
        result.add(geographicalRegion);
        getChildRegions(geographicalRegion, locationModels).forEach(geographicalRegion1 -> addRegion(geographicalRegion1, locationModels, result));
    }

    private List<GeographicalRegion> getChildRegions(GeographicalRegion parentRegion, List<GeographicalRegion> locationModels) {
        return locationModels.stream().filter(geographicalRegion -> isChildOf(geographicalRegion, parentRegion)).collect(Collectors.toList());
    }

    private boolean isChildOf(GeographicalRegion childRegion, GeographicalRegion parentRegion) {
        return childRegion.getParentRegions().stream().anyMatch(geographicalRegion -> geographicalRegion.equals(parentRegion));
    }

    private Collection<? extends Requirement> createImageRequirement(String imageId) {
        if (StringUtils.isBlank(imageId)) {
            return Collections.emptyList();
        }
        return Collections.singletonList(createRequirement(IMAGE_CLASS, "name", RequirementOperator.EQ, imageId));
    }

    private Collection<? extends Requirement> createOSRequirement(camel.requirement.OSRequirement osRequirement) {
        if (osRequirement == null || StringUtils.isBlank(osRequirement.getOs())) {
            return Collections.emptyList();
        }
        return Collections.singletonList(createRequirement(IMAGE_CLASS, "operatingSystem.family", RequirementOperator.IN, prepareOSFamilyValue(osRequirement.getOs())));
    }

    private Map<MmsObject, List<Attribute>> getRequirementsMap(Feature feature) {
        Map<MmsObject, List<Attribute>> result = getRequirementsMap(feature, new HashMap<>());
        checkRequirements(result);
        return result;
    }

    private void checkRequirements(Map<MmsObject, List<Attribute>> result) {
        List<String> errors = new ArrayList<>();
        for (MmsObject mmsObject : result.keySet()) {
            Collection<Attribute> attributes = CollectionUtils.emptyIfNull(result.get(mmsObject));
            if (attributes.size() > 1) {
                errors.add(mmsObject.getId());
            }
        }
        if (CollectionUtils.isNotEmpty(errors)) {
            throw new GeneratorException(errors.stream().collect(Collectors.joining(", ", "Duplicate requirements for: [", "]")));
        }
    }

    private Map<MmsObject, List<Attribute>> getRequirementsMap(Feature feature, Map<MmsObject, List<Attribute>> result) {

        for (Attribute attribute : CollectionUtils.emptyIfNull(feature.getAttributes())) {
            for (MmsObject mmsObject : CollectionUtils.emptyIfNull(attribute.getAnnotations())) {

                List<Attribute> attributes = result.get(mmsObject);
                if (CollectionUtils.isEmpty(attributes)){
                    attributes = new ArrayList<>();
                    result.put(mmsObject, attributes);
                }
                attributes.add(attribute);
            }
        }

        for (Feature feature1 : CollectionUtils.emptyIfNull(feature.getSubFeatures())) {
            getRequirementsMap(feature1, result);
        }
        return result;
    }

    private Requirement createRequirement(String requirementClass, String requirementAttribute, RequirementOperator requirementOperator, String value){
        return new AttributeRequirement()
                .requirementClass(requirementClass)
                .requirementAttribute(requirementAttribute)
                .requirementOperator(requirementOperator)
                .value(value)
                .type("AttributeRequirement");
    }


    private String prepareOSFamilyValue(String osName) {
        String enumName = StringUtils.upperCase(osName);
        if (EnumUtils.isValidEnum(OperatingSystemFamily.class, enumName)) {
            return "OSFamily::" + enumName;
        }
        throw new GeneratorException(String.format("Could not parse %s as a OperatingSystemFamily. Possible values are: %s", enumName, Arrays.toString(OperatingSystemFamily.values())));
    }

    private String prepareCloudTypeValue(String cloudType) {
        String enumName = StringUtils.upperCase(cloudType);
        if (EnumUtils.isValidEnum(CloudType.class, enumName)) {
            return "CloudType::" + enumName;
        }
        throw new GeneratorException(String.format("Could not parse %s as a CloudType. Possible values are: %s", enumName, Arrays.toString(CloudType.values())));
    }

    private Optional<Attribute> getAttribute(Map<MmsObject, List<Attribute>> requirementsMap, String name) {
        return requirementsMap.keySet()
                .stream()
                .filter(mmsObject -> mmsObject.getId().equals(name))
                .findFirst()
                .map(mmsObject -> requirementsMap.get(mmsObject).get(0));
    }

    private ResourceRequirement getResourceRequirement(RequirementSet globalRequirementSet, RequirementSet localRequirementSet) {
        return getRequirement(globalRequirementSet, localRequirementSet, RequirementSet::getResourceRequirement);
    }

    private ProviderRequirement getProviderRequirement(RequirementSet globalRequirementSet, RequirementSet localRequirementSet) {
        return getRequirement(globalRequirementSet, localRequirementSet, RequirementSet::getProviderRequirement);
    }

    private camel.requirement.LocationRequirement getLocationRequirement(RequirementSet globalRequirementSet, RequirementSet localRequirementSet) {
        return getRequirement(globalRequirementSet, localRequirementSet, RequirementSet::getLocationRequirement);
    }

    private camel.requirement.OSRequirement getOSRequirement(RequirementSet globalRequirementSet, RequirementSet localRequirementSet) {
        return getRequirement(globalRequirementSet, localRequirementSet, RequirementSet::getOsRequirement);
    }

    private <T extends camel.requirement.HardRequirement> T getRequirement(RequirementSet globalRequirementSet, RequirementSet localRequirementSet,
                                                                           Function<RequirementSet, T> function) {
        T result = localRequirementSet != null ? function.apply(localRequirementSet) : null;

        if (result == null && globalRequirementSet != null) {
            result = function.apply(globalRequirementSet);
        }
        return result;
    }

    private String getValueAsString(Value value) {
        Objects.requireNonNull(value);
        if (value instanceof IntValue) {
            return String.valueOf(((IntValue) value).getValue());
        } else if (value instanceof FloatValue) {
            return String.valueOf(((FloatValue) value).getValue());
        } else if (value instanceof DoubleValue) {
            return String.valueOf(((DoubleValue) value).getValue());
        }
        throw new GeneratorException("Unsupported value type");
    }

    private void removeCredentials(List<NodeCandidate> nodeCandidates){
        ListUtils.emptyIfNull(nodeCandidates).forEach(nc -> nc.getCloud().setCredential(new CloudCredential()));
        log.debug("Credentials in the Node Candidate List has been removed.");
    }
}
