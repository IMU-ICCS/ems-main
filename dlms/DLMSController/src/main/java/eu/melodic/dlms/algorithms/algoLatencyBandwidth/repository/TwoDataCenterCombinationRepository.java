package eu.melodic.dlms.algorithms.algoLatencyBandwidth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.melodic.dlms.algorithms.algoLatencyBandwidth.dbModel.TwoDataCenterCombination;

@Repository
public interface TwoDataCenterCombinationRepository extends JpaRepository<TwoDataCenterCombination, Long> {

}
