package org.badminton.infrastructure.match.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.badminton.domain.common.enums.MatchResult;
import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.enums.SetStatus;
import org.badminton.domain.common.exception.match.AlreadyWinnerDeterminedException;
import org.badminton.domain.common.exception.match.LeagueParticipantsNotExistsException;
import org.badminton.domain.common.exception.match.ByeMatchException;
import org.badminton.domain.common.exception.match.LeagueParticipantNotDeterminedException;
import org.badminton.domain.common.exception.match.PreviousDetNotFinishedException;
import org.badminton.domain.common.exception.match.RegisterScoreInByeMatchException;
import org.badminton.domain.common.exception.match.RoundNotFinishedException;
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
		super(singlesMatchReader, singlesMatchStore);
		this.singlesMatchReader = singlesMatchReader;
		this.singlesMatchStore = singlesMatchStore;
		this.leagueParticipantReader = leagueParticipantReader;
		this.leagueReader = leagueReader;
	}

	private static boolean isMatchWinnerDetermined(SinglesMatch singlesMatch) {
		return singlesMatch.getPlayer1MatchResult() == MatchResult.WIN
			|| singlesMatch.getPlayer2MatchResult() == MatchResult.WIN;
	}

	private static boolean isParticipantOddSize(List<LeagueParticipant> participants) {
		return participants.size() % 2 != 0;
	}

	private static boolean isSinglesMatchOddSize(List<SinglesMatch> singlesMatches) {
		return singlesMatches.size() % 2 != 0;
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

		allMatches.stream()
			.filter(singlesMatch -> singlesMatch.getMatchStatus() == MatchStatus.BYE
				&& singlesMatch.getLeagueParticipant1() != null)
			.forEach(this::updateNextRoundMatch);

		return BracketInfo.fromSingles(totalRounds, allMatches);
	}

	@Override
	@Transactional
	public SetInfo.Main registerSetScoreInMatch(Long matchId, Integer setNumber,
		MatchCommand.UpdateSetScore updateSetScoreCommand) {
		SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(matchId);

		validatePreviousSetCompletion(setNumber, singlesMatch);

		validatePreviousRoundCompletion(singlesMatch.getLeague().getLeagueId(), singlesMatch.getRoundNumber());

		if (singlesMatch.getMatchStatus() == MatchStatus.BYE) {
			throw new RegisterScoreInByeMatchException(singlesMatch.getId());
		}

		if (isMatchWinnerDetermined(singlesMatch)) {
			throw new AlreadyWinnerDeterminedException(singlesMatch.getId());
		}

		if (singlesMatch.getSinglesSet(setNumber).getSetStatus() == SetStatus.FINISHED) {
			throw new SetFinishedException(setNumber);
		}

		if (singlesMatch.getLeagueParticipant1() == null || singlesMatch.getLeagueParticipant2() == null) {
			throw new LeagueParticipantNotDeterminedException(matchId);
		}

		updateSetScore(singlesMatch, setNumber, updateSetScoreCommand);

		if (LIMIT_SET_GAME > setNumber) {
			changeNextSetStatus(singlesMatch, setNumber);
		}

		if (isMatchWinnerDetermined(singlesMatch)) {
			processMatchAndNextRound(singlesMatch);
		}

		if (isAllMatchFinished(singlesMatch)) {
			leagueReader.readLeagueById(singlesMatch.getLeague().getLeagueId()).finishLeague();
		}
		singlesMatchStore.store(singlesMatch);
		return SetInfo.fromSinglesSet(matchId, setNumber, singlesMatch.getSinglesSets().get(setNumber - 1));
	}

	private void validatePreviousRoundCompletion(Long leagueId, int roundNumber) {

		if (roundNumber == 1) {
			return;
		}

		if (!singlesMatchReader.allRoundMatchesDone(leagueId, roundNumber - 1)) {
			throw new RoundNotFinishedException(roundNumber - 1);
		}
	}

	private void validatePreviousSetCompletion(int setNumber, SinglesMatch singlesMatch) {

		if (setNumber == 1) {
			return;
		}
		if (singlesMatch.getSinglesSet(setNumber - 1).getSetStatus() != SetStatus.FINISHED) {
			throw new PreviousDetNotFinishedException(setNumber - 1);
		}
	}

	private void processMatchAndNextRound(SinglesMatch singlesMatch) {
		singlesMatchStore.store(singlesMatch);
		updateNextRoundMatch(singlesMatch);
		boolean allRoundMatchesDone = singlesMatchReader.allRoundMatchesDone(singlesMatch.getLeague().getLeagueId(),
			singlesMatch.getRoundNumber());
		boolean currentRoundInTotalRound =
			singlesMatch.getRoundNumber() < singlesMatch.getLeague().getTotalRounds();

		if (allRoundMatchesDone && currentRoundInTotalRound) {
			List<SinglesMatch> nextRoundMatches = singlesMatchReader.findMatchesByLeagueAndRound(
				singlesMatch.getLeague().getLeagueId(), singlesMatch.getRoundNumber() + 1
			);
			nextRoundMatches.stream()
				.filter(match -> match.getMatchStatus() == MatchStatus.BYE)
				.forEach(this::updateNextRoundMatch);
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

	@Override
	public SetInfo.Main retrieveSet(Long matchId, int setNumber) {
		SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(matchId);
		SinglesSet singlesSet = singlesMatch.getSinglesSet(1);
		return SetInfo.fromSinglesSet(matchId, setNumber, singlesSet);
	}

	private List<SinglesMatch> createFirstRoundMatches(League league, List<LeagueParticipant> participants) {
		List<SinglesMatch> matches = new ArrayList<>();

		for (int i = 0; i < participants.size() - 1; i += PARTICIPANTS_PER_MATCH) {
			SinglesMatch match = new SinglesMatch(league, participants.get(i), participants.get(i + 1), 1);
			makeSetsInMatch(match);
			singlesMatchStore.store(match);
			matches.add(match);
		}

		if (isParticipantOddSize(participants)) {
			LeagueParticipant byeParticipant = participants.remove(participants.size() - 1);
			SinglesMatch byeMatch = new SinglesMatch(league, byeParticipant, null, 1);
			byeMatch.byeMatch();
			singlesMatchStore.store(byeMatch);
			matches.add(byeMatch);
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

	private List<SinglesMatch> createSubsequentRoundsMatches(League league, int totalRounds) {
		List<SinglesMatch> matches = new ArrayList<>();
		List<SinglesMatch> previousRoundMatches = singlesMatchReader.findMatchesByLeagueAndRound(league.getLeagueId(),
			1);

		for (int roundNumber = 2; roundNumber <= totalRounds; roundNumber++) {
			List<SinglesMatch> currentRoundMatches = createMatchesRound(league, previousRoundMatches, roundNumber);
			matches.addAll(currentRoundMatches);
			previousRoundMatches = currentRoundMatches;
		}

		return matches;
	}

	private List<SinglesMatch> createMatchesRound(League league, List<SinglesMatch> previousRoundMatches,
		int roundNumber) {
		List<SinglesMatch> currentRoundMatches = new ArrayList<>();

		currentRoundMatches.addAll(createRegularMatchesForRound(league, previousRoundMatches, roundNumber));

		if (isSinglesMatchOddSize(previousRoundMatches)) {
			currentRoundMatches.add(createByeRoundMatch(league, previousRoundMatches, roundNumber));
		}

		return currentRoundMatches;
	}

	private List<SinglesMatch> createRegularMatchesForRound(League league, List<SinglesMatch> previousRoundMatches,
		int roundNumber) {
		List<SinglesMatch> regularRoundMatches = new ArrayList<>();

		for (int i = 0; i < previousRoundMatches.size() - 1; i += PARTICIPANTS_PER_MATCH) {
			SinglesMatch match = new SinglesMatch(league, null, null, roundNumber);
			makeSetsInMatch(match);
			singlesMatchStore.store(match);
			regularRoundMatches.add(match);
		}

		return regularRoundMatches;
	}

	private SinglesMatch createByeRoundMatch(League league, List<SinglesMatch> previousRoundMatches,
		int roundNumber) {
		SinglesMatch byeMatch = previousRoundMatches.get(previousRoundMatches.size() - 1);
		LeagueParticipant winner = determineWinner(byeMatch);

		SinglesMatch nextByeMatch = new SinglesMatch(league, winner, null, roundNumber);
		nextByeMatch.byeMatch();
		singlesMatchStore.store(nextByeMatch);

		return nextByeMatch;
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
		if (winner == null) {
			return;
		}
		int totalRounds = singlesMatch.getLeague().getTotalRounds();
		if (singlesMatch.getRoundNumber() == totalRounds) {
			return;
		}
		SinglesMatch startMatch = singlesMatchReader.findFirstMatchByLeagueId(
			singlesMatch.getLeague().getLeagueId()
		);
		int nextRoundMatchId = MatchUtils.calculateNextRoundMatchId(
			Math.toIntExact(singlesMatch.getId()),
			leagueParticipantReader.countParticipantMember(singlesMatch.getLeague().getLeagueId()),
			Math.toIntExact(startMatch.getId())
		);
		SinglesMatch nextRoundMatch = singlesMatchReader.getSinglesMatch((long)nextRoundMatchId);

		if (nextRoundMatch == null) {
			return;
		}

		if (singlesMatch.getMatchStatus() == MatchStatus.BYE) {
			nextRoundMatch.defineLeagueParticipant1(winner);
			singlesMatchStore.store(nextRoundMatch);
			return;
		}

		assignWinnerToNextRoundMatch(nextRoundMatch, winner);
		singlesMatchStore.store(nextRoundMatch);
	}

	private void assignWinnerToNextRoundMatch(SinglesMatch nextRoundMatch, LeagueParticipant winner) {
		if (nextRoundMatch.getLeagueParticipant1() == null) {
			nextRoundMatch.defineLeagueParticipant1(winner);
			return;
		}
		if (nextRoundMatch.getLeagueParticipant2() == null) {
			nextRoundMatch.defineLeagueParticipant2(winner);
		}
	}

	private LeagueParticipant determineWinner(SinglesMatch match) {
		if (match.getMatchStatus() == MatchStatus.BYE) {
			return match.getLeagueParticipant1();
		}
		if (match.getPlayer1MatchResult() == MatchResult.WIN) {
			return match.getLeagueParticipant1();
		}
		if (match.getPlayer2MatchResult() == MatchResult.WIN) {
			return match.getLeagueParticipant2();
		}
		return null;
	}
}
