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
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.cryptacular.util.CertUtil;
import org.cryptacular.x509.GeneralNameType;

import java.io.*;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class KeystoreUtil {
    private final static String DEFAULT_KEY_GEN_ALGORITHM = "RSA";
    private final static String DEFAULT_SIGNATURE_ALGORITHM = "SHA256WithRSA";
    private final static int DEFAULT_KEY_SIZE = 2048;
    private final static int DEFAULT_CERT_START_DATE_OFFSET = -1;
    private final static int DEFAULT_CERT_END_DATE_OFFSET = 3650;

    private final static String BEGIN_CERT = "-----BEGIN CERTIFICATE-----";
    private final static String END_CERT = "-----END CERTIFICATE-----";
    private final static String LINE_SEPARATOR = System.getProperty("line.separator");

    private static boolean bcProviderInitialized = false;

    private String keystoreFile;
    private String keystoreType;
    private String keystorePassword;

    // KeystoreUtil instance methods
    public static KeystoreUtil getKeystore(String file, String type, String password) {
        return new KeystoreUtil(file, type, password);
    }

    private KeystoreUtil(String file, String type, String password) {
        this.keystoreFile = file;
        this.keystoreType = type;
        this.keystorePassword = password;
    }

    // Creates a new keystore file if not already exists
    public KeystoreUtil createIfNotExist() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        File f = new File(keystoreFile);
        if (! f.exists()) {
            log.debug("KeystoreUtil: Keystore file not found: {}", keystoreFile);
            KeyStore keystore = KeyStore.getInstance(keystoreType);
            keystore.load(null, keystorePassword.toCharArray());
            writeKeystore(keystore);
            log.debug("KeystoreUtil: Keystore file created: {}", keystoreFile);
        } else {
            log.debug("KeystoreUtil: Keystore file exists: {}", keystoreFile);
        }
        return this;
    }

    public boolean checkIfExist() {
        File f = new File(keystoreFile);
        return f.exists();
    }

    // Create/Replace Key pair and Certificate methods
    // If keystore file does not exist it will be created
    public KeystoreUtil createKeyAndCert(String entryName, String dn, String ext) throws Exception {
        return createKeyAndCert(entryName, DEFAULT_KEY_GEN_ALGORITHM, DEFAULT_KEY_SIZE, DEFAULT_SIGNATURE_ALGORITHM, DEFAULT_CERT_START_DATE_OFFSET, DEFAULT_CERT_END_DATE_OFFSET, dn, ext);
    }

    public KeystoreUtil createOrReplaceKeyAndCert(String entryName, String dn, String ext) throws Exception {
        return this
                .deleteEntry(entryName)
                .createKeyAndCert(entryName, dn, ext);
    }

    public KeystoreUtil createKeyAndCert(String entryName, String keyGenAlg, int keySize, String sigAlg, int startDateOffset, int endDateOffset, String dn, String extSAN) throws Exception {
        // Replace PUBLIC_IP and DEFAULT_IP placeholders with actual values
        dn = _processPlaceholders(dn, "127.0.0.1");
        extSAN = _processPlaceholders(extSAN, "127.0.0.1");
        boolean hasExt = StringUtils.isNotBlank(extSAN);

        // Read keystore from file or create it
        log.trace("KeystoreUtil: Reading keystore from file: {}", keystoreFile);
        KeyStore keystore = null;
        try {
            keystore = readKeystore();
            log.debug("KeystoreUtil: Keystore loaded from file: {}", keystoreFile);
        } catch (FileNotFoundException e) {
            log.info("KeystoreUtil: Keystore file will be created: {}", keystoreFile);
            //keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore = KeyStore.getInstance(keystoreType);
            keystore.load(null, keystorePassword.toCharArray());
        }

        // Generate new key pair
        log.trace("KeystoreUtil: Creating entry: {}", entryName);
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(keyGenAlg);
        keyPairGen.initialize(keySize);
        KeyPair keyPair = keyPairGen.generateKeyPair();

        // Compute validity period of certificate (will be generated next)
        long now = System.currentTimeMillis();
        Date dtNow = new Date(now);
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(dtNow);
        calendar.add(Calendar.DATE, startDateOffset);
        Date validFrom = calendar.getTime();

        calendar.setTime(dtNow);
        calendar.add(Calendar.DATE, endDateOffset);
        Date validTo = calendar.getTime();

        // Register Bouncy-Castle provider (if not already)
        if (!bcProviderInitialized) {
            bcProviderInitialized = true;
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        }

        // Generate new certificate for key pair
        X500Name subjectName = new X500Name(dn);
        X500Name issuerName = subjectName;
        BigInteger serialNumber = new BigInteger(Long.toString(now));
        X509v3CertificateBuilder certBuilder = new X509v3CertificateBuilder(
                issuerName, serialNumber, validFrom, validTo, subjectName,
                SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded())
        );

        // Add certificate extensions
        JcaX509ExtensionUtils jcaExtUtils = new JcaX509ExtensionUtils();
        //X509Certificate caCert = null;
        //certBuilder.addExtension(Extension.authorityKeyIdentifier, false,
        //        jcaExtUtils.createAuthorityKeyIdentifier(caCert));
        certBuilder.addExtension(Extension.subjectKeyIdentifier, false,
                jcaExtUtils.createSubjectKeyIdentifier(keyPair.getPublic()));
        if (hasExt) {
            extSAN = extSAN.replaceAll("[ \t\r\n]]+","");
            String[] names = extSAN.split(",");
            List<GeneralName> altNames = new ArrayList<>();
            for (String name : names) {
                if (StringUtils.startsWithIgnoreCase(name, "dns:")) {
                    altNames.add(new GeneralName(GeneralName.dNSName, name.substring("dns:".length())));
                } else
                if (StringUtils.startsWithIgnoreCase(name, "ip:")) {
                    altNames.add(new GeneralName(GeneralName.iPAddress, name.substring("ip:".length())));
                } else
                    log.warn("KeystoreUtil: Ignoring element of Subject Alt. Names: {}", name);
            }
            GeneralNames subjectAltName = new GeneralNames(altNames.toArray(new GeneralName[altNames.size()]));
            certBuilder.addExtension(Extension.subjectAlternativeName, false, subjectAltName);
        }

        // Self-Sign and get certificate
        JcaContentSignerBuilder builder = new JcaContentSignerBuilder(sigAlg);
        ContentSigner signer = builder.build(keyPair.getPrivate());

        byte[] certBytes = certBuilder.build(signer).getEncoded();
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        X509Certificate certificate = (X509Certificate)certificateFactory.generateCertificate(new ByteArrayInputStream(certBytes));

        // Add/Replace key pair and certificate chain to keystore
        PrivateKey newKey = keyPair.getPrivate();
        Certificate[] chain = new Certificate[] { certificate };
        String entryPassword = keystorePassword;
        keystore.setKeyEntry(entryName, newKey, entryPassword.toCharArray(), chain);
        log.debug("KeystoreUtil: Entry created: {}", entryName);

        // Store keystore back to file
        log.trace("KeystoreUtil: Writing keystore to file: {}", keystoreFile);
        writeKeystore(keystore);
        log.debug("KeystoreUtil: Keystore stored to file: {}", keystoreFile);

        return this;
    }

    // Replace PUBLIC_IP and DEFAULT_IP placeholders with actual values
    private String _processPlaceholders(String s, String defaultValue) {
        if (s==null) return null;
        if (s.contains("%{PUBLIC_IP}%")) {
            String publicIp = NetUtil.getPublicIpAddress();
            if (StringUtils.isBlank(publicIp)) publicIp = defaultValue;
            s = s.replace("%{PUBLIC_IP}%", publicIp);
        }
        if (s.contains("%{DEFAULT_IP}%")) {
            String defaultIp = NetUtil.getDefaultIpAddress();
            if (StringUtils.isBlank(defaultIp)) defaultIp=defaultValue;
            s = s.replace("%{DEFAULT_IP}%", defaultIp);
        }
        return s;
    }

    public KeystoreUtil createOrReplaceKeyAndCert(String entryName, String keyGenAlg, int keySize, String sigAlg, int startDateOffset, int endDateOffset, String dn, String ext) throws Exception {
        return this
                .deleteEntry(entryName)
                .createKeyAndCert(entryName, keyGenAlg, keySize, sigAlg, startDateOffset, endDateOffset, dn, ext);
    }

    public KeystoreUtil createKeyAndCertWithSAN(String entryName, String dn) throws Exception {
        String sanExt = "dns:localhost,ip:127.0.0.1,ip:%{DEFAULT_IP}%,ip:%{PUBLIC_IP}%";
        return createKeyAndCert(entryName, dn, sanExt);
    }

    public KeystoreUtil createOrReplaceKeyAndCertWithSAN(String entryName, String dn) throws Exception {
        return this
                .deleteEntry(entryName)
                .createKeyAndCertWithSAN(entryName, dn);
    }

    // Delete key pair and/or certificate from keystore
    public KeystoreUtil deleteEntry(String entryName) throws Exception {
        try {
            log.trace("KeystoreUtil: Deleting entry from keystore: alias={}, file={}", entryName, keystoreFile);
            KeyStore keystore = readKeystore();
            keystore.deleteEntry(entryName);
            writeKeystore(keystore);
            log.debug("KeystoreUtil: Entry deleted from keystore: alias={}, file={}", entryName, keystoreFile);
        } catch (FileNotFoundException e) {
            log.debug("KeystoreUtil: Keystore file not exists: {}", keystoreFile);
        }
        return this;
    }

    // Query if alias exists in keystore
    public boolean containsEntry(String entryName) throws Exception {
        KeyStore keystore = readKeystore(keystoreFile, keystoreType, keystorePassword);
        return keystore.containsAlias(entryName);
    }

    // Certificate import/export methods
    public KeystoreUtil importAndReplaceCertFromFile(String entryName, String certFile) throws Exception {
        return this
                .deleteEntry(entryName)
                .importCertFromFile(entryName, certFile);
    }

    public KeystoreUtil importCertFromFile(String entryName, String certFile) throws Exception {
        log.debug("KeystoreUtil: Reading certificate from file: {}", certFile);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate cert = cf.generateCertificate(new FileInputStream(certFile));
        log.trace("KeystoreUtil: Certificate: {}", cert);

        log.trace("KeystoreUtil: Importing certificate to keystore file: alias={}, file={}", entryName, keystoreFile);
        KeyStore keystore = readKeystore(keystoreFile, keystoreType, keystorePassword);
        keystore.setCertificateEntry(entryName, cert);
        writeKeystore(keystore, keystoreFile, keystoreType, keystorePassword);
        log.debug("KeystoreUtil: Certificate imported into keystore: alias={}, file={}", entryName, keystoreFile);

        return this;
    }

    public KeystoreUtil exportCertToFile(String entryName, String certFile) throws Exception {
        log.debug("KeystoreUtil: Reading certificate from keystore: alias={}, keystore={}", entryName, keystoreFile);
        String certPem = getEntryCertificateAsPEM(entryName);
        log.trace("KeystoreUtil: Certificate (PEM):\n{}", certPem);

        log.trace("KeystoreUtil: Storing certificate to file: {}", certFile);
        try (PrintStream ps = new PrintStream(new FileOutputStream(certFile))) {
            ps.print(certPem);
            ps.flush();
        }
        log.debug("KeystoreUtil: Certificate stored in file: {}", certFile);
        return this;
    }

    // Certificate retrieval methods
    public X509Certificate getEntryCertificate(String entryName) throws Exception {
        log.trace("KeystoreUtil.getEntryCertificate(): keystore: file={}, type={}, password={}",
                keystoreFile, keystoreType, keystorePassword);
        KeyStore keystore = readKeystore(keystoreFile, keystoreType, keystorePassword);
        log.trace("KeystoreUtil.getEntryCertificate(): keystore: {}", keystore);
        log.trace("KeystoreUtil.getEntryCertificate(): entry-name: {}", entryName);
        return (X509Certificate) keystore.getCertificate(entryName);
    }

    public String getEntryCertificateAsPEM(String entryName) throws Exception {
        X509Certificate cert = getEntryCertificate(entryName);
        log.trace("KeystoreUtil.getEntryCertificatePEM(): X509 certificate:\n{}", cert);
        byte[] certBytes = cert.getEncoded();
        Base64.Encoder encoder = Base64.getMimeEncoder(64, LINE_SEPARATOR.getBytes());
        String certEncoded = new String(encoder.encode(certBytes));
        String certPem =
                BEGIN_CERT + LINE_SEPARATOR + certEncoded + LINE_SEPARATOR + END_CERT;
        log.trace("KeystoreUtil.getEntryCertificatePEM(): X509 certificate (PEM):\n{}", certPem);
        return certPem;
    }

    public byte[] getEntryCertificateAsDER(String entryName) throws Exception {
        X509Certificate cert = getEntryCertificate(entryName);
        log.trace("KeystoreUtil.getEntryCertificatePEM(): X509 certificate:\n{}", cert);
        byte[] certBytes = cert.getEncoded();
        //if (log.isTraceEnabled()) {
            log.debug("KeystoreUtil.getEntryCertificatePEM(): X509 certificate (DER):\n{}", certBytes);
        //}
        return certBytes;
    }

    // Certificate names methods
    public List<String> getEntryNames(String entryName, boolean onlyIp) throws Exception {
        X509Certificate cert = getEntryCertificate(entryName);
        if (cert==null) {
            log.debug("KeystoreUtil.getEntryNames(): No certificate found for {}", entryName);
            return Collections.emptyList();
        }

        List<String> names = onlyIp
                ? CertUtil.subjectNames(cert, GeneralNameType.IPAddress)
                : CertUtil.subjectNames(cert);
        return names.stream()
                .map(sanName -> {
                    try {
                        return sanName.startsWith("#") ?
                                InetAddress.getByAddress(parseHexToBinary(sanName.substring(1))).getHostAddress()
                                : sanName;
                    } catch (Exception ex) {
                        log.warn("KeystoreUtil: getEntryNames: entry={} caused {}", sanName, ex.toString());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private byte[] parseHexToBinary(String hexValue) {
        byte[] ip = new byte[hexValue.length()/2];
        for(int i = 0, j = 0; i < hexValue.length(); i = i + 2) {
            ip[j++] = (byte)Integer.parseInt(hexValue.substring(i, i+2), 16);
        }
        if (log.isTraceEnabled()) log.trace("KeystoreUtil.parseHexBinary(): hex={}, ip={}", hexValue, Arrays.toString(ip));
        return ip;
    }

    // Certificate listing methods
    public List<String> getCertificateAliases() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        KeyStore ks = KeystoreUtil.readKeystore(keystoreFile, keystoreType, keystorePassword);
        return getCertificateAliases(ks);
    }

    public static List<String> getCertificateAliases(KeyStore ks) throws KeyStoreException {
        List<String> certAliases = new ArrayList<>();
        Enumeration<String> en = ks.aliases();
        while (en.hasMoreElements()) {
            String alias = en.nextElement();
            log.trace("KeystoreUtil.getCertificateAliases(): Checking alias: {}", alias);
            if (ks.isCertificateEntry(alias)) {
                certAliases.add(alias);
                log.trace("KeystoreUtil.getCertificateAliases(): Alias added in results: {}", alias);
            }
        }
        log.trace("KeystoreUtil.getCertificateAliases(): Certificate aliases: {}", certAliases);
        return certAliases;
    }

    // Keystore read/write methods
    public KeyStore readKeystore() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        return KeystoreUtil.readKeystore(keystoreFile, keystoreType, keystorePassword);
    }

    public KeystoreUtil writeKeystore(KeyStore keystore) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        KeystoreUtil.writeKeystore(keystore, keystoreFile, keystoreType, keystorePassword);
        return this;
    }

    public static KeyStore readKeystore(String file, String type, String password) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        KeyStore keystore = KeyStore.getInstance(type);
        try (FileInputStream fis = new FileInputStream(file)) {
            keystore.load(fis, password.toCharArray());
        }
        return keystore;
    }

    public static void writeKeystore(KeyStore keystore, String file, String type, String password) throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            keystore.store(fos, password.toCharArray());
        }
    }

    // Keystore, Trust store and Certificate initialization based on a properties source
    public static void initializeKeystoresAndCertificate(IKeystoreAndCertificateProperties properties, PasswordUtil passwordUtil) throws Exception {
        String keystorePassword = properties.getKeystorePassword();
        String truststorePassword = properties.getTruststorePassword();
        if (passwordUtil!=null) {
            keystorePassword = passwordUtil.encodePassword(keystorePassword);
            truststorePassword = passwordUtil.encodePassword(truststorePassword);
        }
        log.info("KeystoreUtil.initializeKeystoresAndCertificate(): Initializing keystores and certificate");
        log.debug("KeystoreUtil.initializeKeystoresAndCertificate(): Key pair and Certificate settings:");
        log.debug("    Keystore file: {}", properties.getKeystoreFile());
        log.debug("    Keystore type: {}", properties.getKeystoreType());
        log.debug("    Keystore password: {}", keystorePassword);
        log.debug("    Trust store file: {}", properties.getTruststoreFile());
        log.debug("    Trust store type: {}", properties.getTruststoreType());
        log.debug("    Trust store password: {}", truststorePassword);
        log.debug("    Certificate file: {}", properties.getCertificateFile());
        log.debug("    Entry name:  {}", properties.getKeyEntryNameValue());
        log.debug("    Entry DName: {}", properties.getKeyEntryDNameValue());
        log.debug("    Entry SAN:   {}", properties.getKeyEntryExtSANValue());
        log.debug("    Entry Gen.:  {}", properties.getKeyEntryGenerate());

        IKeystoreAndCertificateProperties.KEY_ENTRY_GENERATE keyGen = properties.getKeyEntryGenerate();
        boolean gen = (keyGen==IKeystoreAndCertificateProperties.KEY_ENTRY_GENERATE.YES || keyGen==IKeystoreAndCertificateProperties.KEY_ENTRY_GENERATE.ALWAYS);

        // Check if key entry is missing
        if (keyGen==IKeystoreAndCertificateProperties.KEY_ENTRY_GENERATE.IF_MISSING) {
            // Check if keystore and truststore files exist (and create if they don't)
            KeystoreUtil
                    .getKeystore(properties.getKeystoreFile(), properties.getKeystoreType(), properties.getKeystorePassword())
                    .createIfNotExist();
            KeystoreUtil
                    .getKeystore(properties.getTruststoreFile(), properties.getTruststoreType(), properties.getTruststorePassword())
                    .createIfNotExist();

            // Check if entry with given 'alias' already exists in keystore
            boolean containsEntry = KeystoreUtil
                    .getKeystore(properties.getKeystoreFile(), properties.getKeystoreType(), properties.getKeystorePassword())
                    .containsEntry(properties.getKeyEntryNameValue());
            if (containsEntry) {
                log.debug("    Keystore already contains entry: {}", properties.getKeyEntryNameValue());
            } else {
                log.debug("    Keystore does not contain entry: {}", properties.getKeyEntryNameValue());
                gen = true;
            }
        }

        // Check if IP address is in subject CN or SAN list
        if (keyGen==IKeystoreAndCertificateProperties.KEY_ENTRY_GENERATE.IF_IP_CHANGED) {
            // Check if keystore and truststore files exist (and create if they don't)
            KeystoreUtil
                    .getKeystore(properties.getKeystoreFile(), properties.getKeystoreType(), properties.getKeystorePassword())
                    .createIfNotExist();
            KeystoreUtil
                    .getKeystore(properties.getTruststoreFile(), properties.getTruststoreType(), properties.getTruststorePassword())
                    .createIfNotExist();

            // get subject CN and SAN list (IP's only)
            List<String> addrList = KeystoreUtil
                    .getKeystore(properties.getKeystoreFile(), properties.getKeystoreType(), properties.getKeystorePassword())
                    .getEntryNames(properties.getKeyEntryNameValue(), true);
            log.debug("    Entry addresses: {}", addrList);

            // get current Default and Public IP addresses
            String defaultIp = NetUtil.getDefaultIpAddress();
            String publicIp = NetUtil.getPublicIpAddress();

            // check if Default and Public IP addresses are contained in 'addrList'
            boolean defaultFound = addrList.stream().anyMatch(s -> s.equals(defaultIp));
            boolean publicFound = addrList.stream().anyMatch(s -> s.equals(publicIp));
            gen = !defaultFound || !publicFound;
            log.debug("    Address has changed: {}  (default-ip-found={}, public-ip-found={})",
                    gen, defaultFound, publicFound);
        }

        // Generate new key pair and certificate, and update keystore and trust store
        if (gen) {
            log.debug("    Generating new Key pair and Certificate for: {}", properties.getKeyEntryNameValue());

            KeystoreUtil ksUtil = KeystoreUtil
                    .getKeystore(properties.getKeystoreFile(), properties.getKeystoreType(), properties.getKeystorePassword())
                    .createIfNotExist();
            if (StringUtils.isBlank(properties.getKeyEntryExtSANValue())) {
                log.debug("    Create/Replace entry (with SAN auto-generate): {}", properties.getKeyEntryNameValue());
                ksUtil.createOrReplaceKeyAndCertWithSAN(properties.getKeyEntryNameValue(), properties.getKeyEntryDNameValue());
            } else {
                log.debug("    Create/Replace entry and SAN: entry={}, san={}",
                        properties.getKeyEntryNameValue(), properties.getKeyEntryExtSANValue());
                String extSAN = properties.getKeyEntryExtSANValue().trim();
                ksUtil.createOrReplaceKeyAndCert(properties.getKeyEntryNameValue(), properties.getKeyEntryDNameValue(), extSAN);
            }
            log.debug("    Exporting certificate to: {}", properties.getCertificateFile());
            ksUtil.exportCertToFile(properties.getKeyEntryNameValue(), properties.getCertificateFile());

            KeystoreUtil tsUtil = KeystoreUtil
                    .getKeystore(properties.getTruststoreFile(), properties.getTruststoreType(), properties.getTruststorePassword())
                    .createIfNotExist();
            log.debug("    Importing certificate to trust store: {}", properties.getTruststoreFile());
            tsUtil.importAndReplaceCertFromFile(properties.getKeyEntryNameValue(), properties.getCertificateFile());

            log.debug("    Key pair and Certificate generation completed");
        } else {
            log.debug("    Key pair and Certificate will not be re-generated");
        }

        // Log PEM certificate
        if (log.isDebugEnabled()) {
            String certPemStr = KeystoreUtil
                    .getKeystore(properties.getKeystoreFile(), properties.getKeystoreType(), properties.getKeystorePassword())
                    .getEntryCertificateAsPEM(properties.getKeyEntryNameValue());
            log.debug("    Certificate (PEM):\n{}", certPemStr);
        }
    }
}
