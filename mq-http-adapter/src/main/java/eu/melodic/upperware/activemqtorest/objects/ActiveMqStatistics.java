package eu.melodic.upperware.activemqtorest.objects;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Component
public class ActiveMqStatistics {

	private long msqCount = 0;
	private Boolean hasError = false;

}
