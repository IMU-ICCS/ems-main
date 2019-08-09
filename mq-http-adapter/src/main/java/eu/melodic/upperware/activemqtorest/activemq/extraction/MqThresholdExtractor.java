package eu.melodic.upperware.activemqtorest.activemq.extraction;

import java.util.Optional;

import org.apache.activemq.command.ActiveMQMessage;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;

import eu.melodic.upperware.activemqtorest.objects.MqBaseEntry;
import eu.melodic.upperware.activemqtorest.objects.MqThresholdEntry;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MqThresholdExtractor extends MqDataEntryBaseExtractor implements IMqDataEntryExtractor {
	@Override
	public boolean isApplicable(ActiveMQMessage activeMQMessage) {
		return activeMQMessage.getJMSDestination().toString().contains(melodicConfiguration.getMqTopicThresholdName());
	}

	@Override
	public Optional<MqBaseEntry> extractMqDataEntry(ActiveMQMessage activeMQMessage) {
		Optional<JsonObject> jsonObject = extractJsonPayload(activeMQMessage);
		if (jsonObject.isPresent()) {
			MqThresholdEntry mqThresholdEntry = new MqThresholdEntry();
			mqThresholdEntry.setName(jsonObject.get().get("name").getAsString());
			mqThresholdEntry.setOperator(jsonObject.get().get("operator").getAsString());
			mqThresholdEntry.setThreshold(jsonObject.get().get("threshold").getAsString());
			mqThresholdEntry.setTimestamp(String.valueOf(activeMQMessage.getTimestamp()));
			return Optional.of(mqThresholdEntry);
		} else {
			return Optional.empty();
		}
	}
}
