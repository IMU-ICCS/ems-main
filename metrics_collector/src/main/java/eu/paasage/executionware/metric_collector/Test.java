/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import eu.paasage.camel.Application;
import eu.paasage.camel.CamelFactory;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.DeploymentFactory;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.InternalComponent;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.VM;
import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.camel.execution.ExecutionFactory;
import eu.paasage.camel.execution.ExecutionModel;
import eu.paasage.camel.metric.ComparisonOperatorType;
import eu.paasage.camel.metric.CompositeMetric;
import eu.paasage.camel.metric.CompositeMetricContext;
import eu.paasage.camel.metric.CompositeMetricInstance;
import eu.paasage.camel.metric.Condition;
import eu.paasage.camel.metric.Metric;
import eu.paasage.camel.metric.MetricApplicationBinding;
import eu.paasage.camel.metric.MetricComponentBinding;
import eu.paasage.camel.metric.MetricCondition;
import eu.paasage.camel.metric.MetricFactory;
import eu.paasage.camel.metric.MetricFormula;
import eu.paasage.camel.metric.MetricFormulaParameter;
import eu.paasage.camel.metric.MetricFunctionArityType;
import eu.paasage.camel.metric.MetricFunctionType;
import eu.paasage.camel.metric.MetricInstance;
import eu.paasage.camel.metric.MetricModel;
import eu.paasage.camel.metric.Property;
import eu.paasage.camel.metric.PropertyType;
import eu.paasage.camel.metric.RawMetric;
import eu.paasage.camel.metric.RawMetricInstance;
import eu.paasage.camel.metric.Schedule;
import eu.paasage.camel.metric.ScheduleType;
import eu.paasage.camel.metric.Sensor;
import eu.paasage.camel.metric.Window;
import eu.paasage.camel.metric.WindowSizeType;
import eu.paasage.camel.metric.WindowType;
import eu.paasage.camel.organisation.Organisation;
import eu.paasage.camel.organisation.OrganisationFactory;
import eu.paasage.camel.organisation.OrganisationModel;
import eu.paasage.camel.organisation.PaaSageCredentials;
import eu.paasage.camel.organisation.User;
import eu.paasage.camel.requirement.Requirement;
import eu.paasage.camel.requirement.RequirementFactory;
import eu.paasage.camel.requirement.RequirementGroup;
import eu.paasage.camel.requirement.RequirementModel;
import eu.paasage.camel.requirement.RequirementOperatorType;
import eu.paasage.camel.requirement.ServiceLevelObjective;
import eu.paasage.camel.scalability.HorizontalScalingAction;
import eu.paasage.camel.scalability.NonFunctionalEvent;
import eu.paasage.camel.scalability.ScalabilityFactory;
import eu.paasage.camel.scalability.ScalabilityModel;
import eu.paasage.camel.scalability.ScalabilityRule;
import eu.paasage.camel.scalability.UnaryEventPattern;
import eu.paasage.camel.scalability.UnaryPatternOperatorType;
import eu.paasage.camel.type.DoublePrecisionValue;
import eu.paasage.camel.type.TypeFactory;
import eu.paasage.camel.type.TypeModel;
import eu.paasage.camel.unit.TimeIntervalUnit;
import eu.paasage.camel.unit.Unit;
import eu.paasage.camel.unit.UnitFactory;
import eu.paasage.camel.unit.UnitModel;
import eu.paasage.camel.unit.UnitType;
import eu.paasage.executionware.metric_collector.pubsub.SubscriptionClient;
import eu.paasage.mddb.cdo.client.CDOClient;

public class Test {
	private static MetricCollector mc1, mc2 = null;
	private static boolean runClient = false;
	private static CDOID ecID = null;
	
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Test.class);
	
	private static EObject createTestModel(){
		CamelModel model = null;
		model = CamelFactory.eINSTANCE.createCamelModel();
		model.setName("Camel Model");
		Application app = CamelFactory.eINSTANCE.createApplication();
		app.setName("MyApp");
		app.setVersion("v1.0");
		model.getApplications().add(app);
		UnitModel um = UnitFactory.eINSTANCE.createUnitModel();
		um.setName("Unit Model");
		model.getUnitModels().add(um);
		Unit unit = UnitFactory.eINSTANCE.createTimeIntervalUnit();
		unit.setUnit(UnitType.SECONDS);
		unit.setName("SECONDS");
		um.getUnits().add(unit);
		OrganisationModel orgModel = OrganisationFactory.eINSTANCE.createOrganisationModel();
		orgModel.setName("Org Model");
		Organisation org = OrganisationFactory.eINSTANCE.createOrganisation();
		org.setName("Org");
		org.setEmail("contact@org.com");
		orgModel.setOrganisation(org);
		User u = OrganisationFactory.eINSTANCE.createUser();
		u.setName("user1");
		u.setEmail("user@org.com");
		u.setFirstName("User1");
		u.setLastName("User");
		PaaSageCredentials cred = OrganisationFactory.eINSTANCE.createPaaSageCredentials();
		cred.setPassword("user1@user@org.com");
		u.setPaasageCredentials(cred);
		orgModel.getUsers().add(u);
		app.setOwner(u);
		model.getOrganisationModels().add(orgModel);
		TypeModel tr = TypeFactory.eINSTANCE.createTypeModel();
		tr.setName("Type Rep");
		DoublePrecisionValue val = TypeFactory.eINSTANCE.createDoublePrecisionValue();
		val.setValue(3);
		tr.getValues().add(val);
		model.getTypeModels().add(tr);
		
		RequirementModel rm = RequirementFactory.eINSTANCE.createRequirementModel();
		rm.setName("Requirement Model");
		model.getRequirementModels().add(rm);
		
		MetricModel sm = MetricFactory.eINSTANCE.createMetricModel();
		sm.setName("Scalability Model");
		model.getMetricModels().add(sm);
		Property prop = MetricFactory.eINSTANCE.createProperty();
		prop.setName("PROP");
		prop.setType(PropertyType.MEASURABLE);
		sm.getProperties().add(prop);
		//RAW METRICS
		RawMetric a = MetricFactory.eINSTANCE.createRawMetric();
		a.setName("A");
		a.setUnit(unit);
		a.setProperty(prop);
		a.setValueDirection((short)1);
		sm.getMetrics().add(a);
		RawMetric c = MetricFactory.eINSTANCE.createRawMetric();
		c.setName("C");
		c.setUnit(unit);
		c.setProperty(prop);
		c.setValueDirection((short)1);
		sm.getMetrics().add(c);
		//COMPOSITE METRICS
		CompositeMetric b1 = MetricFactory.eINSTANCE.createCompositeMetric();
		b1.setName("B1");
		MetricFormula mf1 = MetricFactory.eINSTANCE.createMetricFormula();
		mf1.setName("B1_FORMULA");
		mf1.setFunction(MetricFunctionType.MAX);
		mf1.setFunctionArity(MetricFunctionArityType.UNARY);
		mf1.getParameters().add(c);
		/*MetricFormulaParameter mfp = MetricFactory.eINSTANCE.createMetricFormulaParameter();
		mfp.setName("percentile");
		DoublePrecisionValue dv = TypeFactory.eINSTANCE.createDoublePrecisionValue();
		dv.setValue(0.5);
		tr.getValues().add(dv);
		mfp.setValue(dv);
		mf1.getParameters().add(mfp);
		sm.getParameters().add(mfp);*/
		b1.setFormula(mf1);
		b1.setUnit(unit);
		b1.setProperty(prop);
		b1.setValueDirection((short)1);
		sm.getMetrics().add(b1);
		CompositeMetric b2 = MetricFactory.eINSTANCE.createCompositeMetric();
		b2.setName("B2");
		MetricFormula mf2 = MetricFactory.eINSTANCE.createMetricFormula();
		mf2.setName("B2_FORMULA");
		mf2.setFunction(MetricFunctionType.PLUS);
		mf2.setFunctionArity(MetricFunctionArityType.BINARY);
		mf2.getParameters().add(a);
		MetricFormulaParameter param = MetricFactory.eINSTANCE.createMetricFormulaParameter();
		param.setName("PARAM2");
		param.setValue(val);
		mf2.getParameters().add(param);
		sm.getParameters().add(param);
		b2.setFormula(mf2);
		b2.setUnit(unit);
		b2.setProperty(prop);
		b2.setValueDirection((short)1);
		sm.getMetrics().add(b2);
		CompositeMetric a2 = MetricFactory.eINSTANCE.createCompositeMetric();
		a2.setName("A2");
		MetricFormula mf3 = MetricFactory.eINSTANCE.createMetricFormula();
		mf3.setName("A2_FORMULA");
		mf3.setFunction(MetricFunctionType.MIN);
		mf3.setFunctionArity(MetricFunctionArityType.UNARY);
		mf3.getParameters().add(b2);
		a2.setFormula(mf3);
		a2.setUnit(unit);
		a2.setProperty(prop);
		a2.setValueDirection((short)1);
		sm.getMetrics().add(a2);
		CompositeMetric b3 = MetricFactory.eINSTANCE.createCompositeMetric();
		b3.setName("B3");
		MetricFormula mf4 = MetricFactory.eINSTANCE.createMetricFormula();
		mf4.setName("B3_FORMULA");
		mf4.setFunction(MetricFunctionType.MEDIAN);
		mf4.setFunctionArity(MetricFunctionArityType.UNARY);
		mf4.getParameters().add(b1);
		b3.setFormula(mf4);
		b3.setUnit(unit);
		b3.setProperty(prop);
		b3.setValueDirection((short)1);
		sm.getMetrics().add(b3);
		//RAW METRIC INSTANCES
		DeploymentModel dm = DeploymentFactory.eINSTANCE.createDeploymentModel();
		dm.setName("DM1");
		InternalComponent ic = DeploymentFactory.eINSTANCE.createInternalComponent();
		ic.setName("My Internal Component");
		dm.getInternalComponents().add(ic);
		InternalComponentInstance compInst = DeploymentFactory.eINSTANCE.createInternalComponentInstance();
		compInst.setName("My Internal Component Instance");
		compInst.setType(ic);
		dm.getInternalComponentInstances().add(compInst);
		VM vm = DeploymentFactory.eINSTANCE.createVM();
		vm.setName("My Internal Component");
		dm.getVms().add(vm);
		model.getDeploymentModels().add(dm);
		u.getDeploymentModels().add(dm);
		ExecutionModel em = ExecutionFactory.eINSTANCE.createExecutionModel();
		em.setName("Execution Model");
		ExecutionContext ec = ExecutionFactory.eINSTANCE.createExecutionContext();
		ec.setName("EC1");
		ec.setApplication(app);
		ec.setDeploymentModel(dm);
		em.getExecutionContexts().add(ec);
		model.getExecutionModels().add(em);
		RequirementGroup rg = RequirementFactory.eINSTANCE.createRequirementGroup();
		rg.setName("RG");
		rm.getRequirements().add(rg);
		CompositeMetricContext context = MetricFactory.eINSTANCE.createCompositeMetricContext();
		context.setName("APP_CONTEXT");
		context.setApplication(app);
		context.setMetric(b1);
		sm.getContexts().add(context);
		//Assume here that default context for composing metrics is used
		MetricCondition mc1 = MetricFactory.eINSTANCE.createMetricCondition();
		mc1.setName("COND1");
		mc1.setMetricContext(context);
		mc1.setThreshold(0.5);
		mc1.setComparisonOperator(ComparisonOperatorType.GREATER_THAN);
		sm.getConditions().add(mc1);
		CompositeMetricContext context2 = MetricFactory.eINSTANCE.createCompositeMetricContext();
		context2.setName("APP_CONTEXT");
		context2.setApplication(app);
		context2.setMetric(b2);
		sm.getContexts().add(context2);
		MetricCondition mc2 = MetricFactory.eINSTANCE.createMetricCondition();
		mc2.setName("COND2");
		mc2.setMetricContext(context2);
		mc2.setThreshold(3.5);
		mc2.setComparisonOperator(ComparisonOperatorType.GREATER_THAN);
		sm.getConditions().add(mc2);
		CompositeMetricContext context3 = MetricFactory.eINSTANCE.createCompositeMetricContext();
		context3.setName("APP_CONTEXT");
		context3.setApplication(app);
		context3.setMetric(b3);
		sm.getContexts().add(context3);
		MetricCondition mc3 = MetricFactory.eINSTANCE.createMetricCondition();
		mc3.setName("COND3");
		mc3.setMetricContext(context3);
		mc3.setThreshold(0.2);
		mc3.setComparisonOperator(ComparisonOperatorType.GREATER_THAN);
		sm.getConditions().add(mc3);
		CompositeMetricContext context4 = MetricFactory.eINSTANCE.createCompositeMetricContext();
		context4.setName("APP_CONTEXT");
		context4.setApplication(app);
		context4.setMetric(a2);
		sm.getContexts().add(context4);
		MetricCondition mc4 = MetricFactory.eINSTANCE.createMetricCondition();
		mc4.setName("COND4");
		mc4.setMetricContext(context4);
		mc4.setThreshold(0.2);
		mc4.setComparisonOperator(ComparisonOperatorType.GREATER_THAN);
		sm.getConditions().add(mc4);
		ServiceLevelObjective slo1 = RequirementFactory.eINSTANCE.createServiceLevelObjective();
		slo1.setName("SLO1");
		slo1.setCustomServiceLevel(mc1);
		rm.getRequirements().add(slo1);
		ServiceLevelObjective slo2 = RequirementFactory.eINSTANCE.createServiceLevelObjective();
		slo2.setName("SLO2");
		slo2.setCustomServiceLevel(mc2);
		rm.getRequirements().add(slo2);
		ServiceLevelObjective slo3 = RequirementFactory.eINSTANCE.createServiceLevelObjective();
		slo3.setName("SLO3");
		slo3.setCustomServiceLevel(mc3);
		rm.getRequirements().add(slo3);
		rg.getRequirements().add(slo1);
		rg.getRequirements().add(slo2);
		rg.getRequirements().add(slo3);
		rg.setRequirementOperator(RequirementOperatorType.AND);
		u.getRequirementModels().add(rm);
		ec.setRequirementGroup(rg);
		MetricApplicationBinding mab = MetricFactory.eINSTANCE.createMetricApplicationBinding();
		mab.setName("MAB");
		mab.setExecutionContext(ec);
		sm.getBindings().add(mab);
		MetricComponentBinding mcb = MetricFactory.eINSTANCE.createMetricComponentBinding();
		mcb.setName("MCB");
		mcb.setExecutionContext(ec);
		mcb.setComponentInstance(compInst);
		sm.getBindings().add(mcb);
		Sensor sensor = MetricFactory.eINSTANCE.createSensor();
		sensor.setName("SENSOR");
		sm.getSensors().add(sensor);
		Schedule schedule1 = MetricFactory.eINSTANCE.createSchedule();
		schedule1.setName("Every 5 secs");
		schedule1.setInterval(5);
		schedule1.setUnit((TimeIntervalUnit)unit);
		schedule1.setType(ScheduleType.FIXED_DELAY);
		sm.getSchedules().add(schedule1);
		RawMetricInstance a_instance1 = MetricFactory.eINSTANCE.createRawMetricInstance();
		a_instance1.setName("A_1");
		a_instance1.setMetric(a);
		a_instance1.setObjectBinding(mcb);
		a_instance1.setSensor(sensor);
		a_instance1.setSchedule(schedule1);
		sm.getMetricInstances().add(a_instance1);
		
		RawMetricInstance c_instance = MetricFactory.eINSTANCE.createRawMetricInstance();
		c_instance.setName("C_3");
		c_instance.setMetric(c);
		c_instance.setObjectBinding(mcb);
		c_instance.setSensor(sensor);
		c_instance.setSchedule(schedule1);		
		sm.getMetricInstances().add(c_instance);
		
		Schedule schedule2 = MetricFactory.eINSTANCE.createSchedule();
		schedule2.setName("Every 1 min");
		schedule2.setInterval(60);
		schedule2.setUnit((TimeIntervalUnit)unit);
		schedule2.setType(ScheduleType.FIXED_DELAY);
		sm.getSchedules().add(schedule2);
		Window window_b1 = MetricFactory.eINSTANCE.createWindow();
		window_b1.setSizeType(WindowSizeType.MEASUREMENTS_ONLY);
		window_b1.setMeasurementSize(12);
		window_b1.setWindowType(WindowType.FIXED);
		window_b1.setName("WINDOW_B1");
		sm.getWindows().add(window_b1);
		CompositeMetricInstance b1_inst = MetricFactory.eINSTANCE.createCompositeMetricInstance();
		b1_inst.setName("B_1");
		b1_inst.setMetric(b1);
		b1_inst.setObjectBinding(mcb);
		b1_inst.getComposingMetricInstances().add(c_instance);
		b1_inst.setWindow(window_b1);
		b1_inst.setSchedule(schedule2);
		sm.getMetricInstances().add(b1_inst);
		
		CompositeMetricInstance b2_inst = MetricFactory.eINSTANCE.createCompositeMetricInstance();
		b2_inst.setName("B_2");
		b2_inst.setMetric(b2);
		b2_inst.setObjectBinding(mcb);
		b2_inst.setSchedule(schedule1);
		b2_inst.getComposingMetricInstances().add(a_instance1);
		sm.getMetricInstances().add(b2_inst);
		
		Window window_a2 = MetricFactory.eINSTANCE.createWindow();
		window_a2.setSizeType(WindowSizeType.MEASUREMENTS_ONLY);
		window_a2.setMeasurementSize(8);
		window_a2.setWindowType(WindowType.SLIDING);
		window_a2.setName("WINDOW_A2");
		sm.getWindows().add(window_a2);
		CompositeMetricInstance a_inst2 = MetricFactory.eINSTANCE.createCompositeMetricInstance();
		a_inst2.setName("A_2");
		a_inst2.setMetric(a2);
		a_inst2.setObjectBinding(mab);
		a_inst2.getComposingMetricInstances().add(b2_inst);
		a_inst2.setWindow(window_a2);
		a_inst2.setSchedule(schedule2);
		sm.getMetricInstances().add(a_inst2);
		
		Schedule schedule3 = MetricFactory.eINSTANCE.createSchedule();
		schedule3.setName("Every 3 min");
		schedule3.setInterval(180);
		schedule3.setUnit((TimeIntervalUnit)unit);
		schedule3.setType(ScheduleType.FIXED_DELAY);
		sm.getSchedules().add(schedule3);
		Window window_b3 = MetricFactory.eINSTANCE.createWindow();
		window_b3.setSizeType(WindowSizeType.TIME_ONLY);
		window_b3.setTimeSize(180);
		window_b3.setUnit((TimeIntervalUnit)unit);
		window_b3.setWindowType(WindowType.FIXED);
		window_b3.setName("WINDOW_B3");
		sm.getWindows().add(window_b3);
		CompositeMetricInstance b3_inst = MetricFactory.eINSTANCE.createCompositeMetricInstance();
		b3_inst.setName("B_3");
		b3_inst.setMetric(b3);
		b3_inst.setObjectBinding(mab);
		b3_inst.getComposingMetricInstances().add(b1_inst);
		b3_inst.setWindow(window_b3);
		b3_inst.setSchedule(schedule3);
		sm.getMetricInstances().add(b3_inst);
		
		ScalabilityModel scalModel = ScalabilityFactory.eINSTANCE.createScalabilityModel();
		scalModel.setName("Scalability Model");
		model.getScalabilityModels().add(scalModel);
		ScalabilityRule sr = ScalabilityFactory.eINSTANCE.createScalabilityRule();
		sr.setName("Scalability Rule");
		scalModel.getRules().add(sr);
		sr.getEntity().add(u);
		NonFunctionalEvent nfe = ScalabilityFactory.eINSTANCE.createNonFunctionalEvent();
		nfe.setName("NonFunctionalEvent");
		nfe.setIsViolation(true);
		nfe.setMetricCondition(mc4);
		//sr.setEvent(nfe);
		scalModel.getEvents().add(nfe);
		UnaryEventPattern uep = ScalabilityFactory.eINSTANCE.createUnaryEventPattern();
		uep.setName("UEP");
		uep.setEvent(nfe);
		uep.setOperator(UnaryPatternOperatorType.REPEAT);
		uep.setOccurrenceNum(4);
		scalModel.getEvents().add(uep);
		sr.setEvent(uep);
		HorizontalScalingAction action = ScalabilityFactory.eINSTANCE.createHorizontalScalingAction();
		action.setName("Action");
		action.setCount(1);
		action.setInternalComponent(ic);
		action.setVm(vm);
		scalModel.getActions().add(action);
		sr.getActions().add(action);
		return model;
	}

	private static void startCDOListeners(String resourceName, ExecutorService es){
		CDOClient cl = new CDOClient();
		CDOView view = cl.openView();
		
		Hashtable<String,Double> metric2Threshold = new Hashtable<String,Double>();
		Hashtable<String,String> instance2Metric = new Hashtable<String,String>();
		
		CDOResource res = view.getResource(resourceName);
		CamelModel cm = (CamelModel)res.getContents().get(0);
		MetricModel sm = cm.getMetricModels().get(0);
		EList<MetricInstance> metricInstances = sm.getMetricInstances();
		for (MetricInstance mi: metricInstances){
			if (mi instanceof CompositeMetricInstance){
				String id = mi.getName();
				instance2Metric.put(mi.getMetric().getName(), id);
			}
		}
		for (Condition cond: sm.getConditions()){
			MetricCondition mc = (MetricCondition)cond;
			Metric m = mc.getMetricContext().getMetric();
			metric2Threshold.put(instance2Metric.get(m.getName()), mc.getThreshold());
		}
		for (String id: metric2Threshold.keySet()){
			logger.info("Starting listener with metricInstanceID: " + id);
			CDOListener listener = new CDOListener(id,metric2Threshold.get(id));
			//cl.addListener(listener);
			es.submit(listener);
		}
		view.close();
		cl.closeSession();
	}
	
	private static void startRawMetricThreads(String resourceName, ExecutorService es){
		CDOClient cl = new CDOClient();
		CDOView view = cl.openView();
		
		CDOResource res = view.getResource(resourceName);
		CamelModel cm = (CamelModel)res.getContents().get(0);
		MetricModel sm = cm.getMetricModels().get(0);
		ExecutionContext ec = cm.getExecutionModels().get(0).getExecutionContexts().get(0);
		EList<MetricInstance> metricInstances = sm.getMetricInstances();
		for (MetricInstance mi: metricInstances){
			if (mi instanceof RawMetricInstance){
				String id = mi.getName();
				Schedule schedule = mi.getSchedule();
				int size = (int)schedule.getInterval();
				TimeIntervalUnit unit = schedule.getUnit();
				int period = findPeriod(unit);
				logger.info("Starting raw metric thread: " + id);
				es.submit(new RandomMeasurementRunnable("raw_measurement_" + id,period * size, id, mi.cdoID(), ec.cdoID(), ec.getApplication().cdoID(),false));
				try{
					Thread.sleep(1000);
				}
				catch(Exception e){
					logger.error("Something went wrong while starting raw metric thread: " + id,e);
					//e.printStackTrace();
				}
			}
		}
		view.close();
		cl.closeSession();
	}
	
	private static void startAggregatedMetricThreads(String resourceName, boolean directCall){
		CDOClient cl = new CDOClient();
		CDOView view = cl.openView();
		
		CDOResource res = view.getResource(resourceName);
		CamelModel cm = (CamelModel)res.getContents().get(0);
		ExecutionModel em = cm.getExecutionModels().get(0);
		ExecutionContext ec = em.getExecutionContexts().get(0);
		MetricModel mm = cm.getMetricModels().get(0);
		//RequirementGroup rg = ec.getRequirementGroup();
		HashSet<CDOID> ids = new HashSet<CDOID>();
		HashSet<CDOID> ids2 = new HashSet<CDOID>();
		ecID = ec.cdoID();
		for (Metric m: mm.getMetrics()){
			if (m instanceof CompositeMetric){
				String name = m.getName();
				MetricInstance mi = view.createQuery("hql", "select mi from MetricInstance mi, Metric m where m.name='" + name + "' and mi.metric=m").getResult(MetricInstance.class).get(0);
				if (name.equals("B3") || name.equals("A2")) ids.add(mi.cdoID());
				else ids2.add(mi.cdoID());
				logger.info("Considering aggregate metric instance: " + mi.cdoID() + " " + mi.getName() + " " + name);
			}
		}
		
		view.close();
		cl.closeSession();
		
		/*CDOID[] idArray = new CDOID[ids.size()];
		Object[] array = ids.toArray();
		for (int i = 0; i < ids.size(); i++){
			idArray[i] = (CDOID)array[i];
			logger.info("First array gets: " + idArray[i]);
		}
		CDOID[] idArray2 = new CDOID[ids2.size()];
		Object[] array2 = ids2.toArray();
		for (int i = 0; i < ids2.size(); i++){
			idArray2[i] = (CDOID)array2[i];
			logger.info("Second array gets: " + idArray2[i]);
		}*/
		//mc1 = new MetricCollector("/home/kyriakos/Desktop/collector-test");
		mc1 = new MetricCollector("input");
		if (!runClient) mc1.readMetrics(ids, ecID);
		try{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("eu.paasage.executionware.metric-collector.command"));
			oos.writeUTF("readMetrics");
			oos.writeObject(ids2);
			oos.writeObject(ecID);
			oos.flush();
			oos.close();
		}
		catch(Exception e){
			logger.error("Something went wrong while writing the metrics to be read by the local Metric Collector",e);
			//e.printStackTrace();
		}
		mc2 = new MetricCollector();
	}
	
	private static void startSubscribers(String resourceName, ExecutorService es){
		CDOClient cl = new CDOClient();
		CDOView view = cl.openView();
		
		CDOResource res = view.getResource(resourceName);
		CamelModel cm = (CamelModel)res.getContents().get(0);
		ExecutionModel em = cm.getExecutionModels().get(0);
		ExecutionContext ec = em.getExecutionContexts().get(0);
		RequirementGroup rg = ec.getRequirementGroup();
		for (Requirement r: rg.getRequirements()){
			if (r instanceof ServiceLevelObjective){
				ServiceLevelObjective slo = (ServiceLevelObjective)r;
				Condition cond = slo.getCustomServiceLevel();
				if (cond instanceof MetricCondition){
					MetricCondition mcond = (MetricCondition)cond;
					eu.paasage.camel.metric.Metric m = mcond.getMetricContext().getMetric();
					MetricInstance mi = view.createQuery("hql", "select mi from MetricInstance mi, Metric m where m.name='" + m.getName() + "' and mi.metric=m").getResult(MetricInstance.class).get(0);
					es.submit(new SubscriptionClient(mi.getName()));
				}
			}
		}
		
		view.close();
		cl.closeSession();
	}

	
	private static int findPeriod(TimeIntervalUnit unit){
		int period = 1;
		UnitType u = unit.getUnit();
		if (u.equals(UnitType.SECONDS)) period = period * 1000;
		else if (u.equals(UnitType.MINUTES)) period = period * 60000;
		else if (u.equals(UnitType.HOURS)) period = period * 3600000;
		return period;
	}
	
	private static void deleteAllMetrics(CDOID ecID){
		try{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("eu.paasage.executionware.metric-collector.command"));
			oos.writeUTF("deleteMetrics");
			oos.writeObject(ecID);
			oos.flush();
			oos.close();
		}
		catch(Exception e){
			logger.error("Something went wrong while attempting to inform the local MetricCollector in a file-based way about the deletion of metrics",e);
			//e.printStackTrace();
		}
	}
	
	private static Set<CDOID> getTopMetrics(String resourceName){
		CDOClient cl = new CDOClient();
		CDOView view = cl.openView();
		CDOResource res = view.getResource(resourceName);
		CamelModel cm = (CamelModel)res.getContents().get(0);
		ExecutionContext ec = cm.getExecutionModels().get(0).getExecutionContexts().get(0);
		CDOID ecID = ec.cdoID();
		Set<CDOID> metricIDs = CDOUtils.getTopMetrics(view, ecID);
		for (CDOID id: metricIDs) logger.info("Got top-level metric instance: " + id);
		view.close();
		return metricIDs;
	}
	
	private static Set<CDOID> getGlobalMetrics(Set<CDOID> ids){
		CDOClient cl = new CDOClient();
		CDOView view = cl.openView();
		Set<CDOID> metricIDs = CDOUtils.getGlobalMetrics(ids,view);
		for (CDOID id: metricIDs) logger.info("Got global top-level metric instance: " + id);
		view.close();
		return metricIDs;
	}
	
	private static void runExcessiveTest(CDOID id){
		ExecutorService lis = Executors.newFixedThreadPool(210);
		long time = System.currentTimeMillis();
		for (int i = 0; i < 210; i++) lis.submit(new StorageRunnable(id,i,150));
		lis.shutdown();
		try{
			lis.awaitTermination(120, TimeUnit.SECONDS);
		}
		catch(Exception e){
			logger.error("Something went wrong while waiting the termination of 210 Storage Runnable threads",e);
			//e.printStackTrace();
		}
		logger.info("Time elapsed is (ms): " + (System.currentTimeMillis()-time));
		CDOClient cl = new CDOClient();
		CDOView view = cl.openView();
		for (int i = 0; i < 5; i++)
			logger.info("The number of measurements stored for the: " + i + "th thread are: " + view.createQuery("hql","select count(m) from Measurement m where m.value=" + (double)i).getResult(Integer.class).get(0));
		logger.info("The total number of measurements is: " + view.createQuery("hql","select count(m) from Measurement m").getResult(Integer.class).get(0));
		view.close();
		cl.closeSession();
	}
	
	/* Testing the MetricCollector */
	public static void testMetricCollector(String firstArg){
		if (firstArg != null) runClient = Boolean.parseBoolean(firstArg);
		EObject model = createTestModel();
		CDOClient client = new CDOClient();
		CDOTransaction trans = client.openTransaction();
		CDOResource res = trans.getOrCreateResource("test");
		res.getContents().add(model);
		try{
			trans.commit();
		}
		catch(Exception e){
			logger.error("Something went wrong while attempting to store the test model in CDO",e);
			//e.printStackTrace();
		}
		trans.close();
		client.closeSession();
		Set<CDOID> ids = getTopMetrics("test");
		Set<CDOID> globalMetrics = getGlobalMetrics(ids);
		
		ExecutorService thr = Executors.newFixedThreadPool(2);
		startRawMetricThreads("test", thr);
		ExecutorService subscribers = Executors.newFixedThreadPool(3);
		startSubscribers("test", subscribers);
		try{
			Thread.sleep(3000);
		}
		catch(Exception e){
			logger.error("Something went wrong while main thread was sleeping",e);
			//e.printStackTrace();
		}
		startAggregatedMetricThreads("test",true);
		FakeAdapterPublisher fap = null;
		if (runClient){
			fap = new FakeAdapterPublisher(ecID);
			new Thread(fap).start();
		}
		 
		//CDOID ecID = mc.idToECH.keys().nextElement();
		try{
			Thread.sleep(200000);
		}
		catch(Exception e){
			logger.error("Something went wrong while main thread was sleeping",e);
			//e.printStackTrace();
		}
		
		thr.shutdownNow();
		subscribers.shutdown();
		if (runClient) fap.terminate();
		deleteAllMetrics(ecID);
		
		mc1.terminate();
		mc2.terminate();
	}

	/* Test whether measurements are stored in CDO and whether publishing in 0MQ takes place */
	public static void testCDOStoragePublishing(String firstArg){
		if (firstArg != null) runClient = Boolean.parseBoolean(firstArg);
		EObject model = createTestModel();
		CDOClient client = new CDOClient();
		CDOTransaction trans = client.openTransaction();
		CDOResource res = trans.getOrCreateResource("test");
		res.getContents().add(model);
		try{
			trans.commit();
		}
		catch(Exception e){
			logger.error("Something went wrong while attempting to store the test model in CDO",e);
			//e.printStackTrace();
		}
		trans.close();
		client.closeSession();
		Set<CDOID> ids = getTopMetrics("test");
		Set<CDOID> globalMetrics = getGlobalMetrics(ids);
		/* Running multiple measurements storage test
		 * If normal MetricCollector test has to be executed then:
		 * (a) comment next line code and (b) uncomment the last
		 * commented lines of code
		 */
		MetricStorage.initServer(1, 5563);
		runExcessiveTest(ids.iterator().next());
		ExecutorService lis = Executors.newFixedThreadPool(3);
		startCDOListeners("test",lis);
		
		deleteAllMetrics(ecID);
		lis.shutdownNow();
		MetricStorage.terminateServer();
	}
	
	public static void main(String[] args){
		String firstArg = null;
		if (args.length != 0) firstArg = args[0];
		//testMetricCollector(firstArg);
		testCDOStoragePublishing(firstArg);
	}
}
