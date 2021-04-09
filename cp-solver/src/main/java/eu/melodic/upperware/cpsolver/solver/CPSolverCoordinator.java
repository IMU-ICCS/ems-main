package eu.melodic.upperware.cpsolver.solver;

import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.models.commons.NotificationResult;
import eu.melodic.models.commons.NotificationResultImpl;
import eu.melodic.models.commons.Watermark;
import eu.melodic.models.commons.WatermarkImpl;
import eu.melodic.models.services.cpSolver.ConstraintProblemSolutionNotificationRequest;
import eu.melodic.models.services.cpSolver.ConstraintProblemSolutionNotificationRequestImpl;
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTOFactory;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.*;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.passage.upperware.commons.model.tools.CPModelTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static eu.melodic.models.commons.NotificationResult.StatusType.ERROR;
import static eu.melodic.models.commons.NotificationResult.StatusType.SUCCESS;
import static eu.passage.upperware.commons.model.tools.CPModelTool.*;

@Slf4j
@Service
public class CPSolverCoordinator {

    @Autowired
    public CPSolverCoordinator(CPSolver cpSolver, CDOClientX clientX,
                               @Qualifier("memcacheService") CacheService<NodeCandidates> memcacheService,
                               @Qualifier("filecacheService") CacheService<NodeCandidates> filecacheService,
                               Environment env, RestTemplate restTemplate, MelodicSecurityProperties melodicSecurityProperties,
                               JWTService jwtService, PenaltyFunctionProperties penaltyFunctionProperties) {
        this.cpSolver = cpSolver;
        this.clientX = clientX;
        this.memcacheService = memcacheService;
        this.filecacheService = filecacheService;
        this.env = env;
        this.restTemplate = restTemplate;
        this.melodicSecurityProperties = melodicSecurityProperties;
        this.penaltyFunctionProperties = penaltyFunctionProperties;
        this.jwtService = jwtService;
    }

    @SuppressWarnings("CanBeFinal")
    private CPSolver cpSolver;

    private CDOClientX clientX;

    private CacheService<NodeCandidates> memcacheService;
    private CacheService<NodeCandidates> filecacheService;

    private Environment env;

    private RestTemplate restTemplate;

    private MelodicSecurityProperties melodicSecurityProperties;
    private PenaltyFunctionProperties penaltyFunctionProperties;

    private JWTService jwtService;

    @Async
    public void generateCPSolution(String applicationId, String cpResourcePath, String notificationUri, String requestUuid) {
        try {
            NodeCandidates nodeCandidates = memcacheService.load(createCacheKey(cpResourcePath));

            CDOSessionX sessionX = clientX.getSession();
            log.info("Loading resource from CDO: {}", cpResourcePath);
            CDOTransaction trans = sessionX.openTransaction();

            ConstraintProblem cp = getCPFromCDO(cpResourcePath, trans)
                    .orElseThrow(() -> new IllegalStateException("Constraint Problem does not exist in CDO"));

            List<CpSolution> solutions = cpSolver.solve(cp);

            log.info("Found {} solutions:", solutions.size());
            solutions.forEach(solution -> log.info("Solution: {}", solution));

            if (CollectionUtils.isEmpty(solutions)) {
                log.info("Problem is infeasible");
                notifySolutionNotApplied(applicationId, notificationUri, requestUuid);
                return;
            }

            UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(applicationId, cpResourcePath, false, nodeCandidates,
                    melodicSecurityProperties, jwtService, penaltyFunctionProperties);

            double maxUtility = 0.0;
            List<VariableValueDTO> bestSolution = Collections.emptyList();
            for (CpSolution solution : solutions) {
                //calculate utility
                List<VariableValueDTO> result = convertToVariableValues(solution);
                double utility = utilityGenerator.evaluate(result);
                if (utility > maxUtility) {
                    log.debug("New utility value {} is greater than {}", utility, maxUtility);
                    maxUtility = utility;
                    bestSolution = result;
                } else {
                    log.debug("New utility value {} is NOT greater than {}", utility, maxUtility);
                }
            }

            if (maxUtility > 0.0) {
                //save best solution to file
                saveBestSolutionInCDO(cp, maxUtility, bestSolution);
            }

            trans.commit();
            trans.close();
            sessionX.closeSession();

            log.info("Solution has been produced");
            notifySolutionProduced(applicationId, notificationUri, requestUuid);
        } catch (Exception e) {
            log.error("CPSolver returned exception.", e);
            notifySolutionNotApplied(applicationId, notificationUri, requestUuid);
        }
    }

    private List<VariableValueDTO> convertToVariableValues(CpSolution solution) {

        List<VariableValueDTO> intPart = solution.getIntVars()
                .values()
                .stream()
                .map(intVar -> VariableValueDTOFactory.createElement(intVar.getName(), intVar.getValue()))
                .collect(Collectors.toList());

        List<VariableValueDTO> realPart = solution.getRealVars()
                .values()
                .stream()
                .map(realVar -> VariableValueDTOFactory.createElement(realVar.getName(), realVar.getUB()))
                .collect(Collectors.toList());

        return Stream.concat(realPart.stream(), intPart.stream()).collect(Collectors.toList());
    }

    private void saveBestSolutionInCDO(ConstraintProblem cp, double maxUtility, List<VariableValueDTO> bestSolution) {
        log.info("Saving best solution in CDO.....");

        if (!isInitialDeployment(cp)) {
            updateUtilityOfDeployedSolution(cp);
        }

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

    private void updateUtilityOfDeployedSolution(ConstraintProblem cp) {
        log.debug("Updating utility of deployed solution = 0.0");
        Solution deployedSolution = cp.getSolution().get(cp.getDeployedSolutionId());
        log.debug("Previous utility of deployed solution was {}", ((DoubleValueUpperware) deployedSolution.getUtilityValue()).getValue());
        deployedSolution.setUtilityValue(createDoubleValueUpperware(0.0));
    }

    public void generateCPSolutionFromFile(String applicationId, String cpModelFilePath, String nodeCandidatesFilePath) {
        try {
            NodeCandidates nodeCandidates = filecacheService.load(nodeCandidatesFilePath);
            ConstraintProblem cp = getCPFromFile(cpModelFilePath);
            List<CpSolution> solutions = cpSolver.solve(cp);

            log.info("Found {} solutions:", solutions.size());
            solutions.forEach(solution -> log.info("Solution: {}", solution));

            if (CollectionUtils.isEmpty(solutions)) {
                log.info("Problem is infeasible");
                return;
            }

            UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(applicationId, cpModelFilePath,
                    true, nodeCandidates, melodicSecurityProperties, jwtService, penaltyFunctionProperties);

            double maxUtility = 0.0;
            List<VariableValueDTO> bestSolution = Collections.emptyList();
            for (CpSolution solution : solutions) {
                //calculate utility
                List<VariableValueDTO> result = convertToVariableValues(solution);
                double utility = utilityGenerator.evaluate(result);
                if (utility > maxUtility) {
                    log.debug("New utility value {} is greater than {}", utility, maxUtility);
                    maxUtility = utility;
                    bestSolution = result;
                } else {
                    log.debug("New utility value {} is NOT greater than {}", utility, maxUtility);
                }
            }

            if (maxUtility > 0.0) {
                // update cp to contain te best solution
                saveBestSolutionInCDO(cp, maxUtility, bestSolution);
            }

            log.info("Solution has been produced, saving to file " + applicationId);
            // if path was: cpPath.xml, save under: cpPath-solution.xml
            clientX.saveModel(cp, applicationId.split("\\.", 0)[0] + "-solution.xmi");
        } catch (Exception e) {
            log.error("CPSolver returned exception.", e);
        }
    }

    private String createCacheKey(String cdoResourcePath) {
        return cdoResourcePath.substring(cdoResourcePath.indexOf("/") + 1);
    }

    private Optional<ConstraintProblem> getCPFromCDO(String pathName, CDOTransaction trans) {
        CDOResource resource = trans.getResource(pathName);
        return resource.getContents()
                .stream()
                .filter(eObject -> eObject instanceof ConstraintProblem)
                .map(eObject -> (ConstraintProblem) eObject)
                .findFirst();
    }

    private ConstraintProblem getCPFromFile(String pathName) {
        log.info("ConstraintProblem.getCPFromFile: reading file from path " + pathName);
        return (ConstraintProblem) clientX.loadModel(pathName);
    }

    private void notifySolutionProduced(String camelModelID, String notificationUri, String uuid) {
        log.info("Sending solution available notification");
        NotificationResult result = prepareSuccessNotificationResult();
        ConstraintProblemSolutionNotificationRequest notification = prepareNotification(camelModelID, result, uuid);
        sendNotification(notification, notificationUri);
    }

    private void notifySolutionNotApplied(String camelModelID, String notificationUri, String uuid) {
        log.info("Sending solution NOT available notification");
        NotificationResult result = prepareErrorNotificationResult("Solution was not generated.");
        ConstraintProblemSolutionNotificationRequest notification = prepareNotification(camelModelID, result, uuid);
        sendNotification(notification, notificationUri);
    }

    private void sendNotification(ConstraintProblemSolutionNotificationRequest notification, String notificationUri) {
        String esbUrl = env.getProperty("esb.url");

        if (esbUrl.endsWith("/")) {
            esbUrl = esbUrl.substring(0, esbUrl.length() - 1);
        }
        if (notificationUri.startsWith("/")) {
            notificationUri = notificationUri.substring(1);
        }
        try {
            log.info("Sending notification to: {}", esbUrl);
            restTemplate.postForEntity(esbUrl + "/" + notificationUri, notification, String.class);
            log.info("Notification sent.");
        } catch (RestClientException restException) {
            log.error("Error sending notification: ", restException);
        }
    }

    private Watermark prepareWatermark(String uuid) {
        Watermark watermark = new WatermarkImpl();
        watermark.setUser("CPSolver");
        watermark.setSystem("CPSolver");
        watermark.setDate(new Date());
        watermark.setUuid(uuid);
        return watermark;
    }

    private NotificationResult prepareSuccessNotificationResult() {
        NotificationResult result = new NotificationResultImpl();
        result.setStatus(SUCCESS);
        return result;
    }

    private NotificationResult prepareErrorNotificationResult(String errorMsg) {
        NotificationResult result = new NotificationResultImpl();
        result.setErrorDescription(errorMsg);
        result.setStatus(ERROR);
        return result;
    }

    private ConstraintProblemSolutionNotificationRequest prepareNotification(String camelModelID, NotificationResult result, String uuid) {
        ConstraintProblemSolutionNotificationRequest notification = new ConstraintProblemSolutionNotificationRequestImpl();
        notification.setApplicationId(camelModelID);
        notification.setResult(result);
        notification.setWatermark(prepareWatermark(uuid));
        return notification;
    }

}
