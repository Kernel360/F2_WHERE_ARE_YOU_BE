package org.badminton.api.interfaces.match.dto;

import java.util.List;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.match.info.BracketInfo;

public record BracketResponse(
	Long leagueId,
	MatchGenerationType matchGenerationType,
	MatchType matchType,
	MatchStatus matchStatus,
	int roundNumber,
	List<SinglesMatchResponse> singlesMatchInfoList,
	List<DoublesMatchResponse> doublesMatchInfoList
) {

	//TODO: 분기를 없애기
	public static BracketResponse fromBracketInfo(BracketInfo bracketInfo) {
		if (bracketInfo.matchType() == MatchType.SINGLES) {
			return new BracketResponse(bracketInfo.leagueId(),
				bracketInfo.matchGenerationType(),
				bracketInfo.matchType(),
				bracketInfo.matchStatus(),
				bracketInfo.roundNumber(),
				bracketInfo.singlesMatchInfoList().stream()
					.map(SinglesMatchResponse::fromSinglesMatchInfo)
					.toList(),
				null);
		} else if (bracketInfo.matchType() == MatchType.DOUBLES) {
			return new BracketResponse(bracketInfo.leagueId(),
				bracketInfo.matchGenerationType(),
				bracketInfo.matchType(),
				bracketInfo.matchStatus(),
				bracketInfo.roundNumber(),
				null,
				bracketInfo.doublesMatchInfoList().stream()
					.map(DoublesMatchResponse::fromDoublesMatchInfo)
					.toList());
		}
		return null;
	}
}
