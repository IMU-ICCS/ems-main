package eu.melodic.upperware.cpsolver.solver;

import eu.melodic.upperware.cpsolver.solver.parser.ConstraintProblemParser;
import eu.melodic.upperware.cpsolver.solver.parser.SolverParsedData;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CPSolver {

    private ConstraintProblemParser constraintProblemParser;

    public List<CpSolution> solve(ConstraintProblem constraintProblem) {
        SolverParsedData solverParsedData = constraintProblemParser.parse(constraintProblem);
        return solverParsedData.solve();
    }

}
