package eu.melodic.dlms.algorithms.algoAffinity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.melodic.dlms.algorithms.algoAffinity.dbModel.ApplicationComponentDataSourceRelation;

@Repository
public interface ApplicationComponentDataSourceRelationRepository
		extends JpaRepository<ApplicationComponentDataSourceRelation, Long> {

}
