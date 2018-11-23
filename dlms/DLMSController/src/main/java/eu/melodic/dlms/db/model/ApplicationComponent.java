package eu.melodic.dlms.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationComponent {
	@Id
	private Long id;
	private String name;
	
	public ApplicationComponent(Long id) {
		this.id=id;
	}

}
