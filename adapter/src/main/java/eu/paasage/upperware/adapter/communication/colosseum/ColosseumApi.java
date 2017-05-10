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

  Application createApplication(Application application);

  Application updateApplication(Application application);

  void deleteApplication(Application application);

  List<Application> getApplications();

  ApplicationInstance createApplicationInstance(ApplicationInstance applicationInstance);

  void deleteApplicationInstance(ApplicationInstance applicationInstance);

  List<ApplicationInstance> getApplicationInstances();

  VirtualMachineTemplate createVirtualMachine(VirtualMachineTemplate virtualMachineTemplate);

  VirtualMachineTemplate updateVirtualMachine(VirtualMachineTemplate virtualMachineTemplate);

  void deleteVirtualMachine(VirtualMachineTemplate virtualMachineTemplate);

  List<VirtualMachineTemplate> getVirtualMachines();

  VirtualMachine createVirtualMachineInstance(VirtualMachine virtualMachine);

  void deleteVirtualMachineInstance(VirtualMachine virtualMachine);

  List<VirtualMachine> getVirtualMachineInstances();

  LifecycleComponent createLifecycleComponent(LifecycleComponent lifecycleComponent);

  LifecycleComponent updateLifecycleComponent(LifecycleComponent lifecycleComponent);

  void deleteLifecycleComponent(LifecycleComponent lifecycleComponent);

  List<LifecycleComponent> getLifecycleComponents();

  ApplicationComponent createApplicationComponent(ApplicationComponent applicationComponent);

  ApplicationComponent updateApplicationComponent(ApplicationComponent applicationComponent);

  void deleteApplicationComponent(ApplicationComponent applicationComponent);

  List<ApplicationComponent> getApplicationComponents();

  Instance createApplicationComponentInstance(Instance instance);

  void deleteApplicationComponentInstance(Instance instance);

  List<Instance> getApplicationComponentInstances();

  PortProvided createPortProvided(PortProvided portProvided);

  PortProvided updatePortProvided(PortProvided portProvided);

  void deletePortProvided(PortProvided portProvided);

  List<PortProvided> getPortsProvided();

  PortRequired createPortRequired(PortRequired portRequired);

  PortRequired updatePortRequired(PortRequired portRequired);

  void deletePortRequired(PortRequired portRequired);

  List<PortRequired> getPortsRequired();

  Communication createCommunication(Communication communication);

  Communication updateCommunication(Communication communication);

  void deleteCommunication(Communication communication);

  List<Communication> getCommunications();
}
