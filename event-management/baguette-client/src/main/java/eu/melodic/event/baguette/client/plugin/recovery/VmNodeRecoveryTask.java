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
import eu.melodic.event.baguette.client.Sshc;
import eu.melodic.event.util.EventBus;
import eu.melodic.event.util.PasswordUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static eu.melodic.event.common.recovery.RecoveryConstant.SELF_HEALING_RECOVERY_COMPLETED;

/**
 * Client-side, VM-node Self-Healing
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VmNodeRecoveryTask implements RecoveryTask {
    @NonNull private final EventBus<String,Object,Object> eventBus;
    @NonNull private final PasswordUtil passwordUtil;
    @NonNull private final TaskScheduler taskScheduler;

    @Getter @Setter
    private Map nodeInfo;

    private BaguetteClientProperties baguetteClientProperties;

    public void setNodeInfo(@NonNull Map nodeInfo) {
        this.nodeInfo = nodeInfo;
        this.baguetteClientProperties = createBaguetteClientProperties();
    }

    @SneakyThrows
    public List<RECOVERY_COMMAND> getRecoveryCommands() {
        throw new Exception("Method not implemented. Use 'runNodeRecovery(List<RECOVERY_COMMAND>)' instead");
    }

    public void runNodeRecovery() throws Exception {
        throw new Exception("Method not implemented. Use 'runNodeRecovery(List<RECOVERY_COMMAND>)' instead");
    }

    public void runNodeRecovery(List<RECOVERY_COMMAND> recoveryCommands) throws Exception {
        log.debug("VmNodeRecoveryTask: runNodeRecovery(): node-info={}", nodeInfo);

        // Connect to Node (VM)
        Sshc sshc = connectToNode();

        // Redirect SSH output to standard output
        final AtomicBoolean closed = new AtomicBoolean(false);
        redirectSshOutput(sshc.getIn(), "OUT", closed);

        // Carrying out recovery commands
        log.info("VmNodeRecoveryTask: runNodeRecovery(): Executing {} recovery commands", recoveryCommands.size());
        for (RECOVERY_COMMAND command : recoveryCommands) {
            if (command==null || StringUtils.isBlank(command.getCommand())) continue;

            waitFor(command.getWaitBefore(), command.getName());
            log.warn("##############  {}...", command.getName());
            sshc.getOut().println(command.getCommand());
            waitFor(command.getWaitAfter(), command.getName());
        }
        log.info("VmNodeRecoveryTask: runNodeRecovery(): Executed {} recovery commands", recoveryCommands.size());

        // Disconnect from node
        disconnectFromNode(sshc, closed);

        // Send recovery complete event
        eventBus.send(SELF_HEALING_RECOVERY_COMPLETED, baguetteClientProperties.getServerAddress());
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

    private BaguetteClientProperties createBaguetteClientProperties() {
        log.debug("VmNodeRecoveryTask: createBaguetteClientProperties(): node-info={}", nodeInfo);

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

        log.debug("VmNodeRecoveryTask: createBaguetteClientProperties(): os={}, address={}, type={}", os, address, type);
        log.debug("VmNodeRecoveryTask: createBaguetteClientProperties(): username={}, password={}", username, passwordUtil.encodePassword(password));
        log.debug("VmNodeRecoveryTask: createBaguetteClientProperties(): fingerprint={}, key={}", fingerprint, passwordUtil.encodePassword(key));

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

        return config;
    }

    private Sshc connectToNode() throws IOException {
        Sshc sshc = new Sshc();
        sshc.setConfiguration(baguetteClientProperties);
        //XXX:TODO: Try enabling server key verification
        sshc.setUseServerKeyVerifier(false);
        log.info("VmNodeRecoveryTask: connectToNode(): Connecting to node using SSH: address={}, port={}, username={}",
                baguetteClientProperties.getServerAddress(), baguetteClientProperties.getServerPort(), baguetteClientProperties.getServerUsername());
        sshc.start();
        log.debug("VmNodeRecoveryTask: connectToNode(): Connected to node: address={}, port={}, username={}",
                baguetteClientProperties.getServerAddress(), baguetteClientProperties.getServerPort(), baguetteClientProperties.getServerUsername());
        return sshc;
    }

    private void disconnectFromNode(Sshc sshc, AtomicBoolean closed) throws IOException {
        log.info("VmNodeRecoveryTask: disconnectFromNode(): Disconnecting from node: address={}, port={}, username={}",
                baguetteClientProperties.getServerAddress(), baguetteClientProperties.getServerPort(), baguetteClientProperties.getServerUsername());
        closed.set(true);
        sshc.stop();
        log.debug("VmNodeRecoveryTask: disconnectFromNode(): Disconnected from node: address={}, port={}, username={}",
                baguetteClientProperties.getServerAddress(), baguetteClientProperties.getServerPort(), baguetteClientProperties.getServerUsername());
    }

    private void redirectSshOutput(InputStream in, String id, AtomicBoolean closed) {
        taskScheduler.schedule(() -> {
                    try {
                        //IoUtils.copy(sshc.getIn(), System.out);
                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                            while (reader.ready()) {
                                log.info(" {}> {}", id, reader.readLine());
                            }
                        }
                    } catch (IOException e) {
                        if (closed.get()) {
                            log.info("VmNodeRecoveryTask: redirectSshOutput(): Connection closed: id={}", id);
                        } else {
                            log.error("VmNodeRecoveryTask: redirectSshOutput(): Exception while copying SSH IN stream: id={}\n", id, e);
                        }
                    }
                },
                Instant.now()
        );
    }
}
