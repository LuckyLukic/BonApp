package BonApp.BonApp.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StrongPasswordValidation.class)
public @interface StrongPassword {

	String message() default "La password non soddisfa i requisiti di sicurezza";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
