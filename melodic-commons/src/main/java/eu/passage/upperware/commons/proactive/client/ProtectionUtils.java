package eu.passage.upperware.commons.proactive.client;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

class ProtectionUtils {

    public final StandardPBEStringEncryptor textEncryptor = new StandardPBEStringEncryptor();

    public final String encryptorPassword;

    private boolean isSet = false;

    public ProtectionUtils(final String encryptorPassword) {
        this.encryptorPassword = encryptorPassword;
    }

    public String decrypt(String encryptedText) {
        if (!isSet) {
            textEncryptor.setPassword(encryptorPassword);
            isSet = true;
        }
        return textEncryptor.decrypt(encryptedText);
    }

    public String encrypt(String plainText) {
        if (!isSet) {
            textEncryptor.setPassword(encryptorPassword);
            isSet = true;
        }
        return textEncryptor.encrypt(plainText);
    }
}
