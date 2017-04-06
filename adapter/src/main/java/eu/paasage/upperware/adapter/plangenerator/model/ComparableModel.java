/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.plangenerator.model;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class ComparableModel {

  private Collection<Application> applications;
  private Collection<ApplicationInstance> applicationInstances;

  // TODO create rest of attributes

}
