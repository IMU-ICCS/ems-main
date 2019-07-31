/*
 * Melodic EU Project
 * DLMS WebService
 * Data Source Model
 * @author: ferox
 */

package eu.melodic.upperware.dlms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing one datasource.
 */
@Entity

@NoArgsConstructor
@Getter
@Setter
public class DataSource {

	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true)
	private String name;
	private DataSourceType dataSourceType;
	private String ufsURI; /* underlying file system uri */
	private String mountPoint; /* mount point in alluxio */
	private String accessKey; /* access information for datasources with username and password */
	private boolean isReadOnly = false; /* check if modifications can be done on the mount point */

	public DataSource(String name, DataSourceType dataSourceType, String ufsURI, String mountPoint) {
		super();
		this.name = name;
		this.dataSourceType = dataSourceType;
		this.ufsURI = ufsURI;
		this.mountPoint = mountPoint;
	}

	public DataSource(String name, DataSourceType dataSourceType, String ufsURI, String mountPoint, boolean isReadOnly) {
		this(name, dataSourceType, ufsURI, mountPoint);
		this.isReadOnly = isReadOnly;
	}

	public DataSource(String name, DataSourceType dataSourceType, String ufsURI, String mountPoint, String accessKey) {
		this(name, dataSourceType, ufsURI, mountPoint);
		this.accessKey = accessKey;
	}

	public DataSource(String name, DataSourceType dataSourceType, String ufsURI, String mountPoint, String accessKey,
			boolean isReadOnly) {
		this(name, dataSourceType, ufsURI, mountPoint, accessKey);
		this.isReadOnly = isReadOnly;
	}

}