/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.adapter;

import org.ow2.paasage.camel.srl.adapter.communication.FrontendCommunicator;
import org.ow2.paasage.camel.srl.adapter.config.CommandLinePropertiesAccessor;
import eu.paasage.camel.requirement.HorizontalScaleRequirement;
import eu.paasage.camel.scalability.*;

import java.util.List;

/**
 * Created by Frank on 09.09.2015.
 */
public class ScalingActionAdapterFactoryImpl implements ScalingActionAdapterFactory {

    @Override
    public Adapter create(FrontendCommunicator fc, ScalingAction scalingAction,
                          List<ScalabilityRule> associatedRules, List<HorizontalScaleRequirement> associatedScaleRequirements) {
        if (scalingAction instanceof HorizontalScalingAction) {
            return new HorizontalScalingActionAdapter(fc, (HorizontalScalingAction) scalingAction, associatedRules, associatedScaleRequirements);
        } else {
            throw new RuntimeException("ScalingAction not yet implemented!");
        }
    }
}