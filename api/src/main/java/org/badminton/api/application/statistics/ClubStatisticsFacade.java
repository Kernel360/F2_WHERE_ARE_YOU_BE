package org.badminton.api.application.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.badminton.domain.domain.clubmember.service.ClubMemberService;
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

	@Scheduled(fixedRate = 3600000)
	public void approveClubMemberCount() {
		Map<Long, Integer> countByClubIdRegisterMemberCount = new HashMap<>();
		//클럽 아이디를 모두 조회해서
		List<Long> clubIds = clubStatisticsService.clubIdByList();
		// 아이디로 각 갯수를 구해서 저장한다.
		for (Long clubId : clubIds) {
			log.info("Approving club member count : {}", clubId);
			Integer count = clubMemberService.getClubMemberApproveCount(clubId);
			log.info("Approve ClubMemberApproveCount : {}", count);
			countByClubIdRegisterMemberCount.put(clubId, count);
		}
		for (Map.Entry<Long, Integer> entry : countByClubIdRegisterMemberCount.entrySet()) {
			Long clubId = entry.getKey();
			int count = entry.getValue();
			log.info("countByClubIdRegisterMemberCount : {}", count);

			clubStatisticsService.updateByCountAndClubId(clubId, count);
		}
	}
}
