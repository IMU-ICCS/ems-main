
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ArConstraintTest {
    private static ArConstraint constraint;

    @BeforeAll
    public static void setup(){
        constraint = new ArConstraint();
    }
    @Test
    public void shouldThrowEmptyVariables(){
        Map<String, Double> emptyVars = new HashMap<>();
        assertThrows(RuntimeException.class, () -> {
            constraint.evaluate(emptyVars);
        });
    }

    @Test
    public void shouldThrowWrongVariables(){
        Map<String, Double> emptyVars = new HashMap<>();
        assertThrows(RuntimeException.class, () -> {
            constraint.evaluate(emptyVars);
        });
    }

    @Test
    public void TEMP(){
        Map<String, Double> emptyVars = new HashMap<>();
        assertTrue(constraint.evaluate(emptyVars));
    }

}