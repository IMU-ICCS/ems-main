package eu.melodic.upperware.activemqtorest.entry;

import com.google.common.base.Strings;
import eu.melodic.upperware.activemqtorest.influxdb.geolocation.IIpGeoCoder;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.dto.Point;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class MqPredictionEntry extends MqBaseEntry{
    private String name;
    private String topic;

    private double metricValue;
    private long timestamp;
    private long predictionTime;
    private double probability;
    private List<Double> confidence_interval;
    private int level;

    private String refersTo;
    private String cloud;
    private String provider;

    @Override
    public Point getInfluxDbDataPoint(IIpGeoCoder ipGeoCoder) {
        Point point = Point.measurement(getTopic())
                .time(Long.valueOf(getPredictionTime()), TimeUnit.SECONDS)
                .addField("metricValue", Double.valueOf(getMetricValue()))
                .addField("probability", getProbability())
                .addField("timestamp", getTimestamp())
                .tag("level", Integer.toString(getLevel()))
                .tag("refersTo", Strings.nullToEmpty(getRefersTo()))
                .tag("cloud", Strings.nullToEmpty(getCloud()))
                .tag("provider", Strings.nullToEmpty(getProvider()))
                .build();
        return point;
    }
}
