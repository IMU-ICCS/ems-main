/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.client.install;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public OrchestrationHelper.InstallationInstructions prepareInstallationInstructionsForOs(String osFamily, String baseUrl) {
        OrchestrationHelper.InstallationInstructions installationInstructions = null;
        if (LINUX_OS_FAMILIES.contains(osFamily.toUpperCase())) installationInstructions = prepareInstallationInstructionsForLinux(baseUrl);
        else if (WINDOWS_OS_FAMILIES.contains(osFamily.toUpperCase())) installationInstructions = prepareInstallationInstructionsForWin(baseUrl);
        else log.warn("ClientInstallationHelper.prepareInstallationInstructionsForOs(): Unsupported OS family: {}", osFamily);
        return installationInstructions;
    }

    public OrchestrationHelper.InstallationInstructions prepareInstallationInstructionsForWin(String baseUrl) {
        log.warn("ClientInstallationHelper.prepareInstallationInstructionsForWin(): NOT YET IMPLEMENTED");
        return null;
    }

    public OrchestrationHelper.InstallationInstructions prepareInstallationInstructionsForLinux(String baseUrl) {
        log.debug("ClientInstallationHelper.prepareInstallationInstructionsForLinux(): Invoked");

        String installationDir = "/opt/baguette-client";
        String baseDownloadUrl = baseUrl + "/resources";    //XXX: TODO: use value from control service settings
        String installScriptUrl = baseDownloadUrl + "/install.sh";
        String installScriptPath = installationDir + "/bin/install.sh";
        String apiKey = "1234567890";                       //XXX: TODO: use value from control service settings or generated value

        String credentialsTempFile = "/tmp/baguette-server.credentials";
        String credentialsFile = installationDir + "/conf/baguette-server.credentials";
        String clientConfAppend =
                "\n"+
                "#-----------------------------------------------------------------"+
                "\n"+
                "client.id	= ${BAGUATTE_CLIENT_ID}\n"+
                "\n"+
                "host = ${BAGUETTE_SERVER_ADDRESS}\n"+
                "port = ${BAGUETTE_SERVER_PORT}\n"+
                "pubkey = ${BAGUETTE_SERVER_PUBKEY}\n"+
                "fingerprint = ${BAGUETTE_SERVER_PUBKEY_FINGERPRINT}\n"+
                "\n"+
                "username = ${BAGUETTE_SERVER_USERNAME}\n"+
                "password = ${BAGUETTE_SERVER_PASSWORD}\n"+
                "";
        clientConfAppend = clientConfAppend
                .replace("${BAGUATTE_CLIENT_ID}", "anything")
                .replace("${BAGUETTE_SERVER_ADDRESS}", ".............")
                .replace("${BAGUETTE_SERVER_PORT}", "2222")
                .replace("${BAGUETTE_SERVER_PUBKEY}", "...................")
                .replace("${BAGUETTE_SERVER_PUBKEY_FINGERPRINT}", "...................")
                .replace("${BAGUETTE_SERVER_USERNAME}", "bb")
                .replace("${BAGUETTE_SERVER_UPASSWORD}", "yy");
		String clientConfFile = installationDir + "/conf/baguette-client.properties";
		String checkInstallationFile = installationDir + "/conf/ok.txt";

		// Set the target operating system
        OrchestrationHelper.InstallationInstructions installationInstructions = new OrchestrationHelper.InstallationInstructions();
        installationInstructions.setOs("LINUX");
        installationInstructions

		// Check whether EMS Client is already installed
                /*.appendLog("Checking if Baguette Client is already installed")
                .appendCheck("[[ -f "+checkInstallationFile+" ]] && exit 99", 0, true, "NOTE: Baguette Client is already installed")
                .appendExec("Baguette Client is NOT installed")*/
		
		// Create Baguette Client installation directory
                .appendLog("Create Baguette Client installation directory")
                .appendExec("sudo mkdir -p "+installationDir+"/bin")
                .appendExec("sudo mkdir -p "+installationDir+"/conf")
                .appendExec("sudo mkdir -p "+installationDir+"/logs")

        // Download Baguette Client installation script
                .appendLog("Download Baguette Client installation script")
                .appendExec("sudo wget "+installScriptUrl+" -O "+installScriptPath)

        // Make Baguette Client installation script executable
                .appendLog("Make Baguette Client installation script executable")
                .appendExec("sudo chmod u+rwx,og-rwx "+installScriptPath)

        // Run Baguette Client installation script
                .appendLog("Run Baguette Client installation script")
                .appendExec("sudo "+installScriptPath+" "+baseDownloadUrl+" "+apiKey+" \n")
                //.appendExec("sudo "+installationDir + "/bin/install-log.sh"+" "+baseDownloadUrl+" "+apiKey+" \n");
		
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

        return installationInstructions;
    }
}
