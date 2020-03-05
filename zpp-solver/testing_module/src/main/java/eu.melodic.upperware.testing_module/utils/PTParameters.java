package eu.melodic.upperware.testing_module.utils;

import lombok.Data;

@Data
public class PTParameters {
    private int minTmp;
    private int maxTmp;
    private int numThreads;

    @Override
    public String toString() {
        return minTmp + ";" + maxTmp + ";" +numThreads + "\n";
    }
}
