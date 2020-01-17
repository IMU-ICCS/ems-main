package comparators;

import implementation.ImplChromosome;
import implementation.ImplGene;
import io.jenetics.Phenotype;
import io.jenetics.util.RandomRegistry;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.Random;

@AllArgsConstructor
public class StochasticRankingComparator implements Comparator<Phenotype<ImplGene, Double>> {
    private double probability;

    @Override
    public int compare(Phenotype<ImplGene, Double> left, Phenotype<ImplGene, Double> right) {

        ImplChromosome l = (ImplChromosome) left.getGenotype().getChromosome();
        ImplChromosome r = (ImplChromosome) right.getGenotype().getChromosome();

        return AssignmentComparator.compare(l, r, probability);
    }
}
