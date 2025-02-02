package org.badminton.domain.domain.club;

import java.util.List;

import org.badminton.domain.common.exception.club.ClubNameDuplicateException;
import org.badminton.domain.common.policy.ClubMemberPolicy;
import org.badminton.domain.domain.club.command.ClubCreateCommand;
import org.badminton.domain.domain.club.command.ClubUpdateCommand;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.entity.ClubApply;
import org.badminton.domain.domain.club.info.ClubApplicantInfo;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.club.info.ClubCreateInfo;
import org.badminton.domain.domain.club.info.ClubDeleteInfo;
import org.badminton.domain.domain.club.info.ClubSummaryInfo;
import org.badminton.domain.domain.club.info.ClubUpdateInfo;
import org.badminton.domain.domain.club.vo.ClubCache;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

	private final ClubReader clubReader;
	private final ClubStore clubStore;
	private final ClubApplyReader clubApplyReader;
	private final ClubMemberPolicy clubMemberPolicy;

	@Override
	@Transactional(readOnly = true)
	public Page<ClubCardInfo> readAllClubs(Pageable pageable) {
		Page<ClubCache> clubsPage = clubReader.readAllClubs(pageable);
		var response = ClubPage.builder().clubCaches(clubsPage).build();
		return response.clubToRedisPageCardInfo();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ClubCardInfo> searchClubs(String keyword, Pageable pageable) {
		Page<ClubCache> clubsPage = getClubs(keyword, pageable);
		var response = ClubPage.builder().clubCaches(clubsPage).build();
		return response.clubToRedisPageCardInfo();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClubCardInfo> getRecentlyCreatedClub() {

		return clubReader.readRecentlyCreatedClubs();
	}

	@Override
	@Transactional
	public void refreshRecentlyCreatedClubsCache() {
		clubReader.refreshRecentlyCreatedClubsCache();
	}

	@Override
	@Transactional(readOnly = true)
	public ClubSummaryInfo readClub(String clubToken) {
		var club = clubReader.readExistingClub(clubToken);
		return ClubSummaryInfo.from(club);
	}

	@Override
	@Transactional
	public ClubCreateInfo createClub(ClubCreateCommand clubCreateCommand) {
		Club club = new Club(clubCreateCommand.clubName(),
			clubCreateCommand.clubDescription(),
			clubCreateCommand.clubImage());
		String clubName = club.getClubName();
		if (clubReader.UniqueClubName(clubName)) {
			throw new ClubNameDuplicateException(clubName);
		}
		var createResponse = clubStore.store(club);
		return ClubCreateInfo.toClubCreateInfo(createResponse);
	}

	@Override
	@Transactional
	public ClubUpdateInfo updateClub(ClubUpdateCommand clubUpdateCommand, String clubToken, String memberToken) {
		clubMemberPolicy.validateClubOwner(memberToken, clubToken);
		var club = clubReader.readExistingClub(clubToken);
		club.updateClub(clubUpdateCommand);
		var updated = clubStore.store(club);
		return ClubUpdateInfo.toClubUpdateInfo(updated);
	}

	@Override
	@Transactional
	public ClubDeleteInfo deleteClub(String clubToken) {
		var club = clubReader.readClub(clubToken);
		club.doWithdrawal();
		var deleted = clubStore.store(club);
		return ClubDeleteInfo.clubDeleteInfo(deleted);
	}

	@Override
	@Transactional(readOnly = true)
	public ClubCardInfo readClubById(Long clubId) {
		var club = clubReader.readClubByClubId(clubId);
		return ClubCardInfo.from(club);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ClubApplicantInfo> readClubApplicants(String clubToken, Pageable pageable) {
		return clubApplyReader.getClubApplyByClubToken(clubToken,
			ClubApply.ApplyStatus.PENDING, pageable);
	}

	private Page<ClubCache> getClubs(String keyword, Pageable pageable) {
		if (keyword == null || keyword.trim().isEmpty()) {
			return clubReader.readAllClubs(pageable);
		}
		return clubReader.keywordSearch(keyword, pageable);
	}
}
