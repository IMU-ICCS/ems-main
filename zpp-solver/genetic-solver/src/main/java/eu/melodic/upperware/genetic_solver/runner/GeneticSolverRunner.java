package eu.melodic.upperware.genetic_solver.runner;

import eu.melodic.upperware.cp_wrapper.CPWrapper;
import eu.melodic.upperware.cp_wrapper.utility_provider.UtilityProvider;
import eu.melodic.upperware.genetic_solver.cp_genetic_wrapper.ACPGeneticWrapper;
import eu.melodic.upperware.genetic_solver.cp_genetic_wrapper.CPGeneticWrapper;
import eu.melodic.upperware.genetic_solver.jenetics_implementation.*;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.Limits;
import io.jenetics.util.Factory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Clock;
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
    private int timeLimitSeconds = 0;
    @Getter
    private double finalUtility;

    private final Function<Genotype<GeneImpl>, Double> fitnessFunction = new EvalFunction();

    public List<VariableValueDTO> run(ConstraintProblem cp, UtilityProvider utility) {
        CPWrapper cpWrapper = new CPWrapper();
        cpWrapper.parse(cp, utility);

        return cpWrapper.assignmentToVariableValueDTOList(run(new CPGeneticWrapper(cpWrapper)));
    }

    List<Integer> run(ACPGeneticWrapper geneticWrapper) {
        Alterer<GeneImpl, Double> crossoverAlterer = new SinglePointCrossover<>(crossoverProbability);
        Mutator<GeneImpl, Double> mutator = new MutatorImpl(mutationProbability, geneticWrapper, guesses, mutatorProbability);
        Selector<GeneImpl, Double> selector = new SelectorImpl();
        ChromosomeImpl finalChromosome;

        log.info("Starting eu.melodic.upperware.genetic_solver.runner.");
        log.info("Population size: " + populationSize);
        log.info((timeLimitSeconds == 0 ? "Iterations: " + iterations : "Time limit: " + timeLimitSeconds));
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

        log.info("Engine built.");
        log.info("Starting execution stream.");

        if (timeLimitSeconds == 0) {
            finalChromosome = (ChromosomeImpl) (engine.stream().limit(iterations)
                    .collect(EvolutionResult.toBestGenotype()).getChromosome());
        } else {
            finalChromosome = (ChromosomeImpl) (engine.stream().
                    limit(Limits.byExecutionTime(Duration.ofSeconds(timeLimitSeconds), Clock.systemUTC()))
                    .collect(EvolutionResult.toBestGenotype()).getChromosome());
        }
        finalUtility = finalChromosome.getUtility();

        return ACPGeneticWrapper.chromosomeToIntegerList(finalChromosome);
    }

}
