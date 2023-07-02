/*
 * Copyright (C) 2017-2023 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.translate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.melodic.event.translate.dag.DAG;
import eu.melodic.event.translate.dag.DAGNode;
import eu.melodic.event.util.FunctionDefinition;
import lombok.*;
import lombok.experimental.SuperBuilder;
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
    @Getter
    @JsonIgnore
    private final DAG DAG;

    // Event-to-Action map
    @Getter
    private final Map<String, Set<String>> E2A = new HashMap<>();

    // SLO set
    @Getter
    private final Set<String> SLO = new LinkedHashSet<>();

    // Component-to-Sensor map
    @Getter
    private final Map<TranslationContext.Component, Set<TranslationContext.Sensor>> C2S = new HashMap<>();        //XXX:TODO-LOW: Convert to strings

    // Data-to-Sensor map
    @Getter
    private final Map<TranslationContext.Data, Set<TranslationContext.Sensor>> D2S = new HashMap<>();             //XXX:TODO-LOW: Convert to strings

    // Sensor Monitors set
    @Getter
    private final Set<Monitor> MON = new LinkedHashSet<>();                        //XXX:TODO-LOW: Remove ??
    @Getter
    private final Set<String> MONS = new LinkedHashSet<>();

    // Grouping-to-EPL Rule map
    private final Map<String, Map<String, Set<String>>> G2R = new HashMap<>();

    // Grouping-to-Topics map
    private final Map<String, Set<String>> G2T = new HashMap<>();

    // Metric-to-Metric Context map
    @Getter
    private final Map<Metric, Set<MetricContext>> M2MC = new HashMap<>();

    // Composite Metric Variables set
    @Getter
    private final Set<String> CMVar = new LinkedHashSet<>();
    @Getter
    private final Set<MetricVariable> CMVar_1 = new LinkedHashSet<>();

    // Metric Variable Values set (i.e. non-composite metric variable)
    private final Set<String> MVV = new LinkedHashSet<>();
    private final Map<String,String> MvvCP = new HashMap<>();

    // Function set
    @Getter
    private final Set<FunctionDefinition> FUNC = new LinkedHashSet<>();

    // Topics-Connections-per-Grouping
    protected final Map<String, String> providedTopics = new HashMap<>();                       // topic-grouping where this topic is provided
    protected final Map<String, Set<String>> requiredTopics = new HashMap<>();                  // topic-set of groupings where this topic is required
    protected final Map<String, Map<String, Set<String>>> topicConnections = new HashMap<>();   // grouping-provided topic in grouping-groupings that require provided topic
    protected boolean needsRefresh;

    // Metric Constraints
    private final Set<MetricConstraint> metricConstraints = new LinkedHashSet<>();
    // Logical Constraints
    private final Set<LogicalConstraint> logicalConstraints = new LinkedHashSet<>();
    // If-Then-Else Constraints
    private final Set<IfThenConstraint> ifThenConstraints = new LinkedHashSet<>();

    // Load-annotated Metric
    protected final Set<String> loadAnnotatedMetricsSet = new LinkedHashSet<>();

    // Export files
    @Getter @Setter
    private List<String> exportFiles = new ArrayList<>();

    // Element-to-Full-Name cache, pattern and count
    protected transient final Map<NamedElement, String> E2N;              //XXX:TODO-LOW: Clear after translation
    protected transient final AtomicLong elementsCount;
    @Getter @Setter
    protected transient String fullNamePattern;                           // all options: {TYPE}, {--NO--CAMEL}, {--NO--MODEL}, {ELEM}, {HASH}, {COUNT}

    // ====================================================================================================================================================
    // Constructors

    public TranslationContext() {
        this(true);
    }

    public TranslationContext(boolean initializeDag) {
        // Public staff
        this.DAG = initializeDag ? new DAG(this) : new DAG();

        // Element-to-Full-Name staff
        this.E2N = new HashMap<>();
        this.elementsCount = new AtomicLong(0);
        this.fullNamePattern = "{ELEM}";
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

    public MetricContext getMetricContextForMetric(Metric m) {
        Set<MetricContext> set = M2MC.get(m);
        return set == null ? null : set.iterator().next();
    }

    public Set<TranslationContext.MetricConstraint> getMetricConstraints() {
        return new HashSet<>(metricConstraints);
    }

    public Set<TranslationContext.LogicalConstraint> getLogicalConstraints() {
        return new HashSet<>(logicalConstraints);
    }

    public boolean isMVV(String name) {
        for (String mvv : MVV) if (mvv.equals(name)) return true;
        return false;
    }

    public Set<String> getMVV() { return new HashSet<>(MVV); }

    public Map<String,String> getMvvCP() { return new HashMap<>(MvvCP); }

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

    public void addComponentSensorPair(ObjectContext objContext, Sensor sensor) {
        if (objContext != null) {
            Component comp = objContext.getComponent();
            Data data = objContext.getData();
            if (comp != null) _addPair(C2S, comp, sensor);
            if (data != null) _addPair(D2S, data, sensor);
        } else {
            _addPair(C2S, null, sensor);
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

    public void addMetricMetricContextPair(Metric m, MetricContext mc) {
        _addPair(M2MC, m, mc);
    }

    public void addMetricMetricContextPairs(Metric m, List<MetricContext> mcs) {
        _addPair(M2MC, m, mcs);
    }

    public void addCompositeMetricVariable(MetricVariable mv) {
        CMVar.add(mv.getName());
        CMVar_1.add(mv);
    }

    public void addCompositeMetricVariables(List<MetricVariable> mvs) {
        mvs.forEach(this::addCompositeMetricVariable);
    }

    public void addMVV(@NonNull String mvv) {
        MVV.add(mvv);
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
        ComparisonOperatorType op = uc.getComparisonOperator();
        if (op==null)
            throw new IllegalArgumentException("Metric Constraint '"+uc.getName()+"' has no operator specified");

        // Get metric context/variable name
        String metricName = null;
        if (uc instanceof MetricConstraint mc) {
            MetricContext context = mc.getMetricContext();
            if (context!=null) metricName = context.getName();
            if (StringUtils.isBlank(metricName))
                throw new IllegalArgumentException("Metric Constraint '"+mc.getName()+"' has no valid metric context");
        } else
        if (uc instanceof MetricVariableConstraint mvc) {
            MetricVariable mv = mvc.getMetricVariable();
            if (mv!=null) metricName = mv.getName();
            if (StringUtils.isBlank(metricName))
                throw new IllegalArgumentException("Metric Variable Constraint '"+uc.getName()+"' has no valid metric variable");
        } else
            throw new IllegalArgumentException("Invalid Unary Constraint '"+uc.getName()+"' specified. Only metric constraints and metric variable constraints are allowed.");

        // Add threshold information
        metricConstraints.add(
                MetricConstraint.builder()
                        .name(uc.getName())
                        .metric(metricName)
                        .comparisonOperator(op)
                        .threshold(uc.getThreshold())
                        .build()
        );
    }

    public void addLogicalConstraint(LogicalConstraint logicalConstraint, List<DAGNode> nodeList) {
        // Check there is a logical operator
        LogicalOperatorType op = logicalConstraint.getLogicalOperator();
        if (op==null)
            throw new IllegalArgumentException("Logical Constraint '"+logicalConstraint.getName()+"' has no operator specified");

        // Check there are child constraints
        List<String> childConstraintNames = logicalConstraint.getConstraints()
                .stream().map(NamedElement::getName).toList();
        if (childConstraintNames.size()==0)
            throw new IllegalArgumentException("Logical Constraint '"+logicalConstraint.getName()+"' has no child constraints");

        // Set node list
        logicalConstraint.setConstraintNodes(nodeList);

        // Add logical constraint information
        logicalConstraints.add(logicalConstraint);
    }

    public void addIfThenConstraint(@NonNull IfThenConstraint ifThenConstraint) {
        String name = ifThenConstraint.getName();

        // Check child constraints
        Constraint ifConstraint = ifThenConstraint.getIf();
        Constraint thenConstraint = ifThenConstraint.getThen();
        Constraint elseConstraint = ifThenConstraint.getElse();
        if (ifConstraint==null || thenConstraint==null)
            throw new IllegalArgumentException("If-Then-Else Constraint '"+ifConstraint.getName()+"' has no IF or no THEN constraint");
        String ifConstraintName = ifConstraint.getName();
        String thenConstraintName = thenConstraint.getName();
        if (StringUtils.isBlank(ifConstraintName) || StringUtils.isBlank(thenConstraintName))
            throw new IllegalArgumentException("IF or THEN constraint in If-Then-Else constraint'"+ifConstraint.getName()+"' has no name");
        String elseConstraintName = elseConstraint != null ? elseConstraint.getName() : null;
        if (elseConstraint!=null && StringUtils.isBlank(elseConstraintName))
            throw new IllegalArgumentException("ELSE constraint in If-Then-Else constraint'"+ifConstraint.getName()+"' has no name");

        // Add if-then-else constraint information
        ifThenConstraints.add(ifThenConstraint);
    }

    // ====================================================================================================================================================
    // Topic-Connections-per-Grouping-related helper methods
    // Auto-fill of Topic connections between Groupings.... (use provide/require methods below)

    public void provideGroupingTopicPair(String grouping, String topic) {
        if (isMVV(topic)) return;
        addGroupingTopicPair(grouping, topic);
        String providerGrouping = providedTopics.get(grouping);
        if (providerGrouping != null && !providerGrouping.equals(grouping)) {
            throw new IllegalArgumentException("Topic " + topic + " is provided more than once: grouping-1=" + grouping + ", grouping-2=" + providedTopics.get(grouping));
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
        Set<String> groupings = requiredTopics.computeIfAbsent(topic, k -> new HashSet<>());
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
                    throw new IllegalArgumentException("Topic " + requiredTopic + " is not provided in any grouping");
                // remove provider grouping from consumer groupings
                consumerGroupings.remove(providerGrouping);
                // store required topic in 'topicConnections'
                if (consumerGroupings.size() > 0) {
                    // ...get provider grouping topics from topicConnections
                    Map<String, Set<String>> groupingTopics = topicConnections.computeIfAbsent(providerGrouping, k -> new HashMap<>());
                    // ...store consumer groupings for current required topic in provider grouping
                    if (groupingTopics.containsKey(requiredTopic))
                        throw new IllegalArgumentException("INTERNAL ERROR: Required Topic " + requiredTopic + " is already set in provider grouping " + providerGrouping + " in '_TC.topicConnections'");
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
        //log.trace("  getFullName:   elem-eContainer={}", elem.eContainer());
        //String modelName = ((NamedElement) elem.eContainer()).getName();
        //log.trace("  getFullName:   model-name={}", modelName);
        //log.trace("  getFullName:   elem-eContainer-eContainer={}", elem.eContainer().eContainer());
        //String camelName = ((NamedElement) elem.eContainer().eContainer()).getName();
        //log.trace("  getFullName:   camel-name={}", camelName);

        fullName = fullNamePattern
                .replace("{TYPE}", elemType)
                //.replace("{CAMEL}", camelName)
                //.replace("{MODEL}", modelName)
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
        if (e==null) {
            log.error("Null element passed");
        }
        else if (e instanceof ScalabilityRule) return "RUL";
        else if (e instanceof Event) return "EVT";
        else if (e instanceof Constraint) return "CON";
        else if (e instanceof MetricVariable) return "VAR";
        else if (e instanceof MetricContext) return "CTX";
        else if (e instanceof Metric) return "MET";
        else if (e instanceof MetricTemplate) return "TMP";
        else if (e instanceof OptimisationRequirement) return "OPT";
        else if (e instanceof ServiceLevelObjective) return "SLO";
        else if (e instanceof Requirement) return "REQ";
        else if (e instanceof ObjectContext) return "OBJ";
        else if (e instanceof Sensor) return "SNR";
        else if (e instanceof Function) return "FUN";       //XXX:TODO:  Or FunctionDefinition ??
        else if (e instanceof Schedule) return "CTX";
        else if (e instanceof Window) return "CTX";
        else if (e instanceof ScalingAction) return "ACT";
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
    @SuperBuilder
    @NoArgsConstructor
    public static class NamedElement {
        protected String name;
        protected String description;
        protected List<Annotation> annotations = new ArrayList<>();
    }

    // ------------------------------------------------------------------------

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Constraint extends NamedElement {
    }

    @Getter
    @RequiredArgsConstructor
    public enum ComparisonOperatorType {
        GREATER_THAN("GREATER_THAN", ">"),
        GREATER_EQUAL_THAN("GREATER_EQUAL_THAN", ">="),
        LESS_THAN("LESS_THAN", "<"),
        LESS_EQUAL_THAN("LESS_EQUAL_THAN", "<="),
        EQUAL("EQUAL", "="),
        NOT_EQUAL("NOT_EQUAL", "<>");

        private final String name;
        private final String operator;
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class UnaryConstraint extends Constraint {
        private ComparisonOperatorType comparisonOperator;
        private double threshold;
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class MetricConstraint extends UnaryConstraint {
        private String metric;
        private MetricContext metricContext;
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class MetricVariableConstraint extends UnaryConstraint {
        private MetricVariable metricVariable;
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class CompositeConstraint extends Constraint {
    }

    public enum LogicalOperatorType { AND, OR, XOR }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class LogicalConstraint extends CompositeConstraint {
        private LogicalOperatorType logicalOperator;
        private List<Constraint> constraints;
        private List<DAGNode> constraintNodes;
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class IfThenConstraint extends CompositeConstraint {
        private Constraint ifConstraint;
        private Constraint thenConstraint;
        private Constraint elseConstraint;

        public Constraint getIf() { return ifConstraint; }
        public Constraint getThen() { return thenConstraint; }
        public Constraint getElse() { return elseConstraint; }
    }

    // ------------------------------------------------------------------------

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class MetricContext extends NamedElement {
        private String component;
        private Metric metric;
        private Schedule schedule;
        private ObjectContext objectContext;

        /*public MetricContext(camel.metric.MetricContext mc) {
            name = mc.getName();
            component = mc.getObjectContext()!=null && mc.getObjectContext().getComponent()!=null
                    && StringUtils.isNotBlank(mc.getObjectContext().getComponent().getName())
                            ? mc.getObjectContext().getComponent().getName()
                            : null;
            schedule = (mc.getSchedule()!=null) ? new TranslationContext.Schedule(mc.getSchedule()) : null;
        }*/
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Schedule extends NamedElement {
        private String unit;
        private long interval;
        private int repetitions;
        private Date start;
        private Date end;

        /*public Schedule(camel.metric.Schedule s) {
            this(s.getName(), s.getTimeUnit().getName(), s.getInterval(), s.getRepetitions(), s.getStart(), s.getEnd());
        }*/

        public long getIntervalInMillis() {
            if (unit==null) return interval;
            return TimeUnit.MILLISECONDS.convert(interval, TimeUnit.valueOf(unit.toUpperCase()));
        }
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Sensor extends Component {
        private String configurationStr;
        private boolean isPush;
        public Map<String,String> additionalProperties;

        public boolean isPullSensor() { return !isPush; }
        public boolean isPushSensor() { return isPush; }

        public PullSensor getPullSensor() {
            if (this instanceof PullSensor)
                return (PullSensor) this;
            throw new IllegalArgumentException("Not a Pull sensor: "+this.getName());
        }

        public PushSensor getPushSensor() {
            if (this instanceof PushSensor)
                return (PushSensor) this;
            throw new IllegalArgumentException("Not a Push sensor: "+this.getName());
        }

        /*public Sensor(camel.metric.Sensor sensor) {
            this.name = sensor.getName();
            this.configuration = sensor.getConfiguration();
            this.isPush = sensor.isIsPush();
        }*/
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class PushSensor extends Sensor {
        private int port;
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class PullSensor extends Sensor {
        private String className;
        private Map<String,String> configuration;
        private Interval interval;
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    public static class Interval {
        private String unit;
        private int period;
    }

    @SuperBuilder
    @NoArgsConstructor
    public static class LoadMetricVariable extends MetricVariable {
        public LoadMetricVariable(String name, MetricContext context) {
            setName(name);
            setMetricContext(context);
        }
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    public static class Annotation {
        protected String id;
        protected String uri;
        protected boolean implemented;
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Component extends NamedElement {
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Data extends NamedElement {
        private DataSource dataSource;
        private List<Data> includedData;
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class DataSource extends NamedElement {
        private boolean external;
        private Component component;
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Monitor extends NamedElement {  // See: eu.melodic.event.models.interfaces.Monitor
        private String metric;
        private String component;
        private Sensor sensor;
        private List<Sink> sinks;
        private Map<String,String> tags;
        private Map<String,Object> additionalProperties;
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Sink extends NamedElement {  // See: eu.melodic.event.models.interfaces.Sink
        public enum Type { JMS }

        private Type type;
        private String component;
        private Sensor sensor;
        private List<Sink> sinks;
        private Map<String,String> configuration;
        private Map<String,Object> additionalProperties;
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Metric extends NamedElement {
        protected MetricTemplate metricTemplate;
    }

    public enum ValueType {
        INT_TYPE, STRING_TYPE, BOOLEAN_TYPE, FLOAT_TYPE, DOUBLE_TYPE,
        IntType, StringType, BooleanType, FloatType, DoubleType
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class MetricTemplate extends NamedElement {
        private ValueType valueType;
        private short valueDirection;
        private String unit;
        private MeasurableAttribute attribute;
    }

    /*@lombok.Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Unit extends NamedElement {
    }*/

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class MeasurableAttribute extends Attribute {
        private List<Sensor> sensors;
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Attribute extends NamedElement {
        private Object value;
        private ValueType valueType;
        private String unit;
        private Object minValue;
        private Object maxValue;
        private boolean minInclusive;
        private boolean maxInclusive;
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class MetricVariable extends Metric {
        private boolean currentConfiguration;
        private Component component;
        private boolean onNodeCandidates;
        private String formula;
        private List<Metric> componentMetrics;
        private TranslationContext.MetricContext metricContext;
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class ObjectContext extends NamedElement {
        private Component component;
        private Data data;
        //private Communication communication;
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class ScalabilityRule extends NamedElement {
        private Event event;
        private List<Action> actions;
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Event extends NamedElement {
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Action extends NamedElement {
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class ScalingAction extends NamedElement {
        private Component component;
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class OptimisationRequirement extends Requirement {   // SoftRequirement
        private double priority;
        private MetricContext metricContext;
        private MetricVariable metricVariable;
        private boolean minimise;
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class ServiceLevelObjective extends Requirement { // HardRequirement
        private Constraint constraint;
        private Event violationEvent;
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Requirement extends NamedElement {
    }

    @lombok.Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Function extends NamedElement {
        private String expression;
        private List<String> arguments;
    }

    public enum WindowType { FIXED, SLIDING }
    public enum WindowSizeType { MEASUREMENTS_ONLY, TIME_ONLY, FIRST_MATCH, BOTH_MATCH, TIME_ACCUM, TIME_ORDER }

    @lombok.Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Window extends NamedElement {
        private String timeUnit;
        private WindowType windowType;
        private WindowSizeType sizeType;
        private long measurementSize;
        private long timeSize;
        private List<WindowProcessing> processings;
    }

    public enum WindowProcessingType { GROUP, SORT, RANK }

    @lombok.Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class WindowProcessing extends NamedElement {
        private WindowProcessingType processingType;
        private List<WindowCriterion> groupingCriteria;
        private List<WindowCriterion> rankingCriteria;
    }

    public enum CriterionType { INSTANCE, HOST, ZONE, REGION, CLOUD, TIMESTAMP, CUSTOM }

    @lombok.Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class WindowCriterion extends NamedElement {
        private Metric metric;
        private CriterionType type;
        private String custom;
        private boolean ascending;
    }
}
