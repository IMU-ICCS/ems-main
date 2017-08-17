/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class CloudCredential implements Data{

  private String name;
  private String cloudName;

  private String login;
  private String password;
  private Long tenant;

  public String toString() {
    return "CloudCredential(name=" + this.getName() + ", cloudName=" + this.getCloudName()
      + ", login=***, password=***, tenant=***)";
  }
}
