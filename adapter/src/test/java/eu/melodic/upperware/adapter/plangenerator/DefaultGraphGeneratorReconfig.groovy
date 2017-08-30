/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.adapter.plangenerator

import eu.melodic.upperware.adapter.plangenerator.graph.DefaultGraphGenerator
import eu.melodic.upperware.adapter.plangenerator.model.*
import eu.melodic.upperware.adapter.plangenerator.tasks.ApplicationComponentInstanceMonitorTask
import eu.melodic.upperware.adapter.plangenerator.tasks.Task
import eu.melodic.upperware.adapter.plangenerator.tasks.VirtualMachineInstanceMonitorTask
import org.assertj.core.util.Lists
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleDirectedGraph
import org.jgrapht.traverse.TopologicalOrderIterator
import spock.lang.Specification

import static eu.melodic.upperware.adapter.plangenerator.GraphValidatorUtils.addDeleteTasks
import static eu.melodic.upperware.adapter.plangenerator.GraphValidatorUtils.initMap
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.CREATE
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.DELETE

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

    virtualMachineInstanceMonitors = Lists.newArrayList(
            c.toMonitor1(vmInstName, false, oldTasks))
    applicationComponentInstanceMonitors = Lists.newArrayList(
            c.toMonitor3(appCompInstName, appName, false, oldTasks))

    newApplicationComponentInstanceMonitors = Lists.newArrayList(
            c.toMonitor3(appCompInstName, appName, true, newTasks))
    newVirtualMachineInstanceMonitors = Lists.newArrayList(
            c.toMonitor1(vmInstName, true, newTasks))

  }

  def "reconfiguration graph generation: no changes"() {

    setup:
    reconfig = true
    Application oldApplication =
            c.createApplication(appName, false, oldTasks, mockTasks)
    Application application =
            c.createApplication(appName, reconfig, newTasks, oldTasks)

    addDeleteTasks(c, newTasks, oldTasks)

    ComparableModel model = createModel(
            cloudApis, clouds, cloudProperties, cloudCredentials,
            oldApplication, null, lifecycleComponents,
            virtualMachines, virtualMachineInstances, applicationComponents,
            applicationComponentInstances, communications, portsProvided,
            portsRequired, virtualMachineInstanceMonitors,
            applicationComponentInstanceMonitors)

    ComparableModel newModel = createModel(
            newCloudApis, newClouds, newCloudProperties, newCloudCredentials,
            application, null, newLifecycleComponents,
            newVirtualMachines, newVirtualMachineInstances, newApplicationComponents,
            newApplicationComponentInstances, newCommunications, newPortsProvided,
            newPortsRequired, newVirtualMachineInstanceMonitors,
            newApplicationComponentInstanceMonitors)

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateReconfigGraph(model, newModel)

    then:
    noExceptionThrown()
    graph.vertexSet().size() == 0 //monitory?
    checkReconfigGraph(graph, newTasks, dependencies, deletingDependencies)

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
            c.toMonitor1(vmInstName, reconfig, oldTasks),
            c.toMonitor1(vmInstName2, reconfig, oldTasks))
    applicationComponentInstanceMonitors = Lists.newArrayList(
            c.toMonitor3(appCompInstName, appCompName, reconfig, oldTasks),
            c.toMonitor3(appCompInstName2, appCompName2, reconfig, oldTasks))

    /* new Application - change cloud Property and create monitors */
    newCloudProperties = Lists.newArrayList(
            c.createCloudProperty(cloudPropName2, cloudName, true, newTasks, oldTasks))

    c.addTasksToDelete(TaskType.CLOUD_PROPERTY, newTasks, oldTasks)
    newApplicationComponentInstanceMonitors = Lists.newArrayList(
            c.toMonitor3(appCompInstName, appCompName, reconfig, newTasks),
            c.toMonitor3(appCompInstName2, appCompName2, reconfig, newTasks),
    )
    newVirtualMachineInstanceMonitors = Lists.newArrayList(
            c.toMonitor1(vmInstName, reconfig, newTasks),
            c.toMonitor1(vmInstName2, reconfig, newTasks),
    )

    addDeleteTasks(c, newTasks, oldTasks)
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

  def "reconfiguration graph generation: only lifecycle component"() {

    setup:
    reconfig = true
    Application oldApplication =
            c.createApplication(appName, false, oldTasks, mockTasks)
    Application application =
            c.createApplication(appName, reconfig, newTasks, oldTasks)



    newLifecycleComponents = Lists.newArrayList(
            c.createLifecycleComponent(lifecycleName, reconfig, newTasks, oldTasks))

    addDeleteTasks(c, newTasks, oldTasks)

    ComparableModel model = createModel(
            cloudApis, clouds, cloudProperties, cloudCredentials,
            oldApplication, null, lifecycleComponents,
            virtualMachines, virtualMachineInstances, applicationComponents,
            applicationComponentInstances, communications, portsProvided,
            portsRequired, virtualMachineInstanceMonitors,
            applicationComponentInstanceMonitors)


    ComparableModel newModel = createModel(
            newCloudApis, newClouds, newCloudProperties, newCloudCredentials,
            application, null, newLifecycleComponents,
            newVirtualMachines, newVirtualMachineInstances, newApplicationComponents,
            newApplicationComponentInstances, newCommunications, newPortsProvided,
            newPortsRequired, newVirtualMachineInstanceMonitors,
            newApplicationComponentInstanceMonitors)

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateReconfigGraph(model, newModel)

    then:
    noExceptionThrown()
    graph.vertexSet().size() == 3
    graph.edgeSet().size() == 2
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


    newApplicationComponents = Lists.newArrayList(
            c.createAppComponent(appCompName2, appName, lifecycleName, vmName, reconfig, newTasks, oldTasks)
    )
    newApplicationComponentInstances = Lists.newArrayList(
            c.createAppComponentInstance(
                    appCompInstName2, appName, vmInstName, appCompName2, reconfig, newTasks, oldTasks)
    )
    ApplicationComponent
    newApplicationComponentInstanceMonitors = Lists.newArrayList(
            c.toMonitor3(appCompInstName2, appCompName2, reconfig, newTasks)
    )

    addDeleteTasks(c, newTasks, oldTasks)


    ComparableModel model = createModel(
            cloudApis, clouds, cloudProperties, cloudCredentials,
            application, null, lifecycleComponents,
            virtualMachines, virtualMachineInstances, applicationComponents,
            applicationComponentInstances, communications, portsProvided,
            portsRequired, virtualMachineInstanceMonitors, applicationComponentInstanceMonitors)

    ComparableModel newModel = createModel(
            newCloudApis, newClouds, newCloudProperties, newCloudCredentials,
            application, null, newLifecycleComponents,
            newVirtualMachines, newVirtualMachineInstances, newApplicationComponents,
            newApplicationComponentInstances, newCommunications, newPortsProvided,
            newPortsRequired, newVirtualMachineInstanceMonitors,
            newApplicationComponentInstanceMonitors)

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateReconfigGraph(model, newModel)

    then:
    noExceptionThrown()
    graph.vertexSet().size() == 9
    graph.edgeSet().size() == 10
    checkReconfigGraph(graph, newTasks, dependencies, deletingDependencies)
  }


  def "reconfiguration graph generation: changed cloud api"() {

    setup:
    reconfig = true
    Application oldApplication =
            c.createApplication(appName, false, oldTasks, mockTasks)
    Application newApplication =
            c.createApplication(appName, reconfig, newTasks, oldTasks)

    cloudApis = Lists.newArrayList(
            c.createApi2(apiName, cloudPropName, false, oldTasks, mockTasks)
    )

    newCloudApis = Lists.newArrayList(
            c.createApi2(apiName, cloudName, reconfig, newTasks, oldTasks)
    )

    addDeleteTasks(c, newTasks, oldTasks)

    ComparableModel model = createModel(
            cloudApis, clouds, cloudProperties, cloudCredentials,
            oldApplication, null, lifecycleComponents,
            virtualMachines, virtualMachineInstances, applicationComponents,
            applicationComponentInstances, communications, portsProvided,
            portsRequired, virtualMachineInstanceMonitors,
            applicationComponentInstanceMonitors)

    ComparableModel newModel = createModel(
            newCloudApis, newClouds, newCloudProperties, newCloudCredentials,
            newApplication, null, newLifecycleComponents,
            newVirtualMachines, newVirtualMachineInstances, newApplicationComponents,
            newApplicationComponentInstances, newCommunications, newPortsProvided,
            newPortsRequired, newVirtualMachineInstanceMonitors,
            newApplicationComponentInstanceMonitors)

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateReconfigGraph(model, newModel)

    then:
    noExceptionThrown()
    graph.vertexSet().size() == 3
    checkReconfigGraph(graph, newTasks, dependencies, deletingDependencies)

  }

  def "reconfiguration graph generation: only delete task"() {

    setup:
    reconfig = true
    Application oldApplication =
            c.createApplication(appName, false, oldTasks, mockTasks)
    Application newApplication =
            c.createApplication(appName, reconfig, newTasks, oldTasks)

    portsRequired = Lists.newArrayList(
            c.createPortRequired(portRequiredName, appCompName, false, oldTasks, mockTasks),
            c.createPortRequired(portRequiredName2, appCompName2, false, oldTasks, mockTasks)
    )

    newPortsRequired = Lists.newArrayList(
            c.createPortRequired(portRequiredName, appCompName, reconfig, newTasks, oldTasks)
    )
    addDeleteTasks(c, newTasks, oldTasks)

    ComparableModel model = createModel(
            cloudApis, clouds, cloudProperties, cloudCredentials,
            oldApplication, null, lifecycleComponents,
            virtualMachines, virtualMachineInstances, applicationComponents,
            applicationComponentInstances, communications, portsProvided,
            portsRequired, virtualMachineInstanceMonitors,
            applicationComponentInstanceMonitors)

    ComparableModel newModel = createModel(
            newCloudApis, newClouds, newCloudProperties, newCloudCredentials,
            newApplication, null, newLifecycleComponents,
            newVirtualMachines, newVirtualMachineInstances, newApplicationComponents,
            newApplicationComponentInstances, newCommunications, newPortsProvided,
            newPortsRequired, newVirtualMachineInstanceMonitors,
            newApplicationComponentInstanceMonitors)

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateReconfigGraph(model, newModel)

    then:
    noExceptionThrown()
    graph.vertexSet().size() == 3
    checkReconfigGraph(graph, newTasks, dependencies, deletingDependencies)

  }

  def "reconfiguration graph generation: add new vm inst"() {

    setup:
    reconfig = true
    Application oldApplication =
            c.createApplication(appName, false, oldTasks, mockTasks)
    Application newApplication =
            c.createApplication(appName, reconfig, newTasks, oldTasks)

    virtualMachines = Lists.newArrayList(
            c.createVirtualMachine(vmName, cloudName, false, oldTasks, mockTasks)
    )

    virtualMachineInstances = Lists.newArrayList(
            c.createVirtualMachineInstance(vmInstName, vmName, false, oldTasks, mockTasks)
    )

    newVirtualMachines = Lists.newArrayList(
            c.createVirtualMachine(vmName, cloudName, reconfig, newTasks, oldTasks),
            c.createVirtualMachine(vmName2, cloudName, reconfig, newTasks, oldTasks)
    )

    newVirtualMachineInstances = Lists.newArrayList(
            c.createVirtualMachineInstance(vmInstName, vmName, reconfig, newTasks, oldTasks),
            c.createVirtualMachineInstance(vmInstName2, vmName2, reconfig, newTasks, oldTasks)
    )

    newVirtualMachineInstanceMonitors = Lists.newArrayList(
            c.toMonitor1(vmInstName, reconfig, newTasks),
            c.toMonitor1(vmInstName2, reconfig, newTasks)
    )

    addDeleteTasks(c, newTasks, oldTasks)

    ComparableModel model = createModel(
            cloudApis, clouds, cloudProperties, cloudCredentials,
            oldApplication, null, lifecycleComponents,
            virtualMachines, virtualMachineInstances, applicationComponents,
            applicationComponentInstances, communications, portsProvided,
            portsRequired, virtualMachineInstanceMonitors,
            applicationComponentInstanceMonitors)

    ComparableModel newModel = createModel(
            newCloudApis, newClouds, newCloudProperties, newCloudCredentials,
            newApplication, null, newLifecycleComponents,
            newVirtualMachines, newVirtualMachineInstances, newApplicationComponents,
            newApplicationComponentInstances, newCommunications, newPortsProvided,
            newPortsRequired, newVirtualMachineInstanceMonitors,
            newApplicationComponentInstanceMonitors)

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateReconfigGraph(model, newModel)

    then:
    noExceptionThrown()
    graph.vertexSet().size() == 5
    graph.edgeSet().size() == 4
    checkReconfigGraph(graph, newTasks, dependencies, deletingDependencies)

  }


  def "reconfiguration graph generation: allcomponents"() {

    setup:
    reconfig = true
    newCloudApis = Lists.newArrayList(
            c.createApi(apiName, reconfig, newTasks, oldTasks))
    newClouds = Lists.newArrayList(
            c.createCloud(cloudName, apiName, reconfig, newTasks, oldTasks))
    newCloudProperties = Lists.newArrayList(
            c.createCloudProperty(cloudPropName, cloudName, reconfig, newTasks, oldTasks))
    newCloudCredentials = Lists.newArrayList(
            c.createCloudCredential(cloudCredName, cloudName, reconfig, newTasks, oldTasks))

    Application application =
            c.createApplication(appInstName, false, oldTasks, mockTasks)
    Application newApplication =
            c.createApplication(appName, reconfig, newTasks, oldTasks)

    newLifecycleComponents = Lists.newArrayList(
            c.createLifecycleComponent(lifecycleName, reconfig, newTasks, oldTasks),
            c.createLifecycleComponent(lifecycleName2, reconfig, newTasks, oldTasks))

    newVirtualMachines = Lists.newArrayList(
            c.createVirtualMachine(vmName, cloudName, reconfig, newTasks, oldTasks),
            c.createVirtualMachine(vmName2, cloudName, reconfig, newTasks, oldTasks))
    newVirtualMachineInstances = Lists.newArrayList(
            c.createVirtualMachineInstance(vmInstName, vmName, reconfig, newTasks, oldTasks),
            c.createVirtualMachineInstance(vmInstName2, vmName2, reconfig, newTasks, oldTasks))

    newApplicationComponents = Lists.newArrayList(
            c.createAppComponent(appCompName, appName, lifecycleName, vmName, reconfig, newTasks, oldTasks),
            c.createAppComponent(appCompName2, appName, lifecycleName2, vmName2, reconfig, newTasks, oldTasks))

    newApplicationComponentInstances = Lists.newArrayList(
            c.createAppComponentInstance(
                    appCompInstName, appName, vmInstName, appCompName, reconfig, newTasks, oldTasks),
            c.createAppComponentInstance(
                    appCompInstName2, appName, vmInstName2, appCompName2, reconfig, newTasks, oldTasks))

    newCommunications = Lists.newArrayList(
            c.createCommunication(communicationName, portProvidedName, portRequiredName, reconfig, newTasks, oldTasks))
    newPortsProvided = Lists.newArrayList(
            c.createPortProvided(portProvidedName, appCompName, reconfig, newTasks, oldTasks),
            c.createPortProvided(portProvidedName2, appCompName2, reconfig, newTasks, oldTasks))
    newPortsRequired = Lists.newArrayList(
            c.createPortRequired(portRequiredName, appCompName, reconfig, newTasks, oldTasks))

    newVirtualMachineInstanceMonitors = Lists.newArrayList(
            c.toMonitor1(vmInstName, reconfig, newTasks),
            c.toMonitor1(vmInstName2, reconfig, newTasks))
    newApplicationComponentInstanceMonitors = Lists.newArrayList(
            c.toMonitor3(appCompInstName, appCompName, reconfig, newTasks),
            c.toMonitor3(appCompInstName2, appCompName2, reconfig, newTasks))


    addDeleteTasks(c, newTasks, oldTasks)


    ComparableModel model = createModel(
            cloudApis, clouds, cloudProperties, cloudCredentials,
            application, null, lifecycleComponents,
            virtualMachines, virtualMachineInstances, applicationComponents,
            applicationComponentInstances, communications, portsProvided,
            portsRequired, Lists.newArrayList(), Lists.newArrayList())

    ComparableModel newModel = createModel(
            newCloudApis, newClouds, newCloudProperties, newCloudCredentials,
            newApplication, null, newLifecycleComponents,
            newVirtualMachines, newVirtualMachineInstances, newApplicationComponents,
            newApplicationComponentInstances, newCommunications, newPortsProvided,
            newPortsRequired, newVirtualMachineInstanceMonitors,
            newApplicationComponentInstanceMonitors)

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateReconfigGraph(model, newModel)

    then:
    noExceptionThrown()

    graph.vertexSet().size() == 23
    graph.edgeSet().size() == 46
    checkReconfigGraph(graph, newTasks, dependencies, deletingDependencies)
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
  //def apiName2 = "testApiName_2"
  def cloudName = "testCloudName"
  //def cloudName2 = "testCloudName_2"
  def cloudPropName = "testCloudPropertyName"
  def cloudPropName2 = "testCloudPropertyName_2"
  def cloudCredName = "testCloudCredentialName"
  //def cloudCredName2 = "testCloudCredentialName_2"
  def appName = "testApplication"
  def appInstName = "testApplicationInstanceName"
  def lifecycleName = "testLifecycleName"
  def lifecycleName2 = "testLifecycleName_2"
  def vmName = "testVirtualMachineName"
  def vmName2 = "testVirtualMachineName_2"
  def vmInstName = "testVirtualMachineInstanceName"
  def vmInstName2 = "testVirtualMachineInstanceName_2"
  //def vmInstName3 = "testVirtualMachineInstanceName_3"
  def appCompName = "testApplicationComponentName"
  def appCompName2 = "testApplicationComponentName_2"
  def appCompInstName = "testApplicationComponentInstanceName"
  def appCompInstName2 = "testApplicationComponentInstanceName_2"

  def communicationName = "testCommunicationName"
  //def communicationName2 = "testCommunicationName_2"
  def portProvidedName = "testPortProvidedName"
  def portProvidedName2 = "testPortProvidedName_2"
  def portRequiredName = "testPortRequiredName"
  def portRequiredName2 = "testPortRequiredName_2"


  void checkReconfigGraph(SimpleDirectedGraph<Task, DefaultEdge> graph,
                          Map<TaskType, Set<Task>> tasks,
                          Map<TaskType, Set<TaskType>> dependencies,
                          Map<TaskType, Set<TaskType>> deletingDependencies) {

    int tasksSize = 0
    for (Set<Task> s in tasks.values()) {
      tasksSize += s.size()
    }
    //checkOrder(graph)
    //assert (graph.vertexSet().size() == tasksSize)

    for (Task v in graph.vertexSet()) {
      assert (ReconfigGraphValidator.checkReconfigVertex(v, graph, tasks, dependencies, deletingDependencies))
    }
  }

  enum State {
    CREATING_OR_UPDATING, MONITOR_CREATING, DELETING, MONITOR_DELETING
  }

  void checkOrder(SimpleDirectedGraph<Task, DefaultEdge> graph) {

    State state = State.CREATING_OR_UPDATING

    TopologicalOrderIterator<Task, DefaultEdge> it = new TopologicalOrderIterator<>(graph)

    while (it.hasNext()) {
      Task task = it.next()

      switch (state) {

        case State.CREATING_OR_UPDATING:
          if (task instanceof VirtualMachineInstanceMonitorTask
                  || task instanceof ApplicationComponentInstanceMonitorTask) {
            assert (task.getType() == CREATE)
            state = State.MONITOR_CREATING
          } else {
            assert (task.getType() != DELETE)
          }
          break

        case State.MONITOR_CREATING:
          if (task.getType() == DELETE) {
            state = State.DELETING
          } else {
            assert (task instanceof VirtualMachineInstanceMonitorTask || task instanceof
                    ApplicationComponentInstanceMonitorTask)
            assert (task.getType() == CREATE)
          }
          break

        case State.DELETING:
          if (task instanceof VirtualMachineInstanceMonitorTask
                  || task instanceof ApplicationComponentInstanceMonitorTask) {
            state = State.MONITOR_DELETING
          } else {
            assert (task.getType() == DELETE)
          }
          break

        case State.MONITOR_DELETING:
          assert (task instanceof VirtualMachineInstanceMonitorTask ||
                  task instanceof ApplicationComponentInstanceMonitorTask)
          assert (task.getType() == DELETE)
          break

      }
    }
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