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
import com.google.common.collect.Maps;
import eu.melodic.models.commons.NotificationResult;
import eu.melodic.models.commons.NotificationResultImpl;
import eu.melodic.models.commons.Watermark;
import eu.melodic.models.commons.WatermarkImpl;
import eu.melodic.models.services.adapter.DeploymentNotificationRequest;
import eu.melodic.models.services.adapter.DeploymentNotificationRequestImpl;
import eu.melodic.upperware.adapter.communication.cdoserver.CdoServerApi;
import eu.melodic.upperware.adapter.executioncontext.ContextOperations;
import eu.melodic.upperware.adapter.executioncontext.cdoserver.CdoServerUpdater;
import eu.melodic.upperware.adapter.graphlogger.ToLogGraphLogger;
import eu.melodic.upperware.adapter.planexecutor.PlanExecutor;
import eu.melodic.upperware.adapter.plangenerator.Plan;
import eu.melodic.upperware.adapter.plangenerator.PlanGenerator;
import eu.melodic.upperware.adapter.properties.AdapterProperties;
import eu.melodic.upperware.adapter.validation.PlanValidator;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;

import static eu.melodic.models.commons.NotificationResult.StatusType.ERROR;
import static eu.melodic.models.commons.NotificationResult.StatusType.SUCCESS;
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
  private CdoServerUpdater cdoServerUpdater;
  private ToLogGraphLogger toLogGraphLogger;

  private ContextOperations context;

  private RestTemplate restTemplate;

  private AdapterProperties properties;

  @Async
  public void deployNewModel(String resourceName, String notificationUri, String uuid) {
    try {
      acquireLock(resourceName);
    } catch (Exception e) {
      log.error("An exception occurred during acquiring lock for application {}", resourceName, e);
      notifyErrorOccurred(resourceName, notificationUri, uuid, e);
      return;
    }
    log.info("Starting new model deployment process");
    try {
      run(resourceName, notificationUri, uuid);
    } catch (Exception e) {
      log.error("An exception occurred during deployment process", e);
      notifyErrorOccurred(resourceName, notificationUri, uuid, e);
    } finally {
      releaseLock(resourceName);
    }
    log.info("New model deployment process has been finished");
  }

  public void refreshContext() {
    context.refreshContext();
  }

  private void run(String resourceName, String notificationUri, String uuid) {
    Plan plan;
    CDOSessionX cdoSessionX = cdoServerApi.openSession();
    CDOTransaction tr = cdoSessionX.openTransaction();

    try {
      DeploymentInstanceModel targetModel = cdoServerApi.getModelToDeploy(resourceName, tr); //new
      DeploymentInstanceModel currentModel = cdoServerApi.getDeployedModel(resourceName, tr); //old
      if (currentModel == null) {
        plan = planGenerator.buildConfigurationPlan(targetModel);
      } else {
        plan = planGenerator.buildReconfigurationPlan(currentModel, targetModel);
        if (!planValidator.validate(plan)) {
          notifyPlanRejected(resourceName, notificationUri, uuid);
          return;
        }
      }
    } finally {
      cdoSessionX.closeTransaction(tr);
      cdoSessionX.closeSession();
    }
    if (!context.isLoaded()) {
      context.refreshContext();
    }

    toLogGraphLogger.logGraph(plan.getTaskGraph());

    planExecutor.executePlan(plan);
    cdoServerUpdater.updateCamelModel(resourceName);
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
    String errorMsg = e.getMessage();
    log.error("Sending error notification: {}", errorMsg);
    NotificationResult result = prepareErrorNotificationResult(errorMsg);
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
