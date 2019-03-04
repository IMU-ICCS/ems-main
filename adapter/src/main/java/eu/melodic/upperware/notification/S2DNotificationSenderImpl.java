package eu.melodic.upperware.notification;

import eu.melodic.models.commons.NotificationResult;
import eu.melodic.models.services.solverToDeployment.ApplySolutionNotificationRequest;
import eu.melodic.models.services.solverToDeployment.ApplySolutionNotificationRequestImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.function.Supplier;

@Service
@Slf4j
public class S2DNotificationSenderImpl extends NotificationSender<ApplySolutionNotificationRequest> {

    @Autowired
    public S2DNotificationSenderImpl(RestTemplate restTemplate, Environment env) {
        super(restTemplate, env);
    }

    public void notifySolutionApplied(String camelModelID, String notificationUri, String uuid) {
        log.info("Sending solution applied notification");
        NotificationResult result = prepareSuccessNotificationResult();
        notifySolution(camelModelID, notificationUri, uuid, result);
    }

    public void notifySolutionNotApplied(String camelModelID, String notificationUri, String uuid)  {
        log.info("Sending solution NOT applied notification");
        NotificationResult result = prepareErrorNotificationResult("Solution was not applied.");
        notifySolution(camelModelID, notificationUri, uuid, result);
    }

    private void notifySolution(String camelModelID, String notificationUri, String uuid, NotificationResult result)  {
        Supplier<ApplySolutionNotificationRequest> notificationSupplier = () -> {
            ApplySolutionNotificationRequest notification = new ApplySolutionNotificationRequestImpl();
            notification.setApplicationId(camelModelID);
            notification.setResult(result);
            notification.setWatermark(prepareWatermark(uuid));
            return notification;
        };

        sendNotification(notificationSupplier, notificationUri);
    }

    @Override
    String getComponentName() {
        return "SolverToDeployment";
    }
}
