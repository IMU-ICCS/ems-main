/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator;

import eu.melodic.upperware.adapter.plangenerator.model.*;
import eu.melodic.upperware.adapter.plangenerator.tasks.*;

import java.util.Map;
import java.util.Set;

import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.*;
import static eu.melodic.upperware.adapter.plangenerator.TaskType.*;


public class POJOCreatorExample {

  void createOrUpdate(Task createTask, Task updateTask, TaskType type,
                      Map<TaskType, Set<Task>> tasks,
                      Map<TaskType, Set<Task>> oldTasks){
    String id = createTask.getData().getName();
    if (containsTask(oldTasks.get(type), id)){
      if (!oldTasks.get(type).contains(createTask)) {
        addToMap(updateTask, type, tasks);
      }
    }
    else {
      addToMap(createTask, type, tasks);
    }
  }

  void createIfNotExist(Task createTask, TaskType type,
          Map<TaskType, Set<Task>> tasks,
          Map<TaskType, Set<Task>> oldTasks){
    String id = createTask.getData().getName();
    if (!containsTask(oldTasks.get(type), id)){
      addToMap(createTask, type, tasks);
    }
  }

  boolean containsTask(Set<? extends Task> tasks, String id){
    for(Task t: tasks){
      if (t.getData().getName().equals(id)){
        return true;
      }
    }
    return false;
  }

  void addTasksToDelete(TaskType taskType, Map<TaskType, Set<Task>> tasks,
                        Map<TaskType, Set<Task>> oldTasks){
    Set<Task> setNewTasks = tasks.get(taskType);
    Set<Task> setOldTasks = oldTasks.get(taskType);
    setOldTasks.stream()
            .filter(oldTask -> !(setNewTasks.contains(oldTask)))
            .forEach(taskToDelete -> addDeleteTaskToMap(taskToDelete, tasks)
            );
  }

  void addToMap(Task task, TaskType taskType, Map<TaskType, Set<Task>> tasks){
    Set<Task> set = tasks.get(taskType);
    set.add(task);
    tasks.put(taskType, set);
  }

  CloudApi createApi(String name, boolean reconfig,
                     Map<TaskType, Set<Task>> tasks,
                     Map<TaskType, Set<Task>> oldTasks) {

    CloudApi t = CloudApi.builder()
            .name(name)
            .build();

    CloudApiTask task = new CloudApiTask(CREATE, t);
    TaskType taskType = CLOUD_API;
    if (reconfig){
      createOrUpdate(task, new CloudApiTask(UPDATE,t), taskType, tasks, oldTasks);
    }
    else {
      addToMap(task, taskType, tasks);
    }
    return t;
  }


  Cloud createCloud(String name, String apiName, boolean reconfig,
                    Map<TaskType, Set<Task>> tasks,
                    Map<TaskType, Set<Task>> oldTasks) {
    Cloud t = Cloud.builder()
            .name(name)
            .apiName(apiName)
            .build();

    CloudTask task = new CloudTask(CREATE, t);
    TaskType taskType = CLOUD;
    if (reconfig){
      createOrUpdate(task, new CloudTask(UPDATE,t), taskType, tasks, oldTasks);
    }
    else {
      addToMap(task, taskType, tasks);
    }
    return t;
  }

  CloudProperty createCloudProperty(String name, String cloudName, boolean reconfig,
                                    Map<TaskType, Set<Task>> tasks,
                                    Map<TaskType, Set<Task>> oldTasks) {
    CloudProperty t = CloudProperty.builder()
            .name(name)
            .cloudName(cloudName)
            .build();
    CloudPropertyTask createTask = new CloudPropertyTask(CREATE, t);
    TaskType taskType = CLOUD_PROPERTY;
    if (reconfig){
      createIfNotExist(createTask, taskType, tasks, oldTasks);
    }
    else {
      addToMap(createTask, taskType, tasks);
    }
    return t;
  }

  CloudCredential createCloudCredential(String name, String cloudName, boolean reconfig,
                                        Map<TaskType, Set<Task>> tasks,
                                        Map<TaskType, Set<Task>> oldTasks) {

    CloudCredential t = CloudCredential.builder()
            .name(name)
            .cloudName(cloudName)
            .build();
    TaskType taskType = CLOUD_CREDENTIAL;

    CloudCredentialTask task = new CloudCredentialTask(CREATE, t);
    if (reconfig){
      createOrUpdate(task, new CloudCredentialTask(UPDATE,t), taskType, tasks, oldTasks);
    }
    else {
      addToMap(task, taskType, tasks);
    }

    return t;
  }

  Application createApplication(String name, boolean reconfig,
                                Map<TaskType, Set<Task>> tasks,
                                Map<TaskType, Set<Task>> oldTasks) {
    Application t = Application.builder()
            .name(name)
            .build();
    TaskType taskType = APPLICATION;
    ApplicationTask createTask = new ApplicationTask(CREATE, t);

    if (reconfig){
      Set<Task> oldAppTask = oldTasks.get(taskType);
      if (!oldAppTask.contains(createTask)){
        String oldName = oldAppTask.iterator().next().getData().getName();
        createTask.getData().setOldName(oldName);
        ApplicationTask updateTask = new ApplicationTask(UPDATE,createTask.getData());
        addToMap(updateTask, taskType, tasks);
      }
    }
    else{
      addToMap(createTask, taskType, tasks);
    }
    return t;
  }

  ApplicationInstance createApplicationInstance(String name, String appName,
                                                Map<TaskType, Set<Task>> tasks) {
    ApplicationInstance t = ApplicationInstance.builder()
            .name(name)
            .appName(appName)
            .build();
    Set<Task> set = tasks.get(APPLICATION_INSTANCE);
    set.add(new ApplicationInstanceTask(CREATE, t));
    tasks.put(APPLICATION_INSTANCE, set);
    return t;
  }

  LifecycleComponent createLifecycleComponent(String name, boolean reconfig,
                                              Map<TaskType, Set<Task>> tasks,
                                              Map<TaskType, Set<Task>> oldTasks) {
    LifecycleComponent t = LifecycleComponent.builder()
            .name(name)
            .build();
    LifecycleComponentTask createTask = new LifecycleComponentTask(CREATE, t);
    TaskType taskType = LIFECYCLE;
    if (reconfig){
      createIfNotExist(createTask, taskType, tasks, oldTasks);
    }
    else {
      addToMap(createTask, taskType, tasks);
    }
    return t;
  }

  VirtualMachine createVirtualMachine(String name, String cloudName, boolean reconfig,
                                      Map<TaskType, Set<Task>> tasks,
                                      Map<TaskType, Set<Task>> oldTasks) {
    VirtualMachine t = VirtualMachine.builder()
            .name(name)
            .cloudName(cloudName)
            .build();

    VirtualMachineTask createTask = new VirtualMachineTask(CREATE, t);
    TaskType taskType = VIRTUALMACHINE;
    if (reconfig){
      createIfNotExist(createTask, taskType, tasks, oldTasks);
    }
    else {
      addToMap(createTask, taskType, tasks);
    }
    return t;
  }

  VirtualMachineInstance createVirtualMachineInstance(String name, String vmName, boolean reconfig,
                                                      Map<TaskType, Set<Task>> tasks,
                                                      Map<TaskType, Set<Task>> oldTasks) {
    VirtualMachineInstance t = VirtualMachineInstance.builder()
            .name(name)
            .vmName(vmName)
            .build();
    VirtualMachineInstanceTask createTask = new VirtualMachineInstanceTask(CREATE, t);
    TaskType taskType = VIRTUALMACHINE_INSTANCE;
    if (reconfig){
      createIfNotExist(createTask, taskType, tasks, oldTasks);
    }
    else {
      addToMap(createTask, taskType, tasks);
    }
    return t;
  }


  ApplicationComponent createAppComponent(String name, String appName, String lcName, String vmName,
                                          boolean reconfig,
                                          Map<TaskType, Set<Task>> tasks,
                                          Map<TaskType, Set<Task>> oldTasks) {

    ApplicationComponent t = ApplicationComponent.builder()
            .name(name)
            .appName(appName)
            .lcName(lcName)
            .vmName(vmName)
            .build();
    ApplicationComponentTask createTask = new ApplicationComponentTask(CREATE, t);
    TaskType taskType = APPLICATION_COMPONENT;
    if (reconfig){
      createIfNotExist(createTask, taskType, tasks, oldTasks);
    }
    else {
      addToMap(createTask, taskType, tasks);
    }
    return t;
  }

  ApplicationComponentInstance createAppComponentInstance(String name, String appName, String vmInstName, String acName,
                                                          boolean reconfig,
                                                          Map<TaskType, Set<Task>> tasks,
                                                          Map<TaskType, Set<Task>> oldTasks) {
    ApplicationComponentInstance t = ApplicationComponentInstance.builder()
            .name(name)
            .acName(acName)
            .vmInstName(vmInstName)
            .appName(appName)
            .build();

    ApplicationComponentInstanceTask createTask = new ApplicationComponentInstanceTask(CREATE, t);
    TaskType taskType = APPLICATION_COMPONENT_INSTANCE;
    if (reconfig){
      createIfNotExist(createTask, taskType, tasks, oldTasks);
    }
    else {
      addToMap(createTask, taskType, tasks);
    }
    return t;
  }

  Communication createCommunication(String name, String portProvName, String portReqName, boolean reconfig,
                                    Map<TaskType, Set<Task>> tasks, Map<TaskType, Set<Task>> oldTasks) {
    Communication t = Communication.builder()
            .name(name)
            .portProvName(portProvName)
            .portReqName(portReqName)
            .build();
    CommunicationTask createTask = new CommunicationTask(CREATE, t);
    TaskType taskType = COMMUNICATION;
    if (reconfig){
      createIfNotExist(createTask, taskType, tasks, oldTasks);
    }
    else {
      addToMap(createTask, taskType, tasks);
    }
    return t;
  }

  PortProvided createPortProvided(String name, String acName, boolean reconfig,
                                  Map<TaskType, Set<Task>> tasks, Map<TaskType, Set<Task>> oldTasks) {
    PortProvided t = PortProvided.builder()
            .name(name)
            .acName(acName)
            .build();
    PortProvidedTask createTask = new PortProvidedTask(CREATE, t);
    TaskType taskType = PORT_PROVIDED;
    if (reconfig){
      createIfNotExist(createTask, taskType, tasks, oldTasks);
    }
    else {
      addToMap(createTask, taskType, tasks);
    }
    return t;
  }

  PortRequired createPortRequired(String name, String acName, boolean reconfig,
                                  Map<TaskType, Set<Task>> tasks, Map<TaskType, Set<Task>> oldTasks) {
    PortRequired t = PortRequired.builder()
            .name(name)
            .acName(acName)
            .build();
    PortRequiredTask createTask = new PortRequiredTask(CREATE, t);
    TaskType taskType = PORT_REQUIRED;
    if (reconfig){
      createIfNotExist(createTask, taskType, tasks, oldTasks);
    }
    else {
      addToMap(createTask, taskType, tasks);
    }
    return t;
  }

  VirtualMachineInstanceMonitor toMonitor1(String vmInstName,
                                           Map<TaskType, Set<Task>> tasks) {
    VirtualMachineInstanceMonitor t = VirtualMachineInstanceMonitor.builder()
            .vmInstName(vmInstName)
            .build();
    Set<Task> set = tasks.get(VM_INSTANCE_MONITOR);
    set.add(new VirtualMachineInstanceMonitorTask(CREATE, t));
    tasks.put(VM_INSTANCE_MONITOR, set);
    return t;
  }

  ApplicationComponentInstanceMonitor toMonitor3(String acInstName,
                                                 Map<TaskType, Set<Task>> tasks) {
    ApplicationComponentInstanceMonitor t = ApplicationComponentInstanceMonitor.builder()
            .acInstName(acInstName)
            .build();

    Set<Task> set = tasks.get(APP_COMP_INSTANCE_MONITOR);
    set.add(new ApplicationComponentInstanceMonitorTask(CREATE, t));
    tasks.put(APP_COMP_INSTANCE_MONITOR, set);
    return t;
  }

  void addDeleteTaskToMap(Task task, Map<TaskType, Set<Task>> tasks){
    if (task instanceof CloudPropertyTask){
      addToMap(new CloudPropertyTask(
              DELETE, (CloudProperty) task.getData()), CLOUD_PROPERTY, tasks);
    }
    else if (task instanceof LifecycleComponentTask){
      addToMap(new LifecycleComponentTask(
              DELETE, (LifecycleComponent) task.getData()), LIFECYCLE, tasks);
    }
    else if (task instanceof VirtualMachineTask){
      addToMap(new VirtualMachineTask(
              DELETE, (VirtualMachine) task.getData()), VIRTUALMACHINE, tasks);
    }
    else if (task instanceof VirtualMachineInstanceTask){
      addToMap(new VirtualMachineInstanceTask(
              DELETE, (VirtualMachineInstance) task.getData()), VIRTUALMACHINE_INSTANCE, tasks);
    }
    else if (task instanceof ApplicationComponentTask){
      addToMap(new ApplicationComponentTask(
              DELETE, (ApplicationComponent) task.getData()), APPLICATION_COMPONENT, tasks);
    }
    else if (task instanceof ApplicationComponentInstanceTask){
      addToMap(new ApplicationComponentInstanceTask(
              DELETE, (ApplicationComponentInstance) task.getData()), APPLICATION_COMPONENT_INSTANCE, tasks);
    }
    else if (task instanceof PortProvidedTask){
      addToMap(new PortProvidedTask(
              DELETE, (PortProvided) task.getData()), PORT_PROVIDED, tasks);

    }
    else if (task instanceof PortRequiredTask){
      addToMap(new PortRequiredTask(
              DELETE, (PortRequired) task.getData()), PORT_REQUIRED, tasks);
    }
    else if (task instanceof CommunicationTask){
      addToMap(new CommunicationTask(
              DELETE, (Communication) task.getData()), COMMUNICATION, tasks);
    }
  }
}
