/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.plugin;

import com.google.gson.Gson;
import eu.melodic.event.baguette.client.BaguetteClientProperties;
import eu.melodic.event.baguette.client.CommandExecutor;
import eu.melodic.event.baguette.client.Sshc;
import eu.melodic.event.util.EventBus;
import eu.melodic.event.util.PasswordUtil;
import eu.melodic.event.util.Plugin;
import io.atomix.cluster.ClusterMembershipEvent;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.sshd.common.util.io.IoUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Client-side Self-Healing plugin
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SelfHealingPlugin implements Plugin, InitializingBean, EventBus.EventConsumer<String,Object,Object> {
    private final BaguetteClientProperties properties;
    private final CommandExecutor commandExecutor;
    private final EventBus<String,Object,Object> eventBus;
    private final PasswordUtil passwordUtil;

    private final static String CLUSTER_NODE_RECOVERY_FAILED = "CLUSTER_NODE_RECOVERY_FAILED";
    private final static String CLUSTER_NODE_RECOVERY_COMPLETED = "CLUSTER_NODE_RECOVERY_COMPLETED";

    private boolean started;

    private final HashMap<String,Map> credentialsCache = new HashMap<>();
    private final HashMap<String,ScheduledFuture> waitingTasks = new HashMap<>();
    private final TaskScheduler taskScheduler;

    @Data
    private static class RECOVERY_COMMAND {
        private final String name;
        private final String command;
        private final long waitBefore;
        private final long waitAfter;
    }

    private final static RECOVERY_COMMAND[] recoveryCommands = {
            new RECOVERY_COMMAND("Initial wait...",
                    "pwd",0, 10000),
            new RECOVERY_COMMAND("Sending baguette client kill command...",
                    "/opt/baguette-client/bin/kill.sh",0, 2000),
            new RECOVERY_COMMAND("Sending baguette client start command...",
                    "/opt/baguette-client/bin/run.sh",0, 2000),
            new RECOVERY_COMMAND("Exiting...",
                    "exit",0, 0),
    };

    @Value("${self.healing.enabled:true}")
    private boolean enabled;
    @Value("${self.healing.recovery.delay:10000}")
    private long clientRecoveryDelay;
    @Value("${self.healing.recovery.retry.wait:60000}")
    private long clientRecoveryRetryDelay;

    @Override
    public void afterPropertiesSet() {
        log.debug("SelfHealingPlugin: properties: {}", properties);
    }

    public synchronized void start() {
        // check if already running
        if (started) {
            log.warn("SelfHealingPlugin: Already started");
            return;
        }

        eventBus.subscribe(CommandExecutor.EVENT_CLUSTER_NODE_ADDED, this);
        eventBus.subscribe(CommandExecutor.EVENT_CLUSTER_NODE_REMOVED, this);
        eventBus.subscribe(CLUSTER_NODE_RECOVERY_COMPLETED, this);
        log.info("SelfHealingPlugin: Started");
    }

    public synchronized void stop() {
        if (!started) {
            log.warn("SelfHealingPlugin: Not started");
            return;
        }

        eventBus.unsubscribe(CommandExecutor.EVENT_CLUSTER_NODE_ADDED, this);
        eventBus.unsubscribe(CommandExecutor.EVENT_CLUSTER_NODE_REMOVED, this);
        eventBus.unsubscribe(CLUSTER_NODE_RECOVERY_COMPLETED, this);

        // Cancel all waiting recovery tasks
        waitingTasks.forEach((nodeAddress,future) -> {
            future.cancel(true);
        });
        waitingTasks.clear();
        log.info("SelfHealingPlugin: Stopped");
    }

    @Override
    public void onMessage(String topic, Object message, Object sender) {
        log.debug("SelfHealingPlugin: onMessage(): BEGIN: topic={}, message={}, sender={}", topic, message, sender);
        if (!enabled) return;

        if (CommandExecutor.EVENT_CLUSTER_NODE_REMOVED.equals(topic)) {
            log.debug("SelfHealingPlugin: onMessage(): CLUSTER NODE REMOVED: message={}", message);
            processClusterNodeRemovedEvent(message);
        } else
        if (CLUSTER_NODE_RECOVERY_COMPLETED.equals(topic)) {
            log.debug("SelfHealingPlugin: onMessage(): CLUSTER NODE RECOVERY COMPLETED: message={}", message);
            processClusterNodeRecoveryEvent(message);
        } else
        if (CommandExecutor.EVENT_CLUSTER_NODE_ADDED.equals(topic)) {
            log.debug("SelfHealingPlugin: onMessage(): CLUSTER NODE ADDED: message={}", message);
            processClusterNodeAddedEvent(message);
        }
    }

    private void processClusterNodeRemovedEvent(Object message) {
        log.debug("SelfHealingPlugin: processClusterNodeRemovedEvent(): BEGIN: message={}", message);
        if (message instanceof ClusterMembershipEvent) {
            // Get removed node id and address
            ClusterMembershipEvent event = (ClusterMembershipEvent)message;
            String nodeId = event.subject().id().id();
            String nodeAddress = event.subject().address().host();
            log.debug("SelfHealingPlugin: processClusterNodeRemovedEvent(): node-id={}, node-address={}", nodeId, nodeAddress);
            if (StringUtils.isBlank(nodeAddress)) {
                log.warn("SelfHealingPlugin: processClusterNodeRemovedEvent(): Node address is missing. Cannot recover node. Initial message: {}", event);
                return;
            }

            // Get node VM credentials from EMS server
            Map nodeInfo = credentialsCache.get(nodeAddress);
            if (nodeInfo==null) {
                try {
                    log.debug("SelfHealingPlugin: processClusterNodeRemovedEvent(): Querying EMS server for Node Info: id={}, address={}", nodeId, nodeAddress);
                    commandExecutor.executeCommand("SEND SERVER-GET-NODE-SSH-CREDENTIALS " + nodeAddress);
                    String response = commandExecutor.getLastInputLine();
                    log.debug("SelfHealingPlugin: processClusterNodeRemovedEvent(): Node Info from EMS server: id={}, address={}\n{}", nodeId, nodeAddress, response);
                    if (StringUtils.isNotBlank(response)) {
                        nodeInfo = new Gson().fromJson(response, Map.class);
                    }
                    //XXX:TODO: credentialsCache.put(nodeAddress, nodeInfo);
                } catch (Exception ex) {
                    log.error("Exception while querying for node VM credentials: node-address={}\n", nodeAddress, ex);
                    return;
                }
            }
            log.debug("SelfHealingPlugin: processClusterNodeRemovedEvent(): Node info: {}", nodeInfo);
            if (nodeInfo==null || nodeInfo.size()==0) {
                log.warn("SelfHealingPlugin: processClusterNodeRemovedEvent(): Node info is null or empty. Cannot recover node.");
                return;
            }

            // Schedule node recovery task
            final Map nodeInfoFinal = nodeInfo;
            ScheduledFuture<?> future = taskScheduler.schedule(() -> {
                try {
                    waitingTasks.remove(nodeAddress);
                    runNodeRecovery(nodeInfoFinal);
                } catch (Exception e) {
                    log.error("SelfHealingPlugin: processClusterNodeRemovedEvent(): EXCEPTION: while recovering node: node-info={} -- Exception: ", nodeInfoFinal, e);
                }
            }, Instant.now().plusMillis(clientRecoveryDelay));
            waitingTasks.put(nodeAddress, future);
        } else {
            log.warn("SelfHealingPlugin: processClusterNodeRemovedEvent(): Message is not a {} object. Will ignore it.", ClusterMembershipEvent.class.getSimpleName());
        }
    }

    public void runNodeRecovery(Map nodeInfo) throws Exception {
        log.debug("SelfHealingPlugin: runNodeRecovery(): node-info={}", nodeInfo);
        if (nodeInfo==null) return;

        // Extract connection info and credentials
        String os = str(nodeInfo.get("operatingSystem"));
        String address = str(nodeInfo.get("address"));
        String type = str(nodeInfo.get("type"));
        String portStr = str(nodeInfo.get("ssh.port"));
        String username = str(nodeInfo.get("ssh.username"));
        String password = str(nodeInfo.get("ssh.password"));
        String key = str(nodeInfo.get("ssh.key"));
        String fingerprint = str(nodeInfo.get("ssh.fingerprint"));
        int port = 22;
        try {
            if (StringUtils.isNotBlank(portStr))
                port = Integer.parseInt(portStr);
            if (port<1 || port>65535)
                port = 22;
        } catch (Exception e) {}

        log.debug("SelfHealingPlugin: runNodeRecovery(): os={}, address={}, type={}", os, address, type);
        log.debug("SelfHealingPlugin: runNodeRecovery(): username={}, password={}", username, passwordUtil.encodePassword(password));
        log.debug("SelfHealingPlugin: runNodeRecovery(): fingerprint={}, key={}", fingerprint, passwordUtil.encodePassword(key));

        // Connect to node and restart Baguette Client
        BaguetteClientProperties config = new BaguetteClientProperties();
        config.setServerAddress(address);
        config.setServerPort(port);
        config.setServerUsername(username);
        if (!password.isEmpty()) {
            config.setServerPassword(password);
        }
        if (!key.isEmpty()) {
            config.setServerPubkey(key);
            config.setServerFingerprint(fingerprint);
        }
        //XXX:TODO: Make recovery authTimeout configurable
        config.setAuthTimeout(60000);

        Sshc sshc = new Sshc();
        sshc.setConfiguration(config);
        //XXX:TODO: Try enabling server key verification
        sshc.setUseServerKeyVerifier(false);
        log.info("SelfHealingPlugin: runNodeRecovery(): Connecting to node using SSH: address={}, port={}, username={}", address, port, username);
        sshc.start();
        log.debug("SelfHealingPlugin: runNodeRecovery(): Connected to node: address={}, port={}, username={}", address, port, username);

        // Redirect SSH output to standard output
        final AtomicBoolean closed = new AtomicBoolean(false);
        taskScheduler.schedule(() -> {
                    try {
                        IoUtils.copy(sshc.getIn(), System.out);
                    } catch (IOException e) {
                        if (closed.get()) {
                            log.info("SelfHealingPlugin: runNodeRecovery(): Connection closed");
                        } else {
                            log.error("SelfHealingPlugin: runNodeRecovery(): Exception while copying SSH IN stream: ", e);
                        }
                    }
                },
                Instant.now()
        );

        // Carrying out recovery commands
        log.info("SelfHealingPlugin: runNodeRecovery(): Executing {} recovery commands", recoveryCommands.length);
        for (RECOVERY_COMMAND command : recoveryCommands) {
            if (command==null || StringUtils.isBlank(command.getCommand())) continue;

            waitFor(command.getWaitBefore(), command.getName());
            log.warn("##############  {}...", command.getName());
            sshc.getOut().println(command.getCommand());
            waitFor(command.getWaitAfter(), command.getName());
        }
        log.info("SelfHealingPlugin: runNodeRecovery(): Executed {} recovery commands", recoveryCommands.length);

        // Disconnect from node
        log.info("SelfHealingPlugin: runNodeRecovery(): Disconnecting from node: address={}, port={}, username={}", address, port, username);
        closed.set(true);
        sshc.stop();
        log.debug("SelfHealingPlugin: runNodeRecovery(): Disconnected from node: address={}, port={}, username={}", address, port, username);

        // Send recovery complete event
        eventBus.send(CLUSTER_NODE_RECOVERY_COMPLETED, address);
    }

    private String str(Object o) {
        if (o==null) return "";
        return o.toString();
    }

    private void waitFor(long millis, String description) {
        if (millis>0) {
            log.warn("##############  Waiting for {}ms after {}...", millis, description);
            try { Thread.sleep(millis); } catch (InterruptedException e) { }
        }
    }

    private void processClusterNodeAddedEvent(Object message) {
        log.debug("SelfHealingPlugin: processClusterNodeAddedEvent(): BEGIN: message={}", message);
        if (message instanceof ClusterMembershipEvent) {
            // Get added node id and address
            ClusterMembershipEvent event = (ClusterMembershipEvent)message;
            String nodeId = event.subject().id().id();
            String nodeAddress = event.subject().address().host();
            log.debug("SelfHealingPlugin: processClusterNodeAddedEvent(): node-id={}, node-address={}", nodeId, nodeAddress);
            if (StringUtils.isBlank(nodeAddress)) {
                log.warn("SelfHealingPlugin: processClusterNodeAddedEvent(): Node address is missing. Initial message: {}", event);
                return;
            }

            // Cancel any waiting recovery task
            ScheduledFuture<?> future = waitingTasks.remove(nodeAddress);
            future.cancel(true);
            //XXX:TODO: credentialsCache.remove(nodeAddress);
        } else {
            log.warn("SelfHealingPlugin: processClusterNodeAddedEvent(): Message is not a {} object. Will ignore it.", ClusterMembershipEvent.class.getSimpleName());
        }
    }

    private void processClusterNodeRecoveryEvent(Object message) {
        log.debug("SelfHealingPlugin: processClusterNodeRecoveryEvent(): Node address: {}", message);
        if (message==null) return;
        String nodeAddress = message.toString();
        if (StringUtils.isBlank(nodeAddress)) return;

        // Get cached node VM credentials
        Map nodeInfo = credentialsCache.get(nodeAddress);
        log.debug("SelfHealingPlugin: processClusterNodeRecoveryEvent(): Node info: {}", nodeInfo);
        if (nodeInfo==null || nodeInfo.size()==0) {
            log.warn("SelfHealingPlugin: processClusterNodeRecoveryEvent(): Node info is null or empty. Cannot recover node.");
            return;
        }

        // Schedule node recovery task
        final Map nodeInfoFinal = nodeInfo;
        ScheduledFuture<?> future = taskScheduler.schedule(() -> {
            try {
                waitingTasks.remove(nodeAddress);
                runNodeRecovery(nodeInfoFinal);
            } catch (Exception e) {
                log.error("SelfHealingPlugin: processClusterNodeRemovedEvent(): EXCEPTION: while recovering node: node-info={} -- Exception: ", nodeInfoFinal, e);
            }
        }, Instant.now().plusMillis(clientRecoveryRetryDelay));
        waitingTasks.put(nodeAddress, future);
    }
}
