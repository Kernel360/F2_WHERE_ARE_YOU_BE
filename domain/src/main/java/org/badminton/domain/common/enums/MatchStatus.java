package org.badminton.domain.common.enums;

public enum MatchStatus {
	NOT_STARTED("시작 전"),
	IN_PROGRESS("진행 중"),
	FINISHED("종료"),
	BYE("부전승");

	private final String description;

	MatchStatus(String description) {
		this.description = description;
	}
}
