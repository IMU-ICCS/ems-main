package eu.melodic.upperware.cpsolver.solver.parser.creator;

import eu.melodic.upperware.cpsolver.solver.parser.SolverData;
import eu.paasage.upperware.metamodel.cp.CpVariable;
import eu.paasage.upperware.metamodel.cp.Expression;
import eu.paasage.upperware.metamodel.cp.NumericListDomain;
import eu.paasage.upperware.metamodel.cp.RangeDomain;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.RealVar;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class IntVarCreator extends NumericValueSupport implements IntCreator<CpVariable, IntVar> {

    @Override
    public boolean is(Expression e) {
        return e instanceof CpVariable && ACCEPTED_INT_TYPES.contains(getDomainType(((CpVariable) e).getDomain()));
    }

    @Override
    public IntVar apply(SolverData solverData, CpVariable cpVariable) {
        Map<String, IntVar> intVars = solverData.getIntVars();
        if (intVars.containsKey(cpVariable.getId())) {
            return intVars.get(cpVariable.getId());
        }
        log.info("Creating intVar {}", cpVariable.getId());
        IntVar intVar = null;
        if (isRangeDomain(cpVariable.getDomain())) {
            Pair<Integer, Integer> range = parseIntRangeDomain((RangeDomain) cpVariable.getDomain());
            intVar = solverData.getModel().intVar(cpVariable.getId(), range.getLeft(), range.getRight());
        } else if (isNumericListDomain(cpVariable.getDomain())) {
            int[] possibleValues = parseIntNumericListDomain((NumericListDomain) cpVariable.getDomain());
            intVar = solverData.getModel().intVar(cpVariable.getId(), possibleValues);
        }
        intVars.put(cpVariable.getId(), intVar);
        return intVar;
    }

    @Override
    public RealVar applyReal(SolverData solverData, CpVariable cpVariable) {
        IntVar intVar = this.apply(solverData, cpVariable);
        String suffixedName = getPreffixedName(cpVariable.getId());
        if (solverData.getRealVars().containsKey(suffixedName)){
            return solverData.getRealVars().get(suffixedName);
        } else {
            log.info("Creating realVar {} for intVar", suffixedName, cpVariable.getId());
            RealVar realVar = solverData.getModel().realIntView(intVar, getPrecision());
            solverData.getRealVars().put(suffixedName, realVar);
            return realVar;
        }
    }
}
