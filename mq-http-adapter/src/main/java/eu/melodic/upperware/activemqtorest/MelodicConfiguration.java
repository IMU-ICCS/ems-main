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

	@Value("${influxdb.retainer.enabled:true}")
	private boolean influxRetainerEnabled;

	@Value("${influxdb.retainer.expiry:180}")
	private long influxRetainerExpiry;

	@Value("${influxdb.retainer.heap.entries:2000}")
	private long influxRetainerHeapEntries;

	@Value("${influxdb.read.timeout:10}")
	private long influxReadTimeout;

	@Value("${influxdb.write.timeout:10}")
	private long influxWriteTimeout;

	@Value("${mq.topic.threshold.name:_ui_threshold_info}")
	private String mqTopicThresholdName;

	@Value("${mq.topic.instanceinfo.name:_ui_instance_info}")
	private String mqTopicInstanceInfoName;

}
