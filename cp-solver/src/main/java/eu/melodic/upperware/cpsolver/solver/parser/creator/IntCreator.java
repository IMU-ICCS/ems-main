package eu.melodic.upperware.cpsolver.solver.parser.creator;

import eu.melodic.upperware.cpsolver.solver.parser.SolverData;
import org.chocosolver.solver.variables.RealVar;

public interface IntCreator<T, V> extends Creator<T, V> {

    RealVar applyReal(SolverData solverData, T t);

    default String getPreffixedName(String name) {
        return "(real)" + name;
    }

}
