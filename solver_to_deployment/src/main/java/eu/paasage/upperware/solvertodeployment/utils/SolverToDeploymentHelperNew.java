package eu.paasage.upperware.solvertodeployment.utils;

import camel.deployment.*;
import eu.paasage.upperware.solvertodeployment.derivator.lib.CloudMLHelperNew;
import eu.paasage.upperware.solvertodeployment.lib.S2DException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class SolverToDeploymentHelperNew {
    //////////////////////////////////////////////////////////////////////////////////////
    // Software Component Instance
    //////////////////////////////////////////////////////////////////////////////////////

    public static EList<SoftwareComponentInstance> createSoftwareComponentInstance(String componentName, DeploymentTypeModel deploymentTypeModel, int nb) throws S2DException {
        SoftwareComponent softwareComponent = findSoftwareComponent(deploymentTypeModel, componentName);
        return IntStream.range(0, nb)
                .mapToObj(value -> CloudMLHelperNew.createSCInstance(softwareComponent))
                .collect(Collectors.toCollection(BasicEList::new));
    }

    public static SoftwareComponent findSoftwareComponent(DeploymentTypeModel deploymentTypeModel, String componentName) throws S2DException {
        return deploymentTypeModel.getSoftwareComponents().stream()
                .filter(internalComponent -> internalComponent.getName().equalsIgnoreCase(componentName))
                .findFirst()
                .orElseThrow(() -> new S2DException("Unable to find " + componentName + " component in camel model"));
    }


    //////////////////////////////////////////////////////////////////////////////////////
    // VM Instance
    //////////////////////////////////////////////////////////////////////////////////////

    public static EList<VMInstance> searchAndCreateVMInstance(VM result, int cardinality) throws S2DException {

        EList<VMInstance> vmInstances = new BasicEList<>();
        for (int i = 0; i < cardinality; i++) {
            VMInstance vmInstanceResult = CloudMLHelperNew.createVMInstance(result);
            //Attribute attribute = CloudMLHelperNew.findVMType(providerModel);
            // vmInstanceResult.setVmTypeValue(attribute.getValue());
            vmInstanceResult.setType(result);
            vmInstances.add(vmInstanceResult);
        }
        return vmInstances;
    }


    //	//////////////////////////////////////////////////////////////////////////////////////
//	// Hosting Instance
//	//////////////////////////////////////////////////////////////////////////////////////

    private static List<Hosting> findHostings(SoftwareComponent component, DeploymentTypeModel deploymentTypeModel) throws S2DException {
        List<Hosting> result = new ArrayList<>();

        deploymentTypeModel.getHostings().forEach(hosting -> {
            EList<RequiredHost> requiredHosts = hosting.getRequiredHosts();
            requiredHosts.forEach(requiredHost -> log.info("Required host from hosting: {}", requiredHost.getName()));
            String requiredHostFromComponentName = component.getRequiredHost().getName();
            log.info("Req host from component: {}", requiredHostFromComponentName);
            Optional<RequiredHost> first = requiredHosts.stream().filter(requiredHost -> requiredHost.getName().equals(requiredHostFromComponentName)).findFirst();
            if (first.isPresent()) {
                result.add(hosting);
            }
        });

        return result;
    }

    public static List<HostingInstance> createHostingInstance(VMInstance vmInstance, SoftwareComponentInstance softwareComponentInstance, DeploymentTypeModel deploymentTypeModel) throws S2DException {
        List<HostingInstance> result = new ArrayList<>();
        SoftwareComponent softwareComponent = (SoftwareComponent) softwareComponentInstance.getType();
        List<Hosting> hostings = findHostings(softwareComponent, deploymentTypeModel);
        hostings.forEach(hosting -> {
            HostingInstance hostingInstance = CloudMLHelperNew.buildNewHostingInstance(softwareComponentInstance.getType().getName(), vmInstance, softwareComponentInstance, hosting);
            result.add(hostingInstance);
        });
        return result;
    }
}
