package eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts_cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.MoveProvider;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Path;
import lombok.AllArgsConstructor;
import org.javatuples.Pair;

@AllArgsConstructor
public class MoveProviderImpl implements MoveProvider {
    private MCTSWrapper mctsWrapper;

    @Override
    public Pair<Node, Path> searchAndExpand(Node root) {
        Node current = root;
        Path path;

        current.visit();

        Pair<Node, Path> traversingResult = traverse(current);
        current = traversingResult.getValue0();
        path = traversingResult.getValue1();

        expand(current);

        return new Pair<>(current, path);
    }

    private Pair<Node, Path> traverse(Node current) {
        int depth = 0;
        Path path = new Path();

        // While has all available children.
        while (depth < mctsWrapper.getSize() && current.childrenSize() == mctsWrapper.domainSize(depth)) {

            // Selecting best child.
            current = current.getBestChild();

            depth++;
            current.visit();
            path.add(current);
        }
        return new Pair<>(current, path);
    }

    private void expand(Node toExpand) {
        int depth = toExpand.getNodeStatistics().getDepth();
        if (depth >= mctsWrapper.getSize()) {
            return;
        }

        for (int i = mctsWrapper.getMinDomainValue(depth); i <= mctsWrapper.getMaxDomainValue(depth); i++) {
            Node newNode = new NodeImpl(i);
            newNode.linkToTree(toExpand);
        }
    }

}
