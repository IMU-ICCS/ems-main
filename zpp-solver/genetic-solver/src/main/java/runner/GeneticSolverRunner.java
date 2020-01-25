package runner;

import comparators.StochasticRankingComparator;
import cp_genetic_wrapper.ACPGeneticWrapper;
import cp_genetic_wrapper.CPGeneticWrapper;
import cp_wrapper.CPWrapper;
import cp_wrapper.utility_provider.UtilityProvider;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import implementation.*;
import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.Limits;
import io.jenetics.util.Factory;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

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


    private final Function<Genotype<ImplGene>, Double> fitnessFunction = new EvalFunction();

    public List<Integer> run(ConstraintProblem cp, UtilityProvider utility) {
        CPWrapper cpWrapper = new CPWrapper();
        cpWrapper.parse(cp, utility);

        return run(cpWrapper);
    }

    public List<Integer> run(CPWrapper cpWrapper) {
        ACPGeneticWrapper cpGeneticWrapper = new CPGeneticWrapper(cpWrapper);

        return run(cpGeneticWrapper);
    }

    public List<Integer> run(ACPGeneticWrapper geneticWrapper) {
        Alterer<ImplGene, Double> crossoverAlterer = new SinglePointCrossover<>(crossoverProbability);
        Mutator<ImplGene, Double> mutator = new ImplMutator(mutationProbability, geneticWrapper, guesses, mutatorProbability);
        Selector<ImplGene, Double> selector = new ImplSelector(new StochasticRankingComparator(comparatorProbability));
        ImplChromosome finalChromosome;

        Factory<Genotype<ImplGene>> gtf =
                Genotype.of(ImplChromosome.of(populationSize, geneticWrapper.getSize(), geneticWrapper));

        final Engine<ImplGene, Double> engine =
                Engine.builder(fitnessFunction, gtf)
                        .alterers(crossoverAlterer, mutator)
                        .selector(selector)
                        .build();

        if (timeLimit == 0)
            finalChromosome = (ImplChromosome) (engine.stream().limit(iterations)
                    .collect(EvolutionResult.toBestGenotype()).getChromosome());
        else
            finalChromosome = (ImplChromosome) (engine.stream().limit(Limits.byExecutionTime(Duration.ofSeconds(timeLimit)))
                    .collect(EvolutionResult.toBestGenotype()).getChromosome());
        finalUtility = finalChromosome.getUtility();
        return ACPGeneticWrapper.chromosomeToIntegerList(finalChromosome);
    }

}
