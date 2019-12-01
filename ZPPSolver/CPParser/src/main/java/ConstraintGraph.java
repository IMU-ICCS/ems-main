import java.util.Collection;

public class ConstraintGraph {
    private Collection<ArConstraint> constraints;
    private Collection<String> variables;

    public ConstraintGraph(Collection<ArConstraint> constraints, Collection<String> vars) {
        this.constraints = constraints;
        this.variables = vars;
    }

    public int getNumberOfNeighbours(String node, int distance) {
        //TODO
        return 0;
    }
}
