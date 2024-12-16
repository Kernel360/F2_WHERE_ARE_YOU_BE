package org.badminton.infrastructure.match.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.DoublesSet;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.badminton.domain.domain.match.info.SetInfo.Main;
import org.badminton.domain.domain.match.reader.DoublesMatchReader;
import org.badminton.domain.domain.match.service.AbstractDoublesMatchStrategy;
import org.badminton.domain.domain.match.store.DoublesMatchStore;
import org.badminton.infrastructure.match.service.TournamentDoublesBracketCreator;
import org.badminton.infrastructure.match.service.TournamentDoublesEndSetHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TournamentDoublesMatchStrategy extends AbstractDoublesMatchStrategy {

	private final DoublesMatchReader doublesMatchReader;
	private final DoublesMatchStore doublesMatchStore;
	private final TournamentDoublesEndSetHandler tournamentDoublesEndSetHandler;
	private final TournamentDoublesBracketCreator tournamentDoublesBracketCreator;

	public TournamentDoublesMatchStrategy(DoublesMatchReader doublesMatchReader, DoublesMatchStore doublesMatchStore,
		TournamentDoublesEndSetHandler tournamentDoublesEndSetHandler,
		TournamentDoublesBracketCreator tournamentDoublesBracketCreator) {
		super(doublesMatchReader, doublesMatchStore);
		this.doublesMatchReader = doublesMatchReader;
		this.doublesMatchStore = doublesMatchStore;
		this.tournamentDoublesEndSetHandler = tournamentDoublesEndSetHandler;
		this.tournamentDoublesBracketCreator = tournamentDoublesBracketCreator;
	}

	// 대진표 생성
	@Override
	public BracketInfo makeBracket(League league, List<LeagueParticipant> leagueParticipantList) {

		List<DoublesMatch> allMatches = new ArrayList<>();
		Collections.shuffle(leagueParticipantList);

		int totalRounds = tournamentDoublesBracketCreator.getTotalRounds(league, leagueParticipantList);

		tournamentDoublesBracketCreator.generateAllMatches(league, allMatches, leagueParticipantList, totalRounds);

		return BracketInfo.fromDoubles(totalRounds, allMatches);
	}

	// 세트 종료
	@Override
	@Transactional
	public SetInfo.Main endSet(Long matchId, Integer setNumber,
		MatchCommand.UpdateSetScore updateSetScoreCommand) {

		DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);

		tournamentDoublesEndSetHandler.isEndSetAllowed(matchId, setNumber, doublesMatch);

		tournamentDoublesEndSetHandler.processEndSet(setNumber, updateSetScoreCommand, doublesMatch);

		doublesMatchStore.store(doublesMatch);

		return SetInfo.fromDoublesSet(matchId, setNumber, doublesMatch.getDoublesSets().get(setNumber - 1));
	}

	// 세트 조회
	@Override
	public Main retrieveSet(Long matchId, int setNumber) {
		DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);
		DoublesSet doublesSet = doublesMatch.getDoublesSet(setNumber);
		return SetInfo.fromDoublesSet(matchId, setNumber, doublesSet);
	}

}
