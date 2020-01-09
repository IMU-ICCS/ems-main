import cPGeneticWrapper.ACPGeneticWrapper;
import implementation.EvalFunction;
import implementation.ImplChromosome;
import implementation.ImplGene;
import comparators.StochasticRankingComparator;
import io.jenetics.Genotype;
import io.jenetics.Phenotype;
import io.jenetics.util.MSeq;
import org.junit.Test;
import utility.CPGeneticWrapperA;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import static java.util.Collections.reverseOrder;
import static org.junit.Assert.assertEquals;

public class StochasticRankingComparatorTest {
    @Test
    public void checkComparatorWithoutRandomnessAndBrokenConstraints() {
        Comparator<Phenotype<ImplGene, Double>> comparator = new StochasticRankingComparator(0);
        ACPGeneticWrapper geneticWrapper = new CPGeneticWrapperA(null);

        ImplGene[] g = new ImplGene[8];

        g[0] = new ImplGene(0, 0, geneticWrapper);
        g[1] = new ImplGene(0, 1, geneticWrapper);
        g[2] = new ImplGene(0, 2, geneticWrapper);
        g[3] = new ImplGene(0, 3, geneticWrapper);
        g[4] = new ImplGene(1, 0, geneticWrapper);
        g[5] = new ImplGene(1, 1, geneticWrapper);
        g[6] = new ImplGene(1, 2, geneticWrapper);
        g[7] = new ImplGene(1, 3, geneticWrapper);

        List<ImplGene> lq1 = new ArrayList<>();
        List<ImplGene> lq2 = new ArrayList<>();
        List<ImplGene> lq3 = new ArrayList<>();
        List<ImplGene> lq4 = new ArrayList<>();
        List<ImplGene> lq5 = new ArrayList<>();


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


        Phenotype<ImplGene, Double> pq1 = Phenotype.of(Genotype.of(new ImplChromosome(MSeq.of(lq1).toISeq(), 4, geneticWrapper)), 0);
        Phenotype<ImplGene, Double> pq2 = Phenotype.of(Genotype.of(new ImplChromosome(MSeq.of(lq2).toISeq(), 4, geneticWrapper)), 0);
        Phenotype<ImplGene, Double> pq3 = Phenotype.of(Genotype.of(new ImplChromosome(MSeq.of(lq3).toISeq(), 4, geneticWrapper)), 0);
        Phenotype<ImplGene, Double> pq4 = Phenotype.of(Genotype.of(new ImplChromosome(MSeq.of(lq4).toISeq(), 4, geneticWrapper)), 0);
        Phenotype<ImplGene, Double> pq5 = Phenotype.of(Genotype.of(new ImplChromosome(MSeq.of(lq5).toISeq(), 4, geneticWrapper)), 0);

        Function<Genotype<ImplGene>, Double> fun = new EvalFunction();

        pq1 = pq1.eval(fun);
        pq2 = pq2.eval(fun);
        pq3 = pq3.eval(fun);
        pq4 = pq4.eval(fun);
        pq5 = pq5.eval(fun);


        List<Phenotype<ImplGene, Double>> popq = new ArrayList<>();
        popq.add(pq4);
        popq.add(pq2);
        popq.add(pq3);
        popq.add(pq1);
        popq.add(pq5);


        MSeq<Phenotype<ImplGene, Double>> populationq = MSeq.of(popq);

        populationq.sort(reverseOrder(comparator));


        assertEquals(populationq.asList().get(0), pq1);
        assertEquals(populationq.asList().get(1), pq2);
        assertEquals(populationq.asList().get(2), pq3);
        assertEquals(populationq.asList().get(3), pq4);
        assertEquals(populationq.asList().get(4), pq5);
    }
}