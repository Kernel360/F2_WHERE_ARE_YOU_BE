package org.badminton.domain.domain.league.vo;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Address {
	private static final String SEPARATOR = " ";
	private static final int CITY_INDEX = 0;

	private String fullAddress;
	private String region;

	public Address(String fullAddress) {
		this.fullAddress = fullAddress;
		this.region = parseRegion(fullAddress);
	}

	protected Address() {
	}

	private String parseRegion(String fullAddress) {
		String[] stringToken = fullAddress.split(SEPARATOR);
		return stringToken[CITY_INDEX];
	}
}
