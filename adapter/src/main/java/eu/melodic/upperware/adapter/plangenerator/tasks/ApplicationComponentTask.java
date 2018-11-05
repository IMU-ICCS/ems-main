/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.tasks;

import eu.melodic.upperware.adapter.plangenerator.model.ApplicationComponent;
import lombok.ToString;

@Deprecated
@ToString(callSuper = true)
public class ApplicationComponentTask extends ConfigurationTask<ApplicationComponent> {

  public ApplicationComponentTask(Type type, ApplicationComponent data) {
    super(type, data);
  }
}
