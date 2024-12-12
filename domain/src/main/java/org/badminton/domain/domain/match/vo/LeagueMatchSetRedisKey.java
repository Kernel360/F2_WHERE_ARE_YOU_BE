package org.badminton.domain.domain.match.vo;

import lombok.Getter;

@Getter
public class LeagueMatchSetRedisKey {

	public static final String IN_PROGRESS_MATCH_PREFIX = "IN_PROGRESS_";
	private static final String LEAGUE_ID_PREFIX = "_LEAGUE_ID_";
	private static final String MATCH_ID_PREFIX = "_MATCH_ID_";
	private static final String SET_NUMBER_PREFIX = "_SET_NUMBER";

	private final String key;
	private final Long leagueId;
	private final Long matchId;
	private final int setNumber;

	public LeagueMatchSetRedisKey(Long leagueId, Long matchId, int setNumber) {
		this.key =
			IN_PROGRESS_MATCH_PREFIX + LEAGUE_ID_PREFIX + leagueId + MATCH_ID_PREFIX + matchId + SET_NUMBER_PREFIX
				+ setNumber;
		this.leagueId = leagueId;
		this.matchId = matchId;
		this.setNumber = setNumber;
	}
}
