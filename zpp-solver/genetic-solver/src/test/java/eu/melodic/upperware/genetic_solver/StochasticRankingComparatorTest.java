package eu.melodic.upperware.genetic_solver;

import eu.melodic.upperware.genetic_solver.cp_genetic_wrapper.ACPGeneticWrapper;
import eu.melodic.upperware.genetic_solver.jenetics_implementation.EvalFunction;
import eu.melodic.upperware.genetic_solver.jenetics_implementation.ChromosomeImpl;
import eu.melodic.upperware.genetic_solver.jenetics_implementation.GeneImpl;
import eu.melodic.upperware.genetic_solver.comparators.StochasticRankingComparator;
import io.jenetics.Genotype;
import io.jenetics.Phenotype;
import io.jenetics.util.MSeq;
import org.junit.Test;
import eu.melodic.upperware.genetic_solver.utility.CPGeneticWrapperA;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import static java.util.Collections.reverseOrder;
import static org.junit.Assert.assertEquals;

public class StochasticRankingComparatorTest {
    @Test
    public void checkComparatorWithoutRandomnessAndBrokenConstraints() {
        Comparator<Phenotype<GeneImpl, Double>> comparator = new StochasticRankingComparator(0);
        ACPGeneticWrapper geneticWrapper = new CPGeneticWrapperA(null);

        GeneImpl[] g = new GeneImpl[8];

        g[0] = new GeneImpl(0, 0, geneticWrapper);
        g[1] = new GeneImpl(0, 1, geneticWrapper);
        g[2] = new GeneImpl(0, 2, geneticWrapper);
        g[3] = new GeneImpl(0, 3, geneticWrapper);
        g[4] = new GeneImpl(1, 0, geneticWrapper);
        g[5] = new GeneImpl(1, 1, geneticWrapper);
        g[6] = new GeneImpl(1, 2, geneticWrapper);
        g[7] = new GeneImpl(1, 3, geneticWrapper);

        List<GeneImpl> lq1 = new ArrayList<>();
        List<GeneImpl> lq2 = new ArrayList<>();
        List<GeneImpl> lq3 = new ArrayList<>();
        List<GeneImpl> lq4 = new ArrayList<>();
        List<GeneImpl> lq5 = new ArrayList<>();


        // Best list with fitness 2.
        lq1.add(g[4]); lq1.add(g[1]); lq1.add(g[6]); lq1.add(g[3]);
        // Second best list with fitness 1.
        lq2.add(g[4]); lq2.add(g[1]); lq2.add(g[6]); lq2.add(g[7]);
        // Mid list with fitness 0.
        lq3.add(g[0]); lq3.add(g[1]); lq3.add(g[2]); lq3.add(g[3]);
        // Second worst list with fitness -1.
        lq4.add(g[0]); lq4.add(g[5]); lq4.add(g[2]); lq4.add(g[3]);
        // Worst list with fitness -2.
        lq5.add(g[0]); lq5.add(g[5]); lq5.add(g[2]); lq5.add(g[7]);


        Phenotype<GeneImpl, Double> pq1 = Phenotype.of(Genotype.of(new ChromosomeImpl(MSeq.of(lq1).toISeq(), 4, geneticWrapper)), 0);
        Phenotype<GeneImpl, Double> pq2 = Phenotype.of(Genotype.of(new ChromosomeImpl(MSeq.of(lq2).toISeq(), 4, geneticWrapper)), 0);
        Phenotype<GeneImpl, Double> pq3 = Phenotype.of(Genotype.of(new ChromosomeImpl(MSeq.of(lq3).toISeq(), 4, geneticWrapper)), 0);
        Phenotype<GeneImpl, Double> pq4 = Phenotype.of(Genotype.of(new ChromosomeImpl(MSeq.of(lq4).toISeq(), 4, geneticWrapper)), 0);
        Phenotype<GeneImpl, Double> pq5 = Phenotype.of(Genotype.of(new ChromosomeImpl(MSeq.of(lq5).toISeq(), 4, geneticWrapper)), 0);

        Function<Genotype<GeneImpl>, Double> fun = new EvalFunction();

        pq1 = pq1.eval(fun);
        pq2 = pq2.eval(fun);
        pq3 = pq3.eval(fun);
        pq4 = pq4.eval(fun);
        pq5 = pq5.eval(fun);


        List<Phenotype<GeneImpl, Double>> popq = new ArrayList<>();
        popq.add(pq4);
        popq.add(pq2);
        popq.add(pq3);
        popq.add(pq1);
        popq.add(pq5);


        MSeq<Phenotype<GeneImpl, Double>> populationq = MSeq.of(popq);

        populationq.sort(reverseOrder(comparator));


        assertEquals(populationq.asList().get(0), pq1);
        assertEquals(populationq.asList().get(1), pq2);
        assertEquals(populationq.asList().get(2), pq3);
        assertEquals(populationq.asList().get(3), pq4);
        assertEquals(populationq.asList().get(4), pq5);
    }
}