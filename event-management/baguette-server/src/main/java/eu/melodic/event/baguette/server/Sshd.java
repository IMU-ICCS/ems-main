/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.server;

import eu.melodic.event.baguette.server.properties.BaguetteServerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.sshd.common.Factory;
import org.apache.sshd.common.config.keys.KeyUtils;
import org.apache.sshd.common.config.keys.impl.RSAPublicKeyDecoder;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;

import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

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

    public void start(BaguetteServerProperties configuration, ServerCoordinator coordinator) throws IOException {
        log.info("** SSH server **");
        this.coordinator = coordinator;
        this.configuration = configuration;

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

                    public Command create() {
                        ClientShellCommand msc = new ClientShellCommand(this.coordinator, configuration.isClientAddressOverrideAllowed());
                        //msc.setId( "#-"+System.currentTimeMillis() );
                        log.debug("SSH server: Shell Factory: create invoked : New ClientShellCommand id: {}", msc.getId());
                        return msc;
                    }

                    public Command get() {
                        log.debug("SSH server: Shell Factory: get invoked");
                        return null;
                    }

                    public Factory setCoordinator(ServerCoordinator coordinator) {
                        this.coordinator = coordinator;
                        return this;
                    }
                }
                .setCoordinator(coordinator)
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
