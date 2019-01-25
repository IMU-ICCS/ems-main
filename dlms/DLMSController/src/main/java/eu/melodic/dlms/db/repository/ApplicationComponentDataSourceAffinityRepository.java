package eu.melodic.dlms.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.melodic.dlms.db.model.ApplicationComponentDataSourceAffinity;

@Repository
public interface ApplicationComponentDataSourceAffinityRepository
		extends JpaRepository<ApplicationComponentDataSourceAffinity, Long> {

	boolean existsByAcDsKey();
	
}
