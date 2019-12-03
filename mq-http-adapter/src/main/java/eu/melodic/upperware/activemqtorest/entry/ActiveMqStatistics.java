package eu.melodic.upperware.activemqtorest.entry;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Component
public class ActiveMqStatistics {

	private long msqCount;
	private long errorCount;
	private Map<String, ExtractedMetricsDescriptions> recentExtractedMetricsDescriptions;
}
