package eu.melodic.upperware.activemqtorest.entry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Getter
@Setter

public class ExtractedMetricsDescriptions {
	private String type;
	private String lastState;
	private String lastReceived;

	public ExtractedMetricsDescriptions() {
		initializeDateTime();
	}

	public ExtractedMetricsDescriptions(String type, String lastState) {
		initializeDateTime();
		this.type = type;
		this.lastState = lastState;
	}

	private void initializeDateTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		String formattedDate = formatter.format(LocalDateTime.now());
		this.lastReceived = formattedDate;
	}
}
