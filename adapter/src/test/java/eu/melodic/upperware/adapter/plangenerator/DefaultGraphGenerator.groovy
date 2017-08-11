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

  def "graph generation: example"() {

    setup:

    POJOCreatorExample c = new POJOCreatorExample()
    Map<GraphValidator.TASK_TYPE, Set<Task>> tasks = new HashMap<>()

    Collection<CloudApi> cloudApis = Lists.newArrayList(c.createApi(tasks))
    Collection<Cloud> clouds = Lists.newArrayList(c.createCloud(tasks))
    Collection<CloudProperty> cloudProperties = Lists.newArrayList(c.createCloudProperty(tasks))
    Collection<CloudCredential> cloudCredentials = Lists.newArrayList(c.createCloudCredential(tasks))
    Application application = c.createApplication(tasks)
    ApplicationInstance applicationInstance = c.createApplicationInstance(tasks)
    Collection<LifecycleComponent> lifecycleComponents = Lists.newArrayList(c.createLifecycleComponentEl(tasks), c.createLifecycleComponentApp(tasks))
    Collection<VirtualMachine> virtualMachines = (Lists.newArrayList(c.createVirtualMachineEl(tasks), c.createVirtualMachineApp(tasks)))

    Collection<VirtualMachineInstance> virtualMachineInstances = Lists.newArrayList(c.createVirtualMachineInstanceEl(tasks), c.createVirtualMachineInstanceApp(tasks))
    Collection<ApplicationComponent> applicationComponents = Lists.newArrayList(c.createApplicationComponentEl(), c.createApplicationComponentApp())
    Collection<ApplicationComponentInstance> applicationComponentInstances = Lists.newArrayList(c.createApplicationComponentInstanceEl(), c.createApplicationComponentInstanceApp())
    Collection<Communication> communications = Lists.newArrayList(c.createCommunication())
    Collection<PortProvided> portsProvided = Lists.newArrayList(c.createPortProvided10000(), c.createPortProvided9200())
    Collection<PortRequired> portsRequired = Lists.newArrayList(c.createPortRequired())
    Collection<VirtualMachineInstanceMonitor> virtualMachineInstanceMonitors = Lists.newArrayList(c.toMonitor1(), c.toMonitor2())
    Collection<ApplicationComponentInstanceMonitor> applicationComponentInstanceMonitors = Lists.newArrayList(c.toMonitor3(), c.toMonitor4())

    //Collection<CloudApi> cloudApis = new LinkedList<CloudApi>()
    //cloudApis.add(cloudApi)
    ComparableModel model = ComparableModel.builder()
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
            .virtualMachineInstanceMonitors(virtualMachineInstanceMonitors)
            .applicationComponentInstanceMonitors(applicationComponentInstanceMonitors)
            .build()

    DefaultGraphGenerator generator = new DefaultGraphGenerator()

    when:
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateConfigGraph(model)

    then:
    noExceptionThrown()
    equalsGraph(graph, tasks)
    //graph.containsEdge()

  }


  boolean equalsGraph(SimpleDirectedGraph<Task, DefaultEdge> graph,
                      Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {

    //wrong amount of vertices
    int tasksSize = 0
    for (Set<Task> s : tasks.values()) {
      tasksSize += s.size()
    }

    //if (graph.vertexSet().size() != tasksSize) {
    //return false;
    //}
    for (Task v : graph.vertexSet()) {
      if (!(GraphValidator.checkVertex(v, graph, tasks))) {
        return false
      }
    }
    return true
  }


}

//Collection<Cloud> clouds = Mock(Collection)
//    Collection<CloudProperty> cloudProperties = Mock(Collection)
//    Collection<CloudCredential> cloudCredentials = Mock(Collection)
//    Application application = Mock(Application)
//    ApplicationInstance applicationInstance = Mock(ApplicationInstance)
//    Collection<LifecycleComponent> lifecycleComponents= Mock(Collection)
//    Collection<VirtualMachine> virtualMachines = Mock(Collection)
//    Collection<VirtualMachineInstance> virtualMachineInstances = Mock(Collection)
//    Collection<ApplicationComponent> applicationComponents = Mock(Collection)
//    Collection<ApplicationComponentInstance> applicationComponentInstances = Mock(Collection)
//    Collection<Communication> communications = Mock(Collection)
//    Collection<PortProvided> portsProvided = Mock(Collection)
//    Collection<PortRequired> portsRequired = Mock(Collection)
//    Collection<VirtualMachineInstanceMonitor> virtualMachineInstanceMonitors = Mock(Collection)
//    Collection<ApplicationComponentInstanceMonitor> applicationComponentInstanceMonitors = Mock(Collection)
