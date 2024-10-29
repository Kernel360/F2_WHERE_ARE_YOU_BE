package org.badminton.domain.common.enums;

public enum BracketType {
    FREE("프리"),
    TOURNAMENT("토너먼트");

    BracketType(String description) {
        this.description = description;
    }

    private final String description;
}
