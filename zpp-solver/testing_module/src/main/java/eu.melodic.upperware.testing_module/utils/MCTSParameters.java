package eu.melodic.upperware.testing_module.utils;

import lombok.Data;

@Data
public class MCTSParameters {
    private double minTmp;
    private double maxTmp;
    private int numThreads;
    private int iterations;

    @Override
    public String toString() {
        return minTmp + ";" + maxTmp + ";" +numThreads + ";" + iterations + ";";
    }
}
