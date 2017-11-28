/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.view.CDOView;

import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.camel.metric.CompositeMetric;
import eu.paasage.camel.metric.CompositeMetricInstance;
import eu.paasage.camel.metric.Condition;
import eu.paasage.camel.metric.Metric;
import eu.paasage.camel.metric.MetricApplicationBinding;
import eu.paasage.camel.metric.MetricCondition;
import eu.paasage.camel.metric.MetricFormula;
import eu.paasage.camel.metric.MetricFormulaParameter;
import eu.paasage.camel.metric.MetricInstance;
import eu.paasage.camel.requirement.OptimisationRequirement;
import eu.paasage.camel.requirement.Requirement;
import eu.paasage.camel.requirement.RequirementGroup;
import eu.paasage.camel.requirement.ServiceLevelObjective;
import eu.paasage.camel.scalability.BinaryEventPattern;
import eu.paasage.camel.scalability.Event;
import eu.paasage.camel.scalability.EventPattern;
import eu.paasage.camel.scalability.NonFunctionalEvent;
import eu.paasage.camel.scalability.UnaryEventPattern;

public class CDOUtils {
	private static boolean eventIncludesEvent(Event e, Metric m){
		String name = m.getName();
		if (e instanceof NonFunctionalEvent){
			NonFunctionalEvent nfe = (NonFunctionalEvent)e;
			if (nfe.getMetricCondition().getMetricContext().getMetric().getName().equals(name)) return true;
		}
		else if (e instanceof EventPattern){
			EventPattern ep2 = (EventPattern)e;
			return patternIncludesEvent(ep2,m);
		}
		return false;
	}
	
	private static boolean patternIncludesEvent(EventPattern ep, Metric m){
		if (ep instanceof UnaryEventPattern){
			UnaryEventPattern uep = (UnaryEventPattern)ep;
			Event e = uep.getEvent();
			return eventIncludesEvent(e,m);
		}
		else{
			BinaryEventPattern bep = (BinaryEventPattern)ep;
			Event leftEvent = bep.getLeftEvent();
			boolean included = eventIncludesEvent(leftEvent,m);
			if (included) return true;
			Event rightEvent = bep.getRightEvent();
			included = eventIncludesEvent(rightEvent,m);
			if (included) return true;
		}
		return false;
	}
	
	public static boolean canPush(CDOID id, CDOView view){
		CompositeMetricInstance cmi = (CompositeMetricInstance)view.getObject(id);
		Metric m = cmi.getMetric(); 
		String metricName = m.getName();
		ExecutionContext ec = cmi.getObjectBinding().getExecutionContext();
		String ecId = ec.getName();
		//Checking if SLO contains condition on metric of instance
		List<MetricCondition> mc = view.createQuery("hql", "select mc from MetricCondition mc, ServiceLevelObjective slo, ExecutionContext ec where ec.name='" + ecId + "' and slo member of ec.requirementGroup.requirements and slo.customServiceLevel=mc and mc.metricContext.metric.name='" + metricName + "'").getResult(MetricCondition.class);
		if (mc != null && !mc.isEmpty()) return true;
		//Checking if exists optimisation requirement on metric of instance
		List<OptimisationRequirement> or = view.createQuery("hql", "select or1 from OptimisationRequirement or1, ExecutionContext ec where ec.name='" + ecId + "' and or1 member of ec.requirementGroup.requirements and or1.metric.name='" + metricName + "'").getResult(OptimisationRequirement.class);
		if (or != null && !or.isEmpty()) return true;
		//Checking if exists non-functional event in scalability rule with condition on metric of instance
		List<NonFunctionalEvent> events = view.createQuery("hql","select ev from NonFunctionalEvent ev, ExecutionContext ec, User u, ScalabilityRule sr, MetricCondition mc where ec.name='" + ecId +"' and ev.metricCondition=mc and mc.metricContext.metric.name='" + metricName + "' and u member of sr.entity and ec.deploymentModel member of u.deploymentModels and sr.event=ev").getResult(NonFunctionalEvent.class);
		if (events != null && !events.isEmpty()) return true;
		//Checking if exists event pattern in scalability rule containing a non-functional event with condition on metric of instance
		List<EventPattern> eps = view.createQuery("hql","select ep from EventPattern ep, ExecutionContext ec, User u, ScalabilityRule sr where ec.name='" + ecId +"' and u member of sr.entity and ec.deploymentModel member of u.deploymentModels and sr.event=ep").getResult(EventPattern.class);
		if (eps != null && !eps.isEmpty()){
			for (EventPattern ep: eps) {
				boolean contained = patternIncludesEvent(ep,m);
				if (contained) return true;
			}
		}
		return false;
	}
	
	private static Set<CDOID> getMetricInstances(CDOView view, ExecutionContext ec, RequirementGroup rg){
		Set<CDOID> topMetrics = new HashSet<CDOID>();
		for (Requirement r: rg.getRequirements()){
			if (r instanceof ServiceLevelObjective){
				ServiceLevelObjective slo = (ServiceLevelObjective)r;
				Condition c = slo.getCustomServiceLevel();
				if (c instanceof MetricCondition){
					MetricCondition mc = (MetricCondition)c;
					CDOID mi = getMetricInstanceFromCondition(view,mc,ec);
					topMetrics.add(mi);
				}
			}
			else if (r instanceof RequirementGroup){
				topMetrics.addAll(getMetricInstances(view,ec,(RequirementGroup)r));
			}
		}
		return topMetrics;
	}
	
	private static CDOID getMetricInstanceFromCondition(CDOView view, MetricCondition mc, ExecutionContext ec){
		Metric m = mc.getMetricContext().getMetric();
		List<MetricInstance> mis = view.createQuery("hql","select mi from MetricInstance mi, ExecutionContext ec where ec.name='" + ec.getName() + "' and mi.metric.name='" + m.getName() + "' and mi.objectBinding.executionContext.name=ec.name").getResult(MetricInstance.class);
		if (mis != null && !mis.isEmpty()) return mis.get(0).cdoID();
		return null;
	}
	
	private static Set<CDOID> getEventBasedMetricInstances(CDOView view, Event e, ExecutionContext ec){
		Set<CDOID> topMetrics = new HashSet<CDOID>();
		if (e instanceof NonFunctionalEvent){
			NonFunctionalEvent nfe = (NonFunctionalEvent)e;
			MetricCondition mc = nfe.getMetricCondition();
			CDOID mi = getMetricInstanceFromCondition(view,mc,ec);
			topMetrics.add(mi);
		}
		else if (e instanceof EventPattern){
			EventPattern ep = (EventPattern)e;
			if (ep instanceof UnaryEventPattern){
				UnaryEventPattern uep = (UnaryEventPattern)ep;
				Event e2 = uep.getEvent();
				topMetrics.addAll(getEventBasedMetricInstances(view,e2,ec));
			}
			else{
				BinaryEventPattern bep = (BinaryEventPattern)ep;
				Event leftEvent = bep.getLeftEvent();
				topMetrics.addAll(getEventBasedMetricInstances(view,leftEvent,ec));
				Event rightEvent = bep.getRightEvent();
				topMetrics.addAll(getEventBasedMetricInstances(view,rightEvent,ec));
			}
		}
		return topMetrics;
	}
	
	//Assumes that execution context and respective metric instances have already been generated 
	public static Set<CDOID> getTopMetrics(CDOView view, CDOID ecID){
		Set<CDOID> topMetrics = new HashSet<CDOID>();
		ExecutionContext ec = (ExecutionContext)view.getObject(ecID);
		RequirementGroup rg = ec.getRequirementGroup();
		//Get all SLO-based metric instances for specific execution context
		topMetrics.addAll(getMetricInstances(view,ec,rg));
		//Get all event-based metric instances for scalability rules of user posing the requirements in the group
		List<Event> events = view.createQuery("hql","select e from Event e, ExecutionContext ec, User u, ScalabilityRule sr where ec.name='" + ec.getName() +"' and u member of sr.entity and ec.deploymentModel member of u.deploymentModels and sr.event=e").getResult(Event.class);
		if (events != null && !events.isEmpty()){
			for (Event e: events)
			topMetrics.addAll(getEventBasedMetricInstances(view,e,ec));
		}
		
		return topMetrics;
	}
	
	private static boolean changeOfBinding(MetricFormula mf, Metric m, List<MetricInstance> mis, CDOView view){
		for (MetricFormulaParameter mfp: mf.getParameters()){
			if (mfp instanceof Metric){
				Metric m2 = (Metric)mfp;
				List<MetricInstance> instances = view.createQuery("hql","select mi2 from CompositeMetricInstance mi, MetricInstance mi2 where mi2.metric.name='" + m2.getName() + "' and mi.metric.name='" + m.getName() + "' and mi2 member of mi.composingMetricInstances").getResult(MetricInstance.class);
				if (instances != null && !instances.isEmpty()){
					MetricInstance mi2 = instances.get(0);
					if (mi2.getObjectBinding() instanceof MetricApplicationBinding) continue;
					else return true;
				}
			}
			else if (mfp instanceof MetricFormula){
				if (changeOfBinding((MetricFormula)mfp,m,mis,view)) return true; 
			}
		}
		return false;
	}
	
	private static boolean isGlobalMetric(CDOID id, CDOView view){
		MetricInstance mi = (MetricInstance)view.getObject(id);
		if (mi instanceof CompositeMetricInstance){
			List<MetricInstance> mis = ((CompositeMetricInstance)mi).getComposingMetricInstances();
			if (mi.getObjectBinding() instanceof MetricApplicationBinding){
				Metric m = mi.getMetric();
				if (m instanceof CompositeMetric){
					CompositeMetric cm = (CompositeMetric)m;
					MetricFormula mf = cm.getFormula();
					if (changeOfBinding(mf,m,mis,view)) return true;
				}
			}
		}
		
		return false;
	}
	
	//Assumes that a global metric is at application level and computed by other cloud-based metrics
	public static Set<CDOID> getGlobalMetrics(Set<CDOID> ids, CDOView view){
		Set<CDOID> globalMetrics = new HashSet<CDOID>();
		for (CDOID id: ids){
			if (isGlobalMetric(id,view)) globalMetrics.add(id);
		}
		return globalMetrics;
	}
}
