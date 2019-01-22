package eu.passage.upperware.commons.passwords;

public class IdentityPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(String password) {
        return password;
    }
}
