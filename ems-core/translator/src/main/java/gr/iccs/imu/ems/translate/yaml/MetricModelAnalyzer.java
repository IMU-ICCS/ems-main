/*
 * Copyright (C) 2023-2025 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package gr.iccs.imu.ems.translate.yaml;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ParseContext;
import gr.iccs.imu.ems.translate.TranslationContext;
import gr.iccs.imu.ems.translate.model.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetricModelAnalyzer {
    private final NebulousEmsTranslatorProperties properties;
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
        expandShorthandExpressions(metricModel, modelName, ctx);

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
        Set<String> componentNames = new LinkedHashSet<>(componentNamesList);
        Set<String> scopeNames = new LinkedHashSet<>(scopeNamesList);

        // ----- Build component-to-scope mapping -----
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

        // ----- Build artifact directory -----

        // ----- Build of flat lists of metrics, and SLOs, and check their specs -----
        Map<NamesKey, Object> allMetrics = new LinkedHashMap<>();
        Map<NamesKey, Object> allSLOs = new LinkedHashMap<>();
        ctx.read("$.spec.*.*", List.class).stream().filter(Objects::nonNull).forEach(spec -> {
            String parentName = getSpecName(spec);
            if (StringUtils.isBlank(parentName)) throw createException("Component or Scope with no name: " + spec);

            List<Object> metrics = JsonPath.read(spec, "$[?(@.metrics!=null)].metrics.*");
            metrics.stream().filter(Objects::nonNull).forEach(metricSpec -> {
                String metricName = getSpecName(metricSpec);
                if (StringUtils.isBlank(metricName)) throw createException("Metric spec with no name: " + metricSpec);
                allMetrics.put(NamesKey.create(parentName, metricName), metricSpec);
            });

            List<Object> slos = JsonPath.read(spec,
                    "$[?(@.requirements!=null && @.requirements.*[?(@.type=='slo')])].requirements.*");
            slos.stream().filter(Objects::nonNull).forEach(sloSpec -> {
                String sloName = getSpecName(sloSpec);
                if (StringUtils.isBlank(sloName)) throw createException("SLO spec with no name: " + sloSpec);
                allSLOs.put(NamesKey.create(parentName, sloName), sloSpec);
            });
        });
        log.warn("All Metrics: {}", allMetrics);
        log.warn("   All SLOs: {}", allSLOs);

        // ----- Decompose SLOs to their metric hierarchies -----
        Set<NamesKey> metricsUsed = new LinkedHashSet<>();
        allSLOs.forEach((sloNamesKey, sloSpec) -> {
            ServiceLevelObjective slo = ServiceLevelObjective.builder()
                    .name(sloNamesKey.name())
                    .object(sloSpec)
                    .build();
            _TC.addSLO(slo);
            _TC.getDAG().addTopLevelNode(slo);

            Object oConstr = ((Map)sloSpec).get("constraint");
            if (oConstr==null)
                throw createException("SLO without 'constraint': "+sloSpec);
            if (oConstr instanceof Map constrSpec) {
                decomposeConstraint(_TC, constrSpec, sloNamesKey, slo, allMetrics, metricsUsed);    //XXX:TODO: Improve signature
            } else
                throw createException("SLO constraint is not Map: "+sloSpec);
        });

        // ----- Infer metric levels -----

        // ----- Build each component's SLO set (including those in the scopes it participates) -----

        // -- Updating Translation Context ------------------------------------



        // ----------------------------------------------------------

        log.debug("MetricModelAnalyzer.analyzeModel(): END: metric-model: {}", metricModel);
    }

    private final static Pattern METRIC_CONSTRAINT_PATTERN =
            Pattern.compile("^([^<>=!]+)([<>]=|=[<>]|<>|!=|[=><])(.+)$");

    private void expandShorthandExpressions(Object metricModel, String modelName, DocumentContext ctx) throws Exception {
        // ----- Expand SLO constraints -----
        List<Object> expandedConstraints = asList(ctx
                .read("$.spec.*.*.requirements.*[?(@.constraint!=null)]", List.class)).stream()
                        .filter(item -> JsonPath.read(item, "$.constraint") instanceof String)
                        .peek(this::expandConstraint)
                        .toList();
        log.debug("MetricModelAnalyzer.analyzeModel(): Constraints expanded: {}", expandedConstraints);

        //XXX:TODO: ++++ more thing to expand (window, output, sensor, etc)
    }

    // ------------------------------------------------------------------------
    //  Methods for expanding shorthand expressions
    // ------------------------------------------------------------------------

    private void expandConstraint(Object spec) {
        String constraintStr = JsonPath.read(spec, "$.constraint").toString().trim();
        Matcher matcher = METRIC_CONSTRAINT_PATTERN.matcher(constraintStr);
        if (matcher.matches()) {
            String g1 = matcher.group(1);
            String g2 = matcher.group(2);
            String g3 = matcher.group(3);

            if (! isComparisonOperator(g2))
                throw createException("Invalid metric constraint shorthand expression in Requirement [Group 2 not a comparison operator]: "+spec);

            // Swap operands
            if (isDouble(g1)) {
                String tmp = g1;
                g1 = g3;
                g3 = tmp;
            }

            if (StringUtils.isBlank(g1) || StringUtils.isBlank(g3))
                throw createException("Invalid metric constraint shorthand expression in Requirement [Group 1 or 3 is blank]: "+spec);

            String metricName = g1.trim();
            double threshold = Double.parseDouble(g3.trim());

            Map<String, Object> constrMap = Map.of(
                    "type", "metric",
                    "metric", metricName,
                    "operator", g2.trim(),
                    "threshold", threshold
            );

            asMap(spec).put("constraint", constrMap);
        } else
            throw createException("Invalid metric constraint shorthand expression: "+spec);
    }

    // ------------------------------------------------------------------------
    //  Constraints decomposition methods
    // ------------------------------------------------------------------------

    private final static String DEFAULT_CONSTRAINT_NAME_SUFFIX = "_CONSTRAINT";

    private void decomposeConstraint(@NonNull TranslationContext _TC, Map<String, Object> constraintSpec, NamesKey parentNamesKey, NamedElement parent, Map<NamesKey, Object> allMetrics, Set<NamesKey> metricsUsed) {
        // Get constraint type
        String type = getSpecField(constraintSpec, "type");
        if (StringUtils.isBlank(type))
            throw createException("Constraint without 'type': "+constraintSpec);

        // Delegate constraint decomposition based on type
        switch (type) {
            case "metric" ->
                    decomposeMetricConstraint(_TC, constraintSpec, parentNamesKey, parent, allMetrics, metricsUsed);
            case "logical" ->
                    decomposeLogicalConstraint(_TC, constraintSpec, parentNamesKey, parent, allMetrics, metricsUsed);
            case "conditional" ->
                    decomposeConditionalConstraint(_TC, constraintSpec, parentNamesKey, parent, allMetrics, metricsUsed);
            case "variable" ->
                    decomposeMetricVariableConstraint(_TC, constraintSpec, parentNamesKey, parent, allMetrics, metricsUsed);
            default ->
                    throw createException("Constraint 'type' not supported: " + constraintSpec);
        }
    }

    private MetricConstraint decomposeMetricConstraint(@NonNull TranslationContext _TC, Map<String, Object> constraintSpec, NamesKey parentNamesKey, NamedElement parent, Map<NamesKey, Object> allMetrics, Set<NamesKey> metricsUsed) {
        // Get needed fields
        String constraintName = getSpecName(constraintSpec);
        String metricName = getMandatorySpecField(constraintSpec, "metric", "Metric Constraint without 'metric': ");
        String comparisonOperator = getMandatorySpecField(constraintSpec, "operator", "Metric Constraint without 'operator': ");
        Double threshold = getSpecNumber(constraintSpec, "threshold", "Metric Constraint without 'threshold': ");

        if (StringUtils.isBlank(constraintName))
            constraintName = parentNamesKey.child + DEFAULT_CONSTRAINT_NAME_SUFFIX;
        NamesKey constraintNamesKey = getNamesKey(parentNamesKey, constraintName);
        NamesKey metricNamesKey = getNamesKey(parentNamesKey, metricName);

        // Further field checks
        if (! allMetrics.containsKey(metricNamesKey))
            throw createException("Unspecified metric '"+metricNamesKey+"' found in metric constraint: "+ constraintSpec);

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
                _TC, asMap(allMetrics.get(metricNamesKey)), constraintNamesKey, metricConstraint, allMetrics, metricsUsed);

        // Complete TC update
        metricConstraint.setMetricContext(metric);
        _TC.addMetricConstraint(metricConstraint);

        return metricConstraint;
    }

    private void decomposeLogicalConstraint(TranslationContext _TC, Map<String, Object> constraintSpec, NamesKey parentNamesKey, NamedElement parent, Map<NamesKey, Object> allMetrics, Set<NamesKey> metricsUsed) {
        throw new RuntimeException("NOT YET IMPLEMENTED: decomposeLogicalConstraint");
    }

    private void decomposeConditionalConstraint(TranslationContext _TC, Map<String, Object> constraintSpec, NamesKey parentNamesKey, NamedElement parent, Map<NamesKey, Object> allMetrics, Set<NamesKey> metricsUsed) {
        throw new RuntimeException("NOT YET IMPLEMENTED: decomposeConditionalConstraint");
    }

    private MetricVariableConstraint decomposeMetricVariableConstraint(TranslationContext _TC, Map<String, Object> constraintSpec, NamesKey parentNamesKey, NamedElement parent, Map<NamesKey, Object> allMetrics, Set<NamesKey> metricsUsed) {
        throw new RuntimeException("NOT YET IMPLEMENTED: decomposeMetricVariableConstraint");
    }

    // ------------------------------------------------------------------------
    //  Metric decomposition methods
    // ------------------------------------------------------------------------

    private MetricContext decomposeMetric(@NonNull TranslationContext _TC, Map<String, Object> metricSpec, NamesKey parentNamesKey, NamedElement parent, Map<NamesKey, Object> allMetrics, Set<NamesKey> metricsUsed) {
        // Get needed fields
        String metricName = getSpecName(metricSpec).toLowerCase();
        String metricType = getSpecField(metricSpec, "type");

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

        // Delegate decomposition based on metric type
        return switch (metricType) {
            case "raw" ->
                    decomposeRawMetric(_TC, metricSpec, parentNamesKey, parent, allMetrics, metricsUsed);
            case "composite" ->
                    decomposeCompositeMetric(_TC, metricSpec, parentNamesKey, parent, allMetrics, metricsUsed);
            case "constant" ->
                    processConstant(_TC, metricSpec, parentNamesKey, parent, allMetrics, metricsUsed);
            case "ref" ->
                    processRef(_TC, metricSpec, parentNamesKey, parent, allMetrics, metricsUsed);
            default ->
                    throw createException("Unknown metric type '" + metricType + "' in metric '" + metricName + "': " + metricSpec);
        };
    }

    private RawMetricContext decomposeRawMetric(TranslationContext _TC, Map<String, Object> metricSpec, NamesKey parentNamesKey, NamedElement parent, Map<NamesKey, Object> allMetrics, Set<NamesKey> metricsUsed) {
        // Get needed fields
        String metricName = getSpecName(metricSpec);
        Map<String, Object> sensorSpec = asMap(metricSpec.get("sensor"));
        Map<String, Object> outputSpec = asMap(metricSpec.get("output"));

        NamesKey metricNamesKey = getNamesKey(parentNamesKey, metricName);

        // Update TC
        RawMetricContext rawMetric = RawMetricContext.builder()
                .name(metricNamesKey.name())
                .object(metricSpec)
                .build();
        _TC.getDAG().addNode(parent, rawMetric);

        // Process child blocks
        Sensor sensor = processSensor(_TC, sensorSpec, metricNamesKey, rawMetric);
        Schedule schedule = processSchedule(outputSpec, metricNamesKey);

        // Complete TC update
        rawMetric.setSensor(sensor);
        rawMetric.setSchedule(schedule);

        return rawMetric;
    }

    private CompositeMetricContext decomposeCompositeMetric(TranslationContext _TC, Map<String, Object> metricSpec, NamesKey parentNamesKey, NamedElement parent, Map<NamesKey, Object> allMetrics, Set<NamesKey> metricsUsed) {
        return CompositeMetricContext.builder().name("xxxx").build();
    }

    private MetricContext processRef(TranslationContext _TC, Map<String, Object> metricSpec, NamesKey parentNamesKey, NamedElement parent, Map<NamesKey, Object> allMetrics, Set<NamesKey> metricsUsed) {
        return CompositeMetricContext.builder().name("xxxx").build();
    }

    private MetricContext processConstant(TranslationContext _TC, Map<String, Object> metricSpec, NamesKey parentNamesKey, NamedElement parent, Map<NamesKey, Object> allMetrics, Set<NamesKey> metricsUsed) {
        return RawMetricContext.builder().name("xxxx").build();
    }


    // ------------------------------------------------------------------------
    //  Other model elements processing methods
    // ------------------------------------------------------------------------

    private static final String DEFAULT_SENSOR_NAME_SUFFIX = "_SENSOR";
    private static final String DEFAULT_SENSOR_TYPE = "netdata";

    private Sensor processSensor(TranslationContext _TC, Map<String, Object> sensorSpec, NamesKey parentNamesKey, NamedElement parent) {
        // Get needed fields
        String sensorName = getSpecName(sensorSpec);
        String sensorType = getSpecField(sensorSpec, "type");
        boolean isPush = getBooleanValue(getSpecField(sensorSpec, "push"), false);
        boolean isPull = getBooleanValue(getSpecField(sensorSpec, "pull"), false);
        if (StringUtils.isBlank(sensorName)) sensorName = parentNamesKey.child + DEFAULT_SENSOR_NAME_SUFFIX;
        if (StringUtils.isBlank(sensorType)) sensorType = DEFAULT_SENSOR_TYPE;

        NamesKey sensorNamesKey = getNamesKey(parentNamesKey, sensorName);

        // Checks
        if (isPush && isPull)
            throw createException("Sensor cannot be both 'push' and 'pull': sensor '" + sensorName + "' in metric '" + parentNamesKey + "': " + sensorSpec);

        // Update TC
        Sensor sensor = Sensor.builder()
                .name(sensorNamesKey.name())
                .object(sensorSpec)
                .isPush(isPush)
                .build();
        _TC.getDAG().addNode(parent, sensor);

        //XXX:TODO: ... add Monitor, add Component-Sensor pair

        return sensor;
    }

    private Schedule processSchedule(Map<String, Object> scheduleSpec, NamesKey metricNamesKey) {
        // Get needed fields
        String type = getSpecField(scheduleSpec, "type");
        if (StringUtils.isBlank(type)) type = "all";

        Map<String, Object> schedMap = asMap(scheduleSpec.get("schedule"));

        Object valueObj = schedMap.get("value");
        if (!(valueObj instanceof Number))
            throw createException("Schedule value is mandatory and MUST be a positive integer: at metric '" + metricNamesKey + "': " + scheduleSpec);
        long interval = (Long) valueObj;
        if (interval<=0)
            throw createException("Schedule value cannot be zero or negative: at metric '" + metricNamesKey + "': " + scheduleSpec);

        String unitStr = getSpecField(schedMap, "unit");
        ChronoUnit unit = StringUtils.isNotBlank(unitStr)
                ? ChronoUnit.valueOf(unitStr.trim()) : ChronoUnit.SECONDS;

        return Schedule.builder()
                .object(scheduleSpec)
                .interval(interval)
                .timeUnit(unit.toString())
                .build();
    }

    // ------------------------------------------------------------------------
    //  Helper methods
    // ------------------------------------------------------------------------

    private RuntimeException createException(String s) {
        log.error("Parse error: {}", s);
        return new RuntimeException(new ModelException(s));
    }

    private RuntimeException createException(String s, Throwable t) {
        log.error("Parse error: {}: ", s, t);
        return new RuntimeException(s, t);
    }

    private List<Object> asList(Object o) {
        if (o==null) return null;
        if (o instanceof List l) return l;
        throw createException("Object is not a List: "+o);
    }

    private Map<String, Object> asMap(Object o) {
        if (o==null) return null;
        if (o instanceof Map m) return m;
        throw createException("Object is not a Map: "+o);
    }

    private NamesKey getNamesKey(@NonNull NamesKey parentNamesKey, @NonNull String name) {
        return (NamesKey.isFullName(name))
                ? NamesKey.create(name) : NamesKey.create(parentNamesKey.parent, name);
    }

    private String getSpecField(Object o, String field) {
        return getSpecField(o, field, "Block '%s' is not String: ");
    }

    private String getSpecField(Object o, String field, String exceptionMessage) {
        try {
            Map<String, Object> spec = asMap(o);
            Object oValue = spec.get(field);
            if (oValue == null)
                return null;
            if (oValue instanceof String s) {
                s = s.trim();
                return s;
            }
            throw createException(exceptionMessage.formatted(field) + spec);
        } catch (Exception e) {
            throw createException(exceptionMessage.formatted(field), e);
        }
    }

    private String getMandatorySpecField(Object o, String field, String exceptionMessage) {
        String val = getSpecField(o, field, exceptionMessage);
        if (val==null)
            throw createException(exceptionMessage.formatted(field) + o);
        return val;
    }

    private String getSpecName(Object o) {
        return getSpecField(o, "name");
    }

    private Double getSpecNumber(Object o, String field) {
        try {
            Map<String, Object> spec = asMap(o);
            Object oValue = spec.get(field);
            if (oValue == null) return null;
            if (oValue instanceof Number n) {
                return n.doubleValue();
            }
            throw createException("Block '"+field+"' is not Number: " + spec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Double getSpecNumber(Object o, String field, String exceptionMessage) {
        Double val = getSpecNumber(o, field);
        if (val==null)
            throw createException(exceptionMessage+o);
        return val;
    }

    private boolean getBooleanValue(String val, boolean defaultValue) {
        if (StringUtils.isBlank(val)) return defaultValue;
        return "true".equalsIgnoreCase(val.trim());
    }

    // ------------------------------------------------------------------------
    //  More Helper methods
    // ------------------------------------------------------------------------

    private List<Object> getSLOs(Object o) {
        return getRequirements(o).stream().filter(Objects::nonNull).filter(reqSpec -> {
            try {
                Map<String, Object> spec = asMap(reqSpec);
                Object oType = spec.get("type");
                if (oType == null)
                    throw createException("Block does not contain 'type' field: " + spec);
                //return true;
                if (oType instanceof String s) {
                    s = s.trim();
                    return "slo".equals(s);
                }
                throw createException("Block 'type' is not String: " + spec);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    private List<Object> getRequirements(Object o) {
        try {
            Map<String, Object> spec = asMap(o);
            Object oMetrics = spec.get("requirements");
            if (oMetrics == null)
                return Collections.emptyList();
            if (oMetrics instanceof List l) {
                return l;
            }
            throw createException("Block 'requirements' is not List: " + spec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Object> getMetrics(Object o) {
        try {
            Map<String, Object> spec = asMap(o);
            Object oMetrics = spec.get("metrics");
            if (oMetrics == null)
                return Collections.emptyList();
            if (oMetrics instanceof List l) {
                return l;
            }
            throw createException("Block 'metrics' is not List: " + spec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ------------------------------------------------------------------------
    //  Even more helper methods
    // ------------------------------------------------------------------------

    private boolean isExpression(String s) {
        return ! isConstant(s);
    }

    private boolean isConstant(String s) {
        return //constants.contains(s.trim()) ||
                isDouble(s);
    }

    private boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean isComparisonOperator(String s) {
        //XXX:TODO: Improve!!!!
        return Arrays.asList("<", "<=", "=<", ">", ">=", "=<", "=", "<>", "!=").contains(s);
    }

}