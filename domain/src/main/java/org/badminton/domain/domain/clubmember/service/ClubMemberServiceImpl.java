package org.badminton.domain.domain.clubmember.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.badminton.domain.common.exception.clubmember.ClubOwnerCantWithdrawException;
import org.badminton.domain.common.exception.clubmember.ClubMemberOwnerException;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.club.info.ClubCreateInfo;
import org.badminton.domain.domain.clubmember.ClubMemberReader;
import org.badminton.domain.domain.clubmember.ClubMemberStore;
import org.badminton.domain.domain.clubmember.command.ClubMemberBanCommand;
import org.badminton.domain.domain.clubmember.command.ClubMemberExpelCommand;
import org.badminton.domain.domain.clubmember.command.ClubMemberRoleUpdateCommand;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.info.ClubMemberBanRecordInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberMyPageInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberWithdrawInfo;
import org.badminton.domain.domain.clubmember.info.MemberIsClubMemberInfo;
import org.badminton.domain.domain.member.MemberReader;
import org.badminton.domain.domain.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubMemberServiceImpl implements ClubMemberService {

	private final MemberReader memberReader;
	private final ClubMemberReader clubMemberReader;
	private final ClubMemberStore clubMemberStore;

	@Override
	@Transactional
	public void clubMemberOwner(String memberToken, ClubCreateInfo clubInfo) {
		Member member = memberReader.getMember(memberToken);
		var club = new Club(clubInfo);
		ClubMember clubMember = new ClubMember(club, member, ClubMember.ClubMemberRole.ROLE_OWNER);
		clubMemberStore.store(clubMember);
	}

	@Override
	public ClubMemberInfo updateClubMemberRole(ClubMemberRoleUpdateCommand command, Long clubMemberId,
		String clubToken) {

		if (command.role() == ClubMember.ClubMemberRole.ROLE_OWNER) {
			assignNewOwner(clubToken);
		}

		ClubMember clubMember = clubMemberReader.getClubMember(clubMemberId);
		clubMember.updateClubMemberRole(command.role());
		clubOwnerProtect(clubMember);
		clubMemberStore.store(clubMember);

		return ClubMemberInfo.valueOf(clubMember);
	}

	private void assignNewOwner(String clubToken) {
		ClubMember clubOwner = clubMemberReader.getClubOwner(clubToken);
		clubOwner.updateClubMemberRole(ClubMember.ClubMemberRole.ROLE_USER);
		clubMemberStore.store(clubOwner);
	}

	@Override
	public Map<ClubMember.ClubMemberRole, List<ClubMemberInfo>> findAllClubMembers(String clubToken) {
		Map<ClubMember.ClubMemberRole, List<ClubMemberInfo>> responseMap = new TreeMap<>(
			new ClubMemberRoleComparator());

		List<ClubMember> clubMembers =
			clubMemberReader.getAllClubMemberByClubId(clubToken);

		clubMembers.stream()
			.map(ClubMemberInfo::valueOf)
			.forEach(clubMemberResponse -> {
				ClubMember.ClubMemberRole role = clubMemberResponse.role();
				responseMap.computeIfAbsent(role, clubMember -> new ArrayList<>()).add(clubMemberResponse);
			});

		return responseMap;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClubMemberMyPageInfo> getClubMembers(String memberToken) {
		List<ClubMember> clubMembers = clubMemberReader.getClubMembersByMemberToken(memberToken);
		return ClubMemberMyPageInfo.from(clubMembers);
	}

	@Override
	public ClubMemberInfo getClubMember(String memberToken, String clubToken) {
		ClubMember clubMember = clubMemberReader.getClubMemberByMemberTokenAndClubToken(clubToken, memberToken);
		return ClubMemberInfo.valueOf(clubMember);
	}

	@Override
	public ClubMemberBanRecordInfo expelClubMember(ClubMemberExpelCommand command, Long clubMemberId) {
		ExpelStrategy expelStrategy = new ExpelStrategy(clubMemberStore);
		ClubMember clubMember = getClubMember(clubMemberId);
		clubOwnerProtect(clubMember);
		return expelStrategy.execute(clubMember, command);
	}

	@Override
	public ClubMemberBanRecordInfo banClubMember(ClubMemberBanCommand command, Long clubMemberId) {
		BanStrategy banStrategy = new BanStrategy(clubMemberStore);
		ClubMember clubMember = getClubMember(clubMemberId);
		clubOwnerProtect(clubMember);
		return banStrategy.execute(clubMember, command);
	}

	@Override
	public ClubMemberWithdrawInfo withDrawClubMember(Long clubMemberId, String clubToken) {
		ClubMember clubMember = clubMemberReader.getClubMember(clubMemberId);
		checkClubOwner(clubMember, clubToken);
		clubMember.withdrawal();
		clubMemberStore.store(clubMember);
		return new ClubMemberWithdrawInfo(clubMember.getClub().getClubId(), clubMember.getClubMemberId(),
			clubMember.isDeleted());
	}

	private void checkClubOwner(ClubMember clubMember, String clubToken) {
		if (clubMember.getRole() == ClubMember.ClubMemberRole.ROLE_OWNER
			&& clubMemberReader.getClubMemberCountsByClubToken(clubToken) > 1) {
			throw new ClubOwnerCantWithdrawException(clubMember.getClubMemberId());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public boolean checkIfMemberBelongsToClub(String memberToken, String clubToken) {
		return clubMemberReader.checkIsClubMember(memberToken, clubToken);
	}

	@Override
	@Transactional
	public void deleteAllClubMembers(String clubToken) {
		List<ClubMember> clubMembers = clubMemberReader.getAllMember(clubToken);
		clubMembers.forEach(clubMember -> {
			clubMember.withdrawal();
			clubMemberStore.store(clubMember);
		});
	}

	@Override
	public Integer countByClubClubIdAndDeletedFalse(Long clubId) {
		return clubMemberReader.getClubMemberCounts(clubId);
	}

	@Override
	public List<ClubCardInfo> getClubsByMemberToken(String memberToken) {
		List<ClubMember> clubMembers = clubMemberReader.getClubMembersByMemberToken(memberToken);

		List<Club> clubs = clubMembers.stream()
			.map(ClubMember::getClub)
			.distinct()
			.toList();

		return clubs.stream()
			.map(club -> {
				Map<Member.MemberTier, Long> tierCounts = club.getClubMemberCountByTier();
				return ClubCardInfo.from(club, tierCounts);
			})
			.toList();
	}

	@Override
	public MemberIsClubMemberInfo checkIsClubMember(String memberToken, String clubToken) {
		boolean isClubMember = clubMemberReader.checkIsClubMember(memberToken, clubToken);
		if (isClubMember) {
			ClubMember clubMember = clubMemberReader.getClubMemberByMemberTokenAndClubToken(
				clubToken, memberToken);
			return MemberIsClubMemberInfo.fromClubMember(true, clubMember);
		}
		return MemberIsClubMemberInfo.fromClubMember(false, null);

	}

	private ClubMember getClubMember(Long clubMemberId) {
		return clubMemberReader.getClubMember(clubMemberId);
	}

	private void clubOwnerProtect(ClubMember clubMember) {
		if (clubMember.getRole().equals(ClubMember.ClubMemberRole.ROLE_OWNER)) {
			throw new ClubMemberOwnerException(clubMember.getClub().getClubToken());
		}
	}

}
