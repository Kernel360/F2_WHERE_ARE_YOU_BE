package org.badminton.domain.domain.league;

import java.util.List;
import java.util.Optional;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.reader.DoublesMatchReader;
import org.badminton.domain.domain.match.reader.SinglesMatchReader;
import org.badminton.domain.domain.match.store.DoublesMatchStore;
import org.badminton.domain.domain.match.store.DoublesMatchTournamentStore;
import org.badminton.domain.domain.match.store.SinglesMatchStore;
import org.badminton.domain.domain.match.store.SinglesMatchTournamentStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeagueParticipantCancelServiceImpl implements LeagueParticipantCancelService {
	private final DoublesMatchReader doublesMatchReader;
	private final SinglesMatchReader singlesMatchReader;
	private final LeagueParticipantStore leagueParticipantStore;
	private final LeagueParticipantReader leagueParticipantReader;
	private final DoublesMatchStore doublesMatchStore;
	private final SinglesMatchStore singlesMatchStore;
	private final SinglesMatchTournamentStore singlesMatchTournamentStore;
	private final DoublesMatchTournamentStore doublesMatchTournamentStore;

	@Override
	@Transactional
	public void cancelAllLeagueParticipants(Long clubMemberId) {
		List<LeagueParticipant> leagueParticipants =
			leagueParticipantReader.findAllByClubMemberIdAndCanceledFalse(clubMemberId);
		leagueParticipants.forEach(this::leagueParticipantCancel);
	}

	private void leagueParticipantCancel(LeagueParticipant leagueParticipant) {
		League league = leagueParticipant.getLeague();

		if (isRecruiting(league)) {
			cancelParticipant(leagueParticipant);
			return;
		}

		if (isRecruitingCompletedWithEmptyBracket(league)) {
			cancelParticipant(leagueParticipant);
		}

		if (!isBracketEmpty(league) && league.getMatchType() == MatchType.DOUBLES) {
			doublesMatchClose(leagueParticipant, league.getMatchGenerationType());
		}
		if (!isBracketEmpty(league) && league.getMatchType() == MatchType.SINGLES) {
			singlesMatchClose(leagueParticipant, league.getMatchGenerationType());
		}
	}

	private void doublesMatchClose(LeagueParticipant leagueParticipant, MatchGenerationType matchGenerationType) {
		Optional<DoublesMatch> doublesMatchOptional = doublesMatchReader.findMatchByLeagueParticipant(
			leagueParticipant);
		if (doublesMatchOptional.isPresent()) {
			DoublesMatch doublesMatch = doublesMatchOptional.get();
			doublesMatch.determineWinnerTeam(leagueParticipant);
			doublesMatch.closeMatchContainsBannedParticipant();
			doublesMatch.finishMatch();
			doublesMatchStore.store(doublesMatch);
			doublesMatchTournamentNextRoundSet(doublesMatch, matchGenerationType);
		}
	}

	private void singlesMatchClose(LeagueParticipant leagueParticipant, MatchGenerationType matchGenerationType) {
		Optional<SinglesMatch> singlesMatchOptional = singlesMatchReader.findMatchByLeagueParticipant(
			leagueParticipant);
		if (singlesMatchOptional.isPresent()) {
			SinglesMatch singlesMatch = singlesMatchOptional.get();
			singlesMatch.determineWinnerParticipant(leagueParticipant);
			singlesMatch.closeMatchContainsBannedParticipant();
			singlesMatch.finishMatch();
			singlesMatchStore.store(singlesMatch);
			singlesMatchTournamentNextRoundSet(singlesMatch, matchGenerationType);
		}

	}

	private void doublesMatchTournamentNextRoundSet(
		DoublesMatch doublesMatch,
		MatchGenerationType matchGenerationType) {
		if (matchGenerationType == MatchGenerationType.TOURNAMENT) {
			doublesMatchTournamentStore.updateDoublesMatchNextRoundMatch(doublesMatch);
		}
	}

	private void singlesMatchTournamentNextRoundSet(
		SinglesMatch singlesMatch,
		MatchGenerationType matchGenerationType) {
		if (matchGenerationType == MatchGenerationType.TOURNAMENT) {
			singlesMatchTournamentStore.updateSinglesMatchNextRoundMatch(singlesMatch);
		}
	}

	private boolean isRecruiting(League league) {
		return league.getLeagueStatus() == LeagueStatus.RECRUITING;
	}

	private boolean isRecruitingCompletedWithEmptyBracket(League league) {
		return league.getLeagueStatus() == LeagueStatus.RECRUITING_COMPLETED && isBracketEmpty(league);
	}

	private boolean isBracketEmpty(League league) {
		MatchType matchType = league.getMatchType();
		Long leagueId = league.getLeagueId();

		if (matchType == MatchType.DOUBLES) {
			return doublesMatchReader.checkIfBracketEmpty(leagueId);
		}
		if (matchType == MatchType.SINGLES) {
			return singlesMatchReader.checkIfBracketEmpty(leagueId);
		}
		return true;
	}

	private void cancelParticipant(LeagueParticipant leagueParticipant) {
		leagueParticipantStore.cancelStore(leagueParticipant);
	}
}
