/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.translate;

import camel.core.Action;
import camel.core.NamedElement;
import camel.data.Data;
import camel.deployment.Component;
import camel.metric.Function;
import camel.metric.GroupingType;
import camel.metric.Metric;
import camel.metric.MetricContext;
import camel.metric.MetricVariable;
import camel.metric.ObjectContext;
import camel.metric.Sensor;
import camel.requirement.ServiceLevelObjective;
import camel.scalability.Event;

import eu.melodic.event.brokercep.cep.FunctionDefinition;
import eu.melodic.event.translate.analyze.DAG;
import eu.melodic.models.interfaces.ems.Monitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString
@Slf4j
public class TranslationContext {
	// Decomposition DAG
	public final DAG DAG;
	
	// Event-to-Action map
	public final Map<String,Set<String>> E2A;
	
	// SLO set
	public final Set<String> SLO;
	
	// Component-to-Sensor map
	public final Map<Component,Set<Sensor>> C2S;		//XXX:TODO-LOW: Convert to strings
	
	// Data-to-Sensor map
	public final Map<Data,Set<Sensor>> D2S;				//XXX:TODO-LOW: Convert to strings
	
	// Sensor Monitors set
	public final Set<Monitor> MON;						//XXX:TODO-LOW: Remove ??
	public final Set<String> MONS;
	
	// Grouping-to-EPL Rule map
	public final Map<String,Map<String,Set<String>>> G2R;
	
	// Grouping-to-Topics map
	public final Map<String,Set<String>> G2T;
	
	// Topics-Connections-per-Grouping
	protected Map<String,String> providedTopics;						// topic-grouping where this topic is provided
	protected Map<String,Set<String>> requiredTopics;					// topic-set of groupins where this topic is required
	protected Map<String,Map<String,Set<String>>> topicConnections;		// grouping-provided topic in grouping-groupings that require provided topic
	protected boolean needsRefresh;
	
	// Metric-to-Metric Context map
	public final Map<Metric,Set<MetricContext>> M2MC;
	
	// Composite Metric Variables set
	public final Set<String> CMVAR;
	public final Set<MetricVariable> CMVAR_1;
	// Metric Variable Values set (i.e. non-composite metric variable)
	public final Set<String> MVV;
	
	// Function set
	public final Set<FunctionDefinition> FUNC;
	
	// Element-to-Full-Name cache, pattern and count
	private transient final Map<NamedElement,String> E2N;			//XXX:TODO-LOW: Clear after translation
	private transient final AtomicLong elementsCount;
	private transient String fullNamePattern = "{ELEM}";					// all options: {TYPE}, {CAMEL}, {MODEL}, {ELEM}, {HASH}, {COUNT}
	
	
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
	}
	
	// ====================================================================================================================================================
	// Copy/Getter methods
	
	public Map<String,Set<String>> getG2T() {
		HashMap<String,Set<String>> newMap = new HashMap<>();
		G2T.entrySet().stream().forEach(entry -> {
			newMap.put( entry.getKey(), new HashSet<String>( entry.getValue() ) );
		});
		return newMap;
	}
	
	public Map<String,Map<String,Set<String>>> getG2R() {
		Map<String,Map<String,Set<String>>> newGroupingsMap = new HashMap<>();	// groupings
		G2R.entrySet().stream().forEach(entry -> {
			Map<String,Set<String>> newTopicsMap = new HashMap<>();			// topics per grouping
			newGroupingsMap.put( entry.getKey(), newTopicsMap );
			entry.getValue().entrySet().stream().forEach(entry2 -> {
				Set<String> newRuleSet = new HashSet<>();					// rules per topic per grouping
				newTopicsMap.put( entry2.getKey(), newRuleSet );
				newRuleSet.addAll( entry2.getValue() );
			});
		});
		return newGroupingsMap;
	}
	
	public MetricContext getMetricContextForMetric(Metric m) { Set<MetricContext> set = M2MC.get(m); return set==null ? null : set.iterator().next(); }
	
	public boolean isMVV(String name) { for (String mvv : MVV) if (mvv.equals(name)) return true; return false; }
	
	// ====================================================================================================================================================
	// Map- and Set-related helper methods
	
	protected void _addPair(Map map, Object key, Object value) {
		Set valueSet = (Set)map.get(key);
		if (valueSet==null) { valueSet = new HashSet<>(); map.put(key, valueSet); }
		if (List.class.isInstance(value)) valueSet.addAll( (List)value );
		else valueSet.add( value );
	}
	
	public void addEventActionPair(Event event, Action action) { _addPair(E2A, E2N.get(event), E2N.get(action)); }
	
	public void addEventActionPairs(Event event, List<Action> actions) { _addPair(E2A, E2N.get(event), actions.stream().map(action -> E2N.get(action)).collect(Collectors.toList())); }
	
	public void addSLO(ServiceLevelObjective slo) { SLO.add( E2N.get(slo) ); }
	
	public void addComponentSensorPair(ObjectContext objContext, Sensor sensor) {
		if (objContext!=null) {
			Component comp = objContext.getComponent();
			Data data = objContext.getData();
			if (comp!=null) _addPair(C2S, comp, sensor);
			if (data!=null) _addPair(D2S, data, sensor);
		} else {
			_addPair(C2S, null, sensor);
		}
	}
	
	public void addMonitorsForSensor(String sensorName, Set<Monitor> monitors) {
		if (monitors!=null) {
			if (! MONS.contains(sensorName)) {
				MON.addAll(monitors);
				MONS.add(sensorName);
			}
		}
	}
	
	public boolean containsMonitorsForSensor(String sensorName) {
		return MONS.contains(sensorName);
	}
	
	public void addGroupingTopicPair(String grouping, String topic) { _addPair(G2T, grouping, topic); }
	
	public void addGroupingTopicPairs(String grouping, List<String> topics) { _addPair(G2T, grouping, topics); }
	
	public void addGroupingRulePair(String grouping, String topic, String rule) {
		Map<String,Set<String>> topics = G2R.get(grouping);
		if (topics==null) { topics = new HashMap<String,Set<String>>(); G2R.put(grouping, topics); }
		Set<String> rules = topics.get(topic);
		if (rules==null) { rules = new HashSet<String>(); topics.put(topic, rules); }
		rules.add(rule);
	}
	
	public void addGroupingRulePairs(String grouping, String topic, List<String> rules) { rules.stream().forEach(rule -> addGroupingRulePair(grouping, topic, rule)); }
	
	public void addMetricMetricContextPair(Metric m, MetricContext mc) { _addPair(M2MC, m, mc); }
	
	public void addMetricMetricContextPairs(Metric m, List<MetricContext> mcs) { _addPair(M2MC, m, mcs); }
	
	public void addCompositeMetricVariable(MetricVariable mv) { CMVAR.add(mv.getName()); CMVAR_1.add(mv); }
	public void addCompositeMetricVariables(List<MetricVariable> mvs) { mvs.stream().forEach(this::addCompositeMetricVariable); }
	
	public void addMVV(MetricVariable mvv) { MVV.add(mvv.getName()); }
	public void addMVVs(List<MetricVariable> mvvs) { mvvs.stream().forEach(this::addMVV); }
	
	public void addFunction(Function f) {
		FunctionDefinition fdef = new FunctionDefinition().setName(f.getName()).setExpression(f.getExpression()).setArguments(f.getArguments());
		FUNC.add(fdef);
	}
	
	// ====================================================================================================================================================
	// Topic-Connections-per-Grouping-related helper methods
	// Auto-fill of Topic connections between Groupings.... (use provide/require methods below)
	
	public void provideGroupingTopicPair(String grouping, String topic) {
		if (isMVV(topic)) return;
		addGroupingTopicPair(grouping, topic);
		String providerGrouping = providedTopics.get(grouping);
		if (providerGrouping!=null && !providerGrouping.equals(grouping)) {
			throw new CamelToEplTranslationException("Topic "+topic+" is provided more than once: grouping-1="+grouping+", grouping-2="+providedTopics.get(grouping));
		}
		providedTopics.put(topic, grouping);
		needsRefresh = true;
	}
	public void requireGroupingTopicPair(String grouping, String topic) {
		log.warn("------------> requireGroupingTopicPair: grouping={}, topic={}", grouping, topic);
		if (isMVV(topic)) return;
		log.warn("------------> requireGroupingTopicPair: Not an MVV. Good: grouping={}, topic={}", grouping, topic);
		log.warn("------------> requireGroupingTopicPair: requiredTopics BEFORE: {}", requiredTopics);
		addGroupingTopicPair(grouping, topic);
		Set<String> groupings = requiredTopics.get(topic);
		if (groupings==null) requiredTopics.put(topic, groupings = new HashSet<>());
		groupings.add(grouping);
		needsRefresh = true;
		log.warn("------------> requireGroupingTopicPair: requiredTopics AFTER: {}", requiredTopics);
		log.error("------------> requireGroupingTopicPair: stacktrace: {}", new Exception("zzzzzzzz"));
	}
	public void requireGroupingTopicPairs(String grouping, List<String> topics) {
		topics.stream().forEach(t -> { requireGroupingTopicPair(grouping, t); });
	}
	
	public Map<String,Map<String,Set<String>>> getTopicConnections() {
		if (needsRefresh) {
			topicConnections.clear();
			
//XXX:2018-11-30:
			log.warn(">>>>>>>>>>>>  TranslationContext.getTopicConnections(): required-topics: {}", requiredTopics);
			log.warn(">>>>>>>>>>>>  TranslationContext.getTopicConnections(): provided-topics: {}", providedTopics);
//XXX:2018-11-30:
			if (requiredTopics.size()==0) {
				for (Map.Entry<String,String> entry : providedTopics.entrySet()) {
					Set<String> topicGroupings = requiredTopics.get(entry.getKey());
					if (topicGroupings==null) {
						topicGroupings = new HashSet<>();
						requiredTopics.put(entry.getKey(), topicGroupings);
					}
					topicGroupings.add(entry.getValue());
				}
				log.warn(">>>>>>>>>>>>  TranslationContext.getTopicConnections(): required-topics was empty and populated with data from provided-topics: {}", requiredTopics);
			}
			
			// for every required topic...
			for (Map.Entry<String,Set<String>> pair : requiredTopics.entrySet()) {
				// get consumer topics for current required topic
				String requiredTopic = pair.getKey();
				Set<String> consumerGroupings = pair.getValue();
				// get provider grouping of current required topic
				String providerGrouping = providedTopics.get(requiredTopic);
				if (providerGrouping==null) throw new CamelToEplTranslationException("Topic "+requiredTopic+" is not provided in any grouping");
				// remove provider grouping from consumer groupings
				consumerGroupings.remove(providerGrouping);
				// store required topic in 'topicConnections'
				if (consumerGroupings.size()>0) {
					// ...get provider grouping topics from topicConnections
					Map<String,Set<String>> groupingTopics = topicConnections.get(providerGrouping);
					if (groupingTopics==null) topicConnections.put(providerGrouping, groupingTopics = new HashMap<String,Set<String>>());
					// ...store consumer groupings for current required topic in provider grouping
					if (groupingTopics.containsKey(requiredTopic)) throw new CamelToEplTranslationException("INTERNAL ERROR: Required Topic "+requiredTopic+" is already set in provider grouping "+providerGrouping+" in '_TC.topicConnections'");
					groupingTopics.put(requiredTopic, consumerGroupings);
				}
			}
//XXX:2018-11-30:
	/*		log.warn(">>>>>>>>>>>>  TranslationContext.getTopicConnections(): intermediate-results: {}", topicConnections);
			
			// if GLOBAL is empty...
			Map<String,Set<String>> globalGrouping = topicConnections.get("GLOBAL");
			if (globalGrouping==null || globalGrouping.size()==0) {
				// copy the topics of the higher underlying grouping with topics
				GroupingType[] groupingArr = GroupingType.values();
				for (int i=groupingArr.length-2; i>=0; i--) {
					Map<String,Set<String>> underlyingGrouping = topicConnections.get(groupingArr[i].getName());
					log.warn(">>>>>>>>>>>>  TranslationContext.getTopicConnections(): checking grouping: name={}, connections={}", groupingArr[i].getName(), underlyingGrouping);
					if (underlyingGrouping!=null && underlyingGrouping.size()>0) {
						log.warn(">>>>>>>>>>>>  TranslationContext.getTopicConnections(): Topics found in grouping: name={}, connections={}", groupingArr[i].getName(), underlyingGrouping);
						globalGrouping.putAll( underlyingGrouping );
					}
				}
			}
			log.warn(">>>>>>>>>>>>  TranslationContext.getTopicConnections(): results: {}", topicConnections);*/
			
			needsRefresh = false;
		}
		return topicConnections;
	}
	public Map<String,Set<String>> getTopicConnectionsForGrouping(String grouping) {
		return getTopicConnections().get(grouping);
	}
	
	// ====================================================================================================================================================
	// Element full name generation methods
	
	public String getFullNamePattern() { return fullNamePattern; }
	
	public void setFullNamePattern(String pattern) { fullNamePattern = pattern; }
	
	public String getFullName(NamedElement elem) {
		if (elem==null) return null;
		
		// return cached full-name for element
		String fullName = E2N.get(elem);
		if (fullName!=null) return fullName;
		
		// else generate full-name for element (and cache it)
		String elemName = elem.getName();
		String elemType = _getElementType(elem);
		String modelName = ((NamedElement)elem.eContainer()).getName();
		String camelName = ((NamedElement)elem.eContainer().eContainer()).getName();
		
		fullName = fullNamePattern
			.replace("{TYPE}", elemType)
			.replace("{CAMEL}", camelName)
			.replace("{MODEL}", modelName)
			.replace("{ELEM}", elemName)
			.replace("{HASH}", Integer.toString(elemName.hashCode()))
			.replace("{COUNT}", Long.toString( elementsCount.getAndIncrement() ))
			;
		
		E2N.put(elem, fullName);
		
		return fullName;
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
		return new HashSet<>( FUNC );
	}
}
