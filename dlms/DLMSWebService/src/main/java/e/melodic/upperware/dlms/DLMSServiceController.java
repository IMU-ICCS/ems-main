/*
 * Melodic EU Project
 * DLMS WebService
 * DMS Service Controller
 * @author: ferox
 */

package e.melodic.upperware.dlms;

import alluxio.cli.fs.FileSystemShell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Webservice controller for the DLMS service.
 */
@RestController
public class DLMSServiceController {
	
	private FileSystemShell mFsShell;
	
	@Autowired
	private DLMSService dlmsService;
	
	public DLMSServiceController() {
		mFsShell = new FileSystemShell();				
	}

	/**
	 * Returns all datasources in the database.
	 */
    @RequestMapping(value = "/ds", method = RequestMethod.GET)
    public List<DataSource> getDataSources() {
		return dlmsService.getAllDataSources();
	}

	/**
	 * Returns one datasource matching the given id.
	 */
    @RequestMapping(value = "/ds/{id}", method = RequestMethod.GET)
    public DataSource getDataSource(@PathVariable("id") long id) {
		return dlmsService.getDataSourceById(id);
	}

	/**
	 * Unmounts and deletes the datasource matching the given id.
	 */
    @DeleteMapping("/ds/{id}")
    public void deleteDataSource(@PathVariable long id) {
    	dlmsService.deleteById(id);
    }

	/**
	 * Adds this datasource to the database and mounts the mount point.
	 */
    @PostMapping("/ds")
    public ResponseEntity<Object> addDataSource(@RequestBody DataSource ds) {
    	URI location = dlmsService.addDataSource(ds);    	
    	return ResponseEntity.created(location).build();
    }

	/**
	 * Updates the datasource with the given id with the data provided in the datasource object.
	 */
    @PutMapping("/ds/{id}")
    public ResponseEntity<Object> updateDataSource(@RequestBody DataSource ds, @PathVariable long id) {
		dlmsService.updateDataSource(ds, id);
    	return ResponseEntity.noContent().build();
    }

	/**
	 * Migrates (moves) a file from pathFrom to pathTo in the MigrationData object.
	 */
    @PostMapping("/migrate/file")
	public void migrateFile(@RequestBody MigrationData migrationData) {
		dlmsService.migrateFile(migrationData.getPathFrom(), migrationData.getPathTo());
	}

	/**
	 * Migrates (moves) a directory from pathFrom to pathTo in the MigrationData object.
	 */
	@PostMapping("/migrate/dir")
	public void migrateDirectory(@RequestBody MigrationData migrationData) {
		dlmsService.migrateDirectory(migrationData.getPathFrom(), migrationData.getPathTo());
	}

	/**
	 * Migrates (moves) the complete datasource (and all data) with the given id in the MigrationData object to the location in pathTo (in the MigrationData object).
	 */
	@PostMapping("/migrate/ds")
	public void migrateDatasource(@RequestBody MigrationData migrationData) {
		dlmsService.migrateDatasource(migrationData.getId(), migrationData.getPathTo());
	}
    
}