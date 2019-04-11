package eu.melodic.upperware.cpsolver.solver.parser.creator;

import eu.melodic.upperware.cpsolver.solver.CPSolverConstants;
import eu.melodic.upperware.cpsolver.solver.parser.SolverData;
import eu.paasage.upperware.metamodel.cp.Expression;

public interface Creator<T, V> {

    boolean is(Expression e);

    V apply(SolverData solverData, T t);

    default double getPrecision(){
        return CPSolverConstants.PRECISION;
    }
}
