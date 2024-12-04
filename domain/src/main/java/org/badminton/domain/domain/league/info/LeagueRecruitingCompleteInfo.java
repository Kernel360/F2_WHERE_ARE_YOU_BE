package org.badminton.domain.domain.league.info;

import org.badminton.domain.domain.league.enums.LeagueStatus;

public record LeagueRecruitingCompleteInfo(
	Long leagueId,
	LeagueStatus leagueStatus
) {
}
