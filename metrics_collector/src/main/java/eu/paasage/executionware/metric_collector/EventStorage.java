/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.ecore.util.EcoreUtil;

import eu.paasage.camel.LayerType;
import eu.paasage.camel.requirement.Requirement;
import eu.paasage.camel.requirement.RequirementGroup;
import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.camel.execution.ExecutionFactory;
import eu.paasage.camel.execution.ExecutionModel;
import eu.paasage.camel.execution.Measurement;
import eu.paasage.camel.execution.SLOAssessment;
import eu.paasage.camel.metric.ComparisonOperatorType;
import eu.paasage.camel.metric.Condition;
import eu.paasage.camel.scalability.Event;
import eu.paasage.camel.scalability.EventInstance;
import eu.paasage.camel.metric.Metric;
import eu.paasage.camel.metric.MetricCondition;
import eu.paasage.camel.metric.MetricInstance;
import eu.paasage.camel.scalability.ScalabilityFactory;
import eu.paasage.camel.scalability.ScalabilityModel;
import eu.paasage.camel.scalability.SimpleEvent;
import eu.paasage.camel.scalability.StatusType;
import eu.paasage.camel.requirement.ServiceLevelObjective;
import eu.paasage.mddb.cdo.client.CDOClient;

public class EventStorage {
	
	private static List<ServiceLevelObjective> getSlo(Metric m, RequirementGroup rg){
		List<ServiceLevelObjective> slo = null;
		for (Requirement r: rg.getRequirements()){
			if (r instanceof ServiceLevelObjective){
				ServiceLevelObjective slot = (ServiceLevelObjective)r;
				Condition condition = slot.getCustomServiceLevel();
				if (condition instanceof MetricCondition){
					MetricCondition mc = (MetricCondition)condition;
					if (mc.getMetricContext().getMetric().equals(m)){
						if (slo == null) slo = new ArrayList<ServiceLevelObjective>();
						slo.add(slot);
						if (slo.size() == 2) break;
					}
				}
			}
		}
		return slo;
	}
	
	private static boolean checkValue(double value, Condition condition){
		ComparisonOperatorType operator = condition.getComparisonOperator();
		double threshold = condition.getThreshold();
		boolean assess = false;
		if (operator.equals(ComparisonOperatorType.EQUAL)){
			if (value == threshold) assess = true;
		}
		else if (operator.equals(ComparisonOperatorType.GREATER_EQUAL_THAN)){
			if (value >= threshold) assess = true;
		}
		else if (operator.equals(ComparisonOperatorType.GREATER_THAN)){
			if (value > threshold) assess = true;
		}
		else if (operator.equals(ComparisonOperatorType.LESS_THAN)){
			if (value < threshold) assess = true;
		}
		else if (operator.equals(ComparisonOperatorType.LESS_EQUAL_THAN)){
			if (value <= threshold) assess = true;
		}
		else if (operator.equals(ComparisonOperatorType.NOT_EQUAL)){
			if (value != threshold) assess = true;
		}
		return assess;
	}
	
	private static SLOAssessment createSLOAssess(ServiceLevelObjective slo, Measurement measurement, ExecutionContext ec){
		SLOAssessment assess = ExecutionFactory.eINSTANCE.createSLOAssessment();
		assess.setAssessmentTime(new Date());
		assess.setName(CDOIDUtil.createUUID().toString());
		assess.setSlo(slo);
		assess.setMeasurement(measurement);
		assess.setExecutionContext(ec);
		double value = measurement.getValue();
		Condition condition = slo.getCustomServiceLevel();
		assess.setAssessment(checkValue(value,condition));
		
		return assess;
	}
	
	public static synchronized void storeEvent(StatusType status, CDOID eventID, CDOID measID, LayerType layer){
		CDOClient cl = new CDOClient();
		CDOTransaction trans = cl.openTransaction();
		Event event = (Event)trans.getObject(eventID);
		ScalabilityModel sm = (ScalabilityModel)event.eContainer();
		Measurement m = (Measurement)trans.getObject(measID);
		MetricInstance mi = m.getMetricInstance();
		ExecutionContext ec = m.getExecutionContext();
		ExecutionModel em = (ExecutionModel)ec.eContainer();
	
		EventInstance ei = ScalabilityFactory.eINSTANCE.createEventInstance();
		ei.setName(EcoreUtil.generateUUID().toString());
		ei.setEvent((SimpleEvent)event);
		ei.setLayer(layer);
		ei.setStatus(status);
		//Could replace with Measurement here
		ei.setMetricInstance(mi);
		sm.getEventInstances().add(ei);
		
		//Check to create SLOAssessments, if condition is equivalent
		RequirementGroup rg = ec.getRequirementGroup();
		Metric metric = mi.getMetric();
		List<ServiceLevelObjective> slos = getSlo(metric,rg);
		//m.setSlo(slo);
		SLOAssessment assess = null;
		if (slos != null){
			for (ServiceLevelObjective slo: slos){
				assess = createSLOAssess(slo,m,ec);
				em.getSloAssessessments().add(assess);
			}
		}
		try{
			trans.commit();
		}
		catch(Exception e){
			e.printStackTrace();	
		}
		trans.close();
		cl.closeSession();
	}
}
