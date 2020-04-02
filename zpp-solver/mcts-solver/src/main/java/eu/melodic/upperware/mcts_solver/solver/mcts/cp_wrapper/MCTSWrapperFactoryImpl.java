package eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper;

import cp_wrapper.CPWrapper;
import cp_wrapper.utility_provider.UtilityProviderFactory;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MCTSWrapperFactoryImpl implements MCTSWrapperFactory {
    private UtilityProviderFactory utilityProviderFactory;
    private ConstraintProblem constraintProblem;

    @Override
    public MCTSWrapper create() {
        CPWrapper cpWrapper = new CPWrapper();
        cpWrapper.parseWithRandomOrder(constraintProblem, utilityProviderFactory.create());
        return new MCTSWrapper(cpWrapper);
    }
}
