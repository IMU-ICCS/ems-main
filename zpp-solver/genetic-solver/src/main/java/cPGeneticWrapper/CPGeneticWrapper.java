package cPGeneticWrapper;

import cp_wrapper.CPWrapper;
import implementation.OurGene;
import io.jenetics.util.ISeq;
import io.jenetics.util.RandomRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


// TODO need to implement method getRanges and getList in cpParser
public class CPGeneticWrapper {
    private final static Random random = RandomRegistry.getRandom();
    private CPWrapper cpWrapper;

    public CPGeneticWrapper(CPWrapper cpWrapper) {
        this.cpWrapper = cpWrapper;
    }

    // Calculates which variable has highest heuristic value. Returns its index.
    // TODO probably need to calculate it in other way - to get faster exec time
    public int calculateHeuristicBest(List<Integer> values) {
        int bestIndex = -1, bestValue = 0, tmp;

        assert values.size() > 0;

        for (int i = 0; i < values.size(); i++)
            if ((tmp = cpWrapper.getHeuristicEvaluation(values, i)) > bestValue) {
                bestIndex = i;
                bestValue = tmp;
            }

        return bestIndex;
    }

    // Generates random value for variable indexed with index.
    public int generateRandomValue(int index) {
        return 0;
        // TODO random NEEDS to be used here
    }

    public double calculateUtility(ISeq<OurGene> genes) {
        return cpWrapper.getUtility(genesToIntegerList(genes));
    }

    public int countViolatedConstraints(ISeq<OurGene> genes) {
        return cpWrapper.countViolatedConstraints(genesToIntegerList(genes));
    }

    public boolean isValid(int value, int index) {
        // TODO
        return cpWrapper.getMinDomainValue(index) <= value && value <= cpWrapper.getMaxDomainValue(index);
    }

    public boolean getIsFeasible(ISeq<OurGene> genes) {
        return cpWrapper.checkIfFeasible(genesToIntegerList(genes));
    }

    private List<Integer> genesToIntegerList(ISeq<OurGene> genes) {
        List<Integer> list = new ArrayList<>();

        for (OurGene gene : genes)
            list.add(gene.getAllele());

        return list;
    }
}
