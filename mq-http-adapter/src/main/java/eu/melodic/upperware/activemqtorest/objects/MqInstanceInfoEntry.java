package eu.melodic.upperware.activemqtorest.objects;

import java.util.concurrent.TimeUnit;

import org.influxdb.dto.Point;

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
public class MqInstanceInfoEntry extends MqBaseEntry {

	private long id = 42l;
	private String name;
	private String random;
	private String ipAddress;
	private String type;
	private String os;
	private String baguetteClientId;
	private String timestamp;
	private String providerId;
	private String instanceId;


	@Override
	public Point getInfluxDbDataPoint(IIpGeoCoder ipGeoCoder) {
		String timestamp = normalizeTimestamp(getTimestamp());
		Point point = Point.measurement("_Instances")
				.time(Long.valueOf(timestamp), TimeUnit.MILLISECONDS)
				.addField("name", getName())
				.addField("random", getRandom())
				.addField("ipAddress", getIpAddress())
				.addField("countryCode", ipGeoCoder.getCountryCode(getIpAddress()))
				.addField("type", getType())
				.addField("os", getOs())
				.addField("baguetteClientId", getBaguetteClientId())
				.addField("providerId", getProviderId())
				.addField("instanceId", getInstanceId())
				.build();
		return point;
	}

}
