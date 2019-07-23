package de.cas.dcsresearch.melodic.activemqtorest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MelodicConfiguration {

	@Value("${activemq.broker.url}")
	private String melodicMqAddress;

	@Value("${influxdb.url}")
	private String activeMqBrokerAddress;

	public String getMelodicMqAddress() {
		return melodicMqAddress;
	}

	public String getActiveMqBrokerAddress() {
		return activeMqBrokerAddress;
	}

}
