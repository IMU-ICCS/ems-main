package implementation;

import io.jenetics.*;
import io.jenetics.Chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OurSinglePointCrossover extends OurCrossover{

    public OurSinglePointCrossover(int alterations) {
        super(alterations);
    }

    @Override
    protected Genotype<OurGene> crossover(Genotype<OurGene> gt1, Genotype<OurGene> gt2, Random random) {

        assert gt1.length() == gt2.length();
        int crossIndex = random.nextInt(gt1.length());

        List<Chromosome<OurGene>> chromosomes = new ArrayList<>();

        for (Chromosome<OurGene> chromosome : gt1.toSeq().subSeq(0, crossIndex))
            chromosomes.add(chromosome);

        for (Chromosome<OurGene> chromosome : gt2.toSeq().subSeq(crossIndex + 1))
            chromosomes.add(chromosome);

        return Genotype.of(chromosomes);
    }

}
