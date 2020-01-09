package implementation;

import io.jenetics.Genotype;

import java.util.function.Function;

public class EvalFunction implements Function<Genotype<ImplGene>, Double> {
    @Override
    public Double apply(Genotype<ImplGene> chromosomes) {
        return ((ImplChromosome) chromosomes.getChromosome()).getUtility();
    }
}
