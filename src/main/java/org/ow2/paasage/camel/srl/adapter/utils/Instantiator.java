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
import eu.paasage.camel.deployment.Component;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.camel.metric.*;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import java.util.List;

/**
 * Created by Frank on 17.05.2015.
 */
public class Instantiator {
    public static MetricInstance createMetricInstance(CamelModel model,
        EList<EObject> resourceContents, MetricContext context, MetricObjectBinding mob,
        EList<MetricInstance> metricInstances) {

        MetricInstance result;

        // As proposed in a discussion with Kyriakos Kritikos and Christian Perez:
        // <executionContextId> + "_" + <metricContextId> + "_" + <componentInstanceId>
        String metricName = mob.getExecutionContext().getName() + "_" + context.getName();
        // componentInstanceId can only be used when the metric context is bound to a single component:
        if (mob instanceof MetricComponentBinding) {
            metricName += "_" + ((MetricComponentBinding) mob).getComponentInstance().getName();
        } else if (mob instanceof MetricVMBinding) {
            metricName += "_" + ((MetricVMBinding) mob).getVmInstance().getName();
        } else if (mob instanceof MetricApplicationBinding) {
            metricName += "_" + (mob.getExecutionContext().getApplication() == null ?
                "" :
                mob.getExecutionContext().getApplication()
                    .getName()); // TODO PROBLEM - NO INSTANCE!
        }

        if (context.getMetric() instanceof RawMetric) {
            RawMetricContext rawContext = (RawMetricContext) context;

            Sensor sensor = null;

            RawMetricInstance MI = MetricFactory.eINSTANCE.createRawMetricInstance();
            //MI.setId(context.getName() + "___" + instance.getId());
            //MI.setName(context.getName() + "___" + mob.getName());
            MI.setName(metricName);
            sensor = ((RawMetricContext) context).getSensor();
            if (sensor != null)
                MI.setSensor(sensor);
            MI.setMetric(context.getMetric());
            MI.setMetricContext(context);
            /** TODO CHECK FOR CORRECT OBJECT BINDING! */
            //MI.setObjectBinding(getOrCreateNewVirtualMachineBinding(models, resourceContents, instance.getIp()));
            MI.setObjectBinding(mob);
            MI.setSchedule(context.getSchedule());
            //MI.setValueType(valueType);

            //only add metric instance to CDO resource if not already there:
            RawMetricInstance duplicate = getDuplicate(model, MI);
            if (duplicate == null) {
                if (resourceContents != null)
                    resourceContents.add(MI);
                model.getMetricModels().get(0).getMetricInstances().add(MI);
            } else {
                MI = duplicate;
            }

            metricInstances.add(MI);
            result = MI;
        } else if (context.getMetric() instanceof CompositeMetric) {

            CompositeMetricInstance MI = MetricFactory.eINSTANCE.createCompositeMetricInstance();
            //MI.setName(context.getName() + "___" + mob.getName());
            MI.setName(metricName);
            MI.setMetric(context.getMetric());
            MI.setMetricContext(context);
            /** TODO CHECK FOR CORRECT OBJECT BINDING! */
            //MI.setObjectBinding(getOrCreateNewVirtualMachineBinding(models, resourceContents, instance.getIp()));
            MI.setObjectBinding(mob);
            MI.setSchedule(context.getSchedule());
            MI.setWindow(context.getWindow());
            //MI.setValueType(valueType);
            /** This is now done iteratively to avoid double instantiation */
            //            for(MetricContext composedMetricContext : ((CompositeMetricContext)context).getComposingMetricContexts()){
            //                MI.getComposingMetricInstances().add(createMetricInstance(model, resourceContents, composedMetricContext, instance, metricInstances));
            //            }

            //only add metric instance to CDO resource if not already there:
            CompositeMetricInstance duplicate = getDuplicate(model, MI);
            if (duplicate == null) {
                if (resourceContents != null)
                    resourceContents.add(MI);
                model.getMetricModels().get(0).getMetricInstances().add(MI);
            } else {
                MI = duplicate;
            }

            metricInstances.add(MI);
            result = MI;
        } else {
            throw new RuntimeException("MetricType not yet implemented!");
        }


        return result;
    }

    private static RawMetricInstance getDuplicate(CamelModel model, RawMetricInstance mi) {
        for (MetricModel mm : model.getMetricModels()) {
            for (MetricInstance tempMI : mm.getMetricInstances()) {
                if (tempMI.getName().equals(mi.getName()) && tempMI instanceof RawMetricInstance
                    && tempMI.getObjectBinding().equals(mi.getObjectBinding())) {
                    //TODO: identification satisfied by name?
                    return (RawMetricInstance) tempMI;
                }
            }
        }
        return null;
    }

    private static CompositeMetricInstance getDuplicate(CamelModel model,
        CompositeMetricInstance mi) {
        for (MetricModel mm : model.getMetricModels()) {
            for (MetricInstance tempMI : mm.getMetricInstances()) {
                if (tempMI.getName().equals(mi.getName())
                    && tempMI instanceof CompositeMetricInstance && tempMI.getObjectBinding()
                    .equals(mi.getObjectBinding())) {
                    //TODO: identification satisfied by name?
                    return (CompositeMetricInstance) tempMI;
                }
            }
        }
        return null;
    }

    public static void createMetricInstances(CamelModel model, ExecutionContext ec,
        EList<EObject> resourceContents) {

        CamelFinder camelFinder = new CamelFinder(model);

        EList<MetricContext> metricContexts = new BasicEList<MetricContext>();
        EList<MetricInstance> metricInstances = new BasicEList<MetricInstance>();

        /* TODO Dangerous since those could be other conditions */
        metricContexts = camelFinder.getMetricContexts();

        for (MetricContext context : metricContexts) {

            /** ObjectBinding:
             *
             * if context has application + component
             *  --> get all component instances that apply to application and component
             *  --> ComponentBinding
             * if context has component
             *  --> get all component instances to that component
             *  --> ComponentBinding
             * if context has application
             *  --> get all component instances to that application
             *  --> ApplicationBinding
             *
             *
             * if component is instantiiated as VMInstance
             *  --> VMBinding
             */
            //            for(InternalComponent component : model.getDeploymentModels().get(0).getInternalComponents()) {
            //
            //                if(context.getMetric().g)
            //                for(VMInstance vmInstance : CamelFinder.getVMInstances(model, null, context.getComponent())){
            //                    /** TODO might be dangerous if component is instantiated as InternalComponentInstance as well as VMInstance */
            //                    List<VMInstance> vmInstances = CamelFinder.getVMInstances(model, context.getApplication(), component);
            //
            //                    for(VMInstance vmInstance : vmInstances) {
            //                        MetricObjectBinding mob;
            //                        Application randomApp = CamelFinder.getRandomApplication(model); /* TODO insufficient*/
            //                        mob = SingleFactory.getOrCreateNewMetricVMBinding(model, resourceContents, vmInstance, randomApp);
            //
            //                        createMetricInstance(model, resourceContents, context, mob, metricInstances);
            //                    }
            //                }
            //            }

            EList<CompositeMetricContext> compositeMetricContexts =
                new BasicEList<CompositeMetricContext>();

            if (context instanceof RawMetricContext) {
                /**
                 * This is wrong! Actually the solver should create a new metric model along with a new
                 * deployment model, if they do not re-use the entities on type level!
                 */
                Component equivalentComponent = camelFinder
                    .getEquivalentComponent(context.getComponent(), ec.getDeploymentModel());

                List<VMInstance> vmInstances =
                    camelFinder.getVMInstances(context.getApplication(), equivalentComponent, ec);

                for (VMInstance vmInstance : vmInstances) {
                    MetricObjectBinding mob;
                    Application randomApp = (context.getApplication() == null ?
                        camelFinder.getRandomApplication() :
                        context.getApplication()); /* TODO insufficient*/
                    mob = SingleFactory
                        .getOrCreateNewMetricVMBinding(model, ec, resourceContents, vmInstance,
                            randomApp);

                    createMetricInstance(model, resourceContents, context, mob, metricInstances);
                }

                /** TODO might be dangerous if component is instantiated as InternalComponentInstance as well as VMInstance */
                //TODO currently only VM metric instances created:
                //                List<InternalComponentInstance> internalComponentInstances = camelFinder.getInternalComponentInstances(context.getApplication(), context.getComponent());
                //
                //                for (InternalComponentInstance internalComponentInstance : internalComponentInstances) {
                //                    MetricObjectBinding mob;
                //                    if (context.getComponent() == null && context.getApplication() != null) {
                //                        mob = SingleFactory.getOrCreateNewMetricApplicationBinding(model, ec, resourceContents, context.getApplication());
                //                    } else {
                //                        Application randomApp = camelFinder.getRandomApplication(); /* TODO unsufficient */
                //                        mob = SingleFactory.getOrCreateNewMetricComponentBinding(model, ec, resourceContents, internalComponentInstance, null /* TODO how to find out VM */, randomApp);
                //                    }
                //                    createMetricInstance(model, resourceContents, context, mob, metricInstances);
                //                }
            } else if (context instanceof CompositeMetricContext) {
                compositeMetricContexts.add((CompositeMetricContext) context);
            } else {
                throw new RuntimeException("MetricContext-Type not yet implemented!");
            }

            for (CompositeMetricContext compositeMetricContext : compositeMetricContexts) {
                // Get all metric instances of composed metric instances, and create one per input (MAP) or one in general (REDUCE)
                if (((CompositeMetric) compositeMetricContext.getMetric()).getFormula()
                    .getFunctionPattern() == FunctionPatternType.REDUCE) {
                    /* TODO separate instances for components? */
                    MetricObjectBinding mob;
                    mob = SingleFactory
                        .getOrCreateNewMetricApplicationBinding(model, ec, resourceContents,
                            context.getApplication());
                    createMetricInstance(model, resourceContents, context, mob, metricInstances);
                } else {
                    //throw new Exception("FunctionPatternType not yet implemented!");
                    for (MetricContext mc : compositeMetricContext.getComposingMetricContexts()) {
                        //TODO we have to check that all composed metric context have been instantiiated:
                        for (MetricInstance mi : getMetricInstancesToContext(model, mc)) {
                            if (mi.getMetricContext().equals(mc)) {
                                MetricObjectBinding mob;
                                if (mi.getObjectBinding() instanceof MetricVMBinding) {
                                    mob = SingleFactory
                                        .getOrCreateNewMetricVMBinding(model, ec, resourceContents,
                                            ((MetricVMBinding) mi.getObjectBinding())
                                                .getVmInstance(), context.getApplication());
                                } else if (mi
                                    .getObjectBinding() instanceof MetricApplicationBinding) {
                                    mob = SingleFactory
                                        .getOrCreateNewMetricApplicationBinding(model, ec,
                                            resourceContents, context.getApplication());
                                } else { //else if(mi.getObjectBinding() instanceof MetricComponentBinding){
                                    mob = SingleFactory
                                        .getOrCreateNewMetricComponentBinding(model, ec,
                                            resourceContents,
                                            ((MetricComponentBinding) mi.getObjectBinding())
                                                .getComponentInstance(),
                                            ((MetricComponentBinding) mi.getObjectBinding())
                                                .getVmInstance(), context.getApplication());
                                }
                                createMetricInstance(model, resourceContents, context, mob,
                                    metricInstances);
                            }
                        }
                    }
                }
            }
        }

        // After the creation of each individual context, context are added to composed contexts
        for (MetricInstance instance : model.getMetricModels().get(0).getMetricInstances()) {
            if ((instance.getMetricContext() instanceof CompositeMetricContext)) {
                CompositeMetricContext compContext =
                    (CompositeMetricContext) instance.getMetricContext();
                for (MetricContext metricContext : compContext.getComposingMetricContexts()) {
                    CompositeMetricInstance compInstance = (CompositeMetricInstance) instance;
                    for (MetricInstance mi : camelFinder
                        .getAllMetricInstancesToContext(metricContext)) {
                        compInstance.getComposingMetricInstances().add(mi);
                    }
                }
            }
        }
    }

    public static EList<MetricInstance> getMetricInstancesToContext(CamelModel model,
        MetricContext mc) {
        BasicEList<MetricInstance> nonconcurrentList = new BasicEList<>();
        for (MetricInstance mi : model.getMetricModels().get(0).getMetricInstances()) {
            if (mi.getMetricContext() != null && mi.getMetricContext().getName()
                .equals(mc.getName())) {
                nonconcurrentList.add(mi);
            }
        }
        return nonconcurrentList;
    }
}
