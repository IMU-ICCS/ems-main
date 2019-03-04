package eu.melodic.upperware.notification;

import eu.melodic.models.commons.NotificationResult;
import eu.melodic.models.services.adapter.DeploymentNotificationRequest;
import eu.melodic.models.services.adapter.DeploymentNotificationRequestImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.function.Supplier;

@Service
@Slf4j
public class AdapterNotificationSenderImpl extends NotificationSender<DeploymentNotificationRequest> {

    @Autowired
    public AdapterNotificationSenderImpl(RestTemplate restTemplate, Environment env) {
        super(restTemplate, env);
    }

    public void notifyPlanApplied(String resourceName, String notificationUri, String uuid) {
        log.info("Sending plan applied notification");
        NotificationResult result = prepareSuccessNotificationResult();
        notifySolution(resourceName, notificationUri, uuid, result);
    }

    public void notifyPlanRejected(String resourceName, String notificationUri, String uuid) {
        log.info("Sending plan rejected notification");
        NotificationResult result = prepareErrorNotificationResult("Built plan was rejected by Plan Validator");
        notifySolution(resourceName, notificationUri, uuid, result);
    }

    public void notifyErrorOccurred(String resourceName, String notificationUri, String uuid, Exception e) {
        String errorMsg = e.getMessage();
        log.error("Sending error notification: {}", errorMsg);
        NotificationResult result = prepareErrorNotificationResult(errorMsg);
        notifySolution(resourceName, notificationUri, uuid, result);
    }

    private void notifySolution(String resourceName, String notificationUri, String uuid, NotificationResult result) {
        Supplier<DeploymentNotificationRequest> notificationSupplier = () -> {
            DeploymentNotificationRequest notification = new DeploymentNotificationRequestImpl();
            notification.setApplicationId(resourceName);
            notification.setResult(result);
            notification.setWatermark(prepareWatermark(uuid));
            return notification;
        };

        sendNotification(notificationSupplier, notificationUri);
    }

    @Override
    String getComponentName() {
        return "Adapter";
    }
}
