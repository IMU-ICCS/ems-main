package eu.melodic.dlms.algorithms.algoLatencyBandwidth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.melodic.dlms.algorithms.algoLatencyBandwidth.dbModel.CloudProvider;

@Repository
public interface CloudProviderRepository extends JpaRepository<CloudProvider, Long> {

}