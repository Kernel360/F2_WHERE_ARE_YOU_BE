package org.badminton.api.application.club;

import java.util.Map;

import org.badminton.api.interfaces.oauth.dto.CustomOAuth2Member;
import org.badminton.domain.domain.club.ClubService;
import org.badminton.domain.domain.club.command.ClubCreateCommand;
import org.badminton.domain.domain.club.command.ClubUpdateCommand;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.club.info.ClubCreateInfo;
import org.badminton.domain.domain.club.info.ClubDeleteInfo;
import org.badminton.domain.domain.club.info.ClubDetailsInfo;
import org.badminton.domain.domain.club.info.ClubUpdateInfo;
import org.badminton.domain.domain.clubmember.service.ClubMemberService;
import org.badminton.domain.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubFacade {
	private final ClubService clubService;
	private final ClubMemberService clubMemberService;

	public Page<ClubCardInfo> readAllClubs(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return clubService.readAllClubs(pageable);
	}

	public ClubDetailsInfo readClub(String clubToken, CustomOAuth2Member clubMember) {
		String memberToken = clubMember.getMemberToken();
		var club = clubService.readClub(clubToken);
		Map<Member.MemberTier, Long> memberCountByTier = club.getClubMemberCountByTier();
		boolean isClubMember = clubMemberService.checkIfMemberBelongsToClub(memberToken, clubToken);
		int clubMembersCount = club.clubMembers().size();
		return ClubDetailsInfo.fromClubEntityAndMemberCountByTier(club, memberCountByTier, isClubMember,
			clubMembersCount);
	}

	public Page<ClubCardInfo> searchClubs(String keyword, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return clubService.searchClubs(keyword, pageable);
	}

	public ClubCreateInfo createClub(ClubCreateCommand createCommand, String memberToken) {
		clubMemberService.checkMyOwnClub(memberToken);
		var clubCreateInfo = clubService.createClub(createCommand);
		clubMemberService.clubMemberOwner(memberToken, clubCreateInfo);
		return clubCreateInfo;
	}

	public ClubUpdateInfo updateClubInfo(ClubUpdateCommand clubUpdateCommand, String clubToken) {
		return clubService.updateClub(clubUpdateCommand, clubToken);
	}

	public ClubDeleteInfo deleteClubInfo(String clubToken) {
		clubMemberService.deleteAllClubMembers(clubToken);
		return clubService.deleteClub(clubToken);
	}

}
