package eu.melodic.upperware.pt_solver;

import cp_wrapper.utility_provider.UtilityProviderImpl;
import cp_wrapper.utils.CpVariableCreator;
import cp_wrapper.utils.solution_result_notifier.SolutionResultNotifier;
import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.cache.impl.FilecacheService;
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties;
import eu.melodic.upperware.pt_solver.pt_solver.PTSolver;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.cp.*;
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

import static eu.passage.upperware.commons.model.tools.CPModelTool.*;

@Slf4j
@Service
public class PTSolverCoordinator {

    @Autowired
    public PTSolverCoordinator(CDOClientX clientX,
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
    private int numThreads = 10;
    private int seconds = 10;

    public void generateCPSolutionFromFile(String applicationId, String cpModelFilePath, String nodeCandidatesFilePath) {
        try {
            NodeCandidates nodeCandidates = filecacheService.load(nodeCandidatesFilePath);
            ConstraintProblem cp = getCPFromFile(cpModelFilePath);
            UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(applicationId, cpModelFilePath,
                    true, nodeCandidates, utilityGeneratorProperties, melodicSecurityProperties, jwtService, penaltyFunctionProperties);
            log.info("Starting PT Solver with " + numThreads + " threads for " + seconds + " seconds");
            solve(cp, utilityGenerator);

            clientX.saveModel(cp, applicationId.split("\\.", 0)[0] + "-solution.xmi");
        } catch (Exception e) {
            log.error("PTSolver returned exception.", e);
        }
    }

    @Async
    public void generateCPSolution(String applicationId, String cpResourcePath, String notificationUri, String requestUuid) {
        try {
            NodeCandidates nodeCandidates = memcacheService.load(createCacheKey(cpResourcePath));

            CDOSessionX sessionX = clientX.getSession();
            log.info("Loading resource from CDO: {}", cpResourcePath);
            CDOTransaction trans = sessionX.openTransaction();

            ConstraintProblem cp = getCPFromCDO(cpResourcePath, trans)
                    .orElseThrow(() -> new IllegalStateException("Constraint Problem does not exist in CDO"));
            UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(applicationId, cpResourcePath, false, nodeCandidates, utilityGeneratorProperties,
                    melodicSecurityProperties, jwtService, penaltyFunctionProperties);

            solve(cp, utilityGenerator);

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

    private void solve(ConstraintProblem cp, UtilityGeneratorApplication utilityGenerator) {
        PTSolver solver = new PTSolver(minTemp, maxTemp, numThreads, cp, new UtilityProviderImpl(utilityGenerator));
        Pair<List<VariableValueDTO>, Double> solution = solver.solve(new MaxRuntime(seconds, TimeUnit.SECONDS));
        log.info("Found solution with eu.melodic.upperware.genetic_solver.utility: " + solution.getValue1());

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

        log.info("Solution with best eu.melodic.upperware.genetic_solver.utility {}:", maxUtility);
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
