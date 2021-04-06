/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.graph;

import eu.melodic.upperware.adapter.graphlogger.ToLogGraphLogger;
import eu.melodic.upperware.adapter.plangenerator.graph.model.DividedElement;
import eu.melodic.upperware.adapter.plangenerator.graph.model.MelodicGraph;
import eu.melodic.upperware.adapter.plangenerator.model.*;
import eu.melodic.upperware.adapter.plangenerator.tasks.*;
import eu.melodic.upperware.adapter.properties.AdapterProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static eu.melodic.upperware.adapter.plangenerator.graph.model.Type.CONFIG;
import static eu.melodic.upperware.adapter.plangenerator.graph.model.Type.RECONFIG;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.CREATE;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.DELETE;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class DefaultGraphGenerator extends AbstractDefaultGraphGenerator<ComparableModel> {

    private ToLogGraphLogger toLogGraphLogger;
    private AdapterProperties adapterProperties;

    private DiffCalculator<AdapterRequirement, String> requirementDiffCalculator;
    private DiffCalculator<AdapterMonitor, String> monitorDiffCalculator;

    private static final BiPredicate<NodeTask, MonitorTask> NODE_TO_MONITOR_BI_PREDICATE = (nodeTask, monitorTask) ->
            nodeTask.getData().getNodeName().equals(monitorTask.getData().getNodeName()) &&
                    nodeTask.getData().getTaskName().equals(monitorTask.getData().getTaskName());

    @Override
    public SimpleDirectedGraph<Task, DefaultEdge> generateGraph(ComparableModel newComparableModel) {
        return generateGraphNew(newComparableModel, ComparableModel.builder().build(), () -> new MelodicGraph<>(DefaultEdge.class, CONFIG));
    }

    @Override
    public SimpleDirectedGraph<Task, DefaultEdge> generateGraph(ComparableModel newComparableModel, ComparableModel oldComparableModel) {
        return generateGraphNew(newComparableModel, oldComparableModel, () -> new MelodicGraph<>(DefaultEdge.class, RECONFIG));
    }

    private MelodicGraph<Task, DefaultEdge> generateGraphNew(ComparableModel newComparableModel, ComparableModel oldComparableModel, Supplier<MelodicGraph<Task, DefaultEdge>> graphSupplier) {
        MelodicGraph<Task, DefaultEdge> graph = graphSupplier.get();

        Map<String, DividedElement<AdapterRequirement>> adapterRequirementDiff = requirementDiffCalculator.calculateDiff(
                newComparableModel.getAdapterRequirements(),
                oldComparableModel.getAdapterRequirements(),
                AdapterRequirement.NODE_BI_PREDICATE,
                AdapterRequirement::getTaskName);
        requirementDiffCalculator.print("AdapterRequirement", adapterRequirementDiff);

        Map<String, DividedElement<AdapterMonitor>> adapterMonitorDiff = Collections.EMPTY_MAP;
        if (adapterProperties.getEms().isEnabled()) {
            adapterMonitorDiff = monitorDiffCalculator.calculateDiff(
                    newComparableModel.getAdapterMonitors(),
                    oldComparableModel.getAdapterMonitors(),
                    AdapterMonitor.MONITOR_BI_PREDICATE,
                    AdapterMonitor::getTaskName);
            monitorDiffCalculator.print("AdapterMonitor", adapterMonitorDiff);
        }

        //Job
        AdapterJob newAdapterJob = newComparableModel.getAdapterJob();
        Optional<AdapterJob> oldAdapterJobOpt = Optional.ofNullable(oldComparableModel.getAdapterJob());
        log.info("generateGraphNew-> \nnewAdapterJob: {}\noldAdapterJobOpt: {}", newAdapterJob, oldAdapterJobOpt);

        JobTask jobTask = null;
        if (!newAdapterJob.equals(oldAdapterJobOpt.orElse(null))) {
            // TODO: adding previously executed AdapterJob will help in the future in determining what needs to be changed in the job
            //  definition, i.e. in the deployment infrastructure like adding new components
            newAdapterJob.setPreviousJob(oldAdapterJobOpt);
            jobTask = createTask(graph, newAdapterJob, JobTask.JOB_TASK_CREATE);
        }
        //Optional<JobTask> jobTaskOpt = Optional.ofNullable(jobTask);
        log.info("generateGraphNew-> jobTask: {}", jobTask);

        if(!oldAdapterJobOpt.isPresent()) { // first deployment job
            log.info("generateGraphNew-> first deployment job");
            //Nodes
            List<NodeTask> nodeTasksToCreate = createTasks(graph, requirementDiffCalculator.getToCreate(adapterRequirementDiff), NodeTask.NODE_TASK_CREATE);
            addEdge(graph, jobTask, nodeTasksToCreate, () -> true);

            //Monitors
            List<MonitorTask> monitorTasksToCreate = new ArrayList<>();
            if (adapterProperties.getEms().isEnabled()) {
                log.info("EMS enabled = {}, newComparableModel.getAdapterMonitors(): {}, oldComparableModel.getAdapterMonitors(): {}",
                        adapterProperties.getEms().isEnabled(),
                        newComparableModel.getAdapterMonitors().size(),
                        oldComparableModel.getAdapterMonitors().size());

                List<AdapterMonitor> monitorsToCreate = monitorDiffCalculator.getToCreate(adapterMonitorDiff);
                monitorTasksToCreate = createTasks(graph, monitorsToCreate, MonitorTask.MONITOR_TASK_CREATE);
                addEdge(graph, nodeTasksToCreate, monitorTasksToCreate, NODE_TO_MONITOR_BI_PREDICATE);
            }
        } else { // next deployment job / scaling
            log.info("generateGraphNew-> next deployment job / scaling");
            // TODO: we know at this point that we have old and new AdapterJob so in the future we can compare them and add logic to add new components to existing job
            for (String taskName : adapterRequirementDiff.keySet()) {
                DividedElement<AdapterRequirement> adapterRequirementDividedElement = adapterRequirementDiff.get(taskName);
                log.info("Scaling -> working with {} -> toCreate: {}, toRemain: {}, toDelete: {}", taskName,
                        adapterRequirementDividedElement.getToCreate().size(),
                        adapterRequirementDividedElement.getToRemain().size(),
                        adapterRequirementDividedElement.getToDelete().size());

                // right now to my knowledge there can only be reconfiguration that comprises of either Scale Out or Scale In process
                ScaleTask scaleTask = null;

                List<AdapterRequirement> toCreate = adapterRequirementDividedElement.getToCreate();
                if (CollectionUtils.isNotEmpty(toCreate)) { // Scale Out
                    log.info("Scaling out task: {} with {} more nodes", taskName, toCreate.size());
                    scaleTask = createTask(graph, createScaleTaskNew(toCreate), ScaleTask.SCALE_TASK_CREATE);
                } else {
                    List<AdapterRequirement> toDelete = adapterRequirementDividedElement.getToDelete();
                    if (CollectionUtils.isNotEmpty(toDelete)) { // Scale In
                        log.info("Scaling in task: {} with {} less nodes", taskName, toDelete.size());
                        scaleTask = createTask(graph, createScaleTaskNew(toDelete), ScaleTask.SCALE_TASK_DELETE);
                    } else {
                        List<AdapterRequirement> toRemain = adapterRequirementDividedElement.getToRemain();
                        if (CollectionUtils.isNotEmpty(toRemain)) { // No Scale
                            log.info("No scaling for task: {} - remaining {} nodes", taskName, toRemain.size());
                        }
                    }
                }
                /*Optional<ScaleTask> scaleTaskOpt = Optional.ofNullable(scaleTask);
                if(scaleTaskOpt.isPresent()) {
                    addEdge(graph, jobTask, scaleTaskOpt.get(), jobTaskOpt::isPresent);
                }*/
            }
        }

        toLogGraphLogger.logGraph(graph);
        log.info("Built {} graph (new): {}", graph.getType(), graph);
        return graph;
    }

    private <T extends Task> List<T> getTasksWithoutOutgoingEdges(MelodicGraph<Task, DefaultEdge> graph, Type type, Class<T> clazz) {
        return getTasksWithoutOutgoingEdges(graph, type)
                .stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(toList());
    }

    private List<Task> getTasksWithoutOutgoingEdges(MelodicGraph<Task, DefaultEdge> graph, Type type) {
        return graph.vertexSet()
                .stream()
                .filter(task -> graph.outgoingEdgesOf(task).isEmpty())
                .filter(task -> type.equals(task.getType()))
                .collect(toList());
    }

    private <T extends Data, U extends Task> U createTask(MelodicGraph<Task, DefaultEdge> graph, T data, Function<T, U> function) {
        U task = function.apply(data);
        addVertex(graph, task);
        return task;
    }

    private <T extends Data, U extends Task> List<U> createTasks(MelodicGraph<Task, DefaultEdge> graph, List<T> data, Function<T, U> function) {

        return CollectionUtils.emptyIfNull(data)
                .stream()
                .map(t -> createTask(graph, t, function))
                .collect(toList());
    }

    private AdapterScale createScaleTaskNew(List<AdapterRequirement> requirements) {
        final AdapterScale.AdapterScaleBuilder builder = AdapterScale.builder();

        if (CollectionUtils.isNotEmpty(requirements)) {
            final AdapterRequirement adapterRequirement = requirements.get(0);

            builder.taskName(adapterRequirement.getTaskName())
                    .nodeNames(requirements.stream().map(AdapterRequirement::getNodeName).collect(toList()));
        }

        return builder.build();
    }

}