package eu.melodic.upperware.notification;

import eu.melodic.models.commons.NotificationResult;
import eu.melodic.models.commons.NotificationResultImpl;
import eu.melodic.models.commons.Watermark;
import eu.melodic.models.commons.WatermarkImpl;
import eu.melodic.models.services.solverToDeployment.ApplySolutionNotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.function.Supplier;

import static eu.melodic.models.commons.NotificationResult.StatusType.ERROR;
import static eu.melodic.models.commons.NotificationResult.StatusType.SUCCESS;

@Service
@Slf4j
@AllArgsConstructor
public abstract class NotificationSender<T> {

    private RestTemplate restTemplate;
    private Environment env;

    abstract String getComponentName();

    public Watermark prepareWatermark(String uuid) {
        Watermark watermark = new WatermarkImpl();
        watermark.setUser(getComponentName());
        watermark.setSystem(getComponentName());
        watermark.setDate(new Date());
        watermark.setUuid(uuid);
        return watermark;
    }

    void sendNotification(Supplier<T> notificationSupplier, String notificationUri) {
        String esbUrl = env.getProperty("esb.url");
        esbUrl = StringUtils.removeEnd(esbUrl, "/");
        esbUrl = StringUtils.removeStart(esbUrl, "/");

        try {
            log.info("Sending notification to: {}", esbUrl);
            restTemplate.postForEntity(esbUrl + "/" + notificationUri, notificationSupplier.get(), String.class);
        } catch (RestClientException restException){
            log.error("Error sending notification:", restException);
        }
        log.info("Notification sent.");
    }

    NotificationResult prepareSuccessNotificationResult() {
        NotificationResult result = new NotificationResultImpl();
        result.setStatus(SUCCESS);
        return result;
    }

    NotificationResult prepareErrorNotificationResult(String errorMsg) {
        NotificationResult result = new NotificationResultImpl();
        result.setErrorDescription(errorMsg);
        result.setStatus(ERROR);
        return result;
    }

}
