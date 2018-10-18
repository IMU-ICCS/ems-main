package eu.melodic.dlms.algorithms.model;

// store the name 
public class TwoDataCenComb {
	private String dc1;
	private String dc2;

	public TwoDataCenComb(String dc1, String dc2) {
		this.dc1 = dc1;
		this.dc2 = dc2;
	}

	public String getDc1() {
		return dc1;
	}

	public void setDc1(String dc1) {
		this.dc1 = dc1;
	}

	public String getDc2() {
		return dc2;
	}

	public void setDc2(String dc2) {
		this.dc2 = dc2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dc1 == null) ? 0 : dc1.hashCode());
		result = prime * result + ((dc2 == null) ? 0 : dc2.hashCode());
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
		if (dc1 == null) {
			if (other.dc1 != null)
				return false;
		} else if (!dc1.equals(other.dc1))
			return false;
		if (dc2 == null) {
			if (other.dc2 != null)
				return false;
		} else if (!dc2.equals(other.dc2))
			return false;
		return true;
	}

}
