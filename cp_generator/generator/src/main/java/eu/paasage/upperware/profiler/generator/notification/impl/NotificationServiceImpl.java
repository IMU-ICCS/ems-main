package eu.paasage.upperware.profiler.generator.notification.impl;

import eu.melodic.models.commons.NotificationResult;
import eu.melodic.models.commons.NotificationResultImpl;
import eu.melodic.models.commons.Watermark;
import eu.melodic.models.commons.WatermarkImpl;
import eu.melodic.models.services.cpGenerator.ConstraintProblemNotificationRequest;
import eu.melodic.models.services.cpGenerator.ConstraintProblemNotificationRequestImpl;
import eu.paasage.upperware.profiler.generator.notification.NotificationService;
import eu.paasage.upperware.profiler.generator.properties.GeneratorProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static eu.melodic.models.commons.NotificationResult.StatusType.ERROR;
import static eu.melodic.models.commons.NotificationResult.StatusType.SUCCESS;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationServiceImpl implements NotificationService {

    private RestTemplate restTemplate;
    private GeneratorProperties generatorProperties;

    @Override
    public void notifySuccess(String resourceName, String notificationUri, String uuid, String cpPath) {
        log.info("Sending success notification");
        NotificationResult result = prepareSuccessNotificationResult();
        notify(resourceName, notificationUri, uuid, result, cpPath);
    }

    @Override
    public void notifyError(String resourceName, String notificationUri, String uuid, String errorMsg) {
        log.info("Sending error notification");
        NotificationResult result = prepareErrorNotificationResult(errorMsg);
        notify(resourceName, notificationUri, uuid, result, null);
    }

    private void notify(String resourceName, String notificationUri, String uuid, NotificationResult result, String cpPath) {
        ConstraintProblemNotificationRequest notification = prepareNotification(resourceName, result, uuid, cpPath);
        sendNotification(notification, notificationUri);
    }

    private void sendNotification(ConstraintProblemNotificationRequest notification, String notificationUri) {
        String esbUrl = generatorProperties.getEsb().getUrl();
        if (esbUrl.endsWith("/")) {
            esbUrl = esbUrl.substring(0, esbUrl.length() - 1);
        }
        if (notificationUri.startsWith("/")) {
            notificationUri = notificationUri.substring(1);
        }
        restTemplate.postForEntity(esbUrl + "/" + notificationUri, notification, String.class);
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

    private ConstraintProblemNotificationRequest prepareNotification(String resourceName, NotificationResult result, String uuid, String cpPath) {
        ConstraintProblemNotificationRequest notification = new ConstraintProblemNotificationRequestImpl();
        notification.setApplicationId(resourceName);
        notification.setResult(result);
        notification.setWatermark(prepareWatermark(uuid));
        notification.setCdoResourcePath(cpPath);
        return notification;
    }

    private Watermark prepareWatermark(String uuid) {
        Watermark watermark = new WatermarkImpl();
        watermark.setUser("cpGenerator");
        watermark.setSystem("cpGenerator");
        watermark.setDate(new Date());
        watermark.setUuid(uuid);
        return watermark;
    }

}
