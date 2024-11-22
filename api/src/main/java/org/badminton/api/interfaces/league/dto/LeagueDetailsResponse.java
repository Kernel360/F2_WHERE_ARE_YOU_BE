package org.badminton.api.interfaces.league.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.time.LocalDateTime;
import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueDetailsResponse(
        @Schema(description = "경기 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
        Long leagueId,

        @Schema(description = "경기 오너 토큰", requiredMode = RequiredMode.REQUIRED)
        String leagueOwnerToken,

        @Schema(description = "경기 이름", requiredMode = Schema.RequiredMode.REQUIRED)
        String leagueName,

        @Schema(description = "경기 설명", requiredMode = Schema.RequiredMode.REQUIRED)
        String leagueDescription,

        @Schema(description = "경기 장소", requiredMode = Schema.RequiredMode.REQUIRED)
        String fullAddress,

        @Schema(description = "경기 장소 리전", requiredMode = Schema.RequiredMode.REQUIRED)
        String region,

        @Schema(description = "최소 티어, (GOLD | SILVER | BRONZE)", requiredMode = Schema.RequiredMode.REQUIRED)
        Member.MemberTier requiredTier,

        @Schema(description = "현재 경기 상태( ALL | RECRUITING | RECRUITING_COMPLETED | PLAYING | CANCELED | FINISHED)",
                requiredMode = Schema.RequiredMode.REQUIRED)
        LeagueStatus leagueStatus,

        @Schema(description = "경기 방식 (SINGLES | DOUBLES)", requiredMode = Schema.RequiredMode.REQUIRED)
        MatchType matchType,

        @Schema(description = "경기 시작 날짜", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime leagueAt,

        @Schema(description = "모집 마감 날짜", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime recruitingClosedAt,

        @Schema(description = "매칭 조건 (FREE | TOURNAMENT)", example = "TIER")
        MatchGenerationType matchGenerationType,

        @Schema(description = "참가 제한 인원", requiredMode = Schema.RequiredMode.REQUIRED)
        int playerLimitCount,

        @Schema(description = "현재까지 참여한 인원", requiredMode = Schema.RequiredMode.REQUIRED)
        int recruitedMemberCount,

        @Schema(description = "생성 일자", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime createdAt,

        @Schema(description = "수정 일자", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime modifiedAt,

        @Schema(description = "대진표 생성 여부", requiredMode = Schema.RequiredMode.REQUIRED)
        boolean isMatchCreated
) {

}