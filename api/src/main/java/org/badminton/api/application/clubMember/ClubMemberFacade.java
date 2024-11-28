package org.badminton.api.application.clubMember;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubMemberFacade {
	private final ClubMemberService clubMemberService;
	private final ClubApplyService clubApplyService;
	private final MailService mailService;

	public ApplyClubInfo applyClub(String memberToken, String clubToken, ClubApplyCommand command) {
		mailService.prepareClubApplyEmail(clubToken, memberToken);
		return clubApplyService.applyClub(memberToken, clubToken, command.applyReason());

	}

	public ApproveApplyInfo approveApplying(Long clubApplyId) {
		mailService.prepareClubApplyResultEmail(clubApplyId, true);
		return clubApplyService.approveApplying(clubApplyId);
	}

	public RejectApplyInfo rejectApplying(Long clubApplyId) {
		mailService.prepareClubApplyResultEmail(clubApplyId, false);
		return clubApplyService.rejectApplying(clubApplyId);
	}

	public ClubMemberInfo updateClubMemberRole(ClubMemberRoleUpdateCommand command, Long clubMemberId,
		String clubToken) {
		return clubMemberService.updateClubMemberRole(command, clubMemberId, clubToken);
	}

	public Page<ClubMemberInfo> findAllActiveClubMembers(String clubToken, int page, int size) {
		PageRequest pageable = PageRequest.of(page, size);
		return clubMemberService.findAllActiveClubMembers(clubToken, pageable);
	}

	public Page<ClubMemberInfo> findAllBannedClubMembers(String clubToken, int page, int size) {
		Sort sort = Sort.by(Sort.Order.by("role")
			.with(Sort.NullHandling.NATIVE));
		PageRequest pageable = PageRequest.of(page, size);
		return clubMemberService.findAllBannedClubMembers(clubToken, pageable);
	}

	public ClubMemberBanRecordInfo expelClubMember(ClubMemberExpelCommand command, Long clubMemberId) {
		return clubMemberService.expelClubMember(command, clubMemberId);
	}

	public ClubMemberBanRecordInfo banClubMember(ClubMemberBanCommand command, Long clubMemberId) {
		return clubMemberService.banClubMember(command, clubMemberId);
	}

	public ClubMemberWithdrawInfo withdrawClubMember(Long clubMemberId, String clubToken) {
		return clubMemberService.withdrawClubMember(clubMemberId, clubToken);
	}

	public ClubMemberInfo getClubMember(String memberToken, String clubToken) {
		return clubMemberService.getClubMember(memberToken, clubToken);
	}

	public MemberIsClubMemberInfo checkClubMember(String memberToken, String clubToken) {
		return clubMemberService.checkIsClubMember(memberToken, clubToken);
	}

}
