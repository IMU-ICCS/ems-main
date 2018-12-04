package eu.melodic.dlms.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TwoDCKey implements Serializable {
	@NotNull
	@Column(name = "dc1_Id", nullable = false)
	private Long dc1Id;
	@NotNull
	@Column(name = "dc2_Id", nullable = false)
	private Long dc2Id;

}
