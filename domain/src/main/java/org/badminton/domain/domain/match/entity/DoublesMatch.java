package org.badminton.domain.domain.match.entity;

import static org.badminton.domain.common.consts.Constants.INITIAL_WIN_SET_COUNT;
import static org.badminton.domain.common.consts.Constants.SETS_REQUIRED_TO_WIN_MATCH;

import jakarta.persistence.AssociationOverride;
import jakarta.persistence.AssociationOverrides;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.badminton.domain.common.AbstractBaseTime;
import org.badminton.domain.common.enums.MatchResult;
import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.vo.Team;

@Entity
@Table(name = "doubles_match")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DoublesMatch extends AbstractBaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leagueId")
    private League league;

    @Embedded
    @AssociationOverrides({
            @AssociationOverride(name = "leagueParticipant1", joinColumns = @JoinColumn(name = "team1Participant1Id")),
            @AssociationOverride(name = "leagueParticipant2", joinColumns = @JoinColumn(name = "team1Participant2Id"))
    })
    private Team team1;

    @Embedded
    @AssociationOverrides({
            @AssociationOverride(name = "leagueParticipant1", joinColumns = @JoinColumn(name = "team2Participant1Id")),
            @AssociationOverride(name = "leagueParticipant2", joinColumns = @JoinColumn(name = "team2Participant2Id"))
    })
    private Team team2;

    private int team1WinSetCount;
    private int team2WinSetCount;

    @Enumerated(EnumType.STRING)
    private MatchResult team1MatchResult = MatchResult.NONE;

    @Enumerated(EnumType.STRING)
    private MatchResult team2MatchResult = MatchResult.NONE;

    @OneToMany(mappedBy = "doublesMatch", cascade = CascadeType.ALL, orphanRemoval = true)
    List<DoublesSet> doublesSets;

    private int roundNumber;

    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus = MatchStatus.NOT_STARTED;

    public DoublesMatch(League league, Team team1, Team team2, int roundNumber) {
        this.league = league;
        this.team1 = team1;
        this.team2 = team2;
        this.doublesSets = new ArrayList<>();
        this.team1WinSetCount = INITIAL_WIN_SET_COUNT;
        this.team2WinSetCount = INITIAL_WIN_SET_COUNT;
        this.roundNumber = roundNumber;
    }

    public void addSet(DoublesSet doublesSet) {
        this.doublesSets.add(doublesSet);
    }

    public void team1WinSet() {
        this.team1WinSetCount++;
        if (team1WinSetCount == SETS_REQUIRED_TO_WIN_MATCH) {
            this.team1MatchResult = MatchResult.WIN;
            this.team2MatchResult = MatchResult.LOSE;
            this.matchStatus = MatchStatus.FINISHED;
        }
    }

    public void team2WinSet() {
        this.team2WinSetCount++;
        if (team2WinSetCount == SETS_REQUIRED_TO_WIN_MATCH) {
            this.team2MatchResult = MatchResult.WIN;
            this.team1MatchResult = MatchResult.LOSE;
            this.matchStatus = MatchStatus.FINISHED;
        }
    }

    public DoublesSet getDoublesSet(int setNumber) {
        return this.doublesSets.get(setNumber - 1);
    }
}
