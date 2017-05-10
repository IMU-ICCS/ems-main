/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.plangenerator.tasks;

import eu.paasage.upperware.adapter.plangenerator.model.PortProvided;

public class PortProvidedTask extends ConfigurationTask<PortProvided> {

  public PortProvidedTask(String name, Type type, PortProvided data) {
    super(name, type, data);
  }
}
