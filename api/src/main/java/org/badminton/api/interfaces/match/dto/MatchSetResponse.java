package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.common.enums.SetStatus;
import org.badminton.domain.domain.match.info.MatchSetInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record MatchSetResponse(
	@Schema(description = "단식 경기 참가자")
	SinglesMatchPlayerResponse singlesMatchPlayerResponse,
	@Schema(description = "복식 경기 참가자")
	DoublesMatchPlayerResponse doublesMatchPlayerResponse,
	@Schema(description = "세트 상태", requiredMode = RequiredMode.REQUIRED)
	SetStatus setStatus,
	@Schema(description = "세트 점수 1", requiredMode = RequiredMode.REQUIRED)
	int setScore1,
	@Schema(description = "세트 점수 2", requiredMode = RequiredMode.REQUIRED)
	int setScore2,
	@Schema(description = "이긴 세트수 1", requiredMode = RequiredMode.REQUIRED)
	int winSetScore1,
	@Schema(description = "이긴 세트수 2", requiredMode = RequiredMode.REQUIRED)
	int winSetScore2,
	@Schema(description = "세트 번호 (1 | 2 | 3)", requiredMode = RequiredMode.REQUIRED)
	int setNumber
) {

	public static MatchSetResponse from(MatchSetInfo matchSetInfo) {
		return new MatchSetResponse(
			SinglesMatchPlayerResponse.from(matchSetInfo.getSinglesMatchPlayerInfo()),
			DoublesMatchPlayerResponse.of(matchSetInfo.getDoublesMatchPlayerInfo()),
			matchSetInfo.getSetStatus(),
			matchSetInfo.getSetScore1(),
			matchSetInfo.getSetScore2(),
			matchSetInfo.getWinSetScore1(),
			matchSetInfo.getWinSetScore2(),
			matchSetInfo.getSetNumber()
		);
	}
}
