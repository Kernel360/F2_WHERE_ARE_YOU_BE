package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.domain.match.info.DoublesMatchInfo;

import io.swagger.v3.oas.annotations.media.Schema;

public record DoublesMatchResponse(

	@Schema(description = "매치 아이디")
	Long matchId,
	@Schema(description = "팀 1 경기 참가자 1 이름")
	String participant1Name,
	@Schema(description = "팀 1 경기 참가자 1 이미지")
	String participant1Image,
	@Schema(description = "팀 1 경기 참가자 2 이름")
	String participant2Name,
	@Schema(description = "팀 1 경기 참가자 2 이미지")
	String participant2Image,
	@Schema(description = "팀 2 경기 참가자 3 이름")
	String participant3Name,
	@Schema(description = "팀 2 경기 참가자 3 이미지")
	String participant3Image,
	@Schema(description = "팀 2 경기 참가자 4 이름")
	String participant4Name,
	@Schema(description = "팀 2 경기 참가자 4 이미지")
	String participant4Image,
	@Schema(description = "팀 1 이긴 세트수")
	int team1WinSetCount,
	@Schema(description = "팀 2 이긴 세트수")
	int team2WinSetCount,
	int roundNumber
) {

	public static DoublesMatchResponse fromDoublesMatchInfo(DoublesMatchInfo doublesMatchInfo) {
		return new DoublesMatchResponse(
			doublesMatchInfo.matchId(),
			doublesMatchInfo.team1().participant1Name(),
			doublesMatchInfo.team1().participant1Image(),
			doublesMatchInfo.team1().participant2Name(),
			doublesMatchInfo.team1().participant2Image(),
			doublesMatchInfo.team2().participant1Name(),
			doublesMatchInfo.team2().participant1Image(),
			doublesMatchInfo.team2().participant2Name(),
			doublesMatchInfo.team2().participant2Image(),
			doublesMatchInfo.team1WinSetCount(),
			doublesMatchInfo.team2WinSetCount(),
			doublesMatchInfo.roundNumber()
		);
	}
}
