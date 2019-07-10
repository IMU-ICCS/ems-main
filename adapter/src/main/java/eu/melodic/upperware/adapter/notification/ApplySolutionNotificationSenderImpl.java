package eu.melodic.upperware.adapter.notification;

import eu.melodic.models.commons.NotificationResult;

import eu.melodic.models.services.adapter.ApplySolutionNotificationRequest;
import eu.melodic.models.services.adapter.ApplySolutionNotificationRequestImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.function.Supplier;

@Service
@Slf4j
public class ApplySolutionNotificationSenderImpl extends NotificationSender<ApplySolutionNotificationRequest> {

    @Autowired
    public ApplySolutionNotificationSenderImpl(RestTemplate restTemplate, Environment env) {
        super(restTemplate, env);
    }

    public void notifySolutionApplied(String camelModelID, String deploymentInstanceName, String notificationUri, String uuid) {
        log.info("Sending solution applied notification");
        NotificationResult result = prepareSuccessNotificationResult();
        notifySolution(camelModelID, deploymentInstanceName, notificationUri, uuid, result);
    }

    public void notifySolutionNotApplied(String camelModelID, String notificationUri, String uuid)  {
        log.info("Sending solution NOT applied notification");
        NotificationResult result = prepareErrorNotificationResult("Solution was not applied.");
        notifySolution(camelModelID, null, notificationUri, uuid, result);
    }

    private void notifySolution(String camelModelID, String deploymentInstanceName, String notificationUri, String uuid, NotificationResult result)  {
        Supplier<ApplySolutionNotificationRequest> notificationSupplier = () -> {
            ApplySolutionNotificationRequest notification = new ApplySolutionNotificationRequestImpl();
            notification.setApplicationId(camelModelID);
            notification.setDeploymentInstanceName(deploymentInstanceName);
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
