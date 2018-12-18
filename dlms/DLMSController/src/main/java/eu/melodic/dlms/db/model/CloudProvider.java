package eu.melodic.dlms.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CloudProvider {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	@NotNull
	private String name;
	@Column(name = "is_public")
	private boolean isPublic = true;
	private String notes;

	public CloudProvider(String name) {
		this.name = name;
	}

	public CloudProvider(String name, boolean isPublic) {
		this.name = name;
		this.isPublic = isPublic;
	}

}
