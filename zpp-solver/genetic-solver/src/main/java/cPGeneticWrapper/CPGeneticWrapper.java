package cPGeneticWrapper;

import cp_wrapper.CPWrapper;
import implementation.ImplGene;
import io.jenetics.util.ISeq;

import java.util.List;

public class CPGeneticWrapper extends ACPGeneticWrapper {

    public CPGeneticWrapper(CPWrapper cpWrapper) {
        super(cpWrapper);
    }

    // Calculates which variable has highest heuristic value. Returns its index.
    // TODO probably need to calculate it in other way - to get faster exec time (or maybe getHeuristicEvaluation is fast enough)
    @Override
    public int calculateHeuristicBest(ISeq<ImplGene> genes) {
        int bestIndex = -1, bestValue = 0, tmp;

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
    public double calculateUtility(ISeq<ImplGene> genes) {
        return cpWrapper.getUtility(genesToIntegerList(genes));
    }

    @Override
    public int countViolatedConstraints(ISeq<ImplGene> genes) {
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
}
