package eu.melodic.upperware.cpsolver.solver.parser.creator;

import eu.melodic.upperware.cpsolver.solver.parser.SolverData;
import eu.paasage.upperware.metamodel.cp.CpVariable;
import eu.paasage.upperware.metamodel.cp.Expression;
import eu.paasage.upperware.metamodel.cp.RangeDomain;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.chocosolver.solver.variables.RealVar;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class RealVarCreator extends NumericValueSupport implements Creator<CpVariable, RealVar> {

    @Override
    public boolean is(Expression e) {
        return e instanceof CpVariable && ACCEPTED_REAL_TYPES.contains(getDomainType(((CpVariable) e).getDomain()));
    }

    @Override
    public RealVar apply(SolverData solverData, CpVariable cpVariable) {
        Map<String, RealVar> realVars = solverData.getRealVars();
        if (realVars.containsKey(cpVariable.getId())) {
            return realVars.get(cpVariable.getId());
        }
        if (isNumericListDomain(cpVariable.getDomain())) {
            throw new RuntimeException("Temporary unimplemented");
        } else if (isRangeDomain(cpVariable.getDomain())) {
            log.info("Creating realVar {}", cpVariable.getId());
            Pair<Double, Double> range = parseRealRangeDomain((RangeDomain) cpVariable.getDomain());
            RealVar realVar = solverData.getModel().realVar(cpVariable.getId(), range.getLeft(), range.getRight(), getPrecision());
            realVars.put(cpVariable.getId(), realVar);
            return realVar;
        }
        throw new RuntimeException("Unknown domain");
    }
}
