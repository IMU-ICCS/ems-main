/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
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
import eu.melodic.event.baguette.client.install.instruction.Instruction;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
public class VmInstallationHelper extends AbstractInstallationHelper {
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private Environment environment;

    @Override
    public InstallationInstructions prepareInstallationInstructionsForWin(String baseUrl, String clientId, BaguetteServer baguette, String ipSetting) {
        log.warn("VmInstallationHelper.prepareInstallationInstructionsForWin(): NOT YET IMPLEMENTED");
        return null;
    }

    @Override
    public InstallationInstructions prepareInstallationInstructionsForLinux(String baseUrl, String clientId, BaguetteServer baguette, String ipSetting) throws IOException {
        log.debug("VmInstallationHelper.prepareInstallationInstructionsForLinux(): Invoked: base-url={}", baseUrl);

        // Get parameters
        log.debug("VmInstallationHelper.prepareInstallationInstructionsForLinux(): properties: {}", properties);
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
        valueMap.put("BAGUETTE_SERVER_PORT", ""+baguette.getConfiguration().getServerPort());
        valueMap.put("BAGUETTE_SERVER_PUBKEY", baguette.getServerPubkey());
        valueMap.put("BAGUETTE_SERVER_PUBKEY_FINGERPRINT", baguette.getServerPubkeyFingerprint());
        CredentialsMap.Entry<String,String> pair =
                baguette.getConfiguration().getCredentials().entrySet().iterator().next();
        valueMap.put("BAGUETTE_SERVER_USERNAME", pair.getKey());
        valueMap.put("BAGUETTE_SERVER_PASSWORD", pair.getValue());

        if (StringUtils.isEmpty(ipSetting)) throw new IllegalArgumentException("IP_SETTING must have a value");
        valueMap.put("IP_SETTING", ipSetting);

        valueMap.put("BASE_URL", baseUrl);
        valueMap.put("DOWNLOAD_URL", baseDownloadUrl);
        valueMap.put("API_KEY", apiKey);
        valueMap.put("SERVER_CERT_FILE", serverCertFile);
        valueMap.put("REMOTE_TMP_DIR", clientTmpDir);

        try {
            // Read installation instructions from JSON file
            String jsonFile = properties.getInstructions().get("LINUX");
            log.debug("VmInstallationHelper.prepareInstallationInstructionsForLinux: Installation instructions file for LINUX: {}", jsonFile);
            byte[] bdata = FileCopyUtils.copyToByteArray(resourceLoader.getResource(jsonFile).getInputStream());
            String json = new String(bdata, StandardCharsets.UTF_8);
            log.trace("VmInstallationHelper.prepareInstallationInstructionsForLinux: Installation instructions for LINUX: json:\n{}", json);

            // Process placeholders
            json = StringSubstitutor.replace(json, valueMap);
            json = environment.resolvePlaceholders(json);
            //json = environment.resolveRequiredPlaceholders(json);
            json = json.replace('\\', '/');
            log.debug("VmInstallationHelper.prepareInstallationInstructionsForLinux: Installation instructions for LINUX after placeholder processing: json:\n{}", json);

            // Create InstallationInstructions object from JSON
            InstallationInstructions installationInstructions =
                    new Gson().fromJson(json, InstallationInstructions.class);
            installationInstructions.setValueMap(valueMap);
            log.debug("VmInstallationHelper.prepareInstallationInstructionsForLinux: Installation instructions for LINUX: object:\n{}", installationInstructions);

            return installationInstructions;
        } catch (Exception ex) {
            log.error("VmInstallationHelper.prepareInstallationInstructionsForLinux: Exception while reading Installation instructions for LINUX: ", ex);
            throw ex;
        }
    }

/*
    public InstallationInstructions prepareInstallationInstructionsForLinux(String baseUrl, String clientId, BaguetteServer baguette, String ipSetting) throws IOException {
        // Set the target operating system
        InstallationInstructions installationInstructions = new InstallationInstructions();
        installationInstructions.setOs("LINUX");

        // Check whether EMS Client is already installed
                .appendLog("Checking if Baguette Client is already installed")
                .appendCheck("[[ -f "+checkInstallationFile+" ]] && exit 99", 0, true, "NOTE: Baguette Client is already installed")
                .appendExec("Baguette Client is NOT installed")

        // Create Baguette Client installation directories
        String dirList = String.join(" ", properties.getMkdirs());
        if (StringUtils.isNotEmpty(dirList)) {
            installationInstructions.appendLog("Create Baguette Client installation directories");
            installationInstructions.appendExec("sudo mkdir -p " + dirList);
        }

        // Change ownership of installation directories
        if (StringUtils.isNotEmpty(dirList)) {
            installationInstructions.appendLog("Change ownership of installation directories");
            installationInstructions.appendExec("sudo chown -R ubuntu:ubuntu /opt/baguette-client");
        }

        // Create files using touch
        String touchList = String.join(" ", properties.getTouchFiles());
        if (StringUtils.isNotEmpty(touchList)) {
            installationInstructions.appendLog("Touch files");
            installationInstructions.appendExec("touch " + touchList);
        }

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
                .appendLog("Store baguette client configuration archive (in base64 encoding)")
                .appendWriteFile(clientConfArchive, archiveBase64, false)


        // Run Baguette Client installation script
                .appendLog("Run Baguette Client installation script")
                .appendExec("sudo "+installScriptPath+" \""+serverCertFile+"\" "+baseDownloadUrl+" "+apiKey)

        // Launch Baguette Client
                .appendLog("Launch Baguette Client")
                .appendExec("sudo service baguette-client start")


        // Write successful installation file
                .appendLog("Write successful installation file")
                .appendExec("sudo touch " + checkInstallationFile)

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
*/

    private InstallationInstructions _appendCopyInstructions(
            InstallationInstructions installationInstructions,
            Path path,
            Path localBaseDir,
            String remoteTargetDir,
            Map<String,String> valueMap
    ) throws IOException
    {
        String targetFile = StringUtils.substringAfter(path.toUri().toString(), localBaseDir.toUri().toString());
        if (!targetFile.startsWith("/")) targetFile = "/"+targetFile;
        targetFile = remoteTargetDir + targetFile;
        String contents = new String(Files.readAllBytes(path));
        contents = StringSubstitutor.replace(contents, valueMap);
        String description = String.format("Copy file from server to temp to client: %s -> %s", path.toString(), targetFile);
        return _appendCopyInstructions(installationInstructions, targetFile, description, contents);
    }

    private InstallationInstructions _appendCopyInstructions(
            InstallationInstructions installationInstructions,
            String targetFile,
            String description,
            String contents)
    {
        installationInstructions
                .appendInstruction(Instruction.createWriteFile(targetFile, contents, false).description(description))
                .appendExec("sudo chmod u+rw,og-rwx " + targetFile);
        return installationInstructions;
    }
}
