package de.cas.dcsresearch.melodic.activemqtorest.activemq;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.jms.JMSException;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import de.cas.dcsresearch.melodic.activemqtorest.MelodicConfiguration;
import de.cas.dcsresearch.melodic.activemqtorest.influxdb.InfluxDbConnector;
import de.cas.dcsresearch.melodic.activemqtorest.objects.MqDataEntry;
import eu.melodic.event.brokerclient.BrokerClient;


@Service
public class MqTopicListener {

	private static Logger logger = LoggerFactory.getLogger(MqTopicListener.class);

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
				brokerClient.receiveEvents(melodicConfiguration.getMelodicMqAddress(), "*", message -> {
					ActiveMQMessage activeMQMessage = (ActiveMQMessage) message;
					logRawValues(activeMQMessage);

					MqDataEntry dataPoint = createDataEntry(activeMQMessage);
					influxDbConnector.writeDataPoint(dataPoint);

					activeMqStatisticHolder.increaseMsgCount();
				});
			} catch (JMSException | IOException e) {
				activeMqStatisticHolder.setHasError();
			}
		});
		brokerThread.start();
		logger.info("MqTopicListener up and running..");
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
		logger.info("Retrieved raw ActiveMQMessage with content = {}", am.toString());
	}

}