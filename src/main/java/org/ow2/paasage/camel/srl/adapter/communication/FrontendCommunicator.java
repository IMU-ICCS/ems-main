/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.communication;

import de.uniulm.omi.cloudiator.colosseum.client.entities.*;
import de.uniulm.omi.cloudiator.colosseum.client.entities.abstracts.*;
import de.uniulm.omi.cloudiator.colosseum.client.entities.enums.FilterType;
import de.uniulm.omi.cloudiator.colosseum.client.entities.enums.FormulaOperator;
import de.uniulm.omi.cloudiator.colosseum.client.entities.enums.SubscriptionType;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Frank on 08.06.2015.
 */
public interface FrontendCommunicator {
    /** basic monitoring interface */
    /** monitor all component instances attached to this application Id */
    Monitor doMonitorComponents(Application app, Schedule schedule, SensorDescription desc);
    /** monitor all component instances attached to this application Id and component Id*/
    Monitor doMonitorComponents(Application app, Component component, Schedule schedule, SensorDescription desc);
    /** monitor this particular component instance */
    Monitor doMonitorComponents(Application app, Component component, Instance instance, Schedule schedule, SensorDescription desc);

    /** monitor all component instances of this component in this cloud */
    Monitor doMonitorComponents(Application app, Component component, Cloud cloud, Schedule schedule, SensorDescription desc);
    /** monitor all component instances in this cloud */
    Monitor doMonitorComponents(Application app, Cloud cloud, Schedule schedule, SensorDescription desc);

    /** all vms of this application */
    Monitor doMonitorVms(Application app, Schedule schedule, SensorDescription desc);
    /** all vms of this application in this cloud */
    Monitor doMonitorVms(Application app, Cloud component, Schedule schedule, SensorDescription desc);
    /** all vms of this application running a component instance of this component */
    Monitor doMonitorVms(Application app, Component component, Schedule schedule, SensorDescription desc, List<String> externalReferences);
    /** all vms of this application running a component instance of this component in this cloud */
    Monitor doMonitorVms(Application app, Component component, Cloud cloud, Schedule schedule, SensorDescription desc);

    /** most simple map and reduce **/
    Monitor mapAggregatedMonitors(FormulaQuantifier quantifier, Schedule schedule, Window window, FormulaOperator formulaOperator, List<Monitor> monitors, List<Long> scalingActions, List<String> externalReferences);
    Monitor reduceAggregatedMonitors(FormulaQuantifier quantifier, Schedule schedule, Window window, FormulaOperator formulaOperator, List<Monitor> monitors, List<Long> scalingActions, List<String> externalReferences);

    /** remove a monitor */
    void removeMonitor(Monitor monitor);
    void removeMonitor(Long monitorId);

    /** side-effected getters */
    SensorDescription saveSensorDescription(String className, String metricName, Boolean isVmSensor);
    TimeWindow saveTimeWindow(Long timeSize, TimeUnit timeUnit);
    MeasurementWindow saveMeasurementWindow(Long measurements);
    Schedule saveSchedule(Long interval, TimeUnit timeUnit);
    FormulaQuantifier saveFormulaQuantifier(Boolean relative, Double value);
    ConstantMonitor saveConstantMonitor(Double value);
    ComponentHorizontalOutScalingAction saveComponentHorizontalOutScalingAction(Long count, Long min, Long max, Long count1,
        Component component);
    ComponentHorizontalInScalingAction saveComponentHorizontalInScalingAction(Long count, Long min, Long max, Long count1,
        Component component);

    /** ScalingAction composing */
    void addScalingActionToMonitor(ComposedMonitor m, ScalingAction scalingAction);

    /** Searching methods */
    TimeWindow getSmallestTimeWindow(List<Monitor> monitors);
    Schedule getLowestSchedule(List<Monitor> monitors);
    Component getComponentByName(String name);
    Application getApplicationByName(String name);
    VirtualMachine getVirtualMachineToIP(String ip);
    ComposedMonitor getComposedMonitorByExternalId(String name);
    List<RawMonitor> getRawMonitors();
    List<Monitor> getMonitors();
    List<MonitorInstance> getMonitorInstances(Long monitorId);
    List<MonitorInstance> getMonitorInstances();
    List<ComposedMonitor> getComposedMonitors();
    List<ConstantMonitor> getConstantMonitors();
    SensorDescription getSensorDescription(Long id);

    /** External IDs */
    void addExternalId(Monitor monitor, String externalId);
    void addExternalId(MonitorInstance monitorInstance, String externalId);
    void addExternalIdToMonitorInstance(Monitor monitor, String externalId, VirtualMachine virtualMachine);
    void addExternalIdToEmptyMonitorInstance(Monitor monitor, String externalId);

    /** Initializing */
    void clearAllMonitoringAgents();

    MonitorSubscription addMonitorSubscription(Long monitor, String endpoint,
        SubscriptionType type, FilterType filterType, double filterValue);

    Monitor doMonitor(Application app, Component component, Instance instance, Cloud cloud,
        Schedule schedule, SensorDescription desc, List<String> externalReferences);

    public void removeMonitorSubscription(Long id);
    public void removeAllMonitorSubscriptions();

    void cleanMonitoring();

    String getPublicIpOfVmByName(String name);
}
