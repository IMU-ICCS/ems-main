/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter;

import camel.deployment.DeploymentInstanceModel;
import camel.deployment.DeploymentModel;
import eu.melodic.models.interfaces.adapter.*;
import eu.melodic.models.services.adapter.DifferenceResponse;
import eu.melodic.upperware.adapter.extractor.*;
import eu.melodic.upperware.adapter.service.DiffResponseConverter;
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

    private DeployCoordinator deployCoordinator;
    private ApplyCoordinator applyCoordinator;

    private DeploymentRequestValidator validator;

    private DiffResponseConverter diffResponseConverter;

    @PostMapping(value = "/applicationDeployment", consumes = APPLICATION_JSON_VALUE)
    public void applicationDeployment(@RequestBody ApplicationDeploymentRequestImpl request,
                                      @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        validator.validate(request);

        String resourceName = request.getApplicationId();
        String notificationUri = request.getNotificationURI();
        String requestUuid = request.getWatermark().getUuid();

        deployCoordinator.deployNewModel(resourceName, notificationUri, requestUuid, authorization);
    }

    @PostMapping(value = "/applySolution", consumes = APPLICATION_JSON_VALUE)
    public void applySolution(@RequestBody ApplySolutionRequestImpl request) {

        String applicationId = request.getApplicationId();
        String cdoResourcePath = request.getCdoModelsPath();
        String notificationUri = request.getNotificationURI();
        String requestUuid = request.getWatermark().getUuid();

        log.info("Received request: {} {} {} {}", applicationId, cdoResourcePath, notificationUri, requestUuid);

        applyCoordinator.doWorkTS(applicationId, cdoResourcePath, notificationUri, requestUuid);
    }

    @PostMapping(value = "/difference", consumes = APPLICATION_JSON_VALUE)
    public DifferenceResponse difference(@RequestBody DifferenceRequestImpl request) {
        String applicationId = request.getApplicationId();
        String currDeploymentInstanceName = request.getCurrDeploymentInstanceName();
        String prevDeploymentInstanceName = request.getPrevDeploymentInstanceName();

        return diffResponseConverter.convert(deployCoordinator.calculateDifference(applicationId, currDeploymentInstanceName, prevDeploymentInstanceName));
    }

    @PostMapping(value = "/getDeploymentModel", consumes = APPLICATION_JSON_VALUE)
    public DeploymentModelResponse getDeploymentModel(@RequestBody DeploymentModelRequest request) {

        String applicationId = request.getApplicationId();
        String requestUuid = request.getWatermark().getUuid();

        log.info("Received request: {} {}", applicationId, requestUuid);

        DeploymentInstanceModel deploymentModel = deployCoordinator.getDeploymentModel(applicationId);

        return deployCoordinator.prepareResponseNodeData(deploymentModel, applicationId, requestUuid);
    }

    @GetMapping(value = "/refreshContext", produces = APPLICATION_JSON_VALUE)
    public void refreshContext() {
        deployCoordinator.refreshContext();
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
