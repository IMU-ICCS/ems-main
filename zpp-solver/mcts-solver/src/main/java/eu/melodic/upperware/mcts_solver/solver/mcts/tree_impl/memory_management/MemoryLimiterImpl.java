package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.memory_management;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.Fifo;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.MemoryLimiter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemoryLimiterImpl implements MemoryLimiter {
    private int limit;
    @Getter
    private int count;
    private Fifo accessFifo = new FifoImpl();

    public MemoryLimiterImpl(int limit) {
        this.limit = limit;
    }

    @Override
    public void addCount(int count) {
        this.count += count; // Can exceed limit. Should be pruned in a short time.
    }

    @Override
    public void prune() {
        while (count > limit && !accessFifo.empty()) {
            pruneLeastVisited();
        }
    }

    @Override
    public void updateAccessed(Node node) {
        accessFifo.pushBack(node);
    }

    @Override
    public boolean fifoFrontIsCorrect() {
        boolean ok = true;
        Node front = accessFifo.getFront();
        if (front == null)
            return true;

        for (Node child : front.getChildren()) {
            if (child.getChildren().size() > 0) {
                ok = false;
            }
        }
        return ok;
    }

    @Override
    public Node getFront() {
        return accessFifo.getFront();
    }

    private Node pruneLeastVisited() {
        Node toPrune = accessFifo.popFront();

        pruneSubTree(toPrune);
        return toPrune;
    }

    // SubRoot's children should be all leaves.
    private void pruneSubTree(Node subRoot) {
        if (subRoot.getParent() == null) { // If is a root then do nothing.
            return;
        }

        boolean allLeaf = true;
        for (Node potentialLeaf : subRoot.getChildren()) {
            if (potentialLeaf.getChildren().size() > 0) {
                allLeaf = false;
            }
        }

        //assert(allLeaf);
        if (!allLeaf) {
            System.out.println(subRoot);
            System.out.println("Number of children is " + subRoot.getChildren().size());
            System.out.println("Printing children");
            for (Node child : subRoot.getChildren()) {
                System.out.println(child);
                System.out.println("Number of children is " + child.getChildren().size());

            }
            System.out.println(accessFifo.size());
            accessFifo.printFifo();
        }

        for (Node child : subRoot.getChildren()) {
            if (child.getChildren().size() > 0) {
                log.info("SHOULDN't HAPPEn");
                pruneSubTree(child);
            }
        }

        count -= subRoot.getChildren().size();
        subRoot.getChildren().clear();

        subRoot.setDeExpanded();
        //subRoot.setTrimmed();
    }
}
