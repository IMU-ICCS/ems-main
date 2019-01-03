package com.example.latency.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.latency.model.DataCenter;

@Repository
public interface DataCenterRepository extends JpaRepository<DataCenter, Long> {
	Optional<DataCenter> findByName(String name);

}