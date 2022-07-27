/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.install;

import eu.melodic.event.util.Plugin;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface InstallationContextProcessorPlugin extends Plugin {
    void processBeforeInstallation(ClientInstallationTask task, long taskCounter);
    void processAfterInstallation(ClientInstallationTask task, long taskCounter, boolean success);

    default String getSettingFromConfiguration(@NonNull Map<String, String> configuration, @NonNull String key) {
        return getSettingFromConfiguration(configuration, key, null);
    }

    default String getSettingFromConfiguration(@NonNull Map<String, String> configuration, @NonNull String key, String defaultValue) {
        // Create key variations
        List<String> words = Arrays.stream(key.split("[.-_\\p{Lu}]"))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        List<String> variations = Arrays.asList(
                // All letters capital separated with underscores
                words.stream().map(String::toUpperCase).collect(Collectors.joining("_")),
                // Title case
                words.stream().map(StringUtils::capitalize).collect(Collectors.joining()),
                // Camel case
                StringUtils.uncapitalize(words.stream().map(StringUtils::capitalize).collect(Collectors.joining())),
                // All letters lower case separated with periods
                String.join(".", words),
                // All letters lower case separated with dashes
                String.join("-", words),
                // Original key
                key
        );

        // Search for value
        for (String k : variations) {
            if (configuration.containsKey(k)) {
                return configuration.get(k);
            }
        }
        return defaultValue;
    }
}
