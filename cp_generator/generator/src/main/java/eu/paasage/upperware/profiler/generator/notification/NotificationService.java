package eu.paasage.upperware.profiler.generator.notification;

/**
 * Created by pszkup on 11.08.17.
 */
public interface NotificationService {

    void notifySuccess(String resourceName, String notificationUri, String uuid, String cpPath);

    void notifyError(String resourceName, String notificationUri, String uuid, String errorMsg);
}
