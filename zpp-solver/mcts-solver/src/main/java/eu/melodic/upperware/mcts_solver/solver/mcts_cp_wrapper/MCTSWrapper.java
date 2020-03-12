package eu.melodic.upperware.mcts_solver.solver.mcts_cp_wrapper;

import cp_wrapper.CPWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

@Slf4j
@AllArgsConstructor
public class MCTSWrapper implements IMCTSWrapper{
    private final static Random random = new Random();
    private CPWrapper cpWrapper;

    @Override
    public int generateRandomValue(int index) {
        return random.nextInt(cpWrapper.getMaxDomainValue(index) - cpWrapper.getMinDomainValue(index) + 1)
                + cpWrapper.getMinDomainValue(index);
    }

    @Override
    public double getUtility(List<Integer> assignments) {
        log.debug("Evaluating solution " + assignments.toString());

        double utility = cpWrapper.getUtility(assignments);
        log.debug("Solution is " + (cpWrapper.checkIfFeasible(assignments) ? "feasible" : "not feasible") + "utility value:" + utility);
        return utility;
    }

    @Override
    public int getSize() {
        return cpWrapper.getVariablesCount();
    }

    @Override
    public int domainSize(int index) {
        return cpWrapper.getMaxDomainValue(index) - cpWrapper.getMinDomainValue(index) + 1;
    }

    @Override
    public int getMinDomainValue(int index) {
        return cpWrapper.getMinDomainValue(index);
    }

    @Override
    public int getMaxDomainValue(int index) {
        return cpWrapper.getMaxDomainValue(index);
    }

    @Override
    public boolean isFeasible(List<Integer> assignments) {
        return cpWrapper.checkIfFeasible(assignments);
    }
}
