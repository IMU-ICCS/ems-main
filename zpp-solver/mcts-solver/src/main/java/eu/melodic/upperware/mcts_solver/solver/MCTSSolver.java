package eu.melodic.upperware.mcts_solver.solver;

import cp_wrapper.CPWrapper;
import cp_wrapper.utility_provider.UtilityProvider;
import eu.melodic.upperware.mcts_solver.solver.mcts_cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.utility.Solution;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MCTSSolver {
    @Setter
    private double heuristicCoefficient;
    @Getter
    private Solution solution;

    public List<VariableValueDTO> run(ConstraintProblem cp, UtilityProvider utility) {
        CPWrapper cpWrapper = new CPWrapper();
        cpWrapper.parse(cp, utility);
        MCTSWrapper mctsWrapper = new MCTSWrapper(cpWrapper);

        return cpWrapper.assignmentToVariableValueDTOList(run(mctsWrapper));
    }

    private List<Integer> run(MCTSWrapper mctsWrapper) {
        //TODO
        return null;
    }
}
