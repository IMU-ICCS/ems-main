/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.uniulm.omi.cloudiator.colosseum.client.entities.Application;
import de.uniulm.omi.cloudiator.colosseum.client.entities.SensorDescription;
import de.uniulm.omi.cloudiator.colosseum.client.entities.VirtualMachine;
import de.uniulm.omi.cloudiator.colosseum.client.entities.abstracts.Component;
import de.uniulm.omi.cloudiator.colosseum.client.entities.abstracts.Monitor;
import de.uniulm.omi.cloudiator.colosseum.client.entities.internal.KeyValue;

import eu.paasage.camel.metric.*;
import org.ow2.paasage.camel.srl.adapter.communication.FrontendCommunicator;
import org.ow2.paasage.camel.srl.adapter.utils.Convert;
import org.ow2.paasage.camel.srl.adapter.utils.ExternalReferenceHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Frank on 07.09.2015.
 */
public class RawMetricContextAdapter extends AbstractAdapter<Monitor> {
    private final RawMetricContext rawMetricContext;
    private final List<MetricInstance> metricInstances;
    private final String prefix;

    public RawMetricContextAdapter(FrontendCommunicator fc, RawMetricContext rawMetricContext,
                                   List<MetricInstance> metricInstances) {
        this(fc, rawMetricContext, metricInstances, null);
    }

    public RawMetricContextAdapter(FrontendCommunicator fc, RawMetricContext rawMetricContext,
        List<MetricInstance> metricInstances, String prefix) {
        super(fc);
        this.rawMetricContext = rawMetricContext;
        this.metricInstances = metricInstances;
        this.prefix = prefix;
    }

    @Override public Monitor adapt() {
        logger.info("Save RawMetricContext to colosseum: " + rawMetricContext.getName());


        /* TODO implement ... */
        RawMetric rawMetric = (RawMetric) rawMetricContext.getMetric();
        Long id = 0l;

        Application app = null;
        if (rawMetricContext.getApplication() != null) {

            app = getFc().getApplicationByName(rawMetricContext.getApplication().getName());
        }

        Component component = null;
        if (rawMetricContext.getComponent() != null) {
            String componentName = rawMetricContext.getComponent().getName();
            component = getFc().getComponentByName(componentName);
        }

        eu.paasage.camel.metric.Schedule camelSchedule = rawMetricContext.getSchedule();

        if (camelSchedule == null) {
            throw new RuntimeException(
                "No schedule assigned for RawMetricContext " + rawMetricContext.getName());
        }

        de.uniulm.omi.cloudiator.colosseum.client.entities.Schedule schedule = getFc()
            .saveSchedule(camelSchedule.getInterval(),
                Convert.toJavaTimeUnit(camelSchedule.getUnit()));

        // Classname and metricname decompilation:
        final String[] configurationSplit =
            rawMetricContext.getSensor().getConfiguration().split(";");

        String _className = configurationSplit[1];
        String _metricName = configurationSplit[0];
        Boolean _isVmSensor = true; /* TODO */

        /**
         * TODO
         * Integration of sensor configuration until integrated also in CAMEL natively.
         */
        Map<String, String> sensorConfiguration = new HashMap<String, String>();
        try {
            if (configurationSplit.length > 2) {
                int index = rawMetricContext.getSensor().getConfiguration()
                    .indexOf(";", _className.length() + _metricName.length() + 1);
                String jsonConfig =
                    rawMetricContext.getSensor().getConfiguration().substring(index + 1);

                ObjectMapper objectMapper = new ObjectMapper();
                sensorConfiguration = objectMapper.readValue(jsonConfig, HashMap.class);

                _isVmSensor = false; /* TODO only when linked to component */
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        SensorDescription sensorDescription =
            getFc().saveSensorDescription(_className, _metricName, _isVmSensor);

        //SensorConfigurations sensorConfigurations = getFc().saveSensorConfiguration(sensorConfiguration);

        Monitor rawMonitor = null;
        List<KeyValue> externalReferences = new ArrayList<>();

        KeyValue kv = ExternalReferenceHelper.getExternalReference(rawMetricContext, prefix);

        externalReferences.add(kv);

        if (app == null && component != null) {
            rawMonitor = getFc().doMonitorVms(null, /*TODO*/ component, schedule, sensorDescription,
                externalReferences, sensorConfiguration);
        } else if (app != null && component != null) {
            rawMonitor = getFc()
                .doMonitorVms(app, component, schedule, sensorDescription, externalReferences,
                    sensorConfiguration);
        } else {
            /**
             * TODO: implement other Monitor filters
             */
            throw new RuntimeException("Monitor filter not implemented!");
        }



        try {
            // Just for debugging reasons and wait for monitor instance creation
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (MetricInstance metricInstance : metricInstances) {

            KeyValue kvmi = ExternalReferenceHelper.getExternalReference(metricInstance, prefix);

            if (metricInstance.getMetricContext() == rawMetricContext) {

                if (metricInstance.getObjectBinding() instanceof MetricVMBinding) {
                    MetricVMBinding metricVMBinding =
                        (MetricVMBinding) metricInstance.getObjectBinding();

                    VirtualMachine frontendVM =
                        getFc().getVirtualMachineToIP(metricVMBinding.getVmInstance().getIp());

                    getFc().addExternalIdToMonitorInstance(rawMonitor, kvmi.getKey(), kvmi.getValue(), frontendVM);
                } else if (metricInstance.getObjectBinding() instanceof MetricComponentBinding) {
                    logger.info("Raw metric is bound to a component - add to linked VM.");
                    MetricComponentBinding metricComponentBinding =
                        (MetricComponentBinding) metricInstance.getObjectBinding();

                    if (metricComponentBinding.getVmInstance() == null) {
                        getFc().addExternalIdToEmptyMonitorInstance(rawMonitor, kvmi.getKey(), kvmi.getValue());
                    } else {
                        VirtualMachine frontendVM = getFc()
                            .getVirtualMachineToIP(metricComponentBinding.getVmInstance().getIp());

                        getFc().addExternalIdToMonitorInstance(rawMonitor, kvmi.getKey(), kvmi.getValue(), frontendVM);
                    }
                } else if (metricInstance.getObjectBinding() instanceof MetricApplicationBinding) {
                    getFc().addExternalIdToEmptyMonitorInstance(rawMonitor, kvmi.getKey(), kvmi.getValue());
                    logger.error("Raw metric is bound to an application - just add any cdo id.");
                } else {
                    logger.error("Raw metric is bound to something else. NOT IMPLEMENTED.");
                }
            }
        }

        return rawMonitor;
    }
}
