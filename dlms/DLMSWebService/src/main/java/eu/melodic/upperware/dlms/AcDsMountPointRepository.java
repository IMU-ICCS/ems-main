package eu.melodic.upperware.dlms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * JPA repository provided and managed by Spring.
 */
@Repository("acDsMountPointRepository")
public interface AcDsMountPointRepository extends JpaRepository<AcDsMountPoint, Long>{
	
	AcDsMountPoint findByAcName(String acName);
	
	@Query("SELECT CASE WHEN COUNT(ac) > 0 THEN true ELSE false END from AcDsMountPoint ac WHERE ac.acName =:name")
	boolean existsByAcName(@Param("name") String name);
}
