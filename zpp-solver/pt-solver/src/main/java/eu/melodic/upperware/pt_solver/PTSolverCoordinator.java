package eu.melodic.upperware.pt_solver;

import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.cache.impl.FilecacheService;
import eu.melodic.upperware.cp_wrapper.utility_provider.implementations.ParallelUtilityProviderImpl;
import eu.melodic.upperware.cp_wrapper.utils.cp_variable.CpVariableCreator;
import eu.melodic.upperware.cp_wrapper.utils.solution_result_notifier.SolutionResultNotifier;
import eu.melodic.upperware.pt_solver.pt_solver.PTSolver;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluator;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpVariableValue;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
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
import java.util.stream.IntStream;

import static eu.passage.upperware.commons.model.tools.CPModelTool.createSolution;

@Slf4j
@Service
public class PTSolverCoordinator {

    @Autowired
    public PTSolverCoordinator(CDOClientX clientX,
                               @Qualifier("memcacheService") CacheService<NodeCandidates> memcacheService,
                               Environment env, RestTemplate restTemplate, MelodicSecurityProperties melodicSecurityProperties,
                               JWTService jwtService) {
        this.clientX = clientX;
        this.filecacheService = new FilecacheService();
        this.memcacheService = memcacheService;
        this.env = env;
        this.restTemplate = restTemplate;
        this.melodicSecurityProperties = melodicSecurityProperties;
        this.jwtService = jwtService;
        solutionResultNotifier = new SolutionResultNotifier(env, restTemplate);
    }

    private CDOClientX clientX;

    private CacheService<NodeCandidates> memcacheService;
    private CacheService<NodeCandidates> filecacheService;

    private Environment env;

    private RestTemplate restTemplate;

    private MelodicSecurityProperties melodicSecurityProperties;

    private JWTService jwtService;

    private SolutionResultNotifier solutionResultNotifier;
    private double minTemp = 100;
    private double maxTemp = 10000;
    private int numThreads = 10;

    public void generateCPSolutionFromFile(String applicationId, String cpModelFilePath, String nodeCandidatesFilePath, int seconds) {
        try {
            NodeCandidates nodeCandidates = filecacheService.load(nodeCandidatesFilePath);
            ConstraintProblem cp = getCPFromFile(cpModelFilePath);
            List<UtilityFunctionEvaluator> utilityGenerator = IntStream.range(0, numThreads).mapToObj( index -> new UtilityFunctionEvaluator(applicationId, cpModelFilePath,
                    true, nodeCandidates, melodicSecurityProperties, jwtService)).collect(Collectors.toList());
            log.info("Starting PT Solver with " + numThreads + " threads for " + seconds + " seconds");
            solve(cp, utilityGenerator, seconds);

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
            List<UtilityFunctionEvaluator> utilityGenerators = IntStream.range(0, numThreads)
                    .mapToObj(index -> new UtilityFunctionEvaluator(applicationId, cpResourcePath, false, nodeCandidates, melodicSecurityProperties, jwtService))
                    .collect(Collectors.toList());

            solve(cp, utilityGenerators, seconds);

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

    private void solve(ConstraintProblem cp, List<UtilityFunctionEvaluator> utilityGenerators, int seconds) {
        PTSolver solver = new PTSolver(minTemp, maxTemp, numThreads, cp, new ParallelUtilityProviderImpl(utilityGenerators));
        Pair<List<VariableValueDTO>, Double> solution = solver.solve(new MaxRuntime(seconds, TimeUnit.SECONDS));
        log.info("Found solution with utility: {}", solution.getValue1());

        if (solution.getValue1() > 0.0) {
            saveBestSolutionInCDO(cp, solution.getValue1(), solution.getValue0());
        }
    }

    public void generateCPSolutionFromFileWithTemplate(String cpModelPath, String nodeCandidatesFilePath) {
        //TODO
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
