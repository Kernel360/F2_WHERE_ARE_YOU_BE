package org.badminton.domain.domain.match.info;

import java.util.List;

import org.badminton.domain.common.enums.SetStatus;
import org.badminton.domain.domain.match.entity.SinglesSet;

public record SinglesSetInfo(
	int setNumber,
	int score1,
	int score2,
	SetStatus setStatus
) {

	public static List<SinglesSetInfo> finishedSets(List<SinglesSet> singlesSets) {
		return singlesSets.stream()
			.filter(singlesSet -> singlesSet.getSetStatus() == SetStatus.FINISHED)
			.map(singlesSet -> new SinglesSetInfo(singlesSet.getSetNumber(), singlesSet.getPlayer1Score(),
				singlesSet.getPlayer2Score(), singlesSet.getSetStatus()))
			.toList();
	}
}
