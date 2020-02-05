package comparators;

import io.jenetics.Phenotype;
import jenetics_implementation.ChromosomeImpl;
import jenetics_implementation.GeneImpl;
import lombok.AllArgsConstructor;

import java.util.Comparator;

@AllArgsConstructor
public class StochasticRankingComparator implements Comparator<Phenotype<GeneImpl, Double>> {
    private double probability;

    @Override
    public int compare(Phenotype<GeneImpl, Double> left, Phenotype<GeneImpl, Double> right) {

        ChromosomeImpl l = (ChromosomeImpl) left.getGenotype().getChromosome();
        ChromosomeImpl r = (ChromosomeImpl) right.getGenotype().getChromosome();

        return AssignmentComparator.compare(l, r, probability);
    }
}
