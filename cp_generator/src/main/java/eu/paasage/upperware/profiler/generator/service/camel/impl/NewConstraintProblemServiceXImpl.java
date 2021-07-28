package eu.paasage.upperware.profiler.generator.service.camel.impl;

import camel.constraint.ComparisonOperatorType;
import camel.constraint.ConstraintModel;
import camel.constraint.impl.MetricVariableConstraintImpl;
import camel.core.CamelModel;
import camel.core.NamedElement;
import camel.deployment.RequirementSet;
import camel.deployment.SoftwareComponent;
import camel.deployment.impl.DeploymentTypeModelImpl;
import camel.location.LocationModel;
import camel.metric.CompositeMetric;
import camel.metric.Metric;
import camel.metric.MetricVariable;
import camel.metric.RawMetric;
import camel.metric.impl.MetricTypeModelImpl;
import camel.metric.impl.MetricVariableImpl;
import camel.requirement.HorizontalScaleRequirement;
import camel.type.PrimitiveType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.cache.exception.CacheException;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.profiler.generator.communication.NodeCandidatesFetchingService;
import eu.paasage.upperware.profiler.generator.error.GeneratorException;
import eu.paasage.upperware.profiler.generator.service.camel.*;
import eu.paasage.upperware.profiler.generator.service.camel.creator.VariableCreator;
import eu.paasage.upperware.profiler.generator.service.camel.parser.ExpressionService;
import eu.passage.upperware.commons.model.tools.CPModelTool;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadata;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadataTool;
import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.model.NodeCandidate;
import org.activeeon.morphemic.model.Requirement;
import org.apache.commons.collections4.CollectionUtils;
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

import static eu.passage.upperware.commons.MelodicConstants.CDO_SERVER_PATH;

@Slf4j
@Service
public class NewConstraintProblemServiceXImpl implements NewConstraintProblemServiceX {

    private CpFactory cpFactory;
    private List<GeneratorService> generatorServices;
    private NodeCandidatesFetchingService nodeCandidatesFetchingService;
    private CacheService<NodeCandidates> memcacheService;
    private CacheService<NodeCandidates> filecacheService;
    private NodeCandidatesService nodeCandidatesService;
    private ConstantService constantService;
    private ConstraintService constraintService;
    private VariableService variableService;
    private MetricService metricService;
    private ExpressionService expressionService;

    private VariableCreatorFactory variableCreatorFactory;

    @Autowired
    public NewConstraintProblemServiceXImpl(CpFactory cpFactory, List<GeneratorService> generatorServices,
                                            NodeCandidatesFetchingService nodeCandidatesFetchingService, @Qualifier("memcacheService") CacheService<NodeCandidates> memcacheService,
                                            @Qualifier("filecacheService") CacheService<NodeCandidates> filecacheService, NodeCandidatesService nodeCandidatesService,
                                            ConstantService constantService, ConstraintService constraintService, VariableService variableService,
                                            MetricService metricService, ExpressionService expressionService, VariableCreatorFactory variableCreatorFactory) {
        this.cpFactory = cpFactory;
        this.generatorServices = generatorServices;
        this.nodeCandidatesFetchingService = nodeCandidatesFetchingService;
        this.memcacheService = memcacheService;
        this.filecacheService = filecacheService;
        this.nodeCandidatesService = nodeCandidatesService;
        this.constantService = constantService;
        this.constraintService = constraintService;
        this.variableService = variableService;
        this.metricService = metricService;
        this.expressionService = expressionService;
        this.variableCreatorFactory = variableCreatorFactory;
    }

    @Override
    public ConstraintProblem createConstraintProblem(CamelModel camelModel, String cpName, String resourceName) {
        resetServices();

        //CP creation
        ConstraintProblem cp = cpFactory.createConstraintProblem();
        cp.setId(cpName);

        //adding default value for 0 constant
        cp.getConstants().add(constantService.createIntegerConstant(0, String.valueOf(0)));
        cp.getConstants().add(constantService.createIntegerConstant(1, String.valueOf(1)));

        Map<String, Map<Integer, List<NodeCandidate>>> nodeCandidatesMap =  loadProviders(camelModel, resourceName);
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

            Optional<MetricVariableImpl> mCardinality = CamelMetadataTool.findVariableFor(variables, CamelMetadata.CARDINALITY);
            CpVariable cardinalityVariable;

            if (mCardinality.isPresent()) {
                MetricVariableImpl cardinalityMetricVariable = mCardinality.get();
                String variableCardinalityName = cardinalityMetricVariable.getName();

                VariableCreator creator = variableCreatorFactory.getCreator(getType(cardinalityMetricVariable));
                cardinalityVariable = creator.createCpVariable(cp, VariableType.CARDINALITY, componentName, variableService.createIntegerRangeDomain(minValue, maxValue), variableCardinalityName);
            } else {
                VariableCreator creator = variableCreatorFactory.getCreator(PrimitiveType.INT_TYPE);
                cardinalityVariable = creator.createCpVariable(cp, VariableType.CARDINALITY, componentName, variableService.createIntegerRangeDomain(minValue, maxValue));
            }

            //required variables
            List<Integer> valuesForProviders = nodeCandidatesService.getValuesForProviders(nodeCandidatesByComponentName);
            CpVariable providerVariable = variableCreatorFactory.getCreator(PrimitiveType.INT_TYPE)
                    .createCpVariable(cp, CamelMetadata.PROVIDER.variableType, componentName, variableService.createIntegerListDomain(valuesForProviders));


            //optional variables
            CpVariable coresVariable = null;
            Optional<MetricVariableImpl> optCores = CamelMetadataTool.findVariableFor(variables, CamelMetadata.CORES);
            if (optCores.isPresent()){
                List<Integer> valuesForCores = nodeCandidatesService.getValuesForCores(nodeCandidatesByComponentName);
                coresVariable = variableCreatorFactory.getCreator(PrimitiveType.INT_TYPE)
                        .createCpVariable(cp, CamelMetadata.CORES.variableType, componentName, variableService.createIntegerListDomain(valuesForCores), optCores.get().getName());
            }

            CpVariable ramVariable = null;
            Optional<MetricVariableImpl> optRam = CamelMetadataTool.findVariableFor(variables, CamelMetadata.RAM);
            if (optRam.isPresent()){
                List<Long> valuesForRam = nodeCandidatesService.getValuesForRam(nodeCandidatesByComponentName);
                ramVariable = variableCreatorFactory.getCreator(PrimitiveType.INT_TYPE)
                        .createCpVariable(cp, CamelMetadata.RAM.variableType, componentName, variableService.createIntegerListDomain(mapLongToInteger(valuesForRam)), optRam.get().getName());
            }

            CpVariable storageVariable = null;
            Optional<MetricVariableImpl> optStorage = CamelMetadataTool.findVariableFor(variables, CamelMetadata.STORAGE);
            if (optStorage.isPresent()){
                List<Integer> valuesForStorage = nodeCandidatesService.getValuesForStorage(nodeCandidatesByComponentName);
                storageVariable = variableCreatorFactory.getCreator(PrimitiveType.INT_TYPE)
                        .createCpVariable(cp, CamelMetadata.STORAGE.variableType, componentName, variableService.createIntegerListDomain(valuesForStorage), optStorage.get().getName());
            }

            CpVariable latitudeVariable = null;
            Optional<MetricVariableImpl> optLatitude = CamelMetadataTool.findVariableFor(variables, CamelMetadata.LATITUDE);
            if (optLatitude.isPresent()){
                List<Integer> valuesForLatitude = nodeCandidatesService.getValuesForLatitude(nodeCandidatesByComponentName);
                latitudeVariable = variableCreatorFactory.getCreator(PrimitiveType.INT_TYPE)
                        .createCpVariable(cp, CamelMetadata.LATITUDE.variableType, componentName, variableService.createIntegerListDomain(valuesForLatitude), optLatitude.get().getName());
            }

            CpVariable longitudeVariable = null;
            Optional<MetricVariableImpl> optLongitude = CamelMetadataTool.findVariableFor(variables, CamelMetadata.LONGITUDE);
            if (optLongitude.isPresent()){
                List<Integer> valuesForLongitude = nodeCandidatesService.getValuesForLongitude(nodeCandidatesByComponentName);
                longitudeVariable = variableCreatorFactory.getCreator(PrimitiveType.INT_TYPE)
                        .createCpVariable(cp, CamelMetadata.LONGITUDE.variableType, componentName, variableService.createIntegerListDomain(valuesForLongitude), optLongitude.get().getName());
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
                    Pair<Integer, Integer> rangeForStorage = nodeCandidatesService.getRangeForStorage(nodeCandidatesForProvider);
                    Constant min = constantService.createIntegerConstant(rangeForStorage.getLeft(), constantService.getConstantName(VariableType.STORAGE, componentName, "min", "p", String.valueOf(providerIndex)));
                    cp.getConstants().add(min);

                    Constant max = constantService.createIntegerConstant(rangeForStorage.getRight(), constantService.getConstantName(VariableType.STORAGE, componentName, "max", "p", String.valueOf(providerIndex)));
                    cp.getConstants().add(max);

                    createConstraints(cp, storageVariable, cardinalityVariable, min, max, providerFunctionSupplier);
                }

                if (latitudeVariable != null) {
                    Pair<Integer, Integer> rangeForLatitude = nodeCandidatesService.getRangeForLatitude(nodeCandidatesForProvider);
                    Constant min = constantService.createIntegerConstant(rangeForLatitude.getLeft(), constantService.getConstantName(VariableType.LATITUDE, componentName, "min", "p", String.valueOf(providerIndex)));
                    cp.getConstants().add(min);

                    Constant max = constantService.createIntegerConstant(rangeForLatitude.getRight(), constantService.getConstantName(VariableType.LATITUDE, componentName, "max", "p", String.valueOf(providerIndex)));
                    cp.getConstants().add(max);

                    createConstraints(cp, latitudeVariable, cardinalityVariable, min, max, providerFunctionSupplier);
                }

                if (longitudeVariable != null) {
                    Pair<Integer, Integer> rangeForLongitude = nodeCandidatesService.getRangeForLongitude(nodeCandidatesForProvider);
                    Constant min = constantService.createIntegerConstant(rangeForLongitude.getLeft(), constantService.getConstantName(VariableType.LONGITUDE, componentName, "min", "p", String.valueOf(providerIndex)));
                    cp.getConstants().add(min);

                    Constant max = constantService.createIntegerConstant(rangeForLongitude.getRight(), constantService.getConstantName(VariableType.LONGITUDE, componentName, "max", "p", String.valueOf(providerIndex)));
                    cp.getConstants().add(max);

                    createConstraints(cp, longitudeVariable, cardinalityVariable, min, max, providerFunctionSupplier);
                }
            }
        }

        addMetrics(cp, camelModel);

        addConstraint(cp, camelModel, cpName);
        CPModelTool.printCpModel(cp);

        return cp;
    }

    private void addConstraint(ConstraintProblem cp, CamelModel camelModel, String cacheKey) {

        getMetricVariableConstraints(camelModel)
            .stream()
            .peek(metricVariableConstraint -> log.info("Working with MetricVariableConstraintImpl: {}", metricVariableConstraint.getName()))
            .forEach(metricVariableConstraint -> {
                MetricVariable metricVariable = metricVariableConstraint.getMetricVariable();
                double threshold = metricVariableConstraint.getThreshold();
                ComparisonOperatorType comparisonOperator = metricVariableConstraint.getComparisonOperator();

                String name = metricVariable.getName();
                String formula = metricVariable.getFormula();

                if (StringUtils.isNotBlank(formula)){
                    expressionService.parse(name, formula, cp, camelModel, cacheKey);

                    ComposedExpression composedExpression = constraintService.getByName(cp.getAuxExpressions(), name)
                            .orElseThrow(() -> new GeneratorException("AuxExpression " + name + " not created!"));

                    boolean isInteger = threshold % 1 == 0;
                    Constant tresholdConstant = isInteger ? constantService.createIntegerConstant((int)threshold) : constantService.createDoubleConstant(threshold);
                    cp.getConstants().add(tresholdConstant);
                    cp.getConstraints().add(constraintService.createComparisonExpression(composedExpression, convertComparator(comparisonOperator), tresholdConstant));

                }
        });

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

    private List<MetricVariableConstraintImpl> getMetricVariableConstraints(CamelModel camelModel){
        return CollectionUtils.emptyIfNull(camelModel.getConstraintModels())
                .stream()
                .map(ConstraintModel::getConstraints)
                .flatMap(Collection::stream)
                .filter(constraint -> constraint instanceof MetricVariableConstraintImpl)
                .map(metricModel -> (MetricVariableConstraintImpl) metricModel)
                .collect(Collectors.toList());
    }

    private void addMetrics(ConstraintProblem cp, CamelModel camelModel) {
        // 1) - RawMetrics
        List<RawMetric> rawMetrics = getRawMetrics(camelModel);
        log.info("Found {} RawMetrics: {}", rawMetrics.size(), rawMetrics.stream().map(NamedElement::getName).collect(Collectors.joining(",", "[", "]")));

        cp.getCpMetrics()
                .addAll(rawMetrics.stream()
                        .peek(metric -> log.info("Working with RawMetric: {}", metric.getName()))
                        .peek(metric -> log.info("Creating MetricVariable for RawMetrics: {} with type {}", metric.getName(), getType(metric)))
                        .map(metric -> metricService.createCpMetric(metric.getName(), getType(metric))).collect(Collectors.toList()));

//        2) - CompositeMetrics
        List<CompositeMetric> compositeMetrics = getCompositeMetrics(camelModel);
        log.info("Found {} CompositeMetrics: {}", compositeMetrics.size(), compositeMetrics.stream().map(NamedElement::getName).collect(Collectors.joining(",", "[", "]")));

        cp.getCpMetrics()
                .addAll(compositeMetrics.stream()
                        .peek(metric -> log.info("Working with CompositeMetric: {}", metric.getName()))
                        .peek(metric -> log.info("Creating MetricVariable for CompositeMetrics: {} with type {}", metric.getName(), getType(metric)))
                        .map(metric ->metricService.createCpMetric(metric.getName(), getType(metric))).collect(Collectors.toList()));

//        4) - Traditional Attribute
        List<MetricVariableImpl> variables = getVariables(camelModel);
        cp.getCpMetrics()
                .addAll(variables
                .stream()
                .filter(MetricVariableImpl::isCurrentConfiguration)
                .filter(CamelMetadataTool::isFromVariable)
                .peek(metricVariable -> log.info("Creating metric variable for isCurrentConfiguration = true and traditional attribue {} with type {}", metricVariable.getName(), getType(metricVariable)))
                .map(metricVariable -> metricService.createCpMetric( metricVariable.getName(), getType(metricVariable)))
                .collect(Collectors.toList()));

//        7) - is onNodeCandidate
        variables
                .stream()
                .filter(MetricVariableImpl::isOnNodeCandidates)
                .forEach(metricVariable -> log.warn("Flag onNodeCandidate currently unsupported. Variable {} will be ignored", metricVariable.getName()));
    }

    private List<Integer> mapLongToInteger(List<Long> from){
        return from.stream().map(Long::intValue).collect(Collectors.toList());
    }

    private Supplier<ComposedExpression> getProviderExpression(ConstraintProblem cp, CpVariable providerVariable, String componentName, Integer providerValue) {
        Constant providerIndexConstant = constantService.createIntegerConstant(providerValue, "provider_" + componentName  + "_" + providerValue);
        cp.getConstants().add(providerIndexConstant);
        return createProviderFunction(cp, providerVariable, providerIndexConstant);
    }

    /**
     * This method should return F(P,x) function
     */
    private Supplier<ComposedExpression> createProviderFunction(ConstraintProblem cp, CpVariable providerVariable, Constant providerConstant){
        return new NewConstraintProblemServiceXImpl.SingletonSupplier<>(() -> {
            ComposedExpression composedExpression = constraintService.createComposedExpression(OperatorEnum.EQ, providerVariable, providerConstant);
            cp.getAuxExpressions().add(composedExpression);
            return composedExpression;
        });
    }

    private void createConstraints(ConstraintProblem cp, CpVariable variable, CpVariable cardinalityVariable, Constant min, Constant max, Supplier<ComposedExpression> composedExpressionSupplier) {

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

    private PrimitiveType getType(Metric metricVariable) {
        return metricVariable.getMetricTemplate().getValueType().getPrimitiveType();
    }

    private Map<String, Map<Integer, List<NodeCandidate>>> loadProviders(CamelModel camelModel, String resourceName) {
        Map<String, Map<Integer, List<NodeCandidate>>> result = new HashMap<>();

        DeploymentTypeModelImpl deploymentTypeModel = getDeploymentModel(camelModel);

        for (SoftwareComponent softwareComponent : deploymentTypeModel.getSoftwareComponents()) {
            List<NodeCandidate> nodeCandidates = loadProviders(
                    deploymentTypeModel.getGlobalRequirementSet(),
                    softwareComponent.getRequirementSet(),
                    camelModel.getLocationModels(),
                    resourceName
                    );
            log.info("NewConstraintProblemServiceXImpl->loadProviders softwareComponent name: {}, nodeCandidates: {}", softwareComponent.getName(), nodeCandidates);
            Map<String, List<NodeCandidate>> nodeCandidatesByProvider = nodeCandidatesService.groupByProviders(nodeCandidates);
            Map<Integer, List<NodeCandidate>> nodeCandidatesByProviderIndex = getAsIndexMap(nodeCandidatesByProvider);
            result.put(softwareComponent.getName(), nodeCandidatesByProviderIndex);
        }

        return result;
    }

    private DeploymentTypeModelImpl getDeploymentModel(CamelModel camelModel) {
        return (DeploymentTypeModelImpl) camelModel.getDeploymentModels().get(0);
    }

    private List<NodeCandidate> loadProviders(RequirementSet globalRequirementSet, RequirementSet localRequirementSet, List<LocationModel> locationModels, String resourceName) {
        List<Requirement> requirements = nodeCandidatesFetchingService.createRequirements(globalRequirementSet, localRequirementSet, locationModels, resourceName);
        log.info("Requirements: {}", requirements);

        List<NodeCandidate> nodeCandidates;
        try {
            nodeCandidates = nodeCandidatesFetchingService.findNodeCandidates(requirements);
        } catch (RuntimeException e) {
            log.error("Error during fetching node candidates. Message: {}", e.getMessage());
            log.error("RuntimeException: ", e);

            throw new GeneratorException("Problem during fetching node candidates", e);
        }

        if (CollectionUtils.isEmpty(nodeCandidates)){
            throw new GeneratorException(String.format("Problem during fetching node candidates - empty result for query %s", toJson(requirements)));
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

    private List<RawMetric> getRawMetrics(CamelModel camelModel) {
        return getAllMetrics(camelModel)
                .stream()
                .filter(metric -> metric instanceof RawMetric)
                .map(metricModel -> (RawMetric) metricModel)
                .collect(Collectors.toList());
    }

    private List<CompositeMetric> getCompositeMetrics(CamelModel camelModel) {
        return getAllMetrics(camelModel)
                .stream()
                .filter(metric -> metric instanceof CompositeMetric)
                .map(metricModel -> (CompositeMetric) metricModel)
                .collect(Collectors.toList());
    }

    private List<Metric> getAllMetrics(CamelModel camelModel){
        return CollectionUtils.emptyIfNull(camelModel.getMetricModels())
                .stream()
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
