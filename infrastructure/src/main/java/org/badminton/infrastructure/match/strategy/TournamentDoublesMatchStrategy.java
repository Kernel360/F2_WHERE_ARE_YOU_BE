package org.badminton.infrastructure.match.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.badminton.domain.common.enums.MatchResult;
import org.badminton.domain.common.enums.SetStatus;
import org.badminton.domain.common.exception.match.AlreadyWinnerDeterminedException;
import org.badminton.domain.common.exception.match.BeforeSetNotFinishedException;
import org.badminton.domain.common.exception.match.LeagueParticipantsNotExistsException;
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

	@Override
	public BracketInfo makeBracket(League league, List<LeagueParticipant> leagueParticipantList) {
		List<DoublesMatch> allMatches = new ArrayList<>();
		List<LeagueParticipant> currentParticipants = new ArrayList<>(leagueParticipantList);
		Collections.shuffle(currentParticipants);

		int totalRounds = MatchUtils.calculateTotalRounds(currentParticipants.size() / PARTICIPANTS_PER_TEAM);
		league.defineTotalRounds(totalRounds);

		allMatches.addAll(createFirstRoundMatches(league, currentParticipants));
		allMatches.addAll(createSubsequentRoundsMatches(league, totalRounds));

		return BracketInfo.fromDoubles(totalRounds, allMatches);
	}

	@Override
	@Transactional
	public SetInfo.Main registerSetScoreInMatch(Long matchId, Integer setNumber,
		MatchCommand.UpdateSetScore updateSetScoreCommand) {
		DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);

		validatePreviousSetCompletion(setNumber, doublesMatch);

		validatePreviousRoundCompletion(doublesMatch.getLeague().getLeagueId(), doublesMatch.getRoundNumber());

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
			doublesMatchStore.store(doublesMatch);
			updateNextRoundMatch(doublesMatch);
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
			throw new BeforeSetNotFinishedException(setNumber - 1);
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

	private List<DoublesMatch> createFirstRoundMatches(League league, List<LeagueParticipant> participants) {
		List<DoublesMatch> matches = new ArrayList<>();
		for (int i = 0; i < participants.size(); i += PARTICIPANTS_PER_TEAM * TEAMS_PER_MATCH) {
			Team team1 = new Team(participants.get(i), participants.get(i + 1));
			Team team2 = new Team(participants.get(i + 2), participants.get(i + 3));
			DoublesMatch match = new DoublesMatch(league, team1, team2, 1);
			makeSetsInMatch(match);
			doublesMatchStore.store(match);
			matches.add(match);
		}
		return matches;
	}

	private List<DoublesMatch> createRoundMatches(League league, List<DoublesMatch> previousMatches, int roundNumber) {
		List<DoublesMatch> currentRoundMatches = new ArrayList<>();
		for (int i = 0; i < previousMatches.size(); i += TEAMS_PER_MATCH) {
			DoublesMatch match = new DoublesMatch(league, null, null, roundNumber);
			makeSetsInMatch(match);
			doublesMatchStore.store(match);
			currentRoundMatches.add(match);
		}
		return currentRoundMatches;
	}

	private List<DoublesMatch> createSubsequentRoundsMatches(League league, int totalRounds) {
		List<DoublesMatch> matches = new ArrayList<>();
		List<DoublesMatch> previousMatches = doublesMatchReader.findMatchesByLeagueAndRound(league.getLeagueId(), 1);

		for (int roundNumber = 2; roundNumber <= totalRounds; roundNumber++) {
			List<DoublesMatch> currentRoundMatches = createRoundMatches(league, previousMatches, roundNumber);
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

	private void updateSetScore(DoublesMatch doublesMatch, int setIndex,
		MatchCommand.UpdateSetScore updateSetScoreCommand) {
		DoublesSet set = doublesMatch.getDoublesSet(setIndex);
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
		if (nextRoundMatch != null) {
			assignWinnerToNextRoundMatch(nextRoundMatch, winner);
			doublesMatchStore.store(nextRoundMatch);
		}

	}

	private void assignWinnerToNextRoundMatch(DoublesMatch nextRoundMatch, Team winner) {
		if (nextRoundMatch.getTeam1() == null) {
			nextRoundMatch.defineTeam1(winner);
		} else {
			nextRoundMatch.defineTeam2(winner);
		}
	}

	private Team determineWinner(DoublesMatch match) {
		if (match.getTeam1MatchResult() == MatchResult.WIN) {
			return match.getTeam1();
		}
		if (match.getTeam2MatchResult() == MatchResult.WIN) {
			return match.getTeam2();
		}
		return null;
	}
}
