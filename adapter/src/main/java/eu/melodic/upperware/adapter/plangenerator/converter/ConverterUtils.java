/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.converter;

import camel.core.Application;
import camel.core.CamelModel;
import camel.deployment.*;
import com.google.common.collect.Iterables;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

public class ConverterUtils {

  static final String APP_INST_NAME_SUFFIX = "Instance";

  static Application extractApplication(CamelModel model) {
    Application application = model.getApplication();
    if (application == null) {
      throw new IllegalArgumentException("Camel Model contains more than one application or " +
              "does not contain it at all. Number of applications should be equal to 1.");
    }
    return application;
  }

  static ScriptConfiguration extractConfiguration(SoftwareComponent sc) {
    EList<Configuration> configurations = sc.getConfigurations();
    if (CollectionUtils.size(configurations) != 1) {
      throw new IllegalArgumentException("Software Component contains more than one configuration or " +
              "does not contain it at all. Number of configurations should be equal to 1.");
    }

    Configuration configuration = configurations.get(0);

    if (!(configuration instanceof ScriptConfiguration)){
      throw new IllegalArgumentException("Software Component contains configuration which is not instance of ScriptConfiguration");
    }
    return (ScriptConfiguration) configuration;
  }

  static VM findAssociatedVm(SoftwareComponent sc) {
    DeploymentTypeModel deploymentModel = (DeploymentTypeModel) sc.eContainer();
    for (Hosting hosting : deploymentModel.getHostings()) {
      for (RequiredHost requiredHost : hosting.getRequiredHosts()) {
        if (sc.equals(requiredHost.eContainer())) {
          return (VM) hosting.getProvidedHost().eContainer();
        }
      }
    }
    return null;
   }

  static VMInstance findAssociatedVmInstance(VM vm) {
    CamelModel camelModel = (CamelModel) vm.eContainer().eContainer();

    DeploymentModel lastDeploymentModel = Iterables.getLast(camelModel.getDeploymentModels());
    if (lastDeploymentModel instanceof DeploymentInstanceModel) {
      DeploymentInstanceModel dim = (DeploymentInstanceModel) lastDeploymentModel;
      for (VMInstance vmInstance : dim.getVmInstances()) {
        if (vm.equals(vmInstance.getType())){
          return vmInstance;
        }
      }
    }
    return null;
  }

  static SoftwareComponentInstance findSoftwareComponentInstance(HostingInstance hostingInstance) {
    EList<RequiredHostInstance> requiredHostInstances = hostingInstance.getRequiredHostInstances();

    if (CollectionUtils.isEmpty(requiredHostInstances)) {
      throw new IllegalArgumentException("There is no RequiredHostInstance in " + hostingInstance.getName());
    }

    if (requiredHostInstances.size() > 1) {
      throw new IllegalArgumentException("There is " + requiredHostInstances.size() + " RequiredHostInstances in " + hostingInstance.getName());
    }
    return (SoftwareComponentInstance) requiredHostInstances.get(0).eContainer();
  }

  static VMInstance findVMInstance(HostingInstance hostingInstance) {
    return (VMInstance) hostingInstance.getProvidedHostInstance().eContainer();
  }

  static SoftwareComponent findSoftwareComponent(Hosting hosting) {
    EList<RequiredHost> requiredHosts = hosting.getRequiredHosts();

    if (CollectionUtils.isEmpty(requiredHosts)) {
      throw new IllegalArgumentException("There is no RequiredHost in " + hosting.getName());
    }

    if (requiredHosts.size() > 1) {
      throw new IllegalArgumentException("There is " + requiredHosts.size() + " RequiredHosts in " + hosting.getName());
    }
    return (SoftwareComponent) requiredHosts.get(0).eContainer();
  }

  static VM findVM(Hosting hosting) {
    return (VM) hosting.getProvidedHost().eContainer();
  }

  public static DeploymentTypeModel findDeploymentTypeModel(DeploymentInstanceModel deploymentInstanceModel) {
    CamelModel camelModel = (CamelModel) deploymentInstanceModel.eContainer();
    EList<DeploymentModel> deploymentModels = camelModel.getDeploymentModels();
    if (CollectionUtils.isNotEmpty(deploymentModels)) {
      DeploymentModel deploymentModel = deploymentModels.get(0);
      if (deploymentModel instanceof DeploymentTypeModel) {
        return (DeploymentTypeModel) deploymentModel;
      }
      throw new IllegalArgumentException("First DeploymentModel is not instance of DeploymentTypeModel");
    }
    throw new IllegalArgumentException("There is no deployment models defined");
  }


  static <T> T getAncesstor(EObject eObject, Class<T> clazz) {
    if (eObject == null) {
      return null;
    }

    if (clazz.isInstance(eObject)) {
      return clazz.cast(eObject);
    }

    return getAncesstor(eObject.eContainer(), clazz);
  }

  public static String getJobName(DeploymentInstanceModel deploymentInstanceModel){
    return getDeploymentTypeName(deploymentInstanceModel) + "_JOB";
  }

  public static String getScheduleName(DeploymentInstanceModel deploymentInstanceModel) {
    return getDeploymentTypeName(deploymentInstanceModel) + "_SCHEDULE";
  }

  private static String getDeploymentTypeName(DeploymentInstanceModel deploymentInstanceModel) {
    return deploymentInstanceModel.getType().getName();
  }

}
