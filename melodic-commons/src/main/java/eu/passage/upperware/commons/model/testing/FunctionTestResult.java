package eu.passage.upperware.commons.model.testing;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class FunctionTestResult {
    private String functionName;
    private double duration;
    private Long passed;
    private Long failed;
    private Long ignored;
    private List<TestCaseResult> testCaseResults;
    private TestResultEnum overallResult;
    private String failedAtStage;
    private String message;

    public FunctionTestResult(String functionName) {
        this.functionName = functionName;
        this.testCaseResults = new ArrayList<>();
        this.passed = 0L;
        this.failed = 0L;
        this.ignored = 0L;
        this.overallResult = TestResultEnum.SUCCESS;
    }

    public void addTestCaseResult(TestCaseResult testCaseResult) {
        this.testCaseResults.add(testCaseResult);
        TestResultEnum result = testCaseResult.getResult();
        if (result == TestResultEnum.SUCCESS) {
            this.passed += 1;
        } else if (result == TestResultEnum.FAILURE){
            this.failed += 1;
        } else if (result == TestResultEnum.IGNORED) {
            this.ignored += 1;
        }
        updateOverallResult();
    }

    private void updateOverallResult() {
        if (this.overallResult == TestResultEnum.PARTIAL_FAILURE
            || this.overallResult == TestResultEnum.IGNORED) {
            return;
        }
        if (this.failed == 0L && this.ignored == 0L) {
            this.overallResult = TestResultEnum.SUCCESS;
        } else if (this.passed == 0L) {
            this.overallResult = TestResultEnum.FAILURE;
        } else {
            this.overallResult = TestResultEnum.PARTIAL_FAILURE;
        }
    }
}
