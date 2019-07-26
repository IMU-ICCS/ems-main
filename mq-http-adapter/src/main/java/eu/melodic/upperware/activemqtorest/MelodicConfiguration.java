package eu.melodic.upperware.activemqtorest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MelodicConfiguration {

	@Value("${activemq.broker.url}")
	private String melodicMqAddress;

	@Value("${influxdb.url}")
	private String activeMqBrokerAddress;

	@Value("${influxdb.database}")
	private String databaseName;

	public String getMelodicMqAddress() {
		return melodicMqAddress;
	}

	public String getActiveMqBrokerAddress() {
		return activeMqBrokerAddress;
	}

	public String getDatabaseName() {
		return databaseName;
	}
}
