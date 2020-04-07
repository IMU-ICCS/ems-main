package eu.melodic.upperware.nc_solver.nc_solver.nc_wrapper;

import eu.melodic.upperware.nc_solver.nc_solver.cp_components.PTEvaluation;
import eu.melodic.upperware.nc_solver.nc_solver.cp_components.PTSolution;
import cp_wrapper.utility_provider.UtilityProvider;
import cp_wrapper.parser.CPParsedData;
import cp_wrapper.parser.CPParser;
import cp_wrapper.utils.domain_handler.DomainHandler;
import cp_wrapper.utils.cp_variable.VariableNumericType;
import cp_wrapper.utils.numeric_value.*;
import cp_wrapper.utils.numeric_value.implementations.DoubleValue;
import cp_wrapper.utils.numeric_value.implementations.IntegerValue;
import cp_wrapper.utils.numeric_value.implementations.LongValue;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTOFactory;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpVariable;
import eu.paasage.upperware.metamodel.cp.VariableType;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.GeographicCoordinate;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.NodeCandidatesPool;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.IntegerNodeCandidateElementImpl;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.NodeCandidateElementInterface;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.VMConfiguration;
import org.jamesframework.core.problems.objectives.evaluations.Evaluation;
import org.javatuples.Pair;
import eu.melodic.upperware.nc_solver.nc_solver.variable_orderer.ComponentVariableOrderer;

import java.util.*;
/*
    This class provides necessary services related to constraint problem structure:
    - checking feasibility
    - generating random solutions
    - calculating utility
    Unlike pt-solver.eu.melodic.upperware.pt_solver.pt_solver.ptcp_wrapper.PTCPWrapper, it does not use CPWrapper class but
    directly interacts with CPParsedData. The main task of CPWrapper - abstracting values of variables to
    ranges of subsequent integers- is of no use here since we are dealing directly with node candidates.
 */
public class NCWrapper implements DomainProvider {
    private CPParsedData cpParsedData;
    private ComponentVariableOrderer variableOrderer;
    private NodeCandidatesPool candidatesPool;
    private UtilityProvider utilityProvider;

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

    /*
       Returns maximal value of variable @variable
    */
    @Override
    public NumericValueInterface getMaxValue(int variable) {
        if (!variableOrderer.exists(variable)) {
            return new IntegerValue(0);
        }
        return DomainHandler.getMaxValue(cpParsedData.getVariableDomain(variableOrderer.getNameFromIndex(variable)));
    }

    @Override
    public NumericValueInterface getMinValue(int variable) {
        if (!variableOrderer.exists(variable)) {
            return new IntegerValue(0);
        }
        return DomainHandler.getMinValue(cpParsedData.getVariableDomain(variableOrderer.getNameFromIndex(variable)));
    }

    @Override
    public boolean isInDomain(NumericValueInterface value, int index) {
        return !variableOrderer.exists(index) ||
                DomainHandler.isInDomain(value, cpParsedData.getVariableDomain(variableOrderer.getNameFromIndex(index)));
    }

    public List<VariableValueDTO> covertSolutionToVariableValueDTO(PTSolution solution) {
        return assignmentToVariableValueDTOList(convertAssignment(solution.getVarAssignments()));
    }

    public void setNodeCandidatesPool(NodeCandidatesPool candidatesPool) {
        this.candidatesPool = candidatesPool;
    }

    public List<String> getComponents() {
        return variableOrderer.getComponents();
    }

    public Evaluation evaluate(Map<Integer, Map<Integer, NodeCandidateElementInterface>> assignments) {
        Map<String, NumericValueInterface> numericAssignment = convertAssignment(assignments);
        if (cpParsedData.checkIfFeasible(numericAssignment)) {
            return new PTEvaluation(getUtility(numericAssignment));
        } else {
            return new PTEvaluation(0);
        }
    }

    private double getUtility(Map<String, NumericValueInterface> assignments) {
        List<VariableValueDTO> vars = assignmentToVariableValueDTOList(assignments);
        return utilityProvider.evaluate(vars);
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

    /*
        Generates random solution to the constraint problem.
        Used to sample starting point for parallel tempering.
     */
    public PTSolution generateRandom(Random random) {
        return candidatesPool.generateRandom(random);
    }

    private Map<String, NumericValueInterface> convertAssignment(Map<Integer, Map<Integer, NodeCandidateElementInterface>> assignment) {
        Map<String, NumericValueInterface> res = new HashMap<>();
        for (Integer comp : assignment.keySet()) {
            res.put(componentTypeToName.get(new Pair(comp, VariableType.PROVIDER)),
                    new IntegerValue(
                            ((IntegerNodeCandidateElementImpl) assignment.get(comp).get(PTSolution.PROVIDER_INDEX)).getValue()));
            res.put(componentTypeToName.get(new Pair(comp, VariableType.CORES)),
                    new LongValue(((VMConfiguration) assignment.get(comp).get(PTSolution.CONFIGURATION_INDEX)).getCores()));
            res.put(componentTypeToName.get(new Pair(comp, VariableType.RAM)),
                    new LongValue(((VMConfiguration) assignment.get(comp).get(PTSolution.CONFIGURATION_INDEX)).getRam()));
            res.put(componentTypeToName.get(new Pair(comp, VariableType.STORAGE)),
                    new LongValue(((VMConfiguration) assignment.get(comp).get(PTSolution.CONFIGURATION_INDEX)).getDisk()));
            res.put(componentTypeToName.get(new Pair(comp, VariableType.LATITUDE)),
                    new LongValue(((GeographicCoordinate) assignment.get(comp).get(PTSolution.LOCATION_INDEX)).getLatitude()));
            res.put(componentTypeToName.get(new Pair(comp, VariableType.LONGITUDE)),
                    new LongValue(((GeographicCoordinate) assignment.get(comp).get(PTSolution.LOCATION_INDEX)).getLongitude()));
            res.put(componentTypeToName.get(new Pair(comp, VariableType.CARDINALITY)),
                    new LongValue(
                            ((IntegerNodeCandidateElementImpl) assignment.get(comp).get(PTSolution.CARDINALITY_INDEX)).getValue()));
        }
        return res;
    }

    private List<VariableValueDTO> assignmentToVariableValueDTOList(Map<String, NumericValueInterface> assignments) {
        List<VariableValueDTO> result = new ArrayList<>();
        for (Map.Entry<String, NumericValueInterface> a : assignments.entrySet()) {
            if (a.getKey() == null) continue;
            NumericValueInterface val = a.getValue();
            if (cpParsedData.getVariableType(a.getKey()) == VariableNumericType.INT) {
                if (!(val.isInteger())) {
                    throw new RuntimeException("Variable " + a.getKey() + " is not of integer type!");
                }
                result.add(VariableValueDTOFactory.createElement(a.getKey(), val.getIntValue()));
            } else {
                if (!(val instanceof DoubleValue)) {
                    throw new RuntimeException("Variable " + a.getKey() +" is not of double type!");
                }
                result.add(VariableValueDTOFactory.createElement(a.getKey(), val.getDoubleValue()));
            }
        }
        return result;
    }
}
