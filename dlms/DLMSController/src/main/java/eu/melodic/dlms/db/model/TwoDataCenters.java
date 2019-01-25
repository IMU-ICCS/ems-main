package eu.melodic.dlms.db.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TwoDataCenters {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	private Long id;
	private String dc1Id;
	private String dc2Id;
	private int latency;
	private int bandwidth;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	public TwoDataCenters(String dc1Id, String dc2Id, int latency, int bandwidth, Date timestamp) {
		this.dc1Id = dc1Id;
		this.dc2Id = dc2Id;
		this.timestamp = timestamp;
		this.latency = latency;
		this.bandwidth = bandwidth;
	}
}
