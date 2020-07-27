package eu.functionizer.functionizertestingtool.service.provider;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import eu.functionizer.functionizertestingtool.model.ReportEntryKey;
import eu.functionizer.functionizertestingtool.service.test.Stage;
import eu.passage.upperware.commons.model.testing.Condition;
import eu.passage.upperware.commons.model.testing.FunctionTestConfiguration;
import eu.passage.upperware.commons.model.testing.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@Slf4j
public class AWSLambdaService extends TestPreparationService {

    public static AWSLambda buildClient(String user, String secret, String location) {
        BasicAWSCredentials credentials = new BasicAWSCredentials(user, secret);

        return AWSLambdaClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.fromName(location))
            .build();
    }

    public static void prepareAWSLambdaTests(
        List<DynamicNode> functionNode,
        FunctionTestConfiguration configuration,
        String stackId,
        String deployedName,
        String user,
        String secret,
        String location
    ) {
        AWSLambda awsLambdaClient;

        log.debug("Building AWS Lambda Function name");
        String awsFunctionName = String.join(
            "-",
            stackId,
            deployedName
        );

        log.debug("Building AWS Lambda client");
        try {
            awsLambdaClient = AWSLambdaService.buildClient(user, secret, location);
        } catch (Exception e) {
            failDynamicNode(
                configuration.getFunctionName(),
                Stage.BUILD_AWS_LAMBDA_CLIENT,
                e.getMessage()
            );
            ignoreTestCases(functionNode, configuration);
            return;
        }

        List<DynamicTest> awsTests = configuration.getTestCases()
            .stream()
            .map(testCase -> AWSLambdaService.createAWSLambdaTest(
                awsLambdaClient,
                configuration.getFunctionName(),
                awsFunctionName,
                testCase
            ))
            .collect(Collectors.toList());
        functionNode.addAll(awsTests);
    }

    public static DynamicTest createAWSLambdaTest(
        AWSLambda awsLambdaClient,
        String functionName,
        String awsLambdaFunctionName,
        TestCase testCase
    ) {
        log.debug(
            "Creating test case: event={}, condition={}, expected value={}",
            testCase.getEvent(),
            testCase.getCondition().name(),
            testCase.getExpectedValue()
        );

        String displayName = createTestCaseDisplayName(
            functionName,
            testCase.getEvent(),
            testCase.getCondition().name(),
            testCase.getExpectedValue()
        );

        return dynamicTest(
            displayName,
            () -> executeAWSLambdaTestCase(
                displayName,
                awsLambdaClient,
                awsLambdaFunctionName,
                testCase.getEvent(),
                testCase.getCondition(),
                testCase.getExpectedValue()
            )
        );
    }

    public static void executeAWSLambdaTestCase(
        String displayName,
        AWSLambda awsLambda,
        String functionName,
        String event,
        Condition condition,
        String expectedValue
    ) {
        Map<String, String> reportEntry = createReportEntry(displayName);
        reportEntry.put(ReportEntryKey.EVENT, event);
        reportEntry.put(ReportEntryKey.CONDITION, condition.name());
        reportEntry.put(ReportEntryKey.EXPECTED_VALUE, expectedValue);

        InvokeRequest invokeRequest = new InvokeRequest()
            .withFunctionName(functionName)
            .withPayload(event);

        try {
            InvokeResult result = awsLambda.invoke(invokeRequest);
            String resultString = new String(result.getPayload().array(), StandardCharsets.UTF_8);
            if (Arrays.asList(200, 202, 204).contains(result.getStatusCode())) {
                reportEntry.put(ReportEntryKey.ACTUAL_OUTPUT, resultString);
                testReporter.publishEntry(reportEntry);
                assertTrue(condition.getMethod().apply(resultString, expectedValue));
            } else {
                testReporter.publishEntry(reportEntry);
                fail(String.format(
                    "Received status code %s. Reason: %s",
                    result.getStatusCode(),
                    resultString
                ));
            }
        } catch (Exception e) {
            testReporter.publishEntry(reportEntry);
            fail(String.format(
                "An exception while fetching response from AWS Lambda Client occurred. Cause: %s",
                e.getMessage()
            ));
        }
    }
}
