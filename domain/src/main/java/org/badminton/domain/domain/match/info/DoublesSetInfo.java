package org.badminton.domain.domain.match.info;

import java.util.List;

import org.badminton.domain.common.enums.SetStatus;
import org.badminton.domain.domain.match.entity.DoublesSet;

public record DoublesSetInfo(
	int setNumber,
	int score1,
	int score2,
	SetStatus setStatus
) {

	public static List<DoublesSetInfo> finishedSets(List<DoublesSet> doublesSets) {
		return doublesSets.stream()
			.filter(doublesSet -> doublesSet.getSetStatus() == SetStatus.FINISHED)
			.map(doublesSet -> new DoublesSetInfo(doublesSet.getSetNumber(), doublesSet.getTeam1Score(),
				doublesSet.getTeam2Score(), doublesSet.getSetStatus()))
			.toList();
	}
}
