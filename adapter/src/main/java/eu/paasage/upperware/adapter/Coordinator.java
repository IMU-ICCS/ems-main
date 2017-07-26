/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter;

import com.google.common.collect.Maps;
import eu.melodic.models.commons.NotificationResult;
import eu.melodic.models.commons.NotificationResultImpl;
import eu.melodic.models.commons.Watermark;
import eu.melodic.models.commons.WatermarkImpl;
import eu.melodic.models.services.adapter.DeploymentNotificationRequest;
import eu.melodic.models.services.adapter.DeploymentNotificationRequestImpl;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.upperware.adapter.communication.cdoserver.CdoServerApi;
import eu.paasage.upperware.adapter.executioncontext.ContextOperations;
import eu.paasage.upperware.adapter.planexecutor.PlanExecutor;
import eu.paasage.upperware.adapter.plangenerator.Plan;
import eu.paasage.upperware.adapter.plangenerator.PlanGenerator;
import eu.paasage.upperware.adapter.properties.AdapterProperties;
import eu.paasage.upperware.adapter.validation.PlanValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;

import static eu.melodic.models.commons.NotificationResult.StatusType.*;
import static eu.melodic.models.commons.NotificationResult.StatusType.ERROR;
import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class Coordinator {

  private static final Map<String, Object> LOCKS = Maps.newConcurrentMap();

  private CdoServerApi cdoServerApi;

  private PlanGenerator planGenerator;
  private PlanValidator planValidator;
  private PlanExecutor planExecutor;

  private ContextOperations context;

  private RestTemplate restTemplate;

  private AdapterProperties properties;

  @Async
  public void deployNewModel(String resourceName, String notificationUri, String uuid) {
    log.info("Starting new model deployment process");
    acquireLock(resourceName);
    try {
      run(resourceName, notificationUri, uuid);
    } catch (Exception e) {
      log.error("An error occurred during deployment process", e);
      notifyErrorOccurred(resourceName, notificationUri, uuid, e);
    } finally {
      releaseLock(resourceName);
    }
    log.info("New model deployment process has been finished");
  }

  private void run(String resourceName, String notificationUri, String uuid) {
    CDOTransaction tr = cdoServerApi.openTransaction();
    DeploymentModel targetModel = cdoServerApi.getModelToDeploy(resourceName, tr);
    DeploymentModel currentModel = cdoServerApi.getDeployedModel(resourceName, tr);
    Plan plan;
    if (currentModel == null) {
      plan = planGenerator.buildConfigurationPlan(targetModel);
    } else {
      plan = planGenerator.buildReconfigurationPlan(targetModel, currentModel);
      if (!planValidator.validate(plan)) {
        notifyPlanRejected(resourceName, notificationUri, uuid);
        return;
      }
    }
    cdoServerApi.closeTransaction(tr);
    if (!context.isLoaded()) {
      context.refreshContext();
    }
    planExecutor.executePlan(plan);
//    cdoServerCamelUpdater.updateCamelModels();  TODO
    notifyPlanApplied(resourceName, notificationUri, uuid);
  }

  private void notifyPlanApplied(String resourceName, String notificationUri, String uuid) {
    log.info("Sending plan applied notification");
    NotificationResult result = prepareSuccessNotificationResult();
    DeploymentNotificationRequest notification = prepareNotification(resourceName, result, uuid);
    sendNotification(notification, notificationUri);
  }

  private void notifyPlanRejected(String resourceName, String notificationUri, String uuid) {
    log.info("Sending plan rejected notification");
    NotificationResult result = prepareErrorNotificationResult("Built plan was rejected by Plan Validator");
    DeploymentNotificationRequest notification = prepareNotification(resourceName, result, uuid);
    sendNotification(notification, notificationUri);
  }

  private void notifyErrorOccurred(String resourceName, String notificationUri, String uuid, Exception e) {
    log.error("Sending error notification");
    NotificationResult result = prepareErrorNotificationResult(e.getMessage());
    DeploymentNotificationRequest notification = prepareNotification(resourceName, result, uuid);
    sendNotification(notification, notificationUri);
  }

  private NotificationResult prepareSuccessNotificationResult() {
    NotificationResult result = new NotificationResultImpl();
    result.setStatus(SUCCESS);
    return result;
  }

  private NotificationResult prepareErrorNotificationResult(String errorMsg) {
    NotificationResult result = new NotificationResultImpl();
    // TODO unsupported error code
    result.setErrorDescription(errorMsg);
    result.setStatus(ERROR);
    return result;
  }

  private DeploymentNotificationRequest prepareNotification(String resourceName, NotificationResult result, String uuid) {
    DeploymentNotificationRequest notification = new DeploymentNotificationRequestImpl();
    notification.setApplicationId(resourceName);
    notification.setResult(result);
    notification.setWatermark(prepareWatermark(uuid));
    return notification;
  }

  private Watermark prepareWatermark(String uuid) {
    Watermark watermark = new WatermarkImpl();
    watermark.setUser("adapter");
    watermark.setSystem("adapter");
    watermark.setDate(new Date());
    watermark.setUuid(uuid);
    return watermark;
  }

  private void sendNotification(DeploymentNotificationRequest notification, String notificationUri) {
    String esbUrl = properties.getEsb().getUrl();
    if (esbUrl.endsWith("/")) {
      esbUrl = esbUrl.substring(0, esbUrl.length() - 1);
    }
    if (notificationUri.startsWith("/")) {
      notificationUri = notificationUri.substring(1);
    }
    restTemplate.postForEntity(esbUrl + "/" + notificationUri, notification, String.class);
  }

  private static void acquireLock(String resourceName) {
    synchronized (LOCKS) {
      log.info("Acquiring lock for application {}", resourceName);
      Object obj = LOCKS.get(resourceName);
      if (obj != null) {
        throw new IllegalStateException(format("(Re)configuration process for %s is running. " +
          "Wait until it is completed and repeat request.", resourceName));
      }
      LOCKS.put(resourceName, Boolean.TRUE);
    }
  }

  private static void releaseLock(String resourceName) {
    log.info("Releasing lock for application {}", resourceName);
    LOCKS.remove(resourceName);
  }
}
