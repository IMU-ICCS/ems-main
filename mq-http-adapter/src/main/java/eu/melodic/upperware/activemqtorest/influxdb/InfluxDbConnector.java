package eu.melodic.upperware.activemqtorest.influxdb;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import eu.melodic.upperware.activemqtorest.MelodicConfiguration;
import eu.melodic.upperware.activemqtorest.influxdb.geolocation.IIpGeoCoder;
import eu.melodic.upperware.activemqtorest.objects.MqBaseEntry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InfluxDbConnector {

	private InfluxDB influxDB;

	@Autowired
	private MelodicConfiguration melodicConfiguration;

	@Autowired
	private IIpGeoCoder ipGeoCoder;

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		influxDB = InfluxDBFactory.connect(melodicConfiguration.getActiveMqBrokerAddress());
		log.info("Connected to {}, will use database '{}'", melodicConfiguration.getActiveMqBrokerAddress(), melodicConfiguration.getDatabaseName());
	}

	public void writeDataPoint(MqBaseEntry mqDataEntry) {

		Point influxDbDataPoint = mqDataEntry.getInfluxDbDataPoint(ipGeoCoder);

		influxDB.write(melodicConfiguration.getDatabaseName(), "", influxDbDataPoint);
	}

}
