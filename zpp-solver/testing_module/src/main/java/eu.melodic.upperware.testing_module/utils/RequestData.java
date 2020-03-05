package eu.melodic.upperware.testing_module.utils;

import lombok.Data;

@Data
public class RequestData {
    private CPFilesData constraintProblems[];
    private int timeLimits[];
    private String outputPath;
    private PTParameters ptSolversParameters[];
    private GeneticSolverParameters geneticSolverParameters[];
    private int repetitions;
    private CPSamplerData cpSamplerData;
    private int numberOfRandomCP;
}
