package eu.melodic.upperware.activemqtorest.entry;

import java.util.concurrent.TimeUnit;

import org.influxdb.dto.Point;

import com.google.common.base.Strings;

import eu.melodic.upperware.activemqtorest.influxdb.InfluxDataRetainer;
import eu.melodic.upperware.activemqtorest.influxdb.geolocation.IIpGeoCoder;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class MqInstanceInfoEntry extends MqBaseEntry {

	@EqualsAndHashCode.Exclude
	private long id = 42l;
	@EqualsAndHashCode.Exclude
	private String random;
	private String name;
	private String ipAddress;
	@EqualsAndHashCode.Exclude
	private String type;
	@EqualsAndHashCode.Exclude
	private String os;
	private String baguetteClientId;
	@EqualsAndHashCode.Exclude
	private String timestamp;
	@EqualsAndHashCode.Exclude
	private String providerId;
	private String instanceId;


	@Override
	public Point getInfluxDbDataPoint(IIpGeoCoder ipGeoCoder) {
		String timestamp = normalizeTimestamp(getTimestamp());
		Point point = Point.measurement("_Instances")
				.time(Long.valueOf(timestamp), TimeUnit.MILLISECONDS)
				.addField("name", Strings.nullToEmpty(getName()))
				.addField("random", Strings.nullToEmpty(getRandom()))
				.addField("ipAddress", Strings.nullToEmpty(getIpAddress()))
				.addField("countryCode", ipGeoCoder.getCountryCode(getIpAddress()))
				.addField("type", Strings.nullToEmpty(getType()))
				.addField("os", Strings.nullToEmpty(getOs()))
				.addField("baguetteClientId", Strings.nullToEmpty(getBaguetteClientId()))
				.addField("providerId", Strings.nullToEmpty(getProviderId()))
				.addField("instanceId", Strings.nullToEmpty(getInstanceId()))
				.build();
		return point;
	}

	@Override
	public boolean mustRetain(InfluxDataRetainer influxDataRetainer) {
		return influxDataRetainer.getInstanceInfoEntryCache().containsKey(this.hashCode());
	}

	@Override
	public void updateRetained(InfluxDataRetainer influxDataRetainer) {
		influxDataRetainer.getInstanceInfoEntryCache().put(this.hashCode(), this);
	}

}
