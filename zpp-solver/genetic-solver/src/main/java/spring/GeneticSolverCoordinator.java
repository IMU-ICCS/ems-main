package spring;

import cp_wrapper.utility_provider.UtilityProviderImpl;
import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.cache.impl.FilecacheService;
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import runner.GeneticSolverRunner;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class GeneticSolverCoordinator {

    @Autowired
    public GeneticSolverCoordinator(CDOClientX clientX,
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

    public void generateCPSolutionFromFile(String applicationId, String cpModelFilePath, String nodeCandidatesFilePath) {
        try {
            NodeCandidates nodeCandidates = filecacheService.load(nodeCandidatesFilePath);
            ConstraintProblem cp = getCPFromFile(cpModelFilePath);
            UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(applicationId, cpModelFilePath,
                    true, nodeCandidates, utilityGeneratorProperties, melodicSecurityProperties, jwtService, penaltyFunctionProperties);

            GeneticSolverRunner runner = new GeneticSolverRunner();
            runner.setTimeLimit(1);
            runner.run(cp, new UtilityProviderImpl(utilityGenerator));
            log.info("Found solution with utility: " + runner.getFinalUtility());

        } catch (Exception e) {
            log.error("CPSolver returned exception.", e);
        }
    }

    private ConstraintProblem getCPFromFile(String pathName) {
        log.info("ConstraintProblem.getCPFromFile: reading file from path " + pathName);
        return (ConstraintProblem) clientX.loadModel(pathName);
    }

}