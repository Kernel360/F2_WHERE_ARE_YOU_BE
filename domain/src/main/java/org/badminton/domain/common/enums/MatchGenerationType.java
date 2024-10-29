package org.badminton.domain.common.enums;

public enum MatchGenerationType {
	RANDOM("랜덤 생성"),
	TIER("티어별 생성");

	private final String description;

	MatchGenerationType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
