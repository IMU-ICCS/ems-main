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
@Getter
@Setter
@NoArgsConstructor 
public class DataCenterCluster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	private Long id;
	@Column(unique=true)
	private String name;
	private int clusterNumber;
	
	public DataCenterCluster(String name, int clusterNumber) {
		this.name = name;
		this.clusterNumber = clusterNumber;
	}
}
