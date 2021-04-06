package eu.passage.upperware.commons.service.testing;

import eu.passage.upperware.commons.model.testing.Condition;
import eu.passage.upperware.commons.model.testing.FunctionTestConfiguration;
import eu.passage.upperware.commons.model.testing.TestCase;
import eu.passage.upperware.commons.model.testing.TestConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Triple;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class TestConfigurationValidationService {

    public static class NotUniqueFunctionNameException extends Exception {
        String functionName;
        String message;

        NotUniqueFunctionNameException(String functionName) {
            this.functionName = functionName;
            this.message = String.format(
                "Function name '%s' occurred in more than one test. Please adjust the config file",
                functionName
            );
        }

        @Override
        public String getMessage() {
            return this.message;
        }
    }

    public static class NotUniqueTestCaseException extends Exception {
        String functionName;
        String event;
        Condition condition;
        String expected;
        String message;

        NotUniqueTestCaseException(
            String functionName,
            String event,
            Condition condition,
            String expected
        ) {
            this.functionName = functionName;
            this.event = event;
            this.condition = condition;
            this.expected = expected;
            this.message = String.format(
                "The triplet of event = '%s', condition = %s, expected value = '%s'" +
                    " appears in more than one test case of function '%s'." +
                    " Please adjust the test cases to be unique.",
                this.event,
                this.condition,
                this.expected,
                this.functionName
            );
        }
        @Override
        public String getMessage() {
            return this.message;
        }
    }

    public static void validate(TestConfiguration configuration) throws Exception {
        log.info("Checking uniqueness of function names");
        checkFunctionNamesUniqueness(configuration.getTests());

        log.info("Checking uniqueness of test cases");
        for (FunctionTestConfiguration functionTestConfiguration : configuration.getTests()){
            checkTestCasesUniqueness(
                functionTestConfiguration.getTestCases(),
                functionTestConfiguration.getFunctionName()
            );
        }
    }

    public static void checkFunctionNamesUniqueness(
        List<FunctionTestConfiguration> functionTestConfigurations
    ) throws NotUniqueFunctionNameException {
        List<String> functionNames = functionTestConfigurations
            .stream()
            .map(FunctionTestConfiguration::getFunctionName)
            .collect(Collectors.toList());
        Set<String> uniqueNames = new HashSet<>();
        for (String name: functionNames) {
            if (!uniqueNames.add(name)) {
                log.error("Function name '{}' occurred in more than one test", name);
                throw new NotUniqueFunctionNameException(name);
            }
        }
    }

    public static void checkTestCasesUniqueness(
        List<TestCase> testCases, String functionName
    ) throws NotUniqueTestCaseException {
        List<Triple<String, Condition, String>> testCaseTriplets = testCases
            .stream()
            .map(testCase -> Triple.of(testCase.getEvent(), testCase.getCondition(), testCase.getExpectedValue()))
            .collect(Collectors.toList());
        Set<Triple<String, Condition, String>> uniqueTriplets = new HashSet<>();
        for (Triple<String, Condition, String> testCaseTriplet : testCaseTriplets) {
            if (!uniqueTriplets.add(testCaseTriplet)) {
                log.error(
                    "Function '{}' has more than one test case with the event-predicate-expected triplet: ('{}', {}, '{}').",
                    functionName,
                    testCaseTriplet.getLeft(),
                    testCaseTriplet.getMiddle(),
                    testCaseTriplet.getRight()
                );
                throw new NotUniqueTestCaseException(
                    functionName,
                    testCaseTriplet.getLeft(),
                    testCaseTriplet.getMiddle(),
                    testCaseTriplet.getRight()
                );
            }
        }
    }
}
