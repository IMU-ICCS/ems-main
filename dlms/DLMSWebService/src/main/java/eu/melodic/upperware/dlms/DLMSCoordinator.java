package eu.melodic.upperware.dlms;

import eu.melodic.models.commons.NotificationResult;
import eu.melodic.models.commons.NotificationResultImpl;
import eu.melodic.models.commons.Watermark;
import eu.melodic.models.commons.WatermarkImpl;
import eu.melodic.models.services.dlms.DataModelNotificationRequest;
import eu.melodic.models.services.dlms.DataModelNotificationRequestImpl;
import eu.melodic.upperware.dlms.camel.ModelAnalyzer;
import eu.melodic.upperware.dlms.properties.DLMSProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class DLMSCoordinator {

    private final DLMSService dlmsService;
    private final ModelAnalyzer modelAnalyzer;
    private final RestTemplate restTemplate;

    private final DLMSProperties dlmsProperties;
    private final AppCompDataSourceRepository appCompDSRepository;

    @Async
    public void doAddUpdateDataSourcesWork(String applicationId, String notificationURI, String uuid){

        // Map of components and datasources
        List<AppCompDataSource> appCompDSList = new ArrayList<>();
        // read the camel model and process it
        try {
            modelAnalyzer.readModel(applicationId); // read the camel model
            List<DataSource> dataSourceList = modelAnalyzer.getDataSourceList(); // get data sources from camel model

            appCompDSList = modelAnalyzer.getAppCompDSList();
            // do operations if relevant data sources are present in the camel model
            for (DataSource datasource : dataSourceList) {
                if (dlmsService.hasDataSourceByName(datasource.getName())) {
                    // if data source already exists, update if necessary
                    dlmsService.updateDataSource(datasource, datasource.getName());
                } else {
                    // add new data source if it does not exist
                    dlmsService.addDataSource(datasource);
                    log.info(datasource.getName() + " was added");
                }
            }

        } catch (Exception e) {
            // data registration failed
            log.error("AddUpdateDataSources failed", e);
            sendErrorNotification(notificationURI, applicationId, uuid, e.getMessage());
        }

        if (appCompDSList.size() > 0) {
            appCompDSRepository.saveAll(appCompDSList);
            dlmsService.calculateAcDsMp();
        }

        // send notification
        sendSuccessNotification(notificationURI, applicationId, uuid);
    }

    private void sendSuccessNotification(String notificationUri, String applicationId, String uuid){
        final DataModelNotificationRequest notification = createNotification(applicationId, uuid, () -> {
            NotificationResult notificationResult = new NotificationResultImpl();
            notificationResult.setStatus(NotificationResult.StatusType.SUCCESS);
            notificationResult.setErrorCode("0");
            return notificationResult;
        });
        sendNotificationMessage(notification, notificationUri);
    }

    private void sendErrorNotification(String notificationUri, String applicationId, String uuid, String errorMsg){
        final DataModelNotificationRequest notification = createNotification(applicationId, uuid, () -> {
            NotificationResult notificationResult = new NotificationResultImpl();
            notificationResult.setStatus(NotificationResult.StatusType.ERROR);
            notificationResult.setErrorCode("1");
            notificationResult.setErrorDescription(errorMsg);
            return notificationResult;
        });
        sendNotificationMessage(notification, notificationUri);
    }

    private DataModelNotificationRequest createNotification(String applicationId, String uuid, Supplier<NotificationResult> notificationResultSupplier) {
        DataModelNotificationRequest dataModelNotificationRequest = new DataModelNotificationRequestImpl();
        dataModelNotificationRequest.setApplicationId(applicationId);
        dataModelNotificationRequest.setWatermark(prepareWatermark(uuid));
        dataModelNotificationRequest.setResult(notificationResultSupplier.get());
        return dataModelNotificationRequest;
    }

    /**
     * Post the notification message in the provided url
     */
    private void sendNotificationMessage(DataModelNotificationRequest dataModelNotificationRequest, String notificationUri) {
        String esbUrl = dlmsProperties.getEsb().getUrl();
        if (esbUrl.endsWith("/")) {
            esbUrl = esbUrl.substring(0, esbUrl.length() - 1);
        }
        if (notificationUri.startsWith("/")) {
            notificationUri = notificationUri.substring(1);
        }
        log.info("Sending {} notification ", dataModelNotificationRequest.getResult().getStatus());
        restTemplate.postForEntity(esbUrl + "/" + notificationUri, dataModelNotificationRequest,
                DataModelNotificationRequest.class);
    }

    /**
     * Generate watermark
     */
    private Watermark prepareWatermark(String uuid) {
        Watermark watermark = new WatermarkImpl();
        watermark.setUser("dlms");
        watermark.setSystem("dlms");
        watermark.setDate(new Date());
        watermark.setUuid(uuid);
        return watermark;
    }


}
