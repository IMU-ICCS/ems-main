package eu.melodic.event.models.services;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import eu.melodic.event.models.commons.NotificationResult;
import eu.melodic.event.models.commons.Watermark;
import java.lang.Object;
import java.lang.String;
import java.util.Map;

@JsonDeserialize(
    as = CamelModelNotificationRequestImpl.class
)
public interface CamelModelNotificationRequest {
  Map<String, Object> getAdditionalProperties();

  void setAdditionalProperties(Map<String, Object> additionalProperties);

  String getApplicationId();

  void setApplicationId(String applicationId);

  NotificationResult getResult();

  void setResult(NotificationResult result);

  Watermark getWatermark();

  void setWatermark(Watermark watermark);
}
