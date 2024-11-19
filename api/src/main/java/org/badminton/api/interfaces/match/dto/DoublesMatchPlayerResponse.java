package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.domain.match.info.DoublesMatchPlayerInfo;

import io.swagger.v3.oas.annotations.media.Schema;

public record DoublesMatchPlayerResponse(
	@Schema(description = "팀 1 경기 참가자 1 이름", requiredMode = Schema.RequiredMode.REQUIRED)
	String participant1Name,
	@Schema(description = "팀 1 경기 참가자 1 이미지", requiredMode = Schema.RequiredMode.REQUIRED)
	String participant1Image,
	@Schema(description = "팀 1 경기 참가자 2 이름", requiredMode = Schema.RequiredMode.REQUIRED)
	String participant2Name,
	@Schema(description = "팀 1 경기 참가자 2 이미지", requiredMode = Schema.RequiredMode.REQUIRED)
	String participant2Image,
	@Schema(description = "팀 2 경기 참가자 3 이름", requiredMode = Schema.RequiredMode.REQUIRED)
	String participant3Name,
	@Schema(description = "팀 2 경기 참가자 3 이미지", requiredMode = Schema.RequiredMode.REQUIRED)
	String participant3Image,
	@Schema(description = "팀 2 경기 참가자 4 이름", requiredMode = Schema.RequiredMode.REQUIRED)
	String participant4Name,
	@Schema(description = "팀 2 경기 참가자 4 이미지", requiredMode = Schema.RequiredMode.REQUIRED)
	String participant4Image
) {
	public static DoublesMatchPlayerResponse of(DoublesMatchPlayerInfo doublesMatchPlayerInfo) {
		if (doublesMatchPlayerInfo == null) {
			return null;
		}
		return new DoublesMatchPlayerResponse(
			doublesMatchPlayerInfo.team1().participant1Name(),
			doublesMatchPlayerInfo.team1().participant1Image(),
			doublesMatchPlayerInfo.team1().participant2Name(),
			doublesMatchPlayerInfo.team1().participant2Image(),
			doublesMatchPlayerInfo.team2().participant1Name(),
			doublesMatchPlayerInfo.team2().participant1Image(),
			doublesMatchPlayerInfo.team2().participant2Name(),
			doublesMatchPlayerInfo.team2().participant2Image()
		);
	}
}
