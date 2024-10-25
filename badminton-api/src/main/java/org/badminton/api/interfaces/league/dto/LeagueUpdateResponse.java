package org.badminton.api.interfaces.league.dto;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;

public record LeagueUpdateResponse(
        @Schema(description = "경기 아이디", example = "1L")
        Long leagueId,

        @Schema(description = "경기 이름", example = "배드민턴 경기")
        String leagueName,

        @Schema(description = "경기 설명", example = "이 경기는 지역 예선 경기입니다.")
        String leagueDescription,

        @Schema(description = "경기 장소", example = "장충동 체육관")
        String leagueLocation,

        @Schema(description = "최소 티어, 예시) GOLD | SILVER | BRONZE", example = "GOLD")
        Member.MemberTier requiredTier,

        @Schema(description = "현재 경기 상태, 예시) RECRUITING | COMPLETED | CANCELED", example = "RECRUITING")
        LeagueStatus leagueStatus,

        @Schema(description = "경기 방식", example = "SINGLES")
        MatchType matchType,

        @Schema(description = "경기 시작 날짜", example = "2024-09-10T15:30:00")
        LocalDateTime leagueAt,

        @Schema(description = "모집 마감 날짜", example = "2024-09-08T23:59:59")
        LocalDateTime recruitingClosedAt,

        @Schema(description = "매칭 조건", example = "RANDOM")
        MatchGenerationType matchGenerationType,

        @Schema(description = "참가 제한 인원", example = "16")
        int playerLimitCount,

        @Schema(description = "현재까지 참여한 인원", example = "12")
        int recruitedMemberCount,

        @Schema(description = "생성 일자", example = "2024-09-10T15:30:00")
        LocalDateTime createdAt,

        @Schema(description = "수정 일자", example = "2024-09-10T15:30:00")
        LocalDateTime modifiedAt

) {

    public static LeagueUpdateResponse fromLeagueEntityAndRecruitedMemberCountAndIsParticipated(League league,
                                                                                                int recruitedMemberCount) {
        return new LeagueUpdateResponse(
                league.getLeagueId(),
                league.getLeagueName(),
                league.getDescription(),
                league.getLeagueLocation(),
                league.getRequiredTier(),
                league.getLeagueStatus(),
                league.getMatchType(),
                league.getLeagueAt(),
                league.getRecruitingClosedAt(),
                league.getMatchGenerationType(),
                league.getPlayerLimitCount(),
                recruitedMemberCount,
                league.getCreatedAt(),
                league.getModifiedAt()
        );
    }
}