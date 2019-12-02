package eu.melodic.upperware.activemqtorest.activemq.extraction;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.jms.JMSException;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import eu.melodic.upperware.activemqtorest.activemq.MqConstants;
import eu.melodic.upperware.activemqtorest.entry.MqBaseEntry;
import eu.melodic.upperware.activemqtorest.entry.MqDefaultMetricEntry;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MqDefaultMetricExtractor extends MqDataEntryBaseExtractor implements IMqDataEntryExtractor {

	@Override
	public boolean isApplicable(ActiveMQMessage activeMQMessage) {
		return !activeMQMessage.getJMSDestination().toString().contains(melodicConfiguration.getMqTopicInstanceInfoName()) &&
				!activeMQMessage.getJMSDestination().toString().contains(melodicConfiguration.getMqTopicThresholdName());
	}

	@Override
	public Optional<MqBaseEntry> extractMqDataEntry(ActiveMQMessage activeMQMessage) {
		log.debug("Extracting MqBaseEntry");
		String rawMqContent = extractPayload(new String(activeMQMessage.getContent().getData()));
		String[] keyValuePairsAsStrings = rawMqContent.split(MqConstants.KEY_VALUE_PAIR_SEPARATOR);
		String keyValueEncoding = extractUsedSeparator(keyValuePairsAsStrings);

		HashMap<String, String> keyValueMap = Arrays.stream(keyValuePairsAsStrings)//
				.map(s -> s.split(keyValueEncoding))//
				.collect(Collectors.toMap(keyValuePairs -> normalizeMqString(keyValuePairs[0]), keyValuePairs -> normalizeMqString(keyValuePairs[1]), (a, b) -> b, Maps::newHashMap));

		MqDefaultMetricEntry mqDataEntry = new MqDefaultMetricEntry();
		mqDataEntry.setLevel(keyValueMap.getOrDefault(MqConstants.LEVEL, MqConstants.DEFAULT_VALUE_WHEN_EMPTY));
		mqDataEntry.setValue(keyValueMap.get(MqConstants.VALUE));
		mqDataEntry.setTimestamp(keyValueMap.get(MqConstants.TIMESTAMP));
		mqDataEntry.setVmName(keyValueMap.getOrDefault(MqConstants.VM_NAME, StringUtils.EMPTY));
		String topic = activeMQMessage.getJMSDestination().toString().replace(MqConstants.TOPIC_PREFIX, StringUtils.EMPTY);
		mqDataEntry.setTopic(topic);

		activeMqStatisticHolder.addExtractedMetricDescription(mqDataEntry.getTopic(), mqDataEntry.getTimestamp());

		String producerHost = MqConstants.PRODUCER_HOST_DEFAULT_VALUE;
		try {
			producerHost = (String)activeMQMessage.getProperty(MqConstants.PRODUCER_HOST);
		} catch (IOException e) {
			log.error("Could not resolve '{}' on incoming message.", MqConstants.PRODUCER_HOST);
		}
		mqDataEntry.setProducer(producerHost);

		try {
			mqDataEntry.setSourceIpAddress(activeMQMessage.getStringProperty(MqConstants.PRODUCER_HOST));
		} catch (JMSException e) {
			log.warn("Could not resolve property '{}'.", MqConstants.PRODUCER_HOST);
		}

		return Optional.of(mqDataEntry);
	}
}
