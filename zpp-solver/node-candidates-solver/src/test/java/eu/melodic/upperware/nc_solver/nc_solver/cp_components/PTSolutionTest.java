package eu.melodic.upperware.nc_solver.nc_solver.cp_components;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.GeographicCoordinate;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.IntegerNodeCandidateElementImpl;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.NodeCandidateElementInterface;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.VMConfiguration;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PTSolutionTest {

    @Test
    public void equalsTest() {
        Map<Integer, Map<Integer, NodeCandidateElementInterface>> map1 = new HashMap();
        map1.put(1, new HashMap<Integer, NodeCandidateElementInterface>(){{
            put(PTSolution.PROVIDER_INDEX,new IntegerNodeCandidateElementImpl(0));
            put(PTSolution.CARDINALITY_INDEX, new IntegerNodeCandidateElementImpl(1));
            put(PTSolution.CONFIGURATION_INDEX, new VMConfiguration(0,1,1));
            put(PTSolution.LOCATION_INDEX, new GeographicCoordinate(100, 150));
        }});

        Map<Integer, Map<Integer, NodeCandidateElementInterface>> map2 = new HashMap();
        map2.put(1, new HashMap<Integer, NodeCandidateElementInterface>(){{
            put(PTSolution.PROVIDER_INDEX,new IntegerNodeCandidateElementImpl(0));
            put(PTSolution.CARDINALITY_INDEX, new IntegerNodeCandidateElementImpl(1));
            put(PTSolution.CONFIGURATION_INDEX, new VMConfiguration(0,1,1));
            put(PTSolution.LOCATION_INDEX, new GeographicCoordinate(100, 150));
        }});
        PTSolution sol1 = new PTSolution(map1);
        PTSolution sol2 = new PTSolution(map2);
        assertTrue(sol1.equals(sol2));
    }
}