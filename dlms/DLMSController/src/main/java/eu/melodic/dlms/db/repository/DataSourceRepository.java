package eu.melodic.dlms.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.melodic.dlms.db.model.DataSource;

@Repository
public interface DataSourceRepository extends JpaRepository<DataSource, Long> {
	boolean existsByName(String name);

	DataSource findByName(String name);
}
