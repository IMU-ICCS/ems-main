/*
 * Melodic EU Project
 * DLMS WebService
 * DMS Service Interface
 * @author: ferox
 */

package eu.melodic.upperware.dlms;

import java.net.URI;
import java.util.List;

/**
 * DLMS service providing various functionality regarding datasources.
 */
public interface DLMSService {

	/**
	 * Returns one datasource matching the given id. Throws an IdNotFoundException
	 * if no datasource with this id exists.
	 */
	DataSource getDataSourceById(long id);

	/**
	 * Returns one datasource matching the given name. Throws a
	 * NameNotFoundException if no datasource with this name exists.
	 */
	DataSource getDataSourceByName(String name);

	/**
	 * Check if datasource matching the given name exists.
	 */
	boolean hasDataSourceByName(String name);

	/**
	 * Returns all datasources in the database.
	 */
	List<DataSource> getAllDataSources();

	/**
	 * Returns all component name along with data source and mount point.
	 */
	List<AcDsMountPoint> getAllAcDsMp();

	/**
	 * Returns one component name along with the data source and mount point.
	 */
	AcDsMountPoint getAcDsMpByName(String name);

	/**
	 * Returns the command for cloudiator to run for the particular component name.
	 */
	String getAlluxioCmd(String cmpName);
	
	/**
	 * Add data source linked to the component name along with the mount point in
	 * the database.
	 */
	void calculateAcDsMp();

	/**
	 * Unmounts and deletes the datasource matching the given id. Throws an
	 * IdNotFoundException if no datasource with this id exists.
	 */
	void deleteById(long id);

	/**
	 * Unmounts and deletes the datasource matching the given name. Throws an
	 * IdNotFoundException if no datasource with this id exists.
	 */
	void deleteByName(String name);

	/**
	 * Adds this datasource to the database and mounts the mount point. Throws a
	 * CreateDatasourceException if a datasource with the same name already exists.
	 */
	void addDataSource(DataSource ds);

	/**
	 * Updates the datasource with the given id with the data provided in the
	 * datasource object. In the process the datasource with the given id is
	 * unmounted and the mount point from the datasource object is mounted instead.
	 * Then the data is saved for this id. Throws an IdNotFoundException if no
	 * datasource with this id exists.
	 */
	void updateDataSource(DataSource ds, long id);

	/**
	 * Updates the datasource with the given name with the data provided in the
	 * datasource object. In the process the datasource with the given name is
	 * unmounted and the mount point from the datasource object is mounted instead.
	 * Then the data is saved for this name. Throws a NameNotFoundException if no
	 * datasource with this name exists.
	 */
	void updateDataSource(DataSource ds, String name);

	/**
	 * Migrates (moves) a file from pathFrom to pathTo.
	 */
	void migrateFile(String pathFrom, String pathTo);

	/**
	 * Migrates (moves) a directory from pathFrom to pathTo.
	 */
	void migrateDirectory(String pathFrom, String pathTo);

	/**
	 * Migrates (moves) the complete datasource (and all data) with the given id to
	 * the location in pathTo inside another datasource. The 'old' datasource is
	 * unmounted and deleted after the migration. Throws an IdNotFoundException if
	 * no datasource with this id exists.
	 */
	void migrateDatasource(long id, String pathTo);

}
