package implementation;

import io.jenetics.Phenotype;
import io.jenetics.util.RandomRegistry;

import java.util.Comparator;
import java.util.Random;

public class StochasticRankingComparator implements Comparator<Phenotype<OurGene, Double>> {
    static final Random random = RandomRegistry.getRandom();
    private double probability;

    public StochasticRankingComparator(double probability) {
        assert probability >= 0 && probability <= 1;
        this.probability = probability;
    }

    @Override
    public int compare(Phenotype<OurGene, Double> left, Phenotype<OurGene, Double> right) {
        double leftUtility, rightUtility;
        int leftBroken, rightBroken;


        // TODO Maybe such a casting isn't going to work.
        OurChromosome l = (OurChromosome) left.getGenotype().getChromosome();
        OurChromosome r = (OurChromosome) right.getGenotype().getChromosome();

        leftUtility = l.getUtility();
        rightUtility = r.getUtility();
        leftBroken = l.getBrokenConstraints();
        rightBroken = r.getBrokenConstraints();

        if (leftUtility == rightUtility && leftBroken == rightBroken)
            return 0;

        if (leftBroken == rightBroken)
            return Double.compare(leftUtility, rightUtility);
        else if ((l.getIsFeasible() || r.getIsFeasible()) && random.nextDouble() < probability)
            return Double.compare(leftUtility, rightUtility);
        else
            return Integer.compare(rightBroken, leftBroken);
    }
}
