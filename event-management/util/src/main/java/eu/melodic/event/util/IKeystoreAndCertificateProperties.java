/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.util;

public interface IKeystoreAndCertificateProperties {

    public enum KEY_ENTRY_GENERATE { YES, ALWAYS, NO, NEVER, IF_MISSING, IF_IP_CHANGED };

    String getDefaultIpAddress();
    String getPublicIpAddress();

    String getKeystoreFile();
    String getKeystoreType();
    String getKeystorePassword();
    String getTruststoreFile();
    String getTruststoreType();
    String getTruststorePassword();
    String getCertificateFile();

    KEY_ENTRY_GENERATE getKeyEntryGenerate();
    String getKeyEntryName();
    String getKeyEntryDName();
    String getKeyEntryExtSAN();

    public String getKeyEntryNameValue();
    public String getKeyEntryDNameValue();
    public String getKeyEntryExtSANValue();
}
