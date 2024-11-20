package org.badminton.infrastructure.match.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.badminton.domain.common.enums.MatchResult;
import org.badminton.domain.common.enums.SetStatus;
import org.badminton.domain.common.exception.match.AlreadyWinnerDeterminedException;
import org.badminton.domain.common.exception.match.LeagueParticipantsNotExistsException;
import org.badminton.domain.common.exception.match.MatchDuplicateException;
import org.badminton.domain.common.exception.match.SetFinishedException;
import org.badminton.domain.domain.league.LeagueParticipantReader;
import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.entity.SinglesSet;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.badminton.domain.domain.match.reader.SinglesMatchStore;
import org.badminton.domain.domain.match.service.AbstractSinglesMatchStrategy;
import org.badminton.domain.domain.match.store.SinglesMatchReader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TournamentSinglesMatchStrategy extends AbstractSinglesMatchStrategy {

	public static final int SET_COUNT = 3;
	public static final int PARTICIPANTS_PER_MATCH = 2;
	static final Integer LIMIT_SET_GAME = 3;
	private final SinglesMatchStore singlesMatchStore;
	private final LeagueParticipantReader leagueParticipantReader;
	private final SinglesMatchReader singlesMatchReader;
	private final LeagueReader leagueReader;

	public TournamentSinglesMatchStrategy(SinglesMatchReader singlesMatchReader, SinglesMatchStore singlesMatchStore,
		LeagueParticipantReader leagueParticipantReader, LeagueReader leagueReader) {
		super(singlesMatchReader);
		this.singlesMatchReader = singlesMatchReader;
		this.singlesMatchStore = singlesMatchStore;
		this.leagueParticipantReader = leagueParticipantReader;
		this.leagueReader = leagueReader;
	}

	private static boolean isMatchWinnerDetermined(SinglesMatch singlesMatch) {
		return singlesMatch.getPlayer1MatchResult() == MatchResult.WIN
			|| singlesMatch.getPlayer2MatchResult() == MatchResult.WIN;
	}

	@Override
	public BracketInfo makeBracket(League league, List<LeagueParticipant> leagueParticipantList) {
		List<SinglesMatch> allMatches = new ArrayList<>();

		List<LeagueParticipant> currentParticipants = new ArrayList<>(leagueParticipantList);
		Collections.shuffle(currentParticipants);
		int totalRounds = MatchUtils.calculateTotalRounds(currentParticipants.size());
		league.defineTotalRounds(totalRounds);

		allMatches.addAll(createFirstRoundMatches(league, currentParticipants));
		allMatches.addAll(createSubsequentRoundsMatches(league, totalRounds));

		return BracketInfo.fromSingles(totalRounds, allMatches);

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

	@Override
	@Transactional
	public SetInfo.Main registerSetScoreInMatch(Long matchId, Integer setNumber,
		MatchCommand.UpdateSetScore updateSetScoreCommand) {
		SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(matchId);

		if (isMatchWinnerDetermined(singlesMatch))
			throw new AlreadyWinnerDeterminedException(singlesMatch.getId());

		if (singlesMatch.getSinglesSet(setNumber).getSetStatus() == SetStatus.FINISHED)
			throw new SetFinishedException(setNumber);

		if (singlesMatch.getLeagueParticipant1() == null || singlesMatch.getLeagueParticipant2() == null)
			throw new LeagueParticipantsNotExistsException(matchId);

		updateSetScore(singlesMatch, setNumber, updateSetScoreCommand);
		singlesMatchStore.store(singlesMatch);

		if (LIMIT_SET_GAME > setNumber) {
			changeNextSetStatus(singlesMatch, setNumber);
		}

		// 최종 승자가 정해지면 matchResult 업데이트
		if (isMatchWinnerDetermined(singlesMatch)) {
			singlesMatchStore.store(singlesMatch);
			updateNextRoundMatch(singlesMatch);
		}

		if (isAllMatchFinished(singlesMatch))
			leagueReader.readLeagueById(singlesMatch.getLeague().getLeagueId()).finishLeague();

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
	public SetInfo.Main retrieveSet(Long matchId, int setNumber) {
		SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(matchId);
		SinglesSet singlesSet = singlesMatch.getSinglesSet(1);
		return SetInfo.fromSinglesSet(matchId, setNumber, singlesSet);
	}

	private List<SinglesMatch> createFirstRoundMatches(League league, List<LeagueParticipant> participants) {
		List<SinglesMatch> matches = new ArrayList<>();
		for (int i = 0; i < participants.size(); i += PARTICIPANTS_PER_MATCH) {
			SinglesMatch match = new SinglesMatch(league, participants.get(i), participants.get(i + 1), 1);
			makeSetsInMatch(match);
			singlesMatchStore.store(match);
			matches.add(match);
		}
		return matches;
	}

	private List<SinglesMatch> createRoundMatches(League league, List<SinglesMatch> previousMatches, int roundNumber) {
		List<SinglesMatch> currentRoundMatches = new ArrayList<>();
		for (int i = 0; i < previousMatches.size(); i += PARTICIPANTS_PER_MATCH) {
			SinglesMatch match = new SinglesMatch(league, null, null, roundNumber);
			makeSetsInMatch(match);
			singlesMatchStore.store(match);
			currentRoundMatches.add(match);
		}
		return currentRoundMatches;
	}

	private List<SinglesMatch> createSubsequentRoundsMatches(League league, int totalRounds) {
		List<SinglesMatch> matches = new ArrayList<>();
		List<SinglesMatch> previousMatches = singlesMatchReader.findMatchesByLeagueAndRound(league.getLeagueId(), 1);

		for (int roundNumber = 2; roundNumber <= totalRounds; roundNumber++) {
			List<SinglesMatch> currentRoundMatches = createRoundMatches(league, previousMatches, roundNumber);
			matches.addAll(currentRoundMatches);
			previousMatches = currentRoundMatches;
		}

		return matches;
	}

	private void makeSetsInMatch(SinglesMatch singlesMatch) {
		for (int i = 1; i <= SET_COUNT; i++) {
			SinglesSet set = new SinglesSet(singlesMatch, i);
			singlesMatch.addSet(set);
		}
		singlesMatchStore.store(singlesMatch);
	}

	private void updateSetScore(SinglesMatch singlesMatch, int setNumber,
		MatchCommand.UpdateSetScore updateSetScoreCommand) {
		SinglesSet set = singlesMatch.getSinglesSet(setNumber);
		set.endSetScore(updateSetScoreCommand.getScore1(), updateSetScoreCommand.getScore2());

		if (updateSetScoreCommand.getScore1() > updateSetScoreCommand.getScore2()) {
			singlesMatch.player1WinSet();
		} else {
			singlesMatch.player2WinSet();
		}
	}

	private void updateNextRoundMatch(SinglesMatch singlesMatch) {
		LeagueParticipant winner = determineWinner(singlesMatch);
		if (winner == null)
			return;
		int totalRounds = singlesMatch.getLeague().getTotalRounds();
		if (singlesMatch.getRoundNumber() == totalRounds) {
			return;
		}

		SinglesMatch startMatch = singlesMatchReader.findFirstMatchByLeagueId(
			singlesMatch.getLeague().getLeagueId());

		int nextRoundMatchId = MatchUtils.calculateNextRoundMatchId(Math.toIntExact(singlesMatch.getId()),
			leagueParticipantReader.countParticipantMember(singlesMatch.getLeague().getLeagueId()),
			Math.toIntExact(startMatch.getId()));

		SinglesMatch nextRoundMatch = singlesMatchReader.getSinglesMatch((long)nextRoundMatchId);
		if (nextRoundMatch != null) {
			assignWinnerToNextRoundMatch(nextRoundMatch, winner);
			singlesMatchStore.store(nextRoundMatch);
		}

	}

	private void assignWinnerToNextRoundMatch(SinglesMatch nextRoundMatch, LeagueParticipant winner) {
		if (nextRoundMatch.getLeagueParticipant1() == null) {
			nextRoundMatch.defineLeagueParticipant1(winner);
		} else {
			nextRoundMatch.defineLeagueParticipant2(winner);
		}
	}

	private LeagueParticipant determineWinner(SinglesMatch match) {
		if (match.getPlayer1MatchResult() == MatchResult.WIN) {
			return match.getLeagueParticipant1();
		}
		if (match.getPlayer2MatchResult() == MatchResult.WIN) {
			return match.getLeagueParticipant2();
		}
		return null;
	}

	@Override
	public void initMatch(Long matchId) {
		SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(matchId);
		singlesMatch.startMatch();
		singlesMatch.getSinglesSet(1).initMatch();
		singlesMatchStore.store(singlesMatch);
	}

}
