package eu.melodic.dlms.db.model;

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
public class ApplicationComponentDataSourceAffinity {
	@EmbeddedId
	private AcDsKey acDsKey;
	private double affinity;
	
}
