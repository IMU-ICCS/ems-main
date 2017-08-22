package eu.paasage.upperware.profiler.generator.service.camel.impl;

import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.VirtualMachine;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.profiler.generator.service.camel.PaasageConfigurationUtilsService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.EList;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PaasageConfigurationUtilsServiceImpl implements PaasageConfigurationUtilsService {


    @Override
    public Optional<ApplicationComponent> searchApplicationComponentById(EList<ApplicationComponent> components, String id) {
        return components.stream()
                .filter(applicationComponent -> applicationComponent.getCloudMLId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<VirtualMachine> searchVMById(EList<VirtualMachine> vms, String id) {
        return vms.stream()
                .filter(virtualMachine -> virtualMachine.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<VirtualMachineProfile> searchVMProfileById(EList<VirtualMachineProfile> vmachines, String id) {
        return vmachines.stream()
                .filter(virtualMachineProfile -> virtualMachineProfile.getCloudMLId().equals(id))
                .findFirst();
    }

    @Override
    public List<Variable> getVariablesRelatedToAppComponent(ApplicationComponent ac, ConstraintProblem cp) {
        String componentName = ac.getCloudMLId();
        return cp.getVariables().stream()
                .filter(variable -> variable.getComponentName().equals(componentName))
                .collect(Collectors.toList());
    }

    @Override
    public List<VirtualMachine> getVirtualMachineInstancesByProfile(EList<VirtualMachine> vms, VirtualMachineProfile profile) {
        return vms.stream()
                .filter(virtualMachine -> virtualMachine.getProfile().getCloudMLId().equals(profile.getCloudMLId()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Variable> searchVariableByVMName(List<Variable> variables, String variableName) {
        return variables.stream()
                .filter(variable -> variable.getVmId().equals(variableName))
                .findFirst();
    }

}
