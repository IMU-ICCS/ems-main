/*
 * Melodic EU Project
 * DLMS WebService
 * DMS Service Interface
 * @author: ferox
 */

package e.melodic.upperware.dlms;

import java.net.URI;
import java.util.List;

/**
 * DLMS service providing various functionality regarding datasources.
 */
public interface DLMSService {

	/**
	 * Returns one datasource matching the given id.
	 * Throws an IdNotFoundException if no datasource with this id exists.
	 */
	DataSource getDataSourceById(long id);

	/**
	 * Returns all datasources in the database.
	 */
	List<DataSource> getAllDataSources();

	/**
	 * Unmounts and deletes the datasource matching the given id.
	 * Throws an IdNotFoundException if no datasource with this id exists.
	 */
	void deleteById(long id);

	/**
	 * Adds this datasource to the database and mounts the mount point.
	 * Throws a CreateDatasourceException if a datasource with the same name already exists.
	 */
	URI addDataSource(DataSource ds);

	/**
	 * Updates the datasource with the given id with the data provided in the datasource object.
	 * In the process the datasource with the given id is unmounted and the nount point from the datasource object is mounted instead.
	 * Then the data is saved for this id.
	 * Throws an IdNotFoundException if no datasource with this id exists.
	 */
	DataSource updateDataSource(DataSource ds, long id);

	/**
	 * Migrates (moves) a file from pathFrom to pathTo.
	 */
	void migrateFile(String pathFrom, String pathTo);

	/**
	 * Migrates (moves) a directory from pathFrom to pathTo.
	 */
	void migrateDirectory(String pathFrom, String pathTo);

	/**
	 * Migrates (moves) the complete datasource (and all data) with the given id to the location in pathTo inside another datasource.
	 * The 'old' datasource is unmounted and deleted after the migration.
	 * Throws an IdNotFoundException if no datasource with this id exists.
	 */
	void migrateDatasource(long id, String pathTo);
}
