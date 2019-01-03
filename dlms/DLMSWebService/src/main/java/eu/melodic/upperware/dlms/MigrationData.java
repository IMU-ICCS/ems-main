package eu.melodic.upperware.dlms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Wraps migration information such as Source and destination path or datasource
 * id to be migrated.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MigrationData {

	private long id;
	private String pathFrom;
	private String pathTo;

	public MigrationData(String pathFrom, String pathTo) {
		this.pathFrom = pathFrom;
		this.pathTo = pathTo;
	}

}
