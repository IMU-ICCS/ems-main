/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.converter;

import camel.deployment.DeploymentInstanceModel;
import eu.melodic.upperware.adapter.plangenerator.model.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CamelModelConverter implements ModelConverter<DeploymentInstanceModel, ComparableModel> {

  private CloudApiConverter cloudApiConverter;
  private CloudConverter cloudConverter;
  private CloudPropertyConverter cloudPropertyConverter;
  private CloudCredentialConverter cloudCredentialConverter;

  private ApplicationConverter applicationConverter;
  private ApplicationInstanceConverter applicationInstanceConverter;

  private LifecycleComponentConverter lifecycleComponentConverter;

  private VirtualMachineConverter virtualMachineConverter;
  private VirtualMachineInstanceConverter virtualMachineInstanceConverter;

  private ApplicationComponentConverter applicationComponentConverter;
  private ApplicationComponentInstanceConverter applicationComponentInstanceConverter;

  private CommunicationConverter communicationConverter;
  private PortProvidedConverter portProvidedConverter;
  private PortRequiredConverter portRequiredConverter;

  private VirtualMachineInstanceMonitorConverter virtualMachineInstanceMonitorConverter;
  private ApplicationComponentInstanceMonitorConverter applicationComponentInstanceMonitorConverter;

  private JobConverter jobConverter;
  private ProcessesConverter processesConverter;
  private RequirementsConverter requirementsConverter;
  private ScheduleConverter scheduleConverter;

  @Override
  public ComparableModel toComparableModel(DeploymentInstanceModel deploymentModel) {
    return ComparableModel.builder()
      .cloudApis(cloudApiConverter.toComparableModel(deploymentModel))
      .clouds(cloudConverter.toComparableModel(deploymentModel))
      .cloudProperties(cloudPropertyConverter.toComparableModel(deploymentModel))
      .cloudCredentials(cloudCredentialConverter.toComparableModel(deploymentModel))
      .application(applicationConverter.toComparableModel(deploymentModel))
      .applicationInstance(applicationInstanceConverter.toComparableModel(deploymentModel))
      .lifecycleComponents(lifecycleComponentConverter.toComparableModel(deploymentModel))
      .virtualMachines(virtualMachineConverter.toComparableModel(deploymentModel))
      .virtualMachineInstances(virtualMachineInstanceConverter.toComparableModel(deploymentModel))
      .applicationComponents(applicationComponentConverter.toComparableModel(deploymentModel))
      .applicationComponentInstances(applicationComponentInstanceConverter.toComparableModel(deploymentModel))
      .communications(communicationConverter.toComparableModel(deploymentModel))
      .portsProvided(portProvidedConverter.toComparableModel(deploymentModel))
      .portsRequired(portRequiredConverter.toComparableModel(deploymentModel))
      .virtualMachineInstanceMonitors(virtualMachineInstanceMonitorConverter.toComparableModel(deploymentModel))
      .applicationComponentInstanceMonitors(applicationComponentInstanceMonitorConverter.toComparableModel(deploymentModel))
            //TODO
      .adapterJob(jobConverter.toComparableModel(deploymentModel))
      .adapterSchedule(scheduleConverter.toComparableModel(deploymentModel))
      .adapterRequirements(requirementsConverter.toComparableModel(deploymentModel))
      .adapterProcesses(processesConverter.toComparableModel(deploymentModel))
      .build();
  }
}
