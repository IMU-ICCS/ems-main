package eu.melodic.dlms;

import java.util.List;

import org.springframework.stereotype.Service;

import eu.melodic.dlms.db.model.ApplicationComponent;
import eu.melodic.dlms.db.model.ApplicationComponentDataSourceAffinity;
import eu.melodic.dlms.db.model.CloudProvider;
import eu.melodic.dlms.db.model.DataCenter;
import eu.melodic.dlms.db.model.DataCenterZone;
import eu.melodic.dlms.db.model.DataSource;
import eu.melodic.dlms.db.model.Region;
import eu.melodic.dlms.db.repository.ApplicationComponentDataSourceAffinityRepository;
import eu.melodic.dlms.db.repository.ApplicationComponentRepository;
import eu.melodic.dlms.db.repository.CloudProviderRepository;
import eu.melodic.dlms.db.repository.DataCenterRepository;
import eu.melodic.dlms.db.repository.DataCenterZoneRepository;
import eu.melodic.dlms.db.repository.DataSourceRepository;
import eu.melodic.dlms.db.repository.RegionRepository;
import eu.melodic.dlms.exception.IdNotFoundException;
import eu.melodic.dlms.exception.InvalidParameterException;
import eu.melodic.dlms.exception.NameNotFoundException;
import lombok.AllArgsConstructor;

/**
 * Implementation of DLMSService.
 */
@Service("dlmsService")
@AllArgsConstructor
public class DLMSServiceImpl implements DLMSService {

	private final CloudProviderRepository cpRepository;
	private final DataCenterRepository dcRepository;
	private final RegionRepository regionRepository;
	private final ApplicationComponentRepository acRepository;
	private final DataSourceRepository dsRepository;
	private final DataCenterZoneRepository dczRepository;
	private final ApplicationComponentDataSourceAffinityRepository acDSAffinityRepository;

	@Override
	public List<CloudProvider> getAllCloudProviders() {
		return cpRepository.findAll();
	}

	@Override
	public CloudProvider createCloudProvider(CloudProvider cp) {
		return cpRepository.save(cp);
	}

	@Override
	public CloudProvider getCloudProviderById(long id) {
		return cpRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
	}

	@Override
	public CloudProvider getCloudProviderByName(String name) {
		checkCPName(name);
		return cpRepository.findByName(name);
	}

	@Override
	public CloudProvider updateCloudProviderById(CloudProvider cp, long id) {
		ensureIdParameterNotNegative(id);
		checkCPId(id);

		cp.setId(id);
		cpRepository.save(cp);
		return null;
	}

	@Override
	public CloudProvider updateCloudProviderByName(CloudProvider cp, String name) {
		checkCPName(name);
		// get old cloud provider and update it
		CloudProvider cpOrig = cpRepository.findByName(name);
		cp.setId(cpOrig.getId());

		cpRepository.save(cp);
		return null;
	}

	@Override
	public List<DataCenter> getAllDataCenters() {
		return dcRepository.findAll();
	}

	@Override
	public DataCenter createDataCenter(DataCenter dc) {
		return dcRepository.save(dc);
	}

	@Override
	public DataCenter getDataCenterById(long id) {
		return dcRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
	}

	@Override
	public DataCenter getDataCenterByName(String name) {
		checkDCName(name);
		return dcRepository.findByName(name);
	}

	@Override
	public DataCenter updateDataCenterById(DataCenter dc, long id) {
		ensureIdParameterNotNegative(id);
		checkDCId(id);

		dc.setId(id);
		dcRepository.save(dc);
		return null;
	}

	@Override
	public DataCenter updateDataCenterByName(DataCenter dc, String name) {
		checkDCName(name);
		// get old data center and update it
		DataCenter dcOrig = dcRepository.findByName(name);
		dc.setId(dcOrig.getId());

		dcRepository.save(dc);
		return null;
	}

	@Override
	public List<Region> getAllRegions() {
		return regionRepository.findAll();
	}

	@Override
	public Region createRegion(Region region) {
		return regionRepository.save(region);
	}

	@Override
	public Region getRegionById(long id) {
		return regionRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
	}

	@Override
	public Region getRegionByName(String name) {
		checkRegionName(name);
		return regionRepository.findByName(name);
	}

	@Override
	public Region updateRegionById(Region region, long id) {
		ensureIdParameterNotNegative(id);
		checkRegionId(id);

		region.setId(id);
		regionRepository.save(region);
		return null;
	}

	@Override
	public Region updateRegionByName(Region region, String name) {
		checkRegionName(name);
		// get old region and update it
		CloudProvider regionOrig = cpRepository.findByName(name);
		region.setId(regionOrig.getId());

		regionRepository.save(region);
		return null;
	}

	@Override
	public List<ApplicationComponent> getAllApplicationComponents() {
		return acRepository.findAll();
	}

	@Override
	public ApplicationComponent createApplicationComponent(ApplicationComponent ac) {
		return acRepository.save(ac);
	}

	@Override
	public ApplicationComponent getApplicationComponentById(long id) {
		return acRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
	}

	@Override
	public ApplicationComponent getApplicationComponentByName(String name) {
		checkACName(name);
		return acRepository.findByName(name);
	}

	@Override
	public ApplicationComponent updateApplicationComponentById(ApplicationComponent ac, long id) {
		ensureIdParameterNotNegative(id);
		checkACId(id);

		ac.setId(id);
		acRepository.save(ac);
		return null;
	}

	@Override
	public ApplicationComponent updateApplicationComponentByName(ApplicationComponent ac, String name) {
		checkCPName(name);
		// get old application component and update it
		ApplicationComponent acOrig = acRepository.findByName(name);
		ac.setId(acOrig.getId());

		acRepository.save(ac);
		return null;
	}

	@Override
	public List<DataSource> getAllDataSources() {
		return dsRepository.findAll();
	}

	@Override
	public DataSource createDataSource(DataSource ds) {
		return dsRepository.save(ds);
	}

	@Override
	public DataSource getDataSourceById(long id) {
		return dsRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
	}

	@Override
	public DataSource getDataSourceByName(String name) {
		checkDSName(name);
		return dsRepository.findByName(name);
	}

	@Override
	public DataSource updateDataSourceById(DataSource ds, long id) {
		ensureIdParameterNotNegative(id);
		checkDSId(id);

		ds.setId(id);
		dsRepository.save(ds);
		return null;
	}

	@Override
	public DataSource updateDataSourceByName(DataSource ds, String name) {
		checkCPName(name);
		// get old data source and update it
		DataSource dsOrig = dsRepository.findByName(name);
		ds.setId(dsOrig.getId());

		dsRepository.save(ds);
		return null;
	}
	
	@Override
	public List<DataCenterZone> getAllDataCenterZones() {
		return dczRepository.findAll();
	}

	@Override
	public DataCenterZone getDataCenterZoneById(long id) {
		return dczRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
	}

	@Override
	public List<DataCenterZone> getDataCenterZoneByZoneId(int zone) {
		checkDCZZone(zone);
		return dczRepository.findByZone(zone);
	}

	@Override
	public DataCenterZone updateDataCenterZoneById(DataCenterZone dcz, long id) {
		ensureIdParameterNotNegative(id);
		checkDCZId(id);

		dcz.setDataCenterId(id);
		dczRepository.save(dcz);
		return null;
	}
	
	@Override
	public List<ApplicationComponentDataSourceAffinity> getAllACDSAffinity() {
		return acDSAffinityRepository.findAll();
	}

	public void checkCPName(String name) {
		if (!cpRepository.existsByName(name))
			throw new NameNotFoundException(name);
	}

	public void checkDCName(String name) {
		if (!dcRepository.existsByName(name))
			throw new NameNotFoundException(name);
	}

	public void checkRegionName(String name) {
		if (!regionRepository.existsByName(name))
			throw new NameNotFoundException(name);
	}

	public void checkACName(String name) {
		if (!acRepository.existsByName(name))
			throw new NameNotFoundException(name);
	}

	public void checkDSName(String name) {
		if (!dsRepository.existsByName(name))
			throw new NameNotFoundException(name);
	}
	
	public void checkDCZZone(int zone) {
		if (!dczRepository.existsByZone(zone))
			throw new IdNotFoundException(zone);
	}

	public void checkCPId(long id) {
		if (!cpRepository.existsById(id))
			throw new IdNotFoundException(id);
	}

	public void checkDCId(long id) {
		if (!dcRepository.existsById(id))
			throw new IdNotFoundException(id);
	}

	public void checkRegionId(long id) {
		if (!regionRepository.existsById(id))
			throw new IdNotFoundException(id);
	}

	public void checkACId(long id) {
		if (!acRepository.existsById(id))
			throw new IdNotFoundException(id);
	}

	public void checkDSId(long id) {
		if (!dsRepository.existsById(id))
			throw new IdNotFoundException(id);
	}
	
	public void checkDCZId(long id) {
		if (!dczRepository.existsById(id))
			throw new IdNotFoundException(id);
	}

	private void ensureIdParameterNotNegative(long id) {
		if (id < 0) {
			throw new InvalidParameterException("Parameter must be >= 0");
		}
	}

}
