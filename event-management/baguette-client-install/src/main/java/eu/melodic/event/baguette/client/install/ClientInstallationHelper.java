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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Baguette Client installation helper
 */
@Slf4j
public class ClientInstallationHelper implements InitializingBean {
    private static ClientInstallationHelper instance = null;

    public static final List<String> LINUX_OS_FAMILIES = Collections.unmodifiableList(Arrays.asList("CENTOS", "DARWIN", "DEBIAN", "FEDORA ", "FREEBSD ", "GENTOO", "COREOS", "AMZN_LINUX", "MANDRIVA ", "NETBSD", "OEL ", "OPENBSD", "RHEL", "SCIENTIFIC", "CEL", "SLACKWARE", "SOLARIS", "SUSE", "TURBOLINUX", "CLOUD_LINUX", "UBUNTU"));
    public static final List<String> WINDOWS_OS_FAMILIES = Collections.unmodifiableList(Arrays.asList("WINDOWS"));

    /*@Autowired
    private CloudiatorUtilProperties properties;*/

    private ClientInstallationHelper() {
        ClientInstallationHelper.instance = this;
    }

    public synchronized static ClientInstallationHelper getInstance() {
        if (instance == null) instance = new ClientInstallationHelper();
        return instance;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //log.warn("ClientInstallationHelper.afterPropertiesSet(): configuration: {}", properties);
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
        String clientConfPath = installationDir + "/conf/baguette-client.properties";
        String clientConfAppend = "\n# ++++++++++++  TODO  +++++++++++\n\n";

        OrchestrationHelper.InstallationInstructions installationInstructions = new OrchestrationHelper.InstallationInstructions();
        installationInstructions.setOs("linux");
        // Create Baguette Client installation directory
        installationInstructions.appendInstruction(OrchestrationHelper.INSTRUCTION_TYPE.LOG, "Create Baguette Client installation directory");
        installationInstructions.appendInstruction(OrchestrationHelper.INSTRUCTION_TYPE.CMD, "sudo mkdir -p "+installationDir+"/bin");
        installationInstructions.appendInstruction(OrchestrationHelper.INSTRUCTION_TYPE.CMD, "sudo mkdir -p "+installationDir+"/logs");

        // Download Baguette Client installation script
        installationInstructions.appendInstruction(OrchestrationHelper.INSTRUCTION_TYPE.LOG, "Download Baguette Client installation script");
        installationInstructions.appendInstruction(OrchestrationHelper.INSTRUCTION_TYPE.CMD, "sudo wget "+installScriptUrl+" -O "+installScriptPath);

        // Make Baguette Client installation script executable
        installationInstructions.appendInstruction(OrchestrationHelper.INSTRUCTION_TYPE.LOG, "Make Baguette Client installation script executable");
        installationInstructions.appendInstruction(OrchestrationHelper.INSTRUCTION_TYPE.CMD, "sudo chmod u+rwx,og-rwx "+installScriptPath);

        // Run Baguette Client installation script
        installationInstructions.appendInstruction(OrchestrationHelper.INSTRUCTION_TYPE.LOG, "Run Baguette Client installation script");
        installationInstructions.appendInstruction(OrchestrationHelper.INSTRUCTION_TYPE.CMD, "sudo "+installScriptPath+" "+baseDownloadUrl+" "+apiKey+" \n");

        // Add client identification and server credentials configuration
        installationInstructions.appendInstruction(OrchestrationHelper.INSTRUCTION_TYPE.LOG, "Add client identification and server credentials configuration");
        installationInstructions.appendInstruction(clientConfPath, clientConfAppend, false);

        // Launch Baguette Client
        installationInstructions.appendInstruction(OrchestrationHelper.INSTRUCTION_TYPE.LOG, "Launch Baguette Client");
        installationInstructions.appendInstruction(OrchestrationHelper.INSTRUCTION_TYPE.CMD, "sudo service baguette-client start");

        return installationInstructions;
    }
}
