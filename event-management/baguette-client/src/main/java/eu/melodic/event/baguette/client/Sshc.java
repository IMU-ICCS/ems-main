/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client;

import eu.melodic.event.brokercep.BrokerCepService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.sshd.client.ClientFactoryManager;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.config.hosts.HostConfigEntryResolver;
import org.apache.sshd.client.keyverifier.ServerKeyVerifier;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.client.simple.SimpleClient;
import org.apache.sshd.common.PropertyResolverUtils;
import org.apache.sshd.common.config.keys.KeyUtils;
import org.apache.sshd.common.config.keys.impl.RSAPublicKeyDecoder;
import org.apache.sshd.common.keyprovider.KeyPairProvider;
import org.apache.sshd.common.util.io.NoCloseInputStream;
import org.apache.sshd.common.util.io.NoCloseOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.SocketAddress;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Optional;

//import org.apache.sshd.client.keyverifier.AcceptAllServerKeyVerifier;
//import org.apache.sshd.client.keyverifier.RequiredServerKeyVerifier;


/**
 * Custom SSH client
 */
@Service
@Slf4j
public class Sshc {
    private BaguetteClientProperties config;
    private SshClient client;
    private SimpleClient simple;
    private ClientSession session;
    private ClientChannel channel;
    private boolean started = false;
    @Autowired
    private CommandExecutor commandExecutor;
    @Autowired
    private BrokerCepService brokerCepService;

    @Getter
    private InputStream in;
    @Getter
    private PrintStream out;
    //@Getter
    //private PrintStream err;
    @Getter
    private String clientId;

    @Getter @Setter
    private boolean useServerKeyVerifier = true;

    public void setConfiguration(BaguetteClientProperties config) throws IOException {
        this.config = config;
        this.clientId = config.getClientId();
        log.trace("Sshc: cmd-exec: {}", commandExecutor);
        if (this.commandExecutor!=null) this.commandExecutor.setConfiguration(config);
    }

    public synchronized void start(boolean retry) throws IOException {
        if (retry) {
            log.trace("Starting client in retry mode");
            long retryPeriod = config.getRetryPeriod();
            while (!started) {
                log.debug("(Re-)trying to start client....");
                try {
                    start();
                } catch (Exception ex) {
                    log.warn("{}", ex.getMessage());
                }
                if (started) break;
                log.trace("Failed to start. Sleeping for {}ms...", retryPeriod);
                try {
                    Thread.sleep(retryPeriod);
                } catch (InterruptedException ex) {
                    log.debug("Sleep: ", ex);
                }
            }
        } else {
            start();
        }
        if (started) log.trace("Client started");
    }

    public synchronized void start() throws IOException {
        if (started) return;
        log.info("Connecting to server...");

        String host = config.getServerAddress();
        int port = config.getServerPort();
        String serverPubKey = config.getServerPubkey();
        String serverFingerprint = config.getServerFingerprint();
        String username = config.getServerUsername();
        String password = config.getServerPassword();
        long authTimeout = config.getAuthTimeout();

        // Starting client and connecting to server
        this.client = SshClient.setUpDefaultClient();
        client.setHostConfigEntryResolver(HostConfigEntryResolver.EMPTY);
        client.setKeyPairProvider(KeyPairProvider.EMPTY_KEYPAIR_PROVIDER);

        //client.setServerKeyVerifier(AcceptAllServerKeyVerifier.INSTANCE);
        //client.setServerKeyVerifier(new RequiredServerKeyVerifier(....));
        if (useServerKeyVerifier) {
            client.setServerKeyVerifier(new ServerKeyVerifier() {
                        private String serverFingerprint;
                        private String serverPubKey;

                        public boolean verifyServerKey(ClientSession sshClientSession, SocketAddress remoteAddress, PublicKey serverKey) {

                            // Print server address info
                            log.info("verifyServerKey(): remoteAddress: {}", remoteAddress.toString());

                            // Check that server public key fingerprint matches with the one in configuration
                            String fingerprint = KeyUtils.getFingerPrint(serverKey);
                            log.info("verifyServerKey(): serverKey: fingerprint: {}", fingerprint);
                            //if ( fingerprint!=null && KeyUtils.checkFingerPrint(serverFingerprint, serverKey).getFirst() ) log.info("verifyServerKey(): serverKey: fingerprint: MATCH");
                            //else log.warn("verifyServerKey(): serverKey: fingerprint: NO MATCH");

                            // Check that server public key matches with the one in configuration
                            try {
                                log.debug("verifyServerKey(): serverKey: decoder: {}", KeyUtils.getPublicKeyEntryDecoder(serverKey).getClass());
                                java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                                ((RSAPublicKeyDecoder) KeyUtils.getPublicKeyEntryDecoder(serverKey)).encodePublicKey(baos, (RSAPublicKey) serverKey);
                                String keyStr = new String(Base64.getEncoder().encode(baos.toByteArray()));
                                log.debug("verifyServerKey(): serverKey: server public key: \n{}", keyStr);

                                return keyStr.equalsIgnoreCase(serverPubKey);

                            } catch (Exception ex) {
                                log.error("verifyServerKey(): serverKey: EXCEPTION: ", ex);
                                return false;
                            }
                        }

                        public ServerKeyVerifier setServerPubKey(String pubkey, String fingerprint) {
                            this.serverFingerprint = fingerprint;
                            this.serverPubKey = pubkey;
                            return this;
                        }
                    }
                    .setServerPubKey(serverPubKey, serverFingerprint)
            );
        }

        this.simple = SshClient.wrapAsSimpleClient(client);
        //simple.setConnectTimeout(...CONNECT_TIMEOUT...);
        //simple.setAuthenticationTimeout(...AUTH_TIMEOUT...);

        // Set a huge idle timeout, keep-alive to true and heartbeat to 1 minute
        long heartbeatInterval = 60000;
        PropertyResolverUtils.updateProperty(client, ClientFactoryManager.HEARTBEAT_INTERVAL, heartbeatInterval);
        PropertyResolverUtils.updateProperty(client, ClientFactoryManager.IDLE_TIMEOUT, Long.MAX_VALUE);
        PropertyResolverUtils.updateProperty(client, ClientFactoryManager.SOCKET_KEEPALIVE, true);
        log.debug("Set IDLE_TIMEOUT to MAX, KEEP-ALIVE to true, and HEARTBEAT to {}", heartbeatInterval);

        // Start SSH client
        client.start();

        // Authenticate and start session
        this.session = client.connect(username, host, port).verify().getSession();
        session.addPasswordIdentity(password);
        session.auth().verify(authTimeout);

        // Open command shell channel
        this.channel = session.createChannel(ClientChannel.CHANNEL_SHELL);
        PipedInputStream pIn = new PipedInputStream();
        PipedOutputStream pOut = new PipedOutputStream();
        //PipedOutputStream pErr = new PipedOutputStream();
        this.in = new BufferedInputStream(pIn);
        this.out = new PrintStream(pOut, true);
        //this.err = new PrintStream(pErr, true);

        channel.setIn(new NoCloseInputStream(new PipedInputStream(pOut)));
        channel.setOut(new NoCloseOutputStream(new PipedOutputStream(pIn)));
        //channel.setErr(new NoCloseOutputStream( new PipedOutputStream( pErr ) ));

        channel.open();

        log.info("SSH client is ready");
        this.started = true;
    }

    public synchronized void stop() throws IOException {
        if (!started) return;
        this.started = false;
        log.info("Stopping SSH client...");

        channel.close(false).await();
        session.close(false);
        simple.close();
        client.stop();

        log.info("SSH client stopped");
    }

    public synchronized void greeting() {
        if (!started) return;
        String certOneLine = Optional
                .ofNullable(brokerCepService.getBrokerCertificate())
                .orElse("")
                .replace(" ","~~")
                .replace("\r\n","##")
                .replace("\n","$$");
        String clientAddress = config.getDebugFakeIpAddress();
        int clientPort = -1;
        out.printf("-HELLO FROM CLIENT: id=%s broker=%s address=%s port=%d username=%s password=%s cert=%s%n",
                clientId.replace(" ", "~~"),
                brokerCepService.getBrokerCepProperties().getBrokerUrlForClients(),
                StringUtils.isNotBlank(clientAddress) ? clientAddress : "",
                clientPort,
                brokerCepService.getBrokerUsername(),
                brokerCepService.getBrokerPassword(),
                certOneLine);
        out.flush();
    }

    public void run() throws IOException {
        if (!started) return;

        // Start communication protocol with Server
        // Execution waits here until connection is closed
        log.trace("run(): Calling communicateWithServer()...");
        commandExecutor.communicateWithServer(in, out, out);
        out.printf("-BYE FROM CLIENT: %s%n", clientId);
    }
}
