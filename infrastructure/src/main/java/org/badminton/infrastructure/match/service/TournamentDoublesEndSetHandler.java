package org.badminton.infrastructure.match.service;

import static org.badminton.domain.common.consts.Constants.*;

import java.util.List;

import org.badminton.domain.common.enums.MatchResult;
import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.enums.SetStatus;
import org.badminton.domain.common.exception.match.AlreadyWinnerDeterminedException;
import org.badminton.domain.common.exception.match.LeagueParticipantNotDeterminedException;
import org.badminton.domain.common.exception.match.PreviousDetNotFinishedException;
import org.badminton.domain.common.exception.match.RegisterScoreInByeMatchException;
import org.badminton.domain.common.exception.match.RoundNotFinishedException;
import org.badminton.domain.common.exception.match.SetFinishedException;
import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.DoublesSet;
import org.badminton.domain.domain.match.reader.DoublesMatchReader;
import org.badminton.domain.domain.match.store.DoublesMatchStore;
import org.badminton.infrastructure.match.strategy.MatchUtils;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TournamentDoublesEndSetHandler {

	private final DoublesMatchReader doublesMatchReader;
	private final MatchUtils matchUtils;
	private final LeagueReader leagueReader;
	private final DoublesMatchStore doublesMatchStore;

	public void processEndSet(Integer setNumber, MatchCommand.UpdateSetScore updateSetScoreCommand,
		DoublesMatch doublesMatch) {

		matchUtils.validateSetScores(updateSetScoreCommand);
		
		updateWinnerWithSetScore(doublesMatch, setNumber, updateSetScoreCommand);

		if (LIMIT_SET_GAME > setNumber) {
			changeNextSetStatus(doublesMatch, setNumber);
		}

		if (doublesMatch.isMatchWinnerDetermined()) {
			processNextRoundMatches(doublesMatch);
		}

		if (isAllMatchFinished(doublesMatch)) {
			leagueReader.readLeagueById(doublesMatch.getLeague().getLeagueId()).finishLeague();
		}

	}

	public void isEndSetAllowed(Long matchId, Integer setNumber, DoublesMatch doublesMatch) {
		isPreviousSetComplete(setNumber, doublesMatch);

		isPreviousRoundComplete(doublesMatch.getLeague().getLeagueId(), doublesMatch.getRoundNumber());

		if (doublesMatch.getMatchStatus() == MatchStatus.BYE) {
			throw new RegisterScoreInByeMatchException(doublesMatch.getId(), MatchType.DOUBLES);
		}

		if (doublesMatch.isMatchWinnerDetermined()) {
			throw new AlreadyWinnerDeterminedException(doublesMatch.getId());
		}

		if (doublesMatch.getDoublesSet(setNumber).getSetStatus() == SetStatus.FINISHED) {
			throw new SetFinishedException(setNumber);
		}

		if (!doublesMatch.isTeam1Exist() || !doublesMatch.isTeam2Exist()) {
			throw new LeagueParticipantNotDeterminedException(matchId);
		}
	}

	private void isPreviousRoundComplete(Long leagueId, int roundNumber) {

		if (roundNumber == FIRST_ROUND_NUMBER) {
			return;
		}

		int previousRoundNumber = roundNumber - 1;

		if (!doublesMatchReader.allRoundMatchesDone(leagueId, previousRoundNumber)) {
			throw new RoundNotFinishedException(previousRoundNumber);
		}
	}

	private void isPreviousSetComplete(int setNumber, DoublesMatch doublesMatch) {

		if (setNumber == FIRST_SET_NUMBER) {
			return;
		}
		int previousSetNumber = setNumber - 1;
		if (doublesMatch.getDoublesSet(previousSetNumber).getSetStatus() != SetStatus.FINISHED) {
			throw new PreviousDetNotFinishedException(previousSetNumber);
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

	private void processNextRoundMatches(DoublesMatch doublesMatch) {

		doublesMatchStore.store(doublesMatch);

		matchUtils.updateDoublesMatchNextRoundMatch(doublesMatch);

		boolean isCurrentRoundComplete = doublesMatchReader.allRoundMatchesDone(doublesMatch.getLeague().getLeagueId(),
			doublesMatch.getRoundNumber());

		boolean isCurrentRoundLessThanTotalRounds =
			doublesMatch.getRoundNumber() < doublesMatch.getLeague().getTotalRounds();

		if (isCurrentRoundComplete && isCurrentRoundLessThanTotalRounds) {

			int nextRoundNumber = doublesMatch.getRoundNumber() + 1;

			List<DoublesMatch> nextRoundMatches = doublesMatchReader.findMatchesByLeagueAndRound(
				doublesMatch.getLeague().getLeagueId(), nextRoundNumber);

			nextRoundMatches.stream()
				.filter(match -> match.getMatchStatus() == MatchStatus.BYE)
				.forEach(matchUtils::updateDoublesMatchNextRoundMatch);
		}
	}

	private void updateWinnerWithSetScore(DoublesMatch doublesMatch, int setNumber,
		MatchCommand.UpdateSetScore updateSetScoreCommand) {
		DoublesSet set = doublesMatch.getDoublesSet(setNumber);
		set.endSetScore(updateSetScoreCommand.getScore1(), updateSetScoreCommand.getScore2());

		if (updateSetScoreCommand.getScore1() > updateSetScoreCommand.getScore2()) {
			doublesMatch.team1WinSet();
		} else {
			doublesMatch.team2WinSet();
		}
	}
}
