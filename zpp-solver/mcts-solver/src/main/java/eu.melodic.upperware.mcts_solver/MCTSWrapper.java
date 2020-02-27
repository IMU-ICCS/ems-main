package eu.melodic.upperware.mcts_solver;

import cp_wrapper.CPWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

@Slf4j
@AllArgsConstructor
public class MCTSWrapper {
    private final static Random random = new Random();
    private CPWrapper cpWrapper;

    // Generates random value for variable indexed with index.
    public int generateRandomValue(int index) {
        return random.nextInt(cpWrapper.getMaxDomainValue(index) - cpWrapper.getMinDomainValue(index) + 1)
                + cpWrapper.getMinDomainValue(index);
    }

    // Calculates utility for certain variables assignment.
    public double getUtility(List<Integer> assignments) {
        return cpWrapper.getUtility(assignments);
    }

}
