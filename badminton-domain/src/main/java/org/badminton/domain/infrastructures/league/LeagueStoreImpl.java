package org.badminton.domain.infrastructures.league;

import org.badminton.domain.domain.league.LeagueStore;
import org.badminton.domain.domain.league.command.LeagueCreateCommand;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.info.LeagueCreateInfo;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeagueStoreImpl implements LeagueStore {
	private final LeagueRepository leagueRepository;

	@Override
	public LeagueCreateInfo store(LeagueCreateCommand command) {
		var league = command.toEntity();
		League savedLeague = leagueRepository.save(league);
		return LeagueCreateInfo.leagueCreateInfo(savedLeague);
	}
}
