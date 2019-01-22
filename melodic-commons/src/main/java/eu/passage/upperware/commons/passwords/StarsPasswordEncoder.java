package eu.passage.upperware.commons.passwords;

import org.apache.commons.lang3.StringUtils;

public class StarsPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(String password) {
        return StringUtils.isBlank(password) ? "" : StringUtils.repeat("*", password.length());
    }
}
