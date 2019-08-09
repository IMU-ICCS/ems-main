package eu.melodic.upperware.activemqtorest.activemq.extraction;

import java.util.Optional;

import org.apache.activemq.command.ActiveMQMessage;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;

import eu.melodic.upperware.activemqtorest.objects.MqBaseEntry;
import eu.melodic.upperware.activemqtorest.objects.MqInstanceInfoEntry;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MqInstanceInfoExtractor extends MqDataEntryBaseExtractor implements IMqDataEntryExtractor {
	@Override
	public boolean isApplicable(ActiveMQMessage activeMQMessage) {
		return activeMQMessage.getJMSDestination().toString().contains(melodicConfiguration.getMqTopicInstanceInfoName());
	}

	@Override
	public Optional<MqBaseEntry> extractMqDataEntry(ActiveMQMessage activeMQMessage) {
		return extractJsonPayload(activeMQMessage).map(jsonObject -> {
			MqInstanceInfoEntry mqInstanceInfoEntry = new MqInstanceInfoEntry();
			mqInstanceInfoEntry.setBaguetteClientId(jsonObject.get("baguette-client-id").getAsString());
			mqInstanceInfoEntry.setOs(jsonObject.get("operatingSystem").getAsString());
			mqInstanceInfoEntry.setType(jsonObject.get("type").getAsString());
			mqInstanceInfoEntry.setName(jsonObject.get("name").getAsString());
			mqInstanceInfoEntry.setIpAddress(jsonObject.get("ip").getAsString());
			mqInstanceInfoEntry.setRandom(jsonObject.get("random").getAsString());
			mqInstanceInfoEntry.setInstanceId(jsonObject.get("id").getAsString());
			mqInstanceInfoEntry.setProviderId(jsonObject.get("providerId").getAsString());
			mqInstanceInfoEntry.setTimestamp(String.valueOf(activeMQMessage.getTimestamp()));
			return mqInstanceInfoEntry;
		});
	}
}
