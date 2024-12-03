package org.badminton.api.interfaces.match.dto;

import java.util.List;

import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.domain.match.info.DoublesMatchInfo;

import io.swagger.v3.oas.annotations.media.Schema;

public record DoublesMatchResponse(

	@Schema(description = "매치 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
	Long matchId,
	@Schema(description = "매치의 라운드 번호", requiredMode = Schema.RequiredMode.REQUIRED)
	int roundNumber,
	@Schema(description = "매치 상태(NOT_STARTED | IN_PROGRESS | FINISHED)", requiredMode = Schema.RequiredMode.REQUIRED)
	MatchStatus matchStatus,
	@Schema(description = "팀1", requiredMode = Schema.RequiredMode.REQUIRED)
	MatchTeamResponse team1,
	@Schema(description = "팀2", requiredMode = Schema.RequiredMode.REQUIRED)
	MatchTeamResponse team2,
	@Schema(description = "승자의 멤버토큰", requiredMode = Schema.RequiredMode.REQUIRED)
	List<String> winnersToken

) {

	public static DoublesMatchResponse fromDoublesMatchInfo(DoublesMatchInfo doublesMatchInfo) {
		if (doublesMatchInfo == null)
			return null;
		return new DoublesMatchResponse(
			doublesMatchInfo.matchId(),
			doublesMatchInfo.roundNumber(),
			doublesMatchInfo.matchStatus(),
			MatchTeamResponse.fromTeam(doublesMatchInfo.team1()),
			MatchTeamResponse.fromTeam(doublesMatchInfo.team2()),
			doublesMatchInfo.winnersToken()
		);
	}
}
