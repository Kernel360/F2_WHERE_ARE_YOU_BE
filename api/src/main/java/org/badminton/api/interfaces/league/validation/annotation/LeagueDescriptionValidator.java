package org.badminton.api.interfaces.league.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.badminton.api.interfaces.league.validation.validator.LeagueDescriptionValidatorImpl;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = {LeagueDescriptionValidatorImpl.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface LeagueDescriptionValidator {
    String message() default "경기 일정 상세 설명은 공백일 수 없으며, 최소 15자 이상, 최대 1000자 이하여야 합니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
