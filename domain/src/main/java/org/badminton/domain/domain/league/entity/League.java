package org.badminton.domain.domain.league.entity;

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
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.badminton.domain.common.AbstractBaseTime;
import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.member.entity.Member;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "league")
public class League extends AbstractBaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leagueId;

    private String leagueName;

    private String description;

    private String leagueLocation;

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

    public League(String leagueName, String description, String leagueLocation, LocalDateTime leagueAt,
                  Member.MemberTier tierLimit, LocalDateTime recruitingClosedAt, int playerLimitCount,
                  MatchType matchType, MatchGenerationType matchGenerationType, Club club) {
        this.leagueName = leagueName;
        this.description = description;
        this.leagueLocation = leagueLocation;
        this.leagueAt = leagueAt;
        this.requiredTier = tierLimit;
        this.recruitingClosedAt = recruitingClosedAt;
        this.leagueStatus = LeagueStatus.RECRUITING;
        this.playerLimitCount = playerLimitCount;
        this.matchType = matchType;
        this.matchGenerationType = matchGenerationType;
        this.club = club;
    }

    public void completeLeagueRecruiting() {
        this.leagueStatus = LeagueStatus.RECRUITING_COMPLETED;
    }

    public void cancelLeague() {
        this.leagueStatus = LeagueStatus.CANCELED;
    }
}