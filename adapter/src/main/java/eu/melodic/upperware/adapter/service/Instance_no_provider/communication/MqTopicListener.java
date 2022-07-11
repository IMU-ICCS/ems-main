package eu.melodic.upperware.adapter.service.Instance_no_provider.communication;


import eu.melodic.event.brokerclient.BrokerClient;
import eu.melodic.upperware.adapter.properties.AdapterProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MqTopicListener {
	private final AdapterProperties adapterProperties;
	private final BusyInstanceMqListener busyInstanceMqListener;

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		try {
			BrokerClient brokerClient = BrokerClient.newClient();
			waitForActiveMq(brokerClient);
			String topicName = adapterProperties.getActiveMq().getCheckIfComponentBusyActiveMQTopic();
			brokerClient.receiveEvents(null, topicName, busyInstanceMqListener);
		} catch (Exception e) {
			log.error("Error while using BrokerClient.", e);
			restartAfterMqFailure();
		}
		log.info("MqTopicListener up and running..");
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
				adapterProperties.getActiveMq().getMelodicMqRestartInterval()
		);
	}

	private void waitForActiveMq(BrokerClient brokerClient) {
		long RETRY_MAX = adapterProperties.getActiveMq().getMelodicMqConnectionRetryMax();
		for (int retryCount = 1; retryCount <= RETRY_MAX; retryCount++) {
			try {
				brokerClient.openConnection();
			} catch (JMSException e) {
				log.error("Error while initiating connection with MQ. Retry {} of {}", retryCount, RETRY_MAX);
				try {
					Thread.sleep(adapterProperties.getActiveMq().getMelodicMqConnectionRetryInterval());
				} catch (InterruptedException ex) {
				}
				continue;
			}
			break;
		}
	}

}
