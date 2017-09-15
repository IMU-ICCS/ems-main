/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */


package eu.melodic.upperware.adapter.plangenerator

import eu.melodic.upperware.adapter.plangenerator.tasks.*
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleDirectedGraph

import java.util.function.Predicate
import java.util.stream.Collectors

import static eu.melodic.upperware.adapter.plangenerator.GraphValidatorUtils.getInEdges
import static eu.melodic.upperware.adapter.plangenerator.GraphValidatorUtils.setPredicateMap
import static eu.melodic.upperware.adapter.plangenerator.GraphValidatorValidatingUtils.checkVertexCorrectness
import static eu.melodic.upperware.adapter.plangenerator.GraphValidatorValidatingUtils.countAndCheckConnections
import static GraphValidatorUtils.getTaskType
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.CREATE
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.DELETE

class ReconfigGraphValidator {

  static void checkReconfigGraph(SimpleDirectedGraph<Task, DefaultEdge> graph,
                                 Map<TaskType, Set<Task>> tasks,
                                 Map<TaskType, Set<Task>> oldTasks,
                                 Map<TaskType, Set<TaskType>> dependencies,
                                 Map<TaskType, Set<TaskType>> deletingDependencies) {

    for (Task v in graph.vertexSet()) {
      checkReconfigVertex(v, graph, tasks, oldTasks, dependencies, deletingDependencies)
    }
  }


  static void checkReconfigVertex(Task v, SimpleDirectedGraph<Task, DefaultEdge> graph,
                                  Map<TaskType, Set<Task>> tasks,
                                  Map<TaskType, Set<Task>> oldTasks,
                                  Map<TaskType, Set<TaskType>> dependencies,
                                  Map<TaskType, Set<TaskType>> deletingDependencies) {

    Map<TaskType, Predicate<Task>> predicateMap = new HashMap<>()
    setPredicateMap(predicateMap, v, DELETE.equals(v.getType()))

    TaskType taskType = getTaskType(v)
    Set<DefaultEdge> edgesOfVertex = graph.edgesOf(v)
    int inEdgesCnt

    /* set predicates needed to check connections with monitors*/
    Predicate<DefaultEdge> edgeSrcIsMonitor = {
      edge ->
        (graph.getEdgeSource(edge) instanceof ApplicationComponentInstanceMonitorTask
          || graph.getEdgeSource(edge) instanceof VirtualMachineInstanceMonitorTask)
    }

    Predicate<DefaultEdge> edgeTrgtIsMonitor = {
      edge ->
        (graph.getEdgeTarget(edge) instanceof ApplicationComponentInstanceMonitorTask
          || graph.getEdgeTarget(edge) instanceof VirtualMachineInstanceMonitorTask)
    }

    Predicate<DefaultEdge> edgeSrcIsCreateOrUpdateTask = { edge -> !DELETE.equals(graph.getEdgeSource(edge).getType()) }
    Predicate<DefaultEdge> edgeSrcIsDeleteTask = { edge -> DELETE.equals(graph.getEdgeSource(edge).getType()) }
    Predicate<DefaultEdge> edgeTrgtIsDeleteTask = { edge -> DELETE.equals(graph.getEdgeTarget(edge).getType()) }

    Predicate<Task> isDeleteMonitor = { monitor -> DELETE.equals(monitor.getType()) }
    Predicate<Task> isCreateOrUpdateMonitor = { monitor -> !DELETE.equals(monitor.getType()) }

    int appCrCnt =  tasks.get(TaskType.APP_COMP_INSTANCE_MONITOR).stream().filter(isCreateOrUpdateMonitor).count()
    int vmCrCnt = tasks.get(TaskType.VM_INSTANCE_MONITOR).stream().filter(isCreateOrUpdateMonitor).count()

    int createOrUpdatemonitorCounter = appCrCnt + vmCrCnt// + countCreateMonitorsForDeletingInstances(tasks)

    int appDelCnt = tasks.get(TaskType.APP_COMP_INSTANCE_MONITOR).stream().filter(isDeleteMonitor).count()
    int vmDelCnt = tasks.get(TaskType.VM_INSTANCE_MONITOR).stream().filter(isDeleteMonitor).count()
    int deleteMonitorCounter = appDelCnt + vmDelCnt

    Set<DefaultEdge> setInEdges = edgesOfVertex.stream().filter({ edge -> (graph.getEdgeTarget(edge).equals(v))})
      .collect(Collectors.toSet())
    Set<DefaultEdge> setOutEdges = edgesOfVertex.stream().filter({ edge -> (graph.getEdgeSource(edge).equals(v))})
      .collect(Collectors.toSet())

    /* ApplicationInstanceTask does not change*/
    if (!(v instanceof ApplicationInstanceTask)) {
      checkVertexCorrectness(v, taskType, tasks)

      if (v instanceof ApplicationComponentInstanceMonitorTask || v instanceof VirtualMachineInstanceMonitorTask) {
        if (CREATE.equals(v.getType())) {
          assert (setInEdges.stream().allMatch(edgeSrcIsCreateOrUpdateTask))
          assert (setOutEdges.stream().allMatch(edgeTrgtIsDeleteTask))
        } else {
          assert (setOutEdges.stream().allMatch(edgeSrcIsDeleteTask))
        }
      } else {

        if (!DELETE.equals(v.getType())) { /* create or update task */
          inEdgesCnt = countAndCheckConnections(v, taskType, graph, tasks, dependencies, predicateMap)
          assert (inEdgesCnt == getInEdges(v, graph))

          /* all edges must be monitors or none */
          if (!(setOutEdges.stream().noneMatch(edgeTrgtIsMonitor))) {
            assert (setOutEdges.stream().allMatch(edgeTrgtIsMonitor))
            /* all monitors should be connected with task*/
            assert (setOutEdges.size() == createOrUpdatemonitorCounter)
          }

        } else { /* delete task */
          inEdgesCnt = countAndCheckConnections(v, taskType, graph, tasks, deletingDependencies, predicateMap)

          if (inEdgesCnt == 0) {
            /* in delete task with no dependency edges and no create or update task in graph should be no in-edges */
            if (!existsAnyCreateOrUpdateTask(tasks)){
              assert (setInEdges.size() == 0)
            }
            /* in delete task with no dependency edges should be in-edges from monitor tasks*/
            else {
              assert (setInEdges.stream().allMatch(edgeSrcIsMonitor))
              //assert (setInEdges.size() == deleteMonitorCounter)
              assert (setInEdges.size() == createOrUpdatemonitorCounter)

            }
          }
          /* all edges must be monitors or none */
          if (!(setOutEdges.stream().noneMatch(edgeTrgtIsMonitor))) {
            assert (setOutEdges.stream().allMatch(edgeTrgtIsMonitor))
            /* all monitors should be connected with task*/
            assert (setOutEdges.size() == deleteMonitorCounter)
          }
        }
        /* in all (not monitor) task should be out edges */
        assert (inEdgesCnt != edgesOfVertex.size())


      }
    }
  }

  static int countCreateMonitorsForDeletingInstances(Map<TaskType, Set<Task>> tasks){

    Predicate<Task> isDeleteTask = { task -> DELETE.equals(task.getType()) }
    int deletingVmInstances = tasks.get(TaskType.VIRTUALMACHINE_INSTANCE).stream().filter(isDeleteTask).count()
    int deletingAcInstances = tasks.get(TaskType.APPLICATION_COMPONENT_INSTANCE).stream().filter(isDeleteTask).count()
    return deletingAcInstances + deletingVmInstances
  }

  static boolean existsAnyCreateOrUpdateTask(Map<TaskType, Set<Task>> tasks){
    Predicate<Task> isCreateOrUpdateTask = { task -> !DELETE.equals(task.getType()) }
    for (TaskType taskType: tasks.keySet()){
      if (!(TaskType.APP_COMP_INSTANCE_MONITOR.equals(taskType)) && !(TaskType.VM_INSTANCE_MONITOR.equals(taskType))) {
        Set<Task> set = tasks.get(taskType)
        if (set.stream().anyMatch(isCreateOrUpdateTask)){
          return true
        }
      }
    }
    return false
  }

}
