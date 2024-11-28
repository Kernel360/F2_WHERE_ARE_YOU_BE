package org.badminton.api.application.clubMember;

import org.badminton.domain.common.policy.ClubMemberPolicy;
import org.badminton.domain.domain.club.ClubApplyService;
import org.badminton.domain.domain.club.command.ClubApplyCommand;
import org.badminton.domain.domain.clubmember.command.ClubMemberBanCommand;
import org.badminton.domain.domain.clubmember.command.ClubMemberExpelCommand;
import org.badminton.domain.domain.clubmember.command.ClubMemberRoleUpdateCommand;
import org.badminton.domain.domain.clubmember.info.ApplyClubInfo;
import org.badminton.domain.domain.clubmember.info.ApproveApplyInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberBanRecordInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberWithdrawInfo;
import org.badminton.domain.domain.clubmember.info.MemberIsClubMemberInfo;
import org.badminton.domain.domain.clubmember.info.RejectApplyInfo;
import org.badminton.domain.domain.clubmember.service.ClubMemberService;
import org.badminton.domain.domain.mail.MailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubMemberFacade {
	private final ClubMemberService clubMemberService;
	private final ClubApplyService clubApplyService;
	private final MailService mailService;
	private final ClubMemberPolicy clubMemberPolicy;

	@Transactional
	public ApplyClubInfo applyClub(String memberToken, String clubToken, ClubApplyCommand command) {
		ApplyClubInfo applyClubInfo = clubApplyService.applyClub(memberToken, clubToken, command.applyReason());
		mailService.prepareClubApplyEmail(clubToken, memberToken);
		return applyClubInfo;
	}

	public ApproveApplyInfo approveApplying(Long clubApplyId, String memberToken, String clubToken) {
		clubMemberPolicy.validateClubOwner(memberToken, clubToken);
		mailService.prepareClubApplyResultEmail(clubApplyId, true);
		return clubApplyService.approveApplying(clubApplyId);
	}

	public RejectApplyInfo rejectApplying(Long clubApplyId, String memberToken, String clubToken) {
		clubMemberPolicy.validateClubOwner(memberToken, clubToken);
		mailService.prepareClubApplyResultEmail(clubApplyId, false);
		return clubApplyService.rejectApplying(clubApplyId);
	}

	public ClubMemberInfo updateClubMemberRole(ClubMemberRoleUpdateCommand command, Long clubMemberId,
		String clubToken, String memberToken) {
		clubMemberPolicy.validateClubOwner(memberToken, clubToken);
		return clubMemberService.updateClubMemberRole(command, clubMemberId, clubToken);
	}

	public Page<ClubMemberInfo> findAllActiveClubMembers(String memberToken, String clubToken, Pageable pageable) {
		clubMemberPolicy.validateClubMember(memberToken, clubToken);
		return clubMemberService.findAllActiveClubMembers(clubToken, pageable);
	}

	public Page<ClubMemberInfo> findAllBannedClubMembers(String memberToken, String clubToken, Pageable pageable) {
		clubMemberPolicy.validateClubMember(memberToken, clubToken);
		return clubMemberService.findAllBannedClubMembers(clubToken, pageable);
	}

	public ClubMemberBanRecordInfo expelClubMember(ClubMemberExpelCommand command, Long clubMemberId,
		String memberToken, String clubToken) {
		clubMemberPolicy.validateAboveClubManager(memberToken, clubToken);
		return clubMemberService.expelClubMember(command, clubMemberId);
	}

	public ClubMemberBanRecordInfo banClubMember(ClubMemberBanCommand command, Long clubMemberId, String memberToken,
		String clubToken) {
		clubMemberPolicy.validateAboveClubManager(memberToken, clubToken);
		return clubMemberService.banClubMember(command, clubMemberId);
	}

	public ClubMemberWithdrawInfo withdrawClubMember(Long clubMemberId, String clubToken) {
		return clubMemberService.withdrawClubMember(clubMemberId, clubToken);
	}

	public ClubMemberInfo getClubMember(String memberToken, String clubToken) {
		clubMemberPolicy.validateClubMember(memberToken, clubToken);
		return clubMemberService.getClubMember(memberToken, clubToken);
	}

	public MemberIsClubMemberInfo checkClubMember(String memberToken, String clubToken) {
		return clubMemberService.checkIsClubMember(memberToken, clubToken);
	}

}
