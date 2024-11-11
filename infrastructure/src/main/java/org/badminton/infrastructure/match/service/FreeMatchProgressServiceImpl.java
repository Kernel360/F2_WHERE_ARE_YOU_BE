package org.badminton.infrastructure.match.service;

import org.badminton.domain.common.exception.league.LeagueNotExistException;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.match.command.MatchCommand.UpdateSetScore;
import org.badminton.domain.domain.match.info.SetInfo.Main;
import org.badminton.domain.domain.match.reader.DoublesMatchStore;
import org.badminton.domain.domain.match.reader.SinglesMatchStore;
import org.badminton.domain.domain.match.service.MatchProgressService;
import org.badminton.domain.domain.match.service.MatchStrategy;
import org.badminton.domain.domain.match.store.DoublesMatchReader;
import org.badminton.domain.domain.match.store.SinglesMatchReader;
import org.badminton.infrastructure.league.LeagueRepository;
import org.badminton.infrastructure.match.strategy.FreeDoublesMatchStrategy;
import org.badminton.infrastructure.match.strategy.FreeSinglesMatchStrategy;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FreeMatchProgressServiceImpl implements MatchProgressService {

	private final LeagueRepository leagueRepository;
	private final SinglesMatchReader singlesMatchReader;
	private final DoublesMatchReader doublesMatchReader;
	private final SinglesMatchStore singlesMatchStore;
	private final DoublesMatchStore doublesMatchStore;

	@Override
	public MatchStrategy makeSinglesOrDoublesMatchStrategy(Long leagueId) {
		League league = findLeague(leagueId);
		return switch (league.getMatchType()) {
			case SINGLES -> new FreeSinglesMatchStrategy(singlesMatchReader, singlesMatchStore);
			case DOUBLES -> new FreeDoublesMatchStrategy(doublesMatchReader, doublesMatchStore);
		};
	}

	@Override
	public Main registerSetScoreInMatch(MatchStrategy matchStrategy, Long leagueId, Long matchId, int setIndex,
		UpdateSetScore updateSetScoreCommand) {
		return matchStrategy.registerSetScoreInMatch(matchId, setIndex, updateSetScoreCommand);
	}

	private League findLeague(Long leagueId) {
		return leagueRepository.findById(leagueId)
			.orElseThrow(() -> new LeagueNotExistException(leagueId));
	}
}
