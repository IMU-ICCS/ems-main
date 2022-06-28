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
	private String ipAddress;
	private String clientId;
	@EqualsAndHashCode.Exclude
	private String timestamp;
	@EqualsAndHashCode.Exclude
	private String reference;
	private String state;
	@EqualsAndHashCode.Exclude
	private String stateLastUpdate;


	@Override
	public Point getInfluxDbDataPoint(IIpGeoCoder ipGeoCoder) {
		String timestamp = normalizeTimestamp(getTimestamp());
		Point point = Point.measurement("_Instances")
				.time(Long.valueOf(timestamp), TimeUnit.MILLISECONDS)
				.addField("ipAddress", Strings.nullToEmpty(getIpAddress()))
				.addField("clientId", Strings.nullToEmpty(getClientId()))
				.addField("reference", Strings.nullToEmpty(getReference()))
				.addField("state", Strings.nullToEmpty(getState()))
				.addField("stateLastUpdate", Strings.nullToEmpty(getStateLastUpdate()))
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
