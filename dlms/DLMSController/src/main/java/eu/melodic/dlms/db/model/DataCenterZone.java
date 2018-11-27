package eu.melodic.dlms.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor 
@AllArgsConstructor
@Getter
@Setter
public class DataCenterZone {
	@Id
	private long dataCenterId;
	private int zone;
}
