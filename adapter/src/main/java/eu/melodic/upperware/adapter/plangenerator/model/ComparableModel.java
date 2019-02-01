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

import java.util.Collection;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class ComparableModel {

  private AdapterJob adapterJob;
  private AdapterSchedule adapterSchedule;
  private Collection<AdapterRequirement> adapterRequirements;
  private Collection<AdapterProcess> adapterProcesses;
  private Collection<AdapterMonitor> adapterMonitors;

}
