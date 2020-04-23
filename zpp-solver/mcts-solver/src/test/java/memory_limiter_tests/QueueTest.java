package memory_limiter_tests;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.NodeImpl;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.Queue;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class QueueTest {
    private NodeImpl node1 = new NodeImpl(1);
    private NodeImpl node2 = new NodeImpl(2);
    private NodeImpl node3 = new NodeImpl(3);

    @Test
    public void QueueTest() {
        Queue queue = new Queue();

        assertTrue(queue.empty());

        queue.pushBack(node1);

        assertFalse(queue.empty());
        assertNotNull(queue.getFront());
        queue.removeNodeFromQueue(queue.getFront());


        queue.pushBack(node1);
        queue.pushBack(node2);

        assertFalse(queue.empty());
        assertEquals(queue.getFront(), node1);
        queue.removeNodeFromQueue(queue.getFront());
        assertFalse(queue.empty());
        assertEquals(queue.getFront(), node2);
        queue.removeNodeFromQueue(queue.getFront());
        assertTrue(queue.empty());


        queue.pushBack(node1);
        queue.pushBack(node2);
        queue.pushBack(node1);

        assertFalse(queue.empty());
        assertEquals(queue.getFront(), node2);
        queue.removeNodeFromQueue(queue.getFront());
        assertFalse(queue.empty());
        assertEquals(queue.getFront(), node1);
        queue.removeNodeFromQueue(queue.getFront());
        assertTrue(queue.empty());


        queue.pushBack(node1);
        queue.pushBack(node1);

        assertFalse(queue.empty());
        assertEquals(queue.getFront(), node1);
        queue.removeNodeFromQueue(queue.getFront());
        assertTrue(queue.empty());


        queue.pushBack(node1);
        queue.pushBack(node2);
        queue.pushBack(node3);
        queue.pushBack(node1);
        queue.pushBack(node2);
        queue.pushBack(node3);
        queue.pushBack(node3);
        queue.pushBack(node3);

        assertFalse(queue.empty());
        assertEquals(queue.getFront(), node1);
        queue.removeNodeFromQueue(queue.getFront());
        assertFalse(queue.empty());
        assertEquals(queue.getFront(), node2);
        queue.removeNodeFromQueue(queue.getFront());
        assertFalse(queue.empty());
        assertEquals(queue.getFront(), node3);
        queue.removeNodeFromQueue(queue.getFront());
        assertTrue(queue.empty());


        queue.pushBack(node1);
        queue.pushBack(node2);
        queue.pushBack(node3);
        queue.pushBack(node2);
        queue.pushBack(node1);

        assertFalse(queue.empty());
        assertEquals(queue.getFront(), node3);
        queue.removeNodeFromQueue(queue.getFront());
        assertFalse(queue.empty());
        assertEquals(queue.getFront(), node2);
        queue.removeNodeFromQueue(queue.getFront());
        assertFalse(queue.empty());
        assertEquals(queue.getFront(), node1);
        queue.removeNodeFromQueue(queue.getFront());
        assertTrue(queue.empty());

        queue.pushBack(node1);
        queue.pushBack(node2);
        queue.pushBack(node3);
        queue.pushBack(node1);

        assertFalse(queue.empty());
        assertEquals(queue.getFront(), node2);
        queue.removeNodeFromQueue(queue.getFront());
    }

    @Test
    public void randomTest() {
        List<Node> nodes = new ArrayList<>();
        int NODE_NUMBER = 100, TEST_SIZE = 1000000;
        for (int i = 0; i < NODE_NUMBER; i++) {
            nodes.add(new NodeImpl(i));
        }
        List<Node> pseudo_queue = new ArrayList<>();

        Queue queue = new Queue();
        Random random = new Random();

        for (int i = 0; i < TEST_SIZE; i++) {
            int which = random.nextInt(NODE_NUMBER);
            queue.pushBack(nodes.get(which));
            pseudo_queue.remove(nodes.get(which));
            pseudo_queue.add(nodes.get(which));
        }

        for (int i = 0; i < NODE_NUMBER; i++) {
            assertSame(queue.getFront(), pseudo_queue.get(i));
            queue.removeNodeFromQueue(queue.getFront());
        }
    }
}
