package eu.melodic.upperware.activemqtorest.entry;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.melodic.upperware.activemqtorest.MelodicConfiguration;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Component
public class MqAdapterStatus {

	private long msqCount;
	private long errorCount;
	private Map<String, ExtractedMetricsDescriptions> recentExtractedMetricsDescriptions;
	@Autowired
	private MelodicConfiguration melodicConfiguration;
}
