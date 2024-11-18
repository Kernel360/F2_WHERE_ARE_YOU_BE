package org.badminton.infrastructure.match.service;

import org.badminton.domain.common.exception.league.LeagueParticipationNotExistException;
import org.badminton.domain.domain.league.LeagueParticipantReader;
import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.match.command.MatchCommand.UpdateSetScore;
import org.badminton.domain.domain.match.info.SetInfo.Main;
import org.badminton.domain.domain.match.reader.DoublesMatchStore;
import org.badminton.domain.domain.match.reader.SinglesMatchStore;
import org.badminton.domain.domain.match.service.MatchProgressService;
import org.badminton.domain.domain.match.service.MatchStrategy;
import org.badminton.domain.domain.match.store.DoublesMatchReader;
import org.badminton.domain.domain.match.store.SinglesMatchReader;
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

	@Override
	public MatchStrategy makeSinglesOrDoublesMatchStrategy(Long leagueId) {
		League league = findLeague(leagueId);
		return switch (league.getMatchType()) {
			case SINGLES ->
				new TournamentSinglesMatchStrategy(singlesMatchReader, singlesMatchStore, leagueParticipantReader);
			case DOUBLES ->
				new TournamentDoublesMatchStrategy(doublesMatchReader, doublesMatchStore, leagueParticipantReader);
		};
	}

	@Override
	public Main registerSetScoreInMatch(MatchStrategy matchStrategy, Long leagueId, Long matchId, int setIndex,
		UpdateSetScore updateSetScoreCommand, String memberToken) {
		if (leagueParticipantReader.isParticipant(memberToken, leagueId))
			throw new LeagueParticipationNotExistException(leagueId, memberToken);

		return matchStrategy.registerSetScoreInMatch(matchId, setIndex, updateSetScoreCommand);
	}

	private League findLeague(Long leagueId) {
		return leagueReader.readLeagueById(leagueId);
	}
}
