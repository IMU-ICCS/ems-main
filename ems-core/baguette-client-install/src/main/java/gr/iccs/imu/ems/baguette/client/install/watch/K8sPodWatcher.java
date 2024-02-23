/*
 * Copyright (C) 2017-2025 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package gr.iccs.imu.ems.baguette.client.install.watch;

import gr.iccs.imu.ems.util.PasswordUtil;
import io.fabric8.kubernetes.api.model.NodeAddress;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.dsl.Resource;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Kubernetes cluster pods watcher service
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class K8sPodWatcher implements InitializingBean {
    private static final String K8S_SERVICE_ACCOUNT_SECRETS_PATH_DEFAULT = "/var/run/secrets/kubernetes.io/serviceaccount";

    private final TaskScheduler taskScheduler;
    private final PasswordUtil passwordUtil;

    @Override
    public void afterPropertiesSet() throws Exception {
        taskScheduler.scheduleWithFixedDelay(this::doWatch, Instant.now().plusSeconds(20), Duration.ofSeconds(10));
    }

    private String getConfig(@NonNull String key, String defaultValue) {
        String value = System.getenv(key);
        return value==null ? defaultValue : value;
    }

    private void doWatch() {
        try {
            log.debug("K8sPodWatcher: BEGIN: doWatch");

            String serviceAccountPath = getConfig("K8S_SERVICE_ACCOUNT_SECRETS_PATH", K8S_SERVICE_ACCOUNT_SECRETS_PATH_DEFAULT);
            String masterUrl = getConfig("KUBERNETES_SERVICE_HOST", null);
            String caCert = Files.readString(Paths.get(serviceAccountPath, "ca.crt"));
            String token = Files.readString(Paths.get(serviceAccountPath, "token"));
            String namespace = Files.readString(Paths.get(serviceAccountPath, "namespace"));
            log.debug("""
                            K8sPodWatcher:
                              Master URL: {}
                                CA cert.:
                            {}
                                   Token: {}
                               Namespace: {}""",
                    masterUrl, caCert.trim(), passwordUtil.encodePassword(token), namespace);

            // Configure and start Kubernetes API client
            Config config = new ConfigBuilder()
                    .withMasterUrl(masterUrl)
                    .withCaCertData(caCert)
                    .withOauthToken(token)
                    .build();
            try (KubernetesClient client = new KubernetesClientBuilder().withConfig(config).build()) {
                // List PODs
                AtomicInteger counter = new AtomicInteger();
                Set<String> hostIpSet = new HashSet<>();
                client.pods()
                        .inAnyNamespace()
//                        .withLabel("nebulous.application")
                        .resources()
                        .map(Resource::item)
                        .forEach(pod -> {
                            int num = counter.getAndIncrement();
                            String hostIp;
                            log.warn("""
                                            >>>>>>>>>>>>>>>>>  POD-{}:...
                                                         full-name: {}
                                                     metadata-name: {}
                                                            pod IP: {}
                                                           host IP: {}
                                                           message: {}
                                                            labels: {}
                                              
                                            """,
                                    num,
                                    pod.getFullResourceName(),
                                    pod.getMetadata().getName(),
                                    pod.getStatus().getPodIP(),
                                    hostIp = pod.getStatus().getHostIP(),
                                    pod.getStatus().getMessage(),
                                    pod.getMetadata().getLabels()
                            );
                            hostIpSet.add(hostIp);
                        });
                log.info("K8sPodWatcher: Found {} matching pods", counter.get());
                log.info("K8sPodWatcher: Host IPs: {}", hostIpSet);

                // List Nodes
                AtomicInteger nodeCounter = new AtomicInteger();
                Set<String> nodeIpSet = new HashSet<>();
                client.nodes()
                        .resources()
                        .map(Resource::item)
                        .forEach(node -> {
                            int num = nodeCounter.getAndIncrement();
                            Set<String> nodeIps = node.getStatus().getAddresses().stream()
                                    .filter(na -> ! "Hostname".equalsIgnoreCase(na.getType()))
                                    .map(NodeAddress::getAddress).collect(Collectors.toSet());
                            log.warn("""
                                            >>>>>>>>>>>>>>>>>  Node-{}:...
                                                         full-name: {}
                                                     metadata-name: {}
                                                        machine ID: {}
                                                           node OS: {}
                                                          node IPs: {}
                                                            labels: {}
                                            """,
                                    num,
                                    node.getFullResourceName(),
                                    node.getMetadata().getName(),
                                    node.getStatus().getNodeInfo().getMachineID(),
                                    node.getStatus().getNodeInfo().getOperatingSystem(),
                                    node.getStatus().getAddresses(),
                                    node.getMetadata().getLabels()
                            );
                            nodeIpSet.addAll(nodeIps);
                        });
                log.info("K8sPodWatcher: Found {} matching nodes", nodeCounter.get());
                log.info("K8sPodWatcher: Host IPs: {}", nodeIpSet);
            }
        } catch (Exception e) {
            log.warn("K8sPodWatcher: Error while running doWatch: ", e);
        }
    }
}
