package eu.melodic.upperware.pt_solver.pt_solver.components;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PTSolutionTest {

    @Test
    public void copyTest() {
        PTSolution sol1 = new PTSolution(Arrays.asList(new Integer[] {1,2,3,4,5,6,7,8,9}));
        PTSolution sol2 = (PTSolution) sol1.copy();

        sol1.increaseValue(1);
        assertFalse(sol1.getVarAssignments().get(1) == sol2.getVarAssignments().get(1));
    }

    @Test
    public void equalsTest() {
        PTSolution sol1 = new PTSolution(Arrays.asList(new Integer[] {1,2,3,4,5,6,7,8,9}));
        PTSolution sol2 = new PTSolution(Arrays.asList(new Integer[] {1,2,3,4,5,6,7,8,9}));
        PTSolution sol3 = new PTSolution(Arrays.asList(new Integer[] {1,2,3,4,5,7,7,8,9}));
        PTSolution sol4 = new PTSolution(Arrays.asList(new Integer[] {1,2,3,4}));

        assertTrue(sol1.equals(sol2));
        assertTrue(sol2.equals(sol1));
        assertFalse(sol1.equals(sol3));
        assertFalse(sol1.equals(sol4));
    }

}