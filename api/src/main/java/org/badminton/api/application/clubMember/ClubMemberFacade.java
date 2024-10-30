package org.badminton.api.application.clubMember;

import java.util.List;
import java.util.Map;

import org.badminton.domain.domain.club.ClubRegistrationService;
import org.badminton.domain.domain.clubmember.command.ClubMemberBanCommand;
import org.badminton.domain.domain.clubmember.command.ClubMemberExpelCommand;
import org.badminton.domain.domain.clubmember.command.ClubMemberRoleUpdateCommand;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.info.ClubMemberBanRecordInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberJoinInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberWithdrawInfo;
import org.badminton.domain.domain.clubmember.service.ClubMemberService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubMemberFacade {
	private final ClubMemberService clubMemberService;

	private final ClubRegistrationService clubRegistrationService;

	@Transactional
	public ClubMemberJoinInfo joinClub(String memberToken, String clubToken) {
		return clubMemberService.joinClub(memberToken, clubToken);
	}

	public ClubMemberInfo updateClubMemberRole(ClubMemberRoleUpdateCommand command, Long clubMemberId) {
		return clubMemberService.updateClubMemberRole(command, clubMemberId);
	}

	public Map<ClubMember.ClubMemberRole, List<ClubMemberInfo>> findAllClubMembers(String clubToken) {
		return clubMemberService.findAllClubMembers(clubToken);
	}

	public ClubMemberBanRecordInfo expelClubMember(ClubMemberExpelCommand command, Long clubMemberId) {
		return clubMemberService.expelClubMember(command, clubMemberId);
	}

	public ClubMemberBanRecordInfo banClubMember(ClubMemberBanCommand command, Long clubMemberId) {
		return clubMemberService.banClubMember(command, clubMemberId);
	}

	public ClubMemberWithdrawInfo withDrawClubMember(Long clubMemberId) {
		return clubMemberService.withDrawClubMember(clubMemberId);
	}

	public ClubMemberInfo getClubMember(String memberToken, String clubToken) {
		return clubMemberService.getClubMember(memberToken, clubToken);
	}

}
