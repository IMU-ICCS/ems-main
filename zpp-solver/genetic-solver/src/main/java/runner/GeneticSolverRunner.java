package runner;

import comparators.StochasticRankingComparator;
import cp_genetic_wrapper.ACPGeneticWrapper;
import cp_genetic_wrapper.CPGeneticWrapper;
import cp_wrapper.CPWrapper;
import cp_wrapper.utility_provider.UtilityProvider;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.Limits;
import io.jenetics.util.Factory;
import jenetics_implementation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class GeneticSolverRunner {
    @Setter
    private Integer populationSize = 100;
    @Setter
    private Integer iterations = 100;
    @Setter
    private double crossoverProbability = 0.2;
    @Setter
    private double mutatorProbability = 0.3;
    @Setter
    private double mutationProbability = 0.1;
    @Setter
    private double comparatorProbability = 0.1;
    @Setter
    private int guesses = 10;
    @Setter
    private int timeLimit = 0;
    @Getter
    private double finalUtility;

    private final Function<Genotype<GeneImpl>, Double> fitnessFunction = new EvalFunction();

    public List<Integer> run(ConstraintProblem cp, UtilityProvider utility) {
        CPWrapper cpWrapper = new CPWrapper();
        cpWrapper.parse(cp, utility);

        return run(cpWrapper);
    }

    public List<Integer> run(CPWrapper cpWrapper) {
        return run(new CPGeneticWrapper(cpWrapper));
    }

    private List<Integer> run(ACPGeneticWrapper geneticWrapper) {
        Alterer<GeneImpl, Double> crossoverAlterer = new SinglePointCrossover<>(crossoverProbability);
        Mutator<GeneImpl, Double> mutator = new MutatorImpl(mutationProbability, geneticWrapper, guesses, mutatorProbability);
        Selector<GeneImpl, Double> selector = new SelectorImpl();
        ChromosomeImpl finalChromosome;

        log.info("Starting runner.");
        log.info("Population size: " + populationSize);
        log.info((timeLimit == 0 ? "Iterations: " + iterations : "Time limit: " + timeLimit));
        log.info("Crossover probability: " + crossoverProbability);
        log.info("Mutator probability: " + mutatorProbability);
        log.info("Mutation probability: " + mutationProbability);
        log.info("Comparator probability: " + comparatorProbability);
        log.info("Mutator guesses: " + guesses);
        log.info("Problem size: " + geneticWrapper.getSize());

        Factory<Genotype<GeneImpl>> initialPopulation =
                Genotype.of(ChromosomeImpl.of(populationSize, geneticWrapper.getSize(), geneticWrapper));

        final Engine<GeneImpl, Double> engine =
                Engine.builder(fitnessFunction, initialPopulation)
                        .alterers(crossoverAlterer, mutator)
                        .selector(selector)
                        .build();

        if (timeLimit == 0) {
            finalChromosome = (ChromosomeImpl) (engine.stream().limit(iterations)
                    .collect(EvolutionResult.toBestGenotype()).getChromosome());
        } else {
            finalChromosome = (ChromosomeImpl) (engine.stream().limit(Limits.byExecutionTime(Duration.ofSeconds(timeLimit)))
                    .collect(EvolutionResult.toBestGenotype()).getChromosome());
        }
        finalUtility = finalChromosome.getUtility();

        return ACPGeneticWrapper.chromosomeToIntegerList(finalChromosome);
    }

}
