package org.badminton.domain.domain.league.enums;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum Region {
	ALL("전체"),
	SEOUL("서울"),
	GYEONGGI("경기"),
	INCHEON("인천"),
	GANGWON("강원"),
	DAEJEON("대전"),
	SEJONG("세종"),
	CHUNGNAM("충남"),
	CHUNGBUK("충북"),
	DAEGU("대구"),
	GYEONGBUK("경북"),
	BUSAN("부산"),
	ULSAN("울산"),
	GYEONGNAM("경남"),
	GWANGJU("광주"),
	JEONNAM("전남"),
	JEONBUK("전북"),
	JEJU("제주");

	private final String name;

	Region(String name) {
		this.name = name;
	}

	public static String getNameByCode(String code) {
		return Arrays.stream(Region.values())
			.filter(region -> region.name().equalsIgnoreCase(code))
			.findFirst()
			.map(Region::getName)
			.orElse(null); // 없는 코드명일 경우 null 반환
	}
}
