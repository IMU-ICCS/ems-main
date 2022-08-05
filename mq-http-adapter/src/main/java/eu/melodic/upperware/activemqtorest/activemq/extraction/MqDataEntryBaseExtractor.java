package eu.melodic.upperware.activemqtorest.activemq.extraction;

import java.util.Arrays;
import java.util.Optional;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import eu.melodic.upperware.activemqtorest.MelodicConfiguration;
import eu.melodic.upperware.activemqtorest.activemq.MqAdapterStatusHolder;
import eu.melodic.upperware.activemqtorest.activemq.MqConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class MqDataEntryBaseExtractor {

	@Autowired
	MelodicConfiguration melodicConfiguration;

	@Autowired
	MqAdapterStatusHolder mqAdapterStatusHolder;

	String extractUsedSeparator(String[] keyValuePairs) {
		int delimiterConsistentCounter = (int) Arrays.stream(keyValuePairs).filter(string -> string.contains(MqConstants.VALUE_SEPARATOR_JSON)).count();
		if (delimiterConsistentCounter == keyValuePairs.length) {
			return MqConstants.VALUE_SEPARATOR_JSON;
		}
		return MqConstants.VALUE_SEPARATOR_DEFAULT;
	}

	String normalizeMqString(String mqString) {
		return mqString.trim().replaceAll("\"", "");
	}

	String extractPayload(String rawPayload) {
		return StringUtils.substringBetween(rawPayload, "{", "}");
	}

	Optional<JsonObject> extractJsonPayload(ActiveMQMessage activeMQMessage) {
        String rawPayload = new String(activeMQMessage.getContent().getData());
        int messageBegin = rawPayload.indexOf(MqConstants.META_MESSAGE_IDENTIFIER);
        int messageEnd = rawPayload.indexOf(MqConstants.META_MESSAGE_END_IDENTIFIER);
        String payload = rawPayload.substring(messageBegin + MqConstants.META_MESSAGE_IDENTIFIER.length(), messageEnd + 1);
        log.debug("Payload: {}", payload);
		try {
			JsonObject jsonObject = new JsonParser().parse(payload).getAsJsonObject();
			return Optional.of(jsonObject);
		} catch (JsonSyntaxException x) {
			return Optional.empty();
		}
	}

}
