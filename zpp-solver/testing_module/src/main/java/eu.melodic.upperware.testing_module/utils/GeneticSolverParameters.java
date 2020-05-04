package eu.melodic.upperware.testing_module.utils;

import lombok.Data;

@Data
public class GeneticSolverParameters {
    private Integer populationSize;
    private Integer iterations;
    private double crossoverProbability;
    private double mutatorProbability;
    private double mutationProbability;
    private double comparatorProbability;
    private int guesses;

    @Override
    public String toString() {
        return populationSize + ";" + iterations +";" + crossoverProbability + ";" + mutationProbability +";" + mutationProbability +";" + comparatorProbability + ";" + guesses + ";";
    }
}
