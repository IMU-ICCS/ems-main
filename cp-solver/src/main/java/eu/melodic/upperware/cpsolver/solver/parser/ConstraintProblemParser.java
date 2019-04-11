package eu.melodic.upperware.cpsolver.solver.parser;

import eu.paasage.upperware.metamodel.cp.ConstraintProblem;

public interface ConstraintProblemParser {

    SolverParsedData parse(ConstraintProblem constraintProblem);
}
