package eu.melodic.upperware.guibackend.service.secure.store;

import eu.melodic.upperware.guibackend.communication.cloudiator.CloudiatorApi;
import eu.melodic.upperware.guibackend.controller.deployment.common.SecureVariable;
import eu.melodic.upperware.guibackend.exception.SecureVariableNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SecureStoreService {

    private CloudiatorApi cloudiatorApi;
    private static final Pattern SECURE_VARIABLE_PATTERN = Pattern.compile("\\{\\{(.*?)}}");

    public List<String> findSecureVariables(String text) {
        List<String> secureVariablesKeys = new ArrayList<>();
        Matcher matcher = SECURE_VARIABLE_PATTERN.matcher(text);
        while (matcher.find()) {
            secureVariablesKeys.add(matcher.group(1));
            log.info("Found secure variables: {}", matcher.group(1));
        }
        return secureVariablesKeys;
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
}
