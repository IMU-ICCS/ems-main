package eu.functionizer.functionizertestingtool.service.test;

import org.junit.platform.commons.util.PreconditionViolationException;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.reporting.ReportEntry;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

import java.time.Clock;

public class FunctionizerTestListener implements TestExecutionListener {

    private final Clock clock;

    private FunctionizerReportData reportData;

    public FunctionizerTestListener() {
        this(Clock.systemDefaultZone());
    }

    private FunctionizerTestListener(Clock clock) {
        this.clock = clock;
    }

    public FunctionizerReportData getReport() {
        return this.reportData;
    }

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        this.reportData = new FunctionizerReportData(testPlan, clock);
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        this.reportData.timeFinished = System.currentTimeMillis();
        this.reportData.prepareTestResult();
    }

    @Override
    public void dynamicTestRegistered(TestIdentifier testIdentifier) {
    }

    @Override
    public void executionSkipped(TestIdentifier testIdentifier, String reason) {
        this.reportData.markSkipped(testIdentifier, reason);
    }

    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
        this.reportData.markStarted(testIdentifier);
    }

    @Override
    public void reportingEntryPublished(TestIdentifier testIdentifier, ReportEntry entry) {
        this.reportData.addReportEntry(testIdentifier, entry);
    }

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {

        switch (testExecutionResult.getStatus()) {

            case SUCCESSFUL:

            case ABORTED: {
                break;
            }

            case FAILED: {
                testExecutionResult.getThrowable().ifPresent(
                    throwable -> this.reportData.addFailure(testIdentifier, throwable));
                break;
            }

            default:
                throw new PreconditionViolationException(
                    "Unsupported execution status:" + testExecutionResult.getStatus());
        }
        this.reportData.markFinished(testIdentifier, testExecutionResult);
    }
}
