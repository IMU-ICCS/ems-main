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
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.*;
import java.util.function.Predicate;

import static eu.melodic.upperware.adapter.plangenerator.TaskType.*;

public class GraphValidatorUtils {

  public static Map<TaskType, Set<Task>> initMap() {

    Map<TaskType, Set<Task>> tasks = new HashMap<>();
    tasks.put(CLOUD_API, new HashSet<>());
    tasks.put(CLOUD, new HashSet<>());
    tasks.put(CLOUD_PROPERTY, new HashSet<>());
    tasks.put(CLOUD_CREDENTIAL, new HashSet<>());
    tasks.put(APPLICATION, new HashSet<>());
    tasks.put(APPLICATION_INSTANCE, new HashSet<>());
    tasks.put(LIFECYCLE, new HashSet<>());
    tasks.put(VIRTUALMACHINE, new HashSet<>());
    tasks.put(VIRTUALMACHINE_INSTANCE, new HashSet<>());
    tasks.put(APPLICATION_COMPONENT, new HashSet<>());
    tasks.put(APPLICATION_COMPONENT_INSTANCE, new HashSet<>());
    tasks.put(COMMUNICATION, new HashSet<>());
    tasks.put(PORT_PROVIDED, new HashSet<>());
    tasks.put(PORT_REQUIRED, new HashSet<>());
    tasks.put(VM_INSTANCE_MONITOR, new HashSet<>());
    tasks.put(APP_COMP_INSTANCE_MONITOR, new HashSet<>());
    return tasks;
  }


  static void setPredicateMap(Map<TaskType, Predicate<Task>> predicateMap, Task v, boolean delete) {
    if (delete) {
      if (v instanceof CloudApiTask) {
        predicateMap.put(CLOUD_API, task -> ((CloudTask) task).getData().getApiName()
          .equals(((CloudApiTask) v).getData().getName()));
      }

      if (v instanceof CloudTask) {
        predicateMap.put(CLOUD_PROPERTY, task -> ((CloudPropertyTask) task).getData().getCloudName()
          .equals(((CloudTask) v).getData().getName()));
        predicateMap.put(CLOUD_CREDENTIAL, task -> ((CloudCredentialTask) task).getData().getCloudName()
          .equals(((CloudTask) v).getData().getName()));
        predicateMap.put(VIRTUALMACHINE, task -> ((VirtualMachineTask) task).getData().getCloudName()
          .equals(((CloudTask) v).getData().getName()));
      }
      if (v instanceof ApplicationTask) {
        predicateMap.put(APPLICATION_INSTANCE, task -> (
          (ApplicationInstanceTask) task).getData().getAppName()
          .equals(((ApplicationTask) v).getData().getName()));
        predicateMap.put(APPLICATION_COMPONENT, task -> (
          (ApplicationComponentTask) task).getData().getAppName()
          .equals(((ApplicationTask) v).getData().getName()));
      }

      if (v instanceof VirtualMachineTask) {
        predicateMap.put(VIRTUALMACHINE_INSTANCE, task -> ((VirtualMachineInstanceTask) task).getData().getVmName()
          .equals(((VirtualMachineTask) v).getData().getName()));
        predicateMap.put(APPLICATION_COMPONENT, task -> ((ApplicationComponentTask) task).getData().getVmName()
          .equals(((VirtualMachineTask) v).getData().getName()));
      }

      if (v instanceof VirtualMachineInstanceTask) {
        predicateMap.put(APPLICATION_COMPONENT_INSTANCE, task -> (
          (ApplicationComponentInstanceTask) task).getData().getVmInstName()
          .equals(((VirtualMachineInstanceTask) v).getData().getName()));
        predicateMap.put(VM_INSTANCE_MONITOR, task -> (
          (VirtualMachineInstanceMonitorTask) task).getData().getVmInstName()
          .equals(((VirtualMachineInstanceTask) v).getData().getName()));
      }

      if (v instanceof LifecycleComponentTask) {
        predicateMap.put(APPLICATION_COMPONENT, task -> (
          (ApplicationComponentTask) task).getData().getLcName()
          .equals(((LifecycleComponentTask) v).getData().getName()));
      }

      if (v instanceof ApplicationComponentTask) {
        predicateMap.put(PORT_PROVIDED, task -> (
          (PortProvidedTask) task).getData().getAcName()
          .equals(((ApplicationComponentTask) v).getData().getName()));
        predicateMap.put(PORT_REQUIRED, task -> (
          (PortRequiredTask) task).getData().getAcName()
          .equals(((ApplicationComponentTask) v).getData().getName()));
        predicateMap.put(APPLICATION_COMPONENT_INSTANCE, task -> (
          (ApplicationComponentInstanceTask) task).getData().getAcName()
          .equals(((ApplicationComponentTask) v).getData().getName()));
      }

      if (v instanceof ApplicationComponentInstanceTask) {
        predicateMap.put(APP_COMP_INSTANCE_MONITOR, task -> (
          (ApplicationComponentInstanceMonitorTask) task).getData().getAcInstName()
          .equals(((ApplicationComponentInstanceTask) v).getData().getName()));
      }

      if (v instanceof PortProvidedTask) {
        predicateMap.put(COMMUNICATION, task -> (
          (CommunicationTask) task).getData().getPortProvName()
          .equals(((PortProvidedTask) v).getData().getName()));

      }
      if (v instanceof PortRequiredTask) {
        predicateMap.put(COMMUNICATION, task -> (
          (CommunicationTask) task).getData().getPortReqName()
          .equals(((PortRequiredTask) v).getData().getName()));
      }
    } else {
      if (v instanceof CloudTask) {
        predicateMap.put(CLOUD_API, task -> ((CloudApiTask) task).getData().getName()
          .equals(((CloudTask) v).getData().getApiName()));
      }
      if (v instanceof CloudPropertyTask) {
        predicateMap.put(CLOUD, task -> (
          (CloudTask) task).getData().getName()
          .equals(((CloudPropertyTask) v).getData().getCloudName()));
      }
      if (v instanceof CloudCredentialTask) {
        predicateMap.put(CLOUD, task -> (
          (CloudTask) task).getData().getName()
          .equals(((CloudCredentialTask) v).getData().getCloudName()));
      }
      if (v instanceof ApplicationInstanceTask) {
        predicateMap.put(APPLICATION, task -> (task != null));
      }
      if (v instanceof VirtualMachineTask) {
        predicateMap.put(CLOUD, task -> (
          (CloudTask) task).getData().getName()
          .equals(((VirtualMachineTask) v).getData().getCloudName()));
      }
      if (v instanceof VirtualMachineInstanceTask) {
        predicateMap.put(VIRTUALMACHINE, task -> (
          (VirtualMachineTask) task).getData().getName()
          .equals(((VirtualMachineInstanceTask) v).getData().getVmName()));
      }
      if (v instanceof ApplicationComponentTask) {
        predicateMap.put(APPLICATION, task -> ((ApplicationTask) task).getData().getName()
          .equals(((ApplicationComponentTask) v).getData().getAppName()));
        predicateMap.put(LIFECYCLE, task -> ((LifecycleComponentTask) task).getData().getName()
          .equals(((ApplicationComponentTask) v).getData().getLcName()));
        predicateMap.put(VIRTUALMACHINE, task -> ((VirtualMachineTask) task).getData().getName()
          .equals(((ApplicationComponentTask) v).getData().getVmName()));
      }
      if (v instanceof ApplicationComponentInstanceTask) {
        predicateMap.put(APPLICATION_INSTANCE, task -> (task != null));
        predicateMap.put(APPLICATION_COMPONENT, task -> ((ApplicationComponentTask) task).getData().getName()
          .equals(((ApplicationComponentInstanceTask) v).getData().getAcName()));
        predicateMap.put(VIRTUALMACHINE_INSTANCE, task -> ((VirtualMachineInstanceTask) task).getData().getName()
          .equals(((ApplicationComponentInstanceTask) v).getData().getVmInstName()));
      }
      if (v instanceof CommunicationTask) {
        predicateMap.put(PORT_PROVIDED, task -> ((PortProvidedTask) task).getData().getName()
          .equals(((CommunicationTask) v).getData().getPortProvName()));
        predicateMap.put(PORT_REQUIRED, task -> ((PortRequiredTask) task).getData().getName()
          .equals(((CommunicationTask) v).getData().getPortReqName()));
      }
      if (v instanceof PortProvidedTask) {
        predicateMap.put(APPLICATION_COMPONENT, task -> (
          (ApplicationComponentTask) task).getData().getName()
          .equals(((PortProvidedTask) v).getData().getAcName()));
      }
      if (v instanceof PortRequiredTask) {
        predicateMap.put(APPLICATION_COMPONENT, task -> (
          (ApplicationComponentTask) task).getData().getName()
          .equals(((PortRequiredTask) v).getData().getAcName()));
      }
      if (v instanceof VirtualMachineInstanceMonitorTask) {
        predicateMap.put(VIRTUALMACHINE_INSTANCE, task -> (
          (VirtualMachineInstanceTask) task).getData().getName()
          .equals(((VirtualMachineInstanceMonitorTask) v).getData().getVmInstName()));
      }
      if (v instanceof ApplicationComponentInstanceMonitorTask) {
        predicateMap.put(APPLICATION_COMPONENT_INSTANCE, task -> (
          (ApplicationComponentInstanceTask) task).getData().getName()
          .equals(((ApplicationComponentInstanceMonitorTask) v).getData().getAcInstName()));
      }
    }
  }

  public static Map<TaskType, Set<TaskType>> createDependencies() {
    Map<TaskType, Set<TaskType>> dependencies = new HashMap<>();
    Set<TaskType> emptySet = new HashSet<>();

    dependencies.put(CLOUD_API, emptySet);
    dependencies.put(CLOUD, addToSet(CLOUD_API));
    dependencies.put(CLOUD_PROPERTY, addToSet(CLOUD));
    dependencies.put(CLOUD_CREDENTIAL, addToSet(CLOUD));

    dependencies.put(APPLICATION, emptySet);
    dependencies.put(APPLICATION_INSTANCE, addToSet(APPLICATION));

    dependencies.put(LIFECYCLE, emptySet);

    dependencies.put(VIRTUALMACHINE, addToSet(CLOUD));
    dependencies.put(VIRTUALMACHINE_INSTANCE, addToSet(VIRTUALMACHINE));

    dependencies.put(APPLICATION_COMPONENT, addToSet(APPLICATION, LIFECYCLE, VIRTUALMACHINE));
    dependencies.put(APPLICATION_COMPONENT_INSTANCE,
      addToSet(APPLICATION_INSTANCE, VIRTUALMACHINE_INSTANCE, APPLICATION_COMPONENT));

    dependencies.put(COMMUNICATION, addToSet(PORT_PROVIDED, PORT_REQUIRED));
    dependencies.put(PORT_PROVIDED, addToSet(APPLICATION_COMPONENT));
    dependencies.put(PORT_REQUIRED, addToSet(APPLICATION_COMPONENT));

    dependencies.put(VM_INSTANCE_MONITOR, addToSet(VIRTUALMACHINE_INSTANCE));
    dependencies.put(APP_COMP_INSTANCE_MONITOR, addToSet(APPLICATION_COMPONENT_INSTANCE));

    return dependencies;
  }

  public static Map<TaskType, Set<TaskType>> createDeleteDependencies() {
    Map<TaskType, Set<TaskType>> dependencies = new HashMap<>();
    Set<TaskType> emptySet = new HashSet<>();

    dependencies.put(CLOUD_API, addToSet(CLOUD));
    dependencies.put(CLOUD, addToSet(CLOUD_PROPERTY, CLOUD_CREDENTIAL, VIRTUALMACHINE));
    dependencies.put(CLOUD_PROPERTY, emptySet);
    dependencies.put(CLOUD_CREDENTIAL, emptySet);
    dependencies.put(APPLICATION, addToSet(APPLICATION_INSTANCE, APPLICATION_COMPONENT));
    dependencies.put(APPLICATION_INSTANCE, addToSet(APPLICATION_COMPONENT_INSTANCE));
    dependencies.put(LIFECYCLE, addToSet(APPLICATION_COMPONENT));
    dependencies.put(VIRTUALMACHINE, addToSet(VIRTUALMACHINE_INSTANCE, APPLICATION_COMPONENT));
    dependencies.put(VIRTUALMACHINE_INSTANCE, addToSet(APPLICATION_COMPONENT_INSTANCE, VM_INSTANCE_MONITOR));

    dependencies.put(APPLICATION_COMPONENT, addToSet(PORT_PROVIDED, PORT_REQUIRED, APPLICATION_COMPONENT_INSTANCE));
    dependencies.put(APPLICATION_COMPONENT_INSTANCE, addToSet(APP_COMP_INSTANCE_MONITOR));

    dependencies.put(COMMUNICATION, emptySet);

    dependencies.put(PORT_PROVIDED, addToSet(COMMUNICATION));
    dependencies.put(PORT_REQUIRED, addToSet(COMMUNICATION));

    dependencies.put(VM_INSTANCE_MONITOR, emptySet);

    dependencies.put(APP_COMP_INSTANCE_MONITOR, emptySet);

    return dependencies;
  }

  static int getInEdges(Task v, SimpleDirectedGraph<Task, DefaultEdge> graph) {
    int inEdgesCounter = 0;

    for (DefaultEdge e : graph.edgesOf(v)) {
      if (graph.getEdgeTarget(e).equals(v)) {
        inEdgesCounter++;
      }
    }
    return inEdgesCounter;
  }

  static TaskType getTaskType(Task v){
    if (v instanceof CloudApiTask) {return CLOUD_API;}
    else if (v instanceof CloudTask) {return CLOUD;}
    else if (v instanceof CloudPropertyTask) { return CLOUD_PROPERTY;}
    else if (v instanceof CloudCredentialTask) {return CLOUD_CREDENTIAL;}
    else if (v instanceof ApplicationTask) {return APPLICATION;}
    else if (v instanceof ApplicationInstanceTask) {return APPLICATION_INSTANCE;}
    else if (v instanceof LifecycleComponentTask) {return LIFECYCLE;}
    else if (v instanceof VirtualMachineTask) {return VIRTUALMACHINE;}
    else if (v instanceof VirtualMachineInstanceTask) {return VIRTUALMACHINE_INSTANCE;}
    else if (v instanceof ApplicationComponentTask) {return APPLICATION_COMPONENT;}
    else if (v instanceof ApplicationComponentInstanceTask) {return APPLICATION_COMPONENT_INSTANCE;}
    else if (v instanceof PortProvidedTask) {return PORT_PROVIDED;}
    else if (v instanceof PortRequiredTask) {return PORT_REQUIRED;}
    else if (v instanceof CommunicationTask) {return COMMUNICATION;}
    else if (v instanceof ApplicationComponentInstanceMonitorTask) {return APP_COMP_INSTANCE_MONITOR;}
    return VM_INSTANCE_MONITOR;
    //else if (v instanceof VirtualMachineInstanceMonitorTask) {return VM_INSTANCE_MONITOR;}

  }

  static void addDeleteTasks(POJOCreatorExample c, Map<TaskType, Set<Task>> newTasks, Map<TaskType, Set<Task>> oldTasks) {
    c.addTasksToDelete(TaskType.CLOUD_PROPERTY, newTasks, oldTasks);
    c.addTasksToDelete(LIFECYCLE, newTasks, oldTasks);
    c.addTasksToDelete(VIRTUALMACHINE, newTasks, oldTasks);
    c.addTasksToDelete(VIRTUALMACHINE_INSTANCE, newTasks, oldTasks);
    c.addTasksToDelete(APPLICATION_COMPONENT, newTasks, oldTasks);
    c.addTasksToDelete(APPLICATION_COMPONENT_INSTANCE, newTasks, oldTasks);
    c.addTasksToDelete(PORT_PROVIDED, newTasks, oldTasks);
    c.addTasksToDelete(PORT_REQUIRED, newTasks, oldTasks);
    c.addTasksToDelete(COMMUNICATION, newTasks, oldTasks);
  }

  private static Set<TaskType> addToSet(TaskType t) {
    Set<TaskType> set = new HashSet<>();
    set.add(t);
    return set;
  }

  private static Set<TaskType> addToSet(TaskType t1, TaskType t2) {
    Set<TaskType> set = new HashSet<>();
    set.add(t1);
    set.add(t2);
    return set;
  }

  private static Set<TaskType> addToSet(TaskType t1, TaskType t2, TaskType t3) {
    Set<TaskType> set = new HashSet<>();
    set.add(t1);
    set.add(t2);
    set.add(t3);
    return set;
  }

  static ComparableModel createModel(Collection<CloudApi> cloudApis, Collection<Cloud> clouds,
    Collection<CloudProperty> cloudProperties, Collection<CloudCredential> cloudCredentials,
    Application application, ApplicationInstance applicationInstance,
    Collection<LifecycleComponent> lifecycleComponents, Collection<VirtualMachine> virtualMachines,
    Collection<VirtualMachineInstance> virtualMachineInstances,
    Collection<ApplicationComponent> applicationComponents,
    Collection<ApplicationComponentInstance> applicationComponentInstances,
    Collection<Communication> communications, Collection<PortProvided> portsProvided,
    Collection<PortRequired> portsRequired, Collection<VirtualMachineInstanceMonitor> vmInstMonitors,
    Collection<ApplicationComponentInstanceMonitor> appCompInstMonitors) {
    return ComparableModel.builder()
      .cloudApis(cloudApis)
      .clouds(clouds)
      .cloudProperties(cloudProperties)
      .cloudCredentials(cloudCredentials)
      .application(application)
      .applicationInstance(applicationInstance)
      .lifecycleComponents(lifecycleComponents)
      .virtualMachines(virtualMachines)
      .virtualMachineInstances(virtualMachineInstances)
      .applicationComponents(applicationComponents)
      .applicationComponentInstances(applicationComponentInstances)
      .communications(communications)
      .portsProvided(portsProvided)
      .portsRequired(portsRequired)
      .virtualMachineInstanceMonitors(vmInstMonitors)
      .applicationComponentInstanceMonitors(appCompInstMonitors)
      .build();
  }

}