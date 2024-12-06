package org.badminton.domain.domain.league;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.match.store.DoublesMatchReader;
import org.badminton.domain.domain.match.store.SinglesMatchReader;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeagueParticipantCancelProcessor {
	private final DoublesMatchReader doublesMatchReader;
	private final SinglesMatchReader singlesMatchReader;
	private final LeagueParticipantStore leagueParticipantStore;

	public void cancel(LeagueParticipant leagueParticipant) {
		League league = leagueParticipant.getLeague();

		if (isRecruiting(league)) {
			cancelParticipant(leagueParticipant);
			return;
		}

		if (isRecruitingCompletedWithNonEmptyBracket(league)) {
			cancelParticipant(leagueParticipant);
		}
	}

	private boolean isRecruiting(League league) {
		return league.getLeagueStatus() == LeagueStatus.RECRUITING;
	}

	private boolean isRecruitingCompletedWithNonEmptyBracket(League league) {
		return league.getLeagueStatus() == LeagueStatus.RECRUITING_COMPLETED && isBracketNotEmpty(league);
	}

	private boolean isBracketNotEmpty(League league) {
		return !isBracketEmpty(league);
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
