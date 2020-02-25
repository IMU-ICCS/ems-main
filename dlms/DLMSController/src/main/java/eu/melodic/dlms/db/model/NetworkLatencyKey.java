package eu.melodic.dlms.db.model;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class NetworkLatencyKey implements Serializable {
    @NotNull
    @JoinColumn(name = "region_A_Id", nullable = false)
    @ManyToOne(optional = false)
    private Region regionA;
    @NotNull
    @JoinColumn(name = "region_B_Id", nullable = false)
    @ManyToOne(optional = false)
    private Region regionB;
}
