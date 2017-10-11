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
import eu.melodic.upperware.adapter.plangenerator.tasks.Task
import org.assertj.core.util.Lists
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleDirectedGraph
import spock.lang.Specification
import spock.lang.Ignore

import static eu.melodic.upperware.adapter.plangenerator.GraphValidator.checkGraph
import static eu.melodic.upperware.adapter.plangenerator.GraphValidatorUtils.createDependencies
import static eu.melodic.upperware.adapter.plangenerator.GraphValidatorUtils.createModel
import static eu.melodic.upperware.adapter.plangenerator.GraphValidatorUtils.initMap

class DefaultGraphGeneratorConfigSpec extends Specification {


  DefaultGraphGenerator generator
  POJOCreatorExample c
  Map<TaskType, Set<Task>> tasks
  Map<TaskType, Set<Task>> oldTasks
  Map<TaskType, Set<TaskType>> dependencies
  boolean reconfig = false


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
  def portProvidedName = "testPortProvidedName"
  def portProvidedName2 = "testPortProvidedName_2"
  def portRequiredName = "testPortRequiredName"

  Application application
  ApplicationInstance applicationInstance
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

  ComparableModel model

  def setup() {
    c = new POJOCreatorExample()
    tasks = initMap()
    oldTasks = initMap()
    dependencies = createDependencies()
    generator = new DefaultGraphGenerator()
  }

  def createModel() {
    model = createModel(
      cloudApis, clouds, cloudProperties, cloudCredentials,
      application, applicationInstance, lifecycleComponents,
      virtualMachines, virtualMachineInstances, applicationComponents,
      applicationComponentInstances, communications, portsProvided,
      portsRequired, virtualMachineInstanceMonitors,
      applicationComponentInstanceMonitors)
  }

@Ignore
  def "configuration graph generation: all components"() {

    setup:
    cloudApis = Lists.newArrayList(
            c.createApi(apiName, null, reconfig, tasks, oldTasks))
    clouds = Lists.newArrayList(
            c.createCloud(cloudName, apiName, reconfig, tasks, oldTasks))
    cloudProperties = Lists.newArrayList(
            c.createCloudProperty(cloudPropName, cloudName, reconfig, tasks, oldTasks))
    cloudCredentials = Lists.newArrayList(
            c.createCloudCredential(cloudCredName, cloudName, reconfig, tasks, oldTasks))

    application = c.createApplication(appName, reconfig, tasks, oldTasks)
    applicationInstance = c.createApplicationInstance(appInstName, appName, tasks)

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

    communications = Lists.newArrayList(c.createCommunication(
            communicationName, portProvidedName, portRequiredName, reconfig, tasks, oldTasks))
    portsProvided = Lists.newArrayList(
            c.createPortProvided(portProvidedName, appCompName, reconfig, tasks, oldTasks),
            c.createPortProvided(portProvidedName2, appCompName2, reconfig, tasks, oldTasks))
    portsRequired = Lists.newArrayList(
            c.createPortRequired(portRequiredName, appCompName, reconfig, tasks, oldTasks))

    virtualMachineInstanceMonitors = Lists.newArrayList(
            c.toMonitor1(vmInstName, reconfig, tasks),
            c.toMonitor1(vmInstName2, reconfig, tasks))
    applicationComponentInstanceMonitors = Lists.newArrayList(
            c.toMonitor3(appCompInstName, appCompName, reconfig, tasks),
            c.toMonitor3(appCompInstName2, appCompName2, reconfig, tasks))

    createModel()

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateConfigGraph(model)

    then:
    noExceptionThrown()
    checkGraph(graph, tasks, dependencies)

  }

  def "configuration graph generation: without communication"() {

    setup:
    cloudApis = Lists.newArrayList(
            c.createApi(apiName, null, reconfig, tasks, oldTasks))
    clouds = Lists.newArrayList(
            c.createCloud(cloudName, apiName, reconfig, tasks, oldTasks))
    cloudProperties = Lists.newArrayList(
            c.createCloudProperty(cloudPropName, cloudName, reconfig, tasks, oldTasks))
    cloudCredentials = Lists.newArrayList(
            c.createCloudCredential(cloudCredName, cloudName, reconfig, tasks, oldTasks))

    application = c.createApplication(appName, reconfig, tasks, oldTasks)
    applicationInstance = c.createApplicationInstance(appInstName, appName, tasks)

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

    virtualMachineInstanceMonitors = Lists.newArrayList(
            c.toMonitor1(vmInstName, reconfig, tasks),
            c.toMonitor1(vmInstName2, reconfig, tasks))
    applicationComponentInstanceMonitors = Lists.newArrayList(
            c.toMonitor3(appCompInstName, appCompName, reconfig, tasks),
            c.toMonitor3(appCompInstName2, appCompName2, reconfig, tasks))

    createModel()


    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateConfigGraph(model)

    then:
    graph.vertexSet().size() == 20
    graph.edgeSet().size() == 24
    noExceptionThrown()
    checkGraph(graph, tasks, dependencies)

  }

  def "configuration graph generation: two clouds"() {

    setup:
    application = c.createApplication(appName, reconfig, tasks, oldTasks)
    applicationInstance = c.createApplicationInstance(appInstName, appName, tasks)


    cloudApis = Lists.newArrayList(
      c.createApi(apiName, null, reconfig, tasks, oldTasks),
      c.createApi(apiName2, null, reconfig, tasks, oldTasks))
    clouds = Lists.newArrayList(
      c.createCloud(cloudName, apiName, reconfig, tasks, oldTasks),
      c.createCloud(cloudName2, apiName2, reconfig, tasks, oldTasks))
    cloudProperties = Lists.newArrayList(
      c.createCloudProperty(cloudPropName, cloudName, reconfig, tasks, oldTasks),
      c.createCloudProperty(cloudPropName2, cloudName2, reconfig, tasks, oldTasks))
    cloudCredentials = Lists.newArrayList(
      c.createCloudCredential(cloudCredName, cloudName, reconfig, tasks, oldTasks),
      c.createCloudCredential(cloudCredName2, cloudName2, reconfig, tasks, oldTasks))

    createModel()

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateConfigGraph(model)

    then:
    graph.edgeSet().size() == 7
    noExceptionThrown()
    checkGraph(graph, tasks, dependencies)

  }


  def "configuration graph generation: three virtualmachines"() {

    setup:
    application = c.createApplication(appName, reconfig, tasks, oldTasks)
    applicationInstance = c.createApplicationInstance(appInstName, appName, tasks)

    cloudApis = Lists.newArrayList(
      c.createApi(apiName, null, reconfig, tasks, oldTasks))
    clouds = Lists.newArrayList(
      c.createCloud(cloudName, apiName, reconfig, tasks, oldTasks))

    virtualMachines = Lists.newArrayList(
      c.createVirtualMachine(vmName, cloudName, reconfig, tasks, oldTasks))
    virtualMachineInstances = Lists.newArrayList(
      c.createVirtualMachineInstance(vmInstName, vmName, reconfig, tasks, oldTasks),
      c.createVirtualMachineInstance(vmInstName2, vmName, reconfig, tasks, oldTasks),
      c.createVirtualMachineInstance(vmInstName3, vmName, reconfig, tasks, oldTasks))

    createModel()

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateConfigGraph(model)

    then:
    graph.edgeSet().size() == 6
    noExceptionThrown()
    checkGraph(graph, tasks, dependencies)
  }


  def "configuration graph generation: only communication - exception"() {

    setup:
    application = c.createApplication(appName, reconfig, tasks, oldTasks)
    applicationInstance = c.createApplicationInstance(appInstName, appName, tasks)

    communications = Lists.newArrayList(c.createCommunication(
            communicationName, portProvidedName, portRequiredName, reconfig, tasks, oldTasks))
    portsProvided = Lists.newArrayList(
            c.createPortProvided(portProvidedName, appCompName, reconfig, tasks, oldTasks),
            c.createPortProvided(portProvidedName2, appCompName2, reconfig, tasks, oldTasks))
    portsRequired = Lists.newArrayList(
            c.createPortRequired(portRequiredName, appCompName, reconfig, tasks, oldTasks))

    createModel()

    when:
    generator.generateConfigGraph(model)

    then:
    thrown(IllegalStateException)
  }

  def "configuration graph generation: only vm instance - exception"() {

    setup:
    application = c.createApplication(appName, reconfig, tasks, oldTasks)
    applicationInstance = c.createApplicationInstance(appInstName, appName, tasks)

    virtualMachineInstances = Lists.newArrayList(
            c.createVirtualMachineInstance(vmInstName, vmName, reconfig, tasks, oldTasks))

    createModel()

    when:
    generator.generateConfigGraph(model)

    then:
    thrown(IllegalStateException)
  }

  def "configuration graph generation: app component without lifecycle - exception"() {

    setup:
    application = c.createApplication(appName, reconfig, tasks, oldTasks)
    applicationInstance = c.createApplicationInstance(appInstName, appName, tasks)

    cloudApis = Lists.newArrayList(
      c.createApi(apiName, null, reconfig, tasks, oldTasks))
    clouds = Lists.newArrayList(
      c.createCloud(cloudName, apiName, reconfig, tasks, oldTasks))
    virtualMachines = Lists.newArrayList(
      c.createVirtualMachine(vmName, cloudName, reconfig, tasks, oldTasks))
    applicationComponents = Lists.newArrayList(
      c.createAppComponent(appCompName, appName, lifecycleName, vmName, reconfig, tasks, oldTasks))

    createModel()

    when:
    generator.generateConfigGraph(model)

    then:
    thrown(IllegalStateException)
  }


}