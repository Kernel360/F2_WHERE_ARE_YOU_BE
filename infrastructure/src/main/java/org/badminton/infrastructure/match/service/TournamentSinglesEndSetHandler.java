package org.badminton.infrastructure.match.service;

import static org.badminton.domain.common.consts.Constants.*;

import java.util.List;

import org.badminton.domain.common.enums.MatchResult;
import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.enums.SetStatus;
import org.badminton.domain.common.exception.match.AlreadyWinnerDeterminedException;
import org.badminton.domain.common.exception.match.LeagueParticipantNotDeterminedException;
import org.badminton.domain.common.exception.match.PreviousSetNotFinishedException;
import org.badminton.domain.common.exception.match.RegisterScoreInByeMatchException;
import org.badminton.domain.common.exception.match.RoundNotFinishedException;
import org.badminton.domain.common.exception.match.SetFinishedException;
import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.entity.SinglesSet;
import org.badminton.domain.domain.match.reader.SinglesMatchReader;
import org.badminton.domain.domain.match.store.SinglesMatchStore;
import org.badminton.infrastructure.match.strategy.MatchUtils;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TournamentSinglesEndSetHandler {

	private final MatchUtils matchUtils;
	private final SinglesMatchReader singlesMatchReader;
	private final LeagueReader leagueReader;
	private final SinglesMatchStore singlesMatchStore;

	public void isEndSetAllowed(SinglesMatch singlesMatch, Integer setNumber, Long matchId) {

		// 이전 세트 모두 종료 확인
		isPreviousSetComplete(setNumber, singlesMatch);
		// 이전 라운드 모두 종료 확인
		isPreviousRoundComplete(singlesMatch.getLeague().getLeagueId(), singlesMatch.getRoundNumber());

		// 부전승 EndSet 안됨
		if (singlesMatch.getMatchStatus() == MatchStatus.BYE) {
			throw new RegisterScoreInByeMatchException(singlesMatch.getId(), MatchType.SINGLES);
		}

		// Winner 가 이미 결정됨 -> 매치가 끝남
		if (singlesMatch.isMatchWinnerDetermined()) {
			throw new AlreadyWinnerDeterminedException(singlesMatch.getId());
		}

		// 해당 Set 의 상태가 FINISHED
		if (singlesMatch.getSinglesSet(setNumber).getSetStatus() == SetStatus.FINISHED) {
			throw new SetFinishedException(setNumber);
		}

		// LeagueParticipant 이 없을 때
		if (!singlesMatch.isLeagueParticipant1Exist() || !singlesMatch.isLeagueParticipant2Exist()) {
			throw new LeagueParticipantNotDeterminedException(matchId);
		}

	}

	public void processEndSet(SinglesMatch singlesMatch, Integer setNumber,
		MatchCommand.UpdateSetScore updateSetScoreCommand) {

		// setScore 저장
		updateWinnerWithSetScore(singlesMatch, setNumber, updateSetScoreCommand);

		// 현재 세트 번호가 3보다 적다면 다음 세트 상태 변경
		if (LIMIT_SET_GAME > setNumber) {
			changeNextSetStatus(singlesMatch, setNumber);
		}

		// 승자가 결정 되었다면 승자 처우 결정(다음 라운드 진출 할 수 있으면 승자 이동)
		if (singlesMatch.isMatchWinnerDetermined()) {
			processNextRoundMatches(singlesMatch);
		}

		// 경기의 모든 매치가 끝났다면 경기 조회 후 finishLeague
		if (isAllMatchFinished(singlesMatch)) {
			leagueReader.readLeagueById(singlesMatch.getLeague().getLeagueId()).finishLeague();
		}

	}

	private void isPreviousRoundComplete(Long leagueId, int roundNumber) {

		if (roundNumber == FIRST_ROUND_NUMBER) {
			return;
		}
		int previousRoundNumber = roundNumber - 1;

		if (!singlesMatchReader.allRoundMatchesDone(leagueId, previousRoundNumber)) {
			throw new RoundNotFinishedException(previousRoundNumber);
		}
	}

	private void isPreviousSetComplete(int setNumber, SinglesMatch singlesMatch) {

		if (setNumber == FIRST_SET_NUMBER) {
			return;
		}
		int previousSetNumber = setNumber - 1;
		if (singlesMatch.getSinglesSet(previousSetNumber).getSetStatus() != SetStatus.FINISHED) {
			throw new PreviousSetNotFinishedException(previousSetNumber);
		}
	}

	private void updateWinnerWithSetScore(SinglesMatch singlesMatch, int setNumber,
		MatchCommand.UpdateSetScore updateSetScoreCommand) {
		SinglesSet set = singlesMatch.getSinglesSet(setNumber);
		set.endSetScore(updateSetScoreCommand.getScore1(), updateSetScoreCommand.getScore2());

		if (updateSetScoreCommand.getScore1() > updateSetScoreCommand.getScore2()) {
			singlesMatch.player1WinSet();
		} else {
			singlesMatch.player2WinSet();
		}
	}

	private void processNextRoundMatches(SinglesMatch singlesMatch) {
		// singleMatch 저장
		singlesMatchStore.store(singlesMatch);

		matchUtils.updateSinglesMatchNextRoundMatch(singlesMatch);

		boolean isCurrentRoundComplete = singlesMatchReader.allRoundMatchesDone(singlesMatch.getLeague().getLeagueId(),
			singlesMatch.getRoundNumber());

		boolean isCurrentRoundLessThanTotalRounds =
			singlesMatch.getRoundNumber() < singlesMatch.getLeague().getTotalRounds();

		if (isCurrentRoundComplete && isCurrentRoundLessThanTotalRounds) {

			int nextRoundNumber = singlesMatch.getRoundNumber() + 1;

			List<SinglesMatch> nextRoundMatches = singlesMatchReader.findMatchesByLeagueAndRound(
				singlesMatch.getLeague().getLeagueId(), nextRoundNumber);

			nextRoundMatches.stream()
				.filter(match -> match.getMatchStatus() == MatchStatus.BYE)
				.forEach(matchUtils::updateSinglesMatchNextRoundMatch);
		}
	}

	private void changeNextSetStatus(SinglesMatch singlesMatch, Integer setNumber) {
		setNumber++;
		if (singlesMatch.getPlayer1MatchResult().equals(MatchResult.NONE)) {
			singlesMatch.getSinglesSet(setNumber).open();
		}
		if (!singlesMatch.getPlayer1MatchResult().equals(MatchResult.NONE)) {
			singlesMatch.getSinglesSet(setNumber).close();
		}
	}

	private boolean isAllMatchFinished(SinglesMatch singlesMatch) {
		return singlesMatchReader.allMatchesFinishedForLeague(
			singlesMatch.getLeague().getLeagueId());
	}
}
