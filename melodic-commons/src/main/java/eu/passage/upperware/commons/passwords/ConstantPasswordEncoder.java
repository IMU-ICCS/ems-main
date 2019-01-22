package eu.passage.upperware.commons.passwords;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ConstantPasswordEncoder implements PasswordEncoder {

    private String constant = "<hidden>";

    @Override
    public String encode(String password) {
        return constant;
    }
}
