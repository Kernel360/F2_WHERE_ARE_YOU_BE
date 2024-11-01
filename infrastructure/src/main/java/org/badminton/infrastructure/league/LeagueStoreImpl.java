package org.badminton.infrastructure.league;

import org.badminton.domain.domain.league.LeagueStore;
import org.badminton.domain.domain.league.entity.League;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeagueStoreImpl implements LeagueStore {
	private final LeagueRepository leagueRepository;

	@Override
	public League store(League league) {
		return leagueRepository.save(league);
	}
}
