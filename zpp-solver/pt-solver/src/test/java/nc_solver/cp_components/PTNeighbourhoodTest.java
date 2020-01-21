package nc_solver.cp_components;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PTNeighbourhoodTest {

    @Test
    public void getAllMovesTest() {
        List<Integer> assignments = Arrays.asList(new Integer[] {1,2,3,4,5});
        List<Integer> maxValues =  Arrays.asList(new Integer[] {1,2,3,4,5});
        List<Integer> minValues = Arrays.asList(new Integer[] {1,2,3,4,5});
        PTSolution.maxVariableValues = maxValues;
        PTSolution.minVariableValues = minValues;
        PTNeighbourhood neighbourhood = new PTNeighbourhood();
        PTSolution sol = new PTSolution(assignments);
        assertEquals( neighbourhood.getAllMoves(sol).size(), 0);

        maxValues =  Arrays.asList(new Integer[] {10,20,30,40,50});
        PTSolution.maxVariableValues= maxValues;
        assertEquals( neighbourhood.getAllMoves(sol).size(), assignments.size());

        minValues = Arrays.asList(new Integer[] {1,0,0,0,0});
        PTSolution.minVariableValues = minValues;
        assertEquals( neighbourhood.getAllMoves(sol).size(), 2 * assignments.size() - 1);
    }

}