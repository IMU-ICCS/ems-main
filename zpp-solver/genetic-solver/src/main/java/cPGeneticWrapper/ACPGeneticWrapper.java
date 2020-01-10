package cPGeneticWrapper;

import cp_wrapper.CPWrapper;
import implementation.ImplChromosome;
import implementation.ImplGene;
import io.jenetics.Genotype;
import io.jenetics.Phenotype;
import io.jenetics.util.ISeq;
import io.jenetics.util.RandomRegistry;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
public abstract class ACPGeneticWrapper {
    protected final static Random random = RandomRegistry.getRandom();
    protected CPWrapper cpWrapper;

    // Calculates which variable has highest heuristic value. Returns its index.
    public abstract int calculateHeuristicBest(ISeq<ImplGene> values);

    // Generates random value for variable indexed with index.
    public abstract int generateRandomValue(int index);

    public abstract double calculateUtility(ISeq<ImplGene> genes);

    public abstract int countViolatedConstraints(ISeq<ImplGene> genes);

    public abstract boolean isValid(int value, int index);

    public abstract int getSize();

    public static List<Integer> genotypeToIntegerList(Genotype<ImplGene> genotype) {
        return chromosomeToIntegerList((ImplChromosome) genotype.getChromosome());
    }

    private static List<Integer> chromosomeToIntegerList(ImplChromosome chromosome) {
        return genesToIntegerList(chromosome.toSeq());
    }

    protected static List<Integer> genesToIntegerList(ISeq<ImplGene> genes) {
        List<Integer> list = new ArrayList<>();

        for (ImplGene gene : genes)
            list.add(gene.getAllele());

        return list;
    }
}
