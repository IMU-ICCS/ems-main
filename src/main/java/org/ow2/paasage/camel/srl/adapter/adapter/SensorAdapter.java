/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.adapter;

import de.uniulm.omi.cloudiator.colosseum.client.entities.SensorDescription;
import eu.paasage.camel.metric.Sensor;
import org.ow2.paasage.camel.srl.adapter.communication.FrontendCommunicator;

/**
 * Created by Frank on 03.09.2015.
 */
public class SensorAdapter extends AbstractAdapter<SensorDescription> {

    private final Sensor sensor;

    public SensorAdapter(FrontendCommunicator fc, Sensor sensor) {
        super(fc);
        this.sensor = sensor;
    }

    @Override public SensorDescription adapt() {
        String _className = sensor.getConfiguration().split(";")[1];
        String _metricName = sensor.getConfiguration().split(";")[0];
        Boolean _isVmSensor = true;

        logger.info("Save sensor to colosseum: " + sensor.getName());

        SensorDescription sensorDescription =
            getFc().saveSensorDescription(_className, _metricName, _isVmSensor);

        return sensorDescription;
    }
}
