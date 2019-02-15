/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.client.install;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.melodic.event.baguette.server.BaguetteServer;
import eu.melodic.event.baguette.server.properties.BaguetteServerProperties;
import eu.melodic.event.baguette.server.util.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Baguette Client installation helper
 */
@Slf4j
@Service
public class ClientInstallationHelper implements InitializingBean {
    private static ClientInstallationHelper instance = null;
    private static List<String> LINUX_OS_FAMILIES;
    private static List<String> WINDOWS_OS_FAMILIES;

    @Autowired
    private ClientInstallationProperties properties;

    private ClientInstallationHelper() {
        ClientInstallationHelper.instance = this;
    }

    public synchronized static ClientInstallationHelper getInstance() {
        if (instance == null) instance = new ClientInstallationHelper();
        return instance;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("ClientInstallationHelper.afterPropertiesSet(): configuration: {}", properties);
        LINUX_OS_FAMILIES = properties.getOsFamilies().get("LINUX");
        WINDOWS_OS_FAMILIES = properties.getOsFamilies().get("WINDOWS");
    }

    private String getResourceAsString(String resourcePath) throws IOException {
        InputStream resource = new FileSystemResource(resourcePath).getInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    public OrchestrationHelper.InstallationInstructions prepareInstallationInstructionsForOs(Map<String,Object> nodeMap, String baseUrl, String clientId, BaguetteServer baguette) throws IOException {
        if (! baguette.isServerRunning()) throw new RuntimeException("Baguette Server is not running");
        log.debug("ClientInstallationHelper.prepareInstallationInstructionsForOs(): node-map={}, base-url={}, client-id={}", nodeMap, baseUrl, clientId);

        String osFamily = (String) nodeMap.get("operatingSystem");
        OrchestrationHelper.InstallationInstructions installationInstructions = null;
        if (LINUX_OS_FAMILIES.contains(osFamily.toUpperCase())) installationInstructions = prepareInstallationInstructionsForLinux(baseUrl, clientId, baguette);
        else if (WINDOWS_OS_FAMILIES.contains(osFamily.toUpperCase())) installationInstructions = prepareInstallationInstructionsForWin(baseUrl, clientId, baguette);
        else log.warn("ClientInstallationHelper.prepareInstallationInstructionsForOs(): Unsupported OS family: {}", osFamily);
        return installationInstructions;
    }

    public OrchestrationHelper.InstallationInstructions prepareInstallationInstructionsForWin(String baseUrl, String clientId, BaguetteServer baguette) {
        log.warn("ClientInstallationHelper.prepareInstallationInstructionsForWin(): NOT YET IMPLEMENTED");
        return null;
    }

    public OrchestrationHelper.InstallationInstructions prepareInstallationInstructionsForLinux(String baseUrl, String clientId, BaguetteServer baguette) throws IOException {
        log.debug("ClientInstallationHelper.prepareInstallationInstructionsForLinux(): Invoked: base-url={}", baseUrl);

        // Get parameters
        log.debug("prepareInstallationInstructionsForLinux(): properties: {}", properties);
        String checkInstallationFile = properties.getCheckInstalledFile();

        String baseDownloadUrl = _prepareUrl( properties.getDownloadUrl(), baseUrl);
        String apiKey = properties.getApiKey();
        String installScriptUrl = _prepareUrl( properties.getInstallScriptUrl(), baseUrl);
        String installScriptPath = properties.getInstallScriptFile();

        String credentialsTempFile = properties.getCredentialsTempFile();
        String credentialsFile = properties.getCredentialsFile();
        String clientConfTemplateFile = properties.getClientConfigTemplateFile();
        String clientConfFile = properties.getClientConfigFile();

        // Load client config. template and prepare configuration
        String clientConfTemplate = getResourceAsString(clientConfTemplateFile);
        Map<String,String> valueMap = new HashMap<>();
        valueMap.put("BAGUETTE_CLIENT_ID", clientId);
        valueMap.put("BAGUETTE_SERVER_ADDRESS", baguette.getConfiguration().getServerAddress());
        valueMap.put("BAGUETTE_SERVER_PORT", ""+baguette.getConfiguration().getServerPort());
        valueMap.put("BAGUETTE_SERVER_PUBKEY", baguette.getServerPubkey());
        valueMap.put("BAGUETTE_SERVER_PUBKEY_FINGERPRINT", baguette.getServerPubkeyFingerprint());
        BaguetteServerProperties.CredentialsMap.Entry<String,String> pair =
                baguette.getConfiguration().getCredentials().entrySet().iterator().next();
        valueMap.put("BAGUETTE_SERVER_USERNAME", pair.getKey());
        valueMap.put("BAGUETTE_SERVER_PASSWORD", pair.getValue());

        String clientConfAppend = StringSubstitutor.replace(clientConfTemplate, valueMap);
        log.debug("prepareInstallationInstructionsForLinux(): clientConfAppend={}", clientConfAppend);

		// Set the target operating system
        OrchestrationHelper.InstallationInstructions installationInstructions = new OrchestrationHelper.InstallationInstructions();
        installationInstructions.setOs("LINUX");

		// Check whether EMS Client is already installed
                /*.appendLog("Checking if Baguette Client is already installed")
                .appendCheck("[[ -f "+checkInstallationFile+" ]] && exit 99", 0, true, "NOTE: Baguette Client is already installed")
                .appendExec("Baguette Client is NOT installed")*/
		
		// Create Baguette Client installation directories
        installationInstructions.appendLog("Create Baguette Client installation directories");
        properties.getMkdirs().forEach(dir ->
            installationInstructions.appendExec("sudo mkdir -p "+dir)
        );

		// Create files using touch
        installationInstructions.appendLog("Touch files");
        properties.getTouchFiles().forEach(f ->
            installationInstructions.appendExec("sudo touch "+f)
        );

        // Download Baguette Client installation script
        installationInstructions
                .appendLog("Download Baguette Client installation script")
                .appendExec("sudo wget "+installScriptUrl+" -O "+installScriptPath)

        // Make Baguette Client installation script executable
                .appendLog("Make Baguette Client installation script executable")
                .appendExec("sudo chmod u+rwx,og-rwx "+installScriptPath)

        // Run Baguette Client installation script
                .appendLog("Run Baguette Client installation script")
                .appendExec("sudo "+installScriptPath+" "+baseDownloadUrl+" "+apiKey+" \n")

        // Add client identification and server credentials configuration
                .appendLog("Add client identification and server credentials configuration")
                .appendWriteFile(credentialsTempFile, clientConfAppend, false)
                .appendExec("sudo mv "+credentialsTempFile+" "+credentialsFile)
                .appendExec("sudo chmod u+rw,og-rwx "+credentialsFile)
                .appendExec("sudo -- sh -c 'cat "+credentialsFile+" >> "+clientConfFile+"' ")

        // Launch Baguette Client
                .appendLog("Launch Baguette Client")
                .appendExec("sudo service baguette-client start")

        // Write successful installation file
                .appendLog("Write successful installation file")
                .appendExec("sudo touch "+checkInstallationFile)
        ;
        //log.debug("prepareInstallationInstructionsForLinux(): installationInstructions: {}", installationInstructions);

        // Pretty print installationInstructions JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StringWriter sw = new StringWriter();
        try (PrintWriter writer = new PrintWriter(sw)) { gson.toJson(installationInstructions, writer); }
        log.debug("prepareInstallationInstructionsForLinux(): installationInstructions:\n{}", sw.toString());

        return installationInstructions;
    }

    private String _prepareUrl(String urlTemplate, String baseUrl) {
        return urlTemplate
                .replace("%{BASE_URL}%", baseUrl)
                .replace("%{PUBLIC_IP}%", NetUtil.getPublicIpAddress())
                .replace("%{DEFAULT_IP}%", NetUtil.getDefaultIpAddress());
    }
}
