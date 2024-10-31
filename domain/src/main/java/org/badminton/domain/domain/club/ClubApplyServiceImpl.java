package org.badminton.domain.domain.club;

import org.badminton.domain.common.exception.member.MemberAlreadyExistInClubException;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.entity.ClubApply;
import org.badminton.domain.domain.clubmember.ClubMemberReader;
import org.badminton.domain.domain.clubmember.ClubMemberStore;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.info.ApplyClubInfo;
import org.badminton.domain.domain.clubmember.info.ApproveApplyInfo;
import org.badminton.domain.domain.clubmember.info.RejectApplyInfo;
import org.badminton.domain.domain.member.MemberReader;
import org.badminton.domain.domain.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubApplyServiceImpl implements ClubApplyService {

	private final ClubMemberStore clubMemberStore;
	private final ClubMemberReader clubMemberReader;
	private final ClubApplyReader clubApplyReader;
	private final ClubApplyStore clubApplyStore;
	private final ClubReader clubReader;
	private final MemberReader memberReader;

	@Override
	public ApproveApplyInfo approveApplying(Long clubApplyId) {

		ClubApply clubApply = clubApplyReader.getClubApply(clubApplyId);
		clubApply.approvedClubMember();
		clubApplyStore.store(clubApply);

		Club club = clubApply.getClub();
		Member member = clubApply.getMember();
		ClubMember clubMember = new ClubMember(club, member, ClubMember.ClubMemberRole.ROLE_USER);

		boolean isClubMember = clubMemberReader.checkIsClubMember(member.getMemberToken(), club.getClubToken());
		if (isClubMember) {
			throw new MemberAlreadyExistInClubException(member.getMemberToken(), club.getClubToken());
		}

		clubMemberStore.store(clubMember);

		return ApproveApplyInfo.fromClubApply(clubApply);
	}

	@Override
	public RejectApplyInfo rejectApplying(Long clubApplyId) {
		ClubApply clubApply = clubApplyReader.getClubApply(clubApplyId);
		clubApply.rejectedClubMember();
		clubApplyStore.store(clubApply);

		return RejectApplyInfo.fromClubApply(clubApply);
	}

	@Override
	@Transactional
	public ApplyClubInfo applyClub(String memberToken, String clubToken) {

		Club club = clubReader.readClub(clubToken);
		Member member = memberReader.getMember(memberToken);

		ClubApply clubApply = new ClubApply(club, member);

		clubApplyReader.validateApply(clubToken, memberToken);

		clubApplyStore.store(clubApply);
		return ApplyClubInfo.fromClubApply(clubApply);
	}
}
