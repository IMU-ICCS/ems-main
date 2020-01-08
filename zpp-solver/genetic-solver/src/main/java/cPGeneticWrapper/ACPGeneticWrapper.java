package cPGeneticWrapper;

import cp_wrapper.CPWrapper;
import implementation.OurChromosome;
import implementation.OurGene;
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
    public abstract int calculateHeuristicBest(ISeq<OurGene> values);

    // Generates random value for variable indexed with index.
    public abstract int generateRandomValue(int index);

    public abstract double calculateUtility(ISeq<OurGene> genes);

    public abstract int countViolatedConstraints(ISeq<OurGene> genes);

    public abstract boolean isValid(int value, int index);

    public abstract boolean getIsFeasible(ISeq<OurGene> genes);

    public abstract int getSize();


    public static List<Integer> phenotypeToIntegerList(Phenotype<OurGene, Double> phenotype) {
        return genotypeToIntegerList(phenotype.getGenotype());
    }

    public static List<Integer> genotypeToIntegerList(Genotype<OurGene> genotype) {
        return chromosomeToIntegerList((OurChromosome) genotype.getChromosome());
    }

    private static List<Integer> chromosomeToIntegerList(OurChromosome chromosome) {
        return genesToIntegerList(chromosome.toSeq());
    }

    protected static List<Integer> genesToIntegerList(ISeq<OurGene> genes) {
        List<Integer> list = new ArrayList<>();

        for (OurGene gene : genes)
            list.add(gene.getAllele());

        return list;
    }
}
