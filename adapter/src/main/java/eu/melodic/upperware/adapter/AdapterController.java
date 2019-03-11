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
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class AdapterController {

  private Coordinator coordinator;
  private DeploymentRequestValidator validator;

  @PostMapping(value = "/applicationDeployment", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public void applicationDeployment(@RequestBody ApplicationDeploymentRequestImpl request,
                                    @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
    validator.validate(request);

    String resourceName = request.getApplicationId();
    String notificationUri = request.getNotificationURI();
    String requestUuid = request.getWatermark().getUuid();

    coordinator.deployNewModel(resourceName, notificationUri, requestUuid, authorization);
  }

  @PostMapping(value = "/autoScaleEvent", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public void autoScaleEvent() {
    // TODO
  }

  @GetMapping(value = "/refreshContext", produces = APPLICATION_JSON_VALUE)
  public void refreshContext() {
    coordinator.refreshContext();
  }

  @GetMapping(value = "/health")
  public void health() {
  }

  @ExceptionHandler
  @ResponseStatus(BAD_REQUEST)
  public String handleException(BadRequestException exception) {
    log.error(format("Returning error response: invalid request (%s) ", exception.getMessage()));
    return exception.getMessage();
  }
}
