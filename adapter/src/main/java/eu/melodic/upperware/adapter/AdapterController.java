/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter;

import eu.melodic.models.interfaces.adapter.ApplicationDeploymentRequestImpl;
import eu.melodic.upperware.adapter.validation.DeploymentRequestValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class AdapterController {

  private Coordinator coordinator;

  private DeploymentRequestValidator validator;

//XXX:DEL: after finishing development+testing
  @RequestMapping(value = "/test", method = GET)
  public String test() {
    try {
      log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>> TEST - BEGIN");
      return coordinator.test();
    } finally {
      log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>> TEST - END");
    }
  }

  @RequestMapping(value = "/applicationDeployment", method = POST,
    consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public void applicationDeployment(@RequestBody ApplicationDeploymentRequestImpl request) {
    validator.validate(request);

    String resourceName = request.getApplicationId();
    String notificationUri = request.getNotificationURI();
    String requestUuid = request.getWatermark().getUuid();

    coordinator.deployNewModel(resourceName, notificationUri, requestUuid);
  }

  @RequestMapping(value = "/autoScaleEvent", method = POST,
    consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public void autoScaleEvent() {
    // TODO
  }

  @RequestMapping(value = "/refreshContext", method = GET, produces = APPLICATION_JSON_VALUE)
  public void refreshContext() {
    coordinator.refreshContext();
  }

  @RequestMapping(value = "/health", method = GET)
  public void health() {
  }

  @ExceptionHandler
  @ResponseStatus(BAD_REQUEST)
  public String handleException(BadRequestException exception) {
    log.error(format("Returning error response: invalid request (%s) ", exception.getMessage()));
    return exception.getMessage();
  }
}
