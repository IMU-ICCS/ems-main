package eu.melodic.dlms.db.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import eu.melodic.dlms.db.model.TwoDataCenters;

/**
 * JPA repository provided and managed by Spring.
 */
@Repository
public interface TwoDataCentersRepository extends JpaRepository<TwoDataCenters, Long> {
	String databaseName = "two_data_centers";

	List<TwoDataCenters> findByDc1IdAndDc2IdOrderByIdDesc(Long dc1Id, Long dc2Id, Pageable pageable);

	@Query(value = "select * from " + databaseName
			+ " where dc1Id = :dc1Id and dc2Id = :dc2Id and timestamp > :timestamp", nativeQuery = true)
	List<TwoDataCenters> findByLatestRecords(@Param("dc1Id") Long dc1Id, @Param("dc2Id") Long dc2Id,
			@Param("timestamp") Date timestamp);

//	@Query(value = "select * from " + databaseName
//			+ " where dc1Id = :dc1Id and dc2Id = :dc2Id and timestamp > :timestamp order by timestamp desc", nativeQuery = true)
//	List<TwoDataCenters> findByLatestRecordsOrdered(@Param("dc1Id") Long dc1Id, @Param("dc2Id") Long dc2Id,
//			@Param("timestamp") Date timestamp);
//
	@Temporal(TemporalType.DATE)
	@Transactional
	void deleteBytimestampBefore(Date expiryDate);

}