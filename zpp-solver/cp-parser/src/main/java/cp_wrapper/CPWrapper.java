package cp_wrapper;

import cp_wrapper.parser.CPParsedData;
import cp_wrapper.parser.CPParser;
import cp_wrapper.utility_provider.UtilityProvider;
import cp_wrapper.utils.domain_handler.DomainHandler;
import cp_wrapper.utils.numeric_value.*;
import cp_wrapper.utils.numeric_value.implementations.DoubleValue;
import cp_wrapper.utils.variable_orderer.HeuristicVariableOrderer;
import cp_wrapper.utils.cp_variable.VariableNumericType;
import cp_wrapper.utils.variable_orderer.RandomVariableOrderer;
import cp_wrapper.utils.variable_orderer.VariableOrderer;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTOFactory;
import eu.paasage.upperware.metamodel.cp.*;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;


public class CPWrapper {
    private VariableOrderer variableOrderer;
    private CPParsedData cpParsedData;
    private UtilityProvider utilityProvider;
    @Getter
    private Collection<VariableDTO> variableDTOCollection;
    @Getter
    private long numberOfComponents;

    public void parse(ConstraintProblem constraintProblem, UtilityProvider utility) {
        CPParser cpParser = new CPParser();
        cpParsedData = cpParser.parse(constraintProblem);
        this.utilityProvider = utility;
        this.variableOrderer = new HeuristicVariableOrderer(cpParsedData.getConstraintGraph(), cpParsedData.getVariables());
        this.numberOfComponents = cpParsedData.getVariables().stream().map(CpVariable::getComponentId).distinct().count();
        this.variableDTOCollection = cpParsedData.getVariables().stream()
                .map(variable -> new VariableDTO(variable.getId(), variable.getComponentId(), variable.getVariableType()))
                .collect(Collectors.toList());
    }

    public void parseWithRandomOrder(ConstraintProblem constraintProblem, UtilityProvider utility) {
        CPParser cpParser = new CPParser();
        cpParsedData = cpParser.parse(constraintProblem);
        this.utilityProvider = utility;
        this.numberOfComponents = cpParsedData.getVariables().stream().map(CpVariable::getComponentId).distinct().count();
        this.variableOrderer = new RandomVariableOrderer(cpParsedData.getVariables());
        this.variableDTOCollection = cpParsedData.getVariables().stream()
                .map(variable -> new VariableDTO(variable.getId(), variable.getComponentId(), variable.getVariableType()))
                .collect(Collectors.toList());
    }

    public boolean checkIfFeasible(List<Integer> assignment) {
        return cpParsedData.checkIfFeasible(getAssignmentFromValueList(assignment));
    }

    public Domain getVariableDomain(int variableIndex) {
        return cpParsedData.getVariableDomain(variableOrderer.getNameFromIndex(variableIndex));
    }

    public int getIndexFromValue(NumericValueInterface value, int variable) {
        return DomainHandler.getValueIndex(value, getVariableDomain(variable));
    }

    public int getVariableIndexFromComponentAndType(String componentId, VariableType type) {
        return this.variableOrderer.getIndexFromComponentType(componentId, type);
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

    public List<VariableValueDTO> assignmentToVariableValueDTOList(List<Integer> assignments) {
        List<VariableValueDTO> result = new ArrayList<>();
        for (int i = 0; i < assignments.size(); i++) {
            NumericValueInterface value = getVariableValueFromDomainIndex(i, assignments.get(i));
            if (cpParsedData.getVariableType(variableOrderer.getNameFromIndex(i)) == VariableNumericType.INT) {
                if (!(value.isInteger())) {
                    throw new RuntimeException("");
                }
                result.add(VariableValueDTOFactory.createElement(variableOrderer.getNameFromIndex(i), value.getIntValue()));
            } else {
                if (!(value instanceof DoubleValue)) {
                    throw new RuntimeException("Variable " + variableOrderer.getNameFromIndex(i) +" is not of double type!");
                }
                result.add( VariableValueDTOFactory.createElement(variableOrderer.getNameFromIndex(i), value.getDoubleValue()));
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

    private NumericValueInterface getVariableValueFromDomainIndex(int varIndex, int value) {
        Domain domain = cpParsedData.getVariableDomain(variableOrderer.getNameFromIndex(varIndex));
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
}
