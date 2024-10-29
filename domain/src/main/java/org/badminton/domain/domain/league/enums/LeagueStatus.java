package org.badminton.domain.domain.league.enums;

public enum LeagueStatus {
    RECRUITING("모집 중"),
    // TODO: 더 좋은 이름
    COMPLETED("모집 완료"),
    CANCELED("경기 취소");

    private final String description;

    LeagueStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
