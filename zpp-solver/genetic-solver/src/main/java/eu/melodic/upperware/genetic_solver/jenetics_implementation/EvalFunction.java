package eu.melodic.upperware.genetic_solver.jenetics_implementation;

import io.jenetics.Genotype;

import java.util.function.Function;

// Jenetics uses this class to assess individuals' eu.melodic.upperware.genetic_solver.utility when selecting best solutions.
public class EvalFunction implements Function<Genotype<GeneImpl>, Double> {
    @Override
    public Double apply(Genotype<GeneImpl> chromosomes) {
        ChromosomeImpl chromosome = (ChromosomeImpl) chromosomes.getChromosome();

        if (chromosome.getBrokenConstraints() > 0) {
            return 0.0;
        }

        return chromosome.getUtility();
    }
}
