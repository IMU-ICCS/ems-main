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
import java.util.stream.Collectors;
import lombok.ToString;

@ToString
public class TranslationContext {
	// Element-to-Name map
	public final Map<NamedElement,String> E2N = new HashMap<>();
	
	// Decomposition DAG
	public final DAG DAG = new DAG(E2N);
	
	// Event-to-Action map
	public final Map<String,Set<String>> E2A = new HashMap<>();
	
	// SLO set
	public final Set<String> SLO = new HashSet<>();
	
	// Component-to-Sensor map
	public final Map<Component,Set<Sensor>> C2S = new HashMap<>();
	
	// Data-to-Sensor map
	public final Map<Data,Set<Sensor>> D2S = new HashMap<>();
	
	// Sensor Monitors set
	public final Set<Monitor> MON = new HashSet<>();
	public final Set<String> MONS = new HashSet<>();
	
	// Grouping-to-EPL Rule map
	//public final Map<String,Set<String>> G2R = new HashMap<>();
	public final Map<String,Map<String,Set<String>>> G2R = new HashMap<>();
	
	// Grouping-to-Topics map
	public final Map<String,Set<String>> G2T = new HashMap<>();
	
	// Topics-Connections-per-Grouping
	protected Map<String,String> providedTopics = new HashMap<>();						// topic-grouping where this topic is provided
	protected Map<String,Set<String>> requiredTopics = new HashMap<>();					// topic-set of groupins where this topic is required
	protected Map<String,Map<String,Set<String>>> topicConnections = new HashMap<>();	// grouping-provided topic in grouping-groupings that require provided topic
	protected boolean needsRefresh;
	
	// Metric-to-Metric Context map
	public final Map<Metric,Set<MetricContext>> M2MC = new HashMap<>();
	
	// Metric Variable Values set
	public final Set<String> MVV = new HashSet<>();
	
	// Function set
	public final Set<FunctionDefinition> FUNC = new HashSet<>();
	
	
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
	
	/*public void addMVV(MetricVariable mvv) { MVV.add(mvv); }
	public void addMVVs(List<MetricVariable> mvvs) { MVV.addAll(mvvs); }*/
	public void addMVV(MetricVariable mvv) { MVV.add(mvv.getName()); }
	public void addMVVs(List<MetricVariable> mvvs) { mvvs.stream().forEach(this::addMVV); }
	
	public void addFunction(Function f) {
		FunctionDefinition fdef = new FunctionDefinition().setName(f.getName()).setExpression(f.getExpression()).setArguments(f.getArguments());
		FUNC.add(fdef);
	}
	
	public void addElementName(NamedElement element, String fullName) {
		if (E2N.containsKey(element)) throw new CamelToEplTranslationException("Element name already exists: "+fullName);
		E2N.put(element, fullName);
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
		if (isMVV(topic)) return;
		addGroupingTopicPair(grouping, topic);
		Set<String> groupings = requiredTopics.get(topic);
		if (groupings==null) requiredTopics.put(topic, groupings = new HashSet<>());
		groupings.add(grouping);
		needsRefresh = true;
	}
	public void requireGroupingTopicPairs(String grouping, List<String> topics) {
		topics.stream().forEach(t -> { requireGroupingTopicPair(grouping, t); });
	}
	
	public Map<String,Map<String,Set<String>>> getTopicConnections() {
		if (needsRefresh) {
			topicConnections.clear();
			
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
			
			needsRefresh = false;
		}
		return topicConnections;
	}
	public Map<String,Set<String>> getTopicConnectionsForGrouping(String grouping) {
		return getTopicConnections().get(grouping);
	}
	
	// ====================================================================================================================================================
	// Function-Definition-related helper methods
	
	public Set<FunctionDefinition> getFunctionDefinitions() {
		return new HashSet<>( FUNC );
	}
}
