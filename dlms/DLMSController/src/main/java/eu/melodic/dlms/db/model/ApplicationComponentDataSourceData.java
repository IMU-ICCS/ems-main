package eu.melodic.dlms.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ApplicationComponentDataSourceData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(nullable = false)
	private Long appCompId;
	@Column(nullable = false)
	private Long dataSourceId;
	private Long dataRead;
	private Long dataWrite;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	public ApplicationComponentDataSourceData(Long appCompId, Long dataSourceId, Long data, Date timestamp,
			boolean isDataRead) {
		this.appCompId = appCompId;
		this.dataSourceId = dataSourceId;

		this.timestamp = timestamp;
		if (isDataRead)
			this.dataRead = data;
		else
			this.dataWrite = data;
	}

	public ApplicationComponentDataSourceData(Long appCompId, Long dataSourceId, Long dataRead, Long dataWrite,
			Date timestamp) {
		this.appCompId = appCompId;
		this.dataSourceId = dataSourceId;
		this.dataRead = dataRead;
		this.dataWrite = dataWrite;
		this.timestamp = timestamp;
	}

}
