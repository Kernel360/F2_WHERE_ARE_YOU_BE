package org.badminton.api.interfaces.league.dto;

import org.badminton.domain.common.consts.Constants;
import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.policy.LeagueParticipantPolicy;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LeagueUpdateRequest(

	@NotBlank
	@Size(min = Constants.NAME_MIN, max = Constants.NAME_MAX)
	@Schema(description = "경기 이름", example = "배드민턴 경기")
	String leagueName,

	@NotBlank
	@Size(min = Constants.DESCRIPTION_MIN, max = Constants.DESCRIPTION_MAX)
	@Schema(description = "경기 설명", example = "이 경기는 지역 예선 경기입니다.")
	String description,

	@NotNull
	@Min(Constants.PLAYER_LIMIT_MIN)
	@Max(Constants.PLAYER_LIMIT_MAX)
	@Schema(description = "기존 참가 인원보다 적게 입력할 수 없습니다.", example = "16")
	Integer playerLimitCount,

	@NotNull
	@Schema(description = "경기 방식", example = "SINGLES")
	MatchType matchType,

	@NotNull
	@Schema(description = "매칭 조건", example = "FREE")
	MatchGenerationType matchGenerationType
) {
	public void validate() {
		LeagueParticipantPolicy.validatePlayerCount(this.matchType, this.matchGenerationType, this.playerLimitCount);
	}
}
