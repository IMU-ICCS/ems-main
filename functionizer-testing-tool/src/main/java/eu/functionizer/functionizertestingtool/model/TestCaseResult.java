package eu.functionizer.functionizertestingtool.model;

import lombok.Data;

@Data
public class TestCaseResult {
    private String event;
    private String expectedOutput;
    private String actualOutput;
    private String message;
    private TestResultEnum result;
    private double duration;
}
