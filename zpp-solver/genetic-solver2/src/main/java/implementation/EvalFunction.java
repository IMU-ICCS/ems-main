package implementation;

import io.jenetics.Genotype;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;
@Slf4j
public class EvalFunction implements Function<Genotype<ImplGene>, Double> {
    private double best = 0;
    @Override
    public Double apply(Genotype<ImplGene> chromosomes) {
        log.info("Evaluating");
        ImplChromosome chromosome = (ImplChromosome) chromosomes.getChromosome();

        if (chromosome.getBrokenConstraints() > 0)
            return 0.0;
        if (best < chromosome.getUtility()) {
            best  = chromosome.getUtility();
        }
        log.info("Best s o far: " + best);
        return  chromosome.getUtility();
    }
}
