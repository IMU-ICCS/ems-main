package eu.melodic.upperware.nc_solver.nc_solver.node_candidate;

import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.GeographicCoordinate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeographicCoordinateTest {

    @Test
    public void comparatorTest() {
        GeographicCoordinate t = new GeographicCoordinate(100, 150);
        assertEquals(t.compareTo(new GeographicCoordinate(100, 151)), -1);
        assertEquals(t.compareTo(new GeographicCoordinate(100, 150)), 0);
        assertEquals(t.compareTo(new GeographicCoordinate(101, 1)), -1);
        assertEquals(t.compareTo(new GeographicCoordinate(100, 1)), 1);
        assertEquals(t.compareTo(new GeographicCoordinate(99, 151)), 1);
    }

}