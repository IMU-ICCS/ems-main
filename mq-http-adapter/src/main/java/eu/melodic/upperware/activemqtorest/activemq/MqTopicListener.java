package eu.melodic.upperware.activemqtorest.activemq;

import java.util.Optional;
import java.util.Set;

import javax.jms.JMSException;

import org.apache.activemq.command.ActiveMQMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import eu.melodic.event.brokerclient.BrokerClient;
import eu.melodic.upperware.activemqtorest.MelodicConfiguration;
import eu.melodic.upperware.activemqtorest.activemq.extraction.IMqDataEntryExtractor;
import eu.melodic.upperware.activemqtorest.influxdb.InfluxDbConnector;
import eu.melodic.upperware.activemqtorest.entry.MqBaseEntry;
import eu.melodic.upperware.activemqtorest.entry.MqDefaultMetricEntry;
import io.github.cloudiator.rest.api.NodeApi;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MqTopicListener {

	@Autowired
	private InfluxDbConnector influxDbConnector;

	@Autowired
	private MelodicConfiguration melodicConfiguration;

	@Autowired
	private MqAdapterStatusHolder mqAdapterStatusHolder;

	@Autowired
	private Set<IMqDataEntryExtractor> mqDataEntryExtractors;

	@Autowired
	private NodeApi nodeApi;

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		Thread brokerThread = new Thread(() -> {
			try {
				BrokerClient brokerClient = BrokerClient.newClient();
				waitForActiveMq(brokerClient);
				brokerClient.receiveEvents(melodicConfiguration.getMelodicMqAddress(), MqConstants.ALL_DESTINATIONS, message -> {
					ActiveMQMessage activeMQMessage = (ActiveMQMessage) message;
					logRawValues(activeMQMessage);
					Optional<MqBaseEntry> dataPoint = createDataEntry(activeMQMessage);

					if (dataPoint.isPresent()) {
						influxDbConnector.writeMqDataEntry(dataPoint.get());
						mqAdapterStatusHolder.increaseMsgCount();
					} else {
						mqAdapterStatusHolder.increaseErrorCount();
						log.warn("Could not extract incoming message.");
					}
				});
			} catch (Exception e) {
				log.error("Error while using BrokerCLient.", e);
				restartAfterMqFailure();
			}
		});
		brokerThread.start();
		log.info("MqTopicListener up and running..");
	}

	private Optional<MqBaseEntry> createDataEntry(ActiveMQMessage activeMQMessage) {
		return mqDataEntryExtractors.stream().filter(iMqDataEntryExtractor -> iMqDataEntryExtractor.isApplicable(activeMQMessage))
				.findFirst().map(iMqDataEntryExtractor -> iMqDataEntryExtractor.extractMqDataEntry(activeMQMessage))
				.orElse(Optional.of(new MqDefaultMetricEntry()));
	}

	private void restartAfterMqFailure() {
		new java.util.Timer().schedule(
				new java.util.TimerTask() {
					@Override
					public void run() {
						log.info("Restarting MQTopicListener..");
						onApplicationReady();
					}
				},
				melodicConfiguration.getMelodicMqRestartInterval()
		);
	}

	private void waitForActiveMq(BrokerClient brokerClient) {
		long RETRY_MAX = melodicConfiguration.getMelodicMqConnectionRetryMax();
		for (int retryCount = 1; retryCount <= RETRY_MAX; retryCount++) {
			try {
				brokerClient.openConnection(melodicConfiguration.getMelodicMqAddress());
			} catch (JMSException e) {
				log.error("Error while initiating connection with MQ. Retry {} of {}", retryCount, RETRY_MAX);
				try {
					Thread.sleep(melodicConfiguration.getMelodicMqConnectionRetryInterval());
				} catch (InterruptedException ex) {
				}
				continue;
			}
			break;
		}
	}

	void logRawValues(ActiveMQMessage am) {
		log.info("Retrieved raw ActiveMQMessage with content = {}", am.toString());
	}

}