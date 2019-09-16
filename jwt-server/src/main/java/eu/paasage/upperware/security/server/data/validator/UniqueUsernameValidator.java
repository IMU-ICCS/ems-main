package eu.paasage.upperware.security.server.data.validator;

import eu.paasage.upperware.security.server.data.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private UserService userService;

    @Override
    public void initialize(UniqueUsername uniqueUsername) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return !userService.exists(username);
    }
}
