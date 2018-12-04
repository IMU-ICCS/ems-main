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
import eu.melodic.upperware.adapter.plangenerator.model.ComparableModel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CamelModelConverter implements ModelConverter<DeploymentInstanceModel, ComparableModel> {

  private JobConverter jobConverter;
  private ProcessesConverter processesConverter;
  private RequirementsConverter requirementsConverter;
  private ScheduleConverter scheduleConverter;

  @Override
  public ComparableModel toComparableModel(DeploymentInstanceModel deploymentModel) {
    return ComparableModel.builder()
      .adapterJob(jobConverter.toComparableModel(deploymentModel))
      .adapterSchedule(scheduleConverter.toComparableModel(deploymentModel))
      .adapterRequirements(requirementsConverter.toComparableModel(deploymentModel))
      .adapterProcesses(processesConverter.toComparableModel(deploymentModel))
      .build();
  }
}
