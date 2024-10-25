package org.badminton.domain.domain.match.info;

import java.util.List;
import lombok.Getter;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.match.entity.DoublesMatchEntity;
import org.badminton.domain.domain.match.entity.SinglesMatchEntity;

@Getter
public class MatchInfo {

    public record SetScoreDetails(
            Long matchId,
            Long leagueId,
            MatchType matchType,
            SinglesMatchInfo singlesMatch,
            DoublesMatchInfo doublesMatch,
            List<SinglesSetInfo> singlesSets,
            List<DoublesSetInfo> doublesSets
    ) {

        public static SetScoreDetails fromSinglesMatchToMatchDetails(SinglesMatchEntity singlesMatch) {

            return new SetScoreDetails(singlesMatch.getSinglesMatchId(),
                    singlesMatch.getLeague().getLeagueId(),
                    MatchType.SINGLES,
                    SinglesMatchInfo.fromSinglesMatch(singlesMatch),
                    null,
                    SinglesSetInfo.fromSinglesSets(singlesMatch.getSinglesSets()),
                    null);

        }

        public static SetScoreDetails fromDoublesMatchToMatchDetails(DoublesMatchEntity doublesMatch) {

            return new SetScoreDetails(doublesMatch.getDoublesMatchId(),
                    doublesMatch.getLeague().getLeagueId(),
                    MatchType.DOUBLES,
                    null,
                    DoublesMatchInfo.fromDoublesMatchEntity(doublesMatch),
                    null,
                    DoublesSetInfo.fromDoublesSets(doublesMatch.getDoublesSets()));

        }
    }
}
