package eu.paasage.upperware.security.server.data.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ValidPassword {

    String message() default "Strong password is required: min 8 signs length, min 1 uppercase letter, min 1 small letter and min 1 digit, no white signs";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
