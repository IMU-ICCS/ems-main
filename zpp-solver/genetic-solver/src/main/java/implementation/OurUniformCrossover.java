package implementation;

import io.jenetics.Chromosome;
import io.jenetics.Genotype;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OurUniformCrossover extends OurCrossover {

    public OurUniformCrossover(int alterations) {
        super(alterations);
    }

    @Override
    protected Genotype<OurGene> crossover(Genotype<OurGene> gt1, Genotype<OurGene> gt2, Random random) {

        assert gt1.length() == gt2.length();

        int len = gt1.length();
        List<Chromosome<OurGene>> chromosomes = new ArrayList<>();

        for (int i = 0; i < len; i++) {
            if (random.nextBoolean()) {
                chromosomes.add(gt1.get(i));
            } else {
                chromosomes.add(gt2.get(i));
            }
        }

        return Genotype.of(chromosomes);
    }
}
