package eu.functionizer.functionizertestingtool.service.test;

import eu.functionizer.functionizertestingtool.model.*;
import org.junit.ComparisonFailure;
import org.junit.platform.commons.util.ExceptionUtils;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.reporting.ReportEntry;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import static eu.functionizer.functionizertestingtool.service.test.ServerlessFunctionTestFactory.ALL_TESTS_DISPLAY_NAME;
import static java.util.Collections.emptyMap;
import static org.junit.platform.engine.TestExecutionResult.Status.ABORTED;
import static org.junit.platform.engine.TestExecutionResult.Status.SUCCESSFUL;

public class FunctionizerReportData {
    private static final int MILLIS_PER_SECOND = 1000;

    public long timeFinished;
    private List<Failure> failures = new ArrayList<>();

    private FunctionizerTestResult testResult;


    private final Map<TestIdentifier, TestExecutionResult> finishedTests = new ConcurrentHashMap<>();
    private final Map<TestIdentifier, String> skippedTests = new ConcurrentHashMap<>();
    private final Map<TestIdentifier, Instant> startInstants = new ConcurrentHashMap<>();
    private final Map<TestIdentifier, Instant> endInstants = new ConcurrentHashMap<>();

    private TestIdentifier root;
    private final Map<String, Map<String, String>> reportEntries = new ConcurrentHashMap<>();

    private final TestPlan testPlan;
    private final Clock clock;

    FunctionizerReportData(TestPlan testPlan, Clock clock) {
        this.testPlan = testPlan;
        this.clock = clock;

    }

    void markSkipped(TestIdentifier testIdentifier, String reason) {
        this.skippedTests.put(testIdentifier, reason == null ? "" : reason);
    }

    void markStarted(TestIdentifier testIdentifier) {
        this.startInstants.put(testIdentifier, this.clock.instant());
    }

    void markFinished(TestIdentifier testIdentifier, TestExecutionResult result) {
        this.endInstants.put(testIdentifier, this.clock.instant());
        if (result.getStatus() == ABORTED) {
            String reason = result
                .getThrowable()
                .map(ExceptionUtils::readStackTrace)
                .orElse("");
            this.skippedTests.put(testIdentifier, reason);
        }
        else {
            this.finishedTests.put(testIdentifier, result);
        }
    }

    void addReportEntry(TestIdentifier testIdentifier, ReportEntry entry) {
        // Note: we get the actual test display name from the report entry,
        // because dynamic test report entries do not recognize their display names properly

        if (entry.getKeyValuePairs().containsKey(ReportEntryKey.ROOT)) {
            this.root = testIdentifier;
        }

        String actualTestDisplayName = entry.getKeyValuePairs().get("displayName");
        if (actualTestDisplayName != null) {
            Map<String, String> report = this.reportEntries.computeIfAbsent(
                actualTestDisplayName,
                key -> new HashMap<>()
            );
            entry.getKeyValuePairs().forEach(report::put);
        }
    }

    void addFailure(TestIdentifier testIdentifier, Throwable throwable) {
        failures.add(new Failure(testIdentifier.getDisplayName(), throwable.getMessage()));
    }

    public static class Failure {
        String displayName;
        String cause;

        public Failure(
            String displayName,
            String cause
        ) {
            this.displayName = displayName;
            this.cause = cause;
        }
    }

    TestExecutionResult getResult(TestIdentifier testIdentifier) {
        if (this.finishedTests.containsKey(testIdentifier)) {
            return this.finishedTests.get(testIdentifier);
        }
        Optional<TestIdentifier> parent = this.testPlan.getParent(testIdentifier);
        if (parent.isPresent()) {
            Optional<TestIdentifier> ancestor = findAncestor(
                parent.get(),
                this.finishedTests::containsKey
            );
            if (ancestor.isPresent()) {
                TestExecutionResult result = this.finishedTests.get(ancestor.get());
                if (result.getStatus() != SUCCESSFUL) {
                    return result;
                }
            }
        }

        return null;
    }

    double getDurationInSeconds(TestIdentifier testIdentifier) {
        Instant startInstant = this.startInstants.getOrDefault(testIdentifier, Instant.EPOCH);
        Instant endInstant = this.endInstants.getOrDefault(testIdentifier, startInstant);
        return Duration.between(startInstant, endInstant).toMillis() / (double) MILLIS_PER_SECOND;
    }

    private Optional<TestIdentifier> findAncestor(
        TestIdentifier testIdentifier,
        Predicate<TestIdentifier> predicate
    ) {
        Optional<TestIdentifier> current = Optional.of(testIdentifier);
        while (current.isPresent()) {
            if (predicate.test(current.get())) {
                return current;
            }
            current = this.testPlan.getParent(current.get());
        }
        return Optional.empty();
    }

    public FunctionizerTestResult getTestResult() {
        return this.testResult;
    }

    public void prepareTestResult() {
        this.testResult = new FunctionizerTestResult();
        this.testResult.setDuration(getDurationInSeconds(this.root));

        Map<String, String> testReportEntry = this.reportEntries.getOrDefault(
            ALL_TESTS_DISPLAY_NAME,
            emptyMap()
        );
        if (testReportEntry.containsKey(ReportEntryKey.STAGE)) {
            this.testResult.setMessage(testReportEntry.get(ReportEntryKey.FAILURE_CAUSE));
            this.testResult.setFailedAtStage(testReportEntry.get(ReportEntryKey.STAGE));
            this.testResult.setTestsRunResult(TestResultEnum.FAILURE);
            return;
        }

        Set<TestIdentifier> functionNodes = testPlan.getChildren(this.root);
        for (TestIdentifier functionNode : functionNodes) {
            FunctionTestResult functionTestResult = new FunctionTestResult(
                functionNode.getDisplayName()
            );
            functionTestResult.setDuration(getDurationInSeconds(functionNode));
            Map<String, String> functionReportEntry = this.reportEntries.getOrDefault(
                functionNode.getDisplayName(),
                emptyMap()
            );
            if (functionReportEntry.containsKey(ReportEntryKey.STAGE)) {
                functionTestResult.setFailedAtStage(functionReportEntry.get(ReportEntryKey.STAGE));
                functionTestResult.setMessage(functionReportEntry.get(ReportEntryKey.FAILURE_CAUSE));
                functionTestResult.setOverallResult(TestResultEnum.FAILURE);
            }
            Set<TestIdentifier> testCases = testPlan.getChildren(functionNode);

            for(TestIdentifier testCase : testCases) {
                TestCaseResult testCaseResult = new TestCaseResult();
                TestExecutionResult result = getResult(testCase);
                testCaseResult.setDuration(getDurationInSeconds(testCase));
                testCaseResult.setResult(TestResultEnum.fromTestExecutionResult(result));

                Map<String, String> reportEntry = this.reportEntries.getOrDefault(
                    testCase.getDisplayName(),
                    emptyMap()
                );
                if (reportEntry.containsKey(ReportEntryKey.IGNORED)) {
                    testCaseResult.setResult(TestResultEnum.IGNORED);
                }
                testCaseResult.setEvent(reportEntry.get(ReportEntryKey.EVENT));
                testCaseResult.setExpectedOutput(reportEntry.get(ReportEntryKey.EXPECTED_OUTPUT));
                testCaseResult.setActualOutput(reportEntry.get(ReportEntryKey.EXPECTED_OUTPUT));

                result.getThrowable().ifPresent(error -> {
                    testCaseResult.setMessage(error.getMessage());
                    if (error instanceof ComparisonFailure) {
                        ComparisonFailure comparisonFailure = (ComparisonFailure) error;
                        testCaseResult.setExpectedOutput(comparisonFailure.getExpected());
                        testCaseResult.setActualOutput(comparisonFailure.getActual());
                    } else {
                        testCaseResult.setActualOutput(null);
                    }
                });
                functionTestResult.addTestCaseResult(testCaseResult);
            }

            this.testResult.addFunctionTestResult(functionTestResult);
        }
        this.testResult.setTestsRunResult(TestResultEnum.SUCCESS);
    }
}
