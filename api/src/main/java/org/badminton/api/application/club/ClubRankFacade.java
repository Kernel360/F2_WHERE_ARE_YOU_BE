package org.badminton.api.application.club;

import java.util.ArrayList;
import java.util.List;

import org.badminton.api.application.club.strategy.Rank;
import org.badminton.domain.domain.club.ClubService;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.statistics.ClubStatisticsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubRankFacade {
	private final ClubService clubService;
	private final ClubStatisticsService clubStatisticsService;

	@Transactional(readOnly = true)
	public List<ClubCardInfo> rankClub(Rank rank) {
		var clubStatisticsList = clubStatisticsService.getAll();

		var rankResult = rank.arrangeByStrategy(clubStatisticsList);

		List<ClubCardInfo> clubList = new ArrayList<>();
		rankResult.stream()
			.limit(10)
			.forEach(clubStatistics -> {
				var club = clubService.readClubById(clubStatistics.getClub().getClubId());
				clubList.add(club);
			});

		return clubList;
	}

	@Transactional(readOnly = true)
	public List<ClubCardInfo> readRecentlyClub() {
		return clubService.readRecentlyClub();
	}
}
