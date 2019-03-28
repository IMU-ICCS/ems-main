package eu.melodic.upperware.cpsolver.solver.parser.creator;

import eu.melodic.upperware.cpsolver.solver.parser.SolverData;
import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.Expression;
import lombok.extern.slf4j.Slf4j;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.RealVar;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IntConstantCreator extends NumericValueSupport implements IntCreator<Constant, IntVar> {

    @Override
    public boolean is(Expression e) {
        return e instanceof Constant && ACCEPTED_INT_TYPES.contains(((Constant) e).getType());
    }

    @Override
    public IntVar apply(SolverData solverData, Constant constant) {
        return apply(solverData, solverData.getIntConstants(), constant.getId(), constant::getValue);
    }

    @Override
    public RealVar applyReal(SolverData solverData, Constant constant) {
        IntVar intVar = this.apply(solverData, constant);
        log.info("Creating intVar {}", intVar);
        String suffixedName = getPreffixedName(constant.getId());
        if (solverData.getRealConstants().containsKey(suffixedName)){
            return solverData.getRealConstants().get(suffixedName);
        } else {
            log.info("Creating realVar {} for intVar", suffixedName, constant.getId());
            RealVar realVar = solverData.getModel().realIntView(intVar, getPrecision());
            solverData.getRealConstants().put(suffixedName, realVar);
            return realVar;
        }
    }
}
