/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.server;

import eu.melodic.event.baguette.server.properties.BaguetteServerProperties;
import eu.melodic.event.util.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.apache.sshd.common.Factory;
import org.apache.sshd.common.PropertyResolverUtils;
import org.apache.sshd.common.config.keys.KeyUtils;
import org.apache.sshd.common.config.keys.impl.RSAPublicKeyDecoder;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.ServerFactoryManager;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;

import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Custom SSH server
 */
@Slf4j
public class Sshd {
    private ServerCoordinator coordinator;
    private BaguetteServerProperties configuration;
    private SshServer sshd;
    private String serverPubkey;
    private String serverPubkeyFingerprint;

    private boolean heartbeatOn;
    private long heartbeatPeriod;

    private EventBus<String,Object,Object> eventBus;
    private NodeRegistry nodeRegistry;

    public void start(BaguetteServerProperties configuration, ServerCoordinator coordinator, EventBus<String,Object,Object> eventBus, NodeRegistry registry) throws IOException {
        log.info("** SSH server **");
        this.coordinator = coordinator;
        this.configuration = configuration;
        this.eventBus = eventBus;
        this.nodeRegistry = registry;

        // Configure SSH server
        int port = configuration.getServerPort();
        String serverKeyFilePath = configuration.getServerKeyFile();
        log.info("SSH server: Public IP address {}", configuration.getServerAddress());
        log.info("SSH server: Starting on port {}", port);

        sshd = SshServer.setUpDefaultServer();
        sshd.setPort(port);
        File serverKeyFile = new File(serverKeyFilePath);
        log.info("SSH server: Server key file: {}", serverKeyFile.getAbsolutePath());
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(serverKeyFile));
        _loadPubkeyAndFingerprint();

        sshd.setShellFactory(
                new Factory<Command>() {
                    private ServerCoordinator coordinator;
                    private NodeRegistry nodeRegistry;

                    public Command create() {
                        ClientShellCommand msc = new ClientShellCommand(this.coordinator, configuration.isClientAddressOverrideAllowed(), eventBus, nodeRegistry);
                        //msc.setId( "#-"+System.currentTimeMillis() );
                        log.debug("SSH server: Shell Factory: create invoked : New ClientShellCommand id: {}", msc.getId());
                        return msc;
                    }

                    public Command get() {
                        log.debug("SSH server: Shell Factory: get invoked");
                        return null;
                    }

                    public Factory<Command> setCoordinatorAndNodeRegistry(ServerCoordinator coordinator, NodeRegistry registry) {
                        this.coordinator = coordinator;
                        this.nodeRegistry = registry;
                        return this;
                    }
                }
                .setCoordinatorAndNodeRegistry(coordinator, nodeRegistry)
        );

        sshd.setPasswordAuthenticator(
                new PasswordAuthenticator() {
                    private Map<String, String> credentials;

                    public boolean authenticate(String username, String password, ServerSession session) {
                        String pwd = Optional.ofNullable(credentials.get(username.trim())).orElse("");
                        return pwd.equals(password);
                    }

                    public PasswordAuthenticator setCredentials(Map<String, String> credentials) {
                        this.credentials = credentials;
                        return this;
                    }
                }
                .setCredentials(configuration.getCredentials())
        );

        // Set session timeout
        PropertyResolverUtils.updateProperty(sshd, ServerFactoryManager.IDLE_TIMEOUT, Long.MAX_VALUE);
        PropertyResolverUtils.updateProperty(sshd, ServerFactoryManager.SOCKET_KEEPALIVE, true);
        log.debug("SSH server: Set IDLE_TIMEOUT to MAX, and KEEP-ALIVE to true");

        // Start SSH server and accept connections
        sshd.start();
        log.info("SSH server: Ready");

        // Start heartbeat service
        if (configuration.isHeartbeatEnabled()) {
            long heartbeatPeriod = configuration.getHeartbeatPeriod();
            startHeartbeat(heartbeatPeriod);
        }

        // Start coordinator
        coordinator.start();
    }

    public void stop() throws IOException {
        // Stop coordinator
        coordinator.stop();

        // Don't accept new connections
        log.info("SSH server: Stopping SSH server...");
        sshd.setShellFactory(null);

        // Signal heartbeat service to stop
        stopHeartbeat();

        // Close active client connections
        for (ClientShellCommand csc : ClientShellCommand.getActive()) {
            csc.stop("Server exits");
        }

        sshd.stop();
        log.info("SSH server: Stopped");
    }

    public void startHeartbeat(long period) {
        heartbeatOn = true;
        Thread heartbeat = new Thread(
                new Runnable() {
                    private long period;

                    public void run() {
                        log.info("--> Heartbeat: Started: period={}ms", period);
                        while (heartbeatOn && period > 0) {
                            try {
                                Thread.sleep(period);
                            } catch (InterruptedException ex) {
                            }
                            String msg = String.format("Heartbeat %d", System.currentTimeMillis());
                            log.debug("--> Heartbeat: {}", msg);
                            for (ClientShellCommand csc : ClientShellCommand.getActive()) {
                                csc.sendToClient(msg);
                            }
                        }
                        log.info("--> Heartbeat: Stopped");
                    }

                    public Runnable setPeriod(long period) {
                        this.period = period;
                        return this;
                    }
                }
                        .setPeriod(period)
        );
        heartbeat.setDaemon(true);
        heartbeat.start();
    }

    public void stopHeartbeat() {
        heartbeatOn = false;
    }

    protected void broadcastToClients(String msg) {
        for (ClientShellCommand csc : ClientShellCommand.getActive()) {
            log.info("SSH server: Sending to {} : {}", csc.getId(), msg);
            csc.sendToClient(msg);
        }
    }

    public void sendToActiveClients(String command) {
        for (ClientShellCommand csc : ClientShellCommand.getActive()) {
            log.info("SSH server: Sending to client {} : {}", csc.getId(), command);
            csc.sendToClient(command);
        }
    }

    public void sendToClient(String clientId, String command) {
        for (ClientShellCommand csc : ClientShellCommand.getActive()) {
            if (csc.getId().equals(clientId)) {
                log.info("SSH server: Sending to client {} : {}", csc.getId(), command);
                csc.sendToClient(command);
            }
        }
    }

    public Object readFromClient(String clientId, String command) {
        log.trace("SSH server: Sending and Reading to/from client {}: {}", clientId, command);
        for (ClientShellCommand csc : ClientShellCommand.getActive()) {
            log.trace("SSH server: Check CSC: csc-id={}, client={}", csc.getId(), clientId);
            if (csc.getId().equals(clientId)) {
                log.info("SSH server: Sending and Reading to/from client {} : {}", csc.getId(), command);
                return csc.readFromClient(command);
            }
        }
        return null;
    }

    public List<String> getActiveClients() {
        return ClientShellCommand.getActive().stream()
                .map(c -> String.format("%s %s %s:%d", c.getId(),
                        c.getClientIpAddress(),
                        c.getClientClusterNodeHostname(),
                        c.getClientClusterNodePort()))
                .sorted()
                .collect(Collectors.toList());
    }

    public Map<String, Map<String, String>> getActiveClientsMap() {
        return ClientShellCommand.getActive().stream()
                //.sorted((final ClientShellCommand c1, final ClientShellCommand c2) -> c1.getId().compareTo(c2.getId()))
                .collect(Collectors.toMap(ClientShellCommand::getId, c -> {
                    Map<String,String> properties = new LinkedHashMap<>();
                    //properties.put("id", c.getId());
                    properties.put("ip-address", c.getClientIpAddress());
                    properties.put("node-hostname", c.getClientClusterNodeHostname());
                    properties.put("node-port", Integer.toString(c.getClientClusterNodePort()));
                    return properties;
                }));
    }

    public void sendConstants(Map<String, Double> constants) {
        for (ClientShellCommand csc : ClientShellCommand.getActive()) {
            log.info("SSH server: Sending constants to client {} : {}", csc.getId(), constants);
            csc.sendConstants(constants);
        }
    }

    public String getPublicKey() {
        if (serverPubkey==null) _loadPubkeyAndFingerprint();
        return serverPubkey;
    }

    public String getPublicKeyFingerprint() {
        if (serverPubkeyFingerprint==null) _loadPubkeyAndFingerprint();
        return serverPubkeyFingerprint;
    }

    private synchronized void _loadPubkeyAndFingerprint() {
        String serverKeyFilePath = configuration.getServerKeyFile();
        log.debug("_loadPubkeyAndFingerprint(): Server Key file: {}", serverKeyFilePath);
        File serverKeyFile = new File(serverKeyFilePath);
        SimpleGeneratorHostKeyProvider z = new SimpleGeneratorHostKeyProvider(serverKeyFile);
        z.loadKeys().forEach(kp -> {
            log.debug("_loadPubkeyAndFingerprint(): KeyPair found: {}", kp.toString());
            PublicKey serverKey = kp.getPublic();
            log.debug("_loadPubkeyAndFingerprint(): Pubkey: {}", kp.toString());
            try {
                log.debug("_loadPubkeyAndFingerprint(): Decoder class: {}", KeyUtils.getPublicKeyEntryDecoder(serverKey).getClass());
                java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                ((RSAPublicKeyDecoder) KeyUtils.getPublicKeyEntryDecoder(serverKey)).encodePublicKey(baos, (RSAPublicKey) serverKey);
                String keyStr = new String(Base64.getEncoder().encode(baos.toByteArray()));

                this.serverPubkey = keyStr;
                this.serverPubkeyFingerprint = KeyUtils.getFingerPrint(serverKey);
                log.debug("_loadPubkeyAndFingerprint(): Server public key: \n{}", keyStr);
                log.debug("_loadPubkeyAndFingerprint(): Fingerprint: {}", serverPubkeyFingerprint);

            } catch (Exception ex) {
                log.error("_loadPubkeyAndFingerprint(): EXCEPTION: {}", ex);
            }
        });
    }
}
