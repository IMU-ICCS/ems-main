package implementation;

import cPGeneticWrapper.ACPGeneticWrapper;
import comparators.AssignmentComparator;
import io.jenetics.Chromosome;
import io.jenetics.Gene;
import io.jenetics.Mutator;
import io.jenetics.MutatorResult;
import io.jenetics.util.ISeq;
import io.jenetics.util.MSeq;

import java.util.Random;

/*
    Mutator mutates chromosome by changing it's genes values.
    When chromosome breaks some constraint heuristic approach is used.
 */
public class ImplMutator extends Mutator<ImplGene, Double> {
    private ACPGeneticWrapper geneticWrapper;
    private int guesses;

    public ImplMutator(double probability, ACPGeneticWrapper geneticWrapper, int guesses) {
        super(probability);
        this.geneticWrapper = geneticWrapper;
        this.guesses = guesses;
    }


    @Override
    protected MutatorResult<Chromosome<ImplGene>> mutate(
         final Chromosome<ImplGene> chromosome,
         final double p,
         final Random random) {
        // No broken constraints -> mutate normally.
        if (((ImplChromosome) chromosome).getBrokenConstraints() == 0)
            return super.mutate(chromosome, p, random);

        // We should mutate randomly sometimes in order to leave local optimum.
        if (random.nextDouble() < getProbability())
            return mutateRandomly(chromosome, p, random);

        // There are broken constraints mutate variable that breaks most constraints.
        MSeq<ImplGene> genes = chromosome.toSeq().asMSeq();
        ImplGene mutatedGene;
        ImplChromosome tmp, best, decreasingChromosome = null, increasingChromosome = null;
        int index = geneticWrapper.calculateHeuristicBest(genes.asISeq());
        int currentValue, initValue = genes.get(index).getAllele();

        mutatedGene = genes.get(index);
        currentValue = initValue;
        best = (ImplChromosome) chromosome;

        /* We're going to check whether we should be decreasing or increasing value in order to get better result. */
        int direction = 0;
        currentValue--;
        if (geneticWrapper.isValid(currentValue, index)) {
            genes.set(index, mutatedGene.newInstance(currentValue));
            decreasingChromosome = (ImplChromosome) best.newInstance(genes.asISeq());
        }
        currentValue += 2;
        if (geneticWrapper.isValid(currentValue, index)) {
            genes.set(index, mutatedGene.newInstance(currentValue));
            increasingChromosome = (ImplChromosome) best.newInstance(genes.asISeq());
        }

        if (increasingChromosome == null && decreasingChromosome != null &&
                AssignmentComparator.compare(decreasingChromosome, best, 0) > 0)
            direction = -1;
        else if (increasingChromosome != null && decreasingChromosome == null &&
                AssignmentComparator.compare(increasingChromosome, best, 0) > 0)
            direction = 1;
        else if (increasingChromosome != null && decreasingChromosome != null) {
            if (AssignmentComparator.compare(increasingChromosome, best, 0) > 0)
                direction = 1;
            else if (AssignmentComparator.compare(decreasingChromosome, best, 0) > 0)
                direction = -1;
        }

        currentValue = initValue;
        /* Increase gene's values as long as utility increases. */
        while (direction != 0) {
            currentValue += direction;

            if (geneticWrapper.isValid(currentValue, index)) {
                genes.set(index, mutatedGene.newInstance(currentValue));
                tmp = (ImplChromosome) best.newInstance(genes.asISeq());

                if (AssignmentComparator.compare(tmp, best, 0) > 0)
                    best = tmp;
                else
                    // Utility no longer increases -> break from while.
                    direction = 0;

            }
            else
                // Value no longer valid -> break from while.
                direction = 0;
        }

        // Modify chromosome using best value.
        genes.set(index, mutatedGene.newInstance(best.toSeq().get(index).getAllele()));
        return MutatorResult.of(new ImplChromosome(genes.asISeq(), genes.length(), geneticWrapper));
    }

    /* Mutates randomly *guesses* times and returns the best guess. */
    protected MutatorResult<Chromosome<ImplGene>> mutateRandomly(
            final Chromosome<ImplGene> chromosome,
            final double p,
            final Random random) {

        ImplChromosome best = (ImplChromosome) chromosome, tmp;
        MSeq<ImplGene> genes = best.toSeq().asMSeq();
        int index = geneticWrapper.calculateHeuristicBest(genes.asISeq());
        ImplGene mutatedGene;

        mutatedGene = genes.get(index);

        for (int i = 0; i < guesses; i++) {
            genes.set(index, mutatedGene.newInstance());
            tmp = (ImplChromosome) best.newInstance(genes.asISeq());

            if (AssignmentComparator.compare(tmp, best, 0) > 0)
                best = tmp;
        }

        genes.set(index, mutatedGene.newInstance(best.toSeq().get(index).getAllele()));
        return MutatorResult.of(new ImplChromosome(genes.asISeq(), genes.length(), geneticWrapper));
    }
}
