package org.badminton.domain.domain.match.entity;

import static org.badminton.domain.common.consts.Constants.INITIAL_WIN_SET_COUNT;
import static org.badminton.domain.common.consts.Constants.SETS_REQUIRED_TO_WIN_MATCH;

import jakarta.persistence.CascadeType;
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
import org.badminton.domain.common.BaseTimeEntity;
import org.badminton.domain.common.enums.MatchResult;
import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipantEntity;

@Entity
@Table(name = "singles_match")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SinglesMatch extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leagueId")
    private League league;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leagueParticipant1Id")
    private LeagueParticipantEntity leagueParticipant1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leagueParticipant2Id")
    private LeagueParticipantEntity leagueParticipant2;

    private int player1WinSetCount;
    private int player2WinSetCount;

    @Enumerated(EnumType.STRING)
    private MatchResult player1MatchResult = MatchResult.NONE;

    @Enumerated(EnumType.STRING)
    private MatchResult player2MatchResult = MatchResult.NONE;

    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus = MatchStatus.NOT_STARTED;

    @OneToMany(mappedBy = "singlesMatch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SinglesSetEntity> singlesSets;

    public SinglesMatch(League league, LeagueParticipantEntity leagueParticipant1,
                        LeagueParticipantEntity leagueParticipant2) {
        this.league = league;
        this.leagueParticipant1 = leagueParticipant1;
        this.leagueParticipant2 = leagueParticipant2;
        this.singlesSets = new ArrayList<>();
        this.player1WinSetCount = INITIAL_WIN_SET_COUNT;
        this.player2WinSetCount = INITIAL_WIN_SET_COUNT;
    }

    public void addSet(SinglesSetEntity singlesSet) {
        this.singlesSets.add(singlesSet);
    }

    public void player1WinSet() {
        this.player1WinSetCount++;
        if (player1WinSetCount == SETS_REQUIRED_TO_WIN_MATCH) {
            this.player1MatchResult = MatchResult.WIN;
            this.player2MatchResult = MatchResult.LOSE;
            this.matchStatus = MatchStatus.COMPLETED;
        }
    }

    public void player2WinSet() {
        this.player2WinSetCount++;
        if (player2WinSetCount == SETS_REQUIRED_TO_WIN_MATCH) {
            this.player2MatchResult = MatchResult.WIN;
            this.player1MatchResult = MatchResult.LOSE;
            this.matchStatus = MatchStatus.COMPLETED;
        }
    }
}