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
import de.uniulm.omi.cloudiator.colosseum.client.entities.enums.FilterType;
import de.uniulm.omi.cloudiator.colosseum.client.entities.enums.FormulaOperator;
import de.uniulm.omi.cloudiator.colosseum.client.entities.enums.SubscriptionType;
import org.ow2.paasage.camel.srl.adapter.communication.FrontendCommunicator;
import org.ow2.paasage.camel.srl.adapter.config.CommandLinePropertiesAccessor;
import org.ow2.paasage.camel.srl.adapter.utils.Transform;
import eu.paasage.camel.scalability.BinaryEventPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 06.09.2015.
 */
public class BinaryEventPatternAdapter extends AbstractAdapter<ComposedMonitor> {
    private final BinaryEventPattern eventPattern;

    public BinaryEventPatternAdapter(FrontendCommunicator fc, BinaryEventPattern eventPattern) {
        super(fc);
        this.eventPattern = eventPattern;
    }

    @Override
    public ComposedMonitor adapt() {
        logger.info("Save EventPattern to colosseum: " + eventPattern.getName());

        List<Monitor> composedMonitors = new ArrayList<Monitor>();

        for (Monitor m : getFc().getMonitors()) {
            for (String s : m.getExternalReferences()) {
                String left = eventPattern.getLeftEvent().getName();
                String right = eventPattern.getRightEvent().getName();
                if (s.equals(left) || s.equals(right)) {
                    composedMonitors.add(m);
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



        ComposedMonitor composedMonitor = (ComposedMonitor) getFc()
                .reduceAggregatedMonitors(quantifier, schedule, window, operator,
                        composedMonitors);


        getFc().addExternalId(composedMonitor, eventPattern.getName());



        for (MonitorInstance monitorInstance : getFc()
                .getMonitorInstances(composedMonitor.getId())) {
            getFc().addExternalId(monitorInstance, eventPattern.cdoID().toString());
        }


        return composedMonitor;
    }
}
