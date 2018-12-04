package eu.melodic.dlms.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class DataCenter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	private Long id;
	@Column(unique = true)
	private String name;
	private Long regionId;
	private Long cloudProviderId;

	public DataCenter(String name, Long regionId) {
		this.name = name;
		this.regionId = regionId;
	}

	public DataCenter(String name, Long regionId, Long cloudProviderId) {
		this.name = name;
		this.regionId = regionId;
		this.cloudProviderId = cloudProviderId;
	}

}
