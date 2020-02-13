package eu.melodic.upperware.genetic_solver.comparators;

import io.jenetics.Phenotype;
import eu.melodic.upperware.genetic_solver.jenetics_implementation.ChromosomeImpl;
import eu.melodic.upperware.genetic_solver.jenetics_implementation.GeneImpl;
import lombok.AllArgsConstructor;

import java.util.Comparator;

/*
* Not a real comparator as its a non deterministic comparator!!!
* It means it can't be used in Collections.sort.
*/

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
