package eu.melodic.dlms.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.melodic.dlms.db.model.Region;

/**
 * JPA repository provided and managed by Spring.
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
//	@Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END from Region r WHERE r.name =:name AND r.cloudProviderId =:cloudProviderId")
	boolean existsByName(String name);

	Region findByName(String name);

	boolean existsByNameAndCloudProviderId(String name, Long cloudProviderId);

	Region findByNameAndCloudProviderId(String name, Long cloudproviderid);

}