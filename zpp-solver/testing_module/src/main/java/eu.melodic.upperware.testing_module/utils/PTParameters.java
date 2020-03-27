package eu.melodic.upperware.testing_module.utils;

import lombok.Data;

@Data
public class PTParameters {
    private double minTmp;
    private double maxTmp;
    private int numThreads;

    @Override
    public String toString() {
        return minTmp + ";" + maxTmp + ";" +numThreads + ";";
    }
}
