package eu.melodic.upperware.testing_module.utils;

import lombok.Data;

@Data
public class CPSamplerData {
    private int numberComponents;
    private int minConstraints;
    private int maxConstraints;
    private TemplateUtilityComponent utilityFunction[];
    private String nodeCandidates;
    private String cpDirectory;
}
