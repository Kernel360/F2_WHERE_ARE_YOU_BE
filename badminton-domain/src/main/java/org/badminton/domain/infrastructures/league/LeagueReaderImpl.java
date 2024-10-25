package org.badminton.domain.infrastructures.league;

import org.badminton.domain.common.exception.league.LeagueNotExistException;
import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.league.entity.League;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeagueReaderImpl implements LeagueReader {
	private final LeagueRepository leagueRepository;

	@Override
	public League readLeague(String clubToken, Long leagueId) {
		return leagueRepository.findByClubClubTokenAndLeagueId(clubToken, leagueId).orElseThrow(
			() -> new LeagueNotExistException(clubToken, leagueId));
	}
}
