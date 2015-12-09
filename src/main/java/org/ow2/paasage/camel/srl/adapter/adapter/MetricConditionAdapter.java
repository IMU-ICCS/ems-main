/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.adapter;

import de.uniulm.omi.cloudiator.colosseum.client.entities.*;
import de.uniulm.omi.cloudiator.colosseum.client.entities.abstracts.Monitor;
import de.uniulm.omi.cloudiator.colosseum.client.entities.enums.FormulaOperator;
import org.ow2.paasage.camel.srl.adapter.communication.FrontendCommunicator;
import org.ow2.paasage.camel.srl.adapter.execution.Execution;
import org.ow2.paasage.camel.srl.adapter.utils.Convert;
import org.ow2.paasage.camel.srl.adapter.utils.Transform;
import eu.paasage.camel.metric.MetricCondition;
import eu.paasage.camel.metric.MetricContext;
import eu.paasage.camel.scalability.NonFunctionalEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 04.09.2015.
 */
public class MetricConditionAdapter extends AbstractAdapter<ComposedMonitor> {
    private final MetricCondition metricCondition;
    private final NonFunctionalEvent event;

    public MetricConditionAdapter(FrontendCommunicator fc, MetricCondition metricCondition, NonFunctionalEvent event) {
        super(fc);
        this.metricCondition = metricCondition;
        this.event = event;
    }

    @Override
    public ComposedMonitor adapt() {
        logger.info("Save Condition to colosseum: " + metricCondition.getName());

            /* TODO implement ... */
        MetricContext metricContext = metricCondition.getMetricContext();

        ConstantMonitor threshold = getFc().saveConstantMonitor(metricCondition.getThreshold());
        List<Monitor> composedMonitors = new ArrayList<Monitor>();

        int amountInstances = 0;
        for (Monitor m : getFc().getMonitors()) {
            for (String s : m.getExternalReferences()) {
                if (s.equals(metricContext.getName())) {
                    composedMonitors.add(m);
                    amountInstances += getFc().getMonitorInstances(m.getId()).size();
                }
            }
        }
        composedMonitors.add(threshold);

        eu.paasage.camel.metric.Schedule camelSchedule = metricContext.getSchedule();

        Schedule schedule = getFc().saveSchedule(camelSchedule.getInterval(), Convert.toJavaTimeUnit(camelSchedule.getUnit()));

        // Window does not make sense, since we only use the last value to get checked:
        //
        //eu.paasage.camel.metric.Window camelWindow = metricContext.getWindow();
        //TimeWindow window = fc.saveTimeWindow(camelWindow.getTimeSize(),
        //    Convert.toJavaTimeUnit(camelWindow.getUnit()));
        //
        //Only use last value:
        MeasurementWindow window_1_measurment = getFc().saveMeasurementWindow(1l);

        FormulaOperator operator = Transform.condition(metricCondition.getComparisonOperator());


        // Quantifier is not used, since we use the quantifier in the executionware
        // differently. Now we just use a monitor of the value that is calculated
        // by the quantifier.
        FormulaQuantifier quantifier = null;
        ConstantMonitor quantifierMonitor = null;

        int minimumApplies;

        switch (metricContext.getQuantifier()) {
            case ALL:
                minimumApplies = amountInstances;
                quantifier = getFc().saveFormulaQuantifier(true, 1.0);
                break;
            case SOME:
                    /* TODO implement max and min quantity in execware */
                if (metricContext.isIsRelative()) {
                    minimumApplies = (int) Math
                            .ceil(metricContext.getMinQuantity() * amountInstances);
                } else {
                    minimumApplies = (int) metricContext.getMinQuantity();
                }

                quantifier = getFc().saveFormulaQuantifier(metricContext.isIsRelative(),
                        metricContext.getMinQuantity());
                break;
            case ANY:
                minimumApplies = 1;
                quantifier = getFc().saveFormulaQuantifier(false, 1.0);
                break;
            default:
                throw new RuntimeException("Quantifier not implemented");
        }

        FormulaQuantifier quantifierAll = getFc().saveFormulaQuantifier(true, 1.0);


        /*************************************************
         *
         * Approach with applying condition to 3 monitors:
         *
         *************************************************/


        // Condition:
        // TODO not save condition id, since it is never referenced furthermore?
        //fc.addExternalId(composedMonitor, condition.getName());
        // NFE:
        final String idNFE;
        if(event.cdoID() != null){
            idNFE = event.cdoID().toString();
        } else {
            idNFE = event.getName(); /* TODO if CDO is not available this ID might not by
                                        TODO unique through different model instances */
        }


        //1. compute which apply:
        List<String> externalReferencesThreshold = new ArrayList<>();
        externalReferencesThreshold.add(idNFE + "_threshold");
        ComposedMonitor thresholdMonitor = (ComposedMonitor) getFc()
                .mapAggregatedMonitors(quantifierAll /* quantifier TODO currently only ALL is implemented, minimum applies is used for constant monitor*/,
                        schedule, window_1_measurment, operator, composedMonitors, null, externalReferencesThreshold);

        List<Monitor> thresholdMonitors = new ArrayList();
        thresholdMonitors.add(thresholdMonitor);

        //2. sum all applied up
        List<String> externalReferencesApply = new ArrayList<>();
        externalReferencesApply.add(idNFE + "_apply");
        ComposedMonitor applyMonitor = (ComposedMonitor) getFc()
                .reduceAggregatedMonitors(quantifierAll, schedule, window_1_measurment,
                        FormulaOperator.SUM, thresholdMonitors, null, externalReferencesApply);

        List<Monitor> applyMonitors = new ArrayList();
        applyMonitors.add(applyMonitor);
        //create monitor based on quantifier to check if condition is violated:
        quantifierMonitor = getFc().saveConstantMonitor((double) minimumApplies);
        applyMonitors.add(quantifierMonitor);

        //3. compute with condition is violated
        List<String> externalReferencesCondition = new ArrayList<>();
        externalReferencesCondition.add(idNFE);
        ComposedMonitor conditionMonitor = (ComposedMonitor) getFc()
                .mapAggregatedMonitors(quantifierAll, schedule, window_1_measurment,
                        FormulaOperator.GTE, applyMonitors, Execution.getScalingActionByEventId(idNFE), externalReferencesCondition);


            /* Do it with FormulaQunatifier as "minimumApplied"
            ComposedMonitor composedMonitor = (ComposedMonitor)fc.reduceAggregatedMonitors(
                quantifier, schedule, window_1_measurment, operator, composedMonitors);
            */





        try {
            // Just for debugging reasons
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (MonitorInstance monitorInstance : getFc()
                .getMonitorInstances(conditionMonitor.getId())) {
            getFc().addExternalId(monitorInstance, idNFE);
        }

        return conditionMonitor;
    }
}
