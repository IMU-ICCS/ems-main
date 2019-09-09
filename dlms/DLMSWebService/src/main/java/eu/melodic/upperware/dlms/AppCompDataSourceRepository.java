package eu.melodic.upperware.dlms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository provided and managed by Spring.
 */
@Repository("appCompDataSourceRepository")
public interface AppCompDataSourceRepository extends JpaRepository<AppCompDataSource, Long>{

}
