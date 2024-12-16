package org.badminton.domain.domain.match.vo;

import org.badminton.domain.common.enums.MatchType;

import lombok.Getter;

@Getter
public class MatchRedisKey {
	private final String key;
	private final String field;
	private final MatchType matchType;
	private final Long matchId;
	private final int setNumber;

	public MatchRedisKey(String key, String field) {
		this.key = key;
		this.field = field;
		this.matchType = parseMatchType(key);
		this.matchId = parseMatchId(key);
		this.setNumber = Integer.parseInt(field);
	}

	private MatchType parseMatchType(String key) {
		if (key.length() < 6) {
			return null;
		}
		if (key.substring(0, 5).equals(MatchType.SINGLES.getDescription())) {
			return MatchType.SINGLES;
		} else if (key.substring(0, 5).equals(MatchType.DOUBLES.getDescription())) {
			return MatchType.DOUBLES;
		}
		return null;
	}

	private Long parseMatchId(String key) {
		if (key.length() < 6) {
			return null;
		}
		String substring = key.substring(5);
		return Long.valueOf(substring);
	}
}
