/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.communication.colosseum;

import de.uniulm.omi.cloudiator.colosseum.client.entities.*;
import de.uniulm.omi.cloudiator.colosseum.client.entities.Api;
import de.uniulm.omi.cloudiator.colosseum.client.entities.Cloud;
import de.uniulm.omi.cloudiator.colosseum.client.entities.CloudCredential;
import de.uniulm.omi.cloudiator.colosseum.client.entities.Communication;
import de.uniulm.omi.cloudiator.colosseum.client.entities.Hardware;
import de.uniulm.omi.cloudiator.colosseum.client.entities.Image;
import de.uniulm.omi.cloudiator.colosseum.client.entities.Location;
import de.uniulm.omi.cloudiator.colosseum.client.entities.OperatingSystem;
import de.uniulm.omi.cloudiator.colosseum.client.entities.PortProvided;
import de.uniulm.omi.cloudiator.colosseum.client.entities.PortRequired;
import de.uniulm.omi.cloudiator.colosseum.client.entities.VirtualMachine;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.model.*;
import io.github.cloudiator.rest.model.Process;
import io.github.cloudiator.rest.model.Schedule;
import lombok.NonNull;

import java.util.List;

public interface ColosseumApi {

  Queue findQueuedTask(String taskId) throws ApiException;


  Queue addSchedule(ScheduleNew scheduleNew) throws ApiException;

  Schedule getSchedule(String scheduleId) throws ApiException;

  List<Schedule> getSchedules() throws ApiException;


  Queue addProcess(ProcessNew processNew) throws ApiException;

  Process getProcess(String scheduleId, String processId) throws ApiException;

  List<Process> getProcessess(String scheduleId) throws ApiException;


  Job addJob(JobNew jobNew) throws ApiException;

  Job getJob(String jobId) throws ApiException;

  List<Job> getJobs() throws ApiException;


  Queue addNode(NodeRequest nodeRequest) throws ApiException;

  Node getNode(String id) throws ApiException;

  List<Node> getNodes() throws ApiException;


  NodeGroup getNodeGroup(String nodeGroupId) throws ApiException;

  Api createApi(Api api);

  Api updateApi(Api api);

  List<Api> getApis();

  Cloud createCloud(Cloud cloud);

  Cloud updateCloud(Cloud cloud);

  List<Cloud> getClouds();

  CloudProperty createCloudProperty(CloudProperty cloudProperty);

  CloudProperty updateCloudProperty(CloudProperty cloudProperty);

  void deleteCloudProperty(CloudProperty cloudProperty);

  List<CloudProperty> getCloudProperties();

  CloudCredential createCloudCredential(CloudCredential cloudCredential);

  CloudCredential updateCloudCredential(CloudCredential cloudCredential);

  List<CloudCredential> getCloudCredentials();

  Image updateImage(Image image);

  Image getImage(Long cloudId, String name);

  Image getImage(Long cloudId, String name, long timeout);

  OperatingSystem updateOperatingSystem(OperatingSystem operatingSystem);

  OperatingSystem getOperatingSystem(Long id, long timeout);

  Location getLocation(Long cloudId, String name);

  Location getLocation(Long cloudId, String name, long timeout);

  Hardware getHardware(Long cloudId, String name);

  Hardware getHardware(Long cloudId, String name, long timeout);

  Application createApplication(Application application);

  Application updateApplication(Application application);

  void deleteApplication(Application application);

  List<Application> getApplications();

  ApplicationInstance createApplicationInstance(ApplicationInstance applicationInstance);

  void deleteApplicationInstance(ApplicationInstance applicationInstance);

  List<ApplicationInstance> getApplicationInstances();

  VirtualMachineTemplate createVirtualMachine(VirtualMachineTemplate virtualMachineTemplate);

  void deleteVirtualMachine(VirtualMachineTemplate virtualMachineTemplate);

  List<VirtualMachineTemplate> getVirtualMachines();

  VirtualMachine createVirtualMachineInstance(VirtualMachine virtualMachine);

  void deleteVirtualMachineInstance(VirtualMachine virtualMachine);

  List<VirtualMachine> getVirtualMachineInstances();

  boolean isVirtualMachineInstanceRunning(VirtualMachine vm, long timeout);

  LifecycleComponent createLifecycleComponent(LifecycleComponent lifecycleComponent);

  void deleteLifecycleComponent(LifecycleComponent lifecycleComponent);

  List<LifecycleComponent> getLifecycleComponents();

  ApplicationComponent createApplicationComponent(ApplicationComponent applicationComponent);

  void deleteApplicationComponent(ApplicationComponent applicationComponent);

  List<ApplicationComponent> getApplicationComponents();

  Instance createApplicationComponentInstance(Instance instance);

  void deleteApplicationComponentInstance(Instance instance);

  List<Instance> getApplicationComponentInstances();

  boolean isApplicationComponentInstanceRunning(Instance vm, long timeout);

  PortProvided createPortProvided(PortProvided portProvided);

  void deletePortProvided(PortProvided portProvided);

  List<PortProvided> getPortsProvided();

  PortRequired createPortRequired(PortRequired portRequired);

  void deletePortRequired(PortRequired portRequired);

  List<PortRequired> getPortsRequired();

  Communication createCommunication(Communication communication);

  void deleteCommunication(Communication communication);

  List<Communication> getCommunications();
}
