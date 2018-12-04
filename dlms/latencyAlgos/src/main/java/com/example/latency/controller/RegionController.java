package com.example.latency.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.latency.exception.ResourceNotFoundException;
import com.example.latency.model.Region;
import com.example.latency.repository.RegionRepository;

@RestController
@RequestMapping("/api")
public class RegionController {

	@Autowired
	RegionRepository regionRespository;

	// Get All cloud providers
	@GetMapping("/region")
	public List<Region> getAllRegion() {
		return regionRespository.findAll();
	}

	// Create a new cloud provider
	@PostMapping("/region")
	public Region createRegion(@Valid @RequestBody Region region) {
		return regionRespository.save(region);
	}

	// Get a single cloud provider
	@GetMapping("/region/{id}")
	public Region getRegionById(@PathVariable(value = "id") Long regionId) {
		return regionRespository.findById(regionId)
				.orElseThrow(() -> new ResourceNotFoundException("Region", "id", regionId));
	}

}
