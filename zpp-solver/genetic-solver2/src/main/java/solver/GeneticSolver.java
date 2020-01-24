package solver;

import cPGeneticWrapper.ACPGeneticWrapper;
import cPGeneticWrapper.CPGeneticWrapper;
import comparators.StochasticRankingComparator;
import cp_wrapper.CPWrapper;
import cp_wrapper.utility_provider.UtilityProvider;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import implementation.*;
import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.Factory;
import lombok.Setter;

import java.time.Clock;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static io.jenetics.engine.Limits.byExecutionTime;

public class GeneticSolver {
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
        Mutator<ImplGene, Double> mutator = new ImplMutator(mutationProbability, geneticWrapper, mutatorProbability);
        Selector<ImplGene, Double> selector = new ImplSelector(new StochasticRankingComparator(comparatorProbability));

        Factory<Genotype<ImplGene>> gtf =
                Genotype.of(ImplChromosome.of(populationSize, geneticWrapper.getSize(), geneticWrapper));

        final Engine<ImplGene, Double> engine =
                Engine.builder(fitnessFunction, gtf)
                        .alterers(crossoverAlterer, mutator)
                        .selector(selector)
                        .build();
        Duration dur = Duration.ofSeconds(10);
        //return new ArrayList<>();
        return ACPGeneticWrapper.genotypeToIntegerList(
                engine.stream()
                        .limit(iterations)
                    //.limit(byExecutionTime(dur))
                        .collect(EvolutionResult.toBestGenotype())
        );
    }

}
