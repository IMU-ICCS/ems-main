package cp_components;
import node_candidate.GeographicCoordinate;
import node_candidate.VMConfiguration;
import org.javatuples.Quartet;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PTSolutionTest {

    @Test
    public void equalsTest() {
        Map<Integer, Quartet<Integer, VMConfiguration, GeographicCoordinate,Integer>> map1 = new HashMap();
        map1.put(1, new Quartet<>(0, new VMConfiguration(0,1,1), new GeographicCoordinate(100, 150), 1));
        Map<Integer, Quartet<Integer, VMConfiguration, GeographicCoordinate,Integer>> map2 = new HashMap();
        map2.put(1, new Quartet<>(0, new VMConfiguration(0,1,1), new GeographicCoordinate(100, 150), 1));
        PTSolution sol1 = new PTSolution(map1);
        PTSolution sol2 = new PTSolution(map2);
        assertTrue(sol1.equals(sol2));
    }
}