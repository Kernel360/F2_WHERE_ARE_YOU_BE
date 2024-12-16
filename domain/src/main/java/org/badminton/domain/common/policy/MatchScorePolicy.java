package org.badminton.domain.common.policy;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.enums.SetStatus;
import org.badminton.domain.common.exception.match.CannotStartMatchException;
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
			SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(leagueId, matchId);
			validateSinglesPreviousAndCurrentSetStatus(leagueId, singlesMatch, setNumber);
			if (league.getMatchGenerationType() == MatchGenerationType.TOURNAMENT) {
				validateTournamentSinglesPreviousRoundDone(leagueId, singlesMatch);
			}
		} else if (league.getMatchType() == MatchType.DOUBLES) {
			DoublesMatch doublesMatch = doublesMatchReader.readDoublesMatch(leagueId, matchId);
			validateDoublesPreviousAndCurrentSetStatus(leagueId, doublesMatch, setNumber);
			if (league.getMatchGenerationType() == MatchGenerationType.TOURNAMENT) {
				validateTournamentDoublesPreviousRoundDone(leagueId, doublesMatch);
			}
		}
	}

	public void validateMatchStartAvailable(Long leagueId, Long matchId) {
		League league = leagueReader.readLeagueById(leagueId);
		if (league.getMatchType() == MatchType.SINGLES) {
			SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(leagueId, matchId);
			if (singlesMatch.getSinglesSet(1).getSetStatus() == SetStatus.FINISHED) {
				throw new SetFinishedException(1);
			}
			if (singlesMatch.getMatchStatus() != MatchStatus.NOT_STARTED) {
				throw new CannotStartMatchException(matchId, singlesMatch.getMatchStatus());
			}
			if (league.getMatchGenerationType() == MatchGenerationType.TOURNAMENT) {
				validateTournamentSinglesPreviousRoundDone(leagueId, singlesMatch);
			}
		} else if (league.getMatchType() == MatchType.DOUBLES) {
			DoublesMatch doublesMatch = doublesMatchReader.readDoublesMatch(leagueId, matchId);
			if (doublesMatch.getDoublesSet(1).getSetStatus() == SetStatus.FINISHED) {
				throw new SetFinishedException(1);
			}
			if (doublesMatch.getMatchStatus() != MatchStatus.NOT_STARTED) {
				throw new CannotStartMatchException(matchId, doublesMatch.getMatchStatus());
			}
			if (league.getMatchGenerationType() == MatchGenerationType.TOURNAMENT) {
				validateTournamentDoublesPreviousRoundDone(leagueId, doublesMatch);
			}
		}
	}

	private void validateSinglesPreviousAndCurrentSetStatus(Long leagueId, SinglesMatch singlesMatch, int setNumber) {

		if (singlesMatch.getSinglesSet(setNumber).getSetStatus() == SetStatus.FINISHED) {
			throw new SetFinishedException(setNumber);
		}

		if (setNumber == 1) {
			return;
		}

		if (singlesMatch.getSinglesSet(setNumber - 1).getSetStatus() != SetStatus.FINISHED) {
			throw new PreviousSetNotFinishedException(setNumber - 1);
		}
	}

	private void validateTournamentSinglesPreviousRoundDone(Long leagueId, SinglesMatch singlesMatch) {

		if (singlesMatch.getLeagueParticipant1() == null || singlesMatch.getLeagueParticipant2() == null) {
			throw new LeagueParticipantNotDeterminedException(singlesMatch.getId());
		}

		int roundNumber = singlesMatch.getRoundNumber();
		if (roundNumber == 1) {
			return;
		}

		if (!singlesMatchReader.allRoundMatchesDone(leagueId, roundNumber - 1)) {
			throw new RoundNotFinishedException(roundNumber - 1);
		}
	}

	private void validateDoublesPreviousAndCurrentSetStatus(Long leagueId, DoublesMatch doublesMatch, int setNumber) {

		if (doublesMatch.getDoublesSet(setNumber).getSetStatus() == SetStatus.FINISHED) {
			throw new SetFinishedException(setNumber);
		}

		if (setNumber == 1) {
			return;
		}

		if (doublesMatch.getDoublesSet(setNumber - 1).getSetStatus() != SetStatus.FINISHED) {
			throw new PreviousSetNotFinishedException(setNumber - 1);
		}
	}

	private void validateTournamentDoublesPreviousRoundDone(Long leagueId, DoublesMatch doublesMatch) {

		if (doublesMatch.getTeam1() == null || doublesMatch.getTeam2() == null) {
			throw new LeagueParticipantNotDeterminedException(doublesMatch.getId());
		}

		int roundNumber = doublesMatch.getRoundNumber();
		if (roundNumber == 1) {
			return;
		}

		if (!doublesMatchReader.allRoundMatchesDone(leagueId, roundNumber - 1)) {
			throw new RoundNotFinishedException(roundNumber - 1);
		}
	}
}
