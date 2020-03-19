package eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts_cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.MoveProvider;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Path;
import org.javatuples.Pair;

import java.util.Comparator;
import java.util.List;

import static java.util.Collections.max;

public class MoveProviderImpl implements MoveProvider {
    private MCTSWrapper mctsWrapper;

    public MoveProviderImpl(MCTSWrapper mctsWrapper) {
        super();

        this.mctsWrapper = mctsWrapper;
    }

    private void expand(Node toExpand) {
        int depth = toExpand.getNodeStatistics().getDepth();
        if (depth >= mctsWrapper.getSize()) {
            return;
        }
        if (toExpand.childrenSize() == mctsWrapper.domainSize(depth)) {
            return;
        }

        for (int i = mctsWrapper.getMinDomainValue(depth); i <= mctsWrapper.getMaxDomainValue(depth); i++) {
            Node newNode = new NodeImpl(i);
            newNode.linkToTree(toExpand);
        }
    }

    @Override
    public Pair<Node, Path> searchAndExpand(Node root) {
        Node current = root;
        int depth = 0;
        Path path = new Path();

        current.visit();

        // While has all available children.
        while (depth < mctsWrapper.getSize() && current.childrenSize() == mctsWrapper.domainSize(depth)) {
            List<Node> children = current.getChildren();
            Comparator<Node> comparator = new NodeComparator(current);

            // Selecting best child.
            current = max(children, comparator);

            depth++;
            current.visit();
            path.add(current);
        }
        expand(current);

        return new Pair<>(current, path);
    }
}
