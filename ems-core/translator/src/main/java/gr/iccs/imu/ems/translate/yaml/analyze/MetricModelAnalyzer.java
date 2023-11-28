/*
 * Copyright (C) 2023-2025 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package gr.iccs.imu.ems.translate.yaml.analyze;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ParseContext;
import gr.iccs.imu.ems.brokercep.cep.MathUtil;
import gr.iccs.imu.ems.translate.Grouping;
import gr.iccs.imu.ems.translate.TranslationContext;
import gr.iccs.imu.ems.translate.dag.DAGNode;
import gr.iccs.imu.ems.translate.model.*;
import gr.iccs.imu.ems.translate.yaml.NebulousEmsTranslatorProperties;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static gr.iccs.imu.ems.translate.yaml.analyze.AnalysisUtils.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetricModelAnalyzer {
    private final NebulousEmsTranslatorProperties properties;
    private final ShorthandsExpansionHelper shorthandsExpansionHelper;
    private final MetricModelValidator validator;

    // ================================================================================================================
    // Model analysis methods

    public void analyzeModel(@NonNull TranslationContext _TC, @NonNull Object metricModel, String modelName) throws Exception {
        log.debug("MetricModelAnalyzer.analyzeModel(): BEGIN: metric-model: {}", metricModel);

        // -- Initialize jsonpath context -------------------------------------
        Configuration jsonpathConfig = Configuration.defaultConfiguration();
        ParseContext parseContext = JsonPath.using(jsonpathConfig);
        DocumentContext ctx = parseContext.parse(metricModel);

        // -- Expand shorthand expressions ------------------------------------
        log.debug("MetricModelAnalyzer.analyzeModel(): Expanding shorthand expressions in metric model: {}", metricModel);
        shorthandsExpansionHelper.expandShorthandExpressions(metricModel, modelName, ctx);

        // -- Schematron Validation -------------------------------------------
        log.debug("MetricModelAnalyzer.analyzeModel(): Validating metric model: {}", metricModel);
        if (! properties.isSkipModelValidation()) {
            validator.validateModel(metricModel, modelName);
            log.debug("MetricModelAnalyzer.analyzeModel(): Metric model is valid: {}", modelName);
        }

        // -- Model processing ------------------------------------------------
        log.debug("MetricModelAnalyzer.analyzeModel(): Analyzing metric model: {}", metricModel);

        // ----- Get component and scope names -----
        List<String> componentNamesList = ctx.read("$.spec.components.*.name", List.class);
        List<String> scopeNamesList = ctx.read("$.spec.scopes.*.name", List.class);
        log.debug("Component names: {}", componentNamesList);
        log.debug("    Scope names: {}", scopeNamesList);

        // Check name uniqueness
        checkNameUniqueness(componentNamesList, scopeNamesList);

        Set<String> componentNames = new LinkedHashSet<>(componentNamesList);
        Set<String> scopeNames = new LinkedHashSet<>(scopeNamesList);

        // ----- Build component-to-scope mapping -----
        //XXX:TODO:  .... do we need this?
        Map<String, Set<String>> componentToScopeMap = new LinkedHashMap<>();
        ctx.read("$.spec.scopes.*", List.class).stream().filter(Objects::nonNull).forEach(scope -> {
            String sloName = JsonPath.read(scope, "$.name").toString();
            Object oComponents = ((Map)scope).get("components");
            Set<String> includedComponents = componentNames;
            if (oComponents instanceof String s)
                includedComponents = Arrays.stream(s.split(","))
                        .map(String::trim).filter(str->!str.isBlank())
                        .collect(Collectors.toSet());
            if (oComponents instanceof List l)
                includedComponents = ((List<Object>) l).stream().filter(Objects::nonNull)
                        .filter(i->i instanceof String).map(i -> i.toString().trim())
                        .filter(str -> !str.isBlank()).collect(Collectors.toSet());
            if (includedComponents.isEmpty())
                includedComponents = componentNames;
            Set<String> notFound = includedComponents.stream()
                    .filter(i -> !componentNames.contains(i)).collect(Collectors.toSet());
            if (!notFound.isEmpty())
                throw createException("Scope component(s) "+notFound+" have not been specified in scope: "+sloName);
            else
                componentToScopeMap.put(sloName, includedComponents);
        });
        log.debug("Component-to-Scope map: {}", componentToScopeMap);

        // ----- Set container name and make mutable all 'spec' elements (and sub-elements) -----
        Map<String, Object> modelRoot = asMap(ctx.read("$"));
        Object specNode = modelRoot.get("spec");
        specNode = addContainerNameAndMakeMutable(specNode, null);
        modelRoot.put("spec", specNode);

        // ----- Build artifact directory -----

        // ----- Define additional translation structures and cache them in _TC -----
        _TC.setExtensionContext(new AdditionalTranslationContextData());

        Map<String, Object> parentSpecs = $$(_TC).parentSpecs;
        Map<NamesKey, Object> allMetrics = $$(_TC).allMetrics;
        Map<NamesKey, Object> allConstraints = $$(_TC).allConstraints;
        Map<NamesKey, Object> allSLOs = $$(_TC).allSLOs;

        // ----- Build of flat lists of metrics, constraints and SLOs, and check their specs -----
        asList(JsonPath.read(modelRoot, "$.spec.*.*")).stream().filter(Objects::nonNull).forEach(spec -> {
            String parentName = getSpecName(spec);
            if (StringUtils.isBlank(parentName)) throw createException("Component or Scope with no name: " + spec);
            parentSpecs.computeIfAbsent(parentName, (key)->spec);

            // Requirements (SLOs) flat list building
            List<Object> slos = JsonPath.read(spec,
                    "$[?(@.requirements!=null && @.requirements.*[?(@.type=='slo')])].requirements.*");
            slos.stream().filter(Objects::nonNull).forEach(sloSpec -> {
                String sloName = getSpecName(sloSpec);
                if (StringUtils.isBlank(sloName)) throw createException("SLO spec with no name: " + sloSpec);
                allSLOs.put(NamesKey.create(parentName, sloName), sloSpec);
            });

            // Constraints flat list building
            slos.stream().filter(Objects::nonNull).forEach(sloSpec -> {
                String sloName = getSpecName(sloSpec);
                if (StringUtils.isBlank(sloName)) throw createException("SLO spec with no name: " + sloSpec);

                NamesKey sloNamesKey = NamesKey.create(parentName, sloName);
                Map<String, Object> constraintSpec = asMap(asMap(sloSpec).get("constraint"));
                NamesKey constraintNamesKey = createNamesKey(sloNamesKey, sloName);
                allConstraints.put(constraintNamesKey, constraintSpec);
            });

            // Metrics flat list building
            List<Object> metrics = JsonPath.read(spec, "$[?(@.metrics!=null)].metrics.*");
            metrics.stream().filter(Objects::nonNull).forEach(metricSpec -> {
                String metricName = getSpecName(metricSpec);
                if (StringUtils.isBlank(metricName)) throw createException("Metric spec with no name: " + metricSpec);
                NamesKey namesKey = NamesKey.create(parentName, metricName);
                allMetrics.put(namesKey, metricSpec);
            });
        });
        log.debug("    All Metrics: {}", allMetrics);
        log.debug("All Constraints: {}", allConstraints);
        log.debug("       All SLOs: {}", allSLOs);

        // ----- Build of constants lists -----
        asList(JsonPath.read(modelRoot, "$.spec.*.*.metrics.*[?(@.type=='constant')]")).stream().filter(Objects::nonNull).forEach(spec -> {
            processConstant(_TC, asMap(spec), getContainerName(spec));
        });

        // ----- Decompose SLOs with metric constraints to their metric hierarchies -----
        Map<NamesKey, Object> metricSLOs = allSLOs.entrySet().stream()
                //.peek(x -> log.warn("-------->  {}", getSpecField(asMap(x.getValue()).get("constraint"), "type") ))
                .filter(x -> "metric".equals( getSpecField(asMap(x.getValue()).get("constraint"), "type") ))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        decomposeConstraints(_TC, metricSLOs);

        // ----- Decompose SLOs with non-metric constraints -----
        Map<NamesKey, Object> nonMetricSLOs = new LinkedHashMap<>($$(_TC).allSLOs);
        nonMetricSLOs.keySet().removeAll( metricSLOs.keySet() );
        decomposeConstraints(_TC, nonMetricSLOs);

        // ----- Populate component (or data) names in requirements and metrics -----
        //populateComponentNames();
        //...also check about ObjectContexts....

        // ----- Infer groupings (levels) -----
        log.debug("Inferring and setting groupings");
        inferGroupings(_TC);

        // ----- Build each component's SLO set (including those in the scopes it participates) -----

        // -- Updating Translation Context ------------------------------------



        // ----------------------------------------------------------

        log.debug("MetricModelAnalyzer.analyzeModel(): END: metric-model: {}", metricModel);
    }

    // ------------------------------------------------------------------------
    //  Additional translation data structures and shorthand retrieval method
    // ------------------------------------------------------------------------

    private AdditionalTranslationContextData $$(TranslationContext _TC) {
        return _TC.$(AdditionalTranslationContextData.class);
    }

    @Data
    @Accessors(fluent = true)
    private static class AdditionalTranslationContextData {
        private final Map<String, Object> parentSpecs = new LinkedHashMap<>();          // i.e. all component and scope specs
        private final Map<NamesKey, Object> allSLOs = new LinkedHashMap<>();
        private final Map<NamesKey, Object> allConstraints = new LinkedHashMap<>();
        private final Map<NamesKey, Object> allMetrics = new LinkedHashMap<>();
        private final Map<NamesKey, MetricContext> metricsUsed = new LinkedHashMap<>();
        private final Map<NamesKey, Constraint> constraintsUsed = new LinkedHashMap<>();
        private final Map<NamesKey, Object> constants = new LinkedHashMap<>();
    }

    // ------------------------------------------------------------------------
    //  Analysis helper methods
    // ------------------------------------------------------------------------

    private static void checkNameUniqueness(List<String> componentNamesList, List<String> scopeNamesList) {
        List<String> all = new ArrayList<>(componentNamesList);
        all.addAll(scopeNamesList);
        log.trace("      ALL names: {}", all);
        List<String> duplicateNames = all.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();
        if (! duplicateNames.isEmpty()) {
            log.error("Duplicate names: {}", duplicateNames);
            throw createException("Naming conflicts exist for: "+duplicateNames);
        }
    }

    private Object addContainerNameAndMakeMutable(Object o, String parentName) {
        if (o instanceof Map m) {
            Map<String, Object> newM = new LinkedHashMap<>();
            newM.put(CONTAINER_NAME_KEY, parentName);
            String myName0 = getSpecName(o);
            String myName = parentName != null ? parentName : myName0;
            m.forEach((k,v) -> newM.put(k.toString(), addContainerNameAndMakeMutable(v, myName)));
            return newM;
        } else
        if (o instanceof List l) {
            List<Object> newL = new LinkedList<>();
            l.forEach(v -> newL.add(addContainerNameAndMakeMutable(v, parentName)));
            return newL;
        }
        return o;
    }

    // ------------------------------------------------------------------------
    //  Constraints decomposition methods
    // ------------------------------------------------------------------------

    private final static String DEFAULT_CONSTRAINT_NAME_SUFFIX = "_CONSTRAINT";

    private void decomposeConstraints(TranslationContext _TC, Map<NamesKey, Object> sloList) {
        sloList.forEach((sloNamesKey, sloSpec) -> {
            ServiceLevelObjective slo = ServiceLevelObjective.builder()
                    .name(sloNamesKey.name())
                    .object(sloSpec)
                    .build();
            _TC.addSLO(slo);
            _TC.getDAG().addTopLevelNode(slo);

            Object object = asMap(sloSpec).get("constraint");
            if (object==null)
                throw createException("SLO without 'constraint': "+sloSpec);
            if (object instanceof Map constraintSpec) {
                Constraint constr = decomposeConstraint(_TC, asMap(constraintSpec), sloNamesKey, slo);
                slo.setConstraint(constr);
            } else
                throw createException("SLO constraint is not Map: "+sloSpec);
        });
    }

    private Constraint decomposeConstraint(@NonNull TranslationContext _TC, Map<String, Object> constraintSpec, NamesKey parentNamesKey, NamedElement parent) {
        // Get constraint type
        String type = getSpecField(constraintSpec, "type");
        if (StringUtils.isBlank(type))
            throw createException("Constraint without 'type': "+constraintSpec);

        String constraintName = getSpecName(constraintSpec);
        if (StringUtils.isBlank(constraintName))
            constraintName = parentNamesKey.child + DEFAULT_CONSTRAINT_NAME_SUFFIX;
        NamesKey constraintNamesKey = createNamesKey(parentNamesKey, constraintName);
        if ($$(_TC).constraintsUsed.containsKey(constraintNamesKey)) {
            Constraint cachedConstraint = $$(_TC).constraintsUsed.get(constraintNamesKey);
            _TC.getDAG().addNode(parent, cachedConstraint);
            return cachedConstraint;
        }

        // Delegate constraint decomposition based on type
        Constraint constraintNode = switch (type) {
            case "metric" ->
                    decomposeMetricConstraint(_TC, constraintSpec, parentNamesKey, parent);
            case "logical" ->
                    decomposeLogicalConstraint(_TC, constraintSpec, parentNamesKey, parent);
            case "conditional" ->
                    decomposeConditionalConstraint(_TC, constraintSpec, parentNamesKey, parent);
            default ->
                    throw createException("Constraint 'type' not supported: " + constraintSpec);
        };
        $$(_TC).constraintsUsed.put(constraintNamesKey, constraintNode);

        return constraintNode;
    }

    private MetricConstraint decomposeMetricConstraint(@NonNull TranslationContext _TC, Map<String, Object> constraintSpec, NamesKey parentNamesKey, NamedElement parent) {
        // Get needed fields
        String constraintName = getSpecName(constraintSpec);
        String metricName = getMandatorySpecField(constraintSpec, "metric", "Metric Constraint without 'metric': ");
        String comparisonOperator = getMandatorySpecField(constraintSpec, "operator", "Metric Constraint without 'operator': ");
        Double threshold = getSpecNumber(constraintSpec, "threshold", "Metric Constraint without 'threshold': ");

        if (StringUtils.isBlank(constraintName))
            constraintName = parentNamesKey.child + DEFAULT_CONSTRAINT_NAME_SUFFIX;
        NamesKey constraintNamesKey = createNamesKey(parentNamesKey, constraintName);
        NamesKey metricNamesKey = createNamesKey(parentNamesKey, metricName);

        // Further field checks
        if (! $$(_TC).allMetrics.containsKey(metricNamesKey))
            throw createException("Unspecified metric '"+metricNamesKey.name()+"' found in metric constraint: "+ constraintSpec);

        if (! isComparisonOperator(comparisonOperator))
            throw createException("Unknown comparison operator '"+comparisonOperator+"' in metric constraint: "+ constraintSpec);

        // Update TC
        MetricConstraint metricConstraint = MetricConstraint.builder()
                .name(constraintNamesKey.name())
                .object(constraintSpec)
                .comparisonOperator(ComparisonOperatorType.byOperator(comparisonOperator))
                .threshold(threshold)
                .build();
        _TC.getDAG().addNode(parent, metricConstraint);

        // Decompose metric
        MetricContext metric = decomposeMetric(
                _TC, asMap($$(_TC).allMetrics.get(metricNamesKey)), constraintNamesKey, metricConstraint);

        // Complete TC update
        metricConstraint.setMetricContext(metric);
        _TC.addMetricConstraint(metricConstraint);

        return metricConstraint;
    }

    private LogicalConstraint decomposeLogicalConstraint(TranslationContext _TC, Map<String, Object> constraintSpec, NamesKey parentNamesKey, NamedElement parent) {
        // Get needed fields
        String constraintName = getSpecName(constraintSpec);
        String logicalOperator = getMandatorySpecField(constraintSpec, "operator", "Logical Constraint without 'operator': ");
        List<Object> composingConstraints = asList(constraintSpec.get("constraints"));

        if (StringUtils.isBlank(constraintName))
            constraintName = parentNamesKey.child + DEFAULT_CONSTRAINT_NAME_SUFFIX;
        NamesKey constraintNamesKey = createNamesKey(parentNamesKey, constraintName);

        // Further field checks
        if (! isLogicalOperator(logicalOperator))
            throw createException("Unknown logical operator '"+logicalOperator+"' in metric constraint: "+ constraintSpec);
        if (composingConstraints==null || composingConstraints.isEmpty())
            throw createException("At least one composing constraint must be provided in logical constraint: "+ constraintSpec);

        // Update TC
        LogicalConstraint logicalConstraint = LogicalConstraint.builder()
                .name(constraintNamesKey.name())
                .object(constraintSpec)
                .logicalOperator(LogicalOperatorType.valueOf(logicalOperator.trim().toUpperCase()))
                .build();
        _TC.getDAG().addNode(parent, logicalConstraint);

        // Decompose composing constraints
        List<Constraint> composingConstraintsList = new ArrayList<>();
        for (Object cc : composingConstraints) {
            String sloName = cc.toString();
            Constraint childConstraint = decomposeComposingConstraint(_TC, sloName, parentNamesKey, logicalConstraint);
            composingConstraintsList.add(childConstraint);
        }
        logicalConstraint.setConstraints(composingConstraintsList);

        // Complete TC update
        _TC.addLogicalConstraint(logicalConstraint);

        return logicalConstraint;
    }

    private IfThenConstraint decomposeConditionalConstraint(TranslationContext _TC, Map<String, Object> constraintSpec, NamesKey parentNamesKey, NamedElement parent) {
        // Get needed fields
        String constraintName = getSpecName(constraintSpec);

        if (StringUtils.isBlank(constraintName))
            constraintName = parentNamesKey.child + DEFAULT_CONSTRAINT_NAME_SUFFIX;
        NamesKey constraintNamesKey = createNamesKey(parentNamesKey, constraintName);

        // Get the referenced constraints names (their SLOs' actually)
        String   ifSloName = getMandatorySpecField(constraintSpec, "if", "Unspecified IF part in conditional constraint '"+constraintNamesKey.name()+"': "+ constraintSpec);
        String thenSloName = getMandatorySpecField(constraintSpec, "then", "Unspecified THEN part in conditional constraint '"+constraintNamesKey.name()+"': "+ constraintSpec);
        String elseSloName = getSpecField(constraintSpec, "else");

        // Update TC
        IfThenConstraint conditionalConstraint = IfThenConstraint.builder()
                .name(constraintNamesKey.name())
                .object(constraintSpec)
                .build();
        _TC.getDAG().addNode(parent, conditionalConstraint);

        // Decompose the composing metrics
        Constraint   ifConstraint = decomposeComposingConstraint(_TC,   ifSloName, parentNamesKey, conditionalConstraint);
        Constraint thenConstraint = decomposeComposingConstraint(_TC, thenSloName, parentNamesKey, conditionalConstraint);
        Constraint elseConstraint = StringUtils.isNotBlank(elseSloName)
                ? decomposeComposingConstraint(_TC, elseSloName, parentNamesKey, conditionalConstraint) : null;

        // Update the conditional (main) constraint
        conditionalConstraint.setIfConstraint(ifConstraint);
        conditionalConstraint.setThenConstraint(thenConstraint);
        if (elseConstraint!=null)
            conditionalConstraint.setElseConstraint(elseConstraint);

        // Complete TC update
        _TC.addIfThenConstraint(conditionalConstraint);

        return conditionalConstraint;
    }

    private Constraint decomposeComposingConstraint(TranslationContext _TC, String sloName, NamesKey parentNamesKey, Constraint parentConstraint) {
        // Get referenced constraint spec (its SLO spec actually)
        Object sloSpec = $$(_TC).allSLOs.get(NamesKey.create(parentNamesKey.parent, sloName));

        // Construct SLO namesKey
        NamesKey sloNamesKey = NamesKey.create(getContainerName(sloSpec), sloName);

        // Get constraint spec from SLO spec
        Map<String, Object> constraintSpec = asMap(asMap(sloSpec).get("constraint"));

        // Decompose composing constraint
        return decomposeConstraint(_TC, constraintSpec, sloNamesKey, parentConstraint);
    }

    // ------------------------------------------------------------------------
    //  Metric decomposition methods
    // ------------------------------------------------------------------------

    private final static String DEFAULT_METRIC_NAME_SUFFIX = "_METRIC";

    private MetricContext decomposeMetric(@NonNull TranslationContext _TC, Map<String, Object> metricSpec, NamesKey parentNamesKey, NamedElement parent) {
        // Get needed fields
        String metricName = getSpecName(metricSpec).toLowerCase();
        String metricType = getSpecField(metricSpec, "type");

        NamesKey metricNamesKey = getNamesKey(metricSpec, metricName);
        if ($$(_TC).metricsUsed.containsKey(metricNamesKey)) {
            MetricContext cachedMetric = $$(_TC).metricsUsed.get(metricNamesKey);
            _TC.getDAG().addNode(parent, cachedMetric);
            return cachedMetric;
        }

        // Infer metric type
        if (StringUtils.isBlank(metricType)) {
            boolean hasSensor = (metricSpec.get("sensor") != null);
            boolean hasFormula = (metricSpec.get("formula") != null);
            boolean hasConstant = (metricSpec.get("constant") != null);
            boolean hasRef = (metricSpec.get("ref") != null);

                 if ( hasSensor && !hasFormula && !hasConstant && !hasRef) metricType = "raw";
            else if (!hasSensor &&  hasFormula && !hasConstant && !hasRef) metricType = "composite";
            else if (!hasSensor && !hasFormula &&  hasConstant && !hasRef) metricType = "constant";
            else if (!hasSensor && !hasFormula && !hasConstant &&  hasRef) metricType = "ref";
            else
                throw createException("Cannot infer type of metric '"+metricName+"': " + metricSpec);
        }

        // If a 'constant' stop, because constants have already been registered
        if ("constant".equals(metricType)) {
            return null;
        }
        // ...else continue with metric decomposition

        // Delegate decomposition based on metric type
        MetricContext metric = switch (metricType) {
            case "raw" ->
                    decomposeRawMetric(_TC, metricSpec, parentNamesKey, parent);
            case "composite" ->
                    decomposeCompositeMetric(_TC, metricSpec, parentNamesKey, parent);
            case "ref" ->
                    processRef(_TC, metricSpec, parentNamesKey, parent);
            default ->
                    throw createException("Unknown metric type '" + metricType + "' in metric '" + metricName + "': " + metricSpec);
        };
        $$(_TC).metricsUsed.put(metricNamesKey, metric);

        // Set 'object'
        metric.setObject(metricSpec);

        // Process template
        MetricTemplate template = processMetricTemplate(metricSpec, metricNamesKey);
        if (template!=null)
            metric.getMetric().setMetricTemplate(template);

        // Process metric level
        processMetricGrouping(metric, parentNamesKey);

        return metric;
    }

    private RawMetricContext decomposeRawMetric(TranslationContext _TC, Map<String, Object> metricSpec, NamesKey parentNamesKey, NamedElement parent) {
        // Get needed fields
        String metricName = getSpecName(metricSpec);
        Map<String, Object> sensorSpec = asMap(metricSpec.get("sensor"));
        Map<String, Object> outputSpec = asMap(metricSpec.get("output"));

        NamesKey metricNamesKey = getNamesKey(metricSpec, metricName);

        // Update TC
        RawMetricContext rawMetric = RawMetricContext.builder()
                .name(metricNamesKey.name())
                .object(metricSpec)
                .build();
        _TC.getDAG().addNode(parent, rawMetric);

        // Process child blocks
        Sensor sensor = processSensor(_TC, sensorSpec, metricNamesKey, rawMetric);
        Schedule schedule = outputSpec!=null
                ? processSchedule(outputSpec, metricNamesKey) : null;

        // Complete TC update
        rawMetric.setSensor(sensor);
        rawMetric.setSchedule(schedule);
        rawMetric.setMetric(RawMetric.builder()
                .name(metricName + DEFAULT_METRIC_NAME_SUFFIX)
                .build());

        return rawMetric;
    }

    private CompositeMetricContext decomposeCompositeMetric(TranslationContext _TC, Map<String, Object> metricSpec, NamesKey parentNamesKey, NamedElement parent) {
        // Get needed fields
        String metricName = getSpecName(metricSpec);
        String formula = getMandatorySpecField(metricSpec, "formula", "Composite Metric '"+metricName+"' without 'formula': ");
        Map<String, Object> windowSpec = asMap(metricSpec.get("window"));
        Map<String, Object> outputSpec = asMap(metricSpec.get("output"));

        NamesKey metricNamesKey = getNamesKey(metricSpec, metricName);

        // Check formula and extract metrics
        if (StringUtils.isBlank(formula))
            throw createException("Composite metric 'formula' cannot be blank: at '"+metricNamesKey.name()+"': " + metricSpec);
        @NonNull Set<String> formulaArgs = MathUtil.getFormulaArguments(formula);
        log.trace("decomposeCompositeMetric: {}: formula={}, args={}", metricNamesKey.name(), formula, formulaArgs);

        // Remove constants from 'formulaArgs'
        String containerName = getContainerName(metricSpec);
        formulaArgs.removeAll( $$(_TC).constants.keySet().stream()
                .filter(nk ->  nk.parent.equals(containerName))         // Check that all formula args are metrics under the same parent
                .map(nk->nk.child)
                .collect(Collectors.toSet()));
        log.trace("decomposeCompositeMetric: {}: After removing constants: formula={}, args={}", metricNamesKey.name(), formula, formulaArgs);

        // Update TC
        CompositeMetricContext compositeMetric = CompositeMetricContext.builder()
                .name(metricNamesKey.name())
                .object(metricSpec)
                .build();
        _TC.getDAG().addNode(parent, compositeMetric);

        // Decompose to metrics used in formula (i.e. composing or child metrics)
        // NOTE: child metric decomposition MUST be done before this metric has been altered in any way (or else
        //       hashCode() will return new value, different from that cached in the DAG)
        List<MetricContext> childMetricsList = new ArrayList<>();
        for (String childMetricName : formulaArgs) {
            NamesKey childMetricNamesKey = getNamesKey(metricSpec, childMetricName);
            MetricContext childMetric = decomposeMetric(
                    _TC, asMap($$(_TC).allMetrics.get(childMetricNamesKey)), metricNamesKey, compositeMetric);
            if (childMetric!=null)
                childMetricsList.add(childMetric);
        }
        compositeMetric.setComposingMetricContexts(childMetricsList);

        // Process child blocks
        Window window = windowSpec!=null
                ? processWindow(windowSpec, metricNamesKey) : null;
        Schedule schedule = outputSpec!=null
                ? processSchedule(outputSpec, metricNamesKey) : null;

        // Complete TC update
        compositeMetric.setWindow(window);
        compositeMetric.setSchedule(schedule);
        compositeMetric.setMetric(CompositeMetric.builder()
                .name(metricName + DEFAULT_METRIC_NAME_SUFFIX)
                .formula(formula)
                .componentMetrics(childMetricsList.stream().map(MetricContext::getMetric).toList())
                .build());

        return compositeMetric;
    }

    private MetricContext processRef(TranslationContext _TC, Map<String, Object> metricSpec, NamesKey parentNamesKey, NamedElement parent) {
        // Get needed fields
        String refStr = getMandatorySpecField(metricSpec, "ref", "Metric reference must provide a value for 'ref' field: at metric '" + parentNamesKey.name() + "': " + metricSpec);

        // Process 'ref'
        refStr = refStr.replace("[", "").replace("]", "");
        NamesKey referencedNamesKey = NamesKey.isFullName(refStr)
                ? NamesKey.create(refStr) : NamesKey.create(parentNamesKey.parent, refStr);
        MetricContext referencedMetric = $$(_TC).metricsUsed.get(referencedNamesKey);
        if (referencedMetric==null) {
            Object spec = $$(_TC).allMetrics.get(referencedNamesKey);
            if (spec==null)
                throw createException("Cannot find referenced metric '"+referencedNamesKey.name()+"': " + metricSpec);
            referencedMetric = decomposeMetric(_TC, asMap(spec), parentNamesKey, parent);
            // 'decomposeMetric' will take care to add referencedMetric into the DAG
        } else {
            // add a new edge to the referencedMetric in the DAG
            _TC.getDAG().addNode(parent, referencedMetric);
        }

        return referencedMetric;
    }

    private void processConstant(TranslationContext _TC, Map<String, Object> metricSpec, String parentName) {
        // Get needed fields
        String metricName = getSpecName(metricSpec);
        Double defaultValue = getSpecNumber(metricSpec, "default");

        NamesKey metricNamesKey = NamesKey.create(parentName, metricName);

        // Register metric constant, and initial value (if available)
        $$(_TC).constants.put(metricNamesKey, defaultValue);
    }

    private MetricTemplate processMetricTemplate(Map<String, Object> metricSpec, NamesKey parentNamesKey) {
        Object templateObj = metricSpec.get("template");
        if (templateObj instanceof Map templateSpec) {
            String id = getSpecField(templateSpec, "id");
            String type = getMandatorySpecField(templateSpec, "type", "Metric template without 'type': at metric '" + parentNamesKey.name() + "': " + metricSpec);
            ValueType valueType = ValueType.valueOf(type.trim().toUpperCase() + "_TYPE");
            String dirStr = getSpecField(templateSpec, "direction");
            short valueDirection = StringUtils.isNotBlank(dirStr) ? Short.parseShort(dirStr.trim()) : 0;    //XXX:TODO: IMPROVE!!!
            List<Object> range = asList(templateSpec.get("range"));             //XXX:TODO: ...Add it... IMPROVE MetricTemplate class
            List<Object> values = asList(templateSpec.get("values"));           //XXX:TODO: ...Add it... IMPROVE MetricTemplate class
            String unit = getSpecField(templateSpec, "unit");

            return MetricTemplate.builder()
                    .object(templateSpec)
                    .name(id)
                    .valueType(valueType)
                    .valueDirection(valueDirection)
                    .unit(unit)
                    .build();
        }
        return null;
    }

    private MetricContext processMetricGrouping(MetricContext metric, NamesKey parentNamesKey) {
        String groupingStr = getSpecField(metric.getObject(), "level");      //XXX:TODO: ...change to 'grouping'
        if (StringUtils.isNotBlank(groupingStr)) {
            Grouping grouping = Grouping.valueOf(groupingStr.trim().toUpperCase());
            //XXX:TODO: ....shall we do something with this??   This might be also check during inferGroupings()
        }
        return metric;
    }


    // ------------------------------------------------------------------------
    //  Window block processing methods
    // ------------------------------------------------------------------------

    private Window processWindow(Map<String, Object> windowSpec, NamesKey metricNamesKey) {
        // Get window type
        String typeStr = getMandatorySpecField(windowSpec, "type", "Window without 'type': at metric '"+metricNamesKey+"': ");
        WindowType windowType = WindowType.valueOf(typeStr.toUpperCase());

        // Get window size
        Map<String, Object> sizeMap = asMap(windowSpec.get("size"));
        String sizeTypeStr = getSpecField(sizeMap, "type");
        Object valueObj = sizeMap.get("value");
        String unitStr = getSpecField(sizeMap, "unit");

        // Check window size value
        if (!(valueObj instanceof Number) && !(valueObj instanceof String))
            throw createException("Window size value is mandatory and MUST be a positive integer: at metric '" + metricNamesKey + "': " + windowSpec);
        long value = valueObj instanceof Number n
                ? n.longValue() : Long.parseLong(valueObj.toString());
        if (value<=0)
            throw createException("Window size value cannot be zero or negative: at metric '" + metricNamesKey + "': " + windowSpec);

        // Check window size (time) unit
        ChronoUnit unit = StringUtils.isNotBlank(unitStr)
                ? normalizeTimeUnit(unitStr) : ChronoUnit.SECONDS;

        // Get or infer window size type
        WindowSizeType sizeType;
        if (StringUtils.isNotBlank(sizeTypeStr))
            sizeType = WindowSizeType.valueOf(sizeTypeStr.trim().toUpperCase());
        else
            sizeType = (unit !=null) ? WindowSizeType.TIME_ONLY : WindowSizeType.MEASUREMENTS_ONLY;

        // Initialize size parameters
        long measurementSize = -1;
        long timeSize = -1;
        String timeUnit = null;
        if (sizeType!=WindowSizeType.MEASUREMENTS_ONLY) {
            timeSize = value;
            timeUnit = unit.toString();
        }
        if (sizeType!=WindowSizeType.TIME_ONLY) {
            measurementSize = value;
        }

        // Process any 'processing' blocks
        List<Object> processingSpecs = asList(windowSpec.get("processing"));
        List<WindowProcessing> processingsList = null;
        if (processingSpecs!=null) {
            for (Object processingObj : processingSpecs) {
                WindowProcessing processing = processProcessing(asMap(processingObj), metricNamesKey);
                if (processingsList==null)
                    processingsList = new ArrayList<>();
                processingsList.add(processing);
            }
        }

        return Window.builder()
                .object(windowSpec)
                .windowType(windowType)
                .sizeType(sizeType)
                .measurementSize(measurementSize)
                .timeSize(timeSize)
                .timeUnit(timeUnit)
                .processings(processingsList)
                .build();
    }

    private WindowProcessing processProcessing(Map<String, Object> processingSpec, NamesKey metricNamesKey) {
        // Get processing type
        String typeStr = getMandatorySpecField(processingSpec, "type", "Window Processing without 'type': at metric '"+metricNamesKey+"': ");
        WindowProcessingType processingType = WindowProcessingType.valueOf(typeStr.trim().toUpperCase());

        // Get 'function' field
        String functionStr = getSpecField(processingSpec, "function");
        //XXX:TODO: .... do something with 'function' or delete it!!

        // Process any 'criteria' blocks
        List<Object> criteriaSpecs = asList(processingSpec.get("criteria"));
        List<WindowCriterion> criteriaList = null;
        if (criteriaSpecs!=null) {
            for (Object criterionObj : criteriaSpecs) {
                WindowCriterion criterion = processCriterion(criterionObj, metricNamesKey);
                if (criteriaList==null)
                    criteriaList = new ArrayList<>();
                criteriaList.add(criterion);
            }
        }

        return WindowProcessing.builder()
                .object(processingSpec)
                .processingType(processingType)
                .groupingCriteria( processingType==WindowProcessingType.GROUP ? criteriaList : null )
                .rankingCriteria( processingType!=WindowProcessingType.GROUP ? criteriaList : null )
                .build();
    }

    private WindowCriterion processCriterion(Object criterionSpec, NamesKey metricNamesKey) {
        if (criterionSpec instanceof String typeStr) {
            CriterionType criterionType = CriterionType.valueOf(typeStr);
            if (criterionType!=CriterionType.CUSTOM)
                return WindowCriterion.builder()
                        .type(criterionType).build();
            else
                throw createException("Window Criterion of '"+criterionType+"' type cannot be a string: at metric '" + metricNamesKey + "': " + criterionSpec);
        }

        if (criterionSpec instanceof Map) {
            // Get processing type
            String typeStr = getMandatorySpecField(criterionSpec, "type", "Window Criterion without 'type': at metric '"+metricNamesKey+"': ");
            CriterionType criterionType = CriterionType.valueOf(typeStr);

            // Get 'custom' field (if CUSTOM)
            String custom = null;
            if (criterionType==CriterionType.CUSTOM) {
                custom = getMandatorySpecField(criterionSpec, "custom",
                        "CUSTOM window criterion must provide 'custom' field: at metric '" + metricNamesKey + "': ");
            }

            // Get sort direction (ascending, or descending)
            String ascStr = getSpecField(criterionSpec, "ascending");
            boolean isAscending = getBooleanValue(ascStr.trim(), true);

            return WindowCriterion.builder()
                    .object(criterionSpec)
                    .type(criterionType)
                    .custom(custom)
                    .ascending(isAscending)
                    .build();
        }

        throw createException("Window Criterion specification not supported: at metric '" + metricNamesKey + "': " + criterionSpec);
    }

    // ------------------------------------------------------------------------
    //  Schedule block processing methods
    // ------------------------------------------------------------------------

    private Schedule processSchedule(Map<String, Object> scheduleSpec, NamesKey metricNamesKey) {
        // Get needed fields
        String type = getSpecField(scheduleSpec, "type");
        if (StringUtils.isBlank(type)) type = "all";

        // Get 'schedule'
        Map<String, Object> schedMap = asMap(scheduleSpec.get("schedule"));

        // Get schedule value
        Object valueObj = schedMap.get("value");
        long interval;
        if (!(valueObj instanceof Number) && !(valueObj instanceof String))
            throw createException("Schedule value is mandatory and MUST be a positive integer: at metric '" + metricNamesKey.name() + "': " + scheduleSpec);
        else
            interval = (valueObj instanceof Number n) ? n.longValue() : Long.parseLong(valueObj.toString());
        if (interval<=0)
            throw createException("Schedule value cannot be zero or negative: at metric '" + metricNamesKey.name() + "': " + scheduleSpec);

        // Get schedule unit
        String unitStr = getSpecField(schedMap, "unit");
        ChronoUnit unit = StringUtils.isNotBlank(unitStr)
                ? normalizeTimeUnit(unitStr) : ChronoUnit.SECONDS;

        return Schedule.builder()
                .object(scheduleSpec)
                .interval(interval)
                .timeUnit(unit.toString())
                .type(Schedule.SCHEDULE_TYPE.valueOf(type.trim().toUpperCase()))
                .build();
    }

    // ------------------------------------------------------------------------
    //  Sensor block processing methods
    // ------------------------------------------------------------------------

    private static final String DEFAULT_SENSOR_NAME_SUFFIX = "_SENSOR";
    private static final String DEFAULT_SENSOR_TYPE = "netdata";

    private Sensor processSensor(TranslationContext _TC, Map<String, Object> sensorSpec, NamesKey parentNamesKey, NamedElement parent) {
        // Get needed fields
        String sensorName = getSpecName(sensorSpec);
        String sensorType = getSpecField(sensorSpec, "type");
        if (StringUtils.isBlank(sensorName)) sensorName = parentNamesKey.child + DEFAULT_SENSOR_NAME_SUFFIX;
        if (StringUtils.isBlank(sensorType)) sensorType = DEFAULT_SENSOR_TYPE;

        NamesKey sensorNamesKey = createNamesKey(parentNamesKey, sensorName);

        // Get 'push' or 'pull' type
        boolean isPush = getBooleanValue(getSpecField(sensorSpec, "push"), false);
        boolean isPull = getBooleanValue(getSpecField(sensorSpec, "pull"), false);
        if (isPush && isPull)
            throw createException("Sensor cannot be both 'push' and 'pull': sensor '" + sensorName + "' in metric '" + parentNamesKey + "': " + sensorSpec);

        // Get additional fields
        Object configObj = sensorSpec.get("config");            //XXX:TODO: ...check how it is used...
        Object installObj = sensorSpec.get("install");          //XXX:TODO: ...add this???

        // Update TC
        Sensor sensor = Sensor.builder()
                .name(sensorNamesKey.name())
                .object(sensorSpec)
                .isPush(isPush)
                .configurationStr( configObj instanceof String s ? s : null )
                .additionalProperties( configObj instanceof Map m ? m : null )
                .build();
        _TC.getDAG().addNode(parent, sensor);

        //XXX:TODO: ... add Monitor, add Component-Sensor pair

        return sensor;
    }

    // ------------------------------------------------------------------------
    //  Grouping inference methods
    // ------------------------------------------------------------------------

    private void inferGroupings(TranslationContext _TC) {
        Grouping topLevelGrouping = Grouping.GLOBAL;
        Grouping leafGrouping = Grouping.PER_INSTANCE;

        _TC.getDAG().traverseDAG(node -> {
            NamedElement elem = node.getElement();
            if (elem!=null) {
                inferElementGrouping(_TC, topLevelGrouping, leafGrouping, node);
            }
        });
    }

    private static void inferElementGrouping(TranslationContext _TC, Grouping topLevelGrouping, Grouping leafGrouping, DAGNode node) {
        // Check if grouping has already been set
        if (node.getGrouping()!=null) return;

        // Get node model element
        NamedElement elem = node.getElement();
        if (elem==null) return;                     // Root node?

        // Infer element grouping
        Object groupingObj;
        if (elem instanceof ServiceLevelObjective || elem instanceof Constraint) {
            groupingObj = topLevelGrouping;
        } else if (elem instanceof Sensor || elem instanceof RawMetricContext) {
            groupingObj = leafGrouping;
        } else {
            // Infer parents' groupings
            Set<DAGNode> parents = _TC.getDAG().getParentNodes(node);
            parents.forEach(p -> inferElementGrouping(_TC, topLevelGrouping, leafGrouping, p));

            // Get the lowest parents grouping
            List<Grouping> parentGroupings = parents.stream()
                    .filter(p -> p.getElement()!=null)
                    .map(DAGNode::getGrouping)
                    .collect(Collectors.toSet()).stream()
                    .filter(Objects::nonNull)
                    .sorted().toList();
            if (! parentGroupings.isEmpty())
                groupingObj = parentGroupings.get(0);
            else
                groupingObj = topLevelGrouping;

            // Get grouping in element specification (if provided)
            if (elem.getObject() instanceof Map m) {
                Object gObj = m.get("grouping");
                if (gObj == null)
                    gObj = m.get("level");
                if (gObj != null) {
                    Grouping specGrouping = Grouping.valueOf( gObj.toString().trim().toUpperCase() );
                    if (specGrouping.ordinal() >= leafGrouping.ordinal()) {
                        if (specGrouping.ordinal() < Grouping.valueOf(groupingObj.toString().toUpperCase()).ordinal()) {
                            groupingObj = specGrouping;
                        }
                    } else {
                        groupingObj = leafGrouping;
                    }
                }
            }
        }
        node.setGrouping(Grouping.valueOf(groupingObj.toString().toUpperCase()));
    }

}