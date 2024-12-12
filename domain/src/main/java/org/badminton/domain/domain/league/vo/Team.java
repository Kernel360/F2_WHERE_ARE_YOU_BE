package org.badminton.domain.domain.league.vo;

import org.badminton.domain.domain.league.entity.LeagueParticipant;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Embeddable
public class Team {

	@ManyToOne(fetch = FetchType.LAZY)
	private LeagueParticipant leagueParticipant1;

	@ManyToOne(fetch = FetchType.LAZY)
	private LeagueParticipant leagueParticipant2;

	protected Team() {
	}

	public Team(LeagueParticipant leagueParticipant1, LeagueParticipant leagueParticipant2) {
		this.leagueParticipant1 = leagueParticipant1;
		this.leagueParticipant2 = leagueParticipant2;
	}

	public static Team emptyParticipant() {
		return null;
	}

	public static Team emptyWinner() {
		return null;
	}

	public boolean isTeam(LeagueParticipant leagueParticipant) {
		return this.leagueParticipant1.equals(leagueParticipant) || this.leagueParticipant2.equals(leagueParticipant);
	}
}