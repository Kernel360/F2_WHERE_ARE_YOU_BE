package org.badminton.domain.domain.league.entity;

import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.common.BaseTimeEntity;
import org.badminton.domain.domain.member.entity.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "league_record")
public class LeagueRecord extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leagueRecordId;

    private int winCount;

    private int loseCount;

    private int drawCount;

    private int matchCount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    public LeagueRecord(Member member) {
        this.member = member;
        this.winCount = 0;
        this.loseCount = 0;
        this.drawCount = 0;
        this.matchCount = 0;
    }

    public void updateWinCount(int winCount) {
        this.winCount = winCount;
    }

    public void updateLoseCount(int loseCount) {
        this.loseCount = loseCount;
    }

    public void updateDrawCount(int drawCount) {
        this.drawCount = drawCount;
    }

    public void updateMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

}
