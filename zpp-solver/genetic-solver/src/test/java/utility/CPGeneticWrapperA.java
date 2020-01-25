package utility;

import cp_genetic_wrapper.ACPGeneticWrapper;
import cp_wrapper.CPWrapper;
import implementation.ImplGene;
import io.jenetics.util.ISeq;

import java.util.List;

public class CPGeneticWrapperA extends ACPGeneticWrapper {
    public CPGeneticWrapperA(CPWrapper cpWrapper) {
        super(cpWrapper);
    }

    @Override
    public int calculateHeuristicBest(ISeq<ImplGene> values) {
        return 0;
    }

    @Override
    public int generateRandomValue(int index) {
        return random.nextInt(1001);
    }

    @Override
    public double calculateUtility(ISeq<ImplGene> genes) {
        double res = 0.;
        List<Integer> list = genesToIntegerList(genes);
        for (int i = 0; i < list.size() - 1; i+=2)
            res += list.get(i) - list.get(i + 1);
        return res;
    }

    @Override
    public int countViolatedConstraints(ISeq<ImplGene> genes) {
        return 0;
    }

    @Override
    public boolean isValid(int value, int index) {
        return value <= 1000 && value >= 0;
    }

    @Override
    public int getSize() {
        return 10;
    }

    @Override
    public int getHeuristicEvaluation(List<Integer> assignments, int variableIndex) {
        return 0;
    }
}
