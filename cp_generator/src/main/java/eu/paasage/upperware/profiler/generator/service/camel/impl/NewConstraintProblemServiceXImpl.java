package eu.paasage.upperware.profiler.generator.service.camel.impl;

import camel.core.CamelModel;
import camel.deployment.RequirementSet;
import camel.deployment.SoftwareComponent;
import camel.deployment.impl.DeploymentTypeModelImpl;
import camel.deployment.impl.ScriptConfigurationImpl;
import camel.location.LocationModel;
import camel.requirement.LocationRequirement;
import camel.requirement.OSRequirement;
import camel.requirement.ResourceRequirement;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.cache.exception.CacheException;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpFactory;
import eu.paasage.upperware.profiler.generator.communication.CloudiatorServiceX;
import eu.paasage.upperware.profiler.generator.error.GeneratorException;
import eu.paasage.upperware.profiler.generator.service.camel.*;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.model.NodeCandidate;
import io.github.cloudiator.rest.model.NodeRequirements;
import io.github.cloudiator.rest.model.Requirement;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static eu.passage.upperware.commons.MelodicConstants.CDO_SERVER_PATH;

@Slf4j
@Service
public class NewConstraintProblemServiceXImpl implements NewConstraintProblemServiceX {


    private CpFactory cpFactory;
    private List<GeneratorService> generatorServices;
    private CloudiatorServiceX cloudiatorServiceX;
    private CacheService<NodeCandidates> memcacheService;
    private CacheService<NodeCandidates> filecacheService;
    private NodeCandidatesService nodeCandidatesService;
    private ConstantService constantService;
    private ConstraintService constraintService;
    private VariableService variableService;

    @Autowired
    public NewConstraintProblemServiceXImpl(CpFactory cpFactory, List<GeneratorService> generatorServices,
                                           CloudiatorServiceX cloudiatorServiceX, @Qualifier("memcacheService") CacheService<NodeCandidates> memcacheService,
                                           @Qualifier("filecacheService") CacheService<NodeCandidates> filecacheService, NodeCandidatesService nodeCandidatesService,
                                           ConstantService constantService, ConstraintService constraintService, VariableService variableService) {
        this.cpFactory = cpFactory;
        this.generatorServices = generatorServices;
        this.cloudiatorServiceX = cloudiatorServiceX;
        this.memcacheService = memcacheService;
        this.filecacheService = filecacheService;
        this.nodeCandidatesService = nodeCandidatesService;
        this.constantService = constantService;
        this.constraintService = constraintService;
        this.variableService = variableService;
    }

    @Override
    public ConstraintProblem createConstraintProblem(CamelModel camelModel, String cpName) {
        resetServices();

        //CP creation
        ConstraintProblem cp = cpFactory.createConstraintProblem();
        cp.setId(cpName);

        //adding default value for 0 constant
        cp.getConstants().add(constantService.createIntegerConstant(0, String.valueOf(0)));
        cp.getConstants().add(constantService.createIntegerConstant(1, String.valueOf(1)));

        Map<String, Map<Integer, List<NodeCandidate>>> nodeCandidatesMap =  loadProviders(camelModel);
        try {
            memcacheService.store(cpName, NodeCandidates.of(nodeCandidatesMap));
            String nodeCandidatesFilePath = "/logs/node_candidates_"+ CDO_SERVER_PATH + cp.getId();
            filecacheService.store(nodeCandidatesFilePath, NodeCandidates.of(nodeCandidatesMap));

            log.info("Node candidates stored under key {}", cpName);
            log.info("Node candidates saved in file {}", nodeCandidatesFilePath);

        } catch (CacheException cacheException) {
            throw new GeneratorException(String.format("Problem with storing data to cache under key %s", cpName), cacheException);
        }
        return null;
    }


    private Map<String, Map<Integer, List<NodeCandidate>>> loadProviders(CamelModel camelModel) {
        Map<String, Map<Integer, List<NodeCandidate>>> result = new HashMap<>();

        DeploymentTypeModelImpl deploymentTypeModel = (DeploymentTypeModelImpl) camelModel.getDeploymentModels().get(0);

        for (SoftwareComponent softwareComponent : deploymentTypeModel.getSoftwareComponents()) {

            String imageId = ((ScriptConfigurationImpl) softwareComponent.getConfigurations().get(0)).getImageId();

            NodeRequirements nodeRequirements = cloudiatorServiceX.createNodeRequirements(deploymentTypeModel.getGlobalRequirementSet(), softwareComponent.getRequirementSet(), camelModel.getLocationModels(), imageId);

            log.warn("Requirements: {}", nodeRequirements);

            List<NodeCandidate> nodeCandidates = null;
            try {
                nodeCandidates = cloudiatorServiceX.findNodeCandidates(nodeRequirements);
            } catch (ApiException e) {
                log.error("Error", e);
            }

            System.out.println("Node Candidates: " + nodeCandidates);

        }


//        List<InternalComponent> internalComponents = NewCamelModelTools.getLastDeploymentModel(camelModel).getInternalComponents();
//
//        List<Hosting> hostings = NewCamelModelTools.getHostings(camelModel);

//        for (VM vm : NewCamelModelTools.getVMs(camelModel)) {
//            List<NodeCandidate> nodeCandidates = getNodeCandidates(vm);
//            Map<String, List<NodeCandidate>> stringListMap = nodeCandidatesService.groupByProviders(nodeCandidates);
//
//            Map<Integer, List<NodeCandidate>> tempMap = new HashMap<>();
//
//            int i=0;
//            for (String s : stringListMap.keySet()) {
//                tempMap.put(i, stringListMap.get(s));
//                i++;
//            }
//            result.put(getComponentNameForVm(internalComponents, hostings, vm), tempMap);
//        }
        return result;
    }

//    private List<NodeCandidate> getNodeCandidates(VM vm) {
//        NodeRequirements nodeRequirements = cloudiatorServiceX.createNodeRequirements(vm);
//        log.info("NodeRequirements: {}", nodeRequirements);
//
//        List<NodeCandidate> nodeCandidates;
//        try {
//            nodeCandidates = cloudiatorServiceX.findNodeCandidates(nodeRequirements);
//        } catch (ApiException e) {
//            log.error("Error during fetching node candidates. Code: {}, ResponseBody: {}", e.getCode(), e.getResponseBody());
//            log.error("ApiException: ", e);
//
//            Map<String, List<String>> responseHeaders = MapUtils.emptyIfNull(e.getResponseHeaders());
//            for (String key : responseHeaders.keySet()) {
//                log.error("ResponseHeader: Key: {}, Value: {}", key, responseHeaders.get(key));
//            }
//            throw new GeneratorException("Problem during fetching node candidates", e);
//        }
//        if (CollectionUtils.isEmpty(nodeCandidates)){
//            throw new GeneratorException(String.format("Problem during fetching node candidates - empty result for query %s", toJson(nodeRequirements.getRequirements())));
//        }
//        return nodeCandidates;
//    }

    private String toJson(List<Requirement> requirements) {
        return new Gson().toJson(requirements, new TypeToken<List<Requirement>>() {}.getType());
    }

    private void resetServices() {
        for (GeneratorService generatorService : generatorServices) {
            generatorService.reset();
            log.debug("Reseting service {}", generatorService.getClass().getName());
        }
    }

}
