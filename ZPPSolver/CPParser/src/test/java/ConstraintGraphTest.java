import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ConstraintGraphTest {
    @AllArgsConstructor
    private static class ConstraintMockup implements ArConstraint{
        private Collection<String> variables;

        @Override
        public Collection<String> getVariableNames() {
            return variables;
        }

        @Override
        public boolean evaluate(Map<String, Double> variables) {
            return false;
        }
    };
    private static List<String> variables;
    @BeforeAll
    static void setUp() {
        String[] vars = new String[]{"var1", "var2", "var3", "var4", "var5", "var6", "var7", "var8"};
        variables = Arrays.asList(vars);
    }

    @Test
    public void noEdgesGraphTest() {
        ConstraintGraph graph = new ConstraintGraph(new ArrayList<ArConstraint>(), variables);
        for (int distance = 1; distance < 10; distance ++) {
            for (String v : variables) {
                assertTrue(graph.getNumberOfNeighbours(v, distance) == 0);
            }
        }
    }

    @Test
    public void graphWithCycleTest() {
        Collection<ArConstraint> constraints = new ArrayList<>();
        int varsSize = variables.size();

        for (int i =1; i <= variables.size(); i++) {
            List<String> vars = new ArrayList<>();
            vars.add(variables.get(i-1));
            vars.add(variables.get(i % varsSize));
            constraints.add(new ConstraintMockup(vars));
        }
        ConstraintGraph graph = new ConstraintGraph(constraints, variables);

        for (int i = 1; i < varsSize/2 - 1; i++) {
            assertTrue(graph.getNumberOfNeighbours(variables.get(0), i) == 2);
        }
        for (int i = varsSize/2 + 1; i < 2*varsSize; i++) {
            assertTrue(graph.getNumberOfNeighbours(variables.get(0), i) == 0);
        }
    }

    @Test
    public void fullGraphTest() {
        int varsSize = variables.size();
        Collection<ArConstraint> constraints = new ArrayList<>();
        ArConstraint constraint = new ConstraintMockup(variables);
        constraints.add(constraint);
        ConstraintGraph graph = new ConstraintGraph(constraints, variables);
        for (String var : variables) {
            assertTrue(graph.getNumberOfNeighbours(var, 1) == varsSize -1);
        }
    }

    @Test
    public void hugeFullyConnectedGraphTest() {
        int varsSize = variables.size();
        Collection<ArConstraint> constraints = new ArrayList<>();
        int noOfConstraints = 10000;
        for (int i = 0; i < noOfConstraints; i ++ ) {
            constraints.add(new ConstraintMockup(variables));
        }
        ConstraintGraph graph = new ConstraintGraph(constraints, variables);
        for (String var : variables) {
            assertTrue(graph.getNumberOfNeighbours(var, 1) == varsSize -1);
        }
        for (int dist = 2; dist <= 1000; dist++) {
            assertTrue(graph.getNumberOfNeighbours(variables.get(0), dist) == 0);
        }

        assertTrue(graph.getConstraints(variables.get(0)).size() == noOfConstraints);
    }

    @Test
    public void expandingGraphTest() {
        Collection<ArConstraint> constraints = new ArrayList<>();
        int varsSize = variables.size();
        for(int i = 0; i < varsSize; i++) {
            constraints.add(new ConstraintMockup(variables.subList(0,i+1)));
        }
        ConstraintGraph graph = new ConstraintGraph(constraints, variables);
        for (int i = 0; i < varsSize; i++) {
            assertTrue(graph.getNumberOfNeighbours(variables.get(i), 1) == varsSize - 1 );
            assertTrue(graph.getConstraints(variables.get(i)).size() == varsSize - i);
        }
    }

}