package org.badminton.domain.domain.club;

import java.util.Objects;

import org.badminton.domain.domain.club.entity.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ClubSearch {
	private final String keyword;
	private final Page<Club> findClubs;

	@Builder
	ClubSearch(String keyword, Pageable pageable, ClubReader clubReader) {
		this.keyword = keyword;
		if (Objects.isNull(keyword) || keyword.trim().isEmpty()) {
			this.findClubs = clubReader.readAllClubs(pageable);
		} else {
			this.findClubs = clubReader.keywordSearch(keyword, pageable);
		}
	}

	public static ClubSearch searchResult(String keyword, Pageable pageable, ClubReader clubReader) {
		return ClubSearch.builder()
			.keyword(keyword)
			.clubReader(clubReader)
			.pageable(pageable)
			.build();
	}

}
