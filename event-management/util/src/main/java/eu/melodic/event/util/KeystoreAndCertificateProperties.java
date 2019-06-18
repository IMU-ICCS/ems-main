/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.util;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Data
@ToString(exclude = {"truststorePassword", "keystorePassword"})
@Slf4j
public class KeystoreAndCertificateProperties implements IKeystoreAndCertificateProperties {

    private String defaultIpAddress;
    private String publicIpAddress;

    private String keystoreFile;
    private String keystoreType;
    private String keystorePassword;

    private String truststoreFile;
    private String truststoreType;
    private String truststorePassword;

    private String certificateFile;

    private KEY_ENTRY_GENERATE keyEntryGenerate;
    private String keyEntryName;
    private String keyEntryDName;
    private String keyEntryExtSAN;

    public String getKeyEntryNameValue() { return prepareValue(keyEntryName, this.publicIpAddress, this.defaultIpAddress, "127.0.0.1"); }
    public String getKeyEntryDNameValue() { return prepareValue(keyEntryDName, this.publicIpAddress, this.defaultIpAddress, "127.0.0.1"); }
    public String getKeyEntryExtSANValue() { return prepareValue(keyEntryExtSAN, this.publicIpAddress, this.defaultIpAddress, "127.0.0.1"); }

    // ------------------------------------------------------------------------
    // Helper methods
    // ------------------------------------------------------------------------

    public static String prepareUrl(String url) {
        return prepareValue(url, "");
    }

    public static String prepareValue(String value, String defaultValue) { return prepareValue(value, null, null, ""); }

    public static String prepareValue(String value, String publicIpAddress, String defaultIpAddress, String defaultValue) {
        if (value==null) return null;
        String pubIpAddr = NetUtil.getPublicIpAddress();
        pubIpAddr = StringUtils.isNotBlank(pubIpAddr)
                ? pubIpAddr
                : StringUtils.isNotBlank(publicIpAddress) ? publicIpAddress : defaultValue;
        String defIpAddr = NetUtil.getDefaultIpAddress();
        defIpAddr = StringUtils.isNotBlank(defIpAddr)
                ? defIpAddr
                : StringUtils.isNotBlank(defaultIpAddress) ? defaultIpAddress: defaultValue;
        return value
                .replace("%{PUBLIC_IP}%", pubIpAddr)
                .replace("%{DEFAULT_IP}%", defIpAddr);
    }
}
