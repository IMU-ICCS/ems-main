package de.cas.dcsresearch.melodic.activemqtorest.influxdb;

import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import de.cas.dcsresearch.melodic.activemqtorest.MelodicConfiguration;
import de.cas.dcsresearch.melodic.activemqtorest.objects.MqDataEntry;

@Component
public class InfluxDbConnector {

	private static Logger logger = LoggerFactory.getLogger(InfluxDbConnector.class);

	private InfluxDB influxDB;

	@Autowired
	private MelodicConfiguration melodicConfiguration;

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		influxDB = InfluxDBFactory.connect(melodicConfiguration.getActiveMqBrokerAddress());
	}

	public void writeDataPoint(MqDataEntry mqDataEntry) {

		String timestamp = mqDataEntry.getTimestamp();

		if (timestamp.contains("E")) {
			logger.warn("Unsupported timestamp format in mqDataEntry={}", mqDataEntry);
			timestamp = String.format("%.0f", Double.parseDouble(timestamp));
			timestamp = timestamp.substring(0, 13);
			logger.warn("Corrected timestamp to '{}'", timestamp);
		}

		Point point = Point.measurement(mqDataEntry.getTopic())
				.time(Long.valueOf(timestamp), TimeUnit.MILLISECONDS)
				.addField("value", Double.valueOf(mqDataEntry.getValue()))
				.addField("level", mqDataEntry.getLevel() == null ? 0.0 : Double.parseDouble(mqDataEntry.getLevel()))
				.addField("producer", mqDataEntry.getProducer())
				.build();
		influxDB.write("melodic_ui", "", point);
	}

}
