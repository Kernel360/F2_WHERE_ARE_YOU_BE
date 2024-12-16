package org.badminton.infrastructure.match.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.badminton.domain.common.enums.MatchResult;
import org.badminton.domain.common.enums.SetStatus;
import org.badminton.domain.common.exception.match.AlreadyWinnerDeterminedException;
import org.badminton.domain.common.exception.match.PreviousSetNotFinishedException;
import org.badminton.domain.common.exception.match.SetFinishedException;
import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.league.vo.Team;
import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.DoublesSet;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.badminton.domain.domain.match.reader.DoublesMatchReader;
import org.badminton.domain.domain.match.service.AbstractDoublesMatchStrategy;
import org.badminton.domain.domain.match.store.DoublesMatchStore;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FreeDoublesMatchStrategy extends AbstractDoublesMatchStrategy {
	static final Integer LIMIT_SET_GAME = 3;

	private final DoublesMatchReader doublesMatchReader;
	private final DoublesMatchStore doublesMatchStore;
	private final LeagueReader leagueReader;

	public FreeDoublesMatchStrategy(DoublesMatchReader doublesMatchReader, DoublesMatchStore doublesMatchStore,
		LeagueReader leagueReader) {
		super(doublesMatchReader, doublesMatchStore);
		this.doublesMatchReader = doublesMatchReader;
		this.doublesMatchStore = doublesMatchStore;
		this.leagueReader = leagueReader;
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
	public SetInfo.Main endSet(Long matchId, Integer setNumber,
		MatchCommand.UpdateSetScore updateSetScoreCommand) {
		DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);

		validatePreviousSetCompletion(setNumber, doublesMatch);

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
		}

		if (updateSetScoreCommand.getScore2() > updateSetScoreCommand.getScore1()) {
			doublesMatch.team2WinSet();
		}

		if (setNumber.equals(LIMIT_SET_GAME) && doublesMatch.isDrawMatch()) {
			doublesMatch.setDrawMatch();
		}

		if (LIMIT_SET_GAME > setNumber) {
			changeNextSetStatus(doublesMatch, setNumber);
		}

		if (isAllMatchFinished(doublesMatch)) {
			leagueReader.readLeagueById(doublesMatch.getLeague().getLeagueId()).finishLeague();
		}

		doublesMatchStore.store(doublesMatch);
		return SetInfo.fromDoublesSet(matchId, setNumber, doublesMatch.getDoublesSets().get(setNumber - 1));
	}

	private void validatePreviousSetCompletion(int setNumber, DoublesMatch doublesMatch) {

		if (setNumber == 1) {
			return;
		}
		if (doublesMatch.getDoublesSet(setNumber - 1).getSetStatus() != SetStatus.FINISHED) {
			throw new PreviousSetNotFinishedException(setNumber - 1);
		}
	}

	private void changeNextSetStatus(DoublesMatch doublesMatch, Integer setNumber) {
		setNumber++;
		if (doublesMatch.getTeam1MatchResult().equals(MatchResult.NONE)) {
			doublesMatch.getDoublesSet(setNumber).open();
		}
		if (!doublesMatch.getTeam1MatchResult().equals(MatchResult.NONE)) {
			doublesMatch.getDoublesSet(setNumber).close();
		}
	}

	private boolean isAllMatchFinished(DoublesMatch doublesMatch) {
		return doublesMatchReader.allMatchesFinishedForLeague(
			doublesMatch.getLeague().getLeagueId());
	}

	@Override
	public SetInfo.Main retrieveSet(Long matchId, int setNumber) {
		DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);
		DoublesSet doublesSet = doublesMatch.getDoublesSet(setNumber);
		return SetInfo.fromDoublesSet(matchId, setNumber, doublesSet);
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
}
