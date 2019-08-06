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
				brokerClient.receiveEvents(melodicConfiguration.getMelodicMqAddress(), "*", message -> {
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
		for (int i = 0; i < RETRY_MAX; i++) {
			try {
				brokerClient.openConnection(melodicConfiguration.getMelodicMqAddress());
			} catch (JMSException e) {
				log.error("Error while initiating connection with MQ. Retry {} of {}", i, RETRY_MAX);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException ex) {
				}
				continue;
			}
			break;
		}
	}

	private MqDataEntry createDataEntry(ActiveMQMessage am) {
		String contentAsString = new String(am.getContent().getData());
		String rawMqContent = StringUtils.substringBetween(contentAsString, "{", "}");

		String[] keyValuePairsAsStrings = rawMqContent.split(",");

		HashMap<String, String> keyValueMap = Arrays.stream(keyValuePairsAsStrings)//
				.map(s -> s.split("="))//
				.collect(Collectors.toMap(keyValuePairs -> keyValuePairs[0].trim(), keyValuePairs -> keyValuePairs[1].trim(), (a, b) -> b, Maps::newHashMap));

		MqDataEntry mqDataEntry = new MqDataEntry();
		mqDataEntry.setLevel(keyValueMap.get("level"));
		mqDataEntry.setValue(keyValueMap.get("metricValue"));
		mqDataEntry.setTimestamp(keyValueMap.get("timestamp"));
		String topic = am.getJMSDestination().toString().replace("topic://", "");
		mqDataEntry.setTopic(topic);

		String connectionId = am.getProducerId().getConnectionId();
		mqDataEntry.setProducer(connectionId);

		return mqDataEntry;
	}

	private void logRawValues(ActiveMQMessage am) {
		log.info("Retrieved raw ActiveMQMessage with content = {}", am.toString());
	}

}