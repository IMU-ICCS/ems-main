package eu.melodic.dlms.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.melodic.dlms.db.model.DataCenterZone;

public interface DataCenterZoneRepository extends JpaRepository<DataCenterZone, Long> {
	DataCenterZone findByDataCenterId(long id);
	boolean existsByZone(int zone);
	List<DataCenterZone> findByZone(int zone);
	boolean existsByDataCenterName(String name);
	DataCenterZone findByDataCenterName(String name);
}
