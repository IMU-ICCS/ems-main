package eu.melodic.dlms.algorithms.algoAffinity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.melodic.dlms.algorithms.algoAffinity.dbModel.ApplicationComponent;

@Repository
public interface ApplicationComponentRepository extends JpaRepository<ApplicationComponent, Long> {

}
