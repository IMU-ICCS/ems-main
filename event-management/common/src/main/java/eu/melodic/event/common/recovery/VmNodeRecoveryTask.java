/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.common.recovery;

import eu.melodic.event.common.client.SshClient;
import eu.melodic.event.common.client.SshClientProperties;
import eu.melodic.event.common.collector.CollectorContext;
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
 * VM-node Self-Healing using an SSH connection
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VmNodeRecoveryTask <P extends SshClientProperties> implements RecoveryTask {
    @NonNull private final EventBus<String,Object,Object> eventBus;
    @NonNull private final PasswordUtil passwordUtil;
    @NonNull private final TaskScheduler taskScheduler;
    @NonNull private final CollectorContext<P> collectorContext;

    @Getter
    private Map nodeInfo;

    private P sshClientProperties;

    public void setNodeInfo(@NonNull Map nodeInfo) {
        this.nodeInfo = nodeInfo;
        this.sshClientProperties = createSshClientProperties();
    }

    @SneakyThrows
    public List<RECOVERY_COMMAND> getRecoveryCommands() {
        throw new Exception("Method not implemented. Use 'runNodeRecovery(List<RECOVERY_COMMAND>)' instead");
    }

    public void runNodeRecovery() throws Exception {
        throw new Exception("Method not implemented. Use 'runNodeRecovery(List<RECOVERY_COMMAND>)' instead");
    }

    public void runNodeRecovery(List<RECOVERY_COMMAND> recoveryCommands) throws Exception {
        log.debug("VmNodeRecoveryTask: runNodeRecovery(): BEGIN: recovery-command: {}", recoveryCommands);

        // Connect to Node (VM)
        SshClient<? extends SshClientProperties> sshc = connectToNode();

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
        eventBus.send(SELF_HEALING_RECOVERY_COMPLETED, sshClientProperties.getServerAddress());
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

    private P createSshClientProperties() {
        log.debug("VmNodeRecoveryTask: createSshClientProperties(): BEGIN:");

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

        log.debug("VmNodeRecoveryTask: createSshClientProperties(): os={}, address={}, type={}", os, address, type);
        log.debug("VmNodeRecoveryTask: createSshClientProperties(): username={}, password={}", username, passwordUtil.encodePassword(password));
        log.debug("VmNodeRecoveryTask: createSshClientProperties(): fingerprint={}, key={}", fingerprint, passwordUtil.encodePassword(key));

        // Connect to node and restart EMS client
        P config = collectorContext.getSshClientProperties();
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

    private SshClient<P> connectToNode() throws IOException {
        SshClient<P> sshc = collectorContext.getSshClient();
        sshc.setConfiguration(sshClientProperties);
        //XXX:TODO: Try enabling server key verification
        sshc.setUseServerKeyVerifier(false);
        log.info("VmNodeRecoveryTask: connectToNode(): Connecting to node using SSH: address={}, port={}, username={}",
                sshClientProperties.getServerAddress(), sshClientProperties.getServerPort(), sshClientProperties.getServerUsername());
        sshc.start();
        log.debug("VmNodeRecoveryTask: connectToNode(): Connected to node: address={}, port={}, username={}",
                sshClientProperties.getServerAddress(), sshClientProperties.getServerPort(), sshClientProperties.getServerUsername());
        return sshc;
    }

    private void disconnectFromNode(SshClient sshc, AtomicBoolean closed) throws IOException {
        log.info("VmNodeRecoveryTask: disconnectFromNode(): Disconnecting from node: address={}, port={}, username={}",
                sshClientProperties.getServerAddress(), sshClientProperties.getServerPort(), sshClientProperties.getServerUsername());
        closed.set(true);
        sshc.stop();
        log.debug("VmNodeRecoveryTask: disconnectFromNode(): Disconnected from node: address={}, port={}, username={}",
                sshClientProperties.getServerAddress(), sshClientProperties.getServerPort(), sshClientProperties.getServerUsername());
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
