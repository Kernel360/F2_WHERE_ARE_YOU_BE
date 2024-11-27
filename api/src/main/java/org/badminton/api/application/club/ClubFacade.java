package org.badminton.api.application.club;

import java.util.List;
import java.util.Map;

import org.badminton.domain.domain.club.ClubService;
import org.badminton.domain.domain.club.command.ClubCreateCommand;
import org.badminton.domain.domain.club.command.ClubUpdateCommand;
import org.badminton.domain.domain.club.info.ClubApplicantInfo;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.club.info.ClubCreateInfo;
import org.badminton.domain.domain.club.info.ClubDeleteInfo;
import org.badminton.domain.domain.club.info.ClubDetailsInfo;
import org.badminton.domain.domain.club.info.ClubUpdateInfo;
import org.badminton.domain.domain.clubmember.service.ClubMemberService;
import org.badminton.domain.domain.member.entity.Member;
import org.badminton.domain.domain.statistics.event.CreateClubEvent;
import org.badminton.domain.domain.statistics.event.ReadClubEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubFacade {
	private final ClubService clubService;
	private final ClubMemberService clubMemberService;
	private final ApplicationEventPublisher eventPublisher;

	@Transactional(readOnly = true)
	public Page<ClubCardInfo> readAllClubs(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return clubService.readAllClubs(pageable);
	}

	@Transactional
	public ClubDetailsInfo readClub(String clubToken) {
		var club = clubService.readClub(clubToken);
		Map<Member.MemberTier, Long> memberCountByTier = club.getClubMemberCountByTier();
		int clubMembersCount = clubMemberService.countExistingClub(clubToken);
		eventPublisher.publishEvent(new ReadClubEvent(clubToken));
		return ClubDetailsInfo.from(club, memberCountByTier,
			clubMembersCount);
	}

	@Transactional
	public Page<ClubCardInfo> searchClubs(String keyword, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return clubService.searchClubs(keyword, pageable);
	}

	@Transactional
	public ClubCreateInfo createClub(ClubCreateCommand createCommand, String memberToken) {
		ClubCreateInfo clubCreateInfo = clubService.createClub(createCommand);
		clubMemberService.clubMemberOwner(memberToken, clubCreateInfo);
		eventPublisher.publishEvent(new CreateClubEvent(clubCreateInfo));
		return clubCreateInfo;
	}

	@Transactional
	public ClubUpdateInfo updateClubInfo(ClubUpdateCommand clubUpdateCommand, String clubToken) {
		return clubService.updateClub(clubUpdateCommand, clubToken);
	}

	@Transactional
	public ClubDeleteInfo deleteClubInfo(String clubToken) {
		clubMemberService.deleteAllClubMembers(clubToken);
		return clubService.deleteClub(clubToken);
	}

	@Transactional
	public List<ClubApplicantInfo> readClubApplicants(String clubToken) {
		return clubService.readClubApplicants(clubToken);
	}

}
