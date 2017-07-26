/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.communication.colosseum;

import de.uniulm.omi.cloudiator.colosseum.client.entities.*;

import java.util.List;

public interface ColosseumApi {

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
