/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.install.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.melodic.event.baguette.client.install.ClientInstallationProperties;
import eu.melodic.event.baguette.client.install.instruction.InstallationInstructions;
import eu.melodic.event.baguette.server.BaguetteServer;
import eu.melodic.event.util.CredentialsMap;
import eu.melodic.event.util.KeystoreUtil;
import eu.melodic.event.util.NetUtil;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Baguette Client installation helper
 */
@Slf4j
@Service
public class CloudiatorInstallationHelper implements InitializingBean, ApplicationListener<WebServerInitializedEvent> {
    private static CloudiatorInstallationHelper instance = null;
    private static List<String> LINUX_OS_FAMILIES;
    private static List<String> WINDOWS_OS_FAMILIES;

    @Autowired
    private ClientInstallationProperties properties;

    private String archiveBase64;
    private boolean isServerSecure;
    private String serverCert;

    private CloudiatorInstallationHelper() {
        CloudiatorInstallationHelper.instance = this;
    }

    public synchronized static CloudiatorInstallationHelper getInstance() {
        if (instance == null) instance = new CloudiatorInstallationHelper();
        return instance;
    }

    @Override
    public void afterPropertiesSet() {
        log.info("CloudiatorInstallationHelper.afterPropertiesSet(): configuration: {}", properties);
        LINUX_OS_FAMILIES = properties.getOsFamilies().get("LINUX");
        WINDOWS_OS_FAMILIES = properties.getOsFamilies().get("WINDOWS");
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        log.debug("CloudiatorInstallationHelper.onApplicationEvent(): event={}", event);
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
        log.debug("CloudiatorInstallationHelper.initServerCertificate(): Embedded Tomcat is secure: {}", isServerSecure);

        if (isServerSecure) {
            SSLHostConfig[] sslHostConfigArr = tomcat.getTomcat().getConnector().findSslHostConfigs();
            log.debug("CloudiatorInstallationHelper.initServerCertificate(): Tomcat SSL host config array: length={}",
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
            log.debug("CloudiatorInstallationHelper.initServerCertificate(): Tomcat SSL host config: keystore={}, type={}, key-alias={}",
                    keystoreFile, keystoreType, keyAlias);

            String certFileName = properties.getServerCertFileAtServer();
            if (StringUtils.isNotEmpty(certFileName)) {
                log.debug("CloudiatorInstallationHelper.initServerCertificate(): Exporting server certificate to file: {}", certFileName);
                KeystoreUtil
                        .getKeystore(keystoreFile, keystoreType, keystorePassword)
                        .exportCertToFile(keyAlias, certFileName);
                log.debug("CloudiatorInstallationHelper.initServerCertificate(): Server certificate exported");

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
                    log.debug("CloudiatorInstallationHelper.initServerCertificate(): Removing previous server certificate file");
                    if (!certFile.delete())
                        throw new RuntimeException("Could not remove previous server certificate file: " + certFile);
                }
            }
        }
    }

    private void initBaguetteClientConfigArchive() throws IOException {
        if (StringUtils.isEmpty(properties.getArchiveSourceDir()) || StringUtils.isEmpty(properties.getArchiveFile())) {
            log.debug("CloudiatorInstallationHelper: No baguette client configuration archiving has been configured");
            return;
        }
        log.info("CloudiatorInstallationHelper: Building baguette client configuration archive...");

        // Get archiving settings
        String configDirName = properties.getArchiveSourceDir();
        File configDir = new File(configDirName);
        log.debug("CloudiatorInstallationHelper: Baguette client configuration directory: {}", configDir);
        if (!configDir.exists())
            throw new FileNotFoundException("Baguette client configuration directory not found: " + configDirName);

        String archiveName = properties.getArchiveFile();
        String archiveDirName = properties.getArchiveDir();
        File archiveDir = new File(archiveDirName);
        log.debug("CloudiatorInstallationHelper: Baguette client configuration archive: {}/{}", archiveDirName, archiveName);
        if (!archiveDir.exists())
            throw new FileNotFoundException("Baguette client configuration archive directory not found: " + archiveDirName);

        // Remove previous baguette client configuration archive
        File archiveFile = new File(archiveDirName, archiveName);
        if (archiveFile.exists()) {
            log.debug("CloudiatorInstallationHelper: Removing previous archive...");
            if (!archiveFile.delete())
                throw new RuntimeException("CloudiatorInstallationHelper: Failed removing previous archive: " + archiveName);
        }

        // Create baguette client configuration archive
        Archiver archiver = ArchiverFactory.createArchiver(archiveFile);
        String tempFileName = "archive_" + System.currentTimeMillis();
        log.debug("CloudiatorInstallationHelper: Temp. archive name: {}", tempFileName);
        archiveFile = archiver.create(tempFileName, archiveDir, configDir);
        log.debug("CloudiatorInstallationHelper: Archive generated: {}", archiveFile);
        if (!archiveFile.getName().equals(archiveName)) {
            log.debug("CloudiatorInstallationHelper: Renaming archive to: {}", archiveName);
            if (!archiveFile.renameTo(archiveFile = new File(archiveDir, archiveName)))
                throw new RuntimeException("CloudiatorInstallationHelper: Failed renaming generated archive to: " + archiveName);
        }
        log.info("CloudiatorInstallationHelper: Baguette client configuration archive: {}", archiveFile);

        // Base64 encode archive and cache in memory
        byte[] archiveBytes = Files.readAllBytes(archiveFile.toPath());
        this.archiveBase64 = Base64.getEncoder().encodeToString(archiveBytes);
        log.debug("CloudiatorInstallationHelper: Archive Base64 encoded: {}", archiveBase64);
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
        log.debug("CloudiatorInstallationHelper.prepareInstallationInstructionsForOs(): node-map={}, base-url={}, client-id={}", nodeMap, baseUrl, clientId);

        String osFamily = (String) nodeMap.get("operatingSystem");
        InstallationInstructions installationInstructions = null;
        if (LINUX_OS_FAMILIES.contains(osFamily.toUpperCase()))
            installationInstructions = prepareInstallationInstructionsForLinux(baseUrl, clientId, baguette, ipSetting);
        else if (WINDOWS_OS_FAMILIES.contains(osFamily.toUpperCase()))
            installationInstructions = prepareInstallationInstructionsForWin(baseUrl, clientId, baguette, ipSetting);
        else
            log.warn("CloudiatorInstallationHelper.prepareInstallationInstructionsForOs(): Unsupported OS family: {}", osFamily);
        return installationInstructions;
    }

    public InstallationInstructions prepareInstallationInstructionsForWin(String baseUrl, String clientId, BaguetteServer baguette, String ipSetting) {
        log.warn("CloudiatorInstallationHelper.prepareInstallationInstructionsForWin(): NOT YET IMPLEMENTED");
        return null;
    }

    public InstallationInstructions prepareInstallationInstructionsForLinux(String baseUrl, String clientId, BaguetteServer baguette, String ipSetting) throws IOException {
        log.debug("CloudiatorInstallationHelper.prepareInstallationInstructionsForLinux(): Invoked: base-url={}", baseUrl);

        // Get parameters
        log.debug("prepareInstallationInstructionsForLinux(): properties: {}", properties);
        String checkInstallationFile = properties.getCheckInstalledFile();

        String baseDownloadUrl = _prepareUrl(properties.getDownloadUrl(), baseUrl);
        String apiKey = properties.getApiKey();
        String installScriptUrl = _prepareUrl(properties.getInstallScriptUrl(), baseUrl);
        String installScriptPath = properties.getInstallScriptFile();

        String serverCertFile = properties.getServerCertFileAtClient();
        String clientConfArchive = properties.getClientConfArchiveFile();

        String copyFromServerDir = properties.getCopyFilesFromServerDir();
        String copyToClientDir = properties.getCopyFilesToClientDir();

        String clientTmpDir = StringUtils.firstNonBlank(properties.getClientTmpDir(), "/tmp");

        // Load client config. template and prepare configuration
        Map<String,String> valueMap = new HashMap<>();
        valueMap.put("BAGUETTE_CLIENT_ID", clientId);
        valueMap.put("BAGUETTE_SERVER_ADDRESS", baguette.getConfiguration().getServerAddress());
        valueMap.put("BAGUETTE_SERVER_HOSTNAME", baguette.getConfiguration().getServerHostname());
        valueMap.put("BAGUETTE_SERVER_PORT", ""+baguette.getConfiguration().getServerPort());
        valueMap.put("BAGUETTE_SERVER_PUBKEY", baguette.getServerPubkey());
        valueMap.put("BAGUETTE_SERVER_PUBKEY_FINGERPRINT", baguette.getServerPubkeyFingerprint());
        CredentialsMap.Entry<String,String> pair =
                baguette.getConfiguration().getCredentials().entrySet().iterator().next();
        valueMap.put("BAGUETTE_SERVER_USERNAME", pair.getKey());
        valueMap.put("BAGUETTE_SERVER_PASSWORD", pair.getValue());

        if (StringUtils.isEmpty(ipSetting)) throw new IllegalArgumentException("IP_SETTING must have a value");
        valueMap.put("IP_SETTING", ipSetting);

        // Set the target operating system
        InstallationInstructions installationInstructions = new InstallationInstructions();
        installationInstructions.setOs("LINUX");

        // Check whether EMS Client is already installed
                /*.appendLog("Checking if Baguette Client is already installed")
                .appendCheck("[[ -f "+checkInstallationFile+" ]] && exit 99", 0, true, "NOTE: Baguette Client is already installed")
                .appendExec("Baguette Client is NOT installed")*/

        // Create Baguette Client installation directories
        installationInstructions.appendLog("Create Baguette Client installation directories");
        String dirList = String.join(" ", properties.getMkdirs());
        if (StringUtils.isNotEmpty(dirList))
            installationInstructions.appendExec("sudo mkdir -p " + dirList);

        // Create files using touch
        installationInstructions.appendLog("Touch files");
        String touchList = String.join(" ", properties.getTouchFiles());
        if (StringUtils.isNotEmpty(touchList))
            installationInstructions.appendExec("sudo touch " + touchList);

        // Clear EMS server certificate (PEM) file, if not secure
        if (!isServerSecure) {
            serverCertFile = "";
        }

        // Copy files from server to Baguette Client
        if (StringUtils.isNotEmpty(copyFromServerDir) && StringUtils.isNotEmpty(copyToClientDir)) {
            Path startDir = Paths.get(copyFromServerDir).toAbsolutePath();
            try (Stream<Path> stream = Files.walk(startDir, Integer.MAX_VALUE)) {
                List<Path> paths = stream
                        .filter(Files::isRegularFile)
                        .map(Path::toAbsolutePath)
                        .sorted()
                        .collect(Collectors.toList());
                for (Path p : paths) {
                    _appendCopyInstructions(installationInstructions, p, startDir, copyToClientDir, clientTmpDir, valueMap);
                }
            }
        }

        // Download Baguette Client installation script
        installationInstructions
                .appendLog("Download Baguette Client installation script")
                //.appendExec("sudo wget --no-check-certificate " + installScriptUrl + " -O " + installScriptPath)
                .appendExec(
                        isServerSecure
                                ? "sudo wget --ca-certificate="+serverCertFile+" " + installScriptUrl + " -O " + installScriptPath
                                : "sudo wget " + installScriptUrl + " -O " + installScriptPath
                )

        // Make Baguette Client installation script executable
                .appendLog("Make Baguette Client installation script executable")
                .appendExec("sudo chmod u+rwx,og-rwx " + installScriptPath)

        // Store Baguette Client configuration archive
                /*.appendLog("Store baguette client configuration archive (in base64 encoding)")
                .appendWriteFile(clientConfArchive, archiveBase64, false)*/

        // Run Baguette Client installation script
                .appendLog("Run Baguette Client installation script")
                .appendExec("sudo "+installScriptPath+" \""+serverCertFile+"\" "+baseDownloadUrl+" "+apiKey)

        // Launch Baguette Client
                /*.appendLog("Launch Baguette Client")
                .appendExec("sudo service baguette-client start")*/

        // Write successful installation file
                /*.appendLog("Write successful installation file")
                .appendExec("sudo touch " + checkInstallationFile)*/
        ;

        // Pretty print installationInstructions JSON
        if (log.isDebugEnabled()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            StringWriter sw = new StringWriter();
            try (PrintWriter writer = new PrintWriter(sw)) {
                gson.toJson(installationInstructions, writer);
            }
            log.debug("prepareInstallationInstructionsForLinux(): installationInstructions:\n{}", sw.toString());
        }

        return installationInstructions;
    }

    private InstallationInstructions _appendCopyInstructions(
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

    private InstallationInstructions _appendCopyInstructions(
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

    private String _prepareUrl(String urlTemplate, String baseUrl) {
        return urlTemplate
                .replace("%{BASE_URL}%", baseUrl)
                .replace("%{PUBLIC_IP}%", Optional.ofNullable(NetUtil.getPublicIpAddress()).orElse(""))
                .replace("%{DEFAULT_IP}%", Optional.ofNullable(NetUtil.getDefaultIpAddress()).orElse(""));
    }
}
