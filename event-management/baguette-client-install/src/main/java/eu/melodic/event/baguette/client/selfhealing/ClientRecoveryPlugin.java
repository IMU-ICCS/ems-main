/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.selfhealing;

import eu.melodic.event.baguette.client.install.ClientInstallationProperties;
import eu.melodic.event.baguette.client.install.ClientInstallationTask;
import eu.melodic.event.baguette.client.install.SshClientInstaller;
import eu.melodic.event.baguette.client.install.helper.InstallationHelperFactory;
import eu.melodic.event.baguette.server.BaguetteServer;
import eu.melodic.event.baguette.server.ClientShellCommand;
import eu.melodic.event.baguette.server.NodeRegistry;
import eu.melodic.event.baguette.server.NodeRegistryEntry;
import eu.melodic.event.common.selfhealing.SelfHealingManager;
import eu.melodic.event.util.EventBus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
@ConditionalOnProperty(name = "CLIENT_RECOVERY_ENABLED", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class ClientRecoveryPlugin implements InitializingBean, EventBus.EventConsumer<String,Object,Object> {
    private final EventBus<String,Object,Object> eventBus;
    private final NodeRegistry nodeRegistry;
    private final TaskScheduler taskScheduler;
    private final ClientInstallationProperties clientInstallationProperties;
    private final BaguetteServer baguetteServer;

    private final HashMap<NodeRegistryEntry, ScheduledFuture<?>> pendingTasks = new HashMap<>();

    @Value("${CLIENT_RECOVERY_DELAY:10000}")
    private long clientRecoveryDelay;
    @Value("${CLIENT_RECOVERY_INSTRUCTIONS_FILES:file:${MELODIC_CONFIG_DIR}/baguette-client-install/linux/recover-baguette.json}")
    private String recoveryInstructionsFile;

    private final static String CLIENT_EXIT_TOPIC = "BAGUETTE_SERVER_CLIENT_EXITED";
    private final static String CLIENT_REGISTERED_TOPIC = "BAGUETTE_SERVER_CLIENT_REGISTERED";

    @Override
    public void afterPropertiesSet() throws Exception {
        eventBus.subscribe(CLIENT_EXIT_TOPIC, this);
        log.info("ClientRecoveryPlugin: Subscribed for BAGUETTE_SERVER_CLIENT_EXITED events");
        eventBus.subscribe(CLIENT_REGISTERED_TOPIC, this);
        log.info("ClientRecoveryPlugin: Subscribed for BAGUETTE_SERVER_CLIENT_REGISTERED events");

        log.trace("ClientRecoveryPlugin: clientInstallationProperties: {}", clientInstallationProperties);
        log.trace("ClientRecoveryPlugin: baguetteServer: {}", baguetteServer);

        log.debug("ClientRecoveryPlugin: Recovery Delay: {}", clientRecoveryDelay);
        log.debug("ClientRecoveryPlugin: Recovery Instructions File: {}", recoveryInstructionsFile);
    }

    @Override
    public void onMessage(String topic, Object message, Object sender) {
        log.debug("ClientRecoveryPlugin: onMessage(): BEGIN: topic={}, message={}, sender={}", topic, message, sender);
        if (! baguetteServer.getSelfHealingManager().isEnabled()) {
            log.debug("ClientRecoveryPlugin: onMessage(): Self-Healing manager is disabled: message={}, sender={}", message, sender);
            return;
        }
        if (CLIENT_EXIT_TOPIC.equals(topic)) {
            log.debug("ClientRecoveryPlugin: onMessage(): CLIENT EXITED: message={}", message);
            processExitEvent(message, sender);
        }
        if (CLIENT_REGISTERED_TOPIC.equals(topic)) {
            log.debug("ClientRecoveryPlugin: onMessage(): CLIENT REGISTERED_TOPIC: message={}", message);
            processRegisteredEvent(message, sender);
        }
    }

    private void processExitEvent(Object message, Object sender) {
        log.debug("ClientRecoveryPlugin: processExitEvent(): BEGIN: message={}", message);
        if (message instanceof ClientShellCommand) {
            ClientShellCommand csc = (ClientShellCommand)message;
            String clientId = csc.getId();
            String address = csc.getClientIpAddress();
            log.warn("ClientRecoveryPlugin: processExitEvent(): client-id={}, client-address={}", clientId, address);
            NodeRegistryEntry nodeInfo = nodeRegistry.getNodeByAddress(address);
            log.debug("ClientRecoveryPlugin: processExitEvent(): client-node-info={}", nodeInfo);
            log.trace("ClientRecoveryPlugin: processExitEvent(): node-registry.node-addresses={}", nodeRegistry.getNodeAddresses());
            log.trace("ClientRecoveryPlugin: processExitEvent(): node-registry.nodes={}", nodeRegistry.getNodes());

            if (! baguetteServer.getSelfHealingManager().isMonitored(nodeInfo)) {
                log.warn("ClientRecoveryPlugin: processExitEvent(): Node is not monitored by Self-Healing manager: client-id={}, client-address={}", clientId, address);
                return;
            }
            baguetteServer.getSelfHealingManager().setNodeSelfHealingState(nodeInfo, SelfHealingManager.NODE_STATE.DOWN, message!=null ? message.toString() : null);

            ScheduledFuture<?> future = taskScheduler.schedule(() -> {
                try {
                    baguetteServer.getSelfHealingManager().setNodeSelfHealingState(nodeInfo, SelfHealingManager.NODE_STATE.RECOVERING);
                    runClientRecovery(nodeInfo);
                } catch (Exception e) {
                    log.error("ClientRecoveryPlugin: processExitEvent(): EXCEPTION: while recovering node: node-info={} -- Exception: ", nodeInfo, e);
                }
            }, Instant.now().plusMillis(clientRecoveryDelay));
            ScheduledFuture<?> old = pendingTasks.put(nodeInfo, future);
            log.info("ClientRecoveryPlugin: processExitEvent(): Add recovery task in the queue: client-id={}, client-address={}", clientId, address);
            if (old!=null && ! old.isDone() && ! old.isCancelled()) {
                log.warn("ClientRecoveryPlugin: processExitEvent(): Cancelled previous recovery task: client-id={}, client-address={}", clientId, address);
                old.cancel(false);
            }
        } else {
            log.warn("ClientRecoveryPlugin: processExitEvent(): Message is not a {} object. Will ignore it.", ClientShellCommand.class.getSimpleName());
        }
    }

    private void processRegisteredEvent(Object message, Object sender) {
        log.debug("ClientRecoveryPlugin: processRegisteredEvent(): BEGIN: message={}", message);
        if (message instanceof ClientShellCommand) {
            ClientShellCommand csc = (ClientShellCommand)message;
            String clientId = csc.getId();
            String address = csc.getClientIpAddress();
            log.warn("ClientRecoveryPlugin: processRegisteredEvent(): client-id={}, client-address={}", clientId, address);
            NodeRegistryEntry nodeInfo = nodeRegistry.getNodeByAddress(address);
            log.debug("ClientRecoveryPlugin: processRegisteredEvent(): client-node-info={}", nodeInfo);
            log.trace("ClientRecoveryPlugin: processRegisteredEvent(): node-registry.node-addresses={}", nodeRegistry.getNodeAddresses());
            log.trace("ClientRecoveryPlugin: processRegisteredEvent(): node-registry.nodes={}", nodeRegistry.getNodes());

            if (! baguetteServer.getSelfHealingManager().isMonitored(nodeInfo)) {
                log.warn("ClientRecoveryPlugin: processRegisteredEvent(): Node is not monitored by Self-Healing manager: client-id={}, client-address={}", clientId, address);
                return;
            }

            ScheduledFuture<?> future = pendingTasks.remove(nodeInfo);
            if (future!=null && ! future.isDone() && ! future.isCancelled()) {
                log.warn("ClientRecoveryPlugin: processRegisteredEvent(): Cancelled recovery task: client-id={}, client-address={}", clientId, address);
                future.cancel(false);
            }
            baguetteServer.getSelfHealingManager().setNodeSelfHealingState(nodeInfo, SelfHealingManager.NODE_STATE.UP, message.toString());

        } else {
            log.warn("ClientRecoveryPlugin: processRegisteredEvent(): Message is not a {} object. Will ignore it.", ClientShellCommand.class.getSimpleName());
        }
    }

    public void runClientRecovery(NodeRegistryEntry entry) throws Exception {
        log.debug("ClientRecoveryPlugin: runClientRecovery(): node-info={}", entry);
        if (entry==null) return;

        entry.getPreregistration().put("instruction-files", recoveryInstructionsFile);

        ClientInstallationTask task = InstallationHelperFactory.getInstance()
                .createInstallationHelper(entry)
                .createClientInstallationTask(entry);
        log.debug("ClientRecoveryPlugin: runClientRecovery(): Client recovery task: {}", task);
        SshClientInstaller installer = SshClientInstaller.builder()
                .task(task)
                .properties(clientInstallationProperties)
                .build();
        log.warn("ClientRecoveryPlugin: runClientRecovery(): Starting client recovery: node-info={}", entry);
        boolean result = installer.execute();
        pendingTasks.remove(entry);
        log.warn("ClientRecoveryPlugin: runClientRecovery(): Client recovery completed: result={}, node-info={}", result, entry);
    }
}
