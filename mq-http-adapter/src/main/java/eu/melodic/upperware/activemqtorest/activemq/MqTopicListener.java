package eu.melodic.upperware.activemqtorest.activemq;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.jms.JMSException;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import eu.melodic.event.brokerclient.BrokerClient;
import eu.melodic.upperware.activemqtorest.MelodicConfiguration;
import eu.melodic.upperware.activemqtorest.influxdb.InfluxDbConnector;
import eu.melodic.upperware.activemqtorest.objects.MqDataEntry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MqTopicListener {

	@Autowired
	private InfluxDbConnector influxDbConnector;

	@Autowired
	private MelodicConfiguration melodicConfiguration;

	@Autowired
	private ActiveMqStatisticHolder activeMqStatisticHolder;

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		Thread brokerThread = new Thread(() -> {
			try {
				BrokerClient brokerClient = BrokerClient.newClient();
				waitForActiveMq(brokerClient);
				brokerClient.receiveEvents(melodicConfiguration.getMelodicMqAddress(), MqConstants.ALL_DESTINATIONS, message -> {
					ActiveMQMessage activeMQMessage = (ActiveMQMessage) message;
					logRawValues(activeMQMessage);

					MqDataEntry dataPoint = createDataEntry(activeMQMessage);
					influxDbConnector.writeDataPoint(dataPoint);

					activeMqStatisticHolder.increaseMsgCount();
				});
			} catch (JMSException | IOException e) {
				log.error("Error while using BrokerCLient", e);
				activeMqStatisticHolder.setHasError();
			}
		});
		brokerThread.start();
		log.info("MqTopicListener up and running..");
	}

	private void waitForActiveMq(BrokerClient brokerClient) {
		int RETRY_MAX = 10;
		for (int retryCount = 1; retryCount <= RETRY_MAX; retryCount++) {
			try {
				brokerClient.openConnection(melodicConfiguration.getMelodicMqAddress());
			} catch (JMSException e) {
				log.error("Error while initiating connection with MQ. Retry {} of {}", retryCount, RETRY_MAX);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException ex) {
				}
				continue;
			}
			break;
		}
	}

	private MqDataEntry createDataEntry(ActiveMQMessage activeMQMessage) {
		String rawMqContent = extractPayload(new String(activeMQMessage.getContent().getData()));
		String[] keyValuePairsAsStrings = rawMqContent.split(MqConstants.KEY_VALUE_PAIR_SEPARATOR);
		String keyValueEncoding = extractUsedSeparator(keyValuePairsAsStrings);

		HashMap<String, String> keyValueMap = Arrays.stream(keyValuePairsAsStrings)//
				.map(s -> s.split(keyValueEncoding))//
				.collect(Collectors.toMap(keyValuePairs -> normalizeMqString(keyValuePairs[0]), keyValuePairs -> normalizeMqString(keyValuePairs[1]), (a, b) -> b, Maps::newHashMap));

		MqDataEntry mqDataEntry = new MqDataEntry();
		mqDataEntry.setLevel(keyValueMap.getOrDefault(MqConstants.LEVEL, "0"));
		mqDataEntry.setValue(keyValueMap.get(MqConstants.VALUE));
		mqDataEntry.setTimestamp(keyValueMap.get(MqConstants.TIMESTAMP));
		mqDataEntry.setVmName(keyValueMap.getOrDefault("vmName", ""));
		String topic = activeMQMessage.getJMSDestination().toString().replace(MqConstants.TOPIC_PREFIX, "");
		mqDataEntry.setTopic(topic);
		String connectionId = activeMQMessage.getProducerId().getConnectionId();
		mqDataEntry.setProducer(connectionId);

		try {
			mqDataEntry.setSourceIpAddress(activeMQMessage.getStringProperty("producer-host"));
		} catch (JMSException e) {
			log.warn("Could not resolve property 'producer-host'.");
		}

		return mqDataEntry;
	}

	private String extractUsedSeparator(String[] keyValuePairs) {
		int delimiterConsistentCounter = (int) Arrays.stream(keyValuePairs).filter(string -> string.contains(MqConstants.VALUE_SEPARATOR_JSON)).count();
		if (delimiterConsistentCounter == keyValuePairs.length) {
			return MqConstants.VALUE_SEPARATOR_JSON;
		}
		return MqConstants.VALUE_SEPARATOR_DEFAULT;
	}

	private String normalizeMqString(String mqString) {
		return mqString.trim().replaceAll("\"", "");
	}

	private String extractPayload(String rawPayload) {
		return StringUtils.substringBetween(rawPayload, "{", "}");
	}

	private void logRawValues(ActiveMQMessage am) {
		log.info("Retrieved raw ActiveMQMessage with content = {}", am.toString());
	}

}