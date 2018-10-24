package eu.melodic.dlms.algorithms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.melodic.dlms.algorithms.dbModel.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

}