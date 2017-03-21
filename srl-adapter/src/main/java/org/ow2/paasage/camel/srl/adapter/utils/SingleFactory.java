/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.utils;

import eu.paasage.camel.Application;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.ComponentInstance;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.camel.metric.*;
import eu.paasage.camel.type.StringValueType;
import eu.paasage.camel.type.TypeFactory;
import eu.paasage.camel.type.ValueType;
import eu.paasage.camel.unit.TimeIntervalUnit;
import eu.paasage.camel.unit.UnitDimensionType;
import eu.paasage.camel.unit.UnitFactory;
import eu.paasage.camel.unit.UnitType;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * Created by Frank on 17.05.2015.
 */
public class SingleFactory {

    public static MetricComponentBinding getOrCreateNewMetricComponentBinding(CamelModel model,
        ExecutionContext ec, EList<EObject> resourceContents, ComponentInstance instance,
        VMInstance vm, Application app) {
        /* TODO Dangerous casting */
        if (resourceContents != null) {
            for (MetricObjectBinding metricObjectBinding : model.getMetricModels().get(0)
                .getBindings()) {
                if (metricObjectBinding instanceof MetricComponentBinding) {
                    MetricComponentBinding metricComponentBinding =
                        (MetricComponentBinding) metricObjectBinding;

                    if (metricComponentBinding.getComponentInstance() == instance
                        && metricComponentBinding.getVmInstance() == vm) {
                        return metricComponentBinding;
                    }
                }
            }
        }

        MetricComponentBinding mcb = MetricFactory.eINSTANCE.createMetricComponentBinding();
        mcb.setName(
            "Bind_with_" + (instance != null ? instance.getName() : "NO_CMP") + '_' + (vm != null ?
                vm.getName() :
                "NO_VM"));
        mcb.setVmInstance(vm);
        mcb.setComponentInstance(instance);
        mcb.setExecutionContext(ec);
        if (resourceContents != null)
            resourceContents.add(mcb);
        model.getMetricModels().get(0).getBindings().add(mcb);


        return mcb;
    }

    public static MetricVMBinding getOrCreateNewMetricVMBinding(CamelModel model,
        ExecutionContext ec, EList<EObject> resourceContents, VMInstance vm, Application app) {
        /* TODO Dangerous casting */
        if (resourceContents != null) {
            for (MetricObjectBinding metricBinding : model.getMetricModels().get(0).getBindings()) {
                if (metricBinding instanceof MetricVMBinding) {
                    MetricVMBinding metricVMBinding = (MetricVMBinding) metricBinding;

                    if (metricVMBinding.getVmInstance() == vm) {
                        return metricVMBinding;
                    }
                }
            }
        }

        MetricVMBinding mvb = MetricFactory.eINSTANCE.createMetricVMBinding();
        mvb.setVmInstance(vm);
        mvb.setExecutionContext(ec);
        mvb.setName("Bind_with_vm_" + vm.getName());
        if (resourceContents != null)
            resourceContents.add(mvb);
        model.getMetricModels().get(0).getBindings().add(mvb);


        return mvb;
    }

    public static MetricApplicationBinding getOrCreateNewMetricApplicationBinding(CamelModel model,
        ExecutionContext ec, EList<EObject> resourceContents, Application app) {
        /* TODO Dangerous casting */
        if (resourceContents != null) {
            for (MetricObjectBinding metricBinding : model.getMetricModels().get(0).getBindings()) {
                if (metricBinding instanceof MetricApplicationBinding) {
                    MetricApplicationBinding metricAppBinding =
                        (MetricApplicationBinding) metricBinding;

                    if (metricAppBinding.getExecutionContext().getApplication() == app) {
                        return metricAppBinding;
                    }
                }
            }
        }

        MetricApplicationBinding mab = MetricFactory.eINSTANCE.createMetricApplicationBinding();
        mab.setExecutionContext(ec);
        mab.setName("Bind_with_app_" + app.getName());
        if (resourceContents != null)
            resourceContents.add(mab);
        model.getMetricModels().get(0).getBindings().add(mab);


        return mab;
    }

    public static Schedule getOrCreateFixedSchedule(CamelModel model,
        EList<EObject> resourceContents, int interval, TimeIntervalUnit timeIntervalUnit,
        String name, ScheduleType scheduleType) {
        /* TODO Dangerous casting */
        if (resourceContents != null) {
            for (Schedule schedule : model.getMetricModels().get(0).getSchedules()) {
                if ((schedule.getInterval() == interval) &&
                    schedule.getUnit().equals(timeIntervalUnit) &&
                    schedule.getName().equals(name) &&
                    schedule.getType().equals(scheduleType)) {
                    return schedule;
                }
            }
        }

        Schedule schedule = MetricFactory.eINSTANCE.createSchedule();
        schedule.setInterval(interval);
        schedule.setUnit(timeIntervalUnit);
        schedule.setName(name);
        schedule.setType(scheduleType);
        if (resourceContents != null)
            resourceContents.add(schedule);
        model.getMetricModels().get(0).getSchedules().add(schedule);

        return schedule;
    }

    public static Window getOrCreateWindow(CamelModel model, EList<EObject> resourceContents,
        int measurementSize, int timeSize, WindowType windowType, TimeIntervalUnit timeIntervalUnit,
        String name, WindowSizeType windowSizeType) {
        /* TODO Dangerous casting */
        if (resourceContents != null) {
            for (Window win : model.getMetricModels().get(0).getWindows()) {
                if ((win.getMeasurementSize() == measurementSize) &&
                    win.getTimeSize() == (timeSize) &&
                    win.getUnit().equals(timeIntervalUnit) &&
                    win.getName().equals(name) &&
                    win.getWindowType().equals(windowType) &&
                    win.getSizeType().equals(windowSizeType)) {
                    return win;
                }
            }
        }


        Window window = MetricFactory.eINSTANCE.createWindow();
        window.setMeasurementSize(measurementSize);
        window.setName(name);
        window.setSizeType(windowSizeType);
        window.setTimeSize(timeSize);
        window.setUnit(timeIntervalUnit);
        window.setWindowType(windowType);
        if (resourceContents != null)
            resourceContents.add(window);
        model.getMetricModels().get(0).getWindows().add(window);

        return window;
    }

    public static ValueType getOrCreateStringType(EList<EObject> resourceContents) {
        /* TODO Dangerous casting */
        if (resourceContents != null) {
            for (EObject obj : resourceContents) {
                if (obj instanceof StringValueType) {
                    return (ValueType) obj;
                }
            }
        }

        ValueType valueType = TypeFactory.eINSTANCE.createStringValueType();
        if (resourceContents != null)
            resourceContents.add(valueType);

        return valueType;
    }

    public static TimeIntervalUnit getOrCreateTimeIntervalUnit(EList<EObject> resourceContents,
        UnitType unitType, UnitDimensionType unitDimensionType, String name) {
        /* TODO Dangerous casting */
        if (resourceContents != null) {
            for (EObject obj : resourceContents) {
                if (obj instanceof TimeIntervalUnit) {
                    TimeIntervalUnit unit = (TimeIntervalUnit) obj;
                    if (unit.getUnit().equals(unitType) &&
                        // TODO dimensiontype not used anymore? unit.getDimensionType().equals(unitDimensionType) &&
                        unit.getName().equals(name)) {
                        return unit;
                    }
                }
            }
        }

        TimeIntervalUnit unit = UnitFactory.eINSTANCE.createTimeIntervalUnit();
        unit.setUnit(unitType);
        //TODO dimensiontype not used anymore? unit.setDimensionType(unitDimensionType);
        unit.setName(name);
        if (resourceContents != null)
            resourceContents.add(unit);

        return unit;
    }
}

