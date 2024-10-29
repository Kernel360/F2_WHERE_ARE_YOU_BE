package org.badminton.domain.domain.clubmember.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.badminton.domain.common.exception.clubmember.ClubMemberDuplicateException;
import org.badminton.domain.domain.club.ClubReader;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.info.ClubCreateInfo;
import org.badminton.domain.domain.clubmember.ClubMemberReader;
import org.badminton.domain.domain.clubmember.ClubMemberStore;
import org.badminton.domain.domain.clubmember.command.ClubMemberBanCommand;
import org.badminton.domain.domain.clubmember.command.ClubMemberExpelCommand;
import org.badminton.domain.domain.clubmember.command.ClubMemberRoleUpdateCommand;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.info.ClubMemberBanRecordInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberDetailInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberJoinInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberMyPageInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberWithdrawInfo;
import org.badminton.domain.domain.member.MemberReader;
import org.badminton.domain.domain.member.entity.Member;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubMemberServiceImpl implements ClubMemberService {

	private final ClubReader clubReader;
	private final MemberReader memberReader;
	private final ClubMemberReader clubMemberReader;
	private final ClubMemberStore clubMemberStore;

	@Override
	public ClubMemberJoinInfo joinClub(
		String memberToken, String clubToken) {

		Club club = clubReader.readClub(clubToken);

		Member member = memberReader.getMember(memberToken);

		if (clubMemberReader.isExist(memberToken)) {
			throw new ClubMemberDuplicateException(clubToken, memberToken);
		}

		ClubMember clubMember = new ClubMember(club, member, ClubMember.ClubMemberRole.ROLE_USER);

		clubMemberStore.store(clubMember);

		return ClubMemberJoinInfo.from(clubMember);
	}

	@Override
	public void clubMemberOwner(String memberToken, ClubCreateInfo clubInfo) {
		Member member = memberReader.getMember(memberToken);
		var club = new Club(clubInfo);
		ClubMember clubMember = new ClubMember(club, member, ClubMember.ClubMemberRole.ROLE_OWNER);
		clubMemberStore.store(clubMember);
	}

	@Override
	public ClubMemberInfo updateClubMemberRole(ClubMemberRoleUpdateCommand command, Long clubMemberId) {
		ClubMember clubMember = clubMemberReader.getClubMember(clubMemberId);
		clubMember.updateClubMemberRole(command.role());
		clubMemberStore.store(clubMember);
		return ClubMemberInfo.valueOf(clubMember);
	}

	@Override
	public Map<ClubMember.ClubMemberRole, List<ClubMemberInfo>> findAllClubMembers(String clubToken) {
		Map<ClubMember.ClubMemberRole, List<ClubMemberInfo>> responseMap = new TreeMap<>(
			new ClubMemberRoleComparator());

		List<ClubMember> clubMembers =
			clubMemberReader.getAllClubMemberByClubId(clubToken);

		clubMembers.stream()
			.map(ClubMemberInfo::valueOf) // 멤버를 응답 객체로 변환
			.forEach(clubMemberResponse -> {
				ClubMember.ClubMemberRole role = clubMemberResponse.role();
				responseMap.computeIfAbsent(role, clubMember -> new ArrayList<>()).add(clubMemberResponse);
			});

		return responseMap;
	}

	@Override
	public ClubMemberMyPageInfo getClubMember(String memberToken) {
		ClubMember clubMember = clubMemberReader.getClubMemberByMemberToken(memberToken);
		return ClubMemberMyPageInfo.from(clubMember);
	}

	@Override
	public ClubMemberDetailInfo getClubMemberDetail(String memberToken) {
		ClubMember clubMember = clubMemberReader.getClubMemberByMemberToken(memberToken);
		return ClubMemberDetailInfo.from(clubMember);
	}

	@Override
	public ClubMemberBanRecordInfo expelClubMember(ClubMemberExpelCommand command, Long clubMemberId) {
		ExpelStrategy expelStrategy = new ExpelStrategy(clubMemberStore);

		ClubMember clubMember = getClubMember(clubMemberId);
		return expelStrategy.execute(clubMember, command);
	}

	@Override
	public ClubMemberBanRecordInfo banClubMember(ClubMemberBanCommand command, Long clubMemberId) {
		BanStrategy banStrategy = new BanStrategy(clubMemberStore);
		ClubMember clubMember = getClubMember(clubMemberId);
		return banStrategy.execute(clubMember, command);
	}

	@Override
	public ClubMemberWithdrawInfo withDrawClubMember(Long clubMemberId) {
		ClubMember clubMember = clubMemberReader.getClubMember(clubMemberId);
		clubMember.withdrawal();
		clubMemberStore.store(clubMember);
		return new ClubMemberWithdrawInfo(clubMember.getClub().getClubId(), clubMember.getClubMemberId(),
			clubMember.isDeleted());
	}

	@Override
	public boolean checkIfMemberBelongsToClub(String memberToken, String clubToken) {
		return clubMemberReader.checkIsClubMember(memberToken, clubToken);
	}

	@Override
	public void checkMyOwnClub(String memberToken) {
		clubMemberReader.checkIsClubOwner(memberToken);
	}

	@Override
	public void deleteAllClubMembers(String clubToken) {
		List<ClubMember> clubMembers = clubMemberReader.getAllMember(clubToken);
		clubMembers.forEach(clubMember -> {
			clubMember.deleteClubMember();
			clubMemberStore.store(clubMember);
		});
	}

	@Override
	public ClubMemberDetailInfo getClubMemberDetailByClubToken(String clubToken, String memberToken) {
		ClubMember clubMember = clubMemberReader.getClubMemberByMemberTokenAndClubToken(clubToken, memberToken);
		return ClubMemberDetailInfo.from(clubMember);
	}

	private ClubMember getClubMember(Long clubMemberId) {
		return clubMemberReader.getClubMember(clubMemberId);
	}

}
