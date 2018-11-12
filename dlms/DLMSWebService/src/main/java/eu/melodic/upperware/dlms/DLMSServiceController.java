/*
 * Melodic EU Project
 * DLMS WebService
 * DMS Service Controller
 * @author: ferox
 */

package eu.melodic.upperware.dlms;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import alluxio.cli.fs.FileSystemShell;
import eu.melodic.models.commons.NotificationResult;
import eu.melodic.models.commons.NotificationResultImpl;
import eu.melodic.models.interfaces.dlms.DataModelRequest;
import eu.melodic.models.services.dlms.DataModelNotificationRequest;
import eu.melodic.models.services.dlms.DataModelNotificationRequestImpl;
import eu.melodic.upperware.dlms.camel.ModelAnalyzer;
import eu.melodic.upperware.dlms.client.DLMSWebServiceClient;

/**
 * Webservice controller for the DLMS service.
 */
@RestController
public class DLMSServiceController {

	private FileSystemShell mFsShell;
	private static final Logger LOGGER = LoggerFactory.getLogger(DLMSWebServiceClient.class);
	@Autowired
	private DLMSService dlmsService;
	@Autowired
	ModelAnalyzer modelAnalyzer;

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
	@RequestMapping(value = "/ds/id/{id}", method = RequestMethod.GET)
	public DataSource getDataSource(@PathVariable("id") Long id) {
		return dlmsService.getDataSourceById(id);
	}

	/**
	 * Returns one datasource matching the given name.
	 */
	@RequestMapping(value = "/ds/name/{name}", method = RequestMethod.GET)
	public DataSource getDataSource(@PathVariable("name") String name) {
		return dlmsService.getDataSourceByName(name);
	}

	/**
	 * Unmounts and deletes the datasource matching the given id.
	 */
	@DeleteMapping("/ds/id/{id}")
	public void deleteDataSource(@PathVariable Long id) {
		dlmsService.deleteById(id);
		;
	}

	/**
	 * Unmounts and deletes the datasource matching the given name.
	 */
	@DeleteMapping("/ds/name/{name}")
	public void deleteDataSource(@PathVariable String name) {
		dlmsService.deleteByName(name);
	}

	/**
	 * Adds/updates the datasource from the camel model to the database and mounts
	 * the mount point
	 */
	@PostMapping("/datamodel")
	public ResponseEntity<Object> addUpdateDataSources(@Valid @RequestBody DataModelRequest dataModelRequest) {
		ResponseEntity<Object> retResponse = null;
		LOGGER.info("The name of the camel model is " + dataModelRequest.getApplicationId());
		// to send the notification
		DataModelNotificationRequest dataModelNotificationRequest = new DataModelNotificationRequestImpl();
		dataModelNotificationRequest.setApplicationId(dataModelRequest.getApplicationId());
		dataModelNotificationRequest.setWatermark(dataModelRequest.getWatermark());
		NotificationResult notificationResult = new NotificationResultImpl();

		try {
			modelAnalyzer.readModel(dataModelRequest.getApplicationId()); // read the camel model
			List<DataSource> dataSourceList = modelAnalyzer.getDataSourceList(); // get data sources from camel model
			for (DataSource datasource : dataSourceList) {
				if (dlmsService.hasDataSourceByName(datasource.getName())) {
					try {
						// if data source already exists, update if necessary
						dlmsService.updateDataSource(datasource, datasource.getName());
						retResponse = ResponseEntity.noContent().build();
					} catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
						notificationResult.setErrorDescription(e.getMessage());
						throw new RuntimeException(e);
					}
				} else {
					try {
						// add new data source if it does not exist
						URI location = dlmsService.addDataSource(datasource);
						LOGGER.info(datasource.getName() + " was added");
						retResponse = ResponseEntity.created(location).build();
					} catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
						notificationResult.setErrorDescription(e.getMessage());
						throw new RuntimeException(e);
					}
				}
				notificationResult.setErrorCode("0");
				notificationResult.setErrorDescription(retResponse.toString());
			}
		} catch (Exception e) {
			notificationResult.setErrorCode("1");
			notificationResult.setErrorDescription("The model could not be read");
			LOGGER.error(e.getMessage(), e);
		}
		dataModelNotificationRequest.setResult(notificationResult);
		sendNotificationMessage(dataModelRequest.getNotificationURI(), dataModelNotificationRequest);
		return retResponse;
	}

	/**
	 * Post the notification message in the provided url
	 */
	public void sendNotificationMessage(String url, DataModelNotificationRequest dataModelNotificationRequest) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			URI uri = new URI(url);
			HttpHeaders headers = createHeaders();
			HttpEntity<DataModelNotificationRequest> entity = new HttpEntity<>(dataModelNotificationRequest, headers);
			restTemplate.exchange(uri, HttpMethod.POST, entity, DataModelNotificationRequest.class);
		} catch (URISyntaxException | RestClientException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	// test the notification request, uncomment this when actual url exists
//	@PostMapping("/notification/msg")
//	public void addNotificationRequest(@Valid @RequestBody DataModelNotificationRequest dataModelNotificationRequest) {
//		System.out.println("Test message");
//		System.out.println(dataModelNotificationRequest.getResult().getErrorCode() + " : "
//				+ dataModelNotificationRequest.getResult().getErrorDescription());
//	}

	/**
	 * Updates the datasource with the given id with the data provided in the
	 * datasource object.
	 */
	@PutMapping("/ds/{id}")
	public ResponseEntity<Object> updateDataSource(@RequestBody DataSource ds, @PathVariable Long id) {
		dlmsService.updateDataSource(ds, id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Updates the datasource with the given name with the data provided in the
	 * datasource object.
	 */
	@PutMapping("/ds/{name}")
	public ResponseEntity<Object> updateDataSource(@RequestBody DataSource ds, @PathVariable String name) {
		dlmsService.updateDataSource(ds, name);
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
	 * Migrates (moves) a directory from pathFrom to pathTo in the MigrationData
	 * object.
	 */
	@PostMapping("/migrate/dir")
	public void migrateDirectory(@RequestBody MigrationData migrationData) {
		dlmsService.migrateDirectory(migrationData.getPathFrom(), migrationData.getPathTo());
	}

	/**
	 * Migrates (moves) the complete datasource (and all data) with the given id in
	 * the MigrationData object to the location in pathTo (in the MigrationData
	 * object).
	 */
	@PostMapping("/migrate/ds")
	public void migrateDatasource(@RequestBody MigrationData migrationData) {
		dlmsService.migrateDatasource(migrationData.getId(), migrationData.getPathTo());
	}

	/**
	 * Creates the HTTPHeaders with the security information.
	 */
	private HttpHeaders createHeaders() {
		return new HttpHeaders() {
			{
				String auth = "user" + ":" + "9687831d-a0bd-4d7a-993d-5d31e740749b";
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
	}
}