/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.client.install;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "baguette.client.install")
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.event.baguette-client-install.properties")
@Slf4j
public class ClientInstallationProperties {
    private final Map<String, List<String>> osFamilies = new HashMap<>();

    private String baseDir;
    private List<String> mkdirs;
    private List<String> touchFiles;
    private String checkInstalledFile;

    private String downloadUrl;
    private String apiKey;
    private String installScriptUrl;
    private String installScriptFile;

    private String credentialsTempFile;
    private String credentialsFile;
    private String clientConfigTemplateFile;
    private String clientConfigFile;

    private String archiveSourceDir;            // the directory in server that will be archived (it must contain client configuration)
    private String archiveDir;                  // the directory in server where client config. archive will be placed into
    private String archiveFile;                 // name of the client configuration archive (in server)
    private String clientConfArchiveFile;       // location in VM, where client config. archive will be stored (in BASE64 encoding)
    //private String clientConfArchiveDest;       // location in VM, where client config. archive will be extracted

    private String serverCertFileAtServer;      // location of EMS server certificate in server (in config-files)
    private String serverCertFileAtClient;      // location in VM, where EMS server certificate will be stored
    private String copyFilesFromServerDir;      // location in EMS server whose contents will be copied to VM
    private String copyFilesToClientDir;        // location in VM where server files will be copied into

    private String clientTmpDir;                // location of temp. directory in VM (typically /tmp)
}
