import comparators.StochasticRankingComparator;
import cPGeneticWrapper.ACPGeneticWrapper;
import cPGeneticWrapper.CPGeneticWrapper;
import cp_wrapper.CPWrapper;
import implementation.*;
import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.Factory;
import lombok.Setter;

import java.util.List;
import java.util.function.Function;

public class Runner {
    @Setter
    private Integer populationSize = 100;
    @Setter
    private Integer iterations = 100;
    @Setter
    private double crossoverProbability = 0.2;
    @Setter
    private double mutatorProbability = 0.05;
    @Setter
    private double comparatorProbability = 0.1;


    private final Function<Genotype<ImplGene>, Double> fitnessFunction = new EvalFunction();

    public List<Integer> run(ACPGeneticWrapper geneticWrapper) {
        Alterer<ImplGene, Double> crossoverAlterer = new SinglePointCrossover<>(crossoverProbability);
        Mutator<ImplGene, Double> mutator = new ImplMutator(mutatorProbability, geneticWrapper);
        Selector<ImplGene, Double> selector = new ImplSelector(new StochasticRankingComparator(comparatorProbability));

        Factory<Genotype<ImplGene>> gtf =
                Genotype.of(ImplChromosome.of(populationSize, geneticWrapper.getSize(), geneticWrapper));

        final Engine<ImplGene, Double> engine =
                Engine.builder(fitnessFunction, gtf)
                        .alterers(crossoverAlterer, mutator)
                        .selector(selector)
                        .build();

        return ACPGeneticWrapper.genotypeToIntegerList(engine.stream().limit(iterations).collect(EvolutionResult.toBestGenotype()));
    }

    public List<Integer> run(CPWrapper cpWrapper) {
        ACPGeneticWrapper cpGeneticWrapper = new CPGeneticWrapper(cpWrapper);

        return run(cpGeneticWrapper);
    }
}
