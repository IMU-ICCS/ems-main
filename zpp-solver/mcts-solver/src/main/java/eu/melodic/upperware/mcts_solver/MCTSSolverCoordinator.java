package eu.melodic.upperware.mcts_solver;

import cp_wrapper.utility_provider.implementations.UtilityProviderFromCDOFactory;
import cp_wrapper.utils.cp_variable.CpVariableCreator;
import cp_wrapper.utils.solution_result_notifier.SolutionResultNotifier;
import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.cache.impl.FilecacheService;
import eu.melodic.upperware.mcts_solver.solver.MCTSSolver;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapperFactoryImpl;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.policy.AvailablePolicies;
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties;
import cp_wrapper.solution.CpSolution;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static eu.passage.upperware.commons.model.tools.CPModelTool.createSolution;

@Slf4j
@Service
public class MCTSSolverCoordinator {

    @Autowired
    public MCTSSolverCoordinator(CDOClientX clientX,
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
    private final double MIN_TMP = 0.001;
    private final double MAX_TMP = 0.9;
    private final int NUM_THREADS = 5;
    private final int ITERATIONS = 100;
    private final MCTSSolver mctsSolver = new MCTSSolver(NUM_THREADS, MIN_TMP, MAX_TMP, ITERATIONS, AvailablePolicies.CHEAPEST_POLICY, false);

    public void generateCPSolutionFromFile(String applicationId, String cpModelFilePath, String nodeCandidatesFilePath, int seconds) {
        try {
            NodeCandidates nodeCandidates = filecacheService.load(nodeCandidatesFilePath);
            ConstraintProblem cp = getCPFromFile(cpModelFilePath);
            List<UtilityGeneratorApplication> utilityGenerator = IntStream.range(0, NUM_THREADS).mapToObj( index -> new UtilityGeneratorApplication(applicationId, cpModelFilePath,
                    true, nodeCandidates, utilityGeneratorProperties, melodicSecurityProperties, jwtService, penaltyFunctionProperties)).collect(Collectors.toList());
            log.info("Starting PT Solver with " + NUM_THREADS + " threads for " + seconds + " seconds");
            solve(cp, utilityGenerator, seconds, nodeCandidates);

            clientX.saveModel(cp, applicationId.split("\\.", 0)[0] + "-solution.xmi");
        } catch (Exception e) {
            log.error("PTSolver returned exception.", e);
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
            List<UtilityGeneratorApplication> utilityGenerators = IntStream.range(0, NUM_THREADS).mapToObj(index -> new UtilityGeneratorApplication(applicationId, cpResourcePath, false, nodeCandidates, utilityGeneratorProperties,
                    melodicSecurityProperties, jwtService, penaltyFunctionProperties)).collect(Collectors.toList());

            solve(cp, utilityGenerators, seconds, nodeCandidates);

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

    private void solve(ConstraintProblem cp, List<UtilityGeneratorApplication> utilityGenerators, int seconds, NodeCandidates nodeCandidates) throws InterruptedException {
        CpSolution solution = mctsSolver.solve(seconds, new MCTSWrapperFactoryImpl(new UtilityProviderFromCDOFactory(utilityGenerators), cp, nodeCandidates));
        log.info("Found solution with utility: " + solution.getUtility());

        if (solution.getUtility() > 0.0) {
            saveBestSolutionInCDO(cp, solution.getUtility(), solution.getSolution());
        }
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


    private ConstraintProblem getCPFromFile(String pathName) {
        log.info("ConstraintProblem.getCPFromFile: reading file from path " + pathName);
        return (ConstraintProblem) clientX.loadModel(pathName);
    }

}
