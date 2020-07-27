package eu.functionizer.functionizertestingtool.service.provider;

import eu.functionizer.functionizertestingtool.model.ReportEntryKey;
import eu.functionizer.functionizertestingtool.service.test.Stage;
import eu.passage.upperware.commons.model.testing.FunctionTestConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestReporter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@Slf4j
public class TestPreparationService {

    public static TestReporter testReporter;

    static String createTestCaseDisplayName(
        String functionName,
        String input,
        String condition,
        String expected
    ) {
        return String.format(
            "Output of %s invoked with %s %s %s",
            functionName,
            input,
            condition,
            expected
        );
    }

    public static Map<String, String> createReportEntry(String displayName) {
        Map<String, String> reportEntry = new HashMap<>();
        reportEntry.put("displayName", displayName);
        return reportEntry;
    }

    public static void failDynamicNode(String displayName, Stage stage, String cause) {
        log.info(
            "Dynamic Node '{}' construction failed at the stage {}. Cause: {}",
            displayName,
            stage.getName(),
            cause
        );
        Map<String, String> reportEntry = createReportEntry(displayName);
        reportEntry.put(ReportEntryKey.STAGE, stage.getName());
        reportEntry.put(ReportEntryKey.FAILURE_CAUSE, cause);
        testReporter.publishEntry(reportEntry);
    }

    public static void ignoreTestCases(List<DynamicNode> functionNode, FunctionTestConfiguration configuration) {
        configuration.getTestCases().forEach(testCase -> {
            String testCaseDisplayName = createTestCaseDisplayName(
                configuration.getFunctionName(),
                testCase.getEvent(),
                testCase.getCondition().name(),
                testCase.getExpectedValue()
            );
            Map<String, String> reportEntry = createReportEntry(testCaseDisplayName);
            reportEntry.put(ReportEntryKey.EVENT, testCase.getEvent());
            reportEntry.put(ReportEntryKey.CONDITION, testCase.getCondition().name());
            reportEntry.put(ReportEntryKey.EXPECTED_VALUE, testCase.getExpectedValue());
            reportEntry.put(ReportEntryKey.IGNORED, "true");
            testReporter.publishEntry(reportEntry);
            functionNode.add(dynamicTest(testCaseDisplayName, () -> fail("Test case ignored")));
        });
    }
}
