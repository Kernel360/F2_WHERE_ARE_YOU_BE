package org.badminton.domain.common.policy;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.enums.SetStatus;
import org.badminton.domain.common.exception.match.LeagueParticipantNotDeterminedException;
import org.badminton.domain.common.exception.match.PreviousSetNotFinishedException;
import org.badminton.domain.common.exception.match.RoundNotFinishedException;
import org.badminton.domain.common.exception.match.SetFinishedException;
import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.reader.DoublesMatchReader;
import org.badminton.domain.domain.match.reader.SinglesMatchReader;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MatchScorePolicy {

	private final LeagueReader leagueReader;
	private final SinglesMatchReader singlesMatchReader;
	private final DoublesMatchReader doublesMatchReader;

	public void validateScoreUpdateAvailable(Long leagueId, Long matchId, int setNumber) {

		League league = leagueReader.readLeagueById(leagueId);
		if (league.getMatchType() == MatchType.SINGLES) {
			validateSinglesPreviousAndCurrentSetStatus(leagueId, matchId, setNumber);
			if (league.getMatchGenerationType() == MatchGenerationType.TOURNAMENT) {
				validateTournamentSinglesPreviousRoundDone(leagueId, matchId);
			}
		} else if (league.getMatchType() == MatchType.DOUBLES) {
			validateDoublesPreviousAndCurrentSetStatus(leagueId, matchId, setNumber);
			if (league.getMatchGenerationType() == MatchGenerationType.TOURNAMENT) {
				validateTournamentDoublesPreviousRoundDone(leagueId, matchId);
			}
		}
	}

	public void validateMatchStartAvailable(Long leagueId, Long matchId) {
		League league = leagueReader.readLeagueById(leagueId);
		if (league.getMatchType() == MatchType.SINGLES
			&& league.getMatchGenerationType() == MatchGenerationType.TOURNAMENT) {
			validateTournamentSinglesPreviousRoundDone(leagueId, matchId);
		} else if (league.getMatchType() == MatchType.DOUBLES
			&& league.getMatchGenerationType() == MatchGenerationType.TOURNAMENT) {
			validateTournamentDoublesPreviousRoundDone(leagueId, matchId);
		}
	}

	private void validateSinglesPreviousAndCurrentSetStatus(Long leagueId, Long matchId, int setNumber) {
		SinglesMatch match = singlesMatchReader.getSinglesMatch(leagueId, matchId);

		if (match.getSinglesSet(setNumber).getSetStatus() == SetStatus.FINISHED) {
			throw new SetFinishedException(setNumber);
		}

		if (setNumber == 1) {
			return;
		}

		if (match.getSinglesSet(setNumber - 1).getSetStatus() != SetStatus.FINISHED) {
			throw new PreviousSetNotFinishedException(setNumber - 1);
		}
	}

	private void validateTournamentSinglesPreviousRoundDone(Long leagueId, Long matchId) {
		SinglesMatch match = singlesMatchReader.getSinglesMatch(leagueId, matchId);

		if (match.getLeagueParticipant1() == null || match.getLeagueParticipant2() == null) {
			throw new LeagueParticipantNotDeterminedException(matchId);
		}

		int roundNumber = match.getRoundNumber();
		if (roundNumber == 1) {
			return;
		}

		if (!singlesMatchReader.allRoundMatchesDone(leagueId, roundNumber - 1)) {
			throw new RoundNotFinishedException(roundNumber - 1);
		}
	}

	private void validateDoublesPreviousAndCurrentSetStatus(Long leagueId, Long matchId, int setNumber) {
		DoublesMatch match = doublesMatchReader.readDoublesMatch(leagueId, matchId);

		if (match.getDoublesSet(setNumber).getSetStatus() == SetStatus.FINISHED) {
			throw new SetFinishedException(setNumber);
		}

		if (setNumber == 1) {
			return;
		}

		if (match.getDoublesSet(setNumber - 1).getSetStatus() != SetStatus.FINISHED) {
			throw new PreviousSetNotFinishedException(setNumber - 1);
		}
	}

	private void validateTournamentDoublesPreviousRoundDone(Long leagueId, Long matchId) {
		DoublesMatch match = doublesMatchReader.readDoublesMatch(leagueId, matchId);

		if (match.getTeam1() == null || match.getTeam2() == null) {
			throw new LeagueParticipantNotDeterminedException(matchId);
		}

		int roundNumber = match.getRoundNumber();
		if (roundNumber == 1) {
			return;
		}

		if (!doublesMatchReader.allRoundMatchesDone(leagueId, roundNumber - 1)) {
			throw new RoundNotFinishedException(roundNumber - 1);
		}
	}
}
