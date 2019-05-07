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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Data
@Slf4j
public class KeystoreAndCertificateProperties implements IKeystoreAndCertificateProperties {

    //@Value("${default-ip-address:}")
    private String defaultIpAddress;
    //@Value("${public-ip-address:}")
    private String publicIpAddress;

    //@Value("${control.ssl.keystore.file:}")
    private String keystoreFile;
    //@Value("${control.ssl.keystore.type:}")
    private String keystoreType;
    //@Value("${control.ssl.keystore.password:}")
    private String keystorePassword;
    //@Value("${control.ssl.truststore.file:}")
    private String truststoreFile;
    //@Value("${control.ssl.truststore.type:}")
    private String truststoreType;
    //@Value("${control.ssl.truststore.password:}")
    private String truststorePassword;
    //@Value("${control.ssl.certificate.file:}")
    private String certificateFile;

    //@Value("${control.ssl.entry.generate:IF-IP-CHANGED}")
    private KEY_ENTRY_GENERATE keyEntryGenerate;
    //@Value("${control.ssl.entry.name:ems}")
    private String keyEntryName;
    //@Value("${control.ssl.entry.dname:CN=ems,OU=Unknown,O=Unknown,L=Unknown,ST=Unknown,C=Unknown}")
    private String keyEntryDName;
    //@Value("${control.ssl.entry.ext-san:dns:localhost,ip:127.0.0.1,ip:%{DEFAULT_IP}%,ip:%{PUBLIC_IP}%}")
    private String keyEntryExtSAN;

    public String getKeyEntryNameValue() { return prepareValue(keyEntryName, this.publicIpAddress, this.defaultIpAddress, "127.0.0.1"); }
    public String getKeyEntryDNameValue() { return prepareValue(keyEntryDName, this.publicIpAddress, this.defaultIpAddress, "127.0.0.1"); }
    public String getKeyEntryExtSANValue() { return prepareValue(keyEntryExtSAN, this.publicIpAddress, this.defaultIpAddress, "127.0.0.1"); }

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
