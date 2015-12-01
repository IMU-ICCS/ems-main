/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.utils;

import de.uniulm.omi.cloudiator.colosseum.client.entities.ComposedMonitor;
import eu.paasage.camel.Action;
import eu.paasage.camel.Application;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.*;
import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.camel.execution.ExecutionModel;
import eu.paasage.camel.metric.*;
import eu.paasage.camel.requirement.HorizontalScaleRequirement;
import eu.paasage.camel.requirement.Requirement;
import eu.paasage.camel.requirement.ScaleRequirement;
import eu.paasage.camel.scalability.*;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Frank on 17.05.2015.
 *
 * Wrapper for Queries (HQL, OCL) or search-algorithms.
 */
public class CamelFinder {
    private final CamelModel model;

    public CamelFinder(CamelModel model) {
        this.model = model;
    }

    public EObject getUser(){
        return null; /*TODO*/
    }

    public EList<InternalComponentInstance> getInternalComponentInstances(Application application, Component component, ExecutionContext ec){
        EList<InternalComponentInstance> result = new BasicEList<InternalComponentInstance>();

        for(InternalComponentInstance instance : ec.getDeploymentModel().getInternalComponentInstances()){
        //for(InternalComponentInstance instance : model.getDeploymentModels().get(model.getDeploymentModels().size()-1).getInternalComponentInstances()){
            if ((component == null || instance.getType() == component)){
                /** TODO check for application:   && (application == null || instance.getType(). == application)){ */
                result.add(instance);
            }
        }

        return result;
    }

    public EList<VMInstance> getVMInstances(Application application, Component component, ExecutionContext ec){
        EList<VMInstance> result = new BasicEList<VMInstance>();

        for(VMInstance instance : ec.getDeploymentModel().getVmInstances()){
        //for(VMInstance instance : model.getDeploymentModels().get(model.getDeploymentModels().size()-1).getVmInstances()){
            if ((component == null || instance.getType() == component
                || ((component instanceof InternalComponent) &&
                    isInternalComponentInstalledOnVM(instance, (InternalComponent)component, ec.getDeploymentModel())))){
                /** TODO check for application:   && (application == null || instance.getType(). == application)){ */
                result.add(instance);
            }
        }

        return result;
    }

    private boolean isInternalComponentInstalledOnVM(VMInstance instance, InternalComponent component, DeploymentModel deploymentModel) {
        HostingInstance hiOfVM = getHostingInstanceToVM(instance, deploymentModel);
        List<RequiredHostInstance> rhis = getRequiredHostInstancesToInternalComponent(component, deploymentModel);
        for(RequiredHostInstance rhiComp : rhis){
            if(hiOfVM.getRequiredHostInstance().equals(rhiComp)){
                return true;
            }
        }
        return false;
    }

    private List<RequiredHostInstance> getRequiredHostInstancesToInternalComponent(InternalComponent component, DeploymentModel deploymentModel) {
        List<RequiredHostInstance> result = new ArrayList<>();

        List<InternalComponentInstance> cis = getInstancesToInternalComponent(component, deploymentModel);

        for(InternalComponentInstance ci : cis){
            result.add(ci.getRequiredHostInstance());
        }

        return result;
    }

    private List<InternalComponentInstance> getInstancesToInternalComponent(InternalComponent component, DeploymentModel deploymentModel) {
        List<InternalComponentInstance> result = new ArrayList<>();

        for(InternalComponentInstance ici : deploymentModel.getInternalComponentInstances()){
            if(ici.getType().equals(component)){
                result.add(ici);
            }
        }

        return result;
    }

    private HostingInstance getHostingInstanceToVM(VMInstance instance, DeploymentModel deploymentModel) {
        for(HostingInstance hi : deploymentModel.getHostingInstances()){
            for(ProvidedHostInstance phi : instance.getProvidedHostInstances()){
                if(hi.getProvidedHostInstance().equals(phi)){
                    return hi;
                }
            }
        }

        return null;
    }

    public EList<MetricInstance> getAllMetricInstancesToContext(MetricContext metricContext){
        EList<MetricInstance> result = new BasicEList<MetricInstance>();

        for(MetricInstance mi : model.getMetricModels().get(0).getMetricInstances()){
            if (mi.getMetricContext() == metricContext){
                result.add(mi);
            }
        }

        return result;
    }

    public Application getRandomApplication() {
        Application result = null;

        for(Application app : model.getApplications()){
            result = app;
        }

        return result;
    }

    public ExecutionContext getRandomExecutionContext() {
        ExecutionContext result = null;

        for(ExecutionContext ec : model.getExecutionModels().get(0).getExecutionContexts()){
            result = ec;
        }

        return result;
    }

    public NonFunctionalEvent getNfeToCondition(Condition condition) {
        for(Event ev : model.getScalabilityModels().get(0).getEvents()){
            if(ev instanceof NonFunctionalEvent && ((NonFunctionalEvent)ev).getMetricCondition() == condition){
                return (NonFunctionalEvent)ev; // insteaad of only the Name .getName();
            }
        }

        return null;
    }

    public List<CompositeMetricContext> getCompositeMetricContexts(ExecutionContext ec){
        throw new RuntimeException("not implemented");
    }

    public List<CompositeMetricContext> getCompositeMetricContexts(){
        List<CompositeMetricContext> result = model.getMetricModels().get((0)).getContexts().stream().filter(cc -> cc instanceof CompositeMetricContext).map(cc -> (CompositeMetricContext) cc).collect(Collectors.toList());
        return result;
    }

    public List<MetricInstance> getMetricInstances(MetricContext mc, ExecutionContext ec){
        List<MetricInstance> result = new ArrayList<>();

        for(MetricModel mm : model.getMetricModels()){
            for(MetricInstance mi : mm.getMetricInstances()){
                if(mi.getObjectBinding().getExecutionContext().getName().equals(ec.getName())
                        && mi.getMetricContext().getName().equals(mc.getName())){
                    result.add(mi);
                }
            }
        }

        return result;
    }

    public ExecutionContext getExecutionContext(String executionContextName) {
        for(ExecutionModel em : model.getExecutionModels()){
            for(ExecutionContext ec : em.getExecutionContexts()){
                if(ec.getName().equals(executionContextName)){
                    return ec;
                }
            }
        }

        //TODO only so that it works....
        return getRandomExecutionContext();
    }

    public List<Schedule> getSchedules(){
        List<Schedule> result = new ArrayList<>();

        for(MetricModel mm : model.getMetricModels()){
            for(Schedule s : mm.getSchedules()){
                result.add(s);
            }
        }

        return result;
    }

    public List<Sensor> getSensors(){
        List<Sensor> result = new ArrayList<>();

        for(MetricModel mm : model.getMetricModels()){
            for(Sensor s : mm.getSensors()){
                result.add(s);
            }
        }

        return result;
    }

    public List<MetricCondition> getMetricConditions() {
        List<MetricCondition> result = new ArrayList<>();

        for (Condition condition : model.getMetricModels().get(0).getConditions()) {
            if (condition instanceof MetricCondition) {
                result.add((MetricCondition)condition);
            }
        }

        return result;
    }

    public List<EventPattern> getEventPatterns() {
        return model.getScalabilityModels().get(0).getPatterns();
    }

    public List<Window> getWindows() {
        return model.getMetricModels().get(0).getWindows();
    }

    public List<RawMetricContext> getRawMetricContexts() {
        List<RawMetricContext> result = model.getMetricModels().get((0)).getContexts().stream().filter(cc -> cc instanceof RawMetricContext).map(cc -> (RawMetricContext) cc).collect(Collectors.toList());
        return result;
    }

    public EList<MetricContext> getMetricContexts() {
        EList<MetricContext> result = new BasicEList<>();

        for(ConditionContext context : model.getMetricModels().get(0).getContexts()){
            if(context instanceof MetricContext) {
                result.add((MetricContext) context);
            }
        }

        return result;
    }

    public List<ScalabilityRule> getAssociatedRules(Action scalingAction){
        List<ScalabilityRule> result = new ArrayList<>();

        for (ScalabilityRule rule : model.getScalabilityModels().get(0)
                .getRules()) {
            for (Action ruleAction : rule.getActions()) {
                if (ruleAction == scalingAction) {
                    result.add(rule);
                }
            }
        }

        return result;
    }

    public List<HorizontalScaleRequirement> getAssociatedHorizontalScaleRequirements(InternalComponent component){
        List<HorizontalScaleRequirement> result = new ArrayList<>();

        //TODO: check this: EList<Requirement> requirements = model.getRequirementModels().get(0).getRequirements();
        for (ScaleRequirement requirement : model.getScalabilityModels().get(0)
                .getScaleRequirements()) {
            if (requirement instanceof HorizontalScaleRequirement) {
                HorizontalScaleRequirement horizontalScaleRequirement =
                        (HorizontalScaleRequirement) requirement;
                 /* TODO What if several requirements comply to the same component and are inconsistent? */
                if (horizontalScaleRequirement.getComponent().equals(component)) {
                    result.add(horizontalScaleRequirement);
                }
            }
        }

        for (Requirement requirement : model.getRequirementModels().get(0)
                .getRequirements()) {
            if (requirement instanceof HorizontalScaleRequirement) {
                HorizontalScaleRequirement horizontalScaleRequirement =
                        (HorizontalScaleRequirement) requirement;
                 /* TODO What if several requirements comply to the same component and are inconsistent? */
                if (horizontalScaleRequirement.getComponent().equals(component)) {
                    result.add(horizontalScaleRequirement);
                }
            }
        }

        return result;
    }

    public List<HorizontalScalingAction> getScalingActions() {
        List<HorizontalScalingAction> result = new ArrayList<>();

        for (eu.paasage.camel.scalability.ScalingAction scalingAction : model
                .getScalabilityModels().get(0).getActions()) {
            if (scalingAction instanceof HorizontalScalingAction) {
                result.add((HorizontalScalingAction)scalingAction);
            }
        }

        return result;
    }

    public List<VMInstance> getVMInstances() {
        return model.getDeploymentModels().get(model.getDeploymentModels().size()-1).getVmInstances();
    }

    public List<VMInstance> getVMInstances(ExecutionContext ec) {
        return ec.getDeploymentModel().getVmInstances();
    }

    public Component getEquivalentComponent(Component component, DeploymentModel deploymentModel) {
        for(InternalComponent ic : deploymentModel.getInternalComponents()){
            if(ic.getName().equals(component.getName())){
                return ic;
            }
        }

        for(VM vm : deploymentModel.getVms()){
            if(vm.getName().equals(component.getName())){
                return vm;
            }
        }

        return null;
    }
}
