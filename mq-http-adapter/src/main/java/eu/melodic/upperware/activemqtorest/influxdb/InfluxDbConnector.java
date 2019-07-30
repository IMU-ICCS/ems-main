package eu.melodic.upperware.activemqtorest.influxdb;

import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import eu.melodic.upperware.activemqtorest.MelodicConfiguration;
import eu.melodic.upperware.activemqtorest.objects.MqDataEntry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InfluxDbConnector {

	private InfluxDB influxDB;

	@Autowired
	private MelodicConfiguration melodicConfiguration;

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		influxDB = InfluxDBFactory.connect(melodicConfiguration.getActiveMqBrokerAddress());
		log.info("Connected to {}, will use database '{}'", melodicConfiguration.getActiveMqBrokerAddress(), melodicConfiguration.getDatabaseName());
	}

	public void writeDataPoint(MqDataEntry mqDataEntry) {

		String timestamp = mqDataEntry.getTimestamp();

		if (timestamp.contains("E")) {
			log.warn("Unsupported timestamp format in mqDataEntry={}", mqDataEntry);
			timestamp = String.format("%.0f", Double.parseDouble(timestamp));
			timestamp = timestamp.substring(0, 13);
			log.warn("Corrected timestamp to '{}'", timestamp);
		}

		Point point = Point.measurement(mqDataEntry.getTopic())
				.time(Long.valueOf(timestamp), TimeUnit.MILLISECONDS)
				.addField("value", Double.valueOf(mqDataEntry.getValue()))
				.addField("level", mqDataEntry.getLevel() == null ? 0.0 : Double.parseDouble(mqDataEntry.getLevel()))
				.addField("producer", mqDataEntry.getProducer())
				.build();

		influxDB.write(melodicConfiguration.getDatabaseName(), "", point);
	}

}
