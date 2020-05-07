package eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.cp_wrapper.CPWrapper;
import eu.melodic.upperware.cp_wrapper.utility_provider.UtilityProviderFactory;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MCTSWrapperFactoryImpl implements MCTSWrapperFactory {
    private UtilityProviderFactory utilityProviderFactory;
    private ConstraintProblem constraintProblem;
    private NodeCandidates nodeCandidates;

    @Override
    public MCTSWrapper create() {
        CPWrapper cpWrapper = new CPWrapper();
        cpWrapper.parseWithRandomOrder(constraintProblem, utilityProviderFactory.create());
        return new MCTSWrapper(cpWrapper, nodeCandidates);
    }
}
