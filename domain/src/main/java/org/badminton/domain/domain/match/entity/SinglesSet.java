package org.badminton.domain.domain.match.entity;

import static org.badminton.domain.common.consts.Constants.*;

import org.badminton.domain.common.AbstractBaseTime;
import org.badminton.domain.common.enums.SetStatus;

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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "singles_set")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SinglesSet extends AbstractBaseTime {
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "singlesMatchId")
	SinglesMatch singlesMatch;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int setNumber;
	private int player1Score;
	private int player2Score;

	@Enumerated(EnumType.STRING)
	private SetStatus setStatus;

	public SinglesSet(SinglesMatch singlesMatch, int setNumber) {
		this.singlesMatch = singlesMatch;
		this.setNumber = setNumber;
		this.player1Score = INITIAL_SET_SCORE;
		this.player2Score = INITIAL_SET_SCORE;
		this.setStatus = SetStatus.IN_PROGRESS;
	}

	public void saveSetScore(int player1Score, int player2Score) {
		this.player1Score = player1Score;
		this.player2Score = player2Score;
	}

	public void endSetScore(int player1Score, int player2Score) {
		this.player1Score = player1Score;
		this.player2Score = player2Score;
		close();
	}

	public void open() {
		this.setStatus = SetStatus.IN_PROGRESS;
	}

	public void close() {
		this.setStatus = SetStatus.FINISHED;
	}
}
