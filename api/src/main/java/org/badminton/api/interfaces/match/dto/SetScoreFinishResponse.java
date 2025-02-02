package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.match.info.SetInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record SetScoreFinishResponse(
	@Schema(description = "매치 아이디", requiredMode = RequiredMode.REQUIRED)
	Long matchId,
	@Schema(description = "세트 번호 (1 | 2 | 3)", requiredMode = RequiredMode.REQUIRED)
	int setNumber,
	@Schema(description = "점수 1", requiredMode = RequiredMode.REQUIRED)
	int score1,
	@Schema(description = "점수 2", requiredMode = RequiredMode.REQUIRED)
	int score2,
	@Schema(description = "매치 타입 (SINGLES | DOUBLES)", requiredMode = RequiredMode.REQUIRED)
	MatchType matchType
) {

	public static SetScoreFinishResponse fromUpdateSetScoreInfo(SetInfo.Main setInfo) {
		return new SetScoreFinishResponse(setInfo.getMatchId(), setInfo.getSetNumber(), setInfo.getScore1(),
			setInfo.getScore2(), setInfo.getMatchType());
	}

}
