package com.example.latency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.latency.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

}