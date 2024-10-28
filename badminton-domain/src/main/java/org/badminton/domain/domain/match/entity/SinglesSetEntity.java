package org.badminton.domain.domain.match.entity;

import static org.badminton.domain.common.consts.Constants.*;

import org.badminton.domain.common.BaseTimeEntity;

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
@Table(name = "singles_set")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SinglesSetEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long singlesSetId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "singlesMatchId")
    SinglesMatch singlesMatch;

    private int setIndex;
    private int player1Score;
    private int player2Score;

    public SinglesSetEntity(SinglesMatch singlesMatch, int setIndex) {
        this.singlesMatch = singlesMatch;
        this.setIndex = setIndex;
        this.player1Score = INITIAL_SET_SCORE;
        this.player2Score = INITIAL_SET_SCORE;
    }

    public void saveSetScore(int player1Score, int player2Score) {
        this.player1Score = player1Score;
        this.player2Score = player2Score;
    }
}
