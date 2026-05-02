/*
 * Copyright (C) 2023-2026 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package gr.iccs.imu.ems.k8s;

import gr.iccs.imu.ems.control.controller.ControlServiceRequestInfo;
import gr.iccs.imu.ems.control.controller.NodeRegistrationCoordinator;
import gr.iccs.imu.ems.control.plugin.AppModelPlugin;
import gr.iccs.imu.ems.translate.TranslationContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class K8sEmsClientDeploymentPostProcessingPlugin implements AppModelPlugin, InitializingBean {
	private final K8sProperties properties;
	private final ApplicationContext applicationContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("K8sEmsClientDeploymentPostProcessingPlugin: Started");
	}

	@Override
	public void postProcessingNewAppModel(String appModelId, ControlServiceRequestInfo requestInfo, TranslationContext translationContext) {
		// Call control-service to deploy EMS clients
		if (properties.isDeployEmsClientsOnKubernetesEnabled()) {
			try {
				log.info("K8sEmsClientDeploymentPostProcessingPlugin: Start deploying EMS clients...");
				String id = "dummy-" + System.currentTimeMillis();
				Map<String, Object> nodeInfo = new HashMap<>(Map.of(
						"id", id,
						"name", id,
						"type", "K8S",
						"provider", "Kubernetes",
						"zone-id", ""
				));
				applicationContext.getBean(NodeRegistrationCoordinator.class)
						.registerNode("", nodeInfo, translationContext);
				log.debug("K8sEmsClientDeploymentPostProcessingPlugin: EMS clients deployment started");
			} catch (Exception e) {
				log.warn("K8sEmsClientDeploymentPostProcessingPlugin: EXCEPTION while starting EMS client deployment: ", e);
			}
		} else
			log.info("K8sEmsClientDeploymentPostProcessingPlugin: EMS clients deployment is disabled");
    }
}