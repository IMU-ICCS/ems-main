package implementation;

import io.jenetics.Genotype;

import java.util.function.Function;

public class EvalFunction implements Function<Genotype<ImplGene>, Double> {
    @Override
    public Double apply(Genotype<ImplGene> chromosomes) {
        ImplChromosome chromosome = (ImplChromosome) chromosomes.getChromosome();

        if (chromosome.getBrokenConstraints() > 0)
            return 0.0;
        return  chromosome.getUtility();
    }
}
