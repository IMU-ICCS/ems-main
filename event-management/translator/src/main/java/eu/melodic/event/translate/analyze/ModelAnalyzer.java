/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.translate.analyze;

import camel.constraint.Constraint;
import camel.constraint.IfThenConstraint;
import camel.constraint.LogicalConstraint;
import camel.constraint.MetricConstraint;
import camel.constraint.MetricVariableConstraint;
import camel.core.Action;
import camel.core.CamelModel;
import camel.core.CorePackage;
import camel.core.Feature;
import camel.core.MeasurableAttribute;
import camel.core.NamedElement;
import camel.data.Data;
import camel.deployment.Component;
import camel.metric.CompositeMetric;
import camel.metric.CompositeMetricContext;
import camel.metric.Function;
import camel.metric.GroupingType;
import camel.metric.Metric;
import camel.metric.MetricContext;
import camel.metric.MetricTemplate;
import camel.metric.MetricTypeModel;
import camel.metric.MetricVariable;
import camel.metric.ObjectContext;
import camel.metric.RawMetric;
import camel.metric.RawMetricContext;
import camel.metric.Schedule;
import camel.metric.Sensor;
import camel.metric.Window;
import camel.requirement.OptimisationRequirement;
import camel.requirement.ServiceLevelObjective;
import camel.requirement.Requirement;
import camel.requirement.RequirementModel;
import camel.scalability.BinaryEventPattern;
import camel.scalability.UnaryEventPattern;
import camel.scalability.Event;
import camel.scalability.NonFunctionalEvent;
import camel.scalability.ScalabilityModel;
import camel.scalability.ScalabilityRule;
import camel.scalability.ScalingAction;
import camel.type.ValueType;
import camel.type.*;
import camel.unit.Unit;

import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;

import eu.melodic.event.translate.TranslationContext;
import eu.melodic.event.translate.analyze.DAGNode;
import eu.melodic.event.translate.properties.CamelToEplTranslatorProperties;

import eu.melodic.models.interfaces.ems.Monitor;
import eu.melodic.models.interfaces.ems.MonitorImpl;
import eu.melodic.models.interfaces.ems.Interval;
import eu.melodic.models.interfaces.ems.IntervalImpl;
import eu.melodic.models.interfaces.ems.KeyValuePair;
import eu.melodic.models.interfaces.ems.KeyValuePairImpl;
import eu.melodic.models.interfaces.ems.PullSensor;
import eu.melodic.models.interfaces.ems.PullSensorImpl;
import eu.melodic.models.interfaces.ems.PushSensor;
import eu.melodic.models.interfaces.ems.PushSensorImpl;
import eu.melodic.models.interfaces.ems.Sink;
import eu.melodic.models.interfaces.ems.SinkImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

import org.eclipse.emf.cdo.eresource.CDOResource;
//import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

@Slf4j
public class ModelAnalyzer {
	private CamelToEplTranslatorProperties properties;
	
	// ================================================================================================================
	// Public API
	
	public void analyzeModel(TranslationContext _TC, String leafGrouping, CamelModel camelModel, CamelToEplTranslatorProperties properties) {
		log.debug("ModelAnalyzer.analyzeModel():  Analyzing models...");
		
		this.properties = properties;
		
		// building Element-to-Name map
		_buildElementNamesMap(_TC, camelModel);
		log.debug("ModelAnalyzer.analyzeModel():  Populating Element-to-Names map completed");
		
		// building Metric-to-Metric Context map
		_buildMetricToMetricContextMap(_TC, camelModel);
		log.debug("ModelAnalyzer.analyzeModel():  Populating Metric-to-Metric Context map completed");
		
		// building MVV set
		_buildMVVSet(_TC, camelModel);
		log.debug("ModelAnalyzer.analyzeModel():  Populating MVV set completed");
		
		// extract Functions
		_extractFunctions(_TC, camelModel);
		log.debug("ModelAnalyzer.analyzeModel():  Extracting Functions completed");
		
		// analyze scalability rules
		_analyzeScalabilityRules(_TC, camelModel);
		log.debug("ModelAnalyzer.analyzeModel():  Scalability Model analysis completed");
		
		// analyze optimisation requirements
		_analyzeOptimisationRequirements(_TC, camelModel);
		log.debug("ModelAnalyzer.analyzeModel():  Optimisation Requirements analysis completed");
		
		// analyze service level objectives
		_analyzeServiceLevelObjectives(_TC, camelModel);
		log.debug("ModelAnalyzer.analyzeModel():  Service Level Objectives analysis completed");
		
		// analyze metric variables
		_analyzeMetricVariables(_TC, camelModel);
		log.debug("ModelAnalyzer.analyzeModel():  Metric Variables analysis completed");
		
		// infer groupings
		_inferGroupings(_TC, leafGrouping);
		log.debug("ModelAnalyzer._inferGroupings():  Grouping inferencing completed");
		
		_TC.DAG.traverseDAG(node -> {
			log.trace("------------> DAG node:{}, grouping:{}, id:{}, hash:{}", node, node.getGrouping(), node.getId(), node.hashCode());
		});
	}
	
	// ================================================================================================================
	// Model analysis methods
	
	private long elementCounter;
	
	protected void _buildElementNamesMap(TranslationContext _TC, CamelModel camelModel) {
		log.info("_buildElementNamesMap():  Extracting element names from all Models of CAMEL model...");
		
		java.util.Iterator it = camelModel.eResource().getAllContents();
		List<Class> searchClasses = java.util.Arrays.asList(
			camel.scalability.ScalabilityRule.class, camel.scalability.EventPattern.class, camel.scalability.EventInstance.class,
			camel.scalability.Event.class, camel.scalability.ScalingAction.class, camel.scalability.Timer.class,
			camel.requirement.Requirement.class, camel.constraint.Constraint.class, 
			camel.metric.MetricContext.class, camel.metric.AttributeContext.class, camel.metric.ObjectContext.class, 
			camel.metric.Window.class, camel.metric.Schedule.class, camel.metric.Sensor.class, camel.metric.Function.class, 
			camel.metric.MetricTemplate.class, camel.metric.MetricObjectBinding.class,
			camel.metric.MetricInstance.class, camel.metric.Metric.class
		);
		log.warn("_buildElementNamesMap():  Search classes: {}", searchClasses);
		
		elementCounter = 0;
		while (it.hasNext()) {
			org.eclipse.emf.ecore.EObject item = (org.eclipse.emf.ecore.EObject) it.next();
			if (item!=null) {
				if (item instanceof NamedElement) {
					NamedElement elem = (NamedElement) item;
					String elemName = elem.getName();
					Class elemClass = elem.getClass();
					String elemClassName = elem.getClass().getName();
					log.trace("_buildElementNamesMap():  named-element={}, class={}", elemName, elemClassName);
					
					boolean found = false;
					Class foundClass = null;
					for (Class c : searchClasses) {
						if (c.isAssignableFrom(elemClass)) {
							log.trace("_buildElementNamesMap():  Found Named-Element: {}, class={}", elemName, c.getSimpleName());
							if (found) {
								log.error("_buildElementNamesMap():  More than one search classes match to Named-Element: {}, class-1: {}, class-2: {}", elemName, foundClass.getName(), c.getName());
								//throw new ModelAnalysisException( String.format("More than one search classes match to Named-Element: %s, class-1: %s, class-2: %s", elemName, foundClass.getName(), c.getName()) );
							} else {
								found = true;
								foundClass = c;
							}
						}
					}
					if (found) {
						String elemType = _getElementType(elem);
						String fullName = String.format("%s__%s__%s__%d", elemType, camelModel.getName(), elem.getName(), elementCounter++);
						_TC.addElementName(elem, fullName);
						log.debug("_buildElementNamesMap():  named-element={}, type={}, full-name={}", elemName, elemType, fullName);
					} else {
						log.trace("_buildElementNamesMap():  No search classes matches to Named-Element: {}, element-class: {}", elemName, elemClassName);
					}
				}
				//else { log.trace("_buildElementNamesMap():  item-class={}, item={}", item.getClass().getName(), item); }
			}
			//else { log.trace("_buildElementNamesMap():  item: NULL"); }
		}
		
		log.info("_buildElementNamesMap():  Element-to-Names map: {}", _TC.E2N);
	}
	
	protected String _getElementType(NamedElement e) {
		Class c = e.getClass();
		if (false) ;
		else if (ScalabilityRule.class.isAssignableFrom(c)) return "RUL";
		else if (Event.class.isAssignableFrom(c)) return "EVT";
		else if (Constraint.class.isAssignableFrom(c)) return "CON";
		else if (MetricVariable.class.isAssignableFrom(c)) return "VAR";
		else if (MetricContext.class.isAssignableFrom(c)) return "CTX";
		else if (Metric.class.isAssignableFrom(c)) return "MET";
		else if (MetricTemplate.class.isAssignableFrom(c)) return "TMP";
		else if (OptimisationRequirement.class.isAssignableFrom(c)) return "OPT";
		else if (ServiceLevelObjective.class.isAssignableFrom(c)) return "SLO";
		else if (Requirement.class.isAssignableFrom(c)) return "REQ";
		else if (ObjectContext.class.isAssignableFrom(c)) return "OBJ";
		else if (Sensor.class.isAssignableFrom(c)) return "SNR";
		else if (Function.class.isAssignableFrom(c)) return "FUN";
		else if (Schedule.class.isAssignableFrom(c)) return "CTX";
		else if (Window.class.isAssignableFrom(c)) return "CTX";
		else if (ScalingAction.class.isAssignableFrom(c)) return "ACT";
		else {
			//throw new ModelAnalysisException( String.format("Unknown element type: %s  class=%s", e.getName(), e.getClass().getName()) );
			log.error("Unknown element type: {}  class={}", e.getName(), e.getClass().getName());
		}
		return "XXX";
	}
	
	protected void _buildMetricToMetricContextMap(TranslationContext _TC, CamelModel camelModel) {
		// extract metric models
		log.info("  Extracting Metric Type Models from CAMEL model...");
		List<MetricTypeModel> metricModels = camelModel.getMetricModels().stream()
				.filter(mm -> MetricTypeModel.class.isAssignableFrom(mm.getClass()))
				.map( mm -> (MetricTypeModel)mm )
				.collect(Collectors.toList());
		log.info("  Extracting Metric Type Models from CAMEL model... {}", metricModels);
		
		// for every metric type model...
		metricModels.stream().forEach(mm -> {
			// get metric contexts
			log.info("  Extracting Metric Contexts from Metric Type model {}...", mm.getName());
			EList<MetricContext> contexts = mm.getMetricContexts();
			log.info("  Extracting Metric Contexts from Metric Type model {}... {}", mm.getName(), contexts);
			
			// for every metric context...
			contexts.stream().forEach(mc -> {
				// get metric
				Metric metric = mc.getMetric();
				ObjectContext objContext = mc.getObjectContext();
				log.info("  Metric-Context: {}.{}.{} == {} --> {}", camelModel.getName(), mm.getName(), mc.getName(), getElementName(metric), getElementName(objContext));
				
				// update _TC.M2MC map
				_TC.addMetricMetricContextPair(metric, mc);
			});
		});
	}
	
	protected void _buildMVVSet(TranslationContext _TC, CamelModel camelModel) {
		// extract metric models
		log.info("  Extracting Metric Type Models from CAMEL model...");
		List<MetricTypeModel> metricModels = camelModel.getMetricModels().stream()
				.filter(mm -> MetricTypeModel.class.isAssignableFrom(mm.getClass()))
				.map( mm -> (MetricTypeModel)mm )
				.collect(Collectors.toList());
		log.info("  Extracting Metric Type Models from CAMEL model... {}", metricModels);
		
		// for every metric type model...
		metricModels.stream().forEach(mm -> {
			// get metric variables
			log.info("  Extracting Metric Variables from Metric Type model {}...", mm.getName());
			List<MetricVariable> variables = mm.getMetrics().stream()
					.filter(met -> MetricVariable.class.isAssignableFrom(met.getClass()))
					.map( met -> (MetricVariable)met )
					.collect(Collectors.toList());
			log.info("  Extracting Metric Variables from Metric Type model {}... {}", mm.getName(), variables);
			
			// for every metric variable...
			variables.stream().forEach(mv -> {
				// get component metrics
				EList<Metric> componentMetrics = mv.getComponentMetrics();
				Component component = mv.getComponent();
				log.info("  Metric-Variable: {}.{}.{} :: component-metrics={}, component={}", camelModel.getName(), mm.getName(), mv.getName(), getListElementNames(componentMetrics), getElementName(component));
				
				// update _TC.MVV set
				if (componentMetrics.size()==0) {
					log.info("  Found MVV: {}.{}.{}", camelModel.getName(), mm.getName(), mv.getName());
					_TC.addMVV(mv);
				}
			});
		});
	}
	
	protected void _extractFunctions(TranslationContext _TC, CamelModel camelModel) {
		// extract metric models
		log.info("  Extracting Metric Type Models from CAMEL model...");
		List<MetricTypeModel> metricModels = camelModel.getMetricModels().stream()
				.filter(mm -> MetricTypeModel.class.isAssignableFrom(mm.getClass()))
				.map( mm -> (MetricTypeModel)mm )
				.collect(Collectors.toList());
		log.info("  Extracting Metric Type Models from CAMEL model... {}", metricModels);
		
		// for every metric type model...
		metricModels.stream().forEach(mm -> {
			// get Function definitions
			log.info("  Extracting Functions from Metric Type model {}...", mm.getName());
			EList<Function> functions = mm.getFunctions();
			log.info("  Extracting Functions from Metric Type model {}... {}", mm.getName(), functions);
			
			// for every metric context...
			functions.stream().forEach(f -> {
				// get expression and parameters
				String expression = f.getExpression();
				EList<String> arguments = f.getArguments();
				log.info("  Function: {}.{}.{} == {} --> {}", camelModel.getName(), mm.getName(), f.getName(), expression, arguments);
				
				// update _TC.FUNC set
				_TC.addFunction(f);
			});
		});
	}
	
	protected void _analyzeScalabilityRules(TranslationContext _TC, CamelModel camelModel) {
		// extract scalability rules
		log.info("  Extracting Scalability Models from CAMEL model...");
		EList<ScalabilityModel> scalabilityModels = camelModel.getScalabilityModels();
		log.info("  Extracting Scalability Models from CAMEL model... {}", scalabilityModels);
		
		scalabilityModels.stream().forEach(sm -> {
			log.info("  Extracting Scalability Rules from Scalability model {}...", sm.getName());
			EList<ScalabilityRule> rules = sm.getRules();
			log.info("  Extracting Scalability Rules from Scalability model {}... {}", sm.getName(), rules);
			rules.stream().forEach(rule -> {
				String ruleName = rule.getName();
				Event ruleEvent = rule.getEvent();
				EList<Action> ruleActions = rule.getActions();
				log.info("RULE: {}.{}.{} == {} --> {}", camelModel.getName(), sm.getName(), ruleName, ruleEvent.getName(), ruleActions);
				
				// add event-action pair to E2A map
				_TC.addEventActionPairs(ruleEvent, ruleActions);
				// add event to DAG as top-level nodes
				_TC.DAG.addTopLevelNode( ruleEvent ).setGrouping( getGrouping(ruleEvent) );
				
				// decompose event
				_decomposeEvent(_TC, ruleEvent);
			});
		});
	}
	
	protected void _analyzeOptimisationRequirements(TranslationContext _TC, CamelModel camelModel) {
		// extract requirement models
		log.info("  Extracting Requirement Models from CAMEL model...");
		EList<RequirementModel> requirementModels = camelModel.getRequirementModels();
		log.info("  Extracting Requirement Models from CAMEL model... {}", requirementModels);
		
		// for each requirement model...
		requirementModels.stream().forEach(rm -> {
			//List<Requirement> requirements = rm.getRequirements();
			//log.info("  Extracting Requirements from Requirements model {}... {}", rm.getName(), requirements);
			
			// extract optimisation requirements
			log.info("  Extracting Optimisation Requirements from Requirements model {}...", rm.getName());
			List<OptimisationRequirement> optimisations = rm.getRequirements().stream()
				.filter( req -> OptimisationRequirement.class.isAssignableFrom(req.getClass()) )
				.map( req -> OptimisationRequirement.class.cast(req) )
				.collect(Collectors.toList());
			log.info("  Extracting Optimisation Requirements from Requirements model {}... {}", rm.getName(), optimisations);
			
			// for each optimisation requirement...
			optimisations.stream().forEach(optr -> {
				// extract metric context and variable
				String reqName = optr.getName();
				MetricContext mc = optr.getMetricContext();
				MetricVariable mv = optr.getMetricVariable();
				log.info("  Processing Optimisation Requirement {} from Requirements model {}: metric-context={}, metric-variable={}...",
						optr.getName(), rm.getName(), getElementName(mc), getElementName(mv));
				
				// update DAG and decompose metric context
				if (mc!=null) {
					// add metric context to DAG as top-level node
					_TC.DAG.addTopLevelNode( mc ).setGrouping( getGrouping(mc) );
					
					// decompose metric context
					_decomposeMetricContext(_TC, mc);
				}
				
				// update DAG and decompose metric variable
				if (mv!=null) {
					// add metric variable to DAG as top-level node
					_TC.DAG.addTopLevelNode( mv ).setGrouping( getGrouping(mv) );
					
					// decompose metric variable
					_decomposeMetricVariable(_TC, mv);
				}
			});
		});
	}
	
	protected void _analyzeServiceLevelObjectives(TranslationContext _TC, CamelModel camelModel) {
		// extract requirement models
		log.info("  Extracting Requirement Models (for SLO) from CAMEL model...");
		EList<RequirementModel> requirementModels = camelModel.getRequirementModels();
		log.info("  Extracting Requirement Models (for SLO) from CAMEL model... {}", requirementModels);
		
		// for each requirement model...
		requirementModels.stream().forEach(rm -> {
			// extract Service Level Objectives
			log.info("  Extracting Service Level Objectives from Requirements model {}...", rm.getName());
			List<ServiceLevelObjective> slos = rm.getRequirements().stream()
				.filter( req -> ServiceLevelObjective.class.isAssignableFrom(req.getClass()) )
				.map( req -> ServiceLevelObjective.class.cast(req) )
				.collect(Collectors.toList());
			log.info("  Extracting Service Level Objectives from Requirements model {}... {}", rm.getName(), slos);
			
			// for each Service Level Objective...
			slos.stream().forEach(slo -> {
				// extract metric context and variable
				String sloName = slo.getName();
				Constraint sloConstr = slo.getConstraint();
				Event sloEvent = slo.getViolationEvent();
				log.info("  Processing Service Level Objective {} from Requirements model {}: constraint={}, violation-event={}...",
						slo.getName(), rm.getName(), getElementName(sloConstr), getElementName(sloEvent));
				
				// add SLO to SLO set
				_TC.addSLO(slo);
				// add event to DAG as top-level nodes
				_TC.DAG.addTopLevelNode( slo ).setGrouping( getGrouping(slo) );
				
				// update DAG and decompose metric constraint
				if (sloConstr!=null) {
					// add SLO constraint to DAG
					_TC.DAG.addNode(slo, sloConstr).setGrouping( getGrouping(sloConstr) );
					
					// decompose constraint
					_decomposeConstraint(_TC, sloConstr);
				}
				
				if (sloEvent!=null) {
					//XXX: TODO: ???: ++++++++++++++++++++
				}
			});
		});
	}
	
	protected void _analyzeMetricVariables(TranslationContext _TC, CamelModel camelModel) {
		// extract requirement models
		log.info("  Extracting Metric Type Models from CAMEL model...");
		List<MetricTypeModel> metricModels = camelModel.getMetricModels().stream()
				.filter(mm -> MetricTypeModel.class.isAssignableFrom(mm.getClass()))
				.map( mm -> (MetricTypeModel)mm )
				.collect(Collectors.toList());
		log.info("  Extracting Metric Type Models from CAMEL model... {}", getListElementNames(metricModels));
		
		// for every metric type model...
		metricModels.stream().forEach(mm -> {
			// get metric variables
			log.info("  Extracting Metric Variables from Metric Type model {}...", mm.getName());
			List<MetricVariable> variables = mm.getMetrics().stream()
					.filter(met -> MetricVariable.class.isAssignableFrom(met.getClass()))
					.map( met -> (MetricVariable)met )
					.collect(Collectors.toList());
			log.info("  Extracting Metric Variables from Metric Type model {}... {}", mm.getName(), getListElementNames(variables));
			
			// for every metric variable...
			variables.stream().forEach(mv -> {
				// extract metric variable information
				String mvName = mv.getName();
				MetricTemplate template = mv.getMetricTemplate();
				boolean isCurrConfig = mv.isCurrentConfiguration();
				boolean isOnNodeCand = mv.isOnNodeCandidates();
				Component component = mv.getComponent();
				String formula = mv.getFormula();
				EList<Metric> componentMetrics = mv.getComponentMetrics();
				boolean containsMetrics = (componentMetrics!=null && componentMetrics.size()>0);
				log.info("  Processing Metric Variable {} from Metric Type model {}: template={}, is-current-configuration={}, is-on-node-candidates={}, component={}, formula={}, component-metrics={}, contains-metrics={}...",
						mvName, mm.getName(), getElementName(template), isCurrConfig, isOnNodeCand, getElementName(component), formula, getListElementNames(componentMetrics), containsMetrics);
				
				if ((formula==null || formula.trim().isEmpty()) && containsMetrics) {
					log.error("  Metric Variable has component metrics but formula IS EMPTY: {}", mvName);
					throw new ModelAnalysisException( String.format("Metric Variable has component metrics but formula IS EMPTY: %s", mvName) );
				}
				if (formula!=null && !formula.trim().isEmpty() && !containsMetrics) {
					log.warn("  Metric Variable has NO component metrics: name={}, formula={}", mvName, formula);
				}
				
				if (componentMetrics.size()>0) {
					// add metric variable to DAG as top-level node
					_TC.DAG.addTopLevelNode( mv ).setGrouping( getGrouping(mv) );
				} else {
					// if MVV just register it in _TC
					_TC.addMVV(mv);
				}
				
				// for every component metric
				componentMetrics.stream().forEach(m -> {
					// get metric context or variable of current metric
					Set<MetricContext>  ctxSet = _TC.M2MC.get(m);
					int ctxSize = (ctxSet==null ? 0 : ctxSet.size());
					boolean isMVV = _TC.MVV.contains(m.getName());
					MetricVariable mvv = isMVV ? (MetricVariable)m : null;
					
					if (ctxSize==0 && !isMVV) {
						log.error("  - No metric context or variable found for metric '{}' used in the metric variable '{}' : ctx-set={}, is-MVV={}", m.getName(), mv.getName(), getSetElementNames(ctxSet), isMVV);
						log.error("  _TC.M2MC: keys: {}", getSetElementNames(_TC.M2MC.keySet()));
						log.error("  _TC.M2MC: values: {}", _TC.M2MC.values());
						log.error("  _TC.MVV: {}", getSetElementNames(_TC.MVV));
						throw new ModelAnalysisException( String.format("No metric context or MVV found for metric '%s' used in the metric variable '%s'", m.getName(), mv.getName()) );
					} else
					if (ctxSize>0 && isMVV || ctxSize>1 && !isMVV) {
						List<String> ctxNames = ctxSet.stream().map( mc -> mc.getName() ).collect(Collectors.toList());
						log.error("  - More than one metric contexts and variables were found for metric '{}' used in the metric variable '{}' : ctx-names={}, is-MVV={}", m.getName(), mv.getName(), ctxNames, isMVV);
						log.error("  _TC.M2MC: keys: {}", getSetElementNames(_TC.M2MC.keySet()));
						log.error("  _TC.M2MC: values: {}", _TC.M2MC.values());
						log.error("  _TC.MVV: {}", getSetElementNames(_TC.MVV));
						throw new ModelAnalysisException( String.format("More than one metric contexts and MVVs were found for metric '%s' used in the metric variable '%s': ctx-names=%s, is-MVV=%b", m.getName(), mv.getName(), ctxNames.toString(), isMVV) );
					} else
					if (ctxSize==1 && !isMVV) {
						MetricContext ctx = ctxSet.iterator().next();
						
						// update DAG and decompose metrics
						if (ctx!=null) {
							// add CTX to DAG
							_TC.DAG.addNode(mv, ctx).setGrouping( getGrouping(ctx) );
							
							// decompose metric context
							_decomposeMetricContext(_TC, ctx);
							
						} else {
							//log.error("  - Metric context for metric '{}' used in the metric variable '{}' is null", m.getName(), mv.getName());
							throw new ModelAnalysisException( String.format("Metric context for metric '%s' used in the metric variable '%s' is null", m.getName(), mv.getName()) );
						}
						
					} else
					if (ctxSize==0 && isMVV) {
						// add MVV to DAG
						//_TC.DAG.addNode(mv, mvv).setGrouping( getGrouping(mvv) );
						log.debug("  Component metric is MVV. No DAG node will be added: mvv={}, variable={}", m.getName(), mv.getName());
					} else {
						log.error("IMPLEMENTATION ERROR: Code must never reach this point");
						throw new ModelAnalysisException("IMPLEMENTATION ERROR: Code must never reach this point");
					}
				});
			});
		});
	}
	
	protected void _decomposeEvent(TranslationContext _TC, Event event) {
		log.info("  _decomposeEvent(): {} :: {}", event.getName(), event.getClass().getName());
		
		// decompose event
		if (BinaryEventPattern.class.isInstance(event)) {
			BinaryEventPattern be = (BinaryEventPattern) event;
			String op = be.getOperator().getName();
			Event lEvent = be.getLeftEvent();
			Event rEvent = be.getRightEvent();
			log.info("  _decomposeEvent(): BinaryEventPattern: {} ==> {} {} {} ", be.getName(), lEvent.getName(), op, rEvent.getName());
			
			log.info("  _decomposeEvent(): Adding left event to DAG: {}, parent={}", lEvent.getName(), be.getName());
			_TC.DAG.addNode(be, lEvent).setGrouping( getGrouping(lEvent) );
			log.info("  _decomposeEvent(): Adding right event to DAG: {}, parent={}", rEvent.getName(), be.getName());
			_TC.DAG.addNode(be, rEvent).setGrouping( getGrouping(rEvent) );
			
			log.info("  _decomposeEvent(): Decomposing left event: {}", lEvent.getName());
			_decomposeEvent(_TC, lEvent);
			log.info("  _decomposeEvent(): Decomposing right event: {}", rEvent.getName());
			_decomposeEvent(_TC, rEvent);
		} else
		if (UnaryEventPattern.class.isInstance(event)) {
			UnaryEventPattern ue = (UnaryEventPattern) event;
			String op = ue.getOperator().getName();
			Event uEvent = ue.getEvent();
			log.info("  _decomposeEvent(): UnaryEventPattern: {} ==> {} {} ", ue.getName(), op, uEvent.getName());
			
			_TC.DAG.addNode(event, uEvent).setGrouping( getGrouping(uEvent) );
			
			_decomposeEvent(_TC, uEvent);
		} else
		if (NonFunctionalEvent.class.isInstance(event)) {
			NonFunctionalEvent nfe = (NonFunctionalEvent) event;
			MetricConstraint constr = nfe.getMetricConstraint();
			boolean isViolation = nfe.isIsViolation();
			log.info("  _decomposeEvent(): NonFunctionalEvent: {} ==> {}{} ", nfe.getName(), isViolation?"VIOLATION OF ":"", constr.getName());
			
			_TC.DAG.addNode(event, constr).setGrouping( getGrouping(constr) );
			
			_decomposeMetricConstraint(_TC, constr);
		} else
		{
			throw new ModelAnalysisException( String.format("Invalid event type occurred: %s  class=%s", event.getName(), event.getClass().getName()) );
		}
	}
	
	protected void _decomposeConstraint(TranslationContext _TC, Constraint constraint) {
		log.info("  _decomposeConstraint(): {} :: {}", constraint.getName(), constraint.getClass().getName());
		if (MetricConstraint.class.isAssignableFrom(constraint.getClass())) {
			_decomposeMetricConstraint(_TC, (MetricConstraint) constraint);
		} else
		if (IfThenConstraint.class.isAssignableFrom(constraint.getClass())) {
			throw new ModelAnalysisException("FEATURE NOT IMPLEMENTED");	//XXX: TODO: ++++++++++++++++
		} else
		if (MetricVariableConstraint.class.isAssignableFrom(constraint.getClass())) {
			_decomposeMetricVariableConstraint(_TC, (MetricVariableConstraint)constraint);
		} else
		if (LogicalConstraint.class.isAssignableFrom(constraint.getClass())) {
			throw new ModelAnalysisException("FEATURE NOT IMPLEMENTED");	//XXX: TODO: ++++++++++++++++
		} else
		{
			throw new ModelAnalysisException( String.format("Invalid Constraint type occurred: %s  class=%s", constraint.getName(), constraint.getClass().getName()) );
		}
	}
	
	protected void _decomposeMetricConstraint(TranslationContext _TC, MetricConstraint constraint) {
		log.info("  _decomposeMetricConstraint(): {} :: {}", constraint.getName(), constraint.getClass().getName());
		java.util.Date validity = constraint.getValidity();
		String op = constraint.getComparisonOperator().getName();
		double threshold = constraint.getThreshold();
		MetricContext context = constraint.getMetricContext();
		log.info("  _decomposeMetricConstraint(): {} ==> {} {} {}  validity: {}", constraint.getName(), context.getName(), op, threshold, validity);
		
		_TC.DAG.addNode(constraint, context).setGrouping( getGrouping(context) );
		
		_decomposeMetricContext(_TC, context);
	}
	
	protected void _decomposeMetricVariableConstraint(TranslationContext _TC, MetricVariableConstraint constraint) {
		log.info("  _decomposeMetricVariableConstraint(): {} :: {}", constraint.getName(), constraint.getClass().getName());
		java.util.Date validity = constraint.getValidity();
		String op = constraint.getComparisonOperator().getName();
		double threshold = constraint.getThreshold();
		MetricVariable mvar = constraint.getMetricVariable();
		log.info("  _decomposeMetricVariableConstraint(): {} ==> {} {} {}  validity: {}", constraint.getName(), mvar.getName(), op, threshold, validity);
		
		_TC.DAG.addNode(constraint, mvar).setGrouping( Grouping.GLOBAL );
		
		_decomposeMetricVariable(_TC, mvar);
	}
	
	protected void _decomposeMetricVariable(TranslationContext _TC, MetricVariable mvar) {
		boolean hasMetricsWithCtx = ___decomposeMetricVariable(_TC, mvar);
		if (! hasMetricsWithCtx) {
			log.warn("  _decomposeMetricVariable(): Pruning from DAG metric variable with no measurable component metrics: name={}", mvar.getName());
			_TC.DAG.removeNode(mvar);
		}
	}
	
	protected boolean ___decomposeMetricVariable(TranslationContext _TC, MetricVariable mvar) {
		log.info("  _decomposeMetricVariable(): {} :: {}", mvar.getName(), mvar.getClass().getName());
		
		// Get Metric Variable parameters
		MetricTemplate template = mvar.getMetricTemplate();
		boolean currentConfig  = mvar.isCurrentConfiguration();
		boolean nodeCandidates = mvar.isOnNodeCandidates();
		Component component = mvar.getComponent();
		String formula = mvar.getFormula();
		EList<Metric> metrics = mvar.getComponentMetrics();
		log.info("  _decomposeMetricVariable(): {} :: template={}, current-config={}, on-node-candidates={}, component={}, formula={}, component-metrics={}",
			mvar.getName(), template.getName(), currentConfig, nodeCandidates, getElementName(component), formula, metrics);
		
		// for each component Metric...
		boolean hasMetricWithCtx = false;
		for (Metric m : metrics) {
			// find metric context
			Set<MetricContext> contexts = _TC.M2MC.get(m);
			//if (contexts==null) throw new ModelAnalysisException("Metric not found in M2MC map: "+m.getName());
			if (contexts==null) { log.warn("  _decomposeMetricVariable(): Metric not found in M2MC map. Ignoring: name={}", m.getName()); return false; }
			if (contexts.size()>1) throw new ModelAnalysisException("Metric has >1 metric contexts in M2MC map: "+m.getName()+" : contexts: "+contexts);
			hasMetricWithCtx = true;
			
			// add metric context as mvar's child and decompose metric context
			MetricContext mc = contexts.iterator().next();
			_TC.DAG.addNode(mvar, mc).setGrouping( getGrouping(mc) );
			_decomposeMetricContext(_TC, mc);
		}
		
		return hasMetricWithCtx;
	}
	
	protected void _decomposeMetricContext(TranslationContext _TC, MetricContext context) {
		log.info("  _decomposeMetricContext(): {} :: {}", context.getName(), context.getClass().getName());
		
		// Get common Metric Context parameters
		Metric metric = context.getMetric();
		Window window = context.getWindow();
		Schedule schedule = context.getSchedule();
		ObjectContext objContext = context.getObjectContext();
		log.info("  _decomposeMetricContext(): common fields: {} :: metric={}, window={}, schedule={}, object={}",
			context.getName(), metric.getName(), getElementName(window), getElementName(schedule), getElementName(objContext));
		
		_TC.DAG.addNode(context, metric).setGrouping( getGrouping(metric) );
		
		_decomposeMetric(_TC, metric, objContext);
		
		if (CompositeMetricContext.class.isInstance(context)) {
			CompositeMetricContext cmc = (CompositeMetricContext) context;
			GroupingType grouping = cmc.getGroupingType();
			EList<MetricContext> composingMetricContexts = cmc.getComposingMetricContexts();
			log.info("  _decomposeMetricContext(): CompositeMetricContext: {} :: grouping={}, composing-metric-contexts={}",
				cmc.getName(), grouping!=null?grouping.getName():null, getListElementNames(composingMetricContexts));
			
			for (MetricContext mctx : composingMetricContexts) {
				_TC.DAG.addNode(context, mctx).setGrouping( getGrouping(mctx) );
				
				_decomposeMetricContext(_TC, mctx);
			}
		} else
		if (RawMetricContext.class.isInstance(context)) {
			RawMetricContext rmc = (RawMetricContext) context;
			Sensor sensor = rmc.getSensor();
			log.info("  _decomposeMetricContext(): RawMetricContext: {} :: sensor={}", rmc.getName(), getElementName(sensor));
			
			DAGNode sensorDagNode = _TC.DAG.addNode(context, sensor).setGrouping( getGrouping(sensor) );
			_TC.addMonitorsForSensor( sensor.getName(), _createMonitorsForSensor(_TC, objContext, sensor, sensorDagNode) );
			
			_processSensor(_TC, sensor, objContext);
		} else
		{
			throw new ModelAnalysisException( String.format("Invalid Metric Context type occurred: %s  class=%s", context.getName(), context.getClass().getName()) );
		}
	}
	
	protected void _decomposeMetric(TranslationContext _TC, Metric metric, ObjectContext objContext) {
		log.info("  _decomposeMetric(): {} :: {} for {}", metric.getName(), metric.getClass().getName(), getComponentName(objContext));
		
		// Get common Metric parameters
		MetricTemplate template = metric.getMetricTemplate();
		log.info("  _decomposeMetric(): common fields: {} :: template={}", metric.getName(), template.getName());
		
		// Uncomment to include templates in the DAG and Topics set
		//_TC.DAG.addNode(metric, template).setGrouping( getGrouping(template) );
		//_decomposeMetricTemplate(_TC, template, objContext);
		
		if (CompositeMetric.class.isInstance(metric)) {
			CompositeMetric cm = (CompositeMetric) metric;
			String formula = cm.getFormula();
			EList<Metric> componentMetrics = cm.getComponentMetrics();
			log.info("  _decomposeMetric(): CompositeMetric: {} :: formula={}, component-metrics={}", 
				cm.getName(), formula, getListElementNames(componentMetrics));
			for (Metric m : componentMetrics) {
				_TC.DAG.addNode(metric, m).setGrouping( getGrouping(m) );
				
				_decomposeMetric(_TC, m, objContext);
			}
		} else
		if (RawMetric.class.isInstance(metric)) {
			RawMetric rm = (RawMetric) metric;
			log.info("  _decomposeMetric(): RawMetric: {} ::", rm.getName());
		} else
		if (MetricVariable.class.isInstance(metric)) {
			MetricVariable mv = (MetricVariable) metric;
			log.info("  _decomposeMetric(): MetricVariable: {} ::", mv.getName());
			
			_decomposeMetricVariable(_TC, mv);
		} else
		{
			throw new ModelAnalysisException( String.format("Invalid Metric type occurred: %s  class=%s", 
						metric.getName(), metric.getClass().getName()) );
		}
	}
	
	protected void _decomposeMetricTemplate(TranslationContext _TC, MetricTemplate template, ObjectContext objContext) {
		log.info("  _decomposeMetricTemplate(): {} :: {} for {}", template.getName(), template.getClass().getName(), getComponentName(objContext));
		
		ValueType valType = template.getValueType();
		int direction = template.getValueDirection();
		Unit unit = template.getUnit();
		MeasurableAttribute attribute = template.getAttribute();
		EList<Sensor> sensors = attribute.getSensors();
		log.info("  _decomposeMetricTemplate(): {} :: {} {}/{} {} -- Sensors: {}", 
			template.getName(), attribute.getName(), getElementName(valType), direction, getElementName(unit), getListElementNames(sensors));
		
		for (Sensor s : sensors) {
			DAGNode sensorDagNode = _TC.DAG.addNode(template, s).setGrouping( getGrouping(s) );
			_TC.addMonitorsForSensor( s.getName(), _createMonitorsForSensor(_TC, objContext, s, sensorDagNode) );
			
			_processSensor(_TC, s, objContext);
		}
	}
	
	protected void _processSensor(TranslationContext _TC, Sensor sensor, ObjectContext objContext) {
		log.info("    _processSensor(): {} :: {} for {}", sensor.getName(), sensor.getClass().getName(), getComponentName(objContext));
		
		String configStr = sensor.getConfiguration();
		boolean push = sensor.isIsPush();
		log.info("    _processSensor(): {} :: push={}, configuration={}", sensor.getName(), push, configStr);
		
		_TC.addComponentSensorPair(objContext, sensor);
	}
	
	private List<Sink> EMS_SINKS;
	
	protected Set<Monitor> _createMonitorsForSensor(TranslationContext _TC, ObjectContext objContext, Sensor sensor, DAGNode sensorDagNode) {
		log.info("    _createMonitorsForSensor(): sensor={}", sensor.getName());
		
		// Check if sensor monitors have already been created
		if (_TC.containsMonitorsForSensor(sensor.getName())) {
			log.info("    _createMonitorsForSensor(): sensor={} :: Monitors for this sensor have already been added", sensor.getName());
			return null;
		}
		
		// Create result set
		Set<Monitor> results = new HashSet<>();
		
		// Get sensor type and configuration
		eu.melodic.models.interfaces.ems.Sensor monitorSensor;
		if (sensor.isIsPush()) {
			PushSensor pushSensor = new PushSensorImpl();
			String port = sensor.getConfiguration();
			try {
				pushSensor.setPort(Integer.parseInt(port));
			} catch (NumberFormatException nfe) {
				log.error("    _createMonitorsForSensor(): ERROR: Invalid port, using -1: sensor={}, port={}", sensor.getName(), port);
				pushSensor.setPort(-1);
			}
			monitorSensor = new eu.melodic.models.interfaces.ems.Sensor(pushSensor);
			log.info("    _createMonitorsForSensor(): sensor={} :: port={}, PushSensor: {}", sensor.getName(), port, pushSensor);
		} else {
			PullSensor pullSensor = new PullSensorImpl();
			String className = sensor.getConfiguration();
			pullSensor.setClassName( className );
			//pullSensor.setInterval(....);
			monitorSensor = new eu.melodic.models.interfaces.ems.Sensor(pullSensor);
			log.info("    _createMonitorsForSensor(): sensor={} :: class-name={}, PullSensor: {}", sensor.getName(), className, pullSensor);
		}
		
		// Get monitor component
		String monitorComponent = getComponentName(objContext);
		
		// Initialize JMS_SINK if needed
		if (EMS_SINKS==null) {
			Sink sink = new SinkImpl();
			sink.setType(Sink.TypeType.JMS);
			List<Sink> sinks = new ArrayList<>();
			sinks.add(sink);
			EMS_SINKS = sinks;
		}
		
		// Get additional configuration
		String SensorConfigAnnotation = properties.getSensorConfigurationAnnotation();
		List<KeyValuePair> keyValuePairs = null;
		Optional<Feature> sensorConfig = sensor.getSubFeatures().stream()
			.filter(f -> 
				f.getAnnotations().stream().anyMatch(ann -> {
					camel.mms.MmsConcept o = (camel.mms.MmsConcept)ann;
					//String annPath = ann.getName();
					String annIdPath = ann.getId();
					while (o.getParent()!=null) {
						o = o.getParent();
						//annPath = o.getName()+"."+annPath;
						annIdPath = o.getId()+"."+annIdPath;
					}
					//return annPath.equals(SensorConfigAnnotation);
					return annIdPath.equals(SensorConfigAnnotation);
				})
			)
			.findFirst();
		if (sensorConfig.isPresent()) {
			log.info("    _createMonitorsForSensor(): sensor={} :: Configuration found in sub-feature: {}", sensor.getName(), sensorConfig.get().getName());
			keyValuePairs = sensorConfig.get().getAttributes().stream()
				.map(attr -> {
					String key = attr.getName();
					String value = null;
					
					if (attr.getValue() instanceof StringValue) value = ((StringValue)attr.getValue()).getValue();
					else if (attr.getValue() instanceof BooleanValue) value = Boolean.toString( ((BooleanValue)attr.getValue()).isValue() );
					else if (attr.getValue() instanceof IntValue) value = Integer.toString( ((IntValue)attr.getValue()).getValue() );
					else if (attr.getValue() instanceof FloatValue) value = Float.toString( ((FloatValue)attr.getValue()).getValue() );
					else if (attr.getValue() instanceof DoubleValue) value = Double.toString( ((DoubleValue)attr.getValue()).getValue() );
					else throw new ModelAnalysisException("Invalid Attribute Value type: "+attr.getValue().getClass().getName()+" in sensor configuration: sensor="+sensor.getName()+", sub-feature="+sensorConfig.get().getName()+", attribute="+key);
					
					KeyValuePair pair = new KeyValuePairImpl();
					pair.setKey(key);
					pair.setValue(value);
					return pair;
				})
				.collect(Collectors.toList());
		}
			
		// Get monitor metrics and intervals
		boolean isPull = ! sensor.isIsPush();
		long sensorInterval = Long.MAX_VALUE;	// in seconds
		for (DAGNode parent : _TC.DAG.getParentNodes(sensorDagNode)) {
			// Get metric name from sensor
			log.info("    + _createMonitorsForSensor(): sensor={} :: parent-node={}", sensor.getName(), parent.getName());
			RawMetricContext rmc = (RawMetricContext)parent.getElement();
			log.info("    + _createMonitorsForSensor(): sensor={} :: context={}", sensor.getName(), rmc.getName());
			Metric metric = rmc.getMetric();
			String monitorMetric = metric.getName();
			log.info("    + _createMonitorsForSensor(): sensor={} :: metric={}, component={}", sensor.getName(), monitorMetric, monitorComponent);
			
			// Get interval (if PullSensor)
			if (isPull) {
				Schedule sched = rmc.getSchedule();
				if (sched!=null) {
					long schedInterval = sched.getInterval();
					Unit schedUnit = sched.getTimeUnit();
					if (schedInterval>0 && schedUnit!=null) {
						// convert schedule interval to seconds
						schedInterval = TimeUnit.SECONDS.convert( schedInterval, TimeUnit.valueOf(schedUnit.getName().trim().toUpperCase()) );
						if (schedInterval<sensorInterval) {
							sensorInterval = schedInterval;
						}
					}
//XXX:ASK: WHAT IF it is a REPETITIONS schedule????
				}
			}
			
			// Create a Monitor instance
			Monitor monitor = new MonitorImpl();
			monitor.setMetric(monitorMetric);
			monitor.setSensor(monitorSensor);
			monitor.setComponent(monitorComponent);
			monitor.setSinks(EMS_SINKS);
			monitor.setTags(keyValuePairs);
			// watermark will be set in Coordinator
			
			results.add(monitor);
		}
		
		// Set sesnor interval
		if (isPull) {
			if (sensorInterval < properties.getSensorMinInterval() || sensorInterval == Long.MAX_VALUE) {
				sensorInterval = properties.getSensorDefaultInterval();
			}
			Interval iv = new IntervalImpl();
			iv.setPeriod((int)sensorInterval);
			iv.setUnit(Interval.UnitType.SECONDS);
			monitorSensor.getPullSensor().setInterval(iv);
		}
		
		log.info("    _createMonitorsForSensor(): sensor={} :: monitors={}", sensor.getName(), results);
		
		return results;
	}
	
	// ================================================================================================================
	// Helper methods
	
	protected String getElementName(NamedElement elem) {
		return elem!=null ? elem.getName() : null;
	}
	
	protected List<String> getListElementNames(List list) {
		ArrayList<String> names = new ArrayList<>();
		for (Object elem : list) {
			if (elem!=null && NamedElement.class.isInstance(elem)) {
				names.add( ((NamedElement)elem).getName() );
			}
		}
		return names;
	}
	
	protected Set<String> getSetElementNames(Set set) {
		if (set==null) return null;
		HashSet<String> names = new HashSet<>();
		java.util.Iterator it = set.iterator();
		while (it.hasNext()) {
			Object elem = it.next();
			if (elem!=null && NamedElement.class.isInstance(elem)) {
				names.add( ((NamedElement)elem).getName() );
			}
		}
		return names;
	}
	
	protected String getComponentName(ObjectContext objContext) {
		if (objContext==null) return null;
		Component comp = objContext.getComponent();
		Data data = objContext.getData();
		if (comp!=null && data!=null) throw new ModelAnalysisException("Invalid Object Context: properties Component and Data cannot be not null at the same time: "+objContext.getName());
		if (comp!=null) return comp.getName();
		if (data!=null) return data.getName();
		throw new ModelAnalysisException("Invalid Object Context: either Component or Data property must be not null: "+objContext.getName());
	}
	
	protected boolean checkIfUpperwareElement(NamedElement elem) {
		return false
			|| MetricVariable.class.isInstance(elem)
			|| ServiceLevelObjective.class.isInstance(elem)
			|| Event.class.isInstance(elem)
			|| Constraint.class.isInstance(elem)
			;
	}
	
	protected Grouping getGrouping(NamedElement elem) {
		// Upperware nodes are always GLOBAL
		if (checkIfUpperwareElement(elem)) {
			return Grouping.GLOBAL;
		}
		// Deduce CMC grouping from component groupings
		if (CompositeMetricContext.class.isInstance(elem)) {
			CompositeMetricContext cmc = (CompositeMetricContext) elem;
			GroupingType grouping = cmc.getGroupingType();
			return Grouping.valueOf( grouping.getName() );
		}
		return Grouping.UNSPECIFIED;
	}
	
	// ================================================================================================================
	// Grouping inference methods
	
	protected void _inferGroupings(TranslationContext _TC, String leafGrouping) {
		log.info("  _inferGroupings(): Inferring DAG node groupings...");
		
		// traverse DAG bottom-up
		Set<DAGNode> leafs = _TC.DAG.getLeafNodes();
		log.info("  _inferGroupings(): DAG Leaf Nodes: {}", leafs);
		
		Grouping grouping = Grouping.valueOf(leafGrouping);
		for (DAGNode node : leafs) {
			log.info("    ----> leaf node: element class: {}", node.getElement().getClass());
			
			// Upperware nodes are always GLOBAL
			if (checkIfUpperwareElement(node.getElement())) {
				node.setGrouping(Grouping.GLOBAL);
			} else
			// else use leaf grouping
			{
				node.setGrouping(grouping);
			}
			
			_inferAncestorGroupings(_TC, node);
		}
	}
	
	protected void _inferAncestorGroupings(TranslationContext _TC, DAGNode node) {
		log.info("  _inferAncestorGroupings(): Inferring parent groupings of DAG node: {}...", node);
		
		// Get child node grouping
		Grouping childGrouping = node.getGrouping();
		log.info("  _inferAncestorGroupings(): DAG node grouping: {}...", childGrouping);
		if (childGrouping==null || childGrouping.equals(Grouping.UNSPECIFIED)) {
			throw new IllegalArgumentException("_inferAncestorGroupings: Node passed has null or UNSPECIFIED grouping: "+node.getName()+", grouping="+childGrouping);
		}
		
		// process node parents
		Set<DAGNode> parents = _TC.DAG.getParentNodes(node);
		log.info("    ----> parent nodes: {}", parents);
		DAGNode _root = _TC.DAG.getRootNode();
		for (DAGNode parent : parents) {
			// exclude DAG root from further processing
			if (parent==_root) continue;
			
			Grouping parentGrouping = parent.getGrouping();
			log.info("    ----> parent: {} with grouping: {} lower-than-child-grouping={}", parent, parentGrouping, parentGrouping!=null ? parentGrouping.lowerThan( Grouping.UNSPECIFIED ) : "n/a");
			if (parentGrouping==null || parentGrouping.equals( Grouping.UNSPECIFIED ) || parentGrouping.lowerThan(childGrouping)) {
				Grouping newGrouping;
				
				// Upperware nodes are always GLOBAL
				if (checkIfUpperwareElement(parent.getElement())) {
					newGrouping = Grouping.GLOBAL;
				} else
				// else use child grouping
				{
					//XXX:TODO: The following is not completely correct. Check if an aggregation operator is involved etc etc.
					newGrouping = childGrouping;
				}
				log.info("    ----> setting parent grouping: {} grouping: {}, id: {}, hash: {}", parent, newGrouping, parent.getId(), parent.hashCode());
				parent.setGrouping( newGrouping );
			}
			
			// recursively process ancestors
			_inferAncestorGroupings(_TC, parent);
		}
	}
}