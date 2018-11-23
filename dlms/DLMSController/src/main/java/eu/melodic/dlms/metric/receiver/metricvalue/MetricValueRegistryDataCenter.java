package eu.melodic.dlms.metric.receiver.metricvalue;

import java.util.Date;

import eu.melodic.dlms.db.model.CloudProvider;
import eu.melodic.dlms.db.model.DataCenter;
import eu.melodic.dlms.db.model.Region;
import eu.melodic.dlms.db.model.TwoDataCenters;
import eu.melodic.dlms.db.repository.CloudProviderRepository;
import eu.melodic.dlms.db.repository.DataCenterRepository;
import eu.melodic.dlms.db.repository.RegionRepository;
import eu.melodic.dlms.db.repository.TwoDataCentersRepository;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class MetricValueRegistryDataCenter<T> {
	private final CloudProviderRepository cpRepository;
	private final DataCenterRepository dcRepository;
	private final RegionRepository regionRepository;
	private final TwoDataCentersRepository twoDcRepository;
	private final MetricValueEventDataCenter event;

	public void saveMetricValues() {
		// for the first data center
		long cpId1 = storeCloudProvider(this.event.getCloudProvider1());
		long regionId1 = storeRegion(this.event.getRegion1(), cpId1);
		long dcId1 = storeDataCenter(this.event.getDataCenter1(), regionId1);
		// for the second data center
		long cpId2 = storeCloudProvider(this.event.getCloudProvider2());
		long regionId2 = storeRegion(this.event.getRegion2(), cpId2);
		long dcId2 = storeDataCenter(this.event.getDataCenter2(), regionId2);
		// save the values between them
		storeTwoDataCenter(dcId1, dcId2, this.event.getLatencyVal(), this.event.getBandwidthVal(),
				new Date(this.event.getTimeStamp()));
	}

	/**
	 * Store the cloud provider and return the cloud provider id if it does not exist
	 */
	public long storeCloudProvider(String name) {
		if (!cpRepository.existsByName(name)) {
			CloudProvider cloudProvider = new CloudProvider(name);
			cpRepository.save(cloudProvider);
		}
		// return id for cloud provider
		return cpRepository.findByName(name).getId();
	}

	/**
	 * Store the region and return region id if it does not exist
	 */
	public long storeRegion(String name, long cpId) {
		if (!regionRepository.existsByNameAndCloudProviderId(name, cpId)) {
			Region region = new Region(name, cpId);
			regionRepository.save(region);
		}
		// return id for region
		return regionRepository.findByNameAndCloudProviderId(name, cpId).getId();
	}
	
	/**
	 * Store the data center and return region id if it does not exist
	 */
	public long storeDataCenter(String name, long regionId) {
		if (!dcRepository.existsByName(name)) {
			DataCenter dataCenter = new DataCenter(name,regionId);	
			dcRepository.save(dataCenter);
		}
		// return id for data center
		return dcRepository.findByName(name).getId();
	}
	
	
	/**
	 * Store the values between two data centers 
	 */
	public void storeTwoDataCenter(long dc1Id, long dc2Id, int latency, int bandwidth, Date timestamp) {
		TwoDataCenters twoDataCenters = new TwoDataCenters(dc1Id, dc2Id, latency, bandwidth, timestamp);
		twoDcRepository.save(twoDataCenters);
	}

}
