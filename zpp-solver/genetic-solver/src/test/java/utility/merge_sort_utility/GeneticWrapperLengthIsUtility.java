package utility.merge_sort_utility;

import cp_genetic_wrapper.ACPGeneticWrapper;
import cp_wrapper.CPWrapper;
import io.jenetics.util.ISeq;
import jenetics_implementation.GeneImpl;

import java.util.List;

public class GeneticWrapperLengthIsUtility extends ACPGeneticWrapper {
    public GeneticWrapperLengthIsUtility(double suma, int times, CPWrapper cpWrapper) {
        super(suma, times, cpWrapper);
    }

    @Override
    public int calculateHeuristicBest(ISeq<GeneImpl> values) {
        return 0;
    }

    @Override
    public int generateRandomValue(int index) {
        return 0;
    }

    @Override
    public double calculateUtility(ISeq<GeneImpl> genes) {
        return genes.size();
    }

    @Override
    public int countViolatedConstraints(ISeq<GeneImpl> genes) {
        return 0;
    }

    @Override
    public boolean isValid(int value, int index) {
        return false;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public int getHeuristicEvaluation(List<Integer> assignments, int variableIndex) {
        return 0;
    }
}
