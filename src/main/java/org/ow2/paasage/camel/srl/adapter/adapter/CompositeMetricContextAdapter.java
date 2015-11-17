/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.adapter;

import de.uniulm.omi.cloudiator.colosseum.client.entities.ComposedMonitor;
import de.uniulm.omi.cloudiator.colosseum.client.entities.FormulaQuantifier;
import de.uniulm.omi.cloudiator.colosseum.client.entities.MonitorInstance;
import de.uniulm.omi.cloudiator.colosseum.client.entities.Schedule;
import de.uniulm.omi.cloudiator.colosseum.client.entities.abstracts.Monitor;
import de.uniulm.omi.cloudiator.colosseum.client.entities.enums.FilterType;
import de.uniulm.omi.cloudiator.colosseum.client.entities.enums.FormulaOperator;
import de.uniulm.omi.cloudiator.colosseum.client.entities.enums.SubscriptionType;
import org.ow2.paasage.camel.srl.adapter.communication.FrontendCommunicator;
import org.ow2.paasage.camel.srl.adapter.config.CommandLinePropertiesAccessor;
import org.ow2.paasage.camel.srl.adapter.utils.Convert;
import org.ow2.paasage.camel.srl.adapter.utils.Transform;
import eu.paasage.camel.metric.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 03.09.2015.
 */
public class CompositeMetricContextAdapter extends AbstractAdapter {
    private final CompositeMetricContext context;
    private final List<MetricInstance> metricInstances;

    public CompositeMetricContextAdapter(CommandLinePropertiesAccessor config, FrontendCommunicator fc,
                                         CompositeMetricContext context, List<MetricInstance> metricInstances) {
        super(config, fc);
        this.context = context;
        this.metricInstances = metricInstances;
    }

    @Override
    public void adapt(){
            logger.info("Save CompositeMetricContext to colosseum: " + context.getName());


            CompositeMetric compositeMetric = (CompositeMetric) context.getMetric();

            FormulaQuantifier quantifier = null;

            switch (context.getQuantifier()) {
                case ALL:
                    quantifier = getFc().saveFormulaQuantifier(true, 1.0);
                    break;
                case SOME:
                        /* TODO implement max and min quantity in execware */
                    quantifier = getFc().saveFormulaQuantifier(context.isIsRelative(),
                            context.getMinQuantity());
                    break;
                case ANY:
                    if (compositeMetric.getFormula().getFunctionPattern() == FunctionPatternType.MAP) {
                        // same as all, since we use this value in conditions only:
                        quantifier = getFc().saveFormulaQuantifier(true, 1.0);
                        break;
                    } else {
                        quantifier = getFc().saveFormulaQuantifier(false, 1.0);
                        break;
                    }
                default:
                    throw new RuntimeException("Quantifier is not implemented!");
            }



            eu.paasage.camel.metric.Schedule camelSchedule = context.getSchedule();

            if(camelSchedule == null){
                throw new RuntimeException("Composite metrics needs schedule!");
            }

            Schedule schedule = getFc().saveSchedule(camelSchedule.getInterval(), Convert.toJavaTimeUnit(camelSchedule.getUnit()));

            eu.paasage.camel.metric.Window camelWindow = context.getWindow(); /*TODO implement other units and window types */

            de.uniulm.omi.cloudiator.colosseum.client.entities.abstracts.Window window = null;

            if(camelWindow == null){
                throw new RuntimeException("Composite metrics needs window!");
            } else {
                if(camelWindow.getSizeType().equals(WindowSizeType.MEASUREMENTS_ONLY)){
                    window = getFc().saveMeasurementWindow(camelWindow.getMeasurementSize());
                } else if (camelWindow.getSizeType().equals(WindowSizeType.TIME_ONLY)){
                    window = getFc().saveTimeWindow(camelWindow.getTimeSize(), Convert.toJavaTimeUnit(camelWindow.getUnit()));
                } else if (camelWindow.getSizeType().equals(WindowSizeType.BOTH_MATCH)){
                    throw new RuntimeException("WindowSizeType.BOTH_MATCH not implemented in Adapter"); //TODO which window type to choose?
                } else if (camelWindow.getSizeType().equals(WindowSizeType.FIRST_MATCH)){
                    throw new RuntimeException("WindowSizeType.FIRST_MATCH not implemented in Adapter"); //TODO which window type to choose?
                }
            }



            FormulaOperator operator = Transform.operator(compositeMetric.getFormula().getFunction());

            FunctionPatternType functionPattern =
                    compositeMetric.getFormula().getFunctionPattern();

            List<Monitor> composedMonitors = new ArrayList<>();
            for (Monitor monitor : getFc().getMonitors()) {
                for (MetricContext mc : context
                        .getComposingMetricContexts()) {
                    for (String s : monitor.getExternalReferences()) {
                        if (s.equals(mc.getName())) {
                            composedMonitors.add(monitor);
                        }
                    }
                }
            }

            Monitor compositeMonitor = null;

            logger.info("Add aggregator.");
            if (functionPattern == FunctionPatternType.MAP) {
                compositeMonitor = (ComposedMonitor) getFc()
                        .mapAggregatedMonitors(quantifier, schedule, window, operator,
                                composedMonitors);
            } else if (functionPattern == FunctionPatternType.REDUCE) {
                compositeMonitor = (ComposedMonitor) getFc()
                        .reduceAggregatedMonitors(quantifier, schedule, window, operator,
                                composedMonitors);
            } else {
                throw new RuntimeException("FunctionPatternType is not implemented!");
            }

            getFc().addExternalId(compositeMonitor, context.getName());

            for (MetricInstance metricInstance : metricInstances) {

                // Not VM specific, so add all just another one /* TODO THIS IS SEMANTICALLY WRONG */
                for (MonitorInstance monitorInstance : getFc()
                        .getMonitorInstances(compositeMonitor.getId())) {

                    Boolean isAlreadyTagged = false;

                    for (MetricInstance tempMetricInstance : metricInstances) {
                        for (String s : monitorInstance.getExternalReferences()) {
                            if (s.equals(tempMetricInstance.getName())) {
                                isAlreadyTagged = true;
                                break;
                            }
                        }
                    }

                    if (!isAlreadyTagged) {
                        getFc().addExternalId(monitorInstance, metricInstance.cdoID().toString());
                        break; // go to next metric instance
                    }
                }

                    /*
                    TODO correlate the metric instances together with the composed metric instances
                    TODO curently not possible, since the IP is not stored with the monitor instance

                    if(metricInstance.getObjectBinding() instanceof MetricVMBinding){
                        VirtualMachine frontendVM = fc.getVirtualMachineToIP(((MetricVMBinding) metricInstance.getObjectBinding()).getVmInstance().getIp());
                        fc.addExternalIdToMonitorInstance(compositeMonitor,
                            metricInstance.getName(), frontendVM);
                    } else if(metricInstance.getObjectBinding() instanceof MetricComponentBinding) {
                    } else if(metricInstance.getObjectBinding() instanceof MetricApplicationBinding) {
                    }
                    */
            }

            ///////////////////////////////////////////////////////////////////////////
            //
            // Add Subscription to all composite monitor to send to CDO
            //
            ///////////////////////////////////////////////////////////////////////////
            getFc().addMonitorSubscription(compositeMonitor.getId(), getConfig().getVisorEndpoint(),
                    SubscriptionType.CDO, FilterType.ANY, 0);
        }
}
