package eu.functionizer.functionizertestingtool.service.test;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import eu.functionizer.functionizertestingtool.model.CloudiatorData;
import eu.functionizer.functionizertestingtool.service.provider.TestPreparationService;
import eu.passage.upperware.commons.model.testing.FunctionTestConfiguration;
import eu.functionizer.functionizertestingtool.model.ReportEntryKey;
import eu.functionizer.functionizertestingtool.service.yaml.TestConfigurationLoader;
import eu.passage.upperware.commons.cloudiator.CloudiatorApi;
import eu.passage.upperware.commons.cloudiator.CloudiatorClientApi;
import eu.passage.upperware.commons.cloudiator.QueueInspector;
import eu.passage.upperware.commons.model.provider.CloudDefinition;
import eu.passage.upperware.commons.model.provider.Credential;
import eu.passage.upperware.commons.model.provider.Provider;
import eu.passage.upperware.commons.service.provider.ProviderIdCreatorService;
import eu.passage.upperware.commons.service.provider.ProviderService;
import eu.passage.upperware.commons.service.provider.ProviderValidationService;
import eu.passage.upperware.commons.service.store.SecureStoreService;
import eu.passage.upperware.commons.service.yaml.YamlDataService;
import eu.passage.upperware.commons.cloudiator.CloudiatorProperties;

import io.github.cloudiator.rest.ApiClient;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.api.*;

import io.github.cloudiator.rest.model.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import static eu.functionizer.functionizertestingtool.service.provider.AWSLambdaService.prepareAWSLambdaTests;
import static eu.functionizer.functionizertestingtool.service.provider.AzureFunctionsService.prepareAzureTests;
import static eu.functionizer.functionizertestingtool.service.provider.TestPreparationService.*;

@Slf4j
public class ServerlessFunctionTestFactory {

    private static final String CLOUDIATOR_CONFIG_FILE_PATH = System.getenv("MELODIC_CONFIG_DIR")
        + "/eu.melodic.cloudiator-client.properties";

    public static final String ALL_TESTS_DISPLAY_NAME = "All tests";

    private static Map<String, Provider> providerMap;

    private static CloudiatorApi cloudiatorApi;
    private static MatchmakingApi matchmakingApi;
    private static ProviderService providerService;
    private static Stage stage = null;

    TestReporter testReporter;

    @BeforeAll
    static void setUp() throws IOException {

        Properties properties = new Properties();
        properties.load(new FileInputStream(CLOUDIATOR_CONFIG_FILE_PATH));

        CloudiatorProperties.Cloudiator cloudiator = new CloudiatorProperties.Cloudiator();
        cloudiator.setUrl(properties.getProperty("cloudiator.url"));
        cloudiator.setApiKey(properties.getProperty("cloudiator.apiKey"));
        cloudiator.setHttpReadTimeout(
            Integer.parseInt(properties.getProperty("cloudiator.httpReadTimeout"))
        );

        CloudiatorProperties cloudiatorProperties = new CloudiatorProperties();
        cloudiatorProperties.setCloudiator(cloudiator);

        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(cloudiator.getUrl());
        apiClient.setApiKey(cloudiator.getApiKey());
        apiClient.setReadTimeout(cloudiator.getHttpReadTimeout());

        QueueApi queueApi = new QueueApi(apiClient);

        cloudiatorApi = new CloudiatorClientApi(
            new CloudApi(apiClient),
            new SecurityApi(apiClient),
            new NodeApi(apiClient),
            new ProcessApi(apiClient),
            queueApi,
            new JobApi(apiClient),
            new MonitoringApi(apiClient),
            new QueueInspector(queueApi, cloudiatorProperties)
        );

        matchmakingApi = new MatchmakingApi(apiClient);
        providerService = new ProviderService(
            new ProviderIdCreatorService(),
            new ProviderValidationService(),
            cloudiatorApi,
            new SecureStoreService(cloudiatorApi),
            new YamlDataService()
        );

        providerMap = new HashMap<>();
        for (Provider provider : Provider.values()) {
            providerMap.put(provider.value, provider);
        }
    }

    @BeforeEach
    private void init(TestReporter testReporter) {
        this.testReporter = testReporter;
    }


    @TestFactory
    @DisplayName(ALL_TESTS_DISPLAY_NAME)
    public Collection<DynamicNode> dynamicTestNodes() {
        log.info("Test Factory started");
        stage = Stage.START;
        TestPreparationService.testReporter = this.testReporter;

        Map<String, String> entry = createReportEntry(ALL_TESTS_DISPLAY_NAME);
        entry.put(ReportEntryKey.ROOT, "true");
        testReporter.publishEntry(entry);

        List<DynamicNode> dynamicNodes = new ArrayList<>();
        List<FunctionTestConfiguration> functionTests;

        log.info("Loading test configuration");
        stage = Stage.LOAD_CONFIG;
        try {
            functionTests = TestConfigurationLoader
                .loadTestConfiguration()
                .getTests();
        } catch (Exception e) {
            failDynamicNode(ALL_TESTS_DISPLAY_NAME, stage, e.getMessage());
            return dynamicNodes;
        }

        CloudiatorData cloudiatorData;

        log.info("Fetching data from Cloudiator");
        stage = Stage.FETCH_CLOUDIATOR;
        try {
            cloudiatorData = fetchCloudiatorData();
        } catch (Exception e) {
            testReporter.publishEntry("result", "fail");
            failDynamicNode(ALL_TESTS_DISPLAY_NAME, stage, e.getMessage());
            return dynamicNodes;
        }
        if (cloudiatorData == null) {
            testReporter.publishEntry("result", "fail");
            failDynamicNode(
                ALL_TESTS_DISPLAY_NAME, stage,
                "There are no Functions on the deployed Function list received from Cloudiator."
            );
            return dynamicNodes;
        }

        log.info("Cloudiator data fetched successfully");

        for (FunctionTestConfiguration functionTestConfiguration : functionTests) {
            String functionConfigurationName = functionTestConfiguration.getFunctionName();

            log.info("Gathering data on Function {}", functionConfigurationName);
            stage = Stage.GATHER_FUNCTION_DATA;

            log.debug("Looking up Function in the deployed Function list");
            Function function = cloudiatorData
                .getDeployedFunctions()
                .get(functionConfigurationName.toLowerCase());

            List<DynamicNode> functionNode;

            if (function == null) {
                functionNode = new ArrayList<>();
                failDynamicNode(
                    functionConfigurationName,
                    stage,
                    "Not found in the deployed Function list"
                );
                ignoreTestCases(functionNode, functionTestConfiguration);
            } else {

                String functionDeployedName = cloudiatorData
                    .getFunctionDeployNames()
                    .get(functionConfigurationName);

                NodeCandidate nodeCandidate = cloudiatorData
                    .getNodeCandidates()
                    .get(function.getId());

                log.debug("Collecting test cases");
                functionNode = prepareFunctionNode(
                    functionTestConfiguration,
                    nodeCandidate,
                    cloudiatorData.getUserSecrets(),
                    functionDeployedName,
                    function
                );
            }

            dynamicNodes.add(
                DynamicContainer.dynamicContainer(
                    functionConfigurationName,
                    functionNode
                )
            );
        }
        stage = Stage.END;
        return dynamicNodes;
    }

    private CloudiatorData fetchCloudiatorData(
    ) throws ApiException {

        log.debug("Fetching Functions from Cloudiator");
        Map<String, Function> functions = getFunctionsMap();
        if (functions.size() == 0) {
            return null;
        }

        log.debug("Fetching Function Names from Cloudiator");
        Map<String, String> functionDeployNames = getFunctionDeployNames();

        log.debug("Fetching Node Candidates from Cloudiator");
        Map<String, NodeCandidate> nodeCandidates = getFaasNodeCandidates();

        log.debug("Fetching Cloud Provider Credentials");
        stage = Stage.FETCH_CREDENTIALS;
        Map<String, String> userSecrets = getCredentials();

        return new CloudiatorData(
            functions,
            functionDeployNames,
            nodeCandidates,
            userSecrets
        );
    }

    private Map<String, Function> getFunctionsMap() {
        List<Function> functions = cloudiatorApi.getFunctionList();
        return functions
            .stream()
            .filter(function -> !Objects.requireNonNull(function.getStackId()).isEmpty())
            .collect(Collectors.toMap(
                function -> Objects.requireNonNull(function.getStackId()).split("-")[0],
                function -> function
            ));
    }

    private Map<String, String> getFunctionDeployNames() {
        Stream<Task> faasTasks = getFaasTasks();

        log.debug("Extracting Function Names");
        return faasTasks
            .collect(Collectors.toMap(
                Task::getName,
                ServerlessFunctionTestFactory::getTaskInterfaceFunctionName
            ));
    }

    private Stream<Task> getFaasTasks() {
        log.debug("Fetching Cloudiator Jobs");
        List<Job> jobs = cloudiatorApi.getJobList();

        log.debug("Extracting FaaS tasks from jobs");
        return jobs
            .stream()
            .map(Job::getTasks)
            .filter(Objects::nonNull)
            .flatMap(List::stream)
            .filter(task -> Objects.nonNull(task)
                && Objects.nonNull(task.getInterfaces())
                && Objects.requireNonNull(task.getInterfaces())
                .stream()
                .findFirst()
                .filter(anInterface -> anInterface instanceof FaasInterface)
                .isPresent()
            );
    }

    private static String getTaskInterfaceFunctionName(Task task) {
        Optional<TaskInterface> taskInterface = Objects
            .requireNonNull(task.getInterfaces())
            .stream()
            .findFirst();
        if (taskInterface.isPresent()) {
            FaasInterface faasInterface = (FaasInterface) taskInterface.get();
            return faasInterface.getFunctionName();
        }
        return "";
    }

    private Map<String, NodeCandidate> getFaasNodeCandidates() throws ApiException {
        log.debug("Fetching FaaS Nodes");
        List<Node> faasNodes = cloudiatorApi.getFaasFromNodeList();

        log.debug("Determining Node Candidates");
        List<String> nodeCandidateIds = faasNodes
            .stream()
            .map(Node::getNodeCandidate)
            .collect(Collectors.toList());

        Map<String, NodeCandidate> nodeCandidates = getNodeCandidatesByIds(nodeCandidateIds)
            .stream()
            .collect(Collectors.toMap(NodeCandidate::getId, nodeCandidate -> nodeCandidate));

        return faasNodes
            .stream()
            .collect(Collectors.toMap(
                Node::getOriginId,
                node -> nodeCandidates.get(node.getNodeCandidate())
            ));
    }

    private List<NodeCandidate> getNodeCandidatesByIds(List<String> ids) throws ApiException {
        log.debug("Fetching Node Candidates");
        String constraint = String.format(
            "nodes->forAll(n | Set{'%s'}->includes(n.id))",
            String.join("','", ids)
        );
        return matchmakingApi.findNodeCandidates(
            Collections.singletonList(new OclRequirement()
                .constraint(constraint)
                .type("OclRequirement")
            )
        );
    }

    private Map<String, String> getCredentials() {
        return providerService
            .getCloudDefinitionsForAllProviders()
            .stream()
            .map(CloudDefinition::getCredential)
            .collect(Collectors.toMap(Credential::getUser, Credential::getSecret));
    }

    private List<DynamicNode> prepareFunctionNode(
        FunctionTestConfiguration functionTestConfiguration,
        NodeCandidate nodeCandidate,
        Map<String, String> userSecrets,
        String functionDeployedName,
        Function function
    ) {
        log.info("Preparing Function Node");
        List<DynamicNode> functionNode = new ArrayList<>();

        Cloud cloud = nodeCandidate.getCloud();
        if (cloud == null) {
            log.info("Cloud not found. Omitting test cases");
            failDynamicNode(
                functionTestConfiguration.getFunctionName(),
                Stage.GATHER_FUNCTION_DATA,"Cloud not found"
            );
            ignoreTestCases(functionNode, functionTestConfiguration);
            return functionNode;
        }

        log.debug("Retrieving Cloud credentials");
        String user = Objects.requireNonNull(nodeCandidate.getCloud())
            .getCredential()
            .getUser();
        String secret = userSecrets.get(user);
        if (user == null || secret == null) {
            failDynamicNode
                (functionTestConfiguration.getFunctionName(),
                    stage,
                    "Empty credentials."
                );
            ignoreTestCases(functionNode, functionTestConfiguration);
            return functionNode;
        }
        Provider provider = providerMap.get(
            nodeCandidate.getCloud().getApi().getProviderName()
        );

        switch (provider) {
            case AWS_EC2:
                String location = Objects.requireNonNull(nodeCandidate.getLocation()).getProviderId();

                prepareAWSLambdaTests(
                    functionNode,
                    functionTestConfiguration,
                    function.getStackId(),
                    functionDeployedName,
                    user,
                    secret,
                    location
                );
                break;

            case AZURE:
                prepareAzureTests(
                    functionNode,
                    functionTestConfiguration,
                    function.getStackId(),
                    user,
                    secret
                );
                break;

            default:
                log.info(
                    "Function {} has been deployed on unsupported Cloud. Omitting",
                    functionDeployedName
                );
                ignoreTestCases(functionNode, functionTestConfiguration);
        }
        return functionNode;
    }
}
