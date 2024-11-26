package org.badminton.domain.domain.match.info;

import java.util.ArrayList;
import java.util.List;

import org.badminton.domain.domain.match.entity.DoublesSet;

public record DoublesSetInfo(
	int setNumber,
	int score1,
	int score2
) {

	public static List<DoublesSetInfo> fromDoublesSets(List<DoublesSet> doublesSets) {
		List<DoublesSetInfo> doublesSetResponseList = new ArrayList<>();
		for (DoublesSet doublesSet : doublesSets) {
			doublesSetResponseList.add(new DoublesSetInfo(doublesSet.getSetNumber(), doublesSet.getTeam1Score(),
				doublesSet.getTeam2Score()));
		}
		return doublesSetResponseList;
	}

}
