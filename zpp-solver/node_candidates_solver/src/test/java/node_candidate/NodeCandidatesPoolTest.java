package node_candidate;

import cp_components.PTMover;
import nc_wrapper.MarginalDomainValuesProvider;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NodeCandidatesPoolTest {

    public NodeCandidatesPool createPool() {
        return new NodeCandidatesPool(new MarginalDomainValuesProvider() {
            @Override
            public int getMaxValue(int variable) {
                return 2;
            }

            @Override
            public int getMinValue(int variable) {
                return 0;
            }
        },
                Arrays.asList("comp1", "comp2", "comp3"));
    }

    @Test
    public void firstGetAllMovesTest() {
        NodeCandidatesPool pool = createPool();
        pool.postVMConfiguration(0, new VMConfiguration(0, 0, 0));
        pool.postVMConfiguration(0, new VMConfiguration(1, 0, 0));
        pool.postVMConfiguration(0, new VMConfiguration(2, 0, 0));
        pool.postVMConfiguration(0, new VMConfiguration(0, 2, 0));
        pool.postVMLocation(0, new GeographicCoordinate(100, 100));

        pool.initNodeCandidates();
        List<PTMover> moves = pool.getAllMoves(Arrays.asList(0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1));
        assertEquals(moves.size(), 3*3);

        PTMover m = new PTMover(
                Arrays.asList(0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1),
                Arrays.asList(0, 0, 0, 0, 100, 100, 2, 0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1)
        );

        assertEquals(moves.contains(m), true);

        m = new PTMover(
                Arrays.asList(0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1),
                Arrays.asList(0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1, 0, 0 , 2, 0, 100, 100, 1)
        );

        assertEquals(moves.contains(m), true);

        m = new PTMover(
                Arrays.asList(0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1),
                Arrays.asList(0, 0, 0, 0, 100, 100, 0, 0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1)
        );

        assertEquals(moves.contains(m), true);

    }

    @Test
    public void secondGetAllMovesTest() {
        NodeCandidatesPool pool = createPool();
        pool.postVMConfiguration(0, new VMConfiguration(0, 0, 0));
        pool.postVMConfiguration(0, new VMConfiguration(1, 0, 0));
        pool.postVMConfiguration(0, new VMConfiguration(2, 0, 0));
        pool.postVMLocation(0, new GeographicCoordinate(100, 100));
        pool.postVMLocation(0, new GeographicCoordinate(100, 150));

        pool.postVMConfiguration(1, new VMConfiguration(0, 0, 1));
        pool.postVMConfiguration(1, new VMConfiguration(1, 0, 1));
        pool.postVMConfiguration(1, new VMConfiguration(2, 0, 1));
        pool.postVMLocation(1, new GeographicCoordinate(100, 100));
        pool.postVMLocation(1, new GeographicCoordinate(100, 150));

        pool.initNodeCandidates();
        List<PTMover> moves = pool.getAllMoves(Arrays.asList(0, 1, 0, 0, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1));
        assertEquals(moves.size(), 3*7);

        PTMover m = new PTMover(
                Arrays.asList(0, 1, 0, 0, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1),
                Arrays.asList(0, 1, 0, 0, 100, 100, 1, 1, 0, 0, 1, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1)
        );

        assertEquals(moves.contains(m), true);

        m = new PTMover(
                Arrays.asList(0, 1, 0, 0, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1),
                Arrays.asList(0, 1, 0, 0, 100, 100, 1, 1, 1, 0, 1, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1)
        );

        assertEquals(moves.contains(m), true);

        m = new PTMover(
                Arrays.asList(0, 1, 0, 0, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1),
                Arrays.asList(0, 1, 0, 0, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1, 0, 1, 0, 0, 100, 150, 1)
        );

        assertEquals(moves.contains(m), true);
    }
}