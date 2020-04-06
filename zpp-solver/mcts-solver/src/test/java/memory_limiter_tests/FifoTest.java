package memory_limiter_tests;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.Fifo;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.NodeImpl;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.memory_management.FifoImpl;
import org.junit.Test;

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

        assertSame(node3.getNext(), node2);
        assertSame(node2.getNext(), node1);
        assertSame(node1.getNext(), null);

        assertSame(node3.getPrevious(), null);
        assertSame(node2.getPrevious(), node3);
        assertSame(node1.getPrevious(), node2);

        assertFalse(fifo.empty());
        assertEquals(fifo.popFront(), node3);
        assertFalse(fifo.empty());
        assertEquals(fifo.popFront(), node2);
        assertFalse(fifo.empty());
        assertEquals(fifo.popFront(), node1);
        assertTrue(fifo.empty());

    }
}
