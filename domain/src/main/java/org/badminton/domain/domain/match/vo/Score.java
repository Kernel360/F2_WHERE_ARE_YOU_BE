package org.badminton.domain.domain.match.vo;

import java.util.Optional;

import lombok.Getter;

@Getter
public class Score {

	private final int left;
	private final int right;

	public Score(int left, int right) {
		this.left = left;
		this.right = right;
	}

	public Score(String score) {
		String[] split = score.split(":");
		this.left = Integer.parseInt(split[0]);
		this.right = Integer.parseInt(split[1]);
	}

	public static Optional<Score> emptyScore() {
		return Optional.empty();
	}

	@Override
	public String toString() {
		return left + ":" + right;
	}
}
