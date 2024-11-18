package org.badminton.infrastructure.match.strategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.badminton.domain.common.enums.MatchResult;
import org.badminton.domain.common.enums.SetStatus;
import org.badminton.domain.common.exception.match.AlreadyWinnerDeterminedException;
import org.badminton.domain.common.exception.match.SetFinishedException;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.league.vo.Team;
import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.DoublesSet;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.badminton.domain.domain.match.reader.DoublesMatchStore;
import org.badminton.domain.domain.match.service.AbstractDoublesMatchStrategy;
import org.badminton.domain.domain.match.store.DoublesMatchReader;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FreeDoublesMatchStrategy extends AbstractDoublesMatchStrategy {

	private final DoublesMatchReader doublesMatchReader;
	private final DoublesMatchStore doublesMatchStore;

	public FreeDoublesMatchStrategy(DoublesMatchReader doublesMatchReader, DoublesMatchStore doublesMatchStore) {
		super(doublesMatchReader);
		this.doublesMatchReader = doublesMatchReader;
		this.doublesMatchStore = doublesMatchStore;
	}

	private static boolean isMatchWinnerDetermined(DoublesMatch doublesMatch) {
		return doublesMatch.getTeam1MatchResult() == MatchResult.WIN
			|| doublesMatch.getTeam2MatchResult() == MatchResult.WIN;
	}

	@Override
	public BracketInfo makeBracket(League league,
		List<LeagueParticipant> leagueParticipantList) {
		Collections.shuffle(leagueParticipantList);
		List<DoublesMatch> doublesMatches = makeDoublesMatches(leagueParticipantList, league, 1);
		doublesMatches.forEach(this::makeDoublesSetsInMatch);
		return BracketInfo.fromDoubles(1, doublesMatches);
	}

	@Override
	public SetInfo.Main registerSetScoreInMatch(Long matchId, int setNumber,
		MatchCommand.UpdateSetScore updateSetScoreCommand) {
		DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);

		if (isMatchWinnerDetermined(doublesMatch)) {
			throw new AlreadyWinnerDeterminedException(doublesMatch.getId());
		}

		if (doublesMatch.getDoublesSet(setNumber).getSetStatus() == SetStatus.FINISHED) {
			throw new SetFinishedException(setNumber);
		}

		doublesMatch.getDoublesSet(setNumber)
			.endSetScore(updateSetScoreCommand.getScore1(), updateSetScoreCommand.getScore2());

		if (updateSetScoreCommand.getScore1() > updateSetScoreCommand.getScore2()) {
			doublesMatch.team1WinSet();
		} else {
			doublesMatch.team2WinSet();
		}

		doublesMatchStore.store(doublesMatch);
		return SetInfo.fromDoublesSet(matchId, setNumber, doublesMatch.getDoublesSets().get(setNumber - 1));
	}

	@Override
	public SetInfo.Main retrieveSet(Long matchId, int setNumber) {
		DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);
		DoublesSet doublesSet = doublesMatch.getDoublesSet(setNumber);
		return SetInfo.fromDoublesSet(matchId, setNumber, doublesSet);
	}

	@Override
	public void checkDuplicateInitialBracket(LocalDateTime leagueAt, Long leagueId) {
		boolean isBracketEmpty = doublesMatchReader.checkIfBracketEmpty(leagueId);
		if (!isBracketEmpty && LocalDateTime.now().isBefore(leagueAt)) {
			doublesMatchStore.deleteDoublesBracket(leagueId);
		} else if (!isBracketEmpty && LocalDateTime.now().isAfter(leagueAt)) {
			// TODO: 경기 시작 시간이 지났는데, 이미 대진표가 있는데 또 생성하고 싶다면? -> 막을지, 안막을지
		}
	}

	private void makeDoublesSetsInMatch(DoublesMatch doublesMatch) {
		for (int i = 1; i <= 3; i++) {
			doublesMatch.addSet(new DoublesSet(doublesMatch, i));
		}
		doublesMatchStore.store(doublesMatch);
	}

	private List<DoublesMatch> makeDoublesMatches(List<LeagueParticipant> leagueParticipantList,
		League league, int roundNumber) {

		List<DoublesMatch> doublesMatches = new ArrayList<>();
		for (int i = 0; i < leagueParticipantList.size() - 3; i += 4) {
			Team team1 = new Team(leagueParticipantList.get(i), leagueParticipantList.get(i + 1));
			Team team2 = new Team(leagueParticipantList.get(i + 2), leagueParticipantList.get(i + 3));
			DoublesMatch doublesMatch = new DoublesMatch(league, team1, team2, roundNumber);
			doublesMatches.add(doublesMatch);
			doublesMatchStore.store(doublesMatch);
		}
		return doublesMatches;
	}

	@Override
	public void initMatch(Long matchId) {
		DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);
		doublesMatch.initMatch();
		doublesMatch.getDoublesSet(1).initMatch();
		doublesMatchStore.store(doublesMatch);
	}
}