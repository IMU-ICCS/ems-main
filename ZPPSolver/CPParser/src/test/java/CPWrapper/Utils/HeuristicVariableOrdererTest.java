package CPWrapper.Utils;
import CPWrapper.Mockups.ArConstraintMockup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HeuristicVariableOrdererTest {
    private static List<String> variables;

    @BeforeAll
    static void setUp() {
        String[] vars = new String[]{"var1", "var2", "var3", "var4", "var5", "var6", "var7", "var8"};
        variables = Arrays.asList(vars);
    }

    private static ConstraintGraph createExpandingGraph() {
        Collection<ArConstraint> constraints = new ArrayList<>();
        int varsSize = variables.size();
        for(int i = 0; i < varsSize; i++) {
            constraints.add(new ArConstraintMockup(variables.subList(0,i+1)));
        }
        ConstraintGraph graph = new ConstraintGraph(constraints, variables);
        return graph;
    }

    private static ConstraintGraph sameNumberOfConstraintsGraph() {
        Collection<ArConstraint> constraints = new ArrayList<>();
        int varsSize = variables.size();
        for(int i = 0; i < varsSize; i++) {
            constraints.add(new ArConstraintMockup(variables.subList(0,i+1)));
            for (int j = i + 1; j < varsSize; j++) {
                constraints.add(new ArConstraintMockup(variables.subList(j, j+1)));
            }
        }
        ConstraintGraph graph = new ConstraintGraph(constraints, variables);
        return graph;
    }

    @Test
    public void expandingGraphTest(){
            ConstraintGraph graph = createExpandingGraph();
            HeuristicVariableOrderer orderer = new HeuristicVariableOrderer(graph);
            for (int i = 0; i < variables.size(); i++) {
                assertTrue( orderer.indexToVariableName(i).equals(variables.get(i)));
            }
    }

    @Test
    public void sameNumberOfConstraintsTest() {
        ConstraintGraph graph = sameNumberOfConstraintsGraph();
        // Here the ordering is not deterministic but we can at least check if
        // all the variables are indexed
        HeuristicVariableOrderer orderer = new HeuristicVariableOrderer(graph);
        Set<String> vars = new HashSet<>();
        for (int i = 0; i < variables.size(); i++) {
            vars.add(orderer.indexToVariableName(i));
        }
        assertEquals(vars.size(), variables.size());
    }

    @Test
    public void simpleGraphTest() {
        Collection<ArConstraint> constraints = new ArrayList<>();
        constraints.add(new ArConstraintMockup(variables.subList(0,1)));
        constraints.add(new ArConstraintMockup(variables.subList(1,3)));
        constraints.add(new ArConstraintMockup(variables.subList(2,3)));
        ConstraintGraph graph = new ConstraintGraph(constraints, variables.subList(0,3));
        HeuristicVariableOrderer orderer = new HeuristicVariableOrderer(graph);

        assertTrue(orderer.indexToVariableName(0).equals(variables.get(2)));
        assertTrue(orderer.indexToVariableName(1).equals(variables.get(1)));
        assertTrue(orderer.indexToVariableName(2).equals(variables.get(0)));
    }
}