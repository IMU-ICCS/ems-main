package eu.paasage.upperware.profiler.generator.service.camel.impl;

import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.profiler.generator.service.camel.TypesFactoryService;
import eu.paasage.upperware.profiler.generator.service.camel.VariableService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pszkup on 16.08.17.
 */
@Component
@Slf4j
@AllArgsConstructor
public class VariableServiceImpl implements VariableService {

    private static final String APP_COMPONENT_VAR_PREFIX= "U_app_component_";
    private static final String APP_COMPONENT_VAR_MID= "_vm_";
    private static final String APP_COMPONENT_VAR_SUFFIX= "_provider_";

    private CpFactory cpFactory;
    private TypesFactoryService typesFactoryService;

    @Override
    public Variable createVariable(ApplicationComponent ac, VirtualMachineProfile vm, Provider provider, int min, int max){
        //Var creation
        String vmId= vm.getCloudMLId();
        String providerId= provider.getId();

        String varName= generateApplicationComponentVarName(ac.getCloudMLId(), vmId, providerId);

        log.debug("CPModelDerivator - createAppComponentVariable  - Creating var {} VM Profile/Instance {}", varName, vmId);

        Variable var= createIntegerVariableWithRangeDomain(varName, min, max);
        var.setVmId(vmId);
        var.setProviderId(providerId);
        var.setComponentName(ac.getCloudMLId());
        var.setFlavourName(vm.getFlavourName());

        return var;
    }

    /**
     * Searches all the variables related to a given application component
     * @param ac The application component
     * @param cp The constraint problem for searching the variables
     * @return A list of variables related to the application component
     */
    @Override
    public List<Variable> getVariablesRelatedToAppComponent(ApplicationComponent ac, ConstraintProblem cp) {
        String componentName = ac.getCloudMLId();

        List<Variable> result = cp.getVariables().stream()
                .filter(variable -> variable.getId().contains(componentName))
                .collect(Collectors.toList());

        return result;
    }

    /**
     * Creates a boolean variable with the given name
     * @param varName The variable name
     * @return The boolean variable
     */
    private Variable createBooleanVariable(String varName) {
        return createVariable(varName, createBooleanDomain());
    }

    /**
     * Creates a integer variable with a given name
     * @param varName The variable name
     * @return The integer variable
     */
    public Variable createIntegerVariable(String varName) {
        return createVariable(varName, createIntegerDomain());
    }

    /**
     * Creates a integer variable with a range domain
     * @param varName The name of the variable
     * @param lowerLimit From limit
     * @param upperLimit To limit
     * @return The variable with a range domain
     */
    @Override
    public Variable createIntegerVariableWithRangeDomain(String varName, int lowerLimit, int upperLimit) {
        return createVariable(varName, createIntegerRangeDomain(lowerLimit, upperLimit));
    }

    @Override
    public Variable createDoubleVariableWithRangeDomain(String varName, double lowerLimit, double upperLimit) {
        return createVariable(varName, createDoubleRangeDomain(lowerLimit, upperLimit));
    }

    private Variable createVariable(String varName, Domain domain){
        Variable var= cpFactory.createVariable();
        var.setId(varName);
        var.setDomain(domain);
        return var;
    }

    public String generateApplicationComponentVarName(String appComponentName, String vmpName, String providerId) {
        return APP_COMPONENT_VAR_PREFIX+appComponentName+APP_COMPONENT_VAR_MID+vmpName+APP_COMPONENT_VAR_SUFFIX+providerId;
    }

    /**
     * Creates an integer domain
     * @return The integer domain
     */
    private NumericDomain createIntegerDomain() {
        NumericDomain nd= cpFactory.createNumericDomain();
        nd.setType(BasicTypeEnum.INTEGER);
        return nd;
    }

    /**
     * Creates a boolean domain
     * @return The boolean domain
     */
    private BooleanDomain createBooleanDomain() {
        return cpFactory.createBooleanDomain();
    }

    /**
     * Creates a range domain with given limits
     * @param from From limit
     * @param to To limit
     * @return The range domain
     */
    private RangeDomain createIntegerRangeDomain(int from, int to) {
        RangeDomain rd= cpFactory.createRangeDomain();
        rd.setFrom(typesFactoryService.getIntegerValueUpperware(from));
        rd.setTo(typesFactoryService.getIntegerValueUpperware(to));
        rd.setType(BasicTypeEnum.INTEGER);
        return rd;
    }

    /**
     * Creates a range domain with given limits
     * @param from From limit
     * @param to To limit
     * @return The range domain
     */
    private RangeDomain createDoubleRangeDomain(double from, double to) {
        RangeDomain rd= cpFactory.createRangeDomain();
        rd.setFrom(typesFactoryService.getDoubleValueUpperware(from));
        rd.setTo(typesFactoryService.getDoubleValueUpperware(to));
        rd.setType(BasicTypeEnum.DOUBLE);
        return rd;
    }


}
