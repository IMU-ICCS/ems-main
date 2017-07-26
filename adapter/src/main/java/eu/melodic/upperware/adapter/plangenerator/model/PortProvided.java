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
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class PortProvided {

  private String name;
  private String acName;

  private Integer port;

  // Fields below are redundant but necessary because we are not able to
  // easily connect ports with application components (Colosseum does not
  // share unique identifiers like 'name' for application components so
  // we have to create combined identifiers)
  // TODO: can we add 'name' attribute to application component?

  private String cloudName;
  private String appName;
  private String lcName;
  private String vmName;

  private String location;
  private String hardware;
  private String image;

}
