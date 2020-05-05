package eu.melodic.upperware.nc_solver.nc_solver.node_candidate;

import eu.melodic.upperware.cp_wrapper.utils.numeric_value.NumericValueInterface;
import eu.melodic.upperware.cp_wrapper.utils.numeric_value.implementations.IntegerValue;
import eu.melodic.upperware.nc_solver.nc_solver.cp_components.PTMover;
import eu.melodic.upperware.nc_solver.nc_solver.cp_components.PTSolution;
import eu.melodic.upperware.nc_solver.nc_solver.nc_wrapper.DomainProvider;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.GeographicCoordinate;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.IntegerNodeCandidateElementImpl;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.NodeCandidateElementInterface;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.VMConfiguration;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NodeCandidatesPoolTest {

    public NodeCandidatesPool createPool() {
        return new NodeCandidatesPool(new DomainProvider() {

            @Override
            public NumericValueInterface getMaxValue(int variable) {
                return new IntegerValue(2);
            }

            @Override
            public NumericValueInterface getMinValue(int variable) {
                return new IntegerValue(0);
            }

            @Override
            public boolean isInDomain(NumericValueInterface value, int index) {
                return true;
            }
        },
                Arrays.asList("comp1", "comp2", "comp3"));
    }

    private PTSolution arrayToAssignment(List<Integer> arr) {
        Map<Integer, Map<Integer, NodeCandidateElementInterface>> a = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            int cardinality = arr.get(7*i + 6);
            int provider = arr.get(7*i);
            VMConfiguration conf = new VMConfiguration(arr.get(i*7 + 1), arr.get(i*7 +2), arr.get(i*7 +3));
            GeographicCoordinate loc = new GeographicCoordinate(arr.get(i*7+4), arr.get(i*7 + 5));
            a.put(i, new HashMap<Integer, NodeCandidateElementInterface>(){{
                put(PTSolution.PROVIDER_INDEX, new IntegerNodeCandidateElementImpl(provider));
                put(PTSolution.CARDINALITY_INDEX, new IntegerNodeCandidateElementImpl(cardinality));
                put(PTSolution.CONFIGURATION_INDEX, conf);
                put(PTSolution.LOCATION_INDEX, loc);
            }});
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