package memory_limiter_tests;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.NodeQueue;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.NodeImpl;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.memory_management.NodeQueueImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class NodeQueueTest {
    private Node node1 = new NodeImpl(1);
    private Node node2 = new NodeImpl(2);
    private Node node3 = new NodeImpl(3);

    @Test
    public void FifoTest() {
        NodeQueue nodeQueue = new NodeQueueImpl();

        assertTrue(nodeQueue.empty());
        assertNull(nodeQueue.popFront());


        nodeQueue.pushBack(node1);

        assertFalse(nodeQueue.empty());
        assertNotNull(nodeQueue.popFront());


        nodeQueue.pushBack(node1);
        nodeQueue.pushBack(node2);

        assertFalse(nodeQueue.empty());
        assertEquals(nodeQueue.popFront(), node1);
        assertFalse(nodeQueue.empty());
        assertEquals(nodeQueue.popFront(), node2);
        assertTrue(nodeQueue.empty());


        nodeQueue.pushBack(node1);
        nodeQueue.pushBack(node2);
        nodeQueue.pushBack(node1);

        assertFalse(nodeQueue.empty());
        assertEquals(nodeQueue.popFront(), node2);
        assertFalse(nodeQueue.empty());
        assertEquals(nodeQueue.popFront(), node1);
        assertTrue(nodeQueue.empty());


        nodeQueue.pushBack(node1);
        nodeQueue.pushBack(node1);

        assertFalse(nodeQueue.empty());
        assertEquals(nodeQueue.popFront(), node1);
        assertTrue(nodeQueue.empty());


        nodeQueue.pushBack(node1);
        nodeQueue.pushBack(node2);
        nodeQueue.pushBack(node3);
        nodeQueue.pushBack(node1);
        nodeQueue.pushBack(node2);
        nodeQueue.pushBack(node3);
        nodeQueue.pushBack(node3);
        nodeQueue.pushBack(node3);

        assertFalse(nodeQueue.empty());
        assertEquals(nodeQueue.popFront(), node1);
        assertFalse(nodeQueue.empty());
        assertEquals(nodeQueue.popFront(), node2);
        assertFalse(nodeQueue.empty());
        assertEquals(nodeQueue.popFront(), node3);
        assertTrue(nodeQueue.empty());


        nodeQueue.pushBack(node1);
        nodeQueue.pushBack(node2);
        nodeQueue.pushBack(node3);
        nodeQueue.pushBack(node2);
        nodeQueue.pushBack(node1);

        assertSame(node3.getQueueLinker().getNext(), node2);
        assertSame(node2.getQueueLinker().getNext(), node1);
        assertSame(node1.getQueueLinker().getNext(), null);

        assertSame(node3.getQueueLinker().getPrevious(), null);
        assertSame(node2.getQueueLinker().getPrevious(), node3);
        assertSame(node1.getQueueLinker().getPrevious(), node2);

        assertFalse(nodeQueue.empty());
        assertEquals(nodeQueue.popFront(), node3);
        assertFalse(nodeQueue.empty());
        assertEquals(nodeQueue.popFront(), node2);
        assertFalse(nodeQueue.empty());
        assertEquals(nodeQueue.popFront(), node1);
        assertTrue(nodeQueue.empty());

        nodeQueue.pushBack(node1);
        nodeQueue.pushBack(node2);
        nodeQueue.pushBack(node3);
        nodeQueue.pushBack(node1);

        assertFalse(nodeQueue.empty());
        assertEquals(nodeQueue.popFront(), node2);

    }

    @Test
    public void randomTest() {
        List<Node> nodes = new ArrayList<>();
        int NODE_NUMBER = 100, TEST_SIZE = 1000000;
        for (int i = 0; i < NODE_NUMBER; i++) {
            nodes.add(new NodeImpl(i));
        }
        List<Node> pseudo_queue = new ArrayList<>();

        NodeQueue nodeQueue = new NodeQueueImpl();
        Random random = new Random();

        for (int i = 0; i < TEST_SIZE; i++) {
            int which = random.nextInt(NODE_NUMBER);
            nodeQueue.pushBack(nodes.get(which));
            pseudo_queue.remove(nodes.get(which));
            pseudo_queue.add(nodes.get(which));
        }

        for (int i = 0; i < NODE_NUMBER; i++) {
            assertSame(nodeQueue.popFront(), pseudo_queue.get(i));
        }
    }
}
