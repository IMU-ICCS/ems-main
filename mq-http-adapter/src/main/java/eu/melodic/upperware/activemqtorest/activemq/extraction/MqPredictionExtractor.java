package eu.melodic.upperware.activemqtorest.activemq.extraction;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eu.melodic.upperware.activemqtorest.MorphemicTopicsMatcher;
import eu.melodic.upperware.activemqtorest.activemq.MqConstants;
import eu.melodic.upperware.activemqtorest.entry.ExtractedMetricsDescriptions;
import eu.melodic.upperware.activemqtorest.entry.MqBaseEntry;
import eu.melodic.upperware.activemqtorest.entry.MqPredictionEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class MqPredictionExtractor extends MqDataEntryBaseExtractor implements IMqDataEntryExtractor {


    @Override
    public boolean isApplicable(ActiveMQMessage activeMQMessage) {
        return MorphemicTopicsMatcher.isPredictionTopic(activeMQMessage.getJMSDestination().toString());
    }

    @Override
    public Optional<MqBaseEntry> extractMqDataEntry(ActiveMQMessage activeMQMessage) {
        String rawMqContent = "{" + extractPayload(new String(activeMQMessage.getContent().getData())) + "}";
        log.debug("Extracting Prediction: {}", rawMqContent);
        JsonObject jsonObject = new JsonParser().parse(rawMqContent).getAsJsonObject();
        log.info("Received Prediction for predictionTime: {}", jsonObject.get("probability").getAsDouble());
        MqPredictionEntry mqPredictionEntry = new MqPredictionEntry();
        String metricName = activeMQMessage.getJMSDestination().toString().replace(MqConstants.TOPIC_PREFIX + "prediction.", StringUtils.EMPTY);
        mqPredictionEntry.setMetricName(metricName);
        mqPredictionEntry.setMetricValue(jsonObject.get("metricValue").getAsInt());
        mqPredictionEntry.setProbability(jsonObject.get("probability").getAsDouble());
        mqPredictionEntry.setPredictionTime(jsonObject.get("predictionTime").getAsLong());

        mqPredictionEntry.setCloud(jsonObject.get("cloud").getAsString());
        mqPredictionEntry.setLevel(jsonObject.get("level").getAsInt());
        mqPredictionEntry.setProvider(jsonObject.get("provider").getAsString());
        mqPredictionEntry.setRefersTo(jsonObject.get("refersTo").getAsString());
        mqPredictionEntry.setTimestamp(activeMQMessage.getTimestamp());

        mqAdapterStatusHolder.addExtractedMetricDescription(mqPredictionEntry.getMetricName(), new ExtractedMetricsDescriptions(mqPredictionEntry.getClass().toString(), mqPredictionEntry.toString()));

        return Optional.of(mqPredictionEntry);
    }
}
