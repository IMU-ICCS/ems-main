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

public class ConverterUtils {

  static String getJobName(DeploymentInstanceModel deploymentInstanceModel){
    return getDeploymentTypeName(deploymentInstanceModel) + "_JOB";
  }

  static String getScheduleName(DeploymentInstanceModel deploymentInstanceModel) {
    return getDeploymentTypeName(deploymentInstanceModel) + "_SCHEDULE";
  }

  private static String getDeploymentTypeName(DeploymentInstanceModel deploymentInstanceModel) {
    return deploymentInstanceModel.getType().getName();
  }

}
