package com.example.latency.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.latency.model.DataCenterLatencyBandwidth;

@Repository
public interface DataCenterLatencyBandwidthRepository extends JpaRepository<DataCenterLatencyBandwidth, Long> {
	String databaseName = "datacenter_latency_bandwidth";

	@Query(value = "select * from " + databaseName
			+ " where dc1 = :dc1 and dc2 = :dc2 and timestamp > :timestamp", nativeQuery = true)
//	Optional<DataCenterLatencyBandwidth> findByLatestRecords(@Param("dc1") String dc1, @Param("dc2") String dc2,
//			@Param("timestamp") Date timestamp);
	List<DataCenterLatencyBandwidth> findByLatestRecords(@Param("dc1") String dc1, @Param("dc2") String dc2,
			@Param("timestamp") Date timestamp);

	@Temporal(TemporalType.DATE)
	@Transactional
	void deleteBytimestampBefore(Date expiryDate);

}