/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.executioncontext.colosseum;

import com.google.common.collect.Lists;
import eu.melodic.upperware.adapter.exception.AmbiguousResultException;
import eu.melodic.upperware.adapter.executioncontext.ContextOperations;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.api.JobApi;
import io.github.cloudiator.rest.api.NodeApi;
import io.github.cloudiator.rest.api.ProcessApi;
import io.github.cloudiator.rest.model.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class ColosseumContext implements ContextOperations {

  private final JobApi jobApi;
  private final NodeApi nodeApi;
  private final ProcessApi processApi;

  private final List<NodeGroup> nodeGroups = synchronizedList();
  private final List<Schedule> schedules = synchronizedList();
  private final List<ProcessGroup> processGroups = synchronizedList();
  private final List<Job> jobs = synchronizedList();

  private boolean loaded;

  public void addNodeGroup(@NonNull NodeGroup nodeGroup) {
    nodeGroups.add(nodeGroup);
  }

  public Optional<NodeGroup> getNodeGroup(String name) {
    return getElement(nodeGroups, nodeGroup -> name.equals(nodeGroup.getId()), createAmbiguousResultException(NodeGroup.class, name));
  }

  public Optional<NodeGroup> getNodeGroupByNodeName(String name) {
    return getElement(nodeGroups, nodeGroup -> nodeGroup.getNodes().stream().map(Node::getName).anyMatch(nodeName -> nodeName.endsWith(name)),
            createAmbiguousResultException(NodeGroup.class, name));
  }

    public void deleteNodeGroup(String nodeGroupId) {
      nodeGroups.removeIf(nodeGroup -> nodeGroupId.equals(nodeGroup.getId()));
    }

  public void addSchedule(@NonNull Schedule schedule) {
    schedules.add(schedule);
  }

  public Optional<Schedule> getSchedule(String name) {
    return getElement(schedules, schedule -> name.equals(schedule.getId()), createAmbiguousResultException(Schedule.class, name));
  }

  public void addProcessGroup(@NonNull ProcessGroup processGroup) {
    processGroups.add(processGroup);
  }

  public Optional<ProcessGroup> getProcessGroup(String processGroupId) {
    return getElement(processGroups, processGroup -> processGroupId.equals(processGroup.getId()), createAmbiguousResultException(ProcessGroup.class, processGroupId));
  }


    public Optional<ProcessGroup> getProcessGroup(String nodeGroupId, String scheduleId, String taskName) throws ApiException {
        Objects.requireNonNull(nodeGroupId);
        Objects.requireNonNull(scheduleId);
        Objects.requireNonNull(taskName);

        return processApi.findProcessGroups()
                .stream()
                .filter(processGroup ->
                        processGroup.getProcesses()
                                .stream()
                                .anyMatch(cloudiatorProcess -> {
                                    return scheduleId.equals(cloudiatorProcess.getSchedule()) &&
                                            taskName.equals(cloudiatorProcess.getTask());
                                    //TODO - add check if processGroup is equal
                                }))
                .findFirst();
    }
    public void deleteProcessGroup(String processGroupId) {
        processGroups.removeIf(processGroup -> processGroupId.equals(processGroup.getId()));
    }

  public void addJob(@NonNull Job job) {
    jobs.add(job);
  }

  public Optional<Job> getJob(String name) {
      return getElement(jobs, job -> name.equals(job.getId()), createAmbiguousResultException(Job.class, name));
  }

  private Supplier<AmbiguousResultException> createAmbiguousResultException(Class clazz, String id){
      return () -> new AmbiguousResultException(format("Ambiguous search result - there are more than one %s with the same id=%s", clazz.getSimpleName(), id));
  }

  private <T> Optional<T> getElement(List<T> collection, Predicate<T> predicate, Supplier<AmbiguousResultException> exceptionSupplier) {
    synchronized (collection) {
      return collection.stream()
              .filter(predicate)
              .collect(toSingleton(exceptionSupplier));
    }
  }

  private <T> Collector<T, ?, Optional<T>> toSingleton(Supplier<AmbiguousResultException> exceptionSupplier) {
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

    processGroups.clear();
    processGroups.addAll(processApi.findProcessGroups());

    jobs.clear();
    jobs.addAll(jobApi.findJobs());

    loaded = true;
  }

  @Override
  public boolean isLoaded() {
    return loaded;
  }

  private  <E> List<E> synchronizedList() {
    return Collections.synchronizedList(Lists.newLinkedList());
  }

}
