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
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AcDsKey implements Serializable {
	@NotNull
	@Column(name = "appcomp_id", nullable = false)
	private Long appCompId;
	@NotNull
	@Column(name = "ds_id", nullable = false)
	private Long dsID;

}
