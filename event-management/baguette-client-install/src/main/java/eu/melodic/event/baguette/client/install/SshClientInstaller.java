/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.install;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.sshd.client.ClientFactoryManager;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelExec;
import org.apache.sshd.client.channel.ChannelShell;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.config.hosts.HostConfigEntryResolver;
import org.apache.sshd.client.keyverifier.AcceptAllServerKeyVerifier;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.PropertyResolverUtils;
import org.apache.sshd.common.keyprovider.KeyPairProvider;
import org.apache.sshd.common.util.io.NoCloseOutputStream;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateCrtKey;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

/**
 * SSH client installer
 */
@Slf4j
public class SshClientInstaller {
    private ClientInstallationTask task;
    private long taskCounter;
    private int maxRetries;
    private long connectTimeout = 60000;
    private long authenticationTimeout = 60000;
    private long heartbeatInterval = 10000;

    private SshClient sshClient;
    //private SimpleClient simpleClient;
    private ClientSession session;

    @Builder
    public SshClientInstaller(ClientInstallationTask task, long taskCounter, int maxRetries, long connectTimeout, long authenticationTimeout, long heartbeatInterval) {
        this.task= task;
        this.taskCounter = taskCounter;
        this.maxRetries = maxRetries>0 ? maxRetries : 5;
        this.connectTimeout = connectTimeout>0 ? connectTimeout : 60000;
        this.authenticationTimeout = authenticationTimeout>0 ? authenticationTimeout : 60000;
        this.heartbeatInterval = heartbeatInterval>0 ? heartbeatInterval : 10000;
    }

    public boolean execute() {
        int retries = 0;
        while (retries<=maxRetries) {
            if (retries>0) log.warn("SshClientInstaller: Retry {}/{} executing task #{}", retries, maxRetries, taskCounter);
            if (executeTask(retries)) return true;
            log.warn("SshClientInstaller: Failed executing task #{}", taskCounter);
            retries++;
        }
        log.error("SshClientInstaller: Giving up executing task #{} after {} retries", taskCounter, maxRetries);
        return false;
    }

    private boolean executeTask(int retries) {
        try {
            sshConnect(task);
            executeSteps();
            sshDisconnect();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private boolean sshConnect(ClientInstallationTask task) throws Exception {
        // Get connection information
        SshConfig config = task.getSsh();
        String host = config.getHost();
        int port = config.getPort();
        String privateKey = config.getPrivateKey();
        String fingerprint = config.getFingerprint();
        String username = config.getUsername();
        String password = config.getPassword();

        // Create and configure SSH client
        this.sshClient = SshClient.setUpDefaultClient();
        sshClient.setHostConfigEntryResolver(HostConfigEntryResolver.EMPTY);
        sshClient.setKeyPairProvider(KeyPairProvider.EMPTY_KEYPAIR_PROVIDER);
        sshClient.setServerKeyVerifier(AcceptAllServerKeyVerifier.INSTANCE);

//        this.simpleClient = SshClient.wrapAsSimpleClient(sshClient);
//        simpleClient.setConnectTimeout(connectTimeout);
//        simpleClient.setAuthenticationTimeout(authenticationTimeout);

        PropertyResolverUtils.updateProperty(sshClient, ClientFactoryManager.HEARTBEAT_INTERVAL, heartbeatInterval);
        PropertyResolverUtils.updateProperty(sshClient, ClientFactoryManager.IDLE_TIMEOUT, Long.MAX_VALUE);
        PropertyResolverUtils.updateProperty(sshClient, ClientFactoryManager.SOCKET_KEEPALIVE, true);

        // Start client and connect to SSH server
        try {
            sshClient.start();
            this.session = sshClient.connect(username, host, port).verify().getSession();
            if (StringUtils.isNotBlank(privateKey)) {
                PrivateKey privKey = getPrivateKey(privateKey);
                //PublicKey pubKey = getPublicKey(publicKey);
                PublicKey pubKey = getPublicKey((BCRSAPrivateCrtKey) privKey);
                KeyPair keyPair = new KeyPair(pubKey, privKey);
                session.addPublicKeyIdentity(keyPair);
            }
            if (StringUtils.isNotBlank(password)) {
                session.addPasswordIdentity(password);
            }
            session.auth().verify(authenticationTimeout);

            log.info("SshClientInstaller: Connected to remote host: task #{}: host: {}:{}", taskCounter, host, port);
            return true;

        } catch (Exception ex) {
            log.error("SshClientInstaller: Error while connecting to remote host: task #{}: ", taskCounter, ex);
            throw ex;
        }
    }

    private PrivateKey getPrivateKey(String pemStr) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        try (StringReader keyReader = new StringReader(pemStr); PemReader pemReader = new PemReader(keyReader)) {
            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(content);
            PrivateKey privKey = factory.generatePrivate(keySpecPKCS8);
            return privKey;
        }
        //PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKeyContent.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "")));
        //PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);
    }

    private PublicKey getPublicKey(String pemStr) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        try (StringReader keyReader = new StringReader(pemStr); PemReader pemReader = new PemReader(keyReader)) {
            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(content);
            PublicKey publicKey = factory.generatePublic(pubKeySpec);
            return publicKey;
        }
        /*X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(
                Base64.decode(
                        pemStr.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "")
                                .getBytes()));
        RSAPublicKey pubKey = (RSAPublicKey) factory.generatePublic(keySpecX509);*/
    }

    private PublicKey getPublicKey(RSAPublicKeySpec rsaPrivateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = factory.generatePublic(new RSAPublicKeySpec(rsaPrivateKey.getModulus(), rsaPrivateKey.getPublicExponent()));
        return publicKey;
    }

    private PublicKey getPublicKey(BCRSAPrivateCrtKey rsaPrivateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = factory.generatePublic(new RSAPublicKeySpec(rsaPrivateKey.getModulus(), rsaPrivateKey.getPublicExponent()));
        return publicKey;
    }

    private boolean sshDisconnect() throws Exception {
        try {
            //channel.close(false).await();
            session.close(false);
            //simpleClient.close();
            sshClient.stop();

            SshConfig config = task.getSsh();
            String host = config.getHost();
            int port = config.getPort();
            log.info("SshClientInstaller: Disonnected from remote host: task #{}: host: {}:{}", taskCounter, host, port);
            return true;
        } catch (Exception ex) {
            log.error("SshClientInstaller: Error while disconnecting from remote host: task #{}: ", taskCounter, ex);
            throw ex;
        } finally {
            session = null;
            //simpleClient = null;
            sshClient = null;
        }
    }

    private boolean executeSteps() throws IOException {
        String command = "touch /tmp/ems.txt";
        log.info("SshClientInstaller: Sending command: {}", command);

        // Using EXEC channel
        /*ChannelExec channel = session.createExecChannel(command);
        channel.setOut(new NoCloseOutputStream(System.out));
        channel.setErr(new NoCloseOutputStream(System.err));
        try {
            channel.open().verify(connectTimeout);
            channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED),
                    TimeUnit.SECONDS.toMillis(5));
        } finally {
            channel.close();
        }*/

        // Using SHELL channel
        ChannelShell channel = session.createShellChannel();
        channel.setOut(new NoCloseOutputStream(System.out));
        channel.setErr(new NoCloseOutputStream(System.err));
        try {
            channel.open().verify(connectTimeout);
            try (OutputStream pipedIn = channel.getInvertedIn()) {
                if (!command.endsWith("\n"))
                    command += "\n";
                pipedIn.write(command.getBytes());
                pipedIn.flush();
            }
            channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED),
                    TimeUnit.SECONDS.toMillis(5));
        } finally {
            channel.close();
        }
            /*this.channel = session.createChannel(ClientChannel.CHANNEL_SHELL);
            PipedInputStream pIn = new PipedInputStream();
            PipedOutputStream pOut = new PipedOutputStream();
            PipedOutputStream pErr = new PipedOutputStream();
            this.sshIn = new BufferedInputStream(pIn);
            this.sshOut = new PrintStream(pOut, true);
            this.sshErr = new PrintStream(pErr, true);

            channel.setIn(new NoCloseInputStream(new PipedInputStream(pOut)));
            channel.setOut(new NoCloseOutputStream(new PipedOutputStream(pIn)));
            //channel.setErr(new NoCloseOutputStream(new PipedOutputStream(pErr)));

            channel.open();*/

        return true;
    }
}
