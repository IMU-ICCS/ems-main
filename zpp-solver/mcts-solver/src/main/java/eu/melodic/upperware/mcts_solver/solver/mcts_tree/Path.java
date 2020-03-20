package eu.melodic.upperware.mcts_solver.solver.mcts_tree;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

// Represents assignment chosen from a path that begins in a root Node.
public class Path {
    @Getter
    private List<Integer> path = new ArrayList<>();

    public void add(Node node) {
        path.add(node.getValue());
    }
}
