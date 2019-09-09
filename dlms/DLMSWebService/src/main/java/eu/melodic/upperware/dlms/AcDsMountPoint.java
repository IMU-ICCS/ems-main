package eu.melodic.upperware.dlms;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class AcDsMountPoint {

	@Id
	private String acName;
	private String dsName;
	private String mountPoint;
	private String toLocalMountPoint;

}
