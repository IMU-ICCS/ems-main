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
import com.example.latency.model.CloudProvider;
import com.example.latency.repository.CloudProviderRepository;

@RestController
@RequestMapping("/api")
public class CloudProviderController {

	@Autowired
	CloudProviderRepository cloudProviderRepository;

	// Get All cloud providers
	@GetMapping("/cloudprovider")
	public List<CloudProvider> getAllCloudProvider() {
		return cloudProviderRepository.findAll();
	}

	// Create a new cloud provider
	@PostMapping("/cloudprovider")
	public CloudProvider createCloudProvider(@Valid @RequestBody CloudProvider cloudProvider) {
		return cloudProviderRepository.save(cloudProvider);
	}

	// Get a single cloud provider
	@GetMapping("/cloudprovider/{id}")
	public CloudProvider getCloudProviderById(@PathVariable(value = "id") Long cloudProviderId) {
		return cloudProviderRepository.findById(cloudProviderId)
				.orElseThrow(() -> new ResourceNotFoundException("CloudProvider", "id", cloudProviderId));
	}

}
