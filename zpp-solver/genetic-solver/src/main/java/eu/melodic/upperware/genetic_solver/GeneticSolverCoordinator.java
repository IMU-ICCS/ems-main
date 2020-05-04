package eu.melodic.upperware.genetic_solver;

import cp_wrapper.utility_provider.implementations.UtilityProviderImpl;
import cp_wrapper.utils.cp_variable.CpVariableCreator;
import cp_wrapper.utils.solution_result_notifier.SolutionResultNotifier;
import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.cache.impl.FilecacheService;
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpVariableValue;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import eu.melodic.upperware.genetic_solver.runner.GeneticSolverRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static eu.passage.upperware.commons.model.tools.CPModelTool.createSolution;

@Slf4j
@Service
public class GeneticSolverCoordinator {

    @Autowired
    public GeneticSolverCoordinator(CDOClientX clientX,
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
    private int populationSize = 100;

    public void generateCPSolutionFromFile(String applicationId, String cpModelFilePath, String nodeCandidatesFilePath, int timeLimit) {
        try {
            NodeCandidates nodeCandidates = filecacheService.load(nodeCandidatesFilePath);
            ConstraintProblem cp = getCPFromFile(cpModelFilePath);
            UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(applicationId, cpModelFilePath,
                    true, nodeCandidates, utilityGeneratorProperties, melodicSecurityProperties, jwtService, penaltyFunctionProperties);

            boolean solutionFeasible = solve(cp, utilityGenerator, timeLimit);
            if (!solutionFeasible) {
                log.info("Solution is not feasible!");
                return;
            }

        } catch (Exception e) {
            log.error("GeneticSolver returned exception.", e);
        }
    }

    @Async
    public void generateCPSolution(String applicationId, String cpResourcePath, String notificationUri, String requestUuid, int timeLimit) {
        try {
            NodeCandidates nodeCandidates = memcacheService.load(createCacheKey(cpResourcePath));

            CDOSessionX sessionX = clientX.getSession();
            log.info("Loading resource from CDO: {}", cpResourcePath);
            CDOTransaction trans = sessionX.openTransaction();

            ConstraintProblem cp = getCPFromCDO(cpResourcePath, trans)
                    .orElseThrow(() -> new IllegalStateException("Constraint Problem does not exist in CDO"));
            UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(applicationId, cpResourcePath, false, nodeCandidates, utilityGeneratorProperties,
                    melodicSecurityProperties, jwtService, penaltyFunctionProperties);

            Boolean solutionFeasible = solve(cp, utilityGenerator, timeLimit);

            if (!solutionFeasible) {
                log.info("Problem is infeasible");
                solutionResultNotifier.notifySolutionNotApplied(applicationId, notificationUri, requestUuid);
                return;
            }

            trans.commit();
            trans.close();
            sessionX.closeSession();

            log.info("Solution has been produced");
            solutionResultNotifier.notifySolutionProduced(applicationId, notificationUri, requestUuid);
        } catch (Exception e) {
            log.error("GeneticSolver returned exception.", e);
            solutionResultNotifier.notifySolutionNotApplied(applicationId, notificationUri, requestUuid);
        }
    }

    private boolean solve(ConstraintProblem cp, UtilityGeneratorApplication utilityGenerator, int timeLimit) {
        GeneticSolverRunner runner = new GeneticSolverRunner();
        runner.setPopulationSize(populationSize);
        runner.setTimeLimitSeconds(timeLimit);
        List<VariableValueDTO> solution = runner.run(cp, new UtilityProviderImpl(utilityGenerator));
        log.info("Found solution with utility: " + runner.getFinalUtility());

        if (runner.getFinalUtility() > 0.0) {
            saveBestSolutionInCDO(cp, runner.getFinalUtility(), solution);
            return true;
        } else {
            return false;
        }
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

    private void saveBestSolutionInCDO(ConstraintProblem cp, double maxUtility, List<VariableValueDTO> bestSolution) {
        log.info("Saving best solution in CDO.....");


        List<CpVariableValue> values = cp.getCpVariables()
                .stream()
                .map(var -> CpVariableCreator.createCpVariableValue(bestSolution, var))
                .collect(Collectors.toList());

        log.info("Solution with best utility {}:", maxUtility);
        for (VariableValueDTO variableValueDTO : bestSolution) {
            log.info("\t{}: {}", variableValueDTO.getName(), variableValueDTO.getValue());
        }

        cp.getSolution().add(createSolution(maxUtility, values));
    }

    private String createCacheKey(String cdoResourcePath) {
        return cdoResourcePath.substring(cdoResourcePath.indexOf("/") + 1);
    }
}