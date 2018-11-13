package eu.melodic.dlms.algorithms.latencyBandwidth.dbModel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class TwoDCKey implements Serializable {

	@NotNull
	@Column(name = "dc1_Id", nullable = false)
	private Long dc1Id;
	@NotNull
	@Column(name = "dc2_Id", nullable = false)
	private Long dc2Id;

	public TwoDCKey() {

	}

	public TwoDCKey(Long dc1Id, Long dc2Id) {
		this.dc1Id = dc1Id;
		this.dc2Id = dc2Id;
	}

	public Long getDc1Id() {
		return dc1Id;
	}

	public void setDc1Id(Long dc1Id) {
		this.dc1Id = dc1Id;
	}

	public Long getDc2Id() {
		return dc2Id;
	}

	public void setDc2Id(Long dc2Id) {
		this.dc2Id = dc2Id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dc1Id == null) ? 0 : dc1Id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TwoDCKey other = (TwoDCKey) obj;
		if (dc1Id == null) {
			if (other.dc1Id != null)
				return false;
		} else if (!dc1Id.equals(other.dc1Id))
			return false;
		if (dc2Id == null) {
			if (other.dc2Id != null)
				return false;
		} else if (!dc2Id.equals(other.dc2Id))
			return false;
		return true;
	}

}
