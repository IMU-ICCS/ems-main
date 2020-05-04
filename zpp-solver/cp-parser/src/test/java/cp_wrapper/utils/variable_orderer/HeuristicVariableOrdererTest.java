package cp_wrapper.utils.variable_orderer;
import cp_wrapper.utils.constraint.Constraint;
import cp_wrapper.utils.constraint_graph.ConstraintGraph;
import cp_wrapper.utils.test_utils.mockups.ConstraintMockup;
import cp_wrapper.utils.variable_orderer.HeuristicVariableOrderer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HeuristicVariableOrdererTest {
    private static List<String> variables;

    @BeforeAll
    static void setUp() {
        variables = Arrays.asList("var1", "var2", "var3", "var4", "var5", "var6", "var7", "var8");
    }

    private static ConstraintGraph createExpandingGraph() {
        Collection<Constraint> constraints = new ArrayList<>();
        int varsSize = variables.size();
        for(int i = 0; i < varsSize; i++) {
            constraints.add(new ConstraintMockup(variables.subList(0,i+1)));
        }
        return new ConstraintGraph(constraints, variables);
    }

    private static ConstraintGraph sameNumberOfConstraintsGraph() {
        Collection<Constraint> constraints = new ArrayList<>();
        int varsSize = variables.size();
        for(int i = 0; i < varsSize; i++) {
            constraints.add(new ConstraintMockup(variables.subList(0,i+1)));
            for (int j = i + 1; j < varsSize; j++) {
                constraints.add(new ConstraintMockup(variables.subList(j, j+1)));
            }
        }
        return new ConstraintGraph(constraints, variables);
    }

    @Test
    public void expandingGraphTest(){
            ConstraintGraph graph = createExpandingGraph();
            HeuristicVariableOrderer orderer = new HeuristicVariableOrderer(graph, Collections.emptyList());
            for (int i = 0; i < variables.size(); i++) {
                assertTrue( orderer.getNameFromIndex(i).equals(variables.get(i)));
            }
    }

    @Test
    public void sameNumberOfConstraintsTest() {
        ConstraintGraph graph = sameNumberOfConstraintsGraph();
        // Here the ordering is not deterministic but we can at least check if
        // all the variables are indexed
        HeuristicVariableOrderer orderer = new HeuristicVariableOrderer(graph, Collections.emptyList());
        Set<String> vars = new HashSet<>();
        for (int i = 0; i < variables.size(); i++) {
            vars.add(orderer.getNameFromIndex(i));
        }
        assertEquals(vars.size(), variables.size());
    }

    @Test
    public void simpleGraphTest() {
        Collection<Constraint> constraints = new ArrayList<>();
        constraints.add(new ConstraintMockup(variables.subList(0,1)));
        constraints.add(new ConstraintMockup(variables.subList(1,3)));
        constraints.add(new ConstraintMockup(variables.subList(2,3)));
        ConstraintGraph graph = new ConstraintGraph(constraints, variables.subList(0,3));
        HeuristicVariableOrderer orderer = new HeuristicVariableOrderer(graph, Collections.emptyList());

        assertTrue(orderer.getNameFromIndex(0).equals(variables.get(2)));
        assertTrue(orderer.getNameFromIndex(1).equals(variables.get(1)));
        assertTrue(orderer.getNameFromIndex(2).equals(variables.get(0)));
    }
}