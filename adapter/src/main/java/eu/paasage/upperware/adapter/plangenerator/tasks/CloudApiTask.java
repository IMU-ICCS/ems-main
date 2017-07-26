/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.plangenerator.tasks;

import eu.paasage.upperware.adapter.plangenerator.model.CloudApi;
import lombok.ToString;

@ToString(callSuper = true)
public class CloudApiTask extends ConfigurationTask<CloudApi> {

  public CloudApiTask(Type type, CloudApi data) {
    super(type, data);
  }
}
