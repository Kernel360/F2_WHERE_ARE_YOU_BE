package org.badminton.domain.domain.match.info;

import java.util.ArrayList;
import java.util.List;

import org.badminton.domain.domain.match.entity.SinglesSet;

public record SinglesSetInfo(
	int setNumber,
	int score1,
	int score2
) {

	public static List<SinglesSetInfo> fromSinglesSets(List<SinglesSet> singlesSets) {
		List<SinglesSetInfo> singlesSetResponseList = new ArrayList<>();
		for (SinglesSet singlesSet : singlesSets) {
			singlesSetResponseList.add(new SinglesSetInfo(singlesSet.getSetNumber(), singlesSet.getPlayer1Score(),
				singlesSet.getPlayer2Score()));
		}
		return singlesSetResponseList;
	}
}
