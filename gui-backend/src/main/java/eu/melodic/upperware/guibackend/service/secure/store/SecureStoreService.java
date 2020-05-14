package eu.melodic.upperware.guibackend.service.secure.store;

import eu.melodic.upperware.guibackend.communication.cloudiator.CloudiatorApi;
import eu.melodic.upperware.guibackend.controller.deployment.common.SecureVariable;
import eu.melodic.upperware.guibackend.exception.SecureVariableNotFoundException;
import eu.melodic.upperware.guibackend.exception.ValidationException;
import eu.melodic.upperware.guibackend.model.byon.LoginCredential;
import eu.melodic.upperware.guibackend.model.provider.CloudDefinition;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SecureStoreService {

    private CloudiatorApi cloudiatorApi;
    private static final Pattern SECURE_VARIABLE_PATTERN = Pattern.compile("\\{\\{(.*?)}}");
    private final static String SECURE_VARIABLE_PREFIX = "{{";
    private final static String SECURE_VARIABLE_SUFFIX = "}}";

    private final static String SECURE_VARIABLE_SECURE_SUFIX = "-SECRET";
    private final static String SECURE_VARIABLE_BYON_PREFIX = "byon-";
    private final static String SECURE_VARIABLE_PASSWORD_SUFFIX = "-password";
    private final static String SECURE_VARIABLE_KEY_SUFFIX = "-key";

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
    // chars: '/' and '\' are not allowed as key of variable for Cloudiator secure store.
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
                    cloudiatorApi.storeSecureVariable(secureVariableRequest.getName(), secureVariableRequest.getValue());
                })
                .map(SecureVariable::getName)
                .collect(Collectors.toList());
    }

    public List<SecureVariable> fillSecureVariablesValues(List<String> secureVariablesKeys) {
        return secureVariablesKeys.stream()
                .map(this::fillSecureVariableOrSetEmpty)
                .collect(Collectors.toList());
    }

    private SecureVariable fillSecureVariableOrSetEmpty(String secureKey) {
        String secureValue;
        try {
            secureValue = cloudiatorApi.getSecureVariable(secureKey);
        } catch (SecureVariableNotFoundException ex) {
            secureValue = StringUtils.EMPTY;
        }
        return new SecureVariable(secureKey, secureValue);
    }

    public String getValueForSecureVariableLabel(String variableLabel) {
        Optional<String> optionalSecureVarKey = extractKeyFromSecureVariableLabel(variableLabel);
        if (optionalSecureVarKey.isPresent()) {
            return this.cloudiatorApi.getSecureVariable(optionalSecureVarKey.get());
        } else {
            return variableLabel;
        }
    }

    private Optional<String> extractKeyFromSecureVariableLabel(String secureVariableLabel) {
        List<String> secureVariables = findSecureVariables(secureVariableLabel);
        return secureVariables.size() > 0 ? Optional.of(secureVariables.get(0)) : Optional.empty();
    }

    public Pair<String, String> createKeyLabelForSecret(CloudDefinition cloudDefinition) {
        String keyForSecret = cloudDefinition.getApi().getProviderName() + "-"
                + cloudDefinition.getId()
                + SECURE_VARIABLE_SECURE_SUFIX;
        return Pair.of(keyForSecret, SECURE_VARIABLE_PREFIX + keyForSecret + SECURE_VARIABLE_SUFFIX);
    }

    // create key for secure-store and label for gui-data.yaml file
    public Pair<String, String> createKeyLabelForByonKey(LoginCredential loginCredential) {
        String keyForByonKey = SECURE_VARIABLE_BYON_PREFIX + loginCredential.getId() + SECURE_VARIABLE_KEY_SUFFIX;
        String labelForByonKey = SECURE_VARIABLE_PREFIX + keyForByonKey + SECURE_VARIABLE_SUFFIX;
        return Pair.of(keyForByonKey, labelForByonKey);
    }

    // create key for secure-store and label for gui-data.yaml file
    public Pair<String, String> createKeyLabelForByonPassword(LoginCredential loginCredential) {
        String keyForByonPassword = SECURE_VARIABLE_BYON_PREFIX + loginCredential.getId() + SECURE_VARIABLE_PASSWORD_SUFFIX;
        String labelForByonKey = SECURE_VARIABLE_PREFIX + keyForByonPassword + SECURE_VARIABLE_SUFFIX;
        return Pair.of(keyForByonPassword, labelForByonKey);
    }

    public void deleteSecureVariableByLabel(String secureVarLabel) {
        List<String> secureVariables = findSecureVariables(secureVarLabel);
        if (secureVariables.size() > 0) {
            cloudiatorApi.deleteSecureVariable(secureVariables.get(0));
        }
    }
}
