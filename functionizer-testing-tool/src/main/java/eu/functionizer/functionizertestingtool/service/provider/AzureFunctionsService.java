package eu.functionizer.functionizertestingtool.service.provider;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.appservice.FunctionApp;
import eu.functionizer.functionizertestingtool.model.ReportEntryKey;
import eu.functionizer.functionizertestingtool.service.test.ServerlessFunctionTestFactory;
import eu.functionizer.functionizertestingtool.service.test.Stage;
import eu.passage.upperware.commons.model.testing.FunctionTestConfiguration;
import eu.passage.upperware.commons.model.testing.TestCase;
import io.github.cloudiator.rest.model.Function;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestReporter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@Slf4j
public class AzureFunctionsService extends TestPreparationService {
    private final static String ENDPOINT_STATIC = "azurewebsites.net/api";
    private final static String CLIENT_TENANT_DELIMITER = ":";

    private static String buildFunctionUrl(String appName, String functionName) {
        return String.format("http://%s.%s/%s", appName, ENDPOINT_STATIC, functionName);
    }

    private static String buildResourceGroupName(String stackId) {
        return stackId + "group";
    }

    private static Azure buildAzureClient(String user, String secret) throws IOException {
        int colonIndex = user.lastIndexOf(CLIENT_TENANT_DELIMITER);
        String clientId = user.substring(0, colonIndex);
        String tenantId = user.substring(colonIndex + 1);

        ApplicationTokenCredentials credentials = new ApplicationTokenCredentials(
            clientId,
            tenantId,
            secret,
            AzureEnvironment.AZURE
        );

        return Azure.authenticate(credentials).withDefaultSubscription();
    }

    private static String getFunctionKey(Azure azureClient, String stackId) {
        String resourceGroup = buildResourceGroupName(stackId);

        FunctionApp functionApp = azureClient
            .appServices()
            .functionApps()
            .getByResourceGroup(resourceGroup, stackId);

        return functionApp.getMasterKey();
    }

    private static String invokeFunction(String url, String functionKey, String input) throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("x-functions-key", functionKey);


        HttpEntity<String> entity = new HttpEntity<>(input, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(
            url,
            entity,
            String.class
        );
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new Exception("Not 200 response");
        }
    }

    public static void prepareAzureTests(
        List<DynamicNode> functionNode,
        FunctionTestConfiguration configuration,
        String stackId,
        String user,
        String secret
    ) {
        Azure azureClient;
        String functionKey;

        Stage stage = Stage.BUILD_AZURE_CLIENT;
        try {
            log.debug("Building Azure client");
            azureClient = AzureFunctionsService.buildAzureClient(user, secret);

            log.debug("Fetching Function Key");
            stage = Stage.GET_AZURE_FUNCTION_KEY;
            functionKey = AzureFunctionsService.getFunctionKey(azureClient, stackId);
        } catch (Exception e) {
            failDynamicNode(
                configuration.getFunctionName(),
                stage,
                e.getMessage()
            );
            ignoreTestCases(functionNode, configuration);
            return;
        }

        log.debug("Building Function URL");
        String azureFunctionEndpoint = AzureFunctionsService.buildFunctionUrl(
            stackId,
            configuration.getTriggerPath()
        );
        log.info("Function URL = {}", azureFunctionEndpoint);

        List<DynamicTest> azureTests = configuration
            .getTestCases()
            .stream()
            .map(testCase -> AzureFunctionsService.createAzureTest(
                configuration.getFunctionName(),
                azureFunctionEndpoint,
                functionKey,
                testCase,
                testReporter
            ))
            .collect(Collectors.toList());

        functionNode.addAll(azureTests);
    }

    private static DynamicTest createAzureTest(
        String functionName,
        String azureFunctionEndpoint,
        String functionKey,
        TestCase testCase,
        TestReporter testReporter
    ) {
        log.debug(
            "Creating test case: event={}, expected output={}",
            testCase.getEvent(),
            testCase.getExpectedOutput()
        );
        String displayName = createTestCaseDisplayName(
            functionName,
            testCase.getEvent(),
            testCase.getExpectedOutput()
        );

        Map<String, String> reportEntry = createReportEntry(displayName);
        reportEntry.put(ReportEntryKey.EVENT, testCase.getEvent());
        reportEntry.put(ReportEntryKey.EXPECTED_OUTPUT, testCase.getExpectedOutput());
        testReporter.publishEntry(reportEntry);

        return dynamicTest(
            displayName,
            () -> executeAzureTestCase(
                azureFunctionEndpoint,
                functionKey,
                testCase.getEvent(),
                testCase.getExpectedOutput()
            )
        );
    }

    private static void executeAzureTestCase(
        String endpoint,
        String functionKey,
        String event,
        String expectedOutput
    ) {
        Stage stage = Stage.TEST_EXECUTION;
        try {
            String response = AzureFunctionsService.invokeFunction(
                endpoint,
                functionKey,
                event
            );
            assertEquals(expectedOutput, response);
        } catch (Exception e) {
            fail(String.format(
                "An exception while fetching response from AWS Lambda Client occurred. Cause: %s",
                e.getMessage()
            ));
        }
    }
}
