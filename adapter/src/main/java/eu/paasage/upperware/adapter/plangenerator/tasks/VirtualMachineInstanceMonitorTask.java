/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.plangenerator.tasks;

import eu.paasage.upperware.adapter.plangenerator.model.VirtualMachineInstanceMonitor;
import lombok.ToString;

@ToString(callSuper = true)
public class VirtualMachineInstanceMonitorTask extends ConfigurationTask<VirtualMachineInstanceMonitor> {

  public VirtualMachineInstanceMonitorTask(Type type, VirtualMachineInstanceMonitor data) {
    super(type, data);
  }
}
