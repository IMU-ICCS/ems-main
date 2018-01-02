package eu.paasage.upperware.profiler.generator.service.camel.impl;

import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.*;
import eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware;
import eu.paasage.upperware.profiler.generator.service.camel.TypesFactoryService;
import eu.paasage.upperware.profiler.generator.service.camel.VariableService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static eu.passage.upperware.commons.MelodicConstants.APP_COMPONENT_VAR_MID;
import static eu.passage.upperware.commons.MelodicConstants.APP_COMPONENT_VAR_PREFIX;
import static eu.passage.upperware.commons.MelodicConstants.APP_COMPONENT_VAR_SUFFIX;

/**
 * Created by pszkup on 16.08.17.
 */
@Component
@Slf4j
@AllArgsConstructor
public class VariableServiceImpl implements VariableService {

    private CpFactory cpFactory;
    private TypesFactoryService typesFactoryService;

    @Override
    public String getVarName(VariableType variableType, String vmName) {
        return variableType.getLiteral() + "_" + vmName;
    }

    @Override
    public Variable createVariable(ApplicationComponent ac, VirtualMachineProfile vm, Provider provider, int min, int max){
        //Var creation
        String vmId= vm.getCloudMLId();
        String providerId= provider.getId();

        String varName= generateApplicationComponentVarName(ac.getCloudMLId(), vmId, providerId);

        log.debug("CPModelDerivator - createAppComponentVariable  - Creating var {} VM Profile/Instance {}", varName, vmId);

        Variable var= createIntegerVariableWithRangeDomain(varName, min, max, null, null);
        var.setVmId(vmId);
        var.setProviderId(providerId);
        var.setOsImageId(vm.getOs().getName());

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

        return cp.getVariables().stream()
                .filter(variable -> variable.getId().contains(componentName))
                .collect(Collectors.toList());
    }

    @Override
    public Variable createIntegerVariable(VariableType variableType, String componentId, Domain domain) {
        return createVariable(getVarName(variableType, componentId), domain, variableType, componentId);
    }

    @Override
    public Variable createDoubleVariable(VariableType variableType, String componentId, Domain domain) {
        return createVariable(getVarName(variableType, componentId), domain, variableType, componentId);
    }

    @Override
    public Variable createLongVariable(VariableType variableType, String componentId, Domain domain) {
        return createVariable(getVarName(variableType, componentId), domain, variableType, componentId);
    }

    @Override
    public Variable createFloatVariable(VariableType variableType, String componentId, Domain domain) {
        return createVariable(getVarName(variableType, componentId), domain, variableType, componentId);
    }

    /**
     * Creates a integer variable with a range domain
     * @param varName The name of the variable
     * @param lowerLimit From limit
     * @param upperLimit To limit
     * @return The variable with a range domain
     */
    @Override
    public Variable createIntegerVariableWithRangeDomain(String varName, int lowerLimit, int upperLimit, VariableType variableType, String componentId) {
        return createVariable(varName, createIntegerRangeDomain(lowerLimit, upperLimit), variableType, componentId);
    }

    @Override
    public Variable createDoubleVariableWithRangeDomain(String varName, double lowerLimit, double upperLimit, VariableType variableType, String componentId) {
        return createVariable(varName, createDoubleRangeDomain(lowerLimit, upperLimit), variableType, componentId);
    }

    @Override
    public Variable createLongVariableWithRangeDomain(String varName, long lowerLimit, long upperLimit, VariableType variableType, String componentId) {
        return createVariable(varName, createLongRangeDomain(lowerLimit, upperLimit), variableType, componentId);
    }

    @Override
    public Variable createFloatVariableWithRangeDomain(String varName, float lowerLimit, float upperLimit, VariableType variableType, String componentId) {
        return createVariable(varName, createFloatRangeDomain(lowerLimit, upperLimit), variableType, componentId);
    }

    //TODO - po usunieciu metod deprecated mozna usunac varName i wyliczac je samemeu
    private Variable createVariable(String varName, Domain domain, VariableType variableType, String componentId) {
        Variable variable= cpFactory.createVariable();
        variable.setId(varName);
        variable.setDomain(domain);
        variable.setVariableType(variableType);
        variable.setComponentId(componentId);
        return variable;
    }

    public String generateApplicationComponentVarName(String appComponentName, String vmpName, String providerId) {
        return APP_COMPONENT_VAR_PREFIX+appComponentName+APP_COMPONENT_VAR_MID+vmpName+APP_COMPONENT_VAR_SUFFIX+providerId;
    }

    @Override
    public RangeDomain createIntegerRangeDomain(int from, int to) {
        return createRangeDomain(BasicTypeEnum.INTEGER, typesFactoryService.getIntegerValueUpperware(from), typesFactoryService.getIntegerValueUpperware(to));
    }

    @Override
    public RangeDomain createDoubleRangeDomain(double from, double to) {
        return createRangeDomain(BasicTypeEnum.DOUBLE, typesFactoryService.getDoubleValueUpperware(from), typesFactoryService.getDoubleValueUpperware(to));
    }

    @Override
    public RangeDomain createLongRangeDomain(long from, long to) {
        return createRangeDomain(BasicTypeEnum.LONG, typesFactoryService.getLongValueUpperware(from), typesFactoryService.getLongValueUpperware(to));
    }

    @Override
    public RangeDomain createFloatRangeDomain(float from, float to) {
        return createRangeDomain(BasicTypeEnum.FLOAT, typesFactoryService.getFloatValueUpperware(from), typesFactoryService.getFloatValueUpperware(to));
    }

    @Override
    public NumericListDomain createIntegerListDomain(List<Integer> values) {
        IntegerValueUpperware[] avalibleValues = values.stream()
                .map(value -> typesFactoryService.getIntegerValueUpperware(value))
                .toArray(IntegerValueUpperware[]::new);
        return createNumericListDomain(BasicTypeEnum.INTEGER, avalibleValues);
    }

    @Override
    public NumericListDomain createDoubleListDomain(List<Double> values) {
        DoubleValueUpperware[] avalibleValues = values.stream()
                .map(value -> typesFactoryService.getDoubleValueUpperware(value))
                .toArray(DoubleValueUpperware[]::new);
        return createNumericListDomain(BasicTypeEnum.DOUBLE, avalibleValues);
    }

    @Override
    public NumericListDomain createLongListDomain(List<Long> values) {
        LongValueUpperware[] avalibleValues = values.stream()
                .map(value -> typesFactoryService.getLongValueUpperware(value))
                .toArray(LongValueUpperware[]::new);
        return createNumericListDomain(BasicTypeEnum.LONG, avalibleValues);
    }

    @Override
    public NumericListDomain createFloatListDomain(List<Float> values) {
        FloatValueUpperware[] avalibleValues = values.stream()
                .map(value -> typesFactoryService.getFloatValueUpperware(value))
                .toArray(FloatValueUpperware[]::new);
        return createNumericListDomain(BasicTypeEnum.FLOAT, avalibleValues);
    }

    private RangeDomain createRangeDomain(BasicTypeEnum basicTypeEnum, NumericValueUpperware from, NumericValueUpperware to){
        RangeDomain rd= cpFactory.createRangeDomain();
        rd.setFrom(from);
        rd.setTo(to);
        rd.setType(basicTypeEnum);
        return rd;
    }

    private <T extends NumericValueUpperware> NumericListDomain createNumericListDomain(BasicTypeEnum basicTypeEnum, T[] values){
        NumericListDomain rd= cpFactory.createNumericListDomain();
        rd.getValues().addAll(Arrays.asList(values));
        rd.setType(basicTypeEnum);
        return rd;
    }

}
