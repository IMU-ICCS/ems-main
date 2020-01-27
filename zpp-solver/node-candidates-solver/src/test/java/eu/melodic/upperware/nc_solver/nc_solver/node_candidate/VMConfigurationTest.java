package eu.melodic.upperware.nc_solver.nc_solver.node_candidate;

import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.VMConfiguration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VMConfigurationTest {

    @Test
    public void comparatorTest() {
        VMConfiguration t = new VMConfiguration(100, 200, 300);
        assertEquals(t.compareTo(new VMConfiguration(100, 200, 300)), 0);
        assertEquals(t.compareTo(new VMConfiguration(50, 201, 301)), 1);
        assertEquals(t.compareTo(new VMConfiguration(100, 200, 200)), 1);
        assertEquals(t.compareTo(new VMConfiguration(100, 200, 400)), -1);
        assertEquals(t.compareTo(new VMConfiguration(100, 400, 10)), -1);
    }

}