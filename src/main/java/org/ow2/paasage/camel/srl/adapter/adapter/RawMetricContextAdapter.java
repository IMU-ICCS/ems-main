/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.adapter;

import de.uniulm.omi.cloudiator.colosseum.client.entities.Application;
import de.uniulm.omi.cloudiator.colosseum.client.entities.Schedule;
import de.uniulm.omi.cloudiator.colosseum.client.entities.SensorDescription;
import de.uniulm.omi.cloudiator.colosseum.client.entities.VirtualMachine;
import de.uniulm.omi.cloudiator.colosseum.client.entities.abstracts.Component;
import de.uniulm.omi.cloudiator.colosseum.client.entities.abstracts.Monitor;
import eu.paasage.camel.metric.*;
import org.ow2.paasage.camel.srl.adapter.communication.FrontendCommunicator;
import org.ow2.paasage.camel.srl.adapter.config.CommandLinePropertiesAccessor;
import org.ow2.paasage.camel.srl.adapter.utils.Convert;

import java.util.List;

/**
 * Created by Frank on 07.09.2015.
 */
public class RawMetricContextAdapter extends AbstractAdapter<Monitor> {
    private final RawMetricContext rawMetricContext;
    private final List<MetricInstance> metricInstances;

    public RawMetricContextAdapter(FrontendCommunicator fc, RawMetricContext rawMetricContext,
                                   List<MetricInstance> metricInstances) {
        super(fc);
        this.rawMetricContext = rawMetricContext;
        this.metricInstances = metricInstances;
    }

    @Override
    public Monitor adapt() {
        logger.info("Save RawMetricContext to colosseum: " + rawMetricContext.getName());


        /* TODO implement ... */
        RawMetric rawMetric = (RawMetric) rawMetricContext.getMetric();
        Long id = 0l;

        Application app = null;
        if (rawMetricContext.getApplication() != null) {

            app = getFc().getApplicationByName(
                    rawMetricContext.getApplication().getName());
        }

        Component component = null;
        if (rawMetricContext.getComponent() != null) {
            String componentName = rawMetricContext.getComponent().getName();
            component = getFc().getComponentByName(componentName);
        }

        eu.paasage.camel.metric.Schedule camelSchedule = rawMetricContext.getSchedule();

        if(camelSchedule == null){
            throw new RuntimeException("No schedule assigned for RawMetricContext " + rawMetricContext.getName());
        }

        Schedule schedule = getFc().saveSchedule(camelSchedule.getInterval(), Convert.toJavaTimeUnit(camelSchedule.getUnit()));
        String _className =
                rawMetricContext.getSensor().getConfiguration().split(";")[1];
        String _metricName =
                rawMetricContext.getSensor().getConfiguration().split(";")[0];
        Boolean _isVmSensor = true; /* TODO */

        SensorDescription sensorDescription = getFc().saveSensorDescription(_className, _metricName, _isVmSensor);

        Monitor rawMonitor = null;

        if (app == null && component != null) {
            rawMonitor = getFc().doMonitorVms(null, /*TODO*/ component, schedule,
                    sensorDescription);
        } else if (app != null && component != null) {
            rawMonitor = getFc().doMonitorVms(app, component, schedule, sensorDescription);
        } else {
            /**
             * TODO: implement other Monitor filters
             */
            throw new RuntimeException("Monitor filter not implemented!");
        }


        getFc().addExternalId(rawMonitor, rawMetricContext.getName());

        for (MetricInstance metricInstance : metricInstances) {
            if (metricInstance.getMetricContext() == rawMetricContext) {

                if(metricInstance.getObjectBinding() instanceof MetricVMBinding){
                    MetricVMBinding metricVMBinding = (MetricVMBinding) metricInstance.getObjectBinding();

                    VirtualMachine frontendVM = getFc().getVirtualMachineToIP(metricVMBinding
                            .getVmInstance().getIp());

                    getFc().addExternalIdToMonitorInstance(rawMonitor, metricInstance.cdoID().toString(), frontendVM);
                } else if(metricInstance.getObjectBinding() instanceof MetricComponentBinding) {
                    logger.info("Raw metric is bound to a component - add to linked VM.");
                    MetricComponentBinding metricComponentBinding = (MetricComponentBinding) metricInstance.getObjectBinding();

                    if(metricComponentBinding.getVmInstance() == null){
                        getFc().addExternalIdToEmptyMonitorInstance(rawMonitor, metricInstance.cdoID().toString());
                    } else {
                        VirtualMachine frontendVM = getFc().getVirtualMachineToIP(metricComponentBinding
                                .getVmInstance().getIp());

                        getFc().addExternalIdToMonitorInstance(rawMonitor, metricInstance.cdoID().toString(), frontendVM);
                    }
                } else if(metricInstance.getObjectBinding() instanceof MetricApplicationBinding) {
                    getFc().addExternalIdToEmptyMonitorInstance(rawMonitor, metricInstance.cdoID().toString());
                    logger.error("Raw metric is bound to an application - just add any cdo id.");
                } else {
                    logger.error("Raw metric is bound to something else. NOT IMPLEMENTED.");
                }
            }
        }

        return rawMonitor;
    }
}
