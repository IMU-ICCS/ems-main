/*
 * Copyright (C) 2019 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */


package eu.melodic.vassilis.staff;
 
import java.time.Instant;
import java.util.concurrent.TimeUnit;
 
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
 
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
	
	public String toString() { return timeDepl+""; }
}