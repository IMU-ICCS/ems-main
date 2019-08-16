package eu.melodic.upperware.activemqtorest.objects;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.influxdb.dto.Point;

import eu.melodic.upperware.activemqtorest.influxdb.InfluxDataRetainer;
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
public class MqThresholdEntry extends MqBaseEntry {

	private long id = 42l;
	private String name;
	private String threshold;
	private String operator;
	private String timestamp;


	@Override
	public Point getInfluxDbDataPoint(IIpGeoCoder ipGeoCoder) {
		String timestamp = normalizeTimestamp(getTimestamp());
		Point point = Point.measurement("_Thresholds")
				.time(Long.valueOf(timestamp), TimeUnit.MILLISECONDS)
				.addField("name", getName())
				.addField("threshold", Double.valueOf(getThreshold()))
				.addField("operator", getOperator())
				.build();
		return point;
	}

	@Override
	public boolean mustRetain(InfluxDataRetainer influxDataRetainer) {
		return influxDataRetainer.getThresholdEntryCache().containsKey(this.hashCode());
	}

	@Override
	public void updateRetained(InfluxDataRetainer influxDataRetainer) {
		influxDataRetainer.getThresholdEntryCache().put(this.hashCode(), this);
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + name.hashCode();
		result = 31 * result + threshold.hashCode();
		result = 31 * result + operator.hashCode();
		return result;
	}

}
