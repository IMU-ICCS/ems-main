package eu.melodic.dlms.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.melodic.dlms.db.model.CloudProvider;
@Repository
public interface CloudProviderRepository extends JpaRepository<CloudProvider, Long> {

//	@Query("SELECT CASE WHEN COUNT(cp) > 0 THEN true ELSE false END from CloudProvider cp WHERE cp.name =:name")
	boolean existsByName(String name);
	CloudProvider findByName(String name);
}