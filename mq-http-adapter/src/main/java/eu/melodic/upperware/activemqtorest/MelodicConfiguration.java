package eu.melodic.upperware.activemqtorest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;


@Configuration
@Getter
public class MelodicConfiguration {

	@Value("${activemq.broker.url}")
	private String melodicMqAddress;

	@Value("${activemq.restartinterval:10000}")
	private long melodicMqRestartInterval;

	@Value("${activemq.connectionretryinterval:5000}")
	private long melodicMqConnectionRetryInterval;

	@Value("${activemq.connectionretrymax:10}")
	private long melodicMqConnectionRetryMax;

	@Value("${influxdb.url}")
	private String activeMqBrokerAddress;

	@Value("${influxdb.database}")
	private String databaseName;

}
