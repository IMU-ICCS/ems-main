package eu.melodic.dlms.algorithms.algoAffinity.dbModel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class ACDSKey implements Serializable {
	@NotNull
	@Column(name = "appcomp_id", nullable = false)
	private Long appCompId;
	@NotNull
	@Column(name = "ds_id", nullable = false)
	private Long dsID;

	public ACDSKey() {

	}

	public ACDSKey(Long appCompId, Long dsId) {
		this.appCompId = appCompId;
		this.dsID = dsId;
	}

	public Long getAppCompId() {
		return appCompId;
	}

	public void setAppCompId(Long appCompId) {
		this.appCompId = appCompId;
	}

	public Long getDsID() {
		return dsID;
	}

	public void setDsID(Long dsID) {
		this.dsID = dsID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dsID == null) ? 0 : dsID.hashCode());
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
		ACDSKey other = (ACDSKey) obj;
		if (appCompId == null) {
			if (other.appCompId != null)
				return false;
		} else if (!appCompId.equals(other.appCompId))
			return false;
		if (dsID == null) {
			if (other.dsID != null)
				return false;
		} else if (!dsID.equals(other.dsID))
			return false;
		return true;
	}

}
