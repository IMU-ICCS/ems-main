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
import eu.melodic.event.baguette.client.install.ClientInstallationTask;
import eu.melodic.event.baguette.client.install.SshConfig;
import eu.melodic.event.baguette.client.install.instruction.InstructionsSet;
import eu.melodic.event.baguette.client.install.instruction.Instruction;
import eu.melodic.event.baguette.server.BaguetteServer;
import eu.melodic.event.baguette.server.NodeRegistryEntry;
import eu.melodic.event.util.CredentialsMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Baguette Client installation helper
 */
@Slf4j
@Service
public class VmInstallationHelper extends AbstractInstallationHelper {
    private final static SimpleDateFormat tsW3C = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    private final static SimpleDateFormat tsUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private final static SimpleDateFormat tsFile = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");
    static {
        tsW3C.setTimeZone(TimeZone.getDefault());
        tsUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        tsFile.setTimeZone(TimeZone.getDefault());
    }

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private ClientInstallationProperties clientInstallationProperties;

    @Override
    public ClientInstallationTask createClientInstallationTask(NodeRegistryEntry entry) throws IOException {
        Map<String, String> nodeMap = entry.getPreregistration();

        String baseUrl = nodeMap.get("BASE_URL");
        String clientId = nodeMap.get("CLIENT_ID");
        String ipSetting = nodeMap.get("IP_SETTING");

        // Extract node identification and type information
        String nodeId = nodeMap.get("id");
        String nodeOs = nodeMap.get("operatingSystem");
        String nodeAddress = nodeMap.get("address");
        String nodeType = nodeMap.get("type");
        String nodeName = nodeMap.get("name");
        String nodeProvider = nodeMap.get("provider");

        if (StringUtils.isBlank(nodeType)) nodeType = "VM";

        if (StringUtils.isBlank(nodeOs)) throw new IllegalArgumentException("Missing OS information for Node");
        if (StringUtils.isBlank(nodeAddress)) throw new IllegalArgumentException("Missing Address for Node");

        // Extract node SSH information
        int port = (int) Double.parseDouble(Objects.toString(nodeMap.get("ssh.port"), "22"));
        if (port<1) port = 22;
        String username = nodeMap.get("ssh.username");
        String password = nodeMap.get("ssh.password");
        String privateKey = nodeMap.get("ssh.key");
        String fingerprint = nodeMap.get("ssh.fingerprint");

        if (port<1 || port>65535)
            throw new IllegalArgumentException("Invalid SSH port for Node: " + port);
        if (StringUtils.isBlank(username))
            throw new IllegalArgumentException("Missing SSH username for Node");
        if (StringUtils.isEmpty(password) && StringUtils.isBlank(privateKey))
            throw new IllegalArgumentException("Missing SSH password or private key for Node");

        // Get EMS client installation instructions for VM node
        List<InstructionsSet> instructionsSetList =
                prepareInstallationInstructionsForOs(entry);

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
                .instructionSets(instructionsSetList)
                .build();
        log.debug("VmInstallationHelper.createClientInstallationTask(): Created client installation task: {}", installationTask);

        return installationTask;
    }

    @Override
    public List<InstructionsSet> prepareInstallationInstructionsForWin(NodeRegistryEntry entry) {
        log.warn("VmInstallationHelper.prepareInstallationInstructionsForWin(): NOT YET IMPLEMENTED");
        throw new IllegalArgumentException("VmInstallationHelper.prepareInstallationInstructionsForWin(): NOT YET IMPLEMENTED");
    }

    @Override
    public List<InstructionsSet> prepareInstallationInstructionsForLinux(NodeRegistryEntry entry) throws IOException {
        Map<String, String> nodeMap = entry.getPreregistration();
        BaguetteServer baguette = entry.getBaguetteServer();

        String baseUrl = nodeMap.get("BASE_URL");
        String clientId = nodeMap.get("CLIENT_ID");
        String ipSetting = nodeMap.get("IP_SETTING");
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

        // Create additional keys (with NODE_ prefix) for node map values (as aliases to the already existing keys)
        Map<String,String> additionalKeysMap = nodeMap.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().startsWith("ssh.")
                                ? "NODE_SSH_" + e.getKey().substring(4).toUpperCase()
                                : "NODE_" + e.getKey().toUpperCase(),
                        Map.Entry::getValue));
        nodeMap.putAll(additionalKeysMap);

        // Load client config. template and prepare configuration
        nodeMap.put("BAGUETTE_CLIENT_ID", clientId);
        nodeMap.put("BAGUETTE_SERVER_ADDRESS", baguette.getConfiguration().getServerAddress());
        nodeMap.put("BAGUETTE_SERVER_HOSTNAME", baguette.getConfiguration().getServerHostname());
        nodeMap.put("BAGUETTE_SERVER_PORT", ""+baguette.getConfiguration().getServerPort());
        nodeMap.put("BAGUETTE_SERVER_PUBKEY", baguette.getServerPubkey());
        nodeMap.put("BAGUETTE_SERVER_PUBKEY_FINGERPRINT", baguette.getServerPubkeyFingerprint());
        CredentialsMap.Entry<String,String> pair =
                baguette.getConfiguration().getCredentials().entrySet().iterator().next();
        nodeMap.put("BAGUETTE_SERVER_USERNAME", pair.getKey());
        nodeMap.put("BAGUETTE_SERVER_PASSWORD", pair.getValue());

        if (StringUtils.isEmpty(ipSetting)) throw new IllegalArgumentException("IP_SETTING must have a value");
        nodeMap.put("IP_SETTING", ipSetting);

        // Misc. installation property values
        nodeMap.put("BASE_URL", baseUrl);
        nodeMap.put("DOWNLOAD_URL", baseDownloadUrl);
        nodeMap.put("API_KEY", apiKey);
        nodeMap.put("SERVER_CERT_FILE", serverCertFile);
        nodeMap.put("REMOTE_TMP_DIR", clientTmpDir);

        Date ts = new Date();
        nodeMap.put("TIMESTAMP", Long.toString(ts.getTime()));
        nodeMap.put("TIMESTAMP-W3C", tsW3C.format(ts));
        nodeMap.put("TIMESTAMP-UTC", tsUTC.format(ts));
        nodeMap.put("TIMESTAMP-FILE", tsFile.format(ts));

        nodeMap.putAll(clientInstallationProperties.getParameters());
        nodeMap.put("EMS_PUBLIC_DIR", System.getProperty("PUBLIC_DIR", System.getenv("PUBLIC_DIR")));
        log.trace("VmInstallationHelper.prepareInstallationInstructionsForLinux: value-map: {}", nodeMap);

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
                    _appendCopyInstructions(instructionSets, p, startDir, copyToClientDir, clientTmpDir, valueMap);
                }
            }
        }*/

        List<InstructionsSet> instructionsSetList = new ArrayList<>();

        try {
            // Read installation instructions from JSON file
            List<String> jsonFiles = null;
            if (nodeMap.containsKey("instruction-files")) {
                jsonFiles = Arrays.stream(nodeMap.getOrDefault("instruction-files", "").toString().split(","))
                        .filter(StringUtils::isNotBlank)
                        .map(String::trim)
                        .collect(Collectors.toList());
                if (jsonFiles.size()==0)
                    log.warn("VmInstallationHelper.prepareInstallationInstructionsForLinux: Context map contains 'instruction-files' entry with no contents");
            } else {
                jsonFiles = properties.getInstructions().get("LINUX");
            }
            for (String jsonFile : jsonFiles) {
                log.debug("VmInstallationHelper.prepareInstallationInstructionsForLinux: Installation instructions file for LINUX: {}", jsonFile);
                byte[] bdata = FileCopyUtils.copyToByteArray(resourceLoader.getResource(jsonFile).getInputStream());
                String json = new String(bdata, StandardCharsets.UTF_8);
                log.trace("VmInstallationHelper.prepareInstallationInstructionsForLinux: Template installation instructions for LINUX: json:\n{}", json);

                // Create InstructionsSet object from JSON
                InstructionsSet instructionsSet =
                        new Gson().fromJson(json, InstructionsSet.class);
                instructionsSet.setValueMap(nodeMap);
                instructionsSet.setFileName(jsonFile);
                log.trace("VmInstallationHelper.prepareInstallationInstructionsForLinux: Installation instructions for LINUX: object:\n{}", instructionsSet);

                // Pretty print instructionsSet JSON
                if (log.isTraceEnabled()) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    StringWriter sw = new StringWriter();
                    try (PrintWriter writer = new PrintWriter(sw)) {
                        gson.toJson(instructionsSet, writer);
                    }
                    log.trace("VmInstallationHelper.prepareInstallationInstructionsForLinux: Installation instructions for LINUX: json:\n{}", sw.toString());
                }

                instructionsSetList.add(instructionsSet);
            }

            return instructionsSetList;
        } catch (Exception ex) {
            log.error("VmInstallationHelper.prepareInstallationInstructionsForLinux: Exception while reading Installation instructions for LINUX: ", ex);
            throw ex;
        }
    }

    private InstructionsSet _appendCopyInstructions(
            InstructionsSet instructionsSet,
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
        return _appendCopyInstructions(instructionsSet, targetFile, description, contents);
    }

    private InstructionsSet _appendCopyInstructions(
            InstructionsSet instructionsSet,
            String targetFile,
            String description,
            String contents)
    {
        instructionsSet
                .appendInstruction(Instruction.createWriteFile(targetFile, contents, false).description(description))
                .appendExec("sudo chmod u+rw,og-rwx " + targetFile);
        return instructionsSet;
    }
}
