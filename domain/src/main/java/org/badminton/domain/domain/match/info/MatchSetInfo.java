package org.badminton.domain.domain.match.info;

import org.badminton.domain.common.enums.SetStatus;
import org.badminton.domain.domain.match.entity.DoublesSet;
import org.badminton.domain.domain.match.entity.SinglesSet;
import org.badminton.domain.domain.match.vo.Score;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MatchSetInfo {

	private final SinglesMatchPlayerInfo singlesMatchPlayerInfo;
	private final DoublesMatchPlayerInfo doublesMatchPlayerInfo;
	private final SetStatus setStatus;
	private int setScore1;
	private int setScore2;
	private final int winSetScore1;
	private final int winSetScore2;
	private final int setNumber;

	public static MatchSetInfo fromSingles(SinglesSet singlesSet) {
		return new MatchSetInfo(
			SinglesMatchPlayerInfo.fromSinglesMatch(singlesSet.getSinglesMatch()),
			null,
			singlesSet.getSetStatus(),
			singlesSet.getPlayer1Score(),
			singlesSet.getPlayer2Score(),
			singlesSet.getSinglesMatch().getPlayer1WinSetCount(),
			singlesSet.getSinglesMatch().getPlayer2WinSetCount(),
			singlesSet.getSetNumber()
		);
	}

	public static MatchSetInfo fromDoubles(DoublesSet doublesSet) {
		return new MatchSetInfo(
			null,
			DoublesMatchPlayerInfo.from(doublesSet.getDoublesMatch()),
			doublesSet.getSetStatus(),
			doublesSet.getTeam1Score(),
			doublesSet.getTeam2Score(),
			doublesSet.getDoublesMatch().getTeam1WinSetCount(),
			doublesSet.getDoublesMatch().getTeam2WinSetCount(),
			doublesSet.getSetNumber()
		);
	}

	public void cacheScore(Score score) {
		this.setScore1 = score.getLeft();
		this.setScore2 = score.getRight();
	}
}
