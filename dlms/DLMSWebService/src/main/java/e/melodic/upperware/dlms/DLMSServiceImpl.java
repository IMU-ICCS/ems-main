/*
 * Melodic EU Project
 * DLMS WebService
 * DMS Service Implementation
 * @author: ferox
 */

package e.melodic.upperware.dlms;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import alluxio.Constants;
import alluxio.PropertyKey;
import alluxio.util.ConfigurationUtils;
import alluxio.AlluxioURI;
import alluxio.cli.fs.FileSystemShell;
import alluxio.client.ReadType;
import alluxio.client.WriteType;
import alluxio.client.file.FileInStream;
import alluxio.client.file.FileSystem;
import alluxio.client.file.options.OpenFileOptions;
import alluxio.exception.AlluxioException;
import alluxio.util.io.BufferUtils;
import alluxio.util.io.PathUtils;
import e.melodic.upperware.dlms.DataSource;
import e.melodic.upperware.dlms.DataSourceRepository;



@Service("dlmsService")
public class DLMSServiceImpl implements DLMSService {
	
	@Autowired
	DataSourceRepository dsRepository;
	
	
	
	@Override
	public DataSource getDataSourceById(long id) {
		String arr[] = {"mkdir", "/melodic/xyztest"};
		  int ret;

		    if (!ConfigurationUtils.masterHostConfigured()) {
		      System.out.println(String.format(
		          "Cannot run alluxio shell; master hostname is not "
		              + "configured. Please modify %s to either set %s or configure zookeeper with "
		              + "%s=true and %s=[comma-separated zookeeper master addresses]",
		          Constants.SITE_PROPERTIES, PropertyKey.MASTER_HOSTNAME.toString(),
		          PropertyKey.ZOOKEEPER_ENABLED.toString(), PropertyKey.ZOOKEEPER_ADDRESS.toString()));
		      System.exit(1);
		    }

		    try (FileSystemShell shell = new FileSystemShell()) {
		      ret = shell.run(arr);
		    } catch (IOException e) {
				e.printStackTrace();
			}
		    
		return dsRepository.findById(id).get();
	}

	@Override
	public List<DataSource> getAllDataSources() {
		return dsRepository.findAll();
	}
	
	@Override
	public void deleteById(long id) {
		dsRepository.deleteById(id);
	}
	
	@Override
	public URI addDataSource(DataSource ds) {
			
			String arr[] = {"mount", "/melodic/"+ds.getName(),ds.getUfsURI()};					
			int ret;

		    if (!ConfigurationUtils.masterHostConfigured()) {
		      System.out.println(String.format(
		          "Cannot run alluxio shell; master hostname is not "
		              + "configured. Please modify %s to either set %s or configure zookeeper with "
		              + "%s=true and %s=[comma-separated zookeeper master addresses]",
		          Constants.SITE_PROPERTIES, PropertyKey.MASTER_HOSTNAME.toString(),
		          PropertyKey.ZOOKEEPER_ENABLED.toString(), PropertyKey.ZOOKEEPER_ADDRESS.toString()));
		      System.exit(1);
		    }

		    try (FileSystemShell shell = new FileSystemShell()) {
		      ret = shell.run(arr);
		    } catch (IOException e) {
				e.printStackTrace();
			}

		    
		DataSource newDataSource = dsRepository.save(ds);

			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(newDataSource.getId()).toUri();
			return location;
	}
	

}