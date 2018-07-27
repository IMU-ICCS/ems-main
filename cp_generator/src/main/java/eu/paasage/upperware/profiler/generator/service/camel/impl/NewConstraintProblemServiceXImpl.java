package eu.paasage.upperware.profiler.generator.service.camel.impl;

import camel.constraint.ComparisonOperatorType;
import camel.constraint.Constraint;
import camel.constraint.impl.MetricConstraintImpl;
import camel.constraint.impl.MetricVariableConstraintImpl;
import camel.core.CamelModel;
import camel.deployment.RequirementSet;
import camel.deployment.SoftwareComponent;
import camel.deployment.impl.DeploymentTypeModelImpl;
import camel.deployment.impl.ScriptConfigurationImpl;
import camel.location.LocationModel;
import camel.metric.*;
import camel.metric.impl.MetricImpl;
import camel.metric.impl.MetricTypeModelImpl;
import camel.metric.impl.MetricVariableImpl;
import camel.mms.MmsObject;
import camel.requirement.HorizontalScaleRequirement;
import camel.requirement.RequirementModel;
import camel.requirement.ServiceLevelObjective;
import camel.unit.Unit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.cache.exception.CacheException;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static eu.passage.upperware.commons.MelodicConstants.CDO_SERVER_PATH;

@Slf4j
@Service
public class NewConstraintProblemServiceXImpl implements NewConstraintProblemServiceX {

    //TODO - remove this??
    private static final List<String> META_MODEL_VARIABLES = Arrays.asList("m_cpu", "m_cores", "m_ram", "m_storage", "m_cardinality",
            "m_utility", "m_price");

    private static final List<String> META_DATA_UNIT_TYPES = Arrays.asList("m_int", "m_double");

    private CpFactory cpFactory;
    private List<GeneratorService> generatorServices;
    private CloudiatorServiceX cloudiatorServiceX;
    private CacheService<NodeCandidates> memcacheService;
    private CacheService<NodeCandidates> filecacheService;
    private NodeCandidatesService nodeCandidatesService;
    private ConstantService constantService;
    private ConstraintService constraintService;
    private VariableService variableService;
    private MetricService metricService;

    @Autowired
    public NewConstraintProblemServiceXImpl(CpFactory cpFactory, List<GeneratorService> generatorServices,
                                           CloudiatorServiceX cloudiatorServiceX, @Qualifier("memcacheService") CacheService<NodeCandidates> memcacheService,
                                           @Qualifier("filecacheService") CacheService<NodeCandidates> filecacheService, NodeCandidatesService nodeCandidatesService,
                                           ConstantService constantService, ConstraintService constraintService, VariableService variableService, MetricService metricService) {
        this.cpFactory = cpFactory;
        this.generatorServices = generatorServices;
        this.cloudiatorServiceX = cloudiatorServiceX;
        this.memcacheService = memcacheService;
        this.filecacheService = filecacheService;
        this.nodeCandidatesService = nodeCandidatesService;
        this.constantService = constantService;
        this.constraintService = constraintService;
        this.variableService = variableService;
        this.metricService = metricService;
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

        for (SoftwareComponent softwareComponent : getDeploymentModel(camelModel).getSoftwareComponents()) {
            String componentName = softwareComponent.getName();
            Map<Integer, List<NodeCandidate>> nodeCandidatesByComponentName = nodeCandidatesMap.get(componentName);


            HorizontalScaleRequirement horizontalScaleRequirement = softwareComponent.getRequirementSet().getHorizontalScaleRequirement();

            int minValue = 1;
            int maxValue = 100;
            if (horizontalScaleRequirement != null) {
                minValue = horizontalScaleRequirement.getMinInstances();
                maxValue = horizontalScaleRequirement.getMaxInstances();
            }

            //TODO
            List<MetricVariableImpl> variables = getVariables(camelModel, componentName);

            Optional<MetricVariableImpl> mCardinality = findVariableFor(variables, "m_cardinality");
            Variable cardinalityVariable = null;

            if (mCardinality.isPresent()) {
                MetricVariableImpl cardinalityMetricVariable = mCardinality.get();
                String variableCardinalityName = cardinalityMetricVariable.getName();
                String type = getType(cardinalityMetricVariable);
                if ("m_int".equals(type)){
                    cardinalityVariable = createIntegerVariable(variableCardinalityName, cp, VariableType.CARDINALITY, componentName, minValue, maxValue, variableService.createIntegerRangeDomain(minValue, maxValue));
                } else if ("m_double".equals(type)){
                    throw new GeneratorException("Cardinality should be variable of type int");
                }
            } else {
                cardinalityVariable = createIntegerVariable(null, cp, VariableType.CARDINALITY, componentName, minValue, maxValue, variableService.createIntegerRangeDomain(minValue, maxValue));
            }

            //required variables
            List<Integer> valuesForProviders = nodeCandidatesService.getValuesForProviders(nodeCandidatesByComponentName);
            Variable providerVariable = createIntegerVariable("", cp, VariableType.PROVIDER, componentName, valuesForProviders.get(0), valuesForProviders.get(valuesForProviders.size() -1), variableService.createIntegerListDomain(valuesForProviders));


            //optional variables
            Variable coresVariable = null;
            Optional<MetricVariableImpl> optCores = findVariableFor(variables, "m_cores");
            if (optCores.isPresent()){
                MetricVariableImpl metricVariable = optCores.get();
                List<Integer> valuesForCores = nodeCandidatesService.getValuesForCores(nodeCandidatesByComponentName);
                coresVariable = createIntegerVariable(metricVariable.getName(), cp, VariableType.CORES, componentName, valuesForCores);
            }

            Variable ramVariable = null;
            Optional<MetricVariableImpl> optRam = findVariableFor(variables, "m_ram");
            if (optRam.isPresent()){
                List<Long> valuesForRam = nodeCandidatesService.getValuesForRam(nodeCandidatesByComponentName);
                List<Integer> integers = mapLongToInteger(valuesForRam);
                ramVariable = createIntegerVariable(cp, VariableType.RAM, componentName, integers);
            }

            Variable storageVariable = null;
            Optional<MetricVariableImpl> optStorage = findVariableFor(variables, "m_storage");
            if (optStorage.isPresent()){
                List<Double> valuesForStorage = nodeCandidatesService.getValuesForStorage(nodeCandidatesByComponentName);
                List<Integer> integers = mapDoubleToInteger(valuesForStorage);
                storageVariable = createIntegerVariable(cp, VariableType.STORAGE, componentName, integers);
            }

            //F(P,x)
            Map<Integer, Supplier<ComposedExpression>> providerFunctions = new HashMap<>();

            for (Integer providerIndex: nodeCandidatesByComponentName.keySet()) {
                //F(P,1)
                List<NodeCandidate> nodeCandidatesForProvider = nodeCandidatesByComponentName.get(providerIndex);
                Supplier<ComposedExpression> providerFunctionSupplier = providerFunctions.computeIfAbsent(providerIndex, index -> getProviderExpression(cp, providerVariable, componentName, index));

                if (coresVariable != null) {
                    Pair<Integer, Integer> rangeForCores = nodeCandidatesService.getRangeForCores(nodeCandidatesForProvider);
                    Constant min = constantService.createIntegerConstant(rangeForCores.getLeft(), constantService.getConstantName(VariableType.CORES, componentName, "min", "p", String.valueOf(providerIndex)));
                    cp.getConstants().add(min);

                    Constant max = constantService.createIntegerConstant(rangeForCores.getRight(), constantService.getConstantName(VariableType.CORES, componentName, "max", "p", String.valueOf(providerIndex)));
                    cp.getConstants().add(max);

                    createConstraints(cp, coresVariable, cardinalityVariable, min, max, providerFunctionSupplier);
                }

                if (ramVariable != null) {
                    Pair<Long, Long> rangeForRam = nodeCandidatesService.getRangeForRam(nodeCandidatesForProvider);
                    Constant min = constantService.createLongConstant(rangeForRam.getLeft(), constantService.getConstantName(VariableType.RAM, componentName, "min", "p", String.valueOf(providerIndex)));
                    cp.getConstants().add(min);

                    Constant max = constantService.createLongConstant(rangeForRam.getRight(), constantService.getConstantName(VariableType.RAM, componentName, "max", "p", String.valueOf(providerIndex)));
                    cp.getConstants().add(max);

                    createConstraints(cp, ramVariable, cardinalityVariable, min, max, providerFunctionSupplier);
                }

                if (storageVariable != null) {
                    Pair<Double, Double> rangeForStorage = nodeCandidatesService.getRangeForStorage(nodeCandidatesForProvider);
                    Constant min = constantService.createDoubleConstant(rangeForStorage.getLeft(), constantService.getConstantName(VariableType.STORAGE, componentName, "min", "p", String.valueOf(providerIndex)));
                    cp.getConstants().add(min);

                    Constant max = constantService.createDoubleConstant(rangeForStorage.getRight(), constantService.getConstantName(VariableType.STORAGE, componentName, "max", "p", String.valueOf(providerIndex)));
                    cp.getConstants().add(max);

                    createConstraints(cp, storageVariable, cardinalityVariable, min, max, providerFunctionSupplier);
                }
            }

            System.out.println("TEST");
        }

        addMetrics(cp, camelModel);

        addConstraint(cp, camelModel);



        return cp;
    }

    private void addConstraint(ConstraintProblem cp, CamelModel camelModel) {

        ServiceLevelObjective sloRequirement = getSLORequirement(camelModel);
        if (sloRequirement == null) {
            return;
        }

        Constraint constraint = sloRequirement.getConstraint();
        if (constraint != null) {
            if (constraint instanceof MetricConstraintImpl) {
                MetricConstraintImpl mc = (MetricConstraintImpl) constraint;
                MetricContext metricContext = mc.getMetricContext();

                Constant tresholdConstant = constantService.createDoubleConstant(mc.getThreshold());
                ComparatorEnum comparatorEnum = convertComparator(mc.getComparisonOperator());
                MetricVariable doubleMetricVariable = metricService.createDoubleMetricVariable(metricContext.getMetric().getName());
                cp.getConstraints().add(constraintService.createComparisonExpression(tresholdConstant, comparatorEnum, doubleMetricVariable, mc.getName()));
                cp.getConstants().add(tresholdConstant);
                cp.getMetricVariables().add(doubleMetricVariable);
            }

            if (constraint instanceof MetricVariableConstraintImpl) {
                MetricVariableConstraintImpl mvc = (MetricVariableConstraintImpl) constraint;
            }
        }
    }

    private ComparatorEnum convertComparator(ComparisonOperatorType comparisonOperator){
        if (comparisonOperator == null) {
            throw new GeneratorException("Could not find comparition operator for");
        }

        if (ComparisonOperatorType.GREATER_THAN.equals(comparisonOperator)) {
            return ComparatorEnum.GREATER_THAN;
        } else if (ComparisonOperatorType.GREATER_EQUAL_THAN.equals(comparisonOperator)) {
            return ComparatorEnum.GREATER_OR_EQUAL_TO;
        } else if (ComparisonOperatorType.LESS_THAN.equals(comparisonOperator)) {
            return ComparatorEnum.LESS_THAN;
        } else if (ComparisonOperatorType.LESS_EQUAL_THAN.equals(comparisonOperator)) {
            return ComparatorEnum.LESS_OR_EQUAL_TO;
        } else if (ComparisonOperatorType.EQUAL.equals(comparisonOperator)) {
            return ComparatorEnum.EQUAL_TO;
        } else if (ComparisonOperatorType.NOT_EQUAL.equals(comparisonOperator)) {
            return ComparatorEnum.DIFFERENT;
        }

        throw new GeneratorException("Unsupported comparator: " + comparisonOperator.getName());
    }


    private ServiceLevelObjective getSLORequirement(CamelModel camelModel) {
        for (RequirementModel requirementModel : CollectionUtils.emptyIfNull(camelModel.getRequirementModels())) {
            for (camel.requirement.Requirement requirement : CollectionUtils.emptyIfNull(requirementModel.getRequirements())) {
                if (requirement instanceof ServiceLevelObjective) {
                    return (ServiceLevelObjective) requirement;
                }
            }
        }
        return null;
    }

    private void addMetrics(ConstraintProblem cp, CamelModel camelModel) {
        List<Metric> metrics = getRawAndCompositeMetrics(camelModel);
        cp.getMetricVariables().addAll(metrics.stream().map(metric -> metricService.createDoubleMetricVariable(metric.getName())).collect(Collectors.toList()));

        List<MetricVariableImpl> variables = getVariables(camelModel);
        cp.getMetricVariables().addAll(variables
                .stream()
                .filter(MetricVariableImpl::isCurrentConfiguration)
                .map(variable -> metricService.createDoubleMetricVariable(variable.getName()))
                .collect(Collectors.toList()));
    }

    //TODO - remove in the future
    private List<Integer> mapDoubleToInteger(List<Double> from){
        return from.stream().map(Double::intValue).collect(Collectors.toList());
    }

    //TODO - remove in the future
    private List<Integer> mapLongToInteger(List<Long> from){
        return from.stream().map(Long::intValue).collect(Collectors.toList());
    }

    private Supplier<ComposedExpression> getProviderExpression(ConstraintProblem cp, Variable providerVariable, String componentName, Integer providerValue) {
        Constant providerIndexConstant = constantService.createIntegerConstant(providerValue, "provider_" + componentName  + "_" + providerValue);
        cp.getConstants().add(providerIndexConstant);
        return createProviderFunction(cp, providerVariable, providerIndexConstant);
    }

    /**
     * This method should return F(P,x) function
     * @param providerVariable
     * @param providerConstant
     * @return
     */
    private Supplier<ComposedExpression> createProviderFunction(ConstraintProblem cp, Variable providerVariable, Constant providerConstant){
        return new NewConstraintProblemServiceXImpl.SingletonSupplier<>(() -> {
            ComposedExpression composedExpression = constraintService.createComposedExpression(OperatorEnum.EQ, providerVariable, providerConstant);
            cp.getAuxExpressions().add(composedExpression);
            return composedExpression;
        });
    }

    private void createConstraints(ConstraintProblem cp, Variable variable, Variable cardinalityVariable, Constant min, Constant max, Supplier<ComposedExpression> composedExpressionSupplier) {

        Constant zeroConstant = findConstantByName(cp.getConstants(), "0");

        ComposedExpression composedMinExpression = constraintService.createComposedExpression(OperatorEnum.MINUS, variable, min);
        cp.getAuxExpressions().add(composedMinExpression);

        ComposedExpression multiplyMinComposedExpression = constraintService.createComposedExpression(OperatorEnum.TIMES, cardinalityVariable, composedExpressionSupplier.get(), composedMinExpression);
        cp.getAuxExpressions().add(multiplyMinComposedExpression);

        cp.getConstraints().add(constraintService.createComparisonExpression(multiplyMinComposedExpression, ComparatorEnum.GREATER_OR_EQUAL_TO, zeroConstant));


        ComposedExpression composedMaxExpression = constraintService.createComposedExpression(OperatorEnum.MINUS, max, variable);
        cp.getAuxExpressions().add(composedMaxExpression);

        ComposedExpression multiplyMaxComposedExpression = constraintService.createComposedExpression(OperatorEnum.TIMES, cardinalityVariable, composedExpressionSupplier.get(), composedMaxExpression);
        cp.getAuxExpressions().add(multiplyMaxComposedExpression);

        cp.getConstraints().add(constraintService.createComparisonExpression(multiplyMaxComposedExpression, ComparatorEnum.GREATER_OR_EQUAL_TO, zeroConstant));
    }

    private Constant findConstantByName(EList<Constant> constants, String name) {
        return constants.stream()
                .filter(constant -> name.equals(constant.getId()))
                .findFirst()
                .orElseThrow(() -> new GeneratorException(String.format("Could not find constant with name %s", name)));
    }

    private String getType(MetricVariableImpl metricVariable) {
        Unit unit = metricVariable.getMetricTemplate().getUnit();

        for (MmsObject mmsObject : unit.getAnnotations()) {
            String annotationName = mmsObject.getId();

            for (String metaDataUnitType : META_DATA_UNIT_TYPES) {
                if (metaDataUnitType.equals(annotationName)){
                    log.info("Searching unit for {}", annotationName);
                    return metaDataUnitType;
                }
            }
        }
        throw new GeneratorException("Could not find type");
    }

    private Optional<MetricVariableImpl> findVariableFor(List<MetricVariableImpl> variables, String annotationName) {

        for (MetricVariableImpl variable : variables) {
            boolean hasAnnotation = variable.getMetricTemplate().getAttribute().getAnnotations().stream().anyMatch(mmsObject -> annotationName.equals(mmsObject.getId()));

            if (hasAnnotation){
                return Optional.of(variable);
            }
        }
        return Optional.empty();
    }

    private Map<String, Map<Integer, List<NodeCandidate>>> loadProviders(CamelModel camelModel) {
        Map<String, Map<Integer, List<NodeCandidate>>> result = new HashMap<>();

        DeploymentTypeModelImpl deploymentTypeModel = getDeploymentModel(camelModel);

        for (SoftwareComponent softwareComponent : deploymentTypeModel.getSoftwareComponents()) {
            List<NodeCandidate> nodeCandidates = loadProviders(deploymentTypeModel.getGlobalRequirementSet(), softwareComponent.getRequirementSet(), camelModel.getLocationModels(), getImageId(softwareComponent));
            Map<String, List<NodeCandidate>> nodeCandidatesByProvider = nodeCandidatesService.groupByProviders(nodeCandidates);
            Map<Integer, List<NodeCandidate>> nodeCandidatesByProviderIndex = getAsIndexMap(nodeCandidatesByProvider);
            result.put(softwareComponent.getName(), nodeCandidatesByProviderIndex);
        }

        return result;
    }

    private DeploymentTypeModelImpl getDeploymentModel(CamelModel camelModel) {
        return (DeploymentTypeModelImpl) camelModel.getDeploymentModels().get(0);
    }

    private String getImageId(SoftwareComponent softwareComponent) {
        return ((ScriptConfigurationImpl) softwareComponent.getConfigurations().get(0)).getImageId();
    }

    private List<NodeCandidate> loadProviders(RequirementSet globalRequirementSet, RequirementSet localRequirementSet, List<LocationModel> locationModels, String imageId) {
        NodeRequirements nodeRequirements = cloudiatorServiceX.createNodeRequirements(globalRequirementSet, localRequirementSet, locationModels, imageId);
        log.info("Requirements: {}", nodeRequirements);

        List<NodeCandidate> nodeCandidates;
        try {
            nodeCandidates = cloudiatorServiceX.findNodeCandidates(nodeRequirements);
        } catch (ApiException e) {
            log.error("Error during fetching node candidates. Code: {}, ResponseBody: {}", e.getCode(), e.getResponseBody());
            log.error("ApiException: ", e);

            Map<String, List<String>> responseHeaders = MapUtils.emptyIfNull(e.getResponseHeaders());
            for (String key : responseHeaders.keySet()) {
                log.error("ResponseHeader: Key: {}, Value: {}", key, responseHeaders.get(key));
            }
            throw new GeneratorException("Problem during fetching node candidates", e);
        }

        if (CollectionUtils.isEmpty(nodeCandidates)){
            throw new GeneratorException(String.format("Problem during fetching node candidates - empty result for query %s", toJson(nodeRequirements.getRequirements())));
        }

        return nodeCandidates;
    }

    private Map<Integer, List<NodeCandidate>> getAsIndexMap(Map<String, List<NodeCandidate>> nodeCandidatesByProvider) {
        String[] keys = nodeCandidatesByProvider.keySet().toArray(new String[0]);

        return IntStream.range(0, keys.length)
                .boxed()
                .collect(Collectors.toMap(Function.identity(), index -> nodeCandidatesByProvider.get(keys[index])));
    }

    private String toJson(List<Requirement> requirements) {
        return new Gson().toJson(requirements, new TypeToken<List<Requirement>>() {}.getType());
    }

    private void resetServices() {
        for (GeneratorService generatorService : generatorServices) {
            generatorService.reset();
            log.debug("Reseting service {}", generatorService.getClass().getName());
        }
    }

    private Variable createIntegerVariable(ConstraintProblem cp, VariableType variableType, String componentId, List<Integer> values) {
        return createIntegerVariable(null, cp, variableType, componentId, values);
    }

    private Variable createIntegerVariable(String name, ConstraintProblem cp, VariableType variableType, String componentId, List<Integer> values) {
        if (CollectionUtils.isEmpty(values)){
            log.warn("Empty set of variable type: {} for: {}", variableType, componentId);
            throw new GeneratorException(String.format("Empty set of variable type: %s for: %s", variableType, componentId));
        }

        int minPossibleValue = values.get(0);
        int maxPossibleValue = values.get(values.size()-1);

        return createIntegerVariable(name, cp, variableType, componentId, minPossibleValue, maxPossibleValue, variableService.createIntegerListDomain(values));
    }

    private Variable createIntegerVariable(String variableName, ConstraintProblem cp, VariableType variableType, String componentId, int minPossibleValue, int maxPossibleValue, Domain domain) {

        Variable variable = StringUtils.isNotBlank(variableName) ?
                variableService.createIntegerVariable(variableName, variableType, componentId, null, domain) :
                variableService.createIntegerVariable(variableType, componentId, null, domain);

        cp.getVariables().add(variable);

        Constant minConstant = constantService.createIntegerConstant(minPossibleValue, constantService.getConstantName(variableType, componentId, "min"));
        cp.getConstants().add(minConstant);

        ComparisonExpression minCompariton = constraintService.createComparisonExpression(variable, ComparatorEnum.GREATER_OR_EQUAL_TO, minConstant);
        cp.getConstraints().add(minCompariton);

        Constant maxConstant = constantService.createIntegerConstant(maxPossibleValue, constantService.getConstantName(variableType, componentId, "max"));
        cp.getConstants().add(maxConstant);

        ComparisonExpression maxComparition = constraintService.createComparisonExpression(variable, ComparatorEnum.LESS_OR_EQUAL_TO, maxConstant);
        cp.getConstraints().add(maxComparition);

        return variable;
    }


    private List<MetricVariableImpl> getVariables(CamelModel camelModel) {
        return getAllMetrics(camelModel)
                .stream()
                .filter(metricModel -> metricModel instanceof MetricVariableImpl)
                .map(metricModel -> (MetricVariableImpl) metricModel)
                .collect(Collectors.toList());
    }

    private List<MetricVariableImpl> getVariables(CamelModel camelModel, String componentName) {
        return getVariables(camelModel)
                .stream()
                .filter(metricVariable -> metricVariable.getComponent() != null && componentName.equals((metricVariable.getComponent()).getName()))
                .collect(Collectors.toList());
    }

    private List<Metric> getRawAndCompositeMetrics(CamelModel camelModel) {
        List<RawMetric> rawMetrics = getAllMetrics(camelModel)
                .stream()
                .filter(metric -> metric instanceof RawMetric)
                .map(metricModel -> (RawMetric) metricModel)
                .collect(Collectors.toList());

        List<CompositeMetric> compositeMetrics = getAllMetrics(camelModel)
                .stream()
                .filter(metric -> metric instanceof CompositeMetric)
                .map(metricModel -> (CompositeMetric) metricModel)
                .collect(Collectors.toList());

        return Stream.concat(rawMetrics.stream(), compositeMetrics.stream()).collect(Collectors.toList());
    }

    private List<Metric> getAllMetrics(CamelModel camelModel){
        EList<MetricModel> metricModels = camelModel.getMetricModels();
        if (CollectionUtils.isEmpty(metricModels)){
            return Collections.emptyList();
        }

        return metricModels.stream()
                .map(metricModel -> ((MetricTypeModelImpl) metricModel).getMetrics())
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private static class SingletonSupplier<T> implements Supplier<T> {

        private Supplier<T> supplier;
        private T instance;

        private SingletonSupplier(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        @Override
        public T get() {
            if (instance == null) {
                instance = supplier.get();
            }
            return instance;
        }
    }

}
