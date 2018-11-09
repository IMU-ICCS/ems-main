package eu.melodic.dlms.algorithms.algoLatencyBandwidth.dbModel;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "twodatacenter_combination")
@EntityListeners(AuditingEntityListener.class)
public class TwoDataCenterCombination {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id")
//	private Long id;
	@EmbeddedId
	private TwoDCKey twoDCKey;

	@Column(name = "latency", nullable = false)
	private double latency;
	@Column(name = "bandwidth", nullable = false)
	private double bandwidth;
	@Column(name = "comb_value", nullable = false)
	private double combValue;

	public TwoDataCenterCombination(TwoDCKey twoDCKey, double latency, double bandwidth, double combValue) {
		this.twoDCKey = twoDCKey;

		this.latency = latency;
		this.bandwidth = bandwidth;
		this.combValue = combValue;
	}

	public TwoDataCenterCombination() {
		super();
	}

	public TwoDCKey getTwoDCKey() {
		return twoDCKey;
	}

	public void setTwoDCKey(TwoDCKey twoDCKey) {
		this.twoDCKey = twoDCKey;
	}

	public double getLatency() {
		return latency;
	}

	public void setLatency(double latency) {
		this.latency = latency;
	}

	public double getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(double bandwidth) {
		this.bandwidth = bandwidth;
	}

	public double getCombValue() {
		return combValue;
	}

	public void setCombValue(double combValue) {
		this.combValue = combValue;
	}

}
