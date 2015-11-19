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
import org.ow2.paasage.camel.srl.adapter.utils.Convert;
import eu.paasage.camel.metric.Schedule;

/**
 * Created by Frank on 03.09.2015.
 */
public class ScheduleAdapter extends AbstractAdapter<de.uniulm.omi.cloudiator.colosseum.client.entities.Schedule> {

    private final Schedule schedule;

    public ScheduleAdapter(FrontendCommunicator fc, Schedule schedule) {
        super(fc);
        this.schedule = schedule;
    }

    @Override
    public de.uniulm.omi.cloudiator.colosseum.client.entities.Schedule adapt() {

        /* TODO implement repetitions, etc. */

        logger.info("Save schedule to colosseum: " + schedule.getName());

        de.uniulm.omi.cloudiator.colosseum.client.entities.Schedule colosseumSchedule = getFc().saveSchedule(schedule.getInterval(), Convert.toJavaTimeUnit(schedule.getUnit()));

        return colosseumSchedule;
    }
}
