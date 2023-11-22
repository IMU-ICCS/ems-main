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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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

    // ================================================================================================================
    // Model analysis methods

    public void analyzeModel(@NonNull TranslationContext _TC, @NonNull Object metricModel) {
        log.debug("MetricModelAnalyzer.analyzeModel(): Analyzing metric model: {}", metricModel);

        // Initialize jsonpath context
        Configuration jsonpathConfig = Configuration.defaultConfiguration();
        ParseContext parseContext = JsonPath.using(jsonpathConfig);
        DocumentContext ctx = parseContext.parse(metricModel);

        // -- Expand shorthand expressions ------------------------------------

        // ----- Expand SLO constraints -----
        List<Object> expandedConstraints =
                ctx.read("$.spec.*.*.requirements.*[?(@.constraint!=null)]", List.class).stream()
                        .filter(item -> JsonPath.read(item, "$.constraint") instanceof String)
                        .peek(item -> expandConstraint(item))
                        .toList();
        log.debug("Constraints expanded: {}", expandedConstraints);

        // -- Schematron Validation -------------------------------------------
//		validateWithSchematron(yamlObj);

        // -- Model processing ------------------------------------------------

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
            throwRuntimeException("Naming conflicts exist for: "+duplicateNames);
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
                throwRuntimeException("Scope component(s) "+notFound+" have not been specified in scope: "+sloName);
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
            if (StringUtils.isBlank(parentName)) throwRuntimeException("Component or Scope with no name: " + spec);

            List<Object> metrics = JsonPath.read(spec, "$[?(@.metrics!=null)].metrics.*");
            metrics.stream().filter(Objects::nonNull).forEach(metricSpec -> {
                String metricName = getSpecName(metricSpec);
                if (StringUtils.isBlank(metricName)) throwRuntimeException("Metric spec with no name: " + metricSpec);
                allMetrics.put(NamesKey.create(parentName, metricName), metricSpec);
            });

            List<Object> slos = JsonPath.read(spec,
                    "$[?(@.requirements!=null && @.requirements.*[?(@.type=='slo')])].requirements.*");
            slos.stream().filter(Objects::nonNull).forEach(sloSpec -> {
                String sloName = getSpecName(sloSpec);
                if (StringUtils.isBlank(sloName)) throwRuntimeException("SLO spec with no name: " + sloSpec);
                allSLOs.put(NamesKey.create(parentName, sloName), sloSpec);
            });
        });
        log.warn("All Metrics: {}", allMetrics);
        log.warn("   All SLOs: {}", allSLOs);

        // ----- Decompose each SLO to its metric hierarchy -----
        Set<NamesKey> metricsUsed = new LinkedHashSet<>();
        allSLOs.forEach((sloNamesKey, sloSpec) -> {
            try {
                Object oConstr = ((Map)sloSpec).get("constraint");
                if (oConstr==null)
                    throwException("SLO without 'constraint': "+sloSpec);
                if (oConstr instanceof Map constrSpec) {
                    decomposeConstraint(constrSpec, sloNamesKey, allMetrics, metricsUsed);
                } else
                    throwException("SLO constraint is not Map: "+sloSpec);
            } catch (ModelException e) {
                throw new RuntimeException(e);
            }
        });

        // ----- Infer metric levels -----

        // ----- Build each component's SLO set (including those in the scopes it participates) -----

        // -- Updating Translation Context ------------------------------------



        // ----------------------------------------------------------

        log.debug("MetricModelAnalyzer.analyzeModel(): Analyzing Camel model completed: {}", metricModel);
    }

    private final static Pattern METRIC_CONSTRAINT_PATTERN = Pattern.compile("^([^<>=!]+)([<>]=|=[<>]|<>|!=|[=><])(.+)$");

    private void expandConstraint(Object spec) {
        String constraintStr = JsonPath.read(spec, "$.constraint").toString().trim();
        Matcher matcher = METRIC_CONSTRAINT_PATTERN.matcher(constraintStr);
        if (matcher.matches()) {
            String g1 = matcher.group(1);
            String g2 = matcher.group(2);
            String g3 = matcher.group(3);

            if (! isComparisonOperator(g2))
                throwRuntimeException("Invalid metric constraint shorthand expression in Requirement [Group 2 not a comparison operator]: "+spec);

            // Swap operands
            if (isDouble(g1)) {
                String tmp = g1;
                g1 = g3;
                g3 = tmp;
            }

            if (StringUtils.isBlank(g1) || StringUtils.isBlank(g3))
                throwRuntimeException("Invalid metric constraint shorthand expression in Requirement [Group 1 or 3 is blank]: "+spec);

            String metricName = g1.trim();
            double threshold = Double.parseDouble(g3.trim());

            Map<String, Object> constrMap = Map.of(
                    "type", "metric",
                    "metric", metricName,
                    "operator", g2.trim(),
                    "threshold", threshold
            );

            ((Map) spec).put("constraint", constrMap);
        } else
            throwRuntimeException("Invalid metric constraint shorthand expression: "+spec);
    }

    private ModelException createException(String s) {
        log.error("Parse error: {}", s);
        return new ModelException(s);
    }

    private void throwException(String s) throws ModelException {
        throw createException(s);
    }

    private void throwRuntimeException(String s) {
        throw new RuntimeException(createException(s));
    }

    private Map<String, Object> asMap(Object o) throws ModelException {
        if (o==null) return null;
        if (o instanceof Map m) return m;
        throw createException("Object is not a Map: "+o);
    }

    private String getSpecField(Object o, String field) {
        try {
            Map<String, Object> spec = asMap(o);
            Object oValue = spec.get(field);
            if (oValue == null) return null;
            if (oValue instanceof String s) {
                s = s.trim();
                return s;
            }
            throw createException("Block '"+field+"' is not String: " + spec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getSpecField(Object o, String field, String exceptionMessage) throws ModelException {
        String val = getSpecField(o, field);
        if (val==null)
            throw createException(exceptionMessage+o);
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

    private Double getSpecNumber(Object o, String field, String exceptionMessage) throws ModelException {
        Double val = getSpecNumber(o, field);
        if (val==null)
            throw createException(exceptionMessage+o);
        return val;
    }

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

    private void decomposeConstraint(Map<String, Object> constraintSpec, NamesKey sloNamesKey, Map<NamesKey, Object> allMetrics, Set<NamesKey> metricsUsed) throws ModelException {
        String type = getSpecField(constraintSpec, "type");
        if (StringUtils.isBlank(type))
            throwException("Constraint without 'type': "+constraintSpec);
        if ("metric".equals(type)) {
            // Get needed fields
            String metricName = getSpecField(constraintSpec, "metric", "Metric Constraint without 'metric': ");
            String comparisonOperator = getSpecField(constraintSpec, "operator", "Metric Constraint without 'metric': ");
            Double threshold = getSpecNumber(constraintSpec, "threshold", "Metric Constraint without 'threshold': ");

            // Further field checks
            NamesKey metricNamesKey = (NamesKey.isFullName(metricName))
                    ? NamesKey.create(metricName) : NamesKey.create(sloNamesKey.parent, metricName);
            if (! allMetrics.containsKey(metricNamesKey))
                throw createException("Unspecified metric '"+metricNamesKey+"' found in metric constraint: "+constraintSpec);

            if (! isComparisonOperator(comparisonOperator))
                throw createException("Unknown comparison operator '"+comparisonOperator+"' in metric constraint: "+constraintSpec);

            // Update TC

            // Decompose metric
            metricsUsed.add(metricNamesKey);
            decomposeMetric( asMap(allMetrics.get(metricNamesKey)), allMetrics, metricsUsed );

        } else
            throwException("Constraint 'type' not supported: "+constraintSpec);
    }

    private void decomposeMetric(Map<String, Object> metricSpec, Map<NamesKey, Object> allMetrics, Set<NamesKey> metricsUsed) {

    }


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

    // ========================================================================
    //  JSON path
    // ========================================================================

    // ========================================================================
    //  Schematron validation
    // ========================================================================

/*    public void validateWithSchematron(Object yamlObj) throws Exception {
        // ----------------------------------------------------------
        // Convert Map to XML

        XmlMapper mapper = new XmlMapper();
        mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        String xmlStr = mapper
                .writerWithDefaultPrettyPrinter()
                .withRootName("metricModel")
                .writeValueAsString(yamlObj);
//		log.warn(">>>>>>  XML: {}", xmlStr);

        // ----------------------------------------------------------
        // Schematron tests

        String schematronFile = "json-path/metric-model-schematron.xml";
        SchematronResourcePure schRes = SchematronResourcePure//.fromFile(schematronFile);
                .fromInputStream( schematronFile, Files.newInputStream(Paths.get(schematronFile)) );
        if (!schRes.isValidSchematron())
            throw new IllegalArgumentException ("Invalid Metric Model Schematron: "+schematronFile);

        // Check metric model validity
		*//*EValidity validity = schRes.getSchematronValidity(new StringStreamSource(xmlStr));
		boolean isMetricModelValid = validity.isValid();
		log.warn(">>>>>>>>>>>  SCHEM:  Metric model is valid: {}", isMetricModelValid);
		log.warn(">>>>>>>>>>>  SCHEM:  Validity: {}", validity);*//*

        // Validate metric model and get failed asserts
        SchematronOutputType schOutput = schRes.applySchematronValidationToSVRL(new StringStreamSource(xmlStr));
        List<Object> failedAsserts = schOutput.getActivePatternAndFiredRuleAndFailedAssert();
        int failedAssertsCount = 0;
        for (Object object : failedAsserts) {
            if (object instanceof FailedAssert failedAssert) {
                failedAssertsCount++;
                log.warn(">>>>>>>>>>>  SCHEM:    FAILED-ASSERT: {} -- {}",
                        failedAssert.getId(), //failedAssert.getTest(),
                        getSchematronDiagnosticMessages( failedAssert.getDiagnosticReferenceOrPropertyReferenceOrText() )
                );
            }
        }
        boolean isMetricModelValid = failedAssertsCount > 0;
        log.warn(">>>>>>>>>>>  SCHEM:  Num. of asserts failed: {}", failedAssertsCount);
        log.warn(">>>>>>>>>>>  SCHEM:  Metric model is valid: {}", isMetricModelValid);

        if (! isMetricModelValid)
            throw new IllegalArgumentException ("Invalid Metric Model: "+schematronFile);
    }

    public List getSchematronDiagnosticMessages(Object o) {
        if (o instanceof String s) {
            s = s.replaceAll("[ \t\r\n]+", " ").trim();
            log.trace("----> STRING: {}", s);
            return Collections.singletonList(s);
        } else if (o instanceof Text t) {
            log.trace("----> TEXT: {} = {}", t.getContentCount(), t.getContent().size());
            return t.getContent().stream().map(this::getSchematronDiagnosticMessages).flatMap(List::stream).toList();
        } else if (o instanceof Collection c) {
            log.trace("----> COLLECTION: {}", c.size());
            return c.stream().flatMap(xx -> getSchematronDiagnosticMessages(xx).stream()).toList();
        } else {
            log.trace("----> OTHER: {} {}", o.getClass(), o);
            return Collections.singletonList(o.toString());
        }
    }*/
}