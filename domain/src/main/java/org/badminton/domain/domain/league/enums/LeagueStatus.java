package org.badminton.domain.domain.league.enums;

import lombok.Getter;

@Getter
public enum LeagueStatus {
    RECRUITING("모집 중"),
    RECRUITING_COMPLETED("모집 완료"),
    PLAYING("경기 중"),
    CANCELED("경기 취소"),
    FINISHED("경기 종료");

    private final String description;

    LeagueStatus(String description) {
        this.description = description;
    }

}
