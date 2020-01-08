package implementation;

import io.jenetics.*;

import io.jenetics.util.RandomRegistry;
import io.jenetics.util.Seq;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class OurCrossover implements Alterer<OurGene, Integer> {

    private int alterations;
    private Random random = RandomRegistry.getRandom();

    OurCrossover(int alterations) {
        this.alterations = alterations;
    }

    @Override
    public AltererResult<OurGene, Integer> alter(Seq<Phenotype<OurGene, Integer>> population, long generation) {

        int i1;
        int i2;
        List<Phenotype<OurGene, Integer>> results = new ArrayList<>();

        for (int i = 0; i < alterations; i++) {

            i1 = random.nextInt(population.length());
            i2 = random.nextInt(population.length());

            Genotype<OurGene> gt1 = population.get(i1).getGenotype();
            Genotype<OurGene> gt2 = population.get(i2).getGenotype();

            Genotype<OurGene> newGenotype = crossover(gt1, gt2, random);

            results.add(Phenotype.of(newGenotype, generation + 1));
        }

        return AltererResult.of(population.append(results).asISeq());
    }

    abstract protected Genotype<OurGene> crossover(Genotype<OurGene> gt1, Genotype<OurGene> gt2, Random random);
}
