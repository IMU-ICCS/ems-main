package eu.melodic.dlms.algorithms.latency_bandwidth;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

// store the name 
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TwoDataCenComb {
	private Long dc1Id;
	private Long dc2Id;
}
