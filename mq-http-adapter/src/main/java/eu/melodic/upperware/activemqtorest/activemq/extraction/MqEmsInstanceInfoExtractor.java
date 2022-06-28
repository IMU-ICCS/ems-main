package eu.melodic.upperware.activemqtorest.activemq.extraction;

import java.util.Optional;

import eu.melodic.upperware.activemqtorest.MorphemicTopicsMatcher;
import org.apache.activemq.command.ActiveMQMessage;
import org.springframework.stereotype.Component;

import eu.melodic.upperware.activemqtorest.entry.ExtractedMetricsDescriptions;
import eu.melodic.upperware.activemqtorest.entry.MqBaseEntry;
import eu.melodic.upperware.activemqtorest.entry.MqInstanceInfoEntry;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MqEmsInstanceInfoExtractor extends MqDataEntryBaseExtractor implements IMqDataEntryExtractor {
	@Override
	public boolean isApplicable(ActiveMQMessage activeMQMessage) {
		return MorphemicTopicsMatcher.isInstanceInfoTopic(activeMQMessage.getJMSDestination().toString());
	}

	@Override
	public Optional<MqBaseEntry> extractMqDataEntry(ActiveMQMessage activeMQMessage) {
		return extractJsonPayload(activeMQMessage).map(jsonObject -> {
			MqInstanceInfoEntry mqInstanceInfoEntry = new MqInstanceInfoEntry();
			mqInstanceInfoEntry.setClientId(jsonObject.get("clientId").getAsString());
			mqInstanceInfoEntry.setIpAddress(jsonObject.get("ipAddress").getAsString());
			mqInstanceInfoEntry.setReference(jsonObject.get("reference").getAsString());
			mqInstanceInfoEntry.setState(jsonObject.get("state").getAsString());
			mqInstanceInfoEntry.setStateLastUpdate(jsonObject.get("stateLastUpdate").getAsString());
			mqInstanceInfoEntry.setTimestamp(String.valueOf(activeMQMessage.getTimestamp()));

			mqAdapterStatusHolder.addExtractedMetricDescription(mqInstanceInfoEntry.getReference(), new ExtractedMetricsDescriptions(mqInstanceInfoEntry.getClass().toString(), mqInstanceInfoEntry.toString()));

			return mqInstanceInfoEntry;
		});
	}
}
