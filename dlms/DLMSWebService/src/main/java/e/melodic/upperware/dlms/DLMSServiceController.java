/*
 * Melodic EU Project
 * DLMS WebService
 * DMS Service Controller
 * @author: ferox
 */

package e.melodic.upperware.dlms;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import alluxio.AlluxioURI;
import alluxio.cli.fs.FileSystemShell;
import alluxio.client.ReadType;
import alluxio.client.WriteType;
import alluxio.client.file.FileInStream;
import alluxio.client.file.FileOutStream;
import alluxio.client.file.FileSystem;
import alluxio.client.file.options.CreateFileOptions;
import alluxio.client.file.options.OpenFileOptions;
import alluxio.exception.AlluxioException;
import alluxio.util.CommonUtils;
import alluxio.util.ConfigurationUtils;
import alluxio.util.FormatUtils;
import e.melodic.upperware.dlms.DLMSService;
import e.melodic.upperware.dlms.DataSource;


@RestController
public class DLMSServiceController {
	
	private FileSystemShell mFsShell;
	
	@Autowired
	private DLMSService dlmsService;
	
	public DLMSServiceController() {
		mFsShell = new FileSystemShell();				
	}
	
    @RequestMapping(value = "/ds", method = RequestMethod.GET)
    public List<DataSource> getDataSources() {
		return dlmsService.getAllDataSources();
	}

    @RequestMapping(value = "/ds/{id}", method = RequestMethod.GET)
    public DataSource getDataSource(@PathVariable("id") long id) {
		return dlmsService.getDataSourceById(id);
	}
    
    @DeleteMapping("/ds/{id}")
    public void deleteDataSource(@PathVariable long id) {
    	dlmsService.deleteById(id);
    }
    
    @PostMapping("/ds")
    public ResponseEntity<Object> addDataSource(@RequestBody DataSource ds) {
    	URI location = dlmsService.addDataSource(ds);    	
    	return ResponseEntity.created(location).build();
    }
    
    
//    @PutMapping("/ds/{id}")
//    public ResponseEntity<Object> updateDataSource(@RequestBody DataSource ds, @PathVariable long id) {
//
//    	Optional<DataSource> dsOptional = dsRepository.findById(id);
//
//    	if (!dsOptional.isPresent())
//    		return ResponseEntity.notFound().build();
//
//    	ds.setId(id);
//    	
//    	dsRepository.save(ds);
//
//    	return ResponseEntity.noContent().build();
//    }
    
}