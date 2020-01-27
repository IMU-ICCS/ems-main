package eu.melodic.dlms.db.model;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PreUpdate;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NetworkLatencyMatrix {
    @EmbeddedId
    private NetworkLatencyKey networkLatencyKey;
    private BigDecimal networkLatencyMillis;
    private LocalDateTime lastUpdate;

    @PreUpdate
    public void updateLastUpdate() { this.lastUpdate = LocalDateTime.now(); }
}
