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
import de.uniulm.omi.cloudiator.colosseum.client.entities.internal.KeyValue;

import eu.paasage.camel.scalability.BinaryEventPattern;
import org.ow2.paasage.camel.srl.adapter.communication.FrontendCommunicator;
import org.ow2.paasage.camel.srl.adapter.execution.Execution;
import org.ow2.paasage.camel.srl.adapter.utils.ExternalReferenceHelper;
import org.ow2.paasage.camel.srl.adapter.utils.Transform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 06.09.2015.
 */
public class BinaryEventPatternAdapter extends AbstractAdapter<ComposedMonitor> {
    private final BinaryEventPattern eventPattern;
    private final String prefix;

    public BinaryEventPatternAdapter(FrontendCommunicator fc, BinaryEventPattern eventPattern) {
        this(fc, eventPattern, null);
    }

    public BinaryEventPatternAdapter(FrontendCommunicator fc, BinaryEventPattern eventPattern, String prefix) {
        super(fc);
        this.eventPattern = eventPattern;
        this.prefix = prefix;
    }

    @Override public ComposedMonitor adapt() {
        logger.info("Save EventPattern to colosseum: " + eventPattern.getName());

        List<Monitor> composedMonitors = new ArrayList<Monitor>();

        for (Monitor m : getFc().getMonitors()) {
            for (KeyValue kv : m.getExternalReferences()) {
                String k = kv.getKey();
                // TODO make this more generic, not just CAMEL or CDO!
                if("CDOID".equals(k) || "CAMEL".equals(k)) {
                    String v = kv.getValue();

                    String left = eventPattern.getLeftEvent().getName();
                    String right = eventPattern.getRightEvent().getName();
                    if (v.equals(left) || v.equals(right)) {
                        composedMonitors.add(m);
                    }
                }
            }
        }

        // TODO actually a new schedule with least common divisible of all:
        Schedule schedule = getFc().getLowestSchedule(composedMonitors);


        // TODO check which is correct:
        // TimeWindow window = fc.getSmallestTimeWindow(composedMonitors); /* TODO implement other window types */
        MeasurementWindow window = getFc().saveMeasurementWindow(1l);

        FormulaOperator operator = Transform.binary(eventPattern.getOperator());



        FormulaQuantifier quantifier = getFc().saveFormulaQuantifier(true, 1.0); /* TODO implement occurrences as quantifier */


        List<KeyValue> externalReferences = new ArrayList<>();

        KeyValue kv = ExternalReferenceHelper.getExternalReference(eventPattern, prefix);

        externalReferences.add(kv);


        ComposedMonitor composedMonitor = (ComposedMonitor) getFc()
            .reduceAggregatedMonitors(quantifier, schedule, window, operator, composedMonitors,
                Execution.getScalingActionByEventId(kv.getKey()), externalReferences);



        try {
            // Just for debugging reasons and wait for monitor instance creation
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (MonitorInstance monitorInstance : getFc()
            .getMonitorInstances(composedMonitor.getId())) {

            getFc().addExternalId(monitorInstance, kv.getKey(), kv.getValue());
        }


        return composedMonitor;
    }
}
