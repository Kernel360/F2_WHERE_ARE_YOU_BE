package org.badminton.domain.domain.match.entity;

import static org.badminton.domain.common.consts.Constants.*;

import org.badminton.domain.common.AbstractBaseTime;
import org.badminton.domain.common.enums.SetStatus;

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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "doubles_set")
@NoArgsConstructor
public class DoublesSet extends AbstractBaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doublesMatchId")
	private DoublesMatch doublesMatch;

	private int setNumber;
	private int team1Score;
	private int team2Score;

	@Enumerated(EnumType.STRING)
	private SetStatus setStatus;

	public DoublesSet(DoublesMatch doublesMatch, int setNumber) {
		this.doublesMatch = doublesMatch;
		this.setNumber = setNumber;
		this.team1Score = INITIAL_SET_SCORE;
		this.team2Score = INITIAL_SET_SCORE;
		this.setStatus = SetStatus.NOT_STARTED;
	}

	public void saveSetScore(int team1Score, int team2Score) {
		this.team1Score = team1Score;
		this.team2Score = team2Score;
	}

	public void endSetScore(int team1Score, int team2Score) {
		this.team1Score = team1Score;
		this.team2Score = team2Score;
		close();
	}

	public void open() {
		this.setStatus = SetStatus.IN_PROGRESS;
	}

	public void close() {
		this.setStatus = SetStatus.FINISHED;
	}

	public void startSet() {
		this.setStatus = SetStatus.IN_PROGRESS;
	}
}
