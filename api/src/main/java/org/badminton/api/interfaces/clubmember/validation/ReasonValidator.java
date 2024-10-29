package org.badminton.api.interfaces.clubmember.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ReasonValidatorImpl.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReasonValidator {

    String message() default "회원 제제 사유는 2자 이상 100자 이하여야합니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
