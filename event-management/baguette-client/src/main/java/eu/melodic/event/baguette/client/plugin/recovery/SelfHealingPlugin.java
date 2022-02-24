/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.plugin.recovery;

import eu.melodic.event.baguette.client.BaguetteClientProperties;
import eu.melodic.event.baguette.client.CommandExecutor;
import eu.melodic.event.baguette.client.collector.netdata.NetdataCollector;
import eu.melodic.event.util.EventBus;
import eu.melodic.event.util.PasswordUtil;
import eu.melodic.event.util.Plugin;
import io.atomix.cluster.ClusterMembershipEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Client-side Self-Healing plugin
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SelfHealingPlugin implements Plugin, InitializingBean, EventBus.EventConsumer<String,Object,Object> {
    private final ApplicationContext applicationContext;
    private final BaguetteClientProperties properties;
    private final CommandExecutor commandExecutor;
    private final EventBus<String,Object,Object> eventBus;
    private final PasswordUtil passwordUtil;
    private final NodeInfoHelper nodeInfoHelper;

    final static String SELF_HEALING_RECOVERY_FAILED = "SELF_HEALING_RECOVERY_FAILED";
    final static String SELF_HEALING_RECOVERY_COMPLETED = "SELF_HEALING_RECOVERY_COMPLETED";

    private boolean started;

    private final HashMap<String,ScheduledFuture<?>> waitingTasks = new HashMap<>();
    private final TaskScheduler taskScheduler;

    @Value("${self.healing.enabled:true}")
    private boolean enabled;
    @Value("${self.healing.recovery.delay:10000}")
    private long clientRecoveryDelay;
    @Value("${self.healing.recovery.retry.wait:60000}")
    private long clientRecoveryRetryDelay;
    @Value("${self.healing.recovery.max.retries:3}")
    private int clientRecoveryMaxRetries;

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
        eventBus.subscribe(NetdataCollector.NETDATA_NODE_PAUSED, this);
        eventBus.subscribe(NetdataCollector.NETDATA_NODE_RESUMED, this);
        log.info("SelfHealingPlugin: Started");
    }

    public synchronized void stop() {
        if (!started) {
            log.warn("SelfHealingPlugin: Not started");
            return;
        }

        eventBus.unsubscribe(CommandExecutor.EVENT_CLUSTER_NODE_ADDED, this);
        eventBus.unsubscribe(CommandExecutor.EVENT_CLUSTER_NODE_REMOVED, this);
        eventBus.unsubscribe(NetdataCollector.NETDATA_NODE_PAUSED, this);
        eventBus.unsubscribe(NetdataCollector.NETDATA_NODE_RESUMED, this);

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

        // Self-Healing for EMS clients
        if (CommandExecutor.EVENT_CLUSTER_NODE_REMOVED.equals(topic)) {
            log.debug("SelfHealingPlugin: onMessage(): CLUSTER NODE REMOVED: message={}", message);
            processClusterNodeRemovedEvent(message);
        } else
        if (CommandExecutor.EVENT_CLUSTER_NODE_ADDED.equals(topic)) {
            log.debug("SelfHealingPlugin: onMessage(): CLUSTER NODE ADDED: message={}", message);
            processClusterNodeAddedEvent(message);
        } else

        // Self-healing for Netdata agents
        if (NetdataCollector.NETDATA_NODE_PAUSED.equals(topic)) {
            log.debug("SelfHealingPlugin: onMessage(): NETDATA NODE PAUSED: message={}", message);
            processNetdataNodePausedEvent(message);
        } else
        if (NetdataCollector.NETDATA_NODE_RESUMED.equals(topic)) {
            log.debug("SelfHealingPlugin: onMessage(): NETDATA NODE RESUMED: message={}", message);
            processNetdataNodeResumedEvent(message);
        } else

        // Unsupported message
        {
            log.debug("SelfHealingPlugin: onMessage(): Unsupported message: topic={}, message={}, sender={}",
                    topic, message, sender);
        }
    }

    // ------------------------------------------------------------------------

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

            createRecoveryTask(nodeId, nodeAddress, EmsClientRecoveryTask.class);
        } else {
            log.warn("SelfHealingPlugin: processClusterNodeRemovedEvent(): Message is not a {} object. Will ignore it.", ClusterMembershipEvent.class.getSimpleName());
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
            cancelRecoveryTask(nodeId, nodeAddress, false);
        } else {
            log.warn("SelfHealingPlugin: processClusterNodeAddedEvent(): Message is not a {} object. Will ignore it.", ClusterMembershipEvent.class.getSimpleName());
        }
    }

    // ------------------------------------------------------------------------

    private void processNetdataNodePausedEvent(Object message) {
        log.debug("SelfHealingPlugin: processNetdataNodePausedEvent(): BEGIN: message={}", message);
        if (!(message instanceof Map)) {
            log.warn("SelfHealingPlugin: processNetdataNodePausedEvent(): Message is not a {} object. Will ignore it.", Map.class.getSimpleName());
            return;
        }

        // Get paused node address
        Object addressValue = ((Map) message).getOrDefault("address", null);
        log.debug("SelfHealingPlugin: processNetdataNodePausedEvent(): node-address={}", addressValue);
        if (addressValue==null) {
            log.warn("SelfHealingPlugin: processNetdataNodePausedEvent(): Node address is missing. Cannot recover node. Initial message: {}", message);
            return;
        }
        String nodeAddress = addressValue.toString();

        if (isLocalAddress(nodeAddress)) {
            // We are responsible for recovering our local Netdata agent
            createRecoveryTask(null, "", NetdataAgentLocalRecoveryTask.class);
        } else {
            // Aggregator is responsible for recovering remote Netdata agents
            createRecoveryTask(null, nodeAddress, NetdataAgentRecoveryTask.class);
        }
    }

    @SneakyThrows
    private boolean isLocalAddress(String address) {
        if (address.isEmpty()) return true;
        if ("127.0.0.1".equals(address)) return true;
        if ("::1".equals(address)) return true;
        if ("0:0:0:0:0:0:0:1".equals(address)) return true;
        InetAddress ia = InetAddress.getByName(address);
        if (ia.isAnyLocalAddress() || ia.isLoopbackAddress()) return true;
        try {
            return NetworkInterface.getByInetAddress(ia) != null;
        } catch (SocketException se) {
            return false;
        }
    }

    private void processNetdataNodeResumedEvent(Object message) {
        log.debug("SelfHealingPlugin: processNetdataNodeResumedEvent(): BEGIN: message={}", message);
        if (!(message instanceof Map)) {
            log.warn("SelfHealingPlugin: processNetdataNodeResumedEvent(): Message is not a {} object. Will ignore it.", Map.class.getSimpleName());
            return;
        }

        // Get resumed node address
        String nodeAddress = ((Map) message).getOrDefault("address", "").toString();
        log.debug("SelfHealingPlugin: processNetdataNodeResumedEvent(): node-address={}", nodeAddress);
        /*if (StringUtils.isBlank(nodeAddress)) {
            log.warn("SelfHealingPlugin: processNetdataNodeResumedEvent(): Node address is missing. Initial message: {}", message);
            return;
        }*/

        // Cancel any waiting recovery task
        cancelRecoveryTask(null, nodeAddress, false);
    }

    // ------------------------------------------------------------------------

    private void createRecoveryTask(String nodeId, @NonNull String nodeAddress, @NonNull Class<? extends RecoveryTask> recoveryTaskClass) {
        // Check if a recovery task has already been scheduled
        synchronized (waitingTasks) {
            if (waitingTasks.containsKey(nodeAddress)) {
                log.warn("SelfHealingPlugin: createRecoveryTask(): Recovery has already been scheduled for Node: id={}, address={}", nodeId, nodeAddress);
                return;
            }
            waitingTasks.put(nodeAddress, null);
        }

        // Get node info and credentials from EMS server
        Map nodeInfo = null;
        if (StringUtils.isNotBlank(nodeAddress)) {
            nodeInfo = nodeInfoHelper.getNodeInfo(nodeId, nodeAddress);
            if (nodeInfo == null || nodeInfo.size() == 0) {
                log.warn("SelfHealingPlugin: createRecoveryTask(): Node info is null or empty. Cannot recover node.");
                return;
            }
            log.trace("SelfHealingPlugin: createRecoveryTask(): Node info retrieved for node: id={}, address={}, node-info:\n{}", nodeId, nodeAddress, nodeInfo);
        } else {
            log.debug("SelfHealingPlugin: createRecoveryTask(): Node address is blank. Node info will not be retrieved: id={}, address={}", nodeId, nodeAddress);
        }

        // Schedule node recovery task
        final RecoveryTask recoveryTask = applicationContext.getBean(recoveryTaskClass);
        if (nodeInfo!=null && nodeInfo.size()>0)
            recoveryTask.setNodeInfo(nodeInfo);
        AtomicInteger retries = new AtomicInteger(0);
        ScheduledFuture<?> future = taskScheduler.scheduleWithFixedDelay(() -> {
            try {
                log.info("SelfHealingPlugin: Retry #{}: Recovering node: id={}, address={}", retries.get(), nodeId, nodeAddress);
                recoveryTask.runNodeRecovery();
                //NOTE: 'recoveryTask.runNodeRecovery()' must send SELF_HEALING_RECOVERY_COMPLETED or _FAILED event
                if (retries.getAndIncrement() > clientRecoveryMaxRetries) {
                    log.warn("SelfHealingPlugin: Max retries reached. No more recovery retries for node: id={}, address={}", nodeId, nodeAddress);
                    cancelRecoveryTask(nodeId, nodeAddress, true);
                }
            } catch (Exception e) {
                log.error("SelfHealingPlugin: EXCEPTION while recovering node: node-info={} -- Exception: ", recoveryTask.getNodeInfo(), e);
                eventBus.send(SELF_HEALING_RECOVERY_FAILED, nodeAddress);
            }
        }, Instant.now().plusMillis(clientRecoveryDelay), Duration.ofMillis(clientRecoveryRetryDelay));
        waitingTasks.put(nodeAddress, future);
        log.info("SelfHealingPlugin: createRecoveryTask(): Created recovery task for Node: id={}, address={}", nodeId, nodeAddress);
    }

    private void cancelRecoveryTask(String nodeId, @NonNull String nodeAddress, boolean retainAddress) {
        synchronized (waitingTasks) {
            ScheduledFuture<?> future = retainAddress ? waitingTasks.put(nodeAddress, null) : waitingTasks.remove(nodeAddress);
            if (future != null) {
                future.cancel(true);
                nodeInfoHelper.remove(nodeId, nodeAddress);
                log.info("SelfHealingPlugin: cancelRecoveryTask(): Cancelled recovery task for Node: id={}, address={}", nodeId, nodeAddress);
            } else
                log.warn("SelfHealingPlugin: cancelRecoveryTask(): No recovery task is scheduled for Node: id={}, address={}", nodeId, nodeAddress);
        }
    }
}
