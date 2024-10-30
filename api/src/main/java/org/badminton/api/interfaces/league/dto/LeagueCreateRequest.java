package org.badminton.api.interfaces.league.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import org.badminton.api.interfaces.league.validation.annotation.LeagueDescriptionValidator;
import org.badminton.api.interfaces.league.validation.annotation.LeagueNameValidator;
import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueCreateRequest(

        @LeagueNameValidator
        @Schema(description = "경기 이름", example = "지역 예선 경기")
        String leagueName,

        @LeagueDescriptionValidator
        @Schema(description = "경기 설명", example = "이 경기는 지역 예선 경기입니다.")
        String description,

        @Schema(description = "경기 장소", example = "성동구 서울숲 체육센터")
        String leagueLocation,

        @Schema(description = "최소 티어", example = "BRONZE")
        Member.MemberTier tierLimit,

        @Schema(description = "경기 상태", example = "RECRUITING")
        LeagueStatus leagueStatus,

        @Schema(description = "경기 방식", example = "SINGLES")
        MatchType matchType,

        // TODO: 시간 예쁘게 만들기
        @Schema(description = "경기 시작 날짜", example = "2024-09-10T15:30:00")
        LocalDateTime leagueAt,

        @Schema(description = "모집 마감 날짜", example = "2024-09-08T23:59:59")
        LocalDateTime recruitingClosedAt,

        @Schema(description = "참가 인원", example = "16")
        int playerLimitCount,

        @Schema(description = "대진표 생성 조건", example = "FREE")
        MatchGenerationType matchGenerationType

) {

}

