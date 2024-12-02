package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.domain.match.info.SinglesMatchInfo;

import io.swagger.v3.oas.annotations.media.Schema;

public record SinglesMatchResponse(
	@Schema(description = "매치 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
	Long matchId,
	@Schema(description = "매치의 라운드 번호", requiredMode = Schema.RequiredMode.REQUIRED)
	int roundNumber,
	@Schema(description = "매치 상태(NOT_STARTED | IN_PROGRESS | FINISHED)", requiredMode = Schema.RequiredMode.REQUIRED)
	MatchStatus matchStatus,
	@Schema(description = "경기 참가자 1", requiredMode = Schema.RequiredMode.REQUIRED)
	Participant participant1,
	@Schema(description = "경기 참가자 2", requiredMode = Schema.RequiredMode.REQUIRED)
	Participant participant2,
	@Schema(description = "승자의 맴버 토큰", requiredMode = Schema.RequiredMode.REQUIRED)
	String winnerToken

) {

	public static SinglesMatchResponse fromSinglesMatchInfo(SinglesMatchInfo singlesMatchInfo) {
		return new SinglesMatchResponse(
			singlesMatchInfo.matchId(),
			singlesMatchInfo.roundNumber(),
			singlesMatchInfo.matchStatus(),
			new Participant(singlesMatchInfo.participant1MemberToken(), singlesMatchInfo.participant1Name(),
				singlesMatchInfo.participant1Image(), singlesMatchInfo.participant1Tier(),
				singlesMatchInfo.participant1WinSetCount()),
			new Participant(singlesMatchInfo.participant2MemberToken(), singlesMatchInfo.participant2Name(),
				singlesMatchInfo.participant2Image(), singlesMatchInfo.participant2Tier(),
				singlesMatchInfo.participant2WinSetCount()),
			singlesMatchInfo.winnerToken()
		);
	}
}
