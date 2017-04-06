/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.executioncontext.colosseum;

import com.google.common.collect.Lists;
import de.uniulm.omi.cloudiator.colosseum.client.entities.Application;
import de.uniulm.omi.cloudiator.colosseum.client.entities.ApplicationInstance;
import eu.paasage.upperware.adapter.executioncontext.Operations;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Getter
@Setter
@Service
public class ColosseumContext implements Operations {

  private Collection<Application> applications = Lists.newArrayList();
  private Collection<ApplicationInstance> applicationInstances = Lists.newArrayList();

  // TODO create rest of attributes which store running context here

}
