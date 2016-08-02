/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.uniulm.omi.cloudiator.colosseum.client.entities.SensorDescription;
import eu.paasage.camel.metric.Sensor;
import org.ow2.paasage.camel.srl.adapter.communication.FrontendCommunicator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        // Classname and metricname decompilation:
        final String[] configurationSplit =
                sensor.getConfiguration().split(";");

        String _className;
        String _metricName;
        Boolean _isVmSensor = true; /* TODO */
        Boolean _isPush = sensor.isIsPush();
        Map<String, String> sensorConfiguration = new HashMap<String, String>();

        if (_isPush) {
            if (configurationSplit.length != 1) {
                throw new IllegalArgumentException("Wrong definition of configuration in sensor " +
                        "for push sensor (1). Expected configuration schema: configuration" +
                        " = \"[metric_name]\". Found illegal configuration:" +
                        sensor.getConfiguration());
            }

            _className = "_no_class_name_";
            _metricName = configurationSplit[0];

        } else {
            if (configurationSplit.length < 2) {
                throw new IllegalArgumentException("Wrong definition of configuration in sensor " +
                        "for pull sensor (1). Expected configuration schema: configuration" +
                        " = \"[metric_name;class_name(;configuration_json)]\". Found illegal configuration:" +
                        sensor.getConfiguration());
            }

            _className = configurationSplit[1];
            _metricName = configurationSplit[0];

            /**
             * TODO
             * Integration of sensor configuration until integrated also in CAMEL natively.
             */
            try {
                if (configurationSplit.length > 2) {
                    int index = sensor.getConfiguration()
                            .indexOf(";", _className.length() + _metricName.length() + 1);
                    String jsonConfig =
                            sensor.getConfiguration().substring(index + 1);

                    ObjectMapper objectMapper = new ObjectMapper();
                    sensorConfiguration = objectMapper.readValue(jsonConfig, HashMap.class);

                    //_isVmSensor = false; /* TODO only when linked to component */
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        SensorDescription sensorDescription =
                getFc().saveSensorDescription(_className, _metricName, _isVmSensor, _isPush);

        logger.info("Save sensor to colosseum: " + sensor.getName());

        return sensorDescription;
    }
}
