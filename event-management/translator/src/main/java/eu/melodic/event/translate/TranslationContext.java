/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.translate;

import camel.constraint.ComparisonOperatorType;
import camel.constraint.Constraint;
import camel.constraint.UnaryConstraint;
import camel.core.Action;
import camel.core.NamedElement;
import camel.data.Data;
import camel.deployment.Component;
import camel.metric.*;
import camel.requirement.ServiceLevelObjective;
import camel.scalability.Event;
import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.melodic.event.translate.analyze.DAG;
import eu.melodic.event.translate.analyze.DAGNode;
import eu.melodic.event.util.FunctionDefinition;
import eu.melodic.models.interfaces.ems.Monitor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@ToString
@Slf4j
public class TranslationContext {
    // Decomposition DAG
    @JsonIgnore
    public final DAG DAG;

    // Event-to-Action map
    public final Map<String, Set<String>> E2A;

    // SLO set
    public final Set<String> SLO;

    // Component-to-Sensor map
    public final Map<Component, Set<TranslationContext.Sensor>> C2S;        //XXX:TODO-LOW: Convert to strings

    // Data-to-Sensor map
    public final Map<Data, Set<TranslationContext.Sensor>> D2S;             //XXX:TODO-LOW: Convert to strings

    // Sensor Monitors set
    public final Set<Monitor> MON;                        //XXX:TODO-LOW: Remove ??
    public final Set<String> MONS;

    // Grouping-to-EPL Rule map
    public final Map<String, Map<String, Set<String>>> G2R;

    // Grouping-to-Topics map
    public final Map<String, Set<String>> G2T;
    // Metric-to-Metric Context map
    public final Map<Metric, Set<camel.metric.MetricContext>> M2MC;
    // Composite Metric Variables set
    public final Set<String> CMVAR;
    public final Set<MetricVariable> CMVAR_1;
    // Metric Variable Values set (i.e. non-composite metric variable)
    public final Set<String> MVV;
    public final Map<String,String> MVV_CP;
    // Function set
    public final Set<FunctionDefinition> FUNC;
    // Element-to-Full-Name cache, pattern and count
    private transient final Map<NamedElement, String> E2N;              //XXX:TODO-LOW: Clear after translation
    private transient final AtomicLong elementsCount;
    // Topics-Connections-per-Grouping
    protected Map<String, String> providedTopics;                       // topic-grouping where this topic is provided
    protected Map<String, Set<String>> requiredTopics;                  // topic-set of groupings where this topic is required
    protected Map<String, Map<String, Set<String>>> topicConnections;   // grouping-provided topic in grouping-groupings that require provided topic
    protected boolean needsRefresh;
    private transient String fullNamePattern;                           // all options: {TYPE}, {CAMEL}, {MODEL}, {ELEM}, {HASH}, {COUNT}

    // Metric Constraints
    protected Set<MetricConstraint> metricConstraints;
    // Logical Constraints
    protected Set<LogicalConstraint> logicalConstraints;
    // If-Then-Else Constraints
    protected Set<IfThenConstraint> ifThenConstraints;

    // Load-annotated Metric
    protected Set<String> loadAnnotatedMetricsSet;


    // ====================================================================================================================================================
    // Constructors

    public TranslationContext() {
        this(true);
    }

    public TranslationContext(boolean initializeDag) {
        // Public staff
        this.DAG = initializeDag ? new DAG(this) : new DAG();
        this.E2A = new HashMap<>();
        this.SLO = new HashSet<>();
        this.C2S = new HashMap<>();
        this.D2S = new HashMap<>();
        this.MON = new HashSet<>();
        this.MONS = new HashSet<>();
        this.G2R = new HashMap<>();
        this.G2T = new HashMap<>();

        this.M2MC = new HashMap<>();
        this.CMVAR = new HashSet<>();
        this.CMVAR_1 = new HashSet<>();
        this.MVV = new HashSet<>();
        this.MVV_CP = new HashMap<>();
        this.FUNC = new HashSet<>();

        // Topics-Connections-per-Grouping staff
        this.providedTopics = new HashMap<>();
        this.requiredTopics = new HashMap<>();
        this.topicConnections = new HashMap<>();
        this.needsRefresh = false;

        // Element-to-Full-Name staff
        this.E2N = new HashMap<>();
        this.elementsCount = new AtomicLong(0);
        this.fullNamePattern = "{ELEM}";

        // Metric Constraints
        this.metricConstraints = new HashSet<>();
        // Logical Constraints
        this.logicalConstraints = new HashSet<>();
        // If-Then-Else Constraints
        this.ifThenConstraints = new HashSet<>();

        // Load-annotated Metric
        this.loadAnnotatedMetricsSet = new HashSet<>();
    }

    // ====================================================================================================================================================
    // Copy/Getter methods

    public Map<String, Set<String>> getG2T() {
        HashMap<String, Set<String>> newMap = new HashMap<>();
        G2T.forEach((key, value) -> newMap.put(key, new HashSet<>(value)));
        return newMap;
    }

    public Map<String, Map<String, Set<String>>> getG2R() {
        Map<String, Map<String, Set<String>>> newGroupingsMap = new HashMap<>();    // groupings
        G2R.forEach((key, value) -> {
            Map<String, Set<String>> newTopicsMap = new HashMap<>();            // topics per grouping
            newGroupingsMap.put(key, newTopicsMap);
            value.forEach((key1, value1) -> {
                Set<String> newRuleSet = new HashSet<>();                    // rules per topic per grouping
                newTopicsMap.put(key1, newRuleSet);
                newRuleSet.addAll(value1);
            });
        });
        return newGroupingsMap;
    }

    public camel.metric.MetricContext getMetricContextForMetric(Metric m) {
        Set<camel.metric.MetricContext> set = M2MC.get(m);
        return set == null ? null : set.iterator().next();
    }

    public boolean isMVV(String name) {
        for (String mvv : MVV) if (mvv.equals(name)) return true;
        return false;
    }

    public Set<TranslationContext.MetricConstraint> getMetricConstraints() {
        return new HashSet<>(metricConstraints);
    }

    public Set<TranslationContext.LogicalConstraint> getLogicalConstraints() {
        return new HashSet<>(logicalConstraints);
    }

    public Set<TranslationContext.IfThenConstraint> getIfThenConstraints() {
        return new HashSet<>(ifThenConstraints);
    }

    public Set<String> getMVVs() { return new HashSet<>(MVV); }

    public Set<String> getCompositeMetricVariables() { return new HashSet<>(CMVAR); }

    // ====================================================================================================================================================
    // Map- and Set-related helper methods

    protected void _addPair(Map map, Object key, Object value) {
        Set valueSet = (Set) map.get(key);
        if (valueSet == null) {
            valueSet = new HashSet<>();
            map.put(key, valueSet);
        }
        if (value instanceof List) valueSet.addAll((List) value);
        else valueSet.add(value);
    }

    public void addEventActionPair(Event event, Action action) {
        _addPair(E2A, E2N.get(event), E2N.get(action));
    }

    public void addEventActionPairs(Event event, List<Action> actions) {
        _addPair(E2A, E2N.get(event), actions.stream().map(E2N::get).collect(Collectors.toList()));
    }

    public void addSLO(ServiceLevelObjective slo) {
        if (E2N.get(slo)!=null) SLO.add(E2N.get(slo));
        else SLO.add(slo.getName());
    }

    public void addComponentSensorPair(ObjectContext objContext, camel.metric.Sensor sensor) {
        TranslationContext.Sensor tcSensor = new TranslationContext.Sensor(sensor);
        if (objContext != null) {
            Component comp = objContext.getComponent();
            Data data = objContext.getData();
            if (comp != null) _addPair(C2S, comp, tcSensor);
            if (data != null) _addPair(D2S, data, tcSensor);
        } else {
            _addPair(C2S, null, tcSensor);
        }
    }

    public void addMonitorsForSensor(String sensorName, Set<Monitor> monitors) {
        if (monitors != null) {
            if (!MONS.contains(sensorName)) {
                MON.addAll(monitors);
                MONS.add(sensorName);
            }
        }
    }

    public boolean containsMonitorsForSensor(String sensorName) {
        return MONS.contains(sensorName);
    }

    public Set<Monitor> getMonitors() {
        return Collections.unmodifiableSet(MON);
    }

    public void addGroupingTopicPair(String grouping, String topic) {
        _addPair(G2T, grouping, topic);
    }

    public void addGroupingTopicPairs(String grouping, List<String> topics) {
        _addPair(G2T, grouping, topics);
    }

    public void addGroupingRulePair(String grouping, String topic, String rule) {
        Map<String, Set<String>> topics = G2R.computeIfAbsent(grouping, k -> new HashMap<>());
        Set<String> rules = topics.computeIfAbsent(topic, k -> new HashSet<>());
        rules.add(rule);
    }

    public void addGroupingRulePairs(String grouping, String topic, List<String> rules) {
        rules.forEach(rule -> addGroupingRulePair(grouping, topic, rule));
    }

    public void addMetricMetricContextPair(Metric m, camel.metric.MetricContext mc) {
        _addPair(M2MC, m, mc);
    }

    public void addMetricMetricContextPairs(Metric m, List<camel.metric.MetricContext> mcs) {
        _addPair(M2MC, m, mcs);
    }

    public void addCompositeMetricVariable(MetricVariable mv) {
        CMVAR.add(mv.getName());
        CMVAR_1.add(mv);
    }

    public void addCompositeMetricVariables(List<MetricVariable> mvs) {
        mvs.forEach(this::addCompositeMetricVariable);
    }

    public void addMVV(MetricVariable mvv) {
        MVV.add(mvv.getName());
    }

    public void addMVVs(List<MetricVariable> mvvs) {
        mvvs.forEach(this::addMVV);
    }

    public void addFunction(Function f) {
        FunctionDefinition fdef = new FunctionDefinition().setName(f.getName()).setExpression(f.getExpression()).setArguments(f.getArguments());
        FUNC.add(fdef);
    }

    public void addMetricConstraint(UnaryConstraint uc) {
        // Get comparison operator
        String opName = uc.getComparisonOperator().getName();
        String op = null;
        if (StringUtils.isBlank(opName)) throw new IllegalArgumentException("Metric Constraint '"+uc.getName()+"' has no operator specified");
        else if (ComparisonOperatorType.EQUAL.getName().equalsIgnoreCase(opName)) op = "=";
        else if (ComparisonOperatorType.NOT_EQUAL.getName().equalsIgnoreCase(opName)) op = "<>";
        else if (ComparisonOperatorType.LESS_THAN.getName().equalsIgnoreCase(opName)) op = "<";
        else if (ComparisonOperatorType.LESS_EQUAL_THAN.getName().equalsIgnoreCase(opName)) op = "<=";
        else if (ComparisonOperatorType.GREATER_THAN.getName().equalsIgnoreCase(opName)) op = ">";
        else if (ComparisonOperatorType.GREATER_EQUAL_THAN.getName().equalsIgnoreCase(opName)) op = ">=";
        else throw new IllegalArgumentException("Metric Constraint '"+uc.getName()+"' has an invalid operator: "+opName);

        // Get metric context/variable name
        String metricName = null;
        if (uc instanceof camel.constraint.MetricConstraint) {
            camel.constraint.MetricConstraint mc = (camel.constraint.MetricConstraint) uc;
            camel.metric.MetricContext context = mc.getMetricContext();
            if (context!=null) metricName = context.getName();
            if (StringUtils.isBlank(metricName))
                throw new IllegalArgumentException("Metric Constraint '"+uc.getName()+"' has no valid metric context");
        } else
        if (uc instanceof camel.constraint.MetricVariableConstraint) {
            camel.constraint.MetricVariableConstraint mvc = (camel.constraint.MetricVariableConstraint) uc;
            MetricVariable mv = mvc.getMetricVariable();
            if (mv!=null) metricName = mv.getName();
            if (StringUtils.isBlank(metricName))
                throw new IllegalArgumentException("Metric Variable Constraint '"+uc.getName()+"' has no valid metric variable");
        } else
            throw new IllegalArgumentException("Invalid Unary Constraint '"+uc.getName()+"' specified. Only metric constraints and metric variable constraints are allowed.");

        // Add threshold information
        metricConstraints.add(new MetricConstraint(uc.getName(), metricName, op, uc.getThreshold()));
    }

    public void addLogicalConstraint(camel.constraint.LogicalConstraint logicalConstraint, List<DAGNode> nodeList) {
        // Get logical operator
        String opName = logicalConstraint.getLogicalOperator().getName();
        if (StringUtils.isBlank(opName))
            throw new IllegalArgumentException("Logical Constraint '"+logicalConstraint.getName()+"' has no operator specified");

        // Get child constraints
        List<String> childConstraintNames = logicalConstraint.getConstraints()
                .stream().map(NamedElement::getName).collect(Collectors.toList());
        if (childConstraintNames.size()==0)
            throw new IllegalArgumentException("Logical Constraint '"+logicalConstraint.getName()+"' has no child constraints");

        // Add logical constraint information
        logicalConstraints.add(new LogicalConstraint(logicalConstraint.getName(), opName, childConstraintNames, nodeList));
    }

    public void addIfThenConstraint(camel.constraint.IfThenConstraint ifThenConstraint) {
        String name = ifThenConstraint.getName();

        // Get child constraints
        Constraint ifConstraint = ifThenConstraint.getIf();
        Constraint thenConstraint = ifThenConstraint.getThen();
        Constraint elseConstraint = ifThenConstraint.getElse();
        String ifConstraintName = ifConstraint.getName();
        String thenConstraintName = thenConstraint.getName();
        String elseConstraintName = elseConstraint != null ? elseConstraint.getName() : null;

        // Add logical constraint information
        ifThenConstraints.add(new IfThenConstraint(name, ifConstraintName, thenConstraintName, elseConstraintName));
    }

    // ====================================================================================================================================================
    // Topic-Connections-per-Grouping-related helper methods
    // Auto-fill of Topic connections between Groupings.... (use provide/require methods below)

    public void provideGroupingTopicPair(String grouping, String topic) {
        if (isMVV(topic)) return;
        addGroupingTopicPair(grouping, topic);
        String providerGrouping = providedTopics.get(grouping);
        if (providerGrouping != null && !providerGrouping.equals(grouping)) {
            throw new CamelToEplTranslationException("Topic " + topic + " is provided more than once: grouping-1=" + grouping + ", grouping-2=" + providedTopics.get(grouping));
        }
        providedTopics.put(topic, grouping);
        needsRefresh = true;
    }

    public void requireGroupingTopicPair(String grouping, String topic) {
        log.debug("requireGroupingTopicPair: grouping={}, topic={}", grouping, topic);
        if (isMVV(topic)) return;
        log.trace("requireGroupingTopicPair: Not an MVV. Good: grouping={}, topic={}", grouping, topic);
        log.trace("requireGroupingTopicPair: requiredTopics BEFORE: {}", requiredTopics);
        addGroupingTopicPair(grouping, topic);
        Set<String> groupings = requiredTopics.get(topic);
        if (groupings == null) requiredTopics.put(topic, groupings = new HashSet<>());
        groupings.add(grouping);
        needsRefresh = true;
        log.trace("requireGroupingTopicPair: requiredTopics AFTER: {}", requiredTopics);
    }

    public void requireGroupingTopicPairs(String grouping, List<String> topics) {
        topics.stream().forEach(t -> {
            requireGroupingTopicPair(grouping, t);
        });
    }

    public Map<String, Map<String, Set<String>>> getTopicConnections() {
        if (needsRefresh) {
            log.debug("TranslationContext.getTopicConnections(): Topic connections need refresh");
            topicConnections.clear();

            log.debug("TranslationContext.getTopicConnections(): required-topics={}, provided-topics={}", requiredTopics, providedTopics);

            // for every required topic...
            for (Map.Entry<String, Set<String>> pair : requiredTopics.entrySet()) {
                // get consumer topics for current required topic
                String requiredTopic = pair.getKey();
                Set<String> consumerGroupings = pair.getValue();
                // get provider grouping of current required topic
                String providerGrouping = providedTopics.get(requiredTopic);
                if (providerGrouping == null)
                    throw new CamelToEplTranslationException("Topic " + requiredTopic + " is not provided in any grouping");
                // remove provider grouping from consumer groupings
                consumerGroupings.remove(providerGrouping);
                // store required topic in 'topicConnections'
                if (consumerGroupings.size() > 0) {
                    // ...get provider grouping topics from topicConnections
                    Map<String, Set<String>> groupingTopics = topicConnections.get(providerGrouping);
                    if (groupingTopics == null)
                        topicConnections.put(providerGrouping, groupingTopics = new HashMap<String, Set<String>>());
                    // ...store consumer groupings for current required topic in provider grouping
                    if (groupingTopics.containsKey(requiredTopic))
                        throw new CamelToEplTranslationException("INTERNAL ERROR: Required Topic " + requiredTopic + " is already set in provider grouping " + providerGrouping + " in '_TC.topicConnections'");
                    groupingTopics.put(requiredTopic, consumerGroupings);
                }
            }

            needsRefresh = false;
            log.debug("TranslationContext.getTopicConnections(): Topic connections refreshed: {}", topicConnections);
        } else {
            log.debug("TranslationContext.getTopicConnections(): No need to refresh Topic connections. Returning from cache: {}", topicConnections);
        }
        return topicConnections;
    }

    public Map<String, Set<String>> getTopicConnectionsForGrouping(String grouping) {
        return getTopicConnections().get(grouping);
    }

    // ====================================================================================================================================================
    // Element full name generation methods

    public String getFullNamePattern() {
        return fullNamePattern;
    }

    public void setFullNamePattern(String pattern) {
        fullNamePattern = pattern;
    }

    public String getFullName(NamedElement elem) {
        log.trace("  getFullName: BEGIN: {}", elem);
        if (elem == null) return null;
        log.trace("  getFullName: NULL check OK: name={}", elem.getName());

        // return cached full-name for element
        String fullName = E2N.get(elem);
        log.trace("  getFullName: Cached Name: {}", fullName);
        if (fullName != null) return fullName;
        log.trace("  getFullName: NO Cached Name:...");

        // else generate full-name for element (and cache it)
        String elemName = elem.getName();
        log.trace("  getFullName:   elem-name={}", elemName);
        String elemType = _getElementType(elem);
        log.trace("  getFullName:   elem-type={}", elemType);
        log.trace("  getFullName:   elem-eContainer={}", elem.eContainer());
        String modelName = ((NamedElement) elem.eContainer()).getName();
        log.trace("  getFullName:   model-name={}", modelName);
        log.trace("  getFullName:   elem-eContainer-eContainer={}", elem.eContainer().eContainer());
        String camelName = ((NamedElement) elem.eContainer().eContainer()).getName();
        log.trace("  getFullName:   camel-name={}", camelName);

        fullName = fullNamePattern
                .replace("{TYPE}", elemType)
                .replace("{CAMEL}", camelName)
                .replace("{MODEL}", modelName)
                .replace("{ELEM}", elemName)
                .replace("{HASH}", Integer.toString(elemName.hashCode()))
                .replace("{COUNT}", Long.toString(elementsCount.getAndIncrement()))
        ;
        log.trace("  getFullName:   New Full name={}", fullName);

        E2N.put(elem, fullName);
        log.trace("  getFullName: END: Cached new FULL name: {}", fullName);

        return fullName;
    }

    public void addElementToNamePair(@NonNull NamedElement elem, @NonNull String fullName) {
        E2N.put(elem, fullName);
    }

    protected String _getElementType(NamedElement e) {
        Class c = e.getClass();
        if (false) ;
        else if (camel.scalability.ScalabilityRule.class.isAssignableFrom(c)) return "RUL";
        else if (camel.scalability.Event.class.isAssignableFrom(c)) return "EVT";
        else if (camel.constraint.Constraint.class.isAssignableFrom(c)) return "CON";
        else if (camel.metric.MetricVariable.class.isAssignableFrom(c)) return "VAR";
        else if (camel.metric.MetricContext.class.isAssignableFrom(c)) return "CTX";
        else if (camel.metric.Metric.class.isAssignableFrom(c)) return "MET";
        else if (camel.metric.MetricTemplate.class.isAssignableFrom(c)) return "TMP";
        else if (camel.requirement.OptimisationRequirement.class.isAssignableFrom(c)) return "OPT";
        else if (camel.requirement.ServiceLevelObjective.class.isAssignableFrom(c)) return "SLO";
        else if (camel.requirement.Requirement.class.isAssignableFrom(c)) return "REQ";
        else if (camel.metric.ObjectContext.class.isAssignableFrom(c)) return "OBJ";
        else if (camel.metric.Sensor.class.isAssignableFrom(c)) return "SNR";
        else if (camel.metric.Function.class.isAssignableFrom(c)) return "FUN";
        else if (camel.metric.Schedule.class.isAssignableFrom(c)) return "CTX";
        else if (camel.metric.Window.class.isAssignableFrom(c)) return "CTX";
        else if (camel.scalability.ScalingAction.class.isAssignableFrom(c)) return "ACT";
        else {
            //throw new ModelAnalysisException( String.format("Unknown element type: %s  class=%s", e.getName(), e.getClass().getName()) );
            log.error("Unknown element type: {}  class={}", e.getName(), e.getClass().getName());
        }
        return "XXX";
    }

    // ====================================================================================================================================================
    // Function-Definition-related helper methods

    public Set<FunctionDefinition> getFunctionDefinitions() {
        return new HashSet<>(FUNC);
    }

    // ====================================================================================================================================================
    // Load-Metrics-related helper methods

    public void addLoadAnnotatedMetric(@NonNull String metricName) {
        loadAnnotatedMetricsSet.add(metricName);
    }

    public void addLoadAnnotatedMetrics(@NonNull Set<String> metricNames) {
        loadAnnotatedMetricsSet.addAll(metricNames);
    }

    public Set<String> getLoadAnnotatedMetricsSet() {
        return new HashSet<>(loadAnnotatedMetricsSet);
    }

    // ====================================================================================================================================================
    // Metric and Logical Constraint helper class

    @lombok.Data
    public static class MetricConstraint {
        private final String name;
        private final String metric;
        private final String operator;
        private final double threshold;
    }

    @lombok.Data
    public static class LogicalConstraint {
        private final String name;
        private final String operator;
        private final List<String> constraints;
        private final List<DAGNode> constraintNodes;
    }

    @lombok.Data
    public static class IfThenConstraint {
        private final String name;
        private final String ifConstraintName;
        private final String thenConstraintName;
        private final String elseConstraintName;
    }

    @lombok.Data
    @RequiredArgsConstructor
    public static class MetricContext {
        private final String name;
        private final String component;
        private final Schedule schedule;

        public MetricContext(camel.metric.MetricContext mc) {
            name = mc.getName();
            component = mc.getObjectContext()!=null && mc.getObjectContext().getComponent()!=null
                    && StringUtils.isNotBlank(mc.getObjectContext().getComponent().getName())
                            ? mc.getObjectContext().getComponent().getName()
                            : null;
            schedule = (mc.getSchedule()!=null) ? new TranslationContext.Schedule(mc.getSchedule()) : null;
        }
    }

    @lombok.Data
    @RequiredArgsConstructor
    public static class Schedule {
        private final String name;
        private final String unit;
        private final long interval;
        private final int repetitions;
        private final Date start;
        private final Date end;

        public Schedule(camel.metric.Schedule s) {
            this(s.getName(), s.getTimeUnit().getName(), s.getInterval(), s.getRepetitions(), s.getStart(), s.getEnd());
        }

        public long getIntervalInMillis() {
            if (unit==null) return interval;
            return TimeUnit.MILLISECONDS.convert(interval, TimeUnit.valueOf(unit.toUpperCase()));
        }
    }

    @lombok.Data
    @RequiredArgsConstructor
    public static class Sensor {
        private final String name;
        private final String configuration;
        private final boolean isPush;

        public Sensor(camel.metric.Sensor sensor) {
            this.name = sensor.getName();
            this.configuration = sensor.getConfiguration();
            this.isPush = sensor.isIsPush();
        }
    }
}
