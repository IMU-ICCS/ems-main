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



/**
 * Entity representing one datasource.
 */
@Entity
public class DataSource {

	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true)
	private String name;
	private DataSourceType type;
	private String ufsURI; /* underlying file system uri */
	private String mountPoint; /* mount point in alluxio */

	public DataSource() {
		super();
	}

	public DataSource(String name, DataSourceType type, String ufsURI, String mountPoint) {
		super();
		this.name = name;
		this.type = type;
		this.ufsURI = ufsURI;
		this.mountPoint = mountPoint;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DataSourceType getType() {
		return type;
	}

	public void setDataSourceType(DataSourceType type) {
		this.type = type;
	}

	public String getUfsURI() {
		return ufsURI;
	}

	public void setUfsURI(String ufsURI) {
		this.ufsURI = ufsURI;
	}

	public String getMountPoint() {
		return mountPoint;
	}

	public void setMountPoint(String mountPoint) {
		this.mountPoint = mountPoint;
	}

}