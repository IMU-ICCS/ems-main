/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.install;

import eu.melodic.event.baguette.client.install.instruction.InstallationInstructions;
import eu.melodic.event.baguette.client.install.instruction.Instruction;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apache.sshd.client.ClientFactoryManager;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelExec;
import org.apache.sshd.client.channel.ChannelSession;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.config.hosts.HostConfigEntryResolver;
import org.apache.sshd.client.keyverifier.AcceptAllServerKeyVerifier;
import org.apache.sshd.client.scp.ScpClient;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.PropertyResolverUtils;
import org.apache.sshd.common.keyprovider.KeyPairProvider;
import org.apache.sshd.common.scp.ScpTimestamp;
import org.apache.sshd.common.util.io.NoCloseInputStream;
import org.apache.sshd.common.util.io.NoCloseOutputStream;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateCrtKey;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SSH client installer
 */
@Slf4j
public class SshClientInstaller implements ClientInstallerPlugin {
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");

    private final ClientInstallationTask task;
    private final long taskCounter;

    private final int maxRetries;
    private final long connectTimeout;
    private final long authenticationTimeout;
    private final long heartbeatInterval;
    private final boolean simulateConnection;
    private final boolean simulateExecution;
    private final long commandExecutionTimeout;
    private final boolean continueOnFail;

    private final ClientInstallationProperties properties;

    private SshClient sshClient;
    //private SimpleClient simpleClient;
    private ClientSession session;
    //private ChannelShell shellChannel;
    private StreamLogger streamLogger;

    /*@Builder
    public SshClientInstaller(ClientInstallationTask task, long taskCounter, int maxRetries, long connectTimeout, long authenticationTimeout, long heartbeatInterval, boolean simulateConnection, boolean simulateExecution, long commandExecutionTimeout, boolean continueOnFail) {
        this.task= task;
        this.taskCounter = taskCounter;

        this.maxRetries = maxRetries>0 ? maxRetries : 5;
        this.connectTimeout = connectTimeout>0 ? connectTimeout : 60000;
        this.authenticationTimeout = authenticationTimeout>0 ? authenticationTimeout : 60000;
        this.heartbeatInterval = heartbeatInterval>0 ? heartbeatInterval : 10000;
        this.simulateConnection = simulateConnection;
        this.simulateExecution = simulateExecution;
        this.commandExecutionTimeout = commandExecutionTimeout;
        this.continueOnFail = continueOnFail;
    }*/

    @Builder
    public SshClientInstaller(ClientInstallationTask task, long taskCounter, ClientInstallationProperties properties) {
        this.task= task;
        this.taskCounter = taskCounter;

        this.maxRetries = properties.getMaxRetries()>0 ? properties.getMaxRetries() : 5;
        this.connectTimeout = properties.getConnectTimeout()>0 ? properties.getConnectTimeout() : 60000;
        this.authenticationTimeout = properties.getAuthenticateTimeout()>0 ? properties.getAuthenticateTimeout() : 60000;
        this.heartbeatInterval = properties.getHeartbeatInterval()>0 ? properties.getHeartbeatInterval() : 10000;
        this.simulateConnection = properties.isSimulateConnection();
        this.simulateExecution = properties.isSimulateExecution();
        this.commandExecutionTimeout = properties.getCommandExecutionTimeout()>0 ? properties.getCommandExecutionTimeout() : 120000;
        this.continueOnFail = properties.isContinueOnFail();

        this.properties = properties;
    }

    @Override
    public boolean execute() { return executeTask(); }

    private boolean executeTask(/*int retries*/) {
        boolean success = false;
        int retries = 0;
        while (!success && retries<=maxRetries) {
            if (retries>0) log.warn("SshClientInstaller: Retry {}/{} executing task #{}", retries, maxRetries, taskCounter);
            try {
                sshConnect();
                //sshOpenShell();
                success = true;
            } catch (Exception ex) {
                success = false;
                log.error("SshClientInstaller: Failed executing task #{}, Exception: ", taskCounter, ex);
                retries++;
            }
        }
        if (!success) {
            log.error("SshClientInstaller: Giving up executing task #{} after {} retries", taskCounter, maxRetries);
            return false;
        }

        try {
            success = executeInstructionsList();
        } catch (Exception ex) {
            log.error("SshClientInstaller: Failed executing installation instructions for task #{}, Exception: ", taskCounter, ex);
            success = false;
        }

        try {
            //sshCloseShell();
            sshDisconnect();
            if (success) log.info("SshClientInstaller: Task completed successfully #{}", taskCounter);
            else log.info("SshClientInstaller: Error occurred while executing task #{}", taskCounter);
            return success;
        } catch (Exception ex) {
            log.error("SshClientInstaller: Exception while disconnecting. Task #{}, Exception: ", taskCounter, ex);
            return false;
        }
    }

    private boolean sshConnect() throws Exception {
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

        //this.simpleClient = SshClient.wrapAsSimpleClient(sshClient);
        //simpleClient.setConnectTimeout(connectTimeout);
        //simpleClient.setAuthenticationTimeout(authenticationTimeout);

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

            initStreamLogger();

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
            if (streamLogger!=null)
                streamLogger.close();

            //channel.close(false).await();
            session.close(false);
            //simpleClient.close();
            sshClient.stop();

            log.info("SshClientInstaller: Disconnected from remote host: task #{}: host: {}:{}", taskCounter, host, port);
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

    private void initStreamLogger() throws IOException {
        if (streamLogger!=null) return;

        String address = session.getConnectAddress().toString().replace("/","").replace(":", "-");
        //log.trace("SshClientInstaller: address: {}", address);
        String logFile = StringUtils.isNotBlank(properties.getSessionRecordingDir())
                ? properties.getSessionRecordingDir()+"/"+address+"-"+ simpleDateFormat.format(new Date())+"-"+taskCounter+".txt"
                : null;
        log.info("SshClientInstaller: Task #{}: Session will be recorded in file: {}", taskCounter, logFile);
        this.streamLogger = new StreamLogger(logFile, "  Task #"+taskCounter);
    }

    private void setChannelStreams(ChannelSession channel) throws IOException {
        initStreamLogger();
        channel.setIn(new NoCloseInputStream( streamLogger.getIn() ));
        channel.setOut(new NoCloseOutputStream( streamLogger.getOut() ));
        channel.setErr(new NoCloseOutputStream( streamLogger.getErr() ));
    }

    /*private boolean sshOpenShell() throws IOException {
        if (simulateConnection) {
            log.info("SshClientInstaller: Simulate open shell channel: task #{}", taskCounter);
            return true;
        }

        shellChannel = session.createShellChannel();
        setChannelStreams(shellChannel);
        shellChannel.open().verify(connectTimeout);
        //shellPipedIn = shellChannel.getInvertedIn();
        log.info("SshClientInstaller: Opened shell channel: task #{}", taskCounter);

        shellChannel.waitFor(
                EnumSet.of(ClientChannelEvent.CLOSED),
                authenticationTimeout);
                //TimeUnit.SECONDS.toMillis(5));
        log.info("SshClientInstaller: Shell channel ready: task #{}", taskCounter);

        return true;
    }

    private boolean sshCloseShell() throws IOException {
        if (simulateConnection) {
            log.info("SshClientInstaller: Simulate close shell channel: task #{}", taskCounter);
            return true;
        }

        shellChannel.close();
        shellChannel = null;
        //shellPipedIn = null;
        streamLogger.close();
        streamLogger = null;
        log.info("SshClientInstaller: Closed shell channel: task #{}", taskCounter);
        return true;
    }

    private boolean sshShellExec(@NotNull String command, long executionTimeout) throws IOException {
        if (simulateConnection || simulateExecution) {
            log.info("SshClientInstaller: Simulate command execution: task #{}: command: {}", taskCounter, command);
            return true;
        }

        // Send command to remote side
        if (!command.endsWith("\n"))
            command += "\n";
        log.info("SshClientInstaller: Sending command: {}", command);
        streamLogger.getInvertedIn().write(command.getBytes());
        streamLogger.getInvertedIn().flush();

        // Search remote side output for expected patterns
        // Not implemented

        shellChannel.waitFor(
                EnumSet.of(ClientChannelEvent.CLOSED),
                executionTimeout>0 ? executionTimeout : commandExecutionTimeout);
                //TimeUnit.SECONDS.toMillis(5));
        return true;
    }*/

    private Integer sshExecCmd(String command) throws IOException {
        return sshExecCmd(command, commandExecutionTimeout);
    }

    private Integer sshExecCmd(String command, long executionTimeout) throws IOException {
        if (simulateConnection || simulateExecution) {
            log.info("SshClientInstaller: Simulate shell command execution: task #{}: command: {}", taskCounter, command);
            return null;
        }

        // Using EXEC channel
        Integer exitStatus = null;
        ChannelExec channel = session.createExecChannel(command);
        setChannelStreams(channel);
        //streamLogger.getInvertedIn().write(command.getBytes());
        streamLogger.logMessage(String.format("EXEC: %s\n", command));
        try {
            channel.open().verify(connectTimeout);

            //XXX: TODO: Search remote side output for expected patterns

            // Wait until channel closes from server side (i.e. command completed) or timeout occurs
            log.trace("SshClientInstaller: task #{}: EXEC: instruction execution-timeout: {}", taskCounter, executionTimeout);
            log.trace("SshClientInstaller: task #{}: EXEC: default command-execution-timeout: {}", taskCounter, commandExecutionTimeout);
            long execTimeout = executionTimeout != 0 ? executionTimeout : commandExecutionTimeout;
            log.debug("SshClientInstaller: task #{}: EXEC: effective instruction execution-timeout: {}", taskCounter, execTimeout);
            Set<ClientChannelEvent> eventSet = channel.waitFor(
                    EnumSet.of(ClientChannelEvent.CLOSED),
                    execTimeout);
                    //TimeUnit.SECONDS.toMillis(50));
            log.debug("SshClientInstaller: task #{}: EXEC: Exit event set: {}", taskCounter, eventSet);
            exitStatus = channel.getExitStatus();
            log.debug("SshClientInstaller: task #{}: EXEC: Exit status: {}", taskCounter, exitStatus);
        } finally {
            channel.close();
        }

        return exitStatus;
    }

    private boolean sshFileDownload(String remoteFilePath, String localFilePath) throws IOException {
        if (simulateConnection || simulateExecution) {
            log.info("SshClientInstaller: Simulate file download: task #{}: remote: {} -> local: {}", taskCounter, remoteFilePath, localFilePath);
            return true;
        }

        streamLogger.logMessage(String.format("DOWNLOAD: SCP: %s -> %s\n", remoteFilePath, localFilePath));
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

        streamLogger.logMessage(String.format("UPLOAD: SCP: %s -> %s\n", localFilePath, remoteFilePath));
        try {
            long startTm = System.currentTimeMillis();
            log.info("SshClientInstaller: Uploading file: task #{}: local: {} -> remote: {}", taskCounter, localFilePath, remoteFilePath);
            ScpClient scpClient = session.createScpClient();
            scpClient.upload(localFilePath, remoteFilePath, ScpClient.Option.PreserveAttributes);
            long endTm = System.currentTimeMillis();
            log.info("SshClientInstaller: File upload completed in {}ms: task #{}: local: {} -> remote: {}", endTm-startTm, taskCounter, localFilePath, remoteFilePath);
        } catch (Exception ex) {
            log.error("SshClientInstaller: File upload failed: task #{}: local: {} -> remote: {} Exception: ", taskCounter, localFilePath, remoteFilePath, ex);
            throw ex;
        }

        return true;
    }

    private boolean sshFileWrite(String content, String remoteFilePath, boolean isExecutable) throws IOException {
        if (simulateConnection || simulateExecution) {
            log.info("SshClientInstaller: Simulate file upload: task #{}: remote: {}, content-length={}", taskCounter, remoteFilePath, content.length());
            return true;
        }

        streamLogger.logMessage(String.format("WRITE FILE: SCP: %s, content-length=%d \n", remoteFilePath, content.length()));
        try {
            long timestamp = System.currentTimeMillis();
            /*Collection<PosixFilePermission> permissions = isExecutable
                    ? Arrays.asList(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_EXECUTE)
                    : Arrays.asList(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE);
            log.info("SshClientInstaller: Uploading file: task #{}: remote: {}, perm={}, content-length={}", taskCounter, remoteFilePath, permissions, content.length());
            log.trace("SshClientInstaller: Uploading file: task #{}: remote: {}, perm={}, content:\n{}", taskCounter, remoteFilePath, permissions, content);
            ScpClient scpClient = session.createScpClient();
            scpClient.upload(content.getBytes(), remoteFilePath, permissions, new ScpTimestamp(timestamp, timestamp));
            */

             /*
             The alternative approach next is much faster than the original approach above (commented out)
             Old approach: write bytes directly to remote file
             New approach: write contents to a local temp. file and then upload it to remote side
             */

            // Write contents to a temporary local file
            File tmpDir = Paths.get(properties.getServerTmpDir()).toFile();
            tmpDir.mkdirs();
            File tmp = File.createTempFile("bci_upload_", ".tmp", tmpDir);
            log.debug("SshClientInstaller: Write to temp. file: task #{}: temp-file: {}, remote: {}, content-length: {}", taskCounter, tmp, remoteFilePath, content.length());
            log.trace("SshClientInstaller: Write to temp. file: task #{}: temp-file: {}, remote: {}, content:\n{}", taskCounter, tmp, remoteFilePath, content);
            try (FileWriter fw = new FileWriter(tmp.getAbsoluteFile())) { fw.write(content); }

            // Upload temporary local file to remote side
            log.trace("SshClientInstaller: Call 'sshFileUpload': task #{}: temp-file={}, remote={}", taskCounter, tmp, remoteFilePath);
            sshFileUpload(tmp.getAbsolutePath(), remoteFilePath);

            // Delete temporary file
            if (!properties.isKeepTempFiles()) {
                log.trace("SshClientInstaller: Remove temp. file: task #{}: temp-file={}", taskCounter, tmp);
                tmp.delete();
            }

            long endTm = System.currentTimeMillis();
            log.info("SshClientInstaller: File upload completed in {}ms: task #{}: remote: {}, content-length={}", endTm-timestamp, taskCounter, remoteFilePath, content.length());
            log.trace("SshClientInstaller: File upload completed in {}ms: task #{}: remote: {}, content:\n{}", endTm-timestamp, taskCounter, remoteFilePath, content);
        } catch (Exception ex) {
            log.error("SshClientInstaller: File upload failed: task #{}: remote: {}, Exception: ", taskCounter, remoteFilePath, ex);
            throw ex;
        }

        return true;
    }

    private boolean executeInstructionsList() throws IOException {
        List<InstallationInstructions> installationInstructionsList = task.getInstallationInstructions();
        int cntSuccess = 0;
        int cntFail = 0;
        for (InstallationInstructions installationInstructions : installationInstructionsList) {
            log.info("----------------------------------------------------------------------");
            log.info("SshClientInstaller: Task #{}: Executing installation instructions set: {}", taskCounter, installationInstructions.getDescription());
            streamLogger.logMessage(
                    String.format("----------------------------------------------------------------------\nExecuting instruction set: %s\n",
                    installationInstructions.getDescription()));
            boolean result = executeInstructions(installationInstructions);
            if (!result) {
                log.error("SshClientInstaller: Task #{}: Installation Instructions failed: {}", taskCounter, installationInstructions.getDescription());
                cntFail++;
                if (!continueOnFail)
                    return false;
            } else {
                log.info("SshClientInstaller: Task #{}: Installation Instructions succeeded: {}", taskCounter, installationInstructions.getDescription());
                cntSuccess++;
            }
        }
        log.info("-------------------------------------------------------------------------");
        log.info("SshClientInstaller: Task #{}: Instruction sets processed: successful={}, failed={}", taskCounter, cntSuccess, cntFail);
        return true;
    }

    private boolean executeInstructions(InstallationInstructions installationInstructions) throws IOException {
        Map<String, String> valueMap = installationInstructions.getValueMap();
        int numOfInstructions = installationInstructions.getInstructions().size();
        int cnt = 0;
        int insCount = installationInstructions.getInstructions().size();
        for (Instruction ins : installationInstructions.getInstructions()) {
            cnt++;
            log.trace("SshClientInstaller: Task #{}: Executing instruction {}/{}: {}", taskCounter, cnt, numOfInstructions, ins);
            log.info("SshClientInstaller: Task #{}: Executing instruction {}/{}: {}", taskCounter, cnt, numOfInstructions, ins.getDescription());
            Integer exitStatus;
            boolean result = true;
            switch (ins.getTaskType()) {
                case LOG:
                    log.info("SshClientInstaller: Task #{}: LOG: {}", taskCounter, ins.getMessage());
                    break;
                case CMD:
                    log.info("SshClientInstaller: Task #{}: EXEC: {}", taskCounter, ins.getCommand());
                    int retries = 0;
                    int maxRetries = ins.getRetries();
                    while (true) {
                        try {
                            exitStatus = sshExecCmd(ins.getCommand(), ins.getExecutionTimeout());
                            result = (exitStatus!=null);
                            //result = (exitStatus==0);
                            log.info("SshClientInstaller: Task #{}: EXEC: exit-status={}", taskCounter, exitStatus);
                            if (result) break;
                        } catch (Exception ex) {
                            if (retries+1>=maxRetries)
                                throw ex;
                            else
                                log.error("SshClientInstaller: Task #{}: EXEC: Last command raised exception: ", taskCounter, ex);
                        }

                        retries++;
                        if (retries<=maxRetries) {
                            log.info("SshClientInstaller: Task #{}: Retry {}/{} for instruction {}/{}: {}",
                                    taskCounter, retries, maxRetries, cnt, numOfInstructions, ins.getDescription());
                        } else {
                            if (maxRetries>0)
                                log.error("sshClientInstaller: Task #{}: Last instruction failed {} times. Giving up", taskCounter, maxRetries);
                            result = false;
                            break;
                        }
                    }
                    break;
                /*case SHELL:
                    log.info("SshClientInstaller: Task #{}: SHELL: {}", taskCounter, ins.getCommand());
                    retries = 0;
                    maxRetries = ins.getRetries();
                    while (true) {
                        try {
                            result = sshShellExec(ins.getCommand(), ins.getExecutionTimeout());
                            log.info("SshClientInstaller: Task #{}: SHELL: exit-status={}", taskCounter, result);
                            if (result) break;
                        } catch (Exception ex) {
                            if (retries+1>=maxRetries)
                                throw ex;
                            else
                                log.error("SshClientInstaller: Task #{}: SHELL: Last command raised exception: ", taskCounter, ex);
                        }

                        retries++;
                        if (retries<=maxRetries) {
                            log.info("SshClientInstaller: Task #{}: Retry {}/{} for instruction {}/{}: {}",
                                    taskCounter, retries, maxRetries, cnt, numOfInstructions, ins.getDescription());
                        } else {
                            if (maxRetries>0)
                                log.error("sshClientInstaller: Task #{}: Last instruction failed {} times. Giving up", taskCounter, maxRetries);
                            result = false;
                            break;
                        }
                    }
                    break;*/
                case FILE:
                    //log.info("SshClientInstaller: Task #{}: FILE: {}, content-length={}", taskCounter, ins.getFileName(), ins.getContents().length());
                    if (Paths.get(ins.getLocalFileName()).toFile().isDirectory()) {
                        log.info("SshClientInstaller: Task #{}: FILE: COPY-PROCESS DIR: {} -> {}", taskCounter, ins.getLocalFileName(), ins.getFileName());
                        result = copyDir(ins.getLocalFileName(), ins.getFileName(), valueMap);
                    } else
                    if (Paths.get(ins.getLocalFileName()).toFile().isFile()) {
                        log.info("SshClientInstaller: Task #{}: FILE: COPY-PROCESS FILE: {} -> {}", taskCounter, ins.getLocalFileName(), ins.getFileName());
                        Path sourceFile = Paths.get(ins.getLocalFileName());
                        Path sourceBaseDir = Paths.get(ins.getLocalFileName()).getParent();
                        result = copyFile(sourceFile, sourceBaseDir, ins.getFileName(), valueMap, ins.isExecutable());
                    } else {
                        log.error("SshClientInstaller: Task #{}: FILE: ERROR: Local file is not directory or normal file: {}", taskCounter, ins.getLocalFileName());
                        result = false;
                    }
                    break;
                case COPY:
                    log.info("SshClientInstaller: Task #{}: UPLOAD: {} -> {}", taskCounter, ins.getLocalFileName(), ins.getFileName());
                    result = sshFileUpload(ins.getLocalFileName(), ins.getFileName());
                    break;
                case CHECK:
                    log.info("SshClientInstaller: Task #{}: CHECK: {}", taskCounter, ins.getCommand());
                    exitStatus = sshExecCmd(ins.getCommand());
                    log.debug("SshClientInstaller: Task #{}: CHECK: Result: match={}, match-status={}, exec-status={}",
                            taskCounter, ins.isMatch(), ins.getExitCode(), exitStatus);
                    if (ins.isMatch() && exitStatus==ins.getExitCode()
                        || !ins.isMatch() && exitStatus!=ins.getExitCode())
                    {
                        log.info("SshClientInstaller: Task #{}: CHECK: MATCH: {}", taskCounter, ins.getMessage());
                        log.info("SshClientInstaller: Task #{}: CHECK: MATCH: Will not process more instructions", taskCounter);
                        return true;
                    }
                    break;
                default:
                    log.error("sshClientInstaller: Unknown instruction type. Ignoring it: {}", ins);
            }
            if (!result) {
                log.error("sshClientInstaller: Last instruction failed. Will not process remaining instructions");
                return false;
            }

            if (cnt<insCount)
                log.trace("sshClientInstaller: Continuing with next command...");
            else
                log.trace("sshClientInstaller: No more instructions");
        }
        return true;
    }

    private boolean copyDir(String sourceDir, String targetDir, Map<String,String> valueMap) throws IOException {
        // Copy files from EMS server to Baguette Client
        if (StringUtils.isNotEmpty(sourceDir) && StringUtils.isNotEmpty(targetDir)) {
            Path baseDir = Paths.get(sourceDir).toAbsolutePath();
            try (Stream<Path> stream = Files.walk(baseDir, Integer.MAX_VALUE)) {
                List<Path> paths = stream
                        .filter(Files::isRegularFile)
                        .map(Path::toAbsolutePath)
                        .sorted()
                        .collect(Collectors.toList());
                for (Path p : paths) {
                    if (!copyFile(p, baseDir, targetDir, valueMap, false))
                        return false;
                }
            }
        }
        return true;
    }

    private boolean copyFile(Path sourcePath, Path sourceBaseDir, String targetDir, Map<String,String> valueMap, boolean isExecutable) throws IOException {
        String targetFile = StringUtils.substringAfter(sourcePath.toUri().toString(), sourceBaseDir.toUri().toString());
        if (!targetFile.startsWith("/")) targetFile = "/"+targetFile;
        targetFile = targetDir + targetFile;

        String contents = new String(Files.readAllBytes(sourcePath));
        log.info("SshClientInstaller: Task #{}: FILE: {}, content-length={}", taskCounter, targetFile, contents.length());
        contents = StringSubstitutor.replace(contents, valueMap);
        log.trace("SshClientInstaller: Task #{}: FILE: {}, final-content:\n{}", taskCounter, targetFile, contents);

        String description = String.format("Copy file from server to temp to client: %s -> %s", sourcePath.toString(), targetFile);

        return sshFileWrite(contents, targetFile, isExecutable);
    }
}
