/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.extra.cloudiator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.melodic.event.baguette.client.install.ClientInstallationTask;
import eu.melodic.event.baguette.client.install.helper.AbstractInstallationHelper;
import eu.melodic.event.baguette.client.install.instruction.InstructionsSet;
import eu.melodic.event.baguette.server.BaguetteServer;
import eu.melodic.event.baguette.server.NodeRegistryEntry;
import eu.melodic.event.util.CredentialsMap;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Baguette Client installation helper
 */
@Slf4j
//@Service
@NoArgsConstructor
public class CloudiatorInstallationHelper extends AbstractInstallationHelper {
    private static CloudiatorInstallationHelper instance = null;

    public synchronized static CloudiatorInstallationHelper getInstance() {
        if (instance == null) instance = new CloudiatorInstallationHelper();
        return instance;
    }

    @Override
    public ClientInstallationTask createClientInstallationTask(NodeRegistryEntry entry) throws Exception {
        return null;
    }

    @Override
    public List<InstructionsSet> prepareInstallationInstructionsForWin(NodeRegistryEntry entry) {
        log.warn("CloudiatorInstallationHelper.prepareInstallationInstructionsForWin(): NOT YET IMPLEMENTED");
        throw new IllegalArgumentException("CloudiatorInstallationHelper.prepareInstallationInstructionsForWin(): NOT YET IMPLEMENTED");
    }

    @Override
    public List<InstructionsSet> prepareInstallationInstructionsForLinux(NodeRegistryEntry entry) throws IOException {
        Map<String, String> nodeMap = entry.getPreregistration();
        BaguetteServer baguette = entry.getBaguetteServer();

        String baseUrl = nodeMap.get("BASE_URL");
        String clientId = nodeMap.get("CLIENT_ID");
        String ipSetting = nodeMap.get("IP_SETTING");
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
        InstructionsSet instructionsSet = new InstructionsSet();
        instructionsSet.setOs("LINUX");

        // Check whether EMS Client is already installed
                /*.appendLog("Checking if Baguette Client is already installed")
                .appendCheck("[[ -f "+checkInstallationFile+" ]] && exit 99", 0, true, "NOTE: Baguette Client is already installed")
                .appendExec("Baguette Client is NOT installed")*/

        // Create Baguette Client installation directories
        instructionsSet.appendLog("Create Baguette Client installation directories");
        String dirList = String.join(" ", properties.getMkdirs());
        if (StringUtils.isNotEmpty(dirList))
            instructionsSet.appendExec("sudo mkdir -p " + dirList);

        // Create files using touch
        instructionsSet.appendLog("Touch files");
        String touchList = String.join(" ", properties.getTouchFiles());
        if (StringUtils.isNotEmpty(touchList))
            instructionsSet.appendExec("sudo touch " + touchList);

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
                    _appendCopyInstructions(instructionsSet, p, startDir, copyToClientDir, clientTmpDir, valueMap);
                }
            }
        }

        // Download Baguette Client installation script
        instructionsSet
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

        // Pretty print instructionsSet JSON
        if (log.isDebugEnabled()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            StringWriter sw = new StringWriter();
            try (PrintWriter writer = new PrintWriter(sw)) {
                gson.toJson(instructionsSet, writer);
            }
            log.debug("prepareInstallationInstructionsForLinux(): instructionsSet:\n{}", sw.toString());
        }

        return Collections.singletonList(instructionsSet);
    }
}
