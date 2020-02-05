package cp_genetic_wrapper;

import cp_wrapper.CPWrapper;
import jenetics_implementation.GeneImpl;
import io.jenetics.util.ISeq;
import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.util.List;
@Slf4j
public class CPGeneticWrapper extends ACPGeneticWrapper {

    public CPGeneticWrapper(CPWrapper cpWrapper) {
        super(0, 0, cpWrapper);
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
        double x1 = System.currentTimeMillis();
        double x = cpWrapper.getUtility(genesToIntegerList(genes));
        double sum = System.currentTimeMillis() - x1;
        suma += sum;
        times ++;
        return x;
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
