package eu.melodic.dlms.algorithms.cost;

import java.util.Optional;

import eu.melodic.dlms.db.model.CloudProvider;
import eu.melodic.dlms.db.model.DataCenter;
import eu.melodic.dlms.db.model.DataCenterZone;
import eu.melodic.dlms.db.model.Region;
import eu.melodic.dlms.db.repository.CloudProviderRepository;
import eu.melodic.dlms.db.repository.DataCenterRepository;
import eu.melodic.dlms.db.repository.DataCenterZoneRepository;
import eu.melodic.dlms.db.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class Algo_ComputeCost {
	// configuration parameters
	private long src;
	private long dst;
	private long size;
	private long max; // to convert cost between 0 and 1
	private long min; // to convert cost between 0 and 1

	private final CloudProviderRepository cpRepository;
	private final RegionRepository regionRepository;
	private final DataCenterRepository dcRepository;
	private final DataCenterZoneRepository dcZoneRepository;

	private boolean isValid = true;

	/**
	 * Compute the total cost
	 * Return value between 0 and 1
	 */
	public double totalCost() {
		long transfer = 0;
		int dist = 1;
		DataCenter srcDc = getDc(src);
		DataCenter dstDc = getDc(dst);
		CloudProvider srcCp = getCp(src);
		CloudProvider dstCp = getCp(dst);
		DataCenterZone srcZone = getZone(src);
		DataCenterZone dstZone = getZone(dst);

		if (isValid) {
			if (!isBothPrivate(srcCp, dstCp)) {
				if (!isSameCp(srcCp, dstCp))
					dist += 10;
				else {
					Region srcRegion = getRegion(srcDc);
					Region dstRegion = getRegion(dstDc);
					if (isValid) {
						if (!isSameRegion(srcRegion, dstRegion))
							dist += 5;
					} else
						log.error("Problem getting the region");
				}
			}else
				log.error("Problem getting the cloud provider");
			if (!isSameZone(srcZone, dstZone))
				dist += 2;
		}
		transfer = size * dist;
		log.info("The cost of the solution was computed successfully");
		// if max and min is not known
		// return (Norm (transfer));	
		
		// if max and min is known
		return (Norm (transfer, max, min));
	}

	/**
	 * Are both source and destination in the same region
	 */
	private boolean isSameRegion(Region srcRegion, Region dstRegion) {
		return (srcRegion.getName().equalsIgnoreCase(dstRegion.getName()));
	}

	/**
	 * Get regions
	 */
	private Region getRegion(DataCenter dc) {
		long id = dc.getRegionId();
		if (regionRepository.existsById(id)) {
			Optional<Region> region = regionRepository.findById(id);
			if (region.isPresent())
				return region.get();
		}
		isValid = false;
		return null;
	}

	/**
	 * Get datacenters
	 */
	private DataCenter getDc(long id) {
		if (dcRepository.existsById(id)) {
			Optional<DataCenter> dc = dcRepository.findById(id);
			if (dc.isPresent())
				return dc.get();
		}
		isValid = false;
		return null;
	}

	/**
	 * Are both source and destination in the same zone
	 */
	private boolean isSameZone(DataCenterZone srcZone, DataCenterZone dstZone) {
		return (srcZone.getZone() == dstZone.getZone() ? true : false);
	}

	/**
	 * Get geographical zone
	 */
	private DataCenterZone getZone(long id) {
		if (dcZoneRepository.existsById(id)) {
			Optional<DataCenterZone> zone = dcZoneRepository.findById(id);
			if (zone.isPresent())
				return zone.get();
		}
		isValid = false;
		return null;
	}

	/**
	 * Are both source and destination private
	 */
	private boolean isBothPrivate(CloudProvider srcCp, CloudProvider dstCp) {
		return (!srcCp.isPublic() && !dstCp.isPublic() ? true : false);
	}

	/**
	 * Do both source and destination have same cloud provider
	 */
	private boolean isSameCp(CloudProvider srcCp, CloudProvider dstCp) {
		return (srcCp.getName().equalsIgnoreCase(dstCp.getName()));

	}

	/**
	 * Get cloud provider
	 */
	private CloudProvider getCp(long id) {
		if (cpRepository.existsById(id)) {
			Optional<CloudProvider> cp = cpRepository.findById(id);
			if (cp.isPresent())
				return cp.get();
		}
		isValid = false;
		return null;
	}

	/**
	 * Normalize the value between 0 and 1
	 * Not so accurate
	 * Use in absence of max and min
	 */
	public double Norm(long value) {
		return (value / (double) (value + 1));
	}
	
	/**
	 * Normalize the value between 0 and 1 using max and min
	 * Better normalization function
	 */
	public double Norm(long value, long max, long min) {
		return ((value-min)/(double)(max-min));
	}	

}
