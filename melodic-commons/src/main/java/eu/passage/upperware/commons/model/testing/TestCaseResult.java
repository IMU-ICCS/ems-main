package eu.passage.upperware.commons.model.testing;

import lombok.Data;

@Data
public class TestCaseResult {
    private String event;
    private String actualOutput;
    private String condition;
    private String expectedValue;
    private String message;
    private TestResultEnum result;
    private double duration;

    public TestCaseResult() {}
}
