package org.badminton.infrastructure.match.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.badminton.domain.common.enums.MatchResult;
import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.enums.SetStatus;
import org.badminton.domain.common.exception.match.AlreadyWinnerDeterminedException;
import org.badminton.domain.common.exception.match.MatchDuplicateException;
import org.badminton.domain.common.exception.match.SetFinishedException;
import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.entity.SinglesSet;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.badminton.domain.domain.match.info.SetInfo.Main;
import org.badminton.domain.domain.match.reader.SinglesMatchStore;
import org.badminton.domain.domain.match.service.AbstractSinglesMatchStrategy;
import org.badminton.domain.domain.match.store.SinglesMatchReader;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FreeSinglesMatchStrategy extends AbstractSinglesMatchStrategy {
	static final Integer LIMIT_SET_GAME = 3;

	private final SinglesMatchReader singlesMatchReader;
	private final SinglesMatchStore singlesMatchStore;
	private final LeagueReader leagueReader;

	public FreeSinglesMatchStrategy(SinglesMatchReader singlesMatchReader, SinglesMatchStore singlesMatchStore,
		LeagueReader leagueReader) {
		super(singlesMatchReader);
		this.singlesMatchReader = singlesMatchReader;
		this.singlesMatchStore = singlesMatchStore;
		this.leagueReader = leagueReader;
	}

	private static boolean isMatchWinnerDetermined(SinglesMatch singlesMatch) {
		return singlesMatch.getPlayer1MatchResult() == MatchResult.WIN
			|| singlesMatch.getPlayer2MatchResult() == MatchResult.WIN;
	}

	private static boolean isMatchFinished(SinglesMatch singlesMatch) {
		return singlesMatch.getMatchStatus() == MatchStatus.FINISHED;
	}

	@Override
	public BracketInfo makeBracket(League league,
		List<LeagueParticipant> leagueParticipantList) {
		Collections.shuffle(leagueParticipantList);
		List<SinglesMatch> singlesMatches = makeSinglesMatches(leagueParticipantList, league, 1);
		singlesMatches.forEach(this::makeSetsInMatch);
		return BracketInfo.fromSingles(1, singlesMatches);
	}

	@Override
	public SetInfo.Main registerSetScoreInMatch(Long matchId, Integer setNumber,
		MatchCommand.UpdateSetScore updateSetScoreCommand) {
		SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(matchId);

		if (isMatchWinnerDetermined(singlesMatch)) {
			throw new AlreadyWinnerDeterminedException(singlesMatch.getId());
		}

		if (singlesMatch.getSinglesSet(setNumber).getSetStatus() == SetStatus.FINISHED) {
			throw new SetFinishedException(setNumber);
		}

		singlesMatch.getSinglesSet(setNumber)
			.endSetScore(updateSetScoreCommand.getScore1(), updateSetScoreCommand.getScore2());

		if (updateSetScoreCommand.getScore1() > updateSetScoreCommand.getScore2()) {
			singlesMatch.player1WinSet();
		} else {
			singlesMatch.player2WinSet();
		}

		if (LIMIT_SET_GAME > setNumber) {
			changeNextSetStatus(singlesMatch, setNumber);
		}

		if (isAllMatchFinished(singlesMatch))
			leagueReader.readLeagueById(singlesMatch.getLeague().getLeagueId()).finishLeague();

		singlesMatchStore.store(singlesMatch);
		return SetInfo.fromSinglesSet(matchId, setNumber, singlesMatch.getSinglesSets().get(setNumber - 1));
	}

	private void changeNextSetStatus(SinglesMatch singlesMatch, Integer setNumber) {
		setNumber++;
		if (singlesMatch.getPlayer1MatchResult().equals(MatchResult.NONE))
			singlesMatch.getSinglesSet(setNumber).open();
		if (!singlesMatch.getPlayer1MatchResult().equals(MatchResult.NONE))
			singlesMatch.getSinglesSet(setNumber).close();
	}

	private boolean isAllMatchFinished(SinglesMatch singlesMatch) {
		return singlesMatchReader.allMatchesFinishedForLeague(
			singlesMatch.getLeague().getLeagueId());
	}

	@Override
	public Main retrieveSet(Long matchId, int setNumber) {
		SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(matchId);
		SinglesSet singlesSet = singlesMatch.getSinglesSet(1);
		return SetInfo.fromSinglesSet(matchId, setNumber, singlesSet);
	}

	@Override
	public void checkDuplicateInitialBracket(Long leagueId) {
		boolean isBracketEmpty = singlesMatchReader.checkIfBracketEmpty(leagueId);

		if (!isBracketEmpty && singlesMatchReader.allMatchesNotStartedForLeague(leagueId)) {
			singlesMatchStore.deleteSinglesBracket(leagueId);
		} else if (!isBracketEmpty && !singlesMatchReader.allMatchesNotStartedForLeague(leagueId)) {
			throw new MatchDuplicateException(leagueId);
		}
	}

	private void makeSetsInMatch(SinglesMatch singlesMatch) {
		for (int i = 1; i <= 3; i++) {
			singlesMatch.addSet(new SinglesSet(singlesMatch, i));
		}
		singlesMatchStore.store(singlesMatch);
	}

	private List<SinglesMatch> makeSinglesMatches(List<LeagueParticipant> leagueParticipantList,
		League league, int roundNumber) {

		List<SinglesMatch> singlesMatches = new ArrayList<>();
		for (int i = 0; i < leagueParticipantList.size() - 1; i += 2) {
			SinglesMatch singlesMatch = new SinglesMatch(league, leagueParticipantList.get(i),
				leagueParticipantList.get(i + 1), roundNumber);
			singlesMatches.add(singlesMatch);
			singlesMatchStore.store(singlesMatch);
		}
		return singlesMatches;
	}

	public void initMatch(Long matchId) {
		SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(matchId);
		singlesMatch.startMatch();
		singlesMatch.getSinglesSet(1).initMatch();
		singlesMatchStore.store(singlesMatch);
	}

}

