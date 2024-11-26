package org.badminton.domain.domain.match.command;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class MatchCommand {

	@Getter
	@Builder
	@ToString
	public static class UpdateSetScore {
		private final int score1;
		private final int score2;

	}
}
