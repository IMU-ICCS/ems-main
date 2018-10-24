
package eu.melodic.dlms.data;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Bean for mapping of JSON data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "version",
    "gauges",
    "counters",
    "histograms",
    "meters",
    "timers"
})
public class Metrics {

    @JsonProperty("version")
    private String version;
    @JsonProperty("gauges")
    private Gauges gauges;
    @JsonProperty("counters")
    private Counters counters;
    @JsonProperty("histograms")
    private Histograms histograms;
    @JsonProperty("meters")
    private Meters meters;
    @JsonProperty("timers")
    private Timers timers;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    @JsonProperty("gauges")
    public Gauges getGauges() {
        return gauges;
    }

    @JsonProperty("gauges")
    public void setGauges(Gauges gauges) {
        this.gauges = gauges;
    }

    @JsonProperty("counters")
    public Counters getCounters() {
        return counters;
    }

    @JsonProperty("counters")
    public void setCounters(Counters counters) {
        this.counters = counters;
    }

    @JsonProperty("histograms")
    public Histograms getHistograms() {
        return histograms;
    }

    @JsonProperty("histograms")
    public void setHistograms(Histograms histograms) {
        this.histograms = histograms;
    }

    @JsonProperty("meters")
    public Meters getMeters() {
        return meters;
    }

    @JsonProperty("meters")
    public void setMeters(Meters meters) {
        this.meters = meters;
    }

    @JsonProperty("timers")
    public Timers getTimers() {
        return timers;
    }

    @JsonProperty("timers")
    public void setTimers(Timers timers) {
        this.timers = timers;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("version", version).append("gauges", gauges).append("counters", counters).append("histograms", histograms).append("meters", meters).append("timers", timers).append("additionalProperties", additionalProperties).toString();
    }

}
