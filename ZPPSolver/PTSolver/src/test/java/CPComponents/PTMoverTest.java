package CPComponents;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PTMoverTest {

    @Test
    public void MovingTest() {
        List<Integer> assignments = Arrays.asList(new Integer[] {1,2,3,4,5});

        PTSolution solution = new PTSolution(Arrays.asList(new Integer[] {1,2,3,4,5}));

        for (int i = 0; i < assignments.size() ; i++) {
            PTMover m = PTMover.newUpMove(i);
            m.apply(solution);
            assertTrue( solution.getVarAssignments().get(i) == assignments.get(i) + 1);
        }

        for (int i = 0; i < assignments.size() ; i++) {
            PTMover m = PTMover.newDownMove(i);
            m.apply(solution);
            assertTrue( solution.getVarAssignments().get(i) == assignments.get(i));
        }

    }

}