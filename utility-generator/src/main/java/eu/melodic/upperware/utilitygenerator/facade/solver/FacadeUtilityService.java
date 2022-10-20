package eu.melodic.upperware.utilitygenerator.facade.solver;

import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluator;
import eu.melodic.upperware.utilitygenerator.facade.solver.event.ExternalCallFinishedEvent;
import eu.melodic.upperware.utilitygenerator.facade.solver.event.SolverExternalCallFinishedEvent;
import eu.morphemic.facade.AbstractTextRequesterFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class FacadeUtilityService {

    protected static final String RESULT_PROPERTY_NAME = "result";

    private final ApplicationEventPublisher applicationEventPublisher;
    private final Map<String, UtilityFunctionEvaluator> utilityGenerators;

    private final CacheService<NodeCandidates> cacheService;

    @Autowired
    public FacadeUtilityService(ApplicationEventPublisher applicationEventPublisher, @Qualifier("memcacheService") CacheService<NodeCandidates> cacheService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.cacheService = cacheService;
        utilityGenerators = new ConcurrentHashMap<>();
    }

    public Map<String, Object> processCamundaRequest(Map<String, Object> data) {
        log.info("asynch processCamundaRequest: {}", data);
        Map<String, Object> result = new HashMap<>();
        result.put(CamundaFacadeImpl.PROCESS_ID, data.get(CamundaFacadeImpl.PROCESS_ID));

        String camelModelFilePath = (String) data.get(CamundaFacadeImpl.CAMEL_MODEL_FILE_PATH_PROPERTY_NAME);

        if(utilityGenerators.get(camelModelFilePath) != null) {
            final String message = "Duplicate UG registration request for app-id " + camelModelFilePath;
            log.warn(message);
            result.put(RESULT_PROPERTY_NAME, message);
        }
        else {
            String cpModelFilePath = (String) data.get(CamundaFacadeImpl.CP_MODEL_FILE_PATH_PROPERTY_NAME);
            boolean readFromFile = data.get(CamundaFacadeImpl.READ_FROM_FILE_PROPERTY_NAME) != null && Boolean.parseBoolean((String) data.get(CamundaFacadeImpl.READ_FROM_FILE_PROPERTY_NAME));
            String nodeCandidatesFilePath = (String) data.get(CamundaFacadeImpl.NODE_CANDIDATES_FILE_PATH_PROPERTY_NAME);

            NodeCandidates nodeCandidates = cacheService.load(createCacheKey(nodeCandidatesFilePath));

            UtilityFunctionEvaluator evaluator = new UtilityFunctionEvaluator(camelModelFilePath, cpModelFilePath, readFromFile, nodeCandidates);
            utilityGenerators.put(camelModelFilePath, evaluator);
            result.put(RESULT_PROPERTY_NAME, "success");
        }

        return result;
    }

    private String createCacheKey(String cdoResourcePath) {
        return cdoResourcePath.substring(cdoResourcePath.indexOf("/") + 1);
    }

    @Async
    public void processSolverRequest(Map<String, Object> data) {
        log.info("asynch processSolverRequest: {}", data);
        Map<String, Object> result = new HashMap<>();
        result.put(AbstractTextRequesterFacade.SENDER_ID_PROPERTY_NAME, data.get(AbstractTextRequesterFacade.SENDER_ID_PROPERTY_NAME));

        Map<String, Number> solutionMap = (Map<String, Number>) data.get(SolverFacadeImpl.SOLUTION_PROPERTY_NAME);
        String appID = (String) data.get(SolverFacadeImpl.TARGET_PROPERTY_NAME);
        if(solutionMap == null || solutionMap.isEmpty() || appID == null || appID.trim().isEmpty()) {
            final String message = "Incomplete parameters";
            log.warn(message);
            result.put(RESULT_PROPERTY_NAME, message);

            ExternalCallFinishedEvent event = new SolverExternalCallFinishedEvent(result, this);
            applicationEventPublisher.publishEvent(event);
            return;
        }

        if(!utilityGenerators.containsKey(appID)) {
            final String message = "No UtilityGenerator present for app-ID " + appID;
            log.warn(message);
            result.put(RESULT_PROPERTY_NAME, message);

            ExternalCallFinishedEvent event = new SolverExternalCallFinishedEvent(result, this);
            applicationEventPublisher.publishEvent(event);
            return;
        }

        Collection<VariableValueDTO> solution = new ArrayList<>(solutionMap.keySet().size());
        for(Map.Entry<String, Number> entry : solutionMap.entrySet()){
            solution.add(new VariableValueDTO(entry.getKey(), entry.getValue()));
        }

        UtilityFunctionEvaluator evaluator = utilityGenerators.get(appID);
        double utilityValue = evaluator.evaluate(solution);

        result.put(SolverFacadeImpl.UTILITY_VALUE_PROPERTY_NAME, utilityValue);

        ExternalCallFinishedEvent event = new SolverExternalCallFinishedEvent(result, this);
        applicationEventPublisher.publishEvent(event);
    }

}
