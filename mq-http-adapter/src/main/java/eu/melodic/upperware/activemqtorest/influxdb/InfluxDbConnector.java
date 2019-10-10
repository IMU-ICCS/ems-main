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
import eu.melodic.upperware.activemqtorest.entry.MqBaseEntry;
import eu.melodic.upperware.activemqtorest.influxdb.geolocation.IIpGeoCoder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

@Slf4j
@Component
public class InfluxDbConnector {

	private InfluxDB influxDB;

	@Autowired
	private MelodicConfiguration melodicConfiguration;

	@Autowired
	private IIpGeoCoder ipGeoCoder;

	@Autowired
	private InfluxDataRetainer influxDataRetainer;

	private boolean isReady;

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		OkHttpClient.Builder okHttpbuilder = new OkHttpClient.Builder();
		okHttpbuilder.readTimeout(melodicConfiguration.getInfluxReadTimeout(), TimeUnit.SECONDS);
		okHttpbuilder.writeTimeout(melodicConfiguration.getInfluxWriteTimeout(), TimeUnit.SECONDS);

		influxDB = InfluxDBFactory.connect(melodicConfiguration.getActiveMqBrokerAddress(), okHttpbuilder);
		isReady = true;
		log.info("Connected to {}, will use database '{}'", melodicConfiguration.getActiveMqBrokerAddress(), melodicConfiguration.getDatabaseName());
	}

	public void writeMqDataEntry(MqBaseEntry mqDataEntry) {
		Point influxDbDataPoint = mqDataEntry.getInfluxDbDataPoint(ipGeoCoder);
		if (melodicConfiguration.isInfluxRetainerEnabled() && mqDataEntry.mustRetain(influxDataRetainer)) {
			log.debug("Retaining data point {}.", influxDbDataPoint);
		} else {
			log.debug("Writing data point {}.", influxDbDataPoint);
			try {
				influxDB.write(melodicConfiguration.getDatabaseName(), "", influxDbDataPoint);
			} catch (Exception e) {
				log.error("Could not write to InfluxDB. Ignoring data point '{}'", influxDbDataPoint, e);
			}
		}
		mqDataEntry.updateRetained(influxDataRetainer);
	}

	public void writeNonRetainableDataPoint(Point influxDbDataPoint) {
		influxDB.write(melodicConfiguration.getDatabaseName(), "", influxDbDataPoint);
	}

	public boolean isReady() {
		return isReady;
	}
}
