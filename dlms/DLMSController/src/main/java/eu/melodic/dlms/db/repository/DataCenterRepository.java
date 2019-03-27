package eu.melodic.dlms.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.melodic.dlms.db.model.DataCenter;

/**
 * JPA repository provided and managed by Spring.
 */
@Repository
public interface DataCenterRepository extends JpaRepository<DataCenter, Long> {
	DataCenter findByName(String name);
	
	boolean existsByName(String name);
}