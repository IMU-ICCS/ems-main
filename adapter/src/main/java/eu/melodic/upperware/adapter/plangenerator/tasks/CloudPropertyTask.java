/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.tasks;

import eu.melodic.upperware.adapter.plangenerator.model.CloudProperty;
import lombok.ToString;

@Deprecated
@ToString(callSuper = true)
public class CloudPropertyTask extends ConfigurationTask<CloudProperty> {

  public CloudPropertyTask(Type type, CloudProperty data) {
    super(type, data);
  }
}
