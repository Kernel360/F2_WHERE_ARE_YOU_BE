package org.badminton.domain.domain.club;

import java.util.List;

import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.club.vo.ClubCache;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClubReader {
	Page<ClubCache> readAllClubs(Pageable pageable);

	Page<ClubCache> keywordSearch(String keyword, Pageable pageable);

	List<ClubCardInfo> readRecentlyCreatedClubs();

	List<Club> readAllClubs();

	Club readClub(String clubToken);

	Club readClubByClubId(Long clubId);

	Club readExistingClub(String clubToken);

	boolean UniqueClubName(String clubName);

	List<ClubCardInfo> refreshRecentlyCreatedClubsCache();
}
