/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.executioncontext.colosseum;

import de.uniulm.omi.cloudiator.colosseum.client.entities.*;
import eu.melodic.upperware.adapter.executioncontext.ContextOperations;
import eu.melodic.upperware.adapter.executioncontext.ContextUtils;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.api.JobApi;
import io.github.cloudiator.rest.api.MonitoringApi;
import io.github.cloudiator.rest.api.NodeApi;
import io.github.cloudiator.rest.api.ProcessApi;
import io.github.cloudiator.rest.model.Schedule;
import io.github.cloudiator.rest.model.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class ColosseumContext extends ContextUtils implements ContextOperations {

  private final JobApi jobApi;
  private final NodeApi nodeApi;
  private final ProcessApi processApi;
  private final MonitoringApi monitoringApi;

  private final List<NodeGroup> nodeGroups = synchronizedList();
  private final List<Schedule> schedules = synchronizedList();
  private final List<CloudiatorProcess> processes = synchronizedList();
  private final List<Job> jobs = synchronizedList();
  private final List<Monitor> monitors = synchronizedList();

  private boolean loaded;

  public void addNodeGroup(@NonNull NodeGroup nodeGroup) {
    nodeGroups.add(nodeGroup);
  }

  public Optional<NodeGroup> getNodeGroup(String name) {
    return getElement(nodeGroups, nodeGroup -> name.equals(nodeGroup.getId()),
            () -> new IllegalStateException(format("Ambiguous search result - there are more than one node with the same name=%s", name)));
  }

  public Optional<NodeGroup> getNodeGroupByNodeName(String name) {
    return getElement(nodeGroups, nodeGroup -> nodeGroup.getNodes().stream().map(Node::getName).anyMatch(nodeName -> nodeName.endsWith(name)),
            () -> new IllegalStateException(format("Ambiguous search result - there are more than one node with the same name=%s", name)));
  }

  public void addSchedule(@NonNull Schedule schedule) {
    schedules.add(schedule);
  }

  public Optional<Schedule> getSchedule(String name) {
    return getElement(schedules, schedule -> name.equals(schedule.getId()),
            () -> new IllegalStateException(format("Ambiguous search result - there are more than one schedules with the same name=%s", name)));
  }

  public void addProcess(@NonNull CloudiatorProcess process) {
    processes.add(process);
  }

  public Optional<CloudiatorProcess> getProcess(String name) {
    return getElement(processes, process -> name.equals(process.getId()),
            () -> new IllegalStateException(format("Ambiguous search result - there are more than one process with the same name=%s", name)));
  }

  public void addJob(@NonNull Job job) {
    jobs.add(job);
  }

  public Optional<Job> getJob(String name) {
    return getElement(jobs, job -> name.equals(job.getId()),
            () -> new IllegalStateException(format("Ambiguous search result - there are more than one job with the same name=%s", name)));
  }

  public Optional<Monitor> getMonitors(String metricName){
    return getElement(monitors, monitor -> metricName.equals(monitor.getMetric()),
            () -> new IllegalStateException(format("Ambiguous search result - there are more than one job with the same name=%s", metricName)));
  }

  public void addMonitor(@NonNull Monitor monitor) {
    monitors.add(monitor);
  }

  private <T> Optional<T> getElement(List<T> collection, Predicate<T> predicate, Supplier<IllegalStateException> exceptionSupplier) {
    synchronized (collection) {
      return collection.stream()
              .filter(predicate)
              .collect(toSingleton(exceptionSupplier));
    }
  }

  private <T> Collector<T, ?, Optional<T>> toSingleton(Supplier<IllegalStateException> exceptionSupplier) {
    return Collectors.collectingAndThen(
            Collectors.toList(),
            list -> {
              if (list.size() > 1) {
                throw exceptionSupplier.get();
              }
              if (list.size() == 0) {
                return Optional.empty();
              }
              return Optional.ofNullable(list.get(0));
            }
    );
  }

  @Override
  @Synchronized
  public void refreshContext() throws ApiException {
    log.info("Refreshing Colosseum context");

    nodeGroups.clear();
    nodeGroups.addAll(nodeApi.findNodeGroups());

    schedules.clear();
    schedules.addAll(processApi.getSchedules());

    processes.clear();
    if (CollectionUtils.isNotEmpty(schedules)){
        processes.addAll(processApi.getProcesses(schedules.get(0).getId()));
  }

    jobs.clear();
    jobs.addAll(jobApi.findJobs());

    monitors.clear();
    monitors.addAll(monitoringApi.findMonitors());

    loaded = true;
  }

  @Override
  public boolean isLoaded() {
    return loaded;
  }

    private void printInstances(String text, List<Instance> instances) {
        JSONArray array = new JSONArray();
        String result = "";
        try {
            for (Instance instance : instances) {
                array.put(createJsonRepresentation(instance));
            }
            result = array.toString(3);
        } catch (Exception e) {
            log.error("Problem with json", e);
        }
        log.info("{} Instances: {}\n{}", text, array.length(), result);
    }

    private JSONObject createJsonRepresentation(Instance instance) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("id", instance.getId());
        result.put("applicationComponent", instance.getApplicationComponent());
        result.put("applicationInstance", instance.getApplicationInstance());
        result.put("virtualMachine", instance.getVirtualMachine());
        return result;
    }
}
