package eu.melodic.dlms.db.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TwoDataCenterCombination {
	@EmbeddedId
	private TwoDCKey twoDCKey;
	@Column(name = "latency", nullable = false)
	private double latency;
	@Column(name = "bandwidth", nullable = false)
	private double bandwidth;
	@Column(name = "comb_value", nullable = false)
	private double combValue;
}
