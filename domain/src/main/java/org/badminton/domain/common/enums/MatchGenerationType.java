package org.badminton.domain.common.enums;

import lombok.Getter;

@Getter
public enum MatchGenerationType {
	FREE("프리 모드"),
	TOURNAMENT("토너먼트 모드");

	private final String description;

	MatchGenerationType(String description) {
		this.description = description;
	}

}
