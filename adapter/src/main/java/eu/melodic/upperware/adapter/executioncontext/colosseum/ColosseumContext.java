/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.executioncontext.colosseum;

import com.google.common.collect.Lists;
import de.uniulm.omi.cloudiator.colosseum.client.entities.*;
import eu.melodic.upperware.adapter.executioncontext.ContextOperations;
import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.lang.String.format;

@Slf4j
@Service
public class ColosseumContext implements ContextOperations {

  private ColosseumApi api;

  private final List<Api> cloudApis = synchronizedList();
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

  private boolean loaded;

  @Autowired
  public ColosseumContext(ColosseumApi api) {
    this.api = api;
  }

  public void addCloudApi(@NonNull Api api) {
    cloudApis.add(api);
  }

  public Optional<Api> getCloudApi(String name) {
    synchronized (cloudApis) {
      Supplier<Stream<Api>> $cloudApis = () -> cloudApis.stream().filter(
        $api -> name.equals($api.getName())
      );
      if ($cloudApis.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are " +
          "more than one cloud api with the same name=%s", name));
      }
      return $cloudApis.get().findAny();
    }
  }

  public void addCloud(@NonNull Cloud cloud) {
    clouds.add(cloud);
  }

  public Optional<Cloud> getCloud(String name) {
    synchronized (clouds) {
      Supplier<Stream<Cloud>> $clouds = () -> clouds.stream().filter(
        $cloud -> name.equals($cloud.getName())
      );
      if ($clouds.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are " +
          "more than one cloud with the same name=%s", name));
      }
      return $clouds.get().findAny();
    }
  }

  public void addCloudProperty(@NonNull CloudProperty cloudProperty) {
    cloudProperties.add(cloudProperty);
  }

  public Optional<CloudProperty> getCloudProperty(Long cloudId, String key) {
    synchronized (cloudProperties) {
      Supplier<Stream<CloudProperty>> $cloudProperties = () -> cloudProperties.stream().filter(
        $cloudProperty -> cloudId.equals($cloudProperty.getCloud()) && key.equals($cloudProperty.getKey())
      );
      if ($cloudProperties.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are " +
          "more than one cloud property with the same params (cloudId=%s, key=%s)", cloudId, key));
      }
      return $cloudProperties.get().findAny();
    }
  }

  public void deleteCloudProperty(@NonNull CloudProperty cloudProperty) {
    cloudProperties.remove(cloudProperty);
  }

  public void addCloudCredential(@NonNull CloudCredential cloudCredential) {
    cloudCredentials.add(cloudCredential);
  }

  public Optional<CloudCredential> getCloudCredential(Long cloudId) {
    synchronized (cloudCredentials) {
      Supplier<Stream<CloudCredential>> $cloudCredentials = () -> cloudCredentials.stream().filter(
        $cloudCredential -> cloudId.equals($cloudCredential.getCloud())
      );
      if ($cloudCredentials.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are " +
          "more than one cloud credential with the same cloudId=%s", cloudId));
      }
      return $cloudCredentials.get().findAny();
    }
  }

  public void addApplication(@NonNull Application app) {
    applications.add(app);
  }

  public Optional<Application> getApplication(String name) {
    synchronized (applications) {
      Supplier<Stream<Application>> $apps = () -> applications.stream().filter(
        application -> name.equals(application.getName())
      );
      if ($apps.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are " +
          "more than one application with the same name=%s", name));
      }
      return $apps.get().findAny();
    }
  }

  public void deleteApplication(@NonNull Application app) {
    applications.remove(app);
  }

  public void addApplicationInstance(@NonNull ApplicationInstance appInst) {
    applicationInstances.add(appInst);
  }

  public Optional<ApplicationInstance> getApplicationInstance(@NonNull String appName) {
    Optional<Application> app = getApplication(appName);
    if (!app.isPresent()) {
      return Optional.empty();
    }
    Long appId = app.get().getId();
    synchronized (applicationInstances) {
      Supplier<Stream<ApplicationInstance>> $appInsts = () -> applicationInstances.stream().filter(
        $appInst -> appId.equals($appInst.getApplication())
      );
      if ($appInsts.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are " +
          "more than one application instance with the same applicationId=%s", appId));
      }
      return $appInsts.get().findAny();
    }
  }

  public void deleteApplicationInstance(@NonNull ApplicationInstance appInst) {
    applicationInstances.remove(appInst);
  }

  public void addVirtualMachine(@NonNull VirtualMachineTemplate vm) {
    virtualMachines.add(vm);
  }

  public Optional<VirtualMachineTemplate> getVirtualMachine(Long cloudId, Long locationId, Long hardwareId, Long imageId) {
    synchronized (virtualMachines) {
      Supplier<Stream<VirtualMachineTemplate>> $vms = () -> virtualMachines.stream().filter(
        $vm -> cloudId.equals($vm.getCloud()) && locationId.equals($vm.getLocation())
          && hardwareId.equals($vm.getHardware()) && imageId.equals($vm.getImage())
      );
      if ($vms.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are more than one virtual machine with the " +
          "same params (cloudId=%s, locationId=%s, hardwareId=%s, imageId=%s)", cloudId, locationId, hardwareId, imageId));
      }
      return $vms.get().findAny();
    }
  }

  public void deleteVirtualMachine(@NonNull VirtualMachineTemplate vm) {
    virtualMachines.remove(vm);
  }

  public void addVirtualMachineInstance(@NonNull VirtualMachine vmInst) {
    virtualMachineInstances.add(vmInst);
  }

  public Optional<VirtualMachine> getVirtualMachineInstance(String name) {
    synchronized (virtualMachineInstances) {
      Supplier<Stream<VirtualMachine>> $vmInsts = () -> virtualMachineInstances.stream().filter(
        $vmInst -> name.equals($vmInst.getName())
      );
      if ($vmInsts.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are " +
          "more than one virtual machine instance with the same name=%s", name));
      }
      return $vmInsts.get().findAny();
    }
  }

  public void deleteVirtualMachineInstance(@NonNull VirtualMachine vmInst) {
    virtualMachineInstances.remove(vmInst);
  }

  public void addLifecycleComponent(@NonNull LifecycleComponent lc) {
    lifecycleComponents.add(lc);
  }

  public Optional<LifecycleComponent> getLifecycleComponent(String name) {
    synchronized (lifecycleComponents) {
      Supplier<Stream<LifecycleComponent>> $lcs = () -> lifecycleComponents.stream().filter(
        $lc -> name.equals($lc.getName())
      );
      if ($lcs.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are " +
          "more than one lifecycle component with the same name=%s", name));
      }
      return $lcs.get().findAny();
    }
  }

  public void deleteLifecycleComponent(@NonNull LifecycleComponent lc) {
    lifecycleComponents.remove(lc);
  }

  public void addApplicationComponent(@NonNull ApplicationComponent ac) {
    applicationComponents.add(ac);
  }

  public Optional<ApplicationComponent> getApplicationComponent(String appName, Long lcId, Long vmId) {
    return getApplicationComponent(appName, lcId);
  }

  public Optional<ApplicationComponent> getApplicationComponent(String appName, Long lcId) {
    Optional<Application> app = getApplication(appName);
    if (!app.isPresent()) {
      return Optional.empty();
    }
    Long appId = app.get().getId();
    synchronized (applicationComponents) {
      Supplier<Stream<ApplicationComponent>> $acs = () -> applicationComponents.stream().filter(
              $ac -> appId.equals($ac.getApplication())
                      && lcId.equals($ac.getComponent())
      );
      if ($acs.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are more than one application component " +
                "with the same params (appName=%s, lcId=%s)", appName, lcId));
      }
      return $acs.get().findAny();
    }
  }

  public void deleteApplicationComponent(@NonNull ApplicationComponent ac) {
    applicationComponents.remove(ac);
  }

  public void addApplicationComponentInstance(@NonNull Instance acInst) {
    printInstances("Before adding: ", applicationComponentInstances);
    applicationComponentInstances.add(acInst);
    printInstances("After adding: ", applicationComponentInstances);
  }

  public Optional<Instance> getApplicationComponentInstance(Long acId, Long appInstId, Long vmInstId) {
    return getApplicationComponentInstance(appInstId, vmInstId);
  }

  public Optional<Instance> getApplicationComponentInstance(Long appInstId, Long vmInstId) {
    synchronized (applicationComponentInstances) {
      Supplier<Stream<Instance>> $acInsts = () -> applicationComponentInstances.stream().filter(
              $acInst ->  appInstId.equals($acInst.getApplicationInstance()) && vmInstId.equals($acInst.getVirtualMachine())
      );
      if ($acInsts.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are more than one application " +
                "component instance with the same params (appInstId=%s, vmInstId=%s)", appInstId, vmInstId));
      }
      return $acInsts.get().findAny();
    }
  }

  public void deleteApplicationComponentInstance(@NonNull Instance acInst) {
    printInstances("Before removing: ", applicationComponentInstances);
//    applicationComponentInstances.remove(acInst);
    applicationComponentInstances.removeIf(instance -> instance.getId().equals(acInst.getId()));
    printInstances("After removing: ", applicationComponentInstances);
  }

  public void addPortProvided(@NonNull PortProvided pp) {
    portsProvided.add(pp);
  }

  public Optional<PortProvided> getPortProvided(String name) {
    synchronized (portsProvided) {
      Supplier<Stream<PortProvided>> $pps = () -> portsProvided.stream().filter(
        $pp -> name.equals($pp.getName())
      );
      if ($pps.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are " +
          "more than one port provided with the same name=%s", name));
      }
      return $pps.get().findAny();
    }
  }

  public void deletePortProvided(@NonNull PortProvided pp) {
    portsProvided.remove(pp);
  }

  public void addPortRequired(@NonNull PortRequired pr) {
    portsRequired.add(pr);
  }

  public Optional<PortRequired> getPortRequired(String name) {
    synchronized (portsRequired) {
      Supplier<Stream<PortRequired>> $prs = () -> portsRequired.stream().filter(
        $pr -> name.equals($pr.getName())
      );
      if ($prs.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are " +
          "more than one port required with the same name=%s", name));
      }
      return $prs.get().findAny();
    }
  }

  public void deletePortRequired(@NonNull PortRequired pr) {
    portsRequired.remove(pr);
  }

  public void addCommunication(@NonNull Communication comm) {
    communications.add(comm);
  }

  public Optional<Communication> getCommunication(Long ppId, Long prId) {
    synchronized (communications) {
      Supplier<Stream<Communication>> $comms = () -> communications.stream().filter(
        $comm -> prId.equals($comm.getRequiredPort()) && ppId.equals($comm.getProvidedPort())
      );
      if ($comms.get().count() > 1) {
        throw new IllegalStateException(format("Ambiguous search result - there are " +
          "more than one communication with the same params (portRequiredId=%s, portProvidedId=%s)", prId, ppId));
      }
      return $comms.get().findAny();
    }
  }

  public void deleteCommunication(@NonNull Communication comm) {
    communications.remove(comm);
  }

  @Override
  @Synchronized
  public void refreshContext() {
    log.info("Refreshing Colosseum context");

    cloudApis.clear();
    cloudApis.addAll(api.getApis());

    clouds.clear();
    clouds.addAll(api.getClouds());

    cloudProperties.clear();
    cloudProperties.addAll(api.getCloudProperties());

    cloudCredentials.clear();
    cloudCredentials.addAll(api.getCloudCredentials());

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

  @Override
  public boolean isLoaded() {
    return loaded;
  }

  private <E> List<E> synchronizedList() {
    return Collections.synchronizedList(Lists.newLinkedList());
  }

    private void printInstances(String text, List<Instance> instances) {
        JSONArray array = new JSONArray();
        String result = "";
        try {
            for (Instance instance : instances) {
                array.put(createJsonRepresentation(instance));
            }
            result = array.toString(3);
        } catch (Exception e) {
            log.error("Problem with json", e);
        }
        log.info("{} Instances: {}\n{}", text, array.length(), result);
    }

    private JSONObject createJsonRepresentation(Instance instance) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("id", instance.getId());
        result.put("applicationComponent", instance.getApplicationComponent());
        result.put("applicationInstance", instance.getApplicationInstance());
        result.put("virtualMachine", instance.getVirtualMachine());
        return result;
    }
}
