package CPWrapper;

import CPWrapper.Parser.CPParsedData;
import CPWrapper.Parser.CPParser;
import CPWrapper.Utils.DomainHandler;
import CPWrapper.Utils.HeuristicVariableOrderer;
import CPWrapper.Utils.VariableOrderer;
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

    public boolean isFeasible(List<Integer> assignment) {
        return cpParsedData.isFeasible(getAssignmentFromValueList(assignment));
    }

    public Domain getVariableDomain(int variableIndex) {
        return cpParsedData.getVariableDomain(variableOrderer.indexToVariableName(variableIndex));
    }

    private double getVariableValueFromDomainIndex(int varIndex, int value) {
            String variableName = variableOrderer.indexToVariableName(varIndex);
            Domain domain = cpParsedData.getVariableDomain(variableName);
            if (DomainHandler.isRangeDomain(domain)) {
                return DomainHandler.getRangeValue(value, (RangeDomain) domain);
            } else if (DomainHandler.isNumericListDomain(domain)) {
                return DomainHandler.getNumericListValue(value, (NumericListDomain) domain);
            }

            throw new RuntimeException("Only domaind of types RangeDomain, NumericListDomain are supported!");
    }

    private Map<String, Double> getAssignmentFromValueList(List<Integer> assignments) {
        Map<String, Double> vars = new HashMap<>();
        for (int i = 0; i < assignments.size(); i++) {
            vars.put(variableOrderer.indexToVariableName(i), getVariableValueFromDomainIndex(i, assignments.get(i)));
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

        return cpParsedData.getHeuristicEvaluation(variableOrderer.indexToVariableName(variableIndex),
                                                    getAssignmentFromValueList(assignments));
    }

    private List<VariableValueDTO> assignmentToVariableValueDTOList(List<Integer> assignments) {
        //TODO
        //VariableValueDTOFactory.createElement()
        return new ArrayList<>();
    }

    public double getUtility(List<Integer> assignments) {
        List<VariableValueDTO> vars = assignmentToVariableValueDTOList(assignments);
        return this.utilityProvider.evaluate(vars);
    }
}
