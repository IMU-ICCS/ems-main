package eu.melodic.dlms.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.melodic.dlms.db.model.ApplicationComponent;

@Repository
public interface ApplicationComponentRepository extends JpaRepository<ApplicationComponent, Long> {

}
