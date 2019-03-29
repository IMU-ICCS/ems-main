package eu.melodic.upperware.cpsolver.solver.parser.creator;

import eu.melodic.upperware.cpsolver.solver.parser.SolverData;
import eu.paasage.upperware.metamodel.cp.CpMetric;
import eu.paasage.upperware.metamodel.cp.Expression;
import org.chocosolver.solver.variables.RealVar;
import org.springframework.stereotype.Service;

@Service
public class RealMetricCreator extends NumericValueSupport implements Creator<CpMetric, RealVar> {

    @Override
    public boolean is(Expression e) {
        return e instanceof CpMetric && ACCEPTED_REAL_TYPES.contains(((CpMetric) e).getType());
    }

    @Override
    public RealVar apply(SolverData solverData, CpMetric cpMetric) {
        return applyReal(solverData, solverData.getRealConstants(), cpMetric.getId(), cpMetric::getValue);
    }

}

