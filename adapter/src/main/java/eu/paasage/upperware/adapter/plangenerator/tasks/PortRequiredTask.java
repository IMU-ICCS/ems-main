/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.plangenerator.tasks;

import eu.paasage.upperware.adapter.plangenerator.model.PortRequired;
import lombok.ToString;

@ToString(callSuper = true)
public class PortRequiredTask extends ConfigurationTask<PortRequired> {
  
  public PortRequiredTask(Type type, PortRequired data) {
    super(type, data);
  }
}
