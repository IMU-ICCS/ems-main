package eu.passage.upperware.commons.model.testing;

import lombok.Data;

import java.util.List;

@Data
public class FunctionTestConfiguration {
    private String functionName;
    private String triggerPath;
    private List<TestCase> testCases;
}
