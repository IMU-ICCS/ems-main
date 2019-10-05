package eu.melodic.dlms.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.melodic.dlms.db.model.ControllerDataSource;

@Repository
public interface ControllerDataSourceRepository extends JpaRepository<ControllerDataSource, Long> {
	boolean existsByName(String name);

	ControllerDataSource findByName(String name);
}
