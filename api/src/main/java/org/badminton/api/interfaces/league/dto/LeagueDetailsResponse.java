package org.badminton.api.interfaces.league.dto;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;

public record LeagueDetailsResponse(
	@Schema(description = "경기 아이디", example = "1L")
	Long leagueId,

	@Schema(description = "경기 이름", example = "배드민턴 경기")
	String leagueName,

	@Schema(description = "경기 설명", example = "이 경기는 지역 예선 경기입니다.")
	String leagueDescription,

	@Schema(description = "경기 장소", example = "서울시 동대문구 장충동 체육관")
	String fullAddress,

	@Schema(description = "경기 장소 리전", example = "장충동 체육관")
	String region,

	@Schema(description = "최소 티어, 예시) GOLD | SILVER | BRONZE", example = "GOLD")
	Member.MemberTier requiredTier,

	@Schema(description = "현재 경기 상태, 예시) RECRUITING | COMPLETED | CANCELED", example = "RECRUITING")
	LeagueStatus leagueStatus,

	@Schema(description = "경기 방식", example = "SINGLES")
	MatchType matchType,

	@Schema(description = "경기 시작 날짜", example = "2024-12-10T15:30:00")
	LocalDateTime leagueAt,

	@Schema(description = "모집 마감 날짜", example = "2024-12-08T23:59:59")
	LocalDateTime recruitingClosedAt,

	@Schema(description = "매칭 조건", example = "RANDOM")
	MatchGenerationType matchGenerationType,

	@Schema(description = "참가 제한 인원", example = "16")
	int playerLimitCount,

	@Schema(description = "현재까지 참여한 인원", example = "12")
	int recruitedMemberCount,

	@Schema(description = "해당하는 경기에 참여 신청을 했는지 여부", example = "true")
	boolean isParticipatedInLeague,

	@Schema(description = "생성 일자", example = "2024-09-10T15:30:00")
	LocalDateTime createdAt,

	@Schema(description = "수정 일자", example = "2024-09-10T15:30:00")
	LocalDateTime modifiedAt,

	@Schema(description = "대진표 생성 여부", example = "true")
	boolean isMatchCreated

) {

}