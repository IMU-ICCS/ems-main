package eu.paasage.upperware.profiler.generator.service.camel;

import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.VirtualMachine;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.Variable;
import org.eclipse.emf.common.util.EList;

import java.util.List;
import java.util.Optional;

/**
 * Created by pszkup on 07.08.17.
 */
public interface PaasageConfigurationUtilsService {


    Optional<Variable> searchVariableByVMName(List<Variable> variables, String name);

    Optional<ApplicationComponent> searchApplicationComponentById(EList<ApplicationComponent> components, String id);

    Optional<VirtualMachine> searchVMById(EList<VirtualMachine> vms, String id);

    Optional<VirtualMachineProfile> searchVMProfileById(EList<VirtualMachineProfile> vmachines, String id);

    List<Variable> getVariablesRelatedToAppComponent(ApplicationComponent ac, ConstraintProblem cp);

    List<VirtualMachine> getVirtualMachineInstancesByProfile(EList<VirtualMachine> vms, VirtualMachineProfile profile);
}
