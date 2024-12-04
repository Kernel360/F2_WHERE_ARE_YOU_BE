package org.badminton.api.interfaces.league.dto;

import java.time.LocalDateTime;

import org.badminton.api.common.exception.league.RecruitingClosedAtAfterLeagueAtException;
import org.badminton.domain.common.consts.Constants;
import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.exception.league.InvalidDoublesPlayerLimitCountException;
import org.badminton.domain.domain.member.entity.Member;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LeagueCreateRequest(

	@NotBlank
	@Size(min = Constants.NAME_MIN, max = Constants.NAME_MAX)
	@Schema(description = "경기 이름", example = "지역 예선 경기")
	String leagueName,

	@NotBlank
	@Length(min = Constants.DESCRIPTION_MIN, max = Constants.DESCRIPTION_MAX)
	@Schema(description = "경기 설명", example = "이 경기는 지역 예선 경기입니다.")
	String description,

	@NotBlank
	@Size(min = Constants.ADDRESS_MIN, max = Constants.ADDRESS_MAX)
	@Schema(description = "경기 장소", example = "서울시 성동구 서울숲 체육센터")
	String fullAddress,

	@NotNull
	@Schema(description = "최소 티어", example = "BRONZE")
	Member.MemberTier tierLimit,

	@NotNull
	@Schema(description = "경기 방식", example = "SINGLES")
	MatchType matchType,

	@NotNull
	@Future(message = "경기 시작 날짜는 현재 시간보다 뒤에 설정되어야 합니다.")
	@Schema(description = "경기 시작 날짜, 모집 마감 날짜는 현재 시간보다 뒤에 설정되어야 합니다.", example = "2026-11-10T15:30:00")
	LocalDateTime leagueAt,

	@NotNull
	@Future(message = "모집 마감 날짜는 현재 시간보다 뒤에 설정되어야 합니다.")
	@Schema(description = "모집 마감 날짜, 모집 마감 날짜는 현재 시간보다 뒤에 설정되어야 합니다.", example = "2026-10-11T23:59:59")
	LocalDateTime recruitingClosedAt,

	@NotNull
	@Min(Constants.PLAYER_LIMIT_MIN)
	@Max(Constants.PLAYER_LIMIT_MAX)
	@Schema(description = "참가인원: 복식일 시 짝수여야 합니다", example = "16")
	Integer playerLimitCount,

	@NotNull
	@Schema(description = "대진표 생성 조건", example = "FREE")
	MatchGenerationType matchGenerationType
) {
	public void validate() {
		validateDate();
		validatePlayerLimitCount();
	}

	private void validateDate() {
		if (recruitingClosedAt.isAfter(leagueAt)) {
			throw new RecruitingClosedAtAfterLeagueAtException(recruitingClosedAt, leagueAt);
		}
	}

	private void validatePlayerLimitCount() {
		if (this.matchType == MatchType.DOUBLES) {
			validateIsEven();
			validateMin();
		}
	}

	private void validateIsEven() {
		if (this.playerLimitCount % 2 == 1) {
			throw new InvalidDoublesPlayerLimitCountException(this.playerLimitCount, this.matchType);
		}
	}

	private void validateMin() {
		if (this.playerLimitCount < Constants.DOUBLES_PLAYER_LIMIT_MIN) {
			throw new InvalidDoublesPlayerLimitCountException(this.playerLimitCount);
		}
	}
}
