/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.cpsolver.lib;

import eu.melodic.models.services.cpSolver.ConstraintProblemSolutionNotificationRequest;
import eu.melodic.models.services.cpSolver.ConstraintProblemSolutionNotificationRequestImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import eu.melodic.models.commons.NotificationResult;
import eu.melodic.models.commons.NotificationResultImpl;
import static eu.melodic.models.commons.NotificationResult.StatusType.ERROR;
import static eu.melodic.models.commons.NotificationResult.StatusType.SUCCESS;
import eu.melodic.models.commons.Watermark;
import eu.melodic.models.commons.WatermarkImpl;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.Date;
import org.springframework.core.env.Environment;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CPSolverExecutor {
  
  private Environment env;
  private RestTemplate restTemplate;

  @Async
  public void generateCPSolution(String applicationId, String cdoResourcePath, String notificationUri, String requestUuid) {
    try {
      CPSolver cpSolver = new CPSolver(cdoResourcePath,null);
      boolean hasSolution = cpSolver.solve();
      if (hasSolution) {
        log.info("Solution has been produced");
        notifySolutionProduced(applicationId, notificationUri, requestUuid);
      } else {
        log.info("Problem is infeasible");
        notifySolutionNotApplied(applicationId, notificationUri, requestUuid);
      }
    } catch (Exception e) {
      log.error("CPSolver returned exception.", e);
      notifySolutionNotApplied(applicationId, notificationUri, requestUuid);
    }
  }

  public void generateCPSolutionFromFile(String applicationId, String filePath, String requestUuid) throws Exception {
      CPSolver cpSolver = new CPSolver(null,filePath);
      boolean hasSolution = cpSolver.solve();
      if (hasSolution) {
        log.info("Solution has been produced");
      } else {
        log.info("Problem is infeasible");
      }
  }


  private void notifySolutionProduced(String camelModelID, String notificationUri, String uuid) {
    log.info("Sending solution available notification");
    NotificationResult result = prepareSuccessNotificationResult();
    ConstraintProblemSolutionNotificationRequest notification = prepareNotification(camelModelID, result, uuid);
    sendNotification(notification, notificationUri);
  }

  private void notifySolutionNotApplied(String camelModelID, String notificationUri, String uuid)  {
    log.info("Sending solution NOT available notification");
    NotificationResult result = prepareErrorNotificationResult("Solution was not generated.");
    ConstraintProblemSolutionNotificationRequest notification = prepareNotification(camelModelID, result, uuid);
    sendNotification(notification, notificationUri);
  }

  private void sendNotification(ConstraintProblemSolutionNotificationRequest notification, String notificationUri) {
    String esbUrl = env.getProperty("esb.url");

    if (esbUrl.endsWith("/")) {
      esbUrl = esbUrl.substring(0, esbUrl.length() - 1);
    }
    if (notificationUri.startsWith("/")) {
      notificationUri = notificationUri.substring(1);
    }
    try {
      log.info("Sending notification to: "+esbUrl);
      restTemplate.postForEntity(esbUrl + "/" + notificationUri, notification, String.class);
    } catch (RestClientException restException){
      log.error("Error sending notification: " +restException.getMessage());
    }
    log.info("Notification sent.");
  }
  private Watermark prepareWatermark(String uuid) {
    Watermark watermark = new WatermarkImpl();
    watermark.setUser("CPSolver");
    watermark.setSystem("CPSolver");
    watermark.setDate(new Date());
    watermark.setUuid(uuid);
    return watermark;
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

  private ConstraintProblemSolutionNotificationRequest prepareNotification(String camelModelID, NotificationResult result, String uuid) {
    ConstraintProblemSolutionNotificationRequest notification = new ConstraintProblemSolutionNotificationRequestImpl();
    notification.setApplicationId(camelModelID);
    notification.setResult(result);
    notification.setWatermark(prepareWatermark(uuid));
    return notification;
  }


}
