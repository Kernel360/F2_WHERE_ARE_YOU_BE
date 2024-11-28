package org.badminton.domain.domain.league.entity;

import java.time.LocalDateTime;

import org.badminton.domain.common.AbstractBaseTime;
import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.exception.league.PlayerLimitCountDecreasedNotAllowedException;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.league.vo.Address;
import org.badminton.domain.domain.member.entity.Member;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "league")
public class League extends AbstractBaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long leagueId;

	private String leagueOwnerMemberToken;

	private String leagueName;

	@Column(columnDefinition = "TEXT", length = 1200)
	private String description;

	@Column(nullable = false, columnDefinition = "INT DEFAULT 1")
	private int totalRounds = 1;

	@Embedded
	@AttributeOverride(name = "fullAddress", column = @Column(name = "fullAddress"))
	@AttributeOverride(name = "region", column = @Column(name = "region"))
	private Address address;

	@Enumerated(EnumType.STRING)
	private Member.MemberTier requiredTier;

	@Enumerated(EnumType.STRING)
	private LeagueStatus leagueStatus;

	@Enumerated(EnumType.STRING)
	private MatchType matchType;

	private LocalDateTime leagueAt;

	private LocalDateTime recruitingClosedAt;

	private int playerLimitCount;

	@Enumerated(EnumType.STRING)
	private MatchGenerationType matchGenerationType;  // FREE, TOURNAMENT

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clubId")
	private Club club;

	public League(String leagueOwnerMemberToken, String leagueName, String description, Address address,
		LocalDateTime leagueAt,
		Member.MemberTier tierLimit, LocalDateTime recruitingClosedAt, int playerLimitCount,
		MatchType matchType,
		MatchGenerationType matchGenerationType, Club club) {
		this.leagueOwnerMemberToken = leagueOwnerMemberToken;
		this.leagueName = leagueName;
		this.description = description;
		this.address = address;
		this.leagueAt = leagueAt;
		this.requiredTier = tierLimit;
		this.recruitingClosedAt = recruitingClosedAt;
		this.leagueStatus = LeagueStatus.RECRUITING;
		this.playerLimitCount = playerLimitCount;
		this.matchType = matchType;
		this.matchGenerationType = matchGenerationType;
		this.club = club;
	}

	public void defineTotalRounds(int totalRounds) {
		this.totalRounds = totalRounds;
	}

	public void updateLeague(String leagueName, String leagueDescription,
		int playerLimitCount, MatchType matchType, MatchGenerationType matchGenerationType) {
		this.leagueName = leagueName;
		this.description = leagueDescription;
		validatePlayerLimitCountWhenUpdate(playerLimitCount);
		this.playerLimitCount = playerLimitCount;
		this.matchType = matchType;
		this.matchGenerationType = matchGenerationType;
	}

	private void validatePlayerLimitCountWhenUpdate(int playerLimitCount) {
		if (this.playerLimitCount > playerLimitCount) {
			throw new PlayerLimitCountDecreasedNotAllowedException(this.playerLimitCount, playerLimitCount);
		}
	}

	public void completeLeagueRecruiting() {
		this.leagueStatus = LeagueStatus.RECRUITING_COMPLETED;
	}

	public void reopenLeagueRecruiting() {
		this.leagueStatus = LeagueStatus.RECRUITING;
	}

	public void cancelLeague() {
		this.leagueStatus = LeagueStatus.CANCELED;
	}

	public void startLeague() {
		this.leagueStatus = LeagueStatus.PLAYING;
	}

	public void finishLeague() {
		this.leagueStatus = LeagueStatus.FINISHED;
	}
}
