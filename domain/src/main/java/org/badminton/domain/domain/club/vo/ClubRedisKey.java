package org.badminton.domain.domain.club.vo;

import lombok.Getter;

@Getter
public class ClubRedisKey {
	private static final String NAMESPACE = "club";
	private static final String TOP10_POPULAR = "top10:popular";
	private static final String TOP10_ACTIVITY = "top10:activity";
	private static final String TOP10_RECENTLY = "top10:recently";

	private ClubRedisKey() {

	}

	public static String getTop10PopularKey() {
		return String.format("%s:%s", NAMESPACE, TOP10_POPULAR);
	}

	public static String getTop10ActivityKey() {
		return String.format("%s:%s", NAMESPACE, TOP10_ACTIVITY);
	}

	public static String getTop10RecentlyKey() {
		return String.format("%s:%s", NAMESPACE, TOP10_RECENTLY);
	}

}
