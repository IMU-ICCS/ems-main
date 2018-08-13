package eu.paasage.upperware.solvertodeployment.utils;

import camel.deployment.*;
import eu.paasage.upperware.solvertodeployment.derivator.lib.CloudMLHelperNew;
import eu.paasage.upperware.solvertodeployment.lib.S2DException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

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


    //DeploymentInstanceModels from DeploymentModels
    public static EList<DeploymentInstanceModel> getDeploymentInstanceModelsList(EList<DeploymentModel> deploymentModels, int dmId) {
        EList<DeploymentInstanceModel> deploymentInstanceModels = new BasicEList<>();
        for (int i = 0; i < deploymentModels.size(); i++) {
            if (i != dmId) {
                deploymentInstanceModels.add((DeploymentInstanceModel) deploymentModels.get(i));
            }
        }
        return deploymentInstanceModels;
    }


    //////////////////////////////////////////////////////////////////////////////////////
    // Hosting Instance
    //////////////////////////////////////////////////////////////////////////////////////

//    public static Hosting findHosting(SoftwareComponent component, DeploymentTypeModel deploymentTypeModel) throws S2DException {
//        List<Hosting> matchingHosts = new ArrayList<>();
//        deploymentTypeModel.getHostings()
//                .forEach(h -> {
//                   if(h.getRequiredHosts().stream()
//                            .anyMatch(requiredHost -> requiredHost.getName().equals(component.getRequiredHost().getName()))){
//                       matchingHosts.add(h);
//                   }
//                });
//
//        if(!matchingHosts.isEmpty()){
//            return matchingHosts.get(0);
//        }
//        else{
//            throw new S2DException("Unable to find hosting for application component name :" + component.getName() + " . Seems to have error in original model");
//        }
//    }
//
//    public static HostingInstance createHostingInstance(VMInstance vmInstance, SoftwareComponentInstance softwareComponentInstance, DeploymentTypeModel deploymentTypeModel) throws S2DException {
//        SoftwareComponent softwareComponent = (SoftwareComponent) softwareComponentInstance.getType();
//        Hosting hosting = findHosting(softwareComponent, deploymentTypeModel);
//
//        HostingInstance hostingInstance = CloudMLHelperNew.buildNewHostingInstance(softwareComponentInstance.getType().getName(), vmInstance, softwareComponentInstance, hosting);
//        if(hostingInstance == null) {
//            throw new S2DException("Unable to find hosting for application component name" + softwareComponentInstance.getName());
//        }
//        return hostingInstance;
//    }

    //////////////////////////////////////////////////////////////////////////////////////
    // VM Instance
    //////////////////////////////////////////////////////////////////////////////////////

//    public static EList<VMInstance> searchAndCreateVMInstance(VM result, int cardinality) throws S2DException {
//
//        EList<VMInstance> vmInstances = new BasicEList<>();
//        for(int i=0; i<cardinality; i++) {
//            VMInstance vmInstanceResult = CloudMLHelperNew.createVMInstance(result);
//            Attribute attribute = CloudMLHelperNew.findVMType(providerModel);
//            vmInstanceResult.setVmType(attribute);
//            vmInstanceResult.setVmTypeValue(attribute.getValue());
//            vmInstances.add(vmInstanceResult);
//        }
//        return vmInstances;
//    }

}
