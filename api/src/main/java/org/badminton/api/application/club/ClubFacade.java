package org.badminton.api.application.club;

import org.badminton.domain.common.policy.ClubMemberPolicy;
import org.badminton.domain.domain.club.ClubService;
import org.badminton.domain.domain.club.command.ClubCreateCommand;
import org.badminton.domain.domain.club.command.ClubUpdateCommand;
import org.badminton.domain.domain.club.event.UpdateClubEvent;
import org.badminton.domain.domain.club.info.ClubApplicantInfo;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.club.info.ClubCreateInfo;
import org.badminton.domain.domain.club.info.ClubDeleteInfo;
import org.badminton.domain.domain.club.info.ClubDetailsInfo;
import org.badminton.domain.domain.club.info.ClubUpdateInfo;
import org.badminton.domain.domain.clubmember.service.ClubMemberService;
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
	private final ClubMemberPolicy clubMemberPolicy;

	@Transactional(readOnly = true)
	public Page<ClubCardInfo> readAllClubs(Pageable pageable) {
		return clubService.readAllClubs(pageable);
	}
	
	@Transactional
	public ClubDetailsInfo readClub(String clubToken) {
		var club = clubService.readClub(clubToken);
		int clubMembersCount = clubMemberService.countExistingClub(clubToken);
		eventPublisher.publishEvent(new ReadClubEvent(clubToken));
		return ClubDetailsInfo.from(club, clubMembersCount);
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
	public ClubUpdateInfo updateClubInfo(ClubUpdateCommand clubUpdateCommand, String clubToken, String memberToken) {
		clubMemberPolicy.validateClubOwner(memberToken, clubToken);
		eventPublisher.publishEvent(new UpdateClubEvent());
		return clubService.updateClub(clubUpdateCommand, clubToken, memberToken);
	}

	@Transactional
	public ClubDeleteInfo deleteClubInfo(String memberToken, String clubToken) {
		clubMemberPolicy.validateClubOwner(memberToken, clubToken);
		clubMemberService.deleteAllClubMembers(clubToken);
		return clubService.deleteClub(clubToken);
	}

	@Transactional
	public Page<ClubApplicantInfo> readClubApplicants(String memberToken, String clubToken, Pageable pageable) {
		clubMemberPolicy.validateAboveClubManager(memberToken, clubToken);
		return clubService.readClubApplicants(clubToken, pageable);
	}
}
