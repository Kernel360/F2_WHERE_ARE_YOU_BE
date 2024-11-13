package org.badminton.api.interfaces.match.dto;

import java.util.List;

import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.domain.match.info.DoublesMatchInfo;

import io.swagger.v3.oas.annotations.media.Schema;

public record DoublesMatchResponse(

	@Schema(description = "매치 아이디")
	Long matchId,
	int roundNumber,
	@Schema(description = "매치 상태(NOT_STARTED | IN_PROGRESS | FINISHED)")
	MatchStatus matchStatus,

	@Schema(description = "팀1")
	MatchTeamResponse team1,

	@Schema(description = "팀2")
	MatchTeamResponse team2,

	List<String> winnersToken

) {

	public static DoublesMatchResponse fromDoublesMatchInfo(DoublesMatchInfo doublesMatchInfo) {
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
