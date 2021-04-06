package eu.melodic.upperware.genetic_solver.cp_genetic_wrapper;

import eu.melodic.upperware.cp_wrapper.CPWrapper;
import eu.melodic.upperware.genetic_solver.jenetics_implementation.GeneImpl;
import io.jenetics.util.ISeq;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class CPGeneticWrapper extends ACPGeneticWrapper {
    public CPGeneticWrapper(CPWrapper cpWrapper) {
        super(cpWrapper);
    }

    // Calculates which variable has highest heuristic value. Returns its index.
    @Override
    public int calculateHeuristicBest(ISeq<GeneImpl> genes) {
        int bestIndex = -1, bestValue = Integer.MIN_VALUE, tmp;

        List<Integer> values = genesToIntegerList(genes);

        assert values.size() > 0;

        for (int i = 0; i < values.size(); i++)
            if ((tmp = cpWrapper.getHeuristicEvaluation(values, i)) > bestValue) {
                bestIndex = i;
                bestValue = tmp;
            }

        return bestIndex;
    }

    // Generates random value for variable indexed with index.
    @Override
    public int generateRandomValue(int index) {
        return random.nextInt(cpWrapper.getMaxDomainValue(index) - cpWrapper.getMinDomainValue(index) + 1)
                + cpWrapper.getMinDomainValue(index);
    }

    @Override
    public double calculateUtility(ISeq<GeneImpl> genes) {
        return cpWrapper.getUtility(genesToIntegerList(genes));
    }

    @Override
    public int countViolatedConstraints(ISeq<GeneImpl> genes) {
        return cpWrapper.countViolatedConstraints(genesToIntegerList(genes));
    }

    @Override
    public boolean isValid(int value, int index) {
        return cpWrapper.getMinDomainValue(index) <= value && value <= cpWrapper.getMaxDomainValue(index);
    }

    @Override
    public int getSize() {
        return cpWrapper.getVariablesCount();
    }

    @Override
    public int getHeuristicEvaluation(List<Integer> assignments, int variableIndex) {
        return cpWrapper.getHeuristicEvaluation(assignments, variableIndex);
    }
}
