package org.badminton.domain.domain.club.vo;

import lombok.Getter;

@Getter
public class ClubRedisKey {
	private static final String NAMESPACE = "club";
	private static final String TOP10_POPULAR = "top10:popular";

	private ClubRedisKey() {

	}

	public static String getTop10PopularKey() {
		return String.format("%s:%s", NAMESPACE, TOP10_POPULAR);
	}

}
