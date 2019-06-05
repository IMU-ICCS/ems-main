/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.translate.analyze;

import camel.constraint.*;
import camel.core.*;
import camel.data.Data;
import camel.deployment.Component;
import camel.metric.*;
import camel.metric.Sensor;
import camel.metric.impl.MetricVariableImpl;
import camel.requirement.OptimisationRequirement;
import camel.requirement.RequirementModel;
import camel.requirement.ServiceLevelObjective;
import camel.scalability.*;
import camel.type.*;
import camel.unit.Unit;
import eu.melodic.event.brokercep.cep.MathUtil;
import eu.melodic.event.translate.TranslationContext;
import eu.melodic.event.translate.properties.CamelToEplTranslatorProperties;
import eu.melodic.models.interfaces.ems.*;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadata;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadataTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.common.util.EList;

import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

//import org.eclipse.emf.cdo.util.ConcurrentAccessException;

@Slf4j
public class ModelAnalyzer {
    private CamelToEplTranslatorProperties properties;

    private List<Sink> EMS_SINKS;

    // ================================================================================================================
    // Model analysis methods

    public void analyzeModel(TranslationContext _TC, String leafGrouping, CamelModel camelModel, CamelToEplTranslatorProperties properties) {
        log.debug("ModelAnalyzer.analyzeModel():  Analyzing models...");

        this.properties = properties;

        // set full-name pattern in _TC, for full-name generation
        _TC.setFullNamePattern(properties.getFullNamePattern());

        // building Metric-to-Metric Context map
        _buildMetricToMetricContextMap(_TC, camelModel);
        log.debug("ModelAnalyzer.analyzeModel():  Populating Metric-to-Metric Context map completed");

        // building MVV set
        _buildMetricVariableSets(_TC, camelModel);
        log.debug("ModelAnalyzer.analyzeModel():  Populating MVV and Composite Metric Variable sets completed");

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

        // analyze constraints
//		_analyzeConstraints(_TC, camelModel);
//		log.debug("ModelAnalyzer.analyzeModel():  Constraints analysis completed");

        // analyze metric variable constraints (to extract metrics needed in CP model)
		_analyzeMetricVariableConstraints(_TC, camelModel);
		log.debug("ModelAnalyzer.analyzeModel():  Metric Variable Constraints analysis completed");

        // infer groupings
        _inferGroupings(_TC, leafGrouping);
        log.debug("ModelAnalyzer._inferGroupings():  Grouping inferencing completed");

        if (log.isTraceEnabled()) {
            _TC.DAG.traverseDAG(node -> log.trace("------------> DAG node:{}, grouping:{}, id:{}, hash:{}", node, node.getGrouping(), node.getId(), node.hashCode()));
        }
    }

    private void _buildMetricToMetricContextMap(TranslationContext _TC, CamelModel camelModel) {
        // extract metric models
        log.info("  Extracting Metric Type Models from CAMEL model...");
        List<MetricTypeModel> metricModels = camelModel.getMetricModels().stream()
                .filter(mm -> MetricTypeModel.class.isAssignableFrom(mm.getClass()))
                .map(mm -> (MetricTypeModel) mm)
                .collect(Collectors.toList());
        log.info("  Extracting Metric Type Models from CAMEL model... {}", getListElementNames(metricModels));

        // for every metric type model...
        metricModels.forEach(mm -> {
            // get metric contexts
            log.info("  Extracting Metric Contexts from Metric Type model {}...", mm.getName());
            EList<MetricContext> contexts = mm.getMetricContexts();
            log.info("  Extracting Metric Contexts from Metric Type model {}... {}", mm.getName(), getListElementNames(contexts));

            // for every metric context...
            contexts.forEach(mc -> {
                // get metric
                Metric metric = mc.getMetric();
                ObjectContext objContext = mc.getObjectContext();
                log.info("  Metric-Context: {}.{}.{} == {} --> {}", camelModel.getName(), mm.getName(), mc.getName(), getElementName(metric), getElementName(objContext));

                // update _TC.M2MC map
                _TC.addMetricMetricContextPair(metric, mc);
            });
        });
        log.info("_buildMetricToMetricContextMap(): M2MC={}", getMapSetElementNames(_TC.M2MC));
        //log.info("_buildMetricToMetricContextMap(): M2MC={}", getMapSetFullNames(_TC, _TC.M2MC));
    }

    private void _buildMetricVariableSets(TranslationContext _TC, CamelModel camelModel) {
        // extract metric models
        log.info("  Extracting Metric Type Models from CAMEL model...");
        List<MetricTypeModel> metricModels = camelModel.getMetricModels().stream()
                .filter(mm -> MetricTypeModel.class.isAssignableFrom(mm.getClass()))
                .map(mm -> (MetricTypeModel) mm)
                .collect(Collectors.toList());
        log.info("  Extracting Metric Type Models from CAMEL model... {}", getListElementNames(metricModels));

        // for every metric type model...
        metricModels.forEach(mm -> {
            // get current-config metric variables
            log.info("  Extracting Current-Config Metric Variables from Metric Type model {}...", mm.getName());
            List<MetricVariable> variables = mm.getMetrics().stream()
                    .filter(met -> MetricVariable.class.isAssignableFrom(met.getClass()))
                    .map(met -> (MetricVariable) met)
                    .filter(MetricVariable::isCurrentConfiguration)
                    // ...also filter using method 'isFromVariable()' from melodic-commons
                    .filter(mv -> CamelMetadataTool.isFromVariable((MetricVariableImpl) mv))
                    .collect(Collectors.toList());
            log.info("  Extracting Metric Variables from Metric Type model {}... {}", mm.getName(), getListElementNames(variables));

            // for every metric variable...
            variables.forEach(mv -> {
                // get component metrics
                EList<Metric> componentMetrics = mv.getComponentMetrics();
                Component component = mv.getComponent();
                log.info("  Metric-Variable: {}.{}.{} :: component-metrics={}, component={}", camelModel.getName(), mm.getName(), mv.getName(), getListElementNames(componentMetrics), getElementName(component));

                // update _TC.MVV set
                if (componentMetrics.size() == 0) {
                    log.info("  Found MVV: {}.{}.{}", camelModel.getName(), mm.getName(), mv.getName());
                    _TC.addMVV(mv);
                } else
                // ...else update _TC.CMVAR set
                {
                    _TC.addCompositeMetricVariable(mv);
                }

                // Find matching variable for CP model
                MetricVariable matchingMv = _findMatchingVar(mv, camelModel);
                if (matchingMv!=null) {
                    _TC.MVV_CP.put(matchingMv.getName(), mv.getName());
                }
            });
        });
    }

//XXX:Improve this method (probably pre-process metric models to avoid multiple scans of the model)
    private static MetricVariable _findMatchingVar(MetricVariable mvar, CamelModel camelModel) {
        CamelMetadata type = CamelMetadataTool.findVariableType((MetricVariableImpl) mvar);
        Component comp = mvar.getComponent();
        if (type==null || comp==null) {
            log.warn("  _findMatchingVar: type or component is null: type={}, component={}", type, comp);
            return null;
        }
        String componentName = comp.getName();

        // extract metric models
        List<MetricTypeModel> metricModels = camelModel.getMetricModels().stream()
                .filter(mm -> MetricTypeModel.class.isAssignableFrom(mm.getClass()))
                .map(mm -> (MetricTypeModel) mm)
                .collect(Collectors.toList());

        // for every metric type model...
        for (MetricTypeModel mm : metricModels) {
            // get metric variables
            log.info("  Extracting Current-Config Metric Variables from Metric Type model {}...", mm.getName());
            List<MetricVariable> variables = mm.getMetrics().stream()
                    .filter(met -> MetricVariable.class.isAssignableFrom(met.getClass()))
                    .map(met -> (MetricVariable) met)
                    .filter(mv -> ! mv.isCurrentConfiguration())
                    .collect(Collectors.toList());

            // for every metric variable...
            for (MetricVariable mv : variables) {
                log.debug("_findMatchingVar(): Checking variable: {}, component={}",
                        mv.getName(), getElementName(mv.getComponent()));
                CamelMetadata type1 = CamelMetadataTool.findVariableType((MetricVariableImpl) mv);
                log.debug("_findMatchingVar(): Variable type: {}, type={}", mv.getName(), type1);
                String componentName1 = mv.getComponent().getName();

                if (type1==type && componentName.equals(componentName1)) {
                    log.debug("_findMatchingVar(): Found matching variable: {} -> {}", mvar.getName(), mv.getName());
                    return mv;
                }
                log.trace("_findMatchingVar(): Variable type or component does not match to: search-type={}, search-component={}",
                        type, componentName);
            }
        }

        String mesg = String.format("No matching Metric variable was found for: mv=%s, camel-metadata-type=%s, component=%s", mvar.getName(), type, componentName);
        //log.error(mesg);
        throw new ModelAnalysisException(mesg);
    }

    private void _extractFunctions(TranslationContext _TC, CamelModel camelModel) {
        // extract metric models
        log.info("  Extracting Metric Type Models from CAMEL model...");
        List<MetricTypeModel> metricModels = camelModel.getMetricModels().stream()
                .filter(mm -> MetricTypeModel.class.isAssignableFrom(mm.getClass()))
                .map(mm -> (MetricTypeModel) mm)
                .collect(Collectors.toList());
        log.info("  Extracting Metric Type Models from CAMEL model... {}", getListElementNames(metricModels));

        // for every metric type model...
        metricModels.forEach(mm -> {
            // get Function definitions
            log.info("  Extracting Functions from Metric Type model {}...", mm.getName());
            EList<Function> functions = mm.getFunctions();
            log.info("  Extracting Functions from Metric Type model {}... {}", mm.getName(), getListElementNames(functions));

            // for every metric context...
            functions.forEach(f -> {
                // get expression and parameters
                String expression = f.getExpression();
                EList<String> arguments = f.getArguments();
                log.info("  Function: {}.{}.{} == {} --> {}", camelModel.getName(), mm.getName(), f.getName(), expression, arguments);

                // update _TC.FUNC set
                _TC.addFunction(f);
            });
        });
    }

    private void _analyzeScalabilityRules(TranslationContext _TC, CamelModel camelModel) {
        // extract scalability rules
        log.info("  Extracting Scalability Models from CAMEL model...");
        EList<ScalabilityModel> scalabilityModels = camelModel.getScalabilityModels();
        log.info("  Extracting Scalability Models from CAMEL model... {}", getListElementNames(scalabilityModels));

        scalabilityModels.forEach(sm -> {
            log.info("  Extracting Scalability Rules from Scalability model {}...", sm.getName());
            EList<ScalabilityRule> rules = sm.getRules();
            log.info("  Extracting Scalability Rules from Scalability model {}... {}", sm.getName(), getListElementNames(rules));
            rules.forEach(rule -> {
                String ruleName = rule.getName();
                Event ruleEvent = rule.getEvent();
                EList<Action> ruleActions = rule.getActions();
                log.info("RULE: {}.{}.{} == {} --> {}", camelModel.getName(), sm.getName(), ruleName, ruleEvent.getName(), ruleActions);

                // add event-action pair to E2A map
                _TC.addEventActionPairs(ruleEvent, ruleActions);
                // add event to DAG as top-level nodes
                _TC.DAG.addTopLevelNode(ruleEvent).setGrouping(getGrouping(ruleEvent));

                // decompose event
                _decomposeEvent(_TC, ruleEvent);
            });
        });
    }

    private void _analyzeOptimisationRequirements(TranslationContext _TC, CamelModel camelModel) {
        // extract requirement models
        log.info("  Extracting Requirement Models from CAMEL model...");
        EList<RequirementModel> requirementModels = camelModel.getRequirementModels();
        log.info("  Extracting Requirement Models from CAMEL model... {}", getListElementNames(requirementModels));

        // for each requirement model...
        requirementModels.forEach(rm -> {
            //List<Requirement> requirements = rm.getRequirements();
            //log.info("  Extracting Requirements from Requirements model {}... {}", rm.getName(), requirements);

            // extract optimisation requirements
            log.info("  Extracting Optimisation Requirements from Requirements model {}...", rm.getName());
            List<OptimisationRequirement> optimisations = rm.getRequirements().stream()
                    .filter(req -> OptimisationRequirement.class.isAssignableFrom(req.getClass()))
                    .map(OptimisationRequirement.class::cast)
                    .collect(Collectors.toList());
            log.info("  Extracting Optimisation Requirements from Requirements model {}... {}", rm.getName(), getListElementNames(optimisations));

            // for each optimisation requirement...
            optimisations.forEach(optr -> {
                // extract metric context and variable
                String reqName = optr.getName();
                MetricContext mc = optr.getMetricContext();
                MetricVariable mv = optr.getMetricVariable();
                log.info("  Processing Optimisation Requirement {} from Requirements model {}: metric-context={}, metric-variable={}...",
                        reqName, rm.getName(), getElementName(mc), getElementName(mv));

                // Optimisation Goal's metric context's component metrics
                Set<Metric> formulaMetrics = new HashSet<>();
                ObjectContext objCtx;
                if (mc != null) {
                    Metric m = mc.getMetric();
                    objCtx = mc.getObjectContext();
                    log.trace("    Extracting metrics of metric context: metric={}, metric-class={}, component={}", m.getName(), m.getClass().getName(), getComponentName(objCtx));
                    if (m instanceof RawMetric) {
                        formulaMetrics.add(m);
                    } else if (m instanceof CompositeMetric) {
                        formulaMetrics.addAll(_extractMetricsFromFormula(_TC, ((CompositeMetric) m).getFormula()));
                    } else if (m instanceof MetricVariable) {
                        formulaMetrics.addAll(_extractMetricsFromFormula(_TC, ((MetricVariable) m).getFormula()));
                    }
                }

                // Optimisation Goal's metric variable's component metrics
                if (mv != null) {
                    log.trace("    Extracting metrics of metric variable: variable={}", mv.getName());
                    formulaMetrics.addAll(_extractMetricsFromFormula(_TC, mv.getFormula()));
                }

                // update DAG and decompose metrics and variables
                for (Metric m : formulaMetrics) {
                    if (m instanceof MetricVariable) {
                        log.trace("    Processing component metric variable of opt. goal formula: goal={}, variable={}, formula={}", reqName, m.getName(), ((MetricVariable) m).getFormula());

                        // add variable to DAG as top-level node
                        _TC.DAG.addTopLevelNode(m).setGrouping(getGrouping(m));

                        // decompose metric
                        _decomposeMetricVariable(_TC, (MetricVariable) m);
                    } else {
                        String formula = (m instanceof CompositeMetric) ? ((CompositeMetric) m).getFormula() : null;
                        log.trace("    Processing component metric context of opt. goal formula: goal={}, context={}, formula={}", reqName, mv.getName(), formula);

                        // get metric context for metric
                        Set<MetricContext> mctx = _TC.M2MC.get(m);
                        if (mctx.size() != 1) {
                            String mesg = String.format("Metric in formula has 0 or more than one metric contexts: metric=%s, formula=%s, contexts=%s", m.getName(), formula, getSetElementNames(mctx));
                            log.error("    Error while processing Optimisation Goal: opt-goal={}, req-model={}: {}", reqName, rm.getName(), mesg);
                            throw new ModelAnalysisException(mesg);
                        }
                        MetricContext mc_1 = mctx.iterator().next();

                        // add metric context to DAG as top-level node
                        _TC.DAG.addTopLevelNode(m).setGrouping(getGrouping(m));

                        // add metric context to DAG
                        _TC.DAG.addNode(m, mc_1).setGrouping(getGrouping(mc_1));

                        // decompose metric context
                        _decomposeMetricContext(_TC, mc_1);
                    }
                }
            });
        });
    }

    private Set<Metric> _extractMetricsFromFormula(TranslationContext _TC, String formula) {
        log.debug("    Extracting metrics from formula: {}", formula);
        List<String> argNames = MathUtil.getFormulaArguments(formula);
        log.debug("    Formula arguments: {}", argNames);

        // find formula component metrics
        Set<Metric> formulaMetrics = _TC.M2MC.keySet().stream()
                .filter(m -> argNames.contains(m.getName()))
                .collect(Collectors.toSet());
        log.debug("    Formula metrics: {}", getSetElementNames(formulaMetrics));

        // find formula component metric variables
        Set<Metric> formulaVars = _TC.CMVAR_1.stream()
                .filter(mv -> argNames.contains(mv.getName()))
                .collect(Collectors.toSet());
        log.debug("    Formula variables: {}", getSetElementNames(formulaVars));

        // merge results
        formulaMetrics.addAll(formulaVars);
        log.debug("    Formula metrics and variables: {}", getSetElementNames(formulaMetrics));

        return formulaMetrics;
    }

    private void _analyzeServiceLevelObjectives(TranslationContext _TC, CamelModel camelModel) {
        // extract requirement models
        log.info("  Extracting Requirement Models (for SLO) from CAMEL model...");
        EList<RequirementModel> requirementModels = camelModel.getRequirementModels();
        log.info("  Extracting Requirement Models (for SLO) from CAMEL model... {}", getListElementNames(requirementModels));

        // for each requirement model...
        requirementModels.forEach(rm -> {
            // extract Service Level Objectives
            log.info("  Extracting Service Level Objectives from Requirements model {}...", rm.getName());
            List<ServiceLevelObjective> slos = rm.getRequirements().stream()
                    .filter(req -> ServiceLevelObjective.class.isAssignableFrom(req.getClass()))
                    .map(ServiceLevelObjective.class::cast)
                    .collect(Collectors.toList());
            log.info("  Extracting Service Level Objectives from Requirements model {}... {}", rm.getName(), getListElementNames(slos));

            // for each Service Level Objective...
            slos.forEach(slo -> {
                // extract metric context and variable
                String sloName = slo.getName();
                Constraint sloConstr = slo.getConstraint();
                Event sloEvent = slo.getViolationEvent();
                log.info("  Processing Service Level Objective {} from Requirements model {}: constraint={}, violation-event={}...",
                        slo.getName(), rm.getName(), getElementName(sloConstr), getElementName(sloEvent));

                // add SLO to SLO set
                _TC.addSLO(slo);
                // add event to DAG as top-level nodes
                _TC.DAG.addTopLevelNode(slo).setGrouping(getGrouping(slo));

                // update DAG and decompose metric constraint
                if (sloConstr != null) {
                    // add SLO constraint to DAG
                    _TC.DAG.addNode(slo, sloConstr).setGrouping(getGrouping(sloConstr));

                    // decompose constraint
                    _decomposeConstraint(_TC, sloConstr);
                }

                if (sloEvent != null) {
                    //XXX: TODO: ???: ++++++++++++++++++++
                }
            });
        });
    }

    private void _analyzeMetricVariables(TranslationContext _TC, CamelModel camelModel) {
        // extract requirement models
        log.info("  Extracting Metric Type Models from CAMEL model...");
        List<MetricTypeModel> metricModels = camelModel.getMetricModels().stream()
                .filter(mm -> MetricTypeModel.class.isAssignableFrom(mm.getClass()))
                .map(mm -> (MetricTypeModel) mm)
                .collect(Collectors.toList());
        log.info("  Extracting Metric Type Models from CAMEL model... {}", getListElementNames(metricModels));

        // for every metric type model...
        metricModels.forEach(mm -> {
            // get metric variables
            log.info("  Extracting current-config Metric Variables from Metric Type model {}...", mm.getName());
            List<MetricVariable> variables = mm.getMetrics().stream()
                    .filter(met -> MetricVariable.class.isAssignableFrom(met.getClass()))
                    .map(met -> (MetricVariable) met)
                    .filter(MetricVariable::isCurrentConfiguration)
                    .collect(Collectors.toList());
            log.info("  Extracting current-config Metric Variables from Metric Type model {}... {}", mm.getName(), getListElementNames(variables));

            // for every metric variable...
            variables.forEach(mv -> {
                // extract metric variable information
                String mvName = mv.getName();
                MetricTemplate template = mv.getMetricTemplate();
                boolean isCurrConfig = mv.isCurrentConfiguration();
                boolean isOnNodeCand = mv.isOnNodeCandidates();
                Component component = mv.getComponent();
                String formula = mv.getFormula();
                List<Metric> componentMetrics = ListUtils.emptyIfNull(mv.getComponentMetrics());
                boolean containsMetrics = ! componentMetrics.isEmpty();
                log.info("  Processing Metric Variable {} from Metric Type model {}: template={}, is-current-configuration={}, is-on-node-candidates={}, component={}, formula={}, component-metrics={}, contains-metrics={}...",
                        mvName, mm.getName(), getElementName(template), isCurrConfig, isOnNodeCand, getElementName(component), formula, getListElementNames(componentMetrics), containsMetrics);

                if ((formula == null || formula.trim().isEmpty()) && containsMetrics) {
                    log.error("  Metric Variable has component metrics but formula IS EMPTY: {}", mvName);
                    throw new ModelAnalysisException(String.format("Metric Variable has component metrics but formula IS EMPTY: %s", mvName));
                }
                if (formula != null && !formula.trim().isEmpty() && !containsMetrics) {
                    log.warn("  Metric Variable has NO component metrics: name={}, formula={}", mvName, formula);
                }

                _checkFormulaAndComponents(_TC, formula, componentMetrics);

                if (componentMetrics.size() > 0) {
                    // add metric variable to DAG as top-level node
                    _TC.DAG.addTopLevelNode(mv).setGrouping(getGrouping(mv));
                } else {
                    // if MVV just register it in _TC
                    _TC.addMVV(mv);
                }

                // for every component metric
                componentMetrics.forEach(m -> {
                    // get metric context or variable of current metric
                    Set<MetricContext> ctxSet = _TC.M2MC.get(m);
                    int ctxSize = (ctxSet == null ? 0 : ctxSet.size());
                    boolean isMVV = _TC.MVV.contains(m.getName());
                    MetricVariable mvv = isMVV ? (MetricVariable) m : null;

                    if (ctxSize == 0 && !isMVV) {
                        log.error("  - No metric context or variable found for metric '{}' used in the metric variable '{}' : ctx-set={}, is-MVV={}", m.getName(), mv.getName(), getSetElementNames(ctxSet), isMVV);
                        log.error("  _TC.M2MC: keys: {}", getSetElementNames(_TC.M2MC.keySet()));
                        log.error("  _TC.M2MC: values: {}", _TC.M2MC.values());
                        log.error("  _TC.MVV: {}", getSetElementNames(_TC.MVV));
                        throw new ModelAnalysisException(String.format("No metric context or MVV found for metric '%s' used in the metric variable '%s'", m.getName(), mv.getName()));
                    } else if (ctxSize > 0 && isMVV || ctxSize > 1 && !isMVV) {
                        List<String> ctxNames = ctxSet.stream().map(NamedElement::getName).collect(Collectors.toList());
                        log.error("  - More than one metric contexts and variables were found for metric '{}' used in the metric variable '{}' : ctx-names={}, is-MVV={}", m.getName(), mv.getName(), ctxNames, isMVV);
                        log.error("  _TC.M2MC: keys: {}", getSetElementNames(_TC.M2MC.keySet()));
                        log.error("  _TC.M2MC: values: {}", _TC.M2MC.values());
                        log.error("  _TC.MVV: {}", getSetElementNames(_TC.MVV));
                        throw new ModelAnalysisException(String.format("More than one metric contexts and MVVs were found for metric '%s' used in the metric variable '%s': ctx-names=%s, is-MVV=%b", m.getName(), mv.getName(), ctxNames.toString(), isMVV));
                    } else if (ctxSize == 1 && !isMVV) {
                        MetricContext ctx = ctxSet.iterator().next();

                        // update DAG and decompose metrics
                        if (ctx != null) {
                            // add CTX to DAG
                            _TC.DAG.addNode(mv, ctx).setGrouping(getGrouping(ctx));

                            // decompose metric context
                            _decomposeMetricContext(_TC, ctx);

                        } else {
                            //log.error("  - Metric context for metric '{}' used in the metric variable '{}' is null", m.getName(), mv.getName());
                            throw new ModelAnalysisException(String.format("Metric context for metric '%s' used in the metric variable '%s' is null", m.getName(), mv.getName()));
                        }

                    } else if (ctxSize == 0 && isMVV) {
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

    /*private void _analyzeConstraints(TranslationContext _TC, CamelModel camelModel) {
        // extract constraint models
        log.info("  Extracting Constraint Models from CAMEL model...");
        EList<ConstraintModel> constraintModels = camelModel.getConstraintModels();
        log.info("  Extracting Constraint Models from CAMEL model... {}", getListElementNames(constraintModels));

        // for each constraint model...
        constraintModels.stream().forEach(cm -> {
            // extract constraints
            log.info("  Extracting Constraints from Constraints model {}...", cm.getName());
            List<Constraint> constraints = cm.getConstraints().stream()
                    .collect(Collectors.toList());
            log.info("  Extracting Constraints from Constraints model {}... {}", cm.getName(), getListElementNames(constraints));

            // for each Constraint...
            constraints.stream().forEach(con -> {
                // extract metric context and variable
                String conName = con.getName();
                log.info("  Processing Constraint {} from Constraints model {}: ...", con.getName(), cm.getName());

                // add constraint to DAG
                _TC.DAG.addTopLevelNode(con).setGrouping(getGrouping(con));

                // decompose constraint
                _decomposeConstraint(_TC, con);
            });
        });
    }*/

    private void _analyzeMetricVariableConstraints(TranslationContext _TC, CamelModel camelModel) {
        // extract constraint models
        log.info("  Extracting Constraint Models from CAMEL model...");
        EList<ConstraintModel> constraintModels = camelModel.getConstraintModels();
        log.info("  Extracting Constraint Models from CAMEL model... {}", getListElementNames(constraintModels));

        // for each constraint model...
        constraintModels.forEach(cm -> {
            // extract constraints
            log.info("  Extracting Metric Variable Constraints from Constraints model {}...", cm.getName());
            List<MetricVariableConstraint> constraints = cm.getConstraints().stream()
                    .filter(c -> c instanceof MetricVariableConstraint)
                    .map(c -> (MetricVariableConstraint)c)
                    .collect(Collectors.toList());
            log.info("  Extracting Metric Variable Constraints from Constraints model {}... {}", cm.getName(), getListElementNames(constraints));

            // for each Constraint...
            constraints.forEach(con -> {
                // extract constraint name and metric variable
                String conName = con.getName();
                log.info("  Processing Metric Variable Constraint {} from Constraints model {}: ...", conName, cm.getName());

                MetricVariable mv = con.getMetricVariable();
                log.info("  Metric Variable Constraint {}: metric variable: {}", con.getName(), mv.getName());

                // decompose constraint
                DAGNode mvNode = _decomposeMetricVariableConstraint(_TC, con, true);

                // Remove top-level metric variable and make its children top-level nodes
                Set<DAGNode> children = _TC.DAG.getNodeChildren(mvNode);
                for (DAGNode child : children) {
                    _TC.DAG.removeEdge(mvNode, child);
                    //_TC.DAG.addEdge(_TC.DAG.getRootNode().getElement(), child.getElement());
                    _TC.DAG.addTopLevelNode(child.getElement());
                }
                _TC.DAG.removeNode(mvNode.getElement());
            });
        });
    }

    private void _decomposeEvent(TranslationContext _TC, Event event) {
        log.info("  _decomposeEvent(): {} :: {}", event.getName(), event.getClass().getName());

        // decompose event
        if (event instanceof BinaryEventPattern) {
            BinaryEventPattern be = (BinaryEventPattern) event;
            String op = be.getOperator().getName();
            Event lEvent = be.getLeftEvent();
            Event rEvent = be.getRightEvent();
            log.info("  _decomposeEvent(): BinaryEventPattern: {} ==> {} {} {} ", be.getName(), lEvent.getName(), op, rEvent.getName());

            log.info("  _decomposeEvent(): Adding left event to DAG: {}, parent={}", lEvent.getName(), be.getName());
            _TC.DAG.addNode(be, lEvent).setGrouping(getGrouping(lEvent));
            log.info("  _decomposeEvent(): Adding right event to DAG: {}, parent={}", rEvent.getName(), be.getName());
            _TC.DAG.addNode(be, rEvent).setGrouping(getGrouping(rEvent));

            log.info("  _decomposeEvent(): Decomposing left event: {}", lEvent.getName());
            _decomposeEvent(_TC, lEvent);
            log.info("  _decomposeEvent(): Decomposing right event: {}", rEvent.getName());
            _decomposeEvent(_TC, rEvent);
        } else if (event instanceof UnaryEventPattern) {
            UnaryEventPattern ue = (UnaryEventPattern) event;
            String op = ue.getOperator().getName();
            Event uEvent = ue.getEvent();
            log.info("  _decomposeEvent(): UnaryEventPattern: {} ==> {} {} ", ue.getName(), op, uEvent.getName());

            _TC.DAG.addNode(event, uEvent).setGrouping(getGrouping(uEvent));

            _decomposeEvent(_TC, uEvent);
        } else if (event instanceof NonFunctionalEvent) {
            NonFunctionalEvent nfe = (NonFunctionalEvent) event;
            MetricConstraint constr = nfe.getMetricConstraint();
            boolean isViolation = nfe.isIsViolation();
            log.info("  _decomposeEvent(): NonFunctionalEvent: {} ==> {}{} ", nfe.getName(), isViolation ? "VIOLATION OF " : "", constr.getName());

            _TC.DAG.addNode(event, constr).setGrouping(getGrouping(constr));

            _decomposeMetricConstraint(_TC, constr);
        } else {
            throw new ModelAnalysisException(String.format("Invalid event type occurred: %s  class=%s", event.getName(), event.getClass().getName()));
        }
    }

    private void _decomposeConstraint(TranslationContext _TC, Constraint constraint) {
        log.info("  _decomposeConstraint(): {} :: {}", constraint.getName(), constraint.getClass().getName());
        if (MetricConstraint.class.isAssignableFrom(constraint.getClass())) {
            log.info("  _decomposeConstraint(): Metric Constraint found: {}", constraint.getName());
            _decomposeMetricConstraint(_TC, (MetricConstraint) constraint);
        } else if (IfThenConstraint.class.isAssignableFrom(constraint.getClass())) {
            log.info("  _decomposeConstraint(): If-Then Constraint found: {}", constraint.getName());
            _decomposeIfThenConstraint(_TC, (IfThenConstraint) constraint);
        } else if (MetricVariableConstraint.class.isAssignableFrom(constraint.getClass())) {
            log.info("  _decomposeConstraint(): Metric Variable Constraint found: {}", constraint.getName());
            // Not used in EMS
            //_decomposeMetricVariableConstraint(_TC, (MetricVariableConstraint)constraint);
        } else if (LogicalConstraint.class.isAssignableFrom(constraint.getClass())) {
            log.info("  _decomposeConstraint(): Logical Constraint found: {}", constraint.getName());
            _decomposeLogicalConstraint(_TC, (LogicalConstraint) constraint);
        } else {
            throw new ModelAnalysisException(String.format("Invalid Constraint type occurred: %s  class=%s", constraint.getName(), constraint.getClass().getName()));
        }
    }

    private void _decomposeMetricConstraint(TranslationContext _TC, MetricConstraint constraint) {
        log.info("  _decomposeMetricConstraint(): {} :: {}", constraint.getName(), constraint.getClass().getName());
        java.util.Date validity = constraint.getValidity();
        String op = constraint.getComparisonOperator().getName();
        double threshold = constraint.getThreshold();
        MetricContext context = constraint.getMetricContext();
        log.info("  _decomposeMetricConstraint(): {} ==> {} {} {}  validity: {}", constraint.getName(), context.getName(), op, threshold, validity);

        _TC.DAG.addNode(constraint, context).setGrouping(getGrouping(context));

        _decomposeMetricContext(_TC, context);
    }

    protected void _decomposeMetricVariableConstraint(TranslationContext _TC, MetricVariableConstraint constraint) {
        _decomposeMetricVariableConstraint(_TC, constraint, false);
    }

    private DAGNode _decomposeMetricVariableConstraint(TranslationContext _TC, MetricVariableConstraint constraint, boolean isTopLevel) {
        log.info("  _decomposeMetricVariableConstraint(): {} :: {}", constraint.getName(), constraint.getClass().getName());
        java.util.Date validity = constraint.getValidity();
        String op = constraint.getComparisonOperator().getName();
        double threshold = constraint.getThreshold();
        MetricVariable mvar = constraint.getMetricVariable();
        log.info("  _decomposeMetricVariableConstraint(): {} ==> {} {} {}  validity: {}", constraint.getName(), mvar.getName(), op, threshold, validity);

        DAGNode mvNode;
        if (isTopLevel)
            mvNode = _TC.DAG.addTopLevelNode(mvar).setGrouping(Grouping.GLOBAL);
        else
            mvNode = _TC.DAG.addNode(constraint, mvar).setGrouping(Grouping.GLOBAL);

        log.trace("  _decomposeMetricVariableConstraint(): CMVAR: {}", _TC.CMVAR);
        log.trace("  _decomposeMetricVariableConstraint(): MVV:   {}", _TC.MVV);
        _decomposeMetricVariable(_TC, mvar);

        return mvNode;
    }

    private void _decomposeIfThenConstraint(TranslationContext _TC, IfThenConstraint constraint) {
        log.info("  _decomposeIfThenConstraint(): {} :: {}", constraint.getName(), constraint.getClass().getName());
        Constraint ifConstraint = constraint.getIf();
        Constraint thenConstraint = constraint.getThen();
        Constraint elseConstraint = constraint.getElse();
        log.info("  _decomposeIfThenConstraint(): {} ==> if: {}, then: {}, else: {}",
                constraint.getName(), getElementName(ifConstraint), getElementName(thenConstraint), getElementName(elseConstraint));

        _TC.DAG.addNode(constraint, ifConstraint).setGrouping(getGrouping(ifConstraint));
        _TC.DAG.addNode(constraint, thenConstraint).setGrouping(getGrouping(thenConstraint));
        if (elseConstraint!=null)
            _TC.DAG.addNode(constraint, elseConstraint).setGrouping(getGrouping(elseConstraint));

        _decomposeConstraint(_TC, ifConstraint);
        _decomposeConstraint(_TC, thenConstraint);
        if (elseConstraint!=null)
            _decomposeConstraint(_TC, elseConstraint);
    }

    private void _decomposeLogicalConstraint(TranslationContext _TC, LogicalConstraint constraint) {
        log.info("  _decomposeLogicalConstraint(): {} :: {}", constraint.getName(), constraint.getClass().getName());
        EList<Constraint> componentConstraints = constraint.getConstraints();
        LogicalOperatorType operator = constraint.getLogicalOperator();
        log.info("  _decomposeLogicalConstraint(): {} ==> operator: {}, component-constraints: {}", constraint.getName(), operator.getName(), getListElementNames(componentConstraints));

        componentConstraints.forEach(lc -> _TC.DAG.addNode(constraint, lc).setGrouping(getGrouping(lc)) );

        componentConstraints.forEach(lc -> _decomposeConstraint(_TC, lc) );
    }

    private boolean _decomposeMetricVariable(TranslationContext _TC, MetricVariable mvar) {
        log.info("  _decomposeMetricVariable(): {} :: {}", mvar.getName(), mvar.getClass().getName());

        // Get Metric Variable parameters
        MetricTemplate template = mvar.getMetricTemplate();
        boolean currentConfig = mvar.isCurrentConfiguration();
        boolean nodeCandidates = mvar.isOnNodeCandidates();
        Component component = mvar.getComponent();
        String formula = mvar.getFormula();
        EList<Metric> metrics = mvar.getComponentMetrics();
        log.info("  _decomposeMetricVariable(): {} :: template={}, current-config={}, on-node-candidates={}, component={}, formula={}, component-metrics={}",
                mvar.getName(), template.getName(), currentConfig, nodeCandidates, getElementName(component), formula, getListElementNames(metrics));

        _checkFormulaAndComponents(_TC, formula, metrics);

        // for each component Metric...
        boolean hasNonMVVComponents = false;    // ?? does any measurable metric exist in component metric??
        for (Metric m : metrics) {
            // check if it is a composite or raw metric
            if (CompositeMetric.class.isAssignableFrom(m.getClass()) || RawMetric.class.isAssignableFrom(m.getClass())) {
                Set<MetricContext> contexts = _TC.M2MC.get(m);
                if (contexts == null)
                    throw new ModelAnalysisException(String.format("Metric variable %s: Component metric not found in M2MC map: %s", mvar.getName(), m.getName()));
                //if (contexts==null) { log.warn("  _decomposeMetricVariable(): Metric not found in M2MC map. Ignoring: name={}", m.getName()); return false; }
                if (contexts.size() > 1)
                    throw new ModelAnalysisException(String.format("Metric variable %s: Component metric has >1 metric contexts in M2MC map: metric=%s, contexts=%s", mvar.getName(), m.getName(), contexts));
                if (contexts.size() == 1) {
                    hasNonMVVComponents = true;

                    // add metric context as mvar's child and decompose metric context
                    MetricContext mc = contexts.iterator().next();
                    log.debug("  _decomposeMetricVariable(): {} :: Component metric with exactly one metric context found: metric={}, context={}", mvar.getName(), m.getName(), mc.getName());
                    _TC.DAG.addNode(mvar, mc).setGrouping(getGrouping(mc));
                    _decomposeMetricContext(_TC, mc);
                }
            } else
            // check if it is metric variable
            if (m instanceof MetricVariable) {
                // check if it is a composite metric variable
                if (_TC.CMVAR.contains(m.getName())) {
                    hasNonMVVComponents = true;

                    // add metric variable 'm' as mvar's child and decompose it
                    MetricVariable mv = (MetricVariable) m;
                    log.debug("  _decomposeMetricVariable(): {} :: Component composite metric variable found: {}", mvar.getName(), mv.getName());
                    _TC.DAG.addNode(mvar, mv).setGrouping(getGrouping(mv));
                    //if (_decomposeMetricVariable(_TC, mv)) hasNonMVVComponents = true;
                    _decomposeMetricVariable(_TC, mv);
                } else
                // check if it is an MVV
                if (_TC.MVV.contains(m.getName())) {
                    log.debug("  _decomposeMetricVariable(): {} :: Component MVV found: {}", mvar.getName(), m.getName());
                    _TC.DAG.addNode(mvar, m).setGrouping(getGrouping(m));
                    // MVV can be pruned later (if property 'translator.prune-mmv' is true)
                } else
                // check if it is a CP model variable (i.e. solver variable)
                if (_isCpModelVariable(_TC, (MetricVariable)m)) {
                    log.debug("  _decomposeMetricVariable(): {} :: CP model variable encountered: {}", mvar.getName(), m.getName());
                    // No DAG node is added for CP model variables
                } else {
                    throw new ModelAnalysisException(String.format("INTERNAL ERROR: Metric Variable not found in CMVAR or in MVV sets and is not a CP model variable: %s, class=%s", m.getName(), m.getClass().getName()));
                }
            } else {
                throw new ModelAnalysisException(String.format("Invalid Metric type occurred: %s, class=%s", m.getName(), m.getClass().getName()));
            }
        }

        return hasNonMVVComponents;
    }

    private void _decomposeMetricContext(TranslationContext _TC, MetricContext context) {
        log.info("  _decomposeMetricContext(): {} :: {}", context.getName(), context.getClass().getName());

        // Get common Metric Context parameters
        Metric metric = context.getMetric();
        Window window = context.getWindow();
        Schedule schedule = context.getSchedule();
        ObjectContext objContext = context.getObjectContext();
        log.info("  _decomposeMetricContext(): common fields: {} :: metric={}, window={}, schedule={}, object={}",
                context.getName(), metric.getName(), getElementName(window), getElementName(schedule), getElementName(objContext));

        // Commented addition in DAG and decomposition of Metrics
        /*_TC.DAG.addNode(context, metric).setGrouping(getGrouping(metric));

        _decomposeMetric(_TC, metric, objContext);*/

        if (context instanceof CompositeMetricContext) {
            CompositeMetricContext cmc = (CompositeMetricContext) context;
            GroupingType grouping = cmc.getGroupingType();
            EList<MetricContext> composingMetricContexts = cmc.getComposingMetricContexts();
            log.info("  _decomposeMetricContext(): CompositeMetricContext: {} :: grouping={}, composing-metric-contexts={}",
                    cmc.getName(), grouping != null ? grouping.getName() : null, getListElementNames(composingMetricContexts));

            for (MetricContext mctx : composingMetricContexts) {
                _TC.DAG.addNode(context, mctx).setGrouping(getGrouping(mctx));

                _decomposeMetricContext(_TC, mctx);
            }
        } else if (context instanceof RawMetricContext) {
            RawMetricContext rmc = (RawMetricContext) context;
            Sensor sensor = rmc.getSensor();
            log.info("  _decomposeMetricContext(): RawMetricContext: {} :: sensor={}", rmc.getName(), getElementName(sensor));

            DAGNode sensorDagNode = _TC.DAG.addNode(context, sensor).setGrouping(getGrouping(sensor));
            _TC.addMonitorsForSensor(sensor.getName(), _createMonitorsForSensor(_TC, objContext, sensor, sensorDagNode));

            _processSensor(_TC, sensor, objContext);
        } else {
            throw new ModelAnalysisException(String.format("Invalid Metric Context type occurred: %s  class=%s", context.getName(), context.getClass().getName()));
        }
    }

    private void _decomposeMetric(TranslationContext _TC, Metric metric, ObjectContext objContext) {
        log.info("  _decomposeMetric(): metric={}, metric-class={}, component={}", metric.getName(), metric.getClass().getName(), getComponentName(objContext));

        // Get common Metric parameters
        MetricTemplate template = metric.getMetricTemplate();
        log.info("  _decomposeMetric(): Common fields of metric {}: template={}", metric.getName(), template.getName());

        // Uncomment to include templates in the DAG and Topics set
        //_TC.DAG.addNode(metric, template).setGrouping( getGrouping(template) );
        //_decomposeMetricTemplate(_TC, template, objContext);

        if (metric instanceof CompositeMetric) {
            CompositeMetric cm = (CompositeMetric) metric;
            String formula = cm.getFormula();
            EList<Metric> componentMetrics = cm.getComponentMetrics();
            log.info("  _decomposeMetric(): CompositeMetric: metric={}, formula={}, component-metrics={}",
                    cm.getName(), formula, getListElementNames(componentMetrics));

            _checkFormulaAndComponents(_TC, formula, componentMetrics);

            for (Metric m : componentMetrics) {
                _TC.DAG.addNode(metric, m).setGrouping(getGrouping(m));

                _decomposeMetric(_TC, m, objContext);
            }
        } else if (metric instanceof RawMetric) {
            RawMetric rm = (RawMetric) metric;
            log.info("  _decomposeMetric(): RawMetric: metric={}", rm.getName());
        } else if (metric instanceof MetricVariable) {
            MetricVariable mv = (MetricVariable) metric;
            log.info("  _decomposeMetric(): MetricVariable: variable={}", mv.getName());

            _decomposeMetricVariable(_TC, mv);
        } else {
            throw new ModelAnalysisException(String.format("Invalid Metric type occurred: %s  class=%s",
                    metric.getName(), metric.getClass().getName()));
        }
    }

    private void _decomposeMetricTemplate(TranslationContext _TC, MetricTemplate template, ObjectContext objContext) {
        log.info("  _decomposeMetricTemplate(): {} :: {} for {}", template.getName(), template.getClass().getName(), getComponentName(objContext));

        ValueType valType = template.getValueType();
        int direction = template.getValueDirection();
        Unit unit = template.getUnit();
        MeasurableAttribute attribute = template.getAttribute();
        EList<Sensor> sensors = attribute.getSensors();
        log.info("  _decomposeMetricTemplate(): {} :: {} {}/{} {} -- Sensors: {}",
                template.getName(), attribute.getName(), getElementName(valType), direction, getElementName(unit), getListElementNames(sensors));

        for (Sensor s : sensors) {
            DAGNode sensorDagNode = _TC.DAG.addNode(template, s).setGrouping(getGrouping(s));
            _TC.addMonitorsForSensor(s.getName(), _createMonitorsForSensor(_TC, objContext, s, sensorDagNode));

            _processSensor(_TC, s, objContext);
        }
    }

    private void _processSensor(TranslationContext _TC, Sensor sensor, ObjectContext objContext) {
        log.info("    _processSensor(): {} :: {} for {}", sensor.getName(), sensor.getClass().getName(), getComponentName(objContext));

        String configStr = sensor.getConfiguration();
        boolean push = sensor.isIsPush();
        log.info("    _processSensor(): {} :: push={}, configuration={}", sensor.getName(), push, configStr);

        _TC.addComponentSensorPair(objContext, sensor);
    }

    private synchronized void _initializeSinks() {
        if (EMS_SINKS == null) {
            log.debug("    _initializeSinks(): Active Sinks type: {}", properties.getSinks());
            log.debug("    _initializeSinks(): Sink type configurations: {}", properties.getSinkConfig());

            List<Sink> sinks = new ArrayList<>();
            for (String sinkType : properties.getSinks()) {
                log.trace("    _initializeSinks(): Processing sink type: {}", sinkType);
                Sink.TypeType sinkTypeType = Sink.TypeType.valueOf(sinkType);
                Map<String,String> configMap = properties.getSinkConfig().get(sinkType);

                if (MapUtils.isEmpty(configMap)) {
                    log.warn("    _initializeSinks(): WARN: Missing configuration for sink type: {}", sinkType);
                    continue;
                }

                // Create configuration for sink type
                List<KeyValuePair> sinkTypeConfig = new ArrayList<>();
                for (Map.Entry<String,String> e : configMap.entrySet()) {
                    KeyValuePair pair = new KeyValuePairImpl();
                    pair.setKey(e.getKey());
                    pair.setValue(e.getValue());
                    sinkTypeConfig.add(pair);
                }

                log.debug("    _initializeSinks(): {} sink type configuration: {}", sinkType,
                        sinkTypeConfig.stream()
                                .map(entry -> entry.getKey() + "=" + entry.getValue())
                                .collect(Collectors.joining(", ", "[", "]")));

                // Create sink entry
                Sink sink = new SinkImpl();
                sink.setType(sinkTypeType);
                sink.setConfiguration(sinkTypeConfig);
                sinks.add(sink);
            }

            // Store sink configurations
            EMS_SINKS = sinks;
            log.debug("    _initializeSinks(): Sink type configurations initialized");
        }
    }

    private Set<Monitor> _createMonitorsForSensor(TranslationContext _TC, ObjectContext objContext, Sensor sensor, DAGNode sensorDagNode) {
        log.info("    _createMonitorsForSensor(): sensor={}", sensor.getName());

        // Check if sensor monitors have already been created
        if (_TC.containsMonitorsForSensor(sensor.getName())) {
            log.info("    _createMonitorsForSensor(): sensor={} :: Monitors for this sensor have already been added", sensor.getName());
            return Collections.emptySet();
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
            pullSensor.setClassName(className);
            pullSensor.setConfiguration(Collections.emptyList());
            //pullSensor.setInterval(....);
            monitorSensor = new eu.melodic.models.interfaces.ems.Sensor(pullSensor);
            log.info("    _createMonitorsForSensor(): sensor={} :: class-name={}, PullSensor: {}", sensor.getName(), className, pullSensor);
        }

        // Get monitor component
        String monitorComponent = getComponentName(objContext);

        // Initialize JMS_SINK if needed
        if (EMS_SINKS == null) {
            _initializeSinks();
        }

        // Get additional configuration
        String sensorConfigAnnotation = properties.getSensorConfigurationAnnotation();
        List<KeyValuePair> keyValuePairs = null;
        Optional<Feature> sensorConfig = sensor.getSubFeatures().stream()
                .filter(f ->
                        f.getAnnotations().stream().anyMatch(ann -> {
                            camel.mms.MmsConcept o = (camel.mms.MmsConcept) ann;
                            //StringBuilder annPath = new StringBuilder(ann.getName());
                            StringBuilder annIdPath = new StringBuilder(ann.getId());
                            while (o.getParent() != null) {
                                o = o.getParent();
                                //annPath.insert(0, o.getName() + ".");
                                annIdPath.insert(0, o.getId() + ".");
                            }
                            //return annPath.toString().equals(sensorConfigAnnotation);
                            return annIdPath.toString().equals(sensorConfigAnnotation);
                        })
                )
                .findFirst();
        if (sensorConfig.isPresent()) {
            log.info("    _createMonitorsForSensor(): sensor={} :: Configuration found in sub-feature: {}", sensor.getName(), sensorConfig.get().getName());
            keyValuePairs = sensorConfig.get().getAttributes().stream()
                    .map(attr -> {
                        String key = attr.getName();
                        String value;

                        if (attr.getValue() instanceof StringValue)
                            value = ((StringValue) attr.getValue()).getValue();
                        else if (attr.getValue() instanceof BooleanValue)
                            value = Boolean.toString(((BooleanValue) attr.getValue()).isValue());
                        else if (attr.getValue() instanceof IntValue)
                            value = Integer.toString(((IntValue) attr.getValue()).getValue());
                        else if (attr.getValue() instanceof FloatValue)
                            value = Float.toString(((FloatValue) attr.getValue()).getValue());
                        else if (attr.getValue() instanceof DoubleValue)
                            value = Double.toString(((DoubleValue) attr.getValue()).getValue());
                        else
                            throw new ModelAnalysisException("Invalid Attribute Value type: " + attr.getValue().getClass().getName() + " in sensor configuration: sensor=" + sensor.getName() + ", sub-feature=" + sensorConfig.get().getName() + ", attribute=" + key);

                        KeyValuePair pair = new KeyValuePairImpl();
                        pair.setKey(key);
                        pair.setValue(value);
                        return pair;
                    })
                    .collect(Collectors.toList());
        }

        // Get monitor metrics and intervals
        boolean isPull = !sensor.isIsPush();
        long sensorInterval = Long.MAX_VALUE;    // in seconds
        for (DAGNode parent : _TC.DAG.getParentNodes(sensorDagNode)) {
            // Get metric name from sensor
            log.info("    + _createMonitorsForSensor(): sensor={} :: parent-node={}", sensor.getName(), parent.getName());
            RawMetricContext rmc = (RawMetricContext) parent.getElement();
            log.info("    + _createMonitorsForSensor(): sensor={} :: context={}", sensor.getName(), rmc.getName());
            /*Metric metric = rmc.getMetric();
            String monitorMetric = metric.getName();
            log.info("    + _createMonitorsForSensor(): sensor={} :: metric={}, component={}", sensor.getName(), monitorMetric, monitorComponent);*/
            String monitorMetric = sensor.getName();
            log.info("    + _createMonitorsForSensor(): sensor={} :: metric/topic={}, component={}", sensor.getName(), monitorMetric, monitorComponent);

            // Get interval (if PullSensor)
            if (isPull) {
                Schedule sched = rmc.getSchedule();
                if (sched != null) {
                    long schedInterval = sched.getInterval();
                    Unit schedUnit = sched.getTimeUnit();
                    if (schedInterval > 0 && schedUnit != null) {
                        // convert schedule interval to seconds
                        schedInterval = TimeUnit.SECONDS.convert(schedInterval, TimeUnit.valueOf(schedUnit.getName().trim().toUpperCase()));
                        if (schedInterval < sensorInterval) {
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

        // Set sensor interval
        if (isPull) {
            if (sensorInterval < properties.getSensorMinInterval() || sensorInterval == Long.MAX_VALUE)
            {
                sensorInterval = properties.getSensorDefaultInterval();
            }
            Interval iv = new IntervalImpl();
            iv.setPeriod((int) sensorInterval);
            iv.setUnit(Interval.UnitType.SECONDS);
            monitorSensor.getPullSensor().setInterval(iv);
        }

        log.info("    _createMonitorsForSensor(): sensor={} :: monitors={}", sensor.getName(), results);

        return results;
    }

    private void _checkFormulaAndComponents(TranslationContext _TC, String formula, List<Metric> componentMetrics) {
        if (!properties.isFormulaCheckEnabled()) return;
        if (StringUtils.isBlank(formula)) return;

        if (componentMetrics == null) componentMetrics = new ArrayList<>();
        List<String> metricNames = getListElementNames(componentMetrics);
        log.debug("    _checkFormulaAndComponents(): formula={}, component-metrics={}", formula, metricNames);
        List<String> argNames = MathUtil.getFormulaArguments(formula);
        log.debug("    _checkFormulaAndComponents(): formula={}, arguments={}", formula, argNames);

        // check if all arguments are found in component metrics - Detailed report
        Set<String> diff1 = new HashSet<>(argNames);
        diff1.removeAll(metricNames);
        log.debug("    _checkFormulaAndComponents(): diff1={}", diff1);
        if (diff1.size() > 0) {
            log.error("    _checkFormulaAndComponents(): ERROR: Formula arguments not found in component metrics: formula={}, arguments-not-found={}, component-metrics={}", formula, diff1, metricNames);
        }

        // check if all component metrics are found in arguments - Detailed report
        Set<String> diff2 = new HashSet<>(metricNames);
        diff2.removeAll(argNames);
        log.debug("    _checkFormulaAndComponents(): diff2={}", diff2);
        if (diff2.size() > 0) {
            log.error("    _checkFormulaAndComponents(): ERROR: Formula component metrics not found in formula arguments: formula={}, metrics-not-found={}, arguments={}", formula, diff2, argNames);
        }

        // if there are differences throw an exception
        if (diff1.size() > 0 || diff2.size() > 0) {
            String message = String.format("Formula arguments and component metrics do not match: formula=%s, component-metrics=%s, arguments=%s", formula, metricNames, argNames);
            log.error("    _checkFormulaAndComponents(): ERROR: {}", message);
            throw new ModelAnalysisException(message);
        }

        // check metrics against contexts
        for (Metric m : componentMetrics) {
            log.trace("    _checkFormulaAndComponents(): Checking formula component metric: formula={}, metric={}", formula, m.getName());

            // check if it is a composite or raw metric
            if (CompositeMetric.class.isAssignableFrom(m.getClass()) || RawMetric.class.isAssignableFrom(m.getClass())) {
                Set<MetricContext> contexts = _TC.M2MC.get(m);
                String message = null;
                if (contexts == null)
                    message = String.format("Formula component metric does not have a metric context in M2MC map: formula=%s, metric=%s", formula, m.getName());
                else if (contexts.size() > 1)
                    message = String.format("Formula component metric has >1 metric contexts in M2MC map: formula=%s, metric=%s, contexts=%s", formula, m.getName(), contexts);
                if (message != null) {
                    log.error("    _checkFormulaAndComponents(): ERROR: {}", message);
                    log.error("    _checkFormulaAndComponents(): ERROR: metric: {}, hash: {}", m, m.hashCode());
                    log.error("    _checkFormulaAndComponents(): ERROR: M2MC: {}", _TC.M2MC);
                    throw new ModelAnalysisException(message);
                }

                if (log.isTraceEnabled()) {
                    log.trace("    _checkFormulaAndComponents(): Formula component metric has exactly 1 metric context in M2MC map: formula={}, metric={}, context={}", formula, m.getName(), contexts.iterator().next());
                }
            } else
            // check if it is metric variable
            if (MetricVariable.class.isAssignableFrom(m.getClass())) {
                // check if it is a composite metric variable
                if (_TC.CMVAR.contains(m.getName())) {
                    if (log.isTraceEnabled()) {
                        log.trace("    _checkFormulaAndComponents(): Formula composite component metric variable found: formula={}, metric-variable={}", formula, m.getName());
                    }
                } else
                // check if it is an MVV
                if (_TC.MVV.contains(m.getName())) {
                    if (log.isTraceEnabled()) {
                        log.trace("    _checkFormulaAndComponents(): Formula component MVV found: formula={}, mvv={}", formula, m.getName());
                    }
                } else
                // check if it is a CP model variable (i.e. solver variable)
                if (_isCpModelVariable(_TC, (MetricVariable)m)) {
                    if (log.isTraceEnabled()) {
                        log.trace("    _checkFormulaAndComponents(): CP model variable encountered: formula={}, cp-model-var={}", formula, m.getName());
                    }
                } else {
                    String message = String.format("INTERNAL ERROR: Formula component metric variable not found in CMVAR or in MVV sets and it is not CP model variable: formula=%s, metric-variable=%s", formula, m.getName());
                    log.error("    _checkFormulaAndComponents(): {}", message);
                    throw new ModelAnalysisException(message);
                }
            } else {
                String message = String.format("INTERNAL ERROR: Invalid formula component metric: formula=%s, metric=%s, metric-class=%s", formula, m.getName(), m.getClass().getName());
                log.error("    _checkFormulaAndComponents(): {}", message);
                throw new ModelAnalysisException(message);
            }
        }

        log.trace("    _checkFormulaAndComponents(): Formula arguments and component metrics match: formula={}, arguments={}, component-metric={}", formula, argNames, metricNames);
    }

    private boolean _isCpModelVariable(TranslationContext _TC, MetricVariable mv) {
        log.debug("    _isCpModelVariable: mv={}", getElementName(mv));
        boolean result = CamelMetadataTool.isFromVariable((MetricVariableImpl) mv);
        log.debug("    _isCpModelVariable: result={}", result);
        return result;
    }

    // ================================================================================================================
    // Helper methods

    private static String getElementName(NamedElement elem) {
        return elem != null ? elem.getName() : null;
    }

    private List<String> getListElementNames(List list) {
        List<String> names = new ArrayList<>();
        for (Object elem : ListUtils.emptyIfNull(list)) {
            if (elem instanceof NamedElement) {
                names.add(((NamedElement) elem).getName());
            }
        }
        return names;
    }

    private Set<String> getSetElementNames(Set set) {
        if (set == null) return Collections.emptySet();
        HashSet<String> names = new HashSet<>();
        for (Object elem : set) {
            if (elem instanceof NamedElement) {
                names.add(((NamedElement) elem).getName());
            }
        }
        return names;
    }

    private Map<String, Set<String>> getMapSetElementNames(Map map) {
        if (map == null) return Collections.emptyMap();
        Map<String, Set<String>> results = new HashMap<>();
        for (Object key : map.keySet()) {
            Object value = map.get(key); //entry.getValue();
            if (key instanceof NamedElement && value instanceof Set) {
                results.put(((NamedElement) key).getName(), getSetElementNames((Set) value));
            }
        }
        return results;
    }
	
	/*private Map<String,Set<String>> getMapSetFullNames(TranslationContext _TC, Map map) {
		if (map==null) return null;
		HashMap<String,Set<String>> results = new HashMap<>();
		for (Object key : map.keySet()) {
			Object value = map.get(key); //entry.getValue();
			if (key instanceof NamedElement && value instanceof Set) {
				String keyStr = _TC.E2N.get((NamedElement)key);
				Set<String> newSet = new HashSet<>();
				for (Object item : (Set)value) {
					if (item instanceof NamedElement) {
						newSet.add( _TC.E2N.get((NamedElement)item) );
					}
				}
				results.put(keyStr, newSet);
			}
		}
		return results;
	}*/

    private String getComponentName(ObjectContext objContext) {
        if (objContext == null) return null;
        Component comp = objContext.getComponent();
        Data data = objContext.getData();
        if (comp != null && data != null)
            throw new ModelAnalysisException("Invalid Object Context: properties Component and Data cannot be not null at the same time: " + objContext.getName());
        if (comp != null) return comp.getName();
        if (data != null) return data.getName();
        throw new ModelAnalysisException("Invalid Object Context: either Component or Data property must be not null: " + objContext.getName());
    }

    private boolean checkIfUpperwareElement(NamedElement elem) {
        return (elem instanceof MetricVariable)
                || (elem instanceof ServiceLevelObjective)
                || (elem instanceof Event)
                || (elem instanceof Constraint)
                ;
    }

    private Grouping getGrouping(NamedElement elem) {
        // Upperware nodes are always GLOBAL
        if (checkIfUpperwareElement(elem)) {
            return Grouping.GLOBAL;
        }
        // Deduce CMC grouping from component groupings
        if (elem instanceof CompositeMetricContext) {
            CompositeMetricContext cmc = (CompositeMetricContext) elem;
            GroupingType grouping = cmc.getGroupingType();
            return Grouping.valueOf(grouping.getName());
        }
        return Grouping.UNSPECIFIED;
    }

    // ================================================================================================================
    // Grouping inference methods

    private void _inferGroupings(TranslationContext _TC, String leafGrouping) {
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

    private void _inferAncestorGroupings(TranslationContext _TC, DAGNode node) {
        log.info("  _inferAncestorGroupings(): Inferring parent groupings of DAG node: {}...", node);

        // Get child node grouping
        Grouping childGrouping = node.getGrouping();
        log.info("  _inferAncestorGroupings(): DAG node grouping: {}...", childGrouping);
        if (childGrouping == null || childGrouping.equals(Grouping.UNSPECIFIED)) {
            throw new IllegalArgumentException("_inferAncestorGroupings: Node passed has null or UNSPECIFIED grouping: " + node.getName() + ", grouping=" + childGrouping);
        }

        // process node parents
        Set<DAGNode> parents = _TC.DAG.getParentNodes(node);
        log.info("    ----> parent nodes: {}", parents);
        DAGNode _root = _TC.DAG.getRootNode();
        for (DAGNode parent : parents) {
            // exclude DAG root from further processing
            if (parent == _root) {
                // ...unless it is top-level node and its grouping is not GLOBAL
                if (childGrouping != Grouping.GLOBAL) {
                    // ...then add to it a (new) top-level, parent node with GLOBAL grouping
                    NamedElement elem = node.getElement();
                    DAGNode newParent = _TC.DAG.addTopLevelNode(elem, "DUPL_" + node.getName()).setGrouping(Grouping.GLOBAL);
                    _TC.DAG.addEdge(newParent.getName(), node.getName());
                    _TC.DAG.removeEdge(_root, node);
                }
                continue;
            }

            Grouping parentGrouping = parent.getGrouping();
            log.info("    ----> parent: {} with grouping: {} lower-than-child-grouping={}", parent, parentGrouping, parentGrouping != null ? parentGrouping.lowerThan(Grouping.UNSPECIFIED) : "n/a");
            if (parentGrouping == null || parentGrouping.equals(Grouping.UNSPECIFIED) || parentGrouping.lowerThan(childGrouping)) {
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
                parent.setGrouping(newGrouping);
            }

            // recursively process ancestors
            _inferAncestorGroupings(_TC, parent);
        }
    }
}