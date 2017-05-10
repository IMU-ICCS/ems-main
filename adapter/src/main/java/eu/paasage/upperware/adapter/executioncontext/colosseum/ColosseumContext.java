/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.executioncontext.colosseum;

import com.google.common.collect.Lists;
import de.uniulm.omi.cloudiator.colosseum.client.entities.*;
import eu.paasage.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.paasage.upperware.adapter.communication.colosseum.ColosseumConfigApi;
import eu.paasage.upperware.adapter.executioncontext.Operations;
import lombok.Getter;
import lombok.NonNull;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@Service
public class ColosseumContext implements Operations {

  private ColosseumApi api;
  private ColosseumConfigApi configApi;

  private final List<Api> apis = synchronizedList();
  private final List<Cloud> clouds = synchronizedList();
  private final List<CloudProperty> cloudProperties = synchronizedList();
  private final List<CloudCredential> cloudCredentials = synchronizedList();

  private final List<Application> applications = synchronizedList();
  private final List<ApplicationInstance> applicationInstances = synchronizedList();

  private final List<VirtualMachineTemplate> virtualMachines = synchronizedList();
  private final List<VirtualMachine> virtualMachineInstances = synchronizedList();

  private final List<LifecycleComponent> lifecycleComponents = synchronizedList();

  private final List<ApplicationComponent> applicationComponents = synchronizedList();
  private final List<Instance> applicationComponentInstances = synchronizedList();

  private final List<PortProvided> portsProvided = synchronizedList();
  private final List<PortRequired> portsRequired = synchronizedList();
  private final List<Communication> communications = synchronizedList();

  @Getter
  private boolean loaded;

  @Autowired
  public ColosseumContext(ColosseumApi api, ColosseumConfigApi configApi) {
    this.api = api;
    this.configApi = configApi;
  }

  public void addApi(Api api) {
    apis.add(api);
  }

  public void addCloud(Cloud cloud) {
    clouds.add(cloud);
  }

  public Optional<Cloud> getCloud(@NonNull String name) {
    synchronized (clouds) {
      Supplier<Stream<Cloud>> $clouds = () -> clouds.stream().filter(
        $cloud -> name.equals($cloud.getName()));
      if ($clouds.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are " +
          "more then one cloud with the same name %s", name));
      }
      return $clouds.get().findAny();
    }
  }

  public void addCloudProperty(@NonNull CloudProperty cloudProperty) {
    cloudProperties.add(cloudProperty);
  }

  public void addCloudCredential(@NonNull CloudCredential cloudCredential) {
    cloudCredentials.add(cloudCredential);
  }

  public void addApplication(@NonNull Application application) {
    applications.add(application);
  }

  public Optional<Application> getApplication(@NonNull String name) {
    synchronized (applications) {
      Supplier<Stream<Application>> $applications = () -> applications.stream().filter(
        application -> name.equals(application.getName()));
      if ($applications.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are " +
          "more then one application with the same name %s", name));
      }
      return $applications.get().findAny();
    }
  }

  public void deleteApplication(@NonNull Application application) {
    applications.remove(application);
  }

  public void addApplicationInstance(@NonNull ApplicationInstance applicationInstance) {
    applicationInstances.add(applicationInstance);
  }

  public Optional<ApplicationInstance> getApplicationInstance(@NonNull String appName) {
    Optional<Application> application = getApplication(appName);
    if (!application.isPresent()) {
      return Optional.empty();
    }
    Long appId = application.get().getId();
    checkNotNull(appId);
    synchronized (applicationInstances) {
      Supplier<Stream<ApplicationInstance>> $applicationInstances = () -> applicationInstances.stream().filter(
        $applicationInstance -> appId.equals($applicationInstance.getApplication()));
      if ($applicationInstances.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are " +
          "more then one application instance with the same application id %s", appId));
      }
      return $applicationInstances.get().findAny();
    }
  }

  public void deleteApplicationInstance(@NonNull ApplicationInstance applicationInstance) {
    applicationInstances.remove(applicationInstance);
  }

  public void addVirtualMachine(@NonNull VirtualMachineTemplate virtualMachine) {
    virtualMachines.add(virtualMachine);
  }

  public Optional<VirtualMachineTemplate> getVirtualMachine(@NonNull Long cloudId, @NonNull Long locationId,
                                                            @NonNull Long hardwareId, @NonNull Long imageId) {
    synchronized (virtualMachines) {
      Supplier<Stream<VirtualMachineTemplate>> $virtualMachines = () -> virtualMachines.stream().filter(
        $virtualMachine -> cloudId.equals($virtualMachine.getCloud()) && locationId.equals($virtualMachine.getLocation())
          && hardwareId.equals($virtualMachine.getHardware()) && imageId.equals($virtualMachine.getImage()));
      if ($virtualMachines.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are more then one virtual machine with the " +
          "same params (cloudId=%s, locationId=%s, hardwareId=%s, imageId=%s)", cloudId, locationId, hardwareId, imageId));
      }
      return $virtualMachines.get().findAny();
    }
  }

  public void deleteVirtualMachine(@NonNull VirtualMachineTemplate virtualMachine) {
    virtualMachines.remove(virtualMachine);
  }

  public void addVirtualMachineInstance(@NonNull VirtualMachine virtualMachineInstance) {
    virtualMachineInstances.add(virtualMachineInstance);
  }

  public Optional<VirtualMachine> getVirtualMachineInstance(@NonNull String name) {
    synchronized (virtualMachineInstances) {
      Supplier<Stream<VirtualMachine>> $virtualMachineInstances = () -> virtualMachineInstances.stream().filter(
        $virtualMachineInstance -> name.equals($virtualMachineInstance.getName()));
      if ($virtualMachineInstances.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are " +
          "more then one virtual machine instance with the same name %s", name));
      }
      return $virtualMachineInstances.get().findAny();
    }
  }

  public void deleteVirtualMachineInstance(@NonNull VirtualMachine virtualMachineInstance) {
    virtualMachineInstances.remove(virtualMachineInstance);
  }

  public void addLifecycleComponent(@NonNull LifecycleComponent lifecycleComponent) {
    lifecycleComponents.add(lifecycleComponent);
  }

  public Optional<LifecycleComponent> getLifecycleComponent(@NonNull String name) {
    synchronized (lifecycleComponents) {
      Supplier<Stream<LifecycleComponent>> $lifecycleComponents = () -> lifecycleComponents.stream().filter(
        $lifecycleComponent -> name.equals($lifecycleComponent.getName()));
      if ($lifecycleComponents.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are " +
          "more then one lifecycle component with the same name %s", name));
      }
      return $lifecycleComponents.get().findAny();
    }
  }

  public void deleteLifecycleComponent(@NonNull LifecycleComponent lifecycleComponent) {
    lifecycleComponents.remove(lifecycleComponent);
  }

  public void addApplicationComponent(@NonNull ApplicationComponent applicationComponent) {
    applicationComponents.add(applicationComponent);
  }

  public Optional<ApplicationComponent> getApplicationComponent(@NonNull String appName, @NonNull Long lcId, @NonNull Long vmId) {
    Optional<Application> application = getApplication(appName);
    if (!application.isPresent()) {
      return Optional.empty();
    }
    Long appId = application.get().getId();
    checkNotNull(appId);
    synchronized (applicationComponents) {
      Supplier<Stream<ApplicationComponent>> $applicationComponents = () -> applicationComponents.stream().filter(
        $applicationComponent -> appId.equals($applicationComponent.getApplication())
          && lcId.equals($applicationComponent.getComponent()) && vmId.equals($applicationComponent.getVirtualMachineTemplate()));
      if ($applicationComponents.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are more then one application component " +
          "with the same params (appName=%s, lcId=%s, vmId=%s)", appName, lcId, vmId));
      }
      return $applicationComponents.get().findAny();
    }
  }

  public void deleteApplicationComponent(@NonNull ApplicationComponent applicationComponent) {
    applicationComponents.remove(applicationComponent);
  }

  public void addApplicationComponentInstance(@NonNull Instance applicationComponentInstance) {
    applicationComponentInstances.add(applicationComponentInstance);
  }

  public void deleteApplicationComponentInstance(@NonNull Instance applicationComponentInstance) {
    applicationComponentInstances.remove(applicationComponentInstance);
  }

  public void addPortProvided(@NonNull PortProvided portProvided) {
    portsProvided.add(portProvided);
  }

  public Optional<PortProvided> getPortProvided(@NonNull String name) {
    synchronized (portsProvided) {
      Supplier<Stream<PortProvided>> $portsProvided = () -> portsProvided.stream().filter(
        $portProvided -> name.equals($portProvided.getName()));
      if ($portsProvided.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are " +
          "more then one port provided with the same name %s", name));
      }
      return $portsProvided.get().findAny();
    }
  }

  public void deletePortProvided(@NonNull PortProvided portProvided) {
    portsProvided.remove(portProvided);
  }

  public void addPortRequired(@NonNull PortRequired portRequired) {
    portsRequired.add(portRequired);
  }

  public Optional<PortRequired> getPortRequired(@NonNull String name) {
    synchronized (portsRequired) {
      Supplier<Stream<PortRequired>> $portsRequired = () -> portsRequired.stream().filter(
        $portRequired -> name.equals($portRequired.getName()));
      if ($portsRequired.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are " +
          "more then one port required with the same name %s", name));
      }
      return $portsRequired.get().findAny();
    }
  }

  public void deletePortRequired(@NonNull PortRequired portRequired) {
    portsRequired.remove(portRequired);
  }

  public void addCommunication(@NonNull Communication communication) {
    communications.add(communication);
  }

  public void deleteCommunication(@NonNull Communication communication) {
    communications.remove(communication);
  }

  @Override
  @Synchronized
  public void refreshContext() {
    apis.clear();
    apis.addAll(configApi.getApis());

    clouds.clear();
    clouds.addAll(configApi.getClouds());

    cloudProperties.clear();
    cloudProperties.addAll(configApi.getCloudProperties());

    cloudCredentials.clear();
    cloudCredentials.addAll(configApi.getCloudCredentials());

    applications.clear();
    applications.addAll(api.getApplications());

    applicationInstances.clear();
    applicationInstances.addAll(api.getApplicationInstances());

    virtualMachines.clear();
    virtualMachines.addAll(api.getVirtualMachines());

    virtualMachineInstances.clear();
    virtualMachineInstances.addAll(api.getVirtualMachineInstances());

    lifecycleComponents.clear();
    lifecycleComponents.addAll(api.getLifecycleComponents());

    applicationComponents.clear();
    applicationComponents.addAll(api.getApplicationComponents());

    applicationComponentInstances.clear();
    applicationComponentInstances.addAll(api.getApplicationComponentInstances());

    portsProvided.clear();
    portsProvided.addAll(api.getPortsProvided());

    portsRequired.clear();
    portsRequired.addAll(api.getPortsRequired());

    communications.clear();
    communications.addAll(api.getCommunications());

    loaded = true;
  }

  private <E> List<E> synchronizedList() {
    return Collections.synchronizedList(Lists.newLinkedList());
  }
}
