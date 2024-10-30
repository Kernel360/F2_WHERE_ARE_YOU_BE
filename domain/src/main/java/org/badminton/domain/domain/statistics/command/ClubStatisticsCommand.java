package org.badminton.domain.domain.statistics.command;

import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.statistics.ClubStatistics;

public record ClubStatisticsCommand(
	Long clubStatisticsId,

	Club club,

	int visitedCount,

	int leagueCount,

	int registrationCount
) {
	public static ClubStatisticsCommand from(ClubStatistics clubStatistics) {
		return new ClubStatisticsCommand(
			clubStatistics.getClubStatisticsId(),
			clubStatistics.getClub(),
			clubStatistics.getVisitedCount(),
			clubStatistics.getLeagueCount(),
			clubStatistics.getRegistrationCount()
		);
	}
}
