package eu.passage.upperware.commons.model.testing;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FunctionizerTestResult {
    private List<FunctionTestResult> functionTestResults;
    private TestResultEnum testsRunResult;
    private String failedAtStage;
    private String message;
    private double duration;

    public FunctionizerTestResult() {
        this.functionTestResults = new ArrayList<>();
    }

    public void addFunctionTestResult(FunctionTestResult functionTestResult) {
        this.functionTestResults.add(functionTestResult);
    }
}
