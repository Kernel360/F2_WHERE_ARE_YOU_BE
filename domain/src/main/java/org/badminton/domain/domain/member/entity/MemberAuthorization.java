package org.badminton.domain.domain.member.entity;

import lombok.Getter;

@Getter
public enum MemberAuthorization {
    AUTHORIZATION_ADMIN("admin"),
    AUTHORIZATION_USER("user");

    private final String description;

    MemberAuthorization(String description) {
        this.description = description;
    }
}
