package eu.paasage.upperware.profiler.generator.service.camel.impl;

import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.*;
import eu.paasage.upperware.profiler.generator.service.camel.TypesFactoryService;
import eu.paasage.upperware.profiler.generator.service.camel.VariableService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

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
    public CpVariable createIntegerCpVariable(VariableType variableType, String componentId, Domain domain) {
        return createVariable(getVarName(variableType, componentId), domain, variableType, componentId);
    }

    @Override
    public CpVariable createDoubleCpVariable(VariableType variableType, String componentId, Domain domain) {
        return createVariable(getVarName(variableType, componentId), domain, variableType, componentId);
    }

    @Override
    public CpVariable createFloatCpVariable(VariableType variableType, String componentId, Domain domain) {
        return createVariable(getVarName(variableType, componentId), domain, variableType, componentId);
    }

    @Override
    public CpVariable createIntegerCpVariable(String name, VariableType variableType, String componentId, Domain domain) {
        return createVariable(name, domain, variableType, componentId);
    }

    @Override
    public CpVariable createFloatCpVariable(String name, VariableType variableType, String componentId, Domain domain) {
        return createVariable(name, domain, variableType, componentId);
    }

    @Override
    public CpVariable createDoubleCpVariable(String name, VariableType variableType, String componentId, Domain domain) {
        return createVariable(name, domain, variableType, componentId);
    }

    private CpVariable createVariable(String varName, Domain domain, VariableType variableType, String componentId) {
        CpVariable variable= cpFactory.createCpVariable();
        variable.setId(varName);
        variable.setDomain(domain);
        variable.setVariableType(variableType);
        variable.setComponentId(componentId);
        return variable;
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
