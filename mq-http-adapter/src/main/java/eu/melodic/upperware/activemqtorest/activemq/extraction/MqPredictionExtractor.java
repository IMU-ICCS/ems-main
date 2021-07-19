package eu.melodic.upperware.activemqtorest.activemq.extraction;

import eu.melodic.upperware.activemqtorest.activemq.MqConstants;
import eu.melodic.upperware.activemqtorest.entry.ExtractedMetricsDescriptions;
import eu.melodic.upperware.activemqtorest.entry.MqBaseEntry;
import eu.melodic.upperware.activemqtorest.entry.MqPredictionEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class MqPredictionExtractor extends MqDataEntryBaseExtractor implements IMqDataEntryExtractor {


    @Override
    public boolean isApplicable(ActiveMQMessage activeMQMessage) {
        return activeMQMessage.getJMSDestination().toString().contains(melodicConfiguration.getMqTopicPrediction());
    }

    @Override
    public Optional<MqBaseEntry> extractMqDataEntry(ActiveMQMessage activeMQMessage) {
        log.debug("Extracting Prediction");
        return extractJsonPayload(activeMQMessage).map(jsonObject -> {
            MqPredictionEntry mqPredictionEntry = new MqPredictionEntry();
            mqPredictionEntry.setName(jsonObject.get("name").getAsString());
            String topic = activeMQMessage.getJMSDestination().toString().replace(MqConstants.TOPIC_PREFIX, StringUtils.EMPTY);
            mqPredictionEntry.setTopic(topic);
            mqPredictionEntry.setMetricValue(jsonObject.get("metricValue").getAsInt());
            mqPredictionEntry.setProbability(jsonObject.get("probability").getAsDouble());
            mqPredictionEntry.setPredictionTime(jsonObject.get("predictionTime").getAsLong());

            mqPredictionEntry.setCloud(jsonObject.get("cloud").getAsString());
            mqPredictionEntry.setLevel(jsonObject.get("level").getAsInt());
            mqPredictionEntry.setProvider(jsonObject.get("provider").getAsString());
            mqPredictionEntry.setRefersTo(jsonObject.get("refersTo").getAsString());
            mqPredictionEntry.setTimestamp(activeMQMessage.getTimestamp());

            mqAdapterStatusHolder.addExtractedMetricDescription(mqPredictionEntry.getName(), new ExtractedMetricsDescriptions(mqPredictionEntry.getClass().toString(), mqPredictionEntry.toString()));

            return mqPredictionEntry;
        });
    }
}
