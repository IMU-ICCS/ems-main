package implementation;

import cPGeneticWrapper.ACPGeneticWrapper;
import comparators.AssignmentComparator;
import io.jenetics.Chromosome;
import io.jenetics.Mutator;
import io.jenetics.MutatorResult;
import io.jenetics.util.MSeq;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

/*
    Mutator mutates chromosome by changing it's genes values.
    When chromosome breaks some constraint heuristic approach is used.
 */
@Slf4j
public class ImplMutator extends Mutator<ImplGene, Double> {
    private ACPGeneticWrapper geneticWrapper;
    private double probability;

    public ImplMutator(double probability, ACPGeneticWrapper geneticWrapper, double mutatorProbability) {
        super(probability);
        this.geneticWrapper = geneticWrapper;
        this.probability = mutatorProbability;
    }


    @Override
    protected MutatorResult<Chromosome<ImplGene>> mutate(
         final Chromosome<ImplGene> chromosome,
         final double p,
         final Random random) {
        //log.info("Mutate start");
        // No broken constraints -> mutate normally.
        /*if (((ImplChromosome) chromosome).getBrokenConstraints() == 0)
            return super.mutate(chromosome, p, random);
*/
        // We should mutate randomly sometimes in order to leave local optimum.
        if (random.nextDouble() < probability)
            return mutateRandomly(chromosome, p, random);
        else return MutatorResult.of(chromosome);

    }

    /* Mutates randomly *guesses* times and returns the best guess. */
    protected MutatorResult<Chromosome<ImplGene>> mutateRandomly(
            final Chromosome<ImplGene> chromosome,
            final double p,
            final Random random) {
        //log.info("Mutate radomly");
        ImplChromosome best = (ImplChromosome) chromosome;
        MSeq<ImplGene> genes = best.toSeq().asMSeq();
        int index = random.nextInt(genes.asISeq().length());
       genes.set(index, new ImplGene(geneticWrapper.generateRandomValue(index), index, geneticWrapper));

        //log.info("Mutate radomly end");
        return MutatorResult.of(new ImplChromosome(genes.asISeq(), genes.length(), geneticWrapper));
    }
}
