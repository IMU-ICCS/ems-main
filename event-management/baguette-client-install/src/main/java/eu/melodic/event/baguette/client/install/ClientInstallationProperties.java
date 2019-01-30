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
}
