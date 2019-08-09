package eu.melodic.upperware.activemqtorest.objects;

import org.influxdb.dto.Point;

import eu.melodic.upperware.activemqtorest.influxdb.geolocation.IIpGeoCoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class MqBaseEntry {

	public abstract Point getInfluxDbDataPoint(IIpGeoCoder ipGeoCoder);

	String normalizeTimestamp(String timestamp) {
		if (timestamp.contains("E")) {
			log.warn("Unsupported timestamp format in mqDataEntry={}", this);
			timestamp = String.format("%.0f", Double.parseDouble(timestamp));
			timestamp = timestamp.substring(0, 13);
			log.warn("Corrected timestamp to '{}'", timestamp);
		}
		return timestamp;
	}

}
