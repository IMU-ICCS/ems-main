package eu.melodic.upperware.mcts_solver.solver.mcts_tree;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Path {
    @Getter
    List<Node> path;

    public Path() {
        path = new ArrayList<>();
    }

    public void add(Node node) {
        path.add(node);
    }

    public Node get(int i) {
        return path.get(i);
    }
}
