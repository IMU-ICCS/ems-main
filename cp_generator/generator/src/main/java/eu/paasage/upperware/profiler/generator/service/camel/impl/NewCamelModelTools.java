package eu.paasage.upperware.profiler.generator.service.camel.impl;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.Hosting;
import eu.paasage.camel.deployment.VM;
import eu.paasage.camel.deployment.VMRequirementSet;
import eu.paasage.camel.requirement.*;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.function.Function;

public class NewCamelModelTools {

    public static List<VM> getVMs(CamelModel camelModel) {
        return getVMs(getLastDeploymentModel(camelModel));
    }

    public static List<VM> getVMs(DeploymentModel dm) {
        return dm.getVms();
    }

    public static DeploymentModel getLastDeploymentModel(CamelModel camelModel){
        return getLastElement(camelModel.getDeploymentModels());
    }

    public static List<Hosting> getHostings(CamelModel camelModel) {
        return getHostings(getLastDeploymentModel(camelModel));
    }

    private static List<Hosting> getHostings(DeploymentModel dm) {
        return dm.getHostings();
    }

    private static VMRequirementSet getGlobalRequirements(DeploymentModel dm) {
        return dm.getGlobalVMRequirementSet();
    }

    private static VMRequirementSet getLocalRequirements(VM vm) {
        return vm.getVmRequirementSet();
    }

    public static QuantitativeHardwareRequirement getHardwareRequirements(VM vm) {
        return getRequirement(vm, VMRequirementSet::getQuantitativeHardwareRequirement);
    }

    public static OSOrImageRequirement getOsOrImageRequirements(VM vm) {
        return getRequirement(vm, VMRequirementSet::getOsOrImageRequirement);
    }

    public static LocationRequirement getLocationRequirements(VM vm) {
        return getRequirement(vm, VMRequirementSet::getLocationRequirement);
    }

    private static <T extends Requirement> T getRequirement(VM vm,  Function<VMRequirementSet, T> function) {

        VMRequirementSet globalRequirements = getGlobalRequirements((DeploymentModel)vm.eContainer());
        VMRequirementSet localRequirements = getLocalRequirements(vm);

        T result = localRequirements != null ? function.apply(localRequirements) : null;

        if (result == null && globalRequirements != null) {
            result = function.apply(globalRequirements);
        }
        return result;
    }


    public static HorizontalScaleRequirement getScaleRequirementForComponent(List<Requirement> reqs, String componentName) {
        for (Requirement req : reqs) {
            if (req instanceof HorizontalScaleRequirement) {
                HorizontalScaleRequirement hsr = (HorizontalScaleRequirement) req;
                if (hsr.getComponent().getName().equals(componentName))
                    return hsr;
            }
        }
        return null;
    }

    private static <T> T getLastElement(List<T> elements) {
        return CollectionUtils.isNotEmpty(elements) ? elements.get(elements.size()-1) : null;
    }

}
