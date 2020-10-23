/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.install.helper;

import eu.melodic.event.baguette.client.install.ClientInstallationProperties;
import eu.melodic.event.baguette.client.install.instruction.InstallationInstructions;
import eu.melodic.event.baguette.server.BaguetteServer;
import eu.melodic.event.util.KeystoreUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apache.tomcat.util.net.SSLHostConfig;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Baguette Client installation helper
 */
@Slf4j
@Service
public abstract class AbstractInstallationHelper implements InitializingBean, ApplicationListener<WebServerInitializedEvent>, InstallationHelper {
    protected static AbstractInstallationHelper instance = null;
    protected static List<String> LINUX_OS_FAMILIES;
    protected static List<String> WINDOWS_OS_FAMILIES;

    @Autowired
    protected ClientInstallationProperties properties;

    protected String archiveBase64;
    protected boolean isServerSecure;
    protected String serverCert;

    public synchronized static AbstractInstallationHelper getInstance() { return instance; }

    @Override
    public void afterPropertiesSet() {
        log.info("AbstractInstallationHelper.afterPropertiesSet(): configuration: {}", properties);
        AbstractInstallationHelper.instance = this;
        LINUX_OS_FAMILIES = properties.getOsFamilies().get("LINUX");
        WINDOWS_OS_FAMILIES = properties.getOsFamilies().get("WINDOWS");
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        log.debug("AbstractInstallationHelper.onApplicationEvent(): event={}", event);
        TomcatWebServer tomcat = (TomcatWebServer) event.getSource();

        try {
            initServerCertificateFile(tomcat);
            initBaguetteClientConfigArchive();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initServerCertificateFile(TomcatWebServer tomcat) throws Exception {
        //this.isServerSecure = "https".equalsIgnoreCase(tomcat.getTomcat().getConnector().getScheme());
        this.isServerSecure = tomcat.getTomcat().getConnector().getSecure();
        log.debug("AbstractInstallationHelper.initServerCertificate(): Embedded Tomcat is secure: {}", isServerSecure);

        if (isServerSecure) {
            SSLHostConfig[] sslHostConfigArr = tomcat.getTomcat().getConnector().findSslHostConfigs();
            log.debug("AbstractInstallationHelper.initServerCertificate(): Tomcat SSL host config array: length={}",
                    sslHostConfigArr.length);
            if (sslHostConfigArr.length!=1)
                throw new RuntimeException("Embedded Tomcat has zero or more than one SSL host configurations: "+sslHostConfigArr.length);

            SSLHostConfig sslHostConfig = sslHostConfigArr[0];
            String keystoreFile = sslHostConfig.getCertificateKeystoreFile();
            String keystorePassword = sslHostConfig.getCertificateKeystorePassword();
            String keystoreType = sslHostConfig.getCertificateKeystoreType();
            String keyAlias = sslHostConfig.getCertificateKeyAlias();

            if (StringUtils.startsWith(keystoreFile, "file:"))
                keystoreFile = StringUtils.substringAfter(keystoreFile, "file:");
            log.debug("AbstractInstallationHelper.initServerCertificate(): Tomcat SSL host config: keystore={}, type={}, key-alias={}",
                    keystoreFile, keystoreType, keyAlias);

            String certFileName = properties.getServerCertFileAtServer();
            if (StringUtils.isNotEmpty(certFileName)) {
                log.debug("AbstractInstallationHelper.initServerCertificate(): Exporting server certificate to file: {}", certFileName);
                KeystoreUtil
                        .getKeystore(keystoreFile, keystoreType, keystorePassword)
                        .exportCertToFile(keyAlias, certFileName);
                log.debug("AbstractInstallationHelper.initServerCertificate(): Server certificate exported");

                File certFile = new File(certFileName);
                if (! certFile.exists())
                    throw new RuntimeException("Server certificate file not found: "+certFile);
                this.serverCert = new String(Files.readAllBytes(Paths.get(certFile.getAbsolutePath())));
            } else {
                this.serverCert = KeystoreUtil
                        .getKeystore(keystoreFile, keystoreType, keystorePassword)
                        .getEntryCertificateAsPEM(keyAlias);
            }

        } else {
            if (StringUtils.isNotEmpty(properties.getServerCertFileAtServer())) {
                File certFile = new File(properties.getServerCertFileAtServer());
                if (certFile.exists()) {
                    log.debug("AbstractInstallationHelper.initServerCertificate(): Removing previous server certificate file");
                    if (!certFile.delete())
                        throw new RuntimeException("Could not remove previous server certificate file: " + certFile);
                }
            }
        }
    }

    private void initBaguetteClientConfigArchive() throws IOException {
        if (StringUtils.isEmpty(properties.getArchiveSourceDir()) || StringUtils.isEmpty(properties.getArchiveFile())) {
            log.debug("AbstractInstallationHelper: No baguette client configuration archiving has been configured");
            return;
        }
        log.info("AbstractInstallationHelper: Building baguette client configuration archive...");

        // Get archiving settings
        String configDirName = properties.getArchiveSourceDir();
        File configDir = new File(configDirName);
        log.debug("AbstractInstallationHelper: Baguette client configuration directory: {}", configDir);
        if (!configDir.exists())
            throw new FileNotFoundException("Baguette client configuration directory not found: " + configDirName);

        String archiveName = properties.getArchiveFile();
        String archiveDirName = properties.getArchiveDir();
        File archiveDir = new File(archiveDirName);
        log.debug("AbstractInstallationHelper: Baguette client configuration archive: {}/{}", archiveDirName, archiveName);
        if (!archiveDir.exists())
            throw new FileNotFoundException("Baguette client configuration archive directory not found: " + archiveDirName);

        // Remove previous baguette client configuration archive
        File archiveFile = new File(archiveDirName, archiveName);
        if (archiveFile.exists()) {
            log.debug("AbstractInstallationHelper: Removing previous archive...");
            if (!archiveFile.delete())
                throw new RuntimeException("AbstractInstallationHelper: Failed removing previous archive: " + archiveName);
        }

        // Create baguette client configuration archive
        Archiver archiver = ArchiverFactory.createArchiver(archiveFile);
        String tempFileName = "archive_" + System.currentTimeMillis();
        log.debug("AbstractInstallationHelper: Temp. archive name: {}", tempFileName);
        archiveFile = archiver.create(tempFileName, archiveDir, configDir);
        log.debug("AbstractInstallationHelper: Archive generated: {}", archiveFile);
        if (!archiveFile.getName().equals(archiveName)) {
            log.debug("AbstractInstallationHelper: Renaming archive to: {}", archiveName);
            if (!archiveFile.renameTo(archiveFile = new File(archiveDir, archiveName)))
                throw new RuntimeException("AbstractInstallationHelper: Failed renaming generated archive to: " + archiveName);
        }
        log.info("AbstractInstallationHelper: Baguette client configuration archive: {}", archiveFile);

        // Base64 encode archive and cache in memory
        byte[] archiveBytes = Files.readAllBytes(archiveFile.toPath());
        this.archiveBase64 = Base64.getEncoder().encodeToString(archiveBytes);
        log.debug("AbstractInstallationHelper: Archive Base64 encoded: {}", archiveBase64);
    }

    private String getResourceAsString(String resourcePath) throws IOException {
        InputStream resource = new FileSystemResource(resourcePath).getInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    public InstallationInstructions prepareInstallationInstructionsForOs(Map<String,Object> nodeMap, Map<String,String> contextMap, BaguetteServer baguette) throws IOException {
        if (! baguette.isServerRunning()) throw new RuntimeException("Baguette Server is not running");

        String baseUrl = contextMap.get("BASE_URL");
        String clientId = contextMap.get("CLIENT_ID");
        String ipSetting = contextMap.get("IP_SETTING");
        log.debug("AbstractInstallationHelper.prepareInstallationInstructionsForOs(): node-map={}, base-url={}, client-id={}", nodeMap, baseUrl, clientId);

        String osFamily = (String) nodeMap.get("operatingSystem");
        InstallationInstructions installationInstructions = null;
        if (LINUX_OS_FAMILIES.contains(osFamily.toUpperCase()))
            installationInstructions = prepareInstallationInstructionsForLinux(nodeMap, contextMap, baguette);
        else if (WINDOWS_OS_FAMILIES.contains(osFamily.toUpperCase()))
            installationInstructions = prepareInstallationInstructionsForWin(nodeMap, contextMap, baguette);
        else
            log.warn("AbstractInstallationHelper.prepareInstallationInstructionsForOs(): Unsupported OS family: {}", osFamily);
        return installationInstructions;
    }

    protected InstallationInstructions _appendCopyInstructions(
            InstallationInstructions installationInstructions,
            Path p,
            Path startDir,
            String copyToClientDir,
            String clientTmpDir,
            Map<String,String> valueMap
    ) throws IOException
    {
        String targetFile = StringUtils.substringAfter(p.toUri().toString(), startDir.toUri().toString());
        if (!targetFile.startsWith("/")) targetFile = "/"+targetFile;
        targetFile = copyToClientDir + targetFile;
        String contents = new String(Files.readAllBytes(p));
        contents = StringSubstitutor.replace(contents, valueMap);
        String tmpFile = clientTmpDir+"/installEMS_"+System.currentTimeMillis();
        installationInstructions
                .appendLog(String.format("Copy file from server to temp to client: %s -> %s -> %s", p.toString(), tmpFile, targetFile));
        return _appendCopyInstructions(installationInstructions, targetFile, tmpFile, contents, clientTmpDir);
    }

    protected InstallationInstructions _appendCopyInstructions(
            InstallationInstructions installationInstructions,
            String targetFile,
            String tmpFile,
            String contents,
            String clientTmpDir
    ) throws IOException
    {
        if (StringUtils.isEmpty(tmpFile))
            tmpFile = clientTmpDir+"/installEMS_"+System.currentTimeMillis();
        installationInstructions
                .appendWriteFile(tmpFile, contents, false)
                .appendExec("sudo mv " + tmpFile + " " + targetFile)
                .appendExec("sudo chmod u+rw,og-rwx " + targetFile);
        return installationInstructions;
    }
}
