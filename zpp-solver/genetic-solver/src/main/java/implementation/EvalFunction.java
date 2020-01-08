package implementation;

import io.jenetics.Genotype;

import java.util.function.Function;

public class EvalFunction implements Function<Genotype<OurGene>, Double> {
    @Override
    public Double apply(Genotype<OurGene> chromosomes) {
        return ((OurChromosome) chromosomes.getChromosome()).getUtility();
    }
}
