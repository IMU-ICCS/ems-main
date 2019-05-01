/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.cryptacular.util.CertUtil;
import org.cryptacular.x509.GeneralNameType;
import sun.security.tools.keytool.Main;

import java.io.FileInputStream;
import java.net.InetAddress;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.DatatypeConverter;

@Slf4j
public class KeystoreUtil {
    public final static String DEFAULT_KEY_GEN_ALGORITHM = "RSA";
    public final static int DEFAULT_KEY_SIZE = 2048;
    public final static String DEFAULT_CERT_START_DATE = "-1d";
    public final static int DEFAULT_CERT_VALIDITY = 3650;

    public final static String BEGIN_CERT = "-----BEGIN CERTIFICATE-----";
    public final static String END_CERT = "-----END CERTIFICATE-----";
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");

    private String keystoreFile;
    private String keystoreType;
    private String keystorePassword;

    public static KeystoreUtil getKeystore(String file, String type, String password) {
        return new KeystoreUtil(file, type, password);
    }

    protected KeystoreUtil(String file, String type, String password) {
        this.keystoreFile = file;
        this.keystoreType = type;
        this.keystorePassword = password;
    }

    public KeystoreUtil createKeyAndCert(String entryName, String dn, String ext) throws Exception {
        return createKeyAndCert(entryName, DEFAULT_KEY_GEN_ALGORITHM, DEFAULT_KEY_SIZE, DEFAULT_CERT_START_DATE, DEFAULT_CERT_VALIDITY, dn, ext);
    }

    public KeystoreUtil createOrReplaceKeyAndCert(String entryName, String dn, String ext) throws Exception {
        return this
                .deleteEntry(entryName)
                .createKeyAndCert(entryName, dn, ext);
    }

    public KeystoreUtil createKeyAndCert(String entryName, String keyGenAlg, int keySize, String startDate, int validity, String dn, String ext) throws Exception {
        boolean hasExt = (ext!=null && !ext.trim().isEmpty());
        if (hasExt) {
            if (ext.indexOf("%{PUBLIC_IP}%")>=0) {
                String publicIp = NetUtil.getPublicIpAddress();
                if (StringUtils.isBlank(publicIp)) publicIp = "127.0.0.1";
                ext = ext.replace("%{PUBLIC_IP}%", publicIp);
            }
            if (ext.indexOf("%{DEFAULT_IP}%")>=0) {
                String defaultIp = NetUtil.getDefaultIpAddress();
                if (StringUtils.isBlank(defaultIp)) defaultIp="127.0.0.1";
                ext = ext.replace("%{DEFAULT_IP}%", defaultIp);
            }
        }
        String args[] = {
                "-debug",
                "-genkeypair",
                "-keyalg", keyGenAlg,
                "-keysize", Integer.toString(keySize),
                "-alias", entryName,
                "-startdate", startDate,
                "-validity", Integer.toString(validity),
                "-dname", dn,
                hasExt ? "-ext" : "", hasExt ? ext : "",
                "-keystore", keystoreFile,
                "-storepass", keystorePassword,
                "-storetype", keystoreType
        };
        log.debug("KeystoreUtil: Creating entry: {}", entryName);
        execute(args);
        log.debug("KeystoreUtil: Entry created: {}", entryName);
        return this;
    }

    public KeystoreUtil createOrReplaceKeyAndCert(String entryName, String keyGenAlg, int keySize, String startDate, int validity, String dn, String ext) throws Exception {
        return this
                .deleteEntry(entryName)
                .createKeyAndCert(entryName, keyGenAlg, keySize, startDate, validity, dn, ext);
    }

    public KeystoreUtil createKeyAndCertWithSAN(String entryName, String dn) throws Exception {
        String sanExt = "SAN=dns:localhost,ip:127.0.0.1,ip:%{DEFAULT_IP}%,ip:%{PUBLIC_IP}%";
        return createKeyAndCert(entryName, dn, sanExt);
    }

    public KeystoreUtil createOrReplaceKeyAndCertWithSAN(String entryName, String dn) throws Exception {
        return this
                .deleteEntry(entryName)
                .createKeyAndCertWithSAN(entryName, dn);
    }

    public KeystoreUtil deleteEntry(String entryName) throws Exception {
        String args[] = {
                "-debug",
                "-delete",
                "-alias", entryName,
                "-keystore", keystoreFile,
                "-storepass", keystorePassword,
                "-storetype", keystoreType
        };
        try {
            log.debug("KeystoreUtil: Deleting entry: {}", entryName);
            execute(args);
            log.debug("KeystoreUtil: Entry deleted: {}", entryName);
        } catch (Exception ex) {
            log.warn("KeystoreUtil: deleteEntry: {}", ex.toString());
        }
        return this;
    }

    public KeystoreUtil exportCertToFile(String entryName, String certFile) throws Exception {
        String args[] = {
                "-debug",
                "-export",
                "-alias", entryName,
                "-file", certFile,
                "-keystore", keystoreFile,
                "-storepass", keystorePassword,
                "-storetype", keystoreType
        };
        log.debug("KeystoreUtil: Exporting certificate: {}", entryName);
        execute(args);
        log.debug("KeystoreUtil: Certificate exported: {} to {}", entryName, certFile);
        return this;
    }

    public KeystoreUtil importAndReplaceCertFromFile(String entryName, String certFile) throws Exception {
        return this
                .deleteEntry(entryName)
                .importCertFromFile(entryName, certFile);
    }

    public KeystoreUtil importCertFromFile(String entryName, String certFile) throws Exception {
        String args[] = {
                "-debug",
                "-import",
                "-noprompt",
                "-alias", entryName,
                "-file", certFile,
                "-keystore", keystoreFile,
                "-storepass", keystorePassword,
                "-storetype", keystoreType
        };
        log.debug("KeystoreUtil: Importing certificate: {} from {}", entryName, certFile);
        execute(args);
        log.debug("KeystoreUtil: Certificate imported: {}", entryName);
        return this;
    }

    public boolean containsEntry(String entryName) throws Exception {
        KeyStore keystore = KeyStore.getInstance(keystoreType);
        try (FileInputStream fis = new FileInputStream(keystoreFile)) {
            keystore.load(fis, keystorePassword.toCharArray());
        }
        return keystore.containsAlias(entryName);
    }

    public X509Certificate getEntryCertificate(String entryName) throws Exception {
        KeyStore keystore = KeyStore.getInstance(keystoreType);
        try (FileInputStream fis = new FileInputStream(keystoreFile)) {
            keystore.load(fis, keystorePassword.toCharArray());
        }
        return (X509Certificate)keystore.getCertificate(entryName);
    }

    public String getEntryCertificatePEM(String entryName) throws Exception {
        X509Certificate cert = getEntryCertificate(entryName);
        log.debug("KeystoreUtil.getEntryCertificatePEM(): X509 certificate:\n{}", cert);
        byte[] certBytes = cert.getEncoded();
        Base64.Encoder encoder = Base64.getMimeEncoder(64, LINE_SEPARATOR.getBytes());
        String certEncoded = new String(encoder.encode(certBytes));
        String certPem =
                BEGIN_CERT + LINE_SEPARATOR + certEncoded + LINE_SEPARATOR + END_CERT;
        log.trace("KeystoreUtil.getEntryCertificatePEM(): X509 certificate (PEM):\n{}", certPem);
        return certPem;
    }

    public List<String> getEntryNames(String entryName, boolean onlyIp) throws Exception {
        X509Certificate cert = getEntryCertificate(entryName);
        if (cert==null) {
            log.warn("KeystoreUtil: getEntryNames: No certificate found for {}", entryName);
            return Collections.emptyList();
        }

        List<String> names = onlyIp
                ? CertUtil.subjectNames(cert, GeneralNameType.IPAddress)
                : CertUtil.subjectNames(cert);
        return names.stream()
                .map(e -> {
                    try {
                        return e.startsWith("#") ?
                                InetAddress.getByAddress(DatatypeConverter.parseHexBinary(e.substring(1))).getHostAddress()
                                : e;
                    } catch (Exception ex) {
                        log.warn("KeystoreUtil: getEntryNames: entry={}\ncaused {}", e, ex.toString());
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

    private void execute(String cmd) throws Exception {
        String command = "-debug " + cmd;
        String args[] = command.split("\\s+");
        execute(args);
    }

    private void execute(String args[]) throws Exception {
        log.debug("KeystoreUtil: Invoking KeyTool: args: {}", Arrays.asList(args));
        long startTm = System.currentTimeMillis();
        Main.main(args);
        long endTm = System.currentTimeMillis();
        log.debug("KeystoreUtil: Invoking KeyTool: completed in {}ms", endTm-startTm);
    }
}
