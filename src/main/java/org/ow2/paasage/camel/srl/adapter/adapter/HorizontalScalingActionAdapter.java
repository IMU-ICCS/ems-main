/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.adapter;

import de.uniulm.omi.cloudiator.colosseum.client.entities.*;
import de.uniulm.omi.cloudiator.colosseum.client.entities.abstracts.Component;
import de.uniulm.omi.cloudiator.colosseum.client.entities.abstracts.ComponentHorizontalScalingAction;
import org.ow2.paasage.camel.srl.adapter.communication.FrontendCommunicator;
import org.ow2.paasage.camel.srl.adapter.config.CommandLinePropertiesAccessor;
import eu.paasage.camel.requirement.HorizontalScaleRequirement;
import eu.paasage.camel.scalability.HorizontalScalingAction;
import eu.paasage.camel.scalability.ScalabilityRule;

import java.util.List;

/**
 * Created by Frank on 09.09.2015.
 */
public class HorizontalScalingActionAdapter extends AbstractAdapter {
    private final HorizontalScalingAction scalingAction;
    private final List<ScalabilityRule> associatedRules;
    private final List<HorizontalScaleRequirement> associatedScaleRequirements;

    public HorizontalScalingActionAdapter(CommandLinePropertiesAccessor config, FrontendCommunicator fc, HorizontalScalingAction scalingAction,
                                          List<ScalabilityRule> associatedRules, List<HorizontalScaleRequirement> associatedScaleRequirements) {
        super(config, fc);
        this.scalingAction = scalingAction;
        this.associatedRules = associatedRules;
        this.associatedScaleRequirements = associatedScaleRequirements;
    }

    @Override
    public void adapt() {
        logger.info("Save ScalingAction to colosseum: " + scalingAction.getName());

        /* TODO implement VM scaling in the executionware */

        ComponentHorizontalScalingAction componentHorizontalScalingAction = null;
        Long min = null;
        Long max = null;
        Long count = null;



        for (HorizontalScaleRequirement horizontalScaleRequirement : associatedScaleRequirements) {
            count = (long) scalingAction.getCount();
            min = (long) horizontalScaleRequirement.getMinInstances();
            max = (long) horizontalScaleRequirement.getMinInstances();
        }

        Component component = getFc().getComponentByName(
                scalingAction.getInternalComponent().getName());

        switch (scalingAction.getType()) {
            case SCALE_OUT:
                componentHorizontalScalingAction =
                        getFc().saveComponentHorizontalOutScalingAction((long) (scalingAction.getCount()), min, max,
                                count, component);
                break;
            case SCALE_IN:
                componentHorizontalScalingAction =
                        getFc().saveComponentHorizontalInScalingAction((long) (scalingAction.getCount()), min, max,
                                count, component);
                break;
            default:
                throw new RuntimeException("Scaling Type not yet implemented!");
        }

        for (ScalabilityRule rule : associatedRules) {
                ComposedMonitor m = getFc().getComposedMonitorByExternalId(rule.getEvent().cdoID().toString());
                getFc().addScalingActionToMonitor(m, componentHorizontalScalingAction);
                /* TODO ADD LISTENER TO EVENT-MONITOR NOT DIRECTLY IN THE AGGREGATOR SERVICE, SINCE THIS WILL LATER NOT BE ACCESSIBLE LOCALLY */
                //fc.addObserverToMonitor(m.getId(), 0.9 /*ungenauigkeit*/, FormulaOperator.GT); create: CliMetricObserver
        }
    }
}