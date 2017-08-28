/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.adapter.plangenerator

import com.google.common.collect.Sets
import eu.melodic.upperware.adapter.plangenerator.graph.AbstractDefaultGraphGenerator
import eu.melodic.upperware.adapter.plangenerator.graph.DefaultGraphGenerator
import eu.melodic.upperware.adapter.plangenerator.graph.model.MelodicGraph

import static eu.melodic.upperware.adapter.plangenerator.GraphValidatorUtils.*

import eu.melodic.upperware.adapter.plangenerator.model.*
import eu.melodic.upperware.adapter.plangenerator.tasks.Task
import org.assertj.core.util.Lists
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleDirectedGraph
import spock.lang.Specification

class DefaultGraphGeneratorReconfigTests extends Specification {

  POJOCreatorExample c
  Map<TaskType, Set<Task>> mockTasks = Mock(Map)
  Map<TaskType, Set<Task>> newTasks
  Map<TaskType, Set<Task>> oldTasks

  Map<TaskType, Set<TaskType>> dependencies
  Map<TaskType, Set<TaskType>> deletingDependencies
  DefaultGraphGenerator generator

  def reconfig

  def setup() {
    c = new POJOCreatorExample()
    newTasks = initMap()
    oldTasks = initMap()
    dependencies = GraphValidator.createDependencies()
    deletingDependencies = ReconfigGraphValidator.createDeleteDependencies()
    generator = new DefaultGraphGenerator()
  }

  def "reconfiguration graph generation: changed only cloud properties"() {

    setup:
    reconfig = false
    /*old Application - all components exist */
    cloudApis = Lists.newArrayList(
            c.createApi(apiName, reconfig, oldTasks, mockTasks))
    clouds = Lists.newArrayList(
            c.createCloud(cloudName, apiName, reconfig, oldTasks, mockTasks))
    cloudProperties = Lists.newArrayList(
            c.createCloudProperty(cloudPropName, cloudName, reconfig, oldTasks, mockTasks))
    cloudCredentials = Lists.newArrayList(
            c.createCloudCredential(cloudCredName, cloudName, reconfig, oldTasks, mockTasks))
    Application application =
            c.createApplication(appName, reconfig, oldTasks, mockTasks)
    ApplicationInstance applicationInstance =
            c.createApplicationInstance(appInstName, appName, oldTasks)
    lifecycleComponents = Lists.newArrayList(
            c.createLifecycleComponent(lifecycleName, reconfig, oldTasks, mockTasks),
            c.createLifecycleComponent(lifecycleName2, reconfig, oldTasks, mockTasks))
    virtualMachines = Lists.newArrayList(
            c.createVirtualMachine(vmName, cloudName, reconfig, oldTasks, mockTasks),
            c.createVirtualMachine(vmName2, cloudName, reconfig, oldTasks, mockTasks))
    virtualMachineInstances = Lists.newArrayList(
            c.createVirtualMachineInstance(vmInstName, vmName, reconfig, oldTasks, mockTasks),
            c.createVirtualMachineInstance(vmInstName2, vmName2, reconfig, oldTasks, mockTasks))
    applicationComponents = Lists.newArrayList(
            c.createAppComponent(appCompName, appName, lifecycleName, vmName, reconfig, oldTasks, mockTasks),
            c.createAppComponent(appCompName2, appName, lifecycleName2, vmName2, reconfig, oldTasks, mockTasks))
    applicationComponentInstances = Lists.newArrayList(
            c.createAppComponentInstance(
                    appCompInstName, appName, vmInstName, appCompName, reconfig, oldTasks, mockTasks),
            c.createAppComponentInstance(
                    appCompInstName2, appName, vmInstName2, appCompName2, reconfig, oldTasks, mockTasks))
    communications = Lists.newArrayList(c.createCommunication(
            communicationName, portProvidedName, portRequiredName, reconfig, oldTasks, mockTasks))
    portsProvided = Lists.newArrayList(
            c.createPortProvided(portProvidedName, appCompName, reconfig, oldTasks, mockTasks),
            c.createPortProvided(portProvidedName2, appCompName2, reconfig, oldTasks, mockTasks))
    portsRequired = Lists.newArrayList(
            c.createPortRequired(portRequiredName, appCompName, reconfig, oldTasks, mockTasks))

    virtualMachineInstanceMonitors = Lists.newArrayList(
            c.toMonitor1(vmInstName, oldTasks),
            c.toMonitor1(vmInstName2, oldTasks))
    applicationComponentInstanceMonitors = Lists.newArrayList(
            c.toMonitor3(appCompInstName, appCompName, oldTasks),
            c.toMonitor3(appCompInstName2, appCompName2, oldTasks))

    /* new Application - change cloud Property and create monitors */
    newCloudProperties = Lists.newArrayList(
            c.createCloudProperty(cloudPropName2, cloudName, true, newTasks, oldTasks))
    c.addTasksToDelete(TaskType.CLOUD_PROPERTY, newTasks, oldTasks)
    newApplicationComponentInstanceMonitors = Lists.newArrayList(
            c.toMonitor3(appCompInstName, appCompName, newTasks),
            c.toMonitor3(appCompInstName2, appCompName2, newTasks),
            c.toMonitor3(appCompInstName, appCompName, newTasks),
            c.toMonitor3(appCompInstName2, appCompName2, newTasks)
    )
    newVirtualMachineInstanceMonitors = Lists.newArrayList(
            c.toMonitor1(vmInstName, newTasks),
            c.toMonitor1(vmInstName2, newTasks),
            c.toMonitor1(vmInstName, newTasks),
            c.toMonitor1(vmInstName2, newTasks)
    )

    ComparableModel model = createModel(
            cloudApis, clouds, cloudProperties, cloudCredentials,
            application, applicationInstance, lifecycleComponents,
            virtualMachines, virtualMachineInstances, applicationComponents,
            applicationComponentInstances, communications, portsProvided,
            portsRequired, virtualMachineInstanceMonitors,
            applicationComponentInstanceMonitors)

    ComparableModel newModel = createModel(
            cloudApis, clouds, newCloudProperties, cloudCredentials,
            application, applicationInstance, lifecycleComponents,
            virtualMachines, virtualMachineInstances, applicationComponents,
            applicationComponentInstances, communications, portsProvided,
            portsRequired, newVirtualMachineInstanceMonitors,
            newApplicationComponentInstanceMonitors)

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateReconfigGraph(model, newModel)

    then:
    noExceptionThrown()
    graph.vertexSet().size() == 10
    graph.edgeSet().size() == 12
    checkReconfigGraph(graph, newTasks, dependencies, deletingDependencies)
  }

  def "reconfiguration graph generation: ac and acinst"() {

    setup:
    reconfig = true

    Application application =
            c.createApplication(appName, false, oldTasks, mockTasks)

    applicationComponents = Lists.newArrayList(
            c.createAppComponent(appCompName, appName, lifecycleName, vmName, false, oldTasks, mockTasks)
    )

    applicationComponentInstances = Lists.newArrayList(
            c.createAppComponentInstance(
                    appCompInstName, appName, vmInstName, appCompName, false, oldTasks, mockTasks))

    applicationComponentInstanceMonitors = Lists.newArrayList(
            c.toMonitor3(appCompInstName, appCompName, newTasks),
            c.toMonitor3(appCompInstName2, appCompName2, newTasks),
            c.toMonitor3(appCompInstName2, appCompName2, newTasks)
    )

    newApplicationComponents = Lists.newArrayList(
            c.createAppComponent(appCompName2, appName, lifecycleName, vmName, reconfig, newTasks, oldTasks)
    )
    newApplicationComponentInstances = Lists.newArrayList(
            c.createAppComponentInstance(
                    appCompInstName2, appName, vmInstName, appCompName2, reconfig, newTasks, oldTasks)
    )

    c.addTasksToDelete(TaskType.APPLICATION_COMPONENT, newTasks, oldTasks)
    c.addTasksToDelete(TaskType.APPLICATION_COMPONENT_INSTANCE, newTasks, oldTasks)


    ComparableModel model = createModel(
            cloudApis, clouds, cloudProperties, cloudCredentials,
            application, null, lifecycleComponents,
            virtualMachines, virtualMachineInstances, applicationComponents,
            applicationComponentInstances, communications, portsProvided,
            portsRequired, Sets.newHashSet(), applicationComponentInstanceMonitors)

    ComparableModel newModel = createModel(
            cloudApis, clouds, cloudProperties, cloudCredentials,
            application, null, lifecycleComponents,
            virtualMachines, virtualMachineInstances, newApplicationComponents,
            newApplicationComponentInstances, communications, portsProvided,
            portsRequired, Sets.newHashSet(),
            applicationComponentInstanceMonitors)

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateReconfigGraph(model, newModel)

    then:
    !graph.isAllowingLoops()
    System.out.println(graph.toString())

    noExceptionThrown()
//    graph.vertexSet().size() == 7
//    graph.edgeSet().size() == 7
//    checkReconfigGraph(graph, newTasks, dependencies, deletingDependencies)
  }


  def "reconfiguration graph generation: only lifecycle component"() {

    setup:
    reconfig = true
    Application oldApplication =
            c.createApplication(appInstName, false, oldTasks, tasks)
    Application application =
            c.createApplication(appName, reconfig, tasks, oldTasks)

    lifecycleComponents = Lists.newArrayList(
            c.createLifecycleComponent(lifecycleName, reconfig, tasks, oldTasks))

    ComparableModel model = createModel(
            cloudApis, clouds, cloudProperties, cloudCredentials,
            oldApplication, null, Sets.newHashSet(),
            virtualMachines, virtualMachineInstances, applicationComponents,
            applicationComponentInstances, communications, portsProvided,
            portsRequired, virtualMachineInstanceMonitors,
            applicationComponentInstanceMonitors)

    ComparableModel newModel = createModel(
            cloudApis, clouds, cloudProperties, cloudCredentials,
            application, null, lifecycleComponents,
            virtualMachines, virtualMachineInstances, applicationComponents,
            applicationComponentInstances, communications, portsProvided,
            portsRequired, virtualMachineInstanceMonitors,
            applicationComponentInstanceMonitors)

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateReconfigGraph(model, newModel)

    then:
    noExceptionThrown()
    //graph.vertexSet().size() == 24
    checkGraph(graph, tasks, dependencies)
  }

  def "reconfiguration graph generation: allcomponents"() {

    setup:
    reconfig = true
    cloudApis = Lists.newArrayList(
            c.createApi(apiName, reconfig, tasks, oldTasks))
    clouds = Lists.newArrayList(
            c.createCloud(cloudName, apiName, reconfig, tasks, oldTasks))
    cloudProperties = Lists.newArrayList(
            c.createCloudProperty(cloudPropName, cloudName, reconfig, tasks, oldTasks))
    cloudCredentials = Lists.newArrayList(
            c.createCloudCredential(cloudCredName, cloudName, reconfig, tasks, oldTasks))

    Application oldApplication =
            c.createApplication(appInstName, false, oldTasks, tasks)
    Application application =
            c.createApplication(appName, reconfig, tasks, oldTasks)

    //ApplicationInstance applicationInstance =
    //c.createApplicationInstance(appInstName, appName, tasks)

    lifecycleComponents = Lists.newArrayList(
            c.createLifecycleComponent(lifecycleName, reconfig, tasks, oldTasks),
            c.createLifecycleComponent(lifecycleName2, reconfig, tasks, oldTasks))

    virtualMachines = Lists.newArrayList(
            c.createVirtualMachine(vmName, cloudName, reconfig, tasks, oldTasks),
            c.createVirtualMachine(vmName2, cloudName, reconfig, tasks, oldTasks))
    virtualMachineInstances = Lists.newArrayList(
            c.createVirtualMachineInstance(vmInstName, vmName, reconfig, tasks, oldTasks),
            c.createVirtualMachineInstance(vmInstName2, vmName2, reconfig, tasks, oldTasks))

    applicationComponents = Lists.newArrayList(
            c.createAppComponent(appCompName, appName, lifecycleName, vmName, reconfig, tasks, oldTasks),
            c.createAppComponent(appCompName2, appName, lifecycleName2, vmName2, reconfig, tasks, oldTasks))

    applicationComponentInstances = Lists.newArrayList(
            c.createAppComponentInstance(
                    appCompInstName, appName, vmInstName, appCompName, reconfig, tasks, oldTasks),
            c.createAppComponentInstance(
                    appCompInstName2, appName, vmInstName2, appCompName2, reconfig, tasks, oldTasks))

    communications = Lists.newArrayList(
            c.createCommunication(communicationName, portProvidedName, portRequiredName, reconfig, tasks, oldTasks))
    portsProvided = Lists.newArrayList(
            c.createPortProvided(portProvidedName, appCompName, reconfig, tasks, oldTasks),
            c.createPortProvided(portProvidedName2, appCompName2, reconfig, tasks, oldTasks))
    portsRequired = Lists.newArrayList(
            c.createPortRequired(portRequiredName, appCompName, reconfig, tasks, oldTasks))

    virtualMachineInstanceMonitors = Lists.newArrayList(
            c.toMonitor1(vmInstName, tasks),
            c.toMonitor1(vmInstName2, tasks))
    applicationComponentInstanceMonitors = Lists.newArrayList(
            c.toMonitor3(appCompInstName, appCompName, tasks),
            c.toMonitor3(appCompInstName2, appCompName2, tasks))

    c.addTasksToDelete(TaskType.CLOUD_PROPERTY, tasks, oldTasks)

    ComparableModel model = createModel(Sets.newHashSet(), Sets.newHashSet(),
            Sets.newHashSet(), Sets.newHashSet(),
            oldApplication, null,
            Sets.newHashSet(), Sets.newHashSet(),
            Sets.newHashSet(), Sets.newHashSet(), Sets.newHashSet(),
            Sets.newHashSet(), Sets.newHashSet(), Sets.newHashSet(),
            virtualMachineInstanceMonitors,
            applicationComponentInstanceMonitors)

    ComparableModel newModel = createModel(
            cloudApis, clouds, cloudProperties, cloudCredentials,
            application, null, lifecycleComponents,
            virtualMachines, virtualMachineInstances, applicationComponents,
            applicationComponentInstances, communications, portsProvided,
            portsRequired, virtualMachineInstanceMonitors,
            applicationComponentInstanceMonitors)

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateReconfigGraph(model, newModel)

    then:
    noExceptionThrown()
    //graph.vertexSet().size() == 24
    checkGraph(graph, tasks, dependencies)
  }

  ComparableModel createModel(Collection<CloudApi> cloudApis, Collection<Cloud> clouds,
                              Collection<CloudProperty> cloudProperties,
                              Collection<CloudCredential> cloudCredentials,
                              Application application, ApplicationInstance applicationInstance,
                              Collection<LifecycleComponent> lifecycleComponents,
                              Collection<VirtualMachine> virtualMachines,
                              Collection<VirtualMachineInstance> virtualMachineInstances,
                              Collection<ApplicationComponent> applicationComponents,
                              Collection<ApplicationComponentInstance> applicationComponentInstances,
                              Collection<Communication> communications,
                              Collection<PortProvided> portsProvided,
                              Collection<PortRequired> portsRequired,
                              Collection<VirtualMachineInstanceMonitor> vmInstMonitors,
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
            .build()
  }


  def apiName = "testApiName"
  def apiName2 = "testApiName_2"
  def cloudName = "testCloudName"
  def cloudName2 = "testCloudName_2"
  def cloudPropName = "testCloudPropertyName"
  def cloudPropName2 = "testCloudPropertyName_2"
  def cloudCredName = "testCloudCredentialName"
  def cloudCredName2 = "testCloudCredentialName_2"
  def appName = "testApplication"
  def appInstName = "testApplicationInstanceName"
  def lifecycleName = "testLifecycleName"
  def lifecycleName2 = "testLifecycleName_2"
  def vmName = "testVirtualMachineName"
  def vmName2 = "testVirtualMachineName_2"
  def vmInstName = "testVirtualMachineInstanceName"
  def vmInstName2 = "testVirtualMachineInstanceName_2"
  def vmInstName3 = "testVirtualMachineInstanceName_3"
  def appCompName = "testApplicationComponentName"
  def appCompName2 = "testApplicationComponentName_2"
  def appCompInstName = "testApplicationComponentInstanceName"
  def appCompInstName2 = "testApplicationComponentInstanceName_2"

  def communicationName = "testCommunicationName"
  //def communicationName2 = "testCommunicationName_2"
  def portProvidedName = "testPortProvidedName"
  def portProvidedName2 = "testPortProvidedName_2"
  def portRequiredName = "testPortRequiredName"
  //def portRequiredName2 = "testPortRequiredName_2"



  boolean checkReconfigGraph(SimpleDirectedGraph<Task, DefaultEdge> graph,
                             Map<TaskType, Set<Task>> tasks,
                             Map<TaskType, Set<TaskType>> dependencies,
                             Map<TaskType, Set<TaskType>> deletingDependencies) {

    int tasksSize = 0
    for (Set<Task> s in tasks.values()) {
      tasksSize += s.size()
    }
    assert (graph.vertexSet().size() == tasksSize)

    for (Task v in graph.vertexSet()) {
      assert (ReconfigGraphValidator.checkReconfigVertex(v, graph, tasks, dependencies, deletingDependencies))
    }
    return true
  }

  Collection<CloudApi> cloudApis = new LinkedList<>()
  Collection<Cloud> clouds = new LinkedList<>()
  Collection<CloudProperty> cloudProperties = new LinkedList<>()
  Collection<CloudCredential> cloudCredentials = new LinkedList<>()
  Collection<LifecycleComponent> lifecycleComponents = new LinkedList<>()
  Collection<VirtualMachine> virtualMachines = new LinkedList<>()
  Collection<VirtualMachineInstance> virtualMachineInstances = new LinkedList<>()
  Collection<ApplicationComponent> applicationComponents = new LinkedList<>()
  Collection<ApplicationComponentInstance> applicationComponentInstances = new LinkedList<>()
  Collection<Communication> communications = new LinkedList<>()
  Collection<PortProvided> portsProvided = new LinkedList<>()
  Collection<PortRequired> portsRequired = new LinkedList<>()
  Collection<VirtualMachineInstanceMonitor> virtualMachineInstanceMonitors = new LinkedList<>()
  Collection<ApplicationComponentInstanceMonitor> applicationComponentInstanceMonitors = new LinkedList<>()

  Collection<CloudApi> newCloudApis = new LinkedList<>()
  Collection<Cloud> newClouds = new LinkedList<>()
  Collection<CloudProperty> newCloudProperties = new LinkedList<>()
  Collection<CloudCredential> newCloudCredentials = new LinkedList<>()
  Collection<LifecycleComponent> newLifecycleComponents = new LinkedList<>()
  Collection<VirtualMachine> newVirtualMachines = new LinkedList<>()
  Collection<VirtualMachineInstance> newVirtualMachineInstances = new LinkedList<>()
  Collection<ApplicationComponent> newApplicationComponents = new LinkedList<>()
  Collection<ApplicationComponentInstance> newApplicationComponentInstances = new LinkedList<>()
  Collection<Communication> newCommunications = new LinkedList<>()
  Collection<PortProvided> newPortsProvided = new LinkedList<>()
  Collection<PortRequired> newPortsRequired = new LinkedList<>()
  Collection<VirtualMachineInstanceMonitor> newVirtualMachineInstanceMonitors = new LinkedList<>()
  Collection<ApplicationComponentInstanceMonitor> newApplicationComponentInstanceMonitors = new LinkedList<>()



}