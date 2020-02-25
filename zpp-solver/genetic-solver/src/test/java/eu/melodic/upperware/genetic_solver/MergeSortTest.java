package eu.melodic.upperware.genetic_solver;

import eu.melodic.upperware.genetic_solver.cp_genetic_wrapper.ACPGeneticWrapper;
import io.jenetics.Chromosome;
import io.jenetics.Genotype;
import io.jenetics.Phenotype;
import io.jenetics.util.ISeq;
import io.jenetics.util.MSeq;
import eu.melodic.upperware.genetic_solver.jenetics_implementation.ChromosomeImpl;
import eu.melodic.upperware.genetic_solver.jenetics_implementation.GeneImpl;
import org.junit.Test;
import eu.melodic.upperware.genetic_solver.sorting_algortihms.MergeSort;
import eu.melodic.upperware.genetic_solver.utility.merge_sort_utility.GeneticWrapperValueIsUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MergeSortTest {
    private static final Random rand = new Random();
    private ACPGeneticWrapper wrapper = new GeneticWrapperValueIsUtility( null);

    private Phenotype<GeneImpl, Double> generatePhenotype(int utility) {
        ISeq<GeneImpl> genes = ISeq.of(new GeneImpl(utility, 0, wrapper));
        Chromosome<GeneImpl> chromosome = new ChromosomeImpl(genes, 1, wrapper);
        Genotype<GeneImpl> genotype = Genotype.of(chromosome);
        return Phenotype.of(genotype, 0);
    }

    private double getFitness(Phenotype<GeneImpl, Double> p) {
        return ((ChromosomeImpl) p.getGenotype().getChromosome()).getUtility();
    }

    @Test
    public void smallEdgeCasesTest() {
        Phenotype<GeneImpl, Double> p1 = generatePhenotype(2);
        Phenotype<GeneImpl, Double> p2 = generatePhenotype(1);
        Phenotype<GeneImpl, Double> p3 = generatePhenotype(3);

        /* Sort 2, 1. */
        MSeq<Phenotype<GeneImpl, Double>> m = MSeq.ofLength(2);;
        m.set(0, p1);
        m.set(1, p2);

        MergeSort.sort(m);

        assertEquals(2, getFitness(m.get(0)), 0.1);
        assertEquals(1, getFitness(m.get(1)), 0.1);

        /* Sort 3. */
        p1 = generatePhenotype(3);
        m = MSeq.ofLength(1);
        m.set(0, p1);

        MergeSort.sort(m);

        assertEquals(getFitness(m.get(0)), 3.0, 0.1);

        /* Sort empty. */
        m = MSeq.ofLength(0);
        MergeSort.sort(m);

        /* Sort 1, 2, 3. */
        p1 = generatePhenotype(1);
        p2 = generatePhenotype(2);
        p3 = generatePhenotype(3);

        m = MSeq.ofLength(3);
        m.set(0, p1);
        m.set(1, p2);
        m.set(2, p3);

        MergeSort.sort(m);

        assertEquals(3, getFitness(m.get(0)), 0.1);
        assertEquals(2, getFitness(m.get(1)), 0.1);
        assertEquals(1, getFitness(m.get(2)), 0.1);
    }


    @Test
    public void simpleTest() {
        /* Try to sort values 7, 3, 1, 5, 4, 9, 1, 5, 4. */
        Phenotype<GeneImpl, Double> p1 = generatePhenotype(7);
        Phenotype<GeneImpl, Double> p2 = generatePhenotype(3);
        Phenotype<GeneImpl, Double> p3 = generatePhenotype(1);
        Phenotype<GeneImpl, Double> p4 = generatePhenotype(5);
        Phenotype<GeneImpl, Double> p5 = generatePhenotype(4);
        Phenotype<GeneImpl, Double> p6 = generatePhenotype(9);
        Phenotype<GeneImpl, Double> p7 = generatePhenotype(1);
        Phenotype<GeneImpl, Double> p8 = generatePhenotype(5);
        Phenotype<GeneImpl, Double> p9 = generatePhenotype(4);

        MSeq<Phenotype<GeneImpl, Double>> m = MSeq.ofLength(9);
        m.set(0, p1);
        m.set(1, p2);
        m.set(2, p3);
        m.set(3, p4);
        m.set(4, p5);
        m.set(5, p6);
        m.set(6, p7);
        m.set(7, p8);
        m.set(8, p9);

        MergeSort.sort(m);

        // Should be 9, 7, 5, 5, 4, 4, 3, 1, 1.
        assertEquals(9, getFitness(m.get(0)), 0.1);
        assertEquals(7, getFitness(m.get(1)), 0.1);
        assertEquals(5, getFitness(m.get(2)), 0.1);
        assertEquals(5, getFitness(m.get(3)), 0.1);
        assertEquals(4, getFitness(m.get(4)), 0.1);
        assertEquals(4, getFitness(m.get(5)), 0.1);
        assertEquals(3, getFitness(m.get(6)), 0.1);
        assertEquals(1, getFitness(m.get(7)), 0.1);
        assertEquals(1, getFitness(m.get(8)), 0.1);
    }

    @Test
    public void bigRandomTest() {
        int size = 50000; // Size of sample.
        int highestUtility = 10000; // Highest possible eu.melodic.upperware.genetic_solver.utility.

        List<Integer> l = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            l.add(rand.nextInt(highestUtility));
        }

        MSeq<Phenotype<GeneImpl, Double>> m = MSeq.ofLength(size);
        for (int i = 0; i < size; i++) {
            m.set(i, generatePhenotype(l.get(i)));
        }

        MergeSort.sort(m);
        l.sort(Integer::compareTo);

        for (int i = 0; i < size; i++) {
            assertEquals(l.get(size - i - 1), getFitness(m.get(i)), 0.1);
        }
    }
}
