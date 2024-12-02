package org.badminton.domain.domain.match.info;

import java.util.List;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.SinglesMatch;

import lombok.Getter;

@Getter
public class MatchInfo {

	public record SetScoreDetails(
		Long matchId,
		Long leagueId,
		String leagueName,
		MatchType matchType,
		Integer setNumberInProgress,
		SinglesMatchInfo singlesMatch,
		DoublesMatchInfo doublesMatch,
		List<SinglesSetInfo> singlesSets,
		List<DoublesSetInfo> doublesSets
	) {

		public static SetScoreDetails fromSinglesMatchToMatchDetails(SinglesMatch singlesMatch) {

			return new SetScoreDetails(singlesMatch.getId(),
				singlesMatch.getLeague().getLeagueId(),
				singlesMatch.getLeague().getLeagueName(),
				MatchType.SINGLES,
				singlesMatch.getSetNumberInProgress(),
				SinglesMatchInfo.fromSinglesMatch(singlesMatch),
				null,
				SinglesSetInfo.finishedSets(singlesMatch.getSinglesSets()),
				null);
		}

		public static SetScoreDetails fromDoublesMatchToMatchDetails(DoublesMatch doublesMatch) {

			return new SetScoreDetails(doublesMatch.getId(),
				doublesMatch.getLeague().getLeagueId(),
				doublesMatch.getLeague().getLeagueName(),
				MatchType.DOUBLES,
				doublesMatch.getSetNumberInProgress(),
				null,
				DoublesMatchInfo.fromDoublesMatch(doublesMatch),
				null,
				DoublesSetInfo.finishedSets(doublesMatch.getDoublesSets()));
		}
	}
}
