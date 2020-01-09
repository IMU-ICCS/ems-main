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
    static final Random random = RandomRegistry.getRandom();
    private double probability;

    @Override
    public int compare(Phenotype<ImplGene, Double> left, Phenotype<ImplGene, Double> right) {
        double leftUtility, rightUtility; // Utility value.
        int leftBroken, rightBroken; // Number of broken constraints.

        ImplChromosome l = (ImplChromosome) left.getGenotype().getChromosome();
        ImplChromosome r = (ImplChromosome) right.getGenotype().getChromosome();

        leftUtility = left.getFitness();
        rightUtility = right.getFitness();
        leftBroken = l.getBrokenConstraints();
        rightBroken = r.getBrokenConstraints();

        if (leftUtility == rightUtility && leftBroken == rightBroken)
            return 0;

        if (leftBroken == rightBroken)
            return Double.compare(leftUtility, rightUtility);
        else if ((l.isFeasible() || r.isFeasible()) && random.nextDouble() < probability)
            return Double.compare(leftUtility, rightUtility);
        else
            return Integer.compare(rightBroken, leftBroken);
    }
}
