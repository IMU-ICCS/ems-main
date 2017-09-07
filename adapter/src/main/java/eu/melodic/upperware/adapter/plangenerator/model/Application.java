/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Application implements Data{

  private String name;
  private String version;
  private String description;
  private String owner;

  @Setter
  private String oldName;
}
