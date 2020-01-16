/*
 * Copyright (C) 2017-2020 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.upperware.penaltycalculator;

import lombok.Data;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Data
@Measurement(name = "ComponentTime", timeUnit = TimeUnit.SECONDS)
public class ComponMeasurement {

    @Column(name = "time")
    private Instant time;

    @Column(name = "ComponentName")
    private String ComponentName;

    @Column(name = "timeDepl")
    private double timeDepl;

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getComponentName() {
        return ComponentName;
    }

    public void setComponentName(String ComponentName) {
        this.ComponentName = ComponentName;
    }

    public double timeDepl() {
        return timeDepl;
    }

    public void timeDepl(double timeDepl) {
        this.timeDepl = timeDepl;
    }

    public String toString() {
        return timeDepl + "";
    }
}