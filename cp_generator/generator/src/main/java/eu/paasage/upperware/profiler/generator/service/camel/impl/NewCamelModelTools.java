package eu.paasage.upperware.profiler.generator.service.camel.impl;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.Hosting;
import eu.paasage.camel.deployment.VM;
import eu.paasage.camel.deployment.VMRequirementSet;
import eu.paasage.camel.requirement.OSOrImageRequirement;
import eu.paasage.camel.requirement.QuantitativeHardwareRequirement;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

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

    public static List<Hosting> getHostings(DeploymentModel dm) {
        return dm.getHostings();
    }

    public static VMRequirementSet getGlobalRequirements(DeploymentModel dm) {
        return dm.getGlobalVMRequirementSet();
    }

    public static VMRequirementSet getLocalRequirements(VM vm) {
        return vm.getVmRequirementSet();
    }

    public static QuantitativeHardwareRequirement getHardwareRequirements(VM vm) {

        VMRequirementSet globalRequirements = getGlobalRequirements((DeploymentModel)vm.eContainer());
        VMRequirementSet localRequirements = getLocalRequirements(vm);

        QuantitativeHardwareRequirement result = null;
        if (localRequirements != null && localRequirements.getQuantitativeHardwareRequirement() != null) {
            result = localRequirements.getQuantitativeHardwareRequirement();
        }

        if (result == null && globalRequirements != null && globalRequirements.getQuantitativeHardwareRequirement() != null) {
            result = globalRequirements.getQuantitativeHardwareRequirement();
        }

        return result;
    }

    public static OSOrImageRequirement getOsOrImageRequirements(VM vm) {

        VMRequirementSet globalRequirements = getGlobalRequirements((DeploymentModel)vm.eContainer());
        VMRequirementSet localRequirements = getLocalRequirements(vm);

        OSOrImageRequirement result = null;
        if (localRequirements != null && localRequirements.getOsOrImageRequirement() != null) {
            result = localRequirements.getOsOrImageRequirement();
        }

        if (result == null && globalRequirements != null && globalRequirements.getOsOrImageRequirement() != null) {
            result = globalRequirements.getOsOrImageRequirement();
        }

        return result;
    }

    private static <T> T getLastElement(List<T> elements) {
        return CollectionUtils.isNotEmpty(elements) ? elements.get(elements.size()-1) : null;
    }

}
