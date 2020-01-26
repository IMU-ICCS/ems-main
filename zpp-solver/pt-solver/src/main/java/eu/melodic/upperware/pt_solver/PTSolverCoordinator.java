package eu.melodic.upperware.pt_solver;

import cp_wrapper.utility_provider.UtilityProviderImpl;
import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.cache.impl.FilecacheService;
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties;
import eu.melodic.upperware.pt_solver.pt_solver.PTSolver;
import eu.melodic.upperware.pt_solver.pt_solver.components.PTSolution;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.*;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.passage.upperware.commons.model.tools.CPModelTool;
import lombok.extern.slf4j.Slf4j;
import org.jamesframework.core.search.stopcriteria.MaxRuntime;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static eu.passage.upperware.commons.model.tools.CPModelTool.*;
import static eu.passage.upperware.commons.model.tools.CPModelTool.createFloatValueUpperware;

@Slf4j
@Service
public class PTSolverCoordinator {

    @Autowired
    public PTSolverCoordinator(CDOClientX clientX,
                               UtilityGeneratorProperties utilityGeneratorProperties, Environment env,
                               RestTemplate restTemplate, MelodicSecurityProperties melodicSecurityProperties,
                               JWTService jwtService, PenaltyFunctionProperties penaltyFunctionProperties) {
        this.clientX = clientX;
        this.filecacheService = new FilecacheService();
        this.utilityGeneratorProperties = utilityGeneratorProperties;
        this.env = env;
        this.restTemplate = restTemplate;
        this.melodicSecurityProperties = melodicSecurityProperties;
        this.penaltyFunctionProperties = penaltyFunctionProperties;
        this.jwtService = jwtService;
    }

    private CDOClientX clientX;

    private CacheService<NodeCandidates> filecacheService;

    private UtilityGeneratorProperties utilityGeneratorProperties;
    private Environment env;

    private RestTemplate restTemplate;

    private MelodicSecurityProperties melodicSecurityProperties;
    private PenaltyFunctionProperties penaltyFunctionProperties;

    private JWTService jwtService;

    private double minTemp = 1;
    private double maxTemp = 100;
    private int numThreads = 10;
    private int seconds = 10;

    public void generateCPSolutionFromFile(String applicationId, String cpModelFilePath, String nodeCandidatesFilePath) {
        try {
            NodeCandidates nodeCandidates = filecacheService.load(nodeCandidatesFilePath);
            ConstraintProblem cp = getCPFromFile(cpModelFilePath);
            UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(applicationId, cpModelFilePath,
                    true, nodeCandidates, utilityGeneratorProperties, melodicSecurityProperties, jwtService, penaltyFunctionProperties);
            log.info("Starting PT Solver with " + numThreads + " threads for " + seconds + " seconds");
            PTSolver solver = new PTSolver(minTemp, maxTemp, numThreads, cp, new UtilityProviderImpl(utilityGenerator));
            Pair<List<VariableValueDTO>, Double> solution = solver.solve(new MaxRuntime(seconds, TimeUnit.SECONDS));
            log.info("Found solution with utility: " + solution.getValue1());

            if (solution.getValue1() > 0.0) {
                saveBestSolutionInCDO(cp, solution.getValue1(), solution.getValue0());
            }

            clientX.saveModel(cp, applicationId.split("\\.", 0)[0] + "-solution.xmi");
        } catch (Exception e) {
            log.error("CPSolver returned exception.", e);
        }
    }

    public void generateCPSolutionFromFileWithTemplate(String cpModelPath, String nodeCandidatesFilePath) {
       //TODO
    }

    private void saveBestSolutionInCDO(ConstraintProblem cp, double maxUtility, List<VariableValueDTO> bestSolution) {
        log.info("Saving best solution in CDO.....");


        List<CpVariableValue> values = cp.getCpVariables()
                .stream()
                .map(var -> createCpVariableValue(bestSolution, var))
                .collect(Collectors.toList());

        log.info("Solution with best utility {}:", maxUtility);
        for (VariableValueDTO variableValueDTO : bestSolution) {
            log.info("\t{}: {}", variableValueDTO.getName(), variableValueDTO.getValue());
        }

        cp.getSolution().add(createSolution(maxUtility, values));
    }

    private CpVariableValue createCpVariableValue(List<VariableValueDTO> bestSolution, CpVariable var) {
        log.debug("Considering variable: {}", var.getId());
        Domain dom = var.getDomain();
        if (dom instanceof RangeDomain) {
            RangeDomain rd = (RangeDomain) dom;
            NumericValueUpperware from = rd.getFrom();
            if (from instanceof IntegerValueUpperware) {
                Number variableValue = findVariableValue(bestSolution, var);
                return CPModelTool.createCpVariableValue(var, createIntegerValueUpperware(variableValue.intValue()));
            } else if (from instanceof LongValueUpperware) {
                Number variableValue = findVariableValue(bestSolution, var);
                return CPModelTool.createCpVariableValue(var, createLongValueUpperware(variableValue.longValue()));
            } else if (from instanceof DoubleValueUpperware) {
                Number variableValue = findVariableValue(bestSolution, var);
                return CPModelTool.createCpVariableValue(var, createDoubleValueUpperware(variableValue.doubleValue()));
            } else {
                Number variableValue = findVariableValue(bestSolution, var);
                return CPModelTool.createCpVariableValue(var, createFloatValueUpperware(variableValue.floatValue()));
            }
        } else if (dom instanceof NumericDomain) {
            NumericDomain nd = (NumericDomain) dom;
            BasicTypeEnum type = nd.getType();
            if (type.equals(BasicTypeEnum.INTEGER)) {
                Number variableValue = findVariableValue(bestSolution, var);
                return CPModelTool.createCpVariableValue(var, createIntegerValueUpperware(variableValue.intValue()));
            } else if (type.equals(BasicTypeEnum.LONG)) {
                Number variableValue = findVariableValue(bestSolution, var);
                return CPModelTool.createCpVariableValue(var, createLongValueUpperware(variableValue.longValue()));
            } else if (type.equals(BasicTypeEnum.DOUBLE)) {
                Number variableValue = findVariableValue(bestSolution, var);
                return CPModelTool.createCpVariableValue(var, createDoubleValueUpperware(variableValue.doubleValue()));
            } else {
                Number variableValue = findVariableValue(bestSolution, var);
                return CPModelTool.createCpVariableValue(var, createFloatValueUpperware(variableValue.floatValue()));
            }
        }
        throw new RuntimeException("Unsupported method type: " + dom.getClass());
    }

    private Number findVariableValue(List<VariableValueDTO> bestSolution, CpVariable var) {
        return bestSolution
                .stream()
                .filter(variableValueDTO -> variableValueDTO.getName().equals(var.getId()))
                .findFirst().orElseThrow(() -> new RuntimeException("Could not find VariableValue for " + var.getId()))
                .getValue();
    }

    private ConstraintProblem getCPFromFile(String pathName) {
        log.info("ConstraintProblem.getCPFromFile: reading file from path " + pathName);
        return (ConstraintProblem) clientX.loadModel(pathName);
    }
}
