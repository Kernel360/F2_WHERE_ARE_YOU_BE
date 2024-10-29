package org.badminton.api.interfaces.league.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.badminton.api.interfaces.league.validation.validator.LeagueNameValidatorImpl;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = {LeagueNameValidatorImpl.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface LeagueNameValidator {
    String message() default "경기 이름은 공백일 수 없으며, 최소 2자 이상, 최대 20자 이하여야 합니다."; // 기본 에러 메시지

    Class<?>[] groups() default {}; // 그룹을 지정할 때 사용

    Class<? extends Payload>[] payload() default {}; // 페이로드를 전달할 때 사용
}
