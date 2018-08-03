package eu.paasage.upperware.profiler.generator.service.camel.impl;

import camel.core.CamelModel;
import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
//import eu.paasage.camel.deployment.Hosting;
//import eu.paasage.camel.deployment.InternalComponent;
//import eu.paasage.camel.deployment.ProvidedHost;
//import eu.paasage.camel.deployment.VM;
//import eu.paasage.camel.metric.Metric;
//import eu.paasage.camel.metric.MetricInstance;
//import eu.paasage.camel.metric.MetricModel;
//import eu.paasage.camel.requirement.HorizontalScaleRequirement;
//import eu.paasage.camel.requirement.OSOrImageRequirement;
//import eu.paasage.camel.requirement.QuantitativeHardwareRequirement;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.profiler.generator.service.camel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class NewConstraintProblemServiceImpl implements NewConstraintProblemService {

    private CpFactory cpFactory;
    private List<GeneratorService> generatorServices;
    private CacheService<NodeCandidates> memcacheService;
    private CacheService<NodeCandidates> filecacheService;
    private NodeCandidatesService nodeCandidatesService;
    private ConstantService constantService;
    private ConstraintService constraintService;
    private VariableService variableService;

    @Autowired
    public NewConstraintProblemServiceImpl(CpFactory cpFactory, List<GeneratorService> generatorServices,
            @Qualifier("memcacheService") CacheService<NodeCandidates> memcacheService,
            @Qualifier("filecacheService") CacheService<NodeCandidates> filecacheService, NodeCandidatesService nodeCandidatesService,
            ConstantService constantService, ConstraintService constraintService, VariableService variableService) {
        this.cpFactory = cpFactory;
        this.generatorServices = generatorServices;
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

        return cp;
    }
//        resetServices();
//
//        //CP creation
//        ConstraintProblem cp = cpFactory.createConstraintProblem();
//        cp.setId(cpName);
//
//        //adding default value for 0 constant
//        cp.getConstants().add(constantService.createIntegerConstant(0, String.valueOf(0)));
//        cp.getConstants().add(constantService.createIntegerConstant(1, String.valueOf(1)));
//
//        Map<String, Map<Integer, List<NodeCandidate>>> nodeCandidatesMap =  loadProviders(camelModel);
//        try {
//            memcacheService.store(cpName, NodeCandidates.of(nodeCandidatesMap));
//            String nodeCandidatesFilePath = "/logs/node_candidates_"+ CDO_SERVER_PATH + cp.getId();
//            filecacheService.store(nodeCandidatesFilePath, NodeCandidates.of(nodeCandidatesMap));
//
//            log.info("Node candidates stored under key {}", cpName);
//            log.info("Node candidates saved in file {}", nodeCandidatesFilePath);
//
//        } catch (CacheException cacheException) {
//            throw new GeneratorException(String.format("Problem with storing data to cache under key %s", cpName), cacheException);
//        }
//
//        List<Hosting> hostings = NewCamelModelTools.getHostings(camelModel);
//        List<InternalComponent> internalComponents = NewCamelModelTools.getLastDeploymentModel(camelModel).getInternalComponents();
//
//
//        for (VM vm : NewCamelModelTools.getVMs(camelModel)) {
//            String vmName = vm.getName();
//
//            String componentName = getComponentNameForVm(internalComponents, hostings, vm);
//
//            //split by provider id
//            Map<Integer, List<NodeCandidate>> nodeCandidatesByComponentName = nodeCandidatesMap.get(componentName);
//            QuantitativeHardwareRequirement hardwareRequirements = NewCamelModelTools.getHardwareRequirements(vm);
//
//            //required variables
//            List<Integer> valuesForProviders = nodeCandidatesService.getValuesForProviders(nodeCandidatesByComponentName);
//            Variable providerVariable = createIntegerCpVariable(cp, VariableType.PROVIDER, componentName, vmName, valuesForProviders);
//
//            HorizontalScaleRequirement scaleRequirementForComponent = NewCamelModelTools.getScaleRequirementForComponent(camelModel.getRequirementModels().get(0).getRequirements(), componentName);
//
//            int minValue = 1;
//            int maxValue = 100;
//            if (scaleRequirementForComponent != null) {
//                minValue = scaleRequirementForComponent.getMinInstances();
//                maxValue = scaleRequirementForComponent.getMaxInstances();
//            }
//
//            Variable cardinalityVariable = createIntegerCpVariable(cp, VariableType.CARDINALITY, componentName, vmName, minValue, maxValue, variableService.createIntegerRangeDomain(minValue, maxValue));
//
//            //optional variables
//            Variable coresVariable = null;
//            if (shouldAddCores(hardwareRequirements)){
//                List<Integer> valuesForCores = nodeCandidatesService.getValuesForCores(nodeCandidatesByComponentName);
//                coresVariable = createIntegerCpVariable(cp, VariableType.CORES, componentName, vmName, valuesForCores);
//            }
//
//            Variable ramVariable = null;
//            if (shouldAddRam(hardwareRequirements)){
//                List<Long> valuesForRam = nodeCandidatesService.getValuesForRam(nodeCandidatesByComponentName);
//                ramVariable = createLongVariable(cp, VariableType.RAM, componentName, vmName, valuesForRam);
//            }
//
//            Variable storageVariable = null;
//            if (shouldAddStorage(hardwareRequirements)){
//                List<Double> valuesForStorage = nodeCandidatesService.getValuesForStorage(nodeCandidatesByComponentName);
//                storageVariable = createDoubleCpVariable(cp, VariableType.STORAGE, componentName, vmName, valuesForStorage);
//            }
//
//            OSOrImageRequirement osOrImageRequirements = NewCamelModelTools.getOsOrImageRequirements(vm);
//
//            Variable osVariable = null;
//            if (shouldAddOs(osOrImageRequirements)){
//                List<Integer> valuesForOs = nodeCandidatesService.getValuesForOsFamily(nodeCandidatesByComponentName);
//                osVariable = createIntegerCpVariable(cp, VariableType.OS, componentName, vmName, valuesForOs);
//            }
//
//
//            //F(P,x)
//            Map<Integer, Supplier<ComposedExpression>> providerFunctions = new HashMap<>();
//
//            for (Integer providerIndex: nodeCandidatesByComponentName.keySet()) {
//                //F(P,1)
//                List<NodeCandidate> nodeCandidatesForProvider = nodeCandidatesByComponentName.get(providerIndex);
//                Supplier<ComposedExpression> providerFunctionSupplier = providerFunctions.computeIfAbsent(providerIndex, index -> getProviderExpression(cp, providerVariable, componentName, index));
//
//                if (coresVariable != null) {
//                    Pair<Integer, Integer> rangeForCores = nodeCandidatesService.getRangeForCores(nodeCandidatesForProvider);
//                    Constant min = constantService.createIntegerConstant(rangeForCores.getLeft(), constantService.getConstantName(VariableType.CORES, componentName, "min", "p", String.valueOf(providerIndex)));
//                    cp.getConstants().add(min);
//
//                    Constant max = constantService.createIntegerConstant(rangeForCores.getRight(), constantService.getConstantName(VariableType.CORES, componentName, "max", "p", String.valueOf(providerIndex)));
//                    cp.getConstants().add(max);
//
//                    createConstraints(cp, coresVariable, cardinalityVariable, min, max, providerFunctionSupplier);
//                }
//
//                if (ramVariable != null) {
//                    Pair<Long, Long> rangeForRam = nodeCandidatesService.getRangeForRam(nodeCandidatesForProvider);
//                    Constant min = constantService.createLongConstant(rangeForRam.getLeft(), constantService.getConstantName(VariableType.RAM, componentName, "min", "p", String.valueOf(providerIndex)));
//                    cp.getConstants().add(min);
//
//                    Constant max = constantService.createLongConstant(rangeForRam.getRight(), constantService.getConstantName(VariableType.RAM, componentName, "max", "p", String.valueOf(providerIndex)));
//                    cp.getConstants().add(max);
//
//                    createConstraints(cp, ramVariable, cardinalityVariable, min, max, providerFunctionSupplier);
//                }
//
//                if (storageVariable != null) {
//                    Pair<Double, Double> rangeForStorage = nodeCandidatesService.getRangeForStorage(nodeCandidatesForProvider);
//                    Constant min = constantService.createDoubleConstant(rangeForStorage.getLeft(), constantService.getConstantName(VariableType.STORAGE, componentName, "min", "p", String.valueOf(providerIndex)));
//                    cp.getConstants().add(min);
//
//                    Constant max = constantService.createDoubleConstant(rangeForStorage.getRight(), constantService.getConstantName(VariableType.STORAGE, componentName, "max", "p", String.valueOf(providerIndex)));
//                    cp.getConstants().add(max);
//
//                    createConstraints(cp, storageVariable, cardinalityVariable, min, max, providerFunctionSupplier);
//                }
//
//                if (osVariable != null) {
//                    Pair<Integer, Integer> rangeForOs = nodeCandidatesService.getRangeForOs(nodeCandidatesForProvider);
//                    Constant min = constantService.createIntegerConstant(rangeForOs.getLeft(), constantService.getConstantName(VariableType.OS, componentName, "min", "p", String.valueOf(providerIndex)));
//                    cp.getConstants().add(min);
//
//                    Constant max = constantService.createIntegerConstant(rangeForOs.getRight(), constantService.getConstantName(VariableType.OS, componentName, "max", "p", String.valueOf(providerIndex)));
//                    cp.getConstants().add(max);
//
//                    createConstraints(cp, osVariable, cardinalityVariable, min, max, providerFunctionSupplier);
//                }
//            }
//        }
//
//        cp.getConstants().addAll(createConstantsFromMetrics(camelModel));
//
//        CPModelTool.printCpModel(cp);
//
//        return cp;
//    }
//
//    private List<Constant> createConstantsFromMetrics(CamelModel camelModel){
//        List<Constant> result = new ArrayList<>();
//        EList<MetricModel> metricModels = camelModel.getMetricModels();
//
//        if (CollectionUtils.isNotEmpty(metricModels)){
//            MetricModel metricModel = metricModels.get(0);
//
//            Stream.concat(
//                    metricModel.getMetrics().stream().map(Metric::getName),
//                    metricModel.getMetricInstances().stream().map(MetricInstance::getName))
//                    .forEach(name -> result.add(constantService.createIntegerConstant(0, createConstantNameForMetric(name))));
//        }
//        return result;
//    }
//
//    private String createConstantNameForMetric(String name){
//        return "METRIC_" + name;
//    }
//
//    private String getComponentNameForVm(List<InternalComponent> internalComponents, List<Hosting> hostings, VM vm) {
//        Hosting hosting = getFirstMatchingHosting(vm.getProvidedHosts(), hostings)
//                .orElseThrow(() -> new GeneratorException(String.format("Hosting for vm %s is null", vm.getName())));
//
//        InternalComponent internalComponent = CollectionUtils.emptyIfNull(internalComponents)
//               .stream()
//               .filter(ic -> ic.getRequiredHost().getName().equals(hosting.getRequiredHost().getName()))
//               .findFirst()
//               .orElseThrow(() -> new GeneratorException(String.format("InternalComponent for hosting %s is null", hosting.getName())));
//
//        return internalComponent.getName();
//    }
//
//    private Optional<Hosting> getFirstMatchingHosting(EList<ProvidedHost> providedHosts, List<Hosting> hostings) {
//        for (ProvidedHost providedHost : providedHosts) {
//            String name = providedHost.getName();
//            for (Hosting hosting : hostings) {
//                if (hosting.getProvidedHost().getName().equals(name)){
//                    return Optional.of(hosting);
//                }
//            }
//        }
//        return Optional.empty();
//    }
//
//    private void createConstraints(ConstraintProblem cp, Variable variable, Variable cardinalityVariable, Constant min, Constant max, Supplier<ComposedExpression> composedExpressionSupplier) {
//
//        Constant zeroConstant = findConstantByName(cp.getConstants(), "0");
//
//        ComposedExpression composedMinExpression = constraintService.createComposedExpression(OperatorEnum.MINUS, variable, min);
//        cp.getAuxExpressions().add(composedMinExpression);
//
//        ComposedExpression multiplyMinComposedExpression = constraintService.createComposedExpression(OperatorEnum.TIMES, cardinalityVariable, composedExpressionSupplier.get(), composedMinExpression);
//        cp.getAuxExpressions().add(multiplyMinComposedExpression);
//
//        cp.getConstraints().add(constraintService.createComparisonExpression(multiplyMinComposedExpression, ComparatorEnum.GREATER_OR_EQUAL_TO, zeroConstant));
//
//
//        ComposedExpression composedMaxExpression = constraintService.createComposedExpression(OperatorEnum.MINUS, max, variable);
//        cp.getAuxExpressions().add(composedMaxExpression);
//
//        ComposedExpression multiplyMaxComposedExpression = constraintService.createComposedExpression(OperatorEnum.TIMES, cardinalityVariable, composedExpressionSupplier.get(), composedMaxExpression);
//        cp.getAuxExpressions().add(multiplyMaxComposedExpression);
//
//        cp.getConstraints().add(constraintService.createComparisonExpression(multiplyMaxComposedExpression, ComparatorEnum.GREATER_OR_EQUAL_TO, zeroConstant));
//    }
//
//    private Variable createIntegerCpVariable(ConstraintProblem cp, VariableType variableType, String componentId, String vmName, List<Integer> values) {
//        if (CollectionUtils.isEmpty(values)){
//            log.warn("Empty set of variable type: {} for: {}", variableType, componentId);
//            throw new GeneratorException(String.format("Empty set of variable type: %s for: %s", variableType, componentId));
//        }
//
//        int minPossibleValue = values.get(0);
//        int maxPossibleValue = values.get(values.size()-1);
//
//        return createIntegerCpVariable(cp, variableType, componentId, vmName, minPossibleValue, maxPossibleValue, variableService.createIntegerListDomain(values));
//    }
//
//    private Variable createIntegerCpVariable(ConstraintProblem cp, VariableType variableType, String componentId, String vmName, int minPossibleValue, int maxPossibleValue, Domain domain) {
//
//        Variable variable = variableService.createIntegerCpVariable(variableType, componentId, vmName, domain);
//        cp.getVariables().add(variable);
//
//        Constant minConstant = constantService.createIntegerConstant(minPossibleValue, constantService.getConstantName(variableType, componentId, "min"));
//        cp.getConstants().add(minConstant);
//
//        ComparisonExpression minCompariton = constraintService.createComparisonExpression(variable, ComparatorEnum.GREATER_OR_EQUAL_TO, minConstant);
//        cp.getConstraints().add(minCompariton);
//
//        Constant maxConstant = constantService.createIntegerConstant(maxPossibleValue, constantService.getConstantName(variableType, componentId, "max"));
//        cp.getConstants().add(maxConstant);
//
//        ComparisonExpression maxComparition = constraintService.createComparisonExpression(variable, ComparatorEnum.LESS_OR_EQUAL_TO, maxConstant);
//        cp.getConstraints().add(maxComparition);
//
//        return variable;
//    }
//
//    private Variable createLongVariable(ConstraintProblem cp, VariableType variableType, String componentId, String vmName, List<Long> values) {
//        if (CollectionUtils.isEmpty(values)){
//            log.warn("Empty set of variable type: {} for: {}", variableType, componentId);
//            throw new GeneratorException(String.format("Empty set of variable type: %s for: %s", variableType, componentId));
//        }
//
//        long minPossibleValue = values.get(0);
//        long maxPossibleValue = values.get(values.size()-1);
//
//        Variable variable = variableService.createLongVariable(variableType, componentId, vmName, variableService.createLongListDomain(values));
//        cp.getVariables().add(variable);
//
//        Constant minConstant = constantService.createLongConstant(minPossibleValue, constantService.getConstantName(variableType, componentId, "min"));
//        cp.getConstants().add(minConstant);
//
//        ComparisonExpression minCompariton = constraintService.createComparisonExpression(variable, ComparatorEnum.GREATER_OR_EQUAL_TO, minConstant);
//        cp.getConstraints().add(minCompariton);
//
//        Constant maxConstant = constantService.createLongConstant(maxPossibleValue, constantService.getConstantName(variableType, componentId, "max"));
//        cp.getConstants().add(maxConstant);
//
//        ComparisonExpression maxComparition = constraintService.createComparisonExpression(variable, ComparatorEnum.LESS_OR_EQUAL_TO, maxConstant);
//        cp.getConstraints().add(maxComparition);
//
//        return variable;
//    }
//
//    private Variable createFloatCpVariable(ConstraintProblem cp, VariableType variableType, String componentId, String vmName, List<Float> values) {
//        if (CollectionUtils.isEmpty(values)){
//            log.warn("Empty set of variable type: {} for: {}", variableType, componentId);
//            throw new GeneratorException(String.format("Empty set of variable type: %s for: %s", variableType, componentId));
//        }
//
//        float minPossibleValue = values.get(0);
//        float maxPossibleValue = values.get(values.size()-1);
//
//        Variable variable = variableService.createFloatCpVariable(variableType, componentId, vmName, variableService.createFloatListDomain(values));
//        cp.getVariables().add(variable);
//
//        Constant minConstant = constantService.createFloatConstant(minPossibleValue, constantService.getConstantName(variableType, componentId, "min"));
//        cp.getConstants().add(minConstant);
//
//        ComparisonExpression minCompariton = constraintService.createComparisonExpression(variable, ComparatorEnum.GREATER_OR_EQUAL_TO, minConstant);
//        cp.getConstraints().add(minCompariton);
//
//        Constant maxConstant = constantService.createFloatConstant(maxPossibleValue, constantService.getConstantName(variableType, componentId, "max"));
//        cp.getConstants().add(maxConstant);
//
//        ComparisonExpression maxComparition = constraintService.createComparisonExpression(variable, ComparatorEnum.LESS_OR_EQUAL_TO, maxConstant);
//        cp.getConstraints().add(maxComparition);
//
//        return variable;
//    }
//
//
//    private Variable createDoubleCpVariable(ConstraintProblem cp, VariableType variableType, String componentId, String vmName, List<Double> values) {
//        if (CollectionUtils.isEmpty(values)){
//            log.warn("Empty set of variable type: {} for: {}", variableType, componentId);
//            throw new GeneratorException(String.format("Empty set of variable type: %s for: %s", variableType, componentId));
//        }
//
//        double minPossibleValue = values.get(0);
//        double maxPossibleValue = values.get(values.size()-1);
//
//        Variable variable = variableService.createDoubleCpVariable(variableType, componentId, vmName, variableService.createDoubleListDomain(values));
//        cp.getVariables().add(variable);
//
//        Constant minConstant = constantService.createDoubleConstant(minPossibleValue, constantService.getConstantName(variableType, componentId, "min"));
//        cp.getConstants().add(minConstant);
//
//        ComparisonExpression minCompariton = constraintService.createComparisonExpression(variable, ComparatorEnum.GREATER_OR_EQUAL_TO, minConstant);
//        cp.getConstraints().add(minCompariton);
//
//        Constant maxConstant = constantService.createDoubleConstant(maxPossibleValue, constantService.getConstantName(variableType, componentId, "max"));
//        cp.getConstants().add(maxConstant);
//
//        ComparisonExpression maxComparition = constraintService.createComparisonExpression(variable, ComparatorEnum.LESS_OR_EQUAL_TO, maxConstant);
//        cp.getConstraints().add(maxComparition);
//
//        return variable;
//    }
//
//
//    private Supplier<ComposedExpression> getProviderExpression(ConstraintProblem cp, Variable providerVariable, String componentName, Integer providerValue) {
//        Constant providerIndexConstant = constantService.createIntegerConstant(providerValue, "provider_" + componentName  + "_" + providerValue);
//        cp.getConstants().add(providerIndexConstant);
//        return createProviderFunction(cp, providerVariable, providerIndexConstant);
//    }
//
//    private Map<String, Map<Integer, List<NodeCandidate>>> loadProviders(CamelModel camelModel) {
//        Map<String, Map<Integer, List<NodeCandidate>>> result = new HashMap<>();
//
//        List<InternalComponent> internalComponents = NewCamelModelTools.getLastDeploymentModel(camelModel).getInternalComponents();
//
//        List<Hosting> hostings = NewCamelModelTools.getHostings(camelModel);
//
//            for (VM vm : NewCamelModelTools.getVMs(camelModel)) {
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
//        return result;
//    }
//
//    private Constant findConstantByName(EList<Constant> constants, String name) {
//        return constants.stream()
//                .filter(constant -> name.equals(constant.getId()))
//                .findFirst()
//                .orElseThrow(() -> new GeneratorException(String.format("Could not find constant with name %s", name)));
//    }
//
//    /**
//     * This method should return F(P,x) function
//     * @param providerVariable
//     * @param providerConstant
//     * @return
//     */
//    private Supplier<ComposedExpression> createProviderFunction(ConstraintProblem cp, Variable providerVariable, Constant providerConstant){
//        return new SingletonSupplier<>(() -> {
//            ComposedExpression composedExpression = constraintService.createComposedExpression(OperatorEnum.EQ, providerVariable, providerConstant);
//            cp.getAuxExpressions().add(composedExpression);
//            return composedExpression;
//        });
//    }
//
//    private List<NodeCandidate> getNodeCandidates(VM vm) {
//        NodeRequirements nodeRequirements = cloudiatorService.createNodeRequirements(vm);
//        log.info("NodeRequirements: {}", nodeRequirements);
//
//        List<NodeCandidate> nodeCandidates;
//        try {
//            nodeCandidates = cloudiatorService.findNodeCandidates(nodeRequirements);
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
//
//    private boolean shouldAddCores(QuantitativeHardwareRequirement quantitativeHardwareRequirement){
//        return quantitativeHardwareRequirement != null && quantitativeHardwareRequirement.getMinCores() > 0 && quantitativeHardwareRequirement.getMaxCores() > 0;
//    }
//
//    private boolean shouldAddRam(QuantitativeHardwareRequirement quantitativeHardwareRequirement){
//        return quantitativeHardwareRequirement != null && quantitativeHardwareRequirement.getMinRAM()> 0 && quantitativeHardwareRequirement.getMaxRAM() > 0;
//    }
//
//    private boolean shouldAddStorage(QuantitativeHardwareRequirement quantitativeHardwareRequirement){
//        return quantitativeHardwareRequirement != null && quantitativeHardwareRequirement.getMinStorage()> 0 && quantitativeHardwareRequirement.getMaxStorage() > 0;
//    }
//
//    private boolean shouldAddOs(OSOrImageRequirement osOrImageRequirement){
//        return osOrImageRequirement != null && StringUtils.isNotBlank(osOrImageRequirement.getName());
//    }
//
//    private String toJson(List<Requirement> requirements) {
//        return new Gson().toJson(requirements, new TypeToken<List<Requirement>>() {}.getType());
//    }
//
    private void resetServices() {
        for (GeneratorService generatorService : generatorServices) {
            generatorService.reset();
            log.debug("Reseting service {}", generatorService.getClass().getName());
        }
    }
//
//    private static class SingletonSupplier<T> implements Supplier<T> {
//
//        private Supplier<T> supplier;
//        private T instance;
//
//        private SingletonSupplier(Supplier<T> supplier) {
//            this.supplier = supplier;
//        }
//
//        @Override
//        public T get() {
//            if (instance == null) {
//                instance = supplier.get();
//            }
//            return instance;
//        }
//    }

}
