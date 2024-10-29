package org.badminton.domain.domain.league.enums;

import lombok.Getter;

@Getter
public enum StartDateType {
	START_DAY(1),
	START_HOUR(0),
	START_MINUTE(0);

	private final Integer description;

	StartDateType(int value) {
		this.description = value;
	}
}
