package com.example.latency.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.example.latency.model.CloudProvider;

@Repository
@Transactional
public class CloudProviderService {

	@PersistenceContext
	private EntityManager entityManager;

	public CloudProvider find(long id) {
		return entityManager.find(CloudProvider.class, id);
	}

}
