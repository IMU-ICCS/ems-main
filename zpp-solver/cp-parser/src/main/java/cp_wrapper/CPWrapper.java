package cp_wrapper;

import cp_wrapper.parser.CPParsedData;
import cp_wrapper.parser.CPParser;
import cp_wrapper.utility_provider.UtilityProvider;
import cp_wrapper.utils.DomainHandler;
import cp_wrapper.utils.numeric_value.*;
import cp_wrapper.utils.numeric_value.implementations.DoubleValue;
import cp_wrapper.utils.numeric_value.implementations.IntegerValue;
import cp_wrapper.utils.numeric_value.implementations.LongValue;
import cp_wrapper.utils.variable_orderer.HeuristicVariableOrderer;
import cp_wrapper.utils.VariableNumericType;
import cp_wrapper.utils.variable_orderer.VariableOrderer;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTOFactory;
import eu.paasage.upperware.metamodel.cp.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CPWrapper {
    private VariableOrderer variableOrderer;
    private CPParsedData cpParsedData;
    private UtilityProvider utilityProvider;

    public void parse(ConstraintProblem constraintProblem, UtilityProvider utility) {
        CPParser cpParser = new CPParser();
        cpParsedData = cpParser.parse(constraintProblem);
        this.utilityProvider = utility;
        this.variableOrderer = new HeuristicVariableOrderer(cpParsedData.getConstraintGraph());
    }

    public void setVariableOrdering(VariableOrderer vO) {
        this.variableOrderer = vO;
    }

    public boolean checkIfFeasible(List<Integer> assignment) {
        return cpParsedData.checkIfFeasible(getAssignmentFromValueList(assignment));
    }

    public Domain getVariableDomain(int variableIndex) {
        return cpParsedData.getVariableDomain(variableOrderer.getNameFromIndex(variableIndex));
    }

    private NumericValueInterface getVariableValueFromDomainIndex(int varIndex, int value) {
            String variableName = variableOrderer.getNameFromIndex(varIndex);
            Domain domain = cpParsedData.getVariableDomain(variableName);
            if (DomainHandler.isRangeDomain(domain)) {
                return DomainHandler.getRangeValue(value, (RangeDomain) domain);
            } else if (DomainHandler.isNumericListDomain(domain)) {
                return DomainHandler.getNumericListValue(value, (NumericListDomain) domain);
            }

            throw new RuntimeException("Only domains of types RangeDomain, NumericListDomain are supported!");
    }

    private Map<String, NumericValueInterface> getAssignmentFromValueList(List<Integer> assignments) {
        Map<String, NumericValueInterface> vars = new HashMap<>();
        for (int i = 0; i < assignments.size(); i++) {
            if (variableOrderer.exists(i)) {
                vars.put(variableOrderer.getNameFromIndex(i), getVariableValueFromDomainIndex(i, assignments.get(i)));
            }
        }
        return vars;
    }

    public int countViolatedConstraints(List<Integer> assignments) {
        if (assignments.size() != cpParsedData.getVariableNames().size()) {
            throw new RuntimeException("Wrong number of variables in assignment");
        }

        return cpParsedData.countViolatedConstraints(getAssignmentFromValueList(assignments));
    }

    public int getHeuristicEvaluation(List<Integer> assignments, int variableIndex) {
        if (assignments.size() != cpParsedData.getVariableNames().size()) {
            throw new RuntimeException("Wrong number of variables in assignment");
        }

        return cpParsedData.getHeuristicEvaluation(variableOrderer.getNameFromIndex(variableIndex),
                                                    getAssignmentFromValueList(assignments));
    }

    private List<VariableValueDTO> assignmentToVariableValueDTOList(List<Integer> assignments) {
        List<VariableValueDTO> result = new ArrayList<>();
        for (int i = 0; i < assignments.size(); i++) {
            NumericValueInterface val = getVariableValueFromDomainIndex(i, assignments.get(i));
            if (cpParsedData.getVariableType(variableOrderer.getNameFromIndex(i)) == VariableNumericType.INT) {
                if (!(val.isInteger())) {
                    throw new RuntimeException("");
                }
                result.add(
                        VariableValueDTOFactory.createElement(variableOrderer.getNameFromIndex(i), val.getIntValue()
                    )
                );
            } else {
                if (!(val instanceof DoubleValue)) {
                    throw new RuntimeException("Variable " + variableOrderer.getNameFromIndex(i) +" is not of double type!");
                }
                result.add(
                        VariableValueDTOFactory.createElement(
                                variableOrderer.getNameFromIndex(i), val.getDoubleValue()
                        )
                );
            }
        }
        return result;
    }

    public double getUtility(List<Integer> assignments) {
        List<VariableValueDTO> vars = assignmentToVariableValueDTOList(assignments);
        return this.utilityProvider.evaluate(vars);
    }

    public int getMaxDomainValue(int variable) {
        return DomainHandler.getMaxDomainValue(
                cpParsedData.getVariableDomain(variableOrderer.getNameFromIndex(variable))
        );
    }

    public int getMinDomainValue(int variable) {
        return DomainHandler.getMinDomainValue(
                cpParsedData.getVariableDomain(variableOrderer.getNameFromIndex(variable))
        );
    }

    public int getVariablesCount() {
        return cpParsedData.getVariableNames().size();
    }
}
