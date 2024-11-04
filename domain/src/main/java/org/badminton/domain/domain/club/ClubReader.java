package org.badminton.domain.domain.club;

import java.util.List;

import org.badminton.domain.domain.club.entity.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClubReader {
	Page<Club> readAllClubs(Pageable pageable);

	Page<Club> keywordSearch(String keyword, Pageable pageable);

	List<Club> readRecentlyClubs();

	Club readClub(String clubToken);

	Club readClubByClubId(Long clubId);
}
