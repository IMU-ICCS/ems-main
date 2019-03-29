package eu.melodic.upperware.cpsolver.solver.parser.creator;

import eu.melodic.upperware.cpsolver.solver.parser.SolverData;
import eu.paasage.upperware.metamodel.cp.CpMetric;
import eu.paasage.upperware.metamodel.cp.Expression;
import lombok.extern.slf4j.Slf4j;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.RealVar;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IntMetricCreator extends NumericValueSupport implements IntCreator<CpMetric, IntVar> {

    @Override
    public boolean is(Expression e) {
        return e instanceof CpMetric && ACCEPTED_INT_TYPES.contains(((CpMetric) e).getType());
    }

    @Override
    public IntVar apply(SolverData solverData, CpMetric cpMetric) {
        return apply(solverData, solverData.getIntConstants(), cpMetric.getId(), cpMetric::getValue);
    }

    @Override
    public RealVar applyReal(SolverData solverData, CpMetric cpMetric) {
        IntVar intVar = this.apply(solverData, cpMetric);
        String suffixedName = getPreffixedName(cpMetric.getId());
        if (solverData.getRealMetrics().containsKey(suffixedName)){
            return solverData.getRealMetrics().get(suffixedName);
        } else {
            log.info("Creating realVar {} for intVar", suffixedName, cpMetric.getId());
            RealVar realVar = solverData.getModel().realIntView(intVar, getPrecision());
            solverData.getRealMetrics().put(suffixedName, realVar);
            return realVar;
        }
    }
}

