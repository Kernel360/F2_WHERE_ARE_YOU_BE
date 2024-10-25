package org.badminton.api.interfaces.league.dto;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;

public record LeagueCreateResponse(
        @Schema(description = "경기 이름", example = "배드민턴 경기")
        String leagueName,

        @Schema(description = "경기 설명", example = "이 경기는 지역 예선 경기입니다.")
        String description,

        @Schema(description = "최소 티어", example = "GOLD")
        Member.MemberTier tierLimit,

        @Schema(description = "현재 경기 상태", example = "OPEN")
        LeagueStatus status,

        @Schema(description = "경기 방식", example = "SINGLE")
        MatchType matchType,

        @Schema(description = "경기 시작 날짜", example = "2024-09-10T15:30:00")
        LocalDateTime leagueAt,

        @Schema(description = "모집 마감 날짜", example = "2024-09-08T23:59:59")
        LocalDateTime closedAt,

        @Schema(description = "참가 인원", example = "16")
        int playerCount,

        @Schema(description = "생성 일자", example = "2024-09-10T15:30:00")
        LocalDateTime createdAt,

        @Schema(description = "수정 일자", example = "2024-09-10T15:30:00")
        LocalDateTime modifiedAt,

        @Schema(description = "매칭 조건", example = "TIER")
        MatchGenerationType matchGenerationType
) {

    public LeagueCreateResponse(League entity) {
        this(
                entity.getLeagueName(),
                entity.getDescription(),
                entity.getRequiredTier(),
                entity.getLeagueStatus(),
                entity.getMatchType(),
                entity.getLeagueAt(),
                entity.getRecruitingClosedAt(),
                entity.getPlayerLimitCount(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getMatchGenerationType()
        );
    }

    public static LeagueCreateResponse leagueCreateEntityToResponse(League entity) {
        return new LeagueCreateResponse(entity);
    }
}
