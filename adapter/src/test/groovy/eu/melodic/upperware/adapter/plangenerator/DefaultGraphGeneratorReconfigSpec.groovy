/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.adapter.plangenerator

import eu.melodic.upperware.adapter.graphlogger.ToLogGraphLogger
import eu.melodic.upperware.adapter.plangenerator.graph.DefaultGraphGenerator
import eu.melodic.upperware.adapter.plangenerator.model.*
import eu.melodic.upperware.adapter.plangenerator.tasks.ApplicationComponentInstanceTask
import eu.melodic.upperware.adapter.plangenerator.tasks.ApplicationComponentTask
import eu.melodic.upperware.adapter.plangenerator.tasks.CloudPropertyTask
import eu.melodic.upperware.adapter.plangenerator.tasks.PortRequiredTask
import eu.melodic.upperware.adapter.plangenerator.tasks.Task
import org.assertj.core.util.Lists
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleDirectedGraph
import spock.lang.Ignore
import spock.lang.Specification

import static eu.melodic.upperware.adapter.plangenerator.GraphValidatorUtils.*
import static eu.melodic.upperware.adapter.plangenerator.ReconfigGraphValidator.checkReconfigGraph
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.DELETE

@Ignore
class DefaultGraphGeneratorReconfigSpec extends Specification {

  POJOCreatorExample c
  Map<TaskType, Set<Task>> mockTasks = Mock(Map)
  Map<TaskType, Set<Task>> newTasks
  Map<TaskType, Set<Task>> oldTasks

  Map<TaskType, Set<TaskType>> dependencies
  Map<TaskType, Set<TaskType>> deletingDependencies
  DefaultGraphGenerator generator

  ComparableModel model
  ComparableModel newModel

  Application application
  Application newApplication

  def apiName = "testApiName"
  def cloudName = "testCloudName"
  def cloudPropName = "testCloudPropertyName"
  def cloudPropName2 = "testCloudPropertyName_2"
  def cloudCredName = "testCloudCredentialName"
  def appName = "testApplication"
  def appInstName = "testApplicationInstanceName"
  def lifecycleName = "testLifecycleName"
  def lifecycleName2 = "testLifecycleName_2"
  def vmName = "testVirtualMachineName"
  def vmName2 = "testVirtualMachineName_2"
  def vmInstName = "testVirtualMachineInstanceName"
  def vmInstName2 = "testVirtualMachineInstanceName_2"
  def appCompName = "testApplicationComponentName"
  def appCompName2 = "testApplicationComponentName_2"
  def appCompInstName = "testApplicationComponentInstanceName"
  def appCompInstName2 = "testApplicationComponentInstanceName_2"

  def communicationName = "testCommunicationName"
  def portProvidedName = "testPortProvidedName"
  def portProvidedName2 = "testPortProvidedName_2"
  def portRequiredName = "testPortRequiredName"
  def portRequiredName2 = "testPortRequiredName_2"

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

  def reconfig

  def setup() {
    c = new POJOCreatorExample()
    newTasks = initMap()
    oldTasks = initMap()
    dependencies = createDependencies()
    deletingDependencies = createDeleteDependencies()
    generator = new DefaultGraphGenerator(new ToLogGraphLogger())

  }

  def setDefaultMonitors(){
    virtualMachineInstanceMonitors = Lists.newArrayList(
      c.toMonitor1(vmInstName, false, oldTasks))
    applicationComponentInstanceMonitors = Lists.newArrayList(
      c.toMonitor3(appCompInstName, appName, false, oldTasks))

    newApplicationComponentInstanceMonitors = Lists.newArrayList(
      c.toMonitor3(appCompInstName, appName, true, newTasks))
    newVirtualMachineInstanceMonitors = Lists.newArrayList(
      c.toMonitor1(vmInstName, true, newTasks))
  }

  def createModel() {
    model = createModel(
      cloudApis, clouds, cloudProperties, cloudCredentials,
      application, null, lifecycleComponents,
      virtualMachines, virtualMachineInstances, applicationComponents,
      applicationComponentInstances, communications, portsProvided,
      portsRequired, virtualMachineInstanceMonitors,
      applicationComponentInstanceMonitors)
  }

  def createNewModel(){
    newModel = createModel(
      newCloudApis, newClouds, newCloudProperties, newCloudCredentials,
      newApplication, null, newLifecycleComponents,
      newVirtualMachines, newVirtualMachineInstances, newApplicationComponents,
      newApplicationComponentInstances, newCommunications, newPortsProvided,
      newPortsRequired, newVirtualMachineInstanceMonitors,
      newApplicationComponentInstanceMonitors)

  }

  def "reconfiguration graph generation: no changes"() {

    setup:
    reconfig = true
    application = c.createApplication(appName, false, oldTasks, mockTasks)
    newApplication = c.createApplication(appName, reconfig, newTasks, oldTasks)

    setDefaultMonitors()

    createModel()
    createNewModel()

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateReconfigGraph(model, newModel)

    then:
    noExceptionThrown()
    graph.vertexSet().size() == 0
    checkReconfigGraph(graph, newTasks, oldTasks, dependencies, deletingDependencies)

  }


  def "reconfiguration graph generation: changed only cloud properties"() {

    setup:
    reconfig = false
    CloudProperty property = c.createCloudProperty(cloudPropName, cloudName, reconfig, oldTasks, mockTasks)


    /*old Application - all components exist */
    cloudApis = Lists.newArrayList(
            c.createApi(apiName, null, reconfig, oldTasks, mockTasks))
    clouds = Lists.newArrayList(
            c.createCloud(cloudName, apiName, reconfig, oldTasks, mockTasks))
    cloudProperties = Lists.newArrayList(property)

    cloudCredentials = Lists.newArrayList(
            c.createCloudCredential(cloudCredName, cloudName, reconfig, oldTasks, mockTasks))
    application = c.createApplication(appName, reconfig, oldTasks, mockTasks)

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
            c.createAppComponent(
                    appCompName, appName, lifecycleName, vmName, reconfig, oldTasks, mockTasks),
            c.createAppComponent(
                    appCompName2, appName, lifecycleName2, vmName2, reconfig, oldTasks, mockTasks))
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

    reconfig = true

    newCloudProperties = Lists.newArrayList(
      c.createCloudProperty(cloudPropName2, cloudName, reconfig, newTasks, oldTasks))

    newApplication = c.createApplication(appName, reconfig, newTasks, oldTasks)

    c.addDeleteTaskToMap(new CloudPropertyTask(DELETE, property), newTasks)

    newApplicationComponentInstanceMonitors = Lists.newArrayList(
            c.toMonitor3(appCompInstName, appCompName, true, newTasks),
            c.toMonitor3(appCompInstName2, appCompName2, true, newTasks))
    newVirtualMachineInstanceMonitors = Lists.newArrayList(
            c.toMonitor1(vmInstName, true, newTasks),
            c.toMonitor1(vmInstName2, true, newTasks))


    createModel()
    newModel = createModel(
      cloudApis, clouds, newCloudProperties, cloudCredentials,
      newApplication, null, lifecycleComponents,
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
    checkReconfigGraph(graph, newTasks, oldTasks, dependencies, deletingDependencies)
  }

  def "reconfiguration graph generation: only lifecycle component"() {

    setup:
    reconfig = true
    application = c.createApplication(appName, false, oldTasks, mockTasks)
    newApplication = c.createApplication(appName, reconfig, newTasks, oldTasks)


    newLifecycleComponents = Lists.newArrayList(
            c.createLifecycleComponent(lifecycleName, reconfig, newTasks, oldTasks))

    setDefaultMonitors()

    createModel()
    createNewModel()

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateReconfigGraph(model, newModel)

    then:
    noExceptionThrown()
    graph.vertexSet().size() == 3
    graph.edgeSet().size() == 2
    checkReconfigGraph(graph, newTasks, oldTasks, dependencies, deletingDependencies)

  }

  def "reconfiguration graph generation: ac and acinst"() {

    setup:
    reconfig = true

    application = c.createApplication(appName, false, oldTasks, mockTasks)

    ApplicationComponent ac = c.createAppComponent(appCompName, appName, lifecycleName, vmName, false, oldTasks, mockTasks)
    ApplicationComponentInstance acInst =
      c.createAppComponentInstance(appCompInstName, appName, vmInstName, appCompName, false, oldTasks, mockTasks)

    applicationComponents = Lists.newArrayList(ac)

    applicationComponentInstances = Lists.newArrayList(acInst)

    virtualMachineInstanceMonitors = Lists.newArrayList(
      c.toMonitor1(vmInstName, false, oldTasks))
    applicationComponentInstanceMonitors = Lists.newArrayList(
      c.toMonitor3(appCompInstName, appName, false, oldTasks))

    newApplication = c.createApplication(appName, reconfig, newTasks, oldTasks)

    newApplicationComponents = Lists.newArrayList(
            c.createAppComponent(
                    appCompName2, appName, lifecycleName, vmName, reconfig, newTasks, oldTasks)
    )
    newApplicationComponentInstances = Lists.newArrayList(
            c.createAppComponentInstance(
                    appCompInstName2, appName, vmInstName, appCompName2, reconfig, newTasks, oldTasks)
    )

    c.addDeleteTaskToMap(new ApplicationComponentTask(DELETE, ac), newTasks)
    c.addDeleteTaskToMap(new ApplicationComponentInstanceTask(DELETE, acInst), newTasks)
    c.toMonitor3(appCompInstName, appName, false, newTasks)

    newApplicationComponentInstanceMonitors = Lists.newArrayList(
      c.toMonitor3(appCompInstName2, appCompName2, reconfig, newTasks))
    newVirtualMachineInstanceMonitors = Lists.newArrayList(
      c.toMonitor1(vmInstName, reconfig, newTasks))


    createModel()
    createNewModel()

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateReconfigGraph(model, newModel)

    then:
    noExceptionThrown()
    graph.vertexSet().size() == 9
    graph.edgeSet().size() == 10
    checkReconfigGraph(graph, newTasks, oldTasks, dependencies, deletingDependencies)
  }


  def "reconfiguration graph generation: changed cloud api"() {

    setup:
    reconfig = true
    application = c.createApplication(appName, false, oldTasks, mockTasks)
    newApplication = c.createApplication(appName, reconfig, newTasks, oldTasks)

    cloudApis = Lists.newArrayList(
            c.createApi(apiName, cloudPropName, false, oldTasks, mockTasks)
    )

    newCloudApis = Lists.newArrayList(
            c.createApi(apiName, cloudName, reconfig, newTasks, oldTasks)
    )
    setDefaultMonitors()

    createModel()
    createNewModel()

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateReconfigGraph(model, newModel)

    then:
    noExceptionThrown()
    graph.vertexSet().size() == 3
    checkReconfigGraph(graph, newTasks, oldTasks, dependencies, deletingDependencies)

  }

  def "reconfiguration graph generation: only delete task"() {

    setup:
    reconfig = true
    application = c.createApplication(appName, false, oldTasks, mockTasks)
    newApplication = c.createApplication(appName, reconfig, newTasks, oldTasks)

    PortRequired pr = c.createPortRequired(portRequiredName2, appCompName2, false, oldTasks, mockTasks)
    portsRequired = Lists.newArrayList(
            c.createPortRequired(
                    portRequiredName, appCompName, false, oldTasks, mockTasks), pr)

    newPortsRequired = Lists.newArrayList(
            c.createPortRequired(portRequiredName, appCompName, reconfig, newTasks, oldTasks)
    )

    c.addDeleteTaskToMap(new PortRequiredTask(DELETE, pr), newTasks)

    setDefaultMonitors()


    createModel()
    createNewModel()

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateReconfigGraph(model, newModel)

    then:
    noExceptionThrown()
    graph.vertexSet().size() == 3
    checkReconfigGraph(graph, newTasks, oldTasks, dependencies, deletingDependencies)

  }

  def "reconfiguration graph generation: add new vm inst"() {

    setup:
    reconfig = true
    Application oldApplication =
            c.createApplication(appName, false, oldTasks, mockTasks)
    Application newApplication =
            c.createApplication(appName, reconfig, newTasks, oldTasks)

    virtualMachineInstanceMonitors = Lists.newArrayList(
        c.toMonitor1(vmInstName, false, oldTasks)
    )
    applicationComponentInstanceMonitors = Lists.newArrayList(
      c.toMonitor3(appCompInstName, appName, false, oldTasks)
    )

    newApplicationComponentInstanceMonitors = Lists.newArrayList(
      c.toMonitor3(appCompInstName, appName, true, newTasks)
    )

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
    checkReconfigGraph(graph, newTasks, oldTasks, dependencies, deletingDependencies)

  }


  def "reconfiguration graph generation: allcomponents"() {

    setup:
    reconfig = true
    newCloudApis = Lists.newArrayList(
            c.createApi(apiName, null, reconfig, newTasks, oldTasks))
    newClouds = Lists.newArrayList(
            c.createCloud(cloudName, apiName, reconfig, newTasks, oldTasks))
    newCloudProperties = Lists.newArrayList(
            c.createCloudProperty(cloudPropName, cloudName, reconfig, newTasks, oldTasks))
    newCloudCredentials = Lists.newArrayList(
            c.createCloudCredential(cloudCredName, cloudName, reconfig, newTasks, oldTasks))

    application = c.createApplication(appInstName, false, oldTasks, mockTasks)
    newApplication = c.createApplication(appName, reconfig, newTasks, oldTasks)

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
            c.createAppComponent(
                    appCompName, appName, lifecycleName, vmName, reconfig, newTasks, oldTasks),
            c.createAppComponent(
                    appCompName2, appName, lifecycleName2, vmName2, reconfig, newTasks, oldTasks))

    newApplicationComponentInstances = Lists.newArrayList(
            c.createAppComponentInstance(
                    appCompInstName, appName, vmInstName, appCompName, reconfig, newTasks, oldTasks),
            c.createAppComponentInstance(
                    appCompInstName2, appName, vmInstName2, appCompName2, reconfig, newTasks, oldTasks))

    newCommunications = Lists.newArrayList(
            c.createCommunication(
                    communicationName, portProvidedName, portRequiredName, reconfig, newTasks, oldTasks))
    newPortsProvided = Lists.newArrayList(
            c.createPortProvided(portProvidedName, appCompName, reconfig, newTasks, oldTasks),
            c.createPortProvided(portProvidedName2, appCompName2, reconfig, newTasks, oldTasks))
    newPortsRequired = Lists.newArrayList(
            c.createPortRequired(portRequiredName, appCompName, reconfig, newTasks, oldTasks))

    addDeleteTasks(c, newTasks, oldTasks)


    newVirtualMachineInstanceMonitors = Lists.newArrayList(
            c.toMonitor1(vmInstName, reconfig, newTasks),
            c.toMonitor1(vmInstName2, reconfig, newTasks))
    newApplicationComponentInstanceMonitors = Lists.newArrayList(
            c.toMonitor3(appCompInstName, appCompName, reconfig, newTasks),
            c.toMonitor3(appCompInstName2, appCompName2, reconfig, newTasks))




    ComparableModel model = createModel()

    ComparableModel newModel = createNewModel()

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateReconfigGraph(model, newModel)

    then:
    noExceptionThrown()

    graph.vertexSet().size() == 23
    graph.edgeSet().size() == 46
    checkReconfigGraph(graph, newTasks, oldTasks, dependencies, deletingDependencies)
  }

}