/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.communication.colosseum;

import de.uniulm.omi.cloudiator.colosseum.client.Client;
import de.uniulm.omi.cloudiator.colosseum.client.entities.*;
import de.uniulm.omi.cloudiator.colosseum.client.entities.Application;
import de.uniulm.omi.cloudiator.colosseum.client.entities.ApplicationComponent;
import de.uniulm.omi.cloudiator.colosseum.client.entities.ApplicationInstance;
import de.uniulm.omi.cloudiator.colosseum.client.entities.Communication;
import de.uniulm.omi.cloudiator.colosseum.client.entities.LifecycleComponent;
import de.uniulm.omi.cloudiator.colosseum.client.entities.VirtualMachine;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ColosseumClientApi implements ColosseumApi {

  /*
   *  TODO Colosseum Client does not throw exceptions while deleting nonexistent application etc.
   *  Because of that execution process will not be interrupted.
   *  There are possible solutions:
   *  - enrich Colosseum Client with this functionality
   *  - create interceptor analyzing HTTP response codes
   *  This remark deals with others eu.paasage.upperware.adapter.colosseum.* 'Client' (not REST) APIs as well.
   */

  private Client client;

  @Override
  public Application createApplication(@NonNull Application application) {
    return client.controller(Application.class)
      .create(application);
  }

  @Override
  public Application updateApplication(@NonNull Application application) {
    return client.controller(Application.class)
      .update(application);
  }

  @Override
  public void deleteApplication(@NonNull Application application) {
    client.controller(Application.class)
      .delete(application);
  }

  @Override
  public List<Application> getApplications() {
    return client.controller(Application.class)
      .getList();
  }

  @Override
  public ApplicationInstance createApplicationInstance(@NonNull ApplicationInstance applicationInstance) {
    return client.controller(ApplicationInstance.class)
      .create(applicationInstance);
  }

  @Override
  public void deleteApplicationInstance(@NonNull ApplicationInstance applicationInstance) {
    client.controller(ApplicationInstance.class)
      .delete(applicationInstance);
  }

  @Override
  public List<ApplicationInstance> getApplicationInstances() {
    return client.controller(ApplicationInstance.class)
      .getList();
  }

  @Override
  public VirtualMachineTemplate createVirtualMachine(@NonNull VirtualMachineTemplate virtualMachineTemplate) {
    return client.controller(VirtualMachineTemplate.class)
      .create(virtualMachineTemplate);
  }

  @Override
  public VirtualMachineTemplate updateVirtualMachine(@NonNull VirtualMachineTemplate virtualMachineTemplate) {
    return client.controller(VirtualMachineTemplate.class)
      .update(virtualMachineTemplate);
  }

  @Override
  public void deleteVirtualMachine(@NonNull VirtualMachineTemplate virtualMachineTemplate) {
    client.controller(VirtualMachineTemplate.class)
      .delete(virtualMachineTemplate);
  }

  @Override
  public List<VirtualMachineTemplate> getVirtualMachines() {
    return client.controller(VirtualMachineTemplate.class)
      .getList();
  }

  @Override
  public VirtualMachine createVirtualMachineInstance(@NonNull VirtualMachine virtualMachine) {
    return client.controller(VirtualMachine.class)
      .create(virtualMachine);
  }

  @Override
  public void deleteVirtualMachineInstance(@NonNull VirtualMachine virtualMachine) {
    client.controller(VirtualMachine.class)
      .delete(virtualMachine);
  }

  @Override
  public List<VirtualMachine> getVirtualMachineInstances() {
    return client.controller(VirtualMachine.class)
      .getList();
  }

  @Override
  public LifecycleComponent createLifecycleComponent(@NonNull LifecycleComponent lifecycleComponent) {
    return client.controller(LifecycleComponent.class)
      .create(lifecycleComponent);
  }

  @Override
  public LifecycleComponent updateLifecycleComponent(@NonNull LifecycleComponent lifecycleComponent) {
    return client.controller(LifecycleComponent.class)
      .update(lifecycleComponent);
  }

  @Override
  public void deleteLifecycleComponent(@NonNull LifecycleComponent lifecycleComponent) {
    client.controller(LifecycleComponent.class)
      .delete(lifecycleComponent);
  }

  @Override
  public List<LifecycleComponent> getLifecycleComponents() {
    return client.controller(LifecycleComponent.class)
      .getList();
  }

  @Override
  public ApplicationComponent createApplicationComponent(@NonNull ApplicationComponent applicationComponent) {
    return client.controller(ApplicationComponent.class)
      .create(applicationComponent);
  }

  @Override
  public ApplicationComponent updateApplicationComponent(@NonNull ApplicationComponent applicationComponent) {
    return client.controller(ApplicationComponent.class)
      .update(applicationComponent);
  }

  @Override
  public void deleteApplicationComponent(@NonNull ApplicationComponent applicationComponent) {
    client.controller(ApplicationComponent.class)
      .delete(applicationComponent);
  }

  @Override
  public List<ApplicationComponent> getApplicationComponents() {
    return client.controller(ApplicationComponent.class)
      .getList();
  }

  @Override
  public Instance createApplicationComponentInstance(@NonNull Instance instance) {
    return client.controller(Instance.class)
      .create(instance);
  }

  @Override
  public void deleteApplicationComponentInstance(@NonNull Instance instance) {
    client.controller(Instance.class)
      .delete(instance);
  }

  @Override
  public List<Instance> getApplicationComponentInstances() {
    return client.controller(Instance.class)
      .getList();
  }

  @Override
  public PortProvided createPortProvided(@NonNull PortProvided portProvided) {
    return client.controller(PortProvided.class)
      .create(portProvided);
  }

  @Override
  public PortProvided updatePortProvided(@NonNull PortProvided portProvided) {
    return client.controller(PortProvided.class)
      .update(portProvided);
  }

  @Override
  public void deletePortProvided(@NonNull PortProvided portProvided) {
    client.controller(PortProvided.class)
      .delete(portProvided);
  }

  @Override
  public List<PortProvided> getPortsProvided() {
    return client.controller(PortProvided.class)
      .getList();
  }

  @Override
  public PortRequired createPortRequired(@NonNull PortRequired portRequired) {
    return client.controller(PortRequired.class)
      .create(portRequired);
  }

  @Override
  public PortRequired updatePortRequired(@NonNull PortRequired portRequired) {
    return client.controller(PortRequired.class)
      .update(portRequired);
  }

  @Override
  public void deletePortRequired(@NonNull PortRequired portRequired) {
    client.controller(PortRequired.class)
      .delete(portRequired);
  }

  @Override
  public List<PortRequired> getPortsRequired() {
    return client.controller(PortRequired.class)
      .getList();
  }

  @Override
  public Communication createCommunication(@NonNull Communication communication) {
    return client.controller(Communication.class)
      .create(communication);
  }

  @Override
  public Communication updateCommunication(@NonNull Communication communication) {
    return client.controller(Communication.class)
      .update(communication);
  }

  @Override
  public void deleteCommunication(@NonNull Communication communication) {
    client.controller(Communication.class)
      .delete(communication);
  }

  @Override
  public List<Communication> getCommunications() {
    return client.controller(Communication.class)
      .getList();
  }
}
