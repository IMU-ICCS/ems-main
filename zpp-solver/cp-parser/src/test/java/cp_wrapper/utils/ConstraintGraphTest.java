package cp_wrapper.utils;

import cp_wrapper.utils.constraint.Constraint;
import cp_wrapper.utils.test_utils.mockups.ConstraintMockup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

class ConstraintGraphTest {
    private static List<String> variables;
    @BeforeAll
    static void setUp() {
        String[] vars = new String[]{"var1", "var2", "var3", "var4", "var5", "var6", "var7", "var8"};
        variables = Arrays.asList(vars);
    }

    @Test
    public void noEdgesGraphTest() {
        ConstraintGraph graph = new ConstraintGraph(new ArrayList<Constraint>(), variables);
        for (int distance = 1; distance < 10; distance ++) {
            for (String v : variables) {
                assertEquals(graph.getNumberOfNeighbours(v, distance), 0);
            }
        }
    }

    @Test
    public void graphWithCycleTest() {
        Collection<Constraint> constraints = new ArrayList<>();
        int varsSize = variables.size();

        for (int i =1; i <= variables.size(); i++) {
            List<String> vars = new ArrayList<>();
            vars.add(variables.get(i-1));
            vars.add(variables.get(i % varsSize));
            constraints.add(new ConstraintMockup(vars));
        }
        ConstraintGraph graph = new ConstraintGraph(constraints, variables);

        for (int i = 1; i < varsSize/2 - 1; i++) {
            assertEquals(graph.getNumberOfNeighbours(variables.get(0), i), 2);
        }
        for (int i = varsSize/2 + 1; i < 2*varsSize; i++) {
            assertEquals(graph.getNumberOfNeighbours(variables.get(0), i), 0);
        }
    }

    @Test
    public void fullGraphTest() {
        int varsSize = variables.size();
        Collection<Constraint> constraints = new ArrayList<>();
        Constraint constraint = new ConstraintMockup(variables);
        constraints.add(constraint);
        ConstraintGraph graph = new ConstraintGraph(constraints, variables);
        for (String var : variables) {
            assertEquals(graph.getNumberOfNeighbours(var, 1), varsSize -1);
        }
    }

    @Test
    public void hugeFullyConnectedGraphTest() {
        int varsSize = variables.size();
        Collection<Constraint> constraints = new ArrayList<>();
        int noOfConstraints = 10000;
        for (int i = 0; i < noOfConstraints; i ++ ) {
            constraints.add(new ConstraintMockup(variables));
        }
        ConstraintGraph graph = new ConstraintGraph(constraints, variables);
        for (String var : variables) {
            assertEquals(graph.getNumberOfNeighbours(var, 1), varsSize - 1);
        }
        for (int dist = 2; dist <= 1000; dist++) {
            assertEquals(graph.getNumberOfNeighbours(variables.get(0), dist), 0);
        }

        assertEquals(graph.getConstraints(variables.get(0)).size(), noOfConstraints);
    }

    @Test
    public void expandingGraphTest() {
        Collection<Constraint> constraints = new ArrayList<>();
        int varsSize = variables.size();
        for(int i = 0; i < varsSize; i++) {
            constraints.add(new ConstraintMockup(variables.subList(0,i+1)));
        }
        ConstraintGraph graph = new ConstraintGraph(constraints, variables);
        for (int i = 0; i < varsSize; i++) {
            assertEquals(graph.getNumberOfNeighbours(variables.get(i), 1), varsSize - 1 );
            assertEquals(graph.getConstraints(variables.get(i)).size(), varsSize - i);
        }
    }

}