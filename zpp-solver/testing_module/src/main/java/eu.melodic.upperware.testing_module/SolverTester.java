package eu.melodic.upperware.testing_module;

import com.google.gson.Gson;
import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.cache.impl.FilecacheService;
import eu.melodic.upperware.cp_sampler.Sampler;
import eu.melodic.upperware.cp_sampler.constraint_problem_data.ConstraintProblemData;
import eu.melodic.upperware.cp_sampler.xmi_writer.XMIWriter;
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties;
import eu.melodic.upperware.testing_module.solvers.*;
import eu.melodic.upperware.testing_module.utils.CPFilesData;
import eu.melodic.upperware.testing_module.utils.RequestData;
import eu.melodic.upperware.testing_module.utils.TemplateUtilityComponent;
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
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
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
    private Reader reader = new FileReader("C:/Users/ZPP/Desktop/genetic/zpp-solver/test-module2/src/main/resources/request.json");
    private MelodicSecurityProperties melodicSecurityProperties;
    private PenaltyFunctionProperties penaltyFunctionProperties;
    private UtilityGeneratorProperties utilityGeneratorProperties;
    private CacheService<NodeCandidates> filecacheService = new FilecacheService();
    private CDOClientX clientX = new CDOClientXImpl(Arrays.asList(TypesPackage.eINSTANCE, CpPackage.eINSTANCE));;
    private JWTService jwtService;
    private BufferedWriter writer;

    @Autowired
    public SolverTester(MelodicSecurityProperties melodicSecurityProperties, UtilityGeneratorProperties utilityGeneratorProperties, PenaltyFunctionProperties penaltyFunctionProperties) throws FileNotFoundException {
        this.melodicSecurityProperties = melodicSecurityProperties;
        this.utilityGeneratorProperties = utilityGeneratorProperties;
        this.penaltyFunctionProperties = penaltyFunctionProperties;
        jwtService = new JWTServiceImpl(melodicSecurityProperties);
    }

    public void runTests() throws IOException {
        RequestData requestData = gson.fromJson(reader, RequestData.class);
        writer = new BufferedWriter(new FileWriter(requestData.getOutputPath()));
        List<SolverController> solverControllers = prepareControllers(requestData);

        log.info("Received request for  " + solverControllers.size() + " different solvers");

        List<String> results = new LinkedList<>();
        NodeCandidates samplerNodeCandidates = filecacheService.load(requestData.getCpSamplerData().getNodeCandidates());
        Sampler sampler = new Sampler(requestData.getCpSamplerData().getNumberComponents(), requestData.getCpSamplerData().getMaxConstraints(), requestData.getCpSamplerData().getMinConstraints());

        List<Quartet<NodeCandidates, ConstraintProblem, UtilityGeneratorApplication, String>> CPs = getAllNonRandomCP(requestData);
        CPs.addAll(generatedRandomCP(requestData, samplerNodeCandidates, sampler));

        CPs.forEach(parsedCP -> {
            log.info("Testing solvers on CP "+ parsedCP.getValue3());
            for (int i = 1; i < requestData.getRepetitions(); i++) {
                results.addAll(
                        solverControllers.stream().map(solver -> solver.solve(parsedCP.getValue0(), parsedCP.getValue1(), parsedCP.getValue2(), parsedCP.getValue3())).collect(Collectors.toList())
                );
            }
        });
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

    private List<SolverController> prepareControllers(RequestData requestData) {
        List<SolverController> solverControllers = new LinkedList<>();
        Arrays.stream(requestData.getTimeLimits()).forEach(timeLimit -> Arrays.stream(requestData.getPtSolversParameters()).forEach(parameters -> solverControllers.add(new PTSolverControllerImpl(parameters, timeLimit))));
        Arrays.stream(requestData.getTimeLimits()).forEach(timeLimit -> Arrays.stream(requestData.getPtSolversParameters()).forEach(parameters -> solverControllers.add(new NCSolverControllerImpl(parameters, timeLimit))));
        Arrays.stream(requestData.getTimeLimits()).forEach(timeLimit -> Arrays.stream(requestData.getGeneticSolverParameters()).forEach(parameters -> solverControllers.add(new GeneticSolverControllerImpl(parameters, timeLimit))));
        Arrays.stream(requestData.getTimeLimits()).forEach(timeLimit -> solverControllers.add(new ChocoSolverControllerImpl(timeLimit)));
        return solverControllers;
    }

    private List<Quartet<NodeCandidates, ConstraintProblem, UtilityGeneratorApplication, String>> getAllNonRandomCP(RequestData requestData) {
        return Arrays.stream(requestData.getConstraintProblems()).map(this::getCP).collect(Collectors.toList());
    }

    private Quartet<NodeCandidates, ConstraintProblem, UtilityGeneratorApplication, String> getCP(CPFilesData cpFilesData) {
        NodeCandidates nodeCandidates = filecacheService.load(cpFilesData.getNodeCandidatesFilePath());
        ConstraintProblem cp = getCPFromFile(cpFilesData.getCpProblemFilePath());
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(cpFilesData.getCamelModelFilePath(), cpFilesData.getCpProblemFilePath(),
                true, nodeCandidates, utilityGeneratorProperties, melodicSecurityProperties, jwtService, penaltyFunctionProperties);
        return new Quartet<>(nodeCandidates, cp, utilityGenerator, cpFilesData.getId());
    }

    private List<Quartet<NodeCandidates, ConstraintProblem, UtilityGeneratorApplication, String>> generatedRandomCP(RequestData requestData, NodeCandidates nodeCandidates, Sampler sampler) {
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
            UtilityGeneratorApplication utilityGeneratorApplication = new UtilityGeneratorApplication(requestData.getCpSamplerData().getCpDirectory() + "sampledCP" + randomCp + ".xmi",
                    nodeCandidates, utilityTemplate);
            return new Quartet<>(sample.getValue1(), cp, utilityGeneratorApplication, "RandomCP" + randomCp);
        }).collect(Collectors.toList());
    }

    private ConstraintProblem getCPFromFile(String pathName) {
        return (ConstraintProblem) clientX.loadModel(pathName);
    }
}