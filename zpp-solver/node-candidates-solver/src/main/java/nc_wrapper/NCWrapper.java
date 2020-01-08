package nc_wrapper;

import cp_components.PTEvaluation;
import cp_components.PTSolution;
import cp_wrapper.UtilityProvider;
import cp_wrapper.parser.CPParsedData;
import cp_wrapper.parser.CPParser;
import cp_wrapper.utils.DomainHandler;
import cp_wrapper.utils.VariableNumericType;
import cp_wrapper.utils.numeric_value_impl.*;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTOFactory;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpVariable;
import eu.paasage.upperware.metamodel.cp.VariableType;
import node_candidate.GeographicCoordinate;
import node_candidate.NodeCandidatesPool;
import node_candidate.VMConfiguration;
import org.jamesframework.core.problems.objectives.evaluations.Evaluation;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import variable_orderer.ComponentVariableOrderer;

import java.util.*;
/*
    This class provides necessary services related to constraint problem structure:
    - checking feasibility
    - generating random solutions
    - calculating utility
    Unlike pt-solver.ptcp_wrapper.PTCPWrapper, it does not use CPWrapper class but
    directly interacts with CPParsedData. The main task of CPWrapper - abstracting values of variables to
    ranges of subsequent integers- is of no use here since we are dealing directly with node candidates.
 */
public class NCWrapper implements DomainProvider {
    private CPParsedData cpParsedData;
    private ComponentVariableOrderer variableOrderer;
    private NodeCandidatesPool candidatesPool;
    UtilityProvider utilityProvider;

    /*
        component, variable type -----> variable name
     */
    private Map<Pair<Integer, VariableType>, String> componentTypeToName;

    public NCWrapper(ConstraintProblem cp, UtilityProvider utilityProvider) {
        CPParser cpParser = new CPParser();
        this.cpParsedData = cpParser.parse(cp);
        this.variableOrderer = new ComponentVariableOrderer(cp);
        this.utilityProvider = utilityProvider;
        fillComponentTypeToName(cp);
    }

    public void setNodeCandidatesPool(NodeCandidatesPool candidatesPool) {
        this.candidatesPool = candidatesPool;
    }

    private void fillComponentTypeToName(ConstraintProblem cp) {
        componentTypeToName = new HashMap<>();
        List<String> components = variableOrderer.getComponents();
        for (int i = 0; i <components.size(); i++) {
            for (CpVariable var : cp.getCpVariables()) {
                if (var.getComponentId().equals(components.get(i))) {
                   componentTypeToName.put(new Pair(i, var.getVariableType()), var.getId());
                }
            }
        }
    }
    public List<String> getComponents() {
        return variableOrderer.getComponents();
    }

    private Map<String, NumericValue> convertAssignment(Map<Integer, Quartet<Integer, VMConfiguration, GeographicCoordinate, Integer>> assignment) {
        Map<String, NumericValue> res = new HashMap<>();
        for (Integer comp : assignment.keySet()) {
            res.put(componentTypeToName.get(new Pair(comp, VariableType.PROVIDER)),
                    new IntegerValue((Integer) assignment.get(comp).getValue(0)));
            res.put(componentTypeToName.get(new Pair(comp, VariableType.CORES)),
                    new LongValue(((VMConfiguration) assignment.get(comp).getValue(1)).getCores()));
            res.put(componentTypeToName.get(new Pair(comp, VariableType.RAM)),
                    new LongValue(((VMConfiguration) assignment.get(comp).getValue(1)).getRam()));
            res.put(componentTypeToName.get(new Pair(comp, VariableType.STORAGE)),
                    new DoubleValue(((VMConfiguration) assignment.get(comp).getValue(1)).getDisk()));
            res.put(componentTypeToName.get(new Pair(comp, VariableType.LATITUDE)),
                    new DoubleValue(((GeographicCoordinate) assignment.get(comp).getValue(2)).getLatitude()));
            res.put(componentTypeToName.get(new Pair(comp, VariableType.LONGITUDE)),
                    new DoubleValue(((GeographicCoordinate) assignment.get(comp).getValue(2)).getLongitude()));
            res.put(componentTypeToName.get(new Pair(comp, VariableType.CARDINALITY)),
                    new IntegerValue(((Integer) assignment.get(comp).getValue(3))));
        }
        return res;
    }

    private List<VariableValueDTO> assignmentToVariableValueDTOList(Map<String, NumericValue> assignments) {
        List<VariableValueDTO> result = new ArrayList<>();
        for (Map.Entry<String, NumericValue> a : assignments.entrySet()) {
            if (a.getKey() == null) continue;
            NumericValue val = a.getValue();
            if (cpParsedData.getVariableType(a.getKey()) == VariableNumericType.INT) {
                if (!(val instanceof IntValueInterface)) {
                    throw new RuntimeException("Variable " + a.getKey() + " is not of integer type!");
                }
                result.add(VariableValueDTOFactory.createElement(a.getKey(), ((IntValueInterface) val).getIntValue()));
            } else {
                if (!(val instanceof DoubleValue)) {
                    throw new RuntimeException("Variable " + a.getKey() +" is not of double type!");
                }
                result.add(VariableValueDTOFactory.createElement(a.getKey(), ((DoubleValue) val).getValue()));
            }
        }
        return result;
    }

    private double getUtility(Map<String, NumericValue> assignments) {
        List<VariableValueDTO> vars = assignmentToVariableValueDTOList(assignments);
        return utilityProvider.evaluate(vars);
    }

    public Evaluation evaluate(Map<Integer, Quartet<Integer, VMConfiguration, GeographicCoordinate, Integer>> assignments) {
        Map<String, NumericValue> a = convertAssignment(assignments);
        if (cpParsedData.checkIfFeasible(a)) {
            return new PTEvaluation(getUtility(a));
        } else {
            return new PTEvaluation(0);
        }
    }
    /*
        Returns maximal value of variable @variable
     */
    @Override
    public NumericValue getMaxValue(int variable) {
        if (!variableOrderer.exists(variable)) {
            return new IntegerValue(0);
        }
        return DomainHandler.getMaxValue(cpParsedData.getVariableDomain(variableOrderer.getNameFromIndex(variable)));
    }

    @Override
    public NumericValue getMinValue(int variable) {
        if (!variableOrderer.exists(variable)) {
            return new IntegerValue(0);
        }
        return DomainHandler.getMinValue(cpParsedData.getVariableDomain(variableOrderer.getNameFromIndex(variable)));
    }

    @Override
    public boolean isInDomain(NumericValue value, int index) {
        if (!variableOrderer.exists(index)) {
            return true;
        } else {
            return DomainHandler.isInDomain(value, cpParsedData.getVariableDomain(variableOrderer.getNameFromIndex(index)));
        }
    }

    /*
        Generates random solution to the constraint problem.
        Used to sample starting point for parallel tempering.
     */
    public PTSolution generateRandom(Random random) {
        return candidatesPool.generateRandom(random);
    }
}
