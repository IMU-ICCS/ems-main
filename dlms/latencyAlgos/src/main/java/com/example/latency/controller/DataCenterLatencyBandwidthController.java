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
import com.example.latency.model.DataCenterLatencyBandwidth;
import com.example.latency.repository.DataCenterLatencyBandwidthRepository;

@RestController
@RequestMapping("/api")
public class DataCenterLatencyBandwidthController {

	@Autowired
	DataCenterLatencyBandwidthRepository dataCenterLatencyBandwidthRepository;

	// Get All
	@GetMapping("/dcrelation")
	public List<DataCenterLatencyBandwidth> getAllDataCenterLatencyBandwidth() {
		return dataCenterLatencyBandwidthRepository.findAll();
	}

	// Create a new
	@PostMapping("/dcrelation")
	public DataCenterLatencyBandwidth createDataCenterLatencyBandwidth(
			@Valid @RequestBody DataCenterLatencyBandwidth dataCenterLatencyBandwidth) {
		return dataCenterLatencyBandwidthRepository.save(dataCenterLatencyBandwidth);
	}

	// Get a single
	@GetMapping("/dcrelation/{id}")
	public DataCenterLatencyBandwidth getDataCenterLatencyBandwidthById(
			@PathVariable(value = "id") Long dataCenterLatencyBandwidthId) {
		return dataCenterLatencyBandwidthRepository.findById(dataCenterLatencyBandwidthId).orElseThrow(
				() -> new ResourceNotFoundException("DataCenterLatencyBandwidth", "id", dataCenterLatencyBandwidthId));
	}

//	public static final String FIND_PROJECTS = "SELECT projectId, projectName FROM projects";

}
