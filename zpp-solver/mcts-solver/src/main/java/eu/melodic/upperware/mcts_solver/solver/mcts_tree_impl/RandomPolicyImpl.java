package eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts_cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Path;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Policy;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Solution;

import java.util.ArrayList;
import java.util.List;

public class RandomPolicyImpl implements Policy {
    private MCTSWrapper mctsWrapper;

    @Override
    public Solution finishPath(Path path) {
        List<Integer> assignment = new ArrayList<>();
        List<Node> nodes = path.getPath();
        for (Node node : nodes) {
            assignment.add(node.getValue());
        }

        for (int i = assignment.size(); i <= mctsWrapper.getSize(); i++) {
            assignment.add(mctsWrapper.generateRandomValue(i));
        }

        return new SolutionImpl(nodes.size(), assignment, mctsWrapper);
    }
}
