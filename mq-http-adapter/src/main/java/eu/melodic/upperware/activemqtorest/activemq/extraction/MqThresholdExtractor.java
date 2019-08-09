package eu.melodic.upperware.activemqtorest.activemq.extraction;

import java.util.Optional;

import org.apache.activemq.command.ActiveMQMessage;
import org.springframework.stereotype.Component;

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
		return extractJsonPayload(activeMQMessage).map(jsonObject -> {
			MqThresholdEntry mqThresholdEntry = new MqThresholdEntry();
			mqThresholdEntry.setName(jsonObject.get("name").getAsString());
			mqThresholdEntry.setOperator(jsonObject.get("operator").getAsString());
			mqThresholdEntry.setThreshold(jsonObject.get("threshold").getAsString());
			mqThresholdEntry.setTimestamp(String.valueOf(activeMQMessage.getTimestamp()));
			return mqThresholdEntry;
		});
	}
}
