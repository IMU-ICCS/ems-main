/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.converter;

import eu.paasage.camel.Application;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.*;
import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.Feature;
import eu.paasage.camel.type.*;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import static java.lang.String.format;

public class ConverterUtils {

  static final String ATTRIB_NAME = "Name";
  static final String ATTRIB_ENDPOINT = "Endpoint";
  static final String ATTRIB_DRIVER = "Driver";

  static final String APP_INST_NAME_SUFFIX = "Instance";
  static final String CLOUD_API_NAME_SUFFIX = "Api";
  static final String CLOUD_PROPERTY_NAME_SUFFIX = "Property";
  static final String CLOUD_CREDENTIAL_NAME_SUFFIX = "Credential";

  private static final String ATTRIB_LOCATION = "Location";
  private static final String ATTRIB_LOCATION_ID = "LocationId";
  private static final String ATTRIB_VM = "VM";
  private static final String ATTRIB_VM_IMAGE_ID = "VMImageId";

  static String convertToString(EObject obj) {
    if (obj == null) {
      return null;
    }
    if (obj instanceof EnumerateValue) {
      return ((EnumerateValue) obj).getName();
    }
    if (obj instanceof StringsValue) {
      return ((StringsValue) obj).getValue();
    }
    throw new IllegalArgumentException(format("Unsupported %s type. Supported types: EnumerateValue, StringsValue", obj.getClass()));
  }

  static Integer convertToInteger(EObject obj) {
    if (obj == null) {
      return null;
    }
    if (obj instanceof IntegerValue) {
      return ((IntegerValue) obj).getValue();
    }
    throw new IllegalArgumentException(format("Unsupported %s type. Supported types: IntegerValue", obj.getClass()));
  }

  static Double convertToDouble(EObject obj) {
    if (obj == null) {
      return null;
    }
    if (obj instanceof DoublePrecisionValue) {
      return ((DoublePrecisionValue) obj).getValue();
    }
    throw new IllegalArgumentException(format("Unsupported %s type. Supported types: DoublePrecisionValue", obj.getClass()));
  }

  static Float convertToFloat(EObject obj) {
    if (obj == null) {
      return null;
    }
    if (obj instanceof FloatsValue) {
      return ((FloatsValue) obj).getValue();
    }
    throw new IllegalArgumentException(format("Unsupported %s type. Supported types: FloatsValue", obj.getClass()));
  }

  static Boolean convertToBoolean(EObject obj) {
    if (obj == null) {
      return null;
    }
    if (obj instanceof BoolValue) {
      return ((BoolValue) obj).isValue();
    }
    throw new IllegalArgumentException(format("Unsupported %s type. Supported types: BoolValue", obj.getClass()));
  }

  static Application extractApplication(CamelModel model) {
    EList<Application> apps = model.getApplications();
    if (CollectionUtils.size(apps) != 1) {
      throw new IllegalArgumentException("Camel Model contains more than one application or " +
        "does not contain it at all. Number of applications should be equal to 1.");
    }
    return apps.get(0);
  }

  static Configuration extractConfiguration(InternalComponent ic) {
    EList<Configuration> configs = ic.getConfigurations();
    if (CollectionUtils.size(configs) != 1) {
      throw new IllegalArgumentException("Internal Component contains more than one configuration or " +
        "does not contain it at all. Number of configurations should be equal to 1.");
    }
    return configs.get(0);
  }

  static String extractCloudName(Feature rootFeature) {
    for (Attribute attribute : rootFeature.getAttributes()) {
      if (ATTRIB_NAME.equals(attribute.getName())) {
        return convertToString(attribute.getValue());
      }
    }
    return null;
  }

  static String extractLocation(Feature rootFeature) {
    for (Feature subFeature : rootFeature.getSubFeatures()) {
      if (ATTRIB_LOCATION.equals(subFeature.getName())) {
        for (Attribute attribute : subFeature.getAttributes()) {
          if (ATTRIB_LOCATION_ID.equals(attribute.getName())) {
            return convertToString(attribute.getValue());
          }
        }
      }
    }
    return null;
  }

  static String extractImage(Feature rootFeature) {
    for (Feature subFeature : rootFeature.getSubFeatures()) {
      if (ATTRIB_VM.equals(subFeature.getName())) {
        for (Attribute attribute : subFeature.getAttributes()) {
          if (ATTRIB_VM_IMAGE_ID.equals(attribute.getName())) {
            return convertToString(attribute.getValue());
          }
        }
      }
    }
    return null;
  }

  static VMInstance findAssociatedVmInstance(VM vm) {
    DeploymentModel model = (DeploymentModel) vm.eContainer();
    for (VMInstance vmInst : model.getVmInstances()) {
      if (vm.equals(vmInst.getType())) {
        return vmInst;
      }
    }
    return null;
  }

  static VM findAssociatedVm(InternalComponent ic) {
    DeploymentModel model = (DeploymentModel) ic.eContainer();
    for (Hosting hosting : model.getHostings()) {
      if (ic.equals(hosting.getRequiredHost().eContainer())) {
        return (VM) hosting.getProvidedHost().eContainer();
      }
    }
    return null;
  }
}
