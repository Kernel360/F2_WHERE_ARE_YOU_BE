package org.badminton.domain.domain.league.info;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OngoingAndUpcomingLeagueInfo {

	Long leagueId;
	LocalDateTime leagueAt;
	String leagueName;
	String description;
	MatchType matchType;
	int playerLimitCount;
	int recruitedMemberCount;
	LeagueStatus leagueStatus;
	Member.MemberTier requiredTier;
	String clubToken;

	public static OngoingAndUpcomingLeagueInfo from(League league, int recruitedMemberCount) {
		return new OngoingAndUpcomingLeagueInfo(
			league.getLeagueId(),
			league.getLeagueAt(),
			league.getLeagueName(),
			league.getDescription(),
			league.getMatchType(),
			league.getPlayerLimitCount(),
			recruitedMemberCount,
			league.getLeagueStatus(),
			league.getRequiredTier(),
			league.getClub().getClubToken()

		);
	}

}
