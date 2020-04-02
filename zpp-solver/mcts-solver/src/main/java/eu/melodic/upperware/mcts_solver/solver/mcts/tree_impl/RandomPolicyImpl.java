package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Path;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Policy;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Solution;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.IntStream;

@AllArgsConstructor
public class RandomPolicyImpl implements Policy {
    private MCTSWrapper mctsWrapper;

    @Override
    public Solution finishPath(Path path) {
        List<Integer> assignment = path.getPath();

        int pathSize = assignment.size();

        IntStream.range(assignment.size(), mctsWrapper.getSize())
                .forEach(i -> assignment.add(mctsWrapper.generateRandomValue(i)));

        return new SolutionImpl(pathSize, assignment, mctsWrapper);
    }
}
