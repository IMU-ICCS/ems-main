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
import de.uniulm.omi.cloudiator.colosseum.client.entities.enums.FormulaOperator;
import de.uniulm.omi.cloudiator.colosseum.client.entities.internal.KeyValue;

import eu.paasage.camel.metric.*;
import org.ow2.paasage.camel.srl.adapter.communication.FrontendCommunicator;
import org.ow2.paasage.camel.srl.adapter.execution.Execution;
import org.ow2.paasage.camel.srl.adapter.utils.Convert;
import org.ow2.paasage.camel.srl.adapter.utils.ExternalReferenceHelper;
import org.ow2.paasage.camel.srl.adapter.utils.Transform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 03.09.2015.
 */
public class CompositeMetricContextAdapter extends AbstractAdapter<Monitor> {
    private final CompositeMetricContext context;
    private final List<MetricInstance> metricInstances;
    private final String prefix;

    public CompositeMetricContextAdapter(FrontendCommunicator fc, CompositeMetricContext context,
                                         List<MetricInstance> metricInstances) {
        this(fc, context, metricInstances, null);
    }

    public CompositeMetricContextAdapter(FrontendCommunicator fc, CompositeMetricContext context,
        List<MetricInstance> metricInstances, String prefix) {
        super(fc);
        this.context = context;
        this.metricInstances = metricInstances;
        this.prefix = prefix;
    }

    @Override public Monitor adapt() {
        logger.info("Save CompositeMetricContext to colosseum: " + context.getName());


        CompositeMetric compositeMetric = (CompositeMetric) context.getMetric();

        FormulaQuantifier quantifier = null;

        switch (context.getQuantifier()) {
            case ALL:
                quantifier = getFc().saveFormulaQuantifier(true, 1.0);
                break;
            case SOME:
                    /* TODO implement max and min quantity in execware */
                quantifier =
                    getFc().saveFormulaQuantifier(context.isIsRelative(), context.getMinQuantity());
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

        if (camelSchedule == null) {
            throw new RuntimeException("Composite metrics needs schedule!");
        }

        Schedule schedule = getFc().saveSchedule(camelSchedule.getInterval(),
            Convert.toJavaTimeUnit(camelSchedule.getUnit()));

        eu.paasage.camel.metric.Window camelWindow = context.getWindow(); /*TODO implement other units and window types */

        de.uniulm.omi.cloudiator.colosseum.client.entities.abstracts.Window window = null;

        if (camelWindow == null) {
            throw new RuntimeException("Composite metrics needs window!");
        } else {
            if (camelWindow.getSizeType().equals(WindowSizeType.MEASUREMENTS_ONLY)) {
                window = getFc().saveMeasurementWindow(camelWindow.getMeasurementSize());
            } else if (camelWindow.getSizeType().equals(WindowSizeType.TIME_ONLY)) {
                window = getFc().saveTimeWindow(camelWindow.getTimeSize(),
                    Convert.toJavaTimeUnit(camelWindow.getUnit()));
            } else if (camelWindow.getSizeType().equals(WindowSizeType.BOTH_MATCH)) {
                throw new RuntimeException(
                    "WindowSizeType.BOTH_MATCH not implemented in Adapter"); //TODO which window type to choose?
            } else if (camelWindow.getSizeType().equals(WindowSizeType.FIRST_MATCH)) {
                throw new RuntimeException(
                    "WindowSizeType.FIRST_MATCH not implemented in Adapter"); //TODO which window type to choose?
            }
        }



        FormulaOperator operator = Transform.operator(compositeMetric.getFormula().getFunction());

        FunctionPatternType functionPattern = compositeMetric.getFormula().getFunctionPattern();

        List<Monitor> composedMonitors = new ArrayList<>();
        for (Monitor monitor : getFc().getMonitors()) {
            for (MetricContext mc : context.getComposingMetricContexts()) {
                for (KeyValue s : monitor.getExternalReferences()) {
                    // TODO check for different external references
                    if ("CDOID".equals(s.getKey()) || "CAMEL".equals(s.getKey())) {

                        KeyValue kvmc = ExternalReferenceHelper.getExternalReference(mc, prefix);

                        if (s.getValue().equals(kvmc.getValue())) { // instead of checking by name mc.getName()
                            composedMonitors.add(monitor);
                        }
                    }
                }
            }
        }

        Monitor compositeMonitor = null;
        List<KeyValue> externalReferences = new ArrayList<>();
        KeyValue kv = ExternalReferenceHelper.getExternalReference(context, prefix);
        externalReferences.add(kv);

        logger.info("Add aggregator.");


        if (functionPattern == FunctionPatternType.MAP) {
            compositeMonitor = (ComposedMonitor) getFc()
                .mapAggregatedMonitors(quantifier, schedule, window, operator, composedMonitors,
                    Execution.getScalingActionByEventId(kv.getValue())
                            /*TODO this will never return an action, since no scaling action
                              TODO is ever directly added to a composed monitor context */,
                    externalReferences);
        } else if (functionPattern == FunctionPatternType.REDUCE) {
            compositeMonitor = (ComposedMonitor) getFc()
                .reduceAggregatedMonitors(quantifier, schedule, window, operator, composedMonitors,
                    Execution.getScalingActionByEventId(kv.getValue())
                            /*TODO this will never return an action, since no scaling action
                              TODO is ever directly added to a composed monitor context */,
                    externalReferences);
        } else {
            throw new RuntimeException("FunctionPatternType is not implemented!");
        }



        try {
            // Just for debugging reasons and wait for monitor instance creation
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (MetricInstance metricInstance : metricInstances) {

            // Not VM specific, so add all just another one /* TODO THIS IS SEMANTICALLY WRONG */
            for (MonitorInstance monitorInstance : getFc()
                .getMonitorInstances(compositeMonitor.getId())) {

                Boolean isAlreadyTagged = false;

                for (MetricInstance tempMetricInstance : metricInstances) {
                    for (KeyValue s : monitorInstance.getExternalReferences()) {
                        // TODO check for different external references
                        if ("CDOID".equals(s.getKey()) || "CAMEL".equals(s.getKey())){
                            if (s.getValue().equals(tempMetricInstance.getName())) {
                                isAlreadyTagged = true;
                                break;
                            }
                        }
                    }
                }

                if (!isAlreadyTagged) {
                    KeyValue kvmi = ExternalReferenceHelper.getExternalReference(metricInstance, prefix);

                    getFc().addExternalId(monitorInstance, kvmi.getKey(), kvmi.getValue());
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

        return compositeMonitor;
    }
}
