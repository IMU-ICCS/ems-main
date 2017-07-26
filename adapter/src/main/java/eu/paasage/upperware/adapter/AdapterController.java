/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter;

import eu.melodic.models.interfaces.adapter.ApplicationDeploymentRequestImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class AdapterController {

  private Coordinator coordinator;

  @RequestMapping(value = "/applicationDeployment", method = POST)
  public void applicationDeployment(@RequestBody ApplicationDeploymentRequestImpl request) {
    String resourceName = request.getApplicationId();
    String notificationUri = request.getNotificationURI();
    String requestUuid = request.getWatermark().getUuid();
    coordinator.deployNewModel(resourceName, notificationUri, requestUuid);
  }

  @RequestMapping(value = "/autoScaleEvent", method = POST)
  public void autoScaleEvent() {
    // TODO
  }

  @RequestMapping(value = "/refreshContext", method = GET)
  public void refreshContext() {
    // TODO
  }

  @RequestMapping(value = "/health", method = GET)
  public void health() {
  }
}
