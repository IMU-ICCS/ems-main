package cp_genetic_wrapper;

import cp_wrapper.CPWrapper;
import io.jenetics.util.ISeq;
import io.jenetics.util.RandomRegistry;
import jenetics_implementation.ChromosomeImpl;
import jenetics_implementation.GeneImpl;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
public abstract class ACPGeneticWrapper {
    protected final static Random random = RandomRegistry.getRandom();
    protected CPWrapper cpWrapper;

    // Calculates which variable has highest heuristic value. Returns its index.
    public abstract int calculateHeuristicBest(ISeq<GeneImpl> values);

    // Generates random value for variable indexed with index.
    public abstract int generateRandomValue(int index);

    public abstract double calculateUtility(ISeq<GeneImpl> genes);

    public abstract int countViolatedConstraints(ISeq<GeneImpl> genes);

    public abstract boolean isValid(int value, int index);

    public abstract int getSize();

    public abstract int getHeuristicEvaluation(List<Integer> assignments, int variableIndex);

    public static List<Integer> chromosomeToIntegerList(ChromosomeImpl chromosome) {
        return genesToIntegerList(chromosome.toSeq());
    }

    protected static List<Integer> genesToIntegerList(ISeq<GeneImpl> genes) {
        List<Integer> list = new ArrayList<>();

        for (GeneImpl gene : genes)
            list.add(gene.getAllele());

        return list;
    }
}
