package eu.melodic.upperware.activemqtorest.entry;

import java.util.concurrent.TimeUnit;

import org.influxdb.dto.Point;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

import eu.melodic.upperware.activemqtorest.influxdb.geolocation.IIpGeoCoder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class MqDefaultMetricEntry extends MqBaseEntry {
	private long id = 42l;
	private String topic;
	private String level;
	private String value;
	private String timestamp;
	private String producer;
	private String sourceIpAddress;
	private String vmName;


	@Override
	public Point getInfluxDbDataPoint(IIpGeoCoder ipGeoCoder) {
		String timestamp = normalizeTimestamp(getTimestamp());
		Point point = Point.measurement(getTopic())
				.time(Long.valueOf(timestamp), TimeUnit.MILLISECONDS)
				.addField("value", Double.valueOf(getValue()))
				.tag("level", getLevel())
				.tag("producer", getProducer())
				.tag("vmName", getVmName())
				.tag("ipAddress", Strings.nullToEmpty(getSourceIpAddress()))
				.tag("countryCode", ipGeoCoder.getCountryCode(getSourceIpAddress()))
				.build();
		return point;
	}

}
