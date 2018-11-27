/*
 * Melodic EU Project
 * DLMS WebService
 * Data Source Repository
 * @author: ferox
 */

package eu.melodic.upperware.dlms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * JPA repository provided and managed by Spring.
 */
@Repository("dataSourceRepository")
public interface DataSourceRepository extends JpaRepository<DataSource, Long> {

	DataSource findByName(String name);

	@Transactional
	void deleteByName(String name);

	@Query("SELECT CASE WHEN COUNT(ds) > 0 THEN true ELSE false END from DataSource ds WHERE ds.name =:name")
	boolean existsByName(@Param("name") String name);
}
