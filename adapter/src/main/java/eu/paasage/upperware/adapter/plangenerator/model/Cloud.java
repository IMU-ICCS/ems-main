/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@Builder
@ToString(exclude = "password")
@EqualsAndHashCode
public class Cloud {

  private String name;

  private String login;
  private String password;
  private Long tenant;

  private String endpoint;
  private String provider;
  private String driver;

  private Map<String, String> filters;

}
