package implementation;

import cPGeneticWrapper.ACPGeneticWrapper;
import io.jenetics.Chromosome;
import io.jenetics.Mutator;
import io.jenetics.MutatorResult;
import io.jenetics.util.ISeq;
import java.util.Random;

/*
    Mutator mutates chromosome by changing it's genes values.
    When chromosome breaks some constraint heuristic approach is used.
 */
public class ImplMutator extends Mutator<ImplGene, Double> {
    private ACPGeneticWrapper geneticWrapper;

    public ImplMutator(double probability, ACPGeneticWrapper geneticWrapper) {
        super(probability);
        this.geneticWrapper = geneticWrapper;

    }

    @Override
    protected MutatorResult<Chromosome<ImplGene>> mutate(
         final Chromosome<ImplGene> chromosome,
         final double p,
         final Random random) {
        // No broken constraints -> mutate normally.
        if (((ImplChromosome) chromosome).getBrokenConstraints() == 0)
            return super.mutate(chromosome, p, random);

        // There are broken constraints mutate variable that breaks most constraints.
        ISeq<ImplGene> genes = chromosome.toSeq();
        int index = geneticWrapper.calculateHeuristicBest(genes);
        int currentValue, initValue, bestValue = genes.get(index).getAllele();
        double currentUtility, bestUtility = ((ImplChromosome) chromosome).getUtility();


        initValue = bestValue;
        currentValue = initValue;

        /* We're going to check whether we should be decreasing or increasing value in order to get better result. */
        double increasedUtility = -1, decreasedUtility = -1;
        int direction = 0;
        currentValue--;
        if (geneticWrapper.isValid(currentValue, index)) {
            genes.asMSeq().set(index, genes.get(index).newInstance(currentValue));
            decreasedUtility = geneticWrapper.calculateUtility(genes);
        }
        currentValue += 2;
        if (geneticWrapper.isValid(currentValue, index)) {
            genes.asMSeq().set(index, genes.get(index).newInstance(currentValue));
            increasedUtility = geneticWrapper.calculateUtility(genes);
        }

        if (increasedUtility > bestUtility && increasedUtility > decreasedUtility)
            direction = 1;
        else if (decreasedUtility > bestUtility)
            direction = -1;

        /* Increase gene's values as long as utility increases. */
        while (direction != 0) {
            currentValue += direction;

            if (geneticWrapper.isValid(currentValue, index)) {
                genes.asMSeq().set(index, genes.get(index).newInstance(currentValue));
                currentUtility = geneticWrapper.calculateUtility(genes);

                if (currentUtility > bestUtility) {
                    bestValue = currentValue;
                    bestUtility = currentUtility;
                }
                else
                    // Utility no longer increases -> break from while.
                    direction = 0;

            }
            else
                // Value no longer valid -> break from while.
                direction = 0;
        }

        // Modify chromosome using best value.
        genes.asMSeq().set(index, genes.get(index).newInstance(bestValue));

        return MutatorResult.of(new ImplChromosome(genes, genes.length(), geneticWrapper));
    }
}
