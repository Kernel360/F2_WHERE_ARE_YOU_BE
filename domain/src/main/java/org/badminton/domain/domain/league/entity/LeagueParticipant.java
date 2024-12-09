package org.badminton.domain.domain.league.entity;

import org.badminton.domain.common.AbstractBaseTime;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.member.entity.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "league_participant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LeagueParticipant extends AbstractBaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long leagueParticipantId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "clubMemberId")
	private ClubMember clubMember;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "memberId")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "leagueId")
	private League league;

	private boolean canceled;

	public LeagueParticipant(ClubMember clubMember, League league) {
		this.clubMember = clubMember;
		this.league = league;
		this.canceled = false;
		this.member = clubMember.getMember();
	}

	public static LeagueParticipant emptyParticipant() {
		return null;
	}

	public static LeagueParticipant emptyWinner() {
		return null;
	}

	public void cancelLeagueParticipation() {
		this.canceled = true;
	}

}
