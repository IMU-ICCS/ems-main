package eu.paasage.upperware.profiler.generator.service.camel.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.cache.exception.CacheException;
import eu.melodic.cloudiator.client.ApiException;
import eu.melodic.cloudiator.client.model.NodeCandidate;
import eu.melodic.cloudiator.client.model.NodeRequirements;
import eu.melodic.cloudiator.client.model.Requirement;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.*;
import eu.paasage.camel.requirement.HorizontalScaleRequirement;
import eu.paasage.camel.requirement.OSOrImageRequirement;
import eu.paasage.camel.requirement.QuantitativeHardwareRequirement;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.profiler.generator.communication.CloudiatorService;
import eu.paasage.upperware.profiler.generator.error.GeneratorException;
import eu.paasage.upperware.profiler.generator.service.camel.*;
import eu.passage.upperware.commons.model.tools.CPModelTool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class NewConstraintProblemServiceImpl implements NewConstraintProblemService {

    private CpFactory cpFactory;
    private List<GeneratorService> generatorServices;
    private CloudiatorService cloudiatorService;
    private CacheService<NodeCandidates> cacheService;
    private NodeCandidatesService nodeCandidatesService;

    private ConstantService constantService;
    private ConstraintService constraintService;
    private VariableService variableService;

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
            cacheService.store(cpName, NodeCandidates.of(nodeCandidatesMap));
            log.info("Node candidates stored under key {}", cpName);
        } catch (CacheException cacheException) {
            throw new GeneratorException(String.format("Problem with storing data to cache under key %s", cpName), cacheException);
        }

        List<Hosting> hostings = NewCamelModelTools.getHostings(camelModel);
        List<InternalComponent> internalComponents = NewCamelModelTools.getLastDeploymentModel(camelModel).getInternalComponents();


        for (VM vm : NewCamelModelTools.getVMs(camelModel)) {
            String vmName = vm.getName();

            String componentName = getComponentNameForVm(internalComponents, hostings, vm);

            //split by provider id
            Map<Integer, List<NodeCandidate>> nodeCandidatesByComponentName = nodeCandidatesMap.get(componentName);
            QuantitativeHardwareRequirement hardwareRequirements = NewCamelModelTools.getHardwareRequirements(vm);

            //required variables
            List<Integer> valuesForProviders = nodeCandidatesService.getValuesForProviders(nodeCandidatesByComponentName);
            Variable providerVariable = createIntegerVariable(cp, VariableType.PROVIDER, componentName, vmName, valuesForProviders);

            HorizontalScaleRequirement scaleRequirementForComponent = NewCamelModelTools.getScaleRequirementForComponent(camelModel.getRequirementModels().get(0).getRequirements(), componentName);

            int minValue = 1;
            int maxValue = 100;
            if (scaleRequirementForComponent != null) {
                minValue = scaleRequirementForComponent.getMinInstances();
                maxValue = scaleRequirementForComponent.getMaxInstances();
            }

            Variable cardinalityVariable = createIntegerVariable(cp, VariableType.CARDINALITY, componentName, vmName, minValue, maxValue, variableService.createIntegerRangeDomain(minValue, maxValue));

            //optional variables
            Variable coresVariable = null;
            if (shouldAddCores(hardwareRequirements)){
                List<Integer> valuesForCores = nodeCandidatesService.getValuesForCores(nodeCandidatesByComponentName);
                coresVariable = createIntegerVariable(cp, VariableType.CORES, componentName, vmName, valuesForCores);
            }

            Variable ramVariable = null;
            if (shouldAddRam(hardwareRequirements)){
                List<Long> valuesForRam = nodeCandidatesService.getValuesForRam(nodeCandidatesByComponentName);
                ramVariable = createLongVariable(cp, VariableType.RAM, componentName, vmName, valuesForRam);
            }

            Variable storageVariable = null;
            if (shouldAddStorage(hardwareRequirements)){
                List<Float> valuesForCores = nodeCandidatesService.getValuesForStorage(nodeCandidatesByComponentName);
                storageVariable = createFloatVariable(cp, VariableType.STORAGE, componentName, vmName, valuesForCores);
            }

            OSOrImageRequirement osOrImageRequirements = NewCamelModelTools.getOsOrImageRequirements(vm);

            Variable osVariable = null;
            if (shouldAddOs(osOrImageRequirements)){
                List<Integer> valuesForOs = nodeCandidatesService.getValuesForOsFamily(nodeCandidatesByComponentName);
                osVariable = createIntegerVariable(cp, VariableType.OS, componentName, vmName, valuesForOs);
            }


            //F(P,x)
            Map<Integer, ComposedExpression> providerFunctions = new HashMap<>();

            for (Integer providerIndex: nodeCandidatesByComponentName.keySet()) {
                //F(P,1)
                List<NodeCandidate> nodeCandidatesForProvider = nodeCandidatesByComponentName.get(providerIndex);
                ComposedExpression providerFunction = providerFunctions.computeIfAbsent(providerIndex, index -> getProviderExpression(cp, providerVariable, index));

                if (coresVariable != null) {
                    Pair<Integer, Integer> rangeForCores = nodeCandidatesService.getRangeForCores(nodeCandidatesForProvider);
                    Constant min = constantService.createIntegerConstant(rangeForCores.getLeft(), constantService.getConstantName(VariableType.CORES, componentName, "min", "p", String.valueOf(providerIndex)));
                    cp.getConstants().add(min);

                    Constant max = constantService.createIntegerConstant(rangeForCores.getRight(), constantService.getConstantName(VariableType.CORES, componentName, "max", "p", String.valueOf(providerIndex)));
                    cp.getConstants().add(max);

                    createConstraints(cp, coresVariable, cardinalityVariable, min, max, providerFunction);
                }

                if (ramVariable != null) {
                    Pair<Long, Long> rangeForRam = nodeCandidatesService.getRangeForRam(nodeCandidatesForProvider);
                    Constant min = constantService.createLongConstant(rangeForRam.getLeft(), constantService.getConstantName(VariableType.RAM, componentName, "min", "p", String.valueOf(providerIndex)));
                    cp.getConstants().add(min);

                    Constant max = constantService.createLongConstant(rangeForRam.getRight(), constantService.getConstantName(VariableType.RAM, componentName, "max", "p", String.valueOf(providerIndex)));
                    cp.getConstants().add(max);

                    createConstraints(cp, ramVariable, cardinalityVariable, min, max, providerFunction);
                }

                if (storageVariable != null) {
                    Pair<Float, Float> rangeForStorage = nodeCandidatesService.getRangeForStorage(nodeCandidatesForProvider);
                    Constant min = constantService.createFloatConstant(rangeForStorage.getLeft(), constantService.getConstantName(VariableType.STORAGE, componentName, "min", "p", String.valueOf(providerIndex)));
                    cp.getConstants().add(min);

                    Constant max = constantService.createFloatConstant(rangeForStorage.getRight(), constantService.getConstantName(VariableType.STORAGE, componentName, "max", "p", String.valueOf(providerIndex)));
                    cp.getConstants().add(max);

                    createConstraints(cp, storageVariable, cardinalityVariable, min, max, providerFunction);
                }

                if (osVariable != null) {
                    Pair<Integer, Integer> rangeForOs = nodeCandidatesService.getRangeForOs(nodeCandidatesForProvider);
                    Constant min = constantService.createIntegerConstant(rangeForOs.getLeft(), constantService.getConstantName(VariableType.OS, componentName, "min", "p", String.valueOf(providerIndex)));
                    cp.getConstants().add(min);

                    Constant max = constantService.createIntegerConstant(rangeForOs.getRight(), constantService.getConstantName(VariableType.OS, componentName, "max", "p", String.valueOf(providerIndex)));
                    cp.getConstants().add(max);

                    createConstraints(cp, osVariable, cardinalityVariable, min, max, providerFunction);
                }
            }
        }

        CPModelTool.printCpModel(cp);

        return cp;
    }

    private String getComponentNameForVm(List<InternalComponent> internalComponents, List<Hosting> hostings, VM vm) {
        Hosting hosting = getFirstMatchingHosting(vm.getProvidedHosts(), hostings)
                .orElseThrow(() -> new GeneratorException(String.format("Hosting for vm %s is null", vm.getName())));

        InternalComponent internalComponent = CollectionUtils.emptyIfNull(internalComponents)
               .stream()
               .filter(ic -> ic.getRequiredHost().getName().equals(hosting.getRequiredHost().getName()))
               .findFirst()
               .orElseThrow(() -> new GeneratorException(String.format("InternalComponent for hosting %s is null", hosting.getName())));

        return internalComponent.getName();
    }

    private Optional<Hosting> getFirstMatchingHosting(EList<ProvidedHost> providedHosts, List<Hosting> hostings) {
        for (ProvidedHost providedHost : providedHosts) {
            String name = providedHost.getName();
            for (Hosting hosting : hostings) {
                if (hosting.getProvidedHost().getName().equals(name)){
                    return Optional.of(hosting);
                }
            }
        }
        return Optional.empty();
    }

    private void createConstraints(ConstraintProblem cp, Variable variable, Variable cardinalityVariable, Constant min, Constant max, ComposedExpression providerFunction) {

        Constant zeroConstant = findConstantByName(cp.getConstants(), "0");

        ComposedExpression composedMinExpression = constraintService.createComposedExpression(OperatorEnum.MINUS, variable, min);
        cp.getAuxExpressions().add(composedMinExpression);

        ComposedExpression multiplyMinComposedExpression = constraintService.createComposedExpression(OperatorEnum.TIMES, cardinalityVariable, providerFunction, composedMinExpression);
        cp.getAuxExpressions().add(multiplyMinComposedExpression);

        cp.getConstraints().add(constraintService.createComparisonExpression(multiplyMinComposedExpression, ComparatorEnum.GREATER_OR_EQUAL_TO, zeroConstant));


        ComposedExpression composedMaxExpression = constraintService.createComposedExpression(OperatorEnum.MINUS, max, variable);
        cp.getAuxExpressions().add(composedMaxExpression);

        ComposedExpression multiplyMaxComposedExpression = constraintService.createComposedExpression(OperatorEnum.TIMES, cardinalityVariable, providerFunction, composedMaxExpression);
        cp.getAuxExpressions().add(multiplyMaxComposedExpression);

        cp.getConstraints().add(constraintService.createComparisonExpression(multiplyMaxComposedExpression, ComparatorEnum.GREATER_OR_EQUAL_TO, zeroConstant));
    }

    private Variable createIntegerVariable(ConstraintProblem cp, VariableType variableType, String componentId, String vmName, List<Integer> values) {
        if (CollectionUtils.isEmpty(values)){
            log.warn("Empty set of variable type: {} for: {}", variableType, componentId);
            throw new GeneratorException(String.format("Empty set of variable type: %s for: %s", variableType, componentId));
        }

        int minPossibleValue = values.get(0);
        int maxPossibleValue = values.get(values.size()-1);

        return createIntegerVariable(cp, variableType, componentId, vmName, minPossibleValue, maxPossibleValue, variableService.createIntegerListDomain(values));
    }

    private Variable createIntegerVariable(ConstraintProblem cp, VariableType variableType, String componentId, String vmName, int minPossibleValue, int maxPossibleValue, Domain domain) {

        Variable variable = variableService.createIntegerVariable(variableType, componentId, vmName, domain);
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

    private Variable createLongVariable(ConstraintProblem cp, VariableType variableType, String componentId, String vmName, List<Long> values) {
        if (CollectionUtils.isEmpty(values)){
            log.warn("Empty set of variable type: {} for: {}", variableType, componentId);
            throw new GeneratorException(String.format("Empty set of variable type: %s for: %s", variableType, componentId));
        }

        long minPossibleValue = values.get(0);
        long maxPossibleValue = values.get(values.size()-1);

        Variable variable = variableService.createLongVariable(variableType, componentId, vmName, variableService.createLongListDomain(values));
        cp.getVariables().add(variable);

        Constant minConstant = constantService.createLongConstant(minPossibleValue, constantService.getConstantName(variableType, componentId, "min"));
        cp.getConstants().add(minConstant);

        ComparisonExpression minCompariton = constraintService.createComparisonExpression(variable, ComparatorEnum.GREATER_OR_EQUAL_TO, minConstant);
        cp.getConstraints().add(minCompariton);

        Constant maxConstant = constantService.createLongConstant(maxPossibleValue, constantService.getConstantName(variableType, componentId, "max"));
        cp.getConstants().add(maxConstant);

        ComparisonExpression maxComparition = constraintService.createComparisonExpression(variable, ComparatorEnum.LESS_OR_EQUAL_TO, maxConstant);
        cp.getConstraints().add(maxComparition);

        return variable;
    }

    private Variable createFloatVariable(ConstraintProblem cp, VariableType variableType, String componentId, String vmName, List<Float> values) {
        if (CollectionUtils.isEmpty(values)){
            log.warn("Empty set of variable type: {} for: {}", variableType, componentId);
            throw new GeneratorException(String.format("Empty set of variable type: %s for: %s", variableType, componentId));
        }

        float minPossibleValue = values.get(0);
        float maxPossibleValue = values.get(values.size()-1);

        Variable variable = variableService.createFloatVariable(variableType, componentId, vmName, variableService.createFloatListDomain(values));
        cp.getVariables().add(variable);

        Constant minConstant = constantService.createFloatConstant(minPossibleValue, constantService.getConstantName(variableType, componentId, "min"));
        cp.getConstants().add(minConstant);

        ComparisonExpression minCompariton = constraintService.createComparisonExpression(variable, ComparatorEnum.GREATER_OR_EQUAL_TO, minConstant);
        cp.getConstraints().add(minCompariton);

        Constant maxConstant = constantService.createFloatConstant(maxPossibleValue, constantService.getConstantName(variableType, componentId, "max"));
        cp.getConstants().add(maxConstant);

        ComparisonExpression maxComparition = constraintService.createComparisonExpression(variable, ComparatorEnum.LESS_OR_EQUAL_TO, maxConstant);
        cp.getConstraints().add(maxComparition);

        return variable;
    }


    private ComposedExpression getProviderExpression(ConstraintProblem cp, Variable providerVariable, Integer providerValue) {
        Constant providerIndexConstant = constantService.createIntegerConstant(providerValue, "provider_" + providerValue);
        cp.getConstants().add(providerIndexConstant);
        ComposedExpression providerFunction = createProviderFunction(providerVariable, providerIndexConstant);
        cp.getAuxExpressions().add(providerFunction);
        return providerFunction;
    }

    private Map<String, Map<Integer, List<NodeCandidate>>> loadProviders(CamelModel camelModel) {
        Map<String, Map<Integer, List<NodeCandidate>>> result = new HashMap<>();

        List<InternalComponent> internalComponents = NewCamelModelTools.getLastDeploymentModel(camelModel).getInternalComponents();

        List<Hosting> hostings = NewCamelModelTools.getHostings(camelModel);

            for (VM vm : NewCamelModelTools.getVMs(camelModel)) {
            List<NodeCandidate> nodeCandidates = getNodeCandidates(vm);
            Map<String, List<NodeCandidate>> stringListMap = nodeCandidatesService.groupByProviders(nodeCandidates);

            Map<Integer, List<NodeCandidate>> tempMap = new HashMap<>();

            int i=0;
            for (String s : stringListMap.keySet()) {
                tempMap.put(i, stringListMap.get(s));
                i++;
            }
            result.put(getComponentNameForVm(internalComponents, hostings, vm), tempMap);
        }
        return result;
    }

    private Constant findConstantByName(EList<Constant> constants, String name) {
        return constants.stream()
                .filter(constant -> name.equals(constant.getId()))
                .findFirst()
                .orElseThrow(() -> new GeneratorException(String.format("Could not find constant with name %s", name)));
    }

    /**
     * This method should return F(P,x) function
     * @param providerVariable
     * @param providerConstant
     * @return
     */
    private ComposedExpression createProviderFunction(Variable providerVariable, Constant providerConstant){
        return constraintService.createComposedExpression(OperatorEnum.EQ, providerVariable, providerConstant);
    }

    private List<NodeCandidate> getNodeCandidates(VM vm) {
        NodeRequirements nodeRequirements = cloudiatorService.createNodeRequirements(vm);
        List<NodeCandidate> nodeCandidates;
        try {
            nodeCandidates = cloudiatorService.findNodeCandidates(nodeRequirements);
        } catch (ApiException e) {
            throw new GeneratorException("Problem during fetching node candidates", e);
        }
        if (CollectionUtils.isEmpty(nodeCandidates)){
            throw new GeneratorException(String.format("Problem during fetching node candidates - empty result for query %s", toJson(nodeRequirements.getRequirements())));
        }
        return nodeCandidates;
    }

    private boolean shouldAddCores(QuantitativeHardwareRequirement quantitativeHardwareRequirement){
        return quantitativeHardwareRequirement.getMinCores() > 0 && quantitativeHardwareRequirement.getMaxCores() > 0;
    }

    private boolean shouldAddRam(QuantitativeHardwareRequirement quantitativeHardwareRequirement){
        return quantitativeHardwareRequirement.getMinRAM()> 0 && quantitativeHardwareRequirement.getMaxRAM() > 0;
    }

    private boolean shouldAddStorage(QuantitativeHardwareRequirement quantitativeHardwareRequirement){
        return quantitativeHardwareRequirement.getMinStorage()> 0 && quantitativeHardwareRequirement.getMaxStorage() > 0;
    }

    private boolean shouldAddOs(OSOrImageRequirement osOrImageRequirement){
        return osOrImageRequirement != null && StringUtils.isNotBlank(osOrImageRequirement.getName());
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

}
