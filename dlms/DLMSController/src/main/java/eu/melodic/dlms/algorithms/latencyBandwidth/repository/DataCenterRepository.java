package eu.melodic.dlms.algorithms.algoLatencyBandwidth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.melodic.dlms.algorithms.algoLatencyBandwidth.dbModel.DataCenter;

@Repository
public interface DataCenterRepository extends JpaRepository<DataCenter, Long> {
	Optional<DataCenter> findByName(String name);

}