package org.badminton.infrastructure.match.service;

import org.badminton.domain.common.exception.league.LeagueParticipationNotExistException;
import org.badminton.domain.domain.league.LeagueParticipantReader;
import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.match.command.MatchCommand.UpdateSetScore;
import org.badminton.domain.domain.match.info.SetInfo.Main;
import org.badminton.domain.domain.match.reader.DoublesMatchReader;
import org.badminton.domain.domain.match.reader.SinglesMatchReader;
import org.badminton.domain.domain.match.service.MatchProgressService;
import org.badminton.domain.domain.match.service.MatchStrategy;
import org.badminton.domain.domain.match.store.DoublesMatchStore;
import org.badminton.domain.domain.match.store.SinglesMatchStore;
import org.badminton.infrastructure.match.strategy.TournamentDoublesMatchStrategy;
import org.badminton.infrastructure.match.strategy.TournamentSinglesMatchStrategy;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TournamentMatchProgressServiceImpl implements MatchProgressService {

	private final SinglesMatchReader singlesMatchReader;
	private final DoublesMatchReader doublesMatchReader;
	private final SinglesMatchStore singlesMatchStore;
	private final DoublesMatchStore doublesMatchStore;
	private final LeagueReader leagueReader;
	private final LeagueParticipantReader leagueParticipantReader;
	private final TournamentSinglesEndSetHandler tournamentSinglesEndSetHandler;
	private final TournamentSinglesBracketCreator tournamentSinglesBracketCreator;
	private final TournamentDoublesEndSetHandler tournamentDoublesEndSetHandler;
	private final TournamentDoublesBracketCreator tournamentDoublesBracketCreator;

	@Override
	public MatchStrategy makeSinglesOrDoublesMatchStrategy(Long leagueId) {
		League league = findLeague(leagueId);
		return switch (league.getMatchType()) {
			case SINGLES -> new TournamentSinglesMatchStrategy(singlesMatchReader, singlesMatchStore,
				tournamentSinglesBracketCreator, tournamentSinglesEndSetHandler);
			case DOUBLES -> new TournamentDoublesMatchStrategy(doublesMatchReader, doublesMatchStore,
				tournamentDoublesEndSetHandler, tournamentDoublesBracketCreator);
		};
	}

	@Override
	public Main registerSetScoreInMatch(MatchStrategy matchStrategy, Long leagueId, Long matchId, Integer setIndex,
		UpdateSetScore updateSetScoreCommand, String memberToken) {
		if (!leagueParticipantReader.isParticipant(memberToken, leagueId))
			throw new LeagueParticipationNotExistException(leagueId, memberToken);

		return matchStrategy.endSet(matchId, setIndex, updateSetScoreCommand);
	}

	private League findLeague(Long leagueId) {
		return leagueReader.readLeagueById(leagueId);
	}
}
