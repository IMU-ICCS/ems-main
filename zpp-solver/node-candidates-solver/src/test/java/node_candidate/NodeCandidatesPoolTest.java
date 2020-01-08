package node_candidate;

import cp_components.PTMover;
import cp_components.PTSolution;
import cp_wrapper.utils.numeric_value_impl.IntegerValue;
import cp_wrapper.utils.numeric_value_impl.NumericValue;
import nc_wrapper.DomainProvider;
import org.javatuples.Quartet;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NodeCandidatesPoolTest {

    public NodeCandidatesPool createPool() {
        return new NodeCandidatesPool(new DomainProvider() {

            @Override
            public NumericValue getMaxValue(int variable) {
                return new IntegerValue(2);
            }

            @Override
            public NumericValue getMinValue(int variable) {
                return new IntegerValue(0);
            }

            @Override
            public boolean isInDomain(NumericValue value, int index) {
                return true;
            }
        },
                Arrays.asList("comp1", "comp2", "comp3"));
    }

    private PTSolution arrayToAssignment(List<Integer> arr) {
        Map<Integer, Quartet<Integer, VMConfiguration, GeographicCoordinate,Integer>> a = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            int cardinality = arr.get(7*i + 6);
            int provider = arr.get(7*i);
            VMConfiguration conf = new VMConfiguration(arr.get(i*7 + 1), arr.get(i*7 +2), arr.get(i*7 +3));
            GeographicCoordinate loc = new GeographicCoordinate(arr.get(i*7+4), arr.get(i*7 + 5));
            a.put(i, new Quartet<>(provider ,conf, loc, cardinality));
        }
        return new PTSolution(a);
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
        List<PTMover> moves = pool.getAllMoves(arrayToAssignment(Arrays.asList(0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1)));
        assertEquals(moves.size(), 3*3);

        PTMover m = new PTMover(
                arrayToAssignment(Arrays.asList(0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1)),
                arrayToAssignment(Arrays.asList(0, 0, 0, 0, 100, 100, 2, 0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1))
        );

        assertEquals(moves.contains(m), true);

        m = new PTMover(
                arrayToAssignment(Arrays.asList(0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1)),
                        arrayToAssignment(Arrays.asList(0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1, 0, 0 , 2, 0, 100, 100, 1))
        );

        assertEquals(moves.contains(m), true);

        m = new PTMover(
                arrayToAssignment(Arrays.asList(0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1)),
                arrayToAssignment(Arrays.asList(0, 0, 0, 0, 100, 100, 0, 0, 0, 0, 0, 100, 100, 1, 0, 0, 0, 0, 100, 100, 1))
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
        List<PTMover> moves = pool.getAllMoves(arrayToAssignment(Arrays.asList(0, 1, 0, 0, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1)));
        assertEquals(moves.size(), 3*7);

        PTMover m = new PTMover(
                arrayToAssignment(Arrays.asList(0, 1, 0, 0, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1)),
                arrayToAssignment(Arrays.asList(0, 1, 0, 0, 100, 100, 1, 1, 0, 0, 1, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1))
        );

        assertEquals(moves.contains(m), true);

        m = new PTMover(
                arrayToAssignment(Arrays.asList(0, 1, 0, 0, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1)),
                arrayToAssignment(Arrays.asList(0, 1, 0, 0, 100, 100, 1, 1, 1, 0, 1, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1))
        );

        assertEquals(moves.contains(m), true);

        m = new PTMover(
                arrayToAssignment(Arrays.asList(0, 1, 0, 0, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1)),
                arrayToAssignment(Arrays.asList(0, 1, 0, 0, 100, 100, 1, 0, 1, 0, 0, 100, 100, 1, 0, 1, 0, 0, 100, 150, 1))
        );

        assertEquals(moves.contains(m), true);
    }
}