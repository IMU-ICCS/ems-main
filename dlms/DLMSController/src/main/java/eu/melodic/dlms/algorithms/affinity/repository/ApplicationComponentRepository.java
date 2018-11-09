package eu.melodic.dlms.algorithms.affinity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.melodic.dlms.algorithms.affinity.dbModel.ApplicationComponent;

@Repository
public interface ApplicationComponentRepository extends JpaRepository<ApplicationComponent, Long> {

}
