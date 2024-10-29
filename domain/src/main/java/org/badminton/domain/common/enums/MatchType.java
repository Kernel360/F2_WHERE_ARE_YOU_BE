package org.badminton.domain.common.enums;

public enum MatchType {
	SINGLES("단식 게임"),
	DOUBLES("복식 게임");

	private final String description;

	MatchType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
