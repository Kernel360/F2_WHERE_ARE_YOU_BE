package org.badminton.infrastructure.match.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.badminton.domain.common.enums.MatchResult;
import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.enums.SetStatus;
import org.badminton.domain.common.exception.match.AlreadyWinnerDeterminedException;
import org.badminton.domain.common.exception.match.LeagueParticipantsNotExistsException;
import org.badminton.domain.common.exception.match.PreviousDetNotFinishedException;
import org.badminton.domain.common.exception.match.RegisterScoreInByeMatchException;
import org.badminton.domain.common.exception.match.RoundNotFinishedException;
import org.badminton.domain.common.exception.match.SetFinishedException;
import org.badminton.domain.domain.league.LeagueParticipantReader;
import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.league.vo.Team;
import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.DoublesSet;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.badminton.domain.domain.match.info.SetInfo.Main;
import org.badminton.domain.domain.match.reader.DoublesMatchStore;
import org.badminton.domain.domain.match.service.AbstractDoublesMatchStrategy;
import org.badminton.domain.domain.match.store.DoublesMatchReader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TournamentDoublesMatchStrategy extends AbstractDoublesMatchStrategy {

	public static final int SET_COUNT = 3;
	public static final int PARTICIPANTS_PER_TEAM = 2;
	public static final int TEAMS_PER_MATCH = 2;
	static final Integer LIMIT_SET_GAME = 3;
	private final DoublesMatchReader doublesMatchReader;
	private final DoublesMatchStore doublesMatchStore;
	private final LeagueParticipantReader leagueParticipantReader;
	private final LeagueReader leagueReader;

	public TournamentDoublesMatchStrategy(DoublesMatchReader doublesMatchReader, DoublesMatchStore doublesMatchStore,
		LeagueParticipantReader leagueParticipantReader, LeagueReader leagueReader) {
		super(doublesMatchReader, doublesMatchStore);
		this.doublesMatchReader = doublesMatchReader;
		this.doublesMatchStore = doublesMatchStore;
		this.leagueParticipantReader = leagueParticipantReader;
		this.leagueReader = leagueReader;
	}

	private static boolean isMatchWinnerDetermined(DoublesMatch doublesMatch) {
		return doublesMatch.getTeam1MatchResult() == MatchResult.WIN
			|| doublesMatch.getTeam2MatchResult() == MatchResult.WIN;
	}

	private static boolean isParticipantOddSize(List<LeagueParticipant> participants) {
		return participants.size() % 4 != 0;
	}

	private static boolean isDoubleMatchOddSize(List<DoublesMatch> doublesMatches) {
		return doublesMatches.size() % 2 != 0;
	}

	@Override
	public BracketInfo makeBracket(League league, List<LeagueParticipant> leagueParticipantList) {
		List<DoublesMatch> allMatches = new ArrayList<>();
		List<LeagueParticipant> currentParticipants = new ArrayList<>(leagueParticipantList);
		Collections.shuffle(currentParticipants);

		int totalRounds = MatchUtils.calculateTotalRounds(currentParticipants.size() / PARTICIPANTS_PER_TEAM);
		league.defineTotalRounds(totalRounds);

		allMatches.addAll(createFirstRoundMatches(league, currentParticipants));
		allMatches.addAll(createSubsequentRoundsMatches(league, totalRounds));

		allMatches.stream()
			.filter(doublesMatch -> doublesMatch.getMatchStatus() == MatchStatus.BYE && doublesMatch.getTeam1() != null)
			.forEach(this::updateNextRoundMatch);

		return BracketInfo.fromDoubles(totalRounds, allMatches);
	}

	@Override
	@Transactional
	public SetInfo.Main registerSetScoreInMatch(Long matchId, Integer setNumber,
		MatchCommand.UpdateSetScore updateSetScoreCommand) {
		DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);

		validatePreviousSetCompletion(setNumber, doublesMatch);

		validatePreviousRoundCompletion(doublesMatch.getLeague().getLeagueId(), doublesMatch.getRoundNumber());

		if (doublesMatch.getMatchStatus() == MatchStatus.BYE) {
			throw new RegisterScoreInByeMatchException(doublesMatch.getId(), MatchType.DOUBLES);
		}

		if (isMatchWinnerDetermined(doublesMatch)) {
			throw new AlreadyWinnerDeterminedException(doublesMatch.getId());
		}

		if (doublesMatch.getDoublesSet(setNumber).getSetStatus() == SetStatus.FINISHED) {
			throw new SetFinishedException(setNumber);
		}

		if (doublesMatch.getTeam1() == null || doublesMatch.getTeam2() == null) {
			throw new LeagueParticipantsNotExistsException(matchId);
		}

		updateSetScore(doublesMatch, setNumber, updateSetScoreCommand);

		if (LIMIT_SET_GAME > setNumber) {
			changeNextSetStatus(doublesMatch, setNumber);
		}

		if (isMatchWinnerDetermined(doublesMatch)) {
			processMatchAndNextRound(doublesMatch);
		}
		if (isAllMatchFinished(doublesMatch)) {
			leagueReader.readLeagueById(doublesMatch.getLeague().getLeagueId()).finishLeague();
		}

		doublesMatchStore.store(doublesMatch);
		return SetInfo.fromDoublesSet(matchId, setNumber, doublesMatch.getDoublesSets().get(setNumber - 1));
	}

	private void validatePreviousRoundCompletion(Long leagueId, int roundNumber) {

		if (roundNumber == 1) {
			return;
		}

		if (!doublesMatchReader.allRoundMatchesDone(leagueId, roundNumber - 1)) {
			throw new RoundNotFinishedException(roundNumber - 1);
		}
	}

	private void validatePreviousSetCompletion(int setNumber, DoublesMatch doublesMatch) {

		if (setNumber == 1) {
			return;
		}
		if (doublesMatch.getDoublesSet(setNumber - 1).getSetStatus() != SetStatus.FINISHED) {
			throw new PreviousDetNotFinishedException(setNumber - 1);
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
	public Main retrieveSet(Long matchId, int setNumber) {
		DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);
		DoublesSet doublesSet = doublesMatch.getDoublesSet(setNumber);
		return SetInfo.fromDoublesSet(matchId, setNumber, doublesSet);
	}

	private void processMatchAndNextRound(DoublesMatch doublesMatch) {
		doublesMatchStore.store(doublesMatch);
		updateNextRoundMatch(doublesMatch);
		boolean allRoundMatchesDone = doublesMatchReader.allRoundMatchesDone(doublesMatch.getLeague().getLeagueId(),
			doublesMatch.getRoundNumber());
		boolean currentRoundInTotalRound =
			doublesMatch.getRoundNumber() < doublesMatch.getLeague().getTotalRounds();

		if (allRoundMatchesDone && currentRoundInTotalRound) {
			List<DoublesMatch> nextRoundMatches = doublesMatchReader.findMatchesByLeagueAndRound(
				doublesMatch.getLeague().getLeagueId(), doublesMatch.getRoundNumber() + 1
			);
			nextRoundMatches.stream()
				.filter(match -> match.getMatchStatus() == MatchStatus.BYE)
				.forEach(this::updateNextRoundMatch);
		}
	}

	private List<DoublesMatch> createFirstRoundMatches(League league, List<LeagueParticipant> participants) {
		List<DoublesMatch> matches = new ArrayList<>();

		for (int i = 0; i < participants.size() - 2; i += PARTICIPANTS_PER_TEAM * TEAMS_PER_MATCH) {
			Team team1 = new Team(participants.get(i), participants.get(i + 1));
			Team team2 = new Team(participants.get(i + 2), participants.get(i + 3));
			DoublesMatch match = new DoublesMatch(league, team1, team2, 1);
			makeSetsInMatch(match);
			doublesMatchStore.store(match);
			matches.add(match);
		}

		if (isParticipantOddSize(participants)) {
			Team byeTeam = new Team(participants.remove(participants.size() - 1),
				participants.get(participants.size() - 2));
			DoublesMatch byeMatch = new DoublesMatch(league, byeTeam, null, 1);
			byeMatch.byeMatch();
			doublesMatchStore.store(byeMatch);
			matches.add(byeMatch);
		}
		return matches;
	}

	private List<DoublesMatch> createMatchesRound(League league, List<DoublesMatch> previousRoundMatches,
		int roundNumber) {
		List<DoublesMatch> currentRoundMatches = new ArrayList<>();

		currentRoundMatches.addAll(createRegularMatchesForRound(league, previousRoundMatches, roundNumber));

		if (isDoubleMatchOddSize(previousRoundMatches)) {
			currentRoundMatches.add(createByeRoundMatch(league, previousRoundMatches, roundNumber));
		}

		return currentRoundMatches;
	}

	private List<DoublesMatch> createRegularMatchesForRound(League league, List<DoublesMatch> previousRoundMatches,
		int roundNumber) {
		List<DoublesMatch> regularRoundMatches = new ArrayList<>();

		for (int i = 0; i < previousRoundMatches.size() - 1; i += TEAMS_PER_MATCH) {
			DoublesMatch match = new DoublesMatch(league, null, null, roundNumber);
			makeSetsInMatch(match);
			doublesMatchStore.store(match);
			regularRoundMatches.add(match);
		}

		return regularRoundMatches;
	}

	private DoublesMatch createByeRoundMatch(League league, List<DoublesMatch> previousRoundMatches,
		int roundNumber) {
		DoublesMatch byeMatch = previousRoundMatches.get(previousRoundMatches.size() - 1);
		Team winner = determineWinner(byeMatch);

		DoublesMatch nextByeMatch = new DoublesMatch(league, winner, null, roundNumber);
		nextByeMatch.byeMatch();
		doublesMatchStore.store(nextByeMatch);

		return nextByeMatch;
	}

	private List<DoublesMatch> createSubsequentRoundsMatches(League league, int totalRounds) {
		List<DoublesMatch> matches = new ArrayList<>();
		List<DoublesMatch> previousMatches = doublesMatchReader.findMatchesByLeagueAndRound(league.getLeagueId(), 1);

		for (int roundNumber = 2; roundNumber <= totalRounds; roundNumber++) {
			List<DoublesMatch> currentRoundMatches = createMatchesRound(league, previousMatches, roundNumber);
			matches.addAll(currentRoundMatches);
			previousMatches = currentRoundMatches;
		}

		return matches;
	}

	private void makeSetsInMatch(DoublesMatch doublesMatch) {
		for (int i = 1; i <= SET_COUNT; i++) {
			DoublesSet set = new DoublesSet(doublesMatch, i);
			doublesMatch.addSet(set);
		}
		doublesMatchStore.store(doublesMatch);
	}

	private void updateSetScore(DoublesMatch doublesMatch, int setNumber,
		MatchCommand.UpdateSetScore updateSetScoreCommand) {
		DoublesSet set = doublesMatch.getDoublesSet(setNumber);
		set.endSetScore(updateSetScoreCommand.getScore1(), updateSetScoreCommand.getScore2());

		if (updateSetScoreCommand.getScore1() > updateSetScoreCommand.getScore2()) {
			doublesMatch.team1WinSet();
		} else {
			doublesMatch.team2WinSet();
		}
	}

	private void updateNextRoundMatch(DoublesMatch doublesMatch) {
		Team winner = determineWinner(doublesMatch);
		if (winner == null) {
			return;
		}
		int totalRounds = doublesMatch.getLeague().getTotalRounds();
		if (doublesMatch.getRoundNumber() == totalRounds) {
			return;
		}

		DoublesMatch startMatch = doublesMatchReader.findFirstMatchByLeagueId(
			doublesMatch.getLeague().getLeagueId());

		int nextRoundMatchId = MatchUtils.calculateNextRoundMatchId(Math.toIntExact(doublesMatch.getId()),
			leagueParticipantReader.countParticipantMember(doublesMatch.getLeague().getLeagueId())
				/ PARTICIPANTS_PER_TEAM,
			Math.toIntExact(startMatch.getId()));

		DoublesMatch nextRoundMatch = doublesMatchReader.getDoublesMatch((long)nextRoundMatchId);

		if (nextRoundMatch == null) {
			return;
		}

		if (doublesMatch.getMatchStatus() == MatchStatus.BYE) {
			nextRoundMatch.defineTeam1(winner);
			doublesMatchStore.store(nextRoundMatch);
			return;
		}

		assignWinnerToNextRoundMatch(nextRoundMatch, winner);
		doublesMatchStore.store(nextRoundMatch);
	}

	private void assignWinnerToNextRoundMatch(DoublesMatch nextRoundMatch, Team winner) {
		if (nextRoundMatch.getTeam1() == null) {
			nextRoundMatch.defineTeam1(winner);
			return;
		}
		if (nextRoundMatch.getTeam2() == null) {
			nextRoundMatch.defineTeam2(winner);
		}

	}

	private Team determineWinner(DoublesMatch match) {
		if (match.getMatchStatus() == MatchStatus.BYE || match.getTeam1MatchResult() == MatchResult.WIN) {
			return match.getTeam1();
		}

		if (match.getTeam2MatchResult() == MatchResult.WIN) {
			return match.getTeam2();
		}
		return null;
	}
}
