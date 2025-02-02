package org.badminton.api.interfaces.match.dto;

import java.util.List;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.match.info.BracketInfo;

import io.swagger.v3.oas.annotations.media.Schema;

public record BracketResponse(
	@Schema(description = "경기 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
	Long leagueId,
	@Schema(description = "대진표 생성 타입(FREE | TOURNAMENT)", requiredMode = Schema.RequiredMode.REQUIRED)
	MatchGenerationType matchGenerationType,
	@Schema(description = "매치 타입(SINGLES | DOUBLES)", requiredMode = Schema.RequiredMode.REQUIRED)
	MatchType matchType,
	@Schema(description = "리그 상태(PLAYING | CANCELED | FINISHED)", requiredMode = Schema.RequiredMode.REQUIRED)
	LeagueStatus leagueStatus,
	@Schema(description = "전체 라운드 수", requiredMode = Schema.RequiredMode.REQUIRED)
	int totalRound,
	@Schema(description = "단식 매치 리스트")
	List<SinglesMatchResponse> singlesMatchResponseList,
	@Schema(description = "복식 매치 리스트")
	List<DoublesMatchResponse> doublesMatchResponseList
) {

	//TODO: 분기를 없애기
	public static BracketResponse fromBracketInfo(BracketInfo bracketInfo) {
		if (bracketInfo.matchType() == MatchType.SINGLES) {
			return new BracketResponse(bracketInfo.leagueId(),
				bracketInfo.matchGenerationType(),
				bracketInfo.matchType(),
				bracketInfo.leagueStatus(),
				bracketInfo.totalRound(),
				bracketInfo.singlesMatchInfoList().stream()
					.map(SinglesMatchResponse::fromSinglesMatchInfo)
					.toList(),
				null);
		} else if (bracketInfo.matchType() == MatchType.DOUBLES) {
			return new BracketResponse(bracketInfo.leagueId(),
				bracketInfo.matchGenerationType(),
				bracketInfo.matchType(),
				bracketInfo.leagueStatus(),
				bracketInfo.totalRound(),
				null,
				bracketInfo.doublesMatchInfoList().stream()
					.map(DoublesMatchResponse::fromDoublesMatchInfo)
					.toList());
		}
		return null;
	}
}