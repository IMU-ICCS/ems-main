/*
 * Copyright (C) 2017-2026 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package gr.iccs.imu.ems.api;

import java.util.Optional;
import java.util.Properties;

/**
 * EMS Release info
 */
public class EmsRelease {
    public final static String EMS_ID = "ems";
    public final static String EMS_NAME = "Event Management System";
    public final static String EMS_VERSION = EmsRelease.class.getPackage().getImplementationVersion();
    public final static String EMS_BUILD = readBuildNumber();
    public final static String EMS_COPYRIGHT =
            "Copyright (C) 2017-2026 Institute of Communication and Computer Systems (imu.iccs.gr)";
    public final static String EMS_LICENSE = "Mozilla Public License, v2.0";
    public final static String EMS_DESCRIPTION = "\n%s (%s), v.%s%s, %s\n%s\n".formatted(
            EMS_NAME, EMS_ID, EMS_VERSION, EMS_BUILD, EMS_LICENSE, EMS_COPYRIGHT);

    private static String readBuildNumber() {
        try {
            Properties props = new Properties();
            props.load(EmsRelease.class.getResourceAsStream("/version.txt"));
            String buildNumber = props.getProperty("buildNumber");
            String buildTimestamp = props.getProperty("maven.build.timestamp");
            return Optional
                    .ofNullable( firstNonBlank(buildNumber, buildTimestamp) )
                    .map(s->" (build "+s+")")
                    .orElse("");
        } catch (Exception e) {
            return null;
        }
    }

    private static String firstNonBlank(String... values) {
        for (String v : values) {
            if (v!=null && ! v.isBlank())
                return v.trim();
        }
        return null;
    }
}