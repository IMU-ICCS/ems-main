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

import static de.uniulm.omi.cloudiator.colosseum.client.entities.enums.RemoteState.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

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
  public Api createApi(@NonNull Api api) {
    return client.controller(Api.class).create(api);
  }

  @Override
  public Api updateApi(@NonNull Api api) {
    return client.controller(Api.class).update(api);
  }

  @Override
  public List<Api> getApis() {
    return client.controller(Api.class).getList();
  }

  @Override
  public Cloud createCloud(@NonNull Cloud cloud) {
    return client.controller(Cloud.class).create(cloud);
  }

  @Override
  public Cloud updateCloud(@NonNull Cloud cloud) {
    return client.controller(Cloud.class).update(cloud);
  }

  @Override
  public List<Cloud> getClouds() {
    return client.controller(Cloud.class).getList();
  }

  @Override
  public CloudProperty createCloudProperty(@NonNull CloudProperty cloudProperty) {
    return client.controller(CloudProperty.class).create(cloudProperty);
  }

  @Override
  public CloudProperty updateCloudProperty(@NonNull CloudProperty cloudProperty) {
    return client.controller(CloudProperty.class).update(cloudProperty);
  }

  @Override
  public void deleteCloudProperty(CloudProperty cloudProperty) {
    client.controller(CloudProperty.class).delete(cloudProperty);
  }

  @Override
  public List<CloudProperty> getCloudProperties() {
    return client.controller(CloudProperty.class).getList();
  }

  @Override
  public CloudCredential createCloudCredential(@NonNull CloudCredential cloudCredential) {
    return client.controller(CloudCredential.class).create(cloudCredential);
  }

  @Override
  public CloudCredential updateCloudCredential(@NonNull CloudCredential cloudCredential) {
    return client.controller(CloudCredential.class).update(cloudCredential);
  }

  @Override
  public List<CloudCredential> getCloudCredentials() {
    return client.controller(CloudCredential.class).getList();
  }

  @Override
  public Image updateImage(@NonNull Image image) {
    return client.controller(Image.class).update(image);
  }

  @Override
  public Image getImage(Long cloudId, String name) {
    return client.controller(Image.class)
      .getSingle(image -> cloudId.equals(image.getCloud()) && name.equals(image.getSwordId()))
      .orNull();
  }

  @Override
  public Image getImage(Long cloudId, String name, long timeout) {
    return client.controller(Image.class)
      .waitAndGetSingle(image -> cloudId.equals(image.getCloud())
        && name.equals(image.getSwordId()), timeout, MILLISECONDS)
      .orNull();
  }

  @Override
  public OperatingSystem updateOperatingSystem(@NonNull OperatingSystem os) {
    return client.controller(OperatingSystem.class).update(os);
  }

  @Override
  public OperatingSystem getOperatingSystem(Long id, long timeout) {
    return client.controller(OperatingSystem.class)
      .waitAndGetSingle(os -> id.equals(os.getId()), timeout, MILLISECONDS)
      .orNull();
  }

  @Override
  public Location getLocation(Long cloudId, String name) {
    return client.controller(Location.class)
      .getSingle(location -> cloudId.equals(location.getCloud()) && name.equals(location.getName()))
      .orNull();
  }

  @Override
  public Location getLocation(Long cloudId, String name, long timeout) {
    return client.controller(Location.class)
      .waitAndGetSingle(location -> cloudId.equals(location.getCloud())
        && name.equals(location.getName()), timeout, MILLISECONDS)
      .orNull();
  }

  @Override
  public Hardware getHardware(Long cloudId, String name) {
    return client.controller(Hardware.class)
      .getSingle(hardware -> cloudId.equals(hardware.getCloud()) && name.equals(hardware.getName()))
      .orNull();
  }

  @Override
  public Hardware getHardware(Long cloudId, String name, long timeout) {
    return client.controller(Hardware.class)
      .waitAndGetSingle(hardware -> cloudId.equals(hardware.getCloud())
        && name.equals(hardware.getName()), timeout, MILLISECONDS)
      .orNull();
  }

  @Override
  public Application createApplication(@NonNull Application app) {
    return client.controller(Application.class).create(app);
  }

  @Override
  public Application updateApplication(@NonNull Application app) {
    return client.controller(Application.class).update(app);
  }

  @Override
  public void deleteApplication(@NonNull Application app) {
    client.controller(Application.class).delete(app);
  }

  @Override
  public List<Application> getApplications() {
    return client.controller(Application.class).getList();
  }

  @Override
  public ApplicationInstance createApplicationInstance(@NonNull ApplicationInstance appInst) {
    return client.controller(ApplicationInstance.class).create(appInst);
  }

  @Override
  public void deleteApplicationInstance(@NonNull ApplicationInstance appInst) {
    client.controller(ApplicationInstance.class).delete(appInst);
  }

  @Override
  public List<ApplicationInstance> getApplicationInstances() {
    return client.controller(ApplicationInstance.class).getList();
  }

  @Override
  public VirtualMachineTemplate createVirtualMachine(@NonNull VirtualMachineTemplate vm) {
    return client.controller(VirtualMachineTemplate.class).create(vm);
  }

  @Override
  public void deleteVirtualMachine(@NonNull VirtualMachineTemplate vm) {
    client.controller(VirtualMachineTemplate.class).delete(vm);
  }

  @Override
  public List<VirtualMachineTemplate> getVirtualMachines() {
    return client.controller(VirtualMachineTemplate.class).getList();
  }

  @Override
  public VirtualMachine createVirtualMachineInstance(@NonNull VirtualMachine vmInst) {
    return client.controller(VirtualMachine.class).create(vmInst);
  }

  @Override
  public void deleteVirtualMachineInstance(@NonNull VirtualMachine vmInst) {
    client.controller(VirtualMachine.class).delete(vmInst);
  }

  @Override
  public List<VirtualMachine> getVirtualMachineInstances() {
    return client.controller(VirtualMachine.class).getList();
  }

  @Override
  public boolean isVirtualMachineInstanceRunning(VirtualMachine vm, long timeout) {
    return client.controller(VirtualMachine.class)
      .waitAndGetSingle($vm -> vm.getId().equals($vm.getId())
        && OK.equals($vm.getRemoteState()), timeout, MILLISECONDS)
      .isPresent();
  }

  @Override
  public LifecycleComponent createLifecycleComponent(@NonNull LifecycleComponent lc) {
    return client.controller(LifecycleComponent.class).create(lc);
  }

  @Override
  public void deleteLifecycleComponent(@NonNull LifecycleComponent lc) {
    client.controller(LifecycleComponent.class).delete(lc);
  }

  @Override
  public List<LifecycleComponent> getLifecycleComponents() {
    return client.controller(LifecycleComponent.class).getList();
  }

  @Override
  public ApplicationComponent createApplicationComponent(@NonNull ApplicationComponent ac) {
    return client.controller(ApplicationComponent.class).create(ac);
  }

  @Override
  public void deleteApplicationComponent(@NonNull ApplicationComponent ac) {
    client.controller(ApplicationComponent.class).delete(ac);
  }

  @Override
  public List<ApplicationComponent> getApplicationComponents() {
    return client.controller(ApplicationComponent.class).getList();
  }

  @Override
  public Instance createApplicationComponentInstance(@NonNull Instance acInst) {
    return client.controller(Instance.class).create(acInst);
  }

  @Override
  public void deleteApplicationComponentInstance(@NonNull Instance acInst) {
    client.controller(Instance.class).delete(acInst);
  }

  @Override
  public List<Instance> getApplicationComponentInstances() {
    return client.controller(Instance.class).getList();
  }

  @Override
  public boolean isApplicationComponentInstanceRunning(Instance acInst, long timeout) {
    return client.controller(Instance.class)
      .waitAndGetSingle($acInst -> acInst.getId().equals($acInst.getId())
        && OK.equals($acInst.getRemoteState()), timeout, MILLISECONDS)
      .isPresent();
  }

  @Override
  public PortProvided createPortProvided(@NonNull PortProvided pp) {
    return client.controller(PortProvided.class).create(pp);
  }

  @Override
  public void deletePortProvided(@NonNull PortProvided pp) {
    client.controller(PortProvided.class).delete(pp);
  }

  @Override
  public List<PortProvided> getPortsProvided() {
    return client.controller(PortProvided.class).getList();
  }

  @Override
  public PortRequired createPortRequired(@NonNull PortRequired pr) {
    return client.controller(PortRequired.class).create(pr);
  }

  @Override
  public void deletePortRequired(@NonNull PortRequired pr) {
    client.controller(PortRequired.class).delete(pr);
  }

  @Override
  public List<PortRequired> getPortsRequired() {
    return client.controller(PortRequired.class).getList();
  }

  @Override
  public Communication createCommunication(@NonNull Communication comm) {
    return client.controller(Communication.class).create(comm);
  }

  @Override
  public void deleteCommunication(@NonNull Communication comm) {
    client.controller(Communication.class).delete(comm);
  }

  @Override
  public List<Communication> getCommunications() {
    return client.controller(Communication.class).getList();
  }
}
