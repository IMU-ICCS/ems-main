package cp_wrapper.utils.solution_result_notifier;

import eu.melodic.models.commons.NotificationResult;
import eu.melodic.models.commons.NotificationResultImpl;
import eu.melodic.models.commons.Watermark;
import eu.melodic.models.commons.WatermarkImpl;
import eu.melodic.models.services.cpSolver.ConstraintProblemSolutionNotificationRequest;
import eu.melodic.models.services.cpSolver.ConstraintProblemSolutionNotificationRequestImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static eu.melodic.models.commons.NotificationResult.StatusType.ERROR;
import static eu.melodic.models.commons.NotificationResult.StatusType.SUCCESS;

@Slf4j
@AllArgsConstructor
public class SolutionResultNotifier {
    private Environment env;

    private RestTemplate restTemplate;

    public void notifySolutionProduced(String camelModelID, String notificationUri, String uuid) {
        log.info("Sending solution available notification");
        NotificationResult result = prepareSuccessNotificationResult();
        ConstraintProblemSolutionNotificationRequest notification = prepareNotification(camelModelID, result, uuid);
        sendNotification(notification, notificationUri);
    }

    public void notifySolutionNotApplied(String camelModelID, String notificationUri, String uuid) {
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
            log.info("Sending notification to: {}", esbUrl);
            restTemplate.postForEntity(esbUrl + "/" + notificationUri, notification, String.class);
            log.info("Notification sent.");
        } catch (RestClientException restException) {
            log.error("Error sending notification: ", restException);
        }
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
