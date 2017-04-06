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

public interface ColosseumApi {

  Application createApplication(String name);

  Application updateApplication(long applicationId, String name);

  void deleteApplication(long applicationId);

  ApplicationInstance createApplicationInstance(long applicationId);

  ApplicationInstance updateApplicationInstance(long applicationId, long applicationInstanceId);

  void deleteApplicationInstance(long applicationInstanceId);

  VirtualMachineTemplate createVirtualMachine(String name, long cloudId, long locationId, long hardwareId, long imageId);

  void deleteVirtualMachine(long virtualMachineId);

  VirtualMachine createVirtualMachineInstance(String name, long cloudId, long locationId, long hardwareId, long imageId);

  void deleteVirtualMachineInstance(long virtualMachineInstanceId);

  LifecycleComponent createLifecycleComponent(String name, String preInstall, String install, String postInstall, String start, String stop);

  LifecycleComponent updateLifecycleComponent(long lifecycleComponentId, String name, String preInstall, String install, String postInstall, String start, String stop);

  void deleteLifecycleComponent(long lifecycleComponentId);

  ApplicationComponent createApplicationComponent(long applicationId, long virtualMachineId, long lifecycleComponentId);

  ApplicationComponent updateApplicationComponent(long applicationId, long applicationComponentId, long virtualMachineId, long lifecycleComponentId);

  void deleteApplicationComponent(long applicationComponentId);

  Instance createApplicationComponentInstance(long applicationInstanceId, long virtualMachineInstanceId, long applicationComponentId);

  void deleteApplicationComponentInstance(long applicationComponentInstanceId);

  PortProvided createProvidedPort(String name, long applicationComponentId, int portNumber);

  PortProvided updateProvidedPort(long portId, String name, long applicationComponentId, int portNumber);

  void deleteProvidedPort(long portId);

  PortRequired createRequiredPort(String name, long applicationComponentId, boolean isMandatory, boolean requiredPortStartCmd);

  PortRequired updateRequiredPort(long portId, String name, long applicationComponentId, boolean isMandatory, boolean requiredPortStartCmd);

  void deleteRequiredPort(long portId);

  Communication createCommunication(long providedPortId, long requiredPortId);

  Communication updateCommunication(long communicationId, long providedPortId, long requiredPortId);

  void deleteCommunication(long communicationId);

}
