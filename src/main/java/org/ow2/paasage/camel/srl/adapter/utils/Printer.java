/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.utils;

import de.uniulm.omi.cloudiator.colosseum.client.entities.ComposedMonitor;
import de.uniulm.omi.cloudiator.colosseum.client.entities.MonitorInstance;
import de.uniulm.omi.cloudiator.colosseum.client.entities.RawMonitor;
import de.uniulm.omi.cloudiator.colosseum.client.entities.SensorDescription;
import org.ow2.paasage.camel.srl.adapter.communication.FrontendCommunicator;

/**
 * Created by Frank on 07.09.2015.
 */
public class Printer {
    private String lf = "\n";
    private final FrontendCommunicator fc;

    public Printer(FrontendCommunicator fc) {
        this.fc = fc;
    }

    public String printMonitorInstances() {
        String result = "";
        for (MonitorInstance monitor : fc.getMonitorInstances()) {
            result += "------------ MONITOR INSTANCES ---------" + lf;
            result += "ID: " + monitor.getId() + lf;
            String externalId = "";
            for (String s : monitor.getExternalReferences()) {
                externalId += (s + ";");
            }
            result += "Ex ID: " + externalId + lf;
            result += "IP ID: " + monitor.getIpAddress() + lf;
        }
        return result;
    }

    public String printRawMetrics() {
        String result = "";
        for (RawMonitor monitor : fc.getRawMonitors()) {
            result += "------------ RAW MONITOR ---------" + lf;
            result += "ID: " + monitor.getId() + lf;
            String externalId = "";
            for (String s : monitor.getExternalReferences()) {
                externalId += (s + ";");
            }
            result += "Ex ID: " + externalId + lf;
            SensorDescription sensorDescription =
                fc.getSensorDescription(monitor.getSensorDescription());
            result += "Class: " + sensorDescription.getClassName() + lf;
        }
        return result;
    }

    public String printCompositeMetrics() {
        String result = "";
        for (ComposedMonitor monitor : fc.getComposedMonitors()) {
            result += "------------ AGGREGATED MONITORS ---------" + lf;
            result += "ID: " + monitor.getId() + lf;
            String externalId = "";
            for (String s : monitor.getExternalReferences()) {
                externalId += (s + ";");
            }
            result += "Ex ID: " + externalId + lf;
            result += "Class: " + monitor.getFunction() + lf;
        }
        return result;
    }
}
