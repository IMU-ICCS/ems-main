package node_candidate;

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