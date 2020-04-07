package memory_limiter_tests;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.Fifo;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.NodeImpl;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.memory_management.FifoImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class FifoTest {
    private Node node1 = new NodeImpl(1);
    private Node node2 = new NodeImpl(2);
    private Node node3 = new NodeImpl(3);

    @Test
    public void FifoTest() {
        Fifo fifo = new FifoImpl();

        assertTrue(fifo.empty());
        assertNull(fifo.popFront());


        fifo.pushBack(node1);

        assertFalse(fifo.empty());
        assertNotNull(fifo.popFront());


        fifo.pushBack(node1);
        fifo.pushBack(node2);

        assertFalse(fifo.empty());
        assertEquals(fifo.popFront(), node1);
        assertFalse(fifo.empty());
        assertEquals(fifo.popFront(), node2);
        assertTrue(fifo.empty());


        fifo.pushBack(node1);
        fifo.pushBack(node2);
        fifo.pushBack(node1);

        assertFalse(fifo.empty());
        assertEquals(fifo.popFront(), node2);
        assertFalse(fifo.empty());
        assertEquals(fifo.popFront(), node1);
        assertTrue(fifo.empty());


        fifo.pushBack(node1);
        fifo.pushBack(node1);

        assertFalse(fifo.empty());
        assertEquals(fifo.popFront(), node1);
        assertTrue(fifo.empty());


        fifo.pushBack(node1);
        fifo.pushBack(node2);
        fifo.pushBack(node3);
        fifo.pushBack(node1);
        fifo.pushBack(node2);
        fifo.pushBack(node3);
        fifo.pushBack(node3);
        fifo.pushBack(node3);

        assertFalse(fifo.empty());
        assertEquals(fifo.popFront(), node1);
        assertFalse(fifo.empty());
        assertEquals(fifo.popFront(), node2);
        assertFalse(fifo.empty());
        assertEquals(fifo.popFront(), node3);
        assertTrue(fifo.empty());


        fifo.pushBack(node1);
        fifo.pushBack(node2);
        fifo.pushBack(node3);
        fifo.pushBack(node2);
        fifo.pushBack(node1);

        assertSame(node3.getFifoNodeLinker().getNext(), node2);
        assertSame(node2.getFifoNodeLinker().getNext(), node1);
        assertSame(node1.getFifoNodeLinker().getNext(), null);

        assertSame(node3.getFifoNodeLinker().getPrevious(), null);
        assertSame(node2.getFifoNodeLinker().getPrevious(), node3);
        assertSame(node1.getFifoNodeLinker().getPrevious(), node2);

        assertFalse(fifo.empty());
        assertEquals(fifo.popFront(), node3);
        assertFalse(fifo.empty());
        assertEquals(fifo.popFront(), node2);
        assertFalse(fifo.empty());
        assertEquals(fifo.popFront(), node1);
        assertTrue(fifo.empty());

        fifo.pushBack(node1);
        fifo.pushBack(node2);
        fifo.pushBack(node3);
        fifo.pushBack(node1);

        assertFalse(fifo.empty());
        assertEquals(fifo.popFront(), node2);

    }

    @Test
    public void randomTest() {
        List<Node> nodes = new ArrayList<>();
        int NODE_NUMBER = 1000, TEST_SIZE = 1000000;
        for (int i = 0; i < NODE_NUMBER; i++) {
            nodes.add(new NodeImpl(i));
        }
        List<Node> pseudo_queue = new ArrayList<>();

        Fifo fifo = new FifoImpl();
        Random random = new Random();

        for (int i = 0; i < TEST_SIZE; i++) {
            int which = random.nextInt(NODE_NUMBER);
            fifo.pushBack(nodes.get(which));
            pseudo_queue.remove(nodes.get(which));
            pseudo_queue.add(nodes.get(which));
        }

        for (int i = 0; i < NODE_NUMBER; i++) {
            assertSame(fifo.popFront(), pseudo_queue.get(i));
        }
    }
}
