package org.badminton.domain.common.enums;

public enum SetStatus {
    NOT_STARTED("시작 전"),
    IN_PROGRESS("진행 중"),
    FINISHED("종료");

    private final String description;

    SetStatus(String description) {
        this.description = description;
    }
}
