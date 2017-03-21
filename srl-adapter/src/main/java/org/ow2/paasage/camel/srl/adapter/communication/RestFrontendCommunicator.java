/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.communication;

import de.uniulm.omi.cloudiator.colosseum.client.Client;
import de.uniulm.omi.cloudiator.colosseum.client.ClientController;
import de.uniulm.omi.cloudiator.colosseum.client.entities.Application;
import de.uniulm.omi.cloudiator.colosseum.client.entities.Cloud;
import de.uniulm.omi.cloudiator.colosseum.client.entities.ComponentHorizontalInScalingAction;
import de.uniulm.omi.cloudiator.colosseum.client.entities.ComponentHorizontalOutScalingAction;
import de.uniulm.omi.cloudiator.colosseum.client.entities.ComposedMonitor;
import de.uniulm.omi.cloudiator.colosseum.client.entities.ConstantMonitor;
import de.uniulm.omi.cloudiator.colosseum.client.entities.FormulaQuantifier;
import de.uniulm.omi.cloudiator.colosseum.client.entities.Instance;
import de.uniulm.omi.cloudiator.colosseum.client.entities.IpAddress;
import de.uniulm.omi.cloudiator.colosseum.client.entities.LifecycleComponent;
import de.uniulm.omi.cloudiator.colosseum.client.entities.MeasurementWindow;
import de.uniulm.omi.cloudiator.colosseum.client.entities.MonitorInstance;
import de.uniulm.omi.cloudiator.colosseum.client.entities.MonitorSubscription;
import de.uniulm.omi.cloudiator.colosseum.client.entities.RawMonitor;
import de.uniulm.omi.cloudiator.colosseum.client.entities.Schedule;
import de.uniulm.omi.cloudiator.colosseum.client.entities.SensorConfigurations;
import de.uniulm.omi.cloudiator.colosseum.client.entities.SensorDescription;
import de.uniulm.omi.cloudiator.colosseum.client.entities.TimeWindow;
import de.uniulm.omi.cloudiator.colosseum.client.entities.VirtualMachine;
import de.uniulm.omi.cloudiator.colosseum.client.entities.abstracts.Component;
import de.uniulm.omi.cloudiator.colosseum.client.entities.abstracts.ExternalReferencedEntity;
import de.uniulm.omi.cloudiator.colosseum.client.entities.abstracts.Monitor;
import de.uniulm.omi.cloudiator.colosseum.client.entities.abstracts.ScalingAction;
import de.uniulm.omi.cloudiator.colosseum.client.entities.abstracts.Window;
import de.uniulm.omi.cloudiator.colosseum.client.entities.enums.FilterType;
import de.uniulm.omi.cloudiator.colosseum.client.entities.enums.FlowOperator;
import de.uniulm.omi.cloudiator.colosseum.client.entities.enums.FormulaOperator;
import de.uniulm.omi.cloudiator.colosseum.client.entities.enums.SubscriptionType;
import de.uniulm.omi.cloudiator.colosseum.client.entities.internal.AbstractEntity;
import de.uniulm.omi.cloudiator.colosseum.client.entities.internal.KeyValue;
import de.uniulm.omi.cloudiator.colosseum.client.entities.internal.NamedEntity;

import org.ow2.paasage.camel.srl.adapter.utils.Convert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Frank on 08.06.2015.
 */
public class RestFrontendCommunicator implements FrontendCommunicator {

    private final Client client;

    public RestFrontendCommunicator(Client client) {
        this.client = client;
    }

    @Override
    public Monitor doMonitorComponents(Application app, Schedule schedule, SensorDescription desc) {
        return doMonitor(app, null, null, null, schedule, desc, null, null);
    }

    @Override
    public Monitor doMonitorComponents(Application app, Component component, Schedule schedule,
                                       SensorDescription desc) {
        return doMonitor(app, component, null, null, schedule, desc, null, null);
    }

    @Override
    public Monitor doMonitorComponents(Application app, Component component, Instance instance,
                                       Schedule schedule, SensorDescription desc) {
        return doMonitor(app, component, instance, null, schedule, desc, null, null);
    }

    @Override
    public Monitor doMonitorComponents(Application app, Component component, Cloud cloud,
                                       Schedule schedule, SensorDescription desc) {
        return doMonitor(app, component, null, cloud, schedule, desc, null, null);
    }

    @Override
    public Monitor doMonitorComponents(Application app, Cloud cloud, Schedule schedule,
                                       SensorDescription desc) {
        return doMonitor(app, null, null, cloud, schedule, desc, null, null);
    }

    @Override
    public Monitor doMonitorVms(Application app, Schedule schedule, SensorDescription desc) {
        return doMonitor(app, null, null, null, schedule, desc, null, null);
    }

    @Override
    public Monitor doMonitorVms(Application app, Cloud cloud, Schedule schedule,
                                SensorDescription desc) {
        return doMonitor(app, null, null, cloud, schedule, desc, null, null);
    }

    @Override
    public Monitor doMonitorVms(Application app, Component component, Schedule schedule,
                                SensorDescription desc, List<KeyValue> externalReferences,
                                Map<String, String> sensorConfiguration) {
        return doMonitor(app, component, null, null, schedule, desc, externalReferences,
                sensorConfiguration);
    }

    @Override
    public Monitor doMonitorVms(Application app, Component component, Cloud cloud,
                                Schedule schedule, SensorDescription desc) {
        return doMonitor(app, component, null, cloud, schedule, desc, null, null);
    }

    @Override
    public Monitor doMonitor(Application app, Component component, Instance instance, Cloud cloud,
                             Schedule schedule, SensorDescription desc, List<KeyValue> externalReferences,
                             Map<String, String> sensorConfiguration) {

        RawMonitor rm = new RawMonitor((app == null ? null : app.getId()),
                (component == null ? null : component.getId()),
                (instance == null ? null : instance.getId()), (cloud == null ? null : cloud.getId()),
                desc.getId(), schedule.getId());
        rm.setExternalReferences(externalReferences);

        if (sensorConfiguration != null && !sensorConfiguration.isEmpty()) {
            SensorConfigurations sensorConfigurations =
                    this.saveSensorConfiguration(sensorConfiguration);
            rm.setSensorConfigurations(sensorConfigurations.getId());
        }

        return client.controller(RawMonitor.class).create(rm);
    }

    @Override
    public Monitor mapAggregatedMonitors(FormulaQuantifier quantifier, Schedule schedule,
                                         Window window, FormulaOperator formulaOperator, List<Monitor> monitors,
                                         List<Long> scalingActions, List<KeyValue> externalReferences) {
        return this
                .doAggregateMonitor(FlowOperator.MAP, quantifier, schedule, window, formulaOperator,
                        monitors, scalingActions, externalReferences);
    }

    @Override
    public Monitor reduceAggregatedMonitors(FormulaQuantifier quantifier, Schedule schedule,
                                            Window window, FormulaOperator formulaOperator, List<Monitor> monitors,
                                            List<Long> scalingActions, List<KeyValue> externalReferences) {
        return this
                .doAggregateMonitor(FlowOperator.REDUCE, quantifier, schedule, window, formulaOperator,
                        monitors, scalingActions, externalReferences);
    }

    private Monitor doAggregateMonitor(FlowOperator flowOperator, FormulaQuantifier quantifier,
                                       Schedule schedule, Window window, FormulaOperator formulaOperator, List<Monitor> monitors,
                                       List<Long> scalingActions, List<KeyValue> externalReferences) {
        List<Long> monitorIds = new ArrayList<>();
        for (Monitor monitor : monitors) {
            monitorIds.add(monitor.getId());
        }


        ComposedMonitor cm =
                new ComposedMonitor(flowOperator, formulaOperator, quantifier.getId(), window.getId(),
                        monitorIds, scalingActions, schedule.getId());

        cm.setExternalReferences(externalReferences);

        return client.controller(ComposedMonitor.class).create(cm);
    }

    @Override
    public void removeMonitor(Monitor monitor) {
        this.removeMonitor(monitor.getId());
    }

    @Override
    public void removeMonitor(Long monitorId) {
        for (Monitor m : this.getMonitors()) {
            if (m.getId().equals(monitorId)) {
                if (m instanceof ConstantMonitor) {
                    client.controller(ConstantMonitor.class).delete((ConstantMonitor) m);
                } else if (m instanceof RawMonitor) {
                    client.controller(RawMonitor.class).delete((RawMonitor) m);
                } else if (m instanceof ComposedMonitor) {
                    client.controller(ComposedMonitor.class).delete((ComposedMonitor) m);
                }
            }
        }
    }

    @Override
    public SensorDescription saveSensorDescription(String className, String metricName,
                                                   Boolean isVmSensor, Boolean isPush) {
        SensorDescription sensorDescription = new SensorDescription(className, metricName, isVmSensor, isPush);

        for (SensorDescription sd : client.controller(SensorDescription.class).getList()) {
            if ((sd.getClassName() == null ? sensorDescription.getClassName() == null : sd.getClassName().equals(sensorDescription.getClassName()))
                    && (sd.getMetricName() == null ? sensorDescription.getMetricName() == null : sd.getMetricName().equals(sensorDescription.getMetricName()))
                    && (sd.getIsVmSensor() == null ? sensorDescription.getIsVmSensor() == null : sd.getIsVmSensor().equals(sensorDescription.getIsVmSensor()))
                    && (sd.getIsPush() == null ? sensorDescription.getIsPush() == null : sd.getIsPush().equals(sensorDescription.getIsPush()))) {
                return sd;
            }
        }

        return client.controller(SensorDescription.class).create(sensorDescription);
    }

    @Override
    public TimeWindow saveTimeWindow(Long timeSize, TimeUnit timeUnit) {
        TimeWindow timeWindow = new TimeWindow(timeSize, timeUnit);

        for (TimeWindow tw : client.controller(TimeWindow.class).getList()) {
            if ((tw.getInterval() == null ? timeWindow.getInterval() == null : tw.getInterval().equals(timeWindow.getInterval()))
                    && (tw.getTimeUnit() == null ? timeWindow.getTimeUnit() == null : tw.getTimeUnit().equals(timeWindow.getTimeUnit()))) {
                return tw;
            }
        }

        return client.controller(TimeWindow.class).create(timeWindow);
    }

    @Override
    public Schedule saveSchedule(Long interval, TimeUnit timeUnit) {
        Schedule schedule = new Schedule(interval, timeUnit);

        for (Schedule s : client.controller(Schedule.class).getList()) {
            if ((s.getInterval() == null ? schedule.getInterval() == null : s.getInterval().equals(schedule.getInterval()))
                    && (s.getTimeUnit() == null ? schedule.getTimeUnit() == null : s.getTimeUnit().equals(schedule.getTimeUnit()))) {
                return s;
            }
        }

        return client.controller(Schedule.class).create(schedule);
    }

    @Override
    public FormulaQuantifier saveFormulaQuantifier(Boolean relative, Double value) {
        FormulaQuantifier formulaQuantifier = new FormulaQuantifier(relative, value);

        for (FormulaQuantifier o : client.controller(FormulaQuantifier.class).getList()) {
            if ((o.getRelative() == null ? formulaQuantifier.getRelative() == null : o.getRelative().equals(formulaQuantifier.getRelative()))
                    && (o.getValue() == null ? formulaQuantifier.getValue() == null : o.getValue().equals(formulaQuantifier.getValue()))) {
                return o;
            }
        }

        return client.controller(FormulaQuantifier.class).create(formulaQuantifier);
    }

    @Override
    public ConstantMonitor saveConstantMonitor(Double value) {
        ConstantMonitor constantMonitor = new ConstantMonitor(value);

        for (ConstantMonitor o : client.controller(ConstantMonitor.class).getList()) {
            if ((o.getValue() == null ? constantMonitor.getValue() == null : o.getValue().equals(constantMonitor.getValue()))) {
                return o;
            }
        }

        return client.controller(ConstantMonitor.class).create(constantMonitor);
    }

    @Override
    public ComponentHorizontalOutScalingAction saveComponentHorizontalOutScalingAction(Long amount,
                                                                                       Long min, Long max, Long count, Component component) {
        ComponentHorizontalOutScalingAction componentHorizontalOutScalingAction = new ComponentHorizontalOutScalingAction(amount, min, max, count, component.getId());

        for (ComponentHorizontalOutScalingAction o : client.controller(ComponentHorizontalOutScalingAction.class).getList()) {
            if ((o.getAmount() == null ? componentHorizontalOutScalingAction.getAmount() == null : o.getAmount().equals(componentHorizontalOutScalingAction.getAmount()))
                    && (o.getMin() == null ? componentHorizontalOutScalingAction.getMin() == null : o.getMin().equals(componentHorizontalOutScalingAction.getMin()))
                    && (o.getMax() == null ? componentHorizontalOutScalingAction.getMax() == null : o.getMax().equals(componentHorizontalOutScalingAction.getMax()))
                    && (o.getCount() == null ? componentHorizontalOutScalingAction.getCount() == null : o.getCount().equals(componentHorizontalOutScalingAction.getCount()))
                    && (o.getApplicationComponent() == null ? componentHorizontalOutScalingAction.getApplicationComponent() == null : o.getApplicationComponent().equals(componentHorizontalOutScalingAction.getApplicationComponent()))) {
                return o;
            }
        }

        return client.controller(ComponentHorizontalOutScalingAction.class).create(componentHorizontalOutScalingAction);
    }

    @Override
    public ComponentHorizontalInScalingAction saveComponentHorizontalInScalingAction(Long amount,
                                                                                     Long min, Long max, Long count, Component component) {
        ComponentHorizontalInScalingAction componentHorizontalInScalingAction = new ComponentHorizontalInScalingAction(amount, min, max, count, component.getId());

        for (ComponentHorizontalInScalingAction o : client.controller(ComponentHorizontalInScalingAction.class).getList()) {
            if ((o.getAmount() == null ? componentHorizontalInScalingAction.getAmount() == null : o.getAmount().equals(componentHorizontalInScalingAction.getAmount()))
                    && (o.getMin() == null ? componentHorizontalInScalingAction.getMin() == null : o.getMin().equals(componentHorizontalInScalingAction.getMin()))
                    && (o.getMax() == null ? componentHorizontalInScalingAction.getMax() == null : o.getMax().equals(componentHorizontalInScalingAction.getMax()))
                    && (o.getCount() == null ? componentHorizontalInScalingAction.getCount() == null : o.getCount().equals(componentHorizontalInScalingAction.getCount()))
                    && (o.getApplicationComponent() == null ? componentHorizontalInScalingAction.getApplicationComponent() == null : o.getApplicationComponent().equals(componentHorizontalInScalingAction.getApplicationComponent()))) {
                return o;
            }
        }

        return client.controller(ComponentHorizontalInScalingAction.class).create(componentHorizontalInScalingAction);
    }

    @Override
    public void addScalingActionToMonitor(ComposedMonitor m, ScalingAction scalingAction) {

        /** Observer */
        //TODO this has to be done dynamically on the SRLEngine
        // when addScalingActionToMonitor is called:
        // void addObserverToMonitor(Long id, Double value, FormulaOperator formulaOperator);

        ComponentHorizontalOutScalingAction sa = (ComponentHorizontalOutScalingAction) scalingAction;
        boolean isOnline = false;

        for (ComponentHorizontalOutScalingAction o : client.controller(ComponentHorizontalOutScalingAction.class).getList()) {
            if ((o.getAmount() == null ? sa.getAmount() == null : o.getAmount().equals(sa.getAmount()))
                    && (o.getMin() == null ? sa.getMin() == null : o.getMin().equals(sa.getMin()))
                    && (o.getMax() == null ? sa.getMax() == null : o.getMax().equals(sa.getMax()))
                    && (o.getCount() == null ? sa.getCount() == null : o.getCount().equals(sa.getCount()))
                    && (o.getApplicationComponent() == null ? sa.getApplicationComponent() == null : o.getApplicationComponent().equals(sa.getApplicationComponent()))) {
                sa = o;
                isOnline = true;
            }
        }

        if(!isOnline){
            sa = client.controller(ComponentHorizontalOutScalingAction.class).create(sa);
        }



        List<ComposedMonitor> cms = client.controller(ComposedMonitor.class).getList();
        for (ComposedMonitor cm : cms) {
            if (m.getId().equals(cm.getId())) {
                if (cm.getScalingActions() == null) {
                    cm.setScalingActions(new ArrayList<Long>());
                }
                cm.getScalingActions().add(sa.getId());

                client.controller(ComposedMonitor.class).update(cm);
            }
        }

        /** TODO implement for other ScalingActions */
    }

    @Override
    public TimeWindow getSmallestTimeWindow(List<Monitor> monitors) {
        TimeWindow result = null;

        for (TimeWindow tw : client.controller(TimeWindow.class).getList()) {
            try {
                if (result == null || Convert.hertz(tw) < Convert.hertz(result)) {
                    result = tw;
                }
            } catch (Exception ex) {
                // just try the next timewindow... TODO add logging
            }
        }

        return result;
    }

    @Override
    public Schedule getLowestSchedule(List<Monitor> monitors) {
        Schedule result = null;

        for (Schedule s : client.controller(Schedule.class).getList()) {
            try {
                if (result == null || Convert.hertz(s) > Convert.hertz(result)) {
                    result = s;
                }
            } catch (Exception ex) {
                // just try the next schedule... TODO add logging
            }
        }

        return result;
    }

    @Override
    public Component getComponentByName(String name) {
        /* TODO any other component types?! */
        return searchByName(client.controller(LifecycleComponent.class).getList(), name);
    }

    @Override
    public Application getApplicationByName(String name) {
        return searchByName(client.controller(Application.class).getList(), name);
    }

    public <T extends NamedEntity> T searchByName(List<T> entities, String name) {
        for (T entity : entities) {
            if (entity.getName().equals(name)) {
                return entity;
            }
        }

        return null;
    }

    public <T extends AbstractEntity> T searchById(List<T> entities, Long id) {
        for (T entity : entities) {
            if (entity.getId().equals(id)) {
                return entity;
            }
        }

        return null;
    }

    @Override
    public VirtualMachine getVirtualMachineToIP(String ip) {
        Long virtualMachineId = null;

        for (IpAddress ipAddress : client.controller(IpAddress.class).getList()) {
            if (ipAddress.getIp().equals(ip)) {
                virtualMachineId = ipAddress.getVirtualMachine();
            }
        }

        return searchById(client.controller(VirtualMachine.class).getList(), virtualMachineId);
    }

    @Override
    public ComposedMonitor getComposedMonitorByExternalId(String name) {

        /* TODO are there duplicate external ids? */
        Long virtualMachineId = null;
        List<ComposedMonitor> cms = client.controller(ComposedMonitor.class).getList();

        for (ComposedMonitor m : cms) {
            for (KeyValue s : m.getExternalReferences()) {
                if ("CDOID".equals(s.getKey()) || "CAMEL".equals(s.getKey())) {
                    if (s.getValue().equals(name)) {
                        return m;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public List<RawMonitor> getRawMonitors() {
        return client.controller(RawMonitor.class).getList();
    }

    @Override
    public List<Monitor> getMonitors() {
        List<Monitor> result = new ArrayList<>();

        result.addAll(getRawMonitors());
        result.addAll(getComposedMonitors());
        result.addAll(getConstantMonitors());

        //TODO add MonitorInstance?

        return result;
    }

    @Override
    public List<MonitorInstance> getMonitorInstances(Long monitorId) {
        List<MonitorInstance> result = new ArrayList<>();

        for (MonitorInstance mi : getMonitorInstances()) {
            if (mi.getMonitor().equals(monitorId)) {
                result.add(mi);
            }
        }

        return result;
    }

    @Override
    public List<MonitorInstance> getMonitorInstances() {
        return client.controller(MonitorInstance.class).getList();
    }

    @Override
    public List<ComposedMonitor> getComposedMonitors() {
        return client.controller(ComposedMonitor.class).getList();
    }

    @Override
    public List<ConstantMonitor> getConstantMonitors() {
        return client.controller(ConstantMonitor.class).getList();
    }

    @Override
    public SensorDescription getSensorDescription(Long id) {
        return searchById(client.controller(SensorDescription.class).getList(), id);
    }

    @Override
    public void addExternalId(Monitor monitor, String externalKey, String externalId) {
        _addExternalId(monitor, externalKey, externalId);
    }

    @Override
    public void addExternalId(MonitorInstance monitorInstance, String externalKey, String externalId) {
        _addExternalId(monitorInstance, externalKey, externalId);
    }

    @Override
    public void addExternalIdToMonitorInstance(Monitor monitor, String externalKey, String externalId,
                                               VirtualMachine virtualMachine) {
        List<MonitorInstance> instances = getMonitorInstances(monitor.getId());
        for (MonitorInstance mi : instances) {
            if (mi.getVirtualMachine().equals(virtualMachine.getId())) {
                _addExternalId(mi, externalKey, externalId);
            }
        }
    }

    @Override
    public void addExternalIdToEmptyMonitorInstance(Monitor monitor, String externalKey, String externalId) {
        List<MonitorInstance> instances = getMonitorInstances(monitor.getId());
        for (MonitorInstance mi : instances) {
            boolean hasCdoReference = false;
            for (KeyValue er : mi.getExternalReferences()) {
                if ("CDOID".equals(er.getKey())) {
                    hasCdoReference = true;
                }
            }
            if (!hasCdoReference) {
                _addExternalId(mi, externalKey, externalId);
                return;
            }
        }
    }

    private <T extends ExternalReferencedEntity> void _addExternalId(T t, String externalKey, String externalId) {
        ClientController<T> ctrlr = client.controller((Class<T>) t.getClass());
        for (T onlineObj : ctrlr.getList()) {
            if (onlineObj.equals(t)) {
                KeyValue kv = new KeyValue(externalKey, externalId);
                if (!t.getExternalReferences().contains(kv)) {
                    t.getExternalReferences().add(kv);
                }
                ctrlr.update(t);
            }
        }
    }

    @Override
    public void clearAllMonitoringAgents() {
        for (VirtualMachine vm : client.controller(VirtualMachine.class).getList()) {
            String ip = getPublicIpAddressToVirtualMachine(vm.getId());
            if (ip != null) {
                // TODO send DELETE to machine:
            }
        }
    }

    private String getPublicIpAddressToVirtualMachine(Long vmId) {
        for (IpAddress ip : client.controller(IpAddress.class).getList()) {
            if (ip.getVirtualMachine().equals(vmId) && ip.getIpType().equals("PUBLIC")) {
                return ip.getIp();
            }
        }

        return null;
    }

    @Override
    public MeasurementWindow saveMeasurementWindow(Long measurements) {
        MeasurementWindow measurementWindow = new MeasurementWindow(measurements);

        for (MeasurementWindow o : client.controller(MeasurementWindow.class).getList()) {
            if ((o.getMeasurements() == null ? measurementWindow.getMeasurements() == null : o.getMeasurements().equals(measurementWindow.getMeasurements()))) {
                return o;
            }
        }

        return client.controller(MeasurementWindow.class).create(measurementWindow);
    }

    @Override
    public MonitorSubscription addMonitorSubscription(Long monitor, String endpoint,
                                                      SubscriptionType type, FilterType filterType, double filterValue) {
        try {
            //@todo remove time dependencies
            Thread.sleep(6000);
        } catch (Exception ex) {
            System.out.println("error with monitorsubscription");
        }

        MonitorSubscription ms = new MonitorSubscription(monitor, endpoint, type, filterType, filterValue);

        for (MonitorSubscription o : client.controller(MonitorSubscription.class).getList()) {
            if ((o.getMonitor() == null ? ms.getMonitor() == null : o.getMonitor().equals(ms.getMonitor()))
                    && (o.getEndpoint() == null ? ms.getEndpoint() == null : o.getEndpoint().equals(ms.getEndpoint()))
                    && (o.getType() == null ? ms.getType() == null : o.getType().equals(ms.getType()))
                    && (o.getFilterType() == null ? ms.getFilterType() == null : o.getFilterType().equals(ms.getFilterType()))
                    && (o.getFilterValue() == null ? ms.getFilterValue() == null : o.getFilterValue().equals(ms.getFilterValue()))) {
                return o;
            }
        }

        return client.controller(MonitorSubscription.class).create(ms);
    }

    @Override
    public void removeMonitorSubscription(Long id) {
        client.controller(MonitorSubscription.class)
                .delete(client.controller(MonitorSubscription.class).get(id));
    }

    @Override
    public void removeAllMonitorSubscriptions() {
        for (MonitorSubscription ms : client.controller(MonitorSubscription.class).getList()) {
            client.controller(MonitorSubscription.class).delete(ms);
        }
    }

    @Override
    public void cleanMonitoring() {
        this.removeAllMonitorSubscriptions();
        // delete existing monitors:
        ArrayList<Long> tmpMonitors = new ArrayList<>();
        for (ComposedMonitor m : this.getComposedMonitors()) {
            tmpMonitors.add(m.getId());
        }
        for (int i = tmpMonitors.size() - 1; i >= 0; i--) {
            this.removeMonitor(tmpMonitors.get(i));
        }
        for (RawMonitor m : this.getRawMonitors()) {
            this.removeMonitor(m);
        }
    }

    @Override
    public String getPublicIpOfVmByName(String name) {
        Long vmId = null;
        for (VirtualMachine vm : client.controller(VirtualMachine.class).getList()) {
            if (vm.getName().equals(name)) {
                vmId = vm.getId();
            }
        }

        if (vmId != null) {
            for (IpAddress ipAddress : client.controller(IpAddress.class).getList()) {
                if (ipAddress.getVirtualMachine().equals(vmId) && ipAddress.getIpType()
                        .equals("PUBLIC")) {
                    return ipAddress.getIp();
                }
            }
        }

        return "";
    }

    @Override
    public SensorConfigurations saveSensorConfiguration(Map<String, String> sensorConfiguration) {
        List<KeyValue> list = new ArrayList<KeyValue>();

        sensorConfiguration.forEach((k, v) -> list.add(new KeyValue(k, v)));

        return client.controller(SensorConfigurations.class).create(new SensorConfigurations(list));
    }

    @Override
    public ComposedMonitor saveComposedMonitor(FlowOperator flowOperator, FormulaOperator function, Long idQuantifier, Long idWindow, List<Long> monitors, List<Long> scalingActions, Long schedule) {

        ComposedMonitor composedMonitor = new ComposedMonitor(flowOperator, function,
                idQuantifier, idWindow, monitors, scalingActions,
                schedule);

        for (ComposedMonitor cm : client.controller(ComposedMonitor.class).getList()) {
            if (cm.getFlowOperator().equals(composedMonitor.getFlowOperator())
                    && cm.getFunction().equals(composedMonitor.getFunction())
                    && cm.getQuantifier().equals(composedMonitor.getQuantifier())
                    && (cm.getWindow() == null ? composedMonitor.getWindow() == null : cm.getWindow().equals(composedMonitor.getWindow()))
                    && cm.getMonitors().equals(composedMonitor.getMonitors())
                    && (cm.getScalingActions() == null ? composedMonitor.getScalingActions() == null : cm.getScalingActions().equals(composedMonitor.getScalingActions()))
                    && (cm.getSchedule() == null ? composedMonitor.getSchedule() == null : cm.getSchedule().equals(composedMonitor.getSchedule()))) {
                return cm;
            }
        }

        return client.controller(ComposedMonitor.class).create(composedMonitor);
    }
}
