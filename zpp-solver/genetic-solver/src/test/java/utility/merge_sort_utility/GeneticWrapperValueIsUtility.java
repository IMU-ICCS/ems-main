package utility.merge_sort_utility;

import eu.melodic.upperware.cp_wrapper.CPWrapper;
import eu.melodic.upperware.genetic_solver.cp_genetic_wrapper.ACPGeneticWrapper;
import eu.melodic.upperware.genetic_solver.jenetics_implementation.GeneImpl;
import io.jenetics.util.ISeq;

import java.util.List;

public class GeneticWrapperValueIsUtility extends ACPGeneticWrapper {
    public GeneticWrapperValueIsUtility(CPWrapper cpWrapper) {
        super(cpWrapper);
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
        assert genes.size() == 1;
        return genes.get(0).getAllele();
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
