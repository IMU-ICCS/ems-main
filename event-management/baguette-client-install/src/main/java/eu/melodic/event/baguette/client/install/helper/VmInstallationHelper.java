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
import eu.melodic.event.baguette.client.install.ClientInstallationTask;
import eu.melodic.event.baguette.client.install.SshConfig;
import eu.melodic.event.baguette.client.install.instruction.InstallationInstructions;
import eu.melodic.event.baguette.client.install.instruction.Instruction;
import eu.melodic.event.baguette.server.BaguetteServer;
import eu.melodic.event.util.CredentialsMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

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
    public ClientInstallationTask createClientInstallationTask(Map<String,Object> nodeMap, Map<String,String> contextMap, BaguetteServer baguette) throws IOException {
        String baseUrl = contextMap.get("BASE_URL");
        String clientId = contextMap.get("CLIENT_ID");
        String ipSetting = contextMap.get("IP_SETTING");

        // Extract node identification and type information
        String nodeId = (String) nodeMap.get("id");
        String nodeOs = (String) nodeMap.get("operatingSystem");
        String nodeAddress = (String) nodeMap.get("address");
        String nodeType = (String) nodeMap.get("type");
        String nodeName = (String) nodeMap.get("name");
        String nodeProvider = (String) nodeMap.get("provider");

        if (StringUtils.isBlank(nodeType)) nodeType = "VM";

        if (StringUtils.isBlank(nodeOs)) throw new IllegalArgumentException("Missing OS information for Node");
        if (StringUtils.isBlank(nodeAddress)) throw new IllegalArgumentException("Missing Address for Node");

        // Extract node SSH information
        Object sshObj = nodeMap.get("ssh");
        if (sshObj==null) throw new IllegalArgumentException("Missing SSH info for Node");
        if (!(sshObj instanceof Map)) throw new IllegalArgumentException("SSH info for Node is *not* a Map");

        Map<String,Object> nodeSsh = (Map<String,Object>) nodeMap.get("ssh");
        int port = (int) Double.parseDouble(Objects.toString(nodeSsh.get("port"), "22"));
        String username = (String) nodeSsh.get("username");
        String password = (String) nodeSsh.get("password");
        String privateKey = (String) nodeSsh.get("key");
        String fingerprint = (String) nodeSsh.get("fingerprint");

        if (port<1) port = 22;

        if (StringUtils.isBlank(username)) throw new IllegalArgumentException("Missing username for SSH");
        if ((password == null || password.isEmpty()) && StringUtils.isBlank(privateKey))
            throw new IllegalArgumentException("Missing SSH password or private key for Node");

        // Get EMS client installation instructions for VM node
        List<InstallationInstructions> installationInstructionsList =
                prepareInstallationInstructionsForOs(nodeMap, contextMap, baguette);

        // Create Installation Task for VM node
        ClientInstallationTask installationTask = ClientInstallationTask.builder()
                .id(clientId)
                .nodeId(nodeId)
                .name(nodeName)
                .os(nodeOs)
                .address(nodeAddress)
                .ssh(SshConfig.builder()
                        .host(nodeAddress)
                        .port(port)
                        .username(username)
                        .password(password)
                        .privateKey(privateKey)
                        .fingerprint(fingerprint)
                        .build())
                .type(nodeType)
                .provider(nodeProvider)
                .installationInstructions(installationInstructionsList)
                .build();

        return installationTask;
    }

    @Override
    public List<InstallationInstructions> prepareInstallationInstructionsForWin(Map<String, Object> nodeMap, Map<String,String> contextMap, BaguetteServer baguette) {
        log.warn("VmInstallationHelper.prepareInstallationInstructionsForWin(): NOT YET IMPLEMENTED");
        throw new IllegalArgumentException("VmInstallationHelper.prepareInstallationInstructionsForWin(): NOT YET IMPLEMENTED");
    }

    @Override
    public List<InstallationInstructions> prepareInstallationInstructionsForLinux(Map<String, Object> nodeMap, Map<String,String> contextMap, BaguetteServer baguette) throws IOException {
        String baseUrl = contextMap.get("BASE_URL");
        String clientId = contextMap.get("CLIENT_ID");
        String ipSetting = contextMap.get("IP_SETTING");
        log.debug("VmInstallationHelper.prepareInstallationInstructionsForLinux(): Invoked: base-url={}", baseUrl);

        // Get parameters
        log.trace("VmInstallationHelper.prepareInstallationInstructionsForLinux(): properties: {}", properties);
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

        // Initialize values map with nodeMap (from request)
        Map<String,String> valueMap = new HashMap<>(nodeMap.entrySet().stream()
                .filter(e -> e.getValue() instanceof String)
                .collect(Collectors.toMap(e -> "NODE_"+e.getKey().toUpperCase(), e -> (String)e.getValue())));
        valueMap.putAll( ((Map<String,Object>)nodeMap.get("ssh")).entrySet().stream()
                .filter(e -> e.getValue() instanceof String)
                .collect(Collectors.toMap(e -> "NODE_SSH_"+e.getKey().toUpperCase(), e -> (String)e.getValue())));

        // Load client config. template and prepare configuration
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

        // Misc. installation property values
        valueMap.put("BASE_URL", baseUrl);
        valueMap.put("DOWNLOAD_URL", baseDownloadUrl);
        valueMap.put("API_KEY", apiKey);
        valueMap.put("SERVER_CERT_FILE", serverCertFile);
        valueMap.put("REMOTE_TMP_DIR", clientTmpDir);
        log.trace("VmInstallationHelper.prepareInstallationInstructionsForLinux: value-map: {}", valueMap);

/*        // Clear EMS server certificate (PEM) file, if not secure
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
        }*/

        List<InstallationInstructions> installationInstructionsList = new ArrayList<>();

        try {
            // Read installation instructions from JSON file
            List<String> jsonFiles = properties.getInstructions().get("LINUX");
            for (String jsonFile : jsonFiles) {
                log.debug("VmInstallationHelper.prepareInstallationInstructionsForLinux: Installation instructions file for LINUX: {}", jsonFile);
                byte[] bdata = FileCopyUtils.copyToByteArray(resourceLoader.getResource(jsonFile).getInputStream());
                String json = new String(bdata, StandardCharsets.UTF_8);
                log.trace("VmInstallationHelper.prepareInstallationInstructionsForLinux: Template installation instructions for LINUX: json:\n{}", json);

                // Process placeholders
                json = StringSubstitutor.replace(json, valueMap);
                json = environment.resolvePlaceholders(json);
                //json = environment.resolveRequiredPlaceholders(json);
                json = json.replace('\\', '/');
                log.trace("VmInstallationHelper.prepareInstallationInstructionsForLinux: Installation instructions for LINUX after placeholder processing: json:\n{}", json);

                // Create InstallationInstructions object from JSON
                InstallationInstructions installationInstructions =
                        new Gson().fromJson(json, InstallationInstructions.class);
                installationInstructions.setValueMap(valueMap);
                installationInstructions.setFileName(jsonFile);
                log.trace("VmInstallationHelper.prepareInstallationInstructionsForLinux: Installation instructions for LINUX: object:\n{}", installationInstructions);

                // Pretty print installationInstructions JSON
                if (log.isTraceEnabled()) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    StringWriter sw = new StringWriter();
                    try (PrintWriter writer = new PrintWriter(sw)) {
                        gson.toJson(installationInstructions, writer);
                    }
                    log.trace("VmInstallationHelper.prepareInstallationInstructionsForLinux: Installation instructions for LINUX: json:\n{}", sw.toString());
                }

                installationInstructionsList.add(installationInstructions);
            }

            return installationInstructionsList;
        } catch (Exception ex) {
            log.error("VmInstallationHelper.prepareInstallationInstructionsForLinux: Exception while reading Installation instructions for LINUX: ", ex);
            throw ex;
        }
    }

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
