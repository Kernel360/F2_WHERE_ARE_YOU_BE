package org.badminton.api.application.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.badminton.domain.domain.clubmember.service.ClubMemberService;
import org.badminton.domain.domain.league.LeagueService;
import org.badminton.domain.domain.statistics.ClubStatisticsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubStatisticsFacade {
	private final ClubStatisticsService clubStatisticsService;
	private final ClubMemberService clubMemberService;
	private final LeagueService leagueService;

	@Scheduled(fixedRate = 3600000)
	public void approveClubMemberCount() {
		Map<Long, Integer> countByClubIdRegisterMemberCount = new HashMap<>();
		List<Long> clubIds = clubStatisticsService.clubIdByList();
		for (Long clubId : clubIds) {
			Integer count = clubMemberService.countExistingClub(clubId);
			countByClubIdRegisterMemberCount.put(clubId, count);
		}
		for (Map.Entry<Long, Integer> entry : countByClubIdRegisterMemberCount.entrySet()) {
			Long clubId = entry.getKey();
			int count = entry.getValue();
			clubStatisticsService.updateByCountAndClubId(clubId, count);
		}
		log.info("approve club member checking!");
	}

	@Scheduled(fixedRate = 3600000)
	public void leagueCount() {
		Map<Long, Integer> countByClubIdRegisterMemberCount = new HashMap<>();
		List<Long> clubIds = clubStatisticsService.clubIdByList();
		for (Long clubId : clubIds) {
			Integer count = leagueService.getLeagueCountByClubId(clubId);
			countByClubIdRegisterMemberCount.put(clubId, count);
		}
		for (Map.Entry<Long, Integer> entry : countByClubIdRegisterMemberCount.entrySet()) {
			Long clubId = entry.getKey();
			int count = entry.getValue();
			clubStatisticsService.updateLeagueCountByClubIdAndCount(clubId, count);
		}
	}

}
