package eu.melodic.upperware.cp_wrapper.utils.cp_variable;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.*;
import eu.passage.upperware.commons.model.tools.CPModelTool;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static eu.passage.upperware.commons.model.tools.CPModelTool.*;

@Slf4j
public class CpVariableCreator {
    public static CpVariableValue createCpVariableValue(List<VariableValueDTO> bestSolution, CpVariable var) {
        log.debug("Considering variable: {}", var.getId());
        Domain dom = var.getDomain();
        if (dom instanceof RangeDomain) {
            RangeDomain rd = (RangeDomain) dom;
            NumericValueUpperware from = rd.getFrom();
            if (from instanceof IntegerValueUpperware) {
                Number variableValue = findVariableValue(bestSolution, var);
                return CPModelTool.createCpVariableValue(var, createIntegerValueUpperware(variableValue.intValue()));
            } else if (from instanceof LongValueUpperware) {
                Number variableValue = findVariableValue(bestSolution, var);
                return CPModelTool.createCpVariableValue(var, createLongValueUpperware(variableValue.longValue()));
            } else if (from instanceof DoubleValueUpperware) {
                Number variableValue = findVariableValue(bestSolution, var);
                return CPModelTool.createCpVariableValue(var, createDoubleValueUpperware(variableValue.doubleValue()));
            } else {
                Number variableValue = findVariableValue(bestSolution, var);
                return CPModelTool.createCpVariableValue(var, createFloatValueUpperware(variableValue.floatValue()));
            }
        } else if (dom instanceof NumericDomain) {
            NumericDomain nd = (NumericDomain) dom;
            BasicTypeEnum type = nd.getType();
            if (type.equals(BasicTypeEnum.INTEGER)) {
                Number variableValue = findVariableValue(bestSolution, var);
                return CPModelTool.createCpVariableValue(var, createIntegerValueUpperware(variableValue.intValue()));
            } else if (type.equals(BasicTypeEnum.LONG)) {
                Number variableValue = findVariableValue(bestSolution, var);
                return CPModelTool.createCpVariableValue(var, createLongValueUpperware(variableValue.longValue()));
            } else if (type.equals(BasicTypeEnum.DOUBLE)) {
                Number variableValue = findVariableValue(bestSolution, var);
                return CPModelTool.createCpVariableValue(var, createDoubleValueUpperware(variableValue.doubleValue()));
            } else {
                Number variableValue = findVariableValue(bestSolution, var);
                return CPModelTool.createCpVariableValue(var, createFloatValueUpperware(variableValue.floatValue()));
            }
        }
        throw new RuntimeException("Unsupported method type: " + dom.getClass());
    }

    private static Number findVariableValue(List<VariableValueDTO> bestSolution, CpVariable var) {
        return bestSolution
                .stream()
                .filter(variableValueDTO -> variableValueDTO.getName().equals(var.getId()))
                .findFirst().orElseThrow(() -> new RuntimeException("Could not find VariableValue for " + var.getId()))
                .getValue();
    }
}
