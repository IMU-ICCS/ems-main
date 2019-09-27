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
	private String localMountPont;	/* local mount to the VM instance that is going to be commissoned */
	
	
	public DataSource(String name, DataSourceType dataSourceType, String ufsURI, String mountPoint, String localMountPoint) {
		super();
		this.name = name;
		this.dataSourceType = dataSourceType;
		this.ufsURI = ufsURI;
		this.mountPoint = mountPoint;
		this.localMountPont = localMountPoint;
	}

	public DataSource(String name, DataSourceType dataSourceType, String ufsURI, String mountPoint, String localMountPoint, boolean isReadOnly) {
		this(name, dataSourceType, ufsURI, mountPoint, localMountPoint);
		this.isReadOnly = isReadOnly;
	}

	public DataSource(String name, DataSourceType dataSourceType, String ufsURI, String mountPoint, String localMountPoint, String accessKey) {
		this(name, dataSourceType, ufsURI, mountPoint, localMountPoint);
		this.accessKey = accessKey;
	}

	public DataSource(String name, DataSourceType dataSourceType, String ufsURI, String mountPoint, String localMountPoint, String accessKey,
			boolean isReadOnly) {
		this(name, dataSourceType, ufsURI, mountPoint, localMountPoint, accessKey);
		this.isReadOnly = isReadOnly;
	}

}