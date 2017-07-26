/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class ComparableModel {

  private Collection<CloudApi> cloudApis;
  private Collection<Cloud> clouds;
  private Collection<CloudProperty> cloudProperties;
  private Collection<CloudCredential> cloudCredentials;

  private Application application;
  private ApplicationInstance applicationInstance;

  private Collection<LifecycleComponent> lifecycleComponents;

  private Collection<VirtualMachine> virtualMachines;
  private Collection<VirtualMachineInstance> virtualMachineInstances;

  private Collection<ApplicationComponent> applicationComponents;
  private Collection<ApplicationComponentInstance> applicationComponentInstances;

  private Collection<Communication> communications;
  private Collection<PortProvided> portsProvided;
  private Collection<PortRequired> portsRequired;

  private Collection<VirtualMachineInstanceMonitor> virtualMachineInstanceMonitors;
  private Collection<ApplicationComponentInstanceMonitor> applicationComponentInstanceMonitors;

}
