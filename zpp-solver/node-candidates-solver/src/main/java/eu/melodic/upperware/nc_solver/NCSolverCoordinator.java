package eu.melodic.upperware.nc_solver;

import cp_wrapper.utility_provider.implementations.UtilityProviderImpl;
import cp_wrapper.utils.solution_result_notifier.SolutionResultNotifier;
import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.cache.impl.FilecacheService;
import eu.melodic.upperware.nc_solver.nc_solver.NCSolver;
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.*;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.passage.upperware.commons.model.tools.CPModelTool;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.jamesframework.core.search.stopcriteria.MaxRuntime;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static eu.passage.upperware.commons.model.tools.CPModelTool.*;

@Slf4j
@Service
public class NCSolverCoordinator {

    @Autowired
    public NCSolverCoordinator(CDOClientX clientX,
                               @Qualifier("memcacheService") CacheService<NodeCandidates> memcacheService,
                               UtilityGeneratorProperties utilityGeneratorProperties, Environment env,
                               RestTemplate restTemplate, MelodicSecurityProperties melodicSecurityProperties,
                               JWTService jwtService, PenaltyFunctionProperties penaltyFunctionProperties) {
        this.clientX = clientX;
        this.filecacheService = new FilecacheService();
        this.memcacheService = memcacheService;
        this.utilityGeneratorProperties = utilityGeneratorProperties;
        this.env = env;
        this.restTemplate = restTemplate;
        this.melodicSecurityProperties = melodicSecurityProperties;
        this.penaltyFunctionProperties = penaltyFunctionProperties;
        this.jwtService = jwtService;
        solutionResultNotifier = new SolutionResultNotifier(env, restTemplate);
    }

    private CDOClientX clientX;

    private CacheService<NodeCandidates> memcacheService;
    private CacheService<NodeCandidates> filecacheService;

    private UtilityGeneratorProperties utilityGeneratorProperties;
    private Environment env;

    private RestTemplate restTemplate;

    private MelodicSecurityProperties melodicSecurityProperties;
    private PenaltyFunctionProperties penaltyFunctionProperties;

    private JWTService jwtService;

    private SolutionResultNotifier solutionResultNotifier;
    private double minTemp = 100;
    private double maxTemp = 10000;
    private int numThreads = 1;


    public void generateCPSolutionFromFile(String applicationId, String cpModelFilePath, String nodeCandidatesFilePath, int seconds) {
        try {
            NodeCandidates nodeCandidates = filecacheService.load(nodeCandidatesFilePath);
            ConstraintProblem cp = getCPFromFile(cpModelFilePath);
            UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(applicationId, cpModelFilePath,
                    true, nodeCandidates, utilityGeneratorProperties, melodicSecurityProperties, jwtService, penaltyFunctionProperties);
            log.info("Starting NC Solver with " + numThreads + " threads for " + seconds + " seconds");
            solve(nodeCandidates, cp, utilityGenerator, seconds);

            clientX.saveModel(cp, applicationId.split("\\.", 0)[0] + "-solution.xmi");

        } catch (Exception e) {
            log.error("NCSolver returned exception.", e);
        }
    }

    @Async
    public void generateCPSolution(String applicationId, String cpResourcePath, String notificationUri, String requestUuid, int seconds) {
        try {
            NodeCandidates nodeCandidates = memcacheService.load(createCacheKey(cpResourcePath));

            CDOSessionX sessionX = clientX.getSession();
            log.info("Loading resource from CDO: {}", cpResourcePath);
            CDOTransaction trans = sessionX.openTransaction();

            ConstraintProblem cp = getCPFromCDO(cpResourcePath, trans)
                    .orElseThrow(() -> new IllegalStateException("Constraint Problem does not exist in CDO"));
            UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(applicationId, cpResourcePath, false, nodeCandidates, utilityGeneratorProperties,
                    melodicSecurityProperties, jwtService, penaltyFunctionProperties);

            solve(nodeCandidates, cp, utilityGenerator, seconds);

            trans.commit();
            trans.close();
            sessionX.closeSession();

            log.info("Solution has been produced");
            solutionResultNotifier.notifySolutionProduced(applicationId, notificationUri, requestUuid);
        } catch (Exception e) {
            log.error("CPSolver returned exception.", e);
            solutionResultNotifier.notifySolutionNotApplied(applicationId, notificationUri, requestUuid);
        }
    }

    private void solve(NodeCandidates nodeCandidates, ConstraintProblem cp, UtilityGeneratorApplication utilityGenerator, int seconds) {
        NCSolver solver = new NCSolver(minTemp, maxTemp, numThreads, cp, new UtilityProviderImpl(utilityGenerator), nodeCandidates);
        Pair<List<VariableValueDTO>, Double> solution = solver.solve(new MaxRuntime(seconds, TimeUnit.SECONDS));
        log.info("Found solution with utility: " + solution.getValue1());

        if (solution.getValue1() > 0.0) {
            saveBestSolutionInCDO(cp, solution.getValue1(), solution.getValue0());
        }
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

    private Optional<ConstraintProblem> getCPFromCDO(String pathName, CDOTransaction trans) {
        CDOResource resource = trans.getResource(pathName);
        return resource.getContents()
                .stream()
                .filter(eObject -> eObject instanceof ConstraintProblem)
                .map(eObject -> (ConstraintProblem) eObject)
                .findFirst();
    }

    private String createCacheKey(String cdoResourcePath) {
        return cdoResourcePath.substring(cdoResourcePath.indexOf("/") + 1);
    }
}