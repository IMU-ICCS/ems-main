package eu.melodic.upperware.testing_module;

import com.google.gson.Gson;
import cp_wrapper.utility_provider.ParallelUtilityProviderImpl;
import cp_wrapper.utility_provider.UtilityProvider;
import cp_wrapper.utility_provider.UtilityProviderImpl;
import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.cache.impl.FilecacheService;
import eu.melodic.upperware.cp_sampler.Sampler;
import eu.melodic.upperware.cp_sampler.constraint_problem_data.ConstraintProblemData;
import eu.melodic.upperware.cp_sampler.xmi_writer.XMIWriter;
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties;
import eu.melodic.upperware.testing_module.solvers.*;
import eu.melodic.upperware.testing_module.utils.*;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties;
import eu.melodic.upperware.utilitygenerator.utility_function.utility_templates_provider.TemplateProvider;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.authapi.token.JWTServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.tools.ant.taskdefs.Parallel;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.Clock;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class SolverTester {
    private Gson gson = new Gson();
    private Reader reader = new FileReader("zpp-solver/testing_module/src/main/resources/request.json");
    private MelodicSecurityProperties melodicSecurityProperties;
    private PenaltyFunctionProperties penaltyFunctionProperties;
    private UtilityGeneratorProperties utilityGeneratorProperties;
    private CacheService<NodeCandidates> filecacheService = new FilecacheService();
    private CDOClientX clientX = new CDOClientXImpl(Arrays.asList(TypesPackage.eINSTANCE, CpPackage.eINSTANCE));;
    private JWTService jwtService;
    private BufferedWriter writer;
    private final int MAX_THREADS = 50;

    @Autowired
    public SolverTester(MelodicSecurityProperties melodicSecurityProperties, UtilityGeneratorProperties utilityGeneratorProperties, PenaltyFunctionProperties penaltyFunctionProperties) throws FileNotFoundException {
        this.melodicSecurityProperties = melodicSecurityProperties;
        this.utilityGeneratorProperties = utilityGeneratorProperties;
        this.penaltyFunctionProperties = penaltyFunctionProperties;
        jwtService = new JWTServiceImpl(melodicSecurityProperties);
    }

    void runTests() throws IOException {
        RequestData requestData = gson.fromJson(reader, RequestData.class);
        writer = new BufferedWriter(new FileWriter(requestData.getOutputPath()));
        List<SolverController> solverControllers = prepareControllers(requestData);

        log.info("Received request for  " + solverControllers.size() + " different solvers");

        List<String> results = new LinkedList<>();
        NodeCandidates samplerNodeCandidates = filecacheService.load(requestData.getCpSamplerData().getNodeCandidates());
        Sampler sampler = new Sampler(requestData.getCpSamplerData().getNumberComponents(), requestData.getCpSamplerData().getMinConstraints(), requestData.getCpSamplerData().getMaxConstraints());

        List<Quartet<NodeCandidates, ConstraintProblem, UtilityGeneratorMaster, String>> CPs = getAllNonRandomCP(requestData);
        CPs.addAll(generateRandomCP(requestData, samplerNodeCandidates, sampler));

        Clock clock = Clock.systemDefaultZone();
        long startTime = clock.millis();

        CPs.forEach(parsedCP -> {
            log.info("Testing solvers on CP "+ parsedCP.getValue3());
            for (int i = 1; i <= requestData.getRepetitions(); i++) {
                results.addAll(
                        solverControllers.stream().map(solver -> {
                                return solver.solve(parsedCP.getValue0(), parsedCP.getValue1(), parsedCP.getValue2(), parsedCP.getValue3());
                        }).collect(Collectors.toList())
                );
            }
        });

        long endTime = clock.millis();
        log.info("Calc time: " + (endTime - startTime));
        log.info("Saving results to "+ requestData.getOutputPath());
        results.forEach(result -> {
            try {
                writer.write(result);
            } catch (IOException e) {
                log.info("IOException while saving to " + requestData.getOutputPath());
                e.printStackTrace();
            }
        });
        writer.close();
        log.info("Finished testing, outputs have been written to " + requestData.getOutputPath());
    }

    private boolean usesUtilityConcurrently(Object o) {
        return  o instanceof PTSolverControllerImpl || o instanceof NCSolverControllerImpl || o instanceof PTSolverTemperatureAdjusterControllerImpl;
    }

    private List<SolverController> prepareControllers(RequestData requestData) {
        List<SolverController> solverControllers = new LinkedList<>();
        Arrays.stream(requestData.getTimeLimits()).forEach(timeLimit -> Arrays.stream(requestData.getPtSolversParameters()).forEach(parameters -> solverControllers.add(new PTSolverControllerImpl(parameters, timeLimit))));
        Arrays.stream(requestData.getTimeLimits()).forEach(timeLimit -> Arrays.stream(requestData.getPtSolversParameters()).forEach(parameters -> solverControllers.add(new NCSolverControllerImpl(parameters, timeLimit))));
        Arrays.stream(requestData.getTimeLimits()).forEach(timeLimit -> Arrays.stream(requestData.getGeneticSolverParameters()).forEach(parameters -> solverControllers.add(new GeneticSolverControllerImpl(parameters, timeLimit))));
       // Arrays.stream(requestData.getTimeLimits()).forEach(timeLimit -> solverControllers.add(new ChocoSolverControllerImpl(timeLimit)));
        Arrays.stream(requestData.getTimeLimits()).forEach(timeLimit -> Arrays.stream(requestData.getPtSolversParameters()).map(PTParameters::getNumThreads).distinct().forEach(numThreads -> solverControllers.add(new PTSolverTemperatureAdjusterControllerImpl(numThreads, timeLimit))));
        return solverControllers;
    }

    private List<Quartet<NodeCandidates, ConstraintProblem, UtilityGeneratorMaster, String>> getAllNonRandomCP(RequestData requestData) {
        return Arrays.stream(requestData.getConstraintProblems()).map(this::getCP).collect(Collectors.toList());
    }

    private Quartet<NodeCandidates, ConstraintProblem, UtilityGeneratorMaster, String> getCP(CPFilesData cpFilesData) {
        NodeCandidates nodeCandidates = filecacheService.load(cpFilesData.getNodeCandidatesFilePath());
        ConstraintProblem cp = getCPFromFile(cpFilesData.getCpProblemFilePath());
        UtilityGeneratorMaster utilityGeneratorMaster = new UtilityGeneratorMasterImpl(melodicSecurityProperties, penaltyFunctionProperties, utilityGeneratorProperties, jwtService, nodeCandidates, cpFilesData.getCamelModelFilePath(), cpFilesData.getCpProblemFilePath());
        return new Quartet<>(nodeCandidates, cp, utilityGeneratorMaster, cpFilesData.getId());
    }

    private List<Quartet<NodeCandidates, ConstraintProblem, UtilityGeneratorMaster, String>> generateRandomCP(RequestData requestData, NodeCandidates nodeCandidates, Sampler sampler) {
        List<Map.Entry<TemplateProvider.AvailableTemplates, Double>> utilityTemplate = (Arrays.stream(requestData.getCpSamplerData().getUtilityFunction()).map(TemplateUtilityComponent::parse).collect(Collectors.toList()));

        return IntStream.range(0, requestData.getNumberOfRandomCP()).mapToObj(randomCp -> {
            Pair<ConstraintProblemData, NodeCandidates> sample = sampler.sample(nodeCandidates);
            XMIWriter writer = new XMIWriter();
            try {
                writer.writeToFile(sample.getValue0(), requestData.getCpSamplerData().getCpDirectory() + "sampledCP" + randomCp + ".xmi");
            } catch (IOException e) {
                e.printStackTrace();
            }
            ConstraintProblem cp = getCPFromFile(requestData.getCpSamplerData().getCpDirectory() + "sampledCP" + randomCp + ".xmi");
            UtilityGeneratorMaster utilityGeneratorMaster = new UtilityGeneratorMasterRandomCP(requestData.getCpSamplerData().getCpDirectory() + "sampledCP" + randomCp + ".xmi", sample.getValue1(), utilityTemplate);
            return new Quartet<>(sample.getValue1(), cp, utilityGeneratorMaster, "RandomCP" + randomCp);
        }).collect(Collectors.toList());
    }

    private ConstraintProblem getCPFromFile(String pathName) {
        return (ConstraintProblem) clientX.loadModel(pathName);
    }
}