package eu.melodic.dlms.algorithms.model;

// store the name 
public class TwoDataCenComb {
	private Long dc1Id;
	private Long dc2Id;

	public TwoDataCenComb(Long dc1Id, Long dc2Id) {
		this.dc1Id = dc1Id;
		this.dc2Id = dc2Id;
	}

	public Long getDc1() {
		return dc1Id;
	}

	public void setDc1(Long dc1Id) {
		this.dc1Id = dc1Id;
	}

	public Long getDc2() {
		return dc2Id;
	}

	public void setDc2(Long dc2Id) {
		this.dc2Id = dc2Id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dc1Id == null) ? 0 : dc1Id.hashCode());
		result = prime * result + ((dc2Id == null) ? 0 : dc2Id.hashCode());
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
		TwoDataCenComb other = (TwoDataCenComb) obj;
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
