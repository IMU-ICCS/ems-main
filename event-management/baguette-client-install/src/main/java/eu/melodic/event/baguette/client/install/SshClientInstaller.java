/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.install;

import eu.melodic.event.baguette.client.install.instruction.Instruction;
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
import org.apache.sshd.client.scp.ScpClient;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.PropertyResolverUtils;
import org.apache.sshd.common.keyprovider.KeyPairProvider;
import org.apache.sshd.common.scp.ScpTimestamp;
import org.apache.sshd.common.util.io.NoCloseOutputStream;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateCrtKey;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.file.attribute.PosixFilePermission;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * SSH client installer
 */
@Slf4j
public class SshClientInstaller implements ClientInstallerPlugin {
    private ClientInstallationTask task;
    private long taskCounter;

    private int maxRetries;
    private long connectTimeout;
    private long authenticationTimeout;
    private long heartbeatInterval;
    private boolean simulateConnection;
    private boolean simulateExecution;

    private SshClient sshClient;
    //private SimpleClient simpleClient;
    private ClientSession session;
    private ChannelShell shellChannel;
    private OutputStream shellPipedIn;

    @Builder
    public SshClientInstaller(ClientInstallationTask task, long taskCounter, int maxRetries, long connectTimeout, long authenticationTimeout, long heartbeatInterval, boolean simulateConnection, boolean simulateExecution) {
        this.task= task;
        this.taskCounter = taskCounter;

        this.maxRetries = maxRetries>0 ? maxRetries : 5;
        this.connectTimeout = connectTimeout>0 ? connectTimeout : 60000;
        this.authenticationTimeout = authenticationTimeout>0 ? authenticationTimeout : 60000;
        this.heartbeatInterval = heartbeatInterval>0 ? heartbeatInterval : 10000;
        this.simulateConnection = simulateConnection;
        this.simulateExecution = simulateExecution;
    }

    @Override
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
            sshOpenShell();
            executeInstructions();
            sshCloseShell();
            sshDisconnect();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private boolean sshConnect(ClientInstallationTask task) throws Exception {
        SshConfig config = task.getSsh();
        String host = config.getHost();
        int port = config.getPort();

        if (simulateConnection) {
            log.info("SshClientInstaller: Simulate connection to remote host: task #{}: host: {}:{}", taskCounter, host, port);
            return true;
        }

        // Get connection information
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
        SshConfig config = task.getSsh();
        String host = config.getHost();
        int port = config.getPort();
        if (simulateConnection) {
            log.info("SshClientInstaller: Simulate disconnect from remote host: task #{}: host: {}:{}", taskCounter, host, port);
            return true;
        }

        try {
            //channel.close(false).await();
            session.close(false);
            //simpleClient.close();
            sshClient.stop();

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

    private boolean sshOpenShell() throws IOException {
        if (simulateConnection) {
            log.info("SshClientInstaller: Simulate open shell channel: task #{}", taskCounter);
            return true;
        }

        shellChannel = session.createShellChannel();
        shellChannel.setOut(new NoCloseOutputStream(System.out));
        shellChannel.setErr(new NoCloseOutputStream(System.err));
        shellChannel.open().verify(connectTimeout);
        shellPipedIn = shellChannel.getInvertedIn();
        log.info("SshClientInstaller: Opened shell channel: task #{}", taskCounter);
        return true;
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
    }

    private boolean sshCloseShell() throws IOException {
        if (simulateConnection) {
            log.info("SshClientInstaller: Simulate close shell channel: task #{}", taskCounter);
            return true;
        }

        shellChannel.close();
        shellChannel = null;
        shellPipedIn = null;
        log.info("SshClientInstaller: Closed shell channel: task #{}", taskCounter);
        return true;
    }

    private boolean sshShellExec(@NotNull String command) throws IOException {
        if (simulateConnection || simulateExecution) {
            log.info("SshClientInstaller: Simulate command execution: task #{}: command: {}", taskCounter, command);
            return true;
        }

        // Send command to remote side
        if (!command.endsWith("\n"))
            command += "\n";
        log.info("SshClientInstaller: Sending command: {}", command);
        shellPipedIn.write(command.getBytes());
        shellPipedIn.flush();

        // Search remote side output for expected patterns
        //XXX: TODO: Search remote side output for expected patterns

        shellChannel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED),
                TimeUnit.SECONDS.toMillis(5));
        return true;
    }

    private boolean sshExecCmd(String command) throws IOException {
        if (simulateConnection || simulateExecution) {
            log.info("SshClientInstaller: Simulate shell command execution: task #{}: command: {}", taskCounter, command);
            return true;
        }

        // Using EXEC channel
        ChannelExec channel = session.createExecChannel(command);
        channel.setOut(new NoCloseOutputStream(System.out));
        channel.setErr(new NoCloseOutputStream(System.err));
        try {
            channel.open().verify(connectTimeout);

            //XXX: TODO: Search remote side output for expected patterns

            channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED),
                    TimeUnit.SECONDS.toMillis(5));
        } finally {
            channel.close();
        }

        return true;
    }

    private boolean sshFileDownload(String remoteFilePath, String localFilePath) throws IOException {
        if (simulateConnection || simulateExecution) {
            log.info("SshClientInstaller: Simulate file download: task #{}: remote: {} -> local: {}", taskCounter, remoteFilePath, localFilePath);
            return true;
        }

        try {
            log.info("SshClientInstaller: Downloading file: task #{}: remote: {} -> local: {}", taskCounter, remoteFilePath, localFilePath);
            ScpClient scpClient = session.createScpClient();
            scpClient.download(remoteFilePath, localFilePath, ScpClient.Option.PreserveAttributes);
            log.info("SshClientInstaller: File download completed: task #{}: remote: {} -> local: {}", taskCounter, remoteFilePath, localFilePath);
        } catch (Exception ex) {
            log.error("SshClientInstaller: File download failed: task #{}: remote: {} -> local: {} Exception: ", taskCounter, remoteFilePath, localFilePath, ex);
            throw ex;
        }

        return true;
    }

    private boolean sshFileUpload(String localFilePath, String remoteFilePath) throws IOException {
        if (simulateConnection || simulateExecution) {
            log.info("SshClientInstaller: Simulate file upload: task #{}: local: {} -> remote: {}", taskCounter, localFilePath, remoteFilePath);
            return true;
        }

        try {
            log.info("SshClientInstaller: Uploading file: task #{}: local: {} -> remote: {}", taskCounter, localFilePath, remoteFilePath);
            ScpClient scpClient = session.createScpClient();
            scpClient.upload(localFilePath, remoteFilePath, ScpClient.Option.PreserveAttributes);
            log.info("SshClientInstaller: File upload completed: task #{}: local: {} -> remote: {}", taskCounter, localFilePath, remoteFilePath);
        } catch (Exception ex) {
            log.error("SshClientInstaller: File upload failed: task #{}: local: {} -> remote: {} Exception: ", taskCounter, localFilePath, remoteFilePath, ex);
            throw ex;
        }

        return true;
    }

    private boolean sshFileWrite(String content, String remoteFilePath, boolean isExecutable) throws IOException {
        if (simulateConnection || simulateExecution) {
            log.info("SshClientInstaller: Simulate file upload: task #{}: remote: {}, content: {}", taskCounter, remoteFilePath, content);
            return true;
        }

        try {
            long timestamp = System.currentTimeMillis();
            Collection<PosixFilePermission> permissions = isExecutable
                    ? Arrays.asList(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_EXECUTE)
                    : Arrays.asList(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE);
            log.info("SshClientInstaller: Uploading file: task #{}: remote: {}, perm={}, content: {}", taskCounter, remoteFilePath, permissions, content);
            ScpClient scpClient = session.createScpClient();
            scpClient.upload(content.getBytes(), remoteFilePath, permissions, new ScpTimestamp(timestamp, timestamp));
            log.info("SshClientInstaller: File upload completed: task #{}: remote: {}, content: {}", taskCounter, remoteFilePath, content);
        } catch (Exception ex) {
            log.error("SshClientInstaller: File upload failed: task #{}: remote: {}, Exception: ", taskCounter, remoteFilePath, ex);
            throw ex;
        }

        return true;
    }

    private boolean executeInstructions() throws IOException {

        int numOfInstructions = task.getInstallationInstructions().getInstructions().size();
        int cnt = 0;
        for (Instruction ins : task.getInstallationInstructions().getInstructions()) {
            cnt++;
            log.info("SshClientInstaller: Task #{}: Executing instruction {}/{}", taskCounter, cnt, numOfInstructions);
            switch (ins.getTaskType()) {
                case LOG:
                    log.info("SshClientInstaller: LOG: {}", ins.getCommand());
                    break;
                case CMD:
                    boolean cmdResult = sshShellExec(ins.getCommand());
                    if (!cmdResult) return false;
                    break;
                case FILE:
                    sshFileWrite(ins.getContents(), ins.getFileName(), ins.isExecutable());
                    break;
                case COPY:
                    sshFileUpload(ins.getLocalFileName(), ins.getFileName());
                    break;
                case CHECK:
                    log.warn("SshClientInstaller: Instruction CHECK not implemented: {}", ins);
                    break;
                default:
                    log.error("sshClientInstaller: Unknown instruction type. Ignoring it: {}", ins);
            }
        }
        return true;
    }
}
