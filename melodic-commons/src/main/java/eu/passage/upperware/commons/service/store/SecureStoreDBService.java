package eu.passage.upperware.commons.service.store;

import eu.passage.upperware.commons.exception.ValidationException;
import eu.passage.upperware.commons.model.SecureVariable;
import eu.passage.upperware.commons.model.byon.LoginCredential;
import eu.passage.upperware.commons.model.provider.CloudDefinition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.jasypt.salt.RandomSaltGenerator;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class SecureStoreDBService {

    private final static String SECURE_VARIABLE_PREFIX = "{{";
    private final static String SECURE_VARIABLE_SUFFIX = "}}";
    private final static String SECURE_VARIABLE_SECURE_SUFFIX = "-SECRET";
    private final static String SECURE_VARIABLE_PASSWORD_SUFFIX = "-password";
    private final static String SECURE_VARIABLE_BYON_PREFIX = "byon-";
    private final static String SECURE_VARIABLE_KEY_SUFFIX = "-key";

    private static final Pattern SECURE_VARIABLE_PATTERN = Pattern.compile("\\{\\{(.*?)}}");

    private final JdbcTemplate jdbcTemplate;

    private final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
    private final String ENCRYPTOR_ALGORITHM = "PBEWithHMACSHA512AndAES_256";

    public SecureStoreDBService(final String url, final String username, final String password, final String secureStorePassword) {
        log.info("Creating SecureStoreDBService service with: url={}, username={}, password={}, secureStorePassword={}", url, username, maskSensitiveText(password), maskSensitiveText(secureStorePassword));
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.mariadb.jdbc.Driver");
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);

        jdbcTemplate = new JdbcTemplate(dataSourceBuilder.build());

        encryptor.setPassword(secureStorePassword);
        encryptor.setAlgorithm(ENCRYPTOR_ALGORITHM);
        encryptor.setIvGenerator(new RandomIvGenerator());
        encryptor.setSaltGenerator(new RandomSaltGenerator());
        log.info("SecureStoreDBService encryptor created with algorithm={}, random Iv generator and random Salt generator", ENCRYPTOR_ALGORITHM);
    }

    public String maskSensitiveText(String sensitiveText) {
        return sensitiveText.substring(0, (int) (0.25 * sensitiveText.length())) + "***";
    }

    public Pair<String, String> createKeyLabelForSecret(CloudDefinition cloudDefinition) {
        String keyForSecret = cloudDefinition.getApi().getProviderName() + "-"
                + cloudDefinition.getId()
                + SECURE_VARIABLE_SECURE_SUFFIX;
        return Pair.of(keyForSecret, SECURE_VARIABLE_PREFIX + keyForSecret + SECURE_VARIABLE_SUFFIX);
    }

    public void storeSecureVariable(String sKey, String sValue) {
        log.info("Storing: sKey={}, sValue={}", sKey, maskSensitiveText(sValue));

        int affected = jdbcTemplate.update("insert into secure_kv (skey, svalue) values (?, ?)", sKey, encryptor.encrypt(sValue));

        log.info("Storing: sKey={}, affected={}", sKey, affected);
    }

    public void deleteSecureVariable(String sKey) {
        log.info("Deleting: sKey={}", sKey);

        int affected = jdbcTemplate.update("delete from secure_kv where skey=?", sKey);

        log.info("Deleting: sKey={}, affected={}", sKey, affected);
    }

    public List<String> findSecureVariables(String text) {
        List<String> secureVariablesKeys = new ArrayList<>();
        Matcher matcher = SECURE_VARIABLE_PATTERN.matcher(text);
        while (matcher.find()) {
            secureVariablesKeys.add(matcher.group(1));
            log.info("Found secure variables: {}", matcher.group(1));
        }
        return secureVariablesKeys;
    }

    // This method checks correctness of secure variable names,
    // chars: '/' and '\' are not allowed as key of variable for secure store.
    public void validateSecureVariables(List<SecureVariable> secureVariablesRequest) {
        String invalidVariables = secureVariablesRequest.stream()
                .filter(secureVariable -> secureVariable.getName().contains("/") || secureVariable.getName().contains("\\"))
                .map(SecureVariable::getName)
                .collect(Collectors.joining(", "));
        if (!invalidVariables.isEmpty()) {
            throw new ValidationException(String.format("Variables: [ %s ] contain not allowed chars: '\\' or '/'", invalidVariables));
        }
    }

    public List<String> saveSecureVariables(List<SecureVariable> secureVariablesRequest) {
        return secureVariablesRequest
                .stream()
                .peek(secureVariableRequest -> {
                    log.info("Saving secure variable with key: {}", secureVariableRequest.getName());
                    this.storeSecureVariable(secureVariableRequest.getName(), secureVariableRequest.getValue());
                })
                .map(SecureVariable::getName)
                .collect(Collectors.toList());
    }

    public String getValueForSecureVariableLabel(String variableLabel) {
        Optional<String> optionalSecureVarKey = extractKeyFromSecureVariableLabel(variableLabel);
        if (optionalSecureVarKey.isPresent()) {
            return this.getSecureVariable(optionalSecureVarKey.get());
        } else {
            return variableLabel;
        }
    }

    private Optional<String> extractKeyFromSecureVariableLabel(String secureVariableLabel) {
        List<String> secureVariables = findSecureVariables(secureVariableLabel);
        return secureVariables.size() > 0 ? Optional.of(secureVariables.get(0)) : Optional.empty();
    }

    public String getSecureVariable(String sKey) {
        log.info("Getting: sKey={}", sKey);

        try {
            String sValue = jdbcTemplate.queryForObject("select svalue from secure_kv where skey=?", String.class, sKey);
            Optional<String> decryptedSValue = Optional.ofNullable(encryptor.decrypt(sValue));
            if(decryptedSValue.isPresent()) {
                log.info("Getting: decryptedSValue={} for sKey={}", maskSensitiveText(decryptedSValue.get()), sKey);
                return decryptedSValue.get();
            } else {
                log.info("Getting: null result for sKey={}, returning \"<-null-result->\"", sKey);
                return "<-null-result->";
            }
        } catch (EmptyResultDataAccessException e) {
            log.info("Getting: error - could not find sValue for sKey={} in the database, returning \"<-empty-result->\"", sKey);
            return "<-empty-result->";
        }
    }

    // create key for secure-store and label for gui-data.yaml file
    public Pair<String, String> createKeyLabelForByonPassword(LoginCredential loginCredential) {
        String keyForByonPassword = SECURE_VARIABLE_BYON_PREFIX + loginCredential.getId() + SECURE_VARIABLE_PASSWORD_SUFFIX;
        String labelForByonKey = SECURE_VARIABLE_PREFIX + keyForByonPassword + SECURE_VARIABLE_SUFFIX;
        return Pair.of(keyForByonPassword, labelForByonKey);
    }

    // create key for secure-store and label for gui-data.yaml file
    public Pair<String, String> createKeyLabelForByonKey(LoginCredential loginCredential) {
        String keyForByonKey = SECURE_VARIABLE_BYON_PREFIX + loginCredential.getId() + SECURE_VARIABLE_KEY_SUFFIX;
        String labelForByonKey = SECURE_VARIABLE_PREFIX + keyForByonKey + SECURE_VARIABLE_SUFFIX;
        return Pair.of(keyForByonKey, labelForByonKey);
    }

    public void deleteSecureVariableByLabel(String secureVarLabel) {
        List<String> secureVariables = findSecureVariables(secureVarLabel);
        if (secureVariables.size() > 0) {
            this.deleteSecureVariable(secureVariables.get(0));
        }
    }

    public List<SecureVariable> fillSecureVariablesValues(List<String> secureVariablesKeys) {
        return secureVariablesKeys.stream()
                .map(this::fillSecureVariableOrSetEmpty)
                .collect(Collectors.toList());
    }

    private SecureVariable fillSecureVariableOrSetEmpty(String secureKey) {
        return new SecureVariable(secureKey, this.getSecureVariable(secureKey));
    }
}
