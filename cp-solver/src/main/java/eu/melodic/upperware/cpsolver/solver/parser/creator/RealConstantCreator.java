package eu.melodic.upperware.cpsolver.solver.parser.creator;

import eu.melodic.upperware.cpsolver.solver.parser.SolverData;
import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.Expression;
import org.chocosolver.solver.variables.RealVar;
import org.springframework.stereotype.Service;

@Service
public class RealConstantCreator extends NumericValueSupport implements Creator<Constant, RealVar> {

    @Override
    public boolean is(Expression e) {
        return e instanceof Constant && ACCEPTED_REAL_TYPES.contains(((Constant) e).getType());
    }

    @Override
    public RealVar apply(SolverData solverData, Constant constant) {
        return applyReal(solverData, solverData.getRealConstants(), constant.getId(), constant::getValue);
    }
}
