package org.badminton.api.interfaces.match.dto;

import java.util.List;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.match.info.MatchInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record MatchDetailsResponse(
	@Schema(description = "매치 아이디", requiredMode = RequiredMode.REQUIRED)
	Long matchId,
	@Schema(description = "경기 아이디", requiredMode = RequiredMode.REQUIRED)
	Long leagueId,
	@Schema(description = "경기 제목", requiredMode = RequiredMode.REQUIRED)
	String leagueTitle,
	@Schema(description = "매치 타입", requiredMode = RequiredMode.REQUIRED)
	MatchType matchType,
	@Schema(description = "현재 진행 중인 세트 넘버, 만약 진행 중인 세트가 없다면 null 응답", requiredMode = RequiredMode.REQUIRED)
	Integer setNumberInProgress,
	@Schema(description = "단식 경기")
	SinglesMatchResponse singlesMatch,
	@Schema(description = "복식 경기")
	DoublesMatchResponse doublesMatch,
	@Schema(description = "상태가 FINISHED 인 단식 경기 세트")
	List<SinglesSetResponse> singlesSets,
	@Schema(description = "상태가 FINISHED 인 복식 경기 세트")
	List<DoublesSetResponse> doublesSets
) {

	public static MatchDetailsResponse fromMatchDetailsInfo(MatchInfo.SetScoreDetails matchDetails) {
		return new MatchDetailsResponse(
			matchDetails.matchId(),
			matchDetails.leagueId(),
			matchDetails.leagueName(),
			matchDetails.matchType(),
			matchDetails.setNumberInProgress(),
			matchDetails.matchType() == MatchType.SINGLES
				? SinglesMatchResponse.fromSinglesMatchInfo(matchDetails.singlesMatch())
				: null,
			matchDetails.matchType() == MatchType.DOUBLES
				? DoublesMatchResponse.fromDoublesMatchInfo(matchDetails.doublesMatch())
				: null,
			matchDetails.matchType() == MatchType.SINGLES
				? SinglesSetResponse.fromSinglesSetInfoList(matchDetails.singlesSets())
				: null,
			matchDetails.matchType() == MatchType.DOUBLES
				? DoublesSetResponse.fromDoublesSetInfoList(matchDetails.doublesSets())
				: null
		);
	}
}
