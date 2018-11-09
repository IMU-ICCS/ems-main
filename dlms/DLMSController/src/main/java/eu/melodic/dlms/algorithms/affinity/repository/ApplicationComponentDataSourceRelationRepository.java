package eu.melodic.dlms.algorithms.affinity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.melodic.dlms.algorithms.affinity.dbModel.ApplicationComponentDataSourceRelation;

@Repository
public interface ApplicationComponentDataSourceRelationRepository
		extends JpaRepository<ApplicationComponentDataSourceRelation, Long> {

}
