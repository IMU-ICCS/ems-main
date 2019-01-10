package eu.melodic.dlms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.melodic.dlms.db.model.ApplicationComponent;
import eu.melodic.dlms.db.model.ApplicationComponentDataSourceAffinity;
import eu.melodic.dlms.db.model.CloudProvider;
import eu.melodic.dlms.db.model.DataCenter;
import eu.melodic.dlms.db.model.DataCenterZone;
import eu.melodic.dlms.db.model.DataSource;
import eu.melodic.dlms.db.model.Region;
import eu.melodic.dlms.utility.DlmsDiffBundle;
import eu.melodic.dlms.utility.UtilityMetrics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Webservice controller for algorithms.
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class DlmsRestController {

	private Map<Algorithm, AlgorithmRunner> algorithms = new ConcurrentHashMap<>();
	private final DLMSService dlmsService;

	/**
	 * Registers an algorithm/runner combination in the controller to be accessible
	 * via the webservice.
	 */
	public void registerAlgorithm(Algorithm algorithm, AlgorithmRunner runner) {
		algorithms.put(algorithm, runner);
	}

	/**
	 * Returns a map of all active algorithms with the value their runner class
	 * produced from the call of queryResults().
	 */
	@GetMapping(value = "/dlmsController/utilityValue")
	public UtilityMetrics getUtilityValue(@RequestBody DlmsDiffBundle diffs) {
		Map<String, Double> utilityValueMap = new HashMap<>(algorithms.size());

		algorithms.forEach((Algorithm key, AlgorithmRunner runner) -> {
			double algorithmResult = runner.queryResults(diffs);
			log.info("result for algorithm {}: {}", key.getName(), algorithmResult);
			utilityValueMap.put(key.getCamelId(), algorithmResult);
		});

		return new UtilityMetrics(utilityValueMap);
	}

	/**
	 * Returns a list of cloud providers
	 */
	@GetMapping(value = "/cloudprovider")
	public List<CloudProvider> getAllCloudProviders() {
		return dlmsService.getAllCloudProviders();
	}

	/**
	 * Create a new cloud provider
	 */
	@PostMapping("/cloudprovider")
	public CloudProvider createCloudProvider(@Valid @RequestBody CloudProvider cp) {
		return dlmsService.createCloudProvider(cp);
	}

	/**
	 * Returns one cloud provider matching the given id. Throws an
	 * IdNotFoundException if no cloud provider with this id exists.
	 */
	@RequestMapping(value = "/cloudprovider/search", params = "id")
	public CloudProvider getCloudProviderById(@RequestParam Long id) {
		return dlmsService.getCloudProviderById(id);
	}

	/**
	 * Returns one cloud provider matching the given name. Throws a
	 * NameNotFoundException if no cloud provider with this name exists.
	 */
	@RequestMapping(value = "/cloudprovider/search", params = "name")
	public CloudProvider getCloudProviderBName(@RequestParam String name) {
		return dlmsService.getCloudProviderByName(name);
	}

	/**
	 * Updates the cloud provider with the given id with the data provided Throws an
	 * IdNotFoundException if no cloud provider with this id exists.
	 */
	@RequestMapping(value = "/cloudprovider/update", params = "id", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateDataSourceById(@RequestBody CloudProvider cp, @RequestParam Long id) {
		dlmsService.updateCloudProviderById(cp, id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Updates the cloud provider with the given name with the data provided Throws
	 * a NameNotFoundException if no cloud provider with this name exists.
	 */
	@RequestMapping(value = "/cloudprovider/update", params = "name", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateDataSourceByName(@RequestBody CloudProvider cp, @RequestParam String name) {
		dlmsService.updateCloudProviderByName(cp, name);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Returns all the data centers in the database
	 */
	@GetMapping(value = "/datacenter")
	public List<DataCenter> getAllDataCenters() {
		return dlmsService.getAllDataCenters();
	}

	/**
	 * Create a new data center
	 */
	@PostMapping("/datacenter")
	public DataCenter createDataCenter(@Valid @RequestBody DataCenter ds) {
		return dlmsService.createDataCenter(ds);
	}

	/**
	 * Returns one data center matching the given id. Throws an IdNotFoundException
	 * if no data center with this id exists.
	 */
	@RequestMapping(value = "/datacenter/search", params = "id")
	public DataCenter getDataCenterById(@RequestParam Long id) {
		return dlmsService.getDataCenterById(id);
	}

	/**
	 * Returns one data center center matching the given name. Throws a
	 * NameNotFoundException if no data center with this name exists.
	 */
	@RequestMapping(value = "/datacenter/search", params = "name")
	public DataCenter getDataCenterByName(@RequestParam String name) {
		return dlmsService.getDataCenterByName(name);
	}

	/**
	 * Updates the data center with the given id with the data provided Throws an
	 * IdNotFoundException if no cloud provider with this id exists.
	 */
	@RequestMapping(value = "/datacenter/update", params = "id", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateDataCenterById(@RequestBody DataCenter ds, @RequestParam Long id) {
		dlmsService.updateDataCenterById(ds, id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Updates the data center with the given name with the data provided Throws a
	 * NameNotFoundException if no cloud provider with this name exists.
	 */
	@RequestMapping(value = "/datacenter/update", params = "name", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateDataCenterByName(@RequestBody DataCenter ds, @RequestParam String name) {
		dlmsService.updateDataCenterByName(ds, name);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Returns a list of regions in the database
	 */
	@GetMapping(value = "/region")
	public List<Region> getAllRegions() {
		return dlmsService.getAllRegions();
	}

	/**
	 * Create a new region
	 */
	@PostMapping("/region")
	public Region createRegion(@Valid @RequestBody Region region) {
		return dlmsService.createRegion(region);
	}

	/**
	 * Returns one region matching the given id. Throws an IdNotFoundException if no
	 * region with this id exists.
	 */
	@RequestMapping(value = "/region/search", params = "id")
	public Region getRegionById(@RequestParam Long id) {
		return dlmsService.getRegionById(id);
	}

	/**
	 * Returns one region matching the given name. Throws a NameNotFoundException if
	 * no region with this name exists.
	 */
	@RequestMapping(value = "/region/search", params = "name")
	public Region getRegionByName(@RequestParam String name) {
		return dlmsService.getRegionByName(name);
	}

	/**
	 * Updates the region with the given id with the data provided Throws an
	 * IdNotFoundException if no cloud provider with this id exists.
	 */
	@RequestMapping(value = "/region/update", params = "id", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateRegionById(@RequestBody Region region, @RequestParam Long id) {
		dlmsService.updateRegionById(region, id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Updates the region with the given name with the data provided Throws a
	 * NameNotFoundException if no cloud provider with this name exists.
	 */
	@RequestMapping(value = "/region/update", params = "name", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateRegionByName(@RequestBody Region region, @RequestParam String name) {
		dlmsService.updateRegionByName(region, name);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Returns all the application components in the database
	 */
	@GetMapping(value = "/applicationcomponent")
	public List<ApplicationComponent> getAllApplicationComponents() {
		return dlmsService.getAllApplicationComponents();
	}

	/**
	 * Create a new application component
	 */
	@PostMapping("/applicationcomponent")
	public ApplicationComponent createApplicationComponent(@RequestBody ApplicationComponent ac) {
		return dlmsService.createApplicationComponent(ac);
	}

	/**
	 * Returns one application component matching the given id. Throws an
	 * IdNotFoundException if no application component with this id exists.
	 */
	@RequestMapping(value = "/applicationcomponent/search", params = "id")
	public ApplicationComponent getApplicationComponentById(@RequestParam Long id) {
		return dlmsService.getApplicationComponentById(id);
	}

	/**
	 * Returns one application component matching the given name. Throws a
	 * NameNotFoundException if no application component with this name exists.
	 */
	@RequestMapping(value = "/applicationcomponent/search", params = "name")
	public ApplicationComponent getApplicationComponentByName(@RequestParam String name) {
		return dlmsService.getApplicationComponentByName(name);
	}

	/**
	 * Updates the application component with the given id with the data provided
	 * Throws an IdNotFoundException if no cloud provider with this id exists.
	 */
	@RequestMapping(value = "/applicationcomponent/update", params = "id", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateApplicationComponentById(@RequestBody ApplicationComponent ac,
			@RequestParam Long id) {
		dlmsService.updateApplicationComponentById(ac, id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Updates the application component with the given name with the data provided
	 * Throws a NameNotFoundException if no cloud provider with this name exists.
	 */
	@RequestMapping(value = "/applicationcomponent/update", params = "name", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateApplicationComponentByName(@RequestBody ApplicationComponent ac,
			@RequestParam String name) {
		dlmsService.updateApplicationComponentByName(ac, name);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Returns a list of data sources in the database
	 */
	@GetMapping(value = "/datasource")
	public List<DataSource> getAllDataSources() {
		return dlmsService.getAllDataSources();
	}

	/**
	 * Create a new cloud provider
	 */
	@PostMapping("/datasource")
	public DataSource createDataSource(@Valid @RequestBody DataSource ds) {
		return dlmsService.createDataSource(ds);
	}

	/**
	 * Returns one data source matching the given id. Throws an IdNotFoundException
	 * if no data source with this id exists.
	 */
	@RequestMapping(value = "/datasource/search", params = "id")
	public DataSource getDataSourceById(@RequestParam Long id) {
		return dlmsService.getDataSourceById(id);
	}

	/**
	 * Returns one data source matching the given name. Throws a
	 * NameNotFoundException if no data source with this name exists.
	 */
	@RequestMapping(value = "/datasource/search", params = "name")
	public DataSource getDataSourceByName(@RequestParam String name) {
		return dlmsService.getDataSourceByName(name);
	}

	/**
	 * Updates the data source with the given id with the data provided Throws an
	 * IdNotFoundException if no cloud provider with this id exists.
	 */
	@RequestMapping(value = "/datasource/update", params = "id", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateDataDataSourceById(@RequestBody DataSource ds, @RequestParam Long id) {
		dlmsService.updateDataSourceById(ds, id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Updates the data source with the given name with the data provided Throws a
	 * NameNotFoundException if no cloud provider with this name exists.
	 */
	@RequestMapping(value = "/datasource/update", params = "name", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateDataSourceByName(@RequestBody DataSource ds, @RequestParam String name) {
		dlmsService.updateDataSourceByName(ds, name);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Returns a list of data center zones in the database
	 */
	@GetMapping(value = "/datacenterzone")
	public List<DataCenterZone> getAllDataCenterZones() {
		return dlmsService.getAllDataCenterZones();
	}

	/**
	 * Returns one data center zone matching the given data center id. Throws an
	 * IdNotFoundException if no data center zone with this id exists.
	 */
	@RequestMapping(value = "/datacenterzone/search", params = "datacenter_id")
	public DataCenterZone getDataCenterZoneById(@RequestParam Long datacenter_id) {
		return dlmsService.getDataCenterZoneById(datacenter_id);
	}

	/**
	 * Returns list of data center in data center zone matching the given zone id.
	 * Throws a IdNotFoundException if no data center zone with this name exists.
	 */
	@RequestMapping(value = "/datacenterzone/search", params = "zone_id")
	public List<DataCenterZone> getDataCenterZoneById(@RequestParam int zone_id) {
		return dlmsService.getDataCenterZoneByZoneId(zone_id);
	}

	/**
	 * Updates the data source with the given id with the data provided Throws an
	 * IdNotFoundException if no cloud provider with this id exists.
	 */
	@RequestMapping(value = "/datacenterzone/update", params = "datacenter_id", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateDataCenterZoneById(@RequestBody DataCenterZone dcz, @RequestParam Long id) {
		dlmsService.updateDataCenterZoneById(dcz, id);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * Returns a list of data center zones in the database
	 */
	@GetMapping(value = "/applicationcomponent_datasource_affinity")
	public List<ApplicationComponentDataSourceAffinity> getAllACDSAffinity() {
		return dlmsService.getAllACDSAffinity();
	}

}
