package eu.melodic.dlms;

import java.util.List;

import eu.melodic.dlms.db.model.ApplicationComponent;
import eu.melodic.dlms.db.model.ApplicationComponentDataSourceAffinity;
import eu.melodic.dlms.db.model.CloudProvider;
import eu.melodic.dlms.db.model.DataCenter;
import eu.melodic.dlms.db.model.DataCenterZone;
import eu.melodic.dlms.db.model.ControllerDataSource;
import eu.melodic.dlms.db.model.Region;

/**
 * DLMS service providing various functionalities like adding cloudprovider.
 */
public interface DLMSService {

	/**
	 * Returns all the cloud providers in the database
	 */
	List<CloudProvider> getAllCloudProviders();

	/**
	 * Create a new cloud provider
	 */
	CloudProvider createCloudProvider(CloudProvider cp);

	/**
	 * Returns one cloud provider matching the given id. Throws an
	 * IdNotFoundException if no cloud provider with this id exists.
	 */
	CloudProvider getCloudProviderById(long id);

	/**
	 * Returns one cloud provider matching the given name. Throws a
	 * NameNotFoundException if no cloud provider with this name exists.
	 */
	CloudProvider getCloudProviderByName(String name);

	/**
	 * Updates the cloud provider with the given id with the data provided Throws an
	 * IdNotFoundException if no cloud provider with this id exists.
	 */
	CloudProvider updateCloudProviderById(CloudProvider cp, long id);

	/**
	 * Updates the cloud provider with the given name with the data provided Throws
	 * a NameNotFoundException if no cloud provider with this name exists.
	 */
	CloudProvider updateCloudProviderByName(CloudProvider cp, String name);

	/**
	 * Returns all the data centers in the database
	 */
	List<DataCenter> getAllDataCenters();

	/**
	 * Create a new data center
	 */
	DataCenter createDataCenter(DataCenter dc);

	/**
	 * Returns one data center matching the given id. Throws an IdNotFoundException
	 * if no data center with this id exists.
	 */
	DataCenter getDataCenterById(long id);

	/**
	 * Returns one data center center matching the given name. Throws a
	 * NameNotFoundException if no data center with this name exists.
	 */
	DataCenter getDataCenterByName(String name);

	/**
	 * Updates the data center with the given id with the data provided Throws an
	 * IdNotFoundException if no data center with this id exists.
	 */
	DataCenter updateDataCenterById(DataCenter ds, long id);

	/**
	 * Updates the data center with the given name with the data provided Throws a
	 * NameNotFoundException if no data center with this name exists.
	 */
	DataCenter updateDataCenterByName(DataCenter ds, String name);

	/**
	 * Returns all the regions in the database
	 */
	List<Region> getAllRegions();

	/**
	 * Create a new region
	 */
	Region createRegion(Region region);

	/**
	 * Returns one region matching the given id. Throws an IdNotFoundException if no
	 * region with this id exists.
	 */
	Region getRegionById(long id);

	/**
	 * Returns one region matching the given name. Throws a NameNotFoundException if
	 * no region with this name exists.
	 */
	Region getRegionByName(String name);

	/**
	 * Updates the region with the given id with the data provided Throws an
	 * IdNotFoundException if no region with this id exists.
	 */
	Region updateRegionById(Region region, long id);

	/**
	 * Updates the region with the given name with the data provided Throws a
	 * NameNotFoundException if no region with this name exists.
	 */
	Region updateRegionByName(Region region, String name);

	/**
	 * Returns all the application components in the database
	 */
	List<ApplicationComponent> getAllApplicationComponents();

	/**
	 * Create a new application component
	 */
	ApplicationComponent createApplicationComponent(ApplicationComponent ac);

	/**
	 * Returns one application component matching the given id. Throws an
	 * IdNotFoundException if no application component with this id exists.
	 */
	ApplicationComponent getApplicationComponentById(long id);

	/**
	 * Returns one application component matching the given name. Throws a
	 * NameNotFoundException if no application component with this name exists.
	 */
	ApplicationComponent getApplicationComponentByName(String name);

	/**
	 * Updates the application component with the given id with the data provided
	 * Throws an IdNotFoundException if no application component with this id
	 * exists.
	 */
	ApplicationComponent updateApplicationComponentById(ApplicationComponent ac, long id);

	/**
	 * Updates the application component with the given name with the data provided
	 * Throws a NameNotFoundException if no application component with this name
	 * exists.
	 */
	ApplicationComponent updateApplicationComponentByName(ApplicationComponent ac, String name);

	/**
	 * Returns all the data sources in the database
	 */
	List<ControllerDataSource> getAllDataSources();

	/**
	 * Create a new data source
	 */
	ControllerDataSource createDataSource(ControllerDataSource ds);

	/**
	 * Returns one data source matching the given id. Throws an IdNotFoundException
	 * if no data source with this id exists.
	 */
	ControllerDataSource getDataSourceById(long id);

	/**
	 * Returns one data source matching the given name. Throws a
	 * NameNotFoundException if no data source with this name exists.
	 */
	ControllerDataSource getDataSourceByName(String name);

	/**
	 * Updates the data source with the given id with the data provided Throws an
	 * IdNotFoundException if no data source with this id exists.
	 */
	ControllerDataSource updateDataSourceById(ControllerDataSource ds, long id);

	/**
	 * Updates the data source with the given name with the data provided Throws a
	 * NameNotFoundException if no data source with this name exists.
	 */
	ControllerDataSource updateDataSourceByName(ControllerDataSource ds, String name);

	/**
	 * Returns all the data center zones in the database
	 */
	List<DataCenterZone> getAllDataCenterZones();

	/**
	 * Returns one data center zone matching the given data center id. Throws an
	 * IdNotFoundException if no data center zone with this id exists.
	 */
	DataCenterZone getDataCenterZoneById(long id);

	/**
	 * Returns list of data center in data center zone matching the given zone id.
	 * Throws a IdNotFoundException if no data center zone with this name exists.
	 */
	List<DataCenterZone> getDataCenterZoneByZoneId(int zone);

	/**
	 * Updates the data center zone with the given id with the data provided Throws
	 * an IdNotFoundException if no data center zone with this id exists.
	 */
	DataCenterZone updateDataCenterZoneById(DataCenterZone dcz, long id);

	/**
	 * Returns all the application component-data source affinity in the database
	 */
	List<ApplicationComponentDataSourceAffinity> getAllACDSAffinity();
}
