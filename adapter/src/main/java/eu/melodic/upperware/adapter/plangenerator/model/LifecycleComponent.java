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
public class LifecycleComponent implements Data {

  private String name;

  private String initCmd;

  private String preInstallCmd;
  private String installCmd;
  private String postInstallCmd;

  private String preStartCmd;
  private String startCmd;
  private String startDetectionCmd;
  private String postStartCmd;

  private String preStopCmd;
  private String stopCmd;
  private String stopDetectionCmd;
  private String postStopCmd;

  private String shutdownCmd;

}
