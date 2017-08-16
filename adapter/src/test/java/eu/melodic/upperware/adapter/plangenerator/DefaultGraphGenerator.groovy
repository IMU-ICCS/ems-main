/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.adapter.plangenerator

import eu.melodic.upperware.adapter.plangenerator.graph.DefaultGraphGenerator
import eu.melodic.upperware.adapter.plangenerator.model.Application
import eu.melodic.upperware.adapter.plangenerator.model.ApplicationComponent
import eu.melodic.upperware.adapter.plangenerator.model.ApplicationComponentInstance
import eu.melodic.upperware.adapter.plangenerator.model.ApplicationComponentInstanceMonitor
import eu.melodic.upperware.adapter.plangenerator.model.ApplicationInstance
import eu.melodic.upperware.adapter.plangenerator.model.Cloud
import eu.melodic.upperware.adapter.plangenerator.model.CloudApi
import eu.melodic.upperware.adapter.plangenerator.model.CloudCredential
import eu.melodic.upperware.adapter.plangenerator.model.CloudProperty
import eu.melodic.upperware.adapter.plangenerator.model.Communication
import eu.melodic.upperware.adapter.plangenerator.model.ComparableModel
import eu.melodic.upperware.adapter.plangenerator.model.LifecycleComponent
import eu.melodic.upperware.adapter.plangenerator.model.PortProvided
import eu.melodic.upperware.adapter.plangenerator.model.PortRequired
import eu.melodic.upperware.adapter.plangenerator.model.VirtualMachine
import eu.melodic.upperware.adapter.plangenerator.model.VirtualMachineInstance
import eu.melodic.upperware.adapter.plangenerator.model.VirtualMachineInstanceMonitor
import eu.melodic.upperware.adapter.plangenerator.tasks.Task
import org.assertj.core.util.Lists
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleDirectedGraph
import spock.lang.Specification


class DefaultGraphGeneratorTests extends Specification {

  POJOCreatorExample c
  Map<GraphValidator.TASK_TYPE, Set<Task>> tasks
  Map<GraphValidator.TASK_TYPE, Set<GraphValidator.TASK_TYPE>> dependencies
  DefaultGraphGenerator generator

  def setup(){
    c = new POJOCreatorExample()
    tasks = GraphValidator.initMap()
    dependencies = GraphValidator.createDependencies()
    generator = new DefaultGraphGenerator()
  }

  def "graph generation: all components"() {

    setup:
    cloudApis = Lists.newArrayList(
            c.createApi(apiName, tasks))
    clouds = Lists.newArrayList(
            c.createCloud(cloudName, apiName, tasks))
    cloudProperties = Lists.newArrayList(
            c.createCloudProperty(cloudPropName, cloudName, tasks))
    cloudCredentials = Lists.newArrayList(
            c.createCloudCredential(cloudCredName, cloudName, tasks))

    Application application =
            c.createApplication(appName, tasks)
    ApplicationInstance applicationInstance =
            c.createApplicationInstance(appInstName, appName, tasks)

    lifecycleComponents = Lists.newArrayList(
            c.createLifecycleComponent(lifecycleName, tasks),
            c.createLifecycleComponent(lifecycleName2, tasks))

    virtualMachines = Lists.newArrayList(
            c.createVirtualMachine(vmName, cloudName, tasks),
            c.createVirtualMachine(vmName2, cloudName, tasks))
    virtualMachineInstances = Lists.newArrayList(
            c.createVirtualMachineInstance(vmInstName, vmName, tasks),
            c.createVirtualMachineInstance(vmInstName2, vmName2, tasks))

    applicationComponents = Lists.newArrayList(
            c.createApplicationComponent(appCompName, appName, lifecycleName, vmName, tasks),
            c.createApplicationComponent(appCompName2, appName, lifecycleName2, vmName2, tasks))

    applicationComponentInstances = Lists.newArrayList(
            c.createApplicationComponentInstance(
                    appCompInstName, appName, vmInstName, appCompName, tasks),
            c.createApplicationComponentInstance(
                    appCompInstName2, appName, vmInstName2, appCompName2, tasks))

    communications = Lists.newArrayList(c.createCommunication(
            communicationName, portProvidedName, portRequiredName, tasks))
    portsProvided = Lists.newArrayList(
            c.createPortProvided(portProvidedName, appCompName, tasks),
            c.createPortProvided(portProvidedName2, appCompName2, tasks))
    portsRequired = Lists.newArrayList(c.createPortRequired(portRequiredName, appCompName, tasks))

    virtualMachineInstanceMonitors = Lists.newArrayList(
            c.toMonitor1(vmInstName, tasks),
            c.toMonitor1(vmInstName2, tasks))
    applicationComponentInstanceMonitors = Lists.newArrayList(
            c.toMonitor3(appCompInstName, tasks),
            c.toMonitor3(appCompInstName2, tasks))

    ComparableModel model = createModel(
            cloudApis, clouds, cloudProperties, cloudCredentials,
            application, applicationInstance, lifecycleComponents,
            virtualMachines, virtualMachineInstances, applicationComponents,
            applicationComponentInstances, communications, portsProvided,
            portsRequired, virtualMachineInstanceMonitors,
            applicationComponentInstanceMonitors)

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateConfigGraph(model)

    then:
    noExceptionThrown()
    checkGraph(graph, tasks, dependencies)

  }

  def "graph generation: without communication"() {

    setup:
    cloudApis = Lists.newArrayList(
            c.createApi(apiName, tasks))
    clouds = Lists.newArrayList(
            c.createCloud(cloudName, apiName, tasks))
    cloudProperties = Lists.newArrayList(
            c.createCloudProperty(cloudPropName, cloudName, tasks))
    cloudCredentials = Lists.newArrayList(
            c.createCloudCredential(cloudCredName, cloudName, tasks))

    Application application =
            c.createApplication(appName, tasks)
    ApplicationInstance applicationInstance =
            c.createApplicationInstance(appInstName, appName, tasks)

    lifecycleComponents = Lists.newArrayList(
            c.createLifecycleComponent(lifecycleName, tasks),
            c.createLifecycleComponent(lifecycleName2, tasks))

    virtualMachines = Lists.newArrayList(
            c.createVirtualMachine(vmName, cloudName, tasks),
            c.createVirtualMachine(vmName2, cloudName, tasks))
    virtualMachineInstances = Lists.newArrayList(
            c.createVirtualMachineInstance(vmInstName, vmName, tasks),
            c.createVirtualMachineInstance(vmInstName2, vmName2, tasks))

    applicationComponents = Lists.newArrayList(
            c.createApplicationComponent(appCompName, appName, lifecycleName, vmName, tasks),
            c.createApplicationComponent(appCompName2, appName, lifecycleName2, vmName2, tasks))

    applicationComponentInstances = Lists.newArrayList(
            c.createApplicationComponentInstance(
                    appCompInstName, appName, vmInstName, appCompName, tasks),
            c.createApplicationComponentInstance(
                    appCompInstName2, appName, vmInstName2, appCompName2, tasks))

    virtualMachineInstanceMonitors = Lists.newArrayList(
            c.toMonitor1(vmInstName, tasks),
            c.toMonitor1(vmInstName2, tasks))
    applicationComponentInstanceMonitors = Lists.newArrayList(
            c.toMonitor3(appCompInstName, tasks),
            c.toMonitor3(appCompInstName2, tasks))

    ComparableModel model = createModel(
            cloudApis, clouds, cloudProperties, cloudCredentials,
            application, applicationInstance, lifecycleComponents,
            virtualMachines, virtualMachineInstances, applicationComponents,
            applicationComponentInstances, communications, portsProvided,
            portsRequired, virtualMachineInstanceMonitors,
            applicationComponentInstanceMonitors)


    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateConfigGraph(model)

    then:
    noExceptionThrown()
    checkGraph(graph, tasks, dependencies)

  }

  def "graph generation: only communication"() {

    setup:
    Application application = c.createApplication(appName, tasks)
    ApplicationInstance applicationInstance = c.createApplicationInstance(appInstName, appName, tasks)

    communications = Lists.newArrayList(c.createCommunication(
            communicationName, portProvidedName, portRequiredName, tasks))
    portsProvided = Lists.newArrayList(
            c.createPortProvided(portProvidedName, appCompName, tasks),
            c.createPortProvided(portProvidedName2, appCompName2, tasks))
    portsRequired = Lists.newArrayList(
            c.createPortRequired(portRequiredName, appCompName, tasks))


    ComparableModel model = createModel(
            cloudApis, clouds, cloudProperties, cloudCredentials,
            application, applicationInstance, lifecycleComponents,
            virtualMachines, virtualMachineInstances, applicationComponents,
            applicationComponentInstances, communications, portsProvided,
            portsRequired, virtualMachineInstanceMonitors,
            applicationComponentInstanceMonitors)

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateConfigGraph(model)

    then:
    noExceptionThrown()
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
  def cloudName = "testCloudName"
  def cloudPropName = "testCloudPropertyName"
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
  //def portRequiredName2 = "testPortRequiredName_2"

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
  Collection<ApplicationComponentInstanceMonitor> applicationComponentInstanceMonitors= new LinkedList<>()





  boolean checkGraph(SimpleDirectedGraph<Task, DefaultEdge> graph,
                     Map<GraphValidator.TASK_TYPE, Set<Task>> tasks,
                     Map<GraphValidator.TASK_TYPE, Set<GraphValidator.TASK_TYPE>> dependencies) {

    int tasksSize = 0
    for (Set<Task> s in tasks.values()) {
      tasksSize += s.size()
    }
    assert (graph.vertexSet().size() == tasksSize)

    for (Task v in graph.vertexSet()) {
      assert (GraphValidator.checkVertex(v, graph, tasks, dependencies))
    }
    return true
  }
}