package jenetics_implementation;

import comparators.AssignmentComparator;
import cp_genetic_wrapper.ACPGeneticWrapper;
import io.jenetics.Chromosome;
import io.jenetics.Mutator;
import io.jenetics.MutatorResult;
import io.jenetics.util.MSeq;

import java.util.List;
import java.util.Random;

/*
    Mutator mutates chromosome by changing it's genes values.
    When chromosome breaks some constraint heuristic approach is used.
 */
public class MutatorImpl extends Mutator<GeneImpl, Double> {
    private ACPGeneticWrapper geneticWrapper;
    private double probability;
    private int guesses;

    private enum Direction {
        up,
        none,
        down
    }

    public MutatorImpl(double probability, ACPGeneticWrapper geneticWrapper, int guesses, double mutatorProbability) {
        super(probability);
        this.geneticWrapper = geneticWrapper;
        this.guesses = guesses;
        this.probability = mutatorProbability;
    }

    /* Checks which direction gives better approximation. */
    private Direction checkDirection(int initValue, int index, int bestHeuristic, List<Integer> assignments) {
        int decreasingValue, increasingValue, currentValue;
        boolean decreasingValid, increasingValid;

        // Setting values for decreasingValue and increasingValue so that IDE doesn't cry.
        decreasingValue = increasingValue = Integer.MIN_VALUE;
        decreasingValid = increasingValid = false;
        Direction direction = Direction.none;

        // Checking which direction  gives better heuristic evaluation. Up (+1) or down (-1).
        currentValue = initValue - 1;
        if (geneticWrapper.isValid(currentValue, index)) {
            assignments.set(index, currentValue);
            decreasingValue = geneticWrapper.getHeuristicEvaluation(assignments, index);
            decreasingValid = true;
        }

        currentValue = initValue + 1;
        if (geneticWrapper.isValid(currentValue, index)) {
            assignments.set(index, currentValue);
            increasingValue = geneticWrapper.getHeuristicEvaluation(assignments, index);
            increasingValid = true;
        }

        if (!increasingValid && decreasingValid &&
                decreasingValue < bestHeuristic) {
            direction = Direction.down;
        }
        else if (increasingValid && !decreasingValid &&
                increasingValue < bestHeuristic) {
            direction = Direction.up;
        }
        else if (increasingValid && decreasingValid) {
            if (increasingValue < bestHeuristic) {
                direction = Direction.up;
            }
            else if (decreasingValue < bestHeuristic) {
                direction = Direction.down;
            }
        }
        return direction;
    }

    /* Increases gene's values as long as heuristicEvaluation increases. Returns value for variable. */
    public int adjust(int bestHeuristic, int initValue, Direction direction, List<Integer> assignments, int index) {
        int currentValue = initValue, currentEvaluation, bestValue = initValue;
        while (true) {
            if (direction == Direction.up) {
                currentValue++;
            } else {
                currentValue--;
            }

            if (geneticWrapper.isValid(currentValue, index)) {
                assignments.set(index, currentValue);
                currentEvaluation = geneticWrapper.getHeuristicEvaluation(assignments, index);


                if (currentEvaluation < bestHeuristic) {
                    bestHeuristic = currentEvaluation;
                    bestValue = currentValue;
                } else {
                    // Utility no longer increases -> we got result.
                    return bestValue;
                }

            } else {
                // Value no longer valid -> we got result.
                return bestValue;
            }
        }
    }

    @Override
    protected MutatorResult<Chromosome<GeneImpl>> mutate(final Chromosome<GeneImpl> chromosome, final double p,
                                                         final Random random) {
        // No broken constraints -> mutate normally.
        if (((ChromosomeImpl) chromosome).getBrokenConstraints() == 0)
            return super.mutate(chromosome, p, random);

        // We should mutate randomly sometimes in order to leave local optimum.
        if (random.nextDouble() < probability)
            return mutateRandomly(chromosome);

        // There are broken constraints mutate variable that breaks most constraints.
        List<Integer> assignments = ACPGeneticWrapper.chromosomeToIntegerList((ChromosomeImpl) chromosome);
        int bestValue, initValue, index, bestHeuristic;

        index = geneticWrapper.calculateHeuristicBest(chromosome.toSeq());
        initValue = assignments.get(index);
        bestHeuristic = geneticWrapper.getHeuristicEvaluation(assignments, index);


        /* We're going to check whether we should be decreasing or increasing value in order to get better result. */
        Direction direction = checkDirection(initValue, index, bestHeuristic, assignments);

        /* Adjust gene's values as long as utility increases. */
        bestValue = adjust(bestHeuristic, initValue, direction, assignments, index);

        // Modify chromosome using best value.
        MSeq<GeneImpl> genes = chromosome.toSeq().asMSeq();
        GeneImpl mutatedGene = genes.get(index);
        genes.set(index, mutatedGene.newInstance(bestValue));
        return MutatorResult.of(new ChromosomeImpl(genes.asISeq(), genes.length(), geneticWrapper));
    }

    /* Mutates randomly *guesses* times and returns the best guess. */
    protected MutatorResult<Chromosome<GeneImpl>> mutateRandomly(
            final Chromosome<GeneImpl> chromosome) {

        ChromosomeImpl best = (ChromosomeImpl) chromosome, current;
        MSeq<GeneImpl> genes = best.toSeq().asMSeq();
        int index = geneticWrapper.calculateHeuristicBest(genes.asISeq());
        GeneImpl mutatedGene;

        mutatedGene = genes.get(index);

        for (int i = 0; i < guesses; i++) {
            genes.set(index, mutatedGene.newInstance());
            current = (ChromosomeImpl) best.newInstance(genes.asISeq());

            if (AssignmentComparator.compare(current, best, 0) > 0)
                best = current;
        }

        genes.set(index, mutatedGene.newInstance(best.toSeq().get(index).getAllele()));
        return MutatorResult.of(new ChromosomeImpl(genes.asISeq(), genes.length(), geneticWrapper));
    }
}
