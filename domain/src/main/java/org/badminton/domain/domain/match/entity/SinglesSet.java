package org.badminton.domain.domain.match.entity;

import static org.badminton.domain.common.consts.Constants.INITIAL_SET_SCORE;

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
import org.badminton.domain.common.AbstractBaseTime;
import org.badminton.domain.common.enums.SetStatus;

@Entity
@Table(name = "singles_set")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SinglesSet extends AbstractBaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "singlesMatchId")
    SinglesMatch singlesMatch;

    private int setIndex;
    private int player1Score;
    private int player2Score;

    private SetStatus setStatus;

    public SinglesSet(SinglesMatch singlesMatch, int setIndex) {
        this.singlesMatch = singlesMatch;
        this.setIndex = setIndex;
        this.player1Score = INITIAL_SET_SCORE;
        this.player2Score = INITIAL_SET_SCORE;
        this.setStatus = SetStatus.NOT_STARTED;
    }

    public void saveSetScore(int player1Score, int player2Score) {
        this.player1Score = player1Score;
        this.player2Score = player2Score;
    }

    public void open() {
        this.setStatus = SetStatus.IN_PROGRESS;
    }

    public void close() {
        this.setStatus = SetStatus.FINISHED;
    }
}
